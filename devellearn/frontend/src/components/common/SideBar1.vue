<template>
  <div>
    <b-nav vertical class="w-25">
      <b-nav-item class="nav_item" disabled>강의 카테고리</b-nav-item>
      <b-nav-item
        class="nav_item"
        v-for="item in menu"
        :key="item.title"
        to="#"
      >
      </b-nav-item>
    </b-nav>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [
        {
          title: "전체강의",
          link: "#",
        },
        {
          title: "프론트 엔드",
          href: "#",
        },
        {
          title: "백 엔드/서버",
          href: "#",
        },
        {
          title: "모바일",
          href: "#",
        },
        {
          title: "게임",
          href: "#",
        },
        {
          title: "임베디드",
          href: "#",
        },
        {
          title: "자료구조/알고리즘",
          href: "#",
        },
        {
          title: "보안",
          href: "#",
        },
        {
          title: "데이터 사이언스",
          href: "#",
        },
        {
          title: "데이터 베이스",
          href: "#",
        },
        {
          title: "자격증",
          href: "#",
        },
        {
          title: "프로그래밍 언어",
          href: "#",
        },
      ],
    };
  },
};
</script>

<style scoped>
.nav_item {
  width: 180px;
  border: 1px solid lightgray;
  text-align: left;
  color: black !important;
}
</style>
