<template>
  <div>
    <form id="insertData">
      <!-- 팝업 내용 -->
      <el-dialog
        :visible.sync="openPopup"
        width="50%"
        center
        :close-on-click-modal="false"
      >
        <div class="card">
          <div class="card__title">
            <div class="icon"></div>
            <h3>프로젝트 등록</h3>
          </div>
          <div class="card__body">
            <div class="half">
              <div class="featured_text">
                <h1>PROJECT</h1>
                <p class="sub">프로젝트를 모집하세요!</p>
                <p class="price">#DEVELLEARN</p>
              </div>

              <div class="image">
                <!-- 이미지 -->
                <div>
                  <p style="text-align: left; margin-top: 25px">첨부 이미지</p>

                  <div style="text-align: left">
                    <div class="gray-box">
                      <v-img :src="img" class="thumbnail-box" />
                    </div>
                    <v-flex xs8>
                      <v-file-input
                        v-model="image"
                        name="image"
                        accept=".png, .jpeg, .jpg"
                        prepend-icon="mdi-camera"
                        @change="preview_thumbnail()"
                        @click:clear="img = ''"
                      />
                    </v-flex>
                    <p style="margin-bottom: 0px">
                      -등록가능확장자 : jpg, png, jpeg
                    </p>
                    <p>-이미지는 300*250에 최적화되어 있습니다</p>
                  </div>
                </div>
              </div>
            </div>
            <div class="half">
              <span class="stock">PROJECT TITLE</span>
              <div class="description">
                <el-input
                  placeholder="제목을 입력해 주세요"
                  v-model="projectName"
                  name="projectName"
                ></el-input>

                <v-text-field
                  :rules="rules"
                  v-model="projectId"
                  name="projectId"
                  label="프로젝트 ID"
                  style="width: 80%"
                >
                </v-text-field>
                <button
                  style="float: right"
                  type="button"
                  id="checkBtn"
                  class="btn04"
                  @click="projectcheckId"
                  required
                >
                  중복확인
                </button>

                <!--카테고리-->
                <span class="stock">CATEGORY</span><br />
                <el-select
                  v-model="category"
                  name="category"
                  placeholder="Select"
                >
                  <el-option
                    v-for="item in categoryOption"
                    :key="item.category"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <br /><span class="stock">HOW TO PROJECT</span><br />
                <!--진행방식-->
                <el-select v-model="how" placeholder="Select" name="how">
                  <el-option
                    v-for="item in howOption"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <br /><span class="stock">TEAM's NUMBER</span><br />
                <!--인원수-->
                <el-input-number
                  v-model="human"
                  name="human"
                  @change="handleChange"
                  :min="1"
                  :max="20"
                ></el-input-number>

                <br /><span class="stock">DATE</span><br />
                <!--날짜-->
                <div class="block">
                  <el-date-picker
                    v-model="startMon"
                    type="date"
                    placeholder="프로젝트 시작일"
                  >
                  </el-date-picker>
                </div>
                <div class="block">
                  <el-date-picker
                    v-model="endMon"
                    type="date"
                    placeholder="프로젝트 마감일"
                  >
                  </el-date-picker>
                </div>

                <!--내용-->
                <span class="stock">CONTENT</span>
                <el-input
                  style="margin-top: 5px"
                  type="textarea"
                  :rows="5"
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
              <button type="button" @click="insertproject()">등록</button>
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
    </form>
  </div>
</template>

<script>
//import axios from 'axios';
export default {
  name: "InsertProject",
  data() {
    return {
      rules: [
        (value) => !!value || "필수정보입니다.",
        (value) => (value || "").length <= 20 || "Max 20 characters",
      ],
      openPopup: false,
      editMode: false,
      projectNo: "",
      projectName: "",
      projectId: "",
      content: "",
      category: "",
      open: "모집중",
      img: "",
      human: "",
      how: "",
      categoryOption: [
        {
          value: "#WEB개발",
          label: "#WEB개발",
        },
        {
          value: "#APP개발",
          label: "#APP개발",
        },
        {
          value: "#커머스/쇼핑몰",
          label: "#커머스/쇼핑몰",
        },
        {
          value: "#퍼블리싱",
          label: "#퍼블리싱",
        },
        {
          value: "#네트워크/보안",
          label: "#네트워크/보안",
        },
        {
          value: "#게임",
          label: "#게임",
        },
        {
          value: "#일반소프트웨어",
          label: "#일반소프트웨어",
        },
        {
          value: "#ETC",
          label: "#ETC",
        },
      ],
      howOption: [
        {
          value: "온라인",
          label: "온라인",
        },
        {
          value: "오프라인",
          label: "오프라인",
        },
        {
          value: "ETC",
          label: "ETC",
        },
      ],
      value: "",
      //이미지
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      isUpdate: true,
      image: "",

      //인원수
      num: 1,
      //날짜
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "Today",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "Yesterday",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "A week ago",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
      startMon: "",
      endMon: "",
      vaildId: false,
    };
  },
  computed: {
    setParams() {
      const params = {
        memberId: this.$store.state.userStore.id,
        projectNo: this.projectNo,
        blogId: this.projectId,
        content: this.content,
      };
      return params;
    },
  },
  methods: {
    //아이디 중복체크
    projectcheckId() {
      this.openPopup = false;
      this.$axios
        .post("/api/projectidCheck", {
          projectId: this.projectId,
        })
        .then((res) => {
          if (res.data != 1) {
            this.$swal.fire({
              title: "사용가능한 아이디입니다!",
              icon: "success",
              showConfirmButton: false,
              timer: 1000,
            });
          } else {
            this.$swal.fire({
              title: "이미 사용중인 아이디입니다!",
              icon: "impossible",
              showConfirmButton: false,
              timer: 1000,
            });
            this.projectId = "";          
          }
          setTimeout();
        });
        
        setTimeout(() => {
          this.openPopup = true;

       }, 1000);

    },
    insertproject() {
      const formData = new FormData(document.getElementById("insertData"));
      let memberId = this.$store.state.userStore.id;
      formData.append("memberId", memberId);
      formData.append("startMon", this.startMon);
      formData.append("endMon", this.endMon);
      this.$axios
        .post("/api/projecti", formData, {
          Headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
      this.$axios
        .post("/api/blogenter", this.setParams)
        .then((response) => {
          console.log(response);
          this.openPopup = false;
          this.$emit("reload");
        })
        .catch(function (error) {
          console.log(error);
        });
      this.projectName = "";
      this.content = "";
      this.category = "";
      this.img = "";
      this.image = "";
      this.how = "";
      this.human = "";
      this.startMon = "";
      this.endMon = "";
      this.open = "";
      Swal.fire({
        position: "center",
        icon: "success",
        title: "등록 완료",
        showConfirmButton: false,
        timer: 1000,
      });
    },
    ////////////////////////////
    //이미지
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
    },
    //인원수
    handleChange(value) {
      console.log(value);
    },
    reload() {
      this.$emit("reload");
    },
    preview_thumbnail() {
      this.img = URL.createObjectURL(this.image);
      console.log("sk : " + this.img);
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
.card {
  background: #fff;
  border: none;
}
.card .card__title {
  display: flex;
  align-items: center;
  padding: 5px 40px;
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
  box-sizing: border-box;
  padding: 0 15px;
  flex: 1 0 50%;
  height: 600px;
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
  padding: 10px 25px;
  border-radius: 10px;
  color: #fff;
  background: #00336d;
  font-family: GmarketSansMedium;
  font-size: 16px;
  transition: 200ms;
}
.card .card__footer .action button:hover {
  background: #fff;
  color: #00336d;
}
.gray-box {
  margin-left: 10px;
  height: 250px;
  width: 300px;
  background-color: rgb(158, 158, 158);
}
.thumbnail-box {
  height: 100%;
  width: 100%;
}
.btn04 {
  color: white;
  border: 1px solid #00336d;
  background-color: #00336d;
  font-size: 10px;
  padding: 5px;
  margin-top: -50px;
}
</style>
