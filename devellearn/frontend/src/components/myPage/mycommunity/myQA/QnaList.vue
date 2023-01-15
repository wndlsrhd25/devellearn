<template>
  <div>
    <v-item-group active-class="primary">
      <v-container class="out0">
        <div>
          <b-tabs content-class="mt-3">
            <b-tab title="전체" @click="setOpen('')"></b-tab>
            <b-tab title="답변중" @click="setOpen('답변중')"></b-tab>
            <b-tab title="해결완료" @click="setOpen('해결완료')"></b-tab>
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
                <th style="font-size:12px; text-align: center;">글번호</th>
                <th style="font-size:12px; text-align: center;">제목</th>
                <th style="font-size:12px; text-align: center;">카테고리</th>
                <th style="font-size:12px; text-align: center;">작성자</th>
                <th style="font-size:12px; text-align: center;">작성일</th>
                <th style="font-size:12px; text-align: center;">답변여부</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="item in qnaList"
                :key="qnaList.qnaNo"
                @click="clickRow(item.qnaNo)"
              >
                <td class="w4">
                  <p>{{ item.qnaNo }}</p>
                </td>

                <td class="w2">
                  <p class="title">{{ item.title }}</p>
                </td>

                <td class="w4">
                  <p>{{ item.category }}</p>
                </td>

                <td class="w4">
                  <p>{{ item.memberId }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.regDate | yyyyMMdd }}</p>
                </td>
                <td class="w1">
                  <b-button
                    class="btn2"
                    variant="outline-primary"
                    @click.stop="clickOpen(item.qnaNo)"
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
          :total-rows="qnaListRows"
          :per-page="listPage"
          align="right"
        ></b-pagination>
      </v-container>
    </v-item-group>
    <qna-detail ref="detailPopup" @reload="getQnaList()"></qna-detail>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import QnaDetail from "./QnaDetail.vue";

export default {
  name: "QnaList",
  components: {QnaDetail},
  data() {
    return {
      isHidden: false,
      openDetail: false,
      qnaList: [],
      oder: 1,
      listPage: 8,
      currentPage: 1,
      qnaListRows: 1,
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
        this.getQnaList();
        return;
      }
      this.search();
    },
  },
  computed: {},
  created() {
    this.getQnaList();
  },
  methods: {
    clickOpen(qnaNo) {
      axios
        .put("/api/qnaOpen", {
          qnaNo: qnaNo,
        })
        .then((response) => {
          console.log(response);
          this.getQnaList();
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
        .post("/api/searchMyQA", {
          memberId: this.$store.state.userStore.id,
          open: this.openyn,
          category: categoryinfo,
          searchinput: this.searchinfo,
          order: this.oeder,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.qnaList = response.data.list;
          this.qnaListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getQnaList() {
      axios
        .post("/api/myQA", {
          memberId: this.$store.state.userStore.id,
          order: this.oeder,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.qnaList = response.data.list;
          this.qnaListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    clickRow(qnaNo) {
      this.$refs.detailPopup.getQnaDetail(qnaNo);
      this.$refs.detailPopup.getQnaRepList(qnaNo);
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
  font-size: 13px !important;
  font-weight: bold;
  text-align: left;
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
