<template>
  <div class="content">
    <!-- 북마크 전체 조회 -->
    <template v-for="item in blogList">
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
            <b-icon
              v-if="item.readStatus == '비공개'"
              class="secret"
              icon="lock-fill"
              font-scale="1.5"
            ></b-icon>
            <b-icon
              v-if="item.book == ''"
              class="bookmark"
              @click.stop="bookClick = item.postNo"
              icon="bookmark"
              font-scale="1.5"
              v-b-modal.bookmark-plus
            ></b-icon>
            <b-icon
              v-if="item.book != ''"
              class="bookmark"
              @click.stop="deleteBook(item)"
              icon="bookmark-fill"
              font-scale="1.5"
            ></b-icon>

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
                <p class="card-body-comment-count">{{ item.commentCnt }}</p>
              </div>
            </div>
            <p class="card-body-description" v-html="item.noneHtmlContent"></p>
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
      class="paging"
      v-model="currentPage"
      :total-rows="row"
      :per-page="listPage"
      align="center"
    ></b-pagination>
    <!-- 사진카드 하나 끝-->
  </div>
</template>
<script>
import { eventBus } from "../../../../main.js";
export default {
  data() {
    return {
      blogList: [],
      //현재 페이지
      currentPage: "1",
      //전체 게시물 수
      row: "1",
      //페이지당 내가 볼 개수
      listPage: "9",
    };
  },
  //페이지 이동할때 변화를 감지해서 다시 리스트를 돌려야함
  watch: {
    currentPage() {
      this.getList();
    },
  },
  components: {},
  created() {
    this.getList();
  },
  methods: {
    //북마크 리스트 전체 조회
    getList() {
      this.$axios
        .post("/api/bookmark", {
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
        })
        .then((resp) => {
          console.log(resp);
          this.blogList = resp.data.list;
          //16개가 리스트에 담겨잇음
          this.row = resp.data.total;
          console.log(this.blogList);
          eventBus.$emit("plusFolderName", "");
        });
    },

    //북마크 삭제
    deleteBook(item) {
      console.log("삭제됨");

      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
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

                this.getList();
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
      return moment(date).format("YYYY.MM.DD HH:mm");
    },
  },
};
</script>
<style scoped>
.content {
  width: 80%;
  min-height: 1000px;
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
  height: 25px;
  padding-top: 10px;
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

/* 페이징 */
.paging {
  bottom: 100px;
  position: absolute;
  width: 100%;
}
</style>
