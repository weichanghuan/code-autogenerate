package com.wch.code.generate.model;

import com.wch.code.generate.contract.DbDataType;
import lombok.Setter;

import java.util.Objects;

/**
 * PGsql接口定义
 */
@Setter
public class SqlDBDefinition implements DBDefinition {

    private String ip;

    private String dbName;

    private String username;

    private String password;

    private String port;

    /**
     * 数据库支持mysql,PGSQL
     */
    private String dbType;


    @Override
    public String getUrl() {
        if (Objects.equals(dbType, DbDataType.MYSQL)) {
            return "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
        }
        if (Objects.equals(dbType, DbDataType.PGSQL)) {
            return "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
        }
        return "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDbName() {
        return dbName;
    }

    @Override
    public String getDriverClass() {
        if (Objects.equals(dbType, DbDataType.MYSQL)) {
           // return "com.mysql.jdbc.Driver";
            return "com.mysql.jdbc.Driver";
        }
        if (Objects.equals(dbType, DbDataType.PGSQL)) {
            return "org.postgresql.Driver";
        }
        return "org.postgresql.Driver";
    }

}
