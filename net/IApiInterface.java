package com.android.androidretrofitdownload.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface IApiInterface {
    /***
     * downloadfile
     */
    @Streaming
    @GET
    Call<ResponseBody> downFiles(@Url String url);
}
