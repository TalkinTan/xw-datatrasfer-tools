package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.tools.DESPlus;
import com.xuanwu.datatransfer.tools.PropertyUtil;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 模块任务面板
 *
 * @Author：ttan
 * 日期：2017-09-11
 */
public class TaskModulePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonTestLink;
    private static MyIconButton buttonSave;
    private static JTextField textFieldDatabaseHost;
    private static JTextField textFieldDatabaseName;
    private static JTextField textFieldDatabaseUser;
    private static JPasswordField passwordFieldDatabasePassword;

    private static final Logger logger = LoggerFactory.getLogger(TaskModulePanel.class);

    /**
     * 构造
     */
    public TaskModulePanel() {
        initialize();
        addComponent();
        setContent();
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
        panelCenter.setLayout(new GridLayout(2, 1));

        // 设置Grid
        JPanel panelGridSetting = new JPanel();
        panelGridSetting.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        // 初始化组件
        JLabel labelDatabaseType = new JLabel(PropertyUtil.getProperty("ds.ui.database.type"));
        JLabel labelDatabaseHost = new JLabel(PropertyUtil.getProperty("ds.ui.database.host"));
        JLabel labelDatabaseName = new JLabel(PropertyUtil.getProperty("ds.ui.database.name"));
        JLabel labelDatabaseUser = new JLabel(PropertyUtil.getProperty("ds.ui.database.user"));
        JLabel labelDatabasePassword = new JLabel(PropertyUtil.getProperty("ds.ui.database.password"));
        JComboBox<String> comboxDatabaseType = new JComboBox<String>();
        comboxDatabaseType.addItem("MySQL");
        comboxDatabaseType.setEditable(false);
        textFieldDatabaseHost = new JTextField();
        textFieldDatabaseName = new JTextField();
        textFieldDatabaseUser = new JTextField();
        passwordFieldDatabasePassword = new JPasswordField();

        // 字体
        labelDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);
        comboxDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
        passwordFieldDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);

        // 大小
        labelDatabaseType.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseHost.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseName.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseUser.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabasePassword.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        comboxDatabaseType.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseHost.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseName.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseUser.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        passwordFieldDatabasePassword.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);

        // 组合元素
        panelGridSetting.add(labelDatabaseType);
        panelGridSetting.add(comboxDatabaseType);
        panelGridSetting.add(labelDatabaseHost);
        panelGridSetting.add(textFieldDatabaseHost);
        panelGridSetting.add(labelDatabaseName);
        panelGridSetting.add(textFieldDatabaseName);
        panelGridSetting.add(labelDatabaseUser);
        panelGridSetting.add(textFieldDatabaseUser);
        panelGridSetting.add(labelDatabasePassword);
        panelGridSetting.add(passwordFieldDatabasePassword);

        panelCenter.add(panelGridSetting);
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

        buttonTestLink = new MyIconButton(ConstantsUI.ICON_TEST_LINK, ConstantsUI.ICON_TEST_LINK_ENABLE,
                ConstantsUI.ICON_TEST_LINK_DISABLE, "");
        buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE, ConstantsUI.ICON_SAVE_ENABLE,
                ConstantsUI.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonTestLink);
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置文本区内容
     */
    public static void setContent() {

        String user = "";
        String password = "";
        try {
            DESPlus des = new DESPlus();
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
            e.printStackTrace();
        }
        textFieldDatabaseUser.setText(user);
        passwordFieldDatabasePassword.setText(password);

    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {


    }
}
