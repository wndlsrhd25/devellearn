<template>
  <div id="blogHome" style="min-height: 100vh">
    <b-modal
      id="bookmark-plus"
      size="m"
      centered
      title="북마크 폴더"
      footerClass="p-2 border-top-0"
      ref="my-modal"
      ><BookPlusModal
        :bookClick="bookClick"
        @reload="postList()"
      ></BookPlusModal>
      <template #modal-footer="{ close }">
        <b-button @click="close()" style="display: none">취소</b-button>
      </template>
    </b-modal>

    <!--게시글 카테고리(폴더) 만들기-->
    <b-modal id="folder-plus" size="m" centered title="카테고리"
      ><FolderPlusModal
        :blogId="id"
        @reloadFolder="postList()"
      ></FolderPlusModal>
      <template #modal-footer="{ close }">
        <b-button @click="close()" style="display: none">확인</b-button>
      </template>
    </b-modal>

    <!-- 사이드바 갈아끼우기-->

    <div style="display: flex">
      <SideBarBlog
        @postContent="ContentInfo"
        v-if="this.$route.query.blogId != this.$store.state.userStore.id"
      />
      <SideBarMyBlog @postContent="ContentInfo" v-else />

      <div class="container">
        <div class="top-box">
          <p class="top-text">
            <b-icon
              class="icon-gear"
              icon="gear-fill"
              style="margin-right: 15px; margin-top: 4px"
              v-b-modal.folder-plus
              v-if="
                this.$route.query.blogId == this.$store.state.userStore.id &&
                this.$route.query.folderName == '전체'
              "
            ></b-icon
            >블로그
          </p>
          <p class="top-all" v-if="this.$route.query.folderName == null">
            전체
          </p>
          <p class="top-all" v-else>
            {{ this.$route.query.folderName }}
          </p>
          <p class="top-count">{{ postContent.length }}</p>

          <b-button
            class="write-btn"
            @click="moveWrite"
            style="margin-left: auto"
            v-if="this.$route.query.blogId == this.$store.state.userStore.id"
            >글쓰기</b-button
          >
          <b-button
            variant="primary"
            style="
              float: right;
              margin-left: 20px;
              outline: none !important;
              box-shadow: none !important;
            "
            @click="moveBlogHome"
            v-if="this.$route.query.blogId == this.$store.state.userStore.id"
            >블로그홈</b-button
          >
        </div>

        <div v-if="this.$route.query.folderName == '전체'">
          <BlogSearchBar
            :ph="placeholder"
            @searchBlog="(text) => (searchCondition = text)"
            @searchTagBlog="(text) => (searchTagCondition = text)"
          />
        </div>

        <!-- 사진카드하나 시작 -->
        <div class="card-box">
          <template v-for="item in postContent">
            <template>
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
                  <!-- 북마크 아이콘 -->
                  <div>
                    <b-icon
                      v-if="item.readStatus == '비공개'"
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

                  <!-- <img class="card-header-img" :src="item.thumbnail" alt="이미지" /> -->
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
                      <p class="card-body-comment-count">
                        {{ item.commentCnt }}
                      </p>
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
                      style="
                        margin-bottom: 8px;
                        opacity: 1;
                        border-color: #294890;
                      "
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
          <!-- 사진카드 하나 끝-->
          <b-pagination
            v-if="postContent.length > 0"
            v-model="currentPage"
            :total-rows="rows"
            :per-page="listPage"
            align="center"
          ></b-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogSearchBar from "@/components/blog/blogMain/BlogSearchBar.vue";
import SideBarBlog from "@/components/blog/blogHome/SideBarBlog.vue";
import SideBarMyBlog from "@/components/blog/blogHome/SideBarMyBlog.vue";
import BookPlusModal from "@/components/blog/blogMain/BookPlusModal.vue";
import FolderPlusModal from "@/components/blog/blogPost/FolderPlusModal.vue";
import { eventBus } from "../../../main.js";

export default {
  props: ["blogId", "folderName", "searchCondition", "searchTagCondition"],
  data() {
    return {
      placeholder: "제목,내용으로 검색",
      postContent: {},
      //blogId: "",
      profile: "",
      value: "",
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      rows: "1",
      //페이지당 내가 볼 개수
      listPage: "9",
      bookClick: "",
      id: this.$route.query.blogId,
      isSearch: false,
    };
  },
  components: {
    BlogSearchBar,
    SideBarBlog,
    SideBarMyBlog,
    BookPlusModal,
    FolderPlusModal,
  },
  created() {
    this.postList();
  },
  watch: {
    currentPage() {
      if (!this.isSearch) {
        this.postList();
        window.scrollTo({ top: 0, left: 0, behavior: "auto" });
        return;
      }
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
      this.search();
    },
    searchCondition() {
      if (this.searchCondition == "" && this.searchTagCondition == "") {
        this.postList();
        window.scrollTo({ top: 0, left: 0, behavior: "auto" });
        this.isSearch = false;
      }
      this.search();
      this.isSearch = true;
    },
    searchTagCondition() {
      if (this.searchTagCondition == "" && this.searchCondition == "") {
        this.postList();
        window.scrollTo({ top: 0, left: 0, behavior: "auto" });
        this.isSearch = false;
      }
      this.search();
      this.isSearch = true;
    },
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
    close() {
      this.postList();
    },
    moveWrite() {
      this.$router.push("/postWrite").catch(() => {});
    },
    //게시글 검색
    search() {
      console.log("검색내용", this.searchCondition);
      console.log("태그내용", this.searchTagCondition);
      this.$axios
        .post("/api/blogs/postHome/search", {
          keyword: this.searchCondition,
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
          blogId: this.$route.query.blogId,
          tags: this.searchTagCondition,
        })
        .then((resp) => {
          console.log(resp);
          this.postContent = resp.data.list;
          this.rows = resp.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //사이드바 클릭시 데이터 가져오기
    ContentInfo(info) {
      console.log(info);
      this.postContent = info;
    },
    //게시글 리스트
    postList() {
      if (
        this.$route.query.folderName == null ||
        this.$route.query.folderName == "전체"
      ) {
        this.$axios
          .post("/api/blogs/postHome", {
            memberId: this.$store.state.userStore.id,
            blogId: this.$route.query.blogId,
            pageNum: this.currentPage,
            pageSize: this.listPage,
          })
          .then((resp) => {
            console.log(resp);
            this.postContent = resp.data.list;
            this.rows = resp.data.total;
            console.log("글수" + this.postContent.length);
          })
          .catch(function (error) {
            console.log("error", error);
          });
        //폴더별
      } else {
        this.$axios
          .post("/api/blogs/postHome/folder", {
            memberId: this.$store.state.userStore.id,
            blogId: this.$route.query.blogId,
            folderName: this.$route.query.folderName,
            pageNum: this.currentPage, //현재 페이지
            pageSize: this.listPage, //페이지당 보여줄 글 수
          })
          .then((resp) => {
            console.log(resp);
            this.postContent = resp.data.list;
            this.rows = resp.data.total; //총 게시글 수
          })
          .catch(function (error) {
            console.log("error", error);
          });
      }
    },
    reload() {
      this.$emit("reload");
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
          confirmButtonText: "승인",
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

                //눈에 보이는 것을 지워줌
                for (let i = 0; i < this.postContent.length; i++) {
                  if (item.postNo == this.postContent[i].postNo) {
                    this.postContent[i].book = 0;
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
    changeDate: function (date) {
      return moment(date).format("YYYY.MM.DD  HH:mm");
    },
    //블로그홈으로
    moveBlogHome() {
      this.$router
        .push({
          path: "myBlogHome",
          query: { blogId: this.$store.state.userStore.id },
        })
        .catch(() => {});
    },
  },
};
</script>

<style scoped>
::v-deep .v-application--wrap {
  min-height: fit-content;
}
.container {
  margin-left: 0;
  width: 70%;
}

.card-box {
  width: 100%;
}
.top-text {
  margin-left: 120px;
  text-align: left;
  font-size: x-large;
}
.top-box {
  display: flex;
  height: 40px;
}
.top-all {
  margin-left: 20px;
  margin-top: 5px;
}
.top-count {
  margin-left: 10px;
  margin-top: 5px;
  color: green;
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

/* 오른쪽 박스*/
.m-md-2 {
  position: fixed;
  top: 200px;
  right: 10%;
}
.input-group-text {
  height: 50px;
  width: 100px;
}
</style>
