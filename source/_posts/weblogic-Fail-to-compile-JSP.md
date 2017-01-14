---
title: Weblogic异常：Fail to compile JSP
date: 2016-06-07 10:12:20
tags:
---
异常说明：
```java
weblogic.servlet.jsp.CompilationException: Failed to compile JSP /pages/frame/homePage.jsp
homePage.jsp:2:18: The type java.util.Map$Entry cannot be resolved. It is indirectly referenced from required .class files
<%@ page import="st.platform.common.PropertyManager"%>
                 ^--------------------------------^
homePage.jsp:3:18: Error in "loginassistor.jsp" at line 19: The type java.lang.CharSequence cannot be resolved. It is indirectly referenced from required .class files
<%@ include file="/pages/security/loginassistor.jsp" %>
                 ^---------------------------------^

	at weblogic.servlet.jsp.JavelinxJSPStub.reportCompilationErrorIfNeccessary(JavelinxJSPStub.java:226)
	at weblogic.servlet.jsp.JavelinxJSPStub.compilePage(JavelinxJSPStub.java:162)
	at weblogic.servlet.jsp.JspStub.prepareServlet(JspStub.java:256)
	at weblogic.servlet.jsp.JspStub.prepareServlet(JspStub.java:216)
	at weblogic.servlet.internal.ServletStubImpl.execute(ServletStubImpl.java:244)
	Truncated. see log file for complete stacktrace
>
```
上网找了好多答案，确定是JDK1.8的问题。但是我检查整个项目的JDK都是1.6，最终锁定weblogic启动的JDK版本问题。
```
C:\oracle\wls\user_projects\domains\catvboss\bin\setDomainEnv.cmd
```
查看该文件JAVA_HOME赋值发现了问题，指向的是JDK1.8，更换成1.6即可。
```
set BEA_JAVA_HOME=

set SUN_JAVA_HOME=C:\PROGRA~1\JDK160~1

if "%JAVA_VENDOR%"=="Oracle" (
	set JAVA_HOME=%BEA_JAVA_HOME%
) else (
	if "%JAVA_VENDOR%"=="Sun" (
		set JAVA_HOME=%SUN_JAVA_HOME%
	) else (
		set JAVA_VENDOR=Sun
		set JAVA_HOME=C:\PROGRA~1\JDK160~1
	)
)
```