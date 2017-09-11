package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.logic.ScheduleExecuteThread;
import com.xuanwu.datatransfer.tools.PropertyUtil;
import com.xuanwu.datatransfer.tools.StatusLog;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  执行状态面板（步骤三）
 *
 * @Author：ttan
 * 日期：2017-09-11
 */
public class ExecuteStatusPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ExecuteStatusPanel.class);

    public static MyIconButton buttonStartSchedule;
    public static MyIconButton buttonStop;

    public static JProgressBar progressTotal;
    public static JProgressBar progressCurrent;

    public static JLabel labelStatus;
    public static JLabel labelStatusDetail;
    public static JLabel labelFrom;
    public static JLabel labelTo;
    public static JLabel labelLastTime;
    public static JLabel labelKeepTime;
    public static JLabel labelNextTime;
    public static JLabel labelSuccess;
    public static JLabel labelFail;
    private static JLabel labelLog;

    private static ScheduledExecutorService service;

    public static boolean isRunning = false;

    /**
     * 构造
     */
    public ExecuteStatusPanel() {
        super(true);
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
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(4, 1));

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

        // 来源/目标 Grid
        JPanel panelGridFromTo = new JPanel();
        panelGridFromTo.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridFromTo.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        labelFrom = new JLabel();
        labelTo = new JLabel();
        labelFrom.setFont(ConstantsUI.FONT_NORMAL);
        labelTo.setFont(ConstantsUI.FONT_NORMAL);
        labelFrom.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelTo.setPreferredSize(ConstantsUI.LABLE_SIZE);

        panelGridFromTo.add(labelFrom);
        panelGridFromTo.add(labelTo);

        // 详情Grid
        JPanel panelGridDetail = new JPanel();
        panelGridDetail.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridDetail.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        labelLastTime = new JLabel();
        labelKeepTime = new JLabel();
        labelNextTime = new JLabel();
        labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime"));
        labelSuccess = new JLabel();
        labelFail = new JLabel();
        labelLog = new JLabel(PropertyUtil.getProperty("ds.ui.status.logDetail"));

        labelLastTime.setFont(ConstantsUI.FONT_NORMAL);
        labelKeepTime.setFont(ConstantsUI.FONT_NORMAL);
        labelNextTime.setFont(ConstantsUI.FONT_NORMAL);
        labelSuccess.setFont(ConstantsUI.FONT_NORMAL);
        labelFail.setFont(ConstantsUI.FONT_NORMAL);
        labelLog.setFont(ConstantsUI.FONT_NORMAL);
        labelLastTime.setPreferredSize(new Dimension(240, 30));
        labelKeepTime.setPreferredSize(new Dimension(300, 30));
        labelNextTime.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelSuccess.setPreferredSize(new Dimension(240, 30));
        labelFail.setPreferredSize(new Dimension(236, 30));
        labelLog.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelLog.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);

        panelGridDetail.add(labelLastTime);
        panelGridDetail.add(labelKeepTime);
        panelGridDetail.add(labelNextTime);
        panelGridDetail.add(labelSuccess);
        panelGridDetail.add(labelFail);
        panelGridDetail.add(labelLog);

        // 进度Grid
        JPanel panelGridProgress = new JPanel();
        panelGridProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridProgress.setLayout(new GridLayout(2, 1, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelCurrentProgress = new JPanel();
        panelCurrentProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCurrentProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 20));
        JPanel panelTotalProgress = new JPanel();
        panelTotalProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelTotalProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        JLabel labelCurrent = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.current"));
        JLabel labelTotal = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.total"));
        labelCurrent.setFont(ConstantsUI.FONT_NORMAL);
        labelTotal.setFont(ConstantsUI.FONT_NORMAL);
        progressCurrent = new JProgressBar();
        progressTotal = new JProgressBar();

        Dimension preferredSizeLabel = new Dimension(80, 30);
        labelCurrent.setPreferredSize(preferredSizeLabel);
        labelTotal.setPreferredSize(preferredSizeLabel);
        Dimension preferredSizeProgressbar = new Dimension(640, 20);
        progressCurrent.setPreferredSize(preferredSizeProgressbar);
        progressTotal.setPreferredSize(preferredSizeProgressbar);

        panelCurrentProgress.add(labelCurrent);
        panelCurrentProgress.add(progressCurrent);
        panelTotalProgress.add(labelTotal);
        panelTotalProgress.add(progressTotal);

        panelGridProgress.add(panelCurrentProgress);
        panelGridProgress.add(panelTotalProgress);

        panelCenter.add(panelGridStatus);
        panelCenter.add(panelGridFromTo);
        panelCenter.add(panelGridDetail);
        panelCenter.add(panelGridProgress);

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
     * 设置状态面板组件内容
     */
    public static void setContent() {

        labelFrom.setText(
                PropertyUtil.getProperty("ds.ui.status.from"));
        labelTo.setText(PropertyUtil.getProperty("ds.ui.status.to"));
        labelLastTime.setText(PropertyUtil.getProperty("ds.ui.status.lastSync"));
        labelKeepTime.setText(PropertyUtil.getProperty("ds.ui.status.keepTime"));

        labelSuccess.setText(PropertyUtil.getProperty("ds.ui.status.successTimes"));
        labelFail.setText(PropertyUtil.getProperty("ds.ui.status.failTimes"));

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
                ExecuteStatusPanel.setContent();

                ExecuteStatusPanel.progressTotal.setValue(0);
                ExecuteStatusPanel.progressCurrent.setValue(0);
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
        labelLog.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelLog.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().open(new File(ConstantsUI.CURRENT_DIR + File.separator + "log"));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    logger.error(e1.toString());
                }
            }
        });
    }

    /**
     * 获取指定时间对应的毫秒数
     *
     * @param time "HH:mm:ss"
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
