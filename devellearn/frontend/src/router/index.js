import Vue from "vue";
import VueRouter from "vue-router";

// 메인
import Main from "@/components/mainPage/Main.vue";
import MasterReq from "@/components/mainPage/MasterReq.vue";

// 회원
import Login from "@/components/login/Login.vue";
import Join from "@/components/login/Join.vue";
import FindPassword from "@/components/login/FindPassword.vue";
import FindId from "@/components/login/FindId.vue";
import FindComId from "@/components/login/FindComId.vue";

import FindComPassword from "@/components/login/FindComPassword.vue";
import CompanyLogin from "@/components/login/CompanyLogin";
import CompanyJoin from "@/components/login/CompanyJoin";

// 강의
import Lecture from "@/components/lecture/lectureList/Lecture.vue";
import LectureRegist from "@/components/lecture/lectureRegist/LectureRegist.vue";
import LectureDetail from "@/components/lecture/lectureDetail/LectureDetail.vue";
import LectureQnaList from "@/components/lecture/lectureQna/LectureQnaList.vue";
import LectureIntro from "@/components/lecture/lectureDetail/LectureIntro.vue";
import LectureQnaRegist from "@/components/lecture/lectureQna/LectureQnaRegist.vue";
import LectureQnaDetail from "@/components/lecture/lectureQna/LectureQnaDetail.vue";
import LectureCartList from "@/components/lecture/lectureCart/LectureCartList.vue";
import LectureLearning from "@/components/lecture/lectureLearning/LectureLearning.vue";
import ToggleCurr from "@/components/lecture/lectureLearning/ToggleCurr.vue";
import ToggleQna from "@/components/lecture/lectureLearning/ToggleQna.vue";
import ToggleNote from "@/components/lecture/lectureLearning/ToggleNote.vue";
import ToggleQnaWrite from "@/components/lecture/lectureLearning/ToggleQnaWrite.vue";
import ToggleQnaDetail from "@/components/lecture/lectureLearning/ToggleQnaDetail.vue";

// 커뮤
import Community from "@/components/community/Community.vue";
import Mentor from "@/components/teamStudy/Mentor.vue";
import QnA from "@/components/community/QnA.vue";
import Study from "@/components/teamStudy/Study.vue";
import Project from "@/components/teamStudy/Project.vue";
import Board from "@/components/community/Board.vue";
import Board2 from "@/components/community/Board2.vue";
import Board3 from "@/components/community/Board3.vue";
import Board4 from "@/components/community/Board4.vue";
import Board5 from "@/components/community/Board5.vue";
import About from "@/components/community/About.vue";

// 블로그
import Blog from "@/components/blog/blogMain/Blog.vue";
import MyBlogHome from "@/components/blog/blogHome/MyBlogHome.vue";
import MyBlogContent from "@/components/blog/blogHome/MyBlogContent.vue";
import BlogBookmark from "@/components/blog/blogHome/bookmark/BlogBookmark.vue";
import BlogFollow from "@/components/blog/blogHome/follow/BlogFollow.vue";
import BlogLike from "@/components/blog/blogHome/like/BlogLike.vue";
import BlogProject from "@/components/blog/blogHome/project/BlogProject.vue";
import PostWrite from "@/components/blog/blogPost/PostWrite.vue";
import BookmarkFolder from "@/components/blog/blogHome/bookmark/BookmarkFolder.vue";
import BookmarkList from "@/components/blog/blogHome/bookmark/BookmarkList.vue";
import PostDetail from "@/components/blog/blogPost/PostDetail.vue";
import BookFolderContent from "@/components/blog/blogHome/bookmark/BookFolderContent.vue";
import PostHome from "@/components/blog/blogPost/PostHome.vue";
import PostEdit from "@/components/blog/blogPost/PostEdit.vue";
import ProjectHome from "@/components/blog/blogPost/ProjectHome.vue";
import ProjectWrite from "@/components/blog/blogPost/ProjectWrite.vue";
import ProjectEdit from "@/components/blog/blogPost/ProjectEdit.vue";

// 마이페이지
import MyPage from "@/components/myPage/MyPage.vue";
import DashBoard from "@/components/myPage/DashBoard.vue";
import MyResume from "@/components/myPage/MyResume.vue";
import MessageBox from "@/components/myPage/MessageBox";
import RecvMsgDetails from "@/components/myPage/RecvMsgDetails";
import SendMsgDetails from "@/components/myPage/SendMsgDetails";
import UpdateInfo from "@/components/myPage/UpdateInfo";
import ResumeHome from "@/components/myPage/ResumeHome";
import Withdrawal from "@/components/myPage/Withdrawal";

//마이페이지-팀스터디,커뮤니티
import MyProject from "@/components/myPage/mycommunity/MyProject";
import MyMentor from "@/components/myPage/mycommunity/MyMentor";
import MyStudy from "@/components/myPage/mycommunity/MyStudy";
import MyBoard from "@/components/myPage/mycommunity/MyBoard";
import MyRep from "@/components/myPage/mycommunity/MyRep";
import MyQA from "@/components/myPage/mycommunity/MyQA";

// 마이페이지 - 강의
import MyLearning from "@/components/myPage/lecture/myLearning/MyLearning.vue";
import Learning from "@/components/myPage/lecture/myLearning/Learning.vue";
import Complete from "@/components/myPage/lecture/myLearning/Complete.vue";
import Like from "@/components/myPage/lecture/myLearning/Like.vue";
import MyQna from "@/components/myPage/lecture/myQna/MyQna.vue";
import RecievedQna from "@/components/myPage/lecture/recievedQna/RecievedQna.vue";
import MngLecture from "@/components/myPage/lecture/mngLecture/MngLecture.vue";
import MngLectureList from "@/components/myPage/lecture/mngLecture/MngLectureList.vue";
import LectureUpdate from "@/components/myPage/lecture/mngLecture/LectureUpdate.vue";

// 마이페이지 - 채용
import MyJob from "@/components/myPage/MyJob.vue";
import MyApply from "@/components/myPage/MyApply.vue";
import MyRecruit from "@/components/myPage/MyRecruit.vue";
import MyCompany from "@/components/myPage/MyCompany.vue";

// 기업회원 마이페이지
import CompanyPage from "@/components/myPage/company/CompanyPage.vue";
import CompanyInfo from "@/components/myPage/company/CompanyInfo.vue";
import ApplyMember from "@/components/myPage/company/ApplyMember.vue";
import UpdateComInfo from "@/components/myPage/company/UpdateComInfo.vue";

// 관리자페이지
import AdminPage from "@/components/myPage/admin/AdminPage.vue";
import LectureMng from "@/components/myPage/admin/LectureMng.vue";
import PayMng from "@/components/myPage/admin/PayMng.vue";
import ReportMng from "@/components/myPage/admin/ReportMng.vue";
import LectureRegistList from "@/components/myPage/admin/LectureRegistList.vue";
import MasterList from "@/components/myPage/admin/MasterList.vue";
import LectureInfo from "@/components/myPage/admin/LectureInfo.vue";
import CareerMng from "@/components/myPage/admin/CareerMng.vue";
import CareerList from "@/components/myPage/admin/CareerList.vue";
import CertiList from "@/components/myPage/admin/CertiList.vue";
import LectureView from "@/components/myPage/admin/LectureView.vue";

// 채용
import JobMain from "@/components/job/JobMain.vue";
import JobDetail from "@/components/job/JobDetail.vue";
import RecruitDetail from "@/components/job/RecruitDetail.vue";

// 기업
import Company from "@/components/company/Company.vue";
import CompanyDetail from "@/components/company/CompanyDetail.vue";
import CompanyReview from "@/components/company/CompanyReview.vue";
import DetailInfo from "@/components/company/DetailInfo.vue";
import Interview from "@/components/company/Interview.vue";

Vue.use(VueRouter);

const requireAuth = () => (to, from, next) => {
  if ($store.getters.userStore.getId !== "") {
    return next();
  }
  next("/login");
};

const routes = [
  {
    path: "/",
    name: "main",
    component: Main,
  },
  {
    path: "/masterReq",
    name: "masterReq",
    component: MasterReq,
  },

  // 로그인 회원가입 --------------------------------
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  {
    path: "/join",
    name: "join",
    component: Join,
  },
  {
    path: "/findPassword",
    name: "findPassword",
    component: FindPassword,
  },
  {
    path: "/findComPassword",
    name: "findComPassword",
    component: FindComPassword,
  },
  {
    path: "/companyLogin",
    name: "companyLogin",
    component: CompanyLogin,
  },
  {
    path: "/companyJoin",
    name: "companyJoin",
    component: CompanyJoin,
  },
  {
    path: "/findId",
    name: "findId",
    component: FindId,
  },
  {
    path: "/findComId",
    name: "findComId",
    component: FindComId,
  },

  // 채용 -------------------------------
  {
    path: "/jobMain",
    name: "jobMain",
    component: JobMain,
  },
  {
    path: "/jobDetail",
    name: "jobDetail",
    component: JobDetail,
    children: [
      {
        path: "recruitDetail",
        name: "recruitDetail",
        component: RecruitDetail,
        props: true,
      },
      {
        path: "companyReview",
        name: "companyReview",
        component: CompanyReview,
        props: true,
      },
    ],
  },
  // 기업-------------------------------
  {
    path: "/company",
    name: "company",
    component: Company,
  },
  {
    path: "/companyDetail",
    name: "companyDetail",
    component: CompanyDetail,
    children: [
      {
        path: "detailInfo",
        name: "detailInfo",
        component: DetailInfo,
      },
      {
        path: "companyReview",
        name: "companyReview",
        component: CompanyReview,
      },
      {
        path: "interview",
        name: "interview",
        component: Interview,
      },
    ],
  },

  // 강의 -----------------------------------
  {
    path: "/lecture",
    name: "lecture",
    component: Lecture,
  },
  {
    path: "/lectureRegist",
    name: "lectureRegist",
    component: LectureRegist,
  },
  {
    path: "/lectureDetail",
    name: "lectureDetail",
    component: LectureDetail,
    children: [
      {
        path: "intro",
        name: "lectureintro",
        component: LectureIntro,
      },
      {
        path: "qna",
        name: "lectureQnaList",
        component: LectureQnaList,
      },
    ],
  },
  {
    path: "/lectureQnaRegist",
    name: "lectureQnaRegist",
    component: LectureQnaRegist,
  },
  {
    path: "/lectureQnaDetail",
    name: "lectureQnaDetail",
    component: LectureQnaDetail,
  },
  {
    path: "/lectureCartList",
    name: "lectureCartList",
    component: LectureCartList,
  },
  {
    path: "/lectureLearning",
    name: "lectureLearning",
    component: LectureLearning,
    children: [
      {
        path: "toggleCurr",
        name: "toggleCurr",
        component: ToggleCurr,
      },
      {
        path: "toggleQna",
        name: "toggleQna",
        component: ToggleQna,
      },
      {
        path: "toggleQnaWrite",
        name: "toggleQnaWrite",
        component: ToggleQnaWrite,
      },
      {
        path: "toggleQnaDetail",
        name: "toggleQnaDetail",
        component: ToggleQnaDetail,
      },
      {
        path: "toggleNote",
        name: "toggleNote",
        component: ToggleNote,
      },
    ],
  },
  // 커뮤니티 -----------------------------------
  {
    path: "/community",
    name: "community",
    component: Community,
    children: [
      {
        path: "",
        component: QnA,
      },
      {
        path: "QnA",
        name: "QnA",
        component: QnA,
      },
      {
        path: "study",
        name: "study",
        component: Study,
      },
      {
        path: "mentor",
        name: "mentor",
        component: Mentor,
      },
      {
        path: "project",
        name: "project",
        component: Project,
      },
      {
        path: "board",
        name: "board",
        component: Board,
      },
      {
        path: "board2",
        name: "board2",
        component: Board2,
      },
      {
        path: "board3",
        name: "board3",
        component: Board3,
      },
      {
        path: "board4",
        name: "board4",
        component: Board4,
      },
      {
        path: "board5",
        name: "board5",
        component: Board5,
      },
      {
        path: "about",
        name: "about",
        component: About,
      },
    ],
  },
  // 블로그 -----------------------------------
  {
    path: "/blog",
    name: "blog",
    component: Blog,
  },
  {
    path: "/myBlogHome",
    name: "myBlogHome",
    component: MyBlogHome,
    children: [
      {
        path: "",
        component: MyBlogContent,
      },
      {
        path: "blogBookmark",
        name: "blogBookmark",
        component: BlogBookmark,
        children: [
          {
            path: "bookmarkList",
            name: "bookmarkList",
            component: BookmarkList,
          },
          {
            path: "bookmarkFolder",
            name: "bookmarkFolder",
            component: BookmarkFolder,
          },
          {
            path: "bookFolderContent",
            name: "bookFolderContent",
            component: BookFolderContent,
          },
        ],
      },
      {
        path: "follow",
        name: "follow",
        component: BlogFollow,
      },
      {
        path: "like",
        name: "like",
        component: BlogLike,
      },
      {
        path: "project",
        name: "project",
        component: BlogProject,
      },
    ],
  },
  {
    path: "/postHome",
    name: "postHome",
    component: PostHome,
  },
  {
    path: "/postWrite",
    name: "postWrite",
    component: PostWrite,
  },
  {
    path: "/postEdit",
    name: "postEdit",
    component: PostEdit,
  },
  {
    path: "/projectEdit",
    name: "projectEdit",
    component: ProjectEdit,
  },
  {
    path: "/postDetail",
    name: "postDetail",
    component: PostDetail,
  },
  {
    path: "/projectHome",
    name: "projectHome",
    component: ProjectHome,
  },
  {
    path: "/projectWrite",
    name: "projectWrite",
    component: ProjectWrite,
  },

  // 마이페이지 ----------------------------------
  {
    path: "/myPage",
    name: "myPage",
    component: MyPage,
    children: [
      {
        path: "dashBoard",
        name: "dashBoard",
        component: DashBoard,
      },
      {
        path: "myResume",
        name: "myResume",
        component: MyResume,
      },
      {
        path: "messageBox",
        name: "messageBox",
        component: MessageBox,
      },
      {
        path: "recvMsgDetails",
        name: "recvMsgDetails",
        component: RecvMsgDetails,
      },
      {
        path: "sendMsgDetails",
        name: "sendMsgDetails",
        component: SendMsgDetails,
      },
      {
        path: "updateInfo",
        name: "updateInfo",
        component: UpdateInfo,
      },
      {
        path: "resumeHome",
        name: "resumeHome",
        component: ResumeHome,
      },
      {
        path: "withdrawal",
        name: "withdrawal",
        component: Withdrawal,
      },
      {
        path: "myLearning",
        name: "myLearning",
        component: MyLearning,
        children: [
          {
            path: "",
            name: "learning",
            component: Learning,
          },
          {
            path: "learning",
            name: "learning",
            component: Learning,
          },
          {
            path: "complete",
            name: "complete",
            component: Complete,
          },
          {
            path: "like",
            name: "like",
            component: Like,
          },
        ],
      },
      {
        path: "myQna",
        name: "myQna",
        component: MyQna,
      },
      {
        path: "recievedQna",
        name: "recievedQna",
        component: RecievedQna,
      },
      {
        path: "mngLecture",
        name: "mngLecture",
        component: MngLecture,
        children: [
          {
            path: "",
            name: "mngLectureList",
            component: MngLectureList,
          },
          {
            path: "mngLectureList",
            name: "mngLectureList",
            component: MngLectureList,
          },
          {
            path: "lectureUpdate",
            name: "lectureUpdate",
            component: LectureUpdate,
          },
        ],
      },
      {
        path: "myJob",
        name: "myJob",
        component: MyJob,
        children: [
          {
            path: "myRecruit",
            name: "myRecruit",
            component: MyRecruit,
          },
          {
            path: "myCompany",
            name: "myCompany",
            component: MyCompany,
          },
        ],
      },
      {
        path: "myApply",
        name: "myApply",
        component: MyApply,
      },
      {
        path: "myproject",
        name: "myproject",
        component: MyProject,
      },
      {
        path: "mymentor",
        name: "mymentor",
        component: MyMentor,
      },
      {
        path: "mystudy",
        name: "mystudy",
        component: MyStudy,
      },
      {
        path: "myboard",
        name: "myboard",
        component: MyBoard,
      },
      {
        path: "myrep",
        name: "myrep",
        component: MyRep,
      },
      {
        path: "myQA",
        name: "myQA",
        component: MyQA,
      },
    ],
  },
  {
    path: "/lectureView",
    name: "lectureView",
    component: LectureView,
  },
  // 기업회원 페이지----------------------------------------
  {
    path: "/companyPage",
    name: "companyPage",
    component: CompanyPage,
    children: [
      {
        path: "companyInfo",
        name: "companyInfo",
        component: CompanyInfo,
      },
      {
        path: "applyMember",
        name: "applyMember",
        component: ApplyMember,
      },
      {
        path: "updateComInfo",
        name: "updateComInfo",
        component: UpdateComInfo,
      },
    ],
  },
  // 관리자 페이지----------------------------------------------
  {
    path: "/adminPage",
    name: "adminPage",
    component: AdminPage,
    children: [
      {
        path: "lectureMng",
        name: "lectureMng",
        component: LectureMng,
        children: [
          {
            path: "regist",
            name: "regist",
            component: LectureRegistList,
          },
          {
            path: "masterList",
            name: "masterList",
            component: MasterList,
          },
          {
            path: "lectureInfo",
            name: "lectureInfo",
            component: LectureInfo,
          },
        ],
      },
      {
        path: "payMng",
        name: "payMng",
        component: PayMng,
      },
      {
        path: "reportMng",
        name: "reportMng",
        component: ReportMng,
      },
      {
        path: "careerMng",
        name: "careerMng",
        component: CareerMng,
        children: [
          {
            path: "careerList",
            name: "careerList",
            component: CareerList,
          },
          {
            path: "certiList",
            name: "certiList",
            component: CertiList,
          },
        ],
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (to.hash) {
      // if has a hash...
      console.log(to.hash);
      return {
        selector: to.hash,
        offset: {y: 200},
      }; // scroll to the element
    }
    return {x: 0, y: 0};
  },
});

export default router;
