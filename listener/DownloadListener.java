package com.android.androidretrofitdownload.listener;

/**
 * Description：下载相关的接口
 */

public interface DownloadListener {
    void onStart();

    void onProgress(int currentLength);

    void onFinish(String localPath);

    void onFailure(String erroInfo);
}
