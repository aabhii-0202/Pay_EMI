package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.GetHelpQuestionAnswerDTO;

import java.util.List;

public class GetHelpQuestionAnswer extends BaseApiResponse{

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("data")
    @Expose
    List<GetHelpQuestionAnswerDTO> data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetHelpQuestionAnswerDTO> getData() {
        return data;
    }

    public void setData(List<GetHelpQuestionAnswerDTO> data) {
        this.data = data;
    }
}
