<template>
  <div>
    <h3 style="color: #00336d">나의 모집 스터디</h3>
    <StudyList></StudyList>
  </div>
</template>

<script>
import StudyList from "./myStudy/StudyList.vue";
export default { components: { StudyList } };
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
