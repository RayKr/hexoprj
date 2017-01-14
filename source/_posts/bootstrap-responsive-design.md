---
title: Bootstrap Responsive Design
date: 2016-07-07 14:28:11
tags:
- html5
- 前端
---
## 基本使用方法
1. 将所有的element放到`<div class="container-fluid">`中。
2. 图片自动响应式布局，使用`class="img-responsive"`
3. 需引用bootstrap样式：
```html
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"/>
```

## Button
button使用方法
```html
<button class="btn">Like</button>
```
使整个button长度充满水平宽度,则使用`btn-block`
```html
<button class="btn btn-block">Like</button>
```
button彩虹色按钮
```html
<button class="btn btn-default">默认按钮</button>
<button class="btn btn-primary">原始按钮</button>
<button class="btn btn-success">成功按钮</button>
<button class="btn btn-info">信息按钮</button>
<button class="btn btn-warning">警告按钮</button>
<button class="btn btn-danger">危险按钮</button>
<button class="btn btn-link">链接按钮</button>
```
如下图所示：
![彩虹按钮](http://www.runoob.com/wp-content/uploads/2014/06/buttonoptions_demo.jpg)

## Bootstrap Grid
Bootstrap提供了网格，用来规范化显示元素，主要使用`col-md-*`样式。其中`md`代表`medium(居中)`，`*`代表占据列数的宽度。
```html
<div class="row">
    <div class="col-xs-4">
      <button class="btn btn-block btn-primary">Like</button>
    </div>
    <div class="col-xs-4">
      <button class="btn btn-block btn-info">Info</button>
    </div>
    <div class="col-xs-4">
      <button class="btn btn-block btn-danger">Delete</button>
    </div>
</div>
```

## Font Awesome
```html
<i class="fa fa-thumbs-up"></i>
```