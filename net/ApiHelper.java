package com.android.androidretrofitdownload.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiHelper {
    private static final String TAG = ApiHelper.class.getSimpleName();
    private static ApiHelper mApiHelper;
    private  OkHttpClient mHttpClient;
    private Retrofit mRetrofit;

    public static ApiHelper getInstance(){
        if (null == mApiHelper){
            synchronized (ApiHelper.class){
                if (null == mApiHelper){
                    mApiHelper = new ApiHelper(30,30,30);
                }
            }
        }
        return  mApiHelper;
    }

    public ApiHelper(int connTimeout, int readTimeout, int writeTimeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS);

        mHttpClient = builder.build();
    }
    public ApiHelper buildRetrofit(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mHttpClient)
                .build();
        return this;
    }

    public <T> T createService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
