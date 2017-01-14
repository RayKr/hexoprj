---
title: Vue.js上手
date: 2016-07-21 00:11:20
tags:
- html
- vue
---

> 启蒙链接： [Vue.js——60分钟快速入门](http://www.cnblogs.com/keepfool/p/5619070.html)

> Vue.js是当下很火的一个JavaScript MVVM库，它是以数据驱动和组件化的思想构建的。相比于Angular.js，Vue.js提供了更加简洁、更易于理解的API，使得我们能够快速地上手并使用Vue.js。
如果你之前已经习惯了用jQuery操作DOM，学习Vue.js时请先抛开手动操作DOM的思维，因为Vue.js是数据驱动的，你无需手动操作DOM。它通过一些特殊的HTML语法，将DOM和数据绑定起来。一旦你创建了绑定，DOM将和数据保持同步，每当变更了数据，DOM也会相应地更新。

下面将结合我个人的实际项目，简述vue基础使用方法：
```html
<div id="carousel-generic" class="carousel slide">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li class="{{ $index == 0 ? 'active' : '' }}" data-target="#carousel-generic" data-slide-to="{{ $index }}" v-for="car in carousels"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        <div class="item {{ $index == 0 ? 'active' : '' }}" v-for="car in carousels">
            <img v-if="car.src" :src="car.src" alt="{{ car.alt }}" onclick="javascript:redirect('{% raw %}{{{% endraw %} car.url }}')">
            <img v-else :src="defsrc" alt="{{ car.alt }}" onclick="javascript:redirect('{% raw %}{{{% endraw %} car.url }}')">
            <div class="carousel-caption">
                <h3 class="carousel-title">{{ car.title }}</h3>
                <p>{{ car.description }}</p>
            </div>
        </div>
    </div>
</div>
```
1. `v-for`用法为`v-for="item in items"`，它所在的标签即为循环生成的最外层标签。
2. `v-for`有两个非常有用的特殊变量：`$index`，它表示当前数组元素的索引，`$key`表示当前对象的key值。
3. `{% raw %}{{{% endraw %} {% raw %}}}{% endraw %}`中可以写表达式，但是只能是单表达式，但是可以用`三元表达式`来实现流程控制语句。
4. `v-if`和`v-show`功能类似，区别在于`v-if`不生成不符合条件的元素，而`v-show`生成元素但是不显示。
5. `v-else`必须配合`v-if`或`v-show`使用。
6. `src`很特殊，不可以直接使用`src="{% raw %}{{{% endraw %}car.url{% raw %}}}{% endraw %}"`，是vue的安全策略限制，必须使用`v-bind:src="car.url"`的方式。注意，此时不需要双花括号。
7. `html`自带属性用`v-bind`，事件监听用`v-on`。
8. `v-bind`简写`:`，`v-on`简写`@`。

以上便是`html`模板内容，接下来便是`js`中的使用方法：
```js
new Vue({
    el: '#' + id,
    data: {
        defsrc: '/upload/images/carousel/blank.jpg',
        carousels: data
    }
});
```
-------

## v-if vs v-show
v-if 也是惰性的：如果在初始渲染时条件为假，则什么也不做——在条件第一次变为真时才开始局部编译（编译会被缓存起来）。
相比之下，v-show 简单得多——元素始终被编译并保留，只是简单地基于 CSS 切换。
一般来说，v-if 有更高的切换消耗而 v-show 有更高的初始渲染消耗。因此，如果需要频繁切换 v-show 较好，如果在运行时条件不大可能改变 v-if 较好。

