package com.neko233.forward.strategy.generate;

import com.neko233.forward.metadata.ColumnMetaData;
import com.neko233.forward.metadata.TableMetaData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class GenerateStrategyTest {

    @Mock
    Map<String, GenerateStrategy> STRATEGY_MAP;

    @InjectMocks
    MysqlGenerateStrategy generateStrategy;


    @Test
    public void testRegisterStrategy() throws Exception {
        try {
            GenerateStrategy.registerStrategy("mysql", new MysqlGenerateStrategy());
        } catch (Exception e) {
            Assertions.assertEquals("Already have this key = mysql", e.getMessage());
        }
    }

    @Test
    public void testGetStrategyByDbType() throws Exception {
        GenerateStrategy result = GenerateStrategy.getStrategyByDbType("mysql");
        boolean isMySql = result instanceof MysqlGenerateStrategy;
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
            result = generateStrategy.getColumnSqlList(new ColumnMetaData(Arrays.<Field>asList(null)));
        } catch (Exception e) {
        }
        Assertions.assertNull(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme