---
title: 阮一峰Javascript笔记
date: 2017-05-24 09:25:06
tags:
categories:
---
## Javascript闭包

由于JS的“链式作用域”结构（子对象会一级级向上寻找到父对象的所有变量，而父对象却不能访问子对象的变量），如果想让外部访问方法内的局部变量，则要使用闭包。

闭包就是能够读取其他函数内部变量的函数，在Javascript中，可以理解成“定义在一个函数内部的函数”。

```js
function f1(){
    var n=999;
    function f2(){
        alert(n); 
    }
    return f2;
}
var result=f1();
result(); // 999
```
这段代码中，`f2` 就是闭包。其实在平常的vue使用过程中，关于 `this` 指代 `vm` 的问题上就是闭包。
```js
var name = "The Window";
var object = {
    name : "My Object",
    getNameFunc : function(){
        return function(){
            return this.name;
        };
    }
};
alert(object.getNameFunc()()); // My Object
```
```js
var name = "The Window";
var object = {
    name : "My Object",
    getNameFunc : function(){
        var that = this;
        return function(){
            return that.name;
        };
    }
};
alert(object.getNameFunc()()); // The Window
```
通过以上两个示例，则可以理解闭包的含义。

## 获取网页大小（clientWidth和clientHeight）

```js
function getViewport(){
    if (document.compatMode == "BackCompat"){
        return {
            width: document.body.clientWidth,
            height: document.body.clientHeight
        }
    } else {
        return {
            width: document.documentElement.clientWidth,
            height: document.documentElement.clientHeight
        }
    }
}
```
这个方法就是获取网页（视口）大小，但是要注意：
1. 这个方法必须要在页面加载完成后才能运行，否则document对象还没生成，会报错。
2. 大多数情况 `document.documentElement.clientWidth` 返回正确值，但是IE6是 `document.body.clientWidth`。
3. clientWidth和clientHeight都是只读属性，不能对它们赋值。

获取带滚动条的网页大小的方式
```js
function getPagearea(){
    if (document.compatMode == "BackCompat"){
        return {
            width: Math.max(document.body.scrollWidth,
　　　　　　　　　　　　　　　document.body.clientWidth),
            height: Math.max(document.body.scrollHeight,
　　　　　　　　　　　　　　　　document.body.clientHeight)
        }
    } else {
        return {
            width: Math.max(document.documentElement.scrollWidth,
　　　　　　　　　　　　　　　　document.documentElement.clientWidth),
            height: Math.max(document.documentElement.scrollHeight,
　　　　　　　　　　　　　　　　document.documentElement.clientHeight)
        }
    }
}
```

获取元素位置还可以使用 `getBoundingClientRect()` 方法，它返回一个对象，其中包含了left、right、top、bottom四个属性，分别对应了该元素的左上角和右下角相对于浏览器窗口（viewport）左上角的距离。

```js
var X= this.getBoundingClientRect().left;
var Y =this.getBoundingClientRect().top;
```