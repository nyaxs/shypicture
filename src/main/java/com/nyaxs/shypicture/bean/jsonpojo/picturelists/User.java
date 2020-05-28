package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("is_premium")
    private Object isPremium;

    @JsonProperty("stats")
    private Object stats;

    @JsonProperty("profile")
    private Object profile;

    @JsonProperty("name")
    private String name;

    @JsonProperty("profile_image_urls")
    private ProfileImageUrls profileImageUrls;

    @JsonProperty("id")
    private int id;

    @JsonProperty("is_following")
    private Object isFollowing;

    @JsonProperty("is_friend")
    private Object isFriend;

    @JsonProperty("account")
    private String account;

    @JsonProperty("is_follower")
    private Object isFollower;

    public void setIsPremium(Object isPremium) {
        this.isPremium = isPremium;
    }

    public Object getIsPremium() {
        return isPremium;
    }

    public void setStats(Object stats) {
        this.stats = stats;
    }

    public Object getStats() {
        return stats;
    }

    public void setProfile(Object profile) {
        this.profile = profile;
    }

    public Object getProfile() {
        return profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfileImageUrls(ProfileImageUrls profileImageUrls) {
        this.profileImageUrls = profileImageUrls;
    }

    public ProfileImageUrls getProfileImageUrls() {
        return profileImageUrls;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIsFollowing(Object isFollowing) {
        this.isFollowing = isFollowing;
    }

    public Object getIsFollowing() {
        return isFollowing;
    }

    public void setIsFriend(Object isFriend) {
        this.isFriend = isFriend;
    }

    public Object getIsFriend() {
        return isFriend;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setIsFollower(Object isFollower) {
        this.isFollower = isFollower;
    }

    public Object getIsFollower() {
        return isFollower;
    }

    @Override
    public String toString() {
        return
                "User{" +
                        "is_premium = '" + isPremium + '\'' +
                        ",stats = '" + stats + '\'' +
                        ",profile = '" + profile + '\'' +
                        ",name = '" + name + '\'' +
                        ",profile_image_urls = '" + profileImageUrls + '\'' +
                        ",id = '" + id + '\'' +
                        ",is_following = '" + isFollowing + '\'' +
                        ",is_friend = '" + isFriend + '\'' +
                        ",account = '" + account + '\'' +
                        ",is_follower = '" + isFollower + '\'' +
                        "}";
    }
}