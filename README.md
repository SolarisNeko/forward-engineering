# ForwardEngineer 正向工程
@author: SolarisNeko

Forward Engineering, use to Java Pojo -> Table DDL.

Now, just support:
1. MySQL

正向工程，用于将 Java Pojo -> Table DDL。



# Base
## Use

```xml

<dependency>
    <groupId>com.neko233</groupId>
    <artifactId>forward-engineering</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Code

```java
// 文档
ForwardEngineer.readMe();

// 单个 pojo -> SQL
    ForwardEngineer.runClass("com.neko.pojo.entity.SystemUser");

// 整个 package -> SQL
    ForwardEngineer.runPackage("com.neko.pojo.entity");

```

# 演示效果

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

## 示意图

见 /README.assets/* 下的图片

# 进度

## 1、已完成

1. 使用 @Table、@Column 生成细粒度的SQL 。
2. 单个class, 扫描package。
3. 对原生 Pojo 的无侵入支持
4. 生成 .sql 文件
5. 加入了文档API
6. 已加入 SQL comment 支持

## 2、未来计划

1. 加入 @Constraint 支持（约束）。
2. 加入对 @Index 支持（索引）。

------

# 介绍

## 1、preface 前言

​ Author still learning, is a Rookie.

​ Give me some issue make me think it is cool .

> 作者是一个菜鸟，还在学习。
>
> 开源，希望大家给我一些很 cool 的建议~

## 2、Origin Intention 初衷

### 原因 1

​ 公司有一个框架，用 `java` 写 `SQL`，维护表结构。

​ 虽然 **高度封装 API** , 但依旧非常麻烦.

​ 在某些 Table 设计不合理下，很辛苦，比我写 SQL 慢太多了（慢1000%+) 。

> 1、Java 即 SQL

### 原因 2

​ 同时，随着时代发展，我们的 Table 逐渐去除 FK（Foreign Key），当 Table 命名不规范的时候，你根本不知道一些==字段的关联关系==。

> 2、Table 关系可视化



---------

# 补充

​ 目前代码中，很多注释，很乱。一定版本迭代后，会重新梳理。

​ 目前注释只是为了记录详细一点，好让我思路不中断。

​ 希望大家能提出很 cool 的建议~

