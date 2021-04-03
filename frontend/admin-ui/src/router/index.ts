import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Dashboard',
    component: () => import('../views/Dashboard.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '/calendars',
        name: 'Calendars',
        component: () =>
          import(
            /* webpackChunkName: "calendars" */ '../views/calendars/Calendars.vue'
          ),
        children: [
          {
            path: ':route',
            name: 'Calendars/Calendar',
            props: true,
            component: () =>
              import(
                /* webpackChunkName: "calendar-view" */ '../views/calendars/CalendarView.vue'
              ),
            children: [
              {
                path: 'workslots',
                name: 'Calendars/Calendar/WorkSlots',
                props: true,
                component: () =>
                  import(
                    /* webpackChunkName: "calendar-workslots" */ '../views/calendars/configuration/WorkSlots.vue'
                  )
              },
              {
                path: 'services',
                name: 'Calendars/Calendar/Services',
                props: true,
                component: () =>
                  import(
                    /* webpackChunkName: "calendar-services" */ '../views/calendars/configuration/Services.vue'
                  )
              },
              {
                path: 'reservation',
                name: 'Calendars/Calendar/Reservation',
                props: true,
                component: () =>
                  import(
                    /* webpackChunkName: "calendar-reservation" */ '../views/calendars/configuration/Reservation.vue'
                  )
              }
            ]
          }
        ]
      },
      {
        path: '/stats',
        name: 'Stats',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "stats" */ '../views/Stats.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/Login.vue')
  },
  {
    path: '/logout',
    name: 'Logout',
    component: () => import('../views/auth/Logout.vue')
  },
  {
    path: '/create-company',
    name: 'CreateCompany',
    meta: {
      requiresAuth: true
    },
    component: () => import('../views/company/CreateCompany.vue')
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
