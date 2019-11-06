package com.wch.code.generate.dto;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;
import java.util.List;

public class GeneratorRequrest implements Serializable {

    private String libraryName;
    private List<String> table;

    private String mapperName;

    private String domainObjectName;

    private String domainObjectNameTarget;

    private String mapperNameTarget;

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }


    public String getDomainObjectNameTarget() {
        return domainObjectNameTarget;
    }

    public void setDomainObjectNameTarget(String domainObjectNameTarget) {
        this.domainObjectNameTarget = domainObjectNameTarget;
    }

    public String getMapperNameTarget() {
        return mapperNameTarget;
    }

    public void setMapperNameTarget(String mapperNameTarget) {
        this.mapperNameTarget = mapperNameTarget;
    }
}
