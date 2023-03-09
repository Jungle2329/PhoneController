package com.ashen.controll.controller;

import com.ashen.controll.manager.DeviceManager;
import com.ashen.controll.manager.LogManager;
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
public class AppSwiper extends Thread {

    public static final String appSwipe = "adb -s %s shell input swipe 300 1000 300 30";
    private static final AppSwiper ourInstance = new AppSwiper();

    private boolean running = false;

    private ArrayList<String> devices = new ArrayList<>();

    public static AppSwiper getInstance() {
        return ourInstance;
    }

    private AppSwiper() {
        start();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (running) {
                LogManager.getInstance().log("exe start");
                DeviceManager.getInstance().getDevices().forEach(s -> {
                    runCmd(String.format(AppSwiper.appSwipe, s), s);
                });
                LogManager.getInstance().log("exe end");
            }
            try {
                Thread.sleep(MainController.getPostInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void swipeStart() {
        LogManager.getInstance().log("开始执行");
        running = true;
    }

    public void swipeStop() {
        LogManager.getInstance().log("停止执行");
        running = false;
    }

    public void runCmd(String cmd, String devices) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LogManager.getInstance().log(String.format("%s执行 -- 时间：%s", devices, sdf.format(new Date())));
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
