---
title: java-xml解析与封装
date: 2016-05-27 11:29:01
categories:
- java
tags:
- java
---
## 前言
在做微信公众号开发的过程中，遇到了针对消息的解析与封装，由于微信提供的是XML形式的数据流，所以必须针对XML有个解析与封装的过程。

## 多种方式实现
{% blockquote 引用： http://www.ibm.com/developerworks/cn/xml/dm-1208gub/index.html Java处理XML的三种主流技术及介绍 %}
大名鼎鼎的 DOM
绿色环保的 SAX
默默无闻的 Digester：XML 的 JavaBean 化
{% endblockquote %}
