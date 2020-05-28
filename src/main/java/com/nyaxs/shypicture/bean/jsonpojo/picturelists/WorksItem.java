package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorksItem {

    @JsonProperty("work")
    private Work work;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("previous_rank")
    private int previousRank;

    public void setWork(Work work) {
        this.work = work;
    }

    public Work getWork() {
        return work;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setPreviousRank(int previousRank) {
        this.previousRank = previousRank;
    }

    public int getPreviousRank() {
        return previousRank;
    }

    @Override
    public String toString() {
        return
                "WorksItem{" +
                        "work = '" + work + '\'' +
                        ",rank = '" + rank + '\'' +
                        ",previous_rank = '" + previousRank + '\'' +
                        "}";
    }
}