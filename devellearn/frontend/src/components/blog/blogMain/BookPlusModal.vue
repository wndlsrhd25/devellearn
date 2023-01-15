<template>
  <div>
    <div>
      <b-input-group prepend="" class="mt-3">
        <b-form-input
          v-model="bookFolderName"
          @keypress.enter="add($event)"
        ></b-form-input>
        <b-input-group-text @click="add($event)">
          <b-icon icon="plus" />
        </b-input-group-text>
      </b-input-group>

      <template v-for="item in bookName">
        <template>
          <b-input-group class="mt-3" :key="item.bookFolderName">
            <input
              type="radio"
              v-model="selectFolderName"
              :value="item.bookFolderName"
              @click="select($event)"
              style="margin-right: 10px"
            />

            <!--{{ selectFolderName }}-->
            <b-form-input :value="item.bookFolderName" readonly></b-form-input>

            <!-- 삭제 -->
            <b-input-group-text @click="xxx(item)">
              <b-icon icon="dash" />
            </b-input-group-text>
            <!-- 삭제 -->
          </b-input-group>
        </template>
      </template>
      <br />
      <div class="modal-button">
        <b-button @click="$bvModal.hide('bookmark-plus')">취소</b-button>
        <b-button @click="insertBook()" style="margin-left: 10px"
          >확인</b-button
        >
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ["bookClick"],
  data() {
    return {
      bookName: [],
      bookFolderName: "",
      bookmarkNo: "",
      selectFolderName: "",
      //모달창 클릭시 들어가야하는 값
      folderName: "",
      no: "",
    };
  },
  components: {},
  created() {
    this.getList();
  },
  methods: {
    //북마크 등록
    insertBook() {
      console.log("게시글 번호", this.bookClick);
      console.log("북마크이름", this.selectFolderName);
      this.$axios
        .post("/api/bookmarkBlog", {
          memberId: this.$store.state.userStore.id,
          postNo: this.bookClick,
          bookFolderName: this.selectFolderName,
        })
        .then((resp) => {
          console.log("북마크 등록", resp.data);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "북마크 성공",
            showConfirmButton: false,
            timer: 1000,
          });

          this.$emit("reload");
          this.$bvModal.hide("bookmark-plus");
        });
    },
    //폴더명 선택
    select(event) {
      console.log("폴더이름 : ", event.target.value);
      this.$emit("selected", event.target.value);
    },
    // 블로그 북마크 폴더별 게시글 수 조회
    getList() {
      this.$axios
        .get(
          "/api/bookmark/@" + this.$store.state.userStore.id + "/bookmarkFolder"
        )
        .then((resp) => {
          console.log(resp);
          this.bookName = resp.data;
        });
    },

    //블로그 북마크 폴더 추가
    add() {
      console.log("전체북마크이름" + this.bookName);
      console.log("추가되는 폴더이름" + this.bookFolderName);

      for (let i = 0; i < this.bookName.length; i++) {
        if (this.bookFolderName == this.bookName[i].bookFolderName) {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "이미 존재하는 이름입니다",
            showConfirmButton: false,
            timer: 1000,
          });
          this.bookFolderName = "";
          return;
        }
      }
      Swal.fire({
        title: "",
        text: "북마크 폴더를 추가하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonText: "취소",
        confirmButtonText: "확인",
      }).then((result) => {
        if (result.isConfirmed) {
          this.$axios
            .post("/api/bookmarkFolder", {
              bookFolderName: this.bookFolderName,
              memberId: this.$store.state.userStore.id,
            })
            .then((resp) => {
              console.log(resp);
              this.bookName.unshift(resp.data);
              this.bookFolderName = "";
              //화면을 껐을때 추가된 화면이 보이게 할 수 있나?
              this.$emit("reload");
            })
            .catch((error) => {
              console.log(error);
            });
        }
      });
    },

    //블로그 북마크 폴더 삭제
    xxx(item) {
      this.$swal
        .fire({
          title: "정말 북마크 폴더를 삭제하시겠습니까?",
          text: "삭제시 북마크한 게시글을 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            //여기에다가 삭제시 axios 넣으면 됨
            console.log("zzzz");
            console.log("zzzz");
            this.$axios
              .delete("/api/deleteBookmark", {
                data: {
                  bookFolderName: item.bookFolderName,
                  memberId: this.$store.state.userStore.id,
                },
              })
              .then((resp) => {
                console.log(resp);
                this.bookName = this.bookName.filter(
                  (data) => data.bookFolderName != item.bookFolderName
                );
                this.$emit("reload");
                
              })
              .catch((error) => {
                console.log("삭제", error);
              });

            Swal.fire({
              position: "center",
              icon: "success",
              title: "폴더 삭제 성공",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        });
    },
  },
};
</script>
<style scope>
.form-control {
  width: 390px;
}
.modal-button {
  float: right;
}
</style>
