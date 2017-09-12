package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.tools.PropertyUtil;
import com.xuanwu.datatransfer.ui.ConstantsUI;

import javax.swing.*;
import java.awt.*;


/**
 *  数据库链接信息（步骤一）
 *
 * @Author：ttan
 * 日期：2017-09-11
 */
public class DBConnectInfoPanel extends JPanel {


    private static final long serialVersionUID = 1L;

    public static JPanel panelFrom;
    private static JPanel databaseConfigPanel;

    /**
     * 构造
     */
    public DBConnectInfoPanel() {
        initialize();
        addComponent();
        addListener();
    }

    /**
     * 初始化面板
     */
    private void initialize() {
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
        databaseConfigPanel = new DatabaseConfigPanel();
    }

    /**
     * 为面板添加组件
     */
    private void addComponent() {

        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);

    }

    /**
     * 面板上部
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.dbconnectiondatabase.title"));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
    }

    /**
     * 面板中部
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new BorderLayout());

        // 数据库列表Panel
        JPanel panelList = new JPanel();
        Dimension preferredSize = new Dimension(245, ConstantsUI.MAIN_WINDOW_HEIGHT);
        panelList.setPreferredSize(preferredSize);
        panelList.setBackground(new Color(62, 62, 62));
        panelList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        panelFrom = new JPanel();
        panelFrom.setBackground(new Color(69, 186, 121));
        panelFrom.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        Dimension preferredSizeListItem = new Dimension(245, 48);
        panelFrom.setPreferredSize(preferredSizeListItem);

        JLabel labelFrom = new JLabel(PropertyUtil.getProperty("ds.ui.dbconnectionsqlserver.info"));
        Font fontListItem = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);
        labelFrom.setFont(fontListItem);
        labelFrom.setForeground(Color.white);
        panelFrom.add(labelFrom);

        panelList.add(panelFrom);

        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(databaseConfigPanel, BorderLayout.CENTER);

        return panelCenter;
    }

    /**
     * 添加相关组件的事件监听
     */
    private void addListener() {

    }

}
