package com.xuanwu.datatransfer.ui.panel;

import com.xuanwu.datatransfer.ui.AppMainWindow;
import com.xuanwu.datatransfer.ui.ConstantsUI;
import com.xuanwu.datatransfer.ui.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xuanwu.datatransfer.tools.DESPlus;
import com.xuanwu.datatransfer.tools.DbUtilSQLServer;
import com.xuanwu.datatransfer.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * 源数据库面板
 * 
 * @author Bob
 *
 */
public class DatabaseConfigPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static MyIconButton buttonTestLink;
	private static MyIconButton buttonSave;
	private static JTextField textFieldDatabaseHost;
	private static JTextField textFieldDatabaseName;
	private static JTextField textFieldDatabaseUser;
	private static JPasswordField passwordFieldDatabasePassword;


	private static JTextField choosePathJTextField;
	private static JFileChooser jFileChooser;
	private static JButton chooseBtn;

	private static final Logger logger = LoggerFactory.getLogger(DatabaseConfigPanel.class);

	/**
	 * 构造
	 */
	public DatabaseConfigPanel() {
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

		this.add(getCenterPanel(), BorderLayout.CENTER);
		this.add(getDownPanel(), BorderLayout.SOUTH);

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
		panelCenter.setLayout(new GridLayout(2, 1));

		// 设置Grid
		JPanel panelGridSetting = new JPanel();
		panelGridSetting.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

		// 初始化组件
		JLabel labelDatabaseType = new JLabel(PropertyUtil.getProperty("ds.ui.database.type"));
		JLabel labelDatabaseHost = new JLabel(PropertyUtil.getProperty("ds.ui.database.host"));
		JLabel labelDatabaseName = new JLabel(PropertyUtil.getProperty("ds.ui.database.name"));
		JLabel labelDatabaseUser = new JLabel(PropertyUtil.getProperty("ds.ui.database.user"));
		JLabel labelDatabasePassword = new JLabel(PropertyUtil.getProperty("ds.ui.database.password"));
		JComboBox<String> comboxDatabaseType = new JComboBox<String>();
		comboxDatabaseType.addItem("SQL Server");
		comboxDatabaseType.setEditable(false);
		textFieldDatabaseHost = new JTextField();
		textFieldDatabaseName = new JTextField();
		textFieldDatabaseUser = new JTextField();
		passwordFieldDatabasePassword = new JPasswordField();

		// 字体
		labelDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
		labelDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
		labelDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
		labelDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
		labelDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);
		comboxDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
		textFieldDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
		textFieldDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
		textFieldDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
		passwordFieldDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);

		// 大小
		labelDatabaseType.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
		labelDatabaseHost.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
		labelDatabaseName.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
		labelDatabaseUser.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
		labelDatabasePassword.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
		comboxDatabaseType.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
		textFieldDatabaseHost.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
		textFieldDatabaseName.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
		textFieldDatabaseUser.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
		passwordFieldDatabasePassword.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);

		// 组合元素
		panelGridSetting.add(labelDatabaseType);
		panelGridSetting.add(comboxDatabaseType);
		panelGridSetting.add(labelDatabaseHost);
		panelGridSetting.add(textFieldDatabaseHost);
		panelGridSetting.add(labelDatabaseName);
		panelGridSetting.add(textFieldDatabaseName);
		panelGridSetting.add(labelDatabaseUser);
		panelGridSetting.add(textFieldDatabaseUser);
		panelGridSetting.add(labelDatabasePassword);
		panelGridSetting.add(passwordFieldDatabasePassword);


		//存放SQL目录选择
		JPanel chooseFilePanel = new JPanel();
		chooseFilePanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		chooseFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

		JLabel pathLabel = new JLabel("脚本存储目录:");
		pathLabel.setPreferredSize(new Dimension(100, 30));

		choosePathJTextField = new JTextField();
		choosePathJTextField.setPreferredSize(new Dimension(300, 24));
		choosePathJTextField.setEditable(false);

		chooseBtn = new JButton("选择");
		chooseBtn.setPreferredSize(new Dimension(60,24));



		chooseFilePanel.add(pathLabel);
		chooseFilePanel.add(choosePathJTextField);
		chooseFilePanel.add(chooseBtn);

		jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser.setDialogTitle("选择目录存储脚本");


		panelCenter.add(panelGridSetting);
		panelCenter.add(chooseFilePanel);

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
		panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

		buttonTestLink = new MyIconButton(ConstantsUI.ICON_TEST_LINK, ConstantsUI.ICON_TEST_LINK_ENABLE,
				ConstantsUI.ICON_TEST_LINK_DISABLE, "");
		buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE, ConstantsUI.ICON_SAVE_ENABLE,
				ConstantsUI.ICON_SAVE_DISABLE, "");
		panelDown.add(buttonTestLink);
		panelDown.add(buttonSave);

		return panelDown;
	}

	/**
	 * 设置文本区内容
	 */
	public static void setContent() {
		//textFieldDatabaseHost.setText(ConstantsTools.CONFIGER.getHostFrom());
		//textFieldDatabaseName.setText(ConstantsTools.CONFIGER.getNameFrom());

		String password = "";
		String user = "";
		try {
			DESPlus des = new DESPlus();
			//password = des.decrypt(ConstantsTools.CONFIGER.getPasswordFrom());
			//user = des.decrypt(ConstantsTools.CONFIGER.getUserFrom());
		} catch (Exception e) {
			logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.decode") + e.toString());
			e.printStackTrace();
		}
		textFieldDatabaseUser.setText(user);
		passwordFieldDatabasePassword.setText(password);

	}

	/**
	 * 为相关组件添加事件监听
	 */
	private void addListener() {
		//选择目录按钮
		chooseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = jFileChooser.showOpenDialog(DatabaseConfigPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					String path = jFileChooser.getSelectedFile().getAbsolutePath();

					choosePathJTextField.setText(path);
				}
			}
		});

		/**
		 * 1，用于连接SQLSERVER
		 * 2，可以将所有的数据都暂存到
		 */
		buttonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String password = "";
					String user = "";
					try {
						DESPlus des = new DESPlus();
						user = des.encrypt(textFieldDatabaseUser.getText());
						password = des.encrypt(new String(passwordFieldDatabasePassword.getPassword()));
					} catch (Exception e1) {
						logger.error(PropertyUtil.getProperty("ds.ui.database.from.err.encode") + e1.toString());
						e1.printStackTrace();
					}

					JOptionPane.showMessageDialog(AppMainWindow.panelTaskChoise, PropertyUtil.getProperty("ds.ui.save.success"), PropertyUtil.getProperty("ds.ui.tips"),
							JOptionPane.PLAIN_MESSAGE);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(AppMainWindow.panelTaskChoise, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
							JOptionPane.ERROR_MESSAGE);
					logger.error("Write to xml file error" + e1.toString());
				}

			}
		});

		buttonTestLink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					DbUtilSQLServer dbSQLServer = DbUtilSQLServer.getInstance();
					String DBUrl = textFieldDatabaseHost.getText();
					String DBName = textFieldDatabaseName.getText();
					String DBUser = textFieldDatabaseUser.getText();
					String DBPassword = new String(passwordFieldDatabasePassword.getPassword());
					Connection conn = dbSQLServer.testConnection(DBUrl, DBName, DBUser, DBPassword);
					if (conn == null) {
						JOptionPane.showMessageDialog(AppMainWindow.panelTaskChoise, PropertyUtil.getProperty("ds.ui.database.err.link.fail"), PropertyUtil.getProperty("ds.ui.tips"),
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(AppMainWindow.panelTaskChoise, PropertyUtil.getProperty("ds.ui.database.err.link.success"), PropertyUtil.getProperty("ds.ui.tips"),
								JOptionPane.PLAIN_MESSAGE);
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(AppMainWindow.panelTaskChoise, PropertyUtil.getProperty("ds.ui.database.err.link.fail")+"\n" + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

}
