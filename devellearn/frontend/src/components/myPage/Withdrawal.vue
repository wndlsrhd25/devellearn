<template>
  <div id="Withdrawal" style="min-height: 100vh">
    <div id="subTitle">
      <h3 style="color: #00336d">회원 탈퇴</h3>
      <hr />
    </div>

    <div id="category">탈퇴 안내 사항</div>
    <!-- ●●●●●부트스트랩 -->
    <div id="message">
      <h7>서비스에 만족하지 못하셨나요? 탈퇴하기 전에 먼저 개선 요청을 해보시는
        건 어떨까요? <br />
        그래도 탈퇴하시겠다면 탈퇴 전에 아래 유의사항을 꼭 읽어주세요! <br />

        🙇🏻‍♂️ 감사합니다 🙇🏻‍♀️ <br />

        1. 탈퇴 시 계정과 관련된 모든 권한이 사라지며 복구할 수 없습니다. <br />
        2.
        <span style="color: red">직접 작성한 콘텐츠(동영상, 게시물, 댓글 등)는 자동으로 삭제되지
          않으며, 만일 삭제를 원하시면 탈퇴 이전에 삭제가 필요합니다.</span>
        <br />
        3. 탈퇴 후 동일한 메일로 재가입이 불가능하며, 탈퇴한 계정과 연동되지
        않습니다. <br />
        4. 탈퇴 후 연동된 소셜 계정 정보도 사라지며 소셜 로그인으로 기존 계정
        이용이 불가능합니다. <br />
        5. 현재 비밀번호를 입력하고 탈퇴하기를 누르시면 위 내용에 동의하는
        것으로 간주됩니다.</h7>

      <div id="pwBox">
        <div>
          <b-form-input class="pwInput" type="password" 
          id="password" name="password" placeholder="비밀번호를 입력하세요."
          style="width: 400px; text-align: center"></b-form-input>
        </div>
        <div id="checkPw">
          <b-button style="width: 200px; margin-left: 15px" @click="checkPassword">확인</b-button>
        </div>
      </div>
      <div id="goWithdrawal">
        <b-button v-if="pwSuccess" style="width: 600px" @click="withdrawal">DEVELLEARN 탈퇴하기</b-button>
      </div>
    </div>
  </div>

  <!-- ●●●●●부트스트랩 -->
</template>
<script>
  import SideBar from "../common/SideBar.vue";

  export default {
    components: {
      SideBar,
    },
    data() {
      return {
        pwSuccess: false,
        password: "",
      };
    },
    methods: {
      withdrawal() {
        this.$swal
          .fire({
            title: "정말 탈퇴하시겠습니까?",
            text: "탈퇴 시 서비스를 이용할 수 없습니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            cancelButtonText: "취소",
            confirmButtonText: "확인",
          })
          .then((result) => {
            if (result.isConfirmed) {

              this.$axios
                .put("/api/withdrawal/" + this.$store.state.userStore.id)
                .then((res) => {
                  Swal.fire({
                    position: "center-center",
                    icon: "success",
                    toast: true,
                    title: "탈퇴처리가 완료되었습니다.",
                    showConfirmButton: false,
                    timer: 1000,
                  }).then((result) => {

                    this.$store.commit("userStore/logout");
                    this.$router
                      .push({
                        path: "/",
                      });
                  })
                });

              ///////

            }
          });

      },
      checkPassword() {
        this.$axios
          .post("/api/login", {
            memberId: this.$store.state.userStore.id,
            password: document.getElementById("password").value,
          })
          .then((res) => {
            if (res.data.password != null) {

              Swal.fire({
                position: "center-center",
                icon: "success",
                toast: true,
                title: "회원정보가 일치합니다.",
                showConfirmButton: false,
                timer: 1000,
              });
              this.pwSuccess = true;
            } else {
              Swal.fire({
                position: "center-center",
                icon: "success",
                toast: true,
                title: "로그인 정보를 다시 확인하세요!",
                showConfirmButton: false,
                timer: 1000,
              }).then((result)=> {
                component.$router.go(0);


              });


            }
          });
      },
    },
  };
</script>
<style scoped>
  #subTitle {
    text-align: center;
  }

  #category {
    text-align: left;
    margin-left: 50px;
    font-weight: bold;
  }

  #message {
    padding-top: 50px;
    width: 1200px;
    text-align: center;
    height: 450px;
  }

  #pwBox {
    width: 600px;
    padding: 100px;
    display: flex;
  }

  .pwInput {
    width: 500px;
    margin-left: 200px;
  }

  #Withdrawal {
    padding-top: 50px;
    width: 900px;
    text-align: center;
    height: 150px;
  }
</style>