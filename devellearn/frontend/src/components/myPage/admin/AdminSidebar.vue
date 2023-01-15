<template>
  <div id="panel">
    <sidebar-menu :menu="menu" :hideToggle="true" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [
        {
          header: true,
          title: "마이페이지",
          hiddenOnCollapse: true,
        },
        {
          title: "강의관리",
          href: { path: "/adminPage/lectureMng/regist" },
        },
        {
          title: "수익금관리",
          href: { path: "/adminPage/payMng" },
        },
        {
          title: "신고관리",
          href: { path: "/adminPage/reportMng" },
        },
        {
          title: "이력신청관리",
          href: { path: "/adminPage/careerMng/careerList" },
        },
      ],
    };
  },
};
</script>

<style scoped>
.v-sidebar-menu {
  padding-top: 150px;
  padding-left: 30px;
  background-color: white;
  z-index: 1;
  width: 260px;
}
</style>
