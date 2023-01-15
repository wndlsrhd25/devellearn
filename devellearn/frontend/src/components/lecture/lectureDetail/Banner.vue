<template>
  <div id="ld_banner">
    <div class="lecture_info">
      <div class="lecture_img">
        <b-img
          :src="'/api/images/lecture/' + lecture.imgSrc"
          fluid
          alt="Fluid image"
        ></b-img>
      </div>
      <div class="lecture_intro">
        <!-- 강의 제목 -->
        <h2>{{ lecture.title }}</h2>
        <!-- 강사명 -->
        <p>개발고수 : {{ lecture.name }}</p>
        <!-- 카테고리 -->
        <p>분야 : {{ lecture.category }}</p>
        <!-- 난이도 -->
        <p>난이도 : {{ lecture.difficulty }}</p>
        <!-- 수강인원수 -->
        <p>{{ lecture.learnerNum }} 명이 수강중입니다!</p>
        <!-- 별점 -->
        <p>
          <b-icon-star-fill
            v-for="i in Math.round(lecture.avgScore)"
            :key="i + new Date()"
            variant="success"
          ></b-icon-star-fill>
          <b-icon-star
            v-for="j in 5 - Math.round(lecture.avgScore)"
            :key="j + new Date() * 2"
            variant="success"
          ></b-icon-star>
          ({{ lecture.avgScore }})
        </p>

        <!-- 태그 -->
        <p>{{ lecture.skillTag }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      no: "",
    };
  },
  props: ["lecture"],
};
</script>

<style scoped>
#ld_banner {
  width: 100%;
  background-color: skyblue;
  height: 340px;
  position: relative;
  padding-top: 40px;
}

.lecture_info {
  /* background-color: red; */
  width: 60%;
  height: 260px;
  margin: auto;
  display: flex;
  justify-content: center;
}

.lecture_img img {
  height: 100%;
}

.lecture_intro {
  /* background-color: green; */
  text-align: left;
  margin-left: 50px;
}

.lecture_intro h2 {
  margin-bottom: 40px;
}

.lecture_intro p {
  margin-bottom: 8px;
}
</style>
