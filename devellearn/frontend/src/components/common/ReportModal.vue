<template>
  <div>
    <b-row>
      <b-col sm="2">
        <label for="textarea-small">사유</label>
      </b-col>
      <b-col sm="10">
        <b-form-select v-model="reason" :options="reasonList"></b-form-select>
      </b-col>
    </b-row>

    <b-row>
      <b-col sm="2">
        <label for="textarea-small">내용</label>
      </b-col>
      <b-col sm="10">
        <b-form-textarea
          id="textarea-small"
          size="sm"
          v-model="content"
        ></b-form-textarea>
      </b-col>
    </b-row>
    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('report')">취소</b-button>
      <b-button @click="reportItem()">신고</b-button>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    report: {},
  },
  data() {
    return {
      content: "",
      reason: "",
      reasonList: [
        {value: "욕설/비방", text: "욕설/비방"},
        {value: "도배글", text: "도배글"},
        {value: "광고", text: "광고"},
        {value: "맘에안듬", text: "맘에안듬"},
      ],
    };
  },
  methods: {
    reportItem() {
      this.report.content = this.content;
      this.report.reportCode = this.reason;
      this.report.memberId = this.$store.state.userStore.id;
      this.$axios
        .post("/api/report", this.report)
        .then((resp) => {
          console.log(resp);
        })
        .catch((error) => {
          console.log(error);
        });
      this.$bvModal.hide("report");
    },
  },
};
</script>
<style scoped></style>
