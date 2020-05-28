package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponsePictures {

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("response")
    private List<ResponseItem> response;

    @JsonProperty("count")
    private int count;

    @JsonProperty("status")
    private String status;

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

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
                "ResponsePictures{" +
                        "pagination = '" + pagination + '\'' +
                        ",response = '" + response + '\'' +
                        ",count = '" + count + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}