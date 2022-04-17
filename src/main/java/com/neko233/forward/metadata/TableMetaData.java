package com.neko233.forward.metadata;

import com.neko233.forward.annotation.Table;
import com.neko233.forward.constant.CharsetEnum;
import com.neko233.forward.constant.EngineEnum;
import com.neko233.forward.util.CamelCaseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableMetaData {

    private Class<?> originalClass;

    private String tableName;

    private String charset;

    private String engine;

    private String comment;

    /**
     * give className, return tableName in [Big Camel] format.
     * 给类名, 返回大驼峰表名。
     */
    public static TableMetaData getTableMetaDataByClass(Class<?> targetClass) {
        // 最终构建的【大驼峰Table名字】
        TableMetaData tableMetaData = new TableMetaData();
        tableMetaData.setOriginalClass(targetClass);

        String tableName;
        String engine;
        String charset;
        String comment = "";

        // get Annotation
        Table[] tableAnnotation = targetClass.getAnnotationsByType(Table.class);

        if (tableAnnotation.length == 0) {
            /** 没有 @Table = 使用默认值 */
            tableName = CamelCaseUtil.toBigCamelUpperName(
                    targetClass.getSimpleName()
            );
            engine = EngineEnum.INNODB.getEngine();
            charset = CharsetEnum.UTF8MB4.getCharset();

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
                    tableName = CamelCaseUtil.toBigCamelUpperName(
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
