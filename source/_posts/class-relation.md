---
title: Java中类与类之间的几种关系
date: 2017-01-19 10:22:40
tags: 
- java
categories: 
- java
---

## 继承关系
**定义**： 一个类（子类）继承另外一个类（基类）功能，并可以增加自己新功能。
**代码**：`extends`标识
**UML类图**： 空心三角箭头的实线，从子类指向基类。
![extends](http://swroom.qiniudn.com/extends.jpg)

## 实现关系
**定义**： 一个类实现一个或多个接口功能。
**代码**：`implements`标识
**UML类图**： 空心三角箭头的虚线，从类指向接口。
![implements](http://swroom.qiniudn.com/implements.jpg)

<!-- more -->

## 依赖关系
**定义**： 类A使用到类B，偶然性、临时性的弱关联，B的变化会影响A。
**代码**：类B作为参数被类A中某个方法中使用。
```java
class A {
    // 类B作为类A方法的形参，则A依赖于B
    void doSomething(B b) {}
}
```
**UML类图**： 带箭头的虚线，从类A指向类B。
![yilai](http://swroom.qiniudn.com/yilai.jpg)

## 关联关系
**定义**：强依赖关系，类A与类B一般是平等的，关系是长久的。关联可以是单向、双向的。
**代码**：类B以类的属性形式出现在类A中。
```java
class A {
    // 类B作为类A的属性，则A和B是关联关系
    private B b;
}
```
**UML类图**：带箭头的实线，从类A指向类B。
![guanlian](http://swroom.qiniudn.com/guanlian.jpg)

## 聚合关系
**定义**：聚合关系是关联关系的一种特例。体现整体与部分的关系，即`has-a`的关系。整体和部分可分离，具有各自生命周期。
**代码**：与关联关系一致
```java
class Family {
    // has-a关系，可分离。
    private Child child;
}
```
**UML类图**：空心菱形加实线箭头，从整体指向部分。
![juhe](http://swroom.qiniudn.com/juhe.jpg)

## 组合关系
**定义**：组合关系也是关联关系中的一种特列。体现的是`contains-a`的关系，比聚合关系更强，也称为强聚合。体现整体与部分关系，但是不可分离，部分脱离整体不可存在，整体的生命周期结束，则部分的生命周期也随之结束。
**代码**：与关联关系一致
```java
class Person {
    // contains-a关系，不可分离，部分离开整体无法存活。
    private Head head;
}
```
**UML类图**：实心菱形加实线箭头，从整体指向部分。
![zuhe](http://swroom.qiniudn.com/zuhe.jpg)

## 总结
1. 继承、实现体现的是类与类之间的**纵向**关系。
2. 依赖、关联、聚合、组合体现的是类与类之间的**横向**关系。
3. 关系关联的强度依次：组合 > 聚合 > 关联 > 依赖。
