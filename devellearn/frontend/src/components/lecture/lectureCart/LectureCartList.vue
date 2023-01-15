<template>
  <div id="lc_wrapper">
    <div class="lc_header_list">
      <div class="lc_header">
        <!-- 헤더 -->
        <div>
          <h2 style="margin-bottom: 20px">수강바구니</h2>
        </div>
        <div>
          <b-button @click="removeAllCart">전체삭제</b-button>
        </div>
      </div>
      <!-- 헤더 끝 -->

      <!-- 장바구니 리스트 -->
      <template v-for="cart in cartList">
        <template>
          <div class="lc_list" :key="cart.cartNo">
            <v-checkbox
              v-model="selected"
              :value="cart"
              class="cart_checkbox"
            ></v-checkbox>
            <v-img
              :src="'/api/images/lecture/' + cart.imgSrc"
              alt="Fluid image"
              class="lc_list_img"
            ></v-img>
            <div class="lc_list_info">
              <h4>{{ cart.title }}</h4>
              <p>{{ cart.lecturer }}</p>
              <p>{{ cart.learnerNum }} 명이 수강하고 있습니다!</p>
            </div>
            <div class="lc_list_price">
              <b-button squared @click="removeCart(cart.cartNo)">ㅡ</b-button>
              <h5 style="width: 100px">
                {{ cart.price.toLocaleString("ko-KR") }}원
              </h5>
            </div>
          </div>
        </template>
      </template>
      <!-- 장바구니 리스트 끝 -->
    </div>

    <!-- 결제 픽스창 -->
    <aside>
      <div class="summary">
        <div class="summary-subtotal">
          <div>구매자 정보</div>
          <div>이름 : {{ $store.state.userStore.user.name }}</div>
          <div>이메일 : {{ $store.state.userStore.user.email }}</div>
          <div>휴대폰번호 : {{ $store.state.userStore.user.phone }}</div>
          <!-- <div class="subtotal-title">Subtotal</div> -->
        </div>
        <div class="summary-delivery">
          <div>선택된 강의</div>
          <div v-for="item in selected" :key="item" class="title_price">
            <div>{{ item.title }}</div>
            <div>{{ item.price.toLocaleString("ko-KR") }} 원</div>
          </div>
        </div>
        <div class="summary-total">
          <div class="total-title">Total</div>
          <div class="total-value final-value" id="basket-total">
            {{ getImpAmount().toLocaleString("ko-KR") }} 원
          </div>
        </div>
        <div class="summary-checkout">
          <b-button @click="requestPay" variant="success" block
            >결제하기</b-button
          >
        </div>
      </div>
    </aside>
    <!-- 결제 픽스창 끝 -->
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      // 장바구니 리스트 담을 배열
      cartList: [],
      selected: [],
    };
  },
  created: function () {
    // 장바구 리스트 전체 조회
    this.getCartList();
  },
  methods: {
    // 장바구니 리스트 전체조회 axios
    getCartList: function () {
      this.$axios
        .get("/api/cart/" + this.$store.state.userStore.user.memberId)
        .then((resp) => {
          console.log(resp);
          this.cartList = resp.data;
          // 초기 화면 - 장바구니 리스트는 전체 선택되게
          this.selected = resp.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 장바구니 삭제 axios
    removeCart: function (cartNo) {
      this.$axios
        .delete("/api/deleteCart/" + cartNo)
        .then((resp) => {
          // cartList 에서 삭제 로직 - view 단에서 삭제
          if (resp.data != 0) {
            for (let i = 0; i < this.cartList.length; i++) {
              if (cartNo == this.cartList[i].cartNo) {
                this.cartList.splice(i, 1);
              }
            }
            for (let i = 0; i < this.selected.length; i++) {
              if (cartNo == this.selected[i].cartNo) {
                this.selected.splice(i, 1);
              }
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 장바구니 전체 삭제 axios
    removeAllCart: function () {
      //////////////////////////////////////////////////삭제(경고 빨간 아이콘) 알림창
      this.$swal
        .fire({
          title: "장바구니를 전체삭제합니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            //여기에다가 삭제시 axios 넣으면 됨
            this.$axios
              .delete(
                "/api/deleteAllCart/" +
                  this.$store.state.userStore.user.memberId
              )
              .then((resp) => {
                console.log(resp);
                if (resp.data != 0) {
                  this.cartList.splice(0, this.cartList.length);
                  this.selected.splice(0, this.cartList.length);
                }
              })
              .catch((error) => {
                console.log(error);
              });
            this.$swal.fire("삭제완료", "", "success");
          }
        });
    },
    // iamport 결제 api 사용
    requestPay: function () {
      // 장바구니가 비어있는 경우
      if (this.cartList.length == 0) {
        //////////////////////////////// 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "구매할 강의가 없습니다.",
          showConfirmButton: true,
          // timer: 1000
        });
        return;
      }
      if (this.selected.length == 0) {
        //////////////////////////////// 알림창
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "구매할 강의를 선택하세요.",
          showConfirmButton: true,
          // timer: 1000
        });
      } else {
        // 내 가맹점 코드
        IMP.init("imp34244854");
        // IMP.request_pay(param, callback); //결제창 호출
        IMP.request_pay(
          {
            // param
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "ORD_" + new Date().getTime(),
            name: this.getImpName(),
            amount: this.getImpAmount(),
            buyer_email: this.$store.state.userStore.user.email,
            buyer_name: this.$store.state.userStore.user.name,
            buyer_tel: this.$store.state.userStore.user.phone,
          },
          (rsp) => {
            // callback
            if (rsp.success) {
              // 결제 성공 시 로직 - 강의 결제 내역에 등록
              console.log("결제 성공", rsp);
              // 결제 내역 DB 등록
              this.insertPayment(rsp);
              //
            } else {
              // 결제 실패 시 로직,
              console.log("결제 실패", rsp);
              alert(rsp.error_msg);
            }
          }
        );
      }
    },
    // 결제 요청 name 속성 세팅
    getImpName() {
      if (this.selected.length >= 2) {
        return (
          this.selected[0].title + " 외 " + (this.selected.length - 1) + "개"
        );
      } else {
        return this.selected[0].title;
      }
    },
    // 결제 요청 amount 속성 세팅
    getImpAmount() {
      let amount = 0;
      for (let i = 0; i < this.selected.length; i++) {
        amount += this.selected[i].price;
      }
      return amount;
    },
    // 결제 내역 DB에 등록 axios
    insertPayment(rsp) {
      // axios로 전달한 배열 파라미터
      let payList = [];
      for (let i = 0; i < this.selected.length; i++) {
        payList.push({
          memberId: this.$store.state.userStore.user.memberId,
          lectureNo: this.selected[i].lectureNo,
          lectureTitle: this.selected[i].title,
          paymentType: rsp.pay_method,
          price: this.selected[i].price,
          merchantUid: rsp.merchant_uid,
        });
      }
      console.log();
      // axios 발생 - 배열을 전달 받을때 map 형식으로 받을 것임 -> 전달방식(키 값 형태) -> ("string": "list")
      this.$axios
        .post("/api/insertPayment", {
          payList: payList,
        })
        .then((resp) => {
          if (resp.data != null) {
            console.log("결제 내용 DB insert 성공", resp.data);
            // 결제 DB 등록 완료 시 나의 강의 DB에 등록
            this.insertMyLecture();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 나의 강의 DB에 등록 axios
    insertMyLecture() {
      // axios로 전달한 배열 파라미터
      let addMyLectureList = [];
      for (let i = 0; i < this.selected.length; i++) {
        addMyLectureList.push({
          memberId: this.$store.state.userStore.user.memberId,
          lectureNo: this.selected[i].lectureNo,
        });
      }
      // axios 발생 - 배열을 전달
      this.$axios
        .post("/api/insertMyLecture", addMyLectureList)
        .then((resp) => {
          if (resp.data != null) {
            console.log("결제 후 나의강의 DB insert 성공", resp.data);
            // 나의 커리큘럼 등록
            this.insertMyCurriculum();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 나의 강의 DB에 등록 후 나의 커리큘럼 DB에 등록 axios
    insertMyCurriculum() {
      // axios로 전달한 배열 파라미터
      let addMyCurriculumList = [];
      for (let i = 0; i < this.selected.length; i++) {
        addMyCurriculumList.push({
          memberId: this.$store.state.userStore.user.memberId,
          lectureNo: this.selected[i].lectureNo,
        });
      }
      // axios 발생 - 배열을 전달
      this.$axios
        .post("/api/insertMyCurriculum", addMyCurriculumList)
        .then((resp) => {
          console.log("결제 후 나의커리큘럼 DB insert 성공", resp.data);
          // 결제 완료 후 장바구니에서 삭제
          this.deleteSelectedCart();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 결제 완료 후 장바구니에서 삭제
    deleteSelectedCart() {
      let selectedCartNo = [];
      for (let i = 0; i < this.selected.length; i++) {
        selectedCartNo.push(this.selected[i].cartNo);
      }
      console.log("결제 후 삭제할 장바구니 목록", selectedCartNo);
      this.$axios
        .post("/api/deleteSelectedCart", {
          cartNoArr: selectedCartNo,
        })
        .then((resp) => {
          if (resp.data != 0) {
            for (let i = 0; i < selectedCartNo.length; i++) {
              for (let j = 0; j < this.cartList.length; j++) {
                if (selectedCartNo[i] == this.cartList[j].cartNo) {
                  this.cartList.splice(j, 1);
                }
              }
              for (let k = 0; k < this.selected.length; k++) {
                if (selectedCartNo[i] == this.selected[k].cartNo) {
                  this.selected.splice(k, 1);
                }
              }
            }
            ////////////////////////////////결제 완료 알림창
            Swal.fire({
              position: "center",
              icon: "success",
              title: "결제가 완료되었습니다.",
              showConfirmButton: true,
              // timer: 1000
            }).then(() => {
              this.$router.go(-4);
            });
          }
        });
    },
  },
};
</script>

<style scoped>
#lc_wrapper {
  width: 60%;
  margin: auto;
  display: flex;
  justify-content: space-between;
  margin-top: 60px;
  height: 100vh;
}

.lc_header_list {
  width: 70%;
}
.lc_header {
  text-align: left;
  display: flex;
  justify-content: space-between;
  border-bottom: 2px solid black;
}

.lc_list {
  margin-top: 40px;
  text-align: left;
  display: flex;
  justify-content: space-between;
  padding-bottom: 20px;
  border-bottom: 1px solid lightgray;
}

.lc_list_img {
  width: 150px;
  height: 150px;
  margin-right: 16px;
}

.lc_list_info {
  width: 55%;
}

.lc_list_price {
  text-align: right;
}

.lc_list_price h5 {
  margin-top: 80px;
}

.payment_app {
  border: 1px solid lightgray;
  width: 250px;
  text-align: left;
  padding: 10px;
  border-radius: 10px;
  height: 300px;
}

.cart_checkbox {
  margin-right: 10px;
  padding-top: 0px !important;
  margin-top: 0px;
}

.select_all {
  display: flex;
}

button {
  color: white;
}

/*  결제창 연습  */
aside {
  float: right;
  position: relative;
  width: 30%;
  margin-left: 60px;
}

.summary {
  background-color: #eee;
  /* border: 1px solid #aaa; */
  padding: 1rem;
  position: fixed;
  width: 280px;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: 0 10px 20px -2px gray;
}

.summary-total-items {
  color: #666;
  font-size: 0.875rem;
  text-align: center;
}

.summary-subtotal,
.summary-total {
  /* border-top: 1px solid #ccc; */
  border-bottom: 1px solid #ccc;
  clear: both;
  margin: 1rem 0;
  overflow: hidden;
  text-align: left;
}
.summary-subtotal > div {
  margin-bottom: 10px;
}

.summary-subtotal > div:nth-child(1) {
  margin-bottom: 10px;
  font-weight: bold;
  font-size: large;
}

.subtotal-title,
.subtotal-value,
.total-title,
.total-value,
.promo-title,
.promo-value {
  color: #111;
  float: left;
  width: 50%;
}

.summary-promo {
  -webkit-transition: all 0.3s ease;
  -moz-transition: all 0.3s ease;
  -o-transition: all 0.3s ease;
  transition: all 0.3s ease;
}

.promo-title {
  float: left;
  width: 70%;
}

.promo-value {
  color: #8b0000;
  float: left;
  text-align: right;
  width: 30%;
}

.summary-delivery {
  padding-bottom: 2rem;
  clear: both;
  margin: 1rem 0;
  text-align: left;
}

.summary-delivery > div {
  margin-bottom: 10px;
}

.summary-delivery > div:nth-child(1) {
  margin-bottom: 10px;
  font-weight: bold;
  font-size: large;
}

.title_price {
  display: flex;
  justify-content: space-between;
}

.subtotal-value,
.total-value {
  text-align: right;
  font-size: large;
}

.total-title {
  font-weight: bold;
  text-transform: uppercase;
}

.summary-checkout {
  display: block;
}

.checkout-cta {
  display: block;
  float: none;
  font-size: 0.75rem;
  text-align: center;
  text-transform: uppercase;
  padding: 0.625rem 0;
  width: 100%;
}

.summary-delivery-selection {
  background-color: #ccc;
  border: 1px solid #aaa;
  border-radius: 4px;
  display: block;
  font-size: 0.625rem;
  height: 34px;
  width: 100%;
}
</style>
