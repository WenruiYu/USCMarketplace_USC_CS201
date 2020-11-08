import Vue from 'vue'
import App from './App.vue'
import router from './router';
import  {KEY_STORE} from "./common/common";
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import {toastError} from "./common/common";

Vue.config.productionTip = false;
Vue.use(ElementUI);

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title} | USC Marketplace`;
  const token = localStorage.getItem(KEY_STORE.TOKEN);
  if (!token && to.path !== '/login' && to.path !== '/') {
    next('/');
  }
  else {
    next();
  }
});
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
