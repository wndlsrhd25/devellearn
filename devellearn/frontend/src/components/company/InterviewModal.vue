<template>
  <div id="regist">
    <b-input-group>
      <template #prepend>
        <b-input-group-text>면접난이도</b-input-group-text>
      </template>
      <b-form-rating
        v-model="data.difficulty"
        variant="warning"
        class="mb-2"
      ></b-form-rating>
    </b-input-group>

    <b-input-group>
      <template #prepend>
        <b-input-group-text>면접분위기</b-input-group-text>
      </template>
      <b-form-select v-model="data.atmosphere" :options="atmos"></b-form-select>
    </b-input-group>

    <b-input-group>
      <template #prepend>
        <b-input-group-text>면접결과</b-input-group-text>
      </template>
      <b-form-select
        v-model="data.result"
        :options="resultList"
      ></b-form-select>
    </b-input-group>

    <br />
    <h5>면접후기</h5>
    <b-form-textarea
      class="text"
      id="textarea"
      v-model="data.content"
      placeholder="내용을 입력해주세요"
      rows="6"
      max-rows="6"
      style="resize: none"
    ></b-form-textarea>
    <b-input-group>
      <template #prepend>
        <b-input-group-text>질문입력</b-input-group-text>
      </template>
      <b-form-input v-model="question" @keydown.enter="append()"></b-form-input>
      <template #append>
        <b-input-group-text class="x"
          ><span @click="append()">+</span></b-input-group-text
        >
      </template>
    </b-input-group>
    <b-input-group v-for="(item, index) in qList" :key="index">
      <template #prepend>
        <b-input-group-text>질문{{ index + 1 }}.</b-input-group-text>
      </template>
      <b-form-input :value="item.trim()"></b-form-input>
      <template #append>
        <b-input-group-text class="x"
          ><span @click="deleteQ(index)">x</span></b-input-group-text
        >
      </template>
    </b-input-group>

    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('registReview')">취소</b-button>
      <b-button @click="registRecruit()" color="primary">등록</b-button>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      data: {
        questionList: [],
      },
      text: "",
      atmos: [
        { text: "긍정적", value: "긍정적" },
        { text: "보통", value: "보통" },
        { text: "부정적", value: "부정적" },
      ],
      resultList: [
        { text: "합격", value: "합격" },
        { text: "대기중", value: "대기중" },
        { text: "불합격", value: "불합격" },
      ],
      qList: [],
      question: "",
    };
  },
  props: {
    review: {},
    comNo: "",
    isUpdate: "",
  },
  mounted() {
    console.log(this.review);
    if (this.review.questionList != null) {
      this.data = Object.assign({}, this.review);
      this.qList = this.data.questionList;
    } else {
      this.isUpdate = false;
    }
    this.data.writer = this.$store.state.userStore.id;
    this.data.comNo = this.comNo;
  },
  methods: {
    deleteQ(index) {
      console.log(index);
      this.qList.splice(index, 1);
    },
    append() {
      console.log(this.question);
      if (this.question != "") {
        // if (this.data.questionList == null) {
        //   let list = [];
        //   list.push(this.question);
        //   this.data.questionList = list;
        //   this.question = "";
        //   return;
        // }
        // this.data.questionList.push(this.question);
        this.qList.push(this.question);
        this.question = "";
      }
    },
    registRecruit() {
      if (this.qList.length == 0) {
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "질문을 입력해주세요.",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }
      this.data.comNo = this.comNo;
      let str = "";
      for (let qu of this.qList) {
        console.log(qu);
        str += qu + ",";
      }
      str = str.substring(0, str.length - 1);
      this.data.question = str;
      if (!this.isUpdate) {
        this.$axios
          .post("/api/interview", this.data)
          .then((resp) => {
            console.log(resp);
            this.$bvModal.hide("registReview");
            this.$emit("reloadList");
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.$axios
          .put("/api/interview", this.data)
          .then((resp) => {
            console.log(resp);
            this.$bvModal.hide("registReview");
            this.$emit("reloadList");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>
<style scoped>
.x {
  max-width: 20px;
  display: inline-block;
  cursor: pointer;
}
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
  width: 120px;
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
