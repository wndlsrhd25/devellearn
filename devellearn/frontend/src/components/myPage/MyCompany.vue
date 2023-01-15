<template>
  <v-container fluid style="max-width: 1300px">
    <v-row>
      <template v-for="item in likeList">
        <template>
          <v-col cols="3" :key="item.comNo">
            <v-card
              class="mx-auto card"
              max-width="300"
              style="box-shadow: none"
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
                height="200px"
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
      v-if="likeList.length > 0"
      v-model="likePage"
      :total-rows="likeRows"
      :per-page="likePerPage"
      align="right"
    ></b-pagination>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      likePage: 1,
      likeList: [],
      likePerPage: 4,
      likeRows: 1,
    };
  },
  watch: {
    likePage() {
      this.getLikeList();
    },
  },
  created() {
    this.getLikeList();
  },
  methods: {
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
        })
        .catch((error) => {
          console.log(error);
        });
    },
    bookmarkDelete(item) {
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
