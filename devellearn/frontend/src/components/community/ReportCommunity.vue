<template>
  <div>
    <el-dialog
      :visible.sync="openPopup"
      width="30%"
      center
      :close-on-click-modal="false"
    >
      <v-card-title>
        <span class="text-h5">신고하기</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-select
                v-model="reason"
                :items="reasonList"
                label="신고사유"
                required
              ></v-select>
            </v-col>

            <v-container fluid>
              <v-textarea
                label="내용을 입력하세요"
                v-model="content"
                rows="3"
                row-height="10"
              ></v-textarea>
            </v-container>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="close()"> 취소 </v-btn>
        <v-btn color="green darken-1" text @click="reportItem()"> 확인 </v-btn>
      </v-card-actions>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    report: {},
  },
  data() {
    return {
      openPopup: false,
      editMode: false,
      content: "",
      reason: "",
      reasonList: [
        { value: "스팸홍보/도배글", text: "스팸홍보/도배글" },
        { value: "음란물", text: "음란물" },
        { value: "불법정보 포함", text: "불법정보 포함" },
        { value: "청소년에게 유해한 내용", text: "청소년에게 유해한 내용" },
        {
          value: "욕설,생명경시/혐오/차별적 표현",
          text: "욕설,생명경시/혐오/차별적 표현",
        },
        { value: "개인정보 노출 게시물", text: "개인정보 노출 게시물" },
        { value: "기타", text: "기타" },
      ],
    };
  },
  methods: {
    close() {
      this.content = "";
      this.reason = "";
      this.$emit("close");
    },
    reportItem() {
      this.report.content = this.content;
      this.report.reportCode = this.reason;
      this.report.memberId = this.$store.state.userStore.id;
      this.$axios
        .post("/api/report", this.report)
        .then((resp) => {
          console.log(resp);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "신고완료",
            showConfirmButton: false,
            timer: 1000,
          });
        })
        .catch((error) => {
          console.log(error);
        });
      this.$emit("close");
    },
  },
};
</script>
<style scoped>
.v-card__title {
  margin: 0 auto;
  padding: 0 auto;
}
el-dialog {
  margin: 0 auto;
  padding: 0 auto;
}
</style>
