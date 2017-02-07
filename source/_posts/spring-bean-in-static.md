---
title: Java静态类中调用Spring的Bean实例实现方法
date: 2017-02-07 09:21:23
tags:
- java
categories:
- java
---

Spring的注入给我们带来了极大的便利，默认创建的bean也为单例。但是Spring的注入有个很大的问题是普通类没办法获得Bean文件中的bean实例。如果是在Web项目中的servlet环境中可以通过`WebApplicationContextUtils`实现。但是普通类就不好处理。此时需要使用下面这种方式实现：

方案一：
```java
public class Enums {

    private EnumService enu; // Spring Bean
    private static Enums es; // 静态对象

    // setter方法用以实现注入
    public void setEnu(EnumService enu) {
        this.enu = enu;
    }

    // 对应xml里配置的init-method
    public void init() {
        es = this;
        es.enu = this.enu;
    }

    public static String getEnuName(String type,String value) {
        return es.enu.getEnu(type, value);
    }
    
    public static void reload() {
        es.enu.reload();
    }
}
```
`applicationContext.xml`配置文件中配置如下：
```xml
<!-- 实现静态类中调用Spring的Bean实例 -->
<bean id="enums" class="com.swroom.genealogy.common.Enums" init-method="init">
    <property name="enu" ref="enumService"/>
</bean>
```
<!-- more -->

方案二：
```java
public class Enums {

    private static EnumService enu; // Spring Bean

    // setter方法用以实现注入
    public void setEnu(EnumService enu) {
        this.enu = enu;
    }

    public static String getEnuName(String type,String value) {
        return enu.getEnu(type, value);
    }

    public static void reload() {
        enu.reload();
    }
}
```
而此时配置文件中只需注入而不再需要初始化方法：
```xml
<!-- 实现静态类中调用Spring的Bean实例 -->
<bean id="enums" class="com.swroom.genealogy.common.Enums">
    <property name="enu" ref="enumService"/>
</bean>
```

这样就可以实现静态类中调用spring bean了。

注意事项：
1. 一定要用初始化方法，如果没有`init`方法直接在构造器中，如果用`new()`实例就会出现问题
2. `this`一定也只能在`init`方法中使用,不能在构造器中使用
3. 不要去实现Spring的相关接口，而应当使用初始化方法

