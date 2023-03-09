package com.ashen.controll.controller;

import com.ashen.controll.manager.DeviceManager;
import com.ashen.controll.manager.GestureManager;
import com.ashen.controll.manager.LogManager;
import com.ashen.controll.ui.MainController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jungle on 2020/9/2.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class AppSwiper extends Thread {

    private static final AppSwiper ourInstance = new AppSwiper();

    private boolean running = false;

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
                exec();
            }
            try {
                Thread.sleep(MainController.getPostInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void exec() {
        LogManager.getInstance().log("exe start");
        DeviceManager.getInstance().getDevices().forEach(iDevice -> {
            GestureManager.getInstance().scrollVideo(iDevice);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LogManager.getInstance().log(String.format("%s执行 -- 时间：%s", iDevice.getName(), sdf.format(new Date())));
        });
        LogManager.getInstance().log("exe end");
    }


    public void swipeStart() {
        LogManager.getInstance().log("开始执行");
        exec();
        running = true;
    }

    public void swipeStop() {
        LogManager.getInstance().log("停止执行");
        running = false;
    }

}
