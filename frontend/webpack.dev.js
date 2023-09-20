const HtmlWebpackPlugin = require("html-webpack-plugin");
const path = require("path");

module.exports = {
  mode: "development",
  devtool: "eval-source-map",
  entry: {
    index: "./src/index.ts",
    cadastroCandidato: "./src/pages/cadastroCandidato.ts",
    cadastroEmpresa: "./src/pages/cadastroEmpresa.ts",
    perfilCandidato: "./src/pages/perfilCandidato.ts",
    perfilEmpresa: "./src/pages/perfilEmpresa.ts"
  },
  output: {
    filename: "[name].[contenthash].js"
  },
  resolve: {
    extensions: [".ts", ".js"]
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: "ts-loader",
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: [
          "style-loader", "css-loader"
        ]
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: "./public/index.html",
      filename: "index.html",
      chunks: ["index"],
      inject: "body"
    }),
    new HtmlWebpackPlugin({
      template: "./public/cadastroCandidato.html",
      filename: "cadastroCandidato.html",
      chunks: ["cadastroCandidato"],
      inject: "body",
    }),
    new HtmlWebpackPlugin({
      template: "./public/cadastroEmpresa.html",
      filename: "cadastroEmpresa.html",
      chunks: ["cadastroEmpresa"],
      inject: "body",
    }),
    new HtmlWebpackPlugin({
      template: "./public/perfilCandidato.html",
      filename: "perfilCandidato.html",
      chunks: ["perfilCandidato"],
      inject: "body",
    }),
    new HtmlWebpackPlugin({
      template: "./public/perfilEmpresa.html",
      filename: "perfilEmpresa.html",
      chunks: ["perfilEmpresa"],
      inject: "body",
    })
  ],
  devServer: {
    historyApiFallback: true,
    port: 3000,
    open: true
  }
};
