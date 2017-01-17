---
title: Hibenate4中getCurrentSession()报错
date: 2017-01-17 15:43:32
cetegories:
- experience
---
## 问题描述
Hibernate4中，dao层直接注入sessionFactory，然后用getCurrentSession方法获取session，运行报错：
```
Could not obtain transaction-synchronized Session for current thread
```
改成openSession后可用。

## 原理分析
首先来看getCurrentSession和openSession的区别：
1. openSession每次打开都是新Session，多次获取的Session是不同的。

2. getCurrentSession是从当前上下文中获取已存在的Session并绑定到当前线程，第一次调用产生新Session，如果未关闭，则后续取的都是同一个Session实例。

2. openSession必须手动关闭；getCurrentSession会在**事务提交**或**事务回滚**时自动关闭。

所以，getCurrentSession是与**事务**有关系的。那么当使用它时必须放在事务里。
```xml
<bean id="transactionManager"
    class="org.springframework.orm.hibernate4.HibernateTransactionManager">
    <property name="sessionFactory">
        <ref bean="sessionFactory" />
    </property>
</bean>

<tx:annotation-driven transaction-manager="transactionManager"/>
```
然后再DAO上加上`@Transaction`注解即可。

## 知识拓展
openSession和getCurrentSession之间的关联：
在 SessionFactory 启动的时候， Hibernate 会根据配置创建相应的 CurrentSessionContext ，在 getCurrentSession() 被调用的时候，实际被执行的方法是 CurrentSessionContext.currentSession() 。在 currentSession() 执行时，如果当前 Session 为空， currentSession 会调用 SessionFactory 的 openSession 。

参考：
1. [Hibernate4中使用getCurrentSession报Could not obtain transaction-synchronized Session for current thread](http://www.cnblogs.com/chyu/p/4817291.html)
