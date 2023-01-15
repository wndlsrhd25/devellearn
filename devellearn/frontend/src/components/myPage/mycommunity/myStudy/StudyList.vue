<template>
  <div>
    <v-item-group active-class="primary">
      <v-container class="out0">
        <div>
          <b-tabs content-class="mt-3">
            <b-tab title="전체" @click="setOpen('전체')"></b-tab>
            <b-tab title="모집중" @click="setOpen('모집중')"></b-tab>
            <b-tab title="모집완료" @click="setOpen('모집완료')"></b-tab>
          </b-tabs>
        </div>

        <div class="searchline">
          <el-input class="searchbar" placeholder="검색하세요" v-model="searchinfo">
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
          <el-button type="primary" class="btn2" @click="setSearch(searchinfo)">검색</el-button>
        </div><br><br>

        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th style="font-size:12px; text-align: center;">포스터</th>
                <th style="font-size:12px; text-align: center;">스터디명</th>
                <th style="font-size:12px; text-align: center;">ID</th>
                <th style="font-size:12px; text-align: center;">카테고리</th>
                <th style="font-size:12px; text-align: center;">작성일</th>
                <th style="font-size:12px; text-align: center;">모집여부</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="item in studyList"
                :key="studyList.studyNo"
                @click="clickRow(item.studyNo)"
              >
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
                  <p style="font-size: 14px !important">{{ item.memberId }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.category }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.regDate | yyyyMMdd }}</p>
                </td>
                <td class="w1">
                  <b-button
                   class="btn2"
                    variant="outline-primary"
                    @click.stop="clickOpen(item.studyNo)"
                  >
                    {{ item.open }}
                  </b-button>
                </td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>

        <b-pagination
          v-model="currentPage"
          :total-rows="studyListRows"
          :per-page="listPage"
          align="right"
        ></b-pagination>
      </v-container>
    </v-item-group>
    <study-detail ref="detailPopup" @reload="getStudyList()"></study-detail>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import StudyDetail from "./StudyDetail.vue";

export default {
  name: "StudyList",
  components: {StudyDetail},
  data() {
    return {
      openDetail: false,
      studyList: [],
      oder: 1,
      listPage: 8,
      currentPage: 1,
      studyListRows: 1,
      keyword: "",
      isSearch: false,
      open: "",
      category: "",
      openyn: "",
      searchinfo: "",
      searchinput: "",
    };
  },
  watch: {
    currentPage() {
      if (!this.isSearch) {
        this.getStudyList();
        return;
      }
      this.search();
    },
  },
  computed: {},
  created() {
    this.getStudyList();
  },
  methods: {
    clickOpen(studyNo) {
      axios
        .put("/api/studyOpen", {
          studyNo: studyNo,
        })
        .then((response) => {
          console.log(response);
          this.getStudyList();
        })
        .catch(function (error) {
          console.log(error);
        });
      this.$emit("reload");
    },
    //검색
    setOpen(open) {
      this.openyn = open;
      this.search();
      this.searchinfo = "";
    },
    setSearch(searchinput) {
      this.searchinfo = searchinput;
      this.search();
      this.searchinfo = "";
    },
    setempty() {
      this.open = "";
      this.categoryinfo = "";
      this.searchinfo = "";
    },
    insertButton() {
      this.openDetail = false;
      this.$refs.insertPopup.editMode = true;
      this.$refs.insertPopup.openPopup = true;
    },
    DateFormat(row) {
      return moment(row.regDate).format("YYYY-MM-DD hh:MM:ss");
    },
    search(categoryinfo) {
      axios
        .post("/api/searchMyStudy", {
          memberId: this.$store.state.userStore.id,
          open: this.openyn,
          category: categoryinfo,
          searchinput: this.searchinfo,
          order: this.oeder,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.studyList = response.data.list;
          this.studyListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getStudyList() {
      axios
        .post("/api/mystudy", {
          order: this.oeder,
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.studyList = response.data.list;
          this.studyListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    clickRow(studyNo) {
      console.log(studyNo);
      this.$refs.detailPopup.getStudyDetail(studyNo);
      this.$refs.detailPopup.getStudyRepList(studyNo);
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
.searchline{
  margin-left: 70%;
  margin-bottom: -3%;
}
.searchbar{
  width: 70%;
  margin-right: 5px;
}
.btn2{
  background-color: #00336d;
  color: white;
}
.w4{
  font-size: 12px;
}
</style>
