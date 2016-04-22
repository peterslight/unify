package com.peterstev.unify.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UnifyAuthenticationApiInterface {
    @FormUrlEncoded
    @POST("authenticate")
    Call<UnifyAuthenticationApiResponse> staffLogin(@Field("email") String email, @Field("password") String password);

//    @FormUrlEncoded
//    @POST("validate")
//    Call<UnifyAuthenticationApiResponse> numSchools(@Field("schools") List<String> schools);
}