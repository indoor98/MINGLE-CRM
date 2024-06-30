import EmployeeRequestList from "pages/admin/EmployeeRequestList.vue";
import AllEmployeeRequestList from "pages/admin/AllEmployeeRequestList.vue";

const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/IndexPage.vue") },
      {
        path: "review",
        component: () => import("pages/review/ReviewPage.vue"),
      },
      {
        path: "voucher-marketer/draft",
        component: () => import("pages/voucher/marketer/DraftPage.vue"),
      },
      {
        path: "voucher-marketer/email",
        component: () => import("pages/voucher/marketer/EmailManagePage.vue"),
      },
      {
        path: "voucher-manager/requested",
        component: () => import("pages/voucher/manager/RequestedPage.vue"),
      },
      {
        path: "voucher-manager/confirmed",
        component: () => import("pages/voucher/manager/ConfirmedPage.vue"),
      },
      {
        path: "voucher-manager/all",
        component: () => import("pages/voucher/manager/AllVouchersPage.vue"),
      },
      {
        path: "reward",
        component: () => import("src/pages/reward/RewardPage.vue"),
      },
      {
        path: "inquiry",
        component: () => import("src/pages/inquiry/InquiryPage.vue"),
      },
      {
        path: "inquiry/:inquiryId",
        name: "InquiryDetailPage",
        component: () => import("src/pages/inquiry/InquiryDetailPage.vue"),
        props: true,
        children: [
          {
            path: "reply",
            component: () => import("src/pages/inquiry/InquiryReply.vue"),
          },
        ],
      },
      {
        path: "customers/:customerId/hotel/reservations",
        component: () => import("pages/reservation/ReservationPage.vue"),
      },
      {
        path: "customers/:customerId/hotel/reservations/:reservationId",
        component: () => import("pages/reservation/ReservationPage.vue"),
      },
      {
        path: "customers/:customerId/hotel/reservations",
        component: () => import("pages/reservation/ReservationPage.vue"),
      },
      {
        path: "customers/:customerId/hotel/reservations/:reservationId",
        component: () => import("pages/reservation/ReservationPage.vue"),
      },
      { path: "", component: () => import("pages/IndexPage.vue") },
      {
        path: "customers",
        component: () => import("pages/customer/CustomerPage.vue"),
      },
      {
        path: "customer-detail/:id",
        component: () => import("pages/customer/detail/CustomerDetailPage.vue"),
        props: true, // 이 부분이 중요합니다. 동적 경로로 전달된 props를 컴포넌트 props로 사용할 수 있게 해줍니다.
      },
      {
        path: "signup",
        component: () => import("pages/auth/SignUpPage.vue"),
      },
      {
        path: "signin",
        component: () => import("pages/auth/SignInPage.vue"),
      },
      {
        path: "email",
        component: () => import("pages/email/EmailPage.vue"),
      },
      {
        path: "email/:eventId",
        name: "emailDetailPage",
        component: () => import("pages/email/EmailLogDetailPage.vue"),
      },
      {
        path: "voucher-email",
        name: "voucherEmailSendPage",
        component: () =>
          import("src/components/voucher/marketer/SendVoucherEmail.vue"),
        props: (route) => ({
          customerId: route.query.customerId,
          voucherCode: route.query.voucherCode,
        }),
      },
      {
        path: "/statistics",
        name: "statisticsPage",
        component: () => import("pages/statistics/StatisticsPage.vue"),
      },
      {
        path: "/statistics-test",
        name: "statisticsTestPage",
        component: () =>
          import("pages/statistics/statistics-test/StatisticsTestPage.vue"),
      },
      {
        path: "/statistics-hotel",
        name: "statisticsHotelPage",
        component: () =>
          import("pages/statistics/statistics-test/StatisticsHotelPage.vue"),
      },
      {
        path: "/statistics-dining",
        name: "statisticsDiningPage",
        component: () =>
          import("pages/statistics/statistics-test/StatisticsDiningPage.vue"),
      },
      {
        path: "/view-log",
        name: "viewLogPage",
        component: () => import("pages/log/view/ViewLogPage.vue"),
      },
      {
        path: "/mypage",
        name: "myPage",
        component: () => import("pages/mypage/MyPage.vue"),
        meta: { requiresAuth: true },
      },
      {
        path: '/admin-request',
        name: 'EmployeeRequestList',
        component: EmployeeRequestList
      },
      {
        path: '/admin-request-all',
        name: 'AllEmployeeRequestList',
        component: AllEmployeeRequestList
      },
      {
        path: 'signup-success',
        component: () => import("pages/auth/SignUpSuccess.vue"),
      },
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
