---
title: SAXParseException:The string "--" is not permitted within comments.
date: 2017-01-17 16:35:52
tags:
- bug
categories:
- experience
---

> SAXParseException:The string "--" is not permitted within comments.
注释不符合规范

去除`XML`、`HTML`、`XHTML`注释`<!--`和`-->`中间所有的`--`和`>`，避免解析错误。

拓展阅读：
1. [如何解决这类问题：The string "--" is not permitted within comments.](http://blog.csdn.net/free4294/article/details/38681095)
2. [stackoverflow - Cure for 'The string “--” is not permitted within comments.' exception?](http://stackoverflow.com/questions/10155940/cure-for-the-string-is-not-permitted-within-comments-exception)
3. [HTML comments](http://www.htmlhelp.com/reference/wilbur/misc/comment.html)
