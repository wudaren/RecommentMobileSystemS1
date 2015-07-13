package com.fbse.recommentmobilesystem.common.common_entity;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable{

    /**
     * 基类标识
     */
    private static final long serialVersionUID = 2653406692921957740L;
    
    private String success;

    private String count;

    private List<CategoryList> obj;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<CategoryList> getObj() {
        return obj;
    }

    public void setObj(List<CategoryList> obj) {
        this.obj = obj;
    }

}
