//package com.neko.forward.facade;
//
//import com.neko.forward.factory.SqlFactory;
//import com.neko.pojo.dto.TableDTO;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @title: 菜单模式（门面模式）
// * @description: 提供菜单，直接点单，给出最终的订单（/结果）
// * @author: SolarisNeko
// * @date: 2021/7/4
// */
//@Getter
//@Setter
//public class DbFacade {
//
//
//    public static String chooseDbSQL(TableDTO tableDTO, List<String> columnSqlList) {
//
//        /**
//         * 支持不同的数据库, 通过 SqlFactory 构建 完整的建表SQL
//         *  ps: 后续, 做成扩展接口
//         * */
//        switch (dbType) {
//            case "mysql": {
//                String tableSQL = SqlFactory.makeTableSqlForMySQL(tableDTO, columnSqlList);
//                return tableSQL;
//            }
//            default: {
//
//            }
//        }
//
//        return "";
//    }
//
//}
