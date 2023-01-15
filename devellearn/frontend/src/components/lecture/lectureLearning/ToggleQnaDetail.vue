<template>
  <div id="tqd_wrapper">
    <div class="tqd_back_img">
      <img
        src="@/assets/back.png"
        class="icon"
        alt="back"
        @click="$router.go(-1)"
      />
    </div>
    <!-- 질문 상세 정보 -->
    <div class="tqd_info">
      <div class="tqd_header">
        <div><h3>Q&A</h3></div>
        <div>
          <h5>{{ myLecture.title }} - {{ myCurriculum.currName }}</h5>
        </div>
      </div>
      <div>
        <div class="qna_info">
          <div>
            <h4>{{ qnaInfo.title }}</h4>
            <div class="updata_delete">
              <span class="update_delete_btn">수정</span>
              <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
              <span @click="deleteQna()" class="update_delete_btn">삭제</span>
            </div>
          </div>
          <div>
            <p style="color: gray">
              {{ qnaInfo.memberId }}&nbsp;&nbsp;·&nbsp;&nbsp;
              {{ changeDateFormat(qnaInfo.regDate) }}
            </p>
            <!-- 자신이 등록한 강의질문인 경우 -->
          </div>
          <hr />
          <div v-html="qnaInfo.content"></div>
        </div>
      </div>
    </div>
    <!-- 질문 상세 정보 끝 -->

    <!-- 답변 -->
    <div class="reply_wrapper">
      <!-- 답변리스트 -->
      <div class="reply_list">
        <template v-for="reply in answerList">
          <template>
            <div class="reply_elem" :key="reply.replyNo">
              <div>
                <div>
                  <img
                    src="@/assets/profile.jpg"
                    alt="profile"
                    class="user_profile"
                  />
                  <h5>{{ reply.memberId }}</h5>
                </div>
                <div class="updata_delete">
                  <span class="update_delete_btn">수정</span>
                  <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                  <span
                    @click="deleteAnswer(reply.answerNo)"
                    class="update_delete_btn"
                    >삭제</span
                  >
                </div>
              </div>
              <p style="margin-top: 8px; color: gray">
                {{ changeDateFormat(reply.regDate) }}
              </p>
              <!-- 답변 내용 -->
              <div v-html="reply.content"></div>
              <!-- 자신이 등록한 답변인 경우 -->
            </div>
          </template>
        </template>
      </div>
      <!-- 답변리스트 끝 -->

      <!-- 답변작성폼 -->
      <div class="reply_write_wrapper">
        <div class="reply_write">
          <div class="reply_member">
            <img
              src="@/assets/profile.jpg"
              alt="profile"
              class="user_profile"
            />
            <h5>{{ this.$store.state.userStore.id }} 님, 답변해주세요.</h5>
          </div>
          <!-- quill-editor -->
          <!-- emit 으로 이벤트 보내서 Editor의 editorContent 데이터 받기 -->
          <!-- ref 으로 하위 컴포넌트인 Editor의 editorContent 데이터 조작(초기화) -->
          <Editor @editorEmit="getContent" ref="editor_content"></Editor>
          <div class="review_btn">
            <b-button variant="primary" @click="insertAnswer">등록</b-button>
          </div>
        </div>
      </div>
      <!-- 답변작성폼 끝-->
    </div>
  </div>
</template>

<script>
import Editor from "../quill-editor/Editor.vue";
import moment from "moment";

export default {
  components: {
    // quill-editor
    Editor,
  },
  props: ["myLecture", "myCurriculum"],
  data: function () {
    return {
      qnaInfo: {},
      answerList: [],
      content: "",
    };
  },
  created: function () {
    this.getQna();
    this.getAnswerList();
  },
  methods: {
    // 강의질문 답변 삭제
    deleteAnswer(answerNo) {
      //////////////////////////////////////////////////삭제(경고 빨간 아이콘) 알림창
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            //여기에다가 삭제시 axios 넣으면 됨
            this.$axios
              .delete("/api/deleteAnswer/" + answerNo)
              .then((resp) => {
                if (resp.data != null) {
                  console.log(resp.data);
                  for (let i = 0; i < this.answerList.length; i++) {
                    if (this.answerList[i].answerNo == answerNo) {
                      this.answerList.splice(i, 1);
                    }
                  }
                  this.$swal.fire("삭제가 완료되었습니다.", "", "success");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        });
    },
    // 강의질문 삭제
    deleteQna() {
      console.log("asfsafad");
      let vm = this;
      //////////////////////////////////////////////////삭제(경고 빨간 아이콘) 알림창
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            //여기에다가 삭제시 axios 넣으면 됨
            this.$axios
              .delete("/api/deleteQna/" + vm.qnaInfo.qnaNo)
              .then((resp) => {
                if (resp.data != "") {
                  console.log(resp.data);
                  this.$swal
                    .fire("삭제되었습니다.", "", "success")
                    .then(function () {
                      vm.$router.go(-1);
                    });
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        });
    },
    // 질문 상세조회 axios
    getQna: function () {
      this.$axios
        .get("/api/qna/" + this.$route.query.qnaNo)
        .then((resp) => {
          // console.log(resp);
          this.qnaInfo = resp.data;
          console.log("toggle qnaInfo", this.qnaInfo);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 질문답변 전체 조회 axios
    getAnswerList: function () {
      this.$axios
        .get("/api/answerList/" + this.$route.query.qnaNo)
        .then((resp) => {
          console.log(resp);
          this.answerList = resp.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 질문답변 작성 axios
    insertAnswer: function () {
      this.$axios
        .post("/api/writeAnswer", {
          qnaNo: this.$route.query.qnaNo,
          memberId: this.$store.state.userStore.id,
          content: this.content,
        })
        .then((resp) => {
          console.log(resp);
          this.answerList.unshift(resp.data);
          // 하위컴포넌트 데이터 조작
          this.$refs.editor_content.editorContent = "";
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // Editor 컴포넌트에서 emit 으로 이벤트 전송 -> Editor의 content 데이터 받는 메서드
    getContent: function (editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },
    // 날짜 포맷 변경
    changeDateFormat: function (date) {
      return moment(date).format("YYYY.MM.DD. a HH:mm");
    },
  },
};
</script>

<style scoped>
#tqd_wrapper {
  width: 420px;
  text-align: left;
}

#tqd_wrapper p {
  font-size: smaller;
}

/* 뒤로가기 이미지 */
#tqd_wrapper .tqd_back_img {
  padding: 14px 0px 0px 14px;
}

#tqd_wrapper .tqd_back_img img {
  width: 26px;
}

#tqd_wrapper .tqd_back_img img:hover {
  cursor: pointer;
}
/* 뒤로가기 이미지 끝 */

#tqd_wrapper .tqd_header {
  margin-bottom: 40px;
}

#tqd_wrapper .tqd_header > div {
  margin-top: 10px;
}
/* 질문 디테일 정보 */
#tqd_wrapper .tqd_info {
  padding: 10px 20px 0px 20px;
  /* background-color: antiquewhite; */
}

#tqd_wrapper .qna_info {
  padding-bottom: 80px;
  border-bottom: 2px solid rgba(0, 0, 0, 0.5);
}

#tqd_wrapper .qna_info > div:nth-child(1) {
  display: flex;
  justify-content: space-between;
}

#tqd_wrapper .qna_info > div > p {
  margin-bottom: 0px;
}

#tqd_wrapper .qna_info > div span {
  font-size: smaller;
}
/* 질문 디테일 정보 끝 */

/* 질문답변 */
.reply_wrapper {
  padding: 20px 20px 0px 20px;
}

.reply_elem {
  padding-bottom: 40px;
}

.reply_elem > div:nth-child(1) {
  display: flex;
  justify-content: space-between;
}

.reply_elem > div > div {
  display: flex;
}

.updata_delete > span {
  font-size: smaller;
}

.reply_member {
  display: flex;
  margin-bottom: 10px;
}

.user_profile {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 8px;
}

.review_btn {
  text-align: right;
}

.update_delete_btn:hover {
  color: cornflowerblue;
  border-bottom: 1px solid cornflowerblue;
  cursor: pointer;
}
</style>
