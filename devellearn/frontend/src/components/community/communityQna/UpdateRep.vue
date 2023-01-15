<template>
  <div>
    <!-- 팝업 내용 -->
    <el-dialog
      :visible.sync="openPopup"
      width="30%"
      center
      :close-on-click-modal="false"
    >
      <!-- 본문 영역 -->
      <el-input placeholder="내용을 입력해 주세요" v-model="content"></el-input>
      <!-- dialog footer 영역 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateRep()">확인</el-button>
        <el-button @click="openPopup = false">취소</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "UpdateRep",
  data() {
    return {
      openPopup: false,
      qnaNo: "",
      qnarepNo: "",
      editMode: false,
      content: "",
    };
  },
  computed: {
    setParams() {
      const params = {
        memberId: this.$store.state.userStore.id,
        qnarepNo: this.qnarepNo,
        qnaNo: this.qnaNo,
        content: this.content,
      };
      return params;
    },
  },
  methods: {
    updateRep() {
      axios
        .put("/api/sqnarep", this.setParams)
        .then((response) => {
          this.openPopup = false;
          this.content = "";
        })
        .catch(function (error) {
          console.log(error);
        });
      Swal.fire({
        position: "center",
        icon: "success",
        title: "수정 완료",
        showConfirmButton: false,
        timer: 1000,
      });
    },
  },
};
</script>

<style scoped></style>
