<template>
  <div id="search">
    <div id="search-box">
      <label>
        <form action="return false">
          <input
            class="search-input"
            type="text"
            :placeholder="ph"
            :value="text"
            @input="input($event)"
            @keydown.enter="search($event)"
          />
          <button class="input-btn" type="button" @click="search($event)">
            <img src="@/assets/search.png" class="icon" alt="" />
          </button>
        </form>
      </label>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      text: "",
    };
  },
  props: {
    ph: { type: String },
  },
  methods: {
    input(e) {
      this.text = e.target.value;
    },
    search(e) {
      e.preventDefault();
      console.log(this.text);
      this.$emit("searchRecruit", this.text);
    },
    filter() {
      this.$emit("openFilter");
    },
  },
};
</script>

<style scoped>
.filter {
  margin: 10px;
  border: 1px solid gray;
  height: 40px;
  width: 70px;
  border-radius: 0.3rem;
  justify-content: center;
}
label {
  position: relative;
  border: 1px solid gray;
  border-radius: 5px;
  width: 100%;
}
.input-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  border: none;
  background-color: transparent;
}
.icon {
  width: 30px;
  height: 30px;
}
#search {
  margin: 30px auto;
  display: flex;
  justify-content: center;
}
.search-input {
  display: inline-flex;
  width: 95%;
  height: 40px;
  padding-left: 10px;
  border: none;
}
.search-input:focus {
  outline: none;
}
#search-box {
  box-sizing: border-box;
  margin: 10px;
  max-width: 800px;
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
