package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.table.CheckHeaderCellRenderer;
import com.xuanwu.datatransfer.ui.table.CheckTableModle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * 模块任务面板
 *
 * @Author：ttan 日期：2017-09-11
 */
public class TaskModulePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public JTable moduleJTable;

    public JPanel panelFilter;
    public JButton btnFilter;

    public JTextField filterField;

    /**
     * 构造
     */
    public TaskModulePanel() {
        initialize();
        addComponent();
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
        //this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    public JPanel getUpPanel() {
        panelFilter = new JPanel();
        panelFilter.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelFilter.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        JLabel filterNameLabel = new JLabel("过滤:");
        filterNameLabel.setPreferredSize(new Dimension(30, 30));
        filterField = new JTextField();
        filterField.setPreferredSize(new Dimension(200, 24));

        panelFilter.add(filterNameLabel);
        panelFilter.add(filterField);

        btnFilter = new JButton("搜索");
        btnFilter.setPreferredSize(new Dimension(50, 24));
        panelFilter.add(btnFilter);

        return panelFilter;
    }


    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        JPanel tablePanel = new JPanel();

        tablePanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        tablePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        tablePanel.setLayout(new BorderLayout(0, 0));

        moduleJTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(moduleJTable,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        //初始化数据
        initTable();

        return tablePanel;
    }

    private void initTable() {
        Vector headerNames = new Vector();
        headerNames.add("全选");
        headerNames.add("模块名称");
        headerNames.add("模块ID");

        Vector data = this.getData();
        CheckTableModle tableModel = new CheckTableModle(data, headerNames);
        moduleJTable.setModel(tableModel);
        moduleJTable.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(moduleJTable));
    }

    /**
     * 获得数据
     *
     * @return
     */
    private Vector getData() {
        Vector data = new Vector();
        Vector rowVector1 = new Vector();
        rowVector1.add(true);
        rowVector1.add("模块1");
        rowVector1.add("25");

        Vector rowVector2 = new Vector();
        rowVector2.add(false);
        rowVector2.add("模块2");
        rowVector2.add("26");

        Vector rowVector3 = new Vector();
        rowVector3.add(false);
        rowVector3.add("模块3");
        rowVector3.add("1");

        Vector rowVector4 = new Vector();
        rowVector4.add(false);
        rowVector4.add("中文的过滤");
        rowVector4.add("备注信息");


        //for(int i=1;i<100;i++) {

        data.add(rowVector1);
        data.add(rowVector2);
        data.add(rowVector3);
        data.add(rowVector4);
        //}

        return data;
    }


    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {


        filterField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFilter.doClick();
            }
        });


        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) moduleJTable.getRowSorter();
                if (sorter == null) {
                    sorter = new TableRowSorter<>(moduleJTable.getModel());
                    moduleJTable.setRowSorter(sorter);
                }

                String text = filterField.getText();

                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    //设置RowFilter 用于从模型中过滤条目，使得这些条目不会在视图中显示
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }

            }
        });


    }
}
