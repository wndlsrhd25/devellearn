<template>
  <div id="tq_wrapper">
    <div class="tq_header">
      <div>
        <div><h3>Q&A</h3></div>
        <div>
          <h5>{{ myLecture.title }} - {{ myCurriculum.currName }}</h5>
        </div>
      </div>
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
        <b-form-input class="mr-1"></b-form-input>
        <b-button type="submit">Search</b-button>
      </div>
    </div>
    <div v-if="qnaList.length == 0" style="margin-bottom: 20px">
      작성된 질문이 없습니다.<br />질문을 작성하여 궁금증을 해결해보세요.
    </div>
    <template v-for="qna in qnaList">
      <template>
        <div
          class="qna_elem"
          @click="
            $router.push({
              path: '/lectureLearning/toggleQnaDetail',
              query: {
                no: $route.query.no,
                curr: $route.query.curr,
                qnaNo: qna.qnaNo,
              },
            })
          "
          :key="qna.qnaNo"
        >
          <div class="qna_elem_info">
            <h5>{{ qna.title }}</h5>
            <div class="less_content">{{ qna.noneHtmlContent }}</div>
            <div class="bottom_info">
              <div>
                <p>
                  {{ qna.memberId }}&nbsp;·&nbsp;
                  {{ changeDateFormat(qna.regDate) }}
                </p>
              </div>
              <div class="elem_num">
                <div>답변</div>
                <div>{{ qna.replyNum }}</div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </template>
    <div>
      <b-button
        block
        variant="success"
        @click="
          $router
            .push({
              path: '/lectureLearning/toggleQnaWrite',
              query: { no: $route.query.no, curr: $route.query.curr },
            })
            .catch(() => {})
        "
        >글 작성하기</b-button
      >
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  props: ["myLecture", "myCurriculum"],
  data: function () {
    return {
      // 강의질문 전체조회 데이터 담을 배열
      qnaList: [],
      isAll: true,
      isResolved: false,
      isUnresolved: false,
    };
  },
  created: function () {
    // 강의질문 리스트
    this.clickedAll();
  },
  methods: {
    // 커리큘럼번호로 강의질문 리스트 출력 axios
    getQnaList: function (status) {
      this.$axios
        .post("/api/qnaListByCurrNo", {
          currNo: this.$route.query.curr,
          status: status,
        })
        .then((resp) => {
          console.log(resp);
          this.qnaList = resp.data;
          console.log("qnaList", this.qnaList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    clickedAll: function () {
      this.getQnaList("all");
      this.isAll = true;
      this.isResolved = false;
      this.isUnresolved = false;
    },
    clickedResolved: function () {
      this.getQnaList("해결");
      this.isAll = false;
      this.isResolved = true;
      this.isUnresolved = false;
    },
    clickedUnresolved: function () {
      this.getQnaList("미해결");
      this.isAll = false;
      this.isResolved = false;
      this.isUnresolved = true;
    },
    // 날짜 포맷 변경
    changeDateFormat: function (date) {
      return moment(date).format("YYYY.MM.DD. a HH:mm");
    },
  },
};
</script>

<style scoped>
#tq_wrapper {
  /* background-color: aqua; */
  text-align: left;
  padding: 20px;
  width: 420px;
}

#tq_wrapper .tq_header {
  margin-bottom: 20px;
  padding-bottom: 10px;
}

#tq_wrapper .tq_header > div:nth-child(1) > div {
  margin-bottom: 15px;
}

#tq_wrapper .tq_curr:hover {
  background-color: rgba(204, 204, 204, 0.2);
}

/* 네비메뉴 */
.nav_menu {
  margin-top: 50px;
  height: 40px;
}

.link {
  font-weight: bold;
  font-size: smaller;
  margin-right: 15px;
  line-height: 210%;
  cursor: pointer;
  width: 40px;
  text-align: center;
}

.link:hover {
  background-color: rgba(204, 204, 204, 0.5);
}

.clicked {
  border-bottom: 2px solid black;
}
/* 네비메뉴 끝*/

/* 검색바 */
.qna_search_bar {
  display: flex;
  justify-content: space-between;
}
/* 검색바 끝 */

/* 질문요소 */
.less_content {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 20px;
}

.qna_elem {
  border: 1px solid lightgray;
  border-radius: 5px;
  box-shadow: 0 4px 4px -4px black;
  padding: 10px;
  margin-bottom: 30px;
  font-size: smaller;
}

.qna_elem_info {
  text-align: left;
}

.qna_elem_info p {
  margin-bottom: 0px;
}

.qna_elem:hover {
  background-color: rgba(204, 204, 204, 0.2);
  cursor: pointer;
}

.qna_elem_info .bottom_info {
  display: flex;
  justify-content: space-between;
  border-top: 1px solid lightgray;
  padding-top: 5px;
}

.qna_elem_info .elem_num {
  display: flex;
}
/* 질문요소 끝 */
</style>
