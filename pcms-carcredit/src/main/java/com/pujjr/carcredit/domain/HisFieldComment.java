package com.pujjr.carcredit.domain;

public class HisFieldComment {
    public String id;

    public String tableName;

    public String tableCname;

    public String fieldName;

    public String fieldCname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCname() {
        return tableCname;
    }

    public void setTableCname(String tableCname) {
        this.tableCname = tableCname;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCname() {
        return fieldCname;
    }

    public void setFieldCname(String fieldCname) {
        this.fieldCname = fieldCname;
    }
}