package com.ashen.controll.adb;

import com.android.ddmlib.AndroidDebugBridge;

/**
 * Created by Jungle on 2020/9/15.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class AdbHelper {

    public static void init(AndroidDebugBridge.IDeviceChangeListener listener) {
        AndroidDebugBridge.addDeviceChangeListener(listener);
        AndroidDebugBridge.initIfNeeded(false);
        AndroidDebugBridge.createBridge("adb", false);

    }
}
