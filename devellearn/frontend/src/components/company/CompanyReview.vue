<template>
  <div id="container">
    <div class="btn">
      <v-row>
        <v-spacer></v-spacer>
        <v-btn
          tile
          color="success"
          @click="registReview()"
          id="review-modal"
          v-if="
            $store.getters['userStore/getId'] != null &&
            $store.getters['userStore/getId'] != '' &&
            $store.getters['userStore/getEmpUser'] == 'false'
          "
        >
          <v-icon left> mdi-pencil </v-icon>
          리뷰작성
        </v-btn>
      </v-row>
    </div>
    <b-modal id="registReview" centered title="기업리뷰" size="lg" hide-footer>
      <ReviewModal
        :review="reviewItem"
        :comNo="this.$route.query.comNo"
        :isUpdate="isUpdate"
        @reloadList="getList()"
      ></ReviewModal>
    </b-modal>

    <v-dialog v-model="dialogDel" max-width="290">
      <DeleteModal @cancle="dialogDel = false" @del="del()"></DeleteModal>
    </v-dialog>
    <v-dialog v-model="reportDialog" max-width="400px">
      <ReportModal2
        :report="report"
        @close="reportDialog = false"
        :key="reportKey"
      ></ReportModal2>
    </v-dialog>

    <template v-if="reviews.length == 0">
      <div>리뷰가 없습니다.</div>
    </template>
    <template v-for="review in reviews">
      <template>
        <div :key="review.reviewNo">
          <div style="display: flex; justify-content: space-between">
            <div>
              <b-icon-person-circle
                class="h1"
                variant="secondary"
              ></b-icon-person-circle>
              <div style="display: inline-block; padding: 15px">
                {{ review.category }} | {{ review.regDate | yyyyMMdd }}
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
              총평점<br />
              <b-icon-star-fill
                v-for="i in review.rate"
                :key="review.reviewNo + ' ' + i"
                variant="warning"
              ></b-icon-star-fill>
              <b-icon-star
                v-for="j in 5 - review.rate"
                :key="review.reviewNo + ' ' + j + ' '"
                variant="warning"
              ></b-icon-star>

              <br />
              급여만족도<br />
              <b-icon-star-fill
                v-for="i in review.salaryRate"
                :key="review.reviewNo + ' ' + i + 'i '"
                variant="warning"
              ></b-icon-star-fill>
              <b-icon-star
                v-for="j in 5 - review.salaryRate"
                :key="review.reviewNo + ' ' + 'i ' + j"
                variant="warning"
              ></b-icon-star>
              <br />
              워라밸만족도<br />
              <b-icon-star-fill
                v-for="i in review.wlbRate"
                :key="review.reviewNo + ' ' + i + 'i ' + i"
                variant="warning"
              ></b-icon-star-fill>
              <b-icon-star
                v-for="j in 5 - review.wlbRate"
                :key="review.reviewNo + ' ' + j + 'j ' + j + j"
                variant="warning"
              ></b-icon-star>
              <br />
              사내분위기<br />
              <b-icon-star-fill
                v-for="i in review.atmosphere"
                :key="review.reviewNo + ' ' + i + 'i ' + i + ' ' + i"
                variant="warning"
              ></b-icon-star-fill>
              <b-icon-star
                v-for="j in 5 - review.atmosphere"
                :key="review.reviewNo + ' ' + j + 'j ' + j + j + ' ' + j"
                variant="warning"
              ></b-icon-star>
              <br />
              성장가능성<br />
              <b-icon-star-fill
                v-for="i in review.potential"
                :key="review.reviewNo + ' ' + i + 'i ' + i + ' ' + i + ' ' + i"
                variant="warning"
              ></b-icon-star-fill>
              <b-icon-star
                v-for="j in 5 - review.potential"
                :key="
                  review.reviewNo + ' ' + j + 'j ' + j + j + ' ' + j + ' ' + j
                "
                variant="warning"
              ></b-icon-star>
            </div>
            <div class="comment">
              <span class="headline">"{{ review.content }}"</span>
              <br /><br />
              <p>
                <span class="ad">장점</span><br />
                <span>{{ review.advantage }}</span>
              </p>

              <p>
                <span class="di">단점</span><br />
                <span>{{ review.disadvantage }}</span>
              </p>
              <p>이 기업을 {{ review.recommend }}!</p>
            </div>
          </div>
        </div>
      </template>
    </template>

    <b-pagination
      v-if="reviews.length > 0"
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      align="right"
    ></b-pagination>
  </div>
</template>
<script>
import ReviewModal from "../company/ReviewModal.vue";
import ReportModal from "../common/ReportModal.vue";
import DeleteModal from "../common/DeleteModal.vue";
import ReportModal2 from "../common/ReportModal2.vue";

export default {
  data: function () {
    return {
      reviews: {},
      reviewItem: {},
      currentPage: 1,
      perPage: 10,
      rows: 1,
      isUpdate: false,
      no: 0,
      report: {},
      dialogDel: false,
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
  },
  methods: {
    getList() {
      this.$axios
        .get("/api/reviews/" + this.$route.query.comNo + "/" + this.currentPage)
        .then((resp) => {
          console.log(resp);
          this.reviews = resp.data.list;
          this.rows = resp.data.total;
        });
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
      this.dialogDel = true;
    },
    del() {
      this.$axios
        .delete("/api/reviews/" + this.no)
        .then((resp) => {
          console.log(resp);
          this.getList();
        })
        .catch((error) => {
          console.log(error);
        });
      this.dialogDel = false;
    },

    reportReview(review) {
      this.report.postCode = "com_reviews";
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

  components: { ReviewModal, ReportModal, DeleteModal, ReportModal2 },
};
</script>
<style scoped>
.comment {
  min-width: 500px;
}
.btn {
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
.di {
  font-weight: bold;
  color: lightcoral;
}
.comment {
  max-width: 500px;
}
</style>
