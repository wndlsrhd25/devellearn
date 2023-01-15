<template>
  <div class="list-4cards">
    <b-container fluid="lg">
      <b-row>
        <template v-for="item in items">
          <b-col cols="3" v-if="item.lectureNo != null" :key="item.lectureNo">
            <!-- vue router 데이터 전달 방식 (query 프로그래밍 방식) -->
            <b-card
              :title="item.title"
              :img-src="'/api/images/lecture/' + item.imgSrc"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="lecture_card"
              @click="
                $router
                  .push({
                    path: '/lectureDetail/intro',
                    query: { no: item.lectureNo },
                  })
                  .catch(() => {})
              "
            >
              <b-card-text>{{ item.name }}</b-card-text>
              <b-card-text>
                <b-icon-star-fill
                  v-for="i in Math.round(item.avgScore)"
                  :key="i * new Date()"
                  variant="warning"
                ></b-icon-star-fill>
                <b-icon-star
                  v-for="j in 5 - Math.round(item.avgScore)"
                  :key="j * new Date()"
                  variant="warning"
                ></b-icon-star>
                ({{ item.avgScore }})
              </b-card-text>
              <b-card-text>{{ item.learnerNum }} 명 절찬 수강중!</b-card-text>
              <b-card-text class="skill_tag">{{ item.skillTag }}</b-card-text>
              <b-card-text
                >₩ {{ item.price.toLocaleString("ko-KR") }}
              </b-card-text>
            </b-card>
          </b-col>
        </template>
      </b-row>
      <v-pagination
        v-model="page"
        :length="total"
        prev-icon="mdi-menu-left"
        next-icon="mdi-menu-right"
      ></v-pagination>
    </b-container>
  </div>
</template>

<script>
export default {
  props: ["listName", "searchItems", "searchOn"],
  data: function () {
    return {
      // 강의 리스트 담을 배열
      items: [],
      page: 1,
      total: 1,
    };
  },
  methods: {
    // moveDetail: function (key) {
    //   this.
    //   console.log(key);
    // },
    searchList(data) {
      this.$axios
        .post("/api/lecture/search", data)
        .then((resp) => {
          console.log(resp.data);
          this.items = resp.data.list;
          this.total = resp.data.pages;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getListAll() {
      this.$axios
        .get("/api/lecture", {
          params: { pageNum: this.page },
        })
        .then((resp) => {
          console.log(resp.data);
          this.items = resp.data.list;
          this.total = resp.data.pages;
          console.log(this.items);
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
  },
  watch: {
    page() {
      if (Object.keys(this.searchItems).length === 0) {
        this.getListAll();
      } else {
        this.searchItems.pageNum = this.page;
        this.searchList(this.searchItems);
      }
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
    },
    searchOn() {
      this.searchItems.pageNum = this.page;
      console.log("검색조건확인하기 : ", this.searchItems);
      console.log("검색조건확인하기 : ", this.searchItems);
      console.log("검색조건확인하기 : ", this.searchItems);

      this.searchList(this.searchItems);
    },
  },
  // 강의리스트 전체 조회 로직 필요
  created: function () {
    this.getListAll();
  },
};
</script>

<style scoped>
/* 카드 이미지 높이 똑같게  */
.card-img-top {
  height: 10rem;
  object-fit: cover;
}
.lecture_card {
  cursor: pointer;
  min-width: 250px;
}

.lecture_card p {
  margin-bottom: 2px;
  text-align: left;
}

.card-title {
  font-size: 22px;
  text-align: left;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 카드 템플릿 연습 */

.card {
  margin: 40px;
  position: relative;
  max-width: 250px;
  max-height: 350px;
  box-shadow: 0 10px 20px -2px gray;
}

.card-title {
  display: block;
  text-align: center;
  color: #fff;
  background-color: #6184a8;
  padding: 2%;
  border-top-right-radius: 4px;
  border-top-left-radius: 4px;
}

.skill_tag {
  overflow: hidden;
  /* 말줄임 ... 으로 표시 */
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
