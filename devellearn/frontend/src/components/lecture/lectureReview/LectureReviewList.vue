<template>
  <div>
    <div data-app>
      <v-dialog v-model="reportDialog" persistent max-width="500px">
        <ReportModal2
          :report="report"
          @close="reportDialog = false"
        ></ReportModal2>
      </v-dialog>
    </div>
    <div class="review_title">
      <h2 id="review">수강평</h2>
      <div>총 {{ reviewList.length }}개의 수강평</div>
    </div>
    <!-- 수강평 작성 폼 -->
    <div id="write_form" v-if="this.checkLogin() && this.checkPay()">
      <!-- 별점 -->
      <div class="inner">
        <v-rating
          empty-icon="mdi-star-outline"
          full-icon="mdi-star"
          length="5"
          v-model="rate"
          large
          color="#ffcb02"
          background-color="#ffcb02"
        >
        </v-rating>
        <p>별점을 선택해주세요.</p>
      </div>
      <!-- 내용 작성 -->
      <div>
        <b-form-textarea
          id="textarea-rows"
          placeholder="귀하의 수강평은 개발고수에게 큰 도움이 됩니다."
          rows="6"
          v-model="content"
        ></b-form-textarea>
      </div>
      <div class="review_btn">
        <b-button variant="primary" @click="insertReview">등록</b-button>
      </div>
    </div>
    <!-- 수강평 작성 폼 끝-->

    <!-- 수강평 리스트 -->
    <div v-if="reviewList.length == 0" style="margin-top: 40px">
      작성된 수강평이 없습니다.<br />수강평을 작성하여 개발고수에게 힘이
      되어주세요.
    </div>
    <template v-for="review in reviewList">
      <template>
        <div id="lr_wrapper" :key="review.reviewNo">
          <div>
            <div>
              <img
                src="@/assets/profile.jpg"
                alt="profile"
                class="user_profile"
              />
              <h5>{{ review.memberId }}</h5>
            </div>
            <div>
              <b-icon-star-fill
                v-for="i in review.rate"
                :key="i + new Date()"
                variant="warning"
              ></b-icon-star-fill>
              <b-icon-star
                v-for="j in 5 - review.rate"
                :key="j + new Date() * 2"
                variant="warning"
              ></b-icon-star>
            </div>
          </div>
          <div>
            <p>{{ review.content }}</p>
            <span
              @click="reportReview(review)"
              v-if="
                $store.getters['userStore/getId'] != '' &&
                $store.getters['userStore/getId'] != review.memberId
              "
              >신고하기</span
            >
          </div>
          <div>
            <p>{{ changeDateFormat(review.regDate) }}</p>
            <div
              v-if="$store.state.userStore.id == review.memberId"
              class="update_delete_btn"
            >
              <span @click="updateReview(review)">수정</span>
              <span @click="deleteReview(review.reviewNo)">삭제</span>
            </div>
          </div>
        </div>
      </template>
    </template>
    <v-row align="center">
      <v-btn block color="success" v-if="hasNextPage" @click="getReviewList()"
        >더보기</v-btn
      >
    </v-row>
    <!-- 수강평 리스트 끝 -->

    <!-- 수강평 신고 모달 -->
    <b-modal
      id="report"
      centered
      title="수강평 신고"
      size="m"
      footerClass="p-2 border-top-0"
    >
      <ReportModal :report="report"></ReportModal>
      <template #modal-footer="{ hide }">
        <b-button @click="hide('forget')" style="display: none"></b-button>
      </template>
    </b-modal>
    <!-- 수강평 신고 모달 끝 -->

    <!-- 수강평 수정 모달 -->
    <b-modal
      id="updateReviewModal"
      centered
      title="수강평 수정"
      size="m"
      footerClass="p-2 border-top-0"
    >
      <LectureReviewUpdateModal
        :oldReview="oldReview"
        @getNewReview="updateReviewList"
      ></LectureReviewUpdateModal>
      <template #modal-footer="{ hide }">
        <b-button @click="hide('forget')" style="display: none"></b-button>
      </template>
    </b-modal>
    <!-- 수강평 수정 모달 끝 -->
  </div>
</template>

<script>
import moment from "moment";
import ReportModal2 from "@/components/common/ReportModal2.vue";
import LectureReviewUpdateModal from "./LectureReviewUpdateModal.vue";

export default {
  components: {
    ReportModal2,
    LectureReviewUpdateModal,
  },
  props: ["myCurrList"],
  data: function () {
    return {
      // 수강평 리스트 담을 배열
      reviewList: [],
      rate: 0,
      content: "",
      report: {},
      oldReview: {},
      reviewNo: "",
      pageNum: 1,
      hasNextPage: true,
      dialog: false,
      dialogPay: false,
      reportDialog: false,
    };
  },
  created: function () {
    this.getReviewList();
  },
  methods: {
    checkPay() {
      if (this.myCurrList.length == 0) {
        return false;
      } else {
        return true;
      }
    },
    checkLogin() {
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        return false;
      } else {
        return true;
      }
    },
    // 수강평등록 axios
    insertReview: function () {
      // 유효성 검사
      if (this.content == "") {
        //////////////////////////////// 알림창
        Swal.fire({
          position: "center-center",
          icon: "warning",
          title: "내용을 입력하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
        return;
      }
      if (this.rate == 0) {
        //////////////////////////////// 알림창
        Swal.fire({
          position: "center-center",
          icon: "warning",
          title: "별점을 입력하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
        return;
      }
      this.$axios
        .post("/api/writeReview", {
          memberId: this.$store.state.userStore.id,
          lectureNo: this.$route.query.no,
          content: this.content,
          rate: this.rate,
        })
        .then((resp) => {
          console.log(resp);
          // 배열 젤 앞에 원소 추가
          this.reviewList.unshift(resp.data);
          // 배열에 원소 추가 후 수강평 입력폼 원점세팅
          this.rate = 0;
          this.content = "";
          ////////////////////////////////글작성 완료 알림창
          Swal.fire({
            position: "center",
            icon: "success",
            title: "수강평이 등록되었습니다.",
            showConfirmButton: true,
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 해당 강의 수강평 전체조회 axios
    getReviewList: function () {
      this.$axios
        .get("/api/reviewList/" + this.$route.query.no + "/" + this.pageNum)
        .then((resp) => {
          console.log(resp.data);
          this.pageNum += 1;
          const data = resp.data.list;
          this.reviewList.push(...data);
          this.hasNextPage = resp.data.hasNextPage;
        })
        .catch((error) => {
          console.log("error", error);
        });
    },
    // 수강평 신고 모달 팝업
    reportReview(review) {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        //내가 실제로 그 버튼을 눌렀을때 실행되야하는 것
        this.report.postCode = "lecture_review";
        this.report.postNo = review.reviewNo;
        this.reportDialog = true;
      }
    },
    // 수강평 수정 모달 팝업
    updateReview(review) {
      this.oldReview = review;
      this.$bvModal.show("updateReviewModal");
    },
    // 자식컴포넌트(수정 모달창)으로 부터 받은 업데이튼 된 리뷰 받아서 배열에 업데이트
    updateReviewList(newReview) {
      console.log("lecture review list newReview", newReview.content);
      for (let i = 0; i < this.reviewList.length; i++) {
        if (this.oldReview.reviewNo == this.reviewList[i].reviewNo) {
          this.reviewList[i].content = newReview.content;
          this.reviewList[i].rate = newReview.rate;
        }
      }
    },
    // 수강평 삭제 모달 팝업
    deleteReview: function (reviewNo) {
      this.reviewNo = reviewNo;
      //////////////////////////////////////////////////삭제(경고 빨간 아이콘) 알림창
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            //여기에다가 삭제시 axios 넣으면 됨
            this.$axios
              .delete("/api/deleteReview/" + this.reviewNo)
              .then((resp) => {
                if (resp.data != "") {
                  console.log(resp.data);
                  this.deleteReviewList();
                  this.$swal.fire("삭제완료", "", "success");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        });
    },
    // 수강평 배열 원소 삭제
    deleteReviewList() {
      // reviewList 배열에서 삭제
      for (let i = 0; i < this.reviewList.length; i++) {
        if (this.reviewList[i].reviewNo == this.reviewNo) {
          this.reviewList.splice(i, 1);
        }
      }
    },
    // 날짜 포맷 변경
    changeDateFormat: function (date) {
      return moment(date).format("YYYY.MM.DD. a HH:mm");
    },
  },
};
</script>

<style scoped>
/* 수강평 리스트 */
#lr_wrapper {
  border-bottom: 1px solid lightgray;
  padding: 10px;
  margin-top: 20px;
}

#lr_wrapper div {
  display: flex;
  justify-content: space-between;
}

.user_profile {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

/* 등록날짜 */
#lr_wrapper div:nth-child(3) {
  margin-top: 10px;
  color: gray;
}

/* 수정 삭제 버튼 간격 띄우기 */
.update_delete_btn span:nth-child(1) {
  margin-right: 10px;
}

#lr_wrapper span {
  /* background-color: blue; */
  height: 24px;
  line-height: 24px;
}

#lr_wrapper span:hover {
  color: cornflowerblue;
  border-bottom: 1px solid cornflowerblue;
  cursor: pointer;
}
/* 수강평 리스트 끝 */

/* 수강평 작성폼 */
#write_form {
  background-color: rgba(204, 204, 204, 0.3);
  padding: 20px;
  border-radius: 10px;
  width: 90%;
  margin: auto;
}

.inner {
  text-align: center;
}

.inner h5 {
  margin-bottom: 20px;
}

.review_btn {
  margin-top: 20px;
  text-align: right;
}

h2 {
  margin-bottom: 20px;
}

.review_title {
  display: flex;
  justify-content: space-between;
}
/* 수강평 작성폼 끝 */
</style>
