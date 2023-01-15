<template>
  <div id="li_wrapper">
    <!-- 강의소개 -->
    <div>
      <h2 id="info">강의소개</h2>
      <div v-html="lecture.introduce"></div>
    </div>
    <!-- 커리큘럼 -->
    <div>
      <div class="curriculum">
        <h2 id="curriculum">커리큘럼</h2>
        <div>
          총 {{ currList.length }}개,
          {{ getTotalLecturePlayTime() }}
        </div>
      </div>

      <!-- 나의 강의에 해당 강의가 없는 경우 -> 수강불가 -->
      <div v-if="myCurrList.length == 0">
        <template v-for="curr in currList">
          <template>
            <div :key="curr.currNo" class="curr_info" @click="clickCurr">
              <div>
                {{ curr.currName }}
              </div>
              <div>
                {{ secondToHms(curr.playTime) }}
              </div>
            </div>
          </template>
        </template>
      </div>
      <!-- 나의 강의에 해당 강의가 있는 경우 -> 수강가능, 진행도 표시 -->
      <div v-if="myCurrList.length != 0">
        <template v-for="curr in myCurrList">
          <template>
            <div
              :key="curr.currNo"
              class="curr_info"
              @click="
                $router
                  .push({
                    path: '/lectureLearning',
                    query: {
                      no: $route.query.no,
                      curr: curr.currNo,
                      startTime: curr.latestWatchedTime,
                    },
                  })
                  .catch(() => {})
              "
            >
              <div>
                <v-img
                  :src="require('@/assets/unchecked.png')"
                  style="width: 27px; height: 24px; margin-right: 8px"
                  v-if="curr.watchedAll == '미완료'"
                ></v-img>
                <v-img
                  :src="require('@/assets/checked.png')"
                  style="width: 27px; margin-right: 8px"
                  v-if="curr.watchedAll == '완료'"
                ></v-img>
                {{ curr.currName }}
              </div>
              <div>
                {{ secondToHms(curr.playTime) }}
              </div>
            </div>
          </template>
        </template>
      </div>
    </div>
    <!-- 수강평 -->
    <div>
      <!-- <h2 id="review">수강평</h2> -->
      <LectureReviewList :myCurrList="myCurrList"></LectureReviewList>
    </div>
  </div>
</template>

<script>
import LectureReviewList from "../lectureReview/LectureReviewList.vue";
import { eventBus } from "@/main";

export default {
  components: {
    LectureReviewList,
  },
  // LectureDetail.vue 로부터 전달받는(바인딩) 데이터
  props: ["lecture", "myLecture", "myCurrList"],
  data: function () {
    return {
      currList: [],
      dialog: false,
      dialogPay: false,
      cartList: [],
    };
  },
  created: function () {
    // 해당 강의의 커리큘럼 리스트 조회
    this.getCurrList();
    this.getCartList();
  },
  methods: {
    clickCurr() {
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
        if (!this.checkPay()) {
          Swal.fire({
            title: "결제가 필요한 서비스입니다",
            text: "수강신청 페이지로 이동하시겠습니까?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonText: "취소",
            confirmButtonText: "확인",
          }).then(async (result) => {
            if (result.isConfirmed) {
              let alreadyCart = true;
              for (let i = 0; i < this.cartList.length; i++) {
                if (this.cartList[i] == this.$route.query.no) {
                  alreadyCart = false;
                  break;
                }
              }
              if (alreadyCart) {
                const resp = await this.$axios.post("/api/cart", {
                  memberId: this.$store.state.userStore.id,
                  lectureNo: this.$route.query.no,
                });
                console.log("장바구니 추가", resp.data);
              }
              vm.$router.push("/lectureCartList").catch(() => {});
            }
          });
        }
      }
    },
    // 장바구니 리스트 전체조회 axios
    getCartList() {
      this.$axios
        .get("/api/cart/" + this.$store.state.userStore.user.memberId)
        .then((resp) => {
          // console.log("오더 앱(강의 상세페이지)에서 장바구니 조회", resp.data);
          for (let i = 0; i < resp.data.length; i++) {
            this.cartList.push(resp.data[i].lectureNo);
          }
          console.log("cartLIst", this.cartList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    checkPay() {
      if (this.myCurrList.length == 0) {
        return false;
      } else {
        return true;
      }
    },

    // 커리큘럼 전체조회 axios
    getCurrList: function () {
      this.$axios
        .get("/api/currList/" + this.$route.query.no)
        .then((resp) => {
          console.log(resp);
          this.currList = resp.data;
          console.log("currList", this.currList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 커리큘럼 시간 변경 : 초 -> 분:초
    secondToHms(value) {
      const sec = parseInt(value, 10); // convert value to number if it's string
      let hours = Math.floor(sec / 3600); // get hours
      let minutes = Math.floor((sec - hours * 3600) / 60); // get minutes
      let seconds = sec - hours * 3600 - minutes * 60; //  get seconds
      // add 0 if value < 10; Example: 2 => 02
      if (hours < 10) {
        hours = "0" + hours + ":";
        if (hours == "00:") {
          hours = "";
        }
      } else {
        hours = hours + ":";
      }
      if (minutes < 10) {
        minutes = "0" + minutes + ":";
        if (minutes == "00:") {
          minutes = "";
        }
      } else {
        minutes = minutes + ":";
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      return hours + minutes + seconds; // Return is HH : MM : SS
    },
    // 강의 전체 플레이시간 계산
    getTotalLecturePlayTime() {
      let time = 0;
      for (let i = 0; i < this.currList.length; i++) {
        time += this.currList[i].playTime;
      }

      time = Number(time);
      var h = Math.floor(time / 3600);
      var m = Math.floor((time % 3600) / 60);

      var hDisplay = h > 0 ? h + "시간" : "";
      var mDisplay = m > 0 ? m + "분의 수업" : "";
      return hDisplay + mDisplay;
    },
  },
};
</script>

<style scoped>
/* 바로 하위단계 div 접근 */
#li_wrapper > div {
  margin-bottom: 60px;
}

#info,
#curriculum,
#review {
  padding-bottom: 10px;
}

.curriculum {
  display: flex;
  justify-content: space-between;
}

.curr_info {
  border-bottom: 1px solid lightgray;
  padding: 12px;
  font-size: 18px;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
}

.curr_info:nth-child(2n-1) {
  background-color: rgba(204, 204, 204, 0.2);
}

.curr_info div:nth-child(1) {
  display: flex;
}

.curr_info:hover {
  background-color: rgba(204, 204, 204, 0.6);
}

h2 {
  margin-bottom: 20px;
}
</style>
