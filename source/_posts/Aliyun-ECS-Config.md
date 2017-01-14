---
title: 阿里云服务器ECS配置
date: 2016-05-26 09:15:45
categories:
- experience
---
## 配置

### 修改密码
1. 购买完成后，选择`实例`-->`更多`-->`重置密码`，此处修改的是服务器的登录密码，用户为`root`.
2. `更多`-->`连接管理终端`-->右上角`修改管理终端密码`，此处修改的是网页端管理终端的密码，与上述密码做好区分。
3. 修改mysql密码，原密码保存在`/alidata/account.log`
```bash
mysqladmin -uroot -p老密码 password 新密码
```
4. 修改ftp密码
```bash
passwd www
```

### 本地软件
1. 本地环境配置`FileZilla`，用于远程FTP操作，用户名`www`，密码为新修改后的密码。
2. 在本地环境配置`XShell5`，用户SSH远程连接服务器。

<!--more-->

### 软件操作命令：
1. nginx:
```
/etc/init.d/nginx start|stop|restart
```
2. tomcat:
```
/etc/init.d/tomcat7 start|stop|restart
```
3. mysql:
```
/etc/init.d/mysqld start|stop|restart
```
4. ftp:
```
/etc/init.d/vsftpd start|stop|restart
```

### Nginx反向代理
一定注意修改`/alidata/server/nginx/conf/vhosts/default.conf`文件内的`location`路径。
默认java过滤为`.jsp`后缀的路径，但是我们使用SpringMVC之后，跳转页都不会有此后缀，所以必须修改为`/`。
```vim
server {
    listen       80 default;
    server_name _;
        index index.html index.htm index.jsp;
        root /alidata/www/default;

        location / {
                proxy_pass    http://127.0.0.1:8080;
        }

        location ~ .*\.(gif|jpg|jpeg|png|bmp|swf)$
        {
                expires 30d;
        }

        location ~ .*\.(js|css)?$
        {
                expires 1h;
        }

        access_log  /alidata/log/nginx/access/default.log;
```

### 实时查看Tomcat的Console信息
```
tail -f /alidata/server/tomacat7/logs/catalina.out
```

### mysql简单操作命令
1. 登录mysql：`mysql -uroot -p`
2. 显示数据库：`show databases;`
3. 创建数据库：`create database xxx;`
4. 使用数据库：`use xxx;`
5. 查看表结构：`describe tablename;`

## 附录
[Java运行环境（Ubuntu 64位  JDK1.7）.pdf](http://pan.baidu.com/s/1kVNaOuF)