package com.ashen.controll.manager;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.ashen.controll.adb.AdbHelper;

import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private TextArea area;

    public static DeviceManager getInstance() {
        return ourInstance;
    }

    public DeviceManager() {
        initAdb();
    }

    public void bindView(TextArea area) {
        this.area = area;
    }

    private void initAdb() {
        AdbHelper.init(new AndroidDebugBridge.IDeviceChangeListener() {
            @Override
            public void deviceConnected(IDevice device) {
                addDevices(device);
                update();

            }

            @Override
            public void deviceDisconnected(IDevice device) {
                removeDevices(device);
                update();
            }

            @Override
            public void deviceChanged(IDevice device, int changeMask) {
            }
        });
    }

    private void addDevices(IDevice device) {
        devices.add(device.toString());
    }

    private void removeDevices(IDevice device) {
        devices.remove(device.toString());
    }

    private void update() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < devices.size(); i++) {
            if (i != devices.size() - 1) {
                sb.append(devices.get(i)).append("\r\n");
            } else {
                sb.append(devices.get(i));
            }
        }
        area.setText(sb.toString());
    }

    public List<IDevice> getDevices() {
        return Arrays.asList(AndroidDebugBridge.getBridge().getDevices());
    }

    public List<String> getDevicesName() {
        return devices;
    }


}
