<template>
  <div>
    <v-card>
      <v-card-text class="text-h6">
        로그인이 필요한 서비스입니다. <br />
        로그인 하시겠습니까?
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn color="green darken-1" text @click="$emit('close')">
          아니요
        </v-btn>

        <v-btn color="green darken-1" text @click="login()"> 네 </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
export default {
  methods: {
    login() {
      this.$emit("close");
      this.$router.push({ path: "/login" }).catch(() => {});
    },
  },
};
</script>
<style scoped></style>
