<template>
  <div class="searchBar">
    <div class="search">
      <div class="search-box">
        <label>
          <form action="">
            <input
              class="search-input"
              type="text"
              :placeholder="ph"
              :value="text"
              @input="input($event)"
              @keypress.enter="search($event)"
            />
          </form>
        </label>
        <b-button style="margin-left: 10px" @click="search($event)"
          >검색</b-button
        >
        <b-button
          variant="outline-info"
          style="margin-left: 10px"
          @click="tagSearchBar()"
        >
          태그검색</b-button
        >
        <b-icon
          icon="arrow-clockwise"
          font-scale="1.5"
          style="margin-left: 10px; margin-top: 7px; cursor: pointer"
          @click="reload()"
        ></b-icon>
      </div>
      <div class="search-box" v-if="show">
        <label>
          <form action="">
            <input
              class="search-input"
              type="text"
              placeholder="태그로 검색"
              :value="tag"
              @input="inputTag($event)"
              @keypress.enter="searchTag($event)"
            />
          </form>
        </label>
        <b-button style="margin-left: 10px" @click="searchTag($event)"
          >검색</b-button
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    ph: { type: String },
  },
  data() {
    return {
      text: "",
      tag: "",
      show: false,
    };
  },
  methods: {
    input(e) {
      this.text = e.target.value;
    },
    search(e) {
      e.preventDefault();
      console.log(this.text);
      this.$emit("searchBlog", this.text);
    },
    inputTag(e) {
      this.tag = e.target.value;
    },
    searchTag(e) {
      e.preventDefault();
      console.log(this.tag);
      this.$emit("searchTagBlog", this.tag);
    },
    tagSearchBar() {
      this.show = !this.show;
    },
    reload() {
      this.text = "";
      this.tag = "";
      this.$emit("reload");
    },
  },
};
</script>

<style scoped>
label {
  position: relative;
  border: 1px solid gray;
  border-radius: 5px;
  width: 100%;
  margin-bottom: 0px;
}
.input-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  border: none;
  background-color: transparent;
}
.search {
  margin: 0 auto;
  width: 1000px;
}
.searchBar {
  margin: 0px auto;
  width: 100%;
  height: 100px;
  margin-bottom: 20px;
}
.search-input {
  display: inline-flex;
  width: 100%;
  height: 40px;
  padding-left: 20px;
  border: none;
}
.search-input:focus {
  outline: none;
}
.search-box {
  box-sizing: border-box;
  margin: 10px;
  max-width: 1000px;
  width: 100%;
  display: flex;
  height: 40px;
  justify-content: center;
  margin-bottom: 20px;
}
</style>
