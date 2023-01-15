<template>
  <div id="company">
    <div id="top">
      <img :src="'/api/images/company/' + company.img" alt="로고" class="img" />
      <div>
        <h3>{{ company.name }}</h3>
        <div class="flex">
          <b-icon-star-fill class="h3" variant="success"></b-icon-star-fill>
          <span class="star">{{ company.avgRate }}</span>
          <a :href="'http://' + company.url"
            ><span class="url">{{ company.url }}</span></a
          >
        </div>
      </div>
      <div style="margin-left: auto">
        <b-button
          v-if="
            $store.getters['userStore/getId'] != null &&
            $store.getters['userStore/getId'] != '' &&
            $store.getters['userStore/getEmpUser'] == 'false'
          "
          class="btn-primary"
          variant="primary"
          @click="bookmark(company)"
        >
          <b-icon
            :icon="company.bookmarkId == null ? 'bookmark' : 'bookmark-fill'"
          ></b-icon
          >저장</b-button
        >
      </div>
    </div>
    <div class="flex icons">
      <div class="flex">
        <b-icon-building class="h1"></b-icon-building>
        <div>
          <span class="icon-main">{{ company.comSize }}</span>
          <span class="icon-sub">기업형태</span>
        </div>
      </div>
      <div class="flex">
        <b-icon-people class="h1"></b-icon-people>
        <div>
          <span class="icon-main" v-if="company.empSize != null"
            >{{ company.empSize }}명</span
          >
          <span class="icon-main" v-else>-</span>
          <span class="icon-sub">사원수</span>
        </div>
      </div>
      <div class="flex">
        <b-icon-calendar-day class="h1"></b-icon-calendar-day>
        <div>
          <span class="icon-main">{{ company.estDate | yyyyMMdd }}</span>
          <span class="icon-sub">설립일</span>
        </div>
      </div>
    </div>
    <div class="rate">
      <div class="manjok">
        <h5>기업만족도</h5>
        <div class="flex">
          <b-icon-star-fill class="h3" variant="success"></b-icon-star-fill>
          <span class="star">{{ company.avgRate }}</span>
        </div>
        <p>업계평균</p>
        <div class="flex">
          <b-icon-star-fill class="h5" variant="success"></b-icon-star-fill>
          <span class="icon-sub">{{ avg.avgRate }}</span>
        </div>
      </div>
      <div class="manjok">
        <h5>급여만족도</h5>
        <div class="flex">
          <b-icon-star-fill class="h3" variant="success"></b-icon-star-fill>
          <span class="star">{{ company.avgSalrate }}</span>
        </div>
        <p>업계평균</p>
        <div class="flex">
          <b-icon-star-fill class="h5" variant="success"></b-icon-star-fill>
          <span class="icon-sub">{{ avg.avgSalRate }}</span>
        </div>
      </div>
      <div class="manjok">
        <h5>워라밸</h5>
        <div class="flex">
          <b-icon-star-fill class="h3" variant="success"></b-icon-star-fill>
          <span class="star">{{ company.avgWlbrate }}</span>
        </div>
        <p>업계평균</p>
        <div class="flex">
          <b-icon-star-fill class="h5" variant="success"></b-icon-star-fill>
          <span class="icon-sub">{{ avg.avgWlbRate }}</span>
        </div>
      </div>
    </div>
    <div class="tab">
      <div>
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/companyDetail/detailInfo',
                query: {comNo: company.comNo},
              })
              .catch(() => {})
          "
          >상세</span
        >
        |
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/companyDetail/companyReview',
                query: {comNo: company.comNo},
              })
              .catch(() => {})
          "
          >리뷰</span
        >|
        <span
          class="link"
          @click="
            $router
              .push({
                path: '/companyDetail/interview',
                query: {comNo: company.comNo},
              })
              .catch(() => {})
          "
          >면접후기</span
        >
      </div>
      <hr />
    </div>

    <router-view></router-view>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      company: {},
      avg: {},
    };
  },
  created() {
    this.getInfo();
  },
  components: {},
  methods: {
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
    getInfo() {
      this.$axios
        .post("/api/company", {
          comNo: this.$route.query.comNo,
          memberId: this.$store.state.userStore.id,
        })
        .then((resp) => {
          console.log(resp);
          this.company = resp.data;
          this.getavg();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getavg() {
      let joblist = this.company.jobType.split(",");
      console.log(joblist);
      this.$axios
        .post("/api/recruit/avg", {
          jobTypeList: joblist,
        })
        .then((resp) => {
          console.log(resp);
          this.avg = resp.data;
        })
        .catch(function (error) {
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

      // 최종 포맷 (ex - '2021-10-08')
      return year + "/" + month + "/" + day;
    },
  },
};
</script>
<style scoped>
p {
  margin-bottom: 5px;
}
.manjok {
  width: auto;
  text-align: center;
}

.rate {
  clear: both;
  display: flex;
  justify-content: space-around;
}
.icons {
  margin: 20px auto;
}
.icon-main {
  font-weight: bold;
  font-size: large;
  margin: 0 5px;
  display: block;
}
.icon-sub {
  font-size: 0.8rem;
  margin: 0 5px;
}
.flex {
  display: flex;
  justify-content: center;
}
#top {
  display: flex;
  align-items: flex-end;

  margin: 0px auto;
}
#company {
  min-height: 80vh;
  width: 60%;
  margin: 50px auto;
  text-align: left;
}
.img {
  height: 100px;
  width: 100px;
  border-radius: 5px;
  margin: 10px;
}
.url {
  line-height: 220%;
  margin-left: 10px;
}
.content {
  width: 80%;
  margin: 0 auto;
  text-align: left;
}
.link {
  font-weight: bold;
  font-size: large;
  margin: 0 15px;
  line-height: 100%;
  cursor: pointer;
  color: #0d6efd;
}
a {
  text-decoration: none;
  color: black;
}
.star {
  margin: 5px;
  padding-bottom: 10px;
  font-size: large;
}
.tab {
  text-align: left;

  margin: 20px auto;
  display: flex;
  /* justify-content: space-between; */
}
</style>
