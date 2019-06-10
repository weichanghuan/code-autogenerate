package com.wch.code.generate.dto;

import java.io.Serializable;

public class GeneratorTableDTO implements Serializable {

    private String xmlFileString;

    private String interfaceFileString;

    private String poFileString;

    private String tableString;


    public String getXmlFileString() {
        return xmlFileString;
    }

    public void setXmlFileString(String xmlFileString) {
        this.xmlFileString = xmlFileString;
    }

    public String getInterfaceFileString() {
        return interfaceFileString;
    }

    public void setInterfaceFileString(String interfaceFileString) {
        this.interfaceFileString = interfaceFileString;
    }

    public String getPoFileString() {
        return poFileString;
    }

    public void setPoFileString(String poFileString) {
        this.poFileString = poFileString;
    }

    public String getTableString() {
        return tableString;
    }

    public void setTableString(String tableString) {
        this.tableString = tableString;
    }
}
