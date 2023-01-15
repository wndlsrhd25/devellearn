<template>
  <div style="min-height: 100vh">
    <!-- 이 div안에 작성 -->
    <div class="top-box">
      <b-modal
        id="bookmark-plus"
        size="m"
        centered
        title="북마크 폴더"
        footerClass="p-2 border-top-0"
        ref="my-modal"
        ><BookPlusModal
          :bookClick="bookClick"
          @reload="getList()"
        ></BookPlusModal>
        <template #modal-footer="{ close }">
          <b-button @click="close()" style="display: none">취소</b-button>
        </template>
      </b-modal>
      <h1
        style="display: inline-block, float: left, width:50px; margin-bottom:20px;"
      >
        블로그
      </h1>

      <!--버튼 -->
      <b-button
        variant="primary"
        style="float: right; margin-right: 200px outline:none !important; box-shadow:none !important;"
        @click="moveBlogHome"
        >블로그홈</b-button
      >
    </div>

    <!-- 블로그 검색 창-->

    <BlogSearchBar
      :ph="placeholder"
      @searchBlog="(text) => (searchCondition = text)"
      @searchTagBlog="(text) => (searchTagCondition = text)"
      @reload="getList()"
    ></BlogSearchBar>

    <div class="align-box">
      <div style="margin-left: 70px; float: right">
        <p
          style="
            float: left;
            font-size: medium;
            cursor: pointer;
            padding-top: 10px;
          "
          @click="order = 'post_no'"
        >
          작성순
        </p>

        <p
          style="
            margin-left: 15px;
            font-size: medium;
            padding-top: 10px;
            cursor: pointer;
            float: left;
          "
          @click="order = 'likes'"
        >
          좋아요순
        </p>

        <p
          style="
            margin-left: 15px;
            font-size: medium;
            padding-top: 10px;
            cursor: pointer;
            float: left;
          "
          @click="order = 'comments'"
        >
          댓글순
        </p>

        <b-button
          class="write-btn"
          @click="moveWrite"
          style="margin-left: 40px"
        >
          글쓰기</b-button
        >
      </div>
    </div>

    <div
      style="width: 80%; margin: 0 auto; position: relative; min-height: 2000px"
    >
      <!-- 사진카드하나 시작 -->

      <template v-for="item in blogList">
        <template v-if="item.postNo != '' || item.postNo != null">
          <div
            class="card"
            :key="item.postNo"
            @click="
              $router
                .push({
                  path: '/postDetail',
                  query: { postNo: item.postNo, blogId: item.blogId },
                })
                .catch(() => {})
            "
          >
            <div class="card-header">
              <!--북마크-->
              <div>
                <b-icon
                  v-if="
                    item.readStatus == '비공개' || item.readStatus == '멤버공개'
                  "
                  class="secret"
                  icon="lock-fill"
                  font-scale="1.5"
                ></b-icon>
                <b-icon
                  v-if="item.book != ''"
                  class="bookmark"
                  @click.stop="deleteBook(item)"
                  icon="bookmark-fill"
                  font-scale="1.5"
                ></b-icon>
                <b-icon
                  v-else
                  class="bookmark"
                  @click.stop="insertBook(item)"
                  icon="bookmark"
                  font-scale="1.5"
                ></b-icon>
              </div>
              <!-- 카드 헤더 -->

              <img
                class="card-header-img"
                :src="'/api/images/blog/' + item.thumbnail"
                alt="이미지"
              />
            </div>

            <!--  카드 바디 -->
            <div class="card-body">
              <!--  카드 바디 헤더 -->
              <div class="card-body-header">
                <h2>{{ item.title }}</h2>
                <p class="card-body-hashtag">{{ item.tags }}</p>

                <div class="card-body-info">
                  <p class="card-body-date">
                    {{ changeDate(item.createDate) }}
                  </p>

                  <p class="card-body-comment">댓글</p>
                  <p class="card-body-comment-count">{{ item.comments }}</p>
                </div>
              </div>
              <p
                class="card-body-description"
                v-html="item.noneHtmlContent"
              ></p>
              <!--  카드 바디 본문 -->

              <!--  카드 바디 푸터 -->
              <div class="card-body-footer">
                <hr
                  style="margin-bottom: 8px; opacity: 1; border-color: #294890"
                />
                <p class="nick-name">{{ item.memberId }}</p>
                <p class="likes-count">{{ item.likes }}</p>
                <b-icon
                  class="icon-likes"
                  icon="heart-fill"
                  font-scale="1"
                ></b-icon>
              </div>
            </div>
          </div>
        </template>
      </template>
      <b-pagination
        v-model="currentPage"
        :total-rows="row"
        :per-page="listPage"
        align="center"
      ></b-pagination>
    </div>
    <!-- 사진카드 하나 끝-->
  </div>
</template>

<script>
import BlogSearchBar from "@/components/blog/blogMain/BlogSearchBar.vue";

import BookPlusModal from "@/components/blog/blogMain/BookPlusModal.vue";

export default {
  data() {
    return {
      placeholder: "제목,내용으로 검색",
      blogList: [],
      profile: "",
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "12",
      selectedFolder: "",
      bookClick: "",
      order: "post_no",
      isSearch: false,
    };
  },
  props: ["searchCondition", "searchTagCondition"],

  created() {
    //디테일에서 태그검색으로 넘어올때
    if (
      this.$route.params.tagDetail != "" ||
      this.$route.params.tagDetail != null
    ) {
      console.log("보낸 태그:" + this.$route.params.tagDetail);
      this.searchTagCondition = this.$route.params.tagDetail;
      this.search();
      return;
    }
    this.getList();
  },
  //페이지 이동할때 변화를 감지해서 다시 리스트를 돌려야함
  watch: {
    currentPage() {
      if (!this.isSearch) {
        this.getList();
        window.scrollTo({ top: 0, left: 0, behavior: "auto" });
        return;
      }
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
      this.search();
    },
    order() {
      //검색중인 내용이 없으면 전체리스트
      if (!this.isSearch) {
        this.getList();
        return;
      }
      //검색중인 내용이 있으면 검색리스트
      this.search();
    },
    searchCondition() {
      if (this.searchCondition == "" && this.searchTagCondition == "") {
        this.getList();
        window.scrollTo({ top: 0, left: 0, behavior: "auto" });
        this.isSearch = false;
      }
      this.search();
      this.isSearch = true;
    },
    searchTagCondition() {
      if (this.searchTagCondition == "" && this.searchCondition == "") {
        this.getList();
        window.scrollTo({ top: 0, left: 0, behavior: "auto" });
        this.isSearch = false;
      }
      this.search();
      this.isSearch = true;
    },
  },
  components: {
    BlogSearchBar,
    BookPlusModal,
  },
  methods: {
    //검색가져오기
    searchBlog(text) {
      console.log(text);
      this.searchCondition = text;
    },
    //태그검색 가져오기
    searchTagBlog(text) {
      console.log(text);
      this.searchTagCondition = text;
    },
    //게시글 검색
    search() {
      //search axios 돌려서 list안에 넣기
      console.log("검색내용", this.searchCondition);
      console.log("태그내용", this.searchTagCondition);
      this.$axios
        .post("/api/blogs/search", {
          keyword: this.searchCondition,
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
          order: this.order,
          tags: this.searchTagCondition,
        })
        .then((resp) => {
          console.log(resp);
          this.blogList = resp.data.list;
          this.row = resp.data.total;
          console.log(this.blogList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //북마크 등록
    insertBook(item) {
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
        this.$refs["my-modal"].show();
        this.bookClick = item.postNo;
      }
    },
    //북마크 삭제
    deleteBook(item) {
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "",
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
            console.log("삭제됨");
            this.$axios
              .delete("/api/deleteBookmark/" + item.book)
              .then((resp) => {
                console.log("북마크 삭제", resp.data);
                //this.blogList = resp.data;
                //눈에 보이는 것을 지워줌
                for (let i = 0; i < this.blogList.length; i++) {
                  if (item.postNo == this.blogList[i].postNo) {
                    this.blogList[i].book = 0;
                  }
                }
              })
              .catch((error) => {
                console.log(error);
              });

            this.$swal.fire({
              title: "삭제완료",
              icon: "success",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        });
    },
    //블로그홈으로 이동하기
    moveBlogHome() {
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
        this.$router
          .push({
            path: "myBlogHome",
            query: { blogId: this.$store.state.userStore.id },
          })
          .catch(() => {});
      }
    },
    //전체리스트 조회
    getList() {
      this.$axios
        .post("/api/blogs", {
          pageNum: this.currentPage,
          memberId: this.$store.state.userStore.id,
          order: this.order,
          pageSize: this.listPage,
        })
        .then((resp) => {
          //this.blogList = resp.data에다가 .list 붙여줘야함

          this.blogList = resp.data.list;
          //16개가 리스트에 담겨잇음
          this.row = resp.data.total;
          console.log(this.blogList);
        });
    },
    close() {
      this.blogList();
    },
    //글쓰기
    moveWrite() {
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
        this.$router.push("/postWrite").catch(() => {});
      }
    },
    changeDate: function (date) {
      return moment(date).format("YYYY.MM.DD  HH:mm");
    },
  },
};
</script>

<style scoped>
::v-deep .v-application--wrap {
  min-height: 0;
}
.btn-primary {
  background-color: rgb(52, 152, 219);
  border-color: rgb(52, 152, 219);
}
.top-box {
  width: 80%;
  margin: 0px auto;
  min-width: 1300px;
}

/* 검색창 */
.search-box {
  width: 70%;
  margin: 0 auto;
}

/* 정렬*/
.align-box {
  display: flex;
  justify-content: right;
  width: 80%;
  min-width: 1200px;
  margin: 0 auto;
}

/* 카드 이미지 */
.card {
  height: 400px;
  width: 18%;
  border-radius: 15px;
  display: inline-block;
  margin-top: 30px;
  margin-left: 30px;
  margin-right: 30px;
  margin-bottom: 30px;
  position: relative;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  overflow: hidden;
  min-width: 300px;
  cursor: pointer;
}

.card-header {
  -webkit-transition: 0.3s; /*사파리 & 크롬*/
  -moz-transition: 0.3s; /*파이어폭스*/
  -ms-transition: 0.3s; /*인터넷 익스플로러*/
  -o-transition: 0.3s; /*오페라*/
  transition: 0.3s;
  width: 100%;
  height: 250px;
  border-radius: 15px 15px 0 0;
  padding: 0px 0px;
}

.card-header-img {
  width: 100%;
  height: 100%;
}
.bookmark {
  transition: 0.3s;
  position: absolute;
  color: #000000;
  top: 10px;
  right: 9px;
  outline: 0;
}
.secret {
  transition: 0.3s;
  position: absolute;
  color: #000000;
  top: 10px;
  left: 9px;
  outline: 0;
}

.card:hover .card-header {
  opacity: 0.5;
  height: 230px;
}

h2 {
  font-size: 18px;
  font-weight: bold;
  color: #000000;
  overflow: hidden;
  height: 21px;
  padding-top: 3px;
}

.card-body {
  padding: 0px 0px;
}

.card-body-header {
  margin: 10px 20px 0px 20px;
  text-align: left;
}

.card-body-description {
  opacity: 0;
  color: #3e4144;

  text-align: left;
  -webkit-transition: 0.2s ease-in-out; /*사파리&크롬*/
  -moz-transition: 0.2s ease-in-out; /*파이어폭스*/
  -ms-transition: 0.2s ease-in-out; /*익스플로러*/
  -o-transition: 0.2s ease-in-out; /*오페라*/
  transition: 0.2s ease-in-out;
  overflow: hidden;
  height: 20px;
  margin: 5px 5px;
  padding: 0 10px 0 10px;
}

.card:hover .card-body-description {
  opacity: 1;
  -webkit-transition: 0.3s ease-in-out;
  -moz-transition: 0.3s ease-in-out;
  -ms-transition: 0.3s ease-in-out;
  -o-transition: 0.3s ease-in-out;
  transition: 0.3s ease-in-out;
}

.card-body-hashtag {
  color: #339ed3;
  font-style: italic;
  margin-bottom: 0px;
  overflow: hidden;
  height: 26px;
  padding-top: 10px;
}

.card-body-info {
  color: #000000;
  display: flex;
  padding: 10px 0 0 0;
  overflow: hidden;
  height: 30px;
}
.card-body-date {
  margin: 0px;
  overflow: hidden;
}
.card-body-comment {
  margin: 0px 0px 0px 15px;
  overflow: hidden;
}
.card-body-comment-count {
  margin: 0px 0px 0px 5px;
  overflow: hidden;
}

.card-body-footer {
  position: absolute;
  margin-top: 5px;
  margin-bottom: 6px;
  bottom: 0;
  width: 100%;
  height: 10%;
  font-size: 14px;
  color: #9fa5a8;
  padding: 0 15px;
  overflow: hidden;
}

hr {
  margin-top: 3px;
}
.nick-name {
  margin-left: 5px;
  float: left;
  color: #000000;
}
.icon-likes {
  float: right;
  margin: 5px 10px;
  color: #ef3e41;
}
.likes-count {
  float: right;
  color: #1d1d1d;
  padding-top: 1px;
}

/*페이징*/
</style>
