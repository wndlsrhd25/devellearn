<template>
  <div>
    <h3 style="color: #00336d">나의 프로젝트</h3>
    <b-tabs content-class="mt-3">
      <b-tab title="참가중인 프로젝트" active>
        <MyProject></MyProject>
      </b-tab>
      <b-tab title="프로젝트 관리">
        <ProjectMember></ProjectMember>
      </b-tab>
      <b-tab title="나의 모집 프로젝트">
        <ProjectList></ProjectList>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import ProjectList from "./myProject/ProjectList.vue";
import ProjectMember from "./myProject/ProjectMember.vue";
import MyProject from "./myProject/MyProject.vue";
export default { components: { ProjectList, ProjectMember, MyProject } };
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
