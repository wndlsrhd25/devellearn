<template>
  <div>
    <form id="updateData">
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
            <h3>멘토링 등록</h3>
          </div>
          <div class="card__body">
            <div class="half">
              <div class="featured_text">
                <h1>MENTORING</h1>
                <p class="sub">멘토링을 모집하세요!</p>
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
              <span class="stock">MENTORING TITLE</span>
              <div class="description">
                <el-input
                  placeholder="제목을 입력해 주세요"
                  v-model="title"
                  name="title"
                ></el-input
                ><br />

                <!--카테고리-->
                <span class="stock"><br />CATEGORY</span><br />
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

                <span class="stock"><br /><br />CONTENT</span>
                <el-input
                  style="margin-top: 5px"
                  type="textarea"
                  :rows="10"
                  placeholder="내용을 입력해 주세요."
                  v-model="content"
                  name="content"
                >
                </el-input>

                <!--모집여부-->
                <span class="stock"><br /><br />CONTENT</span><br />
                <div>
                  <el-radio-group v-model="open" name="open">
                    <el-radio-button
                      label="모집중"
                      value="모집중"
                    ></el-radio-button>
                    <el-radio-button
                      label="모집완료"
                      value="모집완료"
                    ></el-radio-button>
                  </el-radio-group>
                </div>
                <br />

                <!--가격-->
                <br /><span class="stock"
                  ><br />PRICE (1일단위 멘토 가격입니다.)</span
                ><br />
                <el-input-number
                  v-model="price"
                  name="price"
                  controls-position="right"
                  @change="handleChange"
                  :min="1000"
                  :max="1000000000"
                  :step="500"
                ></el-input-number>
              </div>
            </div>
          </div>
          <div class="card__footer">
            <div class="recommend">
              <p>Recommended by</p>
              <h3>DEVELLEARN</h3>
            </div>
            <div class="action">
              <button type="button" @click="updatementor()">등록</button>
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
export default {
  name: "UpdateMentor",
  data() {
    return {
      openPopup: false,
      editMode: false,
      mentorNo: "",
      title: "",
      content: "",
      category: "",
      open: "",
      img: "",
      price: "",
      categoryOption: [
        {
          value: "#개발/프로그래밍",
          label: "#개발/프로그래밍",
        },
        {
          value: "#보안/네트워크",
          label: "#보안/네트워크",
        },
        {
          value: "#데이터사이언스",
          label: "#데이터사이언스",
        },
        {
          value: "#직무/마케팅",
          label: "#직무/마케팅",
        },
        {
          value: "#커리어/취업정보",
          label: "#커리어/취업정보",
        },
        {
          value: "#게임",
          label: "#게임",
        },
        {
          value: "#학문/외국어",
          label: "#학문/외국어",
        },
        {
          value: "#교양/ETC",
          label: "#교양/ETC",
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
    };
  },
  computed: {
    setParams() {
      const params = {
        mentorNo: this.mentorNo,
        memberId: this.$store.state.userStore.id,
        title: this.title,
        content: this.content,
        category: this.category,
        open: this.open,
        price: this.price,
        img: this.img,
      };
      return params;
    },
  },
  methods: {
    updatementor() {
      const formData = new FormData(document.getElementById("updateData"));
      let memberId = this.$store.state.userStore.id;
      let open = this.open;
      formData.append("memberId", memberId);
      formData.append("mentorNo", this.mentorNo);
      formData.append("open", open);

      this.$axios
        .put("/api/mentor", formData, {
          Headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          console.log(response);
          this.openPopup = false;
          this.$emit("reload");
          Swal.fire({
            position: "center",
            icon: "success",
            title: "수정 완료",
            showConfirmButton: false,
            timer: 1000,
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
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
</style>
