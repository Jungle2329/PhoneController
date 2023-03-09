package com.ashen.controll.inneradb;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * Created by Jungle on 2020/9/15.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class Main {
    public static void main(String[] args) {
        AndroidDebugBridge.addDeviceChangeListener(new AndroidDebugBridge.IDeviceChangeListener() {
            @Override
            public void deviceConnected(IDevice iDevice) {
                System.out.println(iDevice);
            }

            @Override
            public void deviceDisconnected(IDevice iDevice) {
                System.out.println(iDevice);
            }

            @Override
            public void deviceChanged(IDevice iDevice, int i) {
                System.out.println(iDevice);
            }
        });
        AndroidDebugBridge.init(false);
        AndroidDebugBridge.createBridge("adb", false);

    }
}
