package com.ashen.controll;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.ashen.controll.adb.AdbHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jungle on 2020/9/2.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class AppSwiper extends Thread {

    public static final String appSwipe = "adb -s %s shell input swipe 300 1000 300 30";
    private static final AppSwiper ourInstance = new AppSwiper();

    private LogListener logListener;
    private boolean running = false;

    private ArrayList<String> devices = new ArrayList<>();

    static AppSwiper getInstance() {
        return ourInstance;
    }

    private AppSwiper() {
        start();
        initAdb();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (running) {
                log("exe start");
                devices.forEach(s -> {
                    runCmd(String.format(AppSwiper.appSwipe, s), s);
                });
                log("exe end");
            }
            try {
                Thread.sleep(MainController.getPostInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void swipeStart() {
        log("开始执行");
        running = true;
    }

    public void swipeStop() {
        log("停止执行");
        running = false;
    }

    public void runCmd(String cmd, String devices) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
//            log(inputStream2String(process.getInputStream()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log(String.format("%s执行 -- 时间：%s", devices, sdf.format(new Date())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    public String getExeResult(Process process) {
        return inputStream2String(process.getInputStream());
    }

    public String inputStream2String(InputStream inputStream) {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String temp = "";
            while ((temp = br.readLine()) != null) {
                result.append(temp).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void log(String log) {
        if (logListener != null) {
            logListener.onPrint(log);
        }
    }

    private void addDevices(IDevice device) {
        devices.add(device.toString());
        log(device.toString() + "添加了");
    }

    private void removeDevices(IDevice device) {
        devices.remove(device.toString());
        log(device.toString() + "删除了");
    }

    public ArrayList<String> getDevices() {
        return devices;
    }

    public interface LogListener {
        void onPrint(String log);
    }
}
