---
title: No enclosing instance of type E is accessible.
date: 2017-01-18 19:27:44
tags:
- exception
- bug
categories:
- experience
---
## 异常明细
> No enclosing instance of type E is accessible. Must qualify the allocation with an enclosing instance of type E(e.g.  x.new A() where x is an instance of E).
没有可访问的内部类E的实例，必须分配一个合适的内部类E的实例（如x.new A()，x必须是E的实例。）


```java
public class Outer {
    class Inner {
        int m = 10;
    }
    public static void main(String[] args) {
        Inner inner = new Inner(); // 此处报错
        System.out.println(inner.m);
    }
}
```
<!-- more -->
## 异常分析
Inner类是Outer类的**动态内部类**，也叫**成员内部类**。由于是在静态main方法中调用，而静态方法是不能调用动态方法的。

这就牵扯到成员内部类调用方式的编写问题，修改此程序有三种解决方案：
* 将Inner类写成单独的类文件，不再是Outer类的内部类。
* 将Inner类使用`static`修饰为静态内部类，则可以在静态方法中调用。
* 前两种方法不再赘述，第三种方法不修改两个类，而是修改类创建时的写法。如下
```java
Inner inner = new Outer.new Inner();
```
一个静态方法如果调用一个类Outer的动态内部类Inner，首先你要实例化这个Outer类，然后用这个对象去new这个动态内部类。

## 知识拓展
Q. 什么时候会使用成员内部类？
A. 当两个类是**一对一**，并且是**has-a**的关系，即内部类依附于外部类，没有了外部类便失去意义的情况时，可以使用成员内部类的写法。

相关知识点：
![inner-class](http://swroom.qiniudn.com/inner-class.png)

## 附录
1. [stackoverflow - Must qualify the allocation with an enclosing instance of type GeoLocation](http://stackoverflow.com/questions/9744639/must-qualify-the-allocation-with-an-enclosing-instance-of-type-geolocation)

2. [Java出现No enclosing instance of type E is accessible. Must qualify the allocation with an enclosing](http://blog.csdn.net/sunny2038/article/details/6926079)