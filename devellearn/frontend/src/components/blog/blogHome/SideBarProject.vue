<template>
  <div id="panel">
    <sidebar-menu
      :menu="menu"
      :hideToggle="true"
      :show-one-child="true"
      @item-click="onItemClick"
    >
      <div slot="header" class="profile-box">
        <img
          class="profile"
          :src="'/api/images/teamstudy/' + this.projectProfile"
          alt="이미지"
        />
        <span class="profile-id">{{ this.$route.query.blogId }}</span>
        <hr />
      </div>
    </sidebar-menu>
  </div>
</template>

<script>
import { eventBus } from "../../../main.js";
export default {
  // props: ["projectProfile"],
  data() {
    return {
      menu: [
        {
          header: true,
          title: "프로젝트",
          hiddenOnCollapse: true,
        },
        {
          title: "전체",
          href: {
            path: "/projectHome",
            query: {blogId: this.$route.query.blogId, folderName: "전체"},
          },
        },
      ],
      postContent: [],
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "12",
      projectProfile: "",
      folderNameList: [],
    };
  },
  watch: {
    currentPage() {
      this.getFolderName();
      this.getProjectThumbnail();
    },
  },
  created() {
    this.getFolderName();
    this.getProjectThumbnail();

     //폴더 추가시 바로 반영되게 하기
    eventBus.$on("plusFolderName", (name) => {
      this.menu.push({
        title: name,
        href: {
          path: "/projectHome",
          query: {
            blogId: this.$route.query.blogId,
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
  methods: {
    //사이드바 클릭했을때 이벤트 발생
    onItemClick(e, i) {
      console.log(i.title);
      //전체
      if (i.title == "전체" || i.title == null) {
        this.$axios
          .post("/api/projectBlog/list", {
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
      } else {
        console.log("폴더 이름2" + i.title);
        this.$axios
          .post("/api/projectBlog/folderlist", {
            memberId: this.$store.state.userStore.id,
            blogId: this.$route.query.blogId,
            folderName: i.title,
            pageNum: this.currentPage,
          })
          .then((resp) => {
            console.log(resp);
            this.postContent = resp.data.list;
            this.row = resp.data.total;
            //console.log("sidebvar emit", this.postContent);
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
        .get("/api/blogs/@" + this.$route.query.blogId + "/folder")
        .then((resp) => {
          console.log(resp.data);
          for (let fname of resp.data) {
            this.menu.push({
              title: fname.folderName,
              href: {
                path: "/projectHome",
                query: {
                  blogId: this.$route.query.blogId,
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
    //프로젝트 썸네일 조회하기
    getProjectThumbnail() {
     
        this.$axios.get("/api/projectBlog/" + this.$route.query.blogId + "/profile").then((resp) => {
          console.log("프로젝트 썸네일 돌아감", resp);
          this.projectProfile = resp.data;
        }).catch(function (error) {
          console.log("error", error);
        })
      },
  },
};
</script>

<style scoped>
::v-deep .v-application--wrap {
  min-height: fit-content;
}
.v-sidebar-menu {
  width: 300px;
  padding-left: 30px;
  background-color: white;
   position: static !important; /* 추가- 사이드바가 영역 겹치는 것 방지 */
   margin-top:20px;
  z-index: 1;
}
.profile-box {
  float: left;
  padding-bottom: 10px;
}
.profile {
  width: 70px;
  height: 70px;
  overflow: hidden;
}

.profile-id {
  padding-left: 20px;
  font-size: 20px;
  font-weight: bold;
  overflow: hidden;
}

/* 팔로우 버튼 */
.follow-btn {
  display: inline-block;
  overflow: hidden;
}

.follow-content {
  padding: 20px 10px 0px 0px;
}
.follow-btn {
  display: flex;
  margin-left: 20px;
  overflow: hidden;
}
.follow-text {
  margin: auto 50px;
  font-size: x-large;
}
</style>
