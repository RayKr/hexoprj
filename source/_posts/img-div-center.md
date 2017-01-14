---
title: img图片在div中水平居中的实现方法
date: 2016-07-18 19:55:44
tags:
- css
---
原理：
利用图片的`margin`属性将图片水平居中，利用`div`的`padding`属性将图片垂直居中。

实现：
```css
img {
    display:block; 
    margin:0 auto;
}
```

备注：
img是内联元素，要设置其margin属性使其居中，就要将其转换为块元素display:block;然后利用margin:0 auto;实现图片的水平居中。

