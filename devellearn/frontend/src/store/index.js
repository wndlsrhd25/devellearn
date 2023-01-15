import Vue from "vue";
import Vuex from "vuex";
import { userStore } from "@/store/modules/userStore";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    userStore,
  },
  state: {
    isjob: sessionStorage.getItem("isjob"),
  },
  getters: {
    getIsjob(state) {
      return state.isjob;
    },
  },
  mutations: {
    setIsJobTrue(state) {
      state.isjob = "true";
      sessionStorage.setItem("isjob", "true");
    },
    setIsJobFalse(state) {
      state.isjob = "false";
      sessionStorage.setItem("isjob", "false");
    },
  },
  actions: {},
});
