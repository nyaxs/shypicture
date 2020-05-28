package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Work {

    @JsonProperty("created_time")
    private String createdTime;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("age_limit")
    private String ageLimit;

    @JsonProperty("caption")
    private Object caption;

    @JsonProperty("image_urls")
    private ImageUrls imageUrls;

    @JsonProperty("reuploaded_time")
    private String reuploadedTime;

    @JsonProperty("title")
    private String title;

    @JsonProperty("type")
    private String type;

    @JsonProperty("tools")
    private Object tools;

    @JsonProperty("is_manga")
    private Object isManga;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("content_type")
    private Object contentType;

    @JsonProperty("stats")
    private Stats stats;

    @JsonProperty("book_style")
    private String bookStyle;

    @JsonProperty("width")
    private int width;

    @JsonProperty("id")
    private int id;

    @JsonProperty("publicity")
    private int publicity;

    @JsonProperty("favorite_id")
    private Object favoriteId;

    @JsonProperty("user")
    private User user;

    @JsonProperty("page_count")
    private int pageCount;

    @JsonProperty("sanity_level")
    private String sanityLevel;

    @JsonProperty("height")
    private int height;

    @JsonProperty("is_liked")
    private Object isLiked;

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public Object getCaption() {
        return caption;
    }

    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public void setReuploadedTime(String reuploadedTime) {
        this.reuploadedTime = reuploadedTime;
    }

    public String getReuploadedTime() {
        return reuploadedTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTools(Object tools) {
        this.tools = tools;
    }

    public Object getTools() {
        return tools;
    }

    public void setIsManga(Object isManga) {
        this.isManga = isManga;
    }

    public Object getIsManga() {
        return isManga;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setContentType(Object contentType) {
        this.contentType = contentType;
    }

    public Object getContentType() {
        return contentType;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public void setBookStyle(String bookStyle) {
        this.bookStyle = bookStyle;
    }

    public String getBookStyle() {
        return bookStyle;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPublicity(int publicity) {
        this.publicity = publicity;
    }

    public int getPublicity() {
        return publicity;
    }

    public void setFavoriteId(Object favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Object getFavoriteId() {
        return favoriteId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setSanityLevel(String sanityLevel) {
        this.sanityLevel = sanityLevel;
    }

    public String getSanityLevel() {
        return sanityLevel;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setIsLiked(Object isLiked) {
        this.isLiked = isLiked;
    }

    public Object getIsLiked() {
        return isLiked;
    }

    @Override
    public String toString() {
        return
                "Work{" +
                        "created_time = '" + createdTime + '\'' +
                        ",metadata = '" + metadata + '\'' +
                        ",age_limit = '" + ageLimit + '\'' +
                        ",caption = '" + caption + '\'' +
                        ",image_urls = '" + imageUrls + '\'' +
                        ",reuploaded_time = '" + reuploadedTime + '\'' +
                        ",title = '" + title + '\'' +
                        ",type = '" + type + '\'' +
                        ",tools = '" + tools + '\'' +
                        ",is_manga = '" + isManga + '\'' +
                        ",tags = '" + tags + '\'' +
                        ",content_type = '" + contentType + '\'' +
                        ",stats = '" + stats + '\'' +
                        ",book_style = '" + bookStyle + '\'' +
                        ",width = '" + width + '\'' +
                        ",id = '" + id + '\'' +
                        ",publicity = '" + publicity + '\'' +
                        ",favorite_id = '" + favoriteId + '\'' +
                        ",user = '" + user + '\'' +
                        ",page_count = '" + pageCount + '\'' +
                        ",sanity_level = '" + sanityLevel + '\'' +
                        ",height = '" + height + '\'' +
                        ",is_liked = '" + isLiked + '\'' +
                        "}";
    }
}