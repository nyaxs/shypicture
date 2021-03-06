package com.nyaxs.shypicture.bean;

import com.nyaxs.shypicture.bean.jsonpojo.picturelists.ProfileImageUrls;

public class User {
    private Object isPremium;

    private Object stats;

    private Object profile;

    private String name;

    private ProfileImageUrls profileImageUrls;

    private int id;

    private boolean isFollowing;

    private boolean isFriend;

    private String account;

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
