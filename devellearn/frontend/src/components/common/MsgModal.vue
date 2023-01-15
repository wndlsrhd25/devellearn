<template>
  <div>
    <div id="box" style="justify-content: space-between">
      <div class="form-group">
        <label for="recipient-name" class="col-form-label">Recipient:</label>
        <input
          type="text"
          class="form-control"
          v-model="recvId"
          id="recvId"
          @change="checkId"
        />
      </div>
      <div class="form-group">
        <label for="message-text" class="col-form-label">Message:</label>
        <textarea class="form-control" v-model="content"></textarea>
      </div>

      <div id="button">
        <b-button id="send" @click="sendMessage()">보내기</b-button>
        <!-- hide할 내용은 숨길 모달창 id값임 -->
        <b-button id="exit" @click="$bvModal.hide('sendMsg')">취소</b-button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    content: "",
    vaildId: false,
  }),

  props: {
    recvId: "",
  },
  methods: {
    sendMessage() {
      console.log(this.$store.state.userStore.id);
      console.log(this.recvId);
      console.log(this.content);
      if (this.content != "" && this.recvId != null) {
        this.$axios
          .post("/api/mesInput", {
            sendId: this.$store.state.userStore.id,
            recvId: this.recvId,
            content: this.content,
          })
          .then((resp) => {
            this.$bvModal.hide("sendMsg");

            Swal.fire({
              position: "center",
              icon: "success",
              toast: true,
              title: "발송 완료!",
              showConfirmButton: false,
              timer: 1000,
            });

            //자식에서 전달하고 부모에게 리로드,,@reload="reload()" 리로드 메소드 만들어서 조회하는거.. 겟 리스트..
            this.$emit("reloadList");
          });
      } else {
        //alert("내용을 모두 입력해주세요!");

        Swal.fire({
          position: "center",
          icon: "info",
          toast: true,
          title: "내용을 모두 입력해주세요!",
          showConfirmButton: false,
          timer: 1000,
        });
      }
    },
    checkId() {
      this.$axios
        .post("/api/idCheck", {
          memberId: this.recvId,
        })
        .then((res) => {
          if (res.data != 1) {
            //alert("존재하지 않는 아이디입니다.");
            Swal.fire({
              position: "center",
              icon: "info",
              toast: true,
              title: "존재하지 않는 아이디입니다.",
              showConfirmButton: false,
              timer: 1000,
            });
            this.recvId = "";
          } else {
            this.vaildId = true;
            //document.getElementById("recvId").readOnly=true;
          }
        });
    },
  },

  components: {},
};
</script>
<style scoped>
#box {
  width: 450px;
  margin: 0 auto;
}
#button {
  text-align: center;
}
#send {
  margin-right: 20px;
  width: 90px;
  background-color: rgb(19, 19, 116);
}

#exit {
  margin-right: 20px;
  width: 90px;
}

#form-group {
  height: auto;
}
</style>
