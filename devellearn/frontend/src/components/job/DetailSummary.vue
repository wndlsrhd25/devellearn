<template>
  <div id="container">
    <b-modal
      id="updateRecruit"
      centered
      title="채용공고 수정"
      size="lg"
      hide-footer
    >
      <RecruitRegist
        :recruit="recruit"
        @reload="$emit('reload')"
      ></RecruitRegist>
    </b-modal>
    <div data-app>
      <v-dialog v-model="dialogDel" max-width="290">
        <DeleteModal @cancle="dialogDel = false" @del="del()"></DeleteModal>
      </v-dialog>
    </div>
    <div class="header">
      <h3>{{ recruit.title }}</h3>
      <p>
        {{ recruit.name }}
        <b-icon-star-fill class="h5" variant="success"></b-icon-star-fill>
        <span> {{ recruit.avgRate }}</span>
      </p>
    </div>
    <div class="btns">
      <template
        v-if="
          $store.getters['userStore/getEmpUser'] == 'true' &&
          recruit.comNo == $store.getters['userStore/getUserData'].comNo
        "
      >
        <a href="javascript:void(0)" @click="$bvModal.show('updateRecruit')">
          <span>수정</span>
        </a>
        |
        <a href="javascript:void(0)" @click="dialogDel = true">
          <span> 삭제 </span>
        </a>
      </template>
      <b-button
        class="btn-primary"
        variant="primary"
        @click="bookmark()"
        v-if="
          $store.getters['userStore/getEmpUser'] == 'false' &&
          $store.getters['userStore/getId'] != null &&
          $store.getters['userStore/getId'] != ''
        "
      >
        <b-icon
          :icon="recruit.bookmarkId == null ? 'bookmark' : 'bookmark-fill'"
        ></b-icon
        >저장</b-button
      >
    </div>
    <div class="rate">
      <div class="manjok">
        <h5>기업만족도</h5>
        <p>{{ recruit.avgRate }}</p>
        <p>업계평균</p>
        <p>{{ avg.avgRate }}</p>
      </div>
      <div class="manjok">
        <h5>급여만족도</h5>
        <p>{{ recruit.avgSalrate }}</p>
        <p>업계평균</p>
        <p>{{ avg.avgSalRate }}</p>
      </div>
      <div class="manjok">
        <h5>워라밸</h5>
        <p>{{ recruit.avgWlbrate }}</p>
        <p>업계평균</p>
        <p>{{ avg.avgWlbRate }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import RecruitRegist from "./RecruitRegist.vue";
import DeleteModal from "../common/DeleteModal.vue";
export default {
  props: {
    recruit: {},
  },
  data: function () {
    return {
      avg: {},
      dialogDel: false,
    };
  },
  watch: {
    recruit(newval, old) {
      if (this.recruit != null) {
        this.getavg();
      }
    },
  },
  created() {
    this.getavg();
  },
  methods: {
    del() {
      this.$axios
        .delete("/api/recruit/" + this.recruit.recruitNo)
        .then((resp) => {
          console.log(resp);
        })
        .catch((error) => {
          console.log(error);
        });
      this.dialogDel = false;
      this.$router.push({ path: "/jobMain" });
    },
    bookmark() {
      if (this.recruit.bookmarkId == null) {
        this.recruit.bookmarkId = this.$store.state.userStore.id;
        this.$axios
          .post("/api/likeRecruit", {
            recruitNo: this.recruit.recruitNo,
            memberId: this.$store.state.userStore.id,
          })
          .then((resp) => {
            console.log(resp);
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.recruit.bookmarkId = null;
        this.$axios
          .delete("/api/deleteRecruitLike", {
            data: {
              recruitNo: this.recruit.recruitNo,
              memberId: this.$store.state.userStore.id,
            },
          })
          .then((resp) => {
            console.log(resp);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    getavg() {
      if (this.recruit.jobType != null) {
        let joblist = this.recruit.jobType.split(",");
        console.log(joblist);
        this.$axios
          .post("/api/recruit/avg", {
            jobTypeList: joblist,
          })
          .then((resp) => {
            console.log(resp);
            this.avg = resp.data;
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
  },
  components: {
    RecruitRegist,
    DeleteModal,
  },
};
</script>

<style scoped>
.btn-primary {
  font-weight: bold;
  margin: 10px;
}
.manjok {
  width: auto;
}
.review {
  text-align: left;
  clear: both;
}
.rate {
  clear: both;
  display: flex;
  justify-content: space-around;
}
#container {
  width: 60%;
  margin: 0 auto;
}
.header {
  float: left;
  text-align: left;
}
.btns {
  float: right;
}
.b-icon.bi {
  vertical-align: -10px;
}
</style>
