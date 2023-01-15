<template>
  <div id="MyPage" style="height: 100vh">
    <v-row display="flex">
      <v-col>
        <div class="card" style="width: 35rem">
          <div class="card-body">
            <h5 class="card-title">프로필</h5>

            <router-link to="/myPage/updateInfo" class="card-link"
              >수정하기></router-link
            >
            <h6 class="card-subtitle mb-5 text-muted">
              {{ selectData.name }}님. 안녕하세요 :)
            </h6>
            <p class="card-text"></p>
          </div>
        </div>
      </v-col>
      <v-col cols="3">
        <div class="card" style="width: 35rem">
          <div class="card-body">
            <h5 class="card-title">쪽지</h5>
            <router-link to="/myPage/messageBox" class="card-link"
              >전체보기></router-link
            >
            <div id="msgView">
              <div v-if="recvData.length == 0">
                <p class="card-text">수신된 쪽지가 없습니다.</p>
              </div>
              <div
                v-if="recvData.length != 0"
                v-for="list in recvData.slice(0, 3)"
                style="
                  height: 25px;
                  width: 100%;
                  text-align: left;
                  overflow: hidden;
                  display: inline-block;
                "
              >
                ( {{ list.sentDate | yyyyMMdd }})&nbsp;{{ list.content }}

                <hr />
              </div>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
    <v-row display="flex">
      <v-col>
        <div class="card" style="width: 35rem">
          <div class="card-body">
            <h5 class="card-title">학습통계</h5>
            <div class="flex-container1">
              <div class="flex-num">{{ completeLecture }}</div>
              <div class="flex-num">{{ openedLecture }}</div>
              <div class="flex-num">{{ finishMentor }}</div>
            </div>
            <div class="flex-container2">
              <div class="flex-item">수강한 강의 수</div>
              <div class="flex-item">개설한 강의 수</div>
              <div class="flex-item">완료 멘토링 수</div>
            </div>
          </div>
        </div>
      </v-col>
      <v-col cols="3">
        <div class="card" style="width: 35rem">
          <div class="card-body">
            <h5 class="card-title">My Skills</h5>

            <div v-if="selectSkill == ''">보유 중인 스킬이 없습니다.</div>

            <b-form-tags
              v-if="selectSkill != ''"
              class="tags-pills"
              input-id="tags-pills"
              v-model="selectSkill"
              tag-variant="primary"
              tag-pills
              size="lg"
              separator=" "
              :disabled="selectSkill"
              placeholder=""
            ></b-form-tags>
          </div>
        </div>
      </v-col>
    </v-row>
    <v-row display="flex">
      <v-col>
        <div class="card" style="width: 35rem">
          <div class="card-body">
            <h5 class="card-title">최근학습강의</h5>
            <router-link to="/myPage/myLearning" class="card-link"
              >전체보기></router-link
            >

            <div v-if="learningLectureList.length == 0">
              최근 학습강의가 없습니다.
            </div>

            <div
              v-if="learningLectureList.length != 0"
              v-for="list in learningLectureList.slice(0, 1)"
            >
              <h3 class="card-text" style="text-align: left">
                {{ list.title }}
              </h3>
              <!-- <h6 class="card-subtitle mb-5 text-muted">
                {{list.title}}</h6> -->

              <div style="text-align: left">
                진도율
                {{
                  ((list.totalWatchedTime / list.totalPlayTime) * 100).toFixed(
                    2
                  )
                }}%
              </div>

              <b-progress class="mt-2" :max="max">
                <b-progress-bar
                  :value="(list.totalWatchedTime / list.totalPlayTime) * 100"
                  variant="success"
                ></b-progress-bar>
              </b-progress>
            </div>
          </div>
        </div>
      </v-col>
      <v-col cols="3">
        <div class="card" style="width: 35rem">
          <div class="card-body">
            <h5 class="card-title">내가 쓴 게시글</h5>
            <router-link to="/myPage/MyBoard" class="card-link"
              >전체보기></router-link
            >

            <div v-if="boardList.length == 0">
              <p class="card-text">작성된 게시글이 없습니다.</p>
            </div>
            <div
              v-if="boardList.length != 0"
              v-for="list in boardList.slice(0, 3)"
              style="
                height: 25px;
                width: 100%;
                text-align: left;
                overflow: hidden;
                display: inline-block;
              "
            >
              ({{ list.regDate | yyyyMMdd }}) {{ list.title }}
            </div>
          </div>
        </div>
      </v-col>
    </v-row>

    <div style="margin-top: 150px"></div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  components: {},

  data() {
    return {
      memberId: "",
      selectData: [],
      selectSkill: [],

      recvData: [],

      openDetail: false,
      boardList: [],

      oder: 1,
      listPage: 5,
      currentPage: 1,
      boardListRows: 1,

      completeLecture: "",
      openedLecture: "",
      finishMentor: "",

      learningLectureList: [],
      // messageData: [],
      // myLectureData: '',
      // lectureData: '',
      // mentorData: '',
      // boardData: [],
    };
  },

  created() {
    this.selectMember();
    this.recvList();
    this.getBoardList();
    this.getCountNum();
    this.getMyLatestOne();
    //this.allDashBoard();
  },

  methods: {
    //로그인 정보로 회원정보 가져오기
    selectMember() {
      var memberId = this.$store.getters["userStore/getId"];
      console.log("로그인정보" + memberId);

      this.$axios
        .get("/api/selectMember/" + memberId)
        .then((res) => {
          console.log(res);
          this.selectData = res.data;
          console.log("이거" + this.selectData);
          const skills = this.selectData.skill.split(",");
          this.selectSkill = skills;
          console.log(this.selectSkill);
          this.testEmail = this.selectData.email;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //최근 쪽지 수신 목록 가져오기
    recvList() {
      var recvId = this.$store.getters["userStore/getId"];
      console.log("recvId" + recvId);

      this.$axios
        .get("/api/recvMes", {
          params: {
            recvId: recvId,
          },
        })
        .then((res) => {
          console.log(res);
          this.recvData = res.data;
          console.log("수신메세지", this.recvData.content);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //내가 쓴 글 가져오기

    getBoardList() {
      axios

        .post("/api/myboard", {
          order: this.order,
          pageNum: this.currentPage,
          pageSize: this.listPage,
          memberId: this.$store.state.userStore.id,
        })

        .then((response) => {
          this.boardList = response.data.list;
          this.boardListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    // 수강한 강의 수 조회

    getCountNum() {
      this.$axios
        .post("/api/completeLecture", {
          memberId: this.$store.state.userStore.id,
        })
        .then((response) => {
          console.log(response.data);
          this.completeLecture = response.data;
        });

      this.$axios
        .post("/api/openedLecture", {
          memberId: this.$store.state.userStore.id,
        })
        .then((response) => {
          this.openedLecture = response.data;
        });

      this.$axios
        .post("/api/finishMentor", {
          memberId: this.$store.state.userStore.id,
        })
        .then((response) => {
          this.finishMentor = response.data;
        });
    },

    //최근 수강한 강의리스트 조회
    getMyLatestOne() {
      this.$axios
        .post("/api/getMyLatestOne", {
          memberId: this.$store.state.userStore.id,
        })
        .then((resp) => {
          if (resp.data != null) {
            for (let i = 0; i < resp.data.length; i++) {
              if (resp.data[i].completeStatus == "미완료") {
                this.learningLectureList.push(resp.data[i]);
              }
              if (resp.data[i].completeStatus == "완료") {
                this.completeLectureList.push(resp.data[i]);
              }
            }
          }



        })
        .catch((error) => {
          console.log(error);
        });
    },

   //대시보드 컨트롤러에서 정보를 못불러와서 일단은 사용 보류,,,,

    // allDashBoard() {
    //   var memberId = this.$store.getters['userStore/getId'];
    //   console.log('로그인정보22222222' + memberId);

    //   this.$axios
    //     .get('/api/dashBoard')

    //     .then((res) => {

    //       console.log(res);
    //       //회원 단건조회 내용
    //       this.selectData = res.data.memberInfo;
    //       console.log('이거' + this.selectData);
    //       const skills = this.selectData.skill.split(",");
    //       this.selectSkill = skills;
    //       console.log(this.selectSkill);

    //       //수신 메세지리스트
    //       this.messageData = res.data.message;
    //       console.log(this.messageData);

    //       //수강한 강의수
    //       this.myLectureData = res.data.myLecutres;
    //       console.log(this.myLectureData);
    //       //개설한 강의수
    //       this.lectureData = res.data.lecture;
    //       console.log(this.lectureData);
    //       //완료한 멘토링 수
    //       this.mentorData = res.data.mentor;
    //       console.log(this.mentorData);
    //       //내가쓴게시글
    //       this.boardData = res.data.board;
    //       console.log(this.boardData);
    //     }).catch(err => {
    //       console.log(err);
    //     })
    // },
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
#MyPage {
  margin: 5px 0px;
  margin-left: -80%;
}

div.col {
  padding: 0px;
  width: 500px;
  padding-top: 20px;
  margin-right: -35%;
}

div.card {
  position: relative;
  display: flex;
  margin: 0 auto;
  height: 200px;
  width: 0 auto;
}

.card-title {
  text-align: left;
}

.card-subtitle {
  text-align: left;
}

.card-link {
  float: right;
}

.flex-container1 {
  display: flex;
  margin: 20px 50px 0px 70px;
}

.flex-container2 {
  display: flex;
  margin: 0px 50px 0px 70px;
}

.flex-item {
  width: 150px;
  height: 30px;
  margin: 10px 0px;
}

.flex-num {
  width: 150px;
  height: 60px;
  font-size: 3em;
}

.card {
  display: flex;
}

.tags-pills {
  margin-top: 15px;
}
</style>
