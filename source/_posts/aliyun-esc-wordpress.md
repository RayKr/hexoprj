---
title: 阿里云ECS搭建Wordpress
date: 2017-01-16 10:39:34
categories:
- experience
---

**主要流程** 

《在阿里云安装wordpress》http://www.tuicool.com/articles/aYbi22f

**Nginx出现“413 Request Entity Too Large”错误。**
解决办法：打开nginx配置文件`nginx.conf`，找到`http{}`，添加`client_max_body_size 20m;`，`service nginx reload`重启nginx。

**上传文件、更新主题、插件时提示fpt账号密码的问题。**
故障原因：由于nginx是root安装并且转移数据的，文件所有者和组都变成了root，而nginx的用户无权限读写，所以会有这个提示。
* 首先获取nginx当前用户。具体方法就是新建一个php文件，在其中输入`<?php echo(exec(“whoami”)); ?>`,到页面执行一下，得到`www-data`.
* 然后更改所有者和组。
    * `chown -R www-data /usr/share/nginx/www/swroom`
    * `chgrp -R www-data /usr/share/nginx/www/swroom`
<!-- more -->
**修改wordpress界面为中文**
解决办法：`wp-config.php`,增加`define(‘WPLANG’, ‘zh_CN’);`
    
**阿里云一键web环境搭建**
解决办法：《Linux一键安装web环境全攻略.pdf》，见附件

**sql-5.6登录指令**
不是`mysqladmin -uroot -p`， 而是用`mysql -uroot -p`