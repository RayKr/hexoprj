---
title: 前端技术解决方案整理
date: 2017-02-14 22:46:03
tags:
- front-end
categories:
- front-end
---
## 模态对话框（Modal）加载远程内容
Bootstrap默认提供的Modal是无法实现加载远程功能的，当出现重复的内容则需编写多遍，或使用`iframe`来实现。其实这个功能可以使用`Sco.js`提供的Modal增强插件，即可轻松实现Modal加载远程内容。具体可查看[sco.modal.js](/post/front-end/scojs.html)

## 页面局部刷新功能
页面中的部分元素需要刷新，或者部分链接需要通过Ajax获取远程数据，此时没有必要再手写一套`$.ajax`，`Sco.js`的Ajax增强插件也提供了该功能实现。具体可查看[sco.ajax.js](/post/front-end/scojs.html)


