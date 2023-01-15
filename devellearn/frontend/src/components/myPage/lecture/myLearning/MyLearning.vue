<template>
  <div id="my_learning">
    <!-- 헤더 -->
    <div class="nav_menu">
      <b-nav>
        <span
          class="link"
          @click="clickedLearning"
          :class="{clicked: isLearning}"
          >수강중인 강의</span
        >
        <span
          class="link"
          @click="clickedComplete"
          :class="{clicked: isComplete}"
          >수강완료한 강의</span
        >
        <span class="link" @click="clickedLike" :class="{clicked: isLike}"
          >찜한 강의</span
        >
      </b-nav>
    </div>
    <router-view
      :learningLectureList="learningLectureList"
      :completeLectureList="completeLectureList"
      :likedLectureList="likedLectureList"
    ></router-view>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      isLearning: true,
      isComplete: false,
      isLike: false,
      learningLectureList: [],
      completeLectureList: [],
      likedLectureList: [],
    };
  },
  created() {
    // 수강중, 수강완료 강의리스트 조회
    this.getLearningOrCompleteMyLectureList();
    // 찜한 강의리스트 조회
    this.getLikedMyLectureList();
  },
  methods: {
    // 수강중인 강의 클릭
    clickedLearning: function () {
      this.$router
        .push({
          path: "/mypage/myLearning/learning",
        })
        .catch(() => {});
      this.isLearning = true;
      this.isComplete = false;
      this.isLike = false;
    },
    // 수강완료한 강의 클릭
    clickedComplete: function () {
      this.$router
        .push({
          path: "/mypage/myLearning/complete",
        })
        .catch(() => {});
      this.isLearning = false;
      this.isComplete = true;
      this.isLike = false;
    },
    // 찜한 강의 클릭
    clickedLike: function () {
      this.$router
        .push({
          path: "/mypage/myLearning/like",
        })
        .catch(() => {});
      this.isLearning = false;
      this.isComplete = false;
      this.isLike = true;
    },
    // 수강중, 수강완료 강의 리스트 조회 axios
    getLearningOrCompleteMyLectureList() {
      console.log("마이페이지 강의리스트 출력");
      this.$axios
        .post("/api/getMyLectureList", {
          memberId: this.$store.getters["userStore/getId"],
        })
        .then((resp) => {
          console.log(resp.data);
          if (resp.data != null) {
            for (let i = 0; i < resp.data.length; i++) {
              if (resp.data[i].completeStatus == "미완료") {
                this.learningLectureList.push(resp.data[i]);
              }
              if (resp.data[i].completeStatus == "완료") {
                this.completeLectureList.push(resp.data[i]);
              }
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getLikedMyLectureList() {
      this.$axios
        .post("/api/getMyLectureList", {
          memberId: this.$store.getters["userStore/getId"],
          likeStatus: "좋아요",
        })
        .then((resp) => {
          if (resp.data != null) {
            // console.log("찜한 강의리스트 조회", resp.data);
            this.likedLectureList = resp.data;
          }
        })
        .catch((error) => {
          console.log(error);
        });
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
