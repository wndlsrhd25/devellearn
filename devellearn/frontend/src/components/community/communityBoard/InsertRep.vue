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
      <el-input placeholder="댓글을 입력해 주세요" v-model="content"></el-input>
      <!-- dialog footer 영역 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="insertrep()">확인</el-button>
        <el-button @click="openPopup = false">취소</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "InsertRep",
  data() {
    return {
      openPopup: false,
      boardNo: "",
      repNo: "",
      editMode: false,
      content: "",
    };
  },
  computed: {
    setParams() {
      const params = {
        boardNo: this.boardNo,
        repNo: this.repNo,
        content: this.content,
      };
      return params;
    },
  },
  methods: {
    insertrep() {
      axios
        .post("/api/rep", this.setParams)
        .then((response) => {
          this.openPopup = false;
          this.content = "";
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped></style>
