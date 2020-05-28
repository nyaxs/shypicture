package com.nyaxs.shypicture.bean.jsonpojo.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {

    @JsonProperty("score")
    private int score;

    @JsonProperty("commented_count")
    private int commentedCount;

    @JsonProperty("scored_count")
    private int scoredCount;

    @JsonProperty("views_count")
    private int viewsCount;

    @JsonProperty("favorited_count")
    private FavoritedCount favoritedCount;

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setCommentedCount(int commentedCount) {
        this.commentedCount = commentedCount;
    }

    public int getCommentedCount() {
        return commentedCount;
    }

    public void setScoredCount(int scoredCount) {
        this.scoredCount = scoredCount;
    }

    public int getScoredCount() {
        return scoredCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setFavoritedCount(FavoritedCount favoritedCount) {
        this.favoritedCount = favoritedCount;
    }

    public FavoritedCount getFavoritedCount() {
        return favoritedCount;
    }

    @Override
    public String toString() {
        return
                "Stats{" +
                        "score = '" + score + '\'' +
                        ",commented_count = '" + commentedCount + '\'' +
                        ",scored_count = '" + scoredCount + '\'' +
                        ",views_count = '" + viewsCount + '\'' +
                        ",favorited_count = '" + favoritedCount + '\'' +
                        "}";
    }
}