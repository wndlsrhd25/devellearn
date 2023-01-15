<template>
  <div id="regist">
    <form id="regData">
      <b-input-group>
        <template #prepend>
          <b-input-group-text
            ><span style="font-weight: bold">제목</span></b-input-group-text
          >
        </template>
        <b-form-input
          placeholder="ex) 프론트엔드 개발자 모집"
          v-model="data.title"
          name="title"
        ></b-form-input>
      </b-input-group>
      <b-input-group>
        <template #prepend>
          <b-input-group-text>직무</b-input-group-text>
        </template>
        <b-form-checkbox-group
          v-model="data.jobType"
          :options="jList"
          name="jobType"
        ></b-form-checkbox-group>
      </b-input-group>
      <b-input-group>
        <template #prepend>
          <b-input-group-text>경력</b-input-group-text>
        </template>
        <b-form-select
          name="career"
          v-model="data.career"
          :options="cList"
        ></b-form-select>
      </b-input-group>
      <b-input-group>
        <template #prepend>
          <b-input-group-text>고용형태</b-input-group-text>
        </template>

        <b-form-select
          name="empType"
          v-model="data.empType"
          :options="eList"
        ></b-form-select>
      </b-input-group>
      <b-input-group>
        <template #prepend>
          <b-input-group-text>급여</b-input-group-text>
        </template>
        <b-form-input name="salary" v-model="data.salary"></b-form-input>
      </b-input-group>
      <b-input-group>
        <template #prepend>
          <b-input-group-text>스킬</b-input-group-text>
        </template>
        <b-form-tags input-id="tags-basic" v-model="data.skill"></b-form-tags>
      </b-input-group>
      <b-input-group>
        <template #prepend>
          <b-input-group-text>마감일</b-input-group-text>
        </template>
        <b-form-input
          type="date"
          v-model="data.endDate"
          name="endDate"
        ></b-form-input>
        *(미입력시 마감기한없음으로 등록)
      </b-input-group>
      사진등록

      <v-flex xs8>
        <v-file-input
          v-model="image"
          name="image"
          accept=".png, .jpeg, .jpg"
          placeholder="사진등록"
          prepend-icon="mdi-camera"
          @click:clear="image = ''"
          @change="Preview_image()"
        />
      </v-flex>
      <v-avatar size="200" rounded="0" style="background-color: gray">
        <template v-if="image == null || image == ''">
          <v-img :src="'/api/images/company/' + data.img" />
        </template>
        <v-img :src="data.img" v-else />
      </v-avatar>

      <br /><br />
      <h4>기업소개</h4>
      <b-form-textarea
        class="text"
        name="introduce"
        v-model="data.introduce"
        placeholder="내용을 입력해주세요"
        rows="6"
        max-rows="6"
        style="resize: none"
      ></b-form-textarea>
      <h4>주요업무</h4>
      <b-form-textarea
        class="text"
        name="jobDetail"
        v-model="data.jobDetail"
        placeholder="내용을 입력해주세요"
        rows="6"
        max-rows="6"
        style="resize: none"
      ></b-form-textarea>
      <h4>우대사항</h4>
      <b-form-textarea
        class="text"
        name="advantage"
        v-model="data.advantage"
        placeholder="내용을 입력해주세요"
        rows="6"
        max-rows="6"
        style="resize: none"
      ></b-form-textarea>
      <h4>기타</h4>
      <b-form-textarea
        name="etc"
        class="text"
        v-model="data.etc"
        placeholder="내용을 입력해주세요"
        rows="6"
        max-rows="6"
        style="resize: none"
      ></b-form-textarea>
      <h4>문의처</h4>
      <b-form-textarea
        class="text"
        name="contact"
        v-model="data.contact"
        placeholder="내용을 입력해주세요"
        rows="3"
        max-rows="3"
        style="resize: none"
      ></b-form-textarea>
      <div style="display: flex; justify-content: space-between">
        <b-button @click="$bvModal.hide('updateRecruit')">취소</b-button>
        <b-button @click="registRecruit()">등록</b-button>
      </div>
      <v-dialog v-model="emptyDialog" width="450px">
        <EmptyContent @close="emptyDialog = false"></EmptyContent>
      </v-dialog>
    </form>
  </div>
</template>
<script>
import EmptyContent from "../common/EmptyContent.vue";
export default {
  components: { EmptyContent },
  data: function () {
    return {
      data: {},
      text: "",
      eList: [
        { text: "정규직", value: "정규직" },
        { text: "계약직", value: "계약직" },
        { text: "프리랜서", value: "프리랜서" },
      ],
      jList: [
        { text: "DBA", value: "DBA" },
        { text: "ERP", value: "ERP" },
        { text: "QA", value: "QA" },
        { text: "게임개발", value: "게임개발" },
        { text: "네트워크/보안/운영", value: "네트워크/보안/운영" },
        { text: "데이터분석", value: "데이터분석" },
        { text: "모바일앱개발", value: "모바일앱개발" },
        { text: "소프트웨어아키텍트", value: "소프트웨어아키텍트" },
        { text: "소프트웨어엔지니어", value: "소프트웨어엔지니어" },
        { text: "시스템엔지니어", value: "시스템엔지니어" },
        { text: "웹개발", value: "웹개발" },
        { text: "프로젝트매니저", value: "프로젝트매니저" },
        { text: "웹퍼블리셔", value: "웹퍼블리셔" },
        { text: "하드웨어엔지니어", value: "하드웨어엔지니어" },
      ],
      cList: [
        { text: "신입", value: "신입" },
        { text: "주니어(1~3년)", value: "주니어(1~3년)" },
        { text: "미들(4~8년)", value: "미들(4~8년)" },
        { text: "시니어(9년 이상)", value: "시니어(9년 이상)" },
      ],

      image: [],
      isUpdate: true,
      imgName: "",
      emptyDialog: false,
    };
  },
  props: {
    recruit: {},
  },
  created() {
    if (this.recruit != null) {
      this.data = Object.assign({}, this.recruit);

      this.imgName = this.data.img;

      const skills = this.recruit.skill.split(",");
      this.data.skill = skills;
      console.log(this.data.skill);

      const jobType = this.recruit.jobType.split(",");
      this.data.jobType = jobType;
      console.log(this.data.jobType);

      if (this.recruit.endDate != null && this.recruit.endDate != "") {
        this.data.endDate = this.yyyyMMdd(this.recruit.endDate);
      }
    } else {
      this.isUpdate = false;
    }
  },
  methods: {
    async registRecruit() {
      // 인풋박스 전체를 form으로 감싸줌(action 이런거 다빼고 id만 부여해줌)
      //  const formData = new FormData(document.getElementById("여기에 id 부여한거 적어줌"));
      //  formData.append("skill", skills); 이거는 데이터를 input에 없는거 추가해줘야하면 이렇게
      // axios로 스프링으로 데이터 보낼때 이렇게
      // .post("/api/recruit", formData, {
      //   Headers: {
      //         "Content-Type": "multipart/form-data",
      //       },
      //     })

      const formData = new FormData(document.getElementById("regData"));
      let skills = "";
      for (let i = 0; i < this.data.skill.length; i++) {
        skills += this.data.skill[i] + ", ";
      }
      skills = skills.substring(0, skills.length - 2);

      formData.append("img", this.imgName);
      formData.append("skill", skills);
      formData.append(
        "comNo",
        this.$store.getters["userStore/getUserData"].comNo
      );
      formData.append(
        "name",
        this.$store.getters["userStore/getUserData"].name
      );

      if (!this.isUpdate) {
        await this.$axios
          .post("/api/recruit", formData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then(async (resp) => {
            console.log(resp);
            this.$emit("reload");
            this.$bvModal.hide("updateRecruit");
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        formData.append("recruitNo", this.data.recruitNo);
        await this.$axios
          .put("/api/recruit", formData, {
            Headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((resp) => {
            console.log(resp);
            this.$emit("reload");
            this.$bvModal.hide("updateRecruit");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    Preview_image() {
      this.data.img = URL.createObjectURL(this.image);
    },

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
      return year + "-" + month + "-" + day;
    },
  },
};
</script>
<style scoped>
.btn-line {
  display: flex;
  justify-content: space-between;
}
.btn-primary {
  background-color: rgb(52, 152, 219);
  border-color: rgb(52, 152, 219);
}
#regist {
  width: 80%;
  margin: 0 auto;
  text-align: left;
}
.input-group-text {
  border: none;
  font-size: large;
  width: 90px;
  text-align: center;
  background-color: white;
}
.input-group {
  margin: 5px;
}
.text {
  margin-bottom: 20px;
}
.input-group
  > :not(:first-child):not(.dropdown-menu):not(.valid-tooltip):not(.valid-feedback):not(.invalid-tooltip):not(.invalid-feedback) {
  border-radius: 0.375rem;
}
</style>
