package com.neko233.forward;

import com.neko233.forward.codeEngine.CodeEngine;
import com.neko233.forward.codeEngine.CodeEngineFactory;
import com.neko233.forward.codeEngine.domain.CodeEngineMetadata;
import com.neko233.forward.util.PackageScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @title: Engine.
 * @description: 正向工程引擎 for Code
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
@Slf4j
public class ForwardEngineForCode {


    /**
     * 运行单个 class
     */
    public static String runClass(String strategyType, String className) {
        // 1  - 扫描 package 下的所有 Class
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("this class not found. 没找到该类");
        }
        return runClass(strategyType, clazz, new HashMap<>());
    }

    public static String runClass(String strategyType, Class<?> clazz) {
        return runClass(strategyType, clazz, new HashMap<>());
    }

    public static String runClass(String strategyType, Class<?> clazz, Map<String, String> diyConfigMap) {

        final CodeEngine codeEngine = CodeEngineFactory.choose(strategyType);

        // super Factory
        log.info("---------- 正向工程 Start ----------------------");
        // 投入 class
        CodeEngineMetadata metadata = CodeEngineMetadata.builder()
                .diyConfigMap(Optional.ofNullable(diyConfigMap).orElse(Collections.emptyMap()))
                .targetClass(clazz)
                .build();
        String mvcCode = codeEngine.generateCode(metadata);
        log.info("---------- 正向工程 End ----------------------");
        log.info("---------- you need to print return value by yourself ----------------------");
        return mvcCode;
    }

    /**
     * 扫描 package 下的所有 class, 会过滤掉所有 Lombok 生成的 Builder
     */
    public static String runPackage(String strategyType, String packageName) {
        return runPackage(strategyType, packageName, new HashMap<>());
    }

    public static String runPackage(String strategyType, String packageName, Map<String, String> diyConfigMap) {
        // 1、scan all Class in package
        List<Class<?>> allClasses = PackageScanner.getClasses(packageName);

        // 2. filter name end with Builder, delete Design Pattern Builder's class | see Lombok
        List<Class<?>> classes = allClasses.stream()
                .filter(clazz -> !clazz.getSimpleName().toLowerCase(Locale.ROOT).endsWith("builder"))
                .collect(Collectors.toList());


        log.info("---------- 正向工程 Start ----------------------");
        StringBuilder codeBuilder = new StringBuilder();
        for (Class<?> clazz : classes) {
            // 生成 services
            String mvcCode = runClass(strategyType, clazz, diyConfigMap);
            codeBuilder.append(mvcCode);
            codeBuilder.append(System.lineSeparator() + "// class = " + clazz.getName() + System.lineSeparator());
        }

        log.info("---------- 正向工程 End ----------------------");

        return codeBuilder.toString();
    }

    /**
     * 注册自定义的【生成策略】
     *
     * @param key    策略名
     * @param engine 策略
     */
    public static void registerGenerateStrategy(String key, CodeEngine engine) {
        CodeEngineFactory.register(key, engine);
    }


    /**
     * 文档
     */
    public static void readMe() {
        log.info("------------ How to use ForwardEngineer -------------------");
        log.info("入口类 = ForwardEngineer.java ");
        log.info("[Demo] 单个 .java 文件 : ");
        log.info(" ForwardEngine.runClass(\"com.neko233.forward.pojo.entity.SystemUser\"); ");
        log.info("[Demo] 扫描 package :");
        log.info(" ForwardEngine.runPackage(\"com.neko233.forward.pojo.entity\"); ");
        log.info("-----------------------------------------------");
    }

}
