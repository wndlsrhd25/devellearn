<template>
  <div>
    <el-dialog
      :visible.sync="openPopup"
      width="50%"
      center
      :close-on-click-modal="false"
    >
      <div class="card">
        <div class="card__title">
          <div class="icon"></div>
          <h3>기술 리뷰 등록</h3>
        </div>
        <div class="card__body">
          <div class="half">
            <div class="featured_text">
              <h1 style="font-size: 30px; color: #00336d">wtih Skill Reivew</h1>
              <p class="sub" style="font-size: 20px">
                기술 리뷰를 작성해보세요!
              </p>
              <p class="price" style="font-size: 20px">#DEVELLEARN</p>
            </div>
          </div>
          <div class="half">
            <span class="stock">TITLE</span>
            <div class="description">
              <el-input
                placeholder="제목을 입력해 주세요"
                v-model="title"
                name="title"
              ></el-input
              ><br />

              <span class="stock"><br /><br />CONTENT</span>
              <el-input
                style="margin-top: 5px"
                type="textarea"
                :rows="15"
                placeholder="내용을 입력해 주세요."
                v-model="content"
                name="content"
              >
              </el-input>
            </div>
          </div>
        </div>
        <div class="card__footer">
          <div class="recommend">
            <p>Recommended by</p>
            <h3>DEVELLEARN</h3>
          </div>
          <div class="action">
            <button type="button" @click="insertboard()">등록</button>
            <button
              type="button"
              @click="openPopup = false"
              style="background-color: gray; margin-left: 5px; border: none"
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "InsertBoard",
  data() {
    return {
      openPopup: false,
      boardNo: "",
      editMode: false,
      title: "",
      content: "",
    };
  },
  computed: {
    setParams() {
      const params = {
        memberId: this.$store.state.userStore.id,
        boardNo: this.boardNo,
        title: this.title,
        content: this.content,
      };
      return params;
    },
  },
  methods: {
    insertboard() {
      axios
        .post("/api/boardi3", this.setParams)
        .then((response) => {
          console.log(response);
          this.openPopup = false;
          this.title = "";
          this.content = "";
          this.$emit("reload");
        })
        .catch(function (error) {
          console.log(error);
        });
      Swal.fire({
        position: "center",
        icon: "success",
        title: "등록 완료",
        showConfirmButton: false,
        timer: 1000,
      });
    },
    reload() {
      this.$emit("reload");
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
.card .card__footer .recommend {
  flex: 1 0 10px;
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
.btn05 {
  color: #00336d;
  border: none;
  font-size: 9px;
  padding-left: 2px;
}
.btn04 {
  color: #00336d;
  border: none;
  font-size: 9px;
  padding-left: 2px;
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
