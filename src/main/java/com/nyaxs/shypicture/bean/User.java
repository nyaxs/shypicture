package com.nyaxs.shypicture.bean;

public class User {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_following() {
        return is_following;
    }

    public void setIs_following(Boolean is_following) {
        this.is_following = is_following;
    }

    public Boolean getIs_follower() {
        return is_follower;
    }

    public void setIs_follower(Boolean is_follower) {
        this.is_follower = is_follower;
    }

    public Boolean getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(Boolean is_friend) {
        this.is_friend = is_friend;
    }

    public Boolean getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(Boolean is_premium) {
        this.is_premium = is_premium;
    }

    //账号
    private String account;
    //昵称
    private String name;

    private Boolean is_following;

    private Boolean is_follower;

    private Boolean is_friend;

    private Boolean is_premium;



}
