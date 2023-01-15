<template lang="">
  <!-- Contact-->
  <div id="lg_wrapper">
    <!-- 약관동의 모달 -->
    <div>
      <b-modal id="modal_insertOath" show scrollable title="강의등록 약관">
        <p>1. Devellearn은 성장기회의 평등을 추구합니다.</p>
        <p class="my-4">
          우리는 때로 무언가를 배워야만 합니다. 하지만 여러 이유로 당연하다고
          생각되어 지는것들이 누군가에게는 사치가 되기도 합니다. Devellearn은
          누구나, 경제적으로 시간적 제약없이 내가 원하는 것을 배우고, 지식을
          나눌 수 있는 공간입니다.
        </p>
        <p>2. 전문 지식으로 수익이 가능한 유일한 곳.</p>
        <p class="my-4">
          Devellearn은 기술 강의, 멘토링으로 의미 있는 보상을 가질 수 있는
          유일한 플랫폼 입니다. 97만명의 수강생이 강의를 신청할 때마다 수익을
          얻을 수 있어요! 지속가능한 수익과 명예를 가져가세요 :)
        </p>
        <p>3. Devellearn은 70% ~ 100% 의 비율의 높은 수익을 제공합니다.</p>
        <p class="my-4">
          좋은 지식은 합당한 보상에서 나온다고 Devellearn은 생각합니다. 때문에
          Devellearn은 다른 학습 서비스에 비해 월등히 높은 수익을 드리고 있어요.
          실제로 Devellearn엔 꾸준히 월 수백 ~ 수천 만원 이상의 수익을 가져가는
          많은 개발고수들이 계셔요. 4. Devellearn의 강의는 개발고수가 자유롭게
          운영할 수 있습니다. 개발고수는 학생추가, 새소식 알림, 운영, 쿠폰 발행
          등으로 자신의 강의를 자유롭게 운영할 수 있습니다. 학습자들과 소식을
          공유하고 자유롭게 운영해 주세요.
        </p>
        <template #modal-footer="{ hide }">
          <b-button @click="hide('forget')" style="display: none"></b-button>
        </template>
        <div style="margin-bottom: 30px">
          <b-form-checkbox
            id="checkbox-1"
            v-model="status"
            name="checkbox-1"
            value="accepted"
            unchecked-value="not_accepted"
          >
            개발고수의 강의등록 약관에 동의합니다.
          </b-form-checkbox>
        </div>
        <div class="btn_group">
          <b-button @click="$bvModal.hide('modal_insertOath')">취소</b-button>
          <b-button
            @click="agreeOathAndRegist()"
            variant="primary"
            v-if="status == 'accepted'"
            >확인</b-button
          >
        </div>
      </b-modal>
    </div>

    <!-- 타이틀 헤더 -->
    <div class="lg_header">
      <h2 class="">강의 등록</h2>
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
            id="title"
          ></b-form-input>
        </div>

        <!-- 강의 소개 -->
        <div>
          <h5>강의 소개</h5>
          <Editor @editorEmit="getContent" id="content"></Editor>
        </div>

        <!-- 강의 카테고리-->
        <div class="category">
          <h5>강의 카테고리</h5>
          <b-form-select
            v-model="category"
            :options="categories"
            class="select_category"
            id="category"
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
          <div>
            <div class="curr">
              <b-form-input
                placeholder="커리큘럼 제목"
                readonly
                class="curr_input"
                id="curr"
              ></b-form-input>
              <div id="blank1"></div>
              <v-file-input
                placeholder="영상 업로드"
                class="curr_file"
                disabled
              />
            </div>
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
          <h5>기술스택(최대 5개)</h5>

          <b-form-tags
            input-id="tags-basic"
            v-model="skillList"
            remove-on-delete
            tag-pills
            tag-variant="primary"
            separator=" ,;"
            :limit="limit"
            id="skill"
          ></b-form-tags>
        </div>

        <!-- 강의 난이도 -->
        <!-- radio 태그 이용 -->
        <div>
          <h5>강의 난이도</h5>
          <b-form-radio-group
            v-model="difficulty"
            :options="levels"
            name="radio-inline"
            class="radio_levels"
            id="difficulty"
          ></b-form-radio-group>
        </div>

        <!-- 강의 가격 -->
        <div>
          <h5>강의 가격</h5>
          <b-form-input
            v-model="price"
            type="number"
            placeholder="가격을 입력하세요."
            id="price"
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
                  id="image"
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
          <b-button variant="primary" @click="insert">등록</b-button>
        </div>
      </form>
    </div>
    <!-- 입력 폼 끝 -->
  </div>
</template>

<script>
import Editor from "../quill-editor/Editor.vue";

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
      lectureNo: "",
      image: "",
      imagePreview: "",
      validation: false,
      status: "not_accepted",
      agreeStatus: false,
      durationArr: [],
    };
  },
  created() {
    this.getLectureNo();
  },
  methods: {
    showMo() {
      this.$bvModal.show("modal_insertOath");
    },
    // 글 미등록 유효성 검사
    checkValidation() {
      if (this.title == "") {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의제목은 필수 입력사항입니다.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#title").focus();
        return;
      }
      if (this.content == "") {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의 소개는 필수 입력사항입니다.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#content").focus();
        return;
      }
      if (this.category == null) {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의 카테고리는 필수 입력사항입니다.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#category").focus();
        return;
      }
      if (this.currFormList.length == 0) {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의 커리큘럼 1개 이상 입력하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#curr").focus();
        return;
      }
      if (this.skillList.length == 0) {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "기술스택 태그를 1개 이상 입력하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#skill").focus();
        return;
      }
      if (this.difficulty == null) {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의 난이도는 필수 입력사항입니다.",
          showConfirmButton: true,
          // timer: 1000
        });

        document.querySelector("#difficulty").focus();
        return;
      }
      if (this.price == "") {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의 가격은 필수 입력사항입니다.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#price").focus();
        return;
      }
      if (this.image == "") {
        ////////////////////////////////유효성 검사 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "강의 썸네일 사진을 첨부하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
        document.querySelector("#image").focus();
        return;
      }
      this.validation = true;
    },
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
    // 강의 등록 axios - form 데이터로
    async insert() {
      // 유효성 검사
      this.checkValidation();

      // 영상 길이 세팅
      this.setVideoDuration();
      console.log("this.durationArr", this.durationArr);

      if (this.validation) {
        // 약관 동의 모달
        this.$bvModal.show("modal_insertOath");
      }
    },
    // 등록약관 동의
    async agreeOathAndRegist() {
      let vm = this;
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
      // 강의등록
      this.$axios
        .post("/api/insertLetcure/", formData, {
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

      //커리큘럼 등록
      for (let i = 0; i < this.currFormList.length; i++) {
        // 스프링 서버로 전송할 formdata 객체 생성
        const formData = new FormData(document.getElementById("curr_data"));
        // formdata에 데이터 추가 - vo 칼럼 이름과 일치하게 이름 설정
        formData.append("lectureNo", this.lectureNo);
        formData.append("lectureTitle", this.title);
        formData.append("currName", this.currFormList[i].currName);
        formData.append("video", this.currFormList[i].currFile);
        // 영상길이 넣기
        formData.append("playTime", this.durationArr[i]);
        // 커리큘럼 등록 axios - formdata multipart 타입으로 전송
        await this.$axios
          .post("/api/insertCurr", formData, {
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
      this.$bvModal.hide("modal_insertOath");
      ////////////////////////////////글작성 완료 알림창
      Swal.fire({
        position: "center",
        icon: "success",
        title: "강의가 등록되었습니다.",
        showConfirmButton: true,
        // timer: 1000
      }).then(function () {
        vm.$router
          .push({
            path: "/lectureDetail/intro",
            query: { no: vm.lectureNo },
          })
          .catch(() => {});
      });
    },

    getVideoDuration(currVideoFile) {
      let vm = this;
      // video 태그 생성
      let video = document.createElement("video");
      // video의 preload 속성에 'metadata'를 추가 --> video 파일의 메타데이터(약간의 정보만) 가져옴
      video.preload = "metadata";
      // video의 src 속성에 URL.createObjectURL메서드로 'url'을 추가
      // URL.createObjectURL : 특정 파일 객체나 로컬 파일 또는 데이터의 참조를 가리키는 새로운 객체 URL을 생성
      video.src = window.URL.createObjectURL(currVideoFile);
      // video가 로드시 발생하는 이벤트
      video.onloadedmetadata = function () {
        // URL.revokeObjectURL : URL.createObjectURL() 호출로 부터 생성된 object URL을 해제
        // --> 불필요한 메모리 누수 방지
        window.URL.revokeObjectURL(video.src);
        console.log("video src", video.src);
        // 영상길이 저장 배열
        vm.durationArr.push(Math.floor(video.duration));
      };
    },

    // duration 세팅
    async setVideoDuration() {
      for (let i = 0; i < this.currFormList.length; i++) {
        this.getVideoDuration(this.currFormList[i].currFile);
      }
      console.log("vm.durationArr", vm.durationArr);
    },

    // 강의 PK 조회 --> 강의랑 커리큘럼이랑 동일하게 강의PK를 넣어주기 위함
    getLectureNo() {
      this.$axios
        .get("/api/lectureNo")
        .then((resp) => {
          if (resp.data != null) {
            this.lectureNo = resp.data;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    Preview_image() {
      this.imagePreview = URL.createObjectURL(this.image);
    },
    // Editor 컴포넌트에서 emit 으로 이벤트 전송 -> Editor의 content 데이터 받는 메서드
    getContent: function (editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },
    moveBack: function () {
      let vm = this;
      ////////////////////////////////////////////////// 알림창
      this.$swal
        .fire({
          title: "입력하신 정보는 저장되지 않습니다.",
          text: " 정말 나가시겠습니까??",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            vm.$router.go(-1);
          }
        });
    },
  },
};
</script>

<style scoped>
#lg_wrapper {
  width: 50%;
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

/* 약관동의 모달 */
#modal_insertOath p:nth-child(2n - 1) {
  font-weight: bolder;
  font-size: larger;
  margin: 0px;
}

#modal_insertOath p {
  margin-top: 10px !important;
}

.btn_group {
  display: flex;
  justify-content: space-evenly;
}
</style>
