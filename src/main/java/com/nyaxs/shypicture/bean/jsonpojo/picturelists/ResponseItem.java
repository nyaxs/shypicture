package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseItem {

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("date")
    private String date;

    @JsonProperty("works")
    private List<WorksItem> works;

    @JsonProperty("content")
    private String content;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setWorks(List<WorksItem> works) {
        this.works = works;
    }

    public List<WorksItem> getWorks() {
        return works;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return
                "ResponseItem{" +
                        "mode = '" + mode + '\'' +
                        ",date = '" + date + '\'' +
                        ",works = '" + works + '\'' +
                        ",content = '" + content + '\'' +
                        "}";
    }
}