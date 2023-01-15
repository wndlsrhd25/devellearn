<template>
  <div style="min-height:1000px">
    <!-- 북마크 -->
    <BookmarkCard />

    <!-- 프로젝트 -->
    <ProjectCard />

    <div class="bottom">
      <!-- 팔로우 -->
      <FollowCard />
      <!-- 좋아요 -->
      <LikeCard />
    </div>
  </div>
</template>
<script>
import BookmarkCard from "@/components/blog/blogHome/bookmark/BookmarkCard.vue";
import ProjectCard from "@/components/blog/blogHome/project/ProjectCard.vue";
import FollowCard from "@/components/blog/blogHome/follow/FollowCard.vue";
import LikeCard from "@/components/blog/blogHome/like/LikeCard.vue";

export default {
  data() {
    return {};
  },
  components: {
    BookmarkCard,
    ProjectCard,
    FollowCard,
    LikeCard,
  },
  methods: {},
};
</script>
<style scoped>
.bottom {
  display: flex;
  width: 60%;
  justify-content: flex-start;
  margin-left: 100px;
  min-width: 1100px;
}
</style>
