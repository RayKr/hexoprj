---
title: 关于Weblogic中sendRedirect路径问题
date: 2016-07-29 11:17:15
tags:
- bug
---
错误代码：
```java
response.sendRedirect("applogin?path=personcenter");
```
刚解决了一个问题，分享一下，weblogic部署的程序，`response.sendRedirect`默认是绝对路径.
上述写法会导致重定向的时候自动补全前面的路径，这样一来就导致springmvc controller无法跳转。

解决办法：是加上一个斜杠。这样就告诉weblogic是从根之后的路径，这已经是个绝对路径，则跳转成功。
```java
response.sendRedirect("/applogin?path=personcenter");
```