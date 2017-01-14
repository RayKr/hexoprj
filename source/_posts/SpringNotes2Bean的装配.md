---
title: Spring Notes：2_Bean的装配
date: 2016-05-07 22:12:05
categories:
- java-framework
tags:
- spring
- java
- framework
---
## 第三章
1. Bean的装配：容器安装要求创建好bean并且传递给代码的过程。
2. 容器首先会调用Bean的无参构造函数初始化Bean。所以养成好习惯，在写带参构造函数的同时，写上无参构造函数，防止spring容器初始化报错。
3. 动态工厂bean：
```xml
<bean id="someFactory" class="com.swroom.factory.SomeFactory" />
<!-- 使用 factory-bean 和 factory-method 实现动态工厂bean -->
<bean id="someService" factory-bean="someFactory" factory-method="getSomeService"/>
```
4. 静态工厂bean：
```xml
<bean id="someService" class="com.swroom.factory.SomeFactory" factory-method="getSomeService"/>
```
5. Bean的作用域
```xml
<!--
    scope作用域：
    prototype(原型模式)，使用时才会由容器创建
    singleton(单例模式)，容器初始化时就会创建，为默认形式
-->
<bean id="someService1" class="com.swroom.service.SomeServiceImpl" init-method="initPost" destroy-method="preDestroy"/>
<bean id="someService2" class="com.swroom.service.SomeServiceImpl"/>
```
6. Bean后处理器：
```xml
<!-- 注册bean后处理器 -->
<bean class="com.swroom.MyBeanPostProcessor" />
```
* 注册bean后处理器没有id
* 实现BeanPostProcessor接口，在bean初始化所有属性之前/后执行两个方法