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

## Class 与 Style 绑定

### 绑定Class
关键字`v-bind:class`
#### 对象语法
```html
<!-- 对象写在行内 -->
<div v-bind:class="{ active: isActive, 'text-danger': hasError }"></div>
<!-- 直接绑定一个对象 -->
<div v-bind:class="classObject"></div>
```
```js
// 根据布尔类型的值，动态绑定class
data: {
  isActive: true,
  hasError: false
}

// 对象的写法
data: {
  classObject: {
    active: true,
    'text-danger': false
  }
}

// 配合计算属性一起使用
data: {
  isActive: true,
  error: null
},
computed: {
  classObject: function () {
    return {
      active: this.isActive && !this.error,
      'text-danger': this.error && this.error.type === 'fatal',
    }
  }
}
```

#### 数组语法
```html
<div v-bind:class="[activeClass, errorClass]">
```
```js
data: {
  activeClass: 'active',
  errorClass: 'text-danger'
}
```
在条件中切换class可用三元表达式：
```html
<div v-bind:class="[isActive ? activeClass : '', errorClass]">
```
数组语法中也可以使用对象：
```html
<div v-bind:class="[{ active: isActive }, errorClass]">
```

#### 用在组件中
当你在一个定制的组件上用到 class 属性的时候，这些类将被添加到根元素上面，这个元素上已经存在的类不会被覆盖。
组件：
```js
Vue.component('my-component', {
  template: '<p class="foo bar">Hi</p>'
})
```
模板：
```html
<my-component class="baz boo"></my-component>
```
渲染：
```html
<p class="foo bar baz boo">Hi</p>
```

### 绑定内联样式Style
与绑定HTML Class写法相似，也包含对象语法和数组语法，并且在数组语法中添加对象，也可以结合计算属性使用。同时Vue会自动添加样式前缀。关键字是`v-bind:style`

#### 对象语法
```html
<div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>

<!-- 为保证模板更清晰，推荐使用直接绑定一个对象 -->
<div v-bind:style="styleObject"></div>
```
```js
data: {
  activeColor: 'red',
  fontSize: 30
}
// or
data: {
  styleObject: {
    color: 'red',
    fontSize: '13px'
  }
}
```

#### 数组语法
```html
<div v-bind:style="[baseStyles, overridingStyles]">
```

## 条件渲染
条件渲染有两种 `v-if` 和 `v-show`，都可以实现根据条件的真假，是否渲染DOM元素。

### v-if
有三个关键字：`v-if`, `v-else`, `v-else-if`，其中`v-else`和`v-else-if`必须有前置对应的关键字，否则不能被识别。
```html
<div v-if="type === 'A'"> A </div>
<div v-else-if="type === 'B'"> B </div>
<div v-else-if="type === 'C'"> C </div>
<div v-else> Not A/B/C </div>
```
`v-if条件组`，可以使用 `template` 标签包装，此时其内的所有DOM元素都统一切换。
```html
<template v-if="ok">
  <h1>Title</h1>
  <p>Paragraph 1</p>
  <p>Paragraph 2</p>
</template>
```

### v-show
用法与 `v-if` 一样，不同的是 `v-show` 元素始终渲染并保持，只是切换元素的CSS属性 `display`
```html
<h1 v-show="ok">Hello!</h1>
```

### v-if 与 v-show 对比
v-if 是真实的条件渲染，因为它会确保条件块在切换当中适当地销毁与重建条件块内的事件监听器和子组件。

v-if 也是惰性的：如果在初始渲染时条件为假，则什么也不做——在条件第一次变为真时才开始局部编译（编译会被缓存起来）。

相比之下， v-show 简单得多——元素始终被编译并保留，只是简单地基于 CSS 切换。

一般来说， v-if 有更高的切换消耗而 v-show 有更高的初始渲染消耗。因此，如果需要频繁切换使用 v-show 较好，如果在运行时条件不大可能改变则使用 v-if 较好。

