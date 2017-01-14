---
title: Oracle自增ID的实现方式
date: 2016-05-16 16:10:09
categories:
- oracle
tags:
- oracle
- sql
- database
---
MySql关于自增ID的实现非常简单，只需要在DDL语句中定义一个`AUTO_INCREMENT`即可，而Oracle不行。
基本理念是使用Oracle序列`Sequences`，创建触发器`Trigger`，在每次插入表中数据时，检查自增段是否为空，如果为空则取出下一个序列值插入到该字段中。

1. 创建测试表
```sql
create table student (
  id number,
  name varchar(10),
  age number
)
```

2. 创建序列
```sql
create sequence stu_seq
increment by 1 --每次加1
start with 1  --从1开始
nomaxvalue  --不设置最大值
nocycle  --一直累加，不循环
nocache  --不建缓冲区
```

<!--more-->

3. 创建触发器
```sql
create trigger stu_trigger before
insert on student for each row when(new.id is null)
begin
  select stu_seq.nextval into :new.id from dual;
end;
```

4. 插入测试数据
```sql
insert into student(name, age) values('李四', 34);
```

5. 查看表数据
```sql
select * from student;
```
