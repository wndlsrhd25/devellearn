<template>
  <div style="min-height: 100vh">
    <div class="like-top">
      <b-icon icon="heart-fill" font-scale="1.5"></b-icon>
      <h5 style="margin-left: 10px">좋아요 게시글</h5>
    </div>
    <!-- 좋아요 게시글 전체 리스트-->

    <div class="container">
      <div style="display: flex">
        <input
          style="margin-left: 160px"
          type="checkbox"
          v-model="allSelected"
        />
        <label style="margin: 5px 0px 0px 10px">전체선택</label>
        <b-button style="margin-left: 20px" @click="deleteAll">삭제</b-button>
      </div>
      <div>
        <template v-for="item in likeList">
          <template>
            <div class="like-card" :key="item.postNo">
              <input
                type="checkbox"
                :id="item"
                :value="item"
                v-model="selectList"
                style="margin-right: 10px"
              />

              <!-- <span>check: {{ selectList }}</span> -->
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
                <div class="content-box" style="overflow: hidden">
                  <p v-html="item.noneHtmlContent"></p>
                </div>
              </div>

              <div class="delete-box" @click="deleteLike(item.likeNo)">
                <b-button style="margin-left: 20px">삭제</b-button>
              </div>
            </div>
          </template>
        </template>
      </div>
      <b-pagination
        v-if="likeList.length > 0"
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
      likeList: [],
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "10",
      selectList: [],
    };
  },
  computed: {
    //checkox 전체선택
    allSelected: {
      //getter
      get: function () {
        return this.likeList.length === this.selectList.length;
      },
      //setter
      set: function (e) {
        this.selectList = e ? this.likeList : [];
      },
    },
  },
  created() {
    this.getLikeList();
  },
  watch: {
    currentPage() {
      this.getLikeList();
    },
  },
  methods: {
    //전체삭제
    deleteAll() {
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
            console.log("리스트", this.selectList);

            for (let i = 0; i < this.selectList.length; i++) {
              this.$axios
                .delete("/api/deleteBlogLike/" + this.selectList[i].likeNo)
                // async await 를 사용하여통신이 끝날때까지 기다림
                .then(async (resp) => {
                  console.log("하트 삭제", resp.data);
                  await this.getLikeList();
                })
                .catch((error) => {
                  console.log(error);
                });
            }

            this.$swal.fire("삭제완료", "", "success");
          }
        });
    },
    //좋아요 리스트 조회
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
    //단건 삭제
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
            //여기에다가 삭제시 axios 넣으면 됨

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

<style lang="scss" scoped>
.like-top {
  display: flex;
  justify-content: flex-start;
  margin-left: 100px;
}
.container {
  width: 1000px;
  margin-left: 10px;
  margin-top: 20px;
  min-height: 800px;
}

.like-card {
  width: 70%;
  border: solid 1.5px #9fa5a8;
  margin: 0 auto;
  border-radius: 10px;
  height: 100px;
  margin-top: 20px;
  display: flex;
  justify-content: flex-start;
  padding: 10px 15px;
  cursor:pointer;
}
.thumbnail-box {
  width: 90px;
  height: 100%;
}
.titleContent-box {
  margin-left: 20px;
  width: 75%;
  height: 100%;
  text-align: left;
  text-overflow: hidden;
}
.title-box {
  width: 100%;
  height: 30px;
  margin-bottom: 0px;
}
.content-box {
  width: 100%;
  height: 42px;
}
.delete-box {
  display: flex;
  margin: auto 0;
  height: 42px;
}

.paging {
  bottom: 500px;
  position: absolute;
  width: 100%;
}
</style>
