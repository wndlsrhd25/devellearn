<template>
  <div id="panel">
    <sidebar-menu :menu="menu" :hideToggle="true" :show-one-child="true" />
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
          title: "나의기업",
          href: { path: "/companyPage/companyInfo" },
        },
        {
          title: "지원내역",
          href: { path: "/companyPage/applyMember" },
        },
        {
          title: "개인정보 수정",
          href: { path: "/companyPage/updateComInfo" },
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
