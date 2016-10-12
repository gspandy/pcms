package com.pujjr.file.domain;

public class DirectoryTemplateCategoryRefDirectory {
    private String id;

    private String tplCategoryId;

    private String dirId;

    private Boolean required;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTplCategoryId() {
        return tplCategoryId;
    }

    public void setTplCategoryId(String tplCategoryId) {
        this.tplCategoryId = tplCategoryId;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}