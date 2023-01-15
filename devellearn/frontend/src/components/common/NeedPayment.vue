<template>
  <div>
    <v-card>
      <v-card-text class="text-h6">
        결제가 필요한 서비스입니다. <br />
        수강신청을 먼저 해주세요.
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn color="green darken-1" text @click="$emit('close')">
          확인
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
export default {
  methods: {
    // async await 를 사용하여 addCart 메서드의 통신이 끝날때까지 기다림
    async addAndmoveCart() {
      this.$emit("close");
      await this.addCart();
      // 장바구니로 이동
      this.$router.push("/lectureCartList").catch(() => {});
    },
    // 장바구니 추가
    // async await 를 사용하여 addCart 메서드의 통신이 끝날때까지 기다림
    async addCart() {
      if (this.cartList.length == 0) {
        // 장바구니 등록 메서드 실행
        await this.insertCart();
      } else {
        // 수강신청한 배열
        for (let i = 0; i < this.cartList.length; i++) {
          if (this.cartList[i] == this.$route.query.no) {
            alert("이미 담은 강의입니다.");
            return;
          }
        }
        // 장바구니 등록 메서드 실행
        await this.insertCart();
      }
    },
    // 장바구니 등록 axios
    async insertCart() {
      const resp = await this.$axios.post("/api/cart", {
        memberId: this.$store.state.userStore.id,
        lectureNo: this.$route.query.no,
      });
      console.log("장바구니 추가", resp.data);
      if (resp.data != 0) {
        this.cartList.push(this.$route.query.no);
      }
    },
  },
};
</script>
<style scoped></style>
