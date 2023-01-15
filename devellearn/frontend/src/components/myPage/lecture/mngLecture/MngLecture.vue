<template>
  <div id="ml_wrapper">
    <!-- 헤더 -->
    <div class="ml_header">
      <h4>개설된 강의</h4>
      <div>
        <b-button @click="$router.push('/lectureRegist').catch(() => {})"
          >강의등록</b-button
        >
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<style scoped>
.ml_header {
  display: flex;
  justify-content: space-between;
}
.ml_header h4 {
  line-height: 20 0%;
}
</style>
