package com.ashen.controll.manager;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.ashen.controll.adb.AdbHelper;

import java.util.ArrayList;

/**
 * Created by Jungle on 2021/6/21.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class DeviceManager {

    private static final DeviceManager ourInstance = new DeviceManager();

    private ArrayList<String> devices = new ArrayList<>();

    public static DeviceManager getInstance() {
        return ourInstance;
    }

    public DeviceManager() {
        initAdb();
    }

    private void initAdb() {
        AdbHelper.init(new AndroidDebugBridge.IDeviceChangeListener() {
            @Override
            public void deviceConnected(IDevice device) {
                addDevices(device);
            }

            @Override
            public void deviceDisconnected(IDevice device) {
                removeDevices(device);
            }

            @Override
            public void deviceChanged(IDevice device, int changeMask) {

            }
        });
    }

    private void addDevices(IDevice device) {
        devices.add(device.toString());
        LogManager.getInstance().log(device.toString() + "添加了");
    }

    private void removeDevices(IDevice device) {
        devices.remove(device.toString());
        LogManager.getInstance().log(device.toString() + "删除了");
    }

    public ArrayList<String> getDevices() {
        return devices;
    }
}
