<template>
  <div id="container">
    <div class="summary">
      <h4>요약</h4>
      <table>
        <tr>
          <td>마감일</td>

          <td v-if="recruit.endDate != null">
            {{ recruit.endDate | yyyyMMdd }}
          </td>
          <td v-else>마감기한없음</td>
        </tr>
        <tr>
          <td>직무</td>
          <td>{{ recruit.jobType }}</td>
        </tr>
        <tr>
          <td>경력</td>
          <td>{{ recruit.career }}</td>
        </tr>
        <tr>
          <td>고용형태</td>
          <td>{{ recruit.empType }}</td>
        </tr>
        <tr>
          <td>급여</td>
          <td>{{ recruit.salary }}</td>
        </tr>
        <tr>
          <td>스킬</td>
          <td>{{ recruit.skill }}</td>
        </tr>
      </table>
    </div>
    <br /><br />
    <h4>기업이미지</h4>
    <img :src="'/api/images/company/' + recruit.img" alt="" class="img" />
    <div>
      <br />
      <h4>기업소개</h4>
      <p style="white-space: pre-wrap">{{ recruit.introduce }}</p>
    </div>
    <div>
      <br />
      <h4>주요업무</h4>
      <p style="white-space: pre-wrap">{{ recruit.jobDetail }}</p>
    </div>
    <div>
      <br />
      <h4>우대사항</h4>
      <p style="white-space: pre-wrap">{{ recruit.advantage }}</p>
    </div>
    <div>
      <br />
      <h4>기타사항</h4>
      <p style="white-space: pre-wrap">{{ recruit.etc }}</p>
    </div>
    <div>
      <br />
      <h4>문의처</h4>
      <p style="white-space: pre-wrap">{{ recruit.contact }}</p>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {
    return {
      recruit: {},
    };
  },
  created() {
    console.log(this.$route.query);
    this.$axios
      .post("/api/recruit/detail", {
        memberId: this.$store.state.userStore.id,
        recruitNo: this.$route.query.recruitNo,
      })
      .then((resp) => {
        console.log(resp);
        this.recruit = resp.data;
      });
  },
  filters: {
    // filter로 쓸 filter ID 지정
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";

      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);

      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();

      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }

      if (day < 10) {
        day = "0" + day;
      }

      // 최종 포맷 (ex - '2021-10-08')
      return year + " / " + month + " / " + day;
    },
  },
};
</script>
<style scoped>
#container {
  margin: 0 auto;
  text-align: left;
}
td {
  min-width: 100px;
  font-size: 1.15rem;
  padding-top: 8px;
}
tr :first-child {
  text-align: justify;
}
img {
  height: 200px;
}
h4 {
  margin-bottom: 5px;
  color: #0d6efd;
  font-weight: bold;
}
</style>
