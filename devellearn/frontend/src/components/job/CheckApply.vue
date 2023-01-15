<template>
  <div>
    <v-card>
      <v-card-text class="text-h6">
        지원이력이 존재합니다. <br />
        지원내역페이지로 이동하시겠습니까?
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn color="green darken-1" text @click="$emit('close')">
          취소
        </v-btn>

        <v-btn color="green darken-1" text @click="go()"> 바로가기 </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
export default {
  methods: {
    go() {
      this.$emit("close");
      this.$router
        .push({
          path: "/myPage/myApply",
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped></style>
