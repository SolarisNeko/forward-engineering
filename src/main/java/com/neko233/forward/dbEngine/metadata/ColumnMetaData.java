package com.neko233.forward.dbEngine.metadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2022-04-12
 */
@Data
@AllArgsConstructor
@Builder
public class ColumnMetaData {

    private List<Field> fieldList;

    public static ColumnMetaData getColumnMetaDataByClass(Class<?> aClass) {
        // get Field[]
        return ColumnMetaData.builder()
                .fieldList(Arrays.asList(aClass.getDeclaredFields()))
                .build();
    }

}
