---
title: Positional parameter are considered deprecated; use named parameters or JPA-style positional parameters instead.
date: 2016-05-28 20:16:36
tags:
---
hibernate 4.1之后对于HQL中查询参数的占位符做了改进，如果仍然用老式的占位符会有类似如下的告警信息：

```java
WARN: [DEPRECATION] Encountered positional parameter near line 1, column 54 in HQL:
Positional parameter are considered deprecated; use named parameters or JPA-style positional parameters instead.
```

从告警提示信息中可以看出，它建议用命名参数或者JPA占位符两中种方法来代替老的占位符查询方法。

比如老的占位符查询代码片段：
```java
String hql = "select t from Blog t where t.site=? ";
Query query = getSession().createQuery(hql);
query.setParameter(0, "micmiu.com");
```

方法一：改成命名参数的方式：

```java
//命名参数的方式
String hql2 = "select t from Blog t where t.site=:site ";
Query query2 = getSession().createQuery(hql2);
query2.setParameter("site", "micmiu.com");
```

方法二：改成JPA占位符的方式：

```java
//JPA占位符方式
String hql3 = "select t from Blog t where t.site=?0 ";
Query query3 = getSession().createQuery(hql3);
query2.setParameter("0", "micmiu.com");
```

后面两种查询方法就不会有告警信息产生了。