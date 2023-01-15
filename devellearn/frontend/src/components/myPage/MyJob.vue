<template>
  <div id="my_learning">
    <!-- 헤더 -->
    <div class="nav_menu">
      <b-nav>
        <span
          class="link"
          @click="clickedRecruit"
          :class="{ clicked: isRecurit }"
          >관심채용</span
        >
        <span
          class="link"
          @click="clickedCompany"
          :class="{ clicked: isCompany }"
          >관심기업</span
        >
      </b-nav>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      isRecurit: true,
      isCompany: false,
    };
  },
  methods: {
    // 관심채용 클릭
    clickedRecruit: function () {
      this.$router
        .push({
          path: "/mypage/myJob/myRecruit",
        })
        .catch(() => {});
      this.isRecurit = true;
      this.isCompany = false;
    },
    // 관심기업 클릭
    clickedCompany: function () {
      this.$router
        .push({
          path: "/mypage/myJob/myCompany",
        })
        .catch(() => {});
      this.isRecurit = false;
      this.isCompany = true;
    },
  },
};
</script>

<style scoped>
#my_learning {
  min-height: 100vh;
}
.nav_menu {
  height: 40px;
  border-bottom: 1px solid lightgray;
  margin-bottom: 40px;
}

.link {
  font-weight: bold;
  font-size: large;
  margin: 0 15px;
  line-height: 210%;
  cursor: pointer;
  width: 150px;
}

.link:hover {
  background-color: rgba(204, 204, 204, 0.5);
}
.clicked {
  border-bottom: 4px solid black;
}
</style>
