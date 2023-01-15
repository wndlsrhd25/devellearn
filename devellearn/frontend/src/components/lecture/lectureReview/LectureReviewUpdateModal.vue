<template>
  <!-- 수강평 작성 폼 -->
  <div id="write_form">
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
      <b-button @click="$bvModal.hide('updateReviewModal')">취소</b-button>
      <b-button @click="udpateReview" variant="primary">수정</b-button>
    </div>
  </div>
  <!-- 수강평 작성 폼 끝-->
</template>

<script>
export default {
  props: ["oldReview"],
  data() {
    return {
      content: this.oldReview.content,
      rate: this.oldReview.rate,
    };
  },
  methods: {
    // 수강평 수정 axios
    udpateReview() {
      this.$axios
        .put("/api/updateLectureReview", {
          reviewNo: this.oldReview.reviewNo,
          content: this.content,
          rate: this.rate,
        })
        .then((resp) => {
          if (resp.data != "") {
            console.log("reviewUpdateModal", resp.data);
            // 수정된 리뷰 부모컴포넌트에 전송
            this.$emit("getNewReview", {
              content: this.content,
              rate: this.rate,
            });
            this.$bvModal.hide("updateReviewModal");
            ////////////////////////////////글작성 완료 알림창
            Swal.fire({
              position: "center",
              icon: "success",
              title: "수강평이 수정되었습니다.",
              showConfirmButton: true,
            });
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
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
  display: flex;
  justify-content: space-between;
}
</style>
