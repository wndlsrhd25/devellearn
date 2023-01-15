<template>
  <div id="jobMain">
    <BannerVue :items="items"></BannerVue>

    <b-modal
      id="updateRecruit"
      centered
      title="채용공고 등록"
      size="lg"
      hide-footer
    >
      <RecruitRegist @reload="reloadList()"></RecruitRegist>
    </b-modal>
    <b-modal id="filter" title="필터" size="lg" ref="filter">
      <div>
        <label class="label"
          >스킬
          <b-form-tags
            input-id="tags-basic"
            v-model="skillList"
            remove-on-delete
            tag-pills
            tag-variant="primary"
            separator=" ,;"
            :limit="limit"
          ></b-form-tags>
        </label>
        <b-form-group label="경력" class="label">
          <b-form-checkbox-group
            id="career"
            v-model="careerList"
            :options="cList"
            name="career"
          ></b-form-checkbox-group>
        </b-form-group>
        <b-form-group label="직무" class="label">
          <b-form-checkbox-group
            id="jobType"
            v-model="jobTypeList"
            :options="jList"
            name="jobType"
          ></b-form-checkbox-group> </b-form-group
        ><b-form-group label="고용형태" class="label">
          <b-form-checkbox-group
            id="hire"
            v-model="empTypeList"
            :options="eList"
            name="hire"
          ></b-form-checkbox-group>
        </b-form-group>
        <b-form-group label="평점" class="label">
          <b-form-rating v-model="avgRate" class="rating"></b-form-rating>이상
        </b-form-group>
      </div>
      <template slot="modal-footer">
        <b-button size="sm" @click="close()"> 취소 </b-button>
        <b-button size="sm" variant="primary" @click="ok()">
          필터적용
        </b-button>
      </template>
    </b-modal>
    <SearchBar
      :ph="ph"
      :reset="reset"
      @resetDone="reset = false"
      @searchRecruit="(text) => (searchCondition = text)"
      @openFilter="$bvModal.show('filter')"
    ></SearchBar>
    <div class="filters">
      <span v-for="(item, index) in skillList" class="filter-list" :key="index">
        {{ item }}
        <span class="delete" @click="deleteItem(index, skillList)">x</span>
      </span>
      <span
        v-for="(item, index) in careerList"
        class="filter-list"
        :key="index"
      >
        {{ item }}
        <span class="delete" @click="deleteItem(index, careerList)">x</span>
      </span>
      <span
        v-for="(item, index) in jobTypeList"
        class="filter-list"
        :key="index"
      >
        {{ item }}
        <span class="delete" @click="deleteItem(index, jobTypeList)">x</span>
      </span>
      <span
        v-for="(item, index) in empTypeList"
        class="filter-list"
        :key="index"
      >
        {{ item }}
        <span class="delete" @click="deleteItem(index, empTypeList)">x</span>
      </span>
      <span v-if="avgRate > 0" class="filter-list">
        {{ avgRate }}점이상
        <span class="delete" @click="resetRate()">x</span>
      </span>
    </div>
    <br />
    <div
      class="btn-line"
      v-if="$store.getters['userStore/getEmpUser'] == 'true'"
    >
      <b-button class="ml-auto" variant="info" v-b-modal.updateRecruit
        >채용공고등록</b-button
      >
    </div>

    <JobList
      :filterOn="filterOn"
      :skillList="skillList"
      :careerList="careerList"
      :jobTypeList="jobTypeList"
      :empTypeList="empTypeList"
      :avgRatefilter="avgRate"
      :searchCondition="searchCondition"
      @clearAll="clearAll()"
    />
  </div>
</template>
<script>
import BannerVue from "@/components/mainPage/BannerVue.vue";
import SearchBar from "@/components/common/SearchBar.vue";
import JobList from "@/components/job/JobList.vue";
import RecruitRegist from "@/components/job/RecruitRegist.vue";

export default {
  data() {
    return {
      items: [
        { src: "hwahae.png" },
        { src: "inflab.png" },
        { src: "nhn.png" },
        { src: "todayhome.png" },
      ],
      filterOn: false,
      ph: "채용공고를 검색하세요",
      searchCondition: "",
      limit: 5,
      skillList: [],
      careerList: [],
      jobTypeList: [],
      empTypeList: [],
      avgRate: 0,
      reset: false,
      cList: [
        { text: "신입", value: "신입" },
        { text: "주니어(1~3년)", value: "주니어(1~3년)" },
        { text: "미들(4~8년)", value: "미들(4~8년)" },
        { text: "시니어(9년 이상)", value: "시니어(9년 이상)" },
      ],
      jList: [
        { text: "DBA", value: "DBA" },
        { text: "ERP", value: "ERP" },
        { text: "QA", value: "QA" },
        { text: "게임개발", value: "게임개발" },
        { text: "네트워크/보안/운영", value: "네트워크/보안/운영" },
        { text: "데이터분석", value: "데이터분석" },
        { text: "모바일앱개발", value: "모바일앱개발" },
        { text: "소프트웨어아키텍트", value: "소프트웨어아키텍트" },
        { text: "소프트웨어엔지니어", value: "소프트웨어엔지니어" },
        { text: "시스템엔지니어", value: "시스템엔지니어" },
        { text: "웹개발", value: "웹개발" },
        { text: "프로젝트매니저", value: "프로젝트매니저" },
        { text: "웹퍼블리셔", value: "웹퍼블리셔" },
        { text: "하드웨어엔지니어", value: "하드웨어엔지니어" },
      ],
      eList: [
        { text: "정규직", value: "정규직" },
        { text: "계약직", value: "계약직" },
        { text: "프리랜서", value: "프리랜서" },
      ],
    };
  },
  methods: {
    reloadList() {
      this.filterOn = this.filterOn ? false : true;
    },
    searchRecruit(text) {
      console.log(text);
      this.searchCondition = text;
    },
    resetRate() {
      this.avgRate = 0;
      this.ok();
    },
    filterModal() {
      document.querySelector("#filter-modal").click();
    },
    deleteItem(index, list) {
      list.splice(index, 1);
      this.ok();
    },
    close() {
      this.$refs["filter"].hide();
    },
    ok() {
      this.filterOn = this.filterOn ? false : true;
      console.log(this.filterOn);
      this.$refs["filter"].hide();
    },
    clearAll() {
      this.searchCondition = "";
      this.skillList = [];
      this.careerList = [];
      this.jobTypeList = [];
      this.empTypeList = [];
      this.avgRatefilter = 0;
      this.reset = true;
      this.ok();
    },
  },

  components: { BannerVue, SearchBar, JobList, RecruitRegist },
};
</script>
<style scoped>
.btn-line {
  display: flex;
  max-width: 1250px;
  margin: 0 auto;
}
.modal-footer {
  justify-content: space-between;
}
.rating {
  width: 50%;
  display: inline-block;
}
.label {
  font-weight: bold;
}
.delete {
  cursor: pointer;
}
.filter-list {
  display: inline-block;
  background-color: lightskyblue;
  font-weight: bold;
  color: white;
  border: 8px solid lightskyblue;
  border-radius: 15px;
  margin-right: 5px;
  margin-bottom: 5px;
}
.filters {
  width: 60%;
  margin: 0 auto;
}

#jobMain {
  min-height: 90vh;
}
</style>
