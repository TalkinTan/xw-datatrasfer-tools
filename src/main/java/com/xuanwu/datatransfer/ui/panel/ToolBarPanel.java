package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.ui.AppMainWindow;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.MyIconButton;
import com.xuanwu.datatransfer.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 工具栏面板
 * 
 * @author Bob
 *
 */
public class ToolBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static MyIconButton buttonDbConnect;
	private static MyIconButton buttonExecuteInfo;
	private static MyIconButton buttonTaskChoise;

	/**
	 * 构造
	 */
	public ToolBarPanel() {
		initialize();
		addButtion();
		addListener();
	}

	/**
	 * 初始化
	 */
	private void initialize() {
		Dimension preferredSize = new Dimension(48, ConstantsUI.MAIN_WINDOW_HEIGHT);
		this.setPreferredSize(preferredSize);
		this.setMaximumSize(preferredSize);
		this.setMinimumSize(preferredSize);
		this.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		this.setLayout(new GridLayout(2, 1));
	}

	/**
	 * 添加工具按钮
	 */
	private void addButtion() {

		JPanel panelUp = new JPanel();
		panelUp.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		panelUp.setLayout(new FlowLayout(-2, -2, -4));
		JPanel panelDown = new JPanel();
		panelDown.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		panelDown.setLayout(new BorderLayout(0, 0));


		buttonDbConnect = new MyIconButton(ConstantsUI.ICON_STATUS_ENABLE, ConstantsUI.ICON_STATUS_ENABLE,
				ConstantsUI.ICON_STATUS, PropertyUtil.getProperty("ds.ui.status.title"));
		buttonTaskChoise = new MyIconButton(ConstantsUI.ICON_DATABASE, ConstantsUI.ICON_DATABASE_ENABLE,
				ConstantsUI.ICON_DATABASE, PropertyUtil.getProperty("ds.ui.database.title"));
		buttonExecuteInfo = new MyIconButton(ConstantsUI.ICON_SCHEDULE, ConstantsUI.ICON_SCHEDULE_ENABLE,
				ConstantsUI.ICON_SCHEDULE, PropertyUtil.getProperty("ds.ui.schedule.title"));

		panelUp.add(buttonDbConnect);
		panelUp.add(buttonTaskChoise);
		panelUp.add(buttonExecuteInfo);

		this.add(panelUp);
	}

	/**
	 * 为各按钮添加事件动作监听
	 */
	private void addListener() {
		buttonDbConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonDbConnect.setIcon(ConstantsUI.ICON_STATUS_ENABLE);
				buttonExecuteInfo.setIcon(ConstantsUI.ICON_SCHEDULE);
				buttonTaskChoise.setIcon(ConstantsUI.ICON_DATABASE);

				AppMainWindow.mainPanelCenter.removeAll();
				ExecuteStatusPanel.setContent();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.panelDBConnectionInfo, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});


		buttonExecuteInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonDbConnect.setIcon(ConstantsUI.ICON_STATUS);
				buttonExecuteInfo.setIcon(ConstantsUI.ICON_SCHEDULE_ENABLE);
				buttonTaskChoise.setIcon(ConstantsUI.ICON_DATABASE);

				AppMainWindow.mainPanelCenter.removeAll();
				ExecuteStatusPanel.setContent();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.panelExecuteStatus, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});

		buttonTaskChoise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonDbConnect.setIcon(ConstantsUI.ICON_STATUS);
				buttonExecuteInfo.setIcon(ConstantsUI.ICON_SCHEDULE);
				buttonTaskChoise.setIcon(ConstantsUI.ICON_DATABASE_ENABLE);

				AppMainWindow.mainPanelCenter.removeAll();
				DatabasePanelFrom.setContent();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.panelTaskChoise, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});

	}
}
