---
title: JavaScript温习笔记
date: 2016-06-17 16:06:21
tags: javascript
---

JavaScript严格区分大小写。

**Number类型**
```js
NaN; // NaN表示Not a Number，当无法计算结果时用NaN表示
Infinity; // Infinity表示无限大，当数值超过了JavaScript的Number所能表示的最大值时，就表示为Infinity
```

字符串可以用双引号，也可用单引号。

**比较运算符**
第一种是`==`比较，它会自动转换数据类型再比较，很多时候，会得到非常诡异的结果；
第二种是`===`比较，它不会自动转换数据类型，如果数据类型不一致，返回false，如果一致，再比较。
由于JavaScript这个设计缺陷，不要使用==比较，始终坚持使用===比较。

`NaN`很特殊，它与所有的值都不相等，包括它自己。`NaN === NaN //false`
唯一能判断`NaN`的是通过`isNaN()`函数：`isNaN(NaN) //true`

浮点数的比较思路：只能计算它们之差的绝对值，看是否小于某个阈值
```js
Math.abs(1 / 3 - (1 - 2 / 3)) < 0.0000001; // true
```

数组，出于代码的可读性考虑，强烈建议直接使用`[]`。

对象，是一组有`key-value`组成的`无序`集合。

变量
变量的命名只能有 大小写字母、数字、`$`和`_`，并且不能用数字开头。

同一个变量可多次赋值，而且可以是不同类型。

js设计之初重大缺陷：如果变量不`var`声明就使用，则默认为全局变量，导致多个同名全局变量产生错误。为了弥补这一缺陷，可以启用strict模式：
```js
// 在JavaScript代码的第一行写上
'use strict';
```

中文：`'\u4e2d\u6587'`

多行字符串，使用\`...\`
```js
alert(`多行
字符串
测试`);
```

字符串不可变，如果针对某个索引赋值，原值不会变。

`indexOf()`函数，用来搜索指定字符串出现的位置，找不到是返回`-1`

js的数组不会出现越界异常，会自动修改范围。

数组的函数：
`slice()`就是对应`String`的`substring()`版本，它截取`Array`的部分元素，然后返回一个新的`Array`

`push()`向Array的末尾添加若干元素，`pop()`则把Array的最后一个元素删除掉

`unshift()`向Array的头部添加若干元素，`shift()`方法则把Array的第一个元素删掉

`sort()`可以对当前Array进行排序，它会直接修改当前Array的元素位置

`reverse()`把整个Array的元素给掉个，也就是反转

`splice()`方法是修改Array的“万能方法”，它可以从指定的索引开始删除若干元素，然后再从该位置添加若干元素：
```js
var arr = ['Microsoft', 'Apple', 'Yahoo', 'AOL', 'Excite', 'Oracle'];
// 从索引2开始删除3个元素,然后再添加两个元素:
arr.splice(2, 3, 'Google', 'Facebook'); // 返回删除的元素 ['Yahoo', 'AOL', 'Excite']
arr; // ['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']
// 只删除,不添加:
arr.splice(2, 2); // ['Google', 'Facebook']
arr; // ['Microsoft', 'Apple', 'Oracle']
// 只添加,不删除:
arr.splice(2, 0, 'Google', 'Facebook'); // 返回[],因为没有删除任何元素
arr; // ['Microsoft', 'Apple', 'Google', 'Facebook', 'Oracle']
```

`concat()`方法把当前的Array和另一个Array连接起来，并返回一个新的Array：
```js
var added = arr.concat([5, 6, 7]);

var arr = ['A', 'B', 'C'];
arr.concat(1, 2, [3, 4]); // ['A', 'B', 'C', 1, 2, 3, 4]
```

`join()`方法是一个非常实用的方法，它把当前Array的每个元素都用指定的字符串连接起来，然后返回连接后的字符串：
```js
var arr = ['A', 'B', 'C', 1, 2, 3];
arr.join('-'); // 'A-B-C-1-2-3'
```

如果if的条件判断语句结果不是true或false怎么办？例如：
```js
var s = '123';
if (s.length) { // 条件计算结果为3
    //
}
```
JavaScript把`null`、`undefined`、`0`、`NaN`和空字符串`''`视为`false`，其他值一概视为`true`，因此上述代码条件判断的结果是true。