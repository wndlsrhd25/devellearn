<template>
  <div id="MessageBox" style="min-height: 100vh">
    <div id="subTitle">
      <h3 style="color: #00336d">쪽지함</h3>
      <hr />
    </div>

    <!-- <b-button>쪽지보내기</b-button> -->

    <b-modal id="sendMsg" centered size="md" title="쪽지보내기" footerClass="p-2 border-top-0">
      <MsgModal :sendId="sendId" @reloadList="reloadAll();"></MsgModal>
      <template #modal-footer="{hide}">
        <b-button @click="hide('sendMsg')" style="display: none"></b-button>
      </template>
    </b-modal>
    <b-button @click="sendMsg()">쪽지보내기</b-button>
    <!-- ●●●●●부트스트랩 -->

    <div id="tabs">
      <b-tabs content-class="mt-3">
        <b-tab title="수신쪽지함" active>

          <div v-if="recvData.length==0"> 
            수신된 쪽지가 없습니다.

          </div>
          <div id="oneMessage" v-if="recvData.length != 0" v-for="list in recvData">
            <b-card @click="
                $router
                  .push({
                    name: 'recvMsgDetails',
                    query: {mesNo: list.mesNo, recvId: list.recvId},
                  })
                  .catch(() => {});
                updateStatus();
              " :title="list.sendId" :sub-title="list.sentDate | yyyyMMdd">
              <b-card-text>
                {{ list.content }}&nbsp;
                <a class="card-link">더보기></a>
              </b-card-text>
            </b-card>
          </div>
        </b-tab>
        <b-tab title="발신쪽지함">
          
          <div v-if="sendData.length==0"> 
            보낸 쪽지가 없습니다.

          </div>
          <div id="oneMessage" v-if="sendData.length != 0" v-for="lists in sendData">
            <b-card :title="lists.recvId" :sub-title="lists.sentDate | yyyyMMdd" @click="
                $router
                  .push({
                    name: 'sendMsgDetails',
                    query: {mesNo: lists.mesNo, recvId: lists.recvId},
                  })
                  .catch(() => {})
              ">
              <b-card-text>
                {{ lists.content }}
                <a class="card-link">더보기></a>
              </b-card-text>
            </b-card>
          </div>
        </b-tab>
      </b-tabs>
    </div>

    <!-- ●●●●●부트스트랩 -->
  </div>
</template>
<script>
  import axios from "axios";
  import SideBar from "../common/SideBar.vue";
  import MsgModal from "../common/MsgModal.vue";

  export default {
    components: {
      SideBar,
      MsgModal,
    },
    data: () => ({
      recvData: [],
      sendData: [],
      mesNo: "",
    }),
    created() {
      this.recvList();
      this.sendList();
    },
    methods: {
      recvList() {
        var memberId = this.$store.state.userStore.id;
        console.log(memberId);
        this.$axios
          .get("/api/recvMes", {
            params: {
              recvId: memberId,
            },
          })
          .then((res) => {
            console.log(res);
            this.recvData = res.data;
          })
          .catch((err) => {
            console.log(err);
          });
      },
      sendList() {
        var memberId = this.$store.state.userStore.id;
        console.log(memberId);
        this.$axios
          .get("/api/sendMes", {
            params: {
              sendId: memberId,
            },
          })
          .then((res) => {
            console.log(res);
            this.sendData = res.data;
          })
          .catch((err) => {
            console.log(err);
          });
      },
      updateStatus() {
        axios
          .put("/api/mesUpdate/" + this.$route.query.mesNo)
          .catch(function (error) {
            console.log(error);
          });
      },

      //sendMsg버튼은 쪽지함에서는 제외하고, 수신된 쪽지에 답하거나 아이디 눌렀을때 쪽지 보내기 기능 쓰는게 좋을 듯
      sendMsg() {
        var sendId = this.$store.state.userStore.id;
        console.log(sendId);
        this.$bvModal.show("sendMsg");
      },

      reloadAll() {
        this.recvList();
        this.sendList();
      }
    },
    filters: {
      // filter로 쓸 filter ID 지정
      yyyyMMdd: function (value) {
        // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
        if (value == "") return "";

        // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
        var js_date = new Date(value);

        // 연도, 월, 일 추출
        var year = js_date.getFullYear();
        var month = js_date.getMonth() + 1;
        var day = js_date.getDate();

        // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
        if (month < 10) {
          month = "0" + month;
        }

        if (day < 10) {
          day = "0" + day;
        }
        // 최종 포맷 (ex - '2021-10-08')
        return year + "/" + month + "/" + day;
      },
    },
  };
</script>
<style scoped>
  #subTitle {
    text-align: center;
  }

  #tabs {
    padding-top: 50px;
    width: 900px;
    text-align: left;
  }

  button {
    float: right;
    margin-top: -6.3%;
  }

  #oneMessage {
    margin-bottom: 1%;
  }

  .card-link {
    text-align: right;
  }

  #MessageBox {
    text-align: left;
    width: 900px;
  }
</style>