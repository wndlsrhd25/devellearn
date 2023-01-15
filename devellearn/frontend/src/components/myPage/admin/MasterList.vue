<template>
  <div>
    <div>
      <div class="reqList">
        <v-app id="inspire">
          <v-data-table :headers="headers" :items="reqList" class="elevation-1">
            <template v-slot:item="{item}">
              <tr>
                <td>{{ item.memberId }}</td>
                <td>{{ item.aDate }}</td>
                <td>
                  <a :href="'/api/files/req/' + item.fileName">{{
                    item.fileName
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
        </v-app>
      </div>
      <div data-app>
        <v-dialog v-model="dialog" max-width="600px">
          <MasterModal
            :req="req"
            @close="dialog = false"
            :key="mkey"
          ></MasterModal>
        </v-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import MasterModal from "./MasterModal.vue";
export default {
  data: () => ({
    headers: [
      {
        text: "신청자",
        align: "center",
        value: "memberId",
      },
      {text: "신청일", align: "center", value: "applyDate"},
      {text: "첨부파일", align: "center", value: "fileName"},
      {text: "상태", align: "center", value: "status"},
      {text: "처리하기", align: "center"},
    ],
    reqList: [],
    page: 1,
    pageCount: 1,
    dialog: false,
    req: {},
    mkey: 0,
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
        .get("/api/masterReq")
        .then((resp) => {
          this.reqList = resp.data;
          this.pageCount = resp.data;
          for (let item of this.reqList) {
            item.aDate = this.yyyyMMdd(item.applyDate);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showDetail(row) {
      console.log(row);
      this.req = row;
      this.dialog = true;
      this.mkey += 1;
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
  components: {MasterModal},
};
</script>
<style scoped></style>
