---
title: Maven配置
date: 2016-05-20 17:07:58
categories:
- experience
tags:
- java
---
## Maven离线仓库
```xml
<!-- Maven official repository -->
<repositories>
    <repository>
        <id>maven</id>
        <name>Maven Repository Switchboard</name>
        <layout>default</layout>
        <url>http://repo1.maven.org/maven2</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>alibaba-opensource</id>
        <name>alibaba-opensource</name>
        <url>http://code.alibabatech.com/mvn/releases/</url>
        <layout>default</layout>
    </repository>
    <repository>
        <id>alibaba-opensource-snapshot</id>
        <name>alibaba-opensource-snapshot</name>
        <url>http://code.alibabatech.com/mvn/snapshots/</url>
        <layout>default</layout>
    </repository>
</repositories>
```

## POM文件依赖格式
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.2.6.RELEASE</version>
    </dependency>
</dependencies>
```

## POM插件配置
```xml
<build>
	<plugins>
	    <plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>1.6</source>
                <target>1.6</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
	</plugins>
</build>
```
