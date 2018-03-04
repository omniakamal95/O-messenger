package com.example.noso.myapplication.beans;

/**
 * Created by omnia on 2/10/2018.
 */

public class User  {
    Friends[] friends;
    private Integer id;
    private String name;
    private String userName;
    private String email;
    private String password;

    public User(String name, String userName, String email, String password) {

        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Friends[] getFriends() {
        return friends;
    }

    public void setFriends(Friends[] friends) {
        this.friends = friends;
    }
}
