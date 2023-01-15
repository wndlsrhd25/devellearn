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
        <br /><br />

        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th style="font-size: 12px; text-align: center">포스터</th>
                <th style="font-size: 12px; text-align: center">프로젝트명</th>
                <th style="font-size: 12px; text-align: center">프로젝트ID</th>
                <th style="font-size: 12px; text-align: center">작성자</th>
                <th style="font-size: 12px; text-align: center">작성일</th>
                <th style="font-size: 12px; text-align: center">카테고리</th>
                <th style="font-size: 12px; text-align: center">모집여부</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="item in projectList"
                :key="projectList.projectNo"
                @click="clickRow(item.projectNo)"
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
                  <p class="title" style="font-size: 14px !important">
                    {{ item.projectName }}
                  </p>
                </td>
                <td class="w4" style="font-size: 14px !important">
                  <p>{{ item.projectId }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.memberId }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.regDate | yyyyMMdd }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.category }}</p>
                </td>
                <td class="w1">
                  <b-button
                    class="btn2"
                    variant="outline-primary"
                    @click.stop="clickOpen(item.projectNo)"
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
          :total-rows="projectListRows"
          :per-page="listPage"
          align="right"
        ></b-pagination>
      </v-container>
    </v-item-group>
    <project-detail
      ref="detailPopup"
      @reload="getProjectList()"
    ></project-detail>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import ProjectDetail from "./ProjectDetail.vue";

export default {
  name: "ProjectList",
  components: {ProjectDetail},
  data() {
    return {
      openDetail: false,
      projectList: [],
      oder: 1,
      listPage: 8,
      currentPage: 1,
      projectListRows: 1,
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
        this.getProjectList();
        return;
      }
      this.search();
    },
  },
  computed: {},
  created() {
    this.getProjectList();
  },
  methods: {
    clickOpen(projectNo) {
      axios
        .put("/api/projectOpen", {
          projectNo: projectNo,
        })
        .then((response) => {
          console.log(response);
          this.getProjectList();
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
        .post("/api/searchMyProject", {
          memberId: this.$store.state.userStore.id,
          open: this.openyn,
          category: categoryinfo,
          searchinput: this.searchinfo,
          order: this.oeder,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.projectList = response.data.list;
          this.projectListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getProjectList() {
      axios
        .post("/api/myproject", {
          order: this.oeder,
          memberId: this.$store.state.userStore.id,
          pageNum: this.currentPage,
          pageSize: this.listPage,
        })
        .then((response) => {
          this.projectList = response.data.list;
          this.projectListRows = response.data.total;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    clickRow(projectNo) {
      console.log(projectNo);
      this.$refs.detailPopup.getProjectDetail(projectNo);
      this.$refs.detailPopup.getProjectRepList(projectNo);
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
  font-size: 12px;
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
.searchline {
  margin-left: 70%;
  margin-bottom: -3%;
}
.searchbar {
  width: 70%;
  margin-right: 5px;
}
.btn2 {
  background-color: #00336d;
  color: white;
}
.w4 {
  font-size: 12px;
}
</style>
