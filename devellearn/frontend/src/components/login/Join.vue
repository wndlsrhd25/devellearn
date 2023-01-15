<template>
  <div id="join" style="min-height: 100vh">
    <validation-observer ref="observer" v-slot="{ invalid }">
      <form>
        <h3>회원가입</h3>

        <h6 style="margin-top: 10px">[개인회원]</h6>
        <div style="margin-top: 30px; text-align: right; font-size: small">
          <router-link to="/companyJoin">▷기업회원가입</router-link>
        </div>

        <v-container fluid>
          <v-row align="center">
            <!-- 1. 아이디 -->
            <v-col class="d-flex">
              <!-- rules에 거는 항목들이 모두 통과해야, Join 버튼이 활성화된다. -->
              <validation-provider
                v-slot="{ errors }"
                name="memberId"
                rules="[required, max15, specialCheck]"
              >
                <div id="idBox">
                  <v-text-field
                    v-model="memberId"
                    :counter="15"
                    :rules="user_id_rule"
                    :error-messages="errors"
                    label="id"
                    @change="changeId"
                    required
                  >
                    <template v-slot:append>
                      <!-- idRule :  -->
                      <v-icon v-if="successId" color="white">{{
                        idRule
                      }}</v-icon>
                      <v-icon v-if="!successId" color="white">{{
                        idRule
                      }}</v-icon>
                    </template>
                  </v-text-field>
                </div>
              </validation-provider>
            </v-col>

            <v-col class="d-flex">
              <v-btn
                v-if="successId"
                id="checkBtn"
                class="btn"
                type="button"
                rounded
                color="primary"
                dark
                @click="checkId()"
                required
                >중복확인</v-btn
              >
            </v-col>
          </v-row>
        </v-container>

        <!-- 2. 비밀번호 https://ffernandamarisol.medium.com/vuetify-matching-passwords-with-rules-a83400eca166-->

        <v-container>
          <v-row justify="center">
            <v-col cols="6">
              <validation-provider
                v-slot="{ errors }"
                name="password"
                rules="required|min:6"
              >
                <!-- password 첫번째 입력-->
                <v-text-field
                  v-model="password"
                  label="Password"
                  prepend-icon="mdi-lock-outline"
                  type="password"
                  :rules="[required, min6]"
                  required
                >
                  <template v-slot:append>
                    <v-icon v-if="successPass" color="white">{{
                      passRule
                    }}</v-icon>
                    <v-icon v-if="!successPass" color="white">{{
                      passRule
                    }}</v-icon>
                  </template>
                </v-text-field>
              </validation-provider>
            </v-col>

            <v-col cols="6">
              <!-- rules="[required, min6, matchingPasswords]" -->
              <!-- matchingPasswords도 넣어야하는데 어떻게 넣어야 할지.. -->
              <!-- :rules="{ required: true, not_space: true }" -> 이렇게 쓰면 될지 -->
              <validation-provider
                v-slot="{ errors }"
                name="password1"
                rules="required|min:6"
              >
                <!-- password1 두번째 입력 -->
                <v-text-field
                  v-model="password1"
                  label="Verify Password"
                  prepend-icon="mdi-lock-outline"
                  type="password"
                  :rules="[required, min6, matchingPasswords]"
                  required
                >
                  <template v-slot:append>
                    <v-icon v-if="successPass1" color="white">{{
                      passRule1
                    }}</v-icon>
                    <v-icon v-if="!successPass1" color="white">{{
                      passRule1
                    }}</v-icon>
                  </template>
                </v-text-field>
              </validation-provider>
            </v-col>
          </v-row>
        </v-container>

        <!-- 3. 이메일 https://kimvampa.tistory.com/105?category=771727 -->
        <v-container fluid>
          <v-row align="center">
            <v-col>
              <validation-provider
                v-slot="{ errors }"
                name="email"
                rules="required|max:30"
              >
                <v-text-field
                  v-model="email"
                  id="email"
                  :counter="30"
                  :error-messages="errors"
                  label="email"
                  name="email"
                  :rules="emailRule"
                  required
                >
                  <template v-slot:append>
                    <v-icon v-if="successMail" color="white">{{
                      emailRules
                    }}</v-icon>
                    <v-icon v-if="!successMail" color="white">{{
                      emailRules
                    }}</v-icon>
                  </template>
                </v-text-field>
              </validation-provider>
            </v-col>

            <!-- 이메일 = email, 메일보내는 버튼함수  : checkMailSend, 버튼 이름 : sendMailbtn  -->
            <!-- 이메일 = email_code, 메일보내는 버튼함수  : checkMailNum, 버튼 이름 : inputNumCheck  -->

            <!-- 이메일 인증의 경우, 일단 주석처리 진행 (이메일 인증 버튼 및 인증번호 입력란 주석) -->
            <v-col class="d-flex">
              <v-btn
                class="sendMailbtn"
                rounded
                color="primary"
                dark
                @click="checkMailSend"
                >이메일인증</v-btn
              >
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col>
              <v-text-field
                v-model="email_code"
                id="email_code"
                :error-messages="errors"
                label="email_code"
                required
              >
              </v-text-field>
            </v-col>
            <v-col class="d-flex">
              <v-btn
                class="inputNumCheck"
                rounded
                color="primary"
                dark
                @click="checkMailNum"
                >인증번호입력</v-btn
              >
            </v-col>
          </v-row>
        </v-container>

        <!-- 4. 이름 -->
        <v-container fluid>
          <validation-provider v-slot="{ errors }" name="name" rules="required">
            <v-text-field
              v-model="name"
              :error-messages="errors"
              label="Name"
              :rules="nameRule"
              required
            >
              <template v-slot:append>
                <v-icon v-if="successName" color="white">{{
                  nameRules
                }}</v-icon>
                <v-icon v-if="!successName" color="white">{{
                  nameRules
                }}</v-icon>
              </template>
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 5. 전화번호 -->

        <v-form>
          <v-container>
            <v-row>
              <v-col cols="12" md="4">
                <validation-provider
                  v-slot="{ errors }"
                  name="phone1"
                  :rules="{ required: true, digits: 3 }"
                >
                  <v-text-field
                    v-model="phone1"
                    :counter="3"
                    :error-messages="errors"
                    label="Phone"
                    required
                  >
                  </v-text-field>
                </validation-provider>
              </v-col>

              <v-col cols="12" md="4">
                <validation-provider
                  v-slot="{ errors }"
                  name="phone2"
                  :rules="{ required: true, digits: 4 }"
                >
                  <v-text-field
                    v-model="phone2"
                    :counter="4"
                    :error-messages="errors"
                    label="Phone"
                    required
                  >
                  </v-text-field>
                </validation-provider>
              </v-col>

              <v-col cols="12" md="4">
                <validation-provider
                  v-slot="{ errors }"
                  name="phone3"
                  :rules="{ required: true, digits: 4 }"
                >
                  <v-text-field
                    v-model="phone3"
                    :counter="4"
                    :error-messages="errors"
                    label="Phone"
                    required
                    @keypress.enter="joinComplete"
                  >
                  </v-text-field>
                </validation-provider>
              </v-col>
            </v-row>
          </v-container>
        </v-form>

        <!-- <v-alert color="blue" type="success">회원가입이 완료되었습니다.</v-alert> -->
      </form>
      <v-btn
        class="submitBtn"
        type="button"
        :disabled="invalid"
        @click="joinComplete"
        >가입하기</v-btn
      >&nbsp;
    </validation-observer>
  </div>
</template>

<script>
import { required, digits, email, min, max } from "vee-validate/dist/rules";
import {
  extend,
  ValidationObserver,
  ValidationProvider,
  setInteractionMode,
} from "vee-validate";

setInteractionMode("eager");
extend("digits", {
  ...digits,
  message: "{length}자리 숫자로 입력하세요.",
});
extend("required", {
  ...required,
  message: "필수입력 사항입니다.",
});
extend("max", {
  ...max,
  message: "{_field_}은 최대 {length}자 이상 입력불가합니다.",
});
extend("min", {
  ...min,
  message: "{_field_}은 최소 {length}이상 입력해주세요.",
});
extend("email", {
  ...email,
  message: "Email must be valid",
});
export default {
  components: {
    ValidationProvider,
    ValidationObserver,
  },
  data: () => ({
    // 입력값
    memberId: "",
    password: "",
    phone1: "",
    phone2: "",
    phone3: "",
    //아이디 중복체크 여부
    vaildId: false,
    successId: false,
    realCode: "",
    //비밀번호 체크 관련
    password: "",
    password1: "",
    successPass: false,
    successPass1: false,
    //이메일 체크 관련
    email: "",
    emailCode: "",
    email_valid: false,
    successMail: false,
    //이름체크 관련
    name: "",
    successName: false,
    //폰번호 관련
    contact: null,
    user_id_rule: [
      (v) => !!v || "id는 필수 입력사항입니다.",
      (v) => /^[a-zA-Z0-9]*$/.test(v) || "id는 영문+숫자만 입력 가능합니다.",
      (v) => !(v && v.length >= 15) || "id는 15자 이상 입력할 수 없습니다.",
    ],
    nameRule: [
      (v) => !!v || "이름은 필수 입력사항입니다.",
      (v) =>
        /^[가-힣a-zA-Z]+$/.test(v) || "이름은 한글/영문만 입력 가능합니다.",
    ],
    rules: {
      required: (value) => !!value || "필수입력사항입니다.",
      min: (v) => v.length >= 6 || "6자 이상으로 입력하세요.",
      //emailMatch: () => (`The email and password you entered don't match`),
      user_pw_rule2: [
        (v) =>
          this.state === "ins"
            ? !!v || "패스워드는 필수 입력사항입니다."
            : true,
        (v) =>
          !(v && v.length >= 30) || "패스워드는 30자 이상 입력할 수 없습니다.",
        (v) => v === this.password || "패스워드가 일치하지 않습니다.",
      ],
    },
    emailRule: [
      (v) => !!v || "이메일을 입력해주세요.",
      (v) => {
        const replaceV = v.replace(/(\s*)/g, "");
        const pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
        return pattern.test(replaceV) || "이메일 형식으로 입력해주세요";
      },
    ],
  }),
  methods: {
    submit() {
      this.$refs.observer.validate();
    },
    //아이디 중복체크
    checkId() {
      if (this.memberId == "") {
        //alert("아이디를 입력해주세요");

        Swal.fire({
          position: "center",
          icon: "info",
          toast: true,
          title: "아이디를 입력해주세요",
          showConfirmButton: false,
          timer: 1000,
        });
      } else {
        this.$axios
          .post("/api/idCheck", {
            memberId: this.memberId,
          })
          .then((res) => {
            if (res.data != 1) {
              //alert("사용가능한 아이디입니다!");

              Swal.fire({
                position: "center",
                icon: "success",
                toast: true,
                title: "사용가능한 아이디입니다!",
                showConfirmButton: false,
                timer: 1000,
              });

              this.vaildId = true;
            } else {
              //alert("이미 사용중인 아이디입니다!");

              Swal.fire({
                position: "center",
                icon: "info",
                toast: true,
                title: "이미 사용중인 아이디입니다!",
                showConfirmButton: false,
                timer: 1000,
              });
              this.memberId = "";
            }
          });
      }
    },

    // checkWithdrawal() {
    //   this.$axios
    //     .post("/api/idCheck", {
    //       memberId: this.memberId,
    //     })
    //     .then((res) => {
    //       if (res.data != 1) {
    //         alert("사용가능한 아이디입니다!");
    //         this.vaildId = true;
    //       } else {
    //         alert("이미 사용중인 아이디입니다!");
    //         this.memberId = "";
    //       }
    //     });
    // },

    //아이디 중복체크 이후, 값이 변하면 다시 체크할 수 있도록 상태를 false로 변경
    changeId() {
      this.vaildId = false;
    },
    //회원가입(JOIN), 전체 데이터 전송
    joinComplete() {
      if (!this.vaildId) {
        //alert("아이디 중복체크 하세요");

        Swal.fire({
          position: "center",
          icon: "info",
          toast: true,
          title: "아이디 중복체크 하세요",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }
      if (!this.email_valid) {
        //alert("이메일 인증을 완료하세요");
        Swal.fire({
          position: "center",
          icon: "info",
          toast: true,
          title: "이메일 인증을 완료하세요",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      } else {
        let saveData = {};
        saveData.memberId = this.memberId;
        saveData.password = this.password;
        saveData.email = this.email;
        saveData.name = this.name;
        saveData.phone = this.phone1 + this.phone2 + this.phone3;
        this.$axios
          .post("/api/join", {
            memberId: this.memberId,
            password: this.password,
            email: this.email,
            name: this.name,
            phone: this.phone1 + this.phone2 + this.phone3,
          })
          .then((res) => {
            if (res.data != null) {
              Swal.fire({
                position: "center",
                icon: "success",
                title: "회원가입 완료!",
                showConfirmButton: false,
                timer: 1000,
              }).then((result) => {
                this.$router.push({
                  path: "/",
                });
              });
            } else {
              component.$router.go(0);
            }
          });
      }
    },
    //이메일 인증 메일 발송
    checkMailSend() {
      console.log(document.getElementById("email").value);
      this.$axios
        .get("/api/emailSending/" + document.getElementById("email").value)
        .then((resp) => {
          console.log(resp); //data 안에서 선언한 변수에 할당 resp.data
          this.realCode = resp.data;
          const inputTarget = document.getElementById("email");
          inputTarget.disabled = true;
        });
    },
    //인증번호 맞는지 확인
    checkMailNum: function () {
      var code = document.getElementById("email_code").value;
      // code 랑 코드 담은 변수랑 같은지 확인
      if (code == this.realCode) {
        //alert("인증이 완료되었습니다.");
        Swal.fire({
          position: "center",
          icon: "success",
          toast: true,
          title: "인증이 완료되었습니다.",
          showConfirmButton: false,
          timer: 1000,
        });
        const codeTarget = document.getElementById("email_code");
        codeTarget.disabled = true;
        this.email_valid = true;
      } else {
        //alert("인증번호를 다시 확인해주세요.");
        Swal.fire({
          position: "center",
          icon: "info",
          toast: true,
          title: "인증번호를 다시 확인해주세요.",
          showConfirmButton: false,
          timer: 1000,
        });
        const codeTarget = document.getElementById("email_code");
        codeTarget = "";
      }
    },
    //아이디 유효성 관련 (15자 이내, 특수문자 사용불가)
    max15: function (value) {
      if (value.length <= 15) {
        return true;
      }
    },
    specialCheck: function (value) {
      var specialCheck = /[`~!@#$%^&*ㄱ-힣|\\\'\";:\/?]/gi;
      //if(specialCheck.test(this.memberId)) {
      if (specialCheck.test(value)) {
        return "특수문자 사용이 불가합니다.";
      } else {
        return true;
      }
    },
    //비밀번호 더블체크 관련
    required: function (value) {
      if (value) {
        return true;
      } else {
        return "필수입력사항입니다.";
      }
    },
    min6: function (value) {
      if (value.length >= 6) {
        return true;
      } else {
        return "6자 이상으로 입력하세요.";
      }
    },
    matchingPasswords: function () {
      if (this.password === this.password1) {
        return true;
      } else {
        return "비밀번호가 일치하지 않습니다.";
      }
    },
    //이메일 유효성 관련
    checkEmail() {
      // 이메일 형식 검사
      const validateEmail =
        /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/;
      if (!validateEmail.test(this.email) || !this.email) {
        this.valid.email = true;
        this.emailHasError = true;
        return;
      }
      this.valid.email = false;
      this.emailHasError = false;
    },
    max30: function (value) {
      if (value.length <= 30) {
        return true;
      }
    },
  },
  computed: {
    //아이디 체크 rule
    idRule: function () {
      if (this.memberId === "") {
        // 아이디는 필수 입력사항
        this.successId = false;
        return "";
      } else if (
        this.max15(this.memberId) === true &&
        this.specialCheck(this.memberId) === true
      ) {
        //  최대 15자 이내어야한다
        this.successId = true;
        return "mdi-check";
      } else {
        // 나머지 처리
        this.successId = false;
        return "mdi-Close";
      }
    },
    //첫번째 비밀번호 rule
    passRule: function () {
      if (this.password === "") {
        // field is empty
        this.successPass = false;
        return "";
      } else if (this.min6(this.password) === true) {
        // password ok
        this.successPass = true;
        return "mdi-check";
      } else {
        // password wrong
        this.successPass = false;
        return "mdi-close";
      }
    },
    passRule1: function () {
      if (this.password1 === "") {
        // field is empty
        this.successPass1 = false;
        return "";
      } else if (this.matchingPasswords() === true) {
        // password ok
        this.successPass1 = true;
        return "mdi-check";
      } else {
        // password wrong
        this.successPass1 = false;
        return "mdi-close";
      }
    },
    emailRules: function () {
      if (this.email === "") {
        // field is empty
        this.successMaill = false;
        return "";
      } else if (this.max30(this.email) === true) {
        // password ok
        this.successMaill = true;
        return "mdi-check";
      } else {
        // password wrong
        this.successMaill = false;
        return "mdi-close";
      }
    },
  },
};
</script>

<style scoped>
#join {
  padding-top: 20px;
  min-height: 100vh;
  margin: 0 auto;
}

form {
  max-width: 400px;
  margin: 0 auto;
}

#idBox {
  width: 220px;
}

#checkBtn {
  margin-bottom: 10px;
  border-radius: 18px;
}

.sendMailbtn,
.inputNumCheck {
  margin-bottom: 10px;
}

.submitBtn {
  margin-top: 50px;
  color: black;
  width: 450px;
  border-radius: 18px;
}
</style>
