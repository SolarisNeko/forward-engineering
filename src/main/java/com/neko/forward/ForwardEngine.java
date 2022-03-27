package com.neko.forward;

import com.neko.forward.constant.DbType;
import com.neko.forward.factory.FileFactory;
import com.neko.forward.factory.DatabaseAbstractFactory;
import com.neko.forward.scan.PackageScanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class ForwardEngine {

    private static DbType dbType = DbType.MYSQL;

    private static DatabaseAbstractFactory sqlFactory;

    /**
     * 运行单个 class
     * */
    public static void runClass(String className) {

        // 1  - 扫描 package 下的所有 Class
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // super Factory
        sqlFactory = DatabaseAbstractFactory.getFactoryByDbType(dbType);

        // 2、正向工程
        System.out.println("---------- 正向工程 Start ----------------------");

            // 投入 class
            String createTableSQL = sqlFactory.getCreateTableSQLByClassName(clazz);

            // Println
            System.out.println();
            System.out.println(createTableSQL);
            System.out.println();

        System.out.println("---------- 正向工程 End ----------------------");
    }

    /**
     * 扫描 package
     */
    public static void runPackage(String packageName) {

        // 1  - 扫描 package 下的所有 Class
        List<Class<?>> classes = PackageScanner.getClasses(packageName);

        // super Factory
        sqlFactory = DatabaseAbstractFactory.getFactoryByDbType(dbType);

        // 2、正向工程
        System.out.println("---------- 正向工程 Start ----------------------");
        StringBuilder sqlSB = new StringBuilder();
        for (Class<?> clazz : classes) {

            // 投入 class
            String createTableSQL = sqlFactory.getCreateTableSQLByClassName(clazz);

            sqlSB.append(createTableSQL);
        }

        //  todo - 扫描 package, 会生成文件
        try {
            // 生成 .sql 文件 | 如果已存在, 则覆盖
            System.out.println("请稍等.. \n");
            // 计算节省时间
            long needSeconds = sqlSB.toString().length() / 2 / 60;
            System.out.println("帮你节省 " + needSeconds + " 分钟 \n");

            /** 生成文件 */
            FileFactory.generateSqlFile(sqlSB.toString());

            System.out.println("正向工程 Finished!\n\n请检查项目中的新文件夹 ~/SQL/target.sql \n");
        } catch (FileNotFoundException e) {
            System.err.println("写入到文件失败！");
        } catch (IOException e) {
            System.err.println("创建文件失败");
        }

        System.out.println("---------- 正向工程 End ----------------------");
    }



    /**
     * 文档
     * */
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


    public static DbType getDbType() {
        return dbType;
    }

    public static void setDbType(DbType dbType) {
        ForwardEngine.dbType = dbType;
    }
}
