package com.hc.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by 诚 on 2016/9/17.
 */
public class Permission implements Serializable{
    private long id;
    private String permissionName;
    private String description;
    private boolean available;

    public Permission(){}
    public Permission(String perName){
        permissionName = perName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}