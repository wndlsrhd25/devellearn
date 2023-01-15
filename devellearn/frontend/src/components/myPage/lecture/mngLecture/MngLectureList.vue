<template>
  <div id="ml_wrapper">
    <b-modal id="modal-center" centered title="반려 사유">
      <p class="my-4">{{ rejection }}</p>
    </b-modal>

    <div class="tbl_lecture">
      <template>
        <b-table striped hover :items="items" :fields="fields">
          <template #cell(botton)="data">
            <div v-if="data.item.status != '삭제'">
              <b-button
                size="sm"
                class="mr-1 success"
                @click="
                  $router
                    .push({
                      path: '/lectureDetail/intro',
                      query: { no: data.item.lectureNo },
                    })
                    .catch(() => {})
                "
              >
                상세
              </b-button>
              <b-button
                size="sm"
                class="mr-1 primary"
                @click="
                  $router
                    .push({
                      path: '/myPage/mngLecture/lectureUpdate',
                      query: { no: data.item.lectureNo },
                    })
                    .catch(() => {})
                "
              >
                수정
              </b-button>
              <b-button
                size="sm"
                class="mr-1"
                @click="deleteLecture(data.item.lectureNo)"
              >
                삭제
              </b-button>
              <b-button
                v-if="data.item.status == '반려'"
                size="sm"
                class="mr-1 warning"
                @click="showDenied(data.item.rejection)"
                v-b-modal.modal-center
              >
                반려사유
              </b-button>
            </div>
            <div v-if="data.item.status == '삭제'">삭제된 강의입니다.</div>
          </template>
        </b-table>
      </template>
    </div>
    <v-pagination
      v-model="page"
      :length="total"
      prev-icon="mdi-menu-left"
      next-icon="mdi-menu-right"
    ></v-pagination>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      // 강의 리스트 담을 배열
      items: [],
      page: 1,
      total: 1,
      fields: [
        {
          key: "lectureNo",
          label: "강의번호",
          sortable: true,
        },
        {
          key: "title",
          label: "강의제목",
          sortable: true,
        },
        {
          key: "status",
          label: "상태",
          sortable: true,
        },
        {
          key: "botton",
          label: "더보기",
        },
      ],
      rejection: "",
    };
  },
  created() {
    this.getListAllByLecturer();
  },
  watch: {
    page() {
      this.getListAllByLecturer();
    },
  },
  methods: {
    // 강의 전체 조회
    getListAllByLecturer() {
      console.log("page", this.page);
      this.$axios
        .get("/api/lectureListByLecturer", {
          params: {
            pageNum: this.page,
            memberId: this.$store.getters["userStore/getId"],
          },
        })
        .then((resp) => {
          console.log(resp.data);
          this.items = resp.data.list;
          this.total = resp.data.pages;
          console.log("페이징 되라ㅏㅏㅏ", this.total);
          console.log("items", this.items);
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    // 강의 삭제 -> status 를 '삭제'로 변경
    deleteLecture(lectureNo) {
      //////////////////////////////////////////////////삭제(경고 빨간 아이콘) 알림창
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
        .then(async (result) => {
          if (result.isConfirmed) {
            //여기에다가 삭제시 axios 넣으면 됨
            await this.$axios
              .put("/api/lecture", {
                lectureNo: lectureNo,
                status: "삭제",
              })
              .then((resp) => {
                if (resp.data != 0) {
                  console.log(resp.data);
                  // lectureList 배열에서 상태를 삭제로 변경
                  for (let i = 0; i < this.items.length; i++) {
                    if (this.items[i].lectureNo == lectureNo) {
                      console.log("삭제로 바까라");
                      this.items[i].status = "삭제";
                    }
                  }
                }
              })
              .catch((error) => {
                console.log(error);
              });
            this.$swal.fire("삭제완료", "", "success");
          }
        });
    },
    // 반려사유보기
    showDenied(rejection) {
      // this.$bvModal.show("modal-center");
      this.rejection = rejection;
    },
  },
};
</script>

<style scoped>
.tbl_lecture {
  margin-top: 20px;
  text-align: left;
}
.ml_header {
  display: flex;
  justify-content: space-between;
  /* border-bottom: 1px solid lightgray; */
  /* margin-bottom: 20px; */
}

.ml_header h4 {
  line-height: 20 0%;
}
</style>
