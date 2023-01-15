<template>
  <div style="min-height: 100vh">
    <div class="write-container">
      <div>
        <h3 style="text-align: left; margin-top: 20px">프로젝트 수정</h3>
      </div>
      <form id="update-blogPost">
        <div class="left-box">
          <p style="text-align: left; margin-top: 20px">
            <b-icon
              icon="asterisk"
              variant="danger"
              style="margin-right: 5px"
            ></b-icon
            >제목
          </p>
          <div>
            <b-form-input
              v-model="title"
              placeholder="제목을 입력하세요."
            ></b-form-input>
          </div>
          <p style="text-align: left; margin-top: 25px">
            <b-icon
              icon="asterisk"
              variant="danger"
              style="margin-right: 5px"
            ></b-icon
            >내용
          </p>
          <div class="ld_editor">
            <PostEditor
              @editorEmit="getContent"
              ref="editor_content"
            ></PostEditor>
          </div>
        </div>

        <div class="right-box">
          <!-- 폴더 선택-->
          <div>
            <p style="text-align: left; margin-top: 20px">카테고리 설정</p>
            <b-form-select
              v-model="folderName"
              :options="folder"
            ></b-form-select>

            <!-- 값은 이렇게 받음-->
            <!-- <div class="mt-3">
            Selected: <strong>{{ folderName }}</strong>
          </div> -->
          </div>

          <!-- 태그 입력 -->
          <div>
            <p style="text-align: left; margin-top: 25px">태그(최대5개)</p>
            <div>
              <b-form-tags
                input-id="tags-basic"
                v-model="tag"
                :limit="limit"
                remove-on-delete
                tag-variant="primary"
                placeholder="ex) 개미"
              ></b-form-tags>
              <!-- 값은 이렇게 -->
              <!-- <p class="mt-2">Value: {{ tag }}</p> -->
            </div>
          </div>

          <!-- 썸네일 -->
          <div>
            <p style="text-align: left; margin-top: 25px">썸네일</p>

            <div style="text-align: left">
              <div class="gray-box">
                <v-img
                  v-if="thumbnail == ''"
                  :src="'/api/images/blog/' + postContent.thumbnail"
                  class="thumbnail-box"
                />

                <v-img v-else :src="thumbnail" class="thumbnail-box" />
              </div>
              <v-flex xs8>
                <v-file-input
                  v-model="image"
                  name="image"
                  accept=".png, .jpeg, .jpg"
                  prepend-icon="mdi-camera"
                  @change="preview_thumbnail()"
                  @click:clear="thumbnail = ''"
                />
              </v-flex>
              <p style="margin-bottom: 0px">-등록가능확장자 : jpg, png, jpeg</p>
              <p>-이미지는 300*250에 최적화되어 있습니다</p>
            </div>
          </div>

          <!-- 공개 여부 -->
          <div>
            <p style="text-align: left; margin-top: 25px">공개여부</p>
            <b-form-select v-model="readStatus" :options="read"></b-form-select>

            <!-- 값은 이렇게 받음-->
            <!-- <div class="mt-3">
            Selected: <strong>{{ readStatus }}</strong>
          </div> -->
          </div>

          <!-- 댓글 설정 -->
          <div>
            <b-form-group
              label="댓글설정"
              v-slot="{ ariaDescribedby }"
              style="text-align: left; margin-top: 25px"
            >
              <b-form-radio-group
                id="radio-group-1"
                v-model="commentSet"
                :options="comment"
                :aria-describedby="ariaDescribedby"
                name="radio-options"
              ></b-form-radio-group>
            </b-form-group>

            <!-- 값은 이렇게 받음-->
            <!-- <div class="mt-3">
            Selected: <strong>{{ commentSet }}</strong>
          </div> -->
          </div>

          <!-- Submit Button -->
          <div class="button_area">
            <b-button variant="dark" v-on:click="moveBack">취소</b-button>
            <b-button
              variant="primary"
              v-on:click="update"
              style="margin-left: 10px"
              >등록</b-button
            >
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import PostEditor from "@/components/blog/blogPost/PostEditor.vue";
export default {
  props: ["postNo", "blogId"],
  components: {
    // quill-editor
    PostEditor,
  },
  data: function () {
    return {
      title: "",
      content: "",
      postInfo: {},
      folderName: "게시글",
      folder: [],
      tag: [],
      limit: 5,
      readStatus: "전체공개",
      read: [
        { value: "전체공개", text: "전체공개" },
        { value: "멤버공개", text: "멤버공개" },
      ],
      commentSet: "허용",
      comment: [
        { text: "허용", value: "허용" },
        { text: "비허용", value: "비허용" },
      ],
      //썸네일
      thumbnail: "",
      url: "",
      image: "",
      postContent: {},
    };
  },
  created() {
    this.getFolderName();
    this.getPostInfo();
  },
  methods: {
    //수정될 게시글 내용
    getPostInfo() {
      this.$axios
        .post("/api/blogs/detailPost", {
          memberId: this.$store.state.userStore.id,
          postNo: this.$route.query.postNo,
        })
        .then((resp) => {
          console.log(resp);
          this.postContent = resp.data;
          console.log("제목 : " + this.postContent.title);
          this.title = this.postContent.title;
          //에디터 가져오기 // 하위컴포넌트(퀼 에디터)에 조회한 내용 전달(지완이)
          this.$refs.editor_content.editorContent = this.postContent.content;
          this.folderName = this.postContent.folderName;

          //태그 자르기
          let stickTag = this.postContent.tags;
          this.tag = stickTag.split("#");

          this.readStatus = this.postContent.readStatus;
          this.commentSet = this.postContent.commentSet;
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    //미리보기 이미지
    preview_thumbnail() {
      this.thumbnail = URL.createObjectURL(this.image);
      console.log("sk : " + this.thumbnail);
    },
    moveBack() {
      this.$router.go(-1);
    },
    //폴더 리스트 조회
    getFolderName() {
      this.$axios
        .get("/api/blogs/@" + this.$route.query.blogId + "/folder")
        .then((resp) => {
          console.log(resp.data);
          for (let gname of resp.data) {
            console.log(gname.folderName + "");
            this.folder.push({
              value: gname.folderName,
              text: gname.folderName,
            });
          }
        })
        .catch(function (error) {
          console.log("error", error);
        });
    },
    //글 수정
    update() {
      console.log("아이디 " + this.$store.state.userStore.id);
      console.log("제목 " + this.title);
      console.log("내용 " + this.content);
      console.log("블로그 아이디 " + this.$store.state.userStore.id);
      console.log("폴더 이름 " + this.folderName);
      console.log("태그이름 " + this.tag);

      let spliceTag = "";
      for (let i = 0; i < this.tag.length; i++) {
        spliceTag += "#" + this.tag[i] + " ";
      }
      //마지막에 "," 자르는것
      spliceTag = spliceTag.substring(0, spliceTag.length - 1);

      const formData = new FormData(document.getElementById("update-blogPost"));
      //에디터는 어떻게 수정되는거지? 파일이 삭제되고 다시 가져오는건가? 그러면 id를 새로 넣어야하나..?
      formData.append("title", this.title);
      formData.append("content", this.content);
      formData.append("folderName", this.folderName);
      formData.append("tags", spliceTag);
      formData.append("readStatus", this.readStatus);
      formData.append("commentSet", this.commentSet);
      formData.append("postNo", this.$route.query.postNo);
      formData.append("thumbnail", this.postContent.thumbnail);

      console.log("자른 태그이름 " + spliceTag);
      console.log("읽기상태 " + this.readStatus);
      console.log("댓글상태 " + this.commentSet);
      console.log("썸네일" + this.thumbnail);

      this.$axios
        .post("/api/blogs/update", formData, {
          Headers: {
            "Content-Type": "multipart/form-data",
          },
          thumbnail: this.thumbnail,
          //파일이름을 보내줘야함
        })
        .then((resp) => {
          console.log(resp);
          this.postInfo = resp.data;
          console.log("정보" + this.postInfo);
          console.log("번호가 나오남?" + this.$route.query.postNo);
          this.$refs.editor_content.editorContent = "";
          Swal.fire({
            position: "center-center",
            icon: "success",
            title: "글수정 성공",
            showConfirmButton: false,
            timer: 1000,
          });
          this.$router
            .push({
              path: "/postDetail",
              query: {
                postNo: this.$route.query.postNo,
                blogId: this.$route.query.blogId,
              },
            })
            .catch(() => {});
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getContent(editorContent) {
      console.log("emit_success", editorContent);
      this.content = editorContent;
      console.log("겟 콘탠트 내용" + this.content);
    },
  },
};
</script>
<style scoped>
/* 에디터 */
.ld_editor {
  min-height: 800px;
}

.write-container {
  width: 75%;
  min-width: 1200px;
  margin: 0 auto;
  height: 1200px;
  padding-top: 20px;
}
#update-blogPost {
  display: flex;
}
.left-box {
  width: 68%;
  margin-top: 20px;
}
.right-box {
  width: 27%;
  min-width: 300px;
  margin-top: 20px;
  margin-left: 3%;
}
.gray-box {
  margin-left: 10px;
  height: 250px;
  width: 300px;
  background-color: rgb(158, 158, 158);
}

.thumbnail-box {
  height: 250px;
  width: 300px;
}
</style>
