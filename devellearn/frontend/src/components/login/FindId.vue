<template>
  <div id="findIdd" style="min-height: 100vh">
    <form>
      <h3 style="margin-bottom: 5%">아이디 찾기</h3>
      <div id="inputForm">
        <v-row align="center">
          <v-col cols="12" md="6">
            <v-container fluid>
              <v-text-field
                v-model="name"
                id="name"
                name="name"
                label="이름"
                required
              >
              </v-text-field>
            </v-container>
          </v-col>
        </v-row>

        <v-container>
          <v-row align="center">
            <v-col cols="12" md="6">
              <v-text-field
                v-model="phone"
                id="phone"
                name="phone"
                :counter="11"
                label="전화번호"
                required
                @keypress.enter="findId"
              >
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </form>

    <v-btn
      class="submit"
      id="submit"
      rounded
      color="primary"
      dark
      @click="findId"
      >아이디 찾기</v-btn
    >

    <!-- 아이디찾기 결과 모달창 -->
    <b-modal id="modal-1" centered @ok="handleOk" title="아이디 찾기 결과">
      <p class="my-4" v-for="item in memberList">
        {{ item }}
      </p>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      phone: "",
      memberList: [],
      memberId: "",
    };
  },
  methods: {
    findId() {
      this.$axios

        .get("/api/findId", {
          params: {
            name: this.name,
            phone: this.phone,
          },
        })
        .then((res) => {
          if (res.data != 0) {
            //VO를 담고있음
            for (let i = 0; i < res.data.length; i++) {
              var splitId = res.data[i].memberId;
              splitId = splitId.replace(/(?<=.{3})./, "*");
              splitId = splitId.replace(/(?<=.{4})./, "*");
              splitId = splitId.replace(/(?<=.{7})./, "*");

              this.memberList.push(splitId);
            }
            this.$bvModal.show("modal-1");
          } else {
            Swal.fire({
              position: "center",
              icon: "warning",
              title: "일치하는 정보가 없습니다.",
              showConfirmButton: false,
              timer: 1300,
            });
            this.name = "";
            this.phone = "";
          }
        });
    },
    //모달 버튼 ok버튼 클릭 시, 로그인 화면으로 이동
    handleOk(bvModalEvent) {
      this.$router.push({
        path: "/login",
      });
    },
  },
};
</script>

<style scoped>
#findIdd {
  padding-top: 150px;
  height: 100vh;
  width: 50%;
  margin: 0 auto;
}

#inputForm {
  width: 800px;
  margin: 0 auto;
}

form {
  max-width: 400px;
  margin: 0 auto;
  padding-top: 50px;
}

#submit {
  margin: 20px auto;
  padding: 20px;
}
</style>
