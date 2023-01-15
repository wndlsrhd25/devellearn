<template>
  <div id="panel">
    <sidebar-menu :menu="menu" :hideToggle="true" :show-one-child="true">
    </sidebar-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [
        {
          header: true,
          title: "Team Study",
          hiddenOnCollapse: true,
        },
        {
          title: "멘토링",
        },
        {
          title: "프로젝트",
        },
        {
          title: "스터디",
        },
        {
          header: true,
          title: "Community",
          hiddenOnCollapse: true,
        },
        {
          title: "지식QnA",
        },
        {
          title: "자유게시판",
        },
        {
          title: "테크뉴스",
        },
        {
          title: "기술리뷰",
        },
        {
          header: true,
          title: "Board",
          hiddenOnCollapse: true,
        },
        {
          title: "공지사항",
        },
        {
          title: "이벤트",
        },
        {
          title: "about 개미",
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
}
</style>
