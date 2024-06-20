<!-- <template>
  <q-dialog v-model="isDialogOpen">
    <q-card>
      <q-card-section>
        <div class="text-h6">바우처 상세 정보</div>
      </q-card-section>

      <q-card-section>
        <div v-if="voucher">
          <p><strong>ID:</strong> {{ voucher.voucherId }}</p>
          <p><strong>고객 이름:</strong> {{ voucher.customerName }}</p>
          <p><strong>생성 사유:</strong> {{ voucher.createdReason }}</p>
          <p><strong>요청 날짜:</strong> {{ toDate(voucher.requestDate) }}</p>
          <p><strong>금액:</strong> {{ voucher.amount }}</p>
          <p v-if="voucher.confirmDate">
            <strong>승인 일자:</strong> {{ toDate(voucher.confirmDate) }}
          </p>
          <p v-if="voucher.rejectedReason">
            <strong>거절 사유:</strong> {{ voucher.rejectedReason }}
          </p>
          <p v-if="voucher.confirmerName">
            <strong>검토 매니저 이름:</strong> {{ voucher.confirmerName }}
          </p>
          <p v-if="voucher.voucherCode">
            <strong>바우처 코드:</strong> {{ voucher.voucherCode }}
          </p>
          <p v-if="voucher.status">
            <strong>전환 여부:</strong> {{ voucher.status }}
          </p>
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="닫기" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";

const props = defineProps({
  voucher: {
    type: Object,
    required: true,
  },
  isOpen: {
    type: Boolean,
    required: true,
  },
});

const isDialogOpen = ref(props.isOpen);

watch(
  () => props.isOpen,
  (newVal) => {
    isDialogOpen.value = newVal;
  }
);

const toDate = (dateStr) => {
  if (!dateStr) return "";
  return `${dateStr.substring(0, 4)}-${dateStr.substring(
    5,
    7
  )}-${dateStr.substring(8, 10)}`;
};
</script>

<style scoped></style> -->
