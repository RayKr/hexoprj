---
title: 前端技术学习札记
date: 2017-02-14 21:07:07
tags:
- front-end
categories:
- front-end
---
## Node.js & NPM
[Node.js](https://nodejs.org/en/)

[NPM淘宝映像](https://npm.taobao.org/)
```
npm install -g cnpm --registry=https://registry.npm.taobao.org
```
[Webpack官网](https://webpack.bootcss.com/)

[Webpack安装说明](https://webpack.bootcss.com/get-started/install-webpack/)
```
npm install webpack@beta -g
```
Webpack使用
```
webpack app/index.js dist/bundle.js
```
或使用配置文件`webpack.config.js`：
```js
module.exports = {
  entry: './app/index.js',
  output: {
    filename: 'bundle.js',
    path: './dist'
  }
}
```
```
webpack --config webpack.config.js
```
> 如果存在 webpack.config.js 文件，webpack 命令默认加载此文件。

通过 npm 使用 webpack
```js
{
  ...
  "scripts": {
    "build": "webpack"
  },
  ...
}
```
```
npm run build
```
