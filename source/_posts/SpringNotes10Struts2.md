---
title: Spring Notes：10_Spring与Struts2整合
date: 2016-05-23 09:37:53
categories:
- java-framework
tags:
- spring
- java
- framework
---
## 环境搭建
```xml
<!-- http://mvnrepository.com/artifact/org.apache.struts/struts2-core -->
<dependency>
    <groupId>org.apache.struts</groupId>
    <artifactId>struts2-core</artifactId>
    <version>2.5</version>
</dependency>
<dependency>
    <groupId>org.apache.struts</groupId>
    <artifactId>struts2-spring-plugin</artifactId>
    <version>2.5</version>
</dependency>
<dependency>
    <groupId>org.apache.struts</groupId>
    <artifactId>struts2-convention-plugin</artifactId>
    <version>2.5</version>
</dependency>
```
注意事项：与spring的整合过程中，除了struts2-core核心包需要依赖外，必须还要有个`struts2-spring-plugin`包。
这个包的作用就是为了两个框架的整合，使Controller中获取Service。
如果不引用此包，受查无异常，运行时会有空指针异常。

<!--more-->

## WebApp与Spring的整合
web.xml
```xml
<!--指定spring文件的位置及名称-->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring-config.xml</param-value>
</context-param>

<!--注册监听器-->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```
首先指定Spring的文件的位置，注册监听器后，就可以在servlet中用`WebApplicationContext`，就可以实现将对象的创建交给spring容器处理。

## Struts2配置
web.xml 注册struts2，用的是`filter`
```xml
<!--注册struts2启动项-->
<filter>
    <filter-name>struts2</filter-name>
    <filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>struts2</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
struts.xml 配置路由
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.3//EN"
        "http://struts.apache.org/dtds/struts-2.3.dtd">
<struts>
</struts>
```
注意事项：
- 文件名必须要正确：`struts.xml`而不是`strust2.xml`
- 放到根目录下，非maven项目为`src`下，maven项目为`resources`文件夹。
- 注意namespace与页面中的路由一致。
- 注意`extends="struts-default"`的拼写

## Spring与Struts2整合
1. 首先要引入插件包：`struts2-spring-plugin`
2. spring配置文件中，如果用注解方式，则包扫描要加入`/action`；如果用xml方式，则需要注册action，并注明service属性
```xml
<context:component-scan base-package="com.manchey.dao, com.manchey.service, com.manchey.action"/>

<!--or-->

<bean id="registerAction" class="com.manchey.action.RegisterAction">
    <property name="userService" ref="userService"/>
</bean>
```
3. 如果用注解，则需要在Action类上注解`@Component("registerAction")`，并且在service上注解`@Autowired`或`@Resource("userService")`

## struts.xml配置路由
```xml
<package name="default" namespace="/test" extends="struts-default">
    <action name="register" class="registerAciton">
        <result>/userinfo.jsp</result>
    </action>
</package>
```
注意事项：
- `class="registerAciton"`使用的是伪类，即这个类是由spring容器创建而来，registerAction即为id
- 为了保证线程安全，`RegisterAction`这个类，需要注解成多例：`@Scope("prototype")`

## 解决no-session问题
至此，struts2与spring的整合已经完成，但是存在个问题。当我的session的get方法换成load方法后，会报`no session`的错误。
究其原因是因为，get是立即返回，load是延时返回。由于spring的事务加在了service层上，当load的时候，从dao->service->action，再由action->service的时候，此时的事务已经关闭，所以是获取不到数据的。
### 解决思路
将事务提升到`View`层，保证`execute()`方法的原子性。
在web.xml中注册OpenSessionInViewFilter：
```xml
  <!-- 注册OpenSessionInViewFilter -->
<filter>
    <filter-name>openSessionInView</filter-name>
    <filter-class>org.springframework.orm.hibernate5.support.OpenSessionInViewFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>openSessionInView</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
注意事项：OpenSessionInViewFilter必须注册在Struts2之前。

## Struts2注解使用
```java
@Component
@Scope("prototype")
@Namespace("/test")
@ParentPackage("struts-default")
public class RegisterAction {
    // ...
    @Action(value = "register", results = @Result(location = "/userinfo.jsp"))
    public String execute() {
        // ...
        return "success";
    }
}
```
