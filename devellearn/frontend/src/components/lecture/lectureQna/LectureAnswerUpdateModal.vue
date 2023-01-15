<template>
  <div>
    <Editor @editorEmit="getContent" ref="editor_content"></Editor>
    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('updateAnswerModal')">취소</b-button>
      <b-button @click="updateAnswer" variant="primary">수정</b-button>
    </div>
  </div>
</template>

<script>
import Editor from "../quill-editor/Editor.vue";
export default {
  components: {
    Editor,
  },
  props: {
    answerNo: "",
  },
  data() {
    return {
      content: "",
    };
  },
  created() {
    this.getOldAnswer();
  },
  methods: {
    // Editor 컴포넌트에서 emit 으로 이벤트 전송 -> Editor의 content 데이터 받는 메서드
    getContent(editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },
    // 기존 답변 조회 axios
    getOldAnswer() {
      this.$axios.get("/api/lectureAnswer/" + this.answerNo).then((resp) => {
        if (resp.data != null) {
          console.log("answer update modal getOldAnswer", resp.data);
          this.$refs.editor_content.editorContent = resp.data.content;
        }
      });
    },
    // 답변 수정 axios
    updateAnswer() {
      if (this.content == "" || this.rate == 0) {
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
          .put("/api/updateAnswer", {
            answerNo: this.answerNo,
            content: this.content,
          })
          .then((resp) => {
            if (resp.data != null) {
              console.log("answer update modal updateAnswer", resp.data);
              // 수정된 답변 부모컴포넌트에 전송
              this.$emit("getNewAnswer", this.content);
              this.$bvModal.hide("updateAnswerModal");
              ////////////////////////////////글작성 완료 알림창
              Swal.fire({
                position: "center",
                icon: "success",
                title: "답변이 수정되었습니다.",
                showConfirmButton: true,
                // timer: 1000
              });
            }
          });
      }
    },
  },
};
</script>

<style scoped></style>
