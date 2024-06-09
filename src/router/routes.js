const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/IndexPage.vue") },
      { path: "customer", component: () => import("pages/CustomerPage.vue") },
      { path: "review", component: () => import("pages/ReviewPage.vue") },
      { path: "inquiry", component: () => import("pages/InquiryPage.vue") },
      {
        path: "inquiry/:inquiryId",
        name: "InquiryDetailPage",
        component: () => import("pages/InquiryDetailPage.vue"),
        props: true,
      },
      { path: "customers/:customerId/hotel/reservations", component: () => import("pages/reservation/ReservationPage.vue") },
      { path: "customers/:customerId/hotel/reservations/:reservationId", component: () => import("pages/reservation/ReservationPage.vue") },
    ],
  },
  {
    path: "/mingle",
    component: () => import("layouts/customer/Index.vue"),
    children: [
      { path: "customer", component: () => import("pages/customer/CustomerPage.vue") },
      { path: "customer-detail/:id", component: () => import("pages/customer/CustomerDetailPage.vue") },
      // { path: "reservation", component: () => import("pages/customer/ReservationPage.vue") },  // 추가
      // { path: "consultation", component: () => import("pages/customer/ConsultationPage.vue") },  // 추가
      // { path: "sales", component: () => import("pages/customer/SalesPage.vue") },  // 추가
      // { path: "statistics", component: () => import("pages/customer/StatisticsPage.vue") },  // 추가
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
