<template>
  <div style="min-height: 100vh">
    <div data-app>
      <v-dialog v-model="dialog" persistent max-width="500px">
        <ReportModal2 :report="report" @close="dialog = false"></ReportModal2>
      </v-dialog>
    </div>
    <!-- 사이드바를 갈아 끼움-->

    <!-- 게시글 내용-->
    <div style="display: flex">
      <!--본인 블로그 사이드바-->
      <SideBarMyBlog
        v-if="this.$route.query.blogId == this.$store.state.userStore.id"
      />

      <!--프로젝트 블로그 사이드바-->
      <SideBarProject
        v-else-if="this.$route.query.blogId != postContent.memberId"
      />

      <!--타인 블로그 사이드바-->
      <SideBarBlog v-else />

      <!-- 북마크 모달창 -->
      <b-modal
        id="bookmark-plus"
        size="m"
        centered
        title="북마크 폴더"
        footerClass="p-2 border-top-0"
        ref="my-modal"
        ><BookPlusModal
          :bookClick="bookClick"
          @reload="postContentInfo"
        ></BookPlusModal>
        <template #modal-footer="{ close }">
          <b-button @click="close()" style="display: none">취소</b-button>
        </template>
      </b-modal>

      <div class="postContents">
        <!-- 제목 -->
        <div class="title">
          <h3>{{ postContent.title }}</h3>
        </div>

        <!-- 상단 내용-->
        <div class="member-date">
          <div>
            <span>{{ postContent.memberId }}</span>
            <span style="margin-left: 20px">
              {{ changeDate(postContent.createDate) }}
              <span style="margin-left: 20px">
                <b-icon icon="eye" font-scale="1"></b-icon>
                <span style="margin-left: 5px">{{
                  postContent.hits
                }}</span></span
              >
            </span>
            <span style="margin-left: 20px">
              <b-icon icon="heart-fill" font-scale="1"></b-icon
              ><span style="margin-left: 5px">{{ likeCnt }}</span>
            </span>
          </div>
          <div>
            <span
              ><b-icon
                icon="folder"
                font-scale="1"
                style="margin-right: 10px"
              ></b-icon
              >{{ postContent.folderName }}</span
            >
          </div>
        </div>

        <!-- 내용-->
        <div class="content">
          <hr />
          <p v-html="postContent.content"></p>
        </div>
        <!-- 태그 -->
        <div class="tag">
          <hr />
          <div style="height: 25px; margin: 0 auto; display: flex">
            <span style="color: gray !important">Tags&nbsp;&nbsp;&nbsp;</span>
            <span
              style="cursor: pointer"
              v-for="(tag, i) of tags"
              :key="i"
              @click="searchTag(tag)"
              >#{{ tag }}&nbsp;</span
            >
          </div>
          <hr />
        </div>
      </div>
    </div>

    <!-- 오른쪽 바-->
    <div class="right-bar">
      <!--뒤로가기-->
      <div @click="moveBack">
        <b-input-group-prepend is-text>
          <b-icon icon="arrow-left" font-scale="1.5"></b-icon>
        </b-input-group-prepend>
      </div>

      <!--북마크-->
      <div>
        <b-input-group-prepend is-text>
          <b-icon
            v-if="postContent.book != ''"
            class="bookmark"
            @click.stop="deleteBook(postContent)"
            icon="bookmark-fill"
            font-scale="1.5"
          ></b-icon>
          <b-icon
            v-else
            class="bookmark"
            @click.stop="insertBook(postContent)"
            icon="bookmark"
            font-scale="1.5"
          ></b-icon>
        </b-input-group-prepend>
      </div>

      <!--좋아요-->
      <div>
        <b-input-group-prepend is-text>
          <b-icon
            :icon="isLiked == false ? 'heart' : 'heart-fill'"
            variant="danger"
            font-scale="1.5"
            @click.stop="clickLiked"
          ></b-icon>
        </b-input-group-prepend>
      </div>

      <!-- 신고하기-->
      <div v-if="postContent.memberId != $store.state.userStore.id">
        <b-input-group-prepend is-text>
          <b-icon
            icon="exclamation-octagon"
            font-scale="1.5"
            @click="reportPost(postContent)"
          ></b-icon>
        </b-input-group-prepend>
      </div>

      <!-- 카카오톡 공유하기 -->
      <div>
        <b-input-group-prepend is-text>
          <img
            src="@/assets/images/카카오톡 로고.png"
            class="kakao"
            @click="shareKakao(postContent)"
          />
        </b-input-group-prepend>
      </div>
      <!-- 삭제 -->
      <div v-if="this.$store.state.userStore.id == postContent.memberId">
        <div>
          <b-input-group-prepend is-text>
            <b-icon
              icon="trash"
              font-scale="1.5"
              @click.stop="deletePost(postContent)"
              v-if="postContent.memberId == postContent.blogId"
            ></b-icon>
            <b-icon
              v-else
              icon="trash"
              font-scale="1.5"
              @click.stop="deletePost(postContent)"
            ></b-icon>
          </b-input-group-prepend>
        </div>

        <!-- 수정하기 -->
        <div>
          <b-input-group-prepend is-text>
            <b-icon
              icon="pencil"
              font-scale="1.5"
              @click.stop="updatePost"
              v-if="postContent.memberId == postContent.blogId"
            ></b-icon>
            <b-icon
              v-else
              icon="pencil"
              font-scale="1.5"
              @click.stop="updateProject"
            ></b-icon>
          </b-input-group-prepend>
        </div>
      </div>
    </div>
    <img
      id="test_obj"
      :src="'/api/images/blog/' + postContent.thumbnail"
      style="display: none"
    />
    <!-- 댓글-->
    <div v-if="postContent.commentSet == '허용'" class="comment">
      <PostComment />
    </div>
  </div>
</template>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
import SideBarBlog from "@/components/blog/blogHome/SideBarBlog.vue";
import SideBarMyBlog from "@/components/blog/blogHome/SideBarMyBlog.vue";
import SideBarProject from "@/components/blog/blogHome/SideBarProject.vue";
import PostComment from "@/components/blog/blogPost/PostComment.vue";
import BookPlusModal from "@/components/blog/blogMain/BookPlusModal.vue";
import ReportModal2 from "@/components/common/ReportModal2.vue";

export default {
  props: ["blogId", "postNo"],
  data() {
    return {
      postContent: "",
      isLiked: false,
      likePost: {},
      likeCnt: "",
      selectedFolder: "",
      bookClick: "",
      dialog: false,
      report: {},
      projectProfile: "",
      tags: [],
      // contentInfo:"",
    };
  },
  components: {
    SideBarBlog,
    //PostContents,
    SideBarMyBlog,
    PostComment,
    BookPlusModal,
    ReportModal2,
    SideBarProject,
  },

  created() {
    // this.getProjectThumbnail();
    // this.getProfile();
    //this.likedCheck();  //좋아요 여부
    //this.blogLikeCnt(); //조하용 수 카운트
    this.updateHits();
  },
  methods: {
    //조회수 조회
    updateHits() {
      if (this.$route.query.blogId != this.$store.state.userStore.id) {
        this.$axios
          .post("/api/blogs/hits", {
            postNo: this.$route.query.postNo,
          })
          .then((resp) => {
            this.postContentInfo();
          })
          .catch(function (error) {
            console.log("error", error);
          });
      } else {
        this.postContentInfo();
      }
    },
    //신고창 띄우기
    reportPost(postContent) {
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
        this.report.postCode = "blog_post";
        this.report.postNo = postContent.postNo;
        this.dialog = true;
      }
    },
    //게시글 수정하기
    updatePost() {
      this.$router
        .push({
          path: "/postEdit",
          query: {
            blogId: this.$store.state.userStore.id,
            postNo: this.$route.query.postNo,
          },
        })
        .catch(() => {});
    },
    //프로젝트 게시글 수정하기
    updateProject() {
      this.$router
        .push({
          path: "/projectEdit",
          query: {
            blogId: this.$route.query.blogId,
            postNo: this.$route.query.postNo,
          },
        })
        .catch(() => {});
    },
    //뒤로가기
    moveBack() {
      this.$router
        .push({
          path: "/blog",
        })
        .catch(() => {});
    },

    //태그 검색
    searchTag(searchT) {
      this.$router
        .push({ name: "blog", params: { tagDetail: searchT } })
        .catch(() => {});
    },

    //디테일 내용물
    postContentInfo: function () {
      this.$axios
        .post("/api/blogs/detailPost", {
          memberId: this.$store.state.userStore.id,
          postNo: this.$route.query.postNo,
        })
        .then((resp) => {
          console.log(resp);
          this.postContent = resp.data;

          let spliceTag = "";
          spliceTag = this.postContent.tags.split("#");
          spliceTag = spliceTag.slice(1, spliceTag.length);
          console.log(spliceTag);

          this.tags = spliceTag;

          //좋아요 여부 조회
          console.log(resp.data.blogLikeVO);

          if (resp.data.blogLikeVO != null) {
            this.isLiked = true;
            this.likePost = resp.data;
          } else {
            this.isLiked = false;
          }
          //좋아요 건수
          this.likeCnt = resp.data.likes;

          console.log("제목 : " + this.postContent.title);
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },

    //프로필만 조회하기
    getProfile() {
      this.$axios
        .get("/api/blogs/@" + this.$route.query.blogId + "/profile")
        .then((resp) => {
          console.log("프로필 조회 돌아감", resp);
          this.profile = resp.data;
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },

    //에디터 내용 가져오기
    getContent(editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
    },

    //좋아요 여부 확인
    likedCheck() {
      this.$axios
        .post("/api/blogLike", {
          memberId: this.$store.state.userStore.id,
          postNo: this.$route.query.postNo,
        })
        .then((resp) => {
          console.log("like", resp.data);
          if (resp.data != "") {
            this.isLiked = true;
            this.likePost = resp.data;
          } else {
            this.isLiked = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    clickLiked() {
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

        //좋아요 취소
        if (this.isLiked == true) {
          this.$axios
            .delete("/api/deleteBlogLike/" + this.likePost.likeNo)
            .then((resp) => {
              console.log("하트 삭제", resp.data);
              if (resp.data != "") {
                this.isLiked = !this.isLiked;
                this.blogLikeCnt();
              }
            })
            .catch((error) => {
              console.log(error);
            });
        } else {
          //좋아요 추가
          this.$axios
            .post("/api/insertBlogLike", {
              memberId: this.$store.state.userStore.id,
              postNo: this.$route.query.postNo,
            })
            .then((resp) => {
              console.log("하트 등록", resp.data);
              if (resp.data != "") {
                this.isLiked = !this.isLiked;
                this.likePost = resp.data;
                this.blogLikeCnt();
              }
            })
            .catch((error) => {
              console.log(error);
            });
        }
      }
    },
    //좋아요 수 카운트
    blogLikeCnt() {
      this.$axios
        .get("/api/blogLikeCnt/" + this.$route.query.postNo)
        .then((resp) => {
          console.log("좋아요 수", resp.data);
          this.likeCnt = resp.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //게시글 삭제
    deletePost(postContent) {
      console.log(postContent.thumbnail);
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "승인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.$axios
              .delete("/api/blogs/" + this.$route.query.postNo, {
                //사진 삭제 위해서 thumbnail은 따로 넘김
                params: { thumbnail: postContent.thumbnail },
              })
              .then((resp) => {
                console.log("삭제 포스트 번호", resp.data);
                this.$router.go(-1);
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
      console.log("삭제됨", item.book);
      this.$axios
        .delete("/api/deleteBookmark/" + item.book)
        .then((resp) => {
          console.log("북마크 삭제", resp.data);
          this.blogList = resp.data;
          //눈에 보이는 것을 지워줌
          this.postContent.book = "";
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //카카오톡 공유하기
    shareKakao(postContent) {
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
        let currentUrl = window.document.location.href;
        let text = postContent.content;
        let newText = text.replace(/<[^>]*>?/g, "");
        let content = newText.substr(0, 50);

        //사진왜안드가!!

        let kakaoImage = document.querySelector("#test_obj").src;
        // let kakaoImage = postContent.thumbnail;
        console.log(kakaoImage);

        window.Kakao.Link.sendDefault({
          objectType: "feed",
          content: {
            title: postContent.title,
            description: content,
            imageUrl: kakaoImage,
            link: {
              webUrl: currentUrl,
            },
          },
          buttons: [
            {
              title: "웹으로 이동",
              link: {
                webUrl: currentUrl,
              },
            },
          ],
        });
      }
    },
    changeDate: function (date) {
      return moment(date).format("YYYY.MM.DD  HH:mm");
    },
  },
};
</script>
<style scoped>
::v-deep .v-application--wrap {
  min-height: fit-content;
}
.total-box {
  margin: 0 auto;
}

.right-bar {
  position: fixed;
  top: 200px;
  right: 10%;
}

.input-group-text {
  background-color: #ffffff;
}

.postContents {
  padding: 20px 10px;
  margin-left: 70px;
  width: 70%;
  min-width: 1300px;
}
.title {
  text-align: left;
  margin-top: 20px;
  margin-bottom: 30px;
}
.member-date {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  width: 95%;
  min-width: 800px;
  margin-right: 0px;
}

.content {
  min-height: 400px;
  width: 95%;
  text-align: left;
}

.tag {
  text-align: left;
  width: 95%;
  color: #339ed3;
}
.bookmark {
  outline: 0;
}

.comment {
  width: 60%;
  min-width: 1250px;
  margin-left: 380px;
}

.ql-snow .ql-editor pre.ql-syntax {
  background: black;
  color: white;
}

.kakao {
  width: 22.5px;
  height: 23.5px;
}
</style>
