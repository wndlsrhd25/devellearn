<template>
  <div>
    <div>
      <div class="reqList">
        <v-app id="inspire">
          <v-data-table
            hide-default-footer
            :headers="headers"
            :items="certiList"
            class="elevation-1"
          >
            <template v-slot:item="{item}">
              <tr style="text-align: left">
                <td>{{ item.memberId }}</td>
                <td>{{ item.certiName }}</td>
                <td>{{ item.acqDate }}</td>

                <td>{{ item.aDate }}</td>
                <td>
                  <a :href="'/api/files/resume/' + item.certiFile">{{
                    item.certiFile
                  }}</a>
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
          <CertiModal :certi="certi" @close="dialog = false"></CertiModal>
        </v-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import CertiModal from "./CertiModal.vue";
export default {
  data: () => ({
    headers: [
      {
        text: "신청자",
        align: "start",
        value: "memberId",
      },
      {text: "자격", value: "certiName"},
      {text: "취득일", value: "acqDate"},
      {text: "신청일", value: "applyDate"},
      {text: "첨부파일", value: "fileName"},
      {text: "상태", value: "status"},
      {text: "처리하기", value: ""},
    ],
    certiList: [],
    page: 1,
    pageCount: 1,
    dialog: false,
    certi: {},
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
        .get("/api/certiList/" + this.page)
        .then((resp) => {
          this.certiList = resp.data.list;
          this.pageCount = resp.data.pages;
          for (let item of this.certiList) {
            item.aDate = this.yyyyMMdd(item.applyDate);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showDetail(row) {
      console.log(row);
      this.certi = row;
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
  components: {CertiModal},
};
</script>
<style scoped></style>
