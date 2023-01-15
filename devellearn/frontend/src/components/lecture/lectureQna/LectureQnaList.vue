<template lang="">
  <div id="ld_qna">
    <!-- 로그인이 필요합니다 모달창 -->
    <v-dialog v-model="dialog" width="450px">
      <NeedLogin @close="dialog = false"></NeedLogin>
    </v-dialog>

    <div class="write_btn">
      <b-button @click="moveQnaWriteForm">질문작성</b-button>
    </div>
    <!-- 검색 카테고리 -->
    <div class="qna_header">
      <!-- 전체, 해결, 미해결 -->
      <div class="nav_menu">
        <b-nav>
          <span class="link" @click="clickedAll" :class="{ clicked: isAll }"
            >전체</span
          >
          <span
            class="link"
            @click="clickedResolved"
            :class="{ clicked: isResolved }"
            >해결</span
          >
          <span
            class="link"
            @click="clickedUnresolved"
            :class="{ clicked: isUnresolved }"
            >미해결</span
          >
        </b-nav>
      </div>
      <!-- 검색바 -->
      <div class="qna_search_bar">
        <b-form-input
          class="mr-1"
          v-model="keyword"
          @keypress.enter="searchQna(1)"
        ></b-form-input>
        <v-btn color="primary" @click="searchQna(1)">Search</v-btn>
      </div>
    </div>

    <!-- 강의질문 리스트 -->
    <div class="qna_list">
      <div v-if="qnaList.length == 0" style="margin-bottom: 20px">
        작성된 질문이 없습니다.<br />질문을 작성하여 궁금증을 해결해보세요.
      </div>
      <template v-for="qna in qnaList">
        <div
          class="qna_elem"
          @click="
            $router.push({
              path: '/lectureQnaDetail',
              query: { no: $route.query.no, qnaNo: qna.qnaNo },
            })
          "
        >
          <div class="qna_elem_info">
            <h4>{{ qna.title }}</h4>
            <div class="less_content">{{ qna.noneHtmlContent }}</div>
            <p>
              {{ qna.memberId }}&nbsp·&nbsp
              {{ changeDateFormat(qna.regDate) }}&nbsp·&nbsp
              {{ qna.lectureTitle }}&nbsp·&nbsp
              {{ qna.currName }}
            </p>
          </div>
          <div class="qna_elem_reply_num">
            <div class="circle">
              <div>{{ qna.replyNum }}</div>
              <div>답변</div>
            </div>
          </div>
        </div>
      </template>
      <v-row align="center">
        <v-btn block color="success" v-if="hasNextPage" @click="moreList()"
          >더보기</v-btn
        >
      </v-row>
    </div>
    <!-- 강의질문 리스트 끝 -->
  </div>
</template>

<script>
import moment from "moment";
import NeedLogin from "../../common/NeedLogin.vue";

export default {
  components: {
    NeedLogin,
  },

  data: function () {
    return {
      // 강의질문 전체조회 데이터 담을 배열
      qnaList: [],
      no: "",
      isAll: true,
      isResolved: false,
      isUnresolved: false,
      pageNum: 1,
      hasNextPage: true,
      status: "all",
      keyword: "",
      dialog: false,
    };
  },
  created: function () {
    // 강의질문 리스트
    this.clickedAll();
  },
  methods: {
    checkLogin() {
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        return false;
      } else {
        return true;
      }
    },
    // qna 작성 페이지로 이동
    moveQnaWriteForm: function () {
      if (!this.checkLogin()) {
        this.dialog = true;
        return;
      }
      this.$router
        .push({
          path: "/lectureQnaRegist",
          query: { no: this.$route.query.no },
        })
        .catch(() => {});
    },
    // 강의번호로 강의질문 리스트 출력 axios
    getQnaList: function () {
      this.$axios
        .post("/api/qnaListByLectureNo", {
          lectureNo: this.$route.query.no,
          status: this.status,
          pageNum: this.pageNum,
        })
        .then((resp) => {
          console.log(resp);
          this.pageNum += 1;
          const data = resp.data.list;
          this.qnaList.push(...data);
          this.hasNextPage = resp.data.hasNextPage;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    clickedAll: function () {
      this.keyword = "";
      this.status = "all";
      this.qnaList = [];
      this.pageNum = 1;
      this.isAll = true;
      this.isResolved = false;
      this.isUnresolved = false;
      this.getQnaList();
    },
    clickedResolved: function () {
      this.keyword = "";
      this.status = "해결";
      this.qnaList = [];
      this.pageNum = 1;
      this.isAll = false;
      this.isResolved = true;
      this.isUnresolved = false;
      this.getQnaList();
    },
    clickedUnresolved: function () {
      this.keyword = "";
      this.status = "미해결";
      this.qnaList = [];
      this.pageNum = 1;
      this.isAll = false;
      this.isResolved = false;
      this.isUnresolved = true;
      this.getQnaList();
    },
    // 날짜 포맷 변경
    changeDateFormat: function (date) {
      return moment(date).format("YYYY.MM.DD. a HH:mm");
    },
    // 검색하기
    searchQna(pageNum) {
      this.pageNum = pageNum;
      this.$axios
        .post("/api/searchLectureQna", {
          lectureNo: this.$route.query.no,
          status: this.status,
          keyword: this.keyword,
          pageNum: this.pageNum,
        })
        .then((resp) => {
          console.log(resp);

          this.qnaList = resp.data.list;
          if (this.qnaList.length == resp.data.total) {
            this.hasNextPage = false;
          } else {
            this.hasNextPage = true;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 더보기클릭
    moreList() {
      if (this.keyword == "") {
        this.getQnaList();
      } else {
        this.searchQna(++this.pageNum);
      }
    },
  },
};
</script>

<style scoped>
/* 강의질문 리스트 내용 말줄임 */
.less_content {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 20px;
}

.write_btn {
  text-align: right;
  margin-bottom: 20px;
}

.qna_header {
  display: flex;
  justify-content: space-between;
}

/* .qna_header 의 첫 번째 div 의 버튼에 접근 */
.qna_header div:nth-child(1) button {
  margin-right: 40px;
  width: 80px;
}

/* search bar */
.qna_search_bar {
  display: flex;
}

.qna_search_bar button {
  height: 38px;
}

/* 네비메뉴 */
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
  width: 80px;
  text-align: center;
}

.link:hover {
  background-color: rgba(204, 204, 204, 0.5);
}

.clicked {
  border-bottom: 4px solid black;
}
/* 네비메뉴 끝*/

/* 질문요소 */
.qna_elem {
  border-bottom: 1px solid lightgray;
  padding: 10px;
  margin-bottom: 30px;
  display: flex;
}

.qna_elem_info {
  width: 80%;
}

.qna_elem_info p {
  color: gray;
}

.qna_elem_reply_num {
  width: 20%;
  text-align: center;
  /* background-color: aqua; */
  height: 80px;
  padding-top: 20px;
}

.qna_elem_reply_num .circle {
  /* background-color: antiquewhite; */
  margin: auto;
  width: 54px;
  height: 54px;
  border: 1px solid lightgray;
  border-radius: 50%;
}

.qna_elem:hover {
  background-color: rgba(204, 204, 204, 0.2);
  cursor: pointer;
}
</style>
