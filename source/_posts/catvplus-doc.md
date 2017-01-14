---
title: CATVPlus Documents
date: 2016-07-16 01:20:23
tags:
- documents
---
## Menu.菜单
**描述**：支持接收JSON格式的数据，自动生成树形菜单。
JSON格式：
```json
[{
"mid": "M001","mname": "首页","url": "http://blog.swroom.com","tag": "NEW","submenu": []},{
"mid": "M002","mname": "车辆买卖","url": "#","tag": "","submenu": [{
"mid": "M003","mname": "新车","url": "#","tag": "","submenu": []},{
"mid": "M004", "mname": "二手车","url": "#","tag": "","submenu": []},{
"mid": "M005","mname": "改装车","url": "#","tag": "","submenu": []}]
}]
```
使用方法：
```html
<script src="/assets/js/common.js"></script>
...
<aside id="menu"></aside>
```
```js
// 两个参数分别为：请求数据的url，需要绑定到的元素
$(function () {
    InitMenu("/init/menu", $("#menu"));
});
```

## Carousel.图片轮播
