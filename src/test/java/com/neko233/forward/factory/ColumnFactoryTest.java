package com.neko233.forward.factory;

import com.neko233.forward.dbEngine.factory.ColumnFactory;
import com.neko233.forward.dbEngine.metadata.ColumnMetaData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author SolarisNeko
 * Date on 2022-04-13
 */
class ColumnFactoryTest {

    @Test
    void testGetColumnMetaDataByClass() {
        ColumnMetaData result = null;
        try {
            result = ColumnFactory.getColumnMetaDataByClass(null);
        } catch (Exception e) {

        }
        Assertions.assertNull(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme