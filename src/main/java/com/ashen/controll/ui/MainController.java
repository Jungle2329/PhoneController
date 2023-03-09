package com.ashen.controll.ui;

import com.ashen.controll.controller.AppSwiper;
import com.ashen.controll.manager.DeviceManager;
import com.ashen.controll.manager.LogManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Created by Jungle on 2020/9/3.
 *
 * @author JungleZhang
 * @version 1.0.0
 * @Description
 */
public class MainController {

    private static long postInterval = 10000;

    public void create(String[] args) {
        createWindow();
    }

    private void createWindow() {
        // 状态框
        JPanel mainWindow = new JPanel(new GridLayout(4, 1));
        mainWindow.add(createTextArea());
        mainWindow.add(createDevicesView());
        mainWindow.add(createIntervalView());
        mainWindow.add(createStartStopView());

        JFrame jFrame = new JFrame();
        jFrame.setLayout(new BorderLayout());
        jFrame.add(mainWindow, BorderLayout.CENTER);
        jFrame.setTitle("PhoneController V1.1.0");
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(600, 500));
        jFrame.setResizable(true);
        jFrame.setLocationRelativeTo(jFrame.getOwner());
    }

    private JPanel createIntervalView() {
        JPanel option = new JPanel(new GridLayout(1, 2));
        JTextField runInterval = new JTextField();
        runInterval.setHorizontalAlignment(JTextField.CENTER);
        option.add(runInterval);
        JButton jButton2 = new JButton("更换运行间隔/秒");
        jButton2.addActionListener(ae -> {
            String text = runInterval.getText();
            try {
                postInterval = Integer.parseInt(text);
                postInterval *= 1000;
                LogManager.getInstance().log(String.format("更新运行间隔为%s秒", text));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        option.add(jButton2);
        return option;
    }

    private JPanel createDevicesView() {
        JPanel option = new JPanel(new GridLayout(1, 2));
        TextArea statusArea = new TextArea();
        statusArea.setColumns(30);
        statusArea.setRows(150);
        statusArea.setBackground(Color.WHITE);
        statusArea.setFont(new Font("宋体", Font.PLAIN, 16));
        statusArea.setEditable(true);
        DeviceManager.getInstance().bindView(statusArea);
        option.add(statusArea);
        return option;
    }

    private JPanel createStartStopView() {
        JPanel jPanel = new JPanel(new GridLayout(1, 2));
        jPanel.add(createStartBottom());
        jPanel.add(createStopBottom());
        return jPanel;
    }

    /**
     * 开始按钮
     *
     * @return
     * @author Ashen
     * @date 2023/3/9 11:16
     **/
    private JButton createStartBottom() {
        JButton jButton = new JButton("开始");
        jButton.setSize(new Dimension(30, 20));
        jButton.addActionListener(e -> {
            AppSwiper.getInstance().swipeStart();
        });
        return jButton;
    }

    /**
     * 停止按钮
     *
     * @return
     * @author Ashen
     * @date 2023/3/9 11:16
     **/
    private JButton createStopBottom() {
        JButton jButton = new JButton("停止");
        jButton.setSize(new Dimension(30, 20));
        jButton.addActionListener(e -> AppSwiper.getInstance().swipeStop());
        return jButton;
    }

    /**
     * TextArea
     *
     * @return
     * @author Ashen
     * @date 2023/3/9 11:16
     **/
    private TextArea createTextArea() {
        TextArea log = new TextArea("欢迎使用AppSwiper");
        log.setColumns(30);
        log.setRows(150);
        log.setBackground(Color.WHITE);
        log.setFont(new Font("宋体", Font.PLAIN, 16));
        log.setEditable(true);
        LogManager.getInstance().bindView(log);
        return log;
    }

    public static long getPostInterval() {
        return postInterval;
    }

}
