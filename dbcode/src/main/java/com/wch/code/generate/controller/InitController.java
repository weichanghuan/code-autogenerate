package com.wch.code.generate.controller;


import com.wch.code.generate.common.ApiResponse;
import com.wch.code.generate.dto.ServiceMultiResult;
import com.wch.code.generate.model.PGsqlDBDefinition;
import com.wch.code.generate.service.ICodeGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class InitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    @Autowired
    private ICodeGeneratorService codeGenerator;

    @PostMapping("/db/connect")
    public ApiResponse getTableList(PGsqlDBDefinition pGsqlDBDefinition) throws Exception {
        ServiceMultiResult<String> tableList = codeGenerator.getTableList(pGsqlDBDefinition);
        return ApiResponse.ofSuccess(tableList.getResult());
    }



}
