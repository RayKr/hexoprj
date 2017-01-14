---
title: Spring Notes：9_Spring与Hibernate整合
date: 2016-05-18 13:59:19
categories:
- java-framework
tags:
- spring
- java
- framework
---
## 任务
Spring与Hibernate的整合其实可以归入Dao层：
1. 把Dao的实现类换掉。
2. 修改srping的配置文件：
    - 修改事务管理器为HibernateTransactionManager
    - 注册SessionFactory，并交给Spring管理

## Dao层实现类使用SessionFactory
```java
session.save(student);
...
String hql = "select name from student";
return session.createQuery(hql).list();
...
return session.get(Student.class, id);
```

<!--more-->

## 注册SessionFactory（重要）
```xml
<!--注册SessionFactory-->
<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
    <!--配置数据源，替代hibernate配置中的四元素-->
    <property name="dataSource" ref="dataSource"/>
    <!--其他hibernate配置项-->
    <property name="hibernateProperties">
        <props>
            <!--方言-->
            <prop key="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</prop>
            <!--自动建表-->
            <prop key="hibernate.hbm2ddl.auto">update</prop>
            <!--显示sql-->
            <prop key="hibernate.show_sql">true</prop>
            <!--格式化sql-->
            <prop key="hibernate.format_sql">true</prop>
        </props>
    </property>
    <!--映射文件位置-->
    <property name="mappingDirectoryLocations" value="com/swroom/beans"/>
</bean>

<!--注册事务管理器-->
<bean id="txManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
    <property name="sessionFactory" ref="sessionFactory"/>
</bean>
```
## AOP配置事务
```xml
<!--事务通知-->
<tx:advice id="interceptor" transaction-manager="txManager">
    <tx:attributes>
        <tx:method name="add*" isolation="DEFAULT" propagation="REQUIRED"/>
        <tx:method name="remove*" isolation="DEFAULT" propagation="REQUIRED"/>
        <tx:method name="modify*" isolation="DEFAULT" propagation="REQUIRED"/>
        <tx:method name="find*" isolation="DEFAULT" propagation="REQUIRED" read-only="true"/>
    </tx:attributes>
</tx:advice>

<!--AOP配置-->
<aop:config>
    <aop:pointcut id="servicePointcut" expression="execution(* *..service.*.*(..))"/>
    <aop:advisor advice-ref="interceptor" pointcut-ref="servicePointcut"/>
</aop:config>
```

## 创建Hibernate映射文件
Student.hbm.xml
```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping package="com.swroom.beans">
    <class name="Student">
        <id name="id">
            <generator class="native"/>
        </id>
        <property name="name"/>
        <property name="age"/>
    </class>
</hibernate-mapping>
```
