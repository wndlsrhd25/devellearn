<template>
  <div id="tw_wrapper">
    <div class="tw_back_img">
      <img src="@/assets/back.png" class="icon" alt="back" @click="moveBack" />
    </div>
    <div class="tw_form">
      <div class="tw_form_header">
        <h3>Q&A작성</h3>
        <h5>{{ myLecture.title }}</h5>
        <h5>{{ myCurriculum.currName }}</h5>
      </div>
      <!-- 입력창 -->
      <div class="tw_form_content">
        <!-- 질문 제목 -->
        <div>
          <h5>질문 제목</h5>
          <b-form-input
            v-model="title"
            placeholder="질문제목을 입력하세요."
          ></b-form-input>
        </div>

        <!-- 질문 내용 -->
        <div class="tw_editor">
          <h5>질문 내용</h5>
          <Editor @editorEmit="getContent"></Editor>
        </div>

        <!-- Submit Button -->
        <div class="button_area">
          <b-button variant="dark" v-on:click="moveBack">취소</b-button>
          <b-button variant="primary" v-on:click="insert">등록</b-button>
        </div>
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
  props: ["myLecture", "myCurriculum"],
  data: function () {
    return {
      title: "",
      content: "",
    };
  },
  methods: {
    moveBack: function () {
      // console.log("moveBack clicked");
      this.$router.go(-1);
    },
    // 질문 등록 axios
    insert: function () {
      this.$axios
        .post("/api/qna", {
          memberId: this.$store.state.userStore.id,
          currNo: this.myCurriculum.currNo,
          currName: this.myCurriculum.currName,
          lectureTitle: this.myLecture.title,
          title: this.title,
          content: this.content,
        })
        .then((resp) => {
          console.log(resp);
          this.$router.go(-1);
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
  },
};
</script>
<style scroped>
#tw_wrapper {
  width: 420px;
  text-align: left;
}

/* 뒤로가기 이미지 */
#tw_wrapper .tw_back_img {
  padding: 14px 0px 0px 14px;
}

#tw_wrapper .tw_back_img img {
  width: 26px;
}

#tw_wrapper .tw_back_img img:hover {
  cursor: pointer;
}
/* 뒤로가기 이미지 끝 */

/* 입력창 */
#tw_wrapper .tw_form {
  padding: 10px 20px 0px 20px;
}

#tw_wrapper .tw_form_header {
  margin-bottom: 40px;
}

#tw_wrapper .tw_form_content .tw_editor {
  margin-top: 20px;
}

#tw_wrapper .tw_form_content .button_area {
  text-align: right;
}

#tw_wrapper .tw_form_content .button_area > button {
  margin-left: 20px;
}
/* 입력창 끝*/
</style>
