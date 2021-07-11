package com.neko.forward.factory;

import com.neko.forward.annotation.Table;
import com.neko.forward.constant.CharsetEnum;
import com.neko.forward.constant.EngineEnum;
import com.neko.forward.util.CharacterUtil;
import com.neko.pojo.dto.TableDTO;

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
    public static TableDTO getTableNameByClass(Class<?> targetClass) {
        // 最终构建的【大驼峰Table名字】
        TableDTO tableDTO = new TableDTO();
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

            tableDTO.setTableName(tableName);
            tableDTO.setEngine(engine);
            tableDTO.setCharset(charset);
            tableDTO.setComment(comment);
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

                tableDTO.setTableName(tableName);
                tableDTO.setEngine(engine);
                tableDTO.setCharset(charset);
                tableDTO.setComment(comment);
            }
        }

        return tableDTO;
    }
}
