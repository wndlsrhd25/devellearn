<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('deleteReviewModal')">취소</b-button>
      <b-button @click="deleteItem()" variant="primary">삭제</b-button>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    reviewNo: "",
  },
  methods: {
    deleteItem() {
      this.$axios
        .delete("/api/deleteReview/" + this.reviewNo)
        .then((resp) => {
          if (resp.data != "") {
            console.log(resp.data);
            this.$emit("getDeleteReview");
            this.$bvModal.hide("deleteReviewModal");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
<style scoped></style>
