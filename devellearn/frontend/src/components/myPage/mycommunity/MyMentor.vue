<template>
  <div>
    <h3 style="color: #00336d">나의 멘토링</h3>
    <b-tabs content-class="mt-3">
      <b-tab title="모집 멘토링" active>
        <MentorList></MentorList>
      </b-tab>
      <b-tab title="멘토링 관리">
        <MentorMember></MentorMember>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import MentorList from "./myMentor/MentorList.vue";
import MentorMember from "./myMentor/MentorMember.vue";
export default { components: { MentorList, MentorMember } };
</script>

<style scoped>
@font-face {
    font-family: 'GmarketSansMedium';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

*{
  font-family: GmarketSansMedium;
}
div{
  width: 900px;
}
</style>