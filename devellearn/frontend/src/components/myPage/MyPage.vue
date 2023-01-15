<template>
  <div id="mypage">
    <div>
      <MySideBar></MySideBar>
    </div>
    <div id="view">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import MySideBar from "./MySideBar.vue";

export default {
  components: {
    MySideBar,
  },
  data() {
    return {
      form: {
        email: "",
        name: "",
        checked: [],
      },
      show: true,
      selected: null,
      options: [
        { value: null, text: "Please select an option" },
        { value: "a", text: "This is First option" },
        { value: "b", text: "Selected Option" },
        { value: { C: "3PO" }, text: "This is an option with object value" },
        { value: "d", text: "This one is disabled", disabled: true },
      ],
    };
  },

  methods: {
    onSubmit(event) {
      event.preventDefault();
      alert(JSON.stringify(this.form));
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
      this.form.email = "";
      this.form.name = "";
      this.form.checked = [];
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
</script>
<style scoped>
#mypage {
  min-height: 100vh; /* 추가 */
  display: flex; /* 추가 */
}
#view {
  margin: 0 auto;
  width: 60%;
  padding-top: 70px;
}
</style>
