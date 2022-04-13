package com.neko233.forward.factory;

import com.neko233.forward.metadata.ColumnMetaData;

import java.util.Arrays;

/**
 * @title:
 * @description:
 * @author: SolarisNeko
 * @date: 2021/7/4
 */
public class ColumnFactory {

    public static ColumnMetaData getColumnMetaDataByClass(Class<?> aClass) {
        if (aClass == null) {
            throw new RuntimeException("Class can't not be null.");
        }
        // get Field[]
        return ColumnMetaData.builder()
                .columnList(Arrays.asList(aClass.getDeclaredFields()))
                .build();
    }

}
