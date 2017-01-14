---
title: Spring Notes：6_AspectJ对AOP的实现
date: 2016-05-15 11:16:14
categories:
- java-framework
tags:
- spring
- java
- framework
---

## 概述
{% blockquote 百度百科 http://baike.baidu.com/view/987127.htm %}
AspectJ是一个面向切面的框架，它扩展了Java语言。AspectJ定义了AOP语法所以它有一个专门的编译器用来生成遵守Java字节编码规范的Class文件。
{% endblockquote %}
AspectJ也是一个实现AOP的轻量级框架，并且支持注解式开发，Spring直接拿过来做了整合。
在Spring中使用AOP开发时，一般使用AspectJ的实现方式。

## AspectJ切入点表达式
```
execution(
    [modifiers-pattern] 访问权限类型
    ret-type-pattern 返回值类型（必需）
    [declaring-type-pattern] 全限定性类名
    name-pattern(param-pattern) 方法名(参数名)（必需）
    [throws-pattern] 抛出异常类型
)
```

可使用的通配符如下：
- `*`：0至多个任意字符
- `..`：用在方法参数中，表示任意多个参数；用在包名后，表示当前包以及子包
- `+`：用在类名后，表示当前类及其子类；用在接口后，表示当前接口及其实现类

<!--more-->

## 环境搭建
- jar包：
    - `aopalliance-1.0.jar`
    - `spring-aop-4.2.5.RELEASE.jar`
    - `spring-aspects-4.2.5.RELEASE.jar`
    - `aspectjweaver-1.5.3.jar`

- 使用AOP约束：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
```

## AspectJ基于注解的AOP实现
### 前置通知（@Before）
```xml
<!-- 目标对象 -->
<bean name="someService" class="com.swroom.service.SomeServiceImpl"/>
<!-- 切面 -->
<bean name="myAspect" class="com.swroom.aspect.MyAspect"/>
<!-- AspectJ自动代理-->
<aop:aspectj-autoproxy/>
```
```java
@Aspect
class MyAspect {
    // 该注解的value值就是切入点表达式
    @Before("execution(* *..ISomeService.doSome(..))")
    public void myBefore() {
        System.out.println("执行前置通知myBefore");
    }
}
```

### 后置通知（@After）
```java
@AfterReturning(value = "execution(* *..doSome(..))", returning = "result")
public void myAfter(Object result) {
    System.out.println("执行AspectJ后置通知,返回值=" + result);
}
```
后置通知需要注意两点：
- 注解是`@AfterReturning`
- 后置通知可以获取返回值，使用方法是
    - 在注解里定义`returning = "result"`
    - 将`result`作为参数放到方法中，并定义成通用Object类型。

### 环绕通知（@Around）
```java
@Around("execution(* *..doThird(..))")
public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("环绕：执行目标方法之前执行");
    Object result = pjp.proceed();
    System.out.println("环绕：执行目标方法之前执行,返回值=" + result);
    if (result != null) {
        result = ((String) result).toUpperCase();
    }
    return result;
}
```
环绕通知注意事项：
- 为了可以调用到目标方法，需要增加一个参数`ProceedingJoinPoint pjp`
- 执行目标方法的写法为`pjp.proceed()`，并且有返回值。
- 环绕通知的返回值与后置通知不同，是可以被修改的。所以将环绕通知方法返回值设置为通用`Object`类型。

### 异常通知（@AfterThrowing）
```java
@AfterThrowing(value = "execution(* *..ISomeService.doSome(..))", throwing = "ex")
public void myThrows(Exception ex) {
    System.out.println("执行异常通知myThrows, ex=" + ex.getMessage());
}
```
异常通知注意事项：
为了获取异常信息，可以定义`throwing="ex"`，并且将ex作为方法参数。

### 最终通知（@After）
```java
@After("execution(* *..doSome(..))")
public void myAfter() {
    System.out.println("执行最终通知方法myAfter");
}
```
最终通知注意事项：
最终通知是在目标方法执行之后，不论成功与否、是否存在异常，都会执行。
后置通知是在目标方法执行完返回之后，才执行后置通知，所以叫AfterReturning。
所以同一个目标方法的最终通知要先于后置通知执行。

### 定义切入点
上述五种通知的切入点存在着代码冗余，所以这就需要定义切入点，减少冗余：
```java
@After("doSomePointcut()")
public void myAfter() {
    System.out.println("执行最终通知方法myAfter");
}
// 定义切入点
@Pointcut("execution(* *..doSome(..))")
public void doSomePointcut() {}
```

## AspectJ基于XML的AOP实现
AOP常用AspectJ实现，AspectJ中常用XML方式写法。

### 前置通知
```xml
<!-- AOP配置-->
<aop:config>
    <!-- 定义切入点 -->
    <aop:pointcut id="doSomePointcut" expression="execution(* *..service.*.doSome(..))"/>
    <aop:pointcut id="doSecondPointcut" expression="execution(* *..service.*.doSecond(..))"/>
    <aop:pointcut id="doThirdPointcut" expression="execution(* *..service.*.doThird(..))"/>

    <!-- 定义切面-->
    <aop:aspect ref="myAspect">
        <!-- 前置通知 -->
        <aop:before method="myBefore" pointcut-ref="doSomePointcut"/>
        <aop:before method="myBefore(org.aspectj.lang.JoinPoint)" pointcut-ref="doSomePointcut"/>
    </aop:aspect>
</aop:config>
```
上述代码段注意事项：
- 定义切入点的方法：`<aop:pointcut ../>`
- 通知的方法如果有重载，参数列表一定要使用`全限定性类名`，如`java.lang.Object`

### 后置通知
```xml
<!-- 后置通知：其他部分与前置通知相同 -->
<aop:after-returning method="myAfterReturning(java.lang.Object)" pointcut-ref="doSecondPointcut" returning="result"/>
```
后置通知带参数方法注意事项：
得需要将参数传值给该参数，需要定义`returning="result"`，并且此处的名称必须与切面POJO类中的参数名一致。

### 环绕通知
```xml
<aop:around method="myAround" pointcut-ref="doThirdPointcut" />
```

### 异常通知
```xml
<aop:after-throwing method="myThrows" pointcut-ref="doSomePointcut" throwing="ex"/>
```
异常通知的参数传值类似于后置通知，不过使用的是`throwing=""`

### 最终通知
```xml
<aop:after method="myAfter" pointcut-ref="doThirdPointcut"/>
```


