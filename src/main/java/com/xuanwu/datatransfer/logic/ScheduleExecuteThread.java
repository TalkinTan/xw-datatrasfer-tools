package com.xuanwu.datatransfer.logic;

import com.xuanwu.datatransfer.tools.*;
import com.xuanwu.datatransfer.ui.panel.ExecuteStatusPanel;

/**
 * 定时任务执行器，继承于执行器线程类
 *
 * @author Bob
 */
public class ScheduleExecuteThread extends ExecuteThread {

    public void run() {
        if (ExecuteStatusPanel.isRunning == false) {
            ExecuteStatusPanel.isRunning = true;
            this.setName("ScheduleExecuteThread");
            long enterTime = System.currentTimeMillis(); // 毫秒数
            ExecuteStatusPanel.progressTotal.setMaximum(6);
            // 初始化变量
            init();
            // 测试连接
            boolean isLinked = testLink();

            if (isLinked) {
                ExecuteStatusPanel.progressTotal.setValue(1);
                // 分析配置文件
                boolean isAnalyseSuccess = analyseConfigFile();
                if (isAnalyseSuccess) {
                    ExecuteStatusPanel.progressTotal.setValue(2);
                    // 备份
                    if (true) {
                        backUp();
                    }
                    ExecuteStatusPanel.progressTotal.setValue(3);
                    // 建立新快照
                    boolean isSnapSuccess = newSnap();
                    if (isSnapSuccess) {
                        ExecuteStatusPanel.progressTotal.setValue(4);
                        // 对比快照,并根据对比结果生成SQL
                        boolean isDiffSuccess = diffSnap();
                        if (isDiffSuccess) {
                            ExecuteStatusPanel.progressTotal.setValue(5);
                            // 执行SQL
                            boolean isExecuteSuccess = executeSQL();
                            if (isExecuteSuccess) {
                                ExecuteStatusPanel.progressTotal.setValue(6);

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
            ExecuteStatusPanel.labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime") + Utils.getNextSyncTime());
            ExecuteStatusPanel.isRunning = false;
        }
    }
}
