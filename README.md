# ForwardEngine 正向工程
# 介绍
@author: SolarisNeko

English:

ForwardEngineering is a Java Class -> Code Generator Tech.
1. you can generate DB Table DDL (example: MySQL)
2. you can generate Spring Web MVC Code
3. you can generate Event Handler in Reactive Style by your self~
Official only support very simple Template to you, but you can DIY your self. Enjoy it!

中文:

正向工程, 是一款将 Java Class 快速翻译成 DB Table / Code 的代码生成工具。

支持 class / package 扫描（Spring Boot 一样~）





# Use

```xml
<dependency>
    <groupId>com.neko233</groupId>
    <artifactId>forward-engineering</artifactId>
    <version>1.0.0</version>
</dependency>
```

# ForwardEngine Code
正向引擎（Java -> RDS，关系型数据库）

目前支持
1. MySQL
2. Hive
```java
// 文档
ForwardEngine.readMe();

// 单个 pojo -> SQL
    ForwardEngine.runClass("com.neko.pojo.entity.SystemUser");

// 整个 package -> SQL
    ForwardEngine.runPackage("com.neko.pojo.entity");

```


## Demo / 演示效果

```java

@Data
public class Student {
    private long id;
    private String username;
    private int sex;
    private int age;
    private Date admissionDate;
}
```

```sql
Create Table STUDENT
(
    `id`             bigint,
    `username`       varchar(255),
    `sex`            int,
    `age`            int,
    `admission_date` datetime
) engine = InnoDB,
  charset = utf8;
```

## diy your protocol / 自定义 SQL 生成方式
```java
// register your diy strategy
ForwardEngine.registerGenerateStrategy("postgre", new YourPostgreStrategy);
```


# CodeForwardEngine 代码生成引擎

简单的代码生成引擎，相比其他的代码生成技术，没有提供很多拿来即用的功能

但是提供了给你自己 diy 自己模板的手段。

CodeForwardEngine 只是提供一个统一的生成 API & 统一的扩展

## Use
generate SpringBoot-Web-Mvc Code

为什么不直接生成文件? 你可以自己改写 Template 处理~ 因为很简单.

实际代码中, CRUD 和 DAO 的方式各有不同. 

### Email DTO
```java
@Data
public class Email {

    int id;
    String username;
    String lastLoginDate;

}

```

### use CodeForwardEngine
```java
    @Test
public void runClass() {
        String s = CodeForwardEngine.runClass(CodeEngineName.JAVA_SPRING_WEB_2_x_x, Email.class);
        System.out.println(s);
    }
```

### DIY your CodeForwardEngine
```java

```

---------

# 补充

​ 目前代码中，很多注释，很乱。一定版本迭代后，会重新梳理。

​ 目前注释只是为了记录详细一点，好让我思路不中断。

​ 希望大家能提出很 cool 的建议~

