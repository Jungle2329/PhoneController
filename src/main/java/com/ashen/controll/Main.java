package com.ashen.controll;

import com.ashen.controll.manager.LogManager;
import com.ashen.controll.ui.MainController;

/**
 * Created by Jungle on 2020/9/3.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class Main {

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.create(args);
        LogManager.getInstance().bindView(controller);
    }
}
