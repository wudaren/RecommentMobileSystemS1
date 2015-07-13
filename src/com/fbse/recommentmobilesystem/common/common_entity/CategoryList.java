package com.fbse.recommentmobilesystem.common.common_entity;

import java.io.Serializable;

public class CategoryList implements Serializable{

    /**
     * 商品列表基类标识
     */
    private static final long serialVersionUID = -3778221747660178185L;

    private String id;

    private String name;

    private String order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    
}
