<template>
  <q-page>
    <div class="q-pa-md">
      <div class="flex justify-between items-center">
        <h2 class="text-h6">승인 요청 전 바우처 목록</h2>
        <q-btn
          color="primary"
          label="+ 바우처 생성"
          @click="showCreationModal = true"
        />
      </div>
      <q-card class="q-mt-md">
        <q-card-section>
          <q-table
            :rows="vouchers"
            :columns="columns"
            row-key="id"
            :loading="loading"
            :pagination="{ rowsPerPage: 50 }"
            style="cursor: pointer"
          >
            <template v-slot:body="props">
              <q-tr :props="props" @click="showVoucherDetail(props.row)">
                <q-td v-for="col in columns" :key="col.name" :props="props">
                  <template v-if="col.name === 'createdReason'">
                    {{ toTenWords(props.row[col.field]) }}
                  </template>
                  <template
                    v-else-if="
                      col.name === 'createdDate' ||
                      col.name === 'startDate' ||
                      col.name === 'endDate'
                    "
                  >
                    {{ toDate(props.row[col.field]) }}
                  </template>
                  <template v-else-if="col.name === 'request'">
                    <q-btn
                      label="요청"
                      color="primary"
                      @click.stop="requestVoucher(props.row.voucherId)"
                    ></q-btn>
                    <q-btn
                      label="삭제"
                      color="secondary"
                      @click.stop="deleteVoucher(props.row.voucherId)"
                    ></q-btn>
                  </template>
                  <template v-else>
                    {{ props.row[col.field] }}
                  </template>
                </q-td>
              </q-tr>
            </template>
            <template v-slot:no-data>
              <q-tr>
                <q-td :colspan="columns.length" class="text-center">
                  바우처가 없습니다.
                </q-td>
              </q-tr>
            </template>
          </q-table>
        </q-card-section>
      </q-card>
      <q-card class="q-mt-md">
        <q-card-section v-if="errorMessage">
          <p style="color: red" class="text-center">{{ errorMessage }}</p>
        </q-card-section>
      </q-card>

      <!-- 바우처 상세 모달 -->
      <q-dialog v-model="showDialog" persistent>
        <VoucherDetail :voucher="selectedVoucher" @close="closeVoucherDetail" />
      </q-dialog>

      <!-- 바우처 생성 모달 -->
      <q-dialog v-model="showCreationModal">
        <q-card style="width: 30%; padding: 20px">
          <q-card-section>
            <div class="text-h6">바우처 생성</div>
          </q-card-section>

          <q-card-section>
            <q-btn @click="showCustomerListModal = true" label="고객 조회">
              <q-dialog v-model="showCustomerListModal">
                <VoucherCustomerListModal
                  @selected-customer="onSelectedCustomer"
                />
              </q-dialog>
            </q-btn>
            <q-input v-model="voucher.customerId" label="회원 ID" readonly />
            <q-input v-model="customerEmail" label="회원 이메일" readonly />
            <q-input v-model="voucher.reason" label="생성 이유" type="string" />
            <q-input v-model="voucher.amount" label="금액" type="number" />
            <q-input
              v-model="voucher.startDate"
              mask="date"
              :rules="['date']"
              label="시작일"
            >
              <template v-slot:append>
                <q-icon name="event" class="cursor-pointer">
                  <q-popup-proxy
                    cover
                    transition-show="scale"
                    transition-hide="scale"
                  >
                    <q-date v-model="voucher.startDate">
                      <div class="row items-center justify-end">
                        <q-btn
                          v-close-popup
                          label="선택"
                          color="primary"
                          flat
                        />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>
            <q-input
              v-model="voucher.endDate"
              mask="date"
              :rules="['date']"
              label="만료일"
            >
              <template v-slot:append>
                <q-icon name="event" class="cursor-pointer">
                  <q-popup-proxy
                    cover
                    transition-show="scale"
                    transition-hide="scale"
                  >
                    <q-date v-model="voucher.endDate">
                      <div class="row items-center justify-end">
                        <q-btn
                          v-close-popup
                          label="선택"
                          color="primary"
                          flat
                        />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>
          </q-card-section>

          <q-card-actions align="right">
            <q-btn
              flat
              label="취소"
              color="primary"
              @click="closeVoucherCreation"
            />
            <q-btn flat label="생성" color="primary" @click="createVoucher" />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { Dialog, Notify } from "quasar";
import VoucherDetail from "../../../components/voucher/VoucherDetail.vue";
import VoucherCustomerListModal from "../../../components/voucher/marketer/VoucherCustomerListModal.vue";

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const showCreationModal = ref(false);
const voucher = ref({
  customerId: "",
  amount: 0,
  startDate: "",
  endDate: "",
});
const showDialog = ref(false);
const selectedVoucher = ref({});
const showCustomerListModal = ref(false);
const selectedCustomer = ref();
const customerEmail = ref("");

const toTenWords = (beforeWord) => {
  const afterword =
    beforeWord.length <= 10 ? beforeWord : beforeWord.substring(0, 10) + "...";
  return afterword;
};

const toDate = (beforeDate) => {
  return (
    beforeDate.substring(0, 4) +
    "-" +
    beforeDate.substring(5, 7) +
    "-" +
    beforeDate.substring(8, 10)
  );
};

const columns = ref([
  {
    name: "voucherId",
    label: "바우처 ID",
    align: "center",
    field: "voucherId",
    sortable: true,
  },
  // {
  //   name: "customerId",
  //   label: "회원 ID",
  //   align: "center",
  //   field: "customerId",
  //   sortable: true,
  // },
  {
    name: "customerName",
    label: "회원 이름",
    align: "center",
    field: "customerName",
    sortable: true,
  },
  {
    name: "amount",
    label: "금액",
    align: "center",
    field: "amount",
    sortable: true,
  },
  {
    name: "createdDate",
    label: "생성 일자",
    align: "center",
    field: "createdDate",
    sortable: true,
  },
  {
    name: "startDate",
    label: "시작 일자",
    align: "center",
    field: "startDate",
    sortable: true,
  },
  {
    name: "endDate",
    label: "만료 일자",
    align: "center",
    field: "endDate",
    sortable: true,
  },
  {
    name: "createdReason",
    label: "생성 이유",
    align: "center",
    field: "createdReason",
  },
  {
    name: "request",
    label: "요청/삭제",
    align: "center",
    field: "request",
  },
]);

const fetchVouchers = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/vouchers/before-requested"
    );
    vouchers.value = response.data.data.sort(
      (a, b) => b.voucherId - a.voucherId
    );
    errorMessage.value = "";
  } catch (error) {
    console.error("바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value = "바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const createVoucher = async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/v1/vouchers", {
      customerId: voucher.value.customerId,
      amount: voucher.value.amount,
      reason: voucher.value.reason,
      startDate: toDate(voucher.value.startDate) + "T00:00:00",
      endDate: toDate(voucher.value.endDate) + "T23:59:59",
    });
    // 새로운 바우처 목록을 다시 불러옴
    fetchVouchers();
    showCreationModal.value = false;
  } catch (error) {
    console.error("바우처 생성 중 에러 발생:", error);
    errorMessage.value = "바우처 생성 중 에러가 발생했습니다.";
  } finally {
    closeVoucherCreation();
  }
};

const showVoucherDetail = (voucher) => {
  selectedVoucher.value = voucher;
  showDialog.value = true;
};

const closeVoucherDetail = () => {
  showDialog.value = false;
  selectedVoucher.value = {};
};

const closeVoucherCreation = () => {
  showCreationModal.value = false;

  voucher.value.customerId = "";
  voucher.value.amount = 0;
  voucher.value.startDate = "";
  voucher.value.endDate = "";
  voucher.value.reason = "";
  customerEmail.value = "";
};

const requestVoucher = async (voucherId) => {
  try {
    const response = await axios.post(
      `http://localhost:8080/api/v1/vouchers/request/${voucherId}`
    );
    // 바우처 목록을 다시 불러옴
    fetchVouchers();
    Notify.create({
      type: "positive",
      message: "요청이 성공적으로 완료되었습니다.",
      color: "green",
    });
  } catch (error) {
    console.error("바우처 요청 중 에러 발생:", error);
    Notify.create({
      type: "negative",
      message: "요청 중 에러가 발생했습니다.",
    });
  }
};

const deleteVoucher = async (voucherId) => {
  try {
    const response = await axios.delete(
      `http://localhost:8080/api/v1/vouchers/${voucherId}`
    );
    // 바우처 목록을 다시 불러옴
    fetchVouchers();
    Notify.create({
      color: "green",
      type: "positive",
      message: "삭제가 성공적으로 완료되었습니다.",
    });
  } catch (error) {
    console.error("바우처 삭제 중 에러 발생:", error);
    Notify.create({
      type: "negative",
      message: "삭제 중 에러가 발생했습니다.",
    });
  }
};

const onSelectedCustomer = (customer) => {
  console.log(customer);
  selectedCustomer.value = customer;
  showCustomerListModal.value = false;
  voucher.value.customerId = customer.at(0).id;
  customerEmail.value = customer.at(0).email;
};
onMounted(fetchVouchers);
</script>
