---
title: Html5知识点总结
date: 2016-07-04 10:45:32
tags:
- 前端
---
## 图片边框样式
```css
.thick-green-border {
    border-color: green;
    border-width: 10px;
    border-style: solid;
    border-radius: 10px;
}
```
其中`border-radius`是个非常重要的属性，可以控制border的弯曲程度，比如使用圆形头像，则可以使用这个属性控制。
```css
/* 50%可以控制为圆形头像 */
.thick-green-border {
    border-radius: 50%;
}
```

## 使用哈希标签#创建死链
通常，很多时候使用`jQuery`来控制链接的行为，所以自身的链接`href`属性需要指向一个死链:`href="#"`

## input标签
1. 当input默认为空时的提示语，使用`placeholder="this is a placeholder"`属性。
2. 要求某个input验证不能为空，则使用`required`属性即可。

## label的神奇
在单选`radio`和多选`checkbox`时候，以往的操作都必须是选择该控件本身才可以，但是往往我们会想着通过点击该控件的文字提示也可以触发选择。此时就应该应用<label>标签了。
`<label>`标签为`input`元素定义标签（label）。
label 元素不会向用户呈现任何特殊的样式。不过，它为鼠标用户改善了可用性，因为如果用户点击 label 元素内的文本，则会切换到控件本身。
<label> 标签的 for 属性应该等于相关元素的 id 元素，以便将它们捆绑起来。
使用方法如下：
```html
<form>
    <label for="male">Male</label>
    <input type="radio" name="sex" id="male" />
    <br />
    <label for="female">Female</label>
    <input type="radio" name="sex" id="female" />
</form>
<!-- or -->
<form>
    <label>Male <input type="radio" name="sex" id="male" /></label>
    <br />
    <label>Female <input type="radio" name="sex" id="female" /></label>
</form>
```

## 关于padding和margin单行样式顺序
```css
padding: 40px 20px 20px 40px;
```
顺序是按照`顺时针`，依次是`padding-top`，`padding-right`，`padding-bottom`，`padding-left`。
margin同理。

## 关于样式覆盖的顺序
1. 都为`class`时，写在后面的样式覆盖掉前面的样式。
2. `id`的样式覆盖掉`class`的样式。
3. `行内样式`样式覆盖掉`id`的样式。
4. `!important`关键字可以覆盖掉其他所有样式。
总结：优先级 `!important` > `inline-style` > `#` > `.`
