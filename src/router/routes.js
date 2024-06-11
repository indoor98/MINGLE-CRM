const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/IndexPage.vue") },
      { path: "review", component: () => import("pages/ReviewPage.vue") },
      {
        path: "voucher",
        component: () => import("src/pages/voucher/VoucherPage.vue"),
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
