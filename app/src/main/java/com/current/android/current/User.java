package com.current.android.current;

/**
 * Created by duboi on 4/8/2018.
 */

public class User {
    public String userName;
    public String avatarType;


    public User(){}

    public User(String name){
        userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarType() {
        return avatarType;
    }

    public void setAvatarType(String avatarType) {
        this.avatarType = avatarType;
    }
}
