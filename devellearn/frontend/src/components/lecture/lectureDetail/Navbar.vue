<template>
  <div id="ld_navbar">
    <div class="nav_menu">
      <b-nav>
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/lectureDetail/intro',
                query: { no: no },
                hash: '#info',
              })
              .catch(() => {})
          "
          >강의소개</span
        >
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/lectureDetail/intro',
                query: { no: no },
                hash: '#curriculum',
              })
              .catch(() => {})
          "
          >커리큘럼</span
        >
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/lectureDetail/intro',
                query: { no: no },
                hash: '#review',
              })
              .catch(() => {})
          "
          >수강평</span
        >
        <!-- Q&A 는 컴포넌트 교체 -->
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/lectureDetail/qna',
                query: { no: no },
              })
              .catch(() => {})
          "
          >Q&A</span
        >
      </b-nav>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      no: "",
    };
  },
  created: function () {
    this.no = this.$route.query.no;
    console.log(this.$route.query);
  },
};
</script>
<style scoped>
#ld_navbar {
  border-top: 1px solid lightgray;
  border-bottom: 1px solid lightgray;
  margin-bottom: 40px;
  background-color: white;
  /* 스크롤 도달했을때 고정시킬 위치 */
  position: sticky;
  top: 124px;
}
.nav_menu {
  width: 60%;
  margin: auto;
  height: 42px;
}

.link {
  font-weight: bold;
  font-size: large;
  margin: 0 15px;
  line-height: 230%;
  cursor: pointer;
  width: 74px;
}

.link:hover {
  /* background-color: rgba(204, 204, 204, 0.5); */
  border-bottom: 4px solid black;
}

a {
  text-decoration: none !important;
}
</style>
