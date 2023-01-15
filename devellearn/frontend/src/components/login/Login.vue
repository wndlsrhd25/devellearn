<template>
  <div id="login">
    <v-form ref="form" v-model="valid" lazy_validation>
      <div id="title">
        <h3>LOGIN</h3>
      </div>
      <v-text-field
        v-model="memberId"
        label="아이디"
        name="memberId"
        required
      ></v-text-field>
      <v-text-field
        v-model="password"
        type="password"
        label="비밀번호"
        required
        @keydown.enter="loginSubmit"
      >
      </v-text-field>

      <!-- <v-checkbox
        v-model="checkbox"
        :error-messages="checkboxErrors"
        label="개인정보 활용에 동의하십니까?"
        required
        @change="$v.checkbox.$touch()"
        @blur="$v.checkbox.$touch()"
      ></v-checkbox> -->

      <v-btn class="mr-4" :disabled="!valid" @click="loginSubmit">
        로그인
      </v-btn>
      &nbsp;&nbsp;
    </v-form>

    <div id="text-link">
      <router-link to="/findId">아이디찾기 ></router-link>&nbsp;&nbsp;
      <router-link to="/findPassword">비밀번호찾기 ></router-link>&nbsp;&nbsp;
      <router-link to="/companyLogin">기업회원 로그인 ></router-link>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      valid: true,
      memberId: "",
      password: "",
    };
  },

  methods: {
    loginSubmit() {
      console.log(this.memberId);
      console.log(this.password);

      this.$axios
        .post("/api/login", {
          memberId: this.memberId,
          password: this.password,
        })
        .then((res) => {
          if (res.data.memberId != null) {
            console.log(res.data);
            this.$store.commit("userStore/setUserData", res.data);
            this.$store.commit("userStore/setId", this.memberId);
            this.$store.commit("userStore/setEmpFalse", this.memberId);
            this.$router.go(-1);
          } else {
            Swal.fire({
              position: "center",
              icon: "warning",
              title: "로그인 정보를 다시 확인하세요",
              showConfirmButton: false,
              timer: 1000,
            });

            component.$router.go(0);
          }
        });
    },
  },
};
</script>

<style scoped>
#login {
  padding-top: 150px;
  height: 100vh;
  width: 50%;
  margin: 0 auto;
}
.mr-4 {
  margin: 50px 0;
  padding: 10px;
}

form {
  max-width: 400px;
  margin: 0 auto;
}

#title {
  margin: 50px;
}
</style>
