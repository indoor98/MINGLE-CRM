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
      { path: "", component: () => import("pages/IndexPage.vue") },
    ],
  },
];

export default routes;
