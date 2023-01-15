<template>
  <div class="like-box">
    <div class="like-top">
      <b-icon icon="heart-fill" font-scale="2"></b-icon>
      <h5 style="margin-left: 10px">좋아요 게시글</h5>
      <h6
        style="margin-left: auto; cursor: pointer"
        @click="$router.push({ path: '/myBlogHome/like' }).catch(() => {})"
      >
        전체보기
      </h6>
    </div>

    <template v-for="item in likeList">
      <template>
        <div class="like-card" :key="item.postNo">
          <div
            class="thumbnail-box"
            @click="
              $router
                .push({
                  path: '/postDetail',
                  query: { postNo: item.postNo, blogId: item.blogId },
                })
                .catch(() => {})
            "
          >
            <img
              class="card-img"
              :src="'/api/images/blog/' + item.thumbnail"
              alt="이미지"
            />
          </div>
          <div
            class="titleContent-box"
            @click="
              $router
                .push({
                  path: '/postDetail',
                  query: { postNo: item.postNo, blogId: item.blogId },
                })
                .catch(() => {})
            "
          >
            <div class="title-box">
              <h4 style="margin-bottom: 0; overflow: hidden; height: 20px">
                {{ item.title }}
              </h4>
            </div>
            <div class="content-box">
              <p style="overflow: hidden" v-html="item.noneHtmlContent"></p>
            </div>
          </div>

          <div class="delete-box">
            <b-button style="margin-left: 10px" @click="deleteLike(item.likeNo)"
              >삭제</b-button
            >
          </div>
        </div>
      </template>
    </template>
  </div>
</template>
<script>
export default {
  data() {
    return {
      likeList: [],
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
    this.getLikeList();
  },
  watch: {
    currentPage() {
      this.getLikeList();
    },
  },
  methods: {
    getLikeList() {
      this.$axios
        .post("/api/blogLikeCard", {
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
        })
        .then((resp) => {
          // console.log(resp);
          this.likeList = resp.data.list;
          this.row = resp.data.total;
          console.log(this.likeList);
        });
    },
    deleteLike(no) {
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            console.log(no);
            this.$axios
              .delete("/api/deleteBlogLike/" + no)
              .then((resp) => {
                console.log("하트 삭제", resp.data);
                this.likeList = resp.data;

                this.getLikeList();
              })
              .catch((error) => {
                console.log(error);
              });

            this.$swal.fire("삭제완료", "", "success");
          }
        });
    },
  },
};
</script>
<style scoped>
.like-box {
  width: 33%;
  border: solid 1.5px #9fa5a8;
  border-radius: 10px;
  margin-bottom: 20px;
  margin-top: 50px;
  min-width: 535px;
  height: 400px;
  display: inline-block;
  vertical-align: middle;
  margin-left: 21px;
  overflow: hidden;
  padding: 0;
}

.like-top {
  display: flex;
  justify-content: flex-start;
  padding: 20px 20px 0px 20px;
}

.like-card {
  width: 90%;
  border: solid 1.5px #9fa5a8;
  margin: 0 auto;
  border-radius: 10px;
  height: 23%;
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  padding: 8px 10px;
  cursor:pointer;
}
.thumbnail-box {
  width: 90px;
  height: 100%;
}
.titleContent-box {
  margin-left: 20px;
  width: 300px;
  height: 100%;
  text-align: left;
  text-overflow: hidden;
}
.title-box {
  width: 300px;
  height: 30px;
  text-overflow: hidden;
  margin-bottom: 0px;
}
.content-box {
  width: 300px;
  height: 42px;
  overflow: hidden;
}
.delete-box {
  display: flex;
  margin: auto 0;
  height: 42px;
}
</style>
