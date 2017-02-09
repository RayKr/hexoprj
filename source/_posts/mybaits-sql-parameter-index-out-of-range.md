---
title: Mybatis执行SQL时出现Parameter index out of range (1 > number of parameters, which is 0)
date: 2017-02-09 15:27:19
tags:
- java
- bug
categories:
- java
---
异常信息：
> Parameter index out of range (1 > number of parameters, which is 0)

**原因：***mapper.xml中写sql语句时，在写`like`时写的不正确。

写like语句的时候 一般都会写成 `like '% %'`

在mybatis里面写就是应该是 `like '%${name} %'` 而不是 `'%#{name} %'`  

**总结：**`${name}` 是不带单引号的，而`#{name}`是带单引号的