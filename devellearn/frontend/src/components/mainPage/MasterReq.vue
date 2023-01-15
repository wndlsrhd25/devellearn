<template>
  <div>
    <form id="masterReq">
      <v-container fluid id="container">
        <v-row>
          <v-col>
            <v-card-title>개발고수 등록신청</v-card-title>
          </v-col>
        </v-row>
        <v-row>
          <v-card-title>나를 소개하는 글</v-card-title>
        </v-row>
        <v-row>
          <v-card-subtitle
            >고수님에 대한 소개와, 제작할 강의에 관련된 내용을 적어주세요.
            가능한 상세하게 적어주시면 구체적인 안내를 받을 수
            있습니다.</v-card-subtitle
          >
        </v-row>
        <v-row>
          <v-textarea
            rows="10"
            auto-grow
            no-resize
            solo
            name="content"
            label="내용을 입력해주세요"
          ></v-textarea>
        </v-row>
        <v-row>
          <v-col cols="2">
            <v-card-subtitle>수익금 반환 계좌 등록</v-card-subtitle>
          </v-col>
          <v-col cols="3">
            <v-select
              :items="banks"
              label="은행명"
              style="text-align: left"
              name="bank"
            ></v-select>
          </v-col>
          <v-col>
            <v-text-field
              v-model="account"
              name="account"
              label="계좌번호  - 제외하고 입력"
            ></v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="2">
            <v-card-subtitle>경력인증서 제출</v-card-subtitle>
          </v-col>
          <v-col>
            <v-file-input multiple label="파일첨부" name="files"></v-file-input>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="2">
            <v-btn color="blue" @click="$router.go(-1)"> 취소 </v-btn>
          </v-col>
          <v-spacer></v-spacer>
          <v-col>
            <v-btn color="blue" @click="submit()"> 신청하기 </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </form>
    <div data-app>
      <v-dialog v-model="dialog" max-width="290">
        <Complete @close="close()"></Complete>
      </v-dialog>
    </div>
  </div>
</template>
<script>
import Complete from "../common/Complete.vue";
export default {
  data: function () {
    return {
      dialog: false,
      account: "",
      rules: [(v) => v.replace(/[0-9]/g, "")],
      banks: [
        {value: "011", text: "제주은행"},
        {value: "012", text: "농협상호금융"},
        {value: "002", text: "산업은행"},
        {value: "003", text: "기업은행"},
        {value: "004", text: "국민은행"},
        {value: "081", text: "KEB하나은행"},
        {value: "020", text: "우리은행"},
        {value: "023", text: "SC제일은행"},
        {value: "027", text: "시티은행"},
        {value: "032", text: "대구은행"},
        {value: "034", text: "광주은행"},
        {value: "035", text: "제주은행"},
        {value: "037", text: "전북은행"},
        {value: "039", text: "경남은행"},
        {value: "045", text: "새마을금고"},
        {value: "088", text: "신한은행"},
        {value: "090", text: "카카오뱅크"},
      ],
    };
  },
  watch: {
    account() {
      this.account = this.account.replace(/[^0-9]/g, "");
    },
  },

  methods: {
    submit() {
      const formData = new FormData(document.getElementById("masterReq"));
      formData.append("memberId", this.$store.state.userStore.id);
      this.$axios
        .post("/api/masterReq", formData, {
          Headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((resp) => {
          console.log(resp);
          this.dialog = true;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    close() {
      this.dialog = false;
      this.$router.push({path: "/"});
    },
  },
  components: {Complete},
};
</script>
<style scoped>
#container {
  max-width: 1000px;
}
.v-text-field.v-text-field--solo .v-input__control {
  width: 80%;
}
</style>
