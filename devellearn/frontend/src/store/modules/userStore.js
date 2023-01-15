export const userStore = {
  namespaced: true,
  state: {
    id: sessionStorage.getItem("id"),
    user: JSON.parse(sessionStorage.getItem("userData")),
    empUser: sessionStorage.getItem("empUser"),
  },
  getters: {
    getId(state) {
      return state.id;
    },
    getUserData(state) {
      return state.user;
    },
    getEmpUser(state) {
      return state.empUser;
    },
  },
  mutations: {
    setEmpFalse(state) {
      state.empUser = "false";
      sessionStorage.setItem("empUser", "false");
    },
    setEmpTrue(state) {
      state.empUser = "true";
      sessionStorage.setItem("empUser", "true");
    },
    setId(state, memberId) {
      state.id = memberId;
      sessionStorage.setItem("id", memberId);
    },
    setUserData(state, userData) {
      state.user = userData;
      state.user.password = "****";
      sessionStorage.setItem("userData", JSON.stringify(userData));
    },
    logout(state) {
      state.id = "";
      state.user = {};
      sessionStorage.removeItem("id");
      sessionStorage.removeItem("userData");
    },
  },
  actions: {},
};
