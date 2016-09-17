package com.hc.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by 诚 on 2016/9/16.
 */
public class Role implements Serializable{
    private long id;
    private String role;
    private String description;
    private boolean available;

    public Role(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roleName) {
        this.role = roleName;
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

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
