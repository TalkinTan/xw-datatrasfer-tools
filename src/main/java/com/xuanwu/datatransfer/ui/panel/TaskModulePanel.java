package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.tools.PropertyUtil;
import com.xuanwu.datatransfer.ui.AppMainWindow;
import com.xuanwu.datatransfer.ui.ConstantsUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 模块任务面板
 *
 * @Author：ttan
 * 日期：2017-09-11
 */
public class TaskModulePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JPanel linkDatasourcePanel;
    public static JPanel linkModulePanel;

    public static JPanel panelRightContent;

    private static JPanel panelDatasource;
    private static JPanel panelModule;

    /**
     * 构造
     */
    public TaskModulePanel() {
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

        panelDatasource = new TaskDataSourcePanel();
        panelModule = new TaskModulePanel();
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

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.taskchoise.title"));
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
        Dimension preferredSize = new Dimension(160, ConstantsUI.MAIN_WINDOW_HEIGHT);
        panelList.setPreferredSize(preferredSize);
        panelList.setBackground(new Color(62, 62, 62));
        panelList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        linkDatasourcePanel = new JPanel();
        linkDatasourcePanel.setBackground(new Color(69, 186, 121));
        linkDatasourcePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        Dimension preferredSizeListItem = new Dimension(245, 48);
        linkDatasourcePanel.setPreferredSize(preferredSizeListItem);
        linkModulePanel = new JPanel();
        linkModulePanel.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        linkModulePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        linkModulePanel.setPreferredSize(preferredSizeListItem);

        JLabel labelFrom = new JLabel(PropertyUtil.getProperty("ds.ui.taskchoise.datasourcetitle"));
        JLabel labelTo = new JLabel(PropertyUtil.getProperty("ds.ui.taskchoise.moduletitle"));
        Font fontListItem = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);
        labelFrom.setFont(fontListItem);
        labelTo.setFont(fontListItem);
        labelFrom.setForeground(Color.white);
        labelTo.setForeground(Color.white);
        linkDatasourcePanel.add(labelFrom);
        linkModulePanel.add(labelTo);

        panelList.add(linkDatasourcePanel);
        panelList.add(linkModulePanel);

        // 数据库设置Panel
        panelRightContent = new JPanel();
        panelRightContent.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelRightContent.setLayout(new BorderLayout());
        panelRightContent.add(panelDatasource);

        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(panelRightContent, BorderLayout.CENTER);

        return panelCenter;
    }

    /**
     * 添加相关组件的事件监听
     */
    private void addListener() {
        linkDatasourcePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                linkDatasourcePanel.setBackground(new Color(69, 186, 121));
                linkModulePanel.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);

                TaskChoisePanel.panelRightContent.removeAll();
                TaskChoisePanel.panelRightContent.add(panelDatasource);
                AppMainWindow.panelTaskChoise.updateUI();

            }
        });

        linkModulePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                linkModulePanel.setBackground(new Color(69, 186, 121));
                linkDatasourcePanel.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);

                TaskChoisePanel.panelRightContent.removeAll();
                TaskChoisePanel.panelRightContent.add(panelModule);
                AppMainWindow.panelTaskChoise.updateUI();

            }
        });

    }

}
