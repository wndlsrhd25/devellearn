<template>
  <div id="tn_wrapper">
    <div class="tn_header">
      <div><h3>강의노트</h3></div>
      <div>
        <h5>{{ myLecture.title }} - {{ myCurriculum.currName }}</h5>
      </div>
    </div>
    <!-- 강의노트 리스트 -->
    <div class="note_list">
      <div v-if="noteList.length == 0">
        작성된 강의노트가 없습니다.<br />강의노트를 작성하여 자신만의 기록을
        남겨보세요.
      </div>
      <template v-for="note in noteList">
        <template>
          <div
            class="note_elem"
            :key="note.lsectureNoteNo"
            @click="moveNoteTime(note.time)"
          >
            <div class="note_elem_info">
              <!-- 에디터로 작성한 html 코드를 읽기 -->
              <div class="note_content" v-html="note.content"></div>
              <div class="note_time">
                <img src="@/assets/clock.png" alt="clock" />
                {{ secondToHms(note.time) }}
              </div>
              <div class="bottom_info">
                <div>
                  <p>
                    {{ changeDateFormat(note.regDate) }}
                  </p>
                </div>
                <!-- 자신이 등록한 강의질문인 경우 -->
                <div class="updata_delete">
                  <span>수정</span>
                  <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                  <span>삭제</span>
                </div>
              </div>
            </div>
          </div>
        </template>
      </template>
    </div>
    <!-- 강의노트 리스트 끝-->

    <!-- 강의노트 작성폼 -->
    <div class="reply_write_wrapper">
      <div class="reply_write">
        <!-- quill-editor -->
        <!-- emit 으로 이벤트 보내서 Editor의 editorContent 데이터 받기 -->
        <!-- ref 으로 하위 컴포넌트인 Editor의 editorContent 데이터 조작(초기화) -->
        <Editor @editorEmit="getContent" ref="editor_content"></Editor>
        <div class="review_btn">
          <b-button variant="primary" @click="insertNote">등록</b-button>
        </div>
      </div>
    </div>
    <!-- 강의노트 작성폼 끝-->
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
  props: ["myLecture", "myCurriculum", "videoCurrentTime"],
  data: function () {
    return {
      noteList: {},
      content: "",
    };
  },
  created: function () {
    this.getNoteList();
  },
  methods: {
    // 강의노트 리스트 조회 axios
    getNoteList: function () {
      this.$axios
        .post("/api/noteList", {
          memberId: this.$store.state.userStore.id,
          currNo: this.$route.query.curr,
        })
        .then((resp) => {
          console.log(resp);
          this.noteList = resp.data;
          console.log("toggle note list", this.noteList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 강의노트 등록 axios
    insertNote: function () {
      // 유효성 검사
      if (this.content == "") {
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "내용을 입력하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
        return;
      }
      this.$axios
        .post("/api/noteWrite", {
          currNo: this.$route.query.curr,
          memberId: this.$store.state.userStore.id,
          content: this.content,
          // 동영상에서 현재 플레이시간 따오는 로직 필요
          time: this.videoCurrentTime,
        })
        .then((resp) => {
          console.log(resp);
          this.noteList.unshift(resp.data);
          this.$refs.editor_content.editorContent = "";
        });
    },
    // Editor 컴포넌트에서 emit 으로 이벤트 전송 -> Editor의 content 데이터 받는 메서드
    getContent: function (editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },
    // 강의노트 시간대로 이동
    moveNoteTime(noteTime) {
      // 부모 컴포넌트로 이벤트 전송
      this.$emit("moveNoteTime", noteTime);
    },
    secondToHms(value) {
      const sec = parseInt(value, 10); // convert value to number if it's string
      let hours = Math.floor(sec / 3600); // get hours
      let minutes = Math.floor((sec - hours * 3600) / 60); // get minutes
      let seconds = sec - hours * 3600 - minutes * 60; //  get seconds
      // add 0 if value < 10; Example: 2 => 02
      if (hours < 10) {
        hours = "0" + hours + ":";
        if (hours == "00:") {
          hours = "";
        }
      } else {
        hours = hours + ":";
      }
      if (minutes < 10) {
        minutes = "0" + minutes + ":";
      } else {
        minutes = minutes + ":";
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      return hours + minutes + seconds; // Return is HH : MM : SS
    },
    // 날짜 포맷 변경
    changeDateFormat: function (date) {
      return moment(date).format("YYYY.MM.DD. a HH:mm");
    },
  },
};
</script>

<style scoped>
#tn_wrapper {
  width: 420px;
  text-align: left;
  padding: 20px;
}
#tn_wrapper .tn_header > div {
  margin-bottom: 15px;
}
#tn_wrapper .tn_header {
  margin-bottom: 40px;
}

/* 강의노트 리스트 */
#tn_wrapper .note_list {
  padding-bottom: 60px;
  margin-bottom: 20px;
  border-bottom: 2px solid rgba(0, 0, 0, 0.5);
}
.note_elem {
  border: 1px solid lightgray;
  border-radius: 5px;
  box-shadow: 0 4px 4px -4px black;
  padding: 10px;
  margin-bottom: 10px;
  font-size: smaller;
}

.note_elem_info {
  text-align: left;
}

.note_elem_info p {
  margin-bottom: 0px;
}

.note_elem:hover {
  background-color: rgba(204, 204, 204, 0.2);
  cursor: pointer;
}

.note_elem_info .bottom_info {
  display: flex;
  justify-content: space-between;
  border-top: 1px solid lightgray;
  padding-top: 5px;
}

.note_elem_info .elem_num {
  display: flex;
}

.note_elem_info .note_time {
  text-align: right;
  margin-bottom: 5px;
}

.note_elem_info .note_time img {
  width: 15px;
  margin-bottom: 3px;
}

.review_btn {
  text-align: right;
}
/* 강의노트 리스트 끝 */
</style>
