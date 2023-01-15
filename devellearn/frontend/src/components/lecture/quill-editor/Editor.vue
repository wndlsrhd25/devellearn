<template>
  <div class="example">
    <quill-editor
      class="editor"
      ref="myTextEditor"
      :disabled="false"
      :value="editorContent"
      :options="editorOption"
      @change="onEditorChange"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @ready="onEditorReady($event)"
    />

    <!-- <div class="output ql-snow">
      <div v-html="content"></div>
    </div> -->`
  </div>
</template>

<script>
import hljs from "highlight.js";
import debounce from "lodash/debounce";
import {quillEditor} from "vue-quill-editor";
import Quill from "quill";

import {ImageDrop} from "quill-image-drop-module";

import {ImagePaste} from "@/imagePasteClass";

//  import ImageResize from "quill-image-resize-module";
// alert(ImageResize)
import Im from "@/ResizeImage/ImageResize";

Quill.register("modules/imageDrop", ImageDrop);
Quill.register("modules/imagePaste", ImagePaste);
Quill.register("modules/imageResize", Im);

// highlight.js style
import "highlight.js/styles/tomorrow.css";

// import theme style
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

export default {
  name: "quill-example-snow",
  title: "Theme: snow",
  components: {
    quillEditor,
  },
  data() {
    return {
      editorOption: {
        placeholder: "내용을 입력하세요.",
        modules: {
          toolbar: [
            ["bold", "italic", "underline", "strike"], // <strong>, <em>, <u>, <s>
            [{color: []}, {background: []}], //style="color: rgb(230, 0, 0);", style="background-color: rgb(230, 0, 0);"
            ["blockquote", "code-block"], // <blockquote>, <pre class="ql-syntax" spellcheck="false">
            [{header: 1}, {header: 2}], // <h1>, <h2>
            [{header: [1, 2, 3, 4, 5, 6, false]}], // <h1>, <h2>, <h3>, <h4>, <h5>, <h6>, normal
            [{list: "ordered"}, {list: "bullet"}], // <ol>, <ul>
            [{size: ["small", false, "large", "huge"]}], //class 제어 - html로 되도록 확인
            [{font: []}], // 글꼴 class로 제어
            [{align: []}], // class
            ["link", "image", "video"],
          ],
          syntax: {
            highlight: (text) => hljs.highlightAuto(text).value,
          },
          imageResize: {
            displayStyles: {
              backgroundColor: "black",
              border: "none",
              color: "white",
            },
            modules: ["Resize", "DisplaySize"],
          },
        },
      },
      editorContent: "",
    };
  },
  methods: {
    onEditorChange: debounce(function (value) {
      this.editorContent = value.html;
      // emit
      this.$emit("editorEmit", this.editorContent);
    }, 466),
    onEditorBlur(editor) {
      console.log("editor blur!", editor);
    },
    onEditorFocus(editor) {
      console.log("editor focus!", editor);
    },
    onEditorReady(editor) {
      console.log("editor ready!", editor);
    },
  },
  computed: {
    editor() {
      return this.$refs.myTextEditor.quill;
    },
    contentCode() {
      return hljs.highlightAuto(this.editorContent).value;
    },
  },
  mounted() {
    console.log("this is Quill instance:", this.editor);
  },
};
</script>

<style>
.ql-editor {
  min-height: 200px;
  font-size: 16px;
}
</style>
