<template>
  <div id="my_learning">
    <!-- 헤더 -->
    <div class="nav_menu">
      <b-nav>
        <span
          class="link"
          @click="clickedLecture"
          :class="{ clicked: isLecture }"
          >강의등록 신청</span
        >
        <span class="link" @click="clickedMaster" :class="{ clicked: isMaster }"
          >고수등록 신청</span
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
      isLecture: true,
      isMaster: false,
    };
  },
  methods: {
    // 강의등록 신청
    clickedLecture: function () {
      this.$router
        .push({
          path: "/adminPage/lectureMng/regist",
        })
        .catch(() => {});
      this.isLecture = true;
      this.isMaster = false;
    },
    // 고수등록 신청
    clickedMaster: function () {
      this.$router
        .push({
          path: "/adminPage/lectureMng/masterList",
        })
        .catch(() => {});
      this.isLecture = false;
      this.isMaster = true;
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
