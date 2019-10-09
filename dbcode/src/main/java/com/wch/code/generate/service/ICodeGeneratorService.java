package com.wch.code.generate.service;

import com.wch.code.generate.dto.ServiceMultiResult;
import com.wch.code.generate.model.DBDefinition;

/**
 * 代码生成器接口
 */
public interface ICodeGeneratorService {

    /**
     * 根据数据库定义信息返回数据库中的表列表
     * @param dbDefinition
     * @return
     */
     ServiceMultiResult<String> getTableList(DBDefinition dbDefinition) throws Exception;

}
