<template>
  <div id="my_learning">
    <!-- 헤더 -->
    <div class="nav_menu">
      <b-nav>
        <span class="link" @click="clickedCareer" :class="{ clicked: isCareer }"
          >경력인증 신청</span
        >
        <span class="link" @click="clickedCerti" :class="{ clicked: isCerti }">
          자격인증 신청</span
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
      isCareer: true,
      isCerti: false,
    };
  },
  methods: {
    // 경력인증 신청
    clickedCareer: function () {
      this.$router
        .push({
          path: "/adminPage/careerMng/careerList",
        })
        .catch(() => {});
      this.isCareer = true;
      this.isCerti = false;
    },
    // 자격인증 신청
    clickedCerti: function () {
      this.$router
        .push({
          path: "/adminPage/careerMng/certiList",
        })
        .catch(() => {});
      this.isCareer = false;
      this.isCerti = true;
    },
  },
};
</script>

<style scoped>
#my_learning {
  min-height: 60vh;
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