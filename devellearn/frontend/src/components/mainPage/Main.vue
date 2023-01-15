<template>
  <div class="main">
    <!-- Cart -->
    <div class="wrap-header-cart js-panel-cart">
      <div class="s-full js-hide-cart"></div>

      <div class="header-cart flex-col-l p-l-65 p-r-25">
        <div class="header-cart-title flex-w flex-sb-m p-b-8">
          <span class="mtext-103 cl2"> Your Cart </span>

          <div
            class="fs-35 lh-10 cl2 p-lr-5 pointer hov-cl1 trans-04 js-hide-cart"
          >
            <i class="zmdi zmdi-close"></i>
          </div>
        </div>

        <div class="header-cart-content flex-w js-pscroll">
          <ul class="header-cart-wrapitem w-full">
            <li class="header-cart-item flex-w flex-t m-b-12">
              <div class="header-cart-item-img">
                <img src="images/item-cart-01.jpg" alt="IMG" />
              </div>

              <div class="header-cart-item-txt p-t-8">
                <a
                  href="#"
                  class="header-cart-item-name m-b-18 hov-cl1 trans-04"
                >
                  White Shirt Pleat
                </a>

                <span class="header-cart-item-info"> 1 x $19.00 </span>
              </div>
            </li>

            <li class="header-cart-item flex-w flex-t m-b-12">
              <div class="header-cart-item-img">
                <img src="images/item-cart-02.jpg" alt="IMG" />
              </div>

              <div class="header-cart-item-txt p-t-8">
                <a
                  href="#"
                  class="header-cart-item-name m-b-18 hov-cl1 trans-04"
                >
                  Converse All Star
                </a>

                <span class="header-cart-item-info"> 1 x $39.00 </span>
              </div>
            </li>

            <li class="header-cart-item flex-w flex-t m-b-12">
              <div class="header-cart-item-img">
                <img src="images/item-cart-03.jpg" alt="IMG" />
              </div>

              <div class="header-cart-item-txt p-t-8">
                <a
                  href="#"
                  class="header-cart-item-name m-b-18 hov-cl1 trans-04"
                >
                  Nixon Porter Leather
                </a>

                <span class="header-cart-item-info"> 1 x $17.00 </span>
              </div>
            </li>
          </ul>

          <div class="w-full">
            <div class="header-cart-total w-full p-tb-40">Total: $75.00</div>

            <div class="header-cart-buttons flex-w w-full">
              <a
                href="shoping-cart.html"
                class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-r-8 m-b-10"
              >
                View Cart
              </a>

              <a
                href="shoping-cart.html"
                class="flex-c-m stext-101 cl0 size-107 bg3 bor2 hov-btn3 p-lr-15 trans-04 m-b-10"
              >
                Check Out
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- video -->
    <section class="section-slide">
      <div class="wrap-slick1">
        <div class="slick1">
          <div class="item-slick1">
            <div class="jb-box">
              <video muted autoplay loop>
                <source src="@/assets/video.mp4" type="video/mp4" />
              </video>
              <div class="jb-text">
                <div class="titleoutline">
                  <div class="maintext">
                    <div class="heading">
                      <h3 class="sub">
                        코딩이 처음인 저도... 괜찮을까요?
                      </h3>
                      <img
                        src="../../../public/images/icons/logo.png"
                        width="35%"
                        alt="IMG-LOGO"
                        style="margin-bottom:20px"
                      />
                      <h1 class="head">디벨런<span>과 함께 </span>개발업계<span>의</span></h1>
                      <h1 class="head"><span>'개미'</span> 가 되어보세요!</h1>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section><br>
    <!--3번째-->
<div style="text-align:left; margin-left: 300px;">
<span style="font-size: 40px; font-weight: 900; color: #00336d;">디벨런 강의!</span>
<p style="font-size: 30px; font-weight: 900; color: gray;"><span style="color :#00336d; font-weight:bold;">DEVELLEAN</span> 을 선택하는 데에는 이유가 있습니다!</p>
</div>
      <v-slide-group
        v-model="model"
        class="pa-4"
        active-class="success"
        show-arrows
      >
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_1.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_2.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_3.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_4.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_5.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_6.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_7.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_8.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_9.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_10.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>
        <v-slide-item>
          <v-card class="ma-4" height="200" width="400">
            <img src="../../../public/images/3_1.jpg" style="width: 100%;">
          </v-card>
        </v-slide-item>

      </v-slide-group>
<!--2번째--><br>
<div style="text-align:left; margin-left: 300px;">
<span style="font-size: 30px; font-weight: 900; color: #00336d;">MENTORING</span>
<span style="font-size: 30px; font-weight: 900; color: gray;"> 을 먼저 </span>
<span style="font-size: 30px; font-weight: 900; color: #00336d;">경험한 </span>
<span style="font-size: 30px; font-weight: 900; color: #00336d;">'개미'</span>
<span style="font-size: 30px; font-weight: 900; color: gray;"> 들의 후기를 들어보세요!</span>
</div>

<div class="items">
    <div class="entry">
      <img src="images/2_1.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_2.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_3.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_4.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_5.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_6.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_7.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_8.jpg"/>
    </div>
    <div class="entry">
      <img src="images/2_9.jpg"/>
    </div>
    
  </div>

  <br><br><br>

<div style="text-align:left; margin-left: 300px;">
  <span style="font-size: 40px; font-weight: 900; color: #00336d;">이러닝 강의!</span><br>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">개린이 </span>
  <span style="font-size: 30px; font-weight: 900; color: gray;">를 위한 </span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">DEVELLEARN</span>
  <span style="font-size: 30px; font-weight: 900; color: gray;">의 </span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">추천강의!</span>
</div>
<!-- 추천강의 리스트 -->
     <CardList :list="beginnerList" :title="'개린이추천강의'"></CardList>
<br><br>


<br><br><br><br>
<div style="text-align:left; margin-left: 300px;">
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">개미가 </span>
  <span style="font-size: 30px; font-weight: 900; color: gray;">선정한 </span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">DEVELLEARN</span>
  <span style="font-size: 30px; font-weight: 900; color: gray;">의 </span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">인기강의!</span>
</div>


<!-- 인기강의 리스트 -->
<CardList :list="hotList" :title="'인기강의!!'"></CardList>
<!-- 배너 영역 -->
<br/><br/><br/>
<!--4번째-->
<el-carousel :interval="5000" arrow="always" height=500px>
    <el-carousel-item>
      <img src="../../../public/images/4_1.jpg" style="width: 100%;">
    </el-carousel-item>
    <el-carousel-item>
      <img src="../../../public/images/4_2.jpg" style="width: 100%;">
    </el-carousel-item>
    <el-carousel-item>
      <img src="../../../public/images/4_3.jpg" style="width: 100%;">
    </el-carousel-item>
    <el-carousel-item>
      <img src="../../../public/images/4_4.jpg" style="width: 100%;">
    </el-carousel-item>
</el-carousel>


<br><br><br><br>
<div style="text-align:left; margin-left: 300px;">
  <span style="font-size: 30px; font-weight: 900; color: gray;">다양한 </span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">기업이 </span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;">DEVELLEARN</span>
  <span style="font-size: 30px; font-weight: 900; color: gray;">과 함께합니다!</span>
</div>
<br><br><br><br>
<!--링크배너-->
<div class="items">
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/a041f2fa-7f40-4517-bdb7-822fc7800d80/%E1%84%8F%E1%85%A1%E1%84%8F%E1%85%A1%E1%84%8B%E1%85%A9.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/b47b4ead-5141-46ec-8003-81c6dbc2e5b7/LG.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/4aa479b9-4e05-4f8e-84b3-1fb7edb60c0c/nc.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/10b49bf2-cd9a-4501-ac3c-84059d04f171/%E1%84%89%E1%85%A1%E1%86%B7%E1%84%89%E1%85%A5%E1%86%BC.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/e7dd4c2a-8496-4708-b9fd-ae66317e40ad/%E1%84%82%E1%85%A6%E1%86%A8%E1%84%89%E1%85%B3%E1%86%AB.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/1429748f-8c44-4941-8913-752071578eaf/%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/34618dc7-2862-4db0-9bbc-631ca0c8f5b5/sk.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/0add4321-40e2-46db-9aac-3222b25a69c4/%E1%84%8B%E1%85%AE%E1%84%8B%E1%85%A1%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%A7%E1%86%BC%E1%84%8C%E1%85%A6%E1%84%83%E1%85%B3%E1%86%AF.png"/>
    </div>
    <div class="entry2">
      <img style="height:50px" src="https://cdn.inflearn.com/public/group_logo/0/c085de89-e640-4bc0-afad-efd1e96dbb58/%E1%84%85%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AB.png"/>
    </div>
    
  </div>
  <br><br><br><br>
<div style="text-align:left; margin-left: 300px;">
  <span style="font-size: 50px; font-weight: 900; color: #00336d;">시작해볼까요? </span>
  <span style="font-size: 50px; font-weight: 900; color: #00336d;">당신의 커리어 점프!</span>
</div>
<br><br><br><br>
<div style="text-align:left; margin-left: 300px;">
  <span style="font-size: 30px; font-weight: 900; color: gray;">ABOUT</span>
  <span style="font-size: 30px; font-weight: 900; color: #00336d;"> 개발에 미쳐라!</span>
</div>
<AboutPage></AboutPage>
   
  </div>
</template>

<script>
// @ is an alias to /src
import BannerVue from "@/components/mainPage/BannerVue.vue";
import SearchBar from "@/components/mainPage/SearchBar.vue";
import CardList from "@/components/mainPage/CardList.vue";
import AboutPage from "@/components/community/About.vue";

export default {
  data() {
    return {
      items: [
        {src: "main1.png"},
        {src: "main2.png"},
        {src: "main3.png"},
        {src: "main4.png"},
      ],
      placeholder: "배우고싶은 강의를 입력하세요!!",
      beginnerList: [],
      hotList: [],
    };
  },
  components: {
    SearchBar,
    CardList,
    BannerVue,
    AboutPage,
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.$axios
        .get("/api/lectureMain")
        .then((resp) => {
          console.log(resp.data);
          this.beginnerList = resp.data.beginner.list;
          this.hotList = resp.data.hotLecture.list;
        })
        .catch((error) => {
          console.log(error);
        });
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
.welcome-banner__bg > img {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.incontent {
  max-width: 75%;
  margin: 0 auto;
}
.list-title {
  width: 65%;
  margin: 0 auto;
  text-align: left;
}
#chatBot {
  position: fixed;
  right: 10%;
  top: 75%;
}
.main {
  height: auto;
}
.jb-box {
  width: 100%;
  height: 100%;
  overflow: hidden;
  margin: 0px auto;
  position: relative;
  font-family: Poppins-Regular;
}
video {
  width: 100%;
  opacity: 0.5;
}
.jb-text {
  position: absolute;
  top: 50%;
  width: 100%;
}
.jb-text p {
  margin-top: -24px;
  text-align: center;
  font-size: 70px;
  font-family: Poppins-Regular;
}

.titleoutline {
  margin: 0;
  padding: 0;
}
.maintext {
  position: relative;
  height: 100vh;
  width: 100%;
  top: 0;
  left: 0;
  right: 0;
}

.maintext .heading {
  font-family: Gmak;
  position: absolute;
  height: auto;
  width: 100%;
  transform: translateY(-50%);
  text-align: center;
}
.maintext .heading .head {
  position: relative;
  font-size: 3em;
  font-weight: 900;
  color: #00336d;
  letter-spacing: 0.1em;
  margin-top: 1%;
}
.maintext .heading .head span {
  color: white;
}
.maintext .heading .sub {
  position: relative;
  font-size: 22px;
  font-weight: 900;
  color: #00336d;
  margin: 0% 0 0% 0;
  letter-spacing: 0.1em;
}
.maintext .heading .btns {
  height: auto;
  width: 100%;
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: center;
  position: relative;
}
.maintext .heading .btns a {
  color: #00336d;
  text-align: center;
  text-decoration: none;
  font-size: 15px;
  font-weight: 300;
  display: block;
  border: 1px solid #00336d;
  height: auto;
  width: 150px;
  padding: 10px;
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: center;
  margin-left: 1%;
  border-radius: 2px;
  transition: 0.5s ease-in-out;
  letter-spacing: 0.1em;
}
.maintext .heading .btns a:hover {
  background: #c29e5b;
  border: 1px solid #c29e5b;
}
.maintext .heading .btns .btn1 {
  background: #0050ac;
  border: 1px solid #0050ac;
}
.maintext .heading .btns .btn1:hover {
  background: #0050ac;
  border: 1px solid #0050ac;
  color: rgba(255, 255, 255, 0.95);
}


.entry img {
  width:100%;
}


.items {
  display: flex;
  align-items: center;
  width: fit-content;
  animation: carouselAnim 50s infinite alternate linear;
}

.entry {
  display: flex;
  align-items: center;
  flex-direction: column;
  position: relative;
  width: 310px;
  background: #fff;
  margin: 1em;
  border-radius: 10px;
  box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.5);
  height:150px;
}
.entry2 {
  display: flex;
  align-items: center;
  flex-direction: column;
  position: relative;
  width: 310px;
  background: #fff;
  margin: 1em;
  border-radius: 10px;
  box-shadow: 1px 1px 2px 0px rgba(0, 0, 0, 0.5);
  height:50px;
}

@media only screen and (max-width: 768px) {
  .items {
    animation: carouselAnim 35s infinite alternate linear;
  }
  
  @keyframes carouselAnim {
    from {
      transform: translate(0, 0);
    }
    to {
      transform: translate(calc(-100% + (2 * 300px)));
    }
  }
}

@keyframes carouselAnim {
  from {
    transform: translate(0, 0);
  }
  to {
    transform: translate(calc(-100% + (5 * 300px)));
  }
}

.el-carousel__item {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

</style>
