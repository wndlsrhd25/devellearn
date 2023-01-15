<template>
  <div>
    <form id="companyData">
      <v-card>
        <v-card-title>
          <span class="text-h5">고수등록신청</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <b-input-group>
              <template #prepend>
                <b-input-group-text
                  >신청내용 : {{ req.content }}</b-input-group-text
                >
              </template>
            </b-input-group>

            <b-input-group>
              <template #prepend>
                <b-input-group-text>처리하기</b-input-group-text>
              </template>
              <b-form-select
                v-model="req.status"
                :options="statusList"
              ></b-form-select>
            </b-input-group>
            <div v-if="req.status == '반려'">
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
    <div data-app>
      <v-dialog v-model="dialog" max-width="290">
        <Complete @close="close()"></Complete>
      </v-dialog>
    </div>
  </div>
</template>
<script>
import Complete from "@/components/common/Complete.vue";
export default {
  props: ["req"],
  data: () => ({
    statusList: [
      {text: "승인", value: "승인"},
      {text: "반려", value: "반려"},
    ],
    dialog: false,
    rejection: "",
  }),

  methods: {
    close() {
      this.$emit("close");
    },
    edit() {
      this.req.rejection = this.rejection;
      console.log(this.req.rejection);

      this.dialog = true;
      this.$axios
        .put("/api/masterReq", this.req)
        .then((resp) => {
          console.log(resp.data);
          // this.$emit("reload");
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  components: {Complete},
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
