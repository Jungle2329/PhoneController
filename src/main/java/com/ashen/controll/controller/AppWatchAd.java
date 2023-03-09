package com.ashen.controll.controller;

import com.android.ddmlib.IDevice;
import com.ashen.controll.ui.MainController;

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
public class AppWatchAd extends Thread {

    public static final String appBack = "adb -s %s shell input keyevent 4";
    public static final String appTap = "adb -s %s shell input tap 104 1298";
    private static final AppWatchAd ourInstance = new AppWatchAd();

    private LogListener logListener;
    private boolean running = false;

    private ArrayList<String> devices = new ArrayList<>();

    public static AppWatchAd getInstance() {
        return ourInstance;
    }

    private AppWatchAd() {
        start();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (running) {
                log("开始看广告");
                execAllDevices(AppWatchAd.appTap);
                try {
                    Thread.sleep(MainController.getPostInterval());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                execAllDevices(AppWatchAd.appBack);
                log("结束看广告");
            }
        }
    }


    public void swipeStart() {
        log("开始执行");
        running = true;
    }

    public void swipeStop() {
        log("停止执行");
        running = false;
    }

    private void execAllDevices(String func) {
        devices.forEach(s -> {
            runCmd(String.format(func, s), s);
        });
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
