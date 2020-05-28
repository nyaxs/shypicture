package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileImageUrls {

    @JsonProperty("px_170x170")
    private String px170x170;

    @JsonProperty("px_50x50")
    private String px50x50;

    public void setPx170x170(String px170x170) {
        this.px170x170 = px170x170;
    }

    public String getPx170x170() {
        return px170x170;
    }

    public void setPx50x50(String px50x50) {
        this.px50x50 = px50x50;
    }

    public String getPx50x50() {
        return px50x50;
    }

    @Override
    public String toString() {
        return
                "ProfileImageUrls{" +
                        "px_170x170 = '" + px170x170 + '\'' +
                        ",px_50x50 = '" + px50x50 + '\'' +
                        "}";
    }
}