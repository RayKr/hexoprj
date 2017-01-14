---
title: SpringMVC：GET方式中文乱码问题解决
date: 2016-07-12 10:57:56
tags:
- SpringMVC
- Encoding
- 中文乱码
---

今天测试`Ajax+SpringMVC`传值，发现使用`GET`方式，SpringMVC获取到的参数内中文乱码，而`POST`方式则没有这个问题。
```xml
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```
在`web.xml`里配置的编码过滤器，是只针对`POST`方式生效的，要想`GET`不乱码，则必须要修改`Tomcat`的`server.xml`配置文件。
文件路径一般在`tomcat\conf\server.xml`，需要增加`useBodyEncodingForURI="true"`。
```xml
<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
			   useBodyEncodingForURI="true"/>
```
**但是**：配置`useBodyEncodingForURI="true"`后，可以解决普通`get`请求的中文乱码问题，但是对于通过`ajax`发起的`get`请求中文依然会乱码，请把`useBodyEncodingForURI="true"`改为`URIEncoding="UTF-8"`即可。
```xml
<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
			   URIEncoding="UTF-8"/>
```
