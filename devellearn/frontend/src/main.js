import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";

import {BootstrapVue, IconsPlugin} from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);
Vue.config.productionTip = false;

// 사이드바
import VueSidebarMenu from "vue-sidebar-menu";
import "@/vue-sidebar-menu.css";
Vue.use(VueSidebarMenu);

// 뷰티파이
import vuetify from "./plugins/vuetify";

//엘리멘트ui
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import locale from "element-ui/lib/locale/lang/ko";

Vue.use(ElementUI, {
  locale,
});

Vue.config.productionTip = false;

//모먼트
import VueMoment from "vue-moment";

Vue.use(VueMoment);

//css
import "@/../public/vendor/bootstrap/css/bootstrap.min.css";
import "@/../public/fonts/font-awesome-4.7.0/css/font-awesome.min.css";
import "@/../public/fonts/iconic/css/material-design-iconic-font.min.css";
import "@/../public/fonts/linearicons-v1.0.0/icon-font.min.css";
import "@/../public/vendor/animate/animate.css";
import "@/../public/vendor/css-hamburgers/hamburgers.min.css";
import "@/../public/vendor/animsition/css/animsition.min.css";
import "@/../public/vendor/select2/select2.min.css";
import "@/../public/vendor/daterangepicker/daterangepicker.css";
import "@/../public/vendor/slick/slick.css";
import "@/../public/vendor/MagnificPopup/magnific-popup.css";
import "@/../public/vendor/perfect-scrollbar/perfect-scrollbar.css";
import "@/../public/css/util.css";
import "@/../public/css/main.css";
import "@/../public/images/icons/favicon.png";

import VueSweetalert2 from "vue-sweetalert2";

// If you don't need the styles, do not connect
import "sweetalert2/dist/sweetalert2.min.css";

Vue.use(VueSweetalert2);

// 사용시 this.$axios 로 호출
Vue.prototype.$http = axios;
Vue.prototype.$axios = axios;
axios.defaults.crossDomain = true;

//형제 컴포넌트 값 받을때 사용
export const eventBus = new Vue();

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
