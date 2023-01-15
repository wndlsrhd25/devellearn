<template>
  <div>
    <v-card>
      <v-card-title class="text-h5"> 삭제하시겠습니까? </v-card-title>

      <v-card-actions>
        <v-btn color="green darken-1" text @click="$emit('cancle')">
          취소
        </v-btn>
        <v-spacer></v-spacer>

        <v-btn color="green darken-1" text @click="$emit('del')"> 삭제 </v-btn>
      </v-card-actions>
    </v-card>
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
        .delete("/api/reviews/" + this.reviewNo)
        .then((resp) => {
          console.log(resp);
          this.$emit("reloadList");
        })
        .catch((error) => {
          console.log(error);
        });
      this.$bvModal.hide("delete");
    },
  },
};
</script>
<style scoped></style>
