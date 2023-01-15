<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('goLogin')">취소</b-button>
      <b-button @click="goLogin()">로그인하기</b-button>
    </div>
  </div>
</template>
<script>
export default {
  props: {},
  methods: {
    goLogin: function () {
      this.$router.push({ path: "/login" }).catch(() => {});
    },
  },
};
</script>

<!-- 쓰는 법

1. 버튼 만들어주기 (a태그)
    <a href="javascript:viod(0)" @click="goLogin()">
    <span> go LOGIN </span>
    </a>

2. 모달창 불러오기
      <b-modal
        id="goLogin"
        centered
        title="로그인필요"
        size="m"
        footerClass="p-2 border-top-0"
      >
        <LoginNeeds :report="report"> </LoginNeeds>
        <template #modal-footer="{ hide }">
          <b-button @click="hide('forget')" style="display: none"></b-button>
        </template>
      </b-modal>

3. 스크립트 안에 import 해주기
import LoginNeeds from "../common/LoginNeeds.vue";  

4. 컴포넌트 안에 써주기
    components: {
      SideBar, MsgModal},

4. 클릭 함수 써주기
    goLogin() {
      this.$bvModal.show("goLogin");
    },
    
-->
<style scoped></style>
