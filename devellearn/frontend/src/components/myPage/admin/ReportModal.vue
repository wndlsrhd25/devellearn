<template>
  <div>
    <form id="companyData">
      <v-card>
        <v-card-title>
          <span class="text-h5">신고처리</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <b-input-group>
              <template #prepend>
                <b-input-group-text
                  >신고사유 : {{ report.reportCode }}</b-input-group-text
                >
              </template>
            </b-input-group>
            <b-input-group>
              <template #prepend>
                <b-input-group-text>신고게시물 내용</b-input-group-text>
              </template>
            </b-input-group>
            <br />

            <v-row>
              <v-textarea
                outlined
                auto-grow
                no-resize
                name="content"
                :value="content"
                style="white-space: pre-wrap"
                readonly
              ></v-textarea>
            </v-row>
            <b-input-group>
              <template #prepend>
                <b-input-group-text>처리하기</b-input-group-text>
              </template>
              <b-form-select
                v-model="report.result"
                :options="resultList"
                name="jobType"
              ></b-form-select>
            </b-input-group>
            <v-card-actions>
              <v-btn color="blue darken-1" text @click="close()"> 닫기 </v-btn>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="edit()"> 완료 </v-btn>
            </v-card-actions>
          </v-container>
        </v-card-text>
      </v-card>
    </form>
  </div>
</template>
<script>
export default {
  props: ["report"],
  data: () => ({
    dialog: false,
    content: "",
    resultList: [
      {value: "삭제(스팸홍보/도배글)", text: "삭제(스팸홍보/도배글)"},
      {value: "삭제(음란물)", text: "삭제(음란물)"},
      {value: "삭제(불법정보 포함)", text: "삭제(불법정보 포함)"},
      {
        value: "삭제(청소년에게 유해한 내용)",
        text: "삭제(청소년에게 유해한 내용)",
      },
      {
        value: "삭제(욕설,생명경시/혐오/차별적 표현)",
        text: "삭제(욕설,생명경시/혐오/차별적 표현)",
      },
      {value: "삭제(개인정보 노출 게시물)", text: "삭제(개인정보 노출 게시물)"},

      {text: "문제없음", value: "문제없음"},
    ],
  }),
  watch: {
    report() {
      this.getContent();
    },
  },
  created() {
    this.getContent();
  },
  methods: {
    getContent() {
      this.$axios
        .post("/api/reportContent", {
          postCode: this.report.postCode,
          postNo: this.report.postNo,
          colname: this.report.colname,
        })
        .then((resp) => {
          this.content = resp.data.content;
          console.log(this.content);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    close() {
      this.$emit("close");
    },
    edit() {
      console.log(this.report);
      // 처리결과등록 -> 글 삭제
      this.$axios
        .put("/api/report", this.report)
        .then((resp) => {
          console.log(resp.data);
          this.$emit("reload");
        })
        .catch((error) => {
          console.log(error);
        });
      if (this.report.result != "문제없음") {
        // 글삭제
        this.$axios
          .delete(
            "/api/report/" +
              this.report.colname +
              "/" +
              this.report.postCode +
              "/" +
              this.report.postNo
          )
          .then((resp) => {
            console.log(resp.data);
            this.$emit("close");
            this.$emit("reload");
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.$emit("close");
      }
      Swal.fire({
        position: "center",
        icon: "success",
        title: "처리완료",
        showConfirmButton: false,
        timer: 1000,
      });
    },
  },
};
</script>
<style scoped>
h5 {
  text-align: left;
  margin: 20px 0;
}
.input-group {
  margin-bottom: 10px;
}
.input-group-text {
  border: none;
  font-size: large;
  padding: 0;
  padding-right: 10px;
  text-align: center;
  background-color: white;
}
</style>
