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
          href: { path: "/community/mentor" },
        },
        {
          title: "프로젝트",
          href: { path: "/community/project" },
        },
        {
          title: "스터디",
          href: { path: "/community/study" },
        },
        {
          header: true,
          title: "Community",
          hiddenOnCollapse: true,
        },
        {
          title: "지식QnA",
          href: { path: "/community/qna" },
        },
        {
          title: "자유게시판",
          href: { path: "/community/board" },
        },
        {
          title: "테크뉴스",
          href: { path: "/community/board2" },
        },
        {
          title: "기술리뷰",
          href: { path: "/community/board3" },
        },
        {
          header: true,
          title: "Board",
          hiddenOnCollapse: true,
        },
        {
          title: "공지사항",
          href: { path: "/community/board4" },
        },
        {
          title: "이벤트",
          href: { path: "/community/board5" },
        },
        {
          title: "about 개미",
          href: { path: "/community/about" },
        },
      ],
    };
  },
};
</script>

<style scoped>

@font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

*{
  font-family: GmarketSansMedium;
}
.v-sidebar-menu {
  padding-top: 150px;
  padding-left: 30px;
  background-color: white;
  z-index: 1;
  width: 260px;
}
</style>
