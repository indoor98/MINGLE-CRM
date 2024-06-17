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
        path: "voucher-manager",
        component: () => import("src/pages/voucher/ManagerVoucherPage.vue"),
      },
      {
        path: "voucher-marketer",
        component: () => import("src/pages/voucher/MarketerVoucherPage.vue"),
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
        component: () => import("pages/voucher/voucherEmailSend.vue"),
        props: (route) => ({
          customerId: route.query.customerId,
          voucherCode: route.query.voucherCode,
        }),
      },
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
