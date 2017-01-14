---
title: 异常总结
date: 2016-05-19 10:17:43
categories:
- experience
tags:
- hibernate
- java
- framework
- exception
---

### org.hibernate.AnnotationException: No identifier specified for entity
检查Hibernate实体类注解，主键ID：`@Id`

### org.springframework.orm.hibernate3.HibernateSystemException: ids for this class must be manually assigned before calling save():
没有配置正确主键ID的生成属性：`@GeneratedValue()`

### org.hibernate.MappingException: Unknown entity:
出现这个问题情况很多，都是由于Mapping错误引起：
1. 如果采用`.hbm.xml`映射文件配置：
    - 检查文件名是否正确，必须是`.hbm.xml`结尾
    - 检查spring配置文件里的路径是否正确。`<property name="mappingDirectoryLocations" value="hbm"/>`
    此处的文件夹注意一点，如果是maven项目，配置文件的根目录是`resources`，需要放在此文件夹下才行。
2. 如果采用Annotation注解方式：
    - 检查`@Entity`导包是否正确。正确的是`import javax.persistence.Entity;`

<!--more-->

### org.hibernate.hql.ast.QuerySyntaxException is not mapped
解决方案：
这一般是HQL语句错误。因为Hibernate是对类查询的，而不是对数据库表进行查询。
所以需要将hql语句中的的表名改成PO类名。

### hibernate的save,update,delete方法执行了但是没有数据库的更改
这个问题是由于在spring-hibernate.xml配置事务连接点时，在`check*`方法上加上了`read-only=true`，而恰恰我测试的方法正是check开头，所以只能执行查询。
知道原因后很好改，去掉只读属性，或者将写入操作拿出check*方法。


### Jaxb, Class has two properties of the same name
```java
// 在类注解上加上
@XmlAccessorType(XmlAccessType.FIELD)
```
否则，只能在getter方法上使用`@XmlElement(name="eleClassA")`

### IDEA Error:java: 未结束的字符串文字
IDEA开发android，总是碰到这个问题，未结束的字符串，编译的时候就会碰到，尤其是新手，很苦恼，不知道怎么解决
这个问题就是编码的问题  UTF-8和GBK的混淆，采用如下方法：
1、在idea的Settings中，找到File Encodings，将IDE Encoding 改为UTF-8
2、更换idea下面的encodings.xml文件，恢复到最近一次设置
3、或者更改encodings.xml里面的配置，GBK和UTF-8统一一下
出现这个问题就是encodings.xml文件的问题 ，记住多改几次，试试就OK了

### SpringMVC路由路径找不到页面
我犯了个很低级的错误，就是`@RequestMapping(value='/index')`这个注解中的`value`写成了`name`.

### java.lang.ClassCastException: javax.mail.Session cannot be cast to javax.mail.Session
javax.mail.Session已经加载过，重复加载后，不能由一个类转换成另一个类的类型。
解决方法：
1. maven项目：`<scope>provided</scope>`
2. Tomcat需要加载mail.jar

### mysql连接错误：1042, "Can't get hostname for your address"
1. 修改`C:\ProgramData\MySQL\MySQL Server 5.7\my.ini`，增加如下：
```
[mysqld]
skip-name-resolve
```
2. 上述方法不生效时，还原上述设置，修改`MySQL57`服务的登录账户类型：![MySQL57设置](http://swroom.qiniudn.com/QQ%E5%9B%BE%E7%89%8720160709095353.png)


