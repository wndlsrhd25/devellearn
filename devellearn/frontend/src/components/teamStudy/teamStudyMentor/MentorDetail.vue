<template>
  <div>
    <el-dialog :visible.sync="openDetail" width="55%" center>
      <div class="card">
        <div class="card__title">
          <div class="icon"></div>
          <h3>MENTORING</h3>
        </div>
        <div class="card__body">
          <div class="half">
            <div class="featured_text">
              <span class="stock">{{ mentorDetail.open }}</span>
              <div class="reviews">
                <span>{{ mentorDetail.regDate | yyyyMMdd }}</span>
              </div>
              <h1>{{ mentorDetail.title }}</h1>
              <p class="sub">{{ mentorDetail.category }}</p>
              <p class="price">
                ￦ {{ Number(mentorDetail.price).toLocaleString("ko-KR") }} / day
              </p>
            </div>
            <div class="image">
              <img :src="'/api/images/teamstudy/' + mentorDetail.img" />
            </div>
          </div>
          <div class="half">
            <button
              type="button"
              class="btn01"
              style="float: left"
              @click="reportMentor()"
              v-if="$store.state.userStore.id != ''"
            >
              신고
            </button>
            <button
              type="button"
              class="btn01"
              style="float: right"
              @click="EditButton()"
              v-if="this.$store.state.userStore.id == mentorDetail.memberId"
            >
              수정
            </button>
            <button
              type="button"
              class="btn02"
              style="float: right"
              @click="DeleteButton()"
              v-if="this.$store.state.userStore.id == mentorDetail.memberId"
            >
              삭제
            </button>
            <br />
            <hr />
            <div class="description">
              <pre style="border: none; white-space : pre-wrap;">{{ mentorDetail.content }}</pre>
            </div>
          </div>
        </div>
        <div class="card__footer">
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
                    <img src="https://placeimg.com/100/100/animals/sepia" />
                  </div>
                  <div class="commentText">
                    <button
                      type="button"
                      class="btn04"
                      style="float: right"
                      @click="repEditButton(item.mentorrepNo)"
                      v-if="$store.state.userStore.id == item.memberId"
                    >
                      수정
                    </button>
                    <button
                      type="button"
                      class="btn05"
                      style="float: right"
                      @click="repDeleteButton(item.mentorrepNo)"
                      v-if="$store.state.userStore.id == item.memberId"
                    >
                      삭제
                    </button>
                    <p class="recontent">{{ item.content }}</p>
                    <p class="reid"> {{ item.memberId }}</p>
                    <p class="sub-text">{{ item.regDate | yyyyMMdd }}</p>
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
          <div class="recommend">
            <p>Mentoring by</p>
            <h3>{{ mentorDetail.memberId }}</h3>
          </div>
          <div class="action">
            <p>멘토링 기간 선택</p>
            <!--날짜-->
            <el-date-picker
              type="daterange"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
              placeholder="    "
              range-separator=" "
              v-model="form.date"
              :picker-options="pickerOptions"
              unlink-panels
            >
            </el-date-picker>

            <!--계산-->
            <div class="pay">
              <p>( {{ mentorDetail.price }} 원 / oneday )</p>
              <div>
                <input
                  v-model="mentorDetail.price"
                  readonly
                  style="text-align: right; width: 100%; color: white"
                />
                원 <br />
                <input
                  v-model="totalcount"
                  readonly
                  style="text-align: right; width: 100%; color: white"
                />
                일 <br />
                <p>결제금액 : {{ total }}</p>
              </div>
            </div>
            <button type="button" 
             @click="requestPayCheck()"
             v-if="this.$store.state.userStore.id != mentorDetail.memberId && mentorDetail.open!='모집완료'"
             >신청하기</button>
          </div>
        </div>
      </div>
    </el-dialog>
    <report-modal :report="report" ref="report"></report-modal>
    <update-rep @reload="reload" ref="updateRepPopup"></update-rep>
    <update-mentor @reload="reload" ref="updatePopup"></update-mentor>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import UpdateMentor from "./UpdateMentor.vue";
import UpdateRep from "./UpdateRep.vue";
import ReportModal from "../../community/ReportCommunity.vue";
export default {
  name: "MentorDetail",
  components: {UpdateMentor, UpdateRep, ReportModal},
  data() {
    return {
      openDetail: false,
      mentorDetail: [],
      repList: [],
      //날짜
      form: {
        timeUnit: 1,
      },
      pickerOptions: {
        disabledDate: this.disabledDate,
      },
      conList: [],
      count: {},
      report: {},
      content: "",
    };
  },
  computed: {
    setParams2() {
      const params2 = {
        mentorNo: this.mentorDetail.mentorNo,
        mentorrepNo: this.mentorrepNo,
        memberId: this.$store.state.userStore.id,
        content: this.content,
      };
      return params2;
    },
    totalcount: function () {
      if (this.form.date != null) {
        let start = new Date(this.form.date[0]);
        let end = new Date(this.form.date[1]);
        let diffDate = end - start;
        return Math.abs(diffDate / (1000 * 60 * 60 * 24)) + 1;
      }
      // console.log('끝'+this.form.date[1])
      // console.log('끝-시작'+(this.form.date[1]-this.form.date[0]))
      return 0;
    },
    total: function () {
      return this.mentorDetail.price * this.totalcount;
    },
    setParams() {
      const params = {
        mentorNo: this.mentorDetail.mentorNo,
        memberId: this.mentorDetail.memberId,
        mentiId: this.$store.state.userStore.id,
        startDate: this.form.date[0],
        endDate: this.form.date[1],
      };
      return params;
    },
  },

  created() {
    this.getConList();
  },

  methods: {
    requestPayCheck() {
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
        IMP.init("imp34244854");
        // IMP.request_pay(param, callback); //결제창 호출
        IMP.request_pay(
          {
            // param
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "ORD_" + new Date().getTime(),
            name: this.mentorDetail.title, //this.getImpName(),//즈믄ㅇ;름
            amount: this.total, //this.getImpAmount(),//가격
            buyer_email: this.$store.state.userStore.user.email,
            buyer_name: this.$store.state.userStore.user.name,
            buyer_tel: this.$store.state.userStore.user.phone,
          },
          (rsp) => {
            // callback
            if (rsp.success) {
              // 결제 성공 시 로직 - 강의 결제 내역에 등록
              this.MentorButton();
              console.log("결제 성공", rsp);
              // 결제 내역 DB 등록
              //this.insertPayment(rsp);
              //
            } else {
              // 결제 실패 시 로직,
              console.log("결제 실패", rsp);
              alert(rsp.error_msg);
            }
          }
        );
      }
    },

    /*
    // iamport 결제 api 사용
    requestPay: function () {
      IMP.init("imp34244854");
      // IMP.request_pay(param, callback); //결제창 호출
      IMP.request_pay(
        {
          // param
          pg: "html5_inicis",
          pay_method: "card",
          merchant_uid: "ORD_" + new Date().getTime(),
          name: this.mentorDetail.title, //this.getImpName(),//즈믄ㅇ;름
          amount: this.total, //this.getImpAmount(),//가격
          buyer_email: this.$store.state.userStore.user.email,
          buyer_name: this.$store.state.userStore.user.name,
          buyer_tel: this.$store.state.userStore.user.phone,
        },
        (rsp) => {
          // callback
          if (rsp.success) {
            // 결제 성공 시 로직 - 강의 결제 내역에 등록
            this.MentorButton();
            console.log("결제 성공", rsp);
            // 결제 내역 DB 등록
            //this.insertPayment(rsp);
            //
          } else {
            // 결제 실패 시 로직,
            console.log("결제 실패", rsp);
            alert(rsp.error_msg);
          }
        }
      );
    },
*/

    //신고
    reportMentor() {
      this.openDetail = false;
      this.report.postCode = "mentors";
      this.report.postNo = this.mentorDetail.mentorNo;
      this.$refs.report.editMode = true;
      this.$refs.report.openPopup = true;
    },
    //날짜
    getCount() {
      axios
        .get("/api/mentorcount/" + mentorNo)
        .then((response) => {
          console.log(response);
          this.count = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getConList() {
      axios
        .get("/api/mentorRelation")
        .then((response) => {
          console.log(response);
          this.conList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    disabledDate(time) {
      for (let i = 0; i < this.conList.length; i++) {
        this.form.timeUnit == "1";
        // console.log('start',this.yyyy(this.conList[i].startDate));
        // console.log('end',this.yyyy(this.conList[i].endDate));
        return (
          time.getTime() > new Date(this.yyyy(this.conList[i].startDate)) &&
          time.getTime() < new Date(this.yyyy(this.conList[i].endDate))
        );
      }
    },
    MentorButton() {
      axios
        .post("/api/mentorenter", this.setParams)
        .then((response) => {
          console.log(response);
          this.openDetail = false;
        })
        .catch(function (error) {
          console.log(error);
        });
        this.$swal.fire("신청완료", "", "success");
    },
    getMentorDetail(mentorNo) {
      axios
        .get("/api/mentor/" + mentorNo)
        .then((response) => {
          console.log(response);
          this.mentorDetail = response.data;
          this.openDetail = true;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getMentorRepList(mentorNo) {
      axios
        .get("/api/mentorRep/" + mentorNo)
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
      this.$refs.updatePopup.mentorNo = this.mentorDetail.mentorNo;
      this.$refs.updatePopup.title = this.mentorDetail.title;
      this.$refs.updatePopup.content = this.mentorDetail.content;
      this.$refs.updatePopup.category = this.mentorDetail.category;
      this.$refs.updatePopup.open = this.mentorDetail.open;
      this.$refs.updatePopup.price = this.mentorDetail.price;
      this.$refs.updatePopup.img = this.mentorDetail.img;
      this.$refs.updatePopup.editMode = true;
      this.$refs.updatePopup.openPopup = true;
    },
    /*DeleteButton() {
      axios
        .delete("/api/mentor/" + this.mentorDetail.mentorNo)
        .then((response) => {
          this.openDetail = false;
          this.$emit("reload");
        })
        .catch(function (error) {
          console.log(error);
        });
    },*/
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
              .delete("/api/mentor/" + this.mentorDetail.mentorNo)
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
    //댓글
    /*insertrep() {
      axios
        .post("/api/mentorRep", this.setParams2)
        .then((response) => {
          this.content = "";
          this.getMentorRepList(this.mentorDetail.mentorNo);
        })
        .catch(function (error) {
          console.log(error);
        });
      this.$emit("reload");
    },*/
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
          .post("/api/mentorRep", this.setParams2)
          .then((response) => {
            this.content = "";
            this.getMentorRepList(this.mentorDetail.mentorNo);
          })
          .catch(function (error) {
            console.log(error);
          });
        this.$emit("reload");
      }
    },
    repEditButton(mentorrepNo) {
      this.openDetail = false;
      this.$refs.updateRepPopup.mentorrepNo = mentorrepNo;
      this.$refs.updateRepPopup.content = "";
      this.$refs.updateRepPopup.editMode = true;
      this.$refs.updateRepPopup.openPopup = true;
    },
    repDeleteButton(mentorrepNo) {
      axios
        .delete("/api/mentorRep/" + mentorrepNo)
        .then((response) => {
          this.content = "";
          this.getMentorRepList(this.mentorDetail.mentorNo);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    DateFormat(row) {
      return moment(row.regDate).format("YYYY-MM-DD hh:MM:ss");
    },
    reload() {
      this.$emit("reload");
    },
    yyyy: function (value) {
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
      return year + "-" + month + "-" + day;
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
  padding: 0px 40px;
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
  position: relative;
  box-sizing: border-box;
  padding: 0 15px;
  flex: 1 0 50%;
  max-width: 50%;
}
.card .card__body .featured_text h1 {
  margin: 0;
  padding: 0;
  font-weight: 800;
  font-family: GmarketSansMedium;
  font-size: 30px;
  line-height: 50px;
  color: #181e28;
}
.card .card__body .featured_text p {
  margin: 0;
  padding: 0;
}
.card .card__body .featured_text p.sub {
  font-family: GmarketSansMedium;
  font-size: 20px;
  text-transform: uppercase;
  color: #686e77;
  font-weight: 300;
  margin-bottom: 5px;
}
.card .card__body .featured_text p.price {
  font-family: GmarketSansMedium;
  color: #252525;
  font-size: 20px;
}
.card .card__body .image {
  padding-top: 15px;
  width: 100%;
}
.card .card__body .image img {
  display: block;
  width: 100%;
  height: 350px;
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
.card .card__footer .recommend {
  flex: 1 0 10px;
  margin-top: -250px;
}
.card .card__footer .recommend p {
  margin: 0;
  font-family: GmarketSansMedium;
  text-transform: uppercase;
  font-weight: 600;
  font-size: 14px;
  color: #555;
}
.card .card__footer .recommend h3 {
  margin: 0;
  font-size: 20px;
  font-family: GmarketSansMedium;
  font-weight: 600;
  text-transform: uppercase;
  color: #00336d;
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
  width: 50%;
  border: none;
  margin: 0 auto;
  position: absolute;
  bottom: 30px;
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

.commentList li > div {
  display: table-cell;
}

.commenterImage img {
  width: 100%;
  border-radius: 50%;
}
.commentText p {
  margin: 0;
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

.commenterImage {
  width: 30px;
  margin-right: 5px;
  height: 100%;
  float: left;
  position: absolute;
  top: 5px;
  left: 5px;
}
.btn05 {
  color: #00336d;
  border: none;
  font-size: 9px;
  padding-left: 2px;
  position: absolute;
  top: 5px;
  right: 5px;
}
.btn04 {
  color: #00336d;
  border: none;
  font-size: 9px;
  padding-left: 2px;
  position: absolute;
  top: 5px;
  right: 30px;
}
.reid {
  color: #00336d;
  font-size: 13px;
  position: absolute;
  top: 5px;
  left: 40px;
}

.recontent{
  position : absolute;
  top : 22px;
  left : 40px;
  width: 70%;
}
.sub-text {
  font-size: 11px;
  position: absolute;
  bottom: 5px;
  right: 5px;
}
.commentList li {
  margin: 0;
  margin-top: 10px;
  background-color: #f1f1f1;
  border-radius: 10px;
  position: relative;
  min-height: 80px;
}
.commentList {
  padding: 0;
  list-style: none;
  max-height: 250px;
}
.action{
  width: 44%;
}
.el-date-editor--daterange.el-input, .el-date-editor--daterange.el-input__inner, .el-date-editor--timerange.el-input, .el-date-editor--timerange.el-input__inner {
    width: 100%;
}
.commentList li {
  width: 100%;
}
.titleBox{
  width: 100%;
}
</style>
