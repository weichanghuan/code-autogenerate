package com.wch.code.generate.dto;

import java.io.Serializable;

public class GeneratorResultDTO implements Serializable {

    /**
     * xml 内容
     */
    private String xmlString;

    /**
     * 接口内容
     */
    private String interfaceString;

    /**
     * po 内容
     */
    private String poString;

    /**
     * 目录名
     */
    private String fileName;

    private String xmlFileName;

    private String interfaceFileName;

    private String poFileName;


    public String getXmlString() {
        return xmlString;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }

    public String getInterfaceString() {
        return interfaceString;
    }

    public void setInterfaceString(String interfaceString) {
        this.interfaceString = interfaceString;
    }

    public String getPoString() {
        return poString;
    }

    public void setPoString(String poString) {
        this.poString = poString;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public String getInterfaceFileName() {
        return interfaceFileName;
    }

    public void setInterfaceFileName(String interfaceFileName) {
        this.interfaceFileName = interfaceFileName;
    }

    public String getPoFileName() {
        return poFileName;
    }

    public void setPoFileName(String poFileName) {
        this.poFileName = poFileName;
    }
}
