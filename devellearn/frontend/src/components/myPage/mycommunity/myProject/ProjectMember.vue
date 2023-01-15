<template>
  <div>
    <v-item-group active-class="primary">
      <v-container class="out0">
        <div>
          <b-tabs content-class="mt-3">
            <b-tab title="전체" @click="setOpen('전체')"></b-tab>
            <b-tab title="승인대기" @click="setOpen('승인대기')"></b-tab>
            <b-tab title="승인완료" @click="setOpen('승인완료')"></b-tab>
          </b-tabs>
        </div>

        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th style="font-size:12px; text-align: center;">포스터</th>
                <th style="font-size:12px; text-align: center;">프로젝트이름</th>
                <th style="font-size:12px; text-align: center;">프로젝트ID</th>
                <th style="font-size:12px; text-align: center;">카테고리</th>
                <th style="font-size:12px; text-align: center;">신청일</th>
                <th style="font-size:12px; text-align: center;">신청한 ID</th>
                <th style="font-size:12px; text-align: center;">승인여부</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in projectList" :key="projectList.projectconsNo">
                <td class="w3">
                  <div class="mb-2">
                    <b-avatar
                      :src="'/api/images/teamstudy/' + item.img"
                      size="4rem"
                    ></b-avatar>
                  </div>
                </td>
                <td class="w4" style="font-size: 14px !important">
                  <p class="title">{{ item.projectName }}</p>
                </td>
                <td class="w4" style="font-size: 14px !important">
                  <p class="title">{{ item.projectId }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.category }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.applyDate | yyyyMMdd }}</p>
                </td>
                <td class="w4">
                  <p>{{ item.comememberId }}</p>
                </td>
                <td class="w1">
                  <b-button
                    class="btn2"
                    variant="outline-primary"
                    v-on:click="isHidden = true"
                    @click="clickStatus(item)"
                  >
                    {{ item.status }}
                  </b-button>
                  <b-button
                    v-if="!isHidden"
                    class="btn3"
                    variant="danger"
                    @click="clickDelete(item.projectconsNo)"
                  >
                    거절
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
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "ProjectMember",
  components: {},
  data() {
    return {
      isHidden: false,
      openDetail: false,
      projectList: [],
      oder: 1,
      listPage: 8,
      currentPage: 1,
      projectListRows: 1,
      isSearch: false,
      status: "",
      category: "",
      statusyn: "",
      searchinfo: "",
      searchinput: "",
      applyDate: "",
      projectconsNo: "",
      projectId: "",
      projectName: "",
    };
  },
  watch: {
    currentPage() {
      this.getProjectList();
      return;
    },
  },
  computed: {},
  created() {
    this.getProjectList();
  },
  methods: {
    clickStatus(item) {
      console.log("승인받는자" + item.comememberId);
      console.log("프로젝트 아이디" + item.projectId);
      axios
        .put("/api/projectStatus", {
          projectconsNo: item.projectconsNo,
          //memberId와 projectId가 필요함
          memberId: item.comememberId,
          projectId: item.projectId,
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
    clickDelete(projectconsNo) {
      axios
        .delete("/api/projectStatusDelate/" + projectconsNo)
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
    setOpen(status) {
      this.statusyn = status;
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
    search(categoryinfo) {
      axios
        .post("/api/searchMyPmember", {
          memberId: this.$store.state.userStore.id,
          status: this.statusyn,
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
    DateFormat(row) {
      return moment(row.regDate).format("YYYY-MM-DD hh:MM:ss");
    },
    getProjectList() {
      axios
        .post("/api/mypmember", {
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
  font-size: 12px !important;
  font-weight: bold;
  text-align: left;
  margin-bottom: 0px;
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
.btn3{
  background-color: #6d0000;
  color: white;
}
.w4{
  font-size: 12px !important;
}
</style>
