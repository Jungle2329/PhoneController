package com.ashen.controll.manager;

import java.awt.TextArea;

/**
 * Created by Jungle on 2021/6/21.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class LogManager {

    private static final LogManager ourInstance = new LogManager();

    private TextArea logsArea;

    public static LogManager getInstance() {
        return ourInstance;
    }

    public void bindView(TextArea logsArea) {
        this.logsArea = logsArea;
    }

    public void log(String msg) {
        if (logsArea != null) {
            logsArea.append("\n");
            logsArea.append(msg);
        }
    }

}
