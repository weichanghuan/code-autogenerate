package com.wch.code.generate.controller;


import com.wch.code.generate.custom.ConfigurationParser;
import com.wch.code.generate.custom.GenerationStarter;
import com.wch.code.generate.dto.GeneratorRequrest;
import com.wch.code.generate.dto.GeneratorResultDTO;
import com.wch.code.generate.model.PGsqlDBDefinition;
import com.wch.code.generate.share.ZipCompressShare;
import com.wch.code.generate.util.JsonUtils;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
public class GeneratorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorController.class);


    @RequestMapping("/input")
    public String input(Model model) {
        model.addAttribute("name", "ok");
        return "/index";
    }


    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public void generate(String dataJSON, PGsqlDBDefinition pGsqlDBDefinition,HttpServletRequest request, HttpServletResponse response) {
        GeneratorRequrest generator=new GeneratorRequrest();
        try {
            generator = JsonUtils.fromJSON(dataJSON,GeneratorRequrest.class);
        }catch (Exception ex){
            LOGGER.error("GeneratorController.generate is error");
        }
        if (generator == null
                || StringUtils.isEmpty(generator.getLibraryName())
                || StringUtils.isEmpty(generator.getTable())
                || StringUtils.isEmpty(generator.getDomainObjectName())
                || StringUtils.isEmpty(generator.getMapperNameTarget())) {
            return;
        }

        List<GeneratorResultDTO> generate = generate(generator,pGsqlDBDefinition);
        System.out.println(generate.size());
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            String fileName = UUID.randomUUID() + " .zip";
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            OutputStream os = response.getOutputStream();
            ZipCompressShare.getZipCompress(os, generate);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static List<GeneratorResultDTO> generate(GeneratorRequrest generatorRequrest, PGsqlDBDefinition pGsqlDBDefinition) {

        List<GeneratorResultDTO> generate = new ArrayList<>();
        List<String> warnings = new ArrayList<String>();
        Set<String> contexts = new HashSet<String>();
        Set<String> fullyqualifiedTables = new HashSet<String>();
        InputStream inputStream = null;
        try {
            ClassPathResource resource = new ClassPathResource("template/mybatisGeneratorConfig.xml");
            inputStream = resource.getInputStream();
        } catch (Exception ex) {
            LOGGER.error("FileNotFoundException filePath={}", "template/mybatisGeneratorConfig.xml");
        }

        try {
            ConfigurationParser cp=new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);

            Context tts2 = config.getContext("tts2");
            //设置数据源
            JDBCConnectionConfiguration jdbcConnectionConfiguration = tts2.getJdbcConnectionConfiguration();
            jdbcConnectionConfiguration.setDriverClass(pGsqlDBDefinition.getDriverClass());
            jdbcConnectionConfiguration.setUserId(pGsqlDBDefinition.getUsername());
            jdbcConnectionConfiguration.setPassword(pGsqlDBDefinition.getPassword());
            jdbcConnectionConfiguration.setConnectionURL(pGsqlDBDefinition.getUrl());

            List<String> table = generatorRequrest.getTable();

            for (String t : table) {
                //设置表名
                TableConfiguration tableConfiguration = new TableConfiguration(tts2);
                tableConfiguration.setDomainObjectName(generatorRequrest.getDomainObjectName());
                tableConfiguration.setMapperName(generatorRequrest.getMapperName());
                tableConfiguration.setInsertStatementEnabled(true);
                tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(true);
                tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(true);
                tableConfiguration.setTableName(t);
                tableConfiguration.setDeleteByExampleStatementEnabled(false);
                tableConfiguration.setUpdateByExampleStatementEnabled(false);
                tableConfiguration.setCountByExampleStatementEnabled(false);
                tableConfiguration.setSelectByExampleStatementEnabled(false);
                tts2.addTableConfiguration(tableConfiguration);
            }

            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = tts2.getJavaModelGeneratorConfiguration();
            javaModelGeneratorConfiguration.setTargetPackage(generatorRequrest.getDomainObjectNameTarget());

            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = tts2.getJavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setTargetPackage(generatorRequrest.getMapperNameTarget());

            DefaultShellCallback shellCallback = new DefaultShellCallback(
                    true);
            GenerationStarter generator = new GenerationStarter(config, shellCallback, warnings);

            ProgressCallback progressCallback = null;

            generate = generator.generate(progressCallback, contexts, fullyqualifiedTables, true);
            return generate;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            LOGGER.error("失败");
        }
        return generate;

    }


}
