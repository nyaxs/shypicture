package com.nyaxs.shypicture.bean.jsonpojo.picturelists;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoritedCount {

    @JsonProperty("private")
    private Object jsonMemberPrivate;

    @JsonProperty("public")
    private Object jsonMemberPublic;

    public void setJsonMemberPrivate(Object jsonMemberPrivate) {
        this.jsonMemberPrivate = jsonMemberPrivate;
    }

    public Object getJsonMemberPrivate() {
        return jsonMemberPrivate;
    }

    public void setJsonMemberPublic(Object jsonMemberPublic) {
        this.jsonMemberPublic = jsonMemberPublic;
    }

    public Object getJsonMemberPublic() {
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