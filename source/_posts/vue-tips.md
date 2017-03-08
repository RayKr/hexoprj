---
title: Vue开发小提示
date: 2017-03-07 22:55:14
tags: front-end
categories: front-end
---

### v-for嵌套取index
多层v-for嵌套取各自的index的方法: 给各自的index起名。
```html
<div v-for="(cabin,cabinIndex) in content.data">
    <div v-for="(area, index) in cabin.areas">
        {{cabinIndex}} - {{index}}
    </div>
</div>
```

### 带参数的计算属性替换方案
绑定Class和Style时，从json中获取出值作为参数计算后设置，如果使用计算属性是无法实现的，因为`computed`不能带参数，此时可以用`methods`来实现：
```html
<div :style='devPosition(device.axisX,device.axisY)'></div>
```
```js
var vm = new Vue({
    // ...
    methods: {
        devPosition: function (x, y) {
            return {
                "left": x + "px",
                "top": y + "px"
            };
        }
    }
});
```

### Vue替代$(function())
实现方式可以使用`mounted`，意为在生命周期的挂载完成后执行的内容，并且在方法内可使用jquery的写法。Vue2.0是`mounted`,Vue1.0是`ready`。
```js
var vm = new Vue({
    // ...
    mounted: function () {
        initCurrentPage();
        bundleEvent();
    }
});
```

### 数组中间位置添加/删除值
操作Vue的JSON数组时，为了避免不能正确获取index和length，一般都使用`splice`：
```js
data.areas.splice(index+1, 0, json);
```

### Vue更新DOM后刷新
如果要实现修改了vm的data后，DOM更新结束后立刻调用一些操作（比如jquery的操作等），可以使用`nextTick(callback)`来实现，详细说明查看[异步更新队列](http://cn.vuejs.org/v2/guide/reactivity.html#异步更新队列)：
```js
var vm = new Vue({
  // ...
  methods: {
      copyCabin: function () {
          var vm = this,
              data = vm.content.data,
              index = data.length - 1,
              node = data[index];
          data.push(node);
          // DOM更新后，重载插件绑定事件
          vm.$nextTick(function () {
              enableFunc();
          });
      }
  }
})
```

### JS的Math
四舍五入用`Math.round(x)`，直接舍弃小数取整数用`Math.floor(x)`