<template>
  <div class="content">
    <div class="project-top">
      <b-icon icon="file-earmark-ppt" font-scale="1.5"></b-icon>
      <h5 style="margin-left: 10px">프로젝트</h5>
    </div>

    <div class="container">
      <template v-for="item in projectList">
        <template>
          <div
            class="project-card"
            :key="item.projectNo"
            @click="
              $router
                .push({
                  path: '/projectHome',
                  query: { blogId: item.projectId },
                })
                .catch(() => {})
            "
            style="cursor: pointer"
          >
            <div class="project-profile-box">
              <img
                class="card-img"
                :src="'/api/images/teamstudy/' + item.img"
                alt="이미지"
              />
            </div>

            <div class="project-content">
              <div class="project-title">
                <h5>{{ item.projectName }}</h5>
              </div>
              <div class="project-date">
                <h5>
                  {{ changeDate(item.startMon)  }} - {{ changeDate(item.endMon) }}
                </h5>
              </div>

              <b-icon
                class="project-icon"
                icon="house-door"
                font-scale="2"
              ></b-icon>
            </div>
          </div>
        </template>
      </template>
      <b-pagination
        v-if="projectList.length > 0"
        v-model="currentPage"
        :total-rows="rows"
        :per-page="listPage"
        align="center"
        class="paging"
      ></b-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      projectList: [],
      no: "",
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "10",
    };
  },
  components: {},
  created() {
    this.getProjectList();
  },
  watch: {
    currentPage() {
      this.getProjectList();
    },
  },
  methods: {
    getProjectList() {
      this.$axios
        .post("/api/projectBlog", {
          memberId: this.$store.state.userStore.id,
        })
        .then((resp) => {
          // console.log(resp);
          this.projectList = resp.data;
          console.log(this.projectList);
        });
    },
     changeDate: function (date) {
      return moment(date).format("YYYY/MM/DD");
    },
  },
  

};
</script>

<style lang="scss" scoped>
.project-top {
  display: flex;
  justify-content: flex-start;
  margin-left: 100px;
}
.container {
  width: 60%;
  margin-left: 100px;
  margin-top: 10px;
  min-height:800px;
}
.project-box {
  width: 60%;
  border: solid 1.5px #9fa5a8;
  border-radius: 10px;
  margin-bottom: 20px;
  margin-top: 50px;
  min-width: 1100px;
  height: 300px;
  overflow: hidden;
}

.project-card {
  width: 80%;
  border: solid 1.5px #9fa5a8;
  border-radius: 10px;
  height: 90px;
  margin-top: 10px;
  margin-bottom: 30px;
  padding: 10px 10px;
  display: flex;
}

.project-content {
  width: 90%;
  margin: auto 0;
}
.project-profile-box {
  width: 10%;
  height: 100%;
}
.card-img {
  width: 100%;
  height: 100%;
}
.project-title {
  width: 40%;
  margin: auto 0;
  float: left;
}
.project-date {
  margin: auto 0;
  float: left;
  width: 50%;
}
.project-icon {
  margin: auto 0;
  float: left;
  width: 10%;
}

.paging {
  bottom: 500px;
  position: absolute;
  width: 100%;
}
</style>
