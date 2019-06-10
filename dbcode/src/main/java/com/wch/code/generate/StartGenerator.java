package com.wch.code.generate;

import com.sun.org.apache.xerces.internal.dom.DeferredDocumentImpl;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 调研框架类
 */
public class StartGenerator {
    public static void main(String[] args)throws Exception {
//        File file =new File("/Users/wch/localfile/soft/mybatisGeneratorConfig.xml");
//        InputStream inputSource=new FileInputStream(file);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document document = builder.parse(inputSource);
//        Element rootNode = document.getDocumentElement();
//        System.out.println(rootNode.getNodeType());
//        DocumentType docType = document.getDoctype();
//
//
//        DefaultShellCallback shellCallback = new DefaultShellCallback(
//                true);
//        MyBatisGeneratorConfigurationParser myBatisGeneratorConfigurationParser=new MyBatisGeneratorConfigurationParser(null);
//        Configuration configuration = myBatisGeneratorConfigurationParser.parseConfiguration(rootNode);
//        System.out.println(docType.getPublicId());
//
//
//
//        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, null);
//
//
//
//        myBatisGenerator.generate(null, null, null);
//
//        System.out.println("init");
        String[] temp={"-configfile","/Users/wch/localfile/soft/mybatisGeneratorConfig.xml","-overwrite"};
        ShellRunner.main(temp);


    }

    private void test(){
        Document document=new DeferredDocumentImpl();
        Element rootNode = document.getDocumentElement();
        DocumentType docType = document.getDoctype();
    }
}
