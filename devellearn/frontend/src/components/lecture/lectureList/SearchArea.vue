<template>
  <div>
    <!-- 강의등록버튼, 강의검색창 -->
    <div id="top-first">
      <!-- 검색창 -->
      <div class="input-group">
        <input
          type="search"
          placeholder="강의를 검색하세요."
          :value="searchItems.text"
          @input="input($event)"
          @keypress.enter="searchOn = searchOn ? false : true"
        />
        <b-button type="button" @click="searchOn = searchOn ? false : true"
          >Search</b-button
        >
      </div>
      <!-- 강의등록 버튼 -->
      <div v-if="$store.getters['userStore/getUserData'] != null">
        <v-btn
          color="info"
          @click="$router.push('/lectureRegist').catch(() => {})"
          v-if="$store.getters['userStore/getUserData'].grade == '개발고수'"
          >강의등록</v-btn
        >
      </div>
    </div>

    <!-- 기술검색창, 정렬순서 -->
    <div id="top-second">
      <v-btn
        @click="openTag ? (openTag = false) : (openTag = true)"
        color="info"
        >기술검색</v-btn
      >
      <div class="sort-box">
        <span class="order" @click="setOrder('learnerNum desc')">인기순</span>
        <span class="order" @click="setOrder('lecture_no desc')">최신순</span>
        <span class="order" @click="setOrder('avgScore desc')">평점순</span>
        <span class="order" @click="setOrder('price')">가격순</span>
      </div>
    </div>
    <div id="top-third" v-if="openTag">
      <span
        v-for="(tag, i) of searchItems.selectTags"
        :key="i"
        class="selectTag"
        >{{ tag }}&nbsp;<span class="delete" @click="deleteTag(tag, i)"
          >x</span
        ></span
      >
    </div>
    <div id="top-third" v-if="openTag">
      <span
        v-for="(tag, i) of tags"
        :key="i"
        class="tag"
        @click="addTag(tag, i)"
        >{{ tag }}&nbsp;</span
      >
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      tags: [],
      openTag: false,
      searchOn: false,
      searchItems: { selectTags: [] },
    };
  },
  created() {
    this.getTags();
  },
  watch: {
    searchOn() {
      console.log("검색상태", this.searchItems);
      this.$emit("search", this.searchItems);
    },
  },
  methods: {
    addTag(tag, i) {
      this.searchItems.selectTags.push(tag);
      this.tags.splice(i, 1);
      this.searchOn = this.searchOn ? false : true;
    },
    deleteTag(tag, i) {
      this.tags.push(tag);
      this.searchItems.selectTags.splice(i, 1);
      this.searchOn = this.searchOn ? false : true;
    },
    setOrder(order) {
      this.searchItems.order = order;
      this.searchOn = this.searchOn ? false : true;
    },
    input(e) {
      this.searchItems.text = e.target.value;
    },
    search(e) {
      e.preventDefault();
      console.log(this.text);
      if (this.text != "") {
        this.$emit("searchRecruit", this.text);
        this.text = "";
      }
    },
    getTags() {
      this.$axios
        .get("/api/lecture/tags")
        .then((resp) => {
          console.log(resp.data);
          this.tags = resp.data.tags;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
#top-first {
  padding: 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid lightgray;
  display: flex;
  justify-content: space-between;
}

#top-second {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
}
#top-third {
  margin-bottom: 20px;
  text-align: left;
}

#top-second input {
  border: 1px solid lightgray;
  width: 150px;
}

/* 추천순. 인기순... */
.sort-box span {
  margin: 10px;
}

.sort-box .order:hover {
  border-bottom: 2px solid black;
}

/* search bar */
.input-group {
  width: 300px;
}
.input-group input {
  height: 38px;
  border: 1px solid lightgray;
  border-radius: 7px 0px 0px 7px;
  padding: 5px;
}
.delete {
  cursor: pointer;
}
.selectTag {
  background-color: #81c784;
  color: white;
  border: 8px solid #81c784;
  border-radius: 15px;
  margin-right: 5px;
}
.tag {
  background-color: #e0e0e0;
  color: white;
  border: 8px solid #e0e0e0;
  border-radius: 15px;
  margin-right: 5px;
  display: inline-block;
  margin-bottom: 5px;
}
.order {
  cursor: pointer;
}
</style>
