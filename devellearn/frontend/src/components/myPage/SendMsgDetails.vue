<template>
  <div id="MessageBox" style="height: 100vh">
    <div id="subTitle">
      <h3 style="color: #00336d">발신 쪽지함</h3>

      <hr />
    </div>
    <!-- ●●●●●부트스트랩 -->

    <div id="tabs">
      <div id="oneMessage">
        <b-card id="bCard" :title="sendData.recvId" :sub-title="sendData.sentDate | yyyyMMdd">
          <b-card-text>
            {{ sendData.content }}&nbsp;
            <hr />

            <div v-if="sendData.readUnread == null">
              <span class="font">수신 여부 : </span>읽지않음
            </div>
            <div v-if="sendData.readUnread == 'y'">
              <span class="font">읽은 시각 : </span>{{ sendData.readDate | yyyyMMdd }}
            </div>
          </b-card-text>
        </b-card>
      </div>

      <div id="btn" style="display: flex; justify-content: flex-end">
        <div id="btn2">
          <b-button @click="deleteMsg()" style="float: right">삭제</b-button>
        </div>
        <div id="btn3">
          <b-button @click="back()" style="float: right">뒤로</b-button>
        </div>
      </div>
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
      sendData: [],
    }),
    created() {
      this.sendList();
    },
    methods: {
      sendList() {
        axios({
            url: "/api/message/" + this.$route.query.mesNo,
            type: "GET",
          })
          .then((res) => {
            console.log(res);
            this.sendData = res.data;
          })
          .catch((err) => {
            console.log(err);
          });
      },

      deleteMsg() {


        this.$swal
          .fire({
            title: "정말 삭제하시겠습니까?",
            text: "삭제시 복구할 수 없습니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            cancelButtonText: "취소",
            confirmButtonText: "확인",
          })

          .then((result) => {
            if (result.isConfirmed) {


              ////// 원래코드 (스왈작동잘하는지 테스트하기)

              axios
                .delete("/api/mesDelete/" + this.$route.query.mesNo)
                .then((resp) => {
                  //alert("삭제완료!");
                  this.$router.push({
                    path: "/myPage/messageBox",
                  });
                })
                .catch(function (error) {
                  console.log(error);
                });
              //////


              this.$swal.fire("삭제완료", "", "success");
            }
          });

      },





      back() {
        this.$router.go(-1);
      },







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
        var hour = js_date.getHours();
        var min = js_date.getMinutes();

        // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
        if (month < 10) {
          month = "0" + month;
        }

        if (day < 10) {
          day = "0" + day;
        }

        // 최종 포맷 (ex - '2021-10-08')
        return year + "/" + month + "/" + day + " " + hour + ":" + min;
      },
    },
  };

  // 쪽지 없을때 없다고 얘기해주기
</script>
<style scoped>
  #subTitle {
    text-align: center;
  }

  #tabs {
    padding-top: 50px;
    width: 900px;
    text-align: left;
    height: 150px;
  }

  #oneMessage {
    margin-bottom: 1%;
    height: auto;
    text-align: left;
  }

  #bCard {
    height: auto;
  }

  #MessageBox {
    text-align: left;
    width: 900px;
  }

  #btn1,
  #btn2,
  #btn3 {
    margin-right: 10px;
  }

  #btn {
    text-align: right;
  }

  #recvId {
    float: left;
    margin-top: -60px;
  }

  .font {
    font-weight: bold;
    color: navy;
  }
</style>