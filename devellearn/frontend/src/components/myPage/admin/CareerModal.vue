<template>
  <div>
    <form id="companyData">
      <v-card>
        <v-card-title>
          <span class="text-h5">경력인증신청</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <b-input-group>
              <v-row>
                <v-col>
                  <b-input-group-text
                    >회사명 : {{ career.company }}</b-input-group-text
                  >
                </v-col>
                <v-col>
                  <b-input-group-text
                    >부서 : {{ career.dept }}</b-input-group-text
                  >
                </v-col>
                <v-col>
                  <b-input-group-text
                    >직책 : {{ career.position }}</b-input-group-text
                  >
                </v-col>
                <v-col>
                  <b-input-group-text
                    >업무 : {{ career.task }}</b-input-group-text
                  >
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <b-input-group-text
                    >근무형태 : {{ career.jobType }}</b-input-group-text
                  >
                </v-col>
                <v-col>
                  <b-input-group-text
                    >근무기간 : {{ career.jobPeriod }}</b-input-group-text
                  >
                </v-col>
              </v-row>
            </b-input-group>

            <b-input-group>
              <template #prepend>
                <b-input-group-text>처리하기</b-input-group-text>
              </template>
              <b-form-select
                v-model="status"
                :options="statusList"
              ></b-form-select>
            </b-input-group>
            <div v-if="status == '반려'">
              <b-input-group>
                <template #prepend>
                  <b-input-group-text>반려사유 입력</b-input-group-text>
                </template>
              </b-input-group>
              <br />
              <v-row>
                <v-textarea
                  outlined
                  auto-grow
                  no-resize
                  name="rejection"
                  v-model="rejection"
                  style="white-space: pre-wrap"
                ></v-textarea>
              </v-row>
            </div>
            <v-card-actions>
              <v-btn color="blue darken-1" text @click="close()"> 닫기 </v-btn>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="edit()"> 완료 </v-btn>
            </v-card-actions>
          </v-container>
        </v-card-text>
      </v-card>
    </form>
  </div>
</template>
<script>
export default {
  props: ["career"],
  data: () => ({
    statusList: [
      {text: "승인", value: "승인"},
      {text: "반려", value: "반려"},
    ],

    rejection: "",
    status: "",
  }),

  methods: {
    close() {
      this.rejection = "";
      this.$emit("close");
    },
    edit() {
      this.career.rejection = this.rejection;
      this.career.status = this.status;
      this.rejection = "";
      this.status = "";

      this.$axios
        .put("/api/careerList", this.career)
        .then((resp) => {
          console.log(resp.data);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "처리완료",
            showConfirmButton: false,
            timer: 1000,
          });
          this.$emit("close");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  components: {},
};
</script>
<style scoped>
h5 {
  text-align: left;
  margin: 20px 0;
}
.input-group {
  margin-bottom: 10px;
}
.input-group-text {
  border: none;
  font-size: large;
  padding: 0;
  padding-right: 10px;
  text-align: center;
  background-color: white;
}
</style>
