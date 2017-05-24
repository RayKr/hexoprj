---
title: 前端开发技巧总结
date: 2017-03-03 20:08:14
tags: front-end
categories: front-end
---

**两个class对调，适用于折叠按钮加号/减号切换的情况**
```js
$(selector).toggleClass('fa-minus').addClass('fa-plus');
```

**文字在div中垂直居中**
```html
<table style="height:100%;">
    <tr>
        <td style="vertical-align:middle;">文字内容</td>
    </tr>
</table>
```

**vue表单ajax提交**
```html
<form @submit.prevent="saveData">
```
在 `from` 标签上使用 `@submit`，配合 `.prevent` 可以阻止原生submit事件，从而在方法中使用ajax提交。

**关于同一个页面多个$(function())的问题**

经测试 `chrome` 和 `ie11` 都会正确执行多个 `$(function())` 的内容，而 firefox 却不能执行。所以为防止出现问题，请确保一个页面中只有一个。

**ajax缓存问题**

`chrome` 和 `firefox` 刷新时会自动不使用ajax缓存的内容，而 `ie11` 则会取缓存内容。其缓存机制是：如果再次请求的url跟之前的完全相同，则ie会从浏览器缓存中直接取出数据。所以在开发中要么在url中增加 `new Date()`时间戳，要么在ajax请求中增加一项配置：`cache: false`。

**防止页面被其他iframe嵌入**
```js
<script type="text/javascript">
    if (window!=top) // 判断当前的window对象是否是top对象
        top.location.href =window.location.href; // 如果不是，将top对象的网址自动导向被嵌入网页的网址
</script>
```
只要将它放入网页源码的头部，那些流氓就没有办法使用你的网页了。

