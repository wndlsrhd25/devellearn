<template>
  <div id="login">
    <v-form ref="form" v-model="valid" lazy_validation>
      <h3>LOGIN</h3>
      <p>-기업회원 로그인 화면입니다-</p>

      <v-text-field v-model="comId" label="아이디" required></v-text-field>
      <v-text-field
        v-model="password"
        type="password"
        label="비밀번호"
        required
        @keypress.enter="loginSubmit"
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


      <router-link to="/findComId">기업회원 아이디찾기 ></router-link>&nbsp;&nbsp;
      <router-link to="/findComPassword">기업회원 비밀번호찾기 ></router-link>&nbsp;&nbsp;
      <router-link to="/login">개인회원 로그인 ></router-link>


    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      valid: true,
      comId: "",
      password: "",
    };
  },
  methods: {
    loginSubmit() {
      let saveData = {};
      saveData.comId = this.comId;
      saveData.password = this.password;

      console.log(this.comId);
      console.log(this.password);

      this.$axios
        .post("/api/companyLogin", {
          comId: this.comId,
          password: this.password,
        })
        .then((res) => {
          if (res.data.comId != null) {
            this.$router.push({
              path: "/jobMain",
            }); //boardList로 페이지 바꾸겠다고 라우터에게 푸시
            this.$store.commit("userStore/setUserData", res.data);
            this.$store.commit("userStore/setId", this.comId);
            this.$store.commit("userStore/setEmpTrue");
            this.$store.commit("setIsJobTrue");
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
  margin-top: 50px;
  padding: 10px;
}

form {
  max-width: 400px;
  margin: 0 auto;
}

#text-link {
  margin-top: 50px;
}

a {
  color: black;
}
</style>
