package com.nyaxs.shypicture.bean.jsonpojo.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponsePicture {

    @JsonProperty("response")
    private List<ResponseItem> response;

    @JsonProperty("count")
    private int count;

    @JsonProperty("status")
    private String status;

    public void setResponse(List<ResponseItem> response) {
        this.response = response;
    }

    public List<ResponseItem> getResponse() {
        return response;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "ResponsePicture{" +
                        "response = '" + response + '\'' +
                        ",count = '" + count + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}