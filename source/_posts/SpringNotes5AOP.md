---
title: Spring Notes：5_AOP编程
date: 2016-05-14 11:10:27
categories:
- java-framework
tags:
- spring
- java
- framework
---
## AOP底层本质
一段代码揭示AOP编程的本质：使用“代理模式”将不同层“织入”在一起，而使得各层的代码干净。
```java
@Test
public void test () {
    final ISomeService service = new SomeServiceImpl();
    //使用动态代理将底层服务（SomeUtil）和业务层（ISomeService）织入到一起
    ISomeService proxy = (ISomeService) Proxy.newProxyInstance(
            service.getClass().getClassLoader(),
            service.getClass().getInterfaces(),
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    SomeUtil.doTransaction();
                    Object result = method.invoke(service, args);
                    SomeUtil.doLog();
                    return result;
                }
            }
    );
    // 使用增强后的代理类执行方法
    proxy.doSome();
    proxy.doSecond();
}
```

## AOP环境配置
1. jar包有两个：**spring-aop-4.2.5.RELEASE.jar** 和 **aopalliance-1.0.jar**
2. xml约束：使用默认spring约束即可。

<!-- more -->

## AOP的几个概念
1. **切面（Aspect）**
泛指交叉业务逻辑，例如事务、日志等。常用切面有通知和顾问。实际就是对业务逻辑的一种增强。
2. **织入（Weaving）**
织入就是将切面代码插入到目标对象的过程。
3. **连接点（JoinPoint）**
一般来说业务接口中的方法为连接点。
4. **切入点（PointCut）**
切入点指切面具体织入的方法。同一个接口中可能只有某个方法需要增强，那么这个方法就是切入点。
`final`的方法**不**能作为连接点和切入点。因为final不能被修改，不能被增强。
5. **目标对象（Target）**
目标对象指的就是将要被增强的对象。
6. **通知（Advice）**
通知是切面的一种简单实现方式。它定义了增强代码切入到目标代码的**时间点**。
**切入点定义切入位置，通知定义切入时间。**
7. **顾问（Advisor）**
顾问能将通知以更复杂的方式织入到目标对象中。

## 通知（Advice）
### 前置通知（MethodBeforeAdvice）
1. 定义前置通知类，实现接口`MethodBeforeAdvice`
```java
public class MyMethodBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("目标方法执行之前：对目标对象的增强代码就是写在这里的。");
    }
}
```
2. 使用xml配置文件织入
```xml
<!--目标对象-->
<bean id="someService" class="com.swroom.aop1.SomeServiceImpl"/>
<!--切面：前置通知-->
<bean id="beforeAdvice" class="com.swroom.aop1.MyMethodBeforeAdvice"/>
<!--代理对象的生成：注意这里的ProxyFactory不是代理类，而是代理对象生成器-->
<bean id="serviceProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="someService"/>
    <!--可以不写该接口，因为默认自动检测到目标类所实现的所有接口-->
    <property name="interfaces" value="com.swroom.aop1.ISomeService"/>
    <property name="interceptorNames" value="beforeAdvice"/>
</bean>
```
注：property属性中可以不定义interfaces接口，因为默认自动检测目标类所实现的所有接口。

### 后置通知（AfterReturningAdvice）
1. 定义后置通知类，实现接口`AfterReturningAdvice`
```java
public class MyAfterReturningAdvice implements AfterReturningAdvice {
    // 后置通知接口的方法，比前置通知多了一个返回值参数，并且只是一个单纯的返回值，无法对其进行处理。
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行方法之后");
    }
}
```
2. 使用xml配置文件织入
```xml
<!--目标对象-->
<bean id="someService" class="com.swroom.aop2.SomeServiceImpl"/>
<!--切面：后置通知-->
<bean id="afterReturning" class="com.swroom.aop2.MyAfterReturningAdvice"/>
<!--代理对象的生成：注意这里的ProxyFactory不是代理类，而是代理对象生成器-->
<bean id="serviceProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="someService"/>
    <property name="interceptorNames" value="afterReturning"/>
</bean>
```

### 环绕通知（MethodInterceptor）
1. 定义环绕通知类，实现接口`MethodInterceptor`
```java
public class MyMethodInterceptor implements MethodInterceptor {
    // invoke方法的返回值就是目标方法的返回值。
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("目标方法执行之前");
        // 执行目标方法
        Object result = methodInvocation.proceed();
        System.out.println("目标方法执行之后");
        return result;
    }
}
```
2. 使用xml配置文件织入
```xml
<!--目标对象-->
<bean id="someService" class="com.swroom.aop3.SomeServiceImpl"/>
<!--切面：环绕通知通知-->
<bean id="aroundAdvice" class="com.swroom.aop3.MyMethodInterceptor"/>
<!--代理对象的生成：注意这里的ProxyFactory不是代理类，而是代理对象生成器-->
<bean id="serviceProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="someService"/>
    <property name="interceptorNames" value="aroundAdvice"/>
</bean>
```

### 异常通知（ThrowsAdvice）
1. 顾名思义，当获取相应的异常时，触发该异常通知增强。
2. 可自定义异常。
3. 编写异常通知类，实现接口`ThrowsAdvice`。
```java
public class MyThrowsAdvice implements ThrowsAdvice {
    public void afterThrowing(UsernameException ex) {
        System.out.println("若发生UsernameException，该方法会自动调用执行！ex=" + ex.getMessage());
    }
    public void afterThrowing(PasswordException ex) {
        System.out.println("若发生PasswordException，该方法会自动调用执行！ex=" + ex.getMessage());
    }
}
```
4. 使用xml织入代码
```xml
<!--目标对象-->
<bean id="someService" class="com.swroom.aop4.SomeServiceImpl"/>
<!--切面：异常通知-->
<bean id="throwsAdvice" class="com.swroom.aop4.MyThrowsAdvice"/>
<!--代理对象的生成：注意这里的ProxyFactory不是代理类，而是代理对象生成器-->
<bean id="serviceProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="someService"/>
    <property name="interceptorNames" value="throwsAdvice"/>
</bean>
```

## 顾问
目标对象和切面都不需要动，只修改xml配置文件，即可使用顾问。可将切面匹配切入点增强。
### 名称匹配方法切入点顾问（NameMatchMethodPointcutAdvisor）
```xml
<!--目标对象-->
<bean id="someService" class="com.swroom.aop5.SomeServiceImpl"/>
<!--切面：后置通知-->
<bean id="afterAdvice" class="com.swroom.aop5.MyAfterReturningAdvice"/>
<!--切面：顾问 指定切入点-->
<bean id="afterAdvisor" class="org.springframework.aop.support.NameMatchMethodPointcutAdvisor">
    <property name="advice" ref="afterAdvice"/>
    <property name="mappedName" value="doSome"/>
</bean>
<!--代理对象的生成：注意这里的ProxyFactory不是代理类，而是代理对象生成器-->
<bean id="serviceProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target" ref="someService"/>
    <property name="interceptorNames" value="afterAdvisor"/>
</bean>
```
注：多留意应该使用`ref`的地方，不要用成了value。

如果需要多个切入点，可针对顾问定义修改如下：
```xml
<!--切面：顾问 指定切入点-->
<bean id="afterAdvisor" class="org.springframework.aop.support.NameMatchMethodPointcutAdvisor">
    <property name="advice" ref="afterAdvice"/>
    <property name="mappedNames">
        <array>
            <value>doSome</value>
            <value>doThird</value>
        </array>
    </property>
</bean>
```
或者直接使用更简便的方法：
```xml
<bean id="afterAdvisor" class="org.springframework.aop.support.NameMatchMethodPointcutAdvisor">
    <property name="advice" ref="afterAdvice"/>
    <property name="mappedNames" value="doSome,doThird"/>
</bean>
```
支持通配符：
```xml
<bean id="afterAdvisor" class="org.springframework.aop.support.NameMatchMethodPointcutAdvisor">
    <property name="advice" ref="afterAdvice"/>
    <property name="mappedNames" value="do*"/>
</bean>
```

### 正则表达式方法切入点顾问（RegexpMethodPointcutAdvisor）
正则表达式匹配的对象是**全限定性**方法名，而不仅仅是简单方法名。即会从包名、类名、方法名都搜索一遍。
```xml
<bean id="afterAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
    <property name="advice" ref="afterAdvice"/>
    <!--正则表达式匹配的对象为：全限定性方法名，而不仅仅是简单方法名-->
    <!--<property name="pattern" value=".*T.*"/>-->
    <!--<property name="pattern" value=".*Sec.*"/>-->
    <property name="pattern" value=".*T.*|.*Sec.*"/>
</bean>
```
注：平常在使用正则表达式顾问时，一般不会使用`patterns`，而是使用`pattern`配合正则表达式的或（|）运算符。

## 自动代理生成器
上述两种顾问方式存在两个缺陷：
1. 当有多个对象需要顾问增强时，需要书写多遍代理，代码冗余。
2. 当`ac.getBean("proxyName")`时，只是一种迫不得已的使用代理名称，而不是目标对象的名称。

为了解决上述缺陷，引入自动代理生成器。有两种：默认和Bean名称

### 默认Advisor自动代理生成器（DefaultAdvisorAutoProxyCreator）
```xml
<!--默认Advisor自动代理生成器-->
<bean id="serviceProxy" class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"/>
```
```java
// 如果使用自动代理服务器，则此处getBean使用目标对象的id即可
ISomeService service = (ISomeService) ac.getBean("someService");
```

### Bean名称自动代理生成器（BeanNameAutoProxyCreator）
默认自动代理生成器也有一个缺陷：无法指定要增强的目标对象，默认是所有对象都增强。
为解决这一问题引入了Bean名称自动代理生成器。
```xml
<!--Bean名称自动代理生成器，不仅可以指定目标对象，还可以指定切面-->
<bean id="serviceProxy" class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
    <property name="beanNames" value="someService1"/>
    <property name="interceptorNames" value="beforeAdvice"/>
</bean>
```

## 动态代理探究
### JDK动态代理和CGLIB代理生成的区别
JDK动态代理只能对实现了接口的类生成代理，而不能针对类 。
CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法 。所以方法不能是final的。

默认的策略是如果目标类是`接口`，则使用`JDK`动态代理技术，如果目标对象没有实现接口，则默认会采用`CGLIB`代理。

如果目标对象实现了接口，可以强制使用CGLIB实现代理（添加CGLIB库，并在spring配置中加入`<aop:aspectj-autoproxy proxy-target-class="true"/>`）。

可参见：[JDK和CGLIB生成动态代理类的区别](http://www.cnblogs.com/binyue/p/4519652.html)