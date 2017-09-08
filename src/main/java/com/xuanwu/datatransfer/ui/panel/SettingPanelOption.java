package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.ui.AppMainWindow;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.MyIconButton;
import com.xuanwu.datatransfer.logic.ConstantsLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xuanwu.datatransfer.tools.ConstantsTools;
import com.xuanwu.datatransfer.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 高级选项面板
 *
 * @author Bob
 */
public class SettingPanelOption extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonSave;

    private static MyIconButton buttionTableFiled;

    private static MyIconButton buttionClearLogs;

    private static MyIconButton buttionClearBaks;

    private static JCheckBox checkBoxAutoBak;

    private static JCheckBox checkBoxDebug;

    private static JCheckBox checkBoxStrict;

    private static JTextField textField;

    private static final Logger logger = LoggerFactory.getLogger(SettingPanelOption.class);

    /**
     * 构造
     */
    public SettingPanelOption() {
        initialize();
        addComponent();
        setCurrentOption();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 添加组件
     */
    private void addComponent() {

        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(1, 1));

        // 设置Grid
        JPanel panelGridOption = new JPanel();
        panelGridOption.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridOption.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        // 初始化组件
        JPanel panelItem1 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelItem2 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelItem3 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelItem4 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelItem5 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelItem6 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelItem7 = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        panelItem1.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelItem2.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelItem3.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelItem4.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelItem5.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelItem6.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelItem7.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        panelItem1.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);
        panelItem2.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);
        panelItem3.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);
        panelItem4.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);
        panelItem5.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);
        panelItem6.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);
        panelItem7.setPreferredSize(ConstantsUI.PANEL_ITEM_SIZE);

        // 各Item
        buttionTableFiled = new MyIconButton(ConstantsUI.ICON_TABLE_FIELD, ConstantsUI.ICON_TABLE_FIELD_ENABLE,
                ConstantsUI.ICON_TABLE_FIELD_DISABLE, "");
        panelItem1.add(buttionTableFiled);

        buttionClearLogs = new MyIconButton(ConstantsUI.ICON_CLEAR_LOG, ConstantsUI.ICON_CLEAR_LOG_ENABLE,
                ConstantsUI.ICON_CLEAR_LOG_DISABLE, "");
        panelItem2.add(buttionClearLogs);

        buttionClearBaks = new MyIconButton(ConstantsUI.ICON_CLEAR_ALL_BAKS, ConstantsUI.ICON_CLEAR_ALL_BAKS_ENABLE,
                ConstantsUI.ICON_CLEAR_ALL_BAKS_DISABLE, "");
        panelItem3.add(buttionClearBaks);

        checkBoxAutoBak = new JCheckBox(PropertyUtil.getProperty("ds.ui.setting.autoBackUp"));
        checkBoxAutoBak.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        checkBoxAutoBak.setFont(ConstantsUI.FONT_RADIO);
        panelItem4.add(checkBoxAutoBak);

        JLabel label = new JLabel(PropertyUtil.getProperty("ds.ui.setting.mysqlPath"));
        textField = new JTextField();
        label.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        label.setFont(ConstantsUI.FONT_RADIO);
        textField.setFont(ConstantsUI.FONT_RADIO);
        Dimension dm = new Dimension(334, 26);
        textField.setPreferredSize(dm);
        panelItem5.add(label);
        panelItem5.add(textField);

        checkBoxStrict = new JCheckBox(PropertyUtil.getProperty("ds.ui.setting.strict"));
        checkBoxStrict.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        checkBoxStrict.setFont(ConstantsUI.FONT_RADIO);
        panelItem6.add(checkBoxStrict);

        checkBoxDebug = new JCheckBox(PropertyUtil.getProperty("ds.ui.setting.debugMode"));
        checkBoxDebug.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        checkBoxDebug.setFont(ConstantsUI.FONT_RADIO);
        panelItem7.add(checkBoxDebug);

        // 组合元素
        panelGridOption.add(panelItem1);
        panelGridOption.add(panelItem2);
        panelGridOption.add(panelItem3);
        panelGridOption.add(panelItem4);
        panelGridOption.add(panelItem5);
        panelGridOption.add(panelItem6);
        panelGridOption.add(panelItem7);

        panelCenter.add(panelGridOption);
        return panelCenter;
    }

    /**
     * 底部面板
     *
     * @return
     */
    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE, ConstantsUI.ICON_SAVE_ENABLE,
                ConstantsUI.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置当前combox选项状态
     */
    public static void setCurrentOption() {
    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
        buttonSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    JOptionPane.showMessageDialog(AppMainWindow.settingPanel, PropertyUtil.getProperty("ds.ui.save.success"),
                            PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(AppMainWindow.settingPanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(),
                            PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                    logger.error("Write to xml file error" + e1.toString());
                }

            }
        });

        buttionTableFiled.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(ConstantsLogic.TABLE_FIELD_DIR));
                } catch (IOException e1) {
                    logger.error("open table_field file fail:" + e1.toString());
                    e1.printStackTrace();
                }

            }
        });

        buttionClearLogs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int answer = JOptionPane.showConfirmDialog(AppMainWindow.settingPanel,
                        PropertyUtil.getProperty("ds.ui.setting.clean.makeSure"),
                        PropertyUtil.getProperty("ds.ui.tips"), 2);

                if (answer == 0) {
                    FileOutputStream testfile = null;
                    try {
                        testfile = new FileOutputStream(ConstantsTools.PATH_LOG);
                        testfile.write(new String("").getBytes());
                        testfile.flush();
                        JOptionPane.showMessageDialog(AppMainWindow.settingPanel,
                                PropertyUtil.getProperty("ds.ui.setting.clean.success"),
                                PropertyUtil.getProperty("ds.ui.tips"),
                                JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(AppMainWindow.settingPanel,
                                PropertyUtil.getProperty("ds.ui.setting.clean.fail") + e1.getMessage(),
                                PropertyUtil.getProperty("ds.ui.tips"),
                                JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    } finally {
                        if (testfile != null) {
                            try {
                                testfile.close();
                            } catch (IOException e1) {
                                JOptionPane.showMessageDialog(AppMainWindow.settingPanel,
                                        PropertyUtil.getProperty("ds.ui.setting.clean.fail") + e1.getMessage(),
                                        PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.ERROR_MESSAGE);
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
}
