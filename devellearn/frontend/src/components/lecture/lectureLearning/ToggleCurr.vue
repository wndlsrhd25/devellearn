<template>
  <div id="tc_wrapper">
    <div class="tc_header">
      <div>
        <div><h3>목차</h3></div>
        <div>
          <h5>{{ myLecture.title }} - {{ myCurriculum.currName }}</h5>
        </div>
      </div>
      <div>
        진도율 :
        {{ completeLectureNum }}강 / {{ myCurrList.length }}강 ({{
          calProgress
        }}%)
      </div>
      <b-progress class="mt-2" :max="max">
        <b-progress-bar :value="value" variant="success"></b-progress-bar>
      </b-progress>
    </div>
    <template v-for="curr in myCurrList">
      <template>
        <div :key="curr.currNo" class="tc_curr" @click="moveAnotherCurr(curr)">
          <div>
            <v-img
              :src="require('@/assets/unchecked.png')"
              style="width: 24px; margin-right: 8px"
              v-if="curr.watchedAll == '미완료'"
            ></v-img>
            <v-img
              :src="require('@/assets/checked.png')"
              style="width: 24px; margin-right: 8px"
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
</template>

<script>
export default {
  props: ["myLecture", "myCurrList", "videoCurrentTime", "myCurriculum"],
  created() {
    console.log(this.myCurrList);
    this.countCompleteLectureNum();
  },
  data() {
    return {
      value:
        (this.myLecture.totalWatchedTime / this.myLecture.totalPlayTime) * 100,
      max: 100,
      completeLectureNum: 0,
    };
  },

  computed: {
    // 진도율 계산
    calProgress() {
      return (
        (this.myLecture.totalWatchedTime / this.myLecture.totalPlayTime) *
        100
      ).toFixed(2);
    },
  },
  methods: {
    // 완료된 강의 수 카운트
    countCompleteLectureNum() {
      for (let i = 0; i < this.myCurrList.length; i++) {
        if (this.myCurrList[i].watchedAll == "완료") {
          this.completeLectureNum += 1;
        }
      }
    },
    // 다른 커리큘럼으로 이동
    moveAnotherCurr(currInfo) {
      // 현재 커리큘럼 재생시간 저장
      this.updateMyCurriculum(currInfo);
      // 학습하기 - 커리큘럼 리스트에서 클릭으로 해당 강의 보기
      this.$router.replace({
        path: "/lectureLearning",
        query: {
          no: this.$route.query.no,
          curr: currInfo.currNo,
          startTime: currInfo.latestWatchedTime,
        },
      });
      this.$router.go(0);
    },
    // 커리큘럼 재생시간 저장
    async updateMyCurriculum(currInfo) {
      let resp = await this.$axios.put("/api/updateMyCurriculum", {
        memberId: this.$store.getters["userStore/getId"],
        currNo: this.$route.query.curr,
        latestWatchedTime: Math.floor(this.videoCurrentTime),
      });
      if (resp.data != null) {
        console.log(
          "현재 비디오 재생시간과 수강컬럼 저장 (나의 커리큘럼)",
          resp.data
        );
      }
    },
    updateMyLecture() {
      this.$axios
        .put("/api/updateMyLecture", {
          latestWatchedCurr: this.$route.query.curr,
          latestWatchedTime: Math.floor(this.videoCurrentTime),
          memberId: this.$store.state.userStore.user.memberId,
          lectureNo: this.$route.query.no,
        })
        .then((resp) => {
          if (resp.data != null) {
            console.log(
              "현재 비디오 재생시간과 수강컬럼 저장 (나의 강의)",
              resp.data
            );
          }
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
      } else {
        minutes = minutes + ":";
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      return hours + minutes + seconds; // Return is HH : MM : SS
    },
  },
};
</script>

<style scoped>
/* 진도율 */
.progress {
  height: 12px;
  border: 1px solid gray;
}
/* 진도율 끝 */

#tc_wrapper {
  /* background-color: aqua; */
  text-align: left;
  padding: 20px;
  width: 420px;
}

#tc_wrapper .tc_header {
  margin-bottom: 20px;
  padding-bottom: 10px;
}

#tc_wrapper .tc_header > div:nth-child(1) > div {
  margin-bottom: 15px;
}

#tc_wrapper .tc_curr {
  display: flex;
  justify-content: space-between;
  padding: 15px 0px;
  border-bottom: 1px solid lightgray;
}

/* 완강 여부 체크버튼, 커리큘럼 제목 */
.tc_curr div:nth-child(1) {
  display: flex;
}

#tc_wrapper .tc_curr:hover {
  background-color: rgba(204, 204, 204, 0.2);
}
</style>
