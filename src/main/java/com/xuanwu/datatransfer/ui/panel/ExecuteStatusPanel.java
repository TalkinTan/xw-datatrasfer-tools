package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.bean.IdName;
import com.xuanwu.datatransfer.logic.ScheduleExecuteThread;
import com.xuanwu.datatransfer.tools.PropertyUtil;
import com.xuanwu.datatransfer.tools.StatusLog;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.MyIconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 执行状态面板（步骤三）
 *
 * @Author：ttan 日期：2017-09-11
 */
public class ExecuteStatusPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static MyIconButton buttonStartSchedule;
    public static MyIconButton buttonStop;

    public static JPanel panelCenter;
    public static JScrollPane jscroll;

    public static JTextArea detailTextArea;
    public static JProgressBar progressTotal;

    public static JLabel labelStatus;
    public static JLabel labelStatusDetail;

    private static ScheduledExecutorService service;

    public static boolean isRunning = false;

    /**
     * 构造
     */
    public ExecuteStatusPanel() {
        super(true);
        initialize();
        addComponent();

        //在点击页面时，才触发事件
        //setContent();
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

        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    /**
     * 上部面板
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.executepanel.title"));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
    }

    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new BorderLayout());

        // 状态Grid
        JPanel panelGridStatus = new JPanel();
        panelGridStatus.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridStatus.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        labelStatus = new JLabel(PropertyUtil.getProperty("ds.ui.status.ready"));
        labelStatusDetail = new JLabel(PropertyUtil.getProperty("ds.ui.status.detail"));
        labelStatus.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 1, 15));
        labelStatusDetail.setFont(ConstantsUI.FONT_NORMAL);

        labelStatus.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelStatusDetail.setPreferredSize(ConstantsUI.LABLE_SIZE);

        panelGridStatus.add(labelStatus);
        panelGridStatus.add(labelStatusDetail);


        //详细配置信息
        JPanel transferDetailPanel = new JPanel();
        transferDetailPanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        transferDetailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));


        detailTextArea = new JTextArea();
        //注释掉，直接采用jscroll的大小来限定，不然没法显示滚动条
        //detailTextArea.setPreferredSize(new Dimension(780, 420));
        detailTextArea.setSelectedTextColor(Color.RED);
        //detailTextArea.setLineWrap(true);        //激活自动换行功能
        //detailTextArea.setWrapStyleWord(true);            // 激活断行不断字功能

        jscroll = new JScrollPane();

        jscroll.setViewportView(detailTextArea);
        jscroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jscroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jscroll.setPreferredSize(new Dimension(780,420));

        transferDetailPanel.add(jscroll);


        // 进度Grid
        JPanel panelTotalProgress = new JPanel();
        panelTotalProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelTotalProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        JLabel labelTotal = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.total"));
        labelTotal.setFont(ConstantsUI.FONT_NORMAL);
        progressTotal = new JProgressBar();

        Dimension preferredSizeLabel = new Dimension(80, 30);
        labelTotal.setPreferredSize(preferredSizeLabel);
        Dimension preferredSizeProgressbar = new Dimension(680, 20);
        progressTotal.setPreferredSize(preferredSizeProgressbar);

        panelTotalProgress.add(labelTotal);
        panelTotalProgress.add(progressTotal);


        panelCenter.add(panelGridStatus, BorderLayout.NORTH);
        panelCenter.add(transferDetailPanel, BorderLayout.CENTER);
        panelCenter.add(panelTotalProgress, BorderLayout.SOUTH);

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
        panelDown.setLayout(new GridLayout(1, 2));
        JPanel panelGrid1 = new JPanel();
        panelGrid1.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGrid1.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonStartSchedule = new MyIconButton(ConstantsUI.ICON_START_SCHEDULE, ConstantsUI.ICON_START_SCHEDULE_ENABLE,
                ConstantsUI.ICON_START_SCHEDULE_DISABLE, "");
        buttonStop = new MyIconButton(ConstantsUI.ICON_STOP, ConstantsUI.ICON_STOP_ENABLE,
                ConstantsUI.ICON_STOP_DISABLE, "");
        buttonStop.setEnabled(false);
        panelGrid1.add(buttonStartSchedule);
        panelGrid1.add(buttonStop);

        panelDown.add(panelGrid1);
        return panelDown;
    }

    /**
     * 设置显示内容
     */
    public static void setContent() {
        Vector<IdName> selectDatasourceList = TaskDataSourcePanel.dataSourceJTable.getSelectedData();

        detailTextArea.setText(String.format(ConstantsUI.EXECUTE_INFO,"数据源迁移")+ "\n");
        detailTextArea.setText(detailTextArea.getText() + showDetail(selectDatasourceList));
        detailTextArea.setText(detailTextArea.getText() + "\n");


        int height = 20;
        Point p = new Point();
        p.setLocation(0, detailTextArea.getLineCount() * height);
        jscroll.getViewport().setViewPosition(p);
        jscroll.updateUI();
    }


    /**
     * 显示详情
     *
     * @param idNames
     * @return
     */
    public static String showDetail(Vector<IdName> idNames) {

        if (idNames == null || idNames.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        Iterator<IdName> its = idNames.iterator();

        IdName in = its.next();
        sb.append("ID 为 " + in.getId() + "," + "名称为:" + in.getName());

        while (its.hasNext()) {
            IdName in2 = its.next();
            sb.append("\n");
            sb.append("ID 为 " + in2.getId() + "," + "名称为:" + in2.getName());
        }


        return sb.toString();
    }

    /**
     * 为各组件添加事件监听
     */
    private void addListener() {
        buttonStartSchedule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                buttonStartSchedule.setEnabled(false);
                buttonStop.setEnabled(true);

                ExecuteStatusPanel.progressTotal.setValue(0);
                labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.scheduledRunning"));
                ScheduleExecuteThread syncThread = new ScheduleExecuteThread();
                service = Executors.newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                String scheduleConf = "";
                if (scheduleConf.equals("true,false,false,false,false,false,false")) {
                    service.scheduleAtFixedRate(syncThread, 0, 5, TimeUnit.MINUTES);

                } else if (scheduleConf.equals("false,true,false,false,false,false,false")) {
                    service.scheduleAtFixedRate(syncThread, 0, 15, TimeUnit.MINUTES);

                } else if (scheduleConf.equals("false,false,true,false,false,false,false")) {
                    service.scheduleAtFixedRate(syncThread, 0, 30, TimeUnit.MINUTES);

                } else if (scheduleConf.equals("false,false,false,true,false,false,false")) {
                    service.scheduleAtFixedRate(syncThread, 0, 1, TimeUnit.HOURS);

                } else if (scheduleConf.equals("false,false,false,false,true,false,false")) {
                    service.scheduleAtFixedRate(syncThread, 0, 1, TimeUnit.DAYS);

                } else if (scheduleConf.equals("false,false,false,false,false,true,false")) {
                    service.scheduleAtFixedRate(syncThread, 0, 7, TimeUnit.DAYS);

                } else if (scheduleConf.equals("false,false,false,false,false,false,true")) {
                    long oneDay = 24 * 60 * 60 * 1000;
                    long initDelay =
                            System.currentTimeMillis();
                    initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
                    service.scheduleAtFixedRate(syncThread, initDelay, oneDay, TimeUnit.MILLISECONDS);
                }

            }
        });
        buttonStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStartSchedule.setEnabled(true);
                service.shutdown();
                StatusLog.setNextTime("");
                labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.ready"));
                buttonStop.setEnabled(false);
            }
        });
    }


}
