<template>
  <!-- 게시글 추가 모달창 -->
  <div>
    <div>
      <b-input-group prepend="" class="mt-3">
        <b-form-input
          v-model="folderName"
          @keypress.enter="add($event)"
        ></b-form-input>
        <b-input-group-text @click="add($event)">
          <b-icon icon="plus" />
        </b-input-group-text>
      </b-input-group>

      <template v-for="item in folderNameList">
        <template>
          <b-input-group prepend="" class="mt-3" :key="item.folderNo">
            <!--수정-->
            <b-form-input
              :value="item.folderName"
              @change="update($event, item.folderName)"
            ></b-form-input>
            <!-- 삭제 -->
            <b-input-group-text @click="xxx(item)">
              <b-icon icon="dash" />
            </b-input-group-text>
          </b-input-group>
        </template>
      </template>

      <br />
      <div class="modal-button">
        <b-button
          @click="$bvModal.hide('folder-plus')"
          style="margin-left: 10px"
          >확인</b-button
        >
      </div>
    </div>
  </div>
</template>
<script>
import { eventBus } from "../../../main.js";
export default {
  props: ["blogId"],
  data() {
    return {
      folderNameList: [],
      folderName: "",
      oldName: "",
    };
  },
  components: {},
  created() {
    this.getList();
  },
  methods: {
    close() {
      this.$emit("reloadFolder");
      this.$bvModal.hide("folder-plus");
    },
    //블로그 폴더 가져오기
    getList() {
      this.$axios
        .get("/api/blogs/@" + this.$route.query.blogId + "/folder")
        .then((resp) => {
          console.log(resp.data);
          this.folderNameList = resp.data;
        });
    },

    //블로그 폴더 추가
    add() {
      Swal.fire({
        title: "",
        text: "카테고리를 추가하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonText: "취소",
        confirmButtonText: "확인",
      }).then((result) => {
        if (result.isConfirmed) {
          this.$axios
            .post("/api/blogs/folder/insert", {
              folderName: this.folderName,
              blogId: this.blogId,
            })
            .then((resp) => {
              console.log(resp);
              this.folderNameList.unshift(resp.data);
              eventBus.$emit("plusFolderName", this.folderName);
              this.getList();
              this.folderName = "";
            })
            .catch((error) => {
              console.log(error);
            });
        }
      });
    },

    //블로그 카테고리 삭제
    xxx(item) {
      this.$swal
        .fire({
          title: "정말 삭제하시겠습니까?",
          text: "삭제시 카데고리 안에 작성된 게시글을 복구할 수 없습니다.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          cancelButtonText: "취소",
          confirmButtonText: "확인",
        })
        .then((result) => {
          if (result.isConfirmed) {
            console.log(item.folderNo);

            this.$axios
              .delete("/api/blogs/folder/delete", {
                data: {
                  folderNo: item.folderNo,
                },
              })
              .then((resp) => {
                console.log(resp);
                eventBus.$emit("minusFolderName", item.folderName);
                this.folderNameList = this.folderNameList.filter(
                  (data) => data.folderName != item.folderName
                );
              })
              .catch((error) => {
                console.log("삭제", error);
              });
            this.$swal.fire({
              title: "삭제완료",
              icon: "success",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        });
    },

    //카테고리 수정
    update(event, oldName) {
      Swal.fire({
        title: "",
        text: "카테고리를 수정하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonText: "취소",
        confirmButtonText: "확인",
      }).then((result) => {
        if (result.isConfirmed) {
          //기존 카테고리 + 수정 카테고리
          let sum = oldName + "," + event;
          //eventBus로 두개는 보낼 수 없는걸까?
          eventBus.$emit("updateFolderName", sum);
          this.$axios
            .post("/api/blogs/folder/update", {
              newFolderName: event,
              blogId: this.blogId,
              folderName: oldName,
            })
            .then((resp) => {
              console.log(resp);
              this.$bvModal.hide("folder-plus");
            });
        }
      });
    },
  },
};
</script>
<style scope></style>
