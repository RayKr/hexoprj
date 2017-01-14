---
title: jQuery
date: 2016-07-10 10:29:07
tags:
- jQuery
- 前端
---
## 基础
```js
$(document).ready(function() {
  // 增加类
  $("button").addClass("animated bounce");
  $(".well").addClass("animated shake");
  $("#target3").addClass("animated fadeOut");
  // 移除类
  $("#target3").removeClass("fadeOut");
  // css 样式
  $("#target1").css("color", "red");
  // 属性
  $("button").prop("disabled", true);
  // html带标签替换
  $("#target4").html("<em>target4</em>");
  // 移除html节点元素
  $("#target4").remove();
  // 移动元素，追加到另一个标签下
  $("#target2").appendTo("#right-well");
  // 复制元素，先克隆再追加
  $("#target5").clone().appendTo("#left-well");
  // 父元素样式
  $("#target1").parent().css("background-color", "red")
  // 子元素样式
  $("#right-well").children().css("color", "orange")
  // 当一个元素没有id时，用 类+序号 的方式确定某一或某些元素 nth-child(n)
  $(".target:nth-child(2)").addClass("animated bounce");
  // odd（奇数），even（偶数）jquery从0开始计数，所以odd代表的是第2,4,6...元素，even代表的是1,3,5...元素
  $(".target:even").addClass("animated shake");
  $(".target:odd").addClass("animated shake");

});
```
`$(document).ready()`很重要，是保证所有页面加载完成后，再执行js，否则可能会有bug。
以上展示了三种jquery选择器方式：类型选择器，类选择器，ID选择器


