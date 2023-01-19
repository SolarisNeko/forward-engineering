package com.neko233.forward.strategy.generate;

import com.neko233.forward.dbEngine.GenerateSqlApiFactory;
import com.neko233.forward.dbEngine.metadata.ColumnMetaData;
import com.neko233.forward.dbEngine.metadata.TableMetaData;
import com.neko233.forward.dbEngine.GenerateSqlApi;
import com.neko233.forward.dbEngine.generate.MysqlGenerateSqlApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author SolarisNeko
 * Date on 2022-04-13
 */
@ExtendWith(MockitoExtension.class)
public class GenerateSqlApiTest {

    @Mock
    Map<String, GenerateSqlApi> STRATEGY_MAP;

    @InjectMocks
    MysqlGenerateSqlApi generateStrategy;


    @Test
    public void testRegisterStrategy() throws Exception {
        try {
            GenerateSqlApiFactory.register("mysql", new MysqlGenerateSqlApi());
        } catch (Exception e) {
            // is ok
            return;
        }
    }

    @Test
    public void testGetStrategyByDbType() throws Exception {
        GenerateSqlApi result = GenerateSqlApiFactory.choose("mysql");
        boolean isMySql = result instanceof MysqlGenerateSqlApi;
        Assertions.assertTrue(isMySql);
    }

    @Test
    public void testGenerateCreateSqlByClass() throws Exception {
        String result = null;
        try {
            result = generateStrategy.generateCreateSqlByClass(null);
        } catch (Exception e) {
        }
        Assertions.assertNull(result);
    }

    @Test
    public void testGenerateTableSql() throws Exception {
        String result = null;
        try {
            result = generateStrategy.generateTableSql(new TableMetaData(null, "tableName", "charset", "engine", "comment"), new ColumnMetaData(Arrays.<Field>asList(null)));
        } catch (Exception e) {
        }
        Assertions.assertNull(result);
    }

    @Test
    public void testGetColumnSqlList() throws Exception {
        List<String> result = null;
        try {
            result = generateStrategy.generateColumnSqlList(new ColumnMetaData(Arrays.<Field>asList(null)));
        } catch (Exception e) {
        }
        Assertions.assertNull(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme