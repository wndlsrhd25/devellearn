<template>
  <!-- 북마크 추가 모달창 -->
  <div>
    <div>
      <b-input-group prepend="" class="mt-3">
        <!-- 추가 -->
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
          <!-- 폴더 이름 리스트 가져오기 -->
          <b-input-group prepend="" class="mt-3" :key="item.bookFolderName">
            <b-form-input
              :value="item.bookFolderName"
              @change="update($event, item.bookFolderName)"
            ></b-form-input>
            <!-- 삭제 -->
            <b-input-group-text @click="xxx(item)">
              <b-icon icon="dash" />
            </b-input-group-text>
            <!-- 삭제 -->
          </b-input-group>
        </template>
      </template>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      bookName: [],
      bookFolderName: "",
      bookmarkNo: "",
    };
  },
  components: {},
  created() {
    this.getList();
  },
  methods: {
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
    add(event) {
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
              Swal.fire({
                position: "center",
                icon: "success",
                title: "폴더 추가 성공",
                showConfirmButton: false,
                timer: 1000,
              });
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

    //북마크 폴더 수정
    update(event, oldName) {
      Swal.fire({
        title: "",
        text: "북마크 폴더명을 수정하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonText: "취소",
        confirmButtonText: "확인",
      }).then((result) => {
        if (result.isConfirmed) {
          console.log("새로운 이름" + event);
          console.log(oldName);

          this.$axios
            .put("/api/bookmarkUpdate", {
              newBookFolderName: event,
              memberId: this.$store.state.userStore.id,
              bookFolderName: oldName,
            })
            .then((resp) => {
              console.log(resp);
              Swal.fire({
                position: "center",
                icon: "success",
                title: "폴더명 수정 완료",
                showConfirmButton: false,
                timer: 1000,
              });
              this.$emit("reload");
            });
        }
      });
    },
  },
};
</script>
<style scope></style>
