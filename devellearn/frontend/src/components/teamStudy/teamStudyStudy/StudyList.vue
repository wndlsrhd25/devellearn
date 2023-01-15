<template class="all">
  <div>
    <v-item-group active-class="primary">
      <v-container class="out0">
        <div class="searchline">
          <el-input
            class="searchbar"
            placeholder="검색하세요"
            v-model="searchinfo"
          >
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
          <el-button type="primary" class="btn2" @click="setSearch(searchinfo)"
            >검색</el-button
          >
        </div>

        <div class="openline">
          <b-tabs content-class="mt-3">
            <b-tab title="전체" @click="setOpen('전체')"></b-tab>
            <b-tab title="모집중" @click="setOpen('모집중')"></b-tab>
            <b-tab title="모집완료" @click="setOpen('모집완료')"></b-tab>
          </b-tabs>
        </div>

        <div class="">
          <div class="row">
            <div class="categorinline">
              <div class="button-group-pills text-center" data-toggle="buttons">
                <label class="btn btn-default" @click="search('#WEB개발')">
                  <input type="radio" name="options" />
                  <div style="color:white;">#WEB개발</div>
                </label>
                <label class="btn btn-default" @click="search('#APP개발')">
                  <input type="radio" name="options" />
                  <div style="color:white;">#APP개발</div>
                </label>
                <label
                  class="btn btn-default"
                  @click="search('#커머스/쇼핑몰')"
                >
                  <input type="radio" name="options" />
                  <div style="color:white;">#커머스/쇼핑몰</div>
                </label>
                <label class="btn btn-default" @click="search('#퍼블리싱')">
                  <input type="radio" name="options" />
                  <div style="color:white;">#퍼블리싱</div> </label
                ><br />
                <label
                  class="btn btn-default"
                  @click="search('#네트워크/보안')"
                >
                  <input type="radio" name="options" />
                  <div style="color:white;">#네트워크/보안</div>
                </label>
                <label class="btn btn-default" @click="search('#게임')">
                  <input type="radio" name="options" />
                  <div style="color:white;">#게이밍개발</div>
                </label>
                <label
                  class="btn btn-default"
                  @click="search('#일반소프트웨어')"
                >
                  <input type="radio" name="options" />
                  <div style="color:white;">#일반소프트웨어</div>
                </label>
                <label class="btn btn-default" @click="search('#ETC')">
                  <input type="radio" name="options" />
                  <div style="color:white;">#ETC</div>
                </label>
              </div>
            </div>
            <el-row class="insert" type="flex" justify="end">
              <el-button @click="insertButton()" class="btn" style="color:white;"
                >스터디 등록</el-button
              >
            </el-row>
          </div>
        </div>
        <v-row>
          <v-col
            v-for="item in studyList"
            :key="studyList.studyNo"
            cols="12"
            md="3"
            @click="clickRow(item.studyNo)"
          >
            <v-item v-slot="{active, toggle}">
              <div class="normal">
                <div class="module">
                  <div class="thumbnail">
                    <img :src="'/api/images/teamstudy/' + item.img" />
                    <div class="date">
                      <div>{{ item.open }}</div>
                    </div>
                  </div>
                  <div class="content">
                    <div class="category">{{ item.open }}</div>
                    <h1 class="titlei">{{ item.title }}</h1>
                    <h2 class="sub-title">{{ item.category }}</h2>
                    <div class="description">{{ item.content }}</div>
                    <div class="meta">
                      <span class="timestamp" style="float: left">
                        <i class="fa fa-clock-o"></i>
                        {{ item.regDate | yyyyMMdd }}
                      </span>
                      <span class="comments" style="float: right">
                        <i class="fa fa-comments"></i>
                        <a class="ttt">{{ item.memberId }}</a>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </v-item>
          </v-col>
        </v-row>

        <b-pagination
          v-model="currentPage"
          :total-rows="studyListRows"
          :per-page="listPage"
          align="right"
          class="page"
        ></b-pagination>
      </v-container>
    </v-item-group>
    <insert-study ref="insertPopup" @reload="getStudyList()"></insert-study>
    <study-detail ref="detailPopup" @reload="getStudyList()"></study-detail>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import StudyDetail from "./StudyDetail.vue";
import InsertStudy from "./InsertStudy.vue";

export default {
  name: "StudyList",
  components: {StudyDetail, InsertStudy},
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
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
      ) {
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "로그인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({ path: "/login" }).catch(() => {});
          }
        });
      } else {
        this.openDetail = false;
        this.$refs.insertPopup.editMode = true;
        this.$refs.insertPopup.openPopup = true;
      }
    },
    DateFormat(row) {
      return moment(row.regDate).format("YYYY-MM-DD hh:MM:ss");
    },
    search(categoryinfo) {
      axios
        .post("/api/searchStudy", {
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
        .post("/api/study", {
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
@font-face {
  font-family: "GmarketSansMedium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: GmarketSansMedium;
}

.page {
  margin-top: 15px;
}
.all {
  font-family: GmarketSansMedium;
}
.btn {
  background-color: #c29e5b;
  color: #00336d;
  margin-bottom: 10px;
}
.btn2 {
  background-color: #00336d;
  color: white;
}
.btn:hover {
  background-color: #00336d;
}
.openline {
  padding-bottom: 15px;
}
.searchline {
  margin-left: 70%;
  margin-bottom: -3%;
}
.searchbar {
  width: 70%;
  margin-right: 5px;
}
.categorinline {
  margin: 0 auto;
  margin-bottom: 7px;
}
.insert {
  margin: 0 auto;
  height: 90px;
}
.button-group-pills .btn {
  border-radius: 5px;
  line-height: 1.5;
  width: 180px;
  margin-bottom: 10px;
  margin-left: 10px;
  border-color: #bbbbbb;
  background-color: #00336d;
  color: #c29e5b;
  float: center;
  font-size: 12px;
}
.button-group-pills .btn.active {
  border-color: #c29e5b;
  background-color: #c29e5b;
  color: #fff;
  box-shadow: none;
}
.button-group-pills .btn:hover {
  border-color: #c29e5b;
  background-color: #c29e5b;
  color: #fff;
}
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
  margin-top: 35px;
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

.row {
  margin-bottom: 10px;
}
.ttt {
  text-decoration: none;
  font-weight: 600;
  color: #000;
  font-weight: 500;
  font-size: 5px;
}
.timestamp {
  margin-left: 5px;
  margin-top: 15px;
  font-size: 5px;
  color: #00336d;
}
.comments {
  margin-right: 5px;
  margin-top: 14px;
  font-size: 5px;
  color: #00336d;
}

header {
  height: 53px;
  display: flex;
  align-items: center;
  flex-direction: column;
  margin: 50px 0;
}
header h1 {
  font-size: 24px;
  font-weight: 400;
  color: #333;
  padding: 0;
  margin: 0 0 15px;
}
header span i.fa {
  color: #00336d;
}
header span {
  font-size: 12px;
  color: #333;
}
.normal,
.hover {
  flex: 1;
  padding: 0px;
  padding-top: 0px;
}
.module {
  width: 200px;
  height: 250px;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.15);
  transition: all 0.3s linear 0s;
  overflow: hidden;
  position: relative;
  border-radius: 5%;
}
.module:hover {
  box-shadow: 0 1px 35px 0 rgba(0, 0, 0, 0.3);
}
.thumbnail {
  padding: 0px;
  position: relative;
  overflow: hidden;
  background: black;
}
.thumbnail img {
  width: 100%;
  height: 150px;
  transition: all 0.3s;
}
.module:hover .thumbnail img {
  transform: scale(1.1);
  opacity: 0.6;
}
.col-12 {
  padding-top: 2px;
}
.thumbnail .date {
  position: absolute;
  top: 5px;
  right: 5px;
  background: #00336d;
  padding-top: 20px;
  color: #fff;
  font-weight: bold;
  border-radius: 100%;
  height: 55px;
  width: 55px;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 5px;
  text-transform: uppercase;
}
.date div:first-child {
  font-size: 13px;
  line-height: 1.2;
}
.content {
  position: absolute;
  width: 100%;
  height: 100px;
  bottom: 0;
  background: #fff;
  padding-top: 0px;
  transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
}
.module:hover .content {
  height: 200px;
}

.content .category {
  position: absolute;
  top: -185px;
  left: 0;
  color: #fff;
  text-transform: uppercase;
  background: #c29e5b;
  padding: 10px 15px;
  font-weight: bold;
}
.titlei {
  margin: 0;
  margin-top: 10px;
  padding: 0 0 0px;
  color: #333333;
  font-size: 18px;
  font-weight: 700;
}
.sub-title {
  margin: 0 auto;
  margin-top: 7px;
  padding: 10 0 5px;
  color: #00336d;
  font-size: 15px;
  font-weight: 400;
}
.description {
  color: #666666;
  font-size: 12px;
  line-height: 1.8em;
  height: 0;
  opacity: 1;
  transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
  overflow: hidden;
  margin-top: 10px;
}
.module:hover .description {
  height: 100px;
}
.meta {
  margin: 5px 0 0;
  color: #999999;
}
</style>
