<template>
  <div id="mypage">
    <div>
      <CompanySidebar></CompanySidebar>
    </div>
    <div id="view">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import CompanySidebar from "./CompanySidebar.vue";
export default {
  components: { CompanySidebar },
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
