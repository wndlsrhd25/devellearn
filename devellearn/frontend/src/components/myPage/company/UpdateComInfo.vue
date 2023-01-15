<template>
  <div id="UpdateInfo" style="min-height: 100vh">
    <!--https://bootstrap-vue.org/docs/components/form  -->
    <div id="subTitle">
      <h3 style="color: #00336d">기업회원 개인정보 수정</h3>
      <hr />
    </div>

    <!-- ●●●●●부트스트랩 -->

    <form id="infoForm">
      <div id="inputBox">


        <v-container>
          <div id="Label">
            <v-label>새 비밀번호</v-label>
          </div>
          <v-row>
            <v-col cols="6">
              <!-- password 첫번째 입력-->

              <!-- 이 입력값에 대해 2가지 조건이 맞으면  validationPW 을 true 반환 -->
              <v-text-field v-model="user_pw" prepend-icon="mdi-lock-outline" type="password" :rules="user_pw_rule"
                :counter="6">
              </v-text-field>
            </v-col>
          </v-row>

          <div id="Label">
            <v-label>새 비밀번호 확인</v-label>
          </div>

          <v-row>
            <v-col cols="6">
              <!-- password 두번째 입력-->

              <!-- 이 입력값에 대해 3가지 조건이 맞으면  validationPW2 true 반환 -->
              <v-text-field v-model="user_pw_chk" id="newPw" prepend-icon="mdi-lock-outline" type="password"
                :rules="user_pw_rule2" :counter="6">
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>

        <hr />

        <v-container fluid>
          <v-row>
            <v-col cols="6">
              <!-- 8.담당자 (manager) -->
              <div id="Label">
                <v-label>담당자</v-label>
              </div>
              <v-text-field v-model="selectData.manager" id="manager" name="manager" :error-messages="errors"
                :rules="nameRule" required>
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>

        <hr />


        <v-container>

          <div id="Label">
            <v-label>Phone</v-label>
          </div>
          <v-row>
            <v-col cols="6">

              <v-text-field v-model="selectData.phone" id="phone" name="phone" :counter="11" :rules="phoneRule"
                required>
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>

        <hr />

        <!-- 10.부서명 (dept) -->
        <v-container fluid>
          <div id="Label">
            <v-label>부서명</v-label>
          </div>
          <v-row>
            <v-col cols="6">
              <v-text-field v-model="selectData.dept" id="dept" name="dept" :error-messages="errors" :rules="nameRule"
                required>
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>

        <!-- 11. 직책 (position) -->
        <v-container fluid>

          <div id="Label">
            <v-label>직책</v-label>
          </div>
          <v-row>
            <v-col cols="6">
              <v-text-field v-model="selectData.position" id="position" name="position" :error-messages="errors"
                :rules="nameRule" required>
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>


        <v-container fluid>
          <div id="Label">
            <v-label>email</v-label>
          </div>

          <v-row align="center">
            <v-col>
              <!-- :error-messages="errors" -->
              <v-text-field v-model="testEmail" id="email" name="email" :rules="emailRule" @change="changeEmail">
              </v-text-field>
            </v-col>

            <v-col class="d-flex">
              <v-btn class="sendMailbtn" id="email_checkBtn2" rounded color="primary" dark @click="checkMailSend">이메일인증
              </v-btn>
            </v-col>
          </v-row>

          <div id="Label">
            <v-label>email_code</v-label>
          </div>

          <v-row align="center">
            <v-col>
              <v-text-field v-model="email_code" id="email_code">
              </v-text-field>
            </v-col>
            <v-col class="d-flex">
              <v-btn class="inputNumCheck" id="email_checkBtn" rounded color="primary" dark @click="checkMailNum">인증번호입력
              </v-btn>
            </v-col>
          </v-row>
        </v-container>

        <hr />

        <b-button style="width: 80px" @click="updateInfo()">수정</b-button>
      </div>
    </form>

    <!-- ●●●●●부트스트랩 -->
  </div>
</template>
<script>
  import {
    required,
    digits,
    email,
    min,
    max,
    regex,
  } from "vee-validate/dist/rules";
  import {
    extend,
    ValidationObserver,
    ValidationProvider,
    setInteractionMode,
  } from "vee-validate";

  extend("digits", {
    ...digits,
    message: "{_field_} needs to be {length} digits. ({_value_})",
  });

  export default {
    components: {
      ValidationProvider,
      ValidationObserver,
    },
    data() {
      return {
        selectData: {},
        testEmail: "",

        email: "",
        email_code: "",
        emailCode: "",

        //값이 변하면 false로 바뀌고, 인증 완료하면 true로 바뀜
        codeCheck: true,
        // 이메일 유효성이 맞으면 true
        validationMail: false,

        //비밀번호 유효성이 맞으면 true,
        validationPW: false,
        validationPW2: false,

        pwCheck: true,
        user_pw: "",
        user_pw_chk: "",

        validationPhone: true,

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

        phoneRule: [
          (v) => {
            const replaceV = v.replace(/(\s*)/g, "");
            const pattern = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;
            if (pattern.test(replaceV) == true) {
              this.validationPhone = true;
              return "";
            } else {
              this.validationPhone = false;
              return "-제외 숫자로만 입력해주세요";
            }
          },
        ],
      };
    },

    created() {
      this.selectComMember();
      // console.log(document.getElementById("email").value);
      console.log("this.validationPW", this.validationPW);
      console.log("this.validationPW2", this.validationPW2);
    },

    methods: {
      // 비밀번호 전송 전 체크
      checkPw() {
        console.log("user_pw", this.user_pw);
        console.log("user_pw_chk", this.user_pw_chk);
        // 비밀번호 변경 안할 시
        if (this.user_pw == "" && this.user_pw_chk == "") {
          console.log("비밀번호 입력 두칸 다 비어있음");
          return true;
        }
        // 비밀번호 변경 할 시
        else {
          // 유효성 검사 두개 다만족
          if (this.validationPW == true && this.validationPW2 == true) {
            return true;
          }
          // 유효성 검사 하나라도 실패
          else {
            return false;
          }
        }
      },

      //로그인 정보로 회원정보 가져오기
      selectComMember() {
        var comId = this.$store.state.userStore.id;
        console.log("로그인정보" + comId);

        this.$axios
          .get("/api/selectComMember/" + comId)
          .then((res) => {
            console.log(res);
            this.selectData = res.data;
            console.log("이거" + this.selectData);
            this.testEmail = this.selectData.email;
            this.$store.commit("userStore/setUserData", res.data);
          })
          .catch((err) => {
            console.log(err);
          });
      },

      //이메일 인증 메일 발송
      checkMailSend() {
        //유효성 검사(validationMail가 true여야만 메일 발송)
        //값이 안바뀌어도 이메일 인증할 필요 없는데?
        if (this.validationMail == true) {
          console.log(document.getElementById("email").value);

          this.$axios
            .get("/api/emailSending/" + document.getElementById("email").value)

            .then((resp) => {
              console.log(resp); //data 안에서 선언한 변수에 할당 resp.data
              this.realCode = resp.data;
              alert(this.realCode);
              const inputTarget = document.getElementById("email");
              inputTarget.disabled = true;
            });
        }
      },

      updateInfo() {
        //this.codeCheck가 true일때만 등록 가능하게 해야함
        if (this.codeCheck == true) {
          if (this.checkPw()) {
            if (this.validationPhone == true) {
              const formData = new FormData(document.getElementById("infoForm"));
              formData.append("comId", this.$store.state.userStore.id);
              formData.append("password", document.getElementById("newPw").value);

              console.log(formData);

              let sendData = {};
              formData.forEach(function (value, key) {
                sendData[key] = value;
                console.log("sendData", sendData);
              });

              console.log("확인" + JSON.stringify(sendData));

              this.$axios.put("/api/updateComInfo", sendData).then((resp) => {
                console.log(resp);
                this.selectComMember();
                alert("기업회원 정보 수정 완료!");
              });
            } else {
              alert("전화번호 양식을 확인하세요.");
            }
          } else {
            alert("비밀번호를 확인하세요.");
          }
        } else {
          alert("이메일 인증을 완료하세요");
        }
      },

      //인증번호 맞는지 확인
      checkMailNum: function () {
        var code = document.getElementById("email_code").value;
        // code 랑 코드 담은 변수랑 같은지 확인
        if (code == this.realCode) {
          alert("인증완료!");

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
          alert("틀렸음");
          const codeTarget = document.getElementById("email_code");
          codeTarget = "";
        }
      },

      //아이디 값이 변하면 다시 체크할 수 있도록 상태를 false로 변경
      changeEmail() {
        console.log("this.selectData.emaill ", this.selectData.email);
        console.log("this.testEmail", this.testEmail);
        if (this.selectData.email == this.testEmail) {
          this.codeCheck = true;
        } else {
          //유효성 검사 다시 하라고 false로 바꿔줘야됨
          this.codeCheck = false;
        }
      },
    },
  };
</script>
<style scoped>
  #UpdateInfo {
    width: 900px;
  }

  #subTitle {
    margin-top: 20px;
    text-align: left;
    margin-left: 50px;
    font-size: x-large;
    color: rgb(0, 0, 158);
  }

  .input-1,
  #input-4 {
    width: 504.83px;
    margin-right: 5px;
  }

  #inputBox {
    width: 600px;
    margin-left: 55px;
  }

  .test1 {
    display: flex;
  }

  .test2 {
    height: 38px;
    margin-top: 32px;
  }

  .sendMailbtn {
    height: 38px;
    margin-top: 32px;
  }

  #input-group {
    text-align: left;
  }

  #lastbtn {
    margin-top: 10px;
  }



  .v-application .error--text {
    color: black !important;
    caret-color: black !important;
  }

  #Label {
    text-align: left;
    font-size: 5px;
    margin-bottom: 0px;
  }

  input#dept {
    width: 350px;
  }
</style>