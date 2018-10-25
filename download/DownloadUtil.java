package com.android.androidretrofitdownload.download;

import android.os.Environment;
import android.util.Log;

import com.android.androidretrofitdownload.listener.DownloadListener;
import com.android.androidretrofitdownload.net.ApiHelper;
import com.android.androidretrofitdownload.net.IApiInterface;
import com.android.androidretrofitdownload.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 下载工具类
 */
public class DownloadUtil {
    private static final String TAG = DownloadUtil.class.getSimpleName();
    private static final String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/Download";
    private static final String URL = "";
    private String mVideoPath;


    private IApiInterface mApiHelper;
    private Call<ResponseBody> responseBodyCall;
    private File mFile;
    private FileOutputStream fileOutputStream = null;
    private long currentLength;
    private long totalLength;

//    private static DownloadUtil mDownloadUtil;
//    private static DownloadUtil getInstance(){
//        if (null == mDownloadUtil){
//            synchronized (DownloadUtil.class){
//                if (null == mDownloadUtil){
//                    mDownloadUtil = new DownloadUtil();
//                }
//            }
//        }
//        return mDownloadUtil;
//    }

    public DownloadUtil(){
        if (null == mApiHelper){
            mApiHelper = ApiHelper.getInstance().buildRetrofit(URL).createService(IApiInterface.class);
        }
    }

    /**
     * 下载文件
     * @param url
     * @param downloadListener
     */
    public void downLoadFile(String url, final DownloadListener downloadListener){
        String name = url;
        //1.获取下载文件名称
        if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO)){
            int index = name.lastIndexOf("/");
            if (index != -1){
                name = name.substring(index);
                mVideoPath = PATH_CHALLENGE_VIDEO + name;
            }
        }

        if (mVideoPath.isEmpty()){
            Log.d(TAG,"路径不存在--------------");
            return;
        }

        //创建文件
        mFile = new File(mVideoPath);
        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsDir(mFile)){
            if (null == mApiHelper){
                Log.d(TAG,"下载链接不存在---------------------");
                return;
            }

            responseBodyCall = mApiHelper.downFiles(url);
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            downloadListener.onStart();
                            //写入本地文件
                            writeLocalDisk(response,mFile,downloadListener);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG,"下载发生异常------------"+t.getMessage());
                    downloadListener.onFailure(t.getMessage());
                }
            });
        }

    }
    /**
     * 写入本地磁盘
     * @param response
     * @param file
     * @param downloadListener
     */
    private void writeLocalDisk(Response<ResponseBody> response, File file, DownloadListener downloadListener) {
        ResponseBody responseBody = response.body();
        if (responseBody == null){
            downloadListener.onFailure("资源错误---------------------");
            return;
        }
        totalLength = responseBody.contentLength();
        InputStream inputStream = responseBody.byteStream();
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes,0,len);
                currentLength += len;
                Log.e(TAG, "当前进度: " + currentLength);
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
                if ((int) (100 * currentLength / totalLength) == 100) {
                    downloadListener.onFinish(mVideoPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            downloadListener.onFailure("IO 异常---------------");
        }finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (inputStream != null){
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    downloadListener.onFailure("IO 异常---------------");
                }
        }


    }
}
