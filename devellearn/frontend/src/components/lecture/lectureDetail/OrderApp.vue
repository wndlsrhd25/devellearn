<template>
  <div>
    <aside>
      <div class="summary">
        <div class="summary-subtotal">
          <div>
            {{ lecture.title }}
            <div style="margin-top: 12px">
              가격 : {{ lecture.price.toLocaleString("ko-KR") }} 원
            </div>
          </div>
          <div>개발고수 : {{ lecture.name }}</div>
          <div>분야 : {{ lecture.category }}</div>
          <div>난이도 : {{ lecture.difficulty }}</div>
          <div>{{ lecture.learnerNum }} 명이 수강중입니다!</div>
          <div>
            <b-icon-star-fill
              v-for="i in Math.round(lecture.avgScore)"
              :key="i + new Date()"
              variant="warning"
            ></b-icon-star-fill>
            <b-icon-star
              v-for="j in 5 - Math.round(lecture.avgScore)"
              :key="j + new Date() * 2"
              variant="warning"
            ></b-icon-star>
            ({{ lecture.avgScore }})
          </div>
          <div>{{ lecture.skillTag }}</div>
        </div>

        <div class="summary-total">
          <div @click.stop="clickLiked">
            <div>
              <b-icon
                :icon="isLiked == false ? 'suit-heart' : 'suit-heart-fill'"
                class="h4"
                variant="danger"
                style="margin-top: 10px"
              ></b-icon>
            </div>
            <div>찜하기</div>
          </div>
          <div @click.stop="clickShared">
            <div>
              <b-icon
                :icon="'share-fill'"
                class="h4"
                style="margin-top: 10px"
              ></b-icon>
            </div>
            <div>공유</div>
          </div>
        </div>
        <!-- 버튼 -->
        <div class="summary-checkout">
          <div v-if="!isMyLecture()">
            <b-button block variant="primary" @click="addAndmoveCart"
              >수강신청</b-button
            >
            <b-button block variant="info" @click="addCart"
              >장바구니담기</b-button
            >
          </div>
          <div>
            <b-button
              block
              variant="success"
              @click="learning"
              v-if="isMyLecture()"
              >이어서 학습하기</b-button
            >
          </div>
        </div>
      </div>
    </aside>
  </div>
</template>

<script>
export default {
  components: {},
  props: ["lecture", "myLecture", "myCurrList"],
  data: function () {
    return {
      isLiked: false,
      likedLecture: {},
      cartList: [],
      dialog: false,
    };
  },
  created: function () {
    // 좋아요 확인
    this.likedCheck();
    // 장바구니 리스트 확인 - 이미 담은 강의일 경우 담지 못하게
    this.getCartList();
    // 내 강의인지 확인
    this.isMyLecture();
  },
  methods: {
    // 이어서 학습하기
    // myLecture DB에서 단건 조회
    learning() {
      if (this.myLecture.latestWatchedCurr != 0) {
        this.$router
          .push({
            path: "/lectureLearning",
            query: {
              no: this.$route.query.no,
              curr: this.myLecture.latestWatchedCurr,
              startTime: this.myLecture.latestWatchedTime,
            },
          })
          .catch(() => {});
      }
      // 새로 등록된 강의라면 lastestWatchedCurr이 0이므로 첫번째 강의부터 재생
      else {
        this.$router
          .push({
            path: "/lectureLearning",
            query: {
              no: this.$route.query.no,
              curr: this.myCurrList[0].currNo,
              startTime: this.myLecture.latestWatchedTime,
            },
          })
          .catch(() => {});
      }
    },

    // async await 를 사용하여 addCart 메서드의 통신이 끝날때까지 기다림
    async addAndmoveCart() {
      let vm = this;
      /////////////////////////////////////////////로그인(그냥 느낌표 아이콘) 알림창
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        //내가 실제로 그 버튼을 눌렀을때 실행되야하는 것
        await this.addCart();
        ////////////////////////////////글작성 완료 알림창
        Swal.fire({
          position: "center",
          icon: "success",
          title: "장바구니에 담았습니다.\n수강신청 페이지로 이동합니다.",
          showConfirmButton: true,
        }).then(function () {
          // 장바구니로 이동
          vm.$router.push("/lectureCartList").catch(() => {});
        });
      }
    },
    // 장바구니 추가
    // async await 를 사용하여 addCart 메서드의 통신이 끝날때까지 기다림
    async addCart() {
      if (this.cartList.length == 0) {
        // 장바구니 등록 메서드 실행
        await this.insertCart();
      } else {
        // 수강신청한 배열
        for (let i = 0; i < this.cartList.length; i++) {
          if (this.cartList[i] == this.$route.query.no) {
            //////////////////////////////// 알림창
            Swal.fire({
              position: "center",
              icon: "warning",
              title: "이미 담은 강의입니다.",
              showConfirmButton: true,
            });
            return;
          }
        }
        // 장바구니 등록 메서드 실행
        await this.insertCart();
      }
    },
    // 장바구니 등록 axios
    async insertCart() {
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        //내가 실제로 그 버튼을 눌렀을때 실행되야하는 것
        const resp = await this.$axios.post("/api/cart", {
          memberId: this.$store.state.userStore.id,
          lectureNo: this.$route.query.no,
        });
        console.log("장바구니 추가", resp.data);
        if (resp.data != 0) {
          this.cartList.push(this.$route.query.no);
          ////////////////////////////////글작성 완료 알림창
          Swal.fire({
            position: "center",
            icon: "success",
            title: "장바구니에 담았습니다.",
            showConfirmButton: true,
            // timer: 1000
          });
        }
      }
    },
    // 장바구니 리스트 전체조회 axios
    getCartList: function () {
      this.$axios
        .get("/api/cart/" + this.$store.state.userStore.user.memberId)
        .then((resp) => {
          // console.log("오더 앱(강의 상세페이지)에서 장바구니 조회", resp.data);
          for (let i = 0; i < resp.data.length; i++) {
            this.cartList.push(resp.data[i].lectureNo);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 좋아요 확인
    likedCheck: function () {
      this.$axios
        .post("/api/lectureLike", {
          memberId: this.$store.state.userStore.id,
          lectureNo: this.$route.query.no,
        })
        .then((resp) => {
          console.log(resp.data);
          if (resp.data != "") {
            this.isLiked = true;
            this.likedLecture = resp.data;
          } else {
            this.isLiked = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 좋아요 / 좋아요 취소
    clickLiked: function () {
      /////////////////////////////////////////////로그인(그냥 느낌표 아이콘) 알림창
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        //내가 실제로 그 버튼을 눌렀을때 실행되야하는 것
        if (this.isLiked == true) {
          // 좋아요 취소 - delete
          // --> deleteMapping은 @RequestBody 를 쓸 수 없어 @Pathvariable 활용하기 위해 likeNo 필요
          this.$axios
            .delete("/api/deleteLiked/" + this.likedLecture.likeNo)
            .then((resp) => {
              console.log(resp);
              if (resp.data != "") {
                this.isLiked = !this.isLiked;
              }
            })
            .catch((error) => {
              console.log(error);
            });
        } else {
          // 좋아요 - insert
          this.$axios
            .post("/api/insertLiked", {
              memberId: this.$store.state.userStore.id,
              lectureNo: this.$route.query.no,
            })
            .then((resp) => {
              console.log(resp);
              if (resp.data != "") {
                this.isLiked = !this.isLiked;
                this.likedLecture = resp.data;
              }
            })
            .catch((error) => {
              console.log(error);
            });
        }
      }
    },
    clickShared() {
      /////////////////////////////////////////////로그인(그냥 느낌표 아이콘) 알림창
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        //내가 실제로 그 버튼을 눌렀을때 실행되야하는 것
        // 공유 로직 (카카오 공유, url복사)
      }
    },
    // 나의 강의인지 확인
    isMyLecture() {
      // console.log("나의 강의인지 확인 lecture ", this.lecture);
      // console.log("나의 강의인지 확인 myLecture ", this.myLecture.lectureNo);
      // null 로 비교????? ""로 비교?????
      // this.myLecture 객체는 뭘로 비교? -> object 로 나와서 lectureNo 로 뽑음
      if (this.myLecture.lectureNo != null) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style scoped>
/*  결제창 연습  */
aside {
  /* float: right; */
  /* position: relative; */
  width: 30%;
  margin-left: 60px;
  position: sticky;
  top: 200px;
}

.summary {
  /* background-color: #eee; */
  background-color: white;
  /* border: 1px solid #aaa; */
  padding: 1rem;
  width: 250px;
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

.summary-subtotal {
  border-bottom: 1px solid #ccc;
  clear: both;
  /* margin-bottom: 1rem; */
  overflow: hidden;
  text-align: left;
}

.summary-total {
  border-bottom: 1px solid #ccc;
  /* padding-bottom: 15px; */
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-around;
}

.summary-total > div {
  display: flex;
  margin-top: 8px;
}

.summary-total > div:hover {
  cursor: pointer;
}

.summary-total > div > div {
  margin-right: 8px;
}
.summary-total > div > div:nth-child(2) {
  line-height: 45px;
}

.summary-subtotal > div {
  margin-bottom: 10px;
}

.summary-subtotal > div:nth-child(1) {
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
  margin-bottom: 10px;
  font-weight: bold;
  font-size: larger;
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
  justify-content: space-evenly;
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
