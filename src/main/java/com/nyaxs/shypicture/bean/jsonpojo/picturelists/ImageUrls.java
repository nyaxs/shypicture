package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageUrls {

    @JsonProperty("large")
    private String large;

    @JsonProperty("px_128x128")
    private String px128x128;

    @JsonProperty("px_480mw")
    private String px480mw;

    public void setLarge(String large) {
        this.large = large;
    }

    public String getLarge() {
        return large;
    }

    public void setPx128x128(String px128x128) {
        this.px128x128 = px128x128;
    }

    public String getPx128x128() {
        return px128x128;
    }

    public void setPx480mw(String px480mw) {
        this.px480mw = px480mw;
    }

    public String getPx480mw() {
        return px480mw;
    }

    @Override
    public String toString() {
        return
                "ImageUrls{" +
                        "large = '" + large + '\'' +
                        ",px_128x128 = '" + px128x128 + '\'' +
                        ",px_480mw = '" + px480mw + '\'' +
                        "}";
    }
}