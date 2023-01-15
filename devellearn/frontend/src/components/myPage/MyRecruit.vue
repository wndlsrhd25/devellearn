<template>
  <v-container fluid style="max-width: 1300px">
    <v-row>
      <template v-for="item in bookmarkList">
        <template>
          <v-col cols="3" :key="item.recruitNo">
            <v-card
              class="mx-auto card"
              max-width="300"
              style="box-shadow: none"
              @click="
                $router
                  .push({
                    path: '/jobDetail/recruitDetail',
                    query: {recruitNo: item.recruitNo, comNo: item.comNo},
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
                height="200px"
                style="border-radius: 3%"
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
      v-if="bookmarkList.length > 0"
      v-model="bookmarkPage"
      :total-rows="bookmarkListRows"
      :per-page="bookPerPage"
      align="right"
    ></b-pagination>
  </v-container>
</template>

<script>
export default {
  props: {
    listName: {type: String},
  },
  data: function () {
    return {
      bookmarkList: [],
      bookmarkPage: 1,
      bookPerPage: 4,
      bookmarkListRows: 1,
    };
  },
  watch: {
    bookmarkPage() {
      this.getBookmarkList();
    },
  },
  methods: {
    getBookmarkList() {
      if (this.$store.state.userStore.id == "") {
        return;
      }
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
    bookmarkDelete(item) {
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
  },
  created: function () {
    this.getBookmarkList();
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
}

.star {
  width: 15px;
  height: 15px;
  margin: 2px;
}
.right {
  text-align: right;
}
</style>
