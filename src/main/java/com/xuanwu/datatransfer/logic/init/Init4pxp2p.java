package com.xuanwu.datatransfer.logic.init;

import com.xuanwu.datatransfer.ui.panel.ExecuteStatusPanel;
import com.opencsv.CSVWriter;
import com.xuanwu.datatransfer.tools.DbUtilMySQL;
import com.xuanwu.datatransfer.tools.DbUtilSQLServer;
import com.xuanwu.datatransfer.tools.LogLevel;
import com.xuanwu.datatransfer.tools.StatusLog;

import java.io.File;

public class Init4pxp2p {

    public static boolean init() {
        StatusLog.setStatusDetail("开始初始化第一次快照，请耐心等待……", LogLevel.INFO);

        boolean isSuccess = true;
        DbUtilMySQL mySql = DbUtilMySQL.getInstance();
        DbUtilSQLServer SQLServer = DbUtilSQLServer.getInstance();
        CSVWriter csvWriterRole = null;
        CSVWriter csvWriterUser = null;
        File snapsDir = null;
        ExecuteStatusPanel.progressCurrent.setMaximum(7);
        int progressValue = 0;
        ExecuteStatusPanel.progressCurrent.setValue(progressValue);

		/*Do Sth you need to init*/
        return isSuccess;
    }

}
