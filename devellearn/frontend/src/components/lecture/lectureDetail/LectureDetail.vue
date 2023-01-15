<template>
  <div id="ld" style="min-height: 100vh">
    <!-- 배너 : 상단 썸네일 및 강의소개 -->
    <Banner :lecture="lecture"></Banner>
    <!-- 네비바 : 강의소개/커리큘럼/수강평/Q&A -->
    <Navbar></Navbar>
    <!-- 메인 -->
    <div id="ld_intro">
      <router-view
        class="content"
        :lecture="lecture"
        :myLecture="myLecture"
        :myCurrList="myCurrList"
      ></router-view>
      <!-- 수강신청 픽스창 -->
      <OrderApp
        :lecture="lecture"
        :myLecture="myLecture"
        :myCurrList="myCurrList"
      ></OrderApp>
    </div>
  </div>
</template>

<script>
import Banner from "./Banner.vue";
import Navbar from "./Navbar.vue";
import OrderApp from "./OrderApp.vue";

export default {
  components: {
    Banner,
    Navbar,
    OrderApp,
  },
  data: function () {
    return {
      no: "",
      lecture: {},
      myLecture: {},
      myCurrList: [],
    };
  },
  created: function () {
    // 강의 단건조회
    this.getLectureInfo();
    // 나의 강의 단건조회 -> 내가 구매한 강의인지 확인하기 위함
    this.getMyLectureInfo();
    // 나의 커리큘럼 전체조회
    this.getMyCurrList();
  },
  methods: {
    // 강의 단건조회 axios
    getLectureInfo: function () {
      // query 프로그래밍 방식으로 보낸 데이터 받기
      this.no = this.$route.query.no;
      // 강의 단건조회 axios 통신
      this.$axios
        .get("/api/lecture/" + this.no)
        .then((resp) => {
          if (resp.data != null) {
            this.lecture = resp.data;
          }
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    // 나의 강의 단건조회 axios 통신
    getMyLectureInfo: function () {
      this.$axios
        .post("/api/getMyLecture", {
          lectureNo: this.$route.query.no,
          memberId: this.$store.state.userStore.user.memberId,
        })
        .then((resp) => {
          if (resp.data != "") {
            this.myLecture = resp.data;
            console.log("learning myLecture info", this.myLecture);
          }
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    // 해당 강의번호로 나의 커리큘럼 리스트 조회 axios
    getMyCurrList: function () {
      this.$axios
        .post("/api/getMyCurriculumList", {
          lectureNo: this.$route.query.no,
          memberId: this.$store.state.userStore.user.memberId,
        })
        .then((resp) => {
          // if (resp.data != null) {
          console.log("강의 상세페이지 나의커리큘럼 리스트 출력 : ", resp.data);
          this.myCurrList = resp.data;
          // }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
* {
  font-family: GmarketSansMedium;
}
#ld_intro {
  width: 60%;
  margin: auto;
  text-align: left;
  display: flex;
}
.content {
  width: 70%;
  /* background-color: red; */
}
</style>
