package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loancategory {


    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("category_name")
    @Expose
    private String category_name;

    @SerializedName("loan_category_logo_url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "loancategory{" +
                "id='" + id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
