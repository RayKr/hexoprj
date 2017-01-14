---
title: $.getJSON()不同写法就可导致样式失效
date: 2016-07-16 00:48:41
tags:
- bug
---
正确写法如下：
```js
$.getJSON(url, function (result) {
    var showlist = $('<ul class="nav" id="side-menu"></ul>');
    showMenu(result, showlist);

    var $nav = $('<div id="navigation"></div>');
    $nav.append(showlist).appendTo(element);

    // 导航栏使用metisMenu，官方文档：http://mm.onokumus.com/
    $nav.metisMenu();
});
```
其中关于`$.getJSON`的写法若采用下面这种：
```js
$.getJSON(url, function (result) {
    var showlist = $('<ul class="nav" id="side-menu"></ul>');
    showMenu(result, showlist);
});

var $nav = $('<div id="navigation"></div>');
$nav.append(showlist).appendTo(element);

// 导航栏使用metisMenu，官方文档：http://mm.onokumus.com/
$nav.metisMenu();
```
则会导致`metisMenu`手风琴效果不能实现。

经过思考，斯认为问题还是出在`回调函数`身上。当执行`$.getJSON`之后立马执行`$nav.metisMenu`，此时可能回调函数还没进行DOM生成。
当回调函数生成DOM后，样式是无法生效的。
所以`showMenu`这个方法必须与`metisMenu()`是同步的，不能是异步的。所以必须都放到回调函数内部。