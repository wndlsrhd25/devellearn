<template>
  <div>
    <!-- 질문 상세 폼 -->
    <div class="ld_qna_detail">
      <div class="qna_info">
        <div>
          <h3>{{ qnaInfo.title }}</h3>
          <!-- 자신이 등록한 강의질문인 경우 -->
          <div
            class="update_delete_btn"
            v-if="$store.state.userStore.id == qnaInfo.memberId"
          >
            <span @click="updateQna">수정</span>
            <span @click="deleteQna">삭제</span>
          </div>
        </div>
        <p>
          {{ qnaInfo.memberId }}&nbsp;·&nbsp;
          {{ changeDateFormat(qnaInfo.regDate) }}&nbsp;·&nbsp;
          {{ qnaInfo.lectureTitle }}&nbsp;·&nbsp;
          {{ qnaInfo.currName }}
        </p>
        <hr />
        <div v-html="qnaInfo.content" class="qnaInfo_content"></div>
      </div>
      <div class="qna_btn">
        <!-- 자신이 등록한 강의질문인 경우 : 버튼 클릭시 미해결->해결 변경 가능 -->
        <div
          v-show="$store.state.userStore.id == qnaInfo.memberId"
          :class="{ resolved_btn: qnaInfo.status == '해결' ? true : false }"
          @click="clickResolved"
        >
          {{ qnaInfo.status }}
        </div>
        <!-- 강의질문에 대한 좋아요 : 버튼 클릭시 관심강의질문에 등록, ♡->♥ 변경 가능 -->
        <div @click.stop="clickLiked">
          <b-icon
            :icon="isLiked == false ? 'suit-heart' : 'suit-heart-fill'"
            class="h4"
            variant="danger"
          ></b-icon>
          <div>{{ likeCnt }}</div>
        </div>
      </div>
    </div>
    <!-- 질문 상세 폼 끝 -->

    <!-- 답변 -->
    <div class="reply_wrapper">
      <!-- 답변작성폼 -->
      <div class="reply_write_wrapper" v-if="checkLogin() && checkPay()">
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

      <!-- 답변리스트 -->
      <div class="reply_list">
        <template v-for="answer in answerList">
          <template>
            <div class="reply_elem" :key="answer.replyNo">
              <div>
                <div>
                  <img
                    src="@/assets/profile.jpg"
                    alt="profile"
                    class="user_profile"
                  />
                  <h4>{{ answer.memberId }}</h4>
                </div>
                <!-- 자신이 등록한 답변인 경우 -->
                <div
                  class="update_delete_btn"
                  v-if="$store.state.userStore.id == answer.memberId"
                >
                  <span @click="showUpdateAnswerModal(answer.answerNo)"
                    >수정</span
                  >
                  <span @click="deleteAnswer(answer.answerNo)">삭제</span>
                </div>
              </div>

              <p>{{ changeDateFormat(answer.regDate) }}</p>
              <hr />
              <!-- 답변 내용 -->
              <div v-html="answer.content"></div>
            </div>
          </template>
        </template>
      </div>
      <!-- 답변리스트 끝 -->

      <!-- 답변 수정 모달 -->
      <b-modal
        id="updateAnswerModal"
        centered
        title="답변 수정내용을 입력하세요."
        size="m"
        footerClass="p-2 border-top-0"
      >
        <LectureAnswerUpdateModal
          :answerNo="updateAnswerNo"
          @getNewAnswer="updateAnswerList"
        ></LectureAnswerUpdateModal>
        <template #modal-footer="{ hide }">
          <b-button @click="hide('forget')" style="display: none"></b-button>
        </template>
      </b-modal>
      <!-- 답변 수정 모달 끝 -->
    </div>
  </div>
</template>

<script>
import Editor from "../quill-editor/Editor.vue";
import moment from "moment";
import LectureAnswerUpdateModal from "./LectureAnswerUpdateModal.vue";

export default {
  components: {
    // quill-editor
    Editor,
    LectureAnswerUpdateModal,
  },
  data: function () {
    return {
      // 질문 상세정보
      qnaInfo: {},
      // 질문에 대한 답변리스트 담을 배열
      answerList: [],
      content: "",
      isLiked: false,
      likedQna: {},
      likeCnt: "",
      updateAnswerNo: "",
      dialog: false,
      dialogPay: false,
      myCurrList: [],
    };
  },
  created: function () {
    // 질문 상세 조회
    this.getLectureInfo();
    // 질문답변 리스트 조회
    this.getAnswerList();
    // 좋아요 했는지 확인
    this.likedCheck();
    // 좋아요 수 카운트
    this.countLectureQnaLike();
    // 나의 커리큘럼 전체조회
    this.getMyCurrList();
  },
  methods: {
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
    checkPay() {
      if (this.myCurrList.length == 0) {
        return false;
      } else {
        return true;
      }
    },
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
    // 질문 상세 조회 axios
    getLectureInfo: function () {
      this.$axios
        .get("/api/qna/" + this.$route.query.qnaNo)
        .then((resp) => {
          console.log(resp);
          this.qnaInfo = resp.data;
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
    insertAnswer() {
      // 유효성 검사
      if (this.content == "") {
        this.$bvModal.show("checkIsEmpty");
        return;
      }
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
          ////////////////////////////////글작성 완료 알림창
          Swal.fire({
            position: "center",
            icon: "success",
            title: "답변이 작성되었습니다.",
            showConfirmButton: true,
            // timer: 1000
          });
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
    // 해결 / 미해결 전환
    clickResolved: function () {
      if (this.qnaInfo.status == "해결") {
        // 미해결로 변경
        this.qnaInfo.status = "미해결";
      } else {
        // 해결로 변경
        this.qnaInfo.status = "해결";
      }
      console.log("status", this.qnaInfo.status);
      this.$axios
        .put("/api/qna", {
          qnaNo: this.$route.query.qnaNo,
          status: this.qnaInfo.status,
        })
        .then((resp) => {
          console.log(resp);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 좋아요 확인
    likedCheck: function () {
      this.$axios
        .post("/api/lectureQnaLike", {
          memberId: this.$store.state.userStore.id,
          qnaNo: this.$route.query.qnaNo,
        })
        .then((resp) => {
          console.log("lectureQnaLike", resp.data);
          if (resp.data != "") {
            this.isLiked = true;
            this.likedQna = resp.data;
          } else {
            this.isLiked = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 좋아요 / 좋아요 취소
    clickLiked: function () {
      /////////////////////////////////////////////로그인(그냥 느낌표 아이콘) 알림창
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        //내가 실제로 그 버튼을 눌렀을때 실행되야하는 것
        if (this.isLiked == true) {
          // 좋아요 취소 - delete
          // --> deleteMapping은 @RequestBody 를 쓸 수 없어 @Pathvariable 활용하기 위해 likeNo 필요
          this.$axios
            .delete("/api/deleteLectureQnaLike/" + this.likedQna.likeNo)
            .then((resp) => {
              console.log("deleteLectureQnaLike", resp.data);
              if (resp.data != "") {
                // css 변경
                this.isLiked = !this.isLiked;
                // 좋아요 카운트 - 1
                this.likeCnt -= 1;
                console.log("this.likedQna", this.likedQna);
              }
            })
            .catch((error) => {
              console.log(error);
            });
        } else {
          // 좋아요 - insert
          this.$axios
            .post("/api/insertLectureQnaLike", {
              memberId: this.$store.state.userStore.id,
              qnaNo: this.$route.query.qnaNo,
            })
            .then((resp) => {
              console.log("insertLectureQnaLike", resp.data);
              if (resp.data != "") {
                // css 변경
                this.isLiked = !this.isLiked;
                // 데이터 변수에 삽입 -> 좋아요 취소할 때 qnaNo 가져오기 위함
                this.likedQna = resp.data;
                // 좋아요 카운트 + 1
                this.likeCnt += 1;
              }
            })
            .catch((error) => {
              console.log(error);
            });
        }
      }
    },
    // 강의질문 좋아요 수 카운트
    countLectureQnaLike: function () {
      this.$axios
        .get("/api/lectureQnaLikeCnt/" + this.$route.query.qnaNo)
        .then((resp) => {
          console.log("lectureQnaLikeCnt", resp.data);
          this.likeCnt = resp.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 날짜 포맷 변경
    changeDateFormat: function (date) {
      return moment(date).format("YYYY.MM.DD. a HH:mm");
    },
    // 강의질문 삭제
    deleteQna() {
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
    // 강의질문 수정 페이지 이동
    updateQna() {
      this.$router
        .push({
          path: "/lectureQnaRegist",
          query: { no: this.$route.query.no, qnaNo: this.qnaInfo.qnaNo },
        })
        .catch(() => {});
    },
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
    // 강의질문 답변 수정 모달
    showUpdateAnswerModal(answerNo) {
      this.updateAnswerNo = answerNo;
      this.$bvModal.show("updateAnswerModal");
    },
    // 강의질문 답변 배열 원소 수정
    updateAnswerList(newAnswerContent) {
      for (let i = 0; i < this.answerList.length; i++) {
        if (this.answerList[i].answerNo == this.updateAnswerNo) {
          this.answerList[i].content = newAnswerContent;
          break;
        }
      }
    },
  },
};
</script>

<style scoped>
.ld_qna_detail {
  width: 60%;
  display: flex;
  justify-content: space-between;
  margin: auto;
  margin-top: 160px;
  text-align: left;
}

.qna_info {
  width: 80%;
}

/* 질문제목 / 수정삭제버튼 */
.qna_info div:nth-child(1) {
  display: flex;
  justify-content: space-between;
}

/* 수정삭제버튼 */
.update_delete_btn {
  margin-top: 8px;
}

.update_delete_btn span:hover {
  color: cornflowerblue;
  border-bottom: 1px solid cornflowerblue;
  cursor: pointer;
}

.update_delete_btn span:nth-child(1) {
  margin-right: 12px;
}

.qna_info p {
  color: gray;
}

.reply_wrapper {
  background-color: rgba(204, 204, 204, 0.2);
  margin-top: 50px;
  padding: 40px 0px 40px 0px;
  text-align: left;
}

.reply_wrapper img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 8px;
}

.reply_write_wrapper {
  width: 60%;
  margin: auto;
}

.reply_write {
  width: 80%;
  background-color: white;
  border-radius: 15px;
  margin-bottom: 30px;
  padding: 20px;
}

.reply_member {
  display: flex;
  margin-bottom: 10px;
}

.reply_list {
  width: 60%;
  margin: auto;
  text-align: left;
}

.reply_elem {
  margin-bottom: 30px;
  width: 80%;
  background-color: white;
  padding: 20px;
  border-radius: 15px;
}

.reply_elem div:nth-child(1) {
  display: flex;
  justify-content: space-between;
}

/* 해결, 좋아요 버튼 */
.qna_btn > div {
  margin-bottom: 20px;
  border: 1px solid lightgray;
  border-radius: 8px;
  box-shadow: 0px 4px 4px -4px black;
  width: 80px;
  height: 38px;
  text-align: center;
  line-height: 38px;
  font-weight: bolder;
  cursor: pointer;
}

.resolved_btn {
  background-color: steelblue;
  color: white;
}
.qna_btn > div:hover {
  transform: translateY(-2px);
}

.qna_btn div:nth-child(2) {
  display: flex;
  justify-content: center;
  font-size: 18px;
  padding-left: 6px;
}

.qna_btn .h4 {
  margin-top: 8px;
}

.qna_btn span {
  margin-top: 1px;
}
/* 해결, 좋아요 버튼 끝 */

.review_btn {
  margin-top: 20px;
  text-align: right;
}
</style>
