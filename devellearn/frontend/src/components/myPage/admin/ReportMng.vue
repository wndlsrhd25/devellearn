<template>
  <div>
    <div>
      <div class="reportList">
        <v-app id="inspire">
          <v-data-table
            :headers="headers"
            :items="reportList"
            class="elevation-1"
            hide-default-footer
          >
            <template v-slot:item="{item}">
              <tr @click="showDetail(item)" style="text-align: left">
                <td>{{ item.memberId }}</td>
                <td>{{ item.reportCode }}</td>
                <td>{{ item.content }}</td>
                <td>{{ item.category }}</td>
              </tr>
            </template>
            <template v-slot:no-data> <h5>신청내역이 없습니다</h5> </template>
          </v-data-table>

          <v-pagination v-model="page" :length="pageCount"></v-pagination>
        </v-app>
      </div>
    </div>
    <div data-app>
      <v-dialog v-model="dialog" max-width="600px">
        <ReportModal
          @close="dialog = false"
          @reload="getList()"
          :report="report"
        ></ReportModal>
      </v-dialog>
    </div>
  </div>
</template>
<script>
import ReportModal from "./ReportModal.vue";
export default {
  data: () => ({
    dialog: false,
    headers: [
      {
        text: "신고자",
        align: "start",
        value: "memberId",
      },
      {text: "신고사유", value: "reportCode"},
      {text: "내용", value: "content"},
      {text: "카테고리", value: "postCode"},
    ],
    reportList: [],
    page: 1,
    pageCount: 1,
    report: {},
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
        .get("/api/report/" + this.page)
        .then((resp) => {
          this.reportList = resp.data.list;
          this.pageCount = resp.data.pages;
          for (let item of this.reportList) {
            if (item.postCode == "com_reviews") {
              item.category = "기업리뷰";
              item.colname = "review_no";
            } else if (item.postCode == "lecture_review") {
              item.category = "수강평";
              item.colname = "review_no";
            } else if (item.postCode == "boards") {
              item.category = "게시판";
              item.colname = "board_no";
            } else if (item.postCode == "mentors") {
              item.category = "멘토링";
              item.colname = "mentor_no";
            } else if (item.postCode == "projects") {
              item.category = "프로젝트";
              item.colname = "project_no";
            } else if (item.postCode == "studys") {
              item.category = "스터디";
              item.colname = "study_no";
            } else if (item.postCode == "blog_post") {
              item.category = "블로그";
              item.colname = "post_no";
            } else if (item.postCode == "interview_review") {
              item.category = "면접후기";
              item.colname = "review_no";
            } else if (item.postCode == "qnas") {
              item.category = "지식QnA";
              item.colname = "qna_no";
            } else if (item.postCode == "blog_comments") {
              item.category = "블로그댓글";
              item.colname = "comment_no";
            } else if (item.postCode == "interview_reviews") {
              item.category = "면접후기";
              item.colname = "review_no";
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showDetail(row) {
      this.report = {...row};
      console.log(row);
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
  components: {ReportModal},
};
</script>
<style scoped></style>
