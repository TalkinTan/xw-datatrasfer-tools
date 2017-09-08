package com.xuanwu.datatransfer.logic;

import com.xuanwu.datatransfer.tools.*;
import com.xuanwu.datatransfer.ui.panel.StatusPanel;

/**
 * 定时任务执行器，继承于执行器线程类
 *
 * @author Bob
 */
public class ScheduleExecuteThread extends ExecuteThread {

    public void run() {
        if (StatusPanel.isRunning == false) {
            StatusPanel.isRunning = true;
            this.setName("ScheduleExecuteThread");
            StatusPanel.buttonStartNow.setEnabled(false);
            long enterTime = System.currentTimeMillis(); // 毫秒数
            StatusPanel.progressTotal.setMaximum(6);
            // 初始化变量
            init();
            // 测试连接
            boolean isLinked = testLink();

            if (isLinked) {
                StatusPanel.progressTotal.setValue(1);
                // 分析配置文件
                boolean isAnalyseSuccess = analyseConfigFile();
                if (isAnalyseSuccess) {
                    StatusPanel.progressTotal.setValue(2);
                    // 备份
                    if (true) {
                        backUp();
                    }
                    StatusPanel.progressTotal.setValue(3);
                    // 建立新快照
                    boolean isSnapSuccess = newSnap();
                    if (isSnapSuccess) {
                        StatusPanel.progressTotal.setValue(4);
                        // 对比快照,并根据对比结果生成SQL
                        boolean isDiffSuccess = diffSnap();
                        if (isDiffSuccess) {
                            StatusPanel.progressTotal.setValue(5);
                            // 执行SQL
                            boolean isExecuteSuccess = executeSQL();
                            if (isExecuteSuccess) {
                                StatusPanel.progressTotal.setValue(6);

                                // 设置持续时间
                                long leaveTime = System.currentTimeMillis(); // 毫秒数
                                float minutes = (float) (leaveTime - enterTime) / 1000; // 秒数
                                StatusLog.setKeepTime(String.valueOf(minutes));
                                // 设置成功次数+1
                                String success = "";
                                StatusLog.setSuccess(success);
                                StatusLog.setStatusDetail(PropertyUtil.getProperty("ds.logic.currentSyncFinish"), LogLevel.INFO);
                            } else {
                                // 恢复快照备份
                                SnapManage.recoverSnapBak();

                                String fail = "";
                                StatusLog.setFail(fail);
                            }

                        } else {
                            // 恢复快照备份
                            SnapManage.recoverSnapBak();

                        }

                    } else {
                        // 恢复快照备份
                        SnapManage.recoverSnapBak();

                    }

                } else {
                }

            }
            // 设置显示下一次执行时间
            StatusPanel.labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime") + Utils.getNextSyncTime());
            StatusPanel.isRunning = false;
        }
    }
}
