<template>
  <v-app>
    <div id="app">
      <!-- lectureLearning 컴포넌트에서는 헤더,네비바,푸터 컴포넌트 안보이게 -->
      <Header
        v-if="
          !$route.path.includes('/lectureLearning') &&
          !$route.path.includes('/lectureView')
        "
      ></Header>
      <!--<NavBar v-if="!$route.path.includes('/lectureLearning')"></NavBar>-->
      <div id="main">
        <router-view />
      </div>
      <Footer
        v-if="
          !$route.path.includes('/lectureLearning') &&
          !$route.path.includes('/lectureView')
        "
      ></Footer>
    </div>
  </v-app>
</template>
<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import Login from "@/components/login/Login.vue";
import NavBar from "@/components/common/NavBar.vue";
import { mapActions, mapGetters } from "vuex";
const userStore = "userStore";

export default {
  name: "App",
  computed: {
    ...mapGetters(userStore, ["getId"]),
  },
  components: {
    Header,
    Footer,
    Login,
    NavBar,
  },
};
</script>

<style scoped>
#app {
  font-family: GmarketSans;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  min-height: 100%;
}
@font-face {
  font-family: "GmarketSans";
  src: url("@/assets/font/GmarketSansTTFBold.ttf") format("truetype");
}
@font-face {
  font-family: "GmarketSans";
  src: url("@/assets/font/GmarketSansTTFLight.ttf") format("truetype");
}
@font-face {
  font-family: "GmarketSans";
  src: url("@/assets/font/GmarketSansTTFMedium.ttf") format("truetype");
}
</style>
<style>
.btn {
  color: white;
}
</style>
