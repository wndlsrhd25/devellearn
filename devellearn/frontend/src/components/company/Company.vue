<template>
  <div id="companyMain">
    <h1>기업리스트</h1>

    <SearchBar
      :ph="ph"
      @searchRecruit="(text) => (searchCondition = text)"
      @openFilter="$bvModal.show('filter')"
    ></SearchBar>
    <div>
      <span v-for="(item, index) in skillList" class="filter-list" :key="index">
        {{ item }}
        <span class="delete" @click="deleteItem(index, skillList)">x</span>
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
        v-for="(item, index) in comSizeList"
        class="filter-list"
        :key="index"
      >
        {{ item }}
        <span class="delete" @click="deleteItem(index, comSizeList)">x</span>
      </span>
      <span v-if="avgRate > 0" class="filter-list">
        {{ avgRate }}점이상
        <span class="delete" @click="resetRate()">x</span>
      </span>
    </div>
    <b-modal id="filter" title="필터" size="xl" ref="filter">
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
        <b-form-group label="직무" class="label">
          <b-form-checkbox-group
            id="jobType"
            v-model="jobTypeList"
            :options="jList"
            name="jobType"
          ></b-form-checkbox-group>
        </b-form-group>
        <b-form-group label="규모" class="label">
          <b-form-checkbox-group
            id="comSize"
            v-model="comSizeList"
            :options="eList"
            name="comSize"
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
    <CompanyList
      :filterOn="filterOn"
      :skillList="skillList"
      :jobTypeList="jobTypeList"
      :comSizeList="comSizeList"
      :avgRatefilter="avgRate"
      :searchCondition="searchCondition"
    ></CompanyList>
  </div>
</template>
<script>
import SearchBar from "../common/SearchBar.vue";
import CompanyList from "./CompanyList.vue";
export default {
  components: { SearchBar, CompanyList },
  methods: {
    close() {
      this.$refs["filter"].hide();
    },
    ok() {
      this.filterOn = this.filterOn ? false : true;
      console.log(this.filterOn);
      this.$refs["filter"].hide();
    },
    searchRecruit(text) {
      console.log(text);
      this.searchCondition = text;
    },
    resetRate() {
      this.avgRate = 0;
      this.ok();
    },

    deleteItem(index, list) {
      list.splice(index, 1);
      this.ok();
    },
  },
  data() {
    return {
      filterOn: false,
      ph: "관심 기업을 검색하세요",
      searchCondition: "",
      limit: 5,
      skillList: [],
      jobTypeList: [],
      comSizeList: [],
      avgRate: 0,
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
        { text: "스타트업", value: "스타트업" },
        { text: "중소기업", value: "중소기업" },
        { text: "중견기업", value: "중견기업" },
        { text: "대기업", value: "대기업" },
      ],
    };
  },
};
</script>
<style scoped>
#companyMain {
  min-height: 80vh;
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
  background-color: lightskyblue;
  font-weight: bold;
  color: white;
  border: 8px solid lightskyblue;
  border-radius: 15px;
  margin-right: 5px;
}
#filter-modal {
  background-color: white;
  cursor: none;
  border-color: white;
  display: none;
}
</style>
