<template>
  <div>
    <template v-if="likeList.length > 0">
      <v-container fluid style="max-width: 1300px">
        <div style="text-align: left">
          <h4>관심 기업</h4>
        </div>
        <v-row>
          <template v-for="item in likeList">
            <template>
              <v-col cols="3" :key="item.comNo">
                <v-card
                  class="mx-auto"
                  max-width="300"
                  height="350px"
                  @click="
                    $router
                      .push({
                        path: '/CompanyDetail/detailInfo',
                        query: { comNo: item.comNo },
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
                    style="border-radius: 3%"
                  ></v-img>

                  <v-card-subtitle class="company title">
                    {{ item.name }}
                  </v-card-subtitle>
                  <v-card-subtitle class="company">
                    {{ item.jobType }}
                  </v-card-subtitle>
                  <v-card-subtitle class="company">
                    {{ item.skill }}
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
          v-model="likePage"
          :total-rows="likeRows"
          :per-page="likePerPage"
          align="right"
        ></b-pagination>
      </v-container>
    </template>
    <br /><br />
    <v-container fluid style="max-width: 1300px">
      <div style="display: flex; justify-content: space-between">
        <h4>기업리스트</h4>
        <div>
          <span class="order" @click="order = 'avg_rate'">평점순</span>
          <span class="order" @click="order = 'emp_size'">규모순</span>
        </div>
      </div>
      <v-row>
        <template v-for="item in companyList">
          <template>
            <v-col cols="3" :key="item.comNo">
              <v-card
                class="mx-auto"
                max-width="300"
                height="350px"
                @click="
                  $router
                    .push({
                      path: '/CompanyDetail/detailInfo',
                      query: { comNo: item.comNo },
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
                  style="border-radius: 3%"
                ></v-img>

                <v-card-subtitle class="company title">
                  {{ item.name }}
                </v-card-subtitle>
                <v-card-subtitle class="company">
                  {{ item.jobType }}
                </v-card-subtitle>
                <v-card-subtitle class="company">
                  {{ item.skill }}
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
        v-model="comPage"
        :total-rows="comRows"
        :per-page="comPerPage"
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
    avgRatefilter: {
      type: Number,
    },
    jobTypeList: [],
    comSizeList: [],
    skillList: [],
  },
  data() {
    return {
      likePage: 1,
      likeList: [],
      likePerPage: 4,
      likeRows: 1,
      companyList: [],
      comPage: 1,
      comPerPage: 16,
      comRows: 1,
      order: "avg_rate",
      isSearch: false,
    };
  },
  watch: {
    filterOn() {
      this.search();
      this.isSearch = true;
      console.log("1231233");
    },
    comPage() {
      this.getList();
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
    },
    order() {
      if (!this.isSearch) {
        this.getList();
        return;
      }
      this.search();
    },
    likePage() {
      this.getLikeList();
      window.scrollTo({ top: 0, left: 0, behavior: "auto" });
    },
    searchCondition() {
      this.search();
      this.isSearch = true;
    },
  },

  created() {
    this.getList();
    this.getLikeList();
  },
  methods: {
    search() {
      this.$axios
        .post("/api/company/search", {
          condition: this.searchCondition,
          memberId: this.$store.state.userStore.id,
          pageNum: this.comPage,
          pageSize: this.comPerPage,
          order: this.order,
          skillList: this.skillList,
          jobTypeList: this.jobTypeList,
          comSizeList: this.comSizeList,
          avgRate: this.avgRatefilter,
        })
        .then((resp) => {
          console.log(resp);
          this.companyList = resp.data.list;
          this.comRows = resp.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getLikeList() {
      if (this.$store.state.userStore.id == "") {
        return;
      }
      this.$axios
        .post("/api/myCompanyList", {
          memberId: this.$store.state.userStore.id,
          pageNum: this.likePage,
          pageSize: 4,
        })
        .then((resp) => {
          console.log(resp);
          this.likeList = resp.data.list;
          this.likeRows = resp.data.total;
          if (this.likeList.length == 0 && this.likeRows != 0) {
            this.likePage--;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getList() {
      this.$axios
        .post("/api/companyList", {
          memberId: this.$store.state.userStore.id,
          order: this.order,
          pageNum: this.comPage,
          pageSize: this.comPerPage,
        })
        .then((resp) => {
          console.log(resp);
          this.companyList = resp.data.list;
          this.comRows = resp.data.total;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    bookmark(item) {
      if (this.$store.state.userStore.id == "") {
        return;
      }
      if (item.bookmarkId == null) {
        item.bookmarkId = this.$store.state.userStore.id;
        this.$axios
          .post("/api/likeCompany", {
            comNo: item.comNo,
            memberId: this.$store.state.userStore.id,
          })
          .then((resp) => {
            console.log(resp);
            this.getLikeList();
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        item.bookmarkId = null;
        this.$axios
          .delete("/api/myCompanyList", {
            data: {
              comNo: item.comNo,
              memberId: this.$store.state.userStore.id,
            },
          })
          .then((resp) => {
            console.log(resp);
            this.getLikeList();
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
      this.companyList.find((el) => {
        if (el.comNo == item.comNo) {
          el.bookmarkId = null;
          return true;
        }
      });

      this.$axios
        .delete("/api/myCompanyList", {
          data: {
            comNo: item.comNo,
            memberId: this.$store.state.userStore.id,
          },
        })
        .then((resp) => {
          console.log(resp);
          this.getLikeList();
        })
        .catch((error) => {
          console.log(error);
        });
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
.theme--light.v-sheet {
  border-color: lightgray;
  border-radius: 3%;
}
.company {
  padding: 0px;
  margin: 3px;
  text-align: left;
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
