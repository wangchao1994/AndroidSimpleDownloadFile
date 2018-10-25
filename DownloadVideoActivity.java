package com.android.androidretrofitdownload;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.androidretrofitdownload.download.DownloadUtil;
import com.android.androidretrofitdownload.listener.DownloadListener;
import com.android.androidretrofitdownload.util.SystemUtil;
import com.android.androidretrofitdownload.util.permission.KbPermission;
import com.android.androidretrofitdownload.util.permission.KbPermissionListener;
import com.android.androidretrofitdownload.util.permission.KbPermissionUtils;
import com.android.androidretrofitdownload.view.KbWithWordsCircleProgressBar;

public class DownloadVideoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String PICTURE_URL = "http://small-bronze.oss-cn-shanghai.aliyuncs.com/" +
            "image/video/cover/2018/3/8/8BBC6C00DF78476C98AD9CA482DEF635.jpg";
    private Context mContext;
    private FrameLayout mBackLayout;
    private ImageView mPicture;
    private FrameLayout mCircleProgressLayout;
    private KbWithWordsCircleProgressBar mCircleProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_video);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SystemUtil.setLightStatusBar(this, Color.WHITE);
        }
        mContext = this;
        TextView toolbarTitle = findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.download_picture);
        mBackLayout = findViewById(R.id.btn_back);
        mBackLayout.setOnClickListener(this);
        mPicture = findViewById(R.id.iv_picture);
        mCircleProgressLayout = findViewById(R.id.fl_circle_progress);
        mCircleProgress = findViewById(R.id.circle_progress);

        if (KbPermissionUtils.needRequestPermission()){
            KbPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                    .callBack(new KbPermissionListener() {
                        @Override
                        public void onPermit(int requestCode, String... permission) {
                            downloadPicture();
                        }
                        @Override
                        public void onCancel(int requestCode, String... permission) {

                        }
                    });
        }
    }

    private void downloadPicture() {
        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.downLoadFile(PICTURE_URL, new DownloadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int currentLength) {

            }

            @Override
            public void onFinish(String localPath) {

            }

            @Override
            public void onFailure(String erroInfo) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
