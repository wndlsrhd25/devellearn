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
      ></video>
    </div>
  </div>
</template>
<script>
export default {
  created: function () {
    // 나의 커리큘럼 단건조회 -> 자식 컴포넌트로 전달 -> 해당 커리큘럼 정보 전달 및 강의질문, 강의노트 조회
    this.getMyCurriculum();
  },
  methods: {
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
</style>
