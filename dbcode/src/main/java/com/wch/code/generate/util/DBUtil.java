package com.wch.code.generate.util;

import com.wch.code.generate.model.DBDefinition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *  数据库lianjie工具 by wch
 */
public class DBUtil {


    // todo 优化
    public static Connection getConnection(DBDefinition dbDefinition) throws Exception {

        Connection con = null;
        try {
            //创建驱动器
            Class.forName(dbDefinition.getDriverClass());
            con = DriverManager.getConnection(dbDefinition.getUrl(), dbDefinition.getUsername(), dbDefinition.getPassword());
        } catch (Exception e) {
            throw e;
        }
        return con;
    }

    /**
     * 关闭 connection
     *
     * @param rs
     * @param conn
     */
    public static void killConnection(ResultSet rs, Connection conn)throws Exception {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}
