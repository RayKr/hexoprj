---
title: Spring Notes：3_XML方式DI注入
date: 2016-05-09 14:44:58
categories:
- java-framework
tags:
- spring
- java
- framework
---

## 设值注入
```xml
<!-- 设值注入方式：实际是调用bean的setter方法-->
<bean id="mySchool" class="com.swroom.di01.School">
    <property name="name" value="青岛大学" />
</bean>

<bean id="student" class="com.swroom.di01.Student">
    <property name="name" value="张三"/>
    <property name="age" value="23"/>
    <property name="mySchool" ref="mySchool"/>
</bean>
```

## 构造注入
```xml
<!-- 设值注入方式：实际是调用bean的setter方法-->
<bean id="mySchool" class="com.swroom.di02.School">
    <property name="name" value="青岛大学" />
</bean>

<!-- 构造注入方式： 调用bean的带参构造函数 -->
<bean id="student" class="com.swroom.di02.Student">
    <constructor-arg name="name" value="李四"/>
    <constructor-arg name="age" value="24"/>
    <constructor-arg name="mySchool" ref="mySchool"/>
</bean>
```

<!--more-->

## p命名空间设值注入方式 和 c命名空间构造器注入方式
```xml
<!-- p命名空间设值注入方式 -->
<bean id="mySchool" class="com.swroom.di03.School" p:name="南京大学"/>
<!-- c命名空间构造器注入方式 -->
<bean id="student" class="com.swroom.di03.Student" c:name="王五" c:age="33" c:school-ref="mySchool" />
```

## 集合属性注入
```xml
<bean id="student" class="com.swroom.di04.Student">
    <property name="name" value="景昀"/>
    <property name="mySchool" ref="mySchool1"/>
    <property name="age" value="26"/>
    <property name="mList">
        <list>
            <ref bean="mySchool1"/>
            <ref bean="mySchool2"/>
        </list>
    </property>
    <property name="mMap">
        <map>
            <entry key="QQ" value="371929908" />
            <entry key="Email" value="jingzonglei@163.com" />
        </map>
    </property>
    <property name="mSets">
        <set>
            <value>中国</value>
            <value>山东</value>
            <value>青岛</value>
        </set>
    </property>
    <property name="mStrs">
        <array>
            <value>山东</value>
            <value>济南</value>
            <value>找蓝翔</value>
        </array>
    </property>
    <property name="mPros">
        <props>
            <prop key="address">青岛广电大厦</prop>
            <prop key="link">swroom.com</prop>
        </props>
    </property>
</bean>
```

## SpEL表达式注入
```xml
<bean id="student1" class="com.swroom.di05.Student">
    <property name="name" value="张三"/>
    <property name="age" value="#{T(java.lang.Math).random() * 70}"/>
    <property name="mySchool" ref="mySchool"/>
</bean>
<bean id="student2" class="com.swroom.di05.Student">
    <property name="name" value="李四"/>
    <property name="age" value="#{student1.changeAge()}"/>
    <property name="mySchool" ref="mySchool"/>
</bean>
```
`#{表达式}`，可以调用其他bean的方法，例如上图的#{student1.changeAge()}

## 使用内部Bean注入
```xml
<bean id="student" class="com.swroom.di06.Student">
    <property name="name" value="张三"/>
    <property name="age" value="23"/>
    <property name="mySchool">
        <bean class="com.swroom.di06.School">
            <property name="name" value="青岛大学" />
        </bean>
    </property>
</bean>
```
这种方式可以让某个bean只能通过另一个bean的内部去读取，而无法单独被获取。方法是去掉id，将bean放在property内，形成内部Bean注入。

## 同类抽象注入
```xml
<bean id="mySchool" abstract="true">
    <property name="school" value="青岛大学"/>
    <property name="department" value="计算机学院"/>
</bean>
<bean id="student1" class="com.swroom.di07.Student" parent="mySchool">
    <property name="name" value="张三"/>
    <property name="age" value="23"/>
</bean>
<bean id="student2" class="com.swroom.di07.Student" parent="mySchool">
    <property name="name" value="李四"/>
    <property name="age" value="24"/>
</bean>
```
把同类的不同Bean的重复属性抽离出来定义新的id，并且设置为抽象abstract="true"，在bean上增加继承属性parent="xxx".
