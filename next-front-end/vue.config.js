const marked = require("marked");
const renderer = new marked.Renderer();

module.exports = {
  devServer: {
    proxy: {
      "^/api": {
        // target: "http://192.168.0.100:8082/",
        target: "http://localhost:8082/",
        ws: true,
        changeOrigin: true
      }
    }
  },
  configureWebpack: {
    devtool: "source-map",
    module: {
      rules: [
        {
          test: /\.(md)$/,
          use: [
            {
              loader: "html-loader"
            },
            {
              loader: "markdown-loader",
              options: {
                renderer
              }
            }
          ]
        },

        {
          test: /\.(pdf)(\?.*)?$/,
          use: [
            {
              loader: "url-loader",
              options: {
                name: "files/[name].[hash:8].[ext]"
              }
            }
          ]
        }
      ]
    }
  }
};
