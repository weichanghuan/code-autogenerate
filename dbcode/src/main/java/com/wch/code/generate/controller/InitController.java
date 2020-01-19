package com.wch.code.generate.controller;


import com.wch.code.generate.common.ApiResponse;
import com.wch.code.generate.dto.ServiceMultiResult;
import com.wch.code.generate.model.DBDefinition;
import com.wch.code.generate.model.SqlDBDefinition;
import com.wch.code.generate.service.ICodeGeneratorService;
import com.wch.code.generate.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    @Autowired
    private ICodeGeneratorService codeGenerator;

    @PostMapping("/db/connect")
    public ApiResponse getTableList(SqlDBDefinition sqlDBDefinition) throws Exception {
        LOGGER.info("InitController.getTableList.dbDefinition={}", JSONUtil.toJSonString(sqlDBDefinition));
        ServiceMultiResult<String> tableList = codeGenerator.getTableList(sqlDBDefinition);
        return ApiResponse.ofSuccess(tableList.getResult());
    }



}
