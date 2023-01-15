<template>
  <div id="join" style="min-height: 150vh">
    <validation-observer ref="observer" v-slot="{ invalid }">
      <form>
        <h3>회원가입</h3>
        <h6 style="margin-top: 10px">[기업회원]</h6>
        <div style="margin-top: 30px; text-align: right; font-size: small">
          <router-link to="/join">▷개인회원가입</router-link>&nbsp;&nbsp;
        </div>

        <v-container fluid>
          <v-row align="center">
            <!-- 1. 아이디(comId) -->
            <v-col class="d-flex">
              <validation-provider
                v-slot="{ errors }"
                name="comId"
                rules="[required, max15, specialCheck]"
              >
                <div id="idBox">
                  <v-text-field
                    v-model="comId"
                    :counter="15"
                    :rules="user_id_rule"
                    :error-messages="errors"
                    label="아이디"
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
                class="btn"
                type="button"
                dark
                @click="checkId"
                required
              >
                중복확인</v-btn
              >
            </v-col>
          </v-row>
        </v-container>

        <!-- 2. 비밀번호 (password)-->

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
                  label="비밀번호"
                  prepend-icon="mdi-lock-outline"
                  type="password"
                  :rules="[required, min6]"
                  :counter="6"
                  required
                >
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
                  label="비밀번호 확인"
                  prepend-icon="mdi-lock-outline"
                  type="password"
                  :rules="[required, min6, matchingPasswords]"
                  :counter="6"
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

        <!-- 3. 사업자등록번호(comNo) -->
        <v-container fluid>
          <validation-provider
            v-slot="{ errors }"
            name="comNo"
            rules="required"
          >
            <v-text-field
              v-model="comNo"
              :error-messages="errors"
              label="사업자등록번호"
              :rules="numberRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 4. 기업명(name) -->
        <v-container fluid>
          <validation-provider v-slot="{ errors }" name="name" rules="required">
            <v-text-field
              v-model="name"
              :error-messages="errors"
              label="기업명"
              :rules="comNameRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 5. 대표자 이름(ceoName) -->
        <v-container fluid>
          <validation-provider
            v-slot="{ errors }"
            name="ceoName"
            rules="required"
          >
            <v-text-field
              v-model="ceoName"
              :error-messages="errors"
              label="대표자 이름"
              :rules="nameRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 6. 설립일자 (estDate) -->
        <v-container fluid>
          <validation-provider
            v-slot="{ errors }"
            name="estDate"
            rules="required"
          >
            <v-text-field
              v-model="estDate"
              :error-messages="errors"
              label="설립일자"
              :rules="dateRule"
              placeholder="YYYY-DD-MM"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 7. 주소 (addr) https://codelist.tistory.com/59 -->

        <div id="add_first">
          <div id="idBox">
            <input type="text" id="roadAddress" placeholder="도로명주소" />
          </div>
          <div>
            <button id="addrBtn" @click="search()">우편번호 찾기</button>
          </div>
        </div>
        <!--onclick이 아니라 @click으로 바꿔야한다. -->
        <input type="hidden" id="postcode" placeholder="우편번호" />
        <input type="hidden" id="jibunAddress" placeholder="지번주소" />
        <span
          id="guide"
          style="color: #000; display: none; font-size: small"
        ></span>

        <div id="add_sec">
          <input type="text" id="detailAddress" placeholder="상세주소" />
        </div>

        <input type="hidden" id="extraAddress" placeholder="참고항목" />

        <!-- 8.담당자 (manager) -->
        <v-container fluid>
          <validation-provider
            v-slot="{ errors }"
            name="manager"
            rules="required"
          >
            <v-text-field
              v-model="manager"
              :error-messages="errors"
              label="담당자"
              :rules="nameRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 9. 전화번호 (phone)-->

        <v-container fluid>
          <validation-provider
            v-slot="{ errors }"
            name="phone"
            rules="required"
          >
            <v-text-field
              v-model="phone"
              :error-messages="errors"
              label="담당자 전화번호"
              :rules="phoneRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 10.부서명 (dept) -->
        <v-container fluid>
          <validation-provider v-slot="{ errors }" name="dept" rules="required">
            <v-text-field
              v-model="dept"
              :error-messages="errors"
              label="부서명"
              :rules="nameRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 11. 직책 (position) -->
        <v-container fluid>
          <validation-provider
            v-slot="{ errors }"
            name="position"
            rules="required"
          >
            <v-text-field
              v-model="position"
              :error-messages="errors"
              label="직책"
              :rules="nameRule"
              required
            >
            </v-text-field>
          </validation-provider>
        </v-container>

        <!-- 12. 이메일 https://kimvampa.tistory.com/105?category=771727 -->
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
                  :counter="30"
                  :error-messages="errors"
                  label="email"
                  :rules="emailRule"
                  required
                  @keypress.enter="joinComplete"
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

            <!-- 이메일 인증의 경우, 일단 주석처리 진행 (이메일 인증 버튼 및 인증번호 입력란 주석) -->
            <!-- <v-col class="d-flex">
    <v-btn class="btn" rounded color="primary" dark @click="mail_check_button">이메일인증</v-btn></v-col>  -->
          </v-row>
          <!-- <v-text-field
          v-model="email_code"
          :counter="30"
          :error-messages="errors"
          label="email_code"
          required></v-text-field> -->
        </v-container>

        <!-- <v-btn class="mr-4" type="button" :disabled="invalid" @click="joinComplete">submit</v-btn>&nbsp; -->
        <v-btn class="mr-4" type="button" @click="joinComplete">submit</v-btn
        >&nbsp;
        <!-- <v-alert color="blue" type="success">회원가입이 완료되었습니다.</v-alert> -->
        <v-btn @click="clear">clear</v-btn>
      </form>
    </validation-observer>
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

export default {
  components: {
    ValidationProvider,
    ValidationObserver,
  },
  data: () => ({
    // 입력값
    comId: "",
    comNo: "",
    ceoName: "",
    estDate: "",
    manager: "",
    phone: "",
    dept: "",
    position: "",
    addr1: "",
    addr2: "",

    //아이디 중복체크 여부
    vaildId: false,
    successId: false,

    //비밀번호 체크 관련
    password: "",
    password1: "",
    successPass: false,
    successPass1: false,

    //이메일 체크 관련
    email: "",
    successMail: false,

    //이름체크 관련
    name: "",
    successName: false,

    //도로명주소 관련

    //폰번호 관련
    contact: null,
    user_id_rule: [
      (v) => !!v || "필수 입력사항입니다.",
      (v) => /^[a-zA-Z0-9]*$/.test(v) || "영문+숫자만 입력 가능합니다.",
      (v) => !(v && v.length >= 15) || "15자 이상 입력할 수 없습니다.",
    ],

    emailRule: [
      (v) => !!v || "이메일을 입력해주세요.",
      (v) => {
        const replaceV = v.replace(/(\s*)/g, "");
        const pattern =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
        return pattern.test(replaceV) || "이메일 형식으로 입력해주세요";
      },
    ],
    nameRule: [
      (v) => !!v || "필수 입력사항입니다.",
      (v) => /^[가-힣a-zA-Z]+$/.test(v) || "한글/영문만 입력 가능합니다.",
    ],
    comNameRule: [
      (v) => !!v || "필수 입력사항입니다.",
      (v) => /^[0-9가-힣a-zA-Z]+$/.test(v) || "한글/영문/숫자 입력 가능합니다.",
    ],
    numberRule: [
      (v) => !!v || "필수 입력사항입니다.",
      (v) => /^[0-9]+$/.test(v) || "숫자만 입력 가능합니다.",
    ],
    dateRule: [
      (v) => !!v || "필수 입력사항입니다.",
      (v) =>
        /^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/.test(v) ||
        "형식에 맞게 작성해주세요.",
    ],

    phoneRule: [
      (v) => !!v || "필수 입력사항입니다.",
      (v) => /^\d{2,3}-\d{3,4}-\d{4}$/.test(v) || "형식에 맞게 작성해주세요.",
    ],

    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => v.length >= 6 || "Min 6 characters",
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
  }),

  methods: {
    submit() {
      this.$refs.observer.validate();
    },
    clear() {
      this.comId = "";
      this.name = "";
      this.phone = "";
      this.email = "";
      this.password = "";
      this.password1 = null;
      this.$refs.observer.reset();
    },
    //아이디 중복체크
    checkId() {
      if (this.comId == "") {
        console.log(this.comId);
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
          .post("/api/comIdCheck", {
            comId: this.comId,
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

              this.comId = "";
            }
          });
      }
    },
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
      } else {
        this.$axios
          .post("/api/companyJoin", {
            comId: this.comId,
            comNo: this.comNo,
            password: this.password,
            name: this.name,
            ceoName: this.ceoName,
            estDate: this.estDate,
            manager: this.manager,
            phone: this.phone,
            email: this.email,
            dept: this.dept,
            position: this.position,
            addr:
              document.getElementById("roadAddress").value +
              "" +
              document.getElementById("detailAddress").value,
          })
          .then((res) => {
            if (res.data != null) {
              // alert('회원가입이 완료되었습니다!');
              this.$router.push({
                path: "/jobMain",
              });
            } else {
              //alert 창 고쳐야함★
              //alert('필수 정보를 입력하세요!');

              Swal.fire({
                position: "center",
                icon: "info",
                toast: true,
                title: "필수 정보를 입력하세요",
                showConfirmButton: false,
                timer: 1000,
              });

              component.$router.go(0);
            }
          });
      }
    },

    //아이디 유효성 관련 (15자 이내, 특수문자 사용불가)
    max15: function (value) {
      if (value.length <= 15) {
        return true;
      }
    },
    specialCheck: function (value) {
      var specialCheck = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
      //if(specialCheck.test(this.comId)) {
      if (specialCheck.test(value)) {
        return "특수문자 못씀";
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

    // 메소드 추가

    search() {
      //@click을 사용할 때 함수는 이렇게 작성해야 한다.
      new window.daum.Postcode({
        oncomplete: (data) => {
          //function이 아니라 => 로 바꿔야한다.
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var roadAddr = data.roadAddress; // 도로명 주소 변수
          var extraRoadAddr = ""; // 참고 항목 변수

          // 법정동명이 있을 경우 추가한다. (법정리는 제외)
          // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          // 건물명이 있고, 공동주택일 경우 추가한다.
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraRoadAddr +=
              extraRoadAddr !== ""
                ? ", " + data.buildingName
                : data.buildingName;
          }
          // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
          if (extraRoadAddr !== "") {
            extraRoadAddr = " (" + extraRoadAddr + ")";
          }

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById("postcode").value = data.zonecode;
          document.getElementById("roadAddress").value = roadAddr;
          document.getElementById("jibunAddress").value = data.jibunAddress;

          // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
          if (roadAddr !== "") {
            document.getElementById("extraAddress").value = extraRoadAddr;
          } else {
            document.getElementById("extraAddress").value = "";
          }

          var guideTextBox = document.getElementById("guide");
          // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
          if (data.autoRoadAddress) {
            var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
            guideTextBox.innerHTML = "(예상 도로명 주소 : " + expRoadAddr + ")";
            guideTextBox.style.display = "block";
          } else if (data.autoJibunAddress) {
            var expJibunAddr = data.autoJibunAddress;
            guideTextBox.innerHTML = "(예상 지번 주소 : " + expJibunAddr + ")";
            guideTextBox.style.display = "block";
          } else {
            guideTextBox.innerHTML = "";
            guideTextBox.style.display = "none";
          }
        },
      }).open();
    },
  },
  computed: {
    //아이디 체크 rule
    idRule: function () {
      if (this.comId === "") {
        // 아이디는 필수 입력사항
        this.successId = false;
        return "";
      } else if (
        this.max15(this.comId) === true &&
        this.specialCheck(this.comId) === true
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
      } else if (
        this.min6(this.password1) === true &&
        this.matchingPasswords() === true
      ) {
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
    nameRules: function () {
      if (this.name === "") {
        // 아이디는 필수 입력사항
        this.successName = false;
        return "";
      } else if (this.specialCheck(this.name) === true) {
        //  특수문자 사용불가
        this.successName = true;
        return "mdi-check";
      } else {
        // 나머지 처리
        this.successName = false;
        return "mdi-Close";
      }
    },
  },
};
</script>

<style scoped>
#join {
  padding-top: 20px;
  height: 100vh;
  margin: 0 auto;
}

form {
  max-width: 400px;
  margin: 0 auto;
}

#idBox {
  width: 240px;
  padding: 0px;
}

#roadAddress {
  border-bottom: grey 1px solid;
  border-left: medium none;
  border-right: medium none;
  border-top: medium none;
  font-size: 11pt;
  margin-left: 15px;
  margin-right: 0px;
  margin-top: 20px;
  width: 190px;
  padding-right: 0px;
  width: 230px;
}

#detailAddress {
  border-bottom: grey 1px solid;
  border-left: medium none;
  border-right: medium none;
  border-top: medium none;
  font-size: 11pt;
  margin-left: 15px;
  margin-top: 40px;
  margin-bottom: 20px;
  width: 370px;
}

#add_first {
  display: flex;
}

#add_sec {
  width: 160px;
  margin-top: 20px;
}

#addrBtn {
  border: rgb(0, 0, 0) 1px solid;
  padding: 10px 12px;
  font-size: 13px;
  border-radius: 5px;
  background-color: black;
  color: white;
  box-shadow: 1px 1px 1px grey;
  margin-left: 30px;
}

.btn {
  margin-bottom: 15px;
  background-color: black;
}

.d-flex col {
  //margin-right: -80px;
}
</style>
