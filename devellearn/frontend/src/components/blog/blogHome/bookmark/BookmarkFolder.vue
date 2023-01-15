<template>
  <!--북마크 폴더 관리 페이지-->
  <div class="container">
    <b-modal id="bookmark-plus" size="l" centered title="북마크 폴더"
      ><BookmarkModal @reload="getFolder"></BookmarkModal>
      <template #modal-footer="{ close }">
        <b-button @click="close()">확인</b-button>
      </template>
    </b-modal>

    <div class="box">
      <!-- plus 박스-->
      <div class="insert-box" v-b-modal.bookmark-plus>
        <div class="plus-icon">
          <b-icon
            icon="plus-square"
            font-scale="3"
            style="margin-top: 15px; margin-left: 20px"
          ></b-icon>
        </div>
      </div>
      <!-- plus 박스 끝-->

      <!-- 북마크 폴더 -->
      <template v-for="item in bookmarkFolder">
        <template>
          <div
            class="bookmark-box"
            :key="item.bookFolderName"
            @click="
              $router
                .push({
                  path: 'bookFolderContent',
                  query: {
                    bookFolderName: item.bookFolderName,
                  },
                })
                .catch(() => {})
            "
          >
            <div class="bookmark-text">
              <b-icon icon="bookmarks" font-scale="1.5"></b-icon>
              <h3>{{ item.bookFolderName }}</h3>
              <h4>{{ item.cnt }}</h4>
            </div>
          </div>
        </template>
      </template>
      <!-- 북마크 폴더 끝-->
    </div>
  </div>
</template>
<script>
import { eventBus } from "../../../../main.js";
import BookmarkModal from "@/components/blog/blogHome/bookmark/BookmarkModal.vue";
export default {
  data() {
    return {
      blogList: [],
      bookmarkFolder: [],
    };
  },
  components: {
    BookmarkModal,
  },
  created() {
    this.getFolder();
  },
  methods: {
    //블로그 북마크 폴더별 게시글 수 조회
    getFolder() {
      this.$axios
        .get(
          "/api/bookmark/@" + this.$store.state.userStore.id + "/bookmarkFolder"
        )
        .then((resp) => {
          console.log(resp);
          eventBus.$emit("bookmarkName", "");
          this.bookmarkFolder = resp.data;
        });
    },
    close() {
      this.getFolder();
    },
  },
};
</script>
<style scoped>
.container {
  margin-left: 0px;
  display: inline-block;

  min-width: 1100px;
  margin-top: 30px;
}
.box {
  display: flex;
  flex-wrap: wrap;
  min-width: 1100px;
}
.insert-box {
  width: 300px;
  height: 300px;
  border-radius: 10px;
  border: solid 1px black;
  position: relative;
  margin-right: 50px;
  cursor: pointer;
}
.plus-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -50px 0 0 -50px;
}

.bookmark-box {
  height: 300px;
  width: 300px;
  border-radius: 10px;
  border: solid 1px black;
  position: relative;
  margin-right: 50px;
  margin-bottom: 30px;
  cursor: pointer;
}

.bookmark-text {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -50px 0 0 -50px;
}
</style>
