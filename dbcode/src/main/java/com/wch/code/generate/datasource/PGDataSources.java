package com.wch.code.generate.datasource;

import org.mybatis.generator.config.JDBCConnectionConfiguration;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PGDataSources implements Serializable {

    /**
     *
     * keys：ai_qa_cardata,ai_qa_test,ai_pro_cardata,saas_pro_saas
     *
     *
     * pg 数据源
     */
    public static Map<String, JDBCConnectionConfiguration> ds=new ConcurrentHashMap();


    static {
//        //eg
//        JDBCConnectionConfiguration jdbcConnectionConfiguration=new JDBCConnectionConfiguration();
//        jdbcConnectionConfiguration.setConnectionURL();
//        jdbcConnectionConfiguration.setDriverClass();
//        jdbcConnectionConfiguration.setPassword();
//        jdbcConnectionConfiguration.setUserId();
//        ds.put("")
    }

}
