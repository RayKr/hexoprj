---
title: Spring Notes：8_Transaction事务
date: 2016-05-17 20:43:17
categories:
- java-framework
tags:
- spring
- java
- framework
---
## Spring事务管理API
### 事务管理器接口（PlatformTransactionManager）
该接口有两个常用实现类
- `DataSourceTransactionManager`： JDBC或iBatis
- `HibernateTransactionManager`： Hibernate

Spring事务回滚的方式是：**发生运行时异常时回滚，发生受查异常时提交**

### 事务定义接口（TransactionDefinition）
`ISOLATION_DEFAULT`(默认),`ISOLATION_READ_COMMITTED`,`ISOLATION_READ_UNCOMMITTED`,`ISOLATION_REPEATABLE_READ`,`ISOLATION_SERIALIZABLE`
`PROPAGATION_REQUIRED`(默认),`PROPAGATION_MANDATORY`,`PROPAGATION_NESTED`,`PROPAGATION_NEVER`,`PROPAGATION_NOT_SUPPORTED`,`PROPAGATION_REQUIRES_NEW`,`PROPAGATION_SUPPORTS`

<!--more-->

## 事务代理工厂Bean管理事务
```xml
<!--注册事务管理器-->
<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>

<!--注册事务代理-->
<bean id="stockProxyService" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
    <!--事务管理器-->
    <property name="transactionManager" ref="txManager"/>
    <!--service-->
    <property name="target" ref="stockService"/>
    <!--事务属性-->
    <property name="transactionAttributes">
        <props>
            <!--key就是切入点, value就是事务隔离和传播方法配置-->
            <prop key="open*">ISOLATION_DEFAULT,PROPAGATION_REQUIRED</prop>
            <!--受查时异常提交，可以手动指定回滚，方法是用"-StockException"-->
            <prop key="buyStock">ISOLATION_DEFAULT,PROPAGATION_REQUIRED,-StockException</prop>
        </props>
    </property>
</bean>
```
注意事项：
- 因为使用jdbcTemplate，所以事务管理器使用`DataSourceTransactionManager`,并且需要指定数据源。
- 注册事务代理使用`TransactionProxyFactoryBean`,需要指定事务管理器、目标对象、事务属性。
- 事务属性key是切入点，value是`事务隔离`和`传播方法`配置项。
- `-StockExcpetion`的意思是碰到StockException异常回滚。`-`代表回滚，`+`代表提交。

## 事务注解管理事务
常用最全约束(di,aop,tx,context)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
```
XML配置文件写法如下：
```xml
<!--事务注解驱动-->
<tx:annotation-driven transaction-manager="txManager"/>
```
将注解写在`service`层，保证service的原子性：
```java
@Override
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public void openAccount(String aname, double money) {
    accountDao.insertAccount(aname, money);
}

@Override
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public void openStock(String sname, int amount) {
    stockDao.insertStock(sname, amount);
}

@Override
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = StockException.class)
public void buyStock(String aname, double money, String sname, int amount) throws StockException {
    boolean isBuy = true;
    accountDao.updateAccount(aname, money, isBuy);
    if (1 == 1) {
        throw new StockException("股票异常");
    }
    stockDao.updateStock(sname, amount, isBuy);
}
```
注意：`rollbackFor = StockException.class`的用法

## AspectJ的AOP配置管理事务（重点）
```xml
<!--注册事务管理器-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>

<!--事务通知-->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <!--此处配置的不是切入点，指定的在连接点方法上应用的事务属性-->
        <tx:method name="open*" isolation="DEFAULT" propagation="REQUIRED"/>
        <tx:method name="buyStock" isolation="DEFAULT" propagation="REQUIRED" rollback-for="StockException"/>
    </tx:attributes>
</tx:advice>

<!--AOP配置-->
<aop:config>
    <aop:pointcut id="stockPointcut" expression="execution(* *..service.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="stockPointcut"/>
</aop:config>
```
注意事项：
- 将事务注册成通知，然后用AOP配置顾问，将通知和切入点织入到一起。
- 切入点的配置在`aop:config`里，`tx:attributes`里的`tx:method`配置的是在连接点方法上的事务属性
- aop配置里的切入点配置用的是Aspect方式实现的


