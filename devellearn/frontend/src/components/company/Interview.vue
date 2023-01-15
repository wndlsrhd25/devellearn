<template>
  <div>
    <div id="stat">
      <div class="diff">
        <h5>면접난이도</h5>
        <h3>{{ diff }}</h3>
        <b-form-input
          id="diff"
          :value="diff"
          type="range"
          min="0"
          max="5"
          step="0.1"
          readonly="readonly"
          disabled
        ></b-form-input>
      </div>
      <div class="atmos">
        <h5>면접분위기</h5>
        <table>
          <tr>
            <td>
              <b-icon-circle-fill
                font-scale="0.8"
                variant="success"
              ></b-icon-circle-fill>
            </td>
            <td>긍정적</td>
            <td>{{ positive }}%</td>
          </tr>
          <tr>
            <td>
              <b-icon-circle-fill
                font-scale="0.8"
                variant="info"
              ></b-icon-circle-fill>
            </td>
            <td>보통</td>
            <td>{{ neutral }}%</td>
          </tr>
          <tr>
            <td>
              <b-icon-circle-fill
                font-scale="0.8"
                variant="danger"
              ></b-icon-circle-fill>
            </td>
            <td>부정적</td>
            <td>{{ negative }}%</td>
          </tr>
        </table>
      </div>
      <div class="result">
        <h5>면접결과</h5>
        <table>
          <tr>
            <td>
              <b-icon-circle-fill
                font-scale="0.8"
                variant="success"
              ></b-icon-circle-fill>
            </td>
            <td>합격</td>
            <td>{{ pass }}%</td>
          </tr>
          <tr>
            <td>
              <b-icon-circle-fill
                font-scale="0.8"
                variant="info"
              ></b-icon-circle-fill>
            </td>
            <td>대기중</td>
            <td>{{ 100 - pass - fail }}%</td>
          </tr>
          <tr>
            <td>
              <b-icon-circle-fill
                font-scale="0.8"
                variant="danger"
              ></b-icon-circle-fill>
            </td>
            <td>불합격ㅠ</td>
            <td>{{ fail }}%</td>
          </tr>
        </table>
      </div>
    </div>
    <div id="container">
      <div class="btn">
        <v-row>
          <v-spacer></v-spacer>
          <v-btn
            tile
            color="success"
            @click="registReview()"
            id="interview-modal"
            v-if="
              $store.getters['userStore/getId'] != null &&
              $store.getters['userStore/getId'] != '' &&
              $store.getters['userStore/getEmpUser'] == 'false'
            "
          >
            <v-icon left> mdi-pencil </v-icon>
            면접후기작성
          </v-btn>
        </v-row>

        <b-modal
          id="registReview"
          centered
          title="면접후기"
          size="lg"
          hide-footer
        >
          <InterviewModal
            :review="reviewItem"
            :comNo="this.$route.query.comNo"
            :isUpdate="isUpdate"
            @reloadList="getList()"
          ></InterviewModal>
        </b-modal>

        <b-modal
          id="delete"
          centered
          size="sm"
          title="삭제하시겠습니까?"
          hide-footer
        >
          <DeleteModal :reviewNo="no" @reloadList="getList()"></DeleteModal>
        </b-modal>

        <v-dialog v-model="reportDialog" max-width="400px">
          <ReportModal2
            :report="report"
            @close="reportDialog = false"
            :key="reportKey"
          ></ReportModal2>
        </v-dialog>
      </div>
      <template v-if="interviewList.length == 0">
        <div>면접후기가 없습니다.</div>
      </template>
      <template v-for="review in interviewList">
        <template>
          <div :key="review.reviewNo">
            <div style="display: flex; justify-content: space-between">
              <div>
                <b-icon-person-circle
                  class="h1"
                  variant="secondary"
                ></b-icon-person-circle>
                <div style="display: inline-block; padding: 15px">
                  {{ review.regDate | yyyyMMdd }}
                </div>
              </div>
              <div style="padding: 25px">
                <template v-if="$store.state.userStore.id == review.writer">
                  <a href="javascript:viod(0)" @click="updateReview(review)">
                    <span>수정</span>
                  </a>
                  |
                  <a href="javascript:viod(0)" @click="deleteReview(review)">
                    <span> 삭제 </span>
                  </a>
                  |
                </template>
                <template
                  v-if="
                    $store.getters['userStore/getId'] != null &&
                    $store.getters['userStore/getId'] != ''
                  "
                >
                  <a href="javascript:viod(0)" @click="reportReview(review)">
                    <span> 신고 </span>
                  </a>
                </template>
              </div>
            </div>
            <div class="content">
              <div class="rate">
                면접난이도<br />
                <b-icon-star-fill
                  v-for="i in review.difficulty"
                  :key="review.reviewNo + ' ' + i"
                  variant="warning"
                ></b-icon-star-fill>
                <b-icon-star
                  v-for="j in 5 - review.difficulty"
                  :key="review.reviewNo + ' ' + j + ' '"
                  variant="warning"
                ></b-icon-star>

                <br />
                면접분위기<br />
                <b-icon-circle-fill
                  font-scale="0.8"
                  :variant="
                    review.atmosphere == '긍정적'
                      ? 'success'
                      : review.atmosphere == '부정적'
                      ? 'danger'
                      : 'info'
                  "
                ></b-icon-circle-fill
                >&nbsp;{{ review.atmosphere }}
                <br />
                면접결과<br />
                <b-icon-circle-fill
                  font-scale="0.8"
                  :variant="
                    review.result == '합격'
                      ? 'success'
                      : review.result == '불합격'
                      ? 'danger'
                      : 'info'
                  "
                ></b-icon-circle-fill
                >&nbsp;{{ review.result }}
              </div>
              <div class="comment">
                <span class="headline">"{{ review.content }}"</span>
                <br /><br />
                <p>
                  <span class="ad">질문목록</span><br />
                  <span
                    v-for="(question, index) in review.questionList"
                    :key="question"
                    >&nbsp;&nbsp;&nbsp;{{ index + 1 }}.&nbsp;{{ question }} <br
                  /></span>
                </p>
              </div>
            </div>
          </div>
        </template>
      </template>

      <b-pagination
        v-if="interviewList.length > 0"
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        align="right"
      ></b-pagination>
    </div>
  </div>
</template>
<script>
import ReportModal2 from "@/components/common/ReportModal2.vue";
import DeleteModal from "@/components/company/DeleteModal.vue";
import InterviewModal from "@/components/company/InterviewModal.vue";

export default {
  components: { ReportModal2, DeleteModal, InterviewModal },
  data: function () {
    return {
      stat: {},
      interviewList: [],
      currentPage: 1,
      perPage: 10,
      isUpdate: true,
      no: 0,
      report: {},
      diff: 0,
      positive: 10,
      negative: 10,
      neutral: 10,
      pass: 0,
      fail: 0,
      wait: 0,
      rows: 0,
      reviewItem: {},
      reportDialog: false,
      reportKey: 0,
    };
  },
  watch: {
    currentPage() {
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
      this.getList();
    },
  },

  created() {
    this.getList();
    this.getStat();
  },
  methods: {
    // 통계데이터 가져오기
    getStat() {
      this.$axios
        .get("/api/interview/stat/" + this.$route.query.comNo)
        .then((resp) => {
          console.log(resp);
          this.stat = resp.data;
          for (let item of this.stat) {
            if (item.DATA == "difficulty") {
              this.diff = item.RATIO;
            } else if (item.DATA == "긍정적") {
              this.positive = item.RATIO;
            } else if (item.DATA == "부정적") {
              this.negative = item.RATIO;
            } else if (item.DATA == "보통") {
              this.neutral = 100 - this.positive - this.negative;
            } else if (item.DATA == "합격") {
              this.pass = item.RATIO;
            } else if (item.DATA == "불합격") {
              this.fail = item.RATIO;
            } else if (item.DATA == "대기중") {
              this.wait = 100 - this.pass - this.fail;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 전체조회
    getList() {
      this.$axios
        .post("/api/interviewList", {
          pageNum: this.currentPage,
          comNo: this.$route.query.comNo,
        })
        .then((resp) => {
          console.log(resp);
          this.interviewList = resp.data.list;
          this.rows = resp.data.total;
          for (let item of this.interviewList) {
            item.question = item.question.replace(/, /g, ",");
            item.questionList = item.question.split(",");
          }
        })
        .catch((error) => {
          console.log(error);
        });
      this.getStat();
    },
    registReview() {
      this.reviewItem = {};
      this.isUpdate = false;
      this.$bvModal.show("registReview");
    },
    updateReview(review) {
      this.reviewItem = review;
      this.isUpdate = true;
      this.$bvModal.show("registReview");
    },
    deleteReview(review) {
      this.no = review.reviewNo;
      this.$bvModal.show("delete");
    },
    reportReview(review) {
      this.report.postCode = "interview_reviews";
      this.report.postNo = review.reviewNo;
      this.reportDialog = true;
      this.reportKey += 1;
    },
  },
  filters: {
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
      // 최종 포맷 (ex - '2021/10/08')
      return year + "/" + month + "/" + day;
    },
  },
};
</script>
<style scoped>
#stat {
  display: flex;
  justify-content: space-evenly;
}
table tr td {
  padding-right: 25px;
}
.comment {
  min-width: 500px;
}
.btn {
  margin-top: 10px;
  justify-content: space-between;
  display: flex;
}
#container {
  width: 100%;
  margin: 0 auto;
  text-align: left;
  min-height: 30vh;
}
.headline {
  font-weight: bold;
  font-size: large;
}
.content {
  display: flex;
  margin-bottom: 20px;
  padding-left: 10px;
}
.rate {
  min-width: 180px;
}
.ad {
  font-weight: bold;
  color: dodgerblue;
}
.comment {
  max-width: 500px;
}
</style>
