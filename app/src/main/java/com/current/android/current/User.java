package com.current.android.current;

/**
 * Created by duboi on 4/8/2018.
 */

public class User {
    public String userName;
    public int age;

    public User(){}

    public User(String name, int age){
        userName = name;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
