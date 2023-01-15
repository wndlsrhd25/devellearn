<template lang="">
  <!-- Contact-->
  <div id="lg_wrapper">
    <!-- 타이틀 헤더 -->
    <div class="lg_header">
      <h2 class="">강의 수정</h2>
      <hr />
    </div>
    <!-- 타이틀 헤더 끝 -->

    <!-- 입력 폼 -->
    <div class="">
      <form id="contactForm">
        <!-- 강의 제목 -->
        <div>
          <h5>강의 제목</h5>
          <b-form-input
            v-model="title"
            placeholder="강의제목을 입력하세요."
          ></b-form-input>
        </div>

        <!-- 강의 소개 -->
        <div>
          <h5>강의 소개</h5>
          <Editor @editorEmit="getContent" ref="editor_content"></Editor>
        </div>

        <!-- 강의 카테고리-->
        <div class="category">
          <h5>강의 카테고리</h5>
          <b-form-select
            v-model="category"
            :options="categories"
            class="select_category"
          ></b-form-select>
        </div>

        <!-- 커리큘럼명, 첨부파일 -->
        <!-- add 버튼 클릭시 커리큘럼 입력창 추가 -->
        <!-- js를 이용해 직접 추가시 부트스트랩뷰 태그는 추가가 안됨... 그래서 추가된 입력창은 데이터바인딩이 어려움 -->
        <!-- 일반 input 태그를 써야하는지? 데이터 주고받을때 체크필요 -->
        <form id="curr_data">
          <div class="curr_title">
            <h5>커리큘럼</h5>
            <b-button @click="addCurrForm" variant="primary">add</b-button>
          </div>
          <div v-for="(form, index) in currFormList" :key="index">
            <div class="curr">
              <b-form-input
                v-model="currFormList[index].currName"
                placeholder="커리큘럼명을 입력하세요."
                class="curr_input"
              ></b-form-input>
              <div id="blank1"></div>
              <v-file-input
                v-model="currFormList[index].currFile"
                accept="video/*"
                placeholder="Upload video"
                class="curr_file"
              />
              <b-button
                variant="danger"
                style="height: 34px; margin-top: 10px"
                @click="removeCurrInput(index)"
                >X</b-button
              >
            </div>
          </div>
        </form>

        <!-- 기술스택 태그 -->
        <!-- 현재는 셀렉박스로 구성 -> 모달창을 띄어서 검색하여 선택하는것이 좋을지 고민(리스트의 양이 너무 많기 때문) -->
        <div class="skill_stack">
          <h5>기술스택</h5>

          <b-form-tags
            input-id="tags-basic"
            v-model="skillList"
            remove-on-delete
            tag-pills
            tag-variant="primary"
            separator=" ,;"
            :limit="limit"
          ></b-form-tags>
        </div>

        <!-- 강의 난이도 -->
        <!-- radio 태그 이용 -->
        <div>
          <h5>강의 난이도</h5>
          <b-form-radio-group
            v-model="difficulty"
            :options="levels"
            :aria-describedby="ariaDescribedby"
            name="radio-inline"
            class="radio_levels"
          ></b-form-radio-group>
        </div>

        <!-- 강의 가격 -->
        <div>
          <h5>강의 가격</h5>
          <b-form-input
            v-model="price"
            type="number"
            placeholder="가격을 입력하세요."
          ></b-form-input>
        </div>

        <!-- 강의 썸네일 -->
        <form id="lecture_thumbnail">
          <div>
            <h5>강의 썸네일</h5>
            <div class="lecture_thumbnail">
              <v-flex xs8>
                <v-file-input
                  v-model="image"
                  name="image"
                  accept=".png, .jpeg, .jpg"
                  placeholder="Upload profile image"
                  prepend-icon="mdi-camera"
                  outlined
                  @change="Preview_image()"
                  class="lecture_thumbnail_input"
                />
              </v-flex>
              <v-avatar size="300" rounded="0" style="background-color: gray">
                <v-img :src="imagePreview" />
              </v-avatar>
            </div>
          </div>
        </form>

        <!-- Submit Button -->
        <div class="button_area">
          <b-button variant="dark" @click="moveBack">취소</b-button>
          <b-button variant="primary" @click="update">수정</b-button>
        </div>
      </form>
    </div>
    <!-- 입력 폼 끝 -->
  </div>
</template>

<script>
import Editor from "../../../lecture/quill-editor/Editor.vue";

export default {
  components: {
    Editor,
  },
  data() {
    return {
      title: "",
      content: "",
      category: null,
      categories: [
        { value: null, text: "강의 카테고리를 선택하세요.", disabled: true },
        { value: "프론트엔드", text: "프론트엔드" },
        { value: "백엔드/서버", text: "백엔드/서버" },
        { value: "모바일", text: "모바일" },
        { value: "게임", text: "게임" },
        { value: "임베디드", text: "임베디드" },
        { value: "자료구조/알고리즘", text: "자료구조/알고리즘" },
        { value: "보안", text: "보안" },
        { value: "데이터사이언스", text: "데이터사이언스" },
        { value: "데이터베이스", text: "데이터베이스" },
        { value: "자격증", text: "자격증" },
        { value: "프로그래밍언어", text: "프로그래밍언어" },
      ],
      currFormList: [],
      currName: "",
      currFile: "",
      skillList: [],
      limit: 5,
      difficulty: null,
      levels: [
        { text: "초급", value: "초급" },
        { text: "중급", value: "중급" },
        { text: "고급", value: "고급" },
      ],
      price: "",
      lectureNo: this.$route.query.no,
      image: "",
      imagePreview: "",
      ariaDescribedby: "",
      previousLecture: {},
    };
  },
  created() {
    this.getLectureInfo();
    this.getCurrList();
  },
  methods: {
    // 커리큘럼 입력창 제거
    removeCurrInput(index) {
      for (let i = 0; i < this.currFormList.length; i++) {
        if (index == i) {
          this.currFormList.splice(i, 1);
        }
      }
    },
    // 커리큘럼 입력창 추가
    addCurrForm: function () {
      this.currFormList.push({
        currName: "",
        currFile: "",
      });
    },
    // 강의 수정 axios - form 데이터로
    async update() {
      let skillTagStr = "";
      for (let i = 0; i < this.skillList.length; i++) {
        let skill = "#" + this.skillList[i] + " ";
        skillTagStr += skill;
      }
      console.log("강의 등록 - 스킬태그 문자열로 변환", skillTagStr);

      const formData = new FormData(
        document.getElementById("lecture_thumbnail")
      );
      formData.append("lectureNo", this.lectureNo);
      formData.append("memberId", this.$store.state.userStore.user.memberId);
      formData.append("title", this.title);
      formData.append("introduce", this.content);
      formData.append("category", this.category);
      formData.append("price", this.price);
      formData.append("difficulty", this.difficulty);
      formData.append("skillTag", skillTagStr);
      // 강의수정
      this.$axios
        .put("/api/updateLecture", formData, {
          Headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(function (resp) {
          console.log(resp);
        })
        .catch(function (error) {
          console.log(error);
        });

      // 커리큘럼 수정
      for (let i = 0; i < this.currFormList.length; i++) {
        // 스프링 서버로 전송할 formdata 객체 생성
        const formData = new FormData(document.getElementById("curr_data"));
        // formdata에 데이터 추가 - vo 칼럼 이름과 일치하게 이름 설정
        formData.append("currNo", this.currFormList[i].currNo);
        formData.append("lectureNo", this.lectureNo);
        formData.append("lectureTitle", this.title);
        formData.append("currName", this.currFormList[i].currName);
        formData.append("video", this.currFormList[i].currFile);
        // 커리큘럼 등록 axios - formdata multipart 타입으로 전송
        // --> 그럼 스프링에서 알아서 받아줌
        await this.$axios
          .put("/api/updateCurr", formData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((resp) => {
            if (resp.data != null) {
              console.log(resp);
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    // 강의단건조회 - 데이터 화면에 뿌리기
    getLectureInfo() {
      this.$axios
        .get("/api/lecture/" + this.lectureNo)
        .then((resp) => {
          if (resp.data != null) {
            console.log("강의 수정페이지 강의단건조회", resp.data);
            // 수정할 내역 조회해서 뿌리기
            this.previousLecture = resp.data;
            this.title = this.previousLecture.title;
            this.$refs.editor_content.editorContent =
              this.previousLecture.introduce;
            this.category = this.previousLecture.category;
            this.skillList = this.previousLecture.skillTag.split("#");
            this.difficulty = this.previousLecture.difficulty;
            this.price = this.previousLecture.price;
            this.imgSrc =
              "http://localhost:8088/devellearn/images/lecture/" +
              this.previousLecture.imgSrc;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 커리큘럼 전체 조회
    getCurrList() {
      this.$axios
        .get("/api/currList/" + this.lectureNo)
        .then((resp) => {
          if (resp.data != null) {
            console.log("강의 수정페이지 커리큘럼전체조회", resp.data);
            // currName, currFile
            for (let i = 0; i < resp.data.length; i++) {
              this.currFormList.push({
                currNo: resp.data[i].currNo,
                currName: resp.data[i].currName,
                currFile: resp.data[i].videoSrc,
              });
            }
          }
          console.log("currFormList", this.currFormList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 이미지 미리보기
    Preview_image() {
      this.imagePreview = URL.createObjectURL(this.image);
    },
    // Editor 컴포넌트에서 emit 으로 이벤트 전송 -> Editor의 content 데이터 받는 메서드
    getContent: function (editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },
    // 취소버튼
    moveBack: function () {
      if (confirm("입력하신 정보는 저장되지 않습니다. 정말 나가시겠습니까?")) {
        this.$router.go(-1);
      } else {
        return;
      }
    },
  },
};
</script>

<style scoped>
#lg_wrapper {
  width: 80%;
  margin: auto;
}

.lg_header {
  margin-top: 40px;
}

#contactForm h5 {
  margin-top: 20px;
  text-align: left;
}
.category {
  border-radius: 5px;
  margin-bottom: 15px;
}

.select_category {
  width: 100%;
  padding: 7px;
}

.curr_title {
  display: flex;
  justify-content: space-between;
}

.curr_title button {
  height: 40px;
  margin-top: 12px;
}
.curr {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.curr .curr_input {
  margin-top: 16px;
}

.curr .curr_file {
  width: 80%;
}

.curr #blank1 {
  width: 100px;
}

.select_skill {
  width: 100%;
  border-bottom: 1px solid lightgray;
}

.mt-3 {
  text-align: left;
}

.radio_levels {
  display: flex;
  justify-content: space-between;
}

.lecture_thumbnail .lecture_thumbnail_input {
  width: 300px;
}

.lecture_thumbnail {
  text-align: left;
}
.button_area button {
  margin: 20px;
}
</style>
