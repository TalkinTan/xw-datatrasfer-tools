package com.xuanwu.datatransfer.ui.table;

import com.xuanwu.datatransfer.bean.IdName;

import javax.swing.*;
import java.util.Vector;

/**
 * 复选框JTable
 *
 * Created by tantan on 2017/9/11.
 */
public class CheckBoxJTable extends JTable {
    public Vector headerNames;

    public CheckTableModle tableModel;

    public CheckBoxJTable(Vector headerNames) {
        super();

        this.headerNames = headerNames;

    }

    /**
     * 设置Table数据
     *
     * @param data
     */
    public void setData(Vector data) {

        tableModel = new CheckTableModle(data, headerNames);

        this.setModel(tableModel);
        this.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(this));

    }

    /**
     * 获取选择的Table 数据
     *
     * @return
     */
    public Vector<IdName> getSelectedData() {

        Vector<IdName> selectedData = new Vector<IdName>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (Boolean.valueOf(tableModel.getValueAt(i, 0) + "")) {

                String id = String.valueOf(tableModel.getValueAt(i, 2));
                String name = String.valueOf(tableModel.getValueAt(i, 1));

                IdName idName = new IdName(id, name);
                selectedData.add(idName);
            }
        }

        return selectedData;
    }
}
