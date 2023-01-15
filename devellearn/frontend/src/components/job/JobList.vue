<template>
  <div>
    <template v-if="bookmarkList.length > 0">
      <v-container fluid style="max-width: 1300px">
        <div style="text-align: left">
          <h4>북마크한 공고</h4>
        </div>
        <v-row>
          <template v-for="item in bookmarkList">
            <template>
              <v-col cols="3" :key="item.recruitNo">
                <v-card
                  class="mx-auto"
                  max-width="300"
                  height="400px"
                  @click="
                    $router
                      .push({
                        path: '/jobDetail/recruitDetail',
                        query: { recruitNo: item.recruitNo, comNo: item.comNo },
                      })
                      .catch(() => {})
                  "
                >
                  <b-icon
                    icon="bookmark-fill"
                    class="bookmark h3 fill"
                    @click.stop="bookmarkDelete(item)"
                  ></b-icon>
                  <v-img
                    :src="'/api/images/company/' + item.img"
                    height="180px"
                  ></v-img>

                  <v-card-subtitle class="company left">
                    {{ item.name }}
                  </v-card-subtitle>
                  <v-card-subtitle class="company title">
                    {{ item.title }}
                  </v-card-subtitle>
                  <v-card-subtitle class="company">
                    {{ item.skill }}
                  </v-card-subtitle>
                  <v-card-subtitle class="company">
                    {{ item.career }}
                  </v-card-subtitle>
                  <v-card-subtitle class="company" v-if="item.endDate != null">
                    {{ item.endDate | yyyyMMdd }}까지
                  </v-card-subtitle>
                  <v-card-subtitle class="company" v-if="item.endDate == null">
                    마감기한없음
                  </v-card-subtitle>
                  <v-card-subtitle class="company right">
                    <img src="@/assets/star.png" alt="star" class="star" />
                    {{ item.avgRate }}
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </template>
          </template>
        </v-row>
        <b-pagination
          v-model="bookmarkPage"
          :total-rows="bookmarkListRows"
          :per-page="bookPerPage"
          align="right"
        ></b-pagination>
      </v-container>
    </template>
    <br /><br />
    <v-container fluid style="max-width: 1300px">
      <div style="display: flex; justify-content: space-between">
        <h4>채용공고</h4>
        <div>
          <span class="order" @click="clearAll()">전체조회</span>
          <span class="order" @click="order = 'recruit_no'">최신순</span>
          <span class="order" @click="order = 'avg_rate'">평점순</span>
          <span class="order" @click="order = 'end_date'">마감임박순</span>
        </div>
      </div>

      <v-row>
        <template v-for="item in recruitList">
          <template>
            <v-col cols="3" :key="item.recruitNo">
              <v-card
                class="mx-auto"
                height="400px"
                @click="
                  $router
                    .push({
                      path: '/jobDetail/recruitDetail',
                      query: { recruitNo: item.recruitNo, comNo: item.comNo },
                    })
                    .catch(() => {})
                "
              >
                <b-icon
                  v-if="
                    $store.getters['userStore/getEmpUser'] == 'false' &&
                    $store.getters['userStore/getId'] != null &&
                    $store.getters['userStore/getId'] != ''
                  "
                  :icon="item.bookmarkId == null ? 'bookmark' : 'bookmark-fill'"
                  class="bookmark h3 fill"
                  @click.stop="bookmark(item)"
                ></b-icon>
                <v-img
                  :src="'/api/images/company/' + item.img"
                  height="180px"
                ></v-img>

                <v-card-subtitle class="company left">
                  {{ item.name }}
                </v-card-subtitle>
                <v-card-subtitle class="company title">
                  {{ item.title }}
                </v-card-subtitle>
                <v-card-subtitle class="company">
                  {{ item.skill }}
                </v-card-subtitle>
                <v-card-subtitle class="company">
                  {{ item.career }}
                </v-card-subtitle>
                <v-card-subtitle class="company" v-if="item.endDate != null">
                  {{ item.endDate | yyyyMMdd }}까지
                </v-card-subtitle>
                <v-card-subtitle class="company" v-if="item.endDate == null">
                  마감기한없음
                </v-card-subtitle>
                <v-card-subtitle class="company right">
                  <img src="@/assets/star.png" alt="star" class="star" />
                  {{ item.avgRate }}
                </v-card-subtitle>
              </v-card>
            </v-col>
          </template>
        </template>
      </v-row>
      <b-pagination
        v-if="recruitList.length > 0"
        v-model="currentPage"
        :total-rows="recruitListRows"
        :per-page="listPage"
        align="right"
      ></b-pagination>
    </v-container>
  </div>
</template>
<script>
export default {
  props: {
    searchCondition: {
      type: String,
    },
    filterOn: { type: Boolean },
    skillList: [],
    careerList: [],
    jobTypeList: [],
    empTypeList: [],
    avgRatefilter: {
      type: Number,
    },
  },
  data: () => ({
    recruitList: [],
    bookmarkList: [],
    order: "recruit_no",
    listPage: 16,
    currentPage: 1,
    recruitListRows: 1,
    bookmarkPage: 1,
    bookPerPage: 4,
    bookmarkListRows: 1,
  }),

  watch: {
    filterOn() {
      this.getList();
      this.currentPage = 1;
    },
    currentPage() {
      this.getList();
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
    },
    order() {
      this.getList();
      this.currentPage = 1;
    },
    bookmarkPage() {
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
      this.getBookmarkList();
    },
    searchCondition() {
      this.getList();
      this.currentPage = 1;
    },
  },
  created() {
    this.getList();
    this.getBookmarkList();
  },
  methods: {
    getList() {
      this.$axios
        .post("/api/recruit/search", {
          condition: this.searchCondition,
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
          pageSize: this.listPage,
          order: this.order,
          skillList: this.skillList,
          careerList: this.careerList,
          jobTypeList: this.jobTypeList,
          empTypeList: this.empTypeList,
          avgRate: this.avgRatefilter,
        })
        .then((resp) => {
          console.log(resp);
          this.recruitList = resp.data.list;
          this.recruitListRows = resp.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    clearAll() {
      this.currentPage = 1;
      this.order = "recruit_no";
      this.$emit("clearAll");
    },
    bookmark(item) {
      //로그인이 안되어 있으면 작동이 안됨
      if (this.$store.state.userStore.id == "") {
        return;
      }
      if (item.bookmarkId == null) {
        //프론트 북마크 색입히기
        item.bookmarkId = this.$store.state.userStore.id;
        // this.bookmarkList.push(item);
        //  insert
        this.$axios
          .post("/api/likeRecruit", {
            recruitNo: item.recruitNo,
            memberId: this.$store.state.userStore.id,
          })
          .then((resp) => {
            console.log(resp);
            this.getBookmarkList();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        //북마크 벗기기
        item.bookmarkId = null;
        this.$axios
          .delete("/api/deleteRecruitLike", {
            data: {
              recruitNo: item.recruitNo,
              memberId: this.$store.state.userStore.id,
            },
          })
          .then((resp) => {
            console.log(resp);
            this.getBookmarkList();
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    bookmarkDelete(item) {
      if (this.$store.state.userStore.id == "") {
        return;
      }
      // this.bookmarkList = this.bookmarkList.filter(function (data) {
      //   return data.recruitNo != item.recruitNo;
      // });
      this.recruitList.find((el) => {
        if (el.recruitNo == item.recruitNo) {
          el.bookmarkId = null;
          return true;
        }
      });

      this.$axios
        .delete("/api/deleteRecruitLike", {
          data: {
            recruitNo: item.recruitNo,
            memberId: this.$store.state.userStore.id,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.getBookmarkList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getBookmarkList() {
      this.$axios
        .post("/api/myRecruit", {
          memberId: this.$store.state.userStore.id,
          pageNum: this.bookmarkPage,
          pageSize: 4,
        })
        .then((resp) => {
          console.log(resp);
          this.bookmarkList = resp.data.list;
          this.bookmarkListRows = resp.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  filters: {
    // filter로 쓸 filter ID 지정
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";
      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);
      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();
      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      // 최종 포맷 (ex - '2021/10/08')
      return year + "/" + month + "/" + day;
    },
  },
};
</script>
<style scoped>
.order {
  margin-right: 15px;
  cursor: pointer;
}
.pagination {
  margin: 10px;
}
.bookmark {
  position: absolute;
  z-index: 10;
  right: 5px;
  top: 5px;
  width: 28px;
}

.company {
  padding: 0px;
  margin: 3px;
  text-align: left;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.v-image {
  border-top-left-radius: 3%;
  border-top-right-radius: 3%;
}

.title {
  font-size: 1.25rem;
  color: black !important;
  font-weight: 500;
  margin: 1rem 0;
  line-height: 125%;

  white-space: pre-line;
}

.star {
  width: 15px;
  height: 15px;
  margin: 2px;
}
.right {
  text-align: right;
  position: absolute;
  bottom: 5px;
  right: 5px;
}
</style>
