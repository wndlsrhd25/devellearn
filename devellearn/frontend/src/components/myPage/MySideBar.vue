<template>
  <div id="panel">
    <sidebar-menu :menu="menu" :hideToggle="true" :show-one-child="true" />
  </div>
</template>

<script>
// href 불러올때는 라우터 > index.js에서 셋팅해줘야함★
export default {
  data() {
    return {
      menu: [
        {
          header: true,
          title: "HOME",
          hiddenOnCollapse: true,
        },
        {
          title: "대시보드",
          href: { path: "/myPage/dashBoard" },
        },
        {
          title: "내블로그",
          href: {
            path: "/myBlogHome",
            query: { blogId: this.$store.state.userStore.id },
          },
        },

        {
          header: true,
          title: "이러닝",
          hiddenOnCollapse: true,
        },
        // 수강중인 강의, 수강 완료 강의, 관심 강의
        {
          title: "내 학습",
          href: { path: "/myPage/myLearning" },
        },
        // 전체, 해결, 미해결
        {
          title: "내 질문",
          href: { path: "/myPage/myQna" },
        },
        // 개발 고수일 경우
        // {
        //   title: "강의관리",
        //   href: { path: "/myPage/mngLecture" },
        // },
        // // 개발 고수일 경우
        // {
        //   title: "받은 질문",
        //   href: { path: "/myPage/recievedQna" },
        // },
        {
          header: true,
          title: "팀스터디",
          hiddenOnCollapse: true,
        },
        {
          title: "프로젝트",
          href: { path: "/myPage/MyProject" },
        },
        {
          title: "멘토링",
          href: { path: "/myPage/MyMentor" },
        },
        {
          title: "스터디",
          href: { path: "/myPage/MyStudy" },
        },
        {
          header: true,
          title: "채용",
          hiddenOnCollapse: true,
        },
        {
          title: "나의채용",
          href: { path: "/myPage/myJob/myRecruit" },
        },
        {
          title: "지원내역",
          href: { path: "/myPage/myApply" },
        },
        {
          title: "나의이력서",
          href: { path: "/myPage/resumeHome" },
        },
        {
          header: true,
          title: "커뮤니티",
          hiddenOnCollapse: true,
        },
        {
          title: "지식QnA",
          href: { path: "/myPage/MyQA" },
        },
        {
          title: "내가 쓴글",
          href: { path: "/myPage/MyBoard" },
        },
        {
          title: "쪽지함",
          href: { path: "/myPage/messageBox" },
        },
        {
          title: "회원탈퇴",
          href: { path: "/myPage/withdrawal" },
        },
      ],
    };
  },
  created() {
    if (this.$store.getters["userStore/getUserData"] != null) {
      if (this.$store.getters["userStore/getUserData"].grade == "개발고수") {
        this.menu.splice(
          6,
          0,
          {
            title: "강의관리",
            href: { path: "/myPage/mngLecture" },
          },
          // 개발 고수일 경우
          {
            title: "받은 질문",
            href: { path: "/myPage/recievedQna" },
          }
        );
      }
    }
  },
};
</script>

<style scoped>
#panel {
  background-color: antiquewhite;
}
.v-sidebar-menu {
  /* padding-top: 100px; */ /* 기존 삭제 */
  position: static !important; /* 추가- 사이드바가 영역 겹치는 것 방지 */
  border-right: 1px solid rgba(204, 204, 204, 0.5);
  border-bottom: 1px solid rgba(204, 204, 204, 0.5);
  padding-left: 30px;
  padding-right: 60px; /* 추가 */
  background-color: white; /* 추가 */
  z-index: 1;
}
.vsm--title {
  padding-left: 15px;
}

#subTitle {
  margin-top: 20px;
  text-align: left;
  margin-left: 50px;
  font-size: x-large;
  color: rgb(0, 0, 158);
}
</style>
