package com.adminapp.nac_admin.common;

import com.adminapp.nac_admin.Forgotpassword.Apirequest_changepass;
import com.adminapp.nac_admin.Forgotpassword.Apirequest_getotp;
import com.adminapp.nac_admin.Forgotpassword.Apiresponse_changepass;
import com.adminapp.nac_admin.Forgotpassword.Apiresponse_getotp;
import com.adminapp.nac_admin.Login.Apirequest_login;
import com.adminapp.nac_admin.Login.Apiresponse_login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APP_API {

    @Headers("x-api-key:8DCiyiPd0f6ojQaYPwsH42IpPacBXf976Yt4TCIr")
    @POST("login")
    Call<Apiresponse_login> doLoginRequest(@Body Apirequest_login data);

    @Headers("x-api-key:8DCiyiPd0f6ojQaYPwsH42IpPacBXf976Yt4TCIr")
    @POST("verify")
    Call<Apiresponse_getotp> getotp(@Body Apirequest_getotp data);

    @Headers("x-api-key:8DCiyiPd0f6ojQaYPwsH42IpPacBXf976Yt4TCIr")
    @POST("reset_password")
    Call<Apiresponse_changepass> changepass(@Body Apirequest_changepass data);
}