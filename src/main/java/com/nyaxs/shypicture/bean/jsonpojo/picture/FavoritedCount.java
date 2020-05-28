package com.nyaxs.shypicture.bean.jsonpojo.picture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoritedCount {

    @JsonProperty("private")
    private int jsonMemberPrivate;

    @JsonProperty("public")
    private int jsonMemberPublic;

    public void setJsonMemberPrivate(int jsonMemberPrivate) {
        this.jsonMemberPrivate = jsonMemberPrivate;
    }

    public int getJsonMemberPrivate() {
        return jsonMemberPrivate;
    }

    public void setJsonMemberPublic(int jsonMemberPublic) {
        this.jsonMemberPublic = jsonMemberPublic;
    }

    public int getJsonMemberPublic() {
        return jsonMemberPublic;
    }

    @Override
    public String toString() {
        return
                "FavoritedCount{" +
                        "private = '" + jsonMemberPrivate + '\'' +
                        ",public = '" + jsonMemberPublic + '\'' +
                        "}";
    }
}