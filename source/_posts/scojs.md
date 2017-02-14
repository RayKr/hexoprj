---
title: Sco.js - Bootstrap Javascript增强插件
date: 2017-02-14 22:54:40
tags:
- front-end
categories:
- front-end
---
`Sco.js`是个很有意思的js插件，在原生bootstrap一些并不支持的地方有所增强，例如模态窗口加载远程数据、tooltip提示、确认对话框、Tab页增强、Message通知（类似Alert）等等有趣且实用的功能。

## 功能组件
### 模态对话框（Modal） 
[sco.modal.js](http://www.bootcss.com/p/sco.js/#modals) 支持加载远程内容：
```html
<a data-trigger="modal" href="lipsum.html" data-title="Modal title" class="btn">Launch demo modal</a>
```

### 确认对话框（Confirm）
[sco.confirm.js](http://www.bootcss.com/p/sco.js/#confirm) 点击取消返回，点击确定可转向链接
```html
<a data-trigger="confirm" href="/delete/me" class="btn">Launch demo confirm</a>
```

### 折叠隐藏（Collapse）
[sco.collapse.js](http://www.bootcss.com/p/sco.js/#collapse) 点击隐藏/显示内容
```html
<a href="#" data-trigger="collapse">Click for default behaviour</a>
<div class="collapsible hide">
  Lorem ipsum...
</div>
```
<!-- more --> 
### 标签页（Tab）
[sco.tab.js](http://www.bootcss.com/p/sco.js/#tab)
```html
<ul class="nav nav-tabs" data-trigger="tab">
  <li><a href="#">Tab 1</a></li>
  <li><a href="#">Tab 2</a></li>
  <li><a href="#">Tab 3</a></li>
  <li><a href="#">Tab 4</a></li>
</ul>
<div class="pane-wrapper">
  <div>...</div>
  <div>...</div>
  <div>...</div>
  <div>...</div>
</div>
```

### 小提示（Tooltip）
[sco.tooltip.js](http://www.bootcss.com/p/sco.js/#tooltip)
```html
<a href="#" data-trigger="tooltip" data-content="Lorem ipsum dolor">Hover me</a>
```

### 消息通知（Message）
[sco.message.js](http://www.bootcss.com/p/sco.js/#message)
```js
$.scojs_message(message, type);
```

### 验证（Valid） 
[sco.valid.js](http://www.bootcss.com/p/sco.js/#valid) 算得上是sco中最复杂的插件，一款form validate插件，提供了十几种常用的验证规则，只需按文档编写即可，简单易用。
```js
$('#form').scojs_valid({rules: options, messages: options});
```

### 多面板（Panes）
[sco.panes.js](http://www.bootcss.com/p/sco.js/#panes) 该插件是Tab插件的核心，可以用来实现滚动图
```js
var $panes = $.scojs_panes(selector, options);
```

### Ajax（Ajax）
[sco.ajax.js](http://www.bootcss.com/p/sco.js/#ajax) 非常实用的插件，如果我想实现页面中的部分链接是用ajax获取远程数据而并不是所有链接，则可以使用该插件轻松实现。也可以用来实现页面的局部刷新功能。
```html
<a data-trigger="ajax" href="lipsum.html" data-target="#ajax_target" class="btn">Ajax demo</a>
```

### 倒计时（Countdown）
[sco.countdown.js](http://www.bootcss.com/p/sco.js/#countdown)
```js
$('#target').scojs_countdown(options);
```

## 附录
* [Sco.js中文文档](http://www.bootcss.com/p/sco.js)