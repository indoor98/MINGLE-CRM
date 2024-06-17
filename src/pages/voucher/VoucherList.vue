<template>
  <div>
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
        >
          <template v-slot:body-cell-createdReason="props">
            <q-td :props="props">
              {{ toTenWords(props.row.createdReason) }}
            </q-td>
          </template>
          <template v-slot:body-cell-createdDate="props">
            <q-td :props="props">
              {{ toDate(props.row.endDate) }}
            </q-td>
          </template>
          <template v-slot:body-cell-startDate="props">
            <q-td :props="props">
              {{ toDate(props.row.startDate) }}
            </q-td>
          </template>
          <template v-slot:body-cell-endDate="props">
            <q-td :props="props">
              {{ toDate(props.row.endDate) }}
            </q-td>
          </template>
          <template v-slot:body-cell-request="props">
            <q-td :props="props">
              <q-btn
                label="요청"
                color="primary"
                @click="requestVoucher(props.row.voucherId)"
              >
              </q-btn>
              <q-btn
                label="삭제"
                color="secondary"
                @click="deleteVoucher(props.row.voucherId)"
              >
              </q-btn>
            </q-td>
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

    <!-- 바우처 생성 모달 -->
    <q-dialog v-model="showCreationModal">
      <q-card>
        <q-card-section>
          <div class="text-h6">바우처 생성</div>
        </q-card-section>

        <q-card-section>
          <q-input v-model="voucher.customerId" label="회원 ID" />
          <q-input v-model="voucher.amount" label="금액" type="number" />
          <q-input v-model="voucher.reason" label="생성 이유" type="string" />
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
                      <q-btn v-close-popup label="Close" color="primary" flat />
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
                      <q-btn v-close-popup label="Close" color="primary" flat />
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
            @click="showCreationModal = false"
          />
          <q-btn flat label="생성" color="primary" @click="createVoucher" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { Dialog, Notify } from "quasar";

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const showCreationModal = ref(false);
const showRequestModal = ref(false);
const voucher = ref({
  customerId: "",
  amount: 0,
  startDate: "",
  endDate: "",
});

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
  {
    name: "customerId",
    label: "회원 ID",
    align: "center",
    field: "customerId",
    sortable: true,
  },
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
    vouchers.value = response.data.data;
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
  }
};

const requestVoucher = async (voucherId) => {
  Dialog.create({
    title: "확인",
    message: `${voucherId}번 바우처 발급을 요청하시겠습니까?`,
    ok: "예",
    cancel: "아니오",
  }).onOk(async () => {
    try {
      await axios.post(
        `http://localhost:8080/api/v1/vouchers/request/${voucherId}`
      );
      Notify.create({
        type: "positive",
        message: "바우처가 성공적으로 승인되었습니다.",
      });
      // Refresh the voucher list after request
      fetchVouchers();
    } catch (error) {
      console.error("바우처 승인 중 에러 발생:", error);
      Notify.create({
        type: "negative",
        message: "바우처 승인 중 에러가 발생했습니다.",
      });
    }
  });
};

const deleteVoucher = async (voucherId) => {
  Dialog.create({
    title: "확인",
    message: `${voucherId}번 바우처를 삭제하시겠습니까?`,
    ok: "예",
    cancel: "아니오",
  }).onOk(async () => {
    try {
      await axios.delete(`http://localhost:8080/api/v1/vouchers/${voucherId}`);
      Notify.create({
        type: "positive",
        message: "바우처가 성공적으로 삭제되었습니다.",
      });
      // Refresh the voucher list after request
      fetchVouchers();
    } catch (error) {
      console.error("바우처 삭제 중 에러 발생:", error);
      Notify.create({
        type: "negative",
        message: "바우처 삭제 중 에러가 발생했습니다.",
      });
    }
  });
};

onMounted(() => {
  fetchVouchers();
});
</script>
<style scoped>
.flex {
  display: flex;
}
.justify-between {
  justify-content: space-between;
}
.items-center {
  align-items: center;
}
</style>
