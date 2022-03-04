package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HelpSubCatagoryResponse extends BaseApiResponse{

    @SerializedName("sub_category")
    @Expose
    List<String> sub_category;

    public List<String> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<String> sub_category) {
        this.sub_category = sub_category;
    }

    @Override
    public String toString() {
        return "HelpSubCatagoryResponse{" +
                "sub_category=" + sub_category +
                '}';
    }
}
