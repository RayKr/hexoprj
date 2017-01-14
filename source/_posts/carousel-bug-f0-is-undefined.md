---
title: Bootstrap Carousel异常 TypeError f0 is undefined
date: 2016-07-14 20:56:21
tags:
- exception
---


解决办法：

> Remove the data-ride="carousel" from the div with ReviewCarousel, this sometimes cause the problem and give the TypeError: f[0].

以上解决办法引用自[stackoverflow.com](http://stackoverflow.com/questions/37208085/typeerror-f0-is-undefined-in-bootstrap-min-js-when-this-error-occuured-and-ho)

去掉`data-ride="carousel"`即可。