<template>
  <div>
    <v-item-group active-class="primary">
      <v-container class="out0">
        <div>
          <b-tabs content-class="mt-3">
            <b-tab title="전체" @click="setOpen('')"></b-tab>
            <b-tab title="진행중" @click="setOpen('진행중')"></b-tab>
            <b-tab title="멘토종료" @click="setOpen('멘토종료')"></b-tab>
          </b-tabs>
        </div>

        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th style="font-size:12px; text-align: center;">포스터</th>
                <th style="font-size:12px; text-align: center;">멘토링</th>
                <th style="font-size:12px; text-align: center;">카테고리</th>
                <th style="font-size:12px; text-align: center;">참가한 멘티</th>
                <th style="font-size:12px; text-align: center;">멘토링기간</th>
                <th style="font-size:12px; text-align: center;">진행여부</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mentorList" :key="mentorList.mentorconsNo">
                <td class="w3">
                  <div class="mb-2">
                    <b-avatar
                      :src="'/api/images/teamstudy/' + item.img"
                      size="4rem"
                    ></b-avatar>
                  </div>
                </td>
                <td class="w4">
                  <p class="title" style="font-size: 14px !important">{{ item.title }}</p>
                </td>
                <td class="w4">
                  <p style="font-size: 14px !important">{{ item.category }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.mentiId }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.startDate | yyyyMMdd }}</p>
                  <p>{{ item.endDate | yyyyMMdd }}</p>
                </td>
                <td class="w1">
                  <b-button
                    class="btn2"
                    variant="outline-primary"
                    @click.stop="clickStatus(item.mentorconsNo)"
                  >
                    {{ item.status }}
                  </b-button>
                </td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>

        <b-pagination
          v-model="currentPage"
          :total-rows="mentorListRows"
          :per-page="listPage"
          align="right"
        ></b-pagination>
      </v-container>
    </v-item-group>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "MentorMember",
  components: {},
  data() {
    return {
      openDetail: false,
      mentorList: [],
      oder: 1,
      listPage: 8,
      currentPage: 1,
      mentorListRows: 1,
      isSearch: false,
      status: "",
      category: "",
      statusyn: "",
      searchinfo: "",
      searchinput: "",
      startDate: "",
      endDate: "",
      mentorconsNo: "",
    };
  },
  watch: {
    currentPage() {
      this.getMentorList();
      return;
    },
  },
  computed: {},
  created() {
    this.getMentorList();
  },
  methods: {
    clickStatus(mentorconsNo) {
      console.log("aaa" + mentorconsNo);
      axios
        .put("/api/mentorStatus", {
          mentorconsNo: mentorconsNo,
        })
        .then((response) => {
          console.log(response);
          this.getMentorList();
        })
        .catch(function (error) {
          console.log(error);
        });
      this.$emit("reload");
    },
    //검색
    setOpen(status) {
      this.statusyn = status;
      this.search();
      this.searchinfo = "";
    },
    search(categoryinfo) {
      axios
        .post("/api/searchMyMenti", {
          memberId: this.$store.state.userStore.id,
          status: this.statusyn,
          category: categoryinfo,
          searchinput: this.searchinfo,
          order: this.oeder,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.mentorList = response.data.list;
          this.mentorListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    DateFormat(row) {
      return moment(row.regDate).format("YYYY-MM-DD hh:MM:ss");
    },
    getMentorList() {
      axios
        .post("/api/mymenti", {
          order: this.oeder,
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.mentorList = response.data.list;
          this.mentorListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    reload() {
      // 목록을 재로딩을 위해 이벤트 emit
      this.$emit("reload");
    },
  },
  filters: {
    yyyyMMdd: function (value) {
      if (value == "") return "";
      var js_date = new Date(value);
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      return year + " / " + month + " / " + day;
    },
  },
};
</script>

<style scoped>
.out0 {
  width: 900px;
}
.v-card--reveal {
  bottom: 0;
  opacity: 1 !important;
  position: absolute;
  width: 100%;
}
.mark {
  float: right;
  margin-top: -4px;
}
.grade {
  float: left;
}
.title {
  font-size: 1em;
  font-weight: bold;
  text-align: left;
  margin-top: -10px;
}
.nickname {
  float: right;
}
.mx-auto {
  padding: 5px;
}
.lean {
  margin-left: 50px;
}
.img {
  margin-bottom: 10px;
}
.outimg {
  position: relative;
}
.category {
  text-align: right;
  font-size: 10px;
  margin-top: -5px;
  border: none;
}
.card {
  width: 200px;
  height: 250px;
}
.down {
  margin: -10px;
}
.line {
  margin-top: -13px;
}
.btn2{
  background-color: #00336d !important;
  color: white !important;
}
.w4{
  font-size: 12px;
}
</style>
