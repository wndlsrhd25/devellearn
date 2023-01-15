<template>
  <div style="min-height: 100vh">
    <!-- 사이드바 -->
    
    <!-- 탭 리스트 항목-->
  
    <div style="display: flex;">
      <SideBarMyBlog @postContent="postFolderContent" />
      <div style="width:1600px">
    <div class="topList">
      <h5
        @click="
          $router
            .push({
              path: '/myBlogHome',
            })
            .catch(() => {})
        "
        style="cursor: pointer"
      >
        홈
      </h5>
      <h5
        @click="
          $router
            .push({ path: '/myBlogHome/blogBookmark/bookmarkList' })
            .catch(() => {})
        "
        style="min-width: 100px; cursor: pointer"
      >
        북마크
      </h5>
      <h5
        @click="$router.push({ path: '/myBlogHome/project' }).catch(() => {})"
        style="min-width: 100px; cursor: pointer"
      >
        프로젝트
      </h5>
      <h5
        @click="$router.push({ path: '/myBlogHome/follow' }).catch(() => {})"
        style="min-width: 160px; cursor: pointer"
      >
        팔로우 블로그
      </h5>
      <h5
        @click="$router.push({ path: '/myBlogHome/like' }).catch(() => {})"
        style="min-width: 160px; cursor: pointer"
      >
        좋아요 게시글
      </h5>

      <!--글쓰기 버튼-->
      <b-button class="write-btn" @click="moveWrite" style="margin-left: auto"
        >글쓰기</b-button
      >
    
    </div>

    <!-- 라우터 만들기 -->

      <!-- 블로그 안에 내용물-->
    
    <router-view></router-view>

</div>
    </div>
  </div>
</template>
<script>
import SideBarMyBlog from "@/components/blog/blogHome/SideBarMyBlog.vue";
import MyBlogContent from "@/components/blog/blogHome/MyBlogContent.vue";

export default {
  data() {
    return {
      postContent: [],
    };
  },
  components: {
    SideBarMyBlog,

    MyBlogContent,
  },

  methods: {
    //사이드바 클릭시 데이터 가져오기
    postFolderContent(info) {
      console.log(info);
      this.postContent = info;
    },
    moveWrite() {
      this.$router.push("/postWrite").catch(() => {});
    },
  },
};
</script>

<style scoped>
.topList {
  display: flex;
  margin: 50px 0px 30px 350px;
  overflow: hidden;
  min-width: 1100px;
  height: 40px;
  margin-left:100px;
  justify-content:space-between;
}
h5 {
  margin-left: 10px;
  overflow: hidden;
}
.write-btn {
  margin-right: 350px;
  overflow: hidden;
  min-width: 80px;
}

</style>
