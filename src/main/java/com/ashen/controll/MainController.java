package com.ashen.controll;

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

    private TextArea logsArea;
    private static long postInterval = 10000;

    public void create(String[] args) {
        createWindow();
        logsArea.setText("欢迎使用AppSwiper");
    }

    private void createWindow() {
        logsArea = createTextArea();
        // 状态框
        JPanel mainWindow = new JPanel(new GridLayout(4, 1));
        mainWindow.add(logsArea);
        mainWindow.add(createDevicesView());
        mainWindow.add(createIntervalView());
        mainWindow.add(createStartStopView());

        JFrame jFrame = new JFrame();
        jFrame.setLayout(new BorderLayout());
        jFrame.add(mainWindow, BorderLayout.CENTER);
        jFrame.setTitle("PhoneController");
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(600, 500));
        jFrame.setResizable(true);
        jFrame.setLocationRelativeTo(jFrame.getOwner());

        AppSwiper.getInstance().addLogListener(log -> {
            logsArea.append("\n");
            logsArea.append(log);
        });
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
                AppSwiper.getInstance().log(String.format("更新运行间隔为%s秒", text));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        option.add(jButton2);
        return option;
    }

    private JPanel createDevicesView() {
        JPanel option = new JPanel(new GridLayout(1, 2));
        JTextField field = new JTextField();
        field.setHorizontalAlignment(JTextField.CENTER);
        AppSwiper.getInstance().getDevices().forEach(s -> {
            field.setText(s + "\n");
            System.out.println(s);
        });

        return option;
    }

    private JPanel createStartStopView() {
        JPanel jPanel = new JPanel(new GridLayout(1, 2));
        jPanel.add(createStartBottom());
        jPanel.add(createStopBottom());
        return jPanel;
    }

    private JButton createStartBottom() {
        JButton jButton = new JButton("开始");
        jButton.setSize(new Dimension(30, 20));
        jButton.addActionListener(e -> {
            AppSwiper.getInstance().swipeStart();
        });
        return jButton;
    }

    private JButton createStopBottom() {
        JButton jButton = new JButton("停止");
        jButton.setSize(new Dimension(30, 20));
        jButton.addActionListener(e -> AppSwiper.getInstance().swipeStop());
        return jButton;
    }

    private TextArea createTextArea() {
        TextArea log = new TextArea("欢迎使用AppSwiper");
        log.setColumns(30);
        log.setRows(150);
        log.setBackground(Color.WHITE);
        log.setFont(new Font("宋体", Font.BOLD, 16));
        log.setEditable(true);
        return log;
    }

    public static long getPostInterval() {
        return postInterval;
    }
}
