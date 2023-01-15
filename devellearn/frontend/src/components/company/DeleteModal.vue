<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('delete')">취소</b-button>
      <b-button @click="deleteItem()">삭제</b-button>
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
        .delete("/api/interview/" + this.reviewNo)
        .then((resp) => {
          console.log(resp);
          this.$emit("reloadList");
          this.$bvModal.hide("delete");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
<style scoped></style>
