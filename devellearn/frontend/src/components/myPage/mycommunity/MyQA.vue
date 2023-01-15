<template>
  <div>
    <h3 style="color: #00336d">나의 QnA</h3>
    <b-tabs content-class="mt-3">
      <b-tab title="나의 질문글" active>
        <QnaList></QnaList>
      </b-tab>
      <b-tab title="나의 답변글">
        <RepList></RepList>
      </b-tab>
    </b-tabs>
  </div>
</template>

<script>
import QnaList from "./myQA/QnaList.vue";
import RepList from "./myQA/RepList.vue";
export default { components: { QnaList, RepList } };
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
