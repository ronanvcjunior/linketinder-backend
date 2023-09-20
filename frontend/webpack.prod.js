const HtmlWebpackPlugin = require("html-webpack-plugin");
const TerserPlugin = require("terser-webpack-plugin");
const path = require("path");

module.exports = {
  mode: "production",
  entry: {
    index: "./src/index.ts",
    cadastroCandidato: "./src/pages/cadastroCandidato.ts",
    cadastroEmpresa: "./src/pages/cadastroEmpresa.ts",
    perfilCandidato: "./src/pages/perfilCandidato.ts",
    perfilEmpresa: "./src/pages/perfilEmpresa.ts"
  },
  output: {
    filename: "[name].[contenthash].js",
    path: path.resolve(__dirname, "dist"),
    publicPath: "/linketinder-backend/frontend/dist/",
    clean: true
  },
  resolve: {
    extensions: [".ts", ".js"]
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: "ts-loader",
        exclude: /node_modules/,
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
      inject: "body",
      publicPath: "/linketinder-backend/frontend/dist/"
    }),
    new HtmlWebpackPlugin({
      template: "./public/cadastroCandidato.html",
      filename: "cadastroCandidato.html",
      chunks: ["cadastroCandidato"],
      inject: "body",
      publicPath: "/linketinder-backend/frontend/dist/"
    }),
    new HtmlWebpackPlugin({
      template: "./public/cadastroEmpresa.html",
      filename: "cadastroEmpresa.html",
      chunks: ["cadastroEmpresa"],
      inject: "body",
      publicPath: "/linketinder-backend/frontend/dist/"
    }),
    new HtmlWebpackPlugin({
      template: "./public/perfilCandidato.html",
      filename: "perfilCandidato.html",
      chunks: ["perfilCandidato"],
      inject: "body",
      publicPath: "/linketinder-backend/frontend/dist/"
    }),
    new HtmlWebpackPlugin({
      template: "./public/perfilEmpresa.html",
      filename: "perfilEmpresa.html",
      chunks: ["perfilEmpresa"],
      inject: "body",
      publicPath: "/linketinder-backend/frontend/dist/"
    })
  ]
};
