<template>
  <div id="mypage">
    <div>
      <AdminSidebar></AdminSidebar>
    </div>
    <div id="view">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import AdminSidebar from "./AdminSidebar.vue";
export default {
  components: { AdminSidebar },
};
</script>
<style scoped>
#mypage {
  min-height: 62vh; /* 추가 */
  display: flex; /* 추가 */
}
#view {
  margin: 0 auto;
  width: 60%;
  padding-top: 70px;
}
</style>
