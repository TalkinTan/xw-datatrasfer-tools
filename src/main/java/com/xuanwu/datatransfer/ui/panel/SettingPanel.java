package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.ui.AppMainWindow;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 设置面板
 * 
 * @author Bob
 *
 */
public class SettingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JPanel panelOption;
	private static JPanel panelAbout;
	public static JPanel settingPanelMain;
	private static JPanel settingPanelOption;
	private static JPanel settingPanelAbout;

	/**
	 * 构造
	 */
	public SettingPanel() {
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
		settingPanelOption = new SettingPanelOption();
		settingPanelAbout = new SettingPanelAbout();
	}

	/**
	 * 添加组件
	 */
	private void addComponent() {

		this.add(getUpPanel(), BorderLayout.NORTH);
		this.add(getCenterPanel(), BorderLayout.CENTER);

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

		JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.setting.title"));
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
		panelCenter.setLayout(new BorderLayout());

		// 列表Panel
		JPanel panelList = new JPanel();
		Dimension preferredSize = new Dimension(245, ConstantsUI.MAIN_WINDOW_HEIGHT);
		panelList.setPreferredSize(preferredSize);
		panelList.setBackground(new Color(62, 62, 62));
		panelList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		panelOption = new JPanel();
		panelOption.setBackground(new Color(69, 186, 121));
		panelOption.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
		Dimension preferredSizeListItem = new Dimension(245, 48);
		panelOption.setPreferredSize(preferredSizeListItem);
		panelAbout = new JPanel();
		panelAbout.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		panelAbout.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
		panelAbout.setPreferredSize(preferredSizeListItem);

		JLabel labelOption = new JLabel(PropertyUtil.getProperty("ds.ui.setting.option"));
		JLabel labelAbout = new JLabel(PropertyUtil.getProperty("ds.ui.setting.about"));
		Font fontListItem = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);
		labelOption.setFont(fontListItem);
		labelAbout.setFont(fontListItem);
		labelOption.setForeground(Color.white);
		labelAbout.setForeground(Color.white);
		panelOption.add(labelOption);
		panelAbout.add(labelAbout);

		panelList.add(panelOption);
		panelList.add(panelAbout);

		// 设置Panel
		settingPanelMain = new JPanel();
		settingPanelMain.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		settingPanelMain.setLayout(new BorderLayout());
		settingPanelMain.add(settingPanelOption);

		panelCenter.add(panelList, BorderLayout.WEST);
		panelCenter.add(settingPanelMain, BorderLayout.CENTER);

		return panelCenter;
	}

	/**
	 * 为相关组件添加事件监听
	 */
	private void addListener() {
		panelOption.addMouseListener(new MouseListener() {

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

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelOption.setBackground(new Color(69, 186, 121));
				panelAbout.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);

				SettingPanel.settingPanelMain.removeAll();
				SettingPanelOption.setCurrentOption();
				SettingPanel.settingPanelMain.add(settingPanelOption);
				AppMainWindow.settingPanel.updateUI();

			}
		});

		panelAbout.addMouseListener(new MouseListener() {

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

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelAbout.setBackground(new Color(69, 186, 121));
				panelOption.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);

				SettingPanel.settingPanelMain.removeAll();
				SettingPanel.settingPanelMain.add(settingPanelAbout);
				AppMainWindow.settingPanel.updateUI();

			}
		});

	}
}
