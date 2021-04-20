package com.xm.test.entity;

import cn.hutool.core.clone.CloneSupport;

import java.io.Serializable;

public class Student extends CloneSupport<Student> implements Serializable {
    private String name;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
