<template>
  <div id="ll_wrapper">
    <div class="ll_video">
      <video
        id="video_player"
        controls
        autoplay
        width="100%"
        height="100%"
        ref="video"
        @timeupdate="onTimeUpdate"
        @ended="onEnded"
        @loadedmetadata="setCurrentTimeToFollowUp"
      ></video>
    </div>
    <!-- 사이드바 팝업 -->
    <div class="ll_sidebar" v-show="toggle">
      <!-- 토글 라우터뷰 -->
      <router-view
        :myLecture="myLecture"
        :myCurriculum="myCurriculum"
        :myCurrList="myCurrList"
        :videoCurrentTime="videoCurrentTime"
        @moveNoteTime="moveNoteTime"
      ></router-view>
    </div>
    <div class="ll_sidebar_menu">
      <img src="@/assets/list.png" class="icon" alt="list" @click="showCurr" />
      <img
        src="@/assets/conversation.png"
        class="icon"
        alt="conversation"
        @click="showQna"
      />
      <img src="@/assets/edit.png" class="icon" alt="edit" @click="showNote" />
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      toggle: false,
      myLecture: {},
      myCurriculum: {},
      myCurrList: [],
      videoCurrentTime: "",
      videoPath: "",
    };
  },
  created: function () {
    // 강의 단건조회 -> 자식 컴포넌트로 전달 -> 해당 강의 정보 전달
    this.getMyLectureInfo();
    // 나의 커리큘럼 단건조회 -> 자식 컴포넌트로 전달 -> 해당 커리큘럼 정보 전달 및 강의질문, 강의노트 조회
    this.getMyCurriculum();
    // 나의 커리큘럼 전체조회 -> 자식 컴포넌트로 전달 -> 해당 강의 커리큘럼 리스트 전달
    this.getMyCurrList();
  },
  methods: {
    // 커리큘럼 사이드바 토글 팝업
    showCurr: function () {
      this.clickedToggle("toggleCurr");
    },
    // 강의질문 사이드바 토글 팝업
    showQna: function () {
      this.clickedToggle("toggleQna");
    },
    // 강의노트 사이드바 토글 팝업
    showNote: function () {
      this.clickedToggle("toggleNote");
    },
    // 사이드바 메뉴 클릭 -> 토글 팝업 -> 페이지 띄어져 있을시 교체, 아닐 시 팝업
    clickedToggle: function (url) {
      // 토글 열린 경우
      if (this.toggle == true) {
        // 해당 url 토글 열린 경우 - 이전 경로 + 토글 닫기
        if (this.$route.path == "/lectureLearning/" + url) {
          this.toggle = !this.toggle;
          this.$router
            .replace({
              path: "/lectureLearning",
              query: { no: this.$route.query.no, curr: this.$route.query.curr },
            })
            .catch(() => {});
        }
        // 해당 url 아닌 토글 열린 경우 - 해당 url 토글로 컴포넌트 교체
        else {
          this.$router
            .replace({
              path: "/lectureLearning/" + url,
              query: { no: this.$route.query.no, curr: this.$route.query.curr },
            })
            .catch(() => {});
        }
      }
      // 토글 닫힌 경우 - 토글 열기 + 해당 url 컴포넌트 교체
      else {
        this.toggle = !this.toggle;
        this.$router
          .replace({
            path: "/lectureLearning/" + url,
            query: { no: this.$route.query.no, curr: this.$route.query.curr },
          })
          .catch(() => {});
      }
    },
    // 나의 강의 단건조회 axios 통신
    getMyLectureInfo: function () {
      this.$axios
        .post("/api/getMyLecture", {
          lectureNo: this.$route.query.no,
          memberId: this.$store.state.userStore.user.memberId,
        })
        .then((resp) => {
          // console.log(resp);
          this.myLecture = resp.data;
          console.log("learning myLecture info", this.myLecture);
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    // 나의 커리큘럼 단건조회 axios 통신
    async getMyCurriculum() {
      await this.$axios
        .post("/api/getMyCurriculum", {
          currNo: this.$route.query.curr,
          memberId: this.$store.state.userStore.user.memberId,
        })
        .then(async (resp) => {
          // console.log(resp);
          this.myCurriculum = resp.data;
          console.log("learning curr info", this.myCurriculum);
          // mycurriculum 정보 획득 후 video source load
          this.loadVideo();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 비디오 src 연결 시 created의 동작보다 video 태그의 source 로드가 더 빨라서 영상 출력이 안됨
    // --> 해결 : created의 동작 후 video 태그에 source 태그를 작성하여 노드 추가하는 형식으로 구성
    loadVideo() {
      let videoTag = document.querySelector("#video_player");
      let source = document.createElement("source");
      source.setAttribute(
        "src",
        "/api/video/lecture_" +
          this.myCurriculum.lectureNo +
          "/" +
          this.myCurriculum.videoSrc
      );
      source.setAttribute("type", "video/mp4");
      videoTag.appendChild(source);
      console.log("videoTag", videoTag);
    },
    // 해당 강의번호로 나의 커리큘럼 리스트 조회 axios
    getMyCurrList: function () {
      this.$axios
        .post("/api/getMyCurriculumList", {
          lectureNo: this.$route.query.no,
          memberId: this.$store.state.userStore.user.memberId,
        })
        .then((resp) => {
          if (resp.data != null) {
            console.log(
              "학습하기 페이지 - 해당 강의의 나의 커리큘럼리스트 전체조회 ",
              resp.data
            );
            this.myCurrList = resp.data;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 현재 비디오 재생시간과 수강중인 커리큘럼을 나의 강의 DB에 저장 axios
    updateMyLecture() {
      this.$axios
        .put("/api/updateMyLecture", {
          latestWatchedCurr: this.$route.query.curr,
          latestWatchedTime: Math.floor(this.videoCurrentTime),
          memberId: this.$store.getters["userStore/getId"],
          lectureNo: this.$route.query.no,
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 현재 비디오 재생시간과 수강중인 커리큘럼을 나의 커리큘럼 DB에 저장 axios
    async updateMyCurriculum() {
      let resp = await this.$axios.put("/api/updateMyCurriculum", {
        memberId: this.$store.getters["userStore/getId"],
        currNo: this.$route.query.curr,
        latestWatchedTime: Math.floor(this.videoCurrentTime),
      });
    },
    // 비디오 재생시간이 변할 때마다 발생 - 현재 비디오 재생시간을 변수에 저장
    onTimeUpdate() {
      // dom 을 쓰지 않고 ref 로 데이터 바인딩
      this.videoCurrentTime = this.$refs.video.currentTime;
    },
    // 비디오 재생 완료 시 발생
    // video 태그의 @ended 이벤트 이용
    async onEnded() {
      let vm = this;
      let isCompletedAll = true;
      // 나의 커리큘럼의 수강여부를 '완료'로 저장
      let resp = await this.$axios.put("/api/updateMyCurriculum", {
        memberId: this.$store.state.userStore.user.memberId,
        currNo: this.$route.query.curr,
        latestWatchedTime: Math.floor(this.videoCurrentTime),
        watchedAll: "완료",
      });
      if (resp.data != null) {
        for (let i = 0; i < this.myCurrList.length; i++) {
          if (this.myCurrList[i].currNo == this.$route.query.curr) {
            this.myCurrList[i].watchedAll = "완료";
          }
          // 모든 커리큘럼의 watchedAll이 '완료'일 경우
          //-> 나의 강의 complete_status를 '완료'로 업데이트하기 위한 판단
          if (this.myCurrList[i].watchedAll != "완료") {
            isCompletedAll = false;
          }
        }
      }
      this.updateMyLecture();
      // 모든 커리큘럼 수강완료가 아닐 때
      if (!isCompletedAll) {
        ////////////////////////////////////////////////// 알림창
        this.$swal
          .fire({
            title: "다음 강의를 계속 수강하시겠습니까?",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            cancelButtonText: "취소",
            confirmButtonText: "확인",
          })
          .then((result) => {
            if (result.isConfirmed) {
              vm.$router.replace({
                path: "/lectureLearning",
                query: {
                  no: this.$route.query.no,
                  curr: Number(this.$route.query.curr) + 1,
                  startTime: 0,
                },
              });
              vm.$router.go(0);
            }
          });
      } else {
        // 모든 커리큘럼 수강 완료시
        Swal.fire({
          position: "center",
          icon: "success",
          title: "모든 강의를 수강하셨습니다. 수고하셨습니다.",
          showConfirmButton: true,
          // timer: 1000
        });
        // 나의 강의 complete_status를 '완료'로 업데이트
        this.$axios
          .put("/api/updateToComplete", {
            memberId: this.$store.getters["userStore/getId"],
            lectureNo: this.$route.query.no,
          })
          .then((resp) => {
            if (resp != 0) {
              console.log(resp.data);
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },

    // 이어서 보기
    // video 태그의 @loadedmetadata 이벤트 이용
    setCurrentTimeToFollowUp() {
      let vm = this;
      // 아직 미수강일 경우 처음부터 재생
      if (this.$route.query.startTime == 0) {
        return;
      }
      // 수강 완료했는데 다시 들을 경우 처음부터 재생
      else if (
        this.$route.query.startTime == Math.floor(this.$refs.video.duration)
      ) {
        return;
      }
      // 수강중일 경우 마지막 시청시간부터 재생
      else {
        ////////////////////////////////////////////////// 알림창
        this.$swal
          .fire({
            title: "이전 수강내역이 있습니다.",
            text: "이어서 수강하시겠습니까?",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            cancelButtonText: "취소",
            confirmButtonText: "확인",
          })
          .then((result) => {
            if (result.isConfirmed) {
              // 비디오의 시작 재생시간 설정
              vm.$refs.video.currentTime = this.$route.query.startTime;
            }
          });
      }
    },
    // 강의노트 작성 시간으로 이동
    moveNoteTime(noteTime) {
      // 자식컴포넌트로부터 받은 이벤트 발생 --> 재생시간 설정
      this.$refs.video.currentTime = noteTime;
    },
  },
  // 해당 페이지(학습하기) 벗어날 때 발생 -> 현재 비디오 재생시간과 수강중인 커리큘럼을 저장
  async beforeRouteLeave(to, from, next) {
    // 현재 비디오 재생시간과 수강중인 커리큘럼 업데이트 (나의 커리큘럼 DB)
    await this.updateMyCurriculum();
    this.updateMyLecture();
    next();
  },
};
</script>

<style scoped>
#ll_wrapper {
  display: flex;
  justify-content: space-between;
}

#ll_wrapper .ll_video {
  width: 100%;
  background-color: gray;
  color: white;
  height: calc(100vh - 10px);
}

.ll_sidebar {
  width: 600px;
  overflow-x: hidden;
  height: calc(100vh - 10px);
}

#ll_wrapper .ll_sidebar_menu {
  width: 70px;
  border-left: 1px solid rgba(0, 0, 0, 0.4);
}

#ll_wrapper .ll_sidebar_menu img {
  width: 70px;
  padding: 18px;
}

#ll_wrapper .ll_sidebar_menu img:hover {
  background-color: cornsilk;
}
</style>
