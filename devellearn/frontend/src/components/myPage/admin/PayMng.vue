<template>
  <div class="lectureList">
    <v-app id="inspire">
      <v-data-table
        :headers="headers"
        :items="payList"
        class="elevation-1"
        hide-default-footer
      >
        <template v-slot:item="{item}">
          <tr style="text-align: left">
            <td>{{ item.memberId }}</td>
            <td>{{ item.price }}</td>
            <td>{{ bankFilter(item.bank) }}</td>
            <td>{{ item.account }}</td>
            <td>{{ item.period }}</td>
            <td><v-icon @click="handlePay(item)">mdi-account-cash</v-icon></td>
          </tr>
        </template>
        <template v-slot:no-data> <h5>내역이 없습니다</h5> </template>
      </v-data-table>

      <v-pagination v-model="page" :length="pageCount"></v-pagination>
    </v-app>
  </div>
</template>
<script>
export default {
  data: () => ({
    headers: [
      {text: "아이디", value: "memberId"},
      {text: "금액", value: "price"},
      {text: "은행", value: "bank"},
      {text: "계좌번호", value: "account"},
      {text: "기간", value: "period"},
      {text: "처리하기"},
    ],
    payList: [],
    page: 1,
    pageCount: 1,
    param: {
      Header: {
        ApiNm: "ReceivedTransferOtherBank",
        Tsymd: "오늘날짜를입력하세요",
        Trtm: "112428",
        Iscd: "001610",
        FintechApsno: "001",
        ApiSvcCd: "ReceivedTransferA",
        IsTuno: "0000",
        AccessToken:
          "a0969e2df622b3974082c76154602576a990017b664caa0f5862aac13b473a0f",
      },
      Bncd: "",
      Acno: "",
      Tram: "",
      DractOtlt: "수익금반환",
      MractOtlt: "devellearn",
    },
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
    async getno() {
      await this.$axios
        .get("/api/getReturn")
        .then((resp) => {
          this.param.Header.IsTuno = resp.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async handlePay(item) {
      await this.getno();
      this.param.Header.Tsymd = this.getToday();
      this.param.Bncd = item.bank;
      this.param.Acno = item.account;
      this.param.Tram = item.price * 0.8;
      this.param.DractOtlt = "강의수익금 반환";
      this.param.MractOtlt = "devellearn 강의 수익";
      console.log(this.param);
      this.$axios
        .post(
          "https://developers.nonghyup.com/ReceivedTransferOtherBank.nh",
          this.param
        )
        .then((resp) => {
          console.log(resp.data.Header.Rsms);
          if (resp.data.Header.Rsms == "정상처리 되었습니다.") {
            this.insertHistory(item);
          } else {
            Swal.fire({
              position: "center",
              icon: "warning",
              title: "처리중 오류발생",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    insertHistory(item) {
      console.log(item);
      item.status = "정상";
      item.moneyNo = this.param.Header.IsTuno;
      this.$axios
        .put("/api/regReturn", item)
        .then((resp) => {
          console.log(resp.data);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "처리완료",
            showConfirmButton: false,
            timer: 1000,
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getList() {
      this.$axios
        .get("/api/getReturnList/" + this.page)
        .then((resp) => {
          this.payList = resp.data.list;
          this.pageCount = resp.data.pages;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getToday() {
      var date = new Date();
      var year = date.getFullYear();
      var month = new String(date.getMonth() + 1);
      var day = new String(date.getDate());

      // 한자리수일 경우 0을 채워준다.
      if (month.length == 1) {
        month = "0" + month;
      }
      if (day.length == 1) {
        day = "0" + day;
      }
      return year + month + day;
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
    bankFilter(bankCode) {
      if (bankCode == "011") {
        return "농협은행";
      } else if (bankCode == "012") {
        return "농협상호금융";
      } else if (bankCode == "002") {
        return "산업은행";
      } else if (bankCode == "003") {
        return "기업은행";
      } else if (bankCode == "004") {
        return "국민은행";
      } else if (bankCode == "081") {
        return "KEB하나은행";
      } else if (bankCode == "020") {
        return "우리은행";
      } else if (bankCode == "023") {
        return "SC제일은행";
      } else if (bankCode == "027") {
        return "시티은행";
      } else if (bankCode == "032") {
        return "대구은행";
      } else if (bankCode == "034") {
        return "광주은행";
      } else if (bankCode == "037") {
        return "전북은행";
      } else if (bankCode == "039") {
        return "경남은행";
      } else if (bankCode == "045") {
        return "새마을금고";
      } else if (bankCode == "088") {
        return "신한은행";
      } else if (bankCode == "090") {
        return "카카오뱅크";
      }
    },
  },
};
</script>
<style scoped></style>
