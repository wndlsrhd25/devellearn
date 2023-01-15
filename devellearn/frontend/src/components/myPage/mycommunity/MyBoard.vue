<template>
  <div>
    <h3 style="color: #00336d">나의 글</h3>
  <b-tabs content-class="mt-3">
    <b-tab title="작성한 글" active>
      <BoardList></BoardList>
    </b-tab>
    <b-tab title="작성한 댓글">
      <RepList></RepList>
    </b-tab>
  </b-tabs>
</div>
</template>

<script>
import BoardList from "./myBoard/BoardList.vue";
import RepList from "./myBoard/RepList.vue";
export default { components: { BoardList, RepList } };
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
