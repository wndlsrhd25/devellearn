<template>
  <div id="header">
    <!-- Header -->
    <header>
      <!-- Header desktop -->
      <div class="container-menu-desktop">
        <!-- Topbar -->
        <div class="top-bar">
          <div class="content-topbar flex-sb-m h-full container">
            <div class="left-top-bar">
              <template
                v-if="
                  $store.getters['userStore/getEmpUser'] == 'false' ||
                  $store.getters['userStore/getEmpUser'] == null
                "
              >
                <a
                  style="cursor: pointer; font-size: 1rem; color: white"
                  @click="main()"
                  >EDUCATION</a
                >&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
              </template>
              <a
                style="cursor: pointer; font-size: 1rem; color: white"
                @click="job()"
              >
                RECRUIT</a
              >
            </div>

            <div class="right-top-bar flex-w h-full">
              <a
                class="flex-c-m trans-04 p-lr-25"
                style="font-weight: bold; color: white; cursor: none"
                v-if="
                  $store.getters['userStore/getId'] != null &&
                  $store.getters['userStore/getId'] != ''
                "
              >
                {{ $store.getters["userStore/getId"] }}님 환영합니다!
              </a>

              <template
                v-if="
                  $store.getters['userStore/getId'] == '' ||
                  $store.getters['userStore/getId'] == null
                "
              >
                <a
                  class="flex-c-m trans-04 p-lr-25"
                  style="cursor: pointer; font-weight: bold; color: white"
                  @click="login()"
                  >로그인</a
                >
                <a
                  class="flex-c-m trans-04 p-lr-25"
                  style="cursor: pointer; font-weight: bold; color: white"
                  @click="join()"
                  >회원가입</a
                >
              </template>
              <template v-else>
                <a
                  class="flex-c-m trans-04 p-lr-25"
                  style="cursor: pointer; font-weight: bold; color: white"
                  @click="logout()"
                  >로그아웃</a
                >
              </template>

              <a
                style="cursor: pointer; font-weight: bold; color: white"
                @click="masterReq()"
                class="flex-c-m trans-04 p-lr-25"
              >
                고수등록
              </a>
            </div>
          </div>
        </div>

        <div class="wrap-menu-desktop">
          <nav class="limiter-menu-desktop container">
            <!-- 로그인이 필요합니다 모달창 -->
            <!-- <v-dialog v-model="dialog" width="450px">
              <NeedLogin @close="dialog = false"></NeedLogin>
            </v-dialog> -->

            <!-- Logo desktop -->
            <template
              v-if="
                this.$store.state.isjob == 'false' ||
                this.$store.state.isjob == null
              "
            >
              <router-link to="/" class="navbar-brand logo">
                <img
                  src="../../../public/images/icons/logo.png"
                  width="70%"
                  alt="IMG-LOGO"
                />
              </router-link>
            </template>
            <template v-else>
              <router-link to="/jobMain" class="navbar-brand logo">
                <img
                  src="../../../public/images/icons/logo.png"
                  width="70%"
                  alt="IMG-LOGO"
                />
              </router-link>
            </template>
            <v-spacer></v-spacer>
            <!-- Menu desktop -->
            <div class="menu-desktop">
              <ul class="main-menu">
                <template v-if="this.$store.state.isjob != 'true'">
                  <li>
                    <router-link to="/lecture" class="nav-link">
                      <a>E-Learning</a>
                    </router-link>
                  </li>

                  <li>
                    <router-link to="/community/mentor" class="nav-link">
                      <a>TeamStudy</a>
                    </router-link>
                  </li>

                  <li>
                    <router-link to="/community/qna" class="nav-link">
                      <a>Community</a>
                    </router-link>
                  </li>

                  <li>
                    <router-link to="/blog" class="nav-link">
                      <a>Blog</a>
                    </router-link>
                  </li>
                </template>
                <template v-else>
                  <li>
                    <router-link to="/jobMain" class="nav-link">
                      <a>RECRUIT</a>
                    </router-link>
                  </li>
                  <li>
                    <router-link to="/company" class="nav-link">
                      <a>COMPANY</a>
                    </router-link>
                  </li>
                </template>
              </ul>
            </div>

            <!-- Icon header -->
            <div class="wrap-icon-header flex-r-m">
              <template
                v-if="
                  $store.getters['userStore/getEmpUser'] == 'false' ||
                  $store.getters['userStore/getEmpUser'] == null
                "
              >
                <div
                  @click="goMessage()"
                  class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11"
                >
                  <i class="zmdi zmdi-comments"></i>
                </div>

                <div
                  @click="goCart()"
                  class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11"
                >
                  <i class="zmdi zmdi-shopping-cart"></i>
                </div>
              </template>

              <div
                @click="myPage()"
                class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11"
              >
                <i class="zmdi zmdi-account"></i>
              </div>
            </div>
          </nav>
        </div>
      </div>

      <!-- Header Mobile -->
      <div class="wrap-header-mobile">
        <!-- Logo moblie -->
        <div class="logo-mobile">
          <template v-if="this.$store.state.isjob == 'false'">
            <router-link to="/" class="navbar-brand logo">
              <img
                src="../../../public/images/icons/logo.png"
                width="100%"
                alt="IMG-LOGO"
              />
            </router-link>
          </template>
          <template v-else>
            <router-link to="/jobMain" class="navbar-brand logo">
              <img
                src="../../../public/images/icons/logo.png"
                width="100%"
                alt="IMG-LOGO"
              />
            </router-link>
          </template>
        </div>

        <!-- Icon header -->
        <div class="wrap-icon-header flex-r-m">
          <template
            v-if="
              $store.getters['userStore/getEmpUser'] == 'false' ||
              $store.getters['userStore/getEmpUser'] == null
            "
          >
            <div
              @click="goMessage()"
              class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11"
            >
              <i class="zmdi zmdi-comments"></i>
            </div>

            <div
              @click="goCart()"
              class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11"
            >
              <i class="zmdi zmdi-shopping-cart"></i>
            </div>
          </template>

          <div
            @click="myPage()"
            class="icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11"
          >
            <i class="zmdi zmdi-account"></i>
          </div>
        </div>

        <!-- Button show menu -->
        <div class="btn-show-menu-mobile hamburger hamburger--squeeze">
          <span class="hamburger-box">
            <span class="hamburger-inner"
              ><ul class="main-menu">
                <template v-if="this.$store.state.isjob != 'true'">
                  <li>
                    <router-link to="/lecture" class="nav-link">
                      <a>E-Learning</a>
                    </router-link>
                  </li>

                  <li>
                    <router-link to="/community/mentor" class="nav-link">
                      <a>TeamStudy</a>
                    </router-link>
                  </li>

                  <li>
                    <router-link to="/community/qna" class="nav-link">
                      <a>Community</a>
                    </router-link>
                  </li>

                  <li>
                    <router-link to="/blog" class="nav-link">
                      <a>Blog</a>
                    </router-link>
                  </li>
                </template>
                <template v-else>
                  <li>
                    <router-link to="/jobMain" class="nav-link">
                      <a>RECRUIT</a>
                    </router-link>
                  </li>
                  <li>
                    <router-link to="/company" class="nav-link">
                      <a>COMPANY</a>
                    </router-link>
                  </li>
                </template>
              </ul></span
            >
          </span>
        </div>
      </div>

      <!-- Menu Mobile -->
      <div class="menu-mobile">
        <ul class="topbar-mobile">
          <li>
            <div class="left-top-bar">
              Free shipping for standard order over $100
            </div>
          </li>

          <li>
            <div class="right-top-bar flex-w h-full">
              <a href="#" class="flex-c-m p-lr-10 trans-04"> Help & FAQs </a>

              <a href="#" class="flex-c-m p-lr-10 trans-04"> My Account </a>

              <a href="#" class="flex-c-m p-lr-10 trans-04"> EN </a>

              <a href="#" class="flex-c-m p-lr-10 trans-04"> USD </a>
            </div>
          </li>
        </ul>

        <ul class="main-menu-m">
          <li>
            <a href="index.html">Home</a>
            <ul class="sub-menu-m">
              <li><a href="index.html">Homepage 1</a></li>
              <li><a href="home-02.html">Homepage 2</a></li>
              <li><a href="home-03.html">Homepage 3</a></li>
            </ul>
            <span class="arrow-main-menu-m">
              <i class="fa fa-angle-right" aria-hidden="true"></i>
            </span>
          </li>

          <li>
            <a href="product.html">E-Learning</a>
          </li>

          <li>
            <a href="shoping-cart.html" class="label1 rs1" data-label1="hot"
              >TeamStudy</a
            >
          </li>

          <li>
            <a href="blog.html">Community</a>
          </li>

          <li>
            <a href="about.html">Blog</a>
          </li>

          <li>
            <a href="contact.html">Contact</a>
          </li>
        </ul>
      </div>

      <!-- Modal Search -->
      <div class="modal-search-header flex-c-m trans-04 js-hide-modal-search">
        <div class="container-search-header">
          <button
            class="flex-c-m btn-hide-modal-search trans-04 js-hide-modal-search"
          >
            <img
              src="../../../public/images/icons/icon-close2.png"
              alt="CLOSE"
            />
          </button>

          <form class="wrap-search-header flex-w p-l-15">
            <button class="flex-c-m trans-04">
              <i class="zmdi zmdi-search"></i>
            </button>
            <input
              class="plh3"
              type="text"
              name="search"
              placeholder="Search..."
            />
          </form>
        </div>
      </div>
    </header>
  </div>
</template>

<script>
import NeedLogin from "./NeedLogin.vue";
export default {
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    login() {
      this.$router.push({path: "/login"}).catch(() => {});
    },
    main() {
      this.$store.commit("setIsJobFalse");
      this.$router.push({path: "/"}).catch(() => {});
    },
    job() {
      this.$store.commit("setIsJobTrue");
      this.$router.push({path: "/jobMain"}).catch(() => {});
    },
    join() {
      this.$router.push({path: "/join"}).catch(() => {});
    },
    logout() {
      this.$axios.get("/api/logout");
      this.$store.commit("userStore/logout");
      this.$store.commit("userStore/setEmpFalse");
      this.$store.commit("setIsJobFalse");
      this.$router.push({path: "/"}).catch(() => {});
    },
    myPage() {
      if (!this.checkLogin()) {
        //this.dialog = true;
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({path: "/login"}).catch(() => {});
          }
        });
        return;
      }
      if (this.$store.getters["userStore/getEmpUser"] == "true") {
        this.$router.push({path: "/companyPage/companyInfo"}).catch(() => {});
        return;
      }
      if (this.$store.getters["userStore/getUserData"].grade == "관리자") {
        this.$router
          .push({path: "/adminPage/lectureMng/regist"})
          .catch(() => {});
        return;
      }
      this.$router.push({path: "/myPage/dashBoard"}).catch(() => {});
    },
    goMessage() {
      if (!this.checkLogin()) {
        // this.dialog = true;
        // return;
        Swal.fire({
          title: "로그인이 필요한 서비스입니다",
          text: "로그인하시겠습니까?",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({path: "/login"}).catch(() => {});
          }
        });
        return;
      }
      this.$router.push({path: "/myPage/messageBox"}).catch(() => {});
    },
    goCart() {
      if (!this.checkLogin()) {
        this.dialog = true;
        return;
      }
      this.$router.push("/lectureCartList").catch(() => {});
    },
    checkLogin() {
      if (
        this.$store.getters["userStore/getId"] == null ||
        this.$store.getters["userStore/getId"] == ""
      ) {
        return false;
      } else {
        return true;
      }
    },
    masterReq() {
      if (!this.checkLogin()) {
        this.dialog = true;
        return;
      }
      this.$router.push({path: "/masterReq"}).catch(() => {});
    },
  },
  components: {NeedLogin},
};
</script>
<style scoped>
#header {
  margin-bottom: 124px;
}
.wrap-icon-header {
  flex-grow: 0;
}
.wrap-menu-desktop {
  background-color: rgb(255, 255, 255);
  z-index: 100;
}
</style>
