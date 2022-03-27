package com.neko233.forward.factory;

import com.neko233.forward.annotation.Table;
import com.neko233.forward.constant.CharsetEnum;
import com.neko233.forward.constant.EngineEnum;
import com.neko233.forward.entity.TableMetaData;
import com.neko233.forward.util.CharacterUtil;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class TableFactory {

    /**
     * give className, return tableName in [Big Camel] format.
     * 给类名, 返回大驼峰表名。
     */
    public static TableMetaData getTableNameByClass(Class<?> targetClass) {
        // 最终构建的【大驼峰Table名字】
        TableMetaData tableMetaData = new TableMetaData();
        String tableName;
        String engine;
        String charset;
        String comment = "";

        // get Annotation
        Table[] tableAnnotation = targetClass.getAnnotationsByType(Table.class);

        if (tableAnnotation.length == 0) {
            /** 没有 @Table = 使用默认值 */
            tableName = CharacterUtil.toBigCamelUpperName(
                    targetClass.getSimpleName()
            );
            engine = EngineEnum.INNODB.getEngine();
            charset = CharsetEnum.UTF8.getCharset();

            tableMetaData.setTableName(tableName);
            tableMetaData.setEngine(engine);
            tableMetaData.setCharset(charset);
            tableMetaData.setComment(comment);
        } else {
            // parse Annotation
            for (int i = 0; i < tableAnnotation.length; i++) {
                Table table = tableAnnotation[i];
                // 获取 tableName, engine, charset
                tableName = table.value();
                engine = table.engine();
                charset = table.charset();
                comment = table.comment();

                if ("".equals(tableName)) {
                    /**
                     * 大驼峰 = 将 SystemUser -> SYSTEM_USER
                     * */
                    tableName = CharacterUtil.toBigCamelUpperName(
                            targetClass.getSimpleName()
                    );
                }

                tableMetaData.setTableName(tableName);
                tableMetaData.setEngine(engine);
                tableMetaData.setCharset(charset);
                tableMetaData.setComment(comment);
            }
        }

        return tableMetaData;
    }
}
