<template>
  <div>
    <el-dialog :visible.sync="openDetail" width="40%" center>
      <div class="card">
        <div class="card__title">
          <div class="icon"></div>
          <h3>지식 QnA</h3>
        </div>
        <div class="card__body">
          <div class="half">
            <div class="featured_text">
              <span class="stock">{{ qnaDetail.open }}</span>
              <div class="reviews">
                <span>{{ qnaDetail.regDate | yyyyMMdd }}</span>
              </div>
              <h1 style="font-size: 25px">{{ qnaDetail.title }}</h1>
              <p class="sub" style="font-size: 15px">
                {{ qnaDetail.category }}
              </p>
              <div class="recommend">
                <p>QnA by</p>
                <h3>{{ qnaDetail.memberId }}</h3>
              </div>
            </div>
            <button
              type="button"
              class="btn01"
              style="float: left"
              @click="reportQna()"
              v-if="$store.state.userStore.id != ''"
            >
              신고
            </button>
            <button
              type="button"
              class="btn01"
              style="float: right"
              @click="EditButton()"
              v-if="this.$store.state.userStore.id == qnaDetail.memberId"
            >
              수정
            </button>
            <button
              type="button"
              class="btn02"
              style="float: right"
              @click="DeleteButton()"
              v-if="this.$store.state.userStore.id == qnaDetail.memberId"
            >
              삭제
            </button>
            <br />
            <hr />
            <div class="description">
              <pre style="border: none">{{ qnaDetail.content }}</pre>
            </div>

            <!--댓글-->
            <div class="detailBox">
              <div class="titleBox">
                <label>Comment</label>
              </div>
              <div class="actionBox">
                <ul
                  class="commentList"
                  v-for="item in repList"
                  :key="repList.repNo"
                >
                  <li>
                    <div class="commenterImage">
                      <img src="http://placekitten.com/50/50" />
                    </div>
                    <div class="commentText">
                      <button
                        type="button"
                        class="btn04"
                        style="float: right"
                        @click="repEditButton(item.qnarepNo)"
                        v-if="$store.state.userStore.id == item.memberId"
                      >
                        수정
                      </button>
                      <button
                        type="button"
                        class="btn05"
                        style="float: right"
                        @click="repDeleteButton(item.qnarepNo)"
                        v-if="$store.state.userStore.id == item.memberId"
                      >
                        삭제
                      </button>
                      <v-btn
                        class="btn06"
                        @click="repChoiceButton(item.qnarepNo)"
                        v-if="
                          $store.state.userStore.id == qnaDetail.memberId &&
                          !isHidden
                        "
                        v-on:click="isHidden = true"
                        >채 택</v-btn
                      >
                      <p class="">{{ item.content }}</p>
                      <p class="sub-text">
                        {{ item.memberId }} by {{ item.regDate | yyyyMMdd }}
                      </p>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="form-inline">
                <div class="form-group">
                  <input
                    class="form-control"
                    type="text"
                    placeholder="댓글을 입력하세요"
                    v-model="content"
                    id="textarea-rows"
                  />
                </div>
                <div class="form-group">
                  <button class="btn03" @click="insertrep()">Add</button>
                </div>
              </div>
            </div>
            <!---->
          </div>
        </div>
        <div class="card__footer"></div>
      </div>
    </el-dialog>
    <report-modal :report="report" ref="report"></report-modal>
    <update-rep @reload="reload" ref="updateRepPopup"></update-rep>
    <update-qna @reload="reload" ref="updatePopup"></update-qna>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import UpdateQna from "./UpdateQna.vue";
import UpdateRep from "./UpdateRep.vue";
import ReportModal from "../../../community/ReportCommunity.vue";
export default {
  name: "QnaDetail",
  components: {UpdateQna, UpdateRep, ReportModal},
  data() {
    return {
      openDetail: false,
      qnaDetail: [],
      repList: [],
      report: {},
      content: "",
      isHidden: false,
    };
  },
  computed: {
    setParams2() {
      const params2 = {
        qnaNo: this.qnaDetail.qnaNo,
        qnarepNo: this.qnarepNo,
        memberId: this.$store.state.userStore.id,
        content: this.content,
      };
      return params2;
    },
  },
  methods: {
    //신고
    reportQna() {
      this.openDetail = false;
      this.report.postCode = "qnas";
      this.report.postNo = this.qnaDetail.qnaNo;
      this.$refs.report.editMode = true;
      this.$refs.report.openPopup = true;
    },
    //상세조회
    getQnaDetail(qnaNo) {
      axios
        .get("/api/sqna/" + qnaNo)
        .then((response) => {
          console.log(response);
          this.qnaDetail = response.data;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getQnaRepList(qnaNo) {
      axios
        .get("/api/sqnarep/" + qnaNo)
        .then((response) => {
          console.log(response);
          this.repList = response.data;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    EditButton() {
      this.openDetail = false;
      this.$refs.updatePopup.qnaNo = this.qnaDetail.qnaNo;
      this.$refs.updatePopup.title = this.qnaDetail.title;
      this.$refs.updatePopup.category = this.qnaDetail.category;
      this.$refs.updatePopup.content = this.qnaDetail.content;
      this.$refs.updatePopup.editMode = true;
      this.$refs.updatePopup.openPopup = true;
    },
    DeleteButton() {
      this.openDetail = false;
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "삭제",
        })
        .then((result) => {
          if (result.isConfirmed) {
            axios
              .delete("/api/sqna/" + this.qnaDetail.qnaNo)
              .then((response) => {
                this.openDetail = false;
                this.$emit("reload");
              })
              .catch(function (error) {
                console.log(error);
              });
            this.$swal.fire("삭제완료", "", "success");
          }
        });
    },
    //댓글등록
    insertrep() {
      if (
        this.$store.state.userStore.id == null ||
        this.$store.state.userStore.id == ""
      ) {
        this.openDetail = false;
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
            this.$router.push({path: "/login"}).catch(() => {});
          }
        });
      } else {
        axios
          .post("/api/sqnarep", this.setParams2)
          .then((response) => {
            this.content = "";
            this.getQnaRepList(this.qnaDetail.qnaNo);
          })
          .catch(function (error) {
            console.log(error);
          });
        this.$emit("reload");
      }
    },
    repEditButton(qnarepNo) {
      this.openDetail = false;
      this.$refs.updateRepPopup.qnarepNo = qnarepNo;
      this.$refs.updateRepPopup.content = "";
      this.$refs.updateRepPopup.editMode = true;
      this.$refs.updateRepPopup.openPopup = true;
    },
    repDeleteButton(qnarepNo) {
      axios
        .delete("/api/sqnarep/" + qnarepNo)
        .then((response) => {
          this.content = "";
          this.getQnaRepList(this.qnaDetail.qnaNo);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    repChoiceButton(qnarepNo) {
      axios
        .put("/api/choiceRep", {
          qnarepNo: qnarepNo,
        })
        .then((response) => {
          console.log(response);
          this.getQnaRepList(this.qnaDetail.qnaNo);
        })
        .catch(function (error) {
          console.log(error);
        });
      this.$emit("reload");
    },
    DateFormat(row) {
      return moment(row.regDate).format("YYYY-MM-DD hh:MM:ss");
    },
    reload() {
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

.with {
  position: flex;
}
.content {
  float: left;
  width: 60%;
  border: 1px solid;
}
.rep {
  float: right;
  height: 450px;
  width: 35%;
}
.img {
  background-color: red;
  width: 35%;
  margin-left: 30px;
}
.mentor {
  font-size: 25px;
  text-align: left;
  color: gray;
}
.outline {
  text-align: left;
}
.nickname {
  margin-top: 20px;
}
.grade {
  float: left;
  padding-right: 10px;
}
.title {
  margin-top: 30px;
  font-size: 30px;
  font-weight: bold;
}
.m1 {
  float: left;
  padding-right: 30px;
}
.btn {
  position: absolute;
  clear: left;
  float: left;
  right: 500px;
  bottom: 30px;
  width: 150px;
}
.mx-auto {
  padding: 50px;
}
.info {
  margin-top: 30px;
}
.leftinfo {
  float: left;
}
.rightinfo {
  float: right;
}
.sub {
  margin-top: 30px;
  width: 300px;
}

.card {
  transition: 200ms;
  background: #fff;
  border: none;
}
.card .card__title {
  display: flex;
  align-items: center;
  padding: 30px 40px;
}
.card .card__title h3 {
  flex: 0 1 200px;
  text-align: right;
  margin: 0;
  color: #252525;
  font-family: GmarketSansMedium;
  font-weight: 600;
  font-size: 20px;
  text-transform: uppercase;
}
.card .card__title .icon {
  flex: 1 0 10px;
  background: #00336d;
  color: #fff;
  padding: 5px 5px;
  transition: 200ms;
}
.card .card__title .icon > a {
  color: #fff;
}
.card .card__title .icon:hover {
  background: #252525;
}
.card .card__body {
  padding: 0 40px;
  display: flex;
  flex-flow: row no-wrap;
  margin-bottom: 25px;
}
.card .card__body > .half {
  height: 500px;
  position: relative;
  box-sizing: border-box;
  padding: 0 15px;
  flex: 1 0 50%;
}
.card .card__body .featured_text h1 {
  margin: 0;
  padding: 0;
  font-weight: 800;
  font-family: GmarketSansMedium;
  font-size: 40px;
  line-height: 50px;
  color: #181e28;
}
.card .card__body .featured_text p {
  margin: 0;
  padding: 0;
}
.card .card__body .featured_text p.sub {
  font-family: GmarketSansMedium;
  font-size: 26px;
  text-transform: uppercase;
  color: #686e77;
  font-weight: 300;
  margin-bottom: 5px;
}
.card .card__body .featured_text p.price {
  font-family: GmarketSansMedium;
  color: #252525;
  font-size: 26px;
}
.card .card__body .image {
  padding-top: 15px;
  width: 100%;
}
.card .card__body .image img {
  display: block;
  max-width: 100%;
  height: auto;
}
.card .card__body .description {
  margin-bottom: 25px;
}
.card .card__body .description p {
  margin: 0;
  font-family: GmarketSansMedium;
  font-weight: 300;
  line-height: 27px;
  font-size: 16px;
  color: #555;
}
.card .card__body span.stock {
  font-family: GmarketSansMedium;
  font-weight: 600;
  color: #00336d;
}
.card .card__body .reviews > span {
  font-family: GmarketSansMedium;
  font-size: 14px;
  margin-left: 5px;
  color: #555;
}
.card .card__footer {
  padding: 30px 40px;
  display: flex;
  flex-flow: row no-wrap;
  align-items: center;
  position: relative;
}
.card .card__footer::before {
  content: "";
  position: absolute;
  display: block;
  top: 0;
  left: 40px;
  width: calc(100% - 40px);
  height: 3px;
  background: #00336d;
  background: linear-gradient(
    to right,
    #00336d 0%,
    #00336d 20%,
    #ddd 20%,
    #ddd 100%
  );
}
.recommend {
  flex: 1 0 10px;
}
.recommend p {
  margin: 0;
  font-family: GmarketSansMedium;
  text-transform: uppercase;
  font-weight: 600;
  font-size: 14px;
  color: #555;
  text-align: right;
}
.recommend h3 {
  margin: 0;
  font-size: 20px;
  font-family: GmarketSansMedium;
  font-weight: 600;
  text-transform: uppercase;
  color: #00336d;
  text-align: right;
}
.card .card__footer .action button {
  cursor: pointer;
  border: 1px solid #00336d;
  padding: 10px 15px;
  border-radius: 10px;
  color: #fff;
  background: #00336d;
  font-family: GmarketSansMedium;
  font-size: 16px;
  transition: 200ms;
  float: right;
  text-align: right;
}
.card .card__footer .action button:hover {
  background: #fff;
  color: #00336d;
}
.btn06 {
  font-size: 12px;
  color: #00336d;
  position: absolute;
  right: 20px;
}
.btn05 {
  color: #00336d;
  border: none;
  font-size: 12px;
  padding-left: 2px;
  position: absolute;
  right: 150px;
}
.btn04 {
  color: #00336d;
  border: none;
  font-size: 12px;
  padding-left: 2px;
  position: absolute;
  right: 120px;
}
.btn03 {
  background-color: #c29e5b;
  color: white;
  border-radius: 5px;
  margin-left: 5px;
  height: 33px;
}

.btn02 {
  background-color: #c29e5b;
  color: white;
  padding-left: 3px;
  padding-right: 3px;
  border-radius: 5px;
}
.btn01 {
  background-color: #00336d;
  color: white;
  padding-left: 3px;
  padding-right: 3px;
  border-radius: 5px;
}

.detailBox {
  bottom: 0;
  width: 100%;
  border: none;
  margin: 0 auto;
  position: absolute;
  bottom: 0;
}
.titleBox {
  background-color: #fdfdfd;
  padding: 2px;
}
.titleBox label {
  color: #444;
  margin: 0;
  display: inline-block;
}

.actionBox .form-group * {
  font-size: 13px;
  width: 100%;
  padding: 2px;
  padding-left: 7px;
  padding-right: 7px;
}
.commentList {
  padding: 0;
  list-style: none;
  max-height: 200px;
  overflow: auto;
}
.commentList li {
  margin: 0;
  margin-top: 10px;
  background-color: #fff9ef;
  border-radius: 10px;
}
.commentList li > div {
  display: table-cell;
}
.commenterImage {
  width: 30px;
  margin-right: 5px;
  height: 100%;
  float: left;
}
.commenterImage img {
  width: 100%;
  border-radius: 50%;
}
.commentText p {
  margin: 0;
  position: relative;
}
.sub-text {
  color: #aaa;
  font-family: verdana;
  font-size: 11px;
  text-align: right;
  float: right;
}
.actionBox {
  border-top: 1px solid #bbb;
  padding: 10px;
  max-height: 150px;
  overflow: auto;
}
.form-inline {
  margin-top: 10px;
  float: right;
  padding: 0;
  list-style: none;
  border: none;
}
.pay {
  text-align: right;
  background-color: #00336d;
  color: white;
  padding-right: 10px;
}
</style>
