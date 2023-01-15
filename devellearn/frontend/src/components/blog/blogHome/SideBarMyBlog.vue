<template>
  <div id="panel">
    <sidebar-menu
      :menu="menu"
      :hideToggle="true"
      :show-one-child="true"
      @item-click="onItemClick"
    >
      <div slot="header" class="profile-box">
        <div class="img-box">
          <img
            class="profile"
            :src="'/api/images/profile/' + userData.profile"
            alt="이미지"
          />
        </div>
        <div class="profile-id">
          <h3 style="margin-top: 20px">{{ userData.memberId }}</h3>
        </div>
      </div>
    </sidebar-menu>
  </div>
</template>

<script>
import { eventBus } from "../../../main.js";
export default {
  data() {
    return {
      menu: [
        {
          header: true,
          title: "홈",
          href: {
            path: "/myBlogHome",
            query: { blogId: this.$store.state.userStore.id },
          },
        },
        {
          title: "북마크",
          href: {
            path: "/myBlogHome/blogBookmark/bookmarkList",
            query: { blogId: this.$store.state.userStore.id },
          },
        },
        {
          title: "프로젝트",
          href: {
            path: "/myBlogHome/project",
            query: { blogId: this.$store.state.userStore.id },
          },
        },
        {
          title: "팔로우 블로그",
          href: {
            path: "/myBlogHome/follow",
            query: { blogId: this.$store.state.userStore.id },
          },
        },
        {
          title: "좋아요 게시글",
          href: {
            path: "/myBlogHome/like/",
            query: { blogId: this.$store.state.userStore.id },
          },
        },
        {
          header: true,
          title: "블로그",
          hiddenOnCollapse: true,
        },
        {
          title: "전체",
          href: {
            path: "/postHome",
            query: {
              blogId: this.$store.state.userStore.id,
              folderName: "전체",
            },
          },
        },
      ],
      userData: {},
      postContent: [],
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "16",
      folderNameList: [],
    };
  },
  components: {},
  created() {
    this.getFolderName();
    this.userData = this.$store.getters["userStore/getUserData"];

    //폴더 추가시 바로 반영되게 하기
    eventBus.$on("plusFolderName", (name) => {
      this.menu.push({
        title: name,
        href: {
          path: "/postHome",
          query: {
            blogId: this.$store.state.userStore.id,
            folderName: name,
          },
        },
      });
    });

    //폴더 삭제시 바로 반영되게 하기
    eventBus.$on("minusFolderName", (name) => {
      console.log(name);
      for (let i = 0; i < this.menu.length; i++) {
        if (name == this.menu[i].title) {
          this.menu.splice(i, 1);
        }
      }
    });

    //카테고리 수정시 바로 반영되게 하기
    eventBus.$on("updateFolderName", (sum) => {
      console.log(sum);
      //합친 카테고리 , 기준으로 나눔
      let sumName = sum.split(",");
      //기존 카테고리
      let oldName = sumName[0];
      //수정 카테고리
      let newName = sumName[1];

      for (let i = 0; i < this.menu.length; i++) {
        if (oldName == this.menu[i].title) {
          this.menu[i].title = newName;
          this.menu[i].href.query.folderName = newName;
        }
      }
    });
  },
  watch: {
    currentPage() {
      this.getFolderName();
    },
  },
  methods: {
    //사이드바 클릭했을때 이벤트 발생
    onItemClick(e, i) {
      console.log(i.title);
      //전체
      if (i.title == "전체") {
        console.log("블로그 아이디" + this.$route.query.blogId);
        this.$axios
          .post("/api/blogs/postHome", {
            memberId: this.$store.state.userStore.id,
            blogId: this.$route.query.blogId,
            pageNum: this.currentPage,
          })
          .then((resp) => {
            console.log(resp);
            this.postContent = resp.data.list;
            this.row = resp.data.total;
            this.$emit("postContent", this.postContent);
          })
          .catch(function (error) {
            console.log("error", error);
          });
        //폴더별
      } else {
        console.log("아이디" + this.$store.state.userStore.id);
        console.log("블로그 아이디" + this.$route.query.blogId);
        console.log("폴더 이름" + i.title);
        this.$axios
          .post("/api/blogs/postHome/folder", {
            memberId: this.$store.state.userStore.id,
            blogId: this.$route.query.blogId,
            folderName: i.title,
            pageNum: this.currentPage,
          })
          .then((resp) => {
            console.log(resp);
            this.postContent = resp.data.list;
            this.row = resp.data.total;
            console.log("sidebvar emit", this.postContent);
            this.$emit("postContent", this.postContent);
          })
          .catch(function (error) {
            console.log("error", error);
          });
      }
    },
    //사이드바에 폴더 리스트 삽입
    getFolderName() {
      this.$axios
        .get("/api/blogs/@" + this.$store.state.userStore.id + "/folder")
        .then((resp) => {
          console.log(resp.data);
          this.folderNameList = resp.data;
          for (let fname of this.folderNameList) {
            this.menu.push({
              title: fname.folderName,
              href: {
                path: "/postHome",
                query: {
                  blogId: this.$store.state.userStore.id,
                  folderName: fname.folderName,
                },
              },
            });
          }
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
  },
};
</script>

<style scoped>
.v-sidebar-menu {
  width: 300px;
  padding-left: 30px;
  margin-top: 20px;
  background-color: white;
  position: static !important; /* 추가- 사이드바가 영역 겹치는 것 방지 */
  z-index: 1;
}
.profile-box {
  display: flex;
  padding-bottom: 15px;
  border-bottom: 1px solid lightgray;
  margin-bottom: 20px;
}

.img-box {
  width: 70px;
  height: 70px;
  border-radius: 70%;
}
.profile {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.profile-id {
  padding-left: 20px;
  font-size: 20px;
  font-weight: bold;
  overflow: hidden;
}
</style>
