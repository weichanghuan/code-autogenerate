package com.wch.code.generate.service;

import com.wch.code.generate.dto.ServiceMultiResult;
import com.wch.code.generate.model.DBDefinition;
import com.wch.code.generate.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器 实现类
 * @author gaowenfeng
 * @date 2018/5/16
 */
@Service
@Lazy
@Slf4j
public class CodeGeneratorServiceImpl implements ICodeGeneratorService{

    @Autowired
    private HttpSession session;


    @Override
    public ServiceMultiResult<String> getTableList(DBDefinition dbDefinition) throws Exception {
        List<String> tables = new ArrayList<>();

        //创建驱动器
        Connection conn = DBUtil.getConnection(dbDefinition);
        ResultSet rs = null;
        try{
            String[] types={"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "public", "%", types);
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            DBUtil.killConnection(rs,conn);
        }
        session.setAttribute("dbDefinition",dbDefinition);
        return new ServiceMultiResult<>(tables.size(),tables);
    }
}
