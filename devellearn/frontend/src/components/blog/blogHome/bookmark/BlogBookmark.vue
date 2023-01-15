<template>
  <div>
    <!-- 북마크 메인페이지 -->
    <div class="bookmark-top">
      <b-icon icon="bookmark-fill" font-scale="1.5"></b-icon>
      <h5 style="margin-left: 10px">북마크</h5>
      <h6 style="margin-left: 15px">전체</h6>
      <h6 style="margin-left: 15px; color: green">{{ bookCntList }}</h6>
    </div>
    <div class="sort-box">
      <span
        style="cursor: pointer"
        @click="
          $router
            .push({ path: '/myBlogHome/blogBookmark/bookmarkList' })
            .catch(() => {})
        "
        ><b-icon icon="dot" font-scale="1.5"></b-icon>전체</span
      >
      <span
        style="margin-left: 15px; cursor: pointer"
        @click="
          $router
            .push({ path: '/myBlogHome/blogBookmark/bookmarkFolder' })
            .catch(() => {})
        "
        ><b-icon icon="dot" font-scale="1.5"></b-icon>폴더별</span
      >
      <p style="margin-top: 4px; margin-left: 25px; color: #2d6bc2">
        {{ this.bookFolderName }}
      </p>
    </div>

    <div>
      <!-- 북마크 네모박스 안-->
      <router-view> </router-view>
    </div>
  </div>
</template>

<script>
import { eventBus } from "../../../../main.js";
export default {
  data() {
    return {
      bookCntList: "",
      bookFolderName: "",
    };
  },
  created() {
    this.bookCnt();

    eventBus.$on("bookmarkName", (name) => {
      this.bookFolderName = name;
    });
  },
  methods: {
    bookCnt() {
      this.$axios
        .get("/api/bookmark/@" + this.$store.state.userStore.id + "/bookCnt")
        .then((resp) => {
          console.log(resp);
          this.bookCntList = resp.data;
        });
    },
  },
};
</script>
<style scoped>
.bookmark-top {
  display: flex;
  justify-content: flex-start;
  margin-left: 100px;
}

.sort-box {
  display: flex;
  margin-top: 15px;
  justify-content: flex-start;
  margin-left: 100px;
}
.content {
  width: 60%;
  margin-left: 0px;
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
  top: 13px;
  right: 12px;
}

.secret {
  transition: 0.3s;
  position: absolute;
  color: #000000;
  top: 13px;
  left: 12px;
  outline: 0;
}
.card:hover .card-header {
  opacity: 0.5;
  height: 210px;
}

h2 {
  font-size: 18px;
  font-weight: bold;
  color: #000000;
  overflow: hidden;
  height: 20px;
  padding-top: 3px;
}

.card-body {
  padding: 0px 0px;
}

.card-body-header {
  line-height: 25px;
  margin: 10px 20px 0px 20px;
  text-align: left;
}

.card-body-description {
  opacity: 0;
  color: #3e4144;
  line-height: 25px;
  text-align: left;
  -webkit-transition: 0.2s ease-in-out; /*사파리&크롬*/
  -moz-transition: 0.2s ease-in-out; /*파이어폭스*/
  -ms-transition: 0.2s ease-in-out; /*익스플로러*/
  -o-transition: 0.2s ease-in-out; /*오페라*/
  transition: 0.2s ease-in-out;
  overflow: hidden;
  height: 20px;
  margin: 5px 20px;
}

.card:hover .card-body-description {
  opacity: 1;
  -webkit-transition: 0.5s ease-in-out;
  -moz-transition: 0.5s ease-in-out;
  -ms-transition: 0.5s ease-in-out;
  -o-transition: 0.5s ease-in-out;
  transition: 0.5s ease-in-out;
}

.card-body-hashtag {
  color: #339ed3;
  font-style: italic;
  margin-bottom: 0px;
  overflow: hidden;
  height: 25px;
  padding-top: 10px;
}

.card-body-info {
  color: #000000;
  display: flex;
  padding: 5px 0px;
  overflow: hidden;
  height: 35px;
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
</style>
