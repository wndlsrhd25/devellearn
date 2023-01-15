<template>
  <div id="findIdd" style="min-height: 100vh">
    <form>
      <h3 style="margin-bottom: 5%">아이디 찾기</h3>
      <h6 style="margin-top: 10px">-기업회원-</h6>

      <div id="inputForm">
        <v-row align="center">
          <v-col cols="12" md="6">
            <v-container fluid>
              <v-text-field
                v-model="name"
                id="name"
                name="name"
                label="기업명"
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
                label="담당자 전화번호"
                required
                @keypress.enter="findComId"
              >
              </v-text-field>
            </v-col>
          </v-row>
        </v-container>

        <!-- 아이디찾기 결과 모달창 -->
        <b-modal id="modal-1" centered @ok="handleOk" title="아이디 찾기 결과">
          <p class="my-4">
            {{ comId }}
          </p>
        </b-modal>
      </div>
    </form>

    <v-btn
      class="submit"
      id="submit"
      rounded
      color="primary"
      dark
      @click="findComId"
      >아이디 찾기</v-btn
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      phone: "",
      comId: "",
    };
  },
  methods: {
    findComId() {
      this.$axios
        .get("/findComId", {
          params: {
            name: this.name,
            phone: this.phone,
          },
        })
        .then((res) => {
          if (res.data != 0) {
            this.comId = res.data.comId;

            var splitId = this.comId;
            splitId = splitId.replace(/(?<=.{3})./, "*");
            splitId = splitId.replace(/(?<=.{4})./, "*");
            splitId = splitId.replace(/(?<=.{7})./, "*");

            this.comId = splitId;
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
        path: "/companyLogin",
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
