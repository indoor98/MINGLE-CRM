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
        path: "voucher",
        component: () => import("src/pages/voucher/VoucherPage.vue"),
      },
      { path: "inquiry", component: () => import("pages/InquiryPage.vue") },
      {
        path: "inquiry/:inquiryId",
        name: "InquiryDetailPage",
        component: () => import("pages/InquiryDetailPage.vue"),
        props: true,
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
        path: "customer",
        component: () => import("pages/customer/CustomerPage.vue"),
      },
      {
        path: "customer-detail/:id",
        component: () => import("pages/customer/CustomerDetailPage.vue"),
      },
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
