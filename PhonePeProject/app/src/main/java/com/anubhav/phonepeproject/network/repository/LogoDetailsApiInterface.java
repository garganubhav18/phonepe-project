package com.anubhav.phonepeproject.network.repository;

import com.anubhav.phonepeproject.model.LogoDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LogoDetailsApiInterface {

    @GET("logo.json")
    Call<List<LogoDetails>> getLogoDetails();
}
