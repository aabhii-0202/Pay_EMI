package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.HomepageDTO;

import java.util.List;

public class GetHelpCatagoryResponse extends BaseApiResponse{


    @SerializedName("category")
    @Expose
    List<String> category;

    @SerializedName("sub_category")
    @Expose
    List<String> sub_category;


    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<String> sub_category) {
        this.sub_category = sub_category;
    }
}
