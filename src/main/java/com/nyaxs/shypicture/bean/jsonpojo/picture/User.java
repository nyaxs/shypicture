package com.nyaxs.shypicture.bean.jsonpojo.picture;

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
    private boolean isFollowing;

    @JsonProperty("is_friend")
    private boolean isFriend;

    @JsonProperty("account")
    private String account;

    @JsonProperty("is_follower")
    private boolean isFollower;

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

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public boolean isIsFollowing() {
        return isFollowing;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public boolean isIsFriend() {
        return isFriend;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setIsFollower(boolean isFollower) {
        this.isFollower = isFollower;
    }

    public boolean isIsFollower() {
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