<template>
  <div>
    <div id="company">
      <div id="top">
        <img
          :src="'/api/images/company/' + company.img"
          alt="로고"
          class="img"
        />
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
          <b-button class="btn-primary" variant="primary" @click="editInfo()">
            수정</b-button
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

      <div class="summary">
        <table>
          <tr>
            <td>대표</td>
            <td>{{ company.ceo }}</td>
          </tr>
          <tr>
            <td>매출</td>
            <td v-if="company.sales != 0">
              {{ company.sales }}
            </td>
            <td v-else>-</td>
          </tr>
          <tr>
            <td>주소</td>
            <td>{{ company.addr }}</td>
          </tr>
          <tr>
            <td>웹사이트</td>
            <td>
              <a :href="'http://' + company.url">{{ company.url }}</a>
            </td>
          </tr>
          <tr>
            <td>직종</td>
            <td style="white-space: pre-wrap">{{ company.jobType }}</td>
          </tr>
          <tr>
            <td>소개</td>
            <td style="white-space: pre-wrap">{{ company.introduce }}</td>
          </tr>
        </table>
      </div>
    </div>
    <v-dialog v-model="dialog" max-width="800px">
      <InfoEdit
        :company="company"
        @close="dialog = false"
        @reload="getInfo()"
      ></InfoEdit>
    </v-dialog>
  </div>
</template>
<script>
import InfoEdit from "./InfoEdit.vue";
export default {
  components: {
    InfoEdit,
  },
  data: function () {
    return {
      company: {},
      avg: {},
      dialog: false,
    };
  },
  created() {
    this.getInfo();
  },
  methods: {
    getInfo() {
      this.$axios
        .post("/api/company", {
          comNo: this.$store.state.userStore.user.comNo,
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
    editInfo() {
      this.dialog = true;
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
table tr td:first-child {
  width: 100px;
  vertical-align: top;
}

table {
  border-collapse: separate;
  border-spacing: 0 20px;
}
p {
  margin-bottom: 5px;
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
table tr td:nth-child(2) {
  display: inline-block;
  max-width: 700px;
  vertical-align: top;
}
</style>
