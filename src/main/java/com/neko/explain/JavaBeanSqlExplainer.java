package com.neko.explain;

import com.neko.explain.annotation.NotGenerate;
import com.neko.explain.model.GameScoreHistory;
import com.neko.forward.util.CharacterUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class JavaBeanSqlExplainer {

    private static final DateFormat SQL_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        List<GameScoreHistory> gameScores = new ArrayList<GameScoreHistory>() {{
            add(GameScoreHistory.builder().userId(123).gameId(10).createTime(new Date()).build());
            add(GameScoreHistory.builder().userId(123).gameId(10).build());
            add(GameScoreHistory.builder().userId(233).gameId(20).build());
        }};

        try {
            String generateInsertSql = JavaBeanSqlExplainer.generateInsertSql(gameScores);
            System.out.println(generateInsertSql);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> String generateInsertSql(List<T> collection) throws IllegalAccessException {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }

        T t = collection.get(0);
        String tableName = t.getClass().getSimpleName();
        List<String> columnNames = transformFields2ColumnName(t.getClass().getDeclaredFields());

        String prefixInsertSql = buildPrefixInsertSql(tableName, columnNames);
        String suffixInsertSql = buildSuffixInsertSql(collection);
        return prefixInsertSql + suffixInsertSql;
    }

    private static <T> String buildSuffixInsertSql(List<T> collection) throws IllegalAccessException {
        long start = System.nanoTime();
        StringBuilder suffixSqlBuilder = new StringBuilder();
        for (T t : collection) {
            Field[] fields = t.getClass().getDeclaredFields();
            suffixSqlBuilder.append("(");
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                NotGenerate annotation = field.getAnnotation(NotGenerate.class);
                if (annotation != null) {
                    // delete the last SEPARATOR
                    if (i == fields.length - 1) {
                        suffixSqlBuilder.delete(suffixSqlBuilder.length() - 2, suffixSqlBuilder.length());
                    }
                    continue;
                }

                field.setAccessible(true);
                Object value = field.get(t);

                // adapt object's type to transform value.
                if (value == null) {
                    suffixSqlBuilder.append("null, ");
                } else {
                    suffixSqlBuilder.append(transform2SqlStringByType(value)).append(", ");
                }

                // delete the last SEPARATOR
                if (i == fields.length - 1) {
                    suffixSqlBuilder.delete(suffixSqlBuilder.length() - 2, suffixSqlBuilder.length());
                }
            }
            suffixSqlBuilder.append("), ");
        }
        long end = System.nanoTime();
        double spendTime = (double) (end - start) / 1_000_000_000;
        String spendOneSqlTime = String.valueOf(spendTime / collection.size()).substring(0, 4);
        log.info("forward generate SQL use time: {} s, generate size = {}, average generate a object sql spend {} s",
                spendTime, collection.size(), spendOneSqlTime);
        return suffixSqlBuilder.substring(0, suffixSqlBuilder.length() - 2);
    }

    /**
     * 根据 value 的 type，转换成正常 sql 逻辑
     *
     * @param value 值
     * @return value 对应的 sql String 内容
     */
    private static String transform2SqlStringByType(Object value) {
        String typeName = value.getClass().getTypeName();

        switch (typeName) {
            case "java.lang.String": {
                return "'" + value + "'";
            }
            case "java.util.Date": {
                return "'" + SQL_DATE_FORMATTER.format((Date) value) + "'";
            }
            default: {
                return value.toString();
            }
        }
    }


    /**
     * InnoDB 不支持 insert delayed
     *
     * @param tableName   处理好的表名
     * @param columnNames 处理好的字段名
     * @return Insert Into ${tableName} (columns...) Values
     */
    private static String buildPrefixInsertSql(String tableName, List<String> columnNames) {
        String join = String.join(", ", columnNames);
        return "Insert into " +
                CharacterUtil.toBigCamelLowerName(tableName) +
                "(" +
                join +
                ") Values ";
    }

    /**
     * 根据 field 生成 insert Sql 的 Values (?, ?, ?, ? ) 的东西
     *
     * @param fields 要生成的字段
     * @return 生成后的 (?, ?, ? ), ? 会转换成实际值
     */
    private static List<String> transformFields2ColumnName(Field[] fields) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
            // 不生成
            NotGenerate annotation = field.getAnnotation(NotGenerate.class);
            if (annotation != null) {
                continue;
            }

            String name = field.getName();
            columnNames.add(name);
        }
        return columnNames;
    }

}
