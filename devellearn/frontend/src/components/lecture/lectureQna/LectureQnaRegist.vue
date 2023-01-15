<template>
  <div>
    <div id="ld_qna_write">
      <h3 v-if="qnaNo == null">Q&A 작성</h3>
      <h3 v-if="qnaNo != null">Q&A 수정</h3>

      <!-- 강의명 출력 -->
      <h5>{{ this.lecture.title }}</h5>

      <!-- 커리큘럼명-->
      <h5>커리큘럼명</h5>
      <div class="curr">
        <b-form-select
          v-model="curr"
          :options="currList"
          class="select_curr"
          id="curr"
        ></b-form-select>
      </div>

      <!-- 질문 제목 -->
      <h5>질문 제목</h5>
      <div>
        <b-form-input
          v-model="title"
          placeholder="질문제목을 입력하세요."
          id="title"
        ></b-form-input>
      </div>

      <!-- 질문 내용 -->
      <h5>질문 내용</h5>
      <div class="ld_editor">
        <Editor
          @editorEmit="getContent"
          ref="editor_content"
          id="content"
        ></Editor>
      </div>

      <!-- Submit Button -->
      <div class="button_area">
        <b-button variant="dark" v-on:click="moveBack">취소</b-button>
        <b-button variant="primary" v-on:click="insert" v-if="qnaNo == null"
          >등록</b-button
        >
        <b-button variant="primary" v-on:click="update" v-if="qnaNo != null"
          >수정</b-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import Editor from "../quill-editor/Editor.vue";
export default {
  components: {
    // quill-editor
    Editor,
  },
  data: function () {
    return {
      // 강의정보(강의번호, 강의명) 조회 필요 : 강의 디테일페이지에서 받으면 됨.
      lecture: {},
      curr: null,
      // 커리큘럼 전체조회 데이터 담을 배열
      currList: [
        { value: null, text: "커리큘럼을 선택하세요.", disabled: true },
      ],
      title: "",
      content: "",
      qnaNo: this.$route.query.qnaNo,
    };
  },
  created: function () {
    // 강의정보 단건 조회
    this.getLectureInfo();
    // 커리큘럼 전체 조회
    this.getCurrList();
    // 수정일 경우
    if (this.qnaNo != null) {
      this.getOldQna();
    }
  },
  methods: {
    // 뒤로가기
    moveBack: function () {
      // 알람창 : 입력하신 정보는 저장되지 않습니다. 정말 나가시겠습니까?
      this.$router.go(-1);
    },
    // 강의 단건조회 axios
    getLectureInfo: function () {
      // 강의 단건조회 axios 통신
      this.$axios
        .get("/api/lecture/" + this.$route.query.no)
        .then((resp) => {
          this.lecture = resp.data;
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    // 해당 강의 커리큘럼 조회 axios
    getCurrList: function () {
      this.$axios
        .get("/api/currList/" + this.$route.query.no)
        .then((resp) => {
          // curriculums 배열에 form-select로 쓸 수 있는 형식으로 데이터 삽입
          for (let i = 0; i < resp.data.length; i++) {
            this.currList.push({
              value: resp.data[i].currNo,
              text: resp.data[i].currName,
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 질문 등록 axios
    insert: function () {
      let vm = this;
      // 유효성 검사
      if (this.content == "" || this.curr == null || this.title == "") {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "빈 칸을 채워주세요.",
          showConfirmButton: true,
          // timer: 1000
        });
      } else {
        this.$axios
          .post("/api/qna", {
            lectureTitle: this.lecture.title,
            memberId: this.$store.state.userStore.id,
            currNo: this.curr,
            currName: this.getCurrName,
            title: this.title,
            content: this.content,
          })
          .then((resp) => {
            if (resp.data != null) {
              console.log(resp);
              Swal.fire({
                position: "center",
                icon: "success",
                title: "질문이 등록되었습니다.",
                showConfirmButton: true,
                // timer: 1000
              }).then(function () {
                vm.$router.go(-1);
              });
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    // Editor 컴포넌트에서 emit 으로 이벤트 전송 -> Editor의 content 데이터 받는 메서드
    getContent: function (editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },
    // 강의질문 수정 - 정보 조회
    getOldQna() {
      this.$axios
        .get("/api/qna/" + this.$route.query.qnaNo)
        .then((resp) => {
          console.log(resp);
          if (resp.data != null) {
            this.title = resp.data.title;
            this.curr = resp.data.currNo;
            // 하위컴포넌트(퀼 에디터)에 조회한 내용 전달
            this.$refs.editor_content.editorContent = resp.data.content;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 강의질문 수정
    update() {
      let vm = this;
      // 유효성 검사
      if (this.content == "" || this.curr == null || this.title == "") {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "빈 칸을 채워주세요.",
          showConfirmButton: true,
          // timer: 1000
        });
      } else {
        this.$axios
          .put("/api/qna", {
            currNo: this.curr,
            currName: this.getCurrName,
            title: this.title,
            content: this.content,
            qnaNo: this.$route.query.qnaNo,
          })
          .then((resp) => {
            if (resp.data != null) {
              console.log(resp.data);
              Swal.fire({
                position: "center",
                icon: "success",
                title: "질문이 수정되었습니다.",
                showConfirmButton: true,
                // timer: 1000
              }).then(function () {
                vm.$router.go(-1);
              });
            }
          });
      }
    },
  },
  computed: {
    // curr번호로 currName 조회
    getCurrName: function () {
      for (let i = 0; i < this.currList.length; i++) {
        if (this.currList[i].value == this.curr) {
          return this.currList[i].text;
        }
      }
    },
  },
};
</script>

<style scoped>
#ld_qna_write {
  width: 40%;
  margin: auto;
  text-align: left;
}

#ld_qna_write h3 {
  margin-top: 30px;
  text-align: center;
}

#ld_qna_write h5 {
  margin-top: 30px;
}

.curr {
  border: 1px solid lightgray;
  border-radius: 5px;
}

.select_curr {
  width: 100%;
  height: 38px;
}

.button_area {
  text-align: center;
}
</style>
