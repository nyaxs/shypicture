package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {

    @JsonProperty("next")
    private int next;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("current")
    private int current;

    @JsonProperty("total")
    private int total;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("previous")
    private Object previous;

    public void setNext(int next) {
        this.next = next;
    }

    public int getNext() {
        return next;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public Object getPrevious() {
        return previous;
    }

    @Override
    public String toString() {
        return
                "Pagination{" +
                        "next = '" + next + '\'' +
                        ",per_page = '" + perPage + '\'' +
                        ",current = '" + current + '\'' +
                        ",total = '" + total + '\'' +
                        ",pages = '" + pages + '\'' +
                        ",previous = '" + previous + '\'' +
                        "}";
    }
}