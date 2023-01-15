<template>
  <div id="panel">
    <sidebar-menu
      :menu="menu"
      :hideToggle="true"
      :show-one-child="true"
      @item-click="onItemClick"
    >
      <div slot="header" >
        <div class="profile-box">
        <div class="img-box">
        <img
          class="profile"
          :src="'/api/images/profile/' + this.followList.profile"
          alt="이미지"
        />
        </div>

        <span class="profile-id">{{ this.$route.query.blogId }}</span>
        </div>
        <!-- 팔로우 버튼 -->
        <!-- 팔로우 리스트가 없는경우 -신청 -->
        <div class="follow-content" v-if="this.followList.followNo == ''">
          <b-button pill variant="outline-success" @click="followInsert()">
            <div class="follow-btn">
              <b-icon
                icon="bell"
                aria-hidden="true"
                dialog
                style="margin-top: 3px"
                font-scale="2.5"
              ></b-icon>
              <span class="follow-text">팔로우</span>
            </div>
          </b-button>
        </div>
        <!-- 팔로우 리스트가 있는경우 -취소 -->
        <div class="follow-content" v-else>
          <b-button pill variant="success" @click="followDelete()">
            <div class="follow-btn">
              <b-icon
                icon="check2"
                aria-hidden="true"
                style="margin-top: 3px"
                font-scale="2.5"
              ></b-icon>

              <span class="follow-text">팔로잉</span>
            </div>
          </b-button>
        </div>

        <hr />
      </div>
    </sidebar-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [
        {
          header: true,
          title: "블로그",
          hiddenOnCollapse: true,
        },
        {
          title: "전체",
          href: {
            path: "/postHome",
            query: { blogId: this.$route.query.blogId, folderName: "전체" },
          },
        },
      ],
      blogId: "",
      followList: {},
      dialog: false,
      // postContent: [],
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "12",
    };
  },
  watch: {
    currentPage() {
      this.getFolderName();
      this.follow();

    },
  },
  created() {
    this.follow();
    this.getFolderName();
   
  },
  methods: {
    follow() {
      console.log("팔로우 리스트");
      this.$axios
        .post("/api/follow", {
          memberId: this.$store.state.userStore.id,
          blogId: this.$route.query.blogId,
        })
        .then((resp) => {
          console.log("팔로우 리스트", resp);
          this.followList = resp.data;
          console.log("뭐지" + this.followList.profile);
        });
    },
    //팔로우 신청
    followInsert() {
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
        console.log("블로그 아이디" + this.$route.query.blogId);
        this.$axios
          .post("/api/insertFollow", {
            memberId: this.$store.state.userStore.id,
            blogId: this.$route.query.blogId,
          })
          .then((resp) => {
            console.log(resp);
            Swal.fire({
              position: "center",
              icon: "success",
              title: "팔로우 완료",
              showConfirmButton: false,
              timer: 1000,
            });
            this.followList = resp.data;
            this.follow();
          });
      }
    },
    //팔로우 삭제
    followDelete() {
      this.$swal
        .fire({
          title: "",
          text: "정말 팔로우를 취소하시겠습니까?",
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
            this.$axios
              .delete("/api/deleteFollow/" + this.followList.followNo)
              .then((resp) => {
                console.log(resp);
                this.followList = resp.data;
                this.follow();
              });
            this.$swal.fire({
              title: "팔로우 취소 완료",
              icon: "success",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        });
    },
    login() {
      this.dialog = false;
      this.$router.push({ path: "/login" }).catch(() => {});
    },

    //사이드바 클릭했을때 이벤트 발생
    onItemClick(e, i) {
      console.log(i.title);
      //전체
      if (i.title == "전체") {
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
        console.log("폴더 이름2" + i.title);
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
        .get("/api/blogs/@" + this.$route.query.blogId + "/folder")
        .then((resp) => {
          console.log(resp.data);
          for (let fname of resp.data) {
            //조회된 폴더명을 사이드바에 삽입
            this.menu.push({
              title: fname.folderName,
              href: {
                path: "/postHome",
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
  },
};
</script>

<style scoped>
.btn-outline-success {
  color: #28a745 !important;
  border-color: #28a745 !important;
}
.btn-outline-success :hover {
  color: white !important;
}
::v-deep .v-application--wrap {
  min-height: fit-content;
}
.v-sidebar-menu {
  width: 300px;
  margin-top: 20px;
  padding-left: 30px;
  background-color: white !important;
  position: static !important; /* 추가- 사이드바가 영역 겹치는 것 방지 */
  z-index: 1;
}
.profile-box {
  display: flex;
  padding-bottom: 10px;

}
.img-box{
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
  margin-top:20px;
}

/* 팔로우 버튼 */
.follow-content {
  padding: 10px 10px 0px 0px;
}
.follow-btn {
  display: flex;
  margin-left: 20px;
  overflow: hidden;
  height: 50px;
}
.follow-text {
  margin: auto 50px;
  font-size: x-large;
}

.theme--light.v-btn {
  color: green !important;
}
</style>
