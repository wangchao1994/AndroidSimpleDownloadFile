package com.android.androidretrofitdownload.util;

import android.os.Build;

/**
 * Description：
 */

public class DeviceUtils {
    /**
     * 获取设备厂商
     * <p>如 Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }
}
