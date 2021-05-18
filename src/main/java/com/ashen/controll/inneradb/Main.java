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
//                try {
//                    iDevice.executeShellCommand("adb shell input swipe 300 1000 300 30", new MultiLineReceiver() {
//                        @Override
//                        public void processNewLines(String[] lines) {
//                            System.out.println(lines);
//                        }
//
//                        public boolean isCancelled() {
//                            return false;
//                        }
//                    });
//                } catch (TimeoutException e) {
//                    e.printStackTrace();
//                } catch (AdbCommandRejectedException e) {
//                    e.printStackTrace();
//                } catch (ShellCommandUnresponsiveException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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
