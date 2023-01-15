<template>
  <div id="my_qna">
    <!-- 헤더 -->
    <div class="nav_menu">
      <b-nav>
        <span class="link" @click="clickedAll" :class="{clicked: isAll}"
          >전체</span
        >
        <span
          class="link"
          @click="clickedResolved"
          :class="{clicked: isResolved}"
          >해결</span
        >
        <span
          class="link"
          @click="clickedUnresolved"
          :class="{clicked: isUnresolved}"
          >미해결</span
        >
      </b-nav>
    </div>
    <div id="ld_qna">
      <!-- 강의질문 리스트 -->
      <div class="qna_list">
        <template v-for="qna in qnaList">
          <template>
            <div
              class="qna_elem"
              @click="
                $router.push({
                  path: '/lectureQnaDetail',
                  query: {qnaNo: qna.qnaNo},
                })
              "
              :key="qna.qnaNo"
            >
              <div class="qna_elem_info">
                <h4>{{ qna.title }}</h4>
                <div class="less_content">{{ qna.noneHtmlContent }}</div>
                <p>
                  {{ qna.memberId }}&nbsp&nbsp·&nbsp&nbsp
                  {{ qna.regDate | yyyyMMdd }}&nbsp&nbsp·&nbsp&nbsp
                  {{ qna.lectureTitle }}&nbsp&nbsp·&nbsp&nbsp
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
        </template>
      </div>
      <!-- 강의질문 리스트 끝 -->
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      // 강의질문 전체조회 데이터 담을 배열
      qnaList: [],
      no: "",
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
    // 회원아이디로 강의질문 리스트 출력 axios
    getQnaList: function (status) {
      this.$axios
        .post("/api/qnaListByLecturer", {
          memberId: this.$store.state.userStore.id,
          status: status,
        })
        .then((resp) => {
          console.log(resp);
          this.qnaList = resp.data;
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
  },
  filters: {
    // filter로 쓸 filter ID 지정
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";

      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);

      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();

      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }

      if (day < 10) {
        day = "0" + day;
      }

      // 최종 포맷 (ex - '2021-10-08')
      return year + " / " + month + " / " + day;
    },
  },
};
</script>

<style scoped>
#my_qna {
  min-height: 100vh;
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
}

.link:hover {
  background-color: rgba(204, 204, 204, 0.5);
}

.clicked {
  border-bottom: 4px solid black;
}
/* 네비메뉴 끝*/

/* 질문요소 */
.less_content {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 20px;
}

.qna_elem {
  border-bottom: 1px solid lightgray;
  padding: 10px;
  margin-bottom: 30px;
  display: flex;
}

.qna_elem_info {
  width: 80%;
  text-align: left;
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
