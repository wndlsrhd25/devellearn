<template>
  <div class="follow-box">
    <div class="follow-top">
      <b-icon icon="people-fill" font-scale="2"></b-icon>
      <h5 style="margin-left: 10px">팔로우 블로그</h5>
      <h6
        style="margin-left: auto; cursor: pointer"
        @click="$router.push({ path: '/myBlogHome/follow' }).catch(() => {})"
      >
        전체보기
      </h6>
    </div>

    <div class="wrap-follow">
      <template v-for="item in followList">
        <template>
          <div class="follow-card" :key="item.memberId">
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
              <h4 style="text-align: left; margin-left: 10px">
                {{ item.memberId }}님의 블로그
              </h4>
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
  components: {},
  created() {
    this.getFollowList();
  },
  watch: {
    currentPage() {
      this.getFollowList();
    },
  },
  methods: {
    //팔로우 리스트 조회
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
          console.log(this.followList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
<style scope>
.follow-box {
  width: 33%;
  border: solid 1.5px #9fa5a8;
  border-radius: 10px;
  margin-bottom: 20px;
  margin-top: 50px;
  min-width: 535px;
  height: 400px;
  text-align: center;
  display: inline-block;
  vertical-align: middle;
  margin-right: 21px;
  overflow: hidden;
}

.follow-top {
  display: flex;
  justify-content: flex-start;
  padding: 20px 20px 0px 20px;
}

.wrap-follow {
  height: 24%;
}

.follow-card {
  width: 90%;
  border: solid 1.5px #9fa5a8;
  margin: 0 auto;
  border-radius: 10px;
  height: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  padding: 8px 10px;
  cursor: pointer;
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
  margin: auto 0;
}
.house-icon {
  margin: auto 0;
}
</style>
