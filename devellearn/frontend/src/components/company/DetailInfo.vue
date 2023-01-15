<template>
  <div>
    <div class="summary">
      <table>
        <tr>
          <td><span class="sub"> 대표</span></td>
          <td>{{ company.ceo }}</td>
        </tr>
        <tr>
          <td>
            <span class="sub"> 매출 </span>
          </td>
          <td v-if="company.sales != 0">
            {{ company.sales }}
          </td>
          <td v-else>-</td>
        </tr>
        <tr>
          <td><span class="sub">주소</span></td>
          <td>{{ company.addr }}</td>
        </tr>
        <tr>
          <td><span class="sub">웹사이트</span></td>
          <td>{{ company.url }}</td>
        </tr>
        <tr>
          <td><span class="sub">직종</span></td>
          <td>{{ company.jobType }}</td>
        </tr>
        <tr>
          <td><span class="sub">소개</span></td>
          <td style="white-space: pre-wrap">{{ company.introduce }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      company: {},
      count: 1,
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    add() {
      this.count++;
    },
    getInfo() {
      this.$axios
        .post("/api/company", {
          comNo: this.$route.query.comNo,
          memberId: this.$store.state.userStore.id,
        })
        .then((resp) => {
          console.log(resp);
          this.company = resp.data;
          this.getavg();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getavg() {
      let joblist = this.company.jobType.split(",");
      console.log(joblist);
      this.$axios
        .post("/api/recruit/avg", {
          jobTypeList: joblist,
        })
        .then((resp) => {
          console.log(resp);
          this.avg = resp.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
<style scoped>
.sub {
  width: 100px;
}
table tr td:first-child {
  width: 100px;
  vertical-align: top;
}
table tr td:nth-child(2) {
  display: inline-block;
  max-width: 700px;
  vertical-align: top;
}

table {
  border-collapse: separate;
  border-spacing: 0 20px;
}
</style>
