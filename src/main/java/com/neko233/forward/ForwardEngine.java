package com.neko233.forward;

import com.neko233.forward.constant.DbType;
import com.neko233.forward.factory.FileFactory;
import com.neko233.forward.strategy.generate.GenerateStrategy;
import com.neko233.forward.scan.PackageScanner;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @title: Engine.
 * @description: 正向工程引擎，类似于 Facade Pattern.
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
@Slf4j
public class ForwardEngine {

    private static GenerateStrategy SQL_GENERATE_FACTORY;

    /**
     * 运行单个 class
     */
    public static String runClass(DbType dbType, String className) {
        return runClass(dbType.getName(), className);
    }

    public static String runClass(DbType dbType, Class<?> clazz) {
        return runClass(dbType.getName(), clazz);
    }

    public static String runClass(String dbType, String className) {
        // 1  - 扫描 package 下的所有 Class
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("该 class 不存在");
        }
        return runClass(dbType, clazz);
    }

    public static String runClass(String dbType, Class<?> clazz) {

        // super Factory
        SQL_GENERATE_FACTORY = GenerateStrategy.getStrategyByDbType(dbType);
        System.out.println("---------- 正向工程 Start ----------------------");
        // 投入 class
        String createSql = SQL_GENERATE_FACTORY.generateCreateSqlByClass(clazz);
        System.out.println("\n" + createSql + "\n");
        System.out.println("---------- 正向工程 End ----------------------");
        return createSql;
    }

    /**
     * 扫描 package 下的所有 class, 会过滤掉所有 Lombok 生成的 Builder
     */
    public static void runPackage(DbType dbType, String packageName) {
        runPackage(dbType.getName(), packageName);
    }

    public static void runPackage(String dbType, String packageName) {

        // 1、scan all Class in package
        List<Class<?>> allClasses = PackageScanner.getClasses(packageName);

        // 2. filter name end with Builder, delete Design Pattern Builder's class
        List<Class<?>> classes = allClasses.stream()
                .filter(clazz -> !clazz.getSimpleName().toLowerCase(Locale.ROOT).endsWith("builder"))
                .collect(Collectors.toList());

        // super Factory
        SQL_GENERATE_FACTORY = GenerateStrategy.getStrategyByDbType(dbType);

        // 2、正向工程
        System.out.println("---------- 正向工程 Start ----------------------");
        StringBuilder sqlBuilder = new StringBuilder();
        for (Class<?> clazz : classes) {
            // 投入 class
            String createTableSql = SQL_GENERATE_FACTORY.generateCreateSqlByClass(clazz);
            sqlBuilder.append(createTableSql);
        }

        //  todo - 扫描 package, 会生成文件
        try {
            // 生成 .sql 文件 | 如果已存在, 则覆盖
            System.out.println("请稍等.. \n");
            // 计算节省时间
            long needSeconds = sqlBuilder.toString().length() / 2 / 60;
            System.out.println("帮你节省 " + needSeconds + " 分钟 \n");

            /** 生成文件 */
            FileFactory.generateSqlFile(sqlBuilder.toString());

            System.out.println("正向工程 Finished!\n\n请检查项目中的新文件夹 ~/SQL/target.sql \n");
        } catch (FileNotFoundException e) {
            System.err.println("写入到文件失败！");
        } catch (IOException e) {
            System.err.println("创建文件失败");
        }

        System.out.println("---------- 正向工程 End ----------------------");
    }

    /**
     * 注册自定义的【生成策略】
     * @param key 策略名
     * @param strategy 策略
     */
    public static void registerGenerateStrategy(String key, GenerateStrategy strategy) {
        GenerateStrategy.registerStrategy(key, strategy);
    }


    /**
     * 文档
     */
    public static void readMe() {
        System.out.println("------------ How to use ForwardEngineer -------------------");
        System.out.println();
        System.out.println("入口类 = ForwardEngineer.java ");
        System.out.println();
        System.out.println("单个 .java 文件 : ");
        System.out.println(" ForwardEngine.runClass(\"com.neko.pojo.entity.SystemUser\"); ");
        System.out.println();
        System.out.println("扫描 package :");
        System.out.println(" ForwardEngine.runPackage(\"com.neko.pojo.entity\"); ");
        System.out.println();
        System.out.println("-----------------------------------------------");
    }

}
