package com.wch.code.generate.model;

/**
 * 数据库模型接口，具体的数据库类要实现这个类
 */
public interface DBDefinition {

    String getDriverClass();

    String getUrl();

    String getUsername();

    String getPassword();

    String getDbName();
}
