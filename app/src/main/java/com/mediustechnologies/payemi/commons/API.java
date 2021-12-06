package com.mediustechnologies.payemi.commons;

import com.mediustechnologies.payemi.Models.bankListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("bankList")
    Call<List<bankListItem>> getbank();
}
