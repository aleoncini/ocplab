package org.keycloak.spid;

public class User {

    private String userName;

    public User() {
        this.userName = "unknown";
    }

    public User setUserName(String name){
        this.userName = name;
        return this;
    }

    public String getUserName() {
        return this.userName;
    }
}