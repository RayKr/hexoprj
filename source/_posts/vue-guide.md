---
title: Vue.js 基础
date: 2017-02-15 08:56:17
tags: front-end
categories: front-end
---

> [**Vue.js 2.0 文档**](https://vuejs.bootcss.com/v2/guide/installation.html)

## 模板语法
### 插值（数据绑定）
#### 文本
数据绑定的最常见形式，就是使用“Mustache”语法（双大括号）：
```html
<span>Message: {{ msg }}</span>
```
此时`msg`的值有变化，插值处的内容就会被更新，文本模式还可用`v-text`来绑定文本值：
```html
<span v-text="msg"></span>
```
除此之外，可以使用`v-once`指令，实现一次性插值，数据改变时不再更新内容，但是要注意会影响该标签内的所有数据绑定：
```html
<span v-once>This will never change: {{ msg }}</span>
```
使用`v-modal`来进行数据的双向绑定：
```html
<span>{{ message }}</span>
<input type="text" v-model="message">
```
当input的值变化是，message显示的内容也会变，反过来如果message的值变化，input框内的内容也会随之改变。

#### 纯HTML
使用“Mustache”语法只能绑定纯文本的数据，如果要输出真正的HTML，则需使用`v-html`指令：
```html
<div v-html="rawHtml"></div>
```
> 注：在站点上动态渲染的任意HTML可能会很危险，容易导致XSS攻击。

#### 属性
“Mustache”不能应用在HTML标签的属性值绑定上，应使用`v-bind`指令：
```html
<div v-bind:id="dynamicId"></div>
<!-- 缩写 -->
<div :id="dynamicId"></div>
```
> 注：此方法针对布尔值的属性也可用（例如disabled），当值为false时，该属性会被移除。

#### Javascript表达式
Vue.js提供了数据绑定时使用Javascript表达式的功能：
```html
{{ number + 1 }}
{{ ok ? 'YES' : 'NO' }}
{{ message.split('').reverse().join('') }}
<div v-bind:id="'list-' + id"></div>
```
以上的写法，会先解析出Javascript表达式的值，然后进行绑定数据。但是必须注意一点，每个绑定只能包含**单个表达式**，条件流控制可以使用**三元表达式**替换。

### 指令
指令（Directives）是带有 `v-` 前缀的特殊属性。指令属性的值预期是**单一JavaScript表达式**。指令的职责就是当其表达式的值改变时相应地将某些行为应用到 DOM 上。

#### 参数
一些指令能接受一个“参数”，在指令后以冒号指明。
例如`v-bind`是用来响应地更新HTML属性：
```html
<a v-bind:href="url"></a>
```
`href`是参数，告知`v-bind`将该元素的`href`属性与表达式的`url`值绑定。

另外`v-on`指令用来监听DOM事件：
```html
<a v-on:click="doSomething">
<!-- 缩写 -->
<a @click="doSomething">
```

#### 修饰符
修饰符（Modifiers）是以半角句号`.`指明的特殊后缀，用于指出一个指令应该**以特殊方式绑定**。

例如，`.prevent` 修饰符告诉 `v-on` 指令对于触发的事件调用 `event.preventDefault()`：
```html
<form v-on:submit.prevent="onSubmit"></form>
```

### 过滤器
过滤器可以用在**Mustache插值**和**v-bind表达式**。

过滤器应该被添加在**Javascript表达式尾部**，由**管道符**（|）指示：
```html
<!-- in mustaches -->
{{ message | capitalize }}
<!-- in v-bind -->
<div v-bind:id="rawId | formatId"></div>
```
```js
new Vue({
  // 过滤器接收表达式的值作为第一参数
  filters: {
    capitalize: function (value) {
      if (!value) return ''
      value = value.toString()
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
  }
})
```
> Vue2.0版本中，过滤器只能应用于上述两种情境，只作为文本转换。更加复杂的数据变化，请使用计算属性。

过滤器可以串联：
```html
{{ message | filterA | filterB }}
```
过滤器是 JavaScript 函数，因此可以接受参数：
```html
{{ message | filterA('arg1', arg2) }}
```
由于表达式的值总是第一参数，所以arg1是第二参数，arg2是第三参数。

## 计算属性
为了避免在模板中放入过多的逻辑，导致难以维护，所以复杂逻辑都应当放在计算属性里。Vue的计算属性基础例子如下：
```html
<div id="example">
  <p>Original message: "{{ message }}"</p>
  <p>Computed reversed message: "{{ reversedMessage }}"</p>
</div>
```
```js
var vm = new Vue({
  el: '#example',
  data: {
    message: 'Hello'
  },
  computed: {
    // 作为计算属性的getter方法
    reversedMessage: function () {
      // `this`指向的是vm实例
      return this.message.split('').reverse().join('')
    }
  }
})
```
这里我们声明了一个计算属性 `reversedMessage` 。我们提供的函数将用作属性 `vm.reversedMessage` 的 `getter` 。

其中计算缓存与 Methods 的区别是：**计算属性是基于它的依赖缓存**。计算属性只有在相关依赖内容发生改变时，才会重新取值。但是Methods每次都执行。

### 计算setter
```js
computed: {
  fullName: {
    // getter
    get: function () {
      return this.firstName + ' ' + this.lastName
    },
    // setter
    set: function (newValue) {
      var names = newValue.split(' ')
      this.firstName = names[0]
      this.lastName = names[names.length - 1]
    }
  }
}
```
现在在运行 `vm.fullName = 'John Doe'` 时， `setter` 会被调用， `vm.firstName` 和 `vm.lastName` 也会被对应更新。



