package com.neko233.forward.dbEngine.factory;

import com.neko233.forward.dbEngine.metadata.ColumnMetaData;

import java.util.Arrays;

/**
 * title:
 * description:
 * @author SolarisNeko
 * SolarisNeko 2021/7/4
 */
public class ColumnFactory {

    public static ColumnMetaData getColumnMetaDataByClass(Class<?> aClass) {
        if (aClass == null) {
            throw new RuntimeException("Class can't not be null.");
        }
        // get Field[]
        return ColumnMetaData.builder()
                .fieldList(Arrays.asList(aClass.getDeclaredFields()))
                .build();
    }

}
