<template>
  <div id="li_wrapper">
    <!-- 강의소개 -->
    <div>
      <h3 id="info">강의소개</h3>
      <div v-html="lecture.introduce"></div>
    </div>
    <!-- 커리큘럼 -->
    <div>
      <h3 id="curriculum">커리큘럼</h3>
      <template v-for="curr in currList">
        <div
          v-if="curr.currNo != null"
          :key="curr.currNo"
          class="curr_info"
          @click="
            $router
              .push({
                path: '/lectureView',
                query: {no: $route.query.no, curr: curr.currNo},
              })
              .catch(() => {})
          "
        >
          <div>
            {{ curr.currName }}
          </div>
          <div>
            {{ curr.playTime }}
          </div>
        </div>
      </template>
    </div>
    <v-row>
      <v-btn @click="$router.go(-1)">뒤로가기</v-btn>
      <v-spacer></v-spacer>
      <v-btn @click="update('반려')">반려</v-btn>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <v-btn color="primary" @click="update('승인')">승인</v-btn>
    </v-row>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      currList: [],
      lecture: {},
    };
  },
  created: function () {
    this.getCurrList();
    this.getInfo();
  },
  methods: {
    // 커리큘럼 전체조회 axios
    getCurrList() {
      this.$axios
        .get("/api/currList/" + this.$route.query.lectureNo)
        .then((resp) => {
          console.log(resp);
          this.currList = resp.data;
          console.log("currList", this.currList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getInfo() {
      this.$axios
        .get("/api/lecture/" + this.$route.query.lectureNo)
        .then((resp) => {
          this.lecture = resp.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    sendUpdateInfo(rejection) {
      this.$axios
        .put("/api/lecture", {
          rejection: rejection,
          status: approval,
          lectureNo: this.$route.query.lectureNo,
        })
        .then((resp) => {
          console.log(resp.data);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "처리완료",
            showConfirmButton: false,
            timer: 1000,
          }).then((result) => {
            this.$router.push("/adminPage/lectureMng/regist");
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    update(approval) {
      let rejection = "";
      if (approval == "반려") {
        Swal.fire({
          title: "반려사유입력",
          input: "text",
          inputAttributes: {
            autocapitalize: "off",
          },
          showCancelButton: true,
          confirmButtonText: "등록",
          showLoaderOnConfirm: true,
        }).then((resp) => {
          console.log(resp.value);
          rejection = resp.value;
          this.$axios
            .put("/api/lecture", {
              rejection: rejection,
              status: approval,
              lectureNo: this.$route.query.lectureNo,
            })
            .then((resp) => {
              console.log(resp.data);
              Swal.fire({
                position: "center",
                icon: "success",
                title: "처리완료",
                showConfirmButton: false,
                timer: 1000,
              }).then((result) => {
                this.$router.push("/adminPage/lectureMng/regist");
              });
            })
            .catch((error) => {
              console.log(error);
            });
        });
      } else {
        this.$axios
          .put("/api/lecture", {
            status: approval,
            lectureNo: this.$route.query.lectureNo,
          })
          .then((resp) => {
            console.log(resp.data);
            Swal.fire({
              position: "center",
              icon: "success",
              title: "처리완료",
              showConfirmButton: false,
              timer: 1000,
            }).then((result) => {
              this.$router.push("/adminPage/lectureMng/regist");
            });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>

<style scoped>
h3 {
  text-align: left;
  padding-left: 15px;
}
/* 바로 하위단계 div 접근 */
#li_wrapper > div {
  margin-bottom: 60px;
}
#info,
#curriculum,
#review {
  padding-bottom: 10px;
}

.curr_info {
  border-bottom: 1px solid lightgray;
  padding: 12px;
  font-size: 18px;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
}

.curr_info:hover {
  background-color: rgba(204, 204, 204, 0.2);
}
</style>
