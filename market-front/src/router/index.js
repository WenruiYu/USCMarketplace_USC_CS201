import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  // mode: 'history',
  routes: [
    // {
    //   path: '/',
    //   redirect: '/dashboard'
    // },
    {
      path: '/',
      component: () => import(/* webpackChunkName: "home" */ '../page/Home.vue'),
      meta: { title: 'Home' },
      children: [
        {
          path: '/',
          component: () => import(/* webpackChunkName: "404" */ '../page/ListingSquare'),
          meta: { title: 'Listing Square' }
        },
        {
          path: '/favorite',
          component: () => import(/* webpackChunkName: "404" */ '../page/FavoriteListing'),
          meta: { title: 'Favorite Listings' }
        },
        {
          path: '/personal',
          component: () => import(/* webpackChunkName: "404" */ '../page/PersonalListing'),
          meta: { title: 'My Listings' }
        },
        {
          path: '/account',
          component: () => import(/* webpackChunkName: "404" */ '../page/Account'),
          meta: { title: 'Account' }
        },
        {
          path: '/404',
          component: () => import(/* webpackChunkName: "404" */ '../page/404.vue'),
          meta: { title: '404' }
        },
      ]
    },
    {
      path: '/login',
      component: () => import(/* webpackChunkName: "login" */ '../page/Login.vue'),
      meta: { title: 'Login to USCMarket' }
    },
    {
      path: '*',
      redirect: { path: '/404' }
    }
  ]
});
