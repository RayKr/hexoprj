---
title: Spring Notes：7_Spring与DAO
date: 2016-05-16 15:18:58
categories:
- java-framework
tags:
- spring
- java
- framework
---

## 概述
DAO其实就是Spring两种核心技术`IoC`和`AOP`的典型应用体现。

## Spring与JDBC模板
### 环境搭建
jar包
- spring-jdbc包：`spring-jdbc-4.2.5.RELEASE.jar`
- 事务包：`spring-tx-4.2.5.RELEASE.jar`
- 连接池包：[commons-pool-1.6.jar](http://pan.baidu.com/s/1ca9F8E)

### 配置数据源
有三种数据源配置方法，在使用时选择其中一种即可：
```xml
<!-- 注册数据源：Spring默认数据源 -->
<bean id="myDataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
    <property name="url" value="jdbc:oracle:thin:@localhost:1521:test"/>
    <property name="username" value="root"/>
    <property name="password" value="123"/>
</bean>
```
<!--more-->

DBCPjar包下载：[commons-dbcp-1.4.jar](http://pan.baidu.com/s/1c1Ahppm)
```xml
<!-- 注册数据源：DBCP数据源 -->
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource">
    <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"/>
    <property name="url" value="jdbc:oracle:thin:@localhost:1521:test"/>
    <property name="username" value="root"/>
    <property name="password" value="123"/>
</bean>
```
C3P0jar包下载：[c3p0-0.9.5.2.jar](http://pan.baidu.com/s/1dE0BKD7)
```xml
<!-- 注册数据源：C3P0数据源 -->
<bean id="myDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="oracle.jdbc.driver.OracleDriver"/>
    <property name="jdbcUrl" value="jdbc:oracle:thin:@localhost:1521:test"/>
    <property name="user" value="root"/>
    <property name="password" value="123"/>
</bean>
```
可以把数据源的配置数据单独抽离出来放在`.properties`文件里，在xml里注册JDBC属性文件，再使用`${}`调用即可。
jdbc-odbc jar包下载：[ojdbc6-12.1.0.2.jar](http://pan.baidu.com/s/1i5DBeF7)
```properties
jdbc.driverClass=oracle.jdbc.driver.OracleDriver
jdbc.url=jdbc:oracle:thin:@localhost:1521:test
jdbc.user=root
jdbc.password=123
```
```xml
<!-- 注册JDBC属性文件-->
<context:property-placeholder location="classpath:jdbc.properties"/>

<!-- 注册数据源：C3P0数据源 -->
<bean id="myDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="${jdbc.driverClass}"/>
    <property name="jdbcUrl" value="${jdbc.url}"/>
    <property name="user" value="${jdbc.user}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

### 注册JdbcTemplate模板
```xml
<!-- 注册JdbcTemplate-->
<bean id="myJdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <property name="dataSource" ref="myDataSource"/>
</bean>

<!-- 注册Dao -->
<bean id="studentDao" class="com.swroom.dao.StudentDaoImpl">
    <property name="jdbcTemplate" ref="myJdbcTemplate"/>
</bean>
```
注意事项：
- 注册JdbcTemplate，需要注明使用的dataSource属性，用ref引用。
- 注册Dao，需要注明使用的jdbcTemplate属性，用ref引用。

另外，由于`JdbcDaoSupport`类中有个`setDataSource`方法，如果jdbcTemplate为空则自动创建模板，所以在xml中注册时，我们可以直接将数据源注册给Dao，省去模板的注册。
```xml
<!-- 注册Dao -->
<bean id="studentDao" class="com.swroom.dao.StudentDaoImpl">
    <property name="dataSource" ref="myDataSource"/>
</bean>
```

## Dao使用JdbcTemplate
### 基本类型返回值
```java
@Override
public void insertStudent(Student student) {
    String sql = "INSERT INTO student(name, age) VALUES (?,?)";
    this.getJdbcTemplate().update(sql, student.getName(), student.getAge());
}

@Override
public void deleteStudent(int id) {
    String sql = "DELETE FROM student WHERE id = ?";
    this.getJdbcTemplate().update(sql, id);
}

@Override
public void updateStudent(Student student) {
    String sql = "UPDATE student SET NAME = ?, age = ? WHERE id = ?";
    this.getJdbcTemplate().update(sql, student.getName(), student.getAge(), student.getId());
}

@Override
public String selectStudentNameById(int id) {
    String sql = "select name from student where id = ?";
    return this.getJdbcTemplate().queryForObject(sql, String.class, id);
}

@Override
public List<String> selectStudentNames() {
    String sql = "select name from student";
    return this.getJdbcTemplate().queryForList(sql, String.class);
}
```
注意事项：
- 添加、删除、修改都是用`update`方法
- 查询方法有：`queryForObject`,`queryForList`,`query`
- query..()方法中有个`值类型`参数
- `this.getJdbcTemplate()`是不可以抽取变量的，因为JdbcTemplate是多例的。会为每个调用的方法创建新的对象，方法执行结束就会被回收。

### 自定义类型返回值
```java
@Override
public Student selectStudentById(int id) {
    String sql = "select id, name, age from student where id = ?";
    return this.getJdbcTemplate().queryForObject(sql, new StudentMapper(), id);
}

@Override
public List<Student> selectStudents() {
    String sql = "select id, name, age from student where";
    return this.getJdbcTemplate().query(sql, new StudentMapper());
}
```
```java
public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setAge(rs.getInt("age"));
        return student;
    }
}
```
注意事项：
- 返回的是Object类型，但是sql语句查询的是各字段，spring没办法帮我们封装好，所以需要我们自己写一个装配类。
- 装配类实现`RowMapper<T>`接口，作用就是将`结果集`转换成`对象`。
- mapRow参数中的`ResultSet`指的是所有结果集中的当前行，就只是一行数据，所以可以直接`rs.getInt("id")`。
