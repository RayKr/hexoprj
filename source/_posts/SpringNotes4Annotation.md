---
title: Spring Notes：4_Annotation
date: 2016-05-09 15:52:45
categories:
- java-framework
tags:
- spring
- java
- framework
---

## 搭建
1. 引用AOP的jar包；替换为context约束
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
```

2. 扫描包注意事项：a.package用点，path用/
```xml
<!-- .* 只扫描子包，不扫描当前包-->
<context:component-scan base-package="com.swroom.*"/>
<!-- 先扫描当前包，再扫描子包-->
<context:component-scan base-package="com.swroom"/>
```

## 类注解
### @Component @Scope作用域
```java
// 与此注解功能相同的还有三个
// @Repository  注解在Dao接口的实现类上，表示当前Dao为组件
// @Service   注解在Service接口的实现类上，表名当前Service为组件
// @Controller  注解Controller类上，表名当前Controller为组件
@Component("myStudent")   // 表明当前类为组件，并且创建的这个组件的对象名称为myStudent。类似于<bean />的id
@Scope("prototype") //作用域，默认singleton
public class Student {
```

<!--more-->

## 注解属性
### @Value("value")
```java
@Value("张三") //放置在属性上的注解（常用）
private String name;
private int age;

public void setName(String name) {
    this.name = name;
}

@Value("25") // 也可以注解在setter方法上，但是不可以放置在getter方法上
public void setAge(int age) {
    this.age = age;
}
```

## 注解域属性
### @Resource
```java
@Resource(name = "mySchool") //byName
private School school;
@Resource  // byType,必须满足is-a关系
private School school;
```

### @Autowired
```java
@Autowired  // byType方式，spring提供的注解
private School school;
@Autowired
@Qualifier("mySchool")  // byName方式，两个注解必须一起用
private School school;
```

## 注解构造之后初始化和销毁之前的方法

### @PostConstruct
```java
@PostConstruct
public void initSome() {
    System.out.println("构造之后初始化。。。");
}
```

### @PreDestroy
```java
@PreDestroy
public void preDead() {
    System.out.println("销毁之前");
}
```

## JavaConfig注解

### @Configuration
```java
/**
 * 使用bean充当spring配置文件，就相当于容器
 */
@Configuration
public class MyJavaConfig {

    @Bean(name = "mySchool")
    public School mySchoolCreator() {
        return new School("南京大学");
    }

    @Bean(name = "myStudent", autowire = Autowire.BY_NAME)
    public Student myStudentCreator() {
        return new Student("张三", 26);
    }
}
```
【注意事项】：
    a. 类注解使用@Configuration
    b. 容器生成的Bean的方法上使用@Bean注解，可注明其他属性，例如name
    c. Autowire.BY_NAME 和 Autowire.BY_TYPE，byName方式需要注意一点，Bean的id和属性变量名称必须一致才可以。

## 使用SpringJunit4进行测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/swroom/di05/applicationContext.xml")
public class MyTest {

    @Autowired
    @Qualifier("myStudent")
    private Student iStudent;

    @Test
    public void test01() {
        System.out.println(iStudent);
    }
}
```

## xml配置文件和注解同时存在
`配置文件`起作用。因为配置文件修改后不用编译重启服务器即可生效，所以便于以后的维护和修改，配置文件的优先级更高。