package com.android.androidretrofitdownload.util.permission;

/**
 * Description：
 */
public interface KbPermissionListener {

    /**
     * 授权
     */
    void onPermit(int requestCode, String... permission);

    /**
     * 未授权
     */
    void onCancel(int requestCode, String... permission);
}
