<template>
  <div class="list-3cards">
    <b-container fluid="lg">
      <b-row>
        <template v-for="item in completeLectureList">
          <template>
            <b-col cols="4" :key="item.lectureNo"
              ><b-card
                :title="item.title"
                :img-src="'/api/images/lecture/' + item.imgSrc"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem"
                class="lecture_card"
                @click="
                  $router
                    .push({
                      path: '/lectureDetail/intro',
                      query: { no: item.lectureNo },
                    })
                    .catch(() => {})
                "
              >
                <div>진도율 100%</div>

                <b-progress class="mt-2" :max="max">
                  <b-progress-bar
                    :value="100"
                    variant="success"
                  ></b-progress-bar>
                </b-progress>
              </b-card>
            </b-col>
          </template>
        </template>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  props: ["completeLectureList"],
  data() {
    return {
      max: 100,
    };
  },
};
</script>

<style scoped>
.card-img-top {
  height: 12rem;
  object-fit: cover;
}
.lecture_card {
  cursor: pointer;
  min-width: 250px;
}

.lecture_card p {
  margin-bottom: 2px;
  text-align: left;
}

.card-title {
  font-size: 22px;
  text-align: left;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 카드 템플릿 연습 */

.card {
  margin: 14px;
  position: relative;
  max-width: 250px;
  max-height: 350px;
  box-shadow: 0 10px 20px -2px gray;
}

.card-title {
  display: block;
  text-align: center;
  color: #fff;
  background-color: #6184a8;
  padding: 2%;
  border-top-right-radius: 4px;
  border-top-left-radius: 4px;
}
</style>
