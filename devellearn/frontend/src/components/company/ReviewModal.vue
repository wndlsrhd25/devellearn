<template>
  <div>
    <div class="review-input">
      <b-row>
        <b-col sm="2">
          <span class="label">전직장/현직장</span>
        </b-col>
        <b-col sm="10">
          <b-form-select
            v-model="data.category"
            :options="categories"
            class="select-box"
          ></b-form-select>
        </b-col>
      </b-row>
      <br />
      <b-row>
        <b-col sm="2">
          <span class="label">총경력</span>
        </b-col>
        <b-col sm="10">
          <b-form-input
            v-model="data.career"
            placeholder="ex) 3년"
            class="select-box"
          ></b-form-input>
        </b-col> </b-row
      ><br />
      <b-row>
        <b-col sm="2">
          <span class="label">총평점</span>
        </b-col>
        <b-col sm="10">
          <b-form-rating
            v-model="data.rate"
            variant="warning"
            class="mb-2"
          ></b-form-rating>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="2">
          <span class="label">한줄평</span>
        </b-col>
        <b-col sm="10">
          <b-form-textarea
            id="content"
            v-model="data.content"
            placeholder="10자 이상"
            rows="4"
            max-rows="6"
          ></b-form-textarea>
        </b-col>
      </b-row>
      <br />
      <b-row>
        <b-col sm="2">
          <span class="label">장점</span>
        </b-col>
        <b-col sm="10">
          <b-form-textarea
            id="advantage"
            v-model="data.advantage"
            placeholder="20자 이상"
            rows="3"
            max-rows="6"
          ></b-form-textarea>
        </b-col> </b-row
      ><br />
      <b-row>
        <b-col sm="2"> <span class="label">단점</span> </b-col
        ><b-col sm="10">
          <b-form-textarea
            id="disadvantage"
            v-model="data.disadvantage"
            placeholder="20자 이상"
            rows="3"
            max-rows="6"
          ></b-form-textarea> </b-col
      ></b-row>
      <br />
      <b-row>
        <b-col sm="2">
          <span class="label">급여만족도</span>
        </b-col>
        <b-col sm="10">
          <b-form-rating
            v-model="data.salaryRate"
            variant="warning"
            class="mb-2"
          ></b-form-rating>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="2">
          <span class="label">워라밸</span>
        </b-col>
        <b-col sm="10">
          <b-form-rating
            v-model="data.wlbRate"
            variant="warning"
            class="mb-2"
          ></b-form-rating>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="2">
          <span class="label">사내분위기</span>
        </b-col>
        <b-col sm="10">
          <b-form-rating
            v-model="data.atmosphere"
            variant="warning"
            class="mb-2"
          ></b-form-rating>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="2">
          <span class="label">성장가능성</span>
        </b-col>
        <b-col sm="10">
          <b-form-rating
            v-model="data.potential"
            variant="warning"
            class="mb-2"
          ></b-form-rating>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="7">
          <span class="label">CEO에 대한 견해</span>
        </b-col>
        <b-col sm="5">
          <b-form-group v-slot="{ ariaDescribedby }">
            <b-form-radio-group
              id="aboutCeo"
              v-model="data.aboutCeo"
              :options="ceos"
              :aria-describedby="ariaDescribedby"
              name="aboutCeo"
              plain
            ></b-form-radio-group>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="7">
          <span class="label">이 기업을 추천하십니까?</span>
        </b-col>
        <b-col sm="5">
          <b-form-group v-slot="{ ariaDescribedby }">
            <b-form-radio-group
              id="recommend"
              v-model="data.recommend"
              :options="recommends"
              :aria-describedby="ariaDescribedby"
              name="recommend"
              plain
            ></b-form-radio-group>
          </b-form-group>
        </b-col>
      </b-row>
      <v-dialog v-model="dialog" width="450px">
        <EmptyContent @close="dialog = false"></EmptyContent>
      </v-dialog>
      <v-dialog v-model="lengthDialog" width="450px">
        <v-card>
          <v-card-title class="text-h5"> 글자수가 부족합니다 </v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="lengthDialog = false">
              확인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
    <div style="display: flex; justify-content: space-between">
      <b-button @click="$bvModal.hide('registReview')">취소</b-button>
      <b-button @click="registReview()">등록</b-button>
    </div>
  </div>
</template>
<script>
import EmptyContent from "../common/EmptyContent.vue";

export default {
  components: {
    EmptyContent,
  },
  props: {
    review: {},
    comNo: "",
    isUpdate: Boolean,
  },
  created() {
    if (this.review != null) {
      this.data = Object.assign({}, this.review);
    }
    this.data.writer = this.$store.state.userStore.id;
    this.data.comNo = this.comNo;
  },
  data: function () {
    return {
      data: {},

      categories: [
        { value: "재직자", text: "재직자" },
        { value: "퇴직자22년도", text: "퇴직자22년도" },
        { value: "퇴직자21년도", text: "퇴직자21년도" },
        { value: "퇴직자20년도", text: "퇴직자20년도" },
        { value: "퇴직자19년도", text: "퇴직자19년도" },
        { value: "퇴직자18년도 이전", text: "퇴직자18년도 이전" },
      ],
      ceos: [
        { value: "지지한다", text: "지지한다" },
        { value: "지지하지 않는다", text: "지지하지 않는다" },
      ],
      recommends: [
        { value: "추천한다", text: "추천한다" },
        { value: "추천하지 않는다", text: "추천하지 않는다" },
      ],
      dialog: false,
      lengthDialog: false,
    };
  },
  methods: {
    registReview() {
      if (!this.checkReview()) {
        return;
      }
      if (this.isUpdate) {
        this.$axios
          .put("/api/reviews", this.data)
          .then((resp) => {
            console.log(resp);
            this.$emit("reloadList");
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.$axios
          .post("/api/reviews", this.data)
          .then((resp) => {
            console.log(resp);
            this.$emit("reloadList");
          })
          .catch((error) => {
            console.log(error);
          });
      }
      this.$emit("reloadList");
      this.$bvModal.hide("registReview");
    },
    // 모든항목 유효성검사
    checkReview() {
      if (this.data.category == "" || this.data.category == null) {
        this.dialog = true;
        return false;
      } else if (this.data.career == "" || this.data.career == null) {
        this.dialog = true;
        return false;
      } else if (this.data.rate == 0 || this.data.rate == null) {
        this.dialog = true;
        return false;
      } else if (this.data.content == "" || this.data.content == null) {
        this.dialog = true;
        return false;
      } else if (this.data.advantage == "" || this.data.advantage == null) {
        this.dialog = true;
        return false;
      } else if (
        this.data.disadvantage == "" ||
        this.data.disadvantage == null
      ) {
        this.dialog = true;
        return false;
      } else if (this.data.salaryRate == "" || this.data.salaryRate == null) {
        this.dialog = true;
        return false;
      } else if (this.data.wlbRate == "" || this.data.wlbRate == null) {
        this.dialog = true;
        return false;
      } else if (this.data.atmosphere == "" || this.data.atmosphere == null) {
        this.dialog = true;
        return false;
      } else if (this.data.potential == "" || this.data.potential == null) {
        this.dialog = true;
        return false;
      } else if (this.data.aboutCeo == "" || this.data.aboutCeo == null) {
        this.dialog = true;
        return false;
      } else if (this.data.recommend == "" || this.data.recommend == null) {
        this.dialog = true;
        return false;
      } else if (this.data.content.replace(/(\s*)/g, "").length < 10) {
        this.lengthDialog = true;
        return false;
      } else if (this.data.advantage.replace(/(\s*)/g, "").length < 20) {
        this.lengthDialog = true;
        return false;
      } else if (this.data.disadvantage.replace(/(\s*)/g, "").length < 20) {
        this.lengthDialog = true;
        return false;
      }
      return true;
    },
  },
};
</script>
<style scoped></style>
