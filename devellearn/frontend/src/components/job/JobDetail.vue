<template>
  <div>
    <br /><br />
    <DetailSummary :recruit="recruit" @reload="reload()"></DetailSummary>
    <br />
    <div class="tab">
      <div>
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/jobDetail/recruitDetail',
                query: { recruitNo: recruit.recruitNo, comNo: comNo },
              })
              .catch(() => {})
          "
          >상세</span
        >
        |

        <span
          class="link"
          @click="
            $router
              .push({
                path: '/jobDetail/companyReview',
                query: { recruitNo: recruit.recruitNo, comNo: comNo },
              })
              .catch(() => {})
          "
          >리뷰</span
        >
      </div>
      <div>
        <div v-if="$store.getters['userStore/getEmpUser'] != 'true'">
          <span class="link" @click="editResume()">이력서 작성하기</span>
          |
          <span class="link" @click="apply()">지원하기</span>
        </div>
        <v-dialog v-model="dialog" width="450px">
          <NeedLogin @close="dialog = false"></NeedLogin>
        </v-dialog>
        <v-dialog v-model="resumeDialog" width="450px">
          <NoResume @close="resumeDialog = false"></NoResume>
        </v-dialog>
        <v-dialog v-model="applyDialog" width="450px">
          <CheckApply @close="applyDialog = false"></CheckApply>
        </v-dialog>
      </div>
      <b-modal
        id="apply"
        centered
        title="인사담당자들에겐 이렇게 보여요! "
        size="lg"
      >
        <div class="resume" ref="document">
          <div class="title">
            <h6>지원서</h6>
            <h5>{{ recruit.title }}</h5>
            <h6>{{ recruit.jobType }}</h6>
          </div>
          <div class="content" v-if="userData != null">
            <div class="profile">
              <div>
                <br />
                <span class="h5">{{ userData.name }}</span>
                <br />
                <span class="h6">{{ phoneNum }}</span>
                <br />
                <span class="h6">{{ userData.email }}</span>
                <br />
                <span class="h6"
                  >{{ userData.address }} <span v-if="userData != ''">,</span>
                </span>
                <span class="h6">{{ userData.addressD }} </span>
              </div>
              <img
                class="profileImg"
                :src="'/api/images/profile/' + userData.profile"
                alt=""
              />
            </div>

            <div>
              <h5 v-if="userData.introduction != null">자기소개</h5>
              <span class="h6" style="white-space: pre-wrap">{{
                userData.introduction
              }}</span>
            </div>
            <div>
              <template v-if="careerList.length > 0">
                <hr />
                <h5>경력</h5>
                <span v-for="item in careerList" :key="item.eduNo">
                  {{ item.jobPeriod }} &nbsp;{{ item.company }} &nbsp;
                  {{ item.dept }} &nbsp; {{ item.position }} &nbsp;
                  {{ item.task }}
                  <v-icon
                    v-if="item.status == '승인'"
                    size="20"
                    color="#007bff"
                  >
                    mdi-check-decagram
                  </v-icon>
                  <br />
                  <br />
                </span>
                <br />
              </template>
            </div>
            <div>
              <template v-if="projectList.length > 0">
                <hr />
                <h5>프로젝트</h5>
                <span v-for="item in projectList" :key="item.eduNo">
                  {{ item.projectName }} &nbsp; {{ item.period }} <br />
                  {{ item.projectContent }}
                  <br />
                  {{ item.projectLink }}
                  <br />
                  <br />
                </span>
              </template>
            </div>
            <div>
              <template v-if="eduList.length > 0">
                <hr />
                <h5>교육</h5>
                <span v-for="item in eduList" :key="item.eduNo">
                  {{ item.eduInsti }} <br />
                  {{ item.eduTitle }} &nbsp;&nbsp;
                  {{ item.eduPeriod }}&nbsp;&nbsp; {{ item.eduStatus }}<br />
                </span>
                <br />
              </template>
            </div>
            <div>
              <template v-if="certList.length > 0">
                <hr />
                <h5>자격증</h5>
                <span v-for="item in certList" :key="item.eduNo">
                  {{ item.certiName }} &nbsp;&nbsp; {{ item.acqDate }}
                  <v-icon
                    v-if="item.status == '승인'"
                    size="20"
                    color="#007bff"
                  >
                    mdi-check-decagram
                  </v-icon>
                  <br />
                </span>
                <br />
              </template>
            </div>
            <div>
              <template v-if="skillList.length > 0">
                <hr />
                <h5>보유스킬</h5>
                <span v-for="item in skillList" :key="item.eduNo">
                  #{{ item }}
                </span>
                <br />
              </template>
            </div>
          </div>
        </div>
        <template #modal-footer="{ close }">
          <b-button @click="close('apply')">취소</b-button>
          <b-button @click="exportToPDF()" variant="primary">지원하기</b-button>
        </template>
      </b-modal>
    </div>
    <hr style="width: 60%; margin: 20px auto" />

    <div style="width: 60%; margin: 0 auto">
      <router-view :key="key" />
    </div>
  </div>
</template>
<script>
import DetailSummary from "@/components/job/DetailSummary.vue";
import SearchBar from "@/components/mainPage/SearchBar.vue";
import html2pdf from "html2pdf.js";
import NeedLogin from "../common/NeedLogin.vue";
import NoResume from "./NoResume.vue";
import CheckApply from "./CheckApply.vue";

export default {
  data: function () {
    return {
      recruit: {},
      comNo: "",
      userData: {},
      careerList: [],
      eduList: [],
      projectList: [],
      certList: [],
      skillList: [],
      phoneNum: "",
      dialog: false,
      resumeDialog: false,
      applyDialog: false,
      key: 0,
    };
  },
  components: { DetailSummary, SearchBar, NeedLogin, NoResume, CheckApply },

  created() {
    if (
      this.$store.getters["userStore/getUserData"] != null &&
      Object.keys(this.$store.getters["userStore/getUserData"]).length !== 0
    ) {
      this.userData = this.$store.getters["userStore/getUserData"];
      this.phoneNum = this.userData.phone.substr(0, 3);
      this.phoneNum += "-" + this.userData.phone.substr(3, 4);
      this.phoneNum += "-" + this.userData.phone.substr(7, 4);
    }

    this.comNo = this.$route.query.comNo;
    console.log(this.$route.query);
    this.getRecruitData();
    this.getResumeList();
    this.selectMember();
  },
  methods: {
    selectMember() {
      var memberId = this.$store.getters["userStore/getId"];

      this.$axios
        .get("/api/selectMember/" + memberId)
        .then((res) => {
          this.selectData = res.data;

          const skills = this.selectData.skill.split(",");
          this.skillList = skills;
          console.log(this.skillList);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getRecruitData() {
      this.$axios
        .post("/api/recruit/detail", {
          memberId: this.$store.state.userStore.id,
          recruitNo: this.$route.query.recruitNo,
        })
        .then((resp) => {
          console.log(resp);
          this.recruit = resp.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /**
     * pdf파일 생성
     */
    async exportToPDF() {
      const opt = {
        margin: [15, 0, 15, 0],
        filename: "resume.pdf",
        image: { type: "jpeg", quality: 0.98 },
        pagebreak: { mode: "avoid-all" },
        html2canvas: {
          // html2canvas 옵션
          useCORS: true, // 영역 안에 로컬 이미지를 삽입 할 때 옵션 필요
          scrollY: 0, // 스크롤 이슈 때문에 필수
          scale: 1, // browsers device pixel ratio
          dpi: 300,
          letterRendering: true,
          allowTaint: false, //useCORS를 true로 설정 시 반드시 allowTaint를 false처리 해주어야함
        },
        jsPDF: { orientation: "portrait", unit: "mm", format: "a4" },
      };

      let pdfFile = new Blob();

      await html2pdf(this.$refs.document, opt)
        .output("blob")
        .then((data) => {
          console.log("blobdata", data);
          pdfFile = data;
        });

      console.log("pdf파일", pdfFile);
      const formData = new FormData();
      formData.append("pdf", pdfFile);
      formData.append("recruitNo", this.$route.query.recruitNo);
      formData.append("memberId", this.userData.memberId);

      this.$axios
        .put("/api/apply", formData, {
          Headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((resp) => {
          console.log(resp);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "지원완료",
            showConfirmButton: false,
            timer: 1000,
          });
        })
        .catch((error) => {
          console.log(error);
        });
      this.$bvModal.hide("apply");
    },
    // 이력서 항목 리스트 가져오기
    getResumeList() {
      this.$axios
        .post("/api/getResumeList", {
          memberId: this.$store.state.userStore.id,
        })
        .then((resp) => {
          this.eduList = resp.data.EduList;
          this.certList = resp.data.CertList;
          // this.skillList = resp.data.SkillList;
          this.projectList = resp.data.ProjectList;
          this.careerList = resp.data.CareerList;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    checkLogin() {
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        return false;
      } else {
        return true;
      }
    },
    editResume() {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
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
        this.$router
          .push({
            path: "/myPage/resumeHome",
          })
          .catch(() => {});
      }
    },
    async apply() {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
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
        let checkApply = 0;
        if (this.$store.getters["userStore/getUserData"].myResume == "N") {
          this.resumeDialog = true;
          return;
        }
        await this.$axios
          .post("/api/checkApply", {
            memberId: this.userData.memberId,
            recruitNo: this.recruit.recruitNo,
          })
          .then(async (resp) => {
            checkApply = resp.data;
            await console.log(resp.data);
          })
          .catch((error) => {
            console.log(error);
          });
        if (checkApply > 0) {
          console.log("지원못해");
          this.applyDialog = true;
          return;
        }
        this.$bvModal.show("apply");
      }
    },

    reload() {
      this.getRecruitData();
      this.key += 1;
    },
  },
};
</script>
<style scoped>
.content {
  width: 80%;
  margin: 0 auto;
  text-align: left;
}
.profile img {
  width: 150px;
  height: 150px;
  background-color: cadetblue;
}
.profile {
  width: 100%;
  margin: 0 auto;
  display: flex;
  align-content: space-between;
  justify-content: space-between;
}
.resume {
  width: 100%;
  margin: 0 auto;
}
.title {
  padding-top: 20px;
  padding-bottom: 10px;
  width: 80%;
  margin: 10px auto;
  background-color: rgb(44, 42, 42);
  border-radius: 10px;
  text-align: center;
}

.title h5,
.title h6 {
  color: white;
}

.tab {
  text-align: left;
  width: 60%;
  margin: 0 auto;
  display: inline-flex;
  justify-content: space-between;
}
.link {
  font-weight: bold;
  font-size: large;
  margin: 0 15px;
  line-height: 100%;
  cursor: pointer;
  color: #0d6efd;
}
a {
  text-decoration: none;
}
</style>
