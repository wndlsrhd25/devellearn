<template>
  <div class="project-box">
    <div class="project-top">
      <b-icon icon="file-earmark-ppt" font-scale="2"></b-icon>
      <h5 style="margin-left: 10px">프로젝트</h5>
      <h6
        style="margin-left: auto; cursor: pointer"
        @click="$router.push({ path: '/myBlogHome/project' }).catch(() => {})"
      >
        전체보기
      </h6>
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
                  query: {
                    blogId: item.projectId,
                  },
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
                  {{ changeDate(item.startMon) }} - {{ changeDate(item.endMon) }}
                </h5>
              </div>
              <div class="project-icon">
                <b-icon icon="house-door" font-scale="2"></b-icon>
              </div>
            </div>
          </div>
        </template>
      </template>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      projectList: [],
    };
  },
  components: {},
  created() {
    this.getProjectList();
  },
  watch: {

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

<style scoped>
.container {
  width: 1000px;
  padding-top: 0;
}
.project-box {
  margin-left: 100px;
  width: 60%;
  border: solid 1.5px #9fa5a8;
  border-radius: 10px;
  margin-bottom: 20px;
  margin-top: 50px;
  min-width: 1100px;
  height: 280px;
  overflow: hidden;
}
.project-top {
  display: flex;
  justify-content: flex-start;
  padding: 20px 20px 0px 20px;
}
.project-card {
  width: 100%;
  border: solid 1.5px #9fa5a8;
  margin: 0 auto;
  border-radius: 10px;
  height: 95px;
  margin-top: 10px;
  margin-bottom: 15px;
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
</style>
