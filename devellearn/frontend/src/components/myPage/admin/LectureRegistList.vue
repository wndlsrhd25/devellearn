<template>
  <div>
    <div>
      <div class="lectureList">
        <v-app id="inspire">
          <v-data-table
            :headers="headers"
            :items="lectureList"
            class="elevation-1"
            hide-default-footer
          >
            <template v-slot:item="{item}">
              <tr @click="showDetail(item)" style="text-align: left">
                <td>{{ item.title }}</td>
                <td>{{ item.category }}</td>
                <td>{{ item.skillTag }}</td>
                <td>{{ item.regDate }}</td>
                <td>{{ item.status }}</td>
              </tr>
            </template>
            <template v-slot:no-data> <h5>신청내역이 없습니다</h5> </template>
          </v-data-table>

          <v-pagination v-model="page" :length="pageCount"></v-pagination>
        </v-app>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    headers: [
      {
        text: "강의명",
        align: "start",
        value: "title",
      },
      {text: "카테고리", value: "category"},
      {text: "스킬태그", value: "skillTag"},
      {text: "등록일", value: "endDate"},
      {text: "상태", value: "status"},
    ],
    lectureList: [],
    page: 1,
    pageCount: 1,
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
        .get("/api/lecture/unapproved/" + this.page)
        .then((resp) => {
          this.lectureList = resp.data.list;
          this.pageCount = resp.data.pages;
          for (let item of this.lectureList) {
            item.regDate = this.yyyyMMdd(item.regDate);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showDetail(row) {
      console.log(row);
      this.$router.push({
        path: "/adminPage/lectureMng/lectureInfo",
        query: {lectureNo: row.lectureNo},
      });
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
};
</script>
<style scoped></style>
