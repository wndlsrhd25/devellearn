<template>
  <div id="navbar">
    <b-navbar
      toggleable="lg"
      type="dark"
      style="background-color: rgb(0, 0, 102)"
    >
      <template v-if="this.$store.state.isjob != 'true'">
        <router-link to="/" class="navbar-brand">Devellearn</router-link>
      </template>
      <template v-else>
        <router-link to="/jobMain" class="navbar-brand"
          >Devellearn</router-link
        ></template
      >
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <template v-if="this.$store.state.isjob != 'true'">
          <b-navbar-nav>
            <li class="nav-item">
              <router-link to="/lecture" class="nav-link"> 강의 </router-link>
            </li>
            <li class="nav-item">
              <router-link to="/community/mentor" class="nav-link">
                팀스터디
              </router-link>
            </li>
            <li class="nav-item">
              <router-link to="/community/qna" class="nav-link">
                커뮤니티
              </router-link>
            </li>
            <li class="nav-item">
              <router-link to="/blog" class="nav-link"> 블로그 </router-link>
            </li>
          </b-navbar-nav>
        </template>
        <template v-else>
          <b-navbar-nav>
            <li class="nav-item">
              <router-link to="/jobMain" class="nav-link">
                채용공고
              </router-link>
            </li>
            <li class="nav-item">
              <router-link to="/company" class="nav-link">
                기업정보
              </router-link>
            </li>
          </b-navbar-nav>
        </template>
        <b-navbar-nav
          class="ml-auto"
          v-if="
            $store.getters['userStore/getId'] != null &&
            $store.getters['userStore/getId'] != ''
          "
        >
          <img
            src="@/assets/cart.png"
            alt="cart"
            class="header-img"
            @click="$router.push('/lectureCartList').catch(() => {})"
          />
          <img
            src="@/assets/message.png"
            alt="message"
            class="header-img"
            @click="
              $router.push({ path: '/myPage/messageBox' }).catch(() => {})
            "
          />
          <img
            src="@/assets/user.png"
            alt="profile"
            class="header-img"
            @click="myPage()"
          />
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    myPage: function () {
      if (this.$store.getters["userStore/getEmpUser"] == "true") {
        this.$router.push({ path: "/companyPage/companyInfo" }).catch(() => {});
        return;
      }
      if (this.$store.getters["userStore/getUserData"].grade == "관리자") {
        this.$router
          .push({ path: "/adminPage/lectureMng/regist" })
          .catch(() => {});
        return;
      }

      this.$router.push({ path: "/myPage/dashBoard" }).catch(() => {});
    },
  },
};
</script>
<style scoped>
.header-img {
  width: 30px;
  height: 30px;
  float: right;
  cursor: pointer;
  margin-right: 10px;
}
#navbar {
  position: sticky;
  top: 46px;
  z-index: 100;
}
.navbar-brand {
  padding-left: 50px;
}
</style>
