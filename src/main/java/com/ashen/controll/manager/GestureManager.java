package com.ashen.controll.manager;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jungle on 2023/3/9.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description 手势管理，集合各种常用手势
 */
public class GestureManager {

    private static final GestureManager ourInstance = new GestureManager();
    private MyMultiLineReceiver mMultiLineReceiver;

    public static GestureManager getInstance() {
        return ourInstance;
    }

    public GestureManager() {
        mMultiLineReceiver = new MyMultiLineReceiver();
    }

    /**
     * 自然向上滚动，适合普通看视频逻辑
     *
     * @param iDevice 设备
     * @return
     * @author Ashen
     * @date 2023/3/9 16:56
     **/
    public void scrollVideo(IDevice iDevice) {
        try {
            iDevice.executeShellCommand("input swipe 300 1000 300 30", mMultiLineReceiver);
        } catch (TimeoutException | AdbCommandRejectedException | ShellCommandUnresponsiveException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 看广告
     *
     * @param iDevice 设备
     * @return
     * @author Ashen
     * @date 2023/3/9 16:56
     **/
    public void watchAd(IDevice iDevice) {
        try {
            iDevice.executeShellCommand("input keyevent 4", mMultiLineReceiver);

        } catch (TimeoutException | AdbCommandRejectedException | ShellCommandUnresponsiveException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过windowCMD命令执行adb
     * runCmd(String.format(AppSwiper.appSwipe, s), s);
     * public static final String appSwipe = "adb -s %s shell input swipe 300 1000 300 30";
     *
     * @return
     * @author Ashen
     * @date 2023/3/9 16:56
     **/
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

    class MyMultiLineReceiver extends MultiLineReceiver {

        @Override
        public void processNewLines(String[] lines) {

        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }
}
