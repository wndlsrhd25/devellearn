<template>
  <div id="ml_wrapper">
    <!-- 헤더 -->
    <div class="ml_header">
      <h4>지원내역</h4>
    </div>

    <div class="applyList">
      <v-app id="inspire">
        <v-data-table :headers="headers" :items="applyList" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-dialog v-model="dialogDelete" max-width="400px">
                <v-card>
                  <v-card-title class="text-h5"
                    >지원취소하시겠습니까?</v-card-title
                  >
                  <v-card-actions>
                    <v-btn color="blue darken-1" text @click="closeDelete"
                      >닫기</v-btn
                    >
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="withdrawalConfirm"
                      >지원취소</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{item}">
            <v-icon size="30" @click="withdrawal(item)">
              mdi-text-box-remove-outline
            </v-icon>
          </template>
          <template v-slot:item.read="{item}">
            <v-icon size="30" @click="openItem(item)">
              mdi-text-box-search-outline
            </v-icon>
          </template>
          <template v-slot:no-data> <h5>지원내역이 없습니다</h5> </template>
        </v-data-table>
      </v-app>
    </div>
  </div>
</template>
<!-- text-box-search-outline -->
<script>
export default {
  data: () => ({
    dialogDelete: false,
    headers: [
      {
        text: "기업명",
        align: "start",
        value: "name",
      },
      {text: "제목", value: "title", sortable: false},
      {text: "마감기한", value: "endDate"},
      {text: "지원날짜", value: "applyDate"},
      {text: "상태", value: "status"},
      {text: "확인", value: "isRead", align: "center"},
      {text: "이력서보기", value: "read", sortable: false, align: "center"},
      {text: "지원취소", value: "actions", sortable: false, align: "center"},
    ],
    applyList: [],
    editedIndex: -1,
    editedItem: {},
    defaultItem: {},
  }),
  computed: {},
  watch: {
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.$axios
        .get("/api/applyList/" + this.$store.state.userStore.id)
        .then((resp) => {
          console.log(resp.data);
          this.applyList = resp.data;
          for (let item of this.applyList) {
            if (item.endDate == null) {
              item.endDate = "마감기한없음";
            } else {
              item.endDate = this.yyyyMMdd(item.endDate);
            }
          }
          for (let item of this.applyList) {
            item.applyDate = this.yyyyMMdd(item.applyDate);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    withdrawal(item) {
      console.log(item);
      this.editedIndex = this.applyList.indexOf(item);
      this.editedItem = item;
      this.dialogDelete = true;
    },
    withdrawalConfirm() {
      this.$axios
        .post("/api/applyList", {
          historyNo: this.editedItem.historyNo,
        })
        .then((resp) => {
          console.log(resp.data);
          this.applyList[this.editedIndex].status = "취소";
        })
        .catch((error) => {
          console.log(error);
        });
      this.closeDelete();
    },
    closeDelete() {
      this.dialogDelete = false;
    },
    openItem(item) {
      location.href = "/api/files/resume/" + item.resume;
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
      return year + "/" + month + "/" + day;
    },
  },
};
</script>

<style scoped>
.v-data-table > .v-data-table__wrapper > table > thead > tr > th {
  font-weight: bold;
  font-size: large;
}
.ml_header {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid lightgray;
}

.ml_header h4 {
  line-height: 20 0%;
}
.applyList {
  padding: 20px;
}
</style>
