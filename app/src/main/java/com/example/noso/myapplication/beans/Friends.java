package com.example.noso.myapplication.beans;

/**
 * Created by omnia on 2/10/2018.
 */

public class Friends {
    private String Name;
    private  String userName;
    private Integer ID;

    public Friends(String name, String userName, Integer ID) {
        Name = name;
        this.userName = userName;
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
