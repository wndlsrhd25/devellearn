<template>
  <div id="community">
    <!-- 이 div안에 작성 -->
    <SideBar></SideBar>
    <div id="view">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/community/SideBar.vue";
export default {
  components: {
    SideBar,
  },
};
</script>

<style scoped>
#community {
  min-height: 80vh;
  display: flex;
}
#view {
  margin-right: 0 auto;
  margin-left: 260px;
  width: 70%;
}
</style>
