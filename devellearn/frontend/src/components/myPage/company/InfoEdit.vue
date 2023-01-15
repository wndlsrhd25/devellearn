<template>
  <div>
    <form id="companyData">
      <v-card>
        <v-card-title>
          <span class="text-h5">기업정보 수정</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-avatar size="200" rounded="0" style="background-color: gray">
              <template v-if="image == null || image == ''">
                <v-img :src="'/api/images/company/' + company.img" />
              </template>
              <v-img :src="company.img" v-else />
            </v-avatar>

            <v-file-input
              :rules="rules"
              name="image"
              v-model="image"
              accept="image/png, image/jpeg, image/bmp"
              prepend-icon="mdi-camera"
              label="기업로고 이미지"
              @click:clear="image = ''"
              @change="Preview_image()"
            ></v-file-input>
            <b-form-group label="직종" class="label">
              <b-form-checkbox-group
                v-model="jobTypeList"
                :options="jList"
              ></b-form-checkbox-group>
            </b-form-group>
            <br />
            <v-row>
              <v-textarea
                outlined
                auto-grow
                no-resize
                name="introduce"
                label="기업소개"
                :value="company.introduce"
                style="white-space: pre-wrap"
              ></v-textarea>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="close()"> 닫기 </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="edit()"> 수정 </v-btn>
        </v-card-actions>
      </v-card>
    </form>
  </div>
</template>
<script>
export default {
  props: ["company"],
  data: () => ({
    rules: [(value) => !value || value.size < 5120000 || "사진크기는 5MB미만"],
    jobTypeList: [],
    jList: [
      {text: "DBA", value: "DBA"},
      {text: "ERP", value: "ERP"},
      {text: "QA", value: "QA"},
      {text: "게임개발", value: "게임개발"},
      {text: "네트워크/보안/운영", value: "네트워크/보안/운영"},
      {text: "데이터분석", value: "데이터분석"},
      {text: "모바일앱개발", value: "모바일앱개발"},
      {text: "소프트웨어아키텍트", value: "소프트웨어아키텍트"},
      {text: "소프트웨어엔지니어", value: "소프트웨어엔지니어"},
      {text: "시스템엔지니어", value: "시스템엔지니어"},
      {text: "웹개발", value: "웹개발"},
      {text: "프로젝트매니저", value: "프로젝트매니저"},
      {text: "웹퍼블리셔", value: "웹퍼블리셔"},
      {text: "하드웨어엔지니어", value: "하드웨어엔지니어"},
    ],
    image: [],
  }),
  created() {
    let arr = this.company.jobType.split(",");
    console.log(arr);
    for (let item of arr) {
      this.jobTypeList.push(item.trim());
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    edit() {
      let job = "";
      for (let str of this.jobTypeList) {
        job += str + ", ";
        console.log(job);
      }
      this.company.jobType = job.substring(0, job.length - 2);
      const formData = new FormData(document.getElementById("companyData"));
      formData.append("jobType", this.company.jobType);
      formData.append("comNo", this.company.comNo);
      formData.append("img", this.company.img);
      this.$axios
        .put("/api/company", formData)
        .then((resp) => {
          console.log(resp.data);
          this.$emit("reload");
          this.close();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    Preview_image() {
      this.company.img = URL.createObjectURL(this.image);
    },
  },
};
</script>
<style scoped></style>
