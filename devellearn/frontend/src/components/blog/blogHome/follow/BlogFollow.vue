<template>
  <div style="min-height: 100vh">
    <div class="follow-top">
      <b-icon icon="people-fill" font-scale="1.5"></b-icon>
      <h5 style="margin-left: 10px">팔로우 블로그</h5>
    </div>
    <!-- 팔로우 블로그 전체 리스트-->
    <div class="container">
      <template v-for="item in followList">
        <template>
          <div class="follow-card" :key="item.memberId">
            <div class="title-box">
              <div
                class="img-box"
                @click="
                  $router
                    .push({
                      path: '/postHome',
                      query: { blogId: item.memberId },
                    })
                    .catch(() => {})
                "
              >
                <img
                  class="card-img"
                  :src="'/api/images/profile/' + item.profile"
                  alt="이미지"
                />
              </div>
              <div
                class="name-box"
                @click="
                  $router
                    .push({
                      path: '/postHome',
                      query: { blogId: item.memberId },
                    })
                    .catch(() => {})
                "
              >
                <h3 style="text-align: left; margin-left: 10px">
                  {{ item.memberId }}님의 블로그
                </h3>
              </div>
            </div>
            <div class="house-icon">
              <b-icon
                icon="house-door"
                font-scale="2"
                style="margin: auto 0"
                @click="
                  $router
                    .push({
                      path: '/postHome',
                      query: { blogId: item.memberId },
                    })
                    .catch(() => {})
                "
              ></b-icon>
            </div>
          </div>
        </template>
      </template>

      <b-pagination
        v-if="followList.length>0"
        class="paging"
        v-model="currentPage"
        :total-rows="row"
        :per-page="listPage"
        align="center"
      ></b-pagination>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      followList: [],
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "10",
    };
  },
  created() {
    this.getFollowList();
  },
  watch: {
    currentPage() {
      this.getFollowList();
    },
  },
  methods: {
    getFollowList() {
      this.$axios
        .post("/api/followList", {
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
        })
        .then((resp) => {
          console.log(resp);
          this.followList = resp.data.list;
          this.row = resp.data.total;
          console.log("팔로우 리스트", this.followList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.follow-top {
  display: flex;
  justify-content: flex-start;
  margin-left: 100px;
  padding: 0px 0px;
  min-width: 1100px;
}
.container {
  width: 1000px;
  margin-left: 100px;
  min-height: 1000px;
  position: relative;
}
.follow-card {
  width: 70%;
  border: solid 1.5px #9fa5a8;
  border-radius: 10px;
  height: 100%;
  justify-content: space-between;
  margin-top: 30px;
  padding: 8px 10px;
  margin-left: 60px;
  cursor: pointer;
}
.title-box {
  display: flex;
}
.img-box {
  width: 70px;
  height: 70px;
  border-radius: 70%;
  overflow: hidden;
  margin: auto 0;
}
.card-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.name-box {
  width: 400px;

  margin-left: 10px;
}
.house-icon {
  margin: auto 0;
  width: 50px;
}

/*페이징*/
.paging {
  bottom: 10px;
  position: absolute;
  width: 100%;
}
</style>
