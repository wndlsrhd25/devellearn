<template>
  <div>
    <b-nav vertical class="w-25">
      <b-nav-item
        class="nav_item"
        v-for="item in menu"
        :key="item.title"
        @click="search(item.title)"
        >{{ item.title }}</b-nav-item
      >
    </b-nav>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [
        { title: "전체강의" },
        { title: "프론트엔드" },
        { title: "백엔드/서버" },
        { title: "모바일" },
        { title: "게임" },
        { title: "임베디드" },
        { title: "자료구조/알고리즘" },
        { title: "보안" },
        { title: "데이터사이언스" },
        { title: "데이터베이스" },
        { title: "자격증" },
        { title: "프로그래밍언어" },
      ],
      category: "",
    };
  },
  methods: {
    search(title) {
      this.$emit("search", title);
    },
  },
};
</script>

<style scoped>
.nav_item {
  width: 180px;
  border: 1px solid lightgray;
  border-bottom: none;
  text-align: left;
  color: black !important;
}
ul > :last-child {
  border: 1px solid lightgray;
}

.nav-item a {
  color: black;
}

/* .w-25 :hover:not(:first-child) {
  background-color: lightskyblue;
} */
</style>
