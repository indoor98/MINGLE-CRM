const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/IndexPage.vue") },
      { path: "customer", component: () => import("pages/CustomerPage.vue") },
      { path: "review", component: () => import("pages/ReviewPage.vue") },

    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
  {
    path: "/mingle",
    component: () => import("layouts/customer/Index.vue"),
    children: [
      { path: "", component: () => import("pages/customer/IndexPage.vue") },
      { path: "/customer-detail/:id", component: () => import("pages/customer/CustomerDetailPage.vue") }
    ],
  },
];

export default routes;
