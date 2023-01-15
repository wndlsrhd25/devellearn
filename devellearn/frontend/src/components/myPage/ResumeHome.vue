<template>
  <div id="ResumeHome" style="min-height: 100vh">
    <div id="subTitle">
      <h3 style="color: #00336d">나의 이력서</h3>
    </div>
    <hr />

    <div id="category">
      <h4>이력서 관리</h4>
    </div>
    <!-- ●●●●●부트스트랩 -->
    <div id="myResume">
      <div id="plusBtn">
        <div v-if="selectData.myResume == 'N'">
          <b-button
            @click="writeNewResume"
            block
            variant="primary"
            style="width: 800px"
            >이력서 작성하기</b-button
          >
        </div>
        <div v-if="selectData.myResume == 'y'">
          <b-button
            @click="moveMyResume"
            block
            variant="warning"
            style="width: 800px"
            >이력서 작성하기</b-button
          >
        </div>
      </div>
    </div>

    <hr />
    <div id="category">
      <h4>경력 인증 신청 내역</h4>
    </div>

    <div v-if="CareerApplyList == ''">신청 내역이 존재하지 않습니다.</div>

    <div v-if="CareerApplyList != ''">
      <table>
        <!-- 줄을 먼저 만들어야함 -->
        <tr>
          <!-- 칸을 만들어야함 -->
          <!-- th는 제목을 말하는 것 -->
          <th>회사명</th>
          <th>신청일자</th>
          <th>처리상태</th>
          <th>반려사유</th>
        </tr>
        <template>
          <tr v-for="line in CareerApplyList">
            <td>{{ line.company }}</td>
            <td>{{ line.applyDate | yyyyMMdd }}</td>
            <td>{{ line.status }}</td>
            <td>{{ line.rejection }}</td>
          </tr>
        </template>
      </table>
    </div>

    <hr />
    <div id="category">
      <h4>자격 인증 신청 내역</h4>
    </div>

    <div v-if="CertiApplyList == ''">신청 내역이 존재하지 않습니다.</div>

    <div v-if="CertiApplyList != ''">
      <table>
        <!-- 줄을 먼저 만들어야함 -->
        <tr>
          <!-- 칸을 만들어야함 -->
          <!-- th는 제목을 말하는 것 -->
          <th>자격증명</th>
          <th>신청일자</th>
          <th>처리상태</th>
          <th>반려사유</th>
        </tr>
        <tr v-for="line in CertiApplyList">
          <td>{{line.certiName}}</td>
          <td>{{line.applyDate|yyyyMMdd}}</td>
          <td>{{line.status}}</td>
          <td>{{line.rejection}}</td>
        </tr>
      </table>
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
      memberId: "",
      CareerApplyList: [],
      CertiApplyList: [],
      selectData: [],
    };
  },
  created() {
    this.applyCareerList();
    this.applyCertiList();
    this.selectMember();
  },
  methods: {
    //로그인 정보로 회원정보 가져오기
    selectMember() {
      console.log("213123123123123123");
      var memberId = this.$store.state.userStore.id;
      console.log("로그인정보 1111111: " + memberId);

      this.$axios
        .get("/api/selectMember/" + memberId)
        .then((res) => {
          console.log(res);
          this.selectData = res.data;
          this.$store.commit("userStore/setUserData", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    writeNewResume() {
      this.$axios
        .put("/api/resumeYes/" + this.$store.state.userStore.id)
        .then((res) => {
          if (res.data != null) {
            //이력서 작성화면으로 이동합니다.
          }
        });

      this.$axios
        .post("/api/insertDevelProj", {
          memberId: this.$store.state.userStore.id,
        })
        .then((res) => {
          if (res.data != 1) {
            //devellearn의 교육사항 내역 불러오기 완료
          }
        });

      this.$axios
        .post("/api/insertDevelEdu", {
          memberId: this.$store.state.userStore.id,
        })
        .then((res) => {
          if (res.data != 1) {
            //devellearn의 프로젝트 내역 불러오기 완료
          }
        });

      Swal.fire({
        position: "center",
        icon: "success",
        toast: true,
        title: "이력서 작성화면으로 이동합니다.",
        showConfirmButton: false,
        timer: 1200,
      }).then((result) => {
        this.$router.push({
          path: "/myPage/myResume",
        });
      });
    },

    moveMyResume() {
      this.$router.push({
        path: "/myPage/myResume",
      });
    },

    applyCareerList() {
      this.$axios
        .post("/api/applyCareer", {
          memberId: this.$store.state.userStore.id,
        })
        .then((res) => {
          this.CareerApplyList = res.data;
        });
    },
    applyCertiList() {
      this.$axios
        .post("/api/applyCerti", {
          memberId: this.$store.state.userStore.id,
        })
        .then((res) => {
          this.CertiApplyList = res.data;
        });
    },
  },
  filters: {
    // filter로 쓸 filter ID 지정
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";
      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);
      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();
      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      // 최종 포맷 (ex - '2021-10-08')
      return year + "/" + month + "/" + day;
    },
  },
};
</script>
<style scoped>
  #ResumeHome {
    margin: 0 auto;
    text-align: center;
  }

  #subTitle {
    margin-left: -250px; 
  }

  #category {
    text-align: left;

    font-weight: bold;
  }

  #myResume {
    padding-top: 50px;
    width: 900px;
    text-align: center;
    height: 150px;
  }

  #plusBtn {
    margin-top: 20px;
    margin-left: 60px;
  }

  table {
    border-collapse: collapse;
    width: 880px;
    text-align: center;
    margin: 20px 20px;
  }

  td {
    border: 1px solid rgb(121, 121, 121);
    padding: 10px;
    text-align: center;
  }

  th {
    border: 1px solid rgb(121, 121, 121);
    padding: 10px;
    text-align: center;
    background-color: rgb(0, 48, 104);
    color: white;
  }
  #roadAddress {
    width: 219.39px;
  }
</style>
