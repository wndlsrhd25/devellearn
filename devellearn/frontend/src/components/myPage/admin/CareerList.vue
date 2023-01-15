<template>
  <div>
    <div>
      <div class="reqList">
        <v-app id="inspire">
          <v-data-table
            hide-default-footer
            :headers="headers"
            :items="careerList"
            class="elevation-1"
          >
            <template v-slot:item="{item}">
              <tr style="text-align: left">
                <td>{{ item.memberId }}</td>
                <td>{{ item.company }}</td>

                <td>{{ item.jobPeriod }}</td>
                <td>{{ item.aDate }}</td>
                <td>
                  <a
                    :href="
                      '/api/files/resume/' +
                      item.certiFile
                    "
                    >{{ item.certiFile }}</a
                  >
                </td>
                <td>{{ item.status }}</td>
                <td>
                  <v-icon @click="showDetail(item)"
                    >mdi-file-document-outline</v-icon
                  >
                </td>
              </tr>
            </template>
            <template v-slot:no-data> <h5>신청내역이 없습니다</h5> </template>
          </v-data-table>
          <v-pagination v-model="page" :length="pageCount"></v-pagination>
        </v-app>
      </div>
      <div data-app>
        <v-dialog v-model="dialog" max-width="600px">
          <CareerModal :career="career" @close="dialog = false"></CareerModal>
        </v-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import CareerModal from "./CareerModal.vue";
export default {
  data: () => ({
    headers: [
      {
        text: "신청자",
        align: "start",
        value: "memberId",
      },
      {text: "회사명", value: "company"},
      {text: "근무기간", value: "jobPeriod"},
      {text: "신청일", value: "applyDate"},
      {text: "첨부파일", value: "fileName"},
      {text: "상태", value: "status"},
      {text: "처리하기", value: ""},
    ],
    careerList: [],
    page: 1,
    pageCount: 1,
    dialog: false,
    career: {},
  }),
  watch: {
    page() {
      this.getList();
    },
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.$axios
        .get("/api/careerList/" + this.page)
        .then((resp) => {
          this.careerList = resp.data.list;
          this.pageCount = resp.data.pages;
          for (let item of this.careerList) {
            item.aDate = this.yyyyMMdd(item.applyDate);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showDetail(row) {
      console.log(row);
      this.career = row;
      this.dialog = true;
    },
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
      return year + "/" + month + "/" + day;
    },
  },
  components: {CareerModal},
};
</script>
<style scoped></style>
