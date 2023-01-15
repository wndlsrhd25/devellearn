<template>
  <div>
    <v-card>
      <v-card-text class="text-h6">
        등록된 이력서가 없습니다. <br />
        이력서를 먼저 작성해주세요
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn color="green darken-1" text @click="$emit('close')">
          취소
        </v-btn>

        <v-btn color="green darken-1" text @click="goResume()">
          바로가기
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
export default {
  methods: {
    goResume() {
      this.$emit("close");
      this.$router
        .push({
          path: "/myPage/myResume",
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped></style>
