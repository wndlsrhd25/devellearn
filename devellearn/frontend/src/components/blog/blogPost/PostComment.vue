<template>
  <div style="min-width: 800px">
    <div data-app>
      <v-dialog v-model="dialog" persistent max-width="500px">
        <ReportModal2 :report="report" @close="dialog = false"></ReportModal2>
      </v-dialog>
    </div>
    <div class="comment-info">
      <div>
        <b-button @click="insert()" class="comment-btn" variant="info"
          ><b-icon
            icon="chat-right"
            font-scale="1"
            style="margin-right: 5px"
          ></b-icon
          >댓글달기</b-button
        >
      </div>
      <div style="margin-left: 30px; margin-top: 5px">
        <h5>({{ this.commentCnt }})</h5>
      </div>
    </div>

    <div v-if="show">
      <form id="insert-comment">
        <div class="comment-insert-box">
          <div class="ld_editor">
            <Editor @editorEmit="getContent" ref="editor_content"></Editor>
          </div>

          <!-- 댓글 등록 -->
          <div class="button-box" v-if="insertBtn">
            <b-button variant="primary" @click="insertComment()">등록</b-button>
            <b-button class="cancle-btn" @click="closeBtn()">취소</b-button>
          </div>

          <!-- 댓글 수정 -->
          <div class="button-box" v-if="updateBtn">
            <b-button variant="primary" @click="updateComment($event)"
              >수정</b-button
            >
            <b-button class="cancle-btn" @click="closeBtn()">취소</b-button>
          </div>

          <!-- 대댓글 등록 -->
          <div class="button-box" v-if="insertReplyBtn">
            <b-button variant="primary" @click="insertCommentReply($event)"
              >등록</b-button
            >
            <b-button class="cancle-btn" @click="closeBtn()">취소</b-button>
          </div>
        </div>
      </form>
    </div>

    <template v-for="item in commentList">
      <template>
        <div :key="item.commentNo" class="comments">
          <div class="commentList-box" v-if="item.commParent == ''">
            <div>
              <div>
                <img
                  :src="'/api/images/profile/' + item.profile"
                  alt="profile"
                  class="user_profile"
                />
                <h4>{{ item.memberId }}</h4>
                <p style="margin-left: 30px; margin-top: 3px">
                  {{ changeDate(item.createDate) }}
                </p>
              </div>
            </div>
            <div>
              <p
                style="margin-left: 70px; text-align: left"
                v-html="item.content"
              ></p>
              <!-- {{ item.content }} -->
            </div>
            <div class="bottom">
              <div style="margin-left: auto">
                <p
                  style="cursor: pointer; color: royalblue"
                  @click="insertReply(item)"
                >
                  답글
                </p>
                <p
                  v-if="item.memberId == $store.state.userStore.id"
                  style="margin-left: 10px; cursor: pointer"
                  @click="update(item)"
                >
                  수정
                </p>
                <p
                  v-if="item.memberId == $store.state.userStore.id"
                  style="margin-left: 10px; cursor: pointer"
                  @click="xxx(item)"
                >
                  삭제
                </p>
                <p
                  v-if="item.memberId != $store.state.userStore.id"
                  style="margin-left: 10px; cursor: pointer"
                  @click="reportComment(item)"
                >
                  신고하기
                </p>
              </div>
            </div>
          </div>

          <!--대댓글-->
          <div class="replyList-box" v-if="item.commParent != ''">
            <div>
              <div>
                <img
                  :src="'/api/images/profile/' + item.profile"
                  alt="profile"
                  class="user_profile"
                />
                <h4>{{ item.memberId }}</h4>
                <p style="margin-left: 30px; margin-top: 3px">
                  {{ changeDate(item.createDate) }}
                </p>
              </div>
            </div>
            <div>
              <p
                style="margin-left: 70px; text-align: left"
                v-html="item.content"
              ></p>
            </div>
            <div class="reply-bottom">
              <div style="margin-left: auto">
                <p
                  v-if="item.memberId == $store.state.userStore.id"
                  style="margin-left: 10px; cursor: pointer"
                  @click="update(item)"
                >
                  수정
                </p>
                <p
                  v-if="item.memberId == $store.state.userStore.id"
                  style="margin-left: 10px; cursor: pointer"
                  @click="xxx(item)"
                >
                  삭제
                </p>
                <p
                  v-if="item.memberId != $store.state.userStore.id"
                  style="margin-left: 10px; cursor: pointer"
                  @click="reportComment(item)"
                >
                  신고하기
                </p>
              </div>
            </div>
          </div>
        </div>
      </template>
    </template>
  </div>
</template>

<script>
import Editor from "@/components/lecture/quill-editor/Editor.vue";
import ReportModal2 from "@/components/common/ReportModal2.vue";
export default {
  data() {
    return {
      commentList: [],
      content: "",
      show: false,
      commentNo: "",
      insertBtn: false,
      updateBtn: false,
      postContent: {},
      dialog: false,
      report: {},
      insertReplyBtn: false,
      commentCnt: "",
      no: "",
    };
  },
  components: {
    Editor,
    ReportModal2,
  },

  created() {
    this.getCommentList();
  },
  methods: {
    getCommentList() {
      this.$axios
        .post("/api/blogComment", { postNo: this.$route.query.postNo })
        .then((resp) => {
          console.log("댓글리스트");
          console.log(resp);
          this.commentList = resp.data;
          if (this.commentList != "" && this.commentList != null) {
            this.commentCnt = this.commentList[0].commentCnt;
          } else {
            this.commentCnt = 0;
          }
        });
    },
    //댓글 입력창 열기
    insert() {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
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
        this.show = true;
        this.insertBtn = true;
        this.updateBtn = false;
        this.insertReplyBtn = false;
      }
    },
    //댓글 입력하기
    insertComment() {
      console.log("내용없음" + this.content);
      console.log("회원 아이디: " + this.$store.state.userStore.id);
      console.log("포스트 번호" + this.$route.query.postNo);
      this.$axios
        .post("/api/blogComment/write", {
          memberId: this.$store.state.userStore.id,
          postNo: this.$route.query.postNo,
          content: this.content,
        })
        .then((resp) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "댓글 입력 성공",
            showConfirmButton: false,
            timer: 1000,
          });
          console.log(resp);
          this.getCommentList();
          this.$refs.editor_content.editorContent = "";
          this.insertBtn = false;
          this.show = false;
        })
        .catch((error) => {
          console.log("댓글에러", error);
        });
    },
    //대댓글 입력창 열기
    insertReply(item) {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
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
        console.log("댓글 번호 : " + item.commentNo);
        this.no = item.commentNo;
        this.show = true;
        this.insertReplyBtn = true;
        this.updateBtn = false;
        this.insertBtn = false;
      }
    },
    //대댓글 입력하기
    insertCommentReply() {
      console.log("내용없음" + this.content);
      console.log("회원 아이디: " + this.$store.state.userStore.id);
      console.log("포스트 번호" + this.$route.query.postNo);
      console.log("댓글번호 : " + this.no);

      this.$axios
        .post("/api/blogComment/write/reply", {
          memberId: this.$store.state.userStore.id,
          postNo: this.$route.query.postNo,
          commParent: this.no,
          content: this.content,
        })
        .then((resp) => {
          console.log(resp);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "댓글입력 성공",
            showConfirmButton: false,
            timer: 1000,
          });
          this.getCommentList();
          this.$refs.editor_content.editorContent = "";
          this.insertReplyBtn = false;
          this.show = false;
          this.commentCnt += 1;
        })
        .catch((error) => {
          console.log("댓글에러", error);
        });
    },
    //댓글 삭제
    xxx(item) {
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
            this.$axios
              .delete("/api/blogComment/" + item.commentNo)
              .then((resp) => {
                console.log(resp);
                this.getCommentList();
              });
            this.$swal.fire({
              title: "삭제완료",
              icon: "success",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        });
    },
    update(item) {
      //수정 버튼 눌렀을때 댓글 단건조회해야함
      console.log("내용 = " + item.content);
      console.log("번호" + item.commentNo);
      this.no = item.commentNo;

      this.show = true;
      this.updateBtn = true;
      this.insertBtn = false;
      this.insertReplyBtn = false;

      this.$axios
        .post("/api/blogComment/select", { commentNo: item.commentNo })
        .then((resp) => {
          console.log(resp);
          this.postContent = resp.data;
          this.$refs.editor_content.editorContent = this.postContent.content;
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    updateComment() {
      console.log("update한 내용=" + this.content);
      //no는 v-model이 아니라서... 어떻게 값을 가져오지?
      console.log("update한 번호=" + this.no);
      this.$axios
        .post("/api/blogComment/update", {
          content: this.content,
          commentNo: this.no,
        })
        .then((resp) => {
          Swal.fire({
            position: "center",
            icon: "success",
            title: "댓글 수정 성공",
            showConfirmButton: false,
            timer: 1000,
          });
          console.log("값이 뭔데?", resp);
          this.updateBtn = false;
          this.show = false;
          this.content = "";
          this.getCommentList();
        });
    },
    closeBtn() {
      this.show = false;
      this.insertReplyBtn = false;
      this.insertBtn = false;
      this.updateBtn = false;
    },

    getContent(editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
      console.log("겟 콘탠트 내용" + this.content);
    },

    //신고하기
    reportComment(item) {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
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
        this.report.postCode = "blog_comments";
        this.report.postNo = item.commentNo;
        this.dialog = true;
      }
    },
    changeDate: function (date) {
      return moment(date).format("YYYY.MM.DD  HH:mm");
    },
  },
};
</script>
<style scoped>
::v-deep .v-application--wrap {
  min-height: fit-content;
}
/* 댓글 입력창 */
.comment-info {
  display: flex;
  justify-content: flex-start;
}
.comment-btn {
  margin-bottom: 20px;
}
.comment-insert-box {
  min-width: 1000px;
  margin-right: 300px;
}

.ld_editor {
  min-width: 1200px;
  margin-left: 20px;
  margin-top: 20px;
}
.button-box {
  margin-top: 5px;
  margin-bottom: 10px;
  width: 1200px;
}
.cancle-btn {
  margin-left: 20px;
}
.comment-box {
  margin-left: 0;
  width: 98%;
}

/* 댓글 리스트 */
.comments {
  width: 100%;
  margin-top: 20px;
}
.commentList-box {
  border-top: 1px solid lightgray;
  padding: 10px;
  margin-top: 10px;
  margin: 0 auto;
  width: 100%;
  min-width: 1100px;
}
.commentList-box div {
  display: flex;
  justify-content: space-between;
}
.user_profile {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 20px;
}
.commentList-box div:nth-child(3) {
  margin-top: 10px;
  color: gray;
}

.bottom {
  display: flex;
  justify-content: flex-start;
  width: 1230px;
  height: 10px;
}

.replyList-box {
  border-top: 0.5px solid lightgray;
  padding: 10px;
  margin-top: 10px;
  margin-left: 50px;
  min-width: 800px;
}

.replyList-box div {
  display: flex;
  justify-content: space-between;
}

.replyList-box div:nth-child(3) {
  margin-top: 10px;
  color: gray;
}

.reply-bottom {
  display: flex;
  justify-content: flex-start;
  margin-left: 180px;
  width: 1000px;
  height: 10px;
}
</style>
