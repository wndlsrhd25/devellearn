<template>
  <div>
    <div class="content">
      <!-- 사이드바 -->
      <SidebarCategory @search="category"></SidebarCategory>
      <div class="content-main">
        <!-- 검색영역 -->
        <SearchArea @search="search"></SearchArea>
        <!-- 강의목록  -->
        <CardListAll
          :searchItems="searchItems"
          :searchOn="searchOn"
        ></CardListAll>
      </div>
    </div>
  </div>
</template>

<script>
// "@/components/lecture/CardListAll.vue"
import SidebarCategory from "./SidebarCategory.vue";
import SearchArea from "./SearchArea.vue";
import CardListAll from "./CardListAll.vue";
export default {
  components: {
    SidebarCategory,
    SearchArea,
    CardListAll,
  },
  data: function () {
    return {
      searchItems: {},
      searchOn: false,
    };
  },

  methods: {
    search(searchItems) {
      console.log("부모컴포넌트로 넘어오는값", searchItems);
      console.log("부모컴포넌트로 넘어오는값", searchItems);
      console.log("부모컴포넌트로 넘어오는값", searchItems);
      console.log("부모컴포넌트로 넘어오는값", searchItems);
      console.log("부모컴포넌트로 넘어오는값", searchItems);
      console.log("부모컴포넌트로 넘어오는값", searchItems);
      this.searchItems = searchItems;
      this.searchOn = this.searchOn ? false : true;
    },
    category(category) {
      if (category == "전체강의") {
        category = "";
      }
      this.searchItems.category = category;
      this.searchOn = this.searchOn ? false : true;
    },
  },
};
</script>

<style scoped>
.content {
  min-height: 100vh;

  display: flex;
  width: 80%;
  margin: 20px auto;
}

.content-main {
  margin-left: 60px;
  width: 100%;
}
</style>
