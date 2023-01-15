const {defineConfig} = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: ["vuetify"],
  lintOnSave: false,
  configureWebpack: {
    module: {
      rules: [
        {
          test: /\.txt$/i,
          use: "raw-loader",
        },
      ],
    },
  },
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8088",
        changeOrigin: true,
      },
    },
  },
});
