---
title: Spring Notes：1_初识与理解
date: 2016-05-07 22:01:58
categories:
- java-framework
tags:
- spring
- java
- framework
---
## 第一章
1. Spring是为了降低代码`耦合度`的。
2. 主业务逻辑代码用`IoC`，注入； 系统级逻辑代码用`AOP`，织入。
3. full-stack(一站式)：视图层，service层，DAO层都有涉及。
4. 首先，spring是个`容器`。里面放着对象，以及对象之间的关联关系。
5. Spring在三层架构中充当着总管的角色。
6. IoC,控制反转，当我使用一个对象的时候，不是我new出来的，而是spring容器注入给我的，我是被动的。

## 第二章
1. Ioc（控制反转）是一种思想，不是实现。有两种实现方式：`DL`（依赖查找）和`DI`（依赖注入）。
2. DI是目前最优秀的解耦方式。
3. ApplicationContext容器和BeanFactory容器的区别：
    * `ApplicationContext`：在初始化容器时就，就将容器内的所有对象进行了创建。
    * `BeanFactory`：当使用时才创建。

    两者各有利弊，现在主流使用ApplicationContext容器。