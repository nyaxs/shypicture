package com.nyaxs.shypicture.bean.jsonpojo.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileImageUrls {

    @JsonProperty("px_50x50")
    private String px50x50;

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
                        "px_50x50 = '" + px50x50 + '\'' +
                        "}";
    }
}