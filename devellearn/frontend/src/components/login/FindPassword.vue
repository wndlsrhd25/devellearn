<template>
  <div id="FindPassword" style="min-height: 100vh">
    <form>
      <h3 style="margin-bottom: 5%">비밀번호 찾기</h3>

      <div id="inputForm">
        <v-container fluid>
          <v-row align="center">
            <v-col cols="7">
              <!-- :error-messages="errors" -->
              <div id="Label"><v-label>email</v-label></div>
              <v-text-field
                v-model="email"
                class="custom-label-color"
                id="email"
                name="email"
                :rules="emailRule"
                @keypress.enter="checkMailSend"
              >
              </v-text-field>
            </v-col>

            <v-col class="d-flex" cols="4">
              
              <v-btn
                class="sendMailbtn"
                id="email_checkBtn2"
                rounded
                color="primary"
                dark
                @click="checkMailSend()"
              >
                이메일인증
              </v-btn>
            </v-col>
          </v-row>

          <v-row align="center">
            <v-col cols="7">
              <div id="Label"><v-label>email_code</v-label></div>
              <v-text-field
                v-model="email_code"
                id="email_code"

                @keypress.enter="checkMailNum"
              >
              </v-text-field>
            </v-col>
            <v-col class="d-flex" cols="4">
              <v-btn
                class="inputNumCheck"
                id="email_checkBtn"
                rounded
                color="primary"
                dark
                @click="checkMailNum"
                >인증번호입력
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </div>

      <!-- true면 활성화 -->

      <v-container v-if="codeCheck">
        <v-row>
          <v-col cols="10">
            <!-- password 첫번째 입력-->

            <!-- 이 입력값에 대해 2가지 조건이 맞으면  validationPW 을 true 반환 -->
            <v-text-field
              v-model="user_pw"
              label="새 비밀번호"
              prepend-icon="mdi-lock-outline"
              type="password"
              :rules="user_pw_rule"
              :counter="6"
            >
            </v-text-field>
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="10">
            <!-- password 두번째 입력-->

            <!-- 이 입력값에 대해 3가지 조건이 맞으면  validationPW2 true 반환 -->
            <v-text-field
              v-model="user_pw_chk"
              label="새 비밀번호 확인"
              id="newPw"
              prepend-icon="mdi-lock-outline"
              type="password"
              :rules="user_pw_rule2"
              :counter="6"
              @keypress.enter="submitPw"
            >
            </v-text-field>
          </v-col>
        </v-row>
        <v-btn
          class="submit"
          id="submit"
          rounded
          color="primary"
          dark
          @click="submitPw"
          >비밀번호 변경</v-btn
        >
      </v-container>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectData: {},

      email: "",
      email_code: "",

      // 이메일 유효성이 맞으면 true
      validationMail: false,
      //회원정보가 있는 이메일이면 turem
      ourMember: true,

      //값이 변하면 false로 바뀌고, 인증 완료하면 true로 바뀜
      codeCheck: false,

      user_pw: "",
      user_pw_chk: "",

      // 이메일 유효성 검사 ▶ validationMail
      emailRule: [
        (v) => !!v || "이메일을 입력해주세요.",
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          const pattern =
            /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
          // return pattern.test(replaceV) || "이메일 형식으로 입력해주세요";
          // return pattern.test(replaceV) ? this.validationMail=true : this.validationMail=false;
          if (pattern.test(replaceV) == true) {
            //이메일 유효성검사가 맞으면 validationMail가 true로 변경
            //validationMail가 true일때만 인증번호 발송이 가능
            this.validationMail = true;
            return "";
          } else {
            this.validationMail = false;
            return "이메일 형식으로 입력해주세요";
          }
        },
      ],

      user_pw_rule: [
        (v) => {
          if (v && v.length < 6) {
            this.validationPW = false;
            console.log("this.validationPW", this.validationPW);
            console.log("user_pw", this.user_pw);
            return "패스워드는 6자 이상 입력해주세요.";
          } else {
            this.validationPW = true;
            console.log("this.validationPW", this.validationPW);
            console.log("user_pw", this.user_pw);
            return "";
          }
        },
      ],

      user_pw_rule2: [
        (v) => {
          if (v == this.user_pw) {
            this.validationPW2 = true;
            console.log("this.validationPW2", this.validationPW2);
            console.log("user_pw_chk", this.user_pw_chk);
            return "";
          } else {
            this.validationPW2 = false;
            console.log("this.validationPW2", this.validationPW2);
            console.log("user_pw_chk", this.user_pw_chk);
            return "패스워드가 일치하지 않습니다.";
          }
        },
      ],
    };
  },

  methods: {
    checkMailSend() {
      //유효성 검사가 맞아야됨
      if (this.validationMail == true) {
        //회원인지 아닌지 이메일 체크
        this.$axios
          .post("/api/emailCheck", {
            email: this.email,
          })
          .then((res) => {
            if (res.data != 0) {
              this.ourMember = true;
              //회원정보가 일치함
              if ((this.ourMember = true)) {
                this.$axios
                  .get(
                    "/api/emailSending/" +
                      document.getElementById("email").value
                  )
                  .then((resp) => {
                    console.log(resp);
                    this.realCode = resp.data;
                    //alert(this.realCode);
                    const inputTarget = document.getElementById("email");
                    inputTarget.disabled = true;
                  });

                Swal.fire({
                  position: "center",
                  icon: "info",
                  title: "이메일이 발송되었습니다.",
                  showConfirmButton: false,
                  timer: 1300,
                });
              }
            } else {
              Swal.fire({
                position: "center",
                icon: "warning",
                title: "회원정보가 존재하지 않습니다.",
                showConfirmButton: false,
                timer: 1300,
              });
              this.email = "";
            }
          });
      }
      //유효성 검사가 통과 안했음
      else {
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "이메일 형식을 확인해주세요",
          showConfirmButton: false,
          timer: 1300,
        });
      }
    },

    //인증번호 맞는지 확인
    checkMailNum() {
      var code = document.getElementById("email_code").value;
      // code 랑 코드 담은 변수랑 같은지 확인
      if (code == this.realCode) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "인증 완료!",
          showConfirmButton: false,
          timer: 1000,
        });

        //비밀번호 입력란 비활성화
        const codeTarget = document.getElementById("email_code");
        codeTarget.disabled = true;
        this.codeCheck = true;

        //이메일 인증버튼 비활성화
        const checkBtn = document.getElementById("email_checkBtn");
        console.log("checkBtn", checkBtn);
        checkBtn.disabled = true;

        //인증코드 입력 비활성화
        const checkBtn2 = document.getElementById("email_checkBtn2");
        console.log("checkBtn2", checkBtn2);
        checkBtn2.disabled = true;
      } else {
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "인증번호를 다시 확인해주세요",
          showConfirmButton: false,
          timer: 1300,
        });
        const codeTarget = document.getElementById("email_code");
        codeTarget = "";
      }
    },

    submitPw() {
      //인증번호 인증을 완료한 사람만 submit 할수 있게
      if (this.validationPW == true && this.validationPW2 == true) {
        this.$axios
          .put("/api/changePw", {
            email: document.getElementById("email").value,
            password: document.getElementById("newPw").value,
          })
          .then((res) => {
            if (res.data != null) {
              Swal.fire({
                position: "center",
                icon: "success",
                title: "비밀번호 변경완료!",
                showConfirmButton: false,
                timer: 1000,
              });

              this.$router.push({
                path: "/login",
              });
            }
          });
      } else {
        Swal.fire({
          position: "center",
          icon: "info",
          title: "비밀번호를 바르게 입력해주세요.",
          showConfirmButton: false,
          timer: 1000,
        });
      }
    },
  },
};
</script>

<style scoped>
#FindPassword {
  padding-top: 150px;
  height: 100vh;
  width: 50%;
  margin: 0 auto;
}

form {
  max-width: 400px;
  margin: 0 auto;
  padding-top: 50px;
}

.v-application .error--text {
  color: black !important;
  caret-color: black !important;
}

.v-text-field fieldset .v-text-field .v-input__control {
  color: black !important;
}

.v-application .error--text {
  color: black !important;
  caret-color: black !important;
}

.custom-label-color .v-label {
  color: black !important;
  opacity: 1;
}

#email {
  color: black;
  caret-color: black;
}
#Label {
  text-align: left;
  font-size: x-small;
  margin-bottom: 0px;
}

</style>
