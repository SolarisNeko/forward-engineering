package orm;

import com.cokutau.common.dbconnector.Db;
import com.example.springdemo.model.GameScore;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class OrmUtil {


    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<String> queryColumnsFromSql = getQueryColumnsFromSql("select `userId`, a, b, gameId From ddd");

        GameScore test = orm(null, "select `userId`, a, b, gameId From ddd", GameScore.class);
        System.out.println(test);


    }

    public static <T> T orm(Db db, String sql, Class clazz, Object... params) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<String> queryColumns = getQueryColumnsFromSql(sql);

        Map<String, Class> targetColumns = getTargetColumnsFromClass(clazz, queryColumns);
        Map<String, Method> targetSetters = getTargetSetterMethods(clazz, targetColumns);

        Object obj = null;
        for (Map.Entry<String, Class> entry : targetColumns.entrySet()) {
            String fieldName = entry.getKey();

            obj = clazz.newInstance();
            Method method = targetSetters.get(fieldName.toLowerCase());
            // value 从 rs 获取
//            Object value = chooseValueByType(rs, method.getReturnType(), fieldName);
            Integer value = 123;
            if (method == null) {
                continue;
            }
            method.invoke(obj, value);
        }
        return (T) obj;
    }

    public static Object chooseValueByType(ResultSet rs, Class clazz, String fieldName) throws SQLException {
        String typeName = clazz.getTypeName();
        Object value = null;
        switch (typeName) {
            case "String": {
                value = rs.getString(fieldName);
                break;
            }
        }
        return value;
    }

    private static Map<String, Method> getTargetSetterMethods(Class clazz, Map<String, Class> targetColumns) {
        Map<String, Method> methodMap = new HashMap<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (!methodName.startsWith("set")) {
                continue;
            }
            String fieldInMethod = methodName.substring(3);
            Class aClass = targetColumns.get(fieldInMethod.toLowerCase());
            if (aClass != null) {
                methodMap.put(fieldInMethod.toLowerCase(), method);
            }
        }
        return methodMap;
    }

    private static Map<String, Class> getTargetColumnsFromClass(Class clazz, List<String> queryColumns) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Class> columnNames = Arrays.stream(fields).collect(toMap(Field::getName, Field::getType, (t1, t2) -> t1));
        Map<String, Class> targetColumns = columnNames.entrySet().stream()
                .filter(col -> queryColumns.contains(col.getKey().toLowerCase()))
                .collect(toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue, (v1, v2) -> v1));
        return targetColumns;
    }


    private static List<String> getQueryColumnsFromSql(String sql) {
        String lowerSql = sql.toLowerCase();
        int select = lowerSql.indexOf("select");
        int from = lowerSql.indexOf("from");
        String columnsSql = lowerSql.substring(select + 6, from);
        List<String> originalQueryColumns = Arrays.stream(columnsSql.split(",")).map(String::trim).map(column -> column.replace("`", "")).collect(toList());
        return originalQueryColumns;
    }

}
