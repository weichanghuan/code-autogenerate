package com.wch.code.generate.model;

import lombok.Setter;

/**
 * PGsql接口定义
 */
@Setter
public class PGsqlDBDefinition implements DBDefinition{
    private String ip;

    private String dbName;

    private String username;

    private String password;

    private String port;


    @Override
    public String getUrl() {
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
        return "org.postgresql.Driver";
    }

}
