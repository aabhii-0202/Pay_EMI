package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mandatoryParmsDTO {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("regex")
    @Expose
    private String regex;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return "mandatoryParmsDTO{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", regex='" + regex + '\'' +
                '}';
    }
}
