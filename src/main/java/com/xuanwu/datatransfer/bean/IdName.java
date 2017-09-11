package com.xuanwu.datatransfer.bean;

/**
 * IdName
 * <p>
 * Created by tantan on 2017/9/11.
 */
public class IdName {

    private String id;

    private String name;

    public IdName() {

    }

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
