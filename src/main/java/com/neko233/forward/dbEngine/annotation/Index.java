package com.neko233.forward.dbEngine.annotation;

import java.lang.annotation.*;

/**
 * @author LHJ
 * date on 2021/7/5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Index {

    /**
     * 根据 groupName 创建
     *  组合索引 | 最左匹配原则
     * */
    String groupName() default "";

    /**
     * 唯一索引 ?
     * */
    boolean uniqueIndex() default false;



}
