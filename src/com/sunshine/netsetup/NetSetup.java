/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 系统设定对话框
 *	[ 文件名      ]  : NetSetup.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 设置系统连接数据库的方式及参数
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.1
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/02      1.0             顾俊            创建
 *	2006/04/06      1.1             顾俊            增加ODBC配置
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## public NetSetup (JFrame frame) {} ]:
 *		功能: 组建系统设定对话框
 *
 *	[## private JPanel buildDBA() {} ]:
 *		功能: 组建数据库面板
 *
 *	[## private JPanel buildSYS() {} ]: 
 *		功能: 组建系统设置面板
 *
 *	[## private void setupInit(int fg) {} ]:
 *		功能: 设置本对话框各组件的默认值
 *
 *	[## private void ceShi() {} ]:
 *		功能: 测试对话框里的设置能否正常连接数据库
 *
 *
 *  [ 遗留问题    ]  : 在JDBC的连接测试时，如果IP不对，容易出现假死机现象
 *
 *##############################################################################
 */
package com.sunshine.netsetup;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.*;

public class NetSetup 
extends JDialog 
implements ActionListener, MouseListener, KeyListener, ItemListener {
	
	JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
	JButton bt1, bt2, bt3, bt4;
	JRadioButton rb1, rb2, rb3;
	ButtonGroup bg;
	JComboBox cb1, cb2;
	JPanel stMain, dba, sys, bp;
	JTabbedPane tp;
	//INI文件中的键名
	String ini[] = { "[SOFTINFO]", "UserName", "CompName", "[CONFIG]", "Soft_First",
					 "Default_Link" , "Default_Page", "Sys_style", "[NUMBER]",
					 "LodgName", "LodgNumber", "EngaName", "EngaNumber", "ChouName", 
					 "ChouNumber", "[HABITUS]", "Ck_Habitus", "Ck_Minute", "[PARTTIME]", 
					 "In_Room", "Out_Room1", "Out_Room2", "InsuDay", "ClockRoom1", 
					 "ClockRoom2", "InsuHour1", "InsuHour2", "[JDBC]", "DBFname", 
					 "UserID", "Password", "IP", "Access", "[ODBC]", "LinkName" };
	
	
	
	/**=======================================================================**
	 *		[## public NetSetup (JFrame frame) {} ]:		构造函数
	 *			参数   ：JFrame 表示本对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组建系统设定对话框
	 **=======================================================================**
	 */
	public NetSetup (JFrame frame) {
		super (frame, "网络设置", true);
		
		bt1 = new TJButton ("pic/save.gif", " 保  存 ", "保存当前配置信息");
		bt2 = new TJButton ("pic/exit.gif", " 返  回 ", "放弃修改");
		bt3 = new TJButton ("pic/recall.gif", " 测  试 ", "以当前配置连接数库");
		bt4 = new TJButton ("pic/recall.gif", " 测  试 ", "以当前配置连接数库");
		tp	= new JTabbedPane ();
		stMain = new JPanel (new BorderLayout ());
		bp     = new JPanel (new FlowLayout (FlowLayout.RIGHT, 10, 8));
		 
		bp.add (bt1);
		bp.add (bt2);
		bt1.setEnabled (false);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
		bt3.addMouseListener(this);
		bt4.addMouseListener(this);
		
		//制作标签栏
		sys = buildSYS ();
		dba = buildDBA ();
		tp.addTab ("参数设置", new ImageIcon ("pic/u05.gif"), sys);
		tp.addTab ("JDBC连接设置", new ImageIcon ("pic/setup.gif"), dba);
		
		//主面板
		stMain.add ("Center", tp);
		stMain.add ("South", bp);
		
		//根据INI文件初起化默认值
		setupInit(2);				//第一次初始化默认值时，参数为2
		
		this.setContentPane (stMain);
		pack ();
		this.setMinimumSize (new Dimension (579, 276));
		sunswing.setWindowCenter(this);
	}
	
	/**=======================================================================**
	 *		[## private JPanel buildDBA() {} ]:			制作标签栏DBA
	 *			参数   ：无
	 *			返回值 ：返回一个JPanel对象
	 *			修饰符 ：private
	 *			功能   ：组建数据库面板
	 **=======================================================================**
	 */
	private JPanel buildDBA() {
		JLabel lb1, lb2, lb3, lb4, lb5;
		JPanel bd, dnet, dbf;
		JPanel jp1, jp2, jp3, jp4, jp5, jp6;
		
		lb1 = new JLabel (" 服 务 器 IP :");
		lb2 = new JLabel ("服务器端口:");
		lb3 = new JLabel ("数据库名称:");
		lb4 = new JLabel ("登录用户名:");
		lb5 = new JLabel ("登 录 密 码 :");
		
		tf1 = new TJTextField (15);
		tf2 = new TJTextField (15);
		tf3 = new TJTextField (15);
		tf4 = new TJTextField (15);
		tf5 = new TJTextField (15);
		tf6 = new TJTextField ("测试结果：未测试 ...", 15);
		tf6.setEditable(false);			//设置测试文本框不可编辑
		
		bd = new JPanel (new GridLayout(1, 2));
		dnet = new JPanel (new GridLayout(3, 1));
		dbf	 = new JPanel (new GridLayout(3, 1));
		
		jp1 = new JPanel ();
		jp2 = new JPanel ();
		jp3 = new JPanel ();
		jp4 = new JPanel ();
		jp5 = new JPanel ();
		jp6 = new JPanel ();
		
		//加键盘监听
		tf1.addKeyListener (this);
		tf2.addKeyListener (this);
		tf3.addKeyListener (this);
		tf4.addKeyListener (this);
		tf5.addKeyListener (this);
		
		//数据库登录设置面板
		jp1.add (lb3);
		jp1.add (tf3);
		jp2.add (lb4);
		jp2.add (tf4);
		jp3.add (lb5);
		jp3.add (tf5);
		dbf.add (jp1);
		dbf.add (jp2);
		dbf.add (jp3);
		
		//网络连接设置面板
		jp4.add (lb1);
		jp4.add (tf1);
		jp5.add (lb2);
		jp5.add (tf2);
		jp6.add (tf6);
		jp6.add (bt3);
		dnet.add (jp4);
		dnet.add (jp5);
		dnet.add (jp6);
		
		//加标题框
		dbf.setBorder (BorderFactory.createTitledBorder ("数据库登录设置" ));
		dnet.setBorder (BorderFactory.createTitledBorder ("网络连接设置" ));
		
		bd.add (dbf);
		bd.add (dnet);
		return bd;		//返回一个JPanel
	}
	
	/**=======================================================================**
	 *		[## private JPanel buildSYS() {} ]:			制作标签栏SYS
	 *			参数   ：无
	 *			返回值 ：返回一个JPanel对象
	 *			修饰符 ：private
	 *			功能   ：组建系统设置面板
	 **=======================================================================**
	 */
	private JPanel buildSYS() {
		JLabel lb1, lb2, lb3;
		JPanel bs, lt, rt, jp1, jp2, jp3, jp4, jp5, jp6;
		
		rb1 = new JRadioButton ("Windwos 系统风格");
		rb2 = new JRadioButton ("JAVA 默认风格");
		rb3 = new JRadioButton ("JAVA 金属风格");
		bg	= new ButtonGroup ();
		lb1 = new JLabel ("连 接 方 式 :");
		lb2 = new JLabel ("数据源名称:");
		lb3 = new JLabel ("系 统 起 始 页 为 :");
		tf7 = new TJTextField (15);
		tf8 = new TJTextField ("测试结果：未测试 ...", 14);
		cb1	= new JComboBox ();
		cb2	= new JComboBox ();
		tf8.setEditable (false);
		
		bs	= new JPanel (new GridLayout(1, 2));
		lt	= new JPanel (new BorderLayout());
		rt	= new JPanel (new BorderLayout());
		jp1 = new JPanel ();
		jp2	= new JPanel (new GridLayout(2, 1));
		jp3	= new JPanel ();
		jp4	= new JPanel ();
		jp5	= new JPanel ();
		jp6	= new JPanel (new GridLayout(3, 1));
		
		bg.add (rb1);
		bg.add (rb2);
		bg.add (rb3);
		cb1.addItem ("  ODBC 连 接                      ");
		cb1.addItem ("  JDBC 连 接                      ");
		cb2.addItem ("  标 准 单 人 间          ");
		cb2.addItem ("  标 准 双 人 间          ");
		
		//加事件监听
		rb1.addActionListener (this);
		rb2.addActionListener (this);
		rb3.addActionListener (this);
		cb1.addItemListener (this);
		cb2.addItemListener (this);
		tf7.addKeyListener (this);
		
		//左边面板
		jp1.add (lb1);
		jp1.add (cb1);
		jp3.add (lb2);
		jp3.add (tf7);
		jp4.add (tf8);
		jp4.add (bt4);
		jp2.add (jp3);
		jp2.add (jp4);
		lt.add ("North", jp1);
		lt.add ("Center", jp2);
		
		//右边面板
		jp5.add (lb3);
		jp5.add (cb2);
		jp6.add (rb1);
		jp6.add (rb2);
		jp6.add (rb3);
		rt.add ("North", jp5);
		rt.add ("Center", jp6);
		
		bs.add (lt);
		bs.add (rt);
		
		//加标题框
		jp1.setBorder (BorderFactory.createTitledBorder ("数据库连接方式"));
		jp2.setBorder (BorderFactory.createTitledBorder ("ODBC连接设置"));
		jp5.setBorder (BorderFactory.createTitledBorder ("窗口起始页"));
		jp6.setBorder (BorderFactory.createTitledBorder ("系统外观设置"));
		
		return bs;		//返回一个JPanel
	}
	
	/**=======================================================================**
	 *		[## private void setupInit(int fg) {} ]:	根据INI文件初起化默认值
	 *			参数   ：int fg变量是一个标志，
	 *					 取值0、1的时候为连接方式事件调用的刷新;
	 *					 取值2的时候为通过INI文件初起化各组件的默认值。
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：设置本对话框各组件的默认值
	 **=======================================================================**
	 */
	private void setupInit(int fg) {
		int link = 0;			//取值 0，1
		int page = 0;			//取值 0，1
		char style = '2';		//取值 0，1，2
		tf1.setText (sunini.getIniKey (ini[31]));
		tf2.setText (sunini.getIniKey (ini[32]));
		tf3.setText (sunini.getIniKey (ini[28]));
		tf4.setText (sunini.getIniKey (ini[29]));
		tf5.setText (sunini.getIniKey (ini[30]));
		tf7.setText (sunini.getIniKey (ini[34]));
		//风格默认设定
		style = sunini.getIniKey (ini[7]).charAt (0);
		switch(style) {
			case '0':
				rb1.setSelected (true);
				break;
			case '1':
				rb2.setSelected (true);
				break;
			case '2':
				rb3.setSelected ( true );
				break;
		}
		
		//连接方式默认值设定
		if(fg == 2) {
			link = Integer.parseInt (sunini.getIniKey (ini[5]));
			cb1.setSelectedIndex (link);
		}
		else {	link = fg;	}

		//起始页默认设定
		page = Integer.parseInt (sunini.getIniKey (ini[6]));
		cb2.setSelectedIndex (page);
		
		if(link == 0) {
			tf1.setEditable (false);
			tf2.setEditable (false);
			tf3.setEditable (false);
			tf4.setEditable (false);
			tf5.setEditable (false);
			tf7.setEditable (true);
			bt3.setEnabled (false);
			bt4.setEnabled (true);
		}
		else {
			tf1.setEditable (true);
			tf2.setEditable (true);
			tf3.setEditable (true);
			tf4.setEditable (true);
			tf5.setEditable (true);
			tf7.setEditable (false);
			bt3.setEnabled (true);
			bt4.setEnabled (false);
		}
	}
	
	//
	/**=======================================================================**
	 *		[## private void ceShi() {} ]:				测试连接
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：测试对话框里的设置能否正常连接数据库
	 **=======================================================================**
	 */
	private void ceShi() {
		int flag = cb1.getSelectedIndex();
		try {
			Statement ste = null;
			if(flag == 1) {		//JDBC连接方式
				String user = tf4.getText();
				String pwd  = tf5.getText();
				String ip   = tf1.getText();
				String acc  = tf2.getText();
				String dbf  = tf3.getText();
				//String url  = "jdbc:microsoft:sqlserver://" + ip + ":" + acc + ";" + "databasename=" + dbf;
				String url  = "jdbc:mysql://" + ip + ":" + acc + "/" + dbf +"?useUnicode=true&characterEncoding=utf-8";
				//注册驱动
				//DriverManager.registerDriver (new com.microsoft.jdbc.sqlserver.SQLServerDriver());
				//获得一个连接
				Connection conn = DriverManager.getConnection (url, user, pwd);
				//建立高级载体
				ste = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				tf6.setText ("测试结果: 成功连接到服务器");
				ste.close();
				conn.close();
				//将INI键值存入缓冲区
				sunini.setIniKey(ini[31], ip);
				sunini.setIniKey(ini[32], acc);
				sunini.setIniKey(ini[28], dbf);
				sunini.setIniKey(ini[29], user);
				sunini.setIniKey(ini[30], pwd);
			}
			else {
				//注册驱动										//JDBCODBC连接方式
				//DriverManager.registerDriver (new sun.jdbc.odbc.JdbcOdbcDriver());
				//获得一个连接
				Connection conn = DriverManager.getConnection ("jdbc:odbc:" + tf7.getText());
				//建立高级载体
				ste = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				tf8.setText ("测试结果: 成功连接到服务器");
				ste.close();
				conn.close();
				//将INI键值存入缓冲区
				sunini.setIniKey(ini[34], tf7.getText());
			}//End if(flag == 1)
			sunini.setIniKey(ini[5], cb1.getSelectedIndex()+"");	//保存连接方式设置
			sunini.setIniKey(ini[6], cb2.getSelectedIndex()+"");	//保存起始页
			bt1.setEnabled ( true );
	    }
	    catch (Exception ex) {
	    	if(flag == 1)
	    		tf6.setText ("测试结果：  连接失败 ...");
	    	else
	    		tf8.setText ("测试结果：  连接失败 ...");
	    	bt1.setEnabled (false);
	    	JOptionPane.showMessageDialog (null, "无法连接到服务器，请检查参数配置与网络连接 ...", "错误", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed (ActionEvent ae) {
		Object o = ae.getSource();
		if(o == rb1)
			sunini.setIniKey(ini[7], "0");				//设置外观为Windows 风格
		else if(o == rb2)
				 sunini.setIniKey(ini[7], "1");			//设置外观为JAVA 默认风格
			 else if(o == rb3)
			 		  sunini.setIniKey(ini[7], "2");	//设置外观为JAVA 金属风格
			 	  else if(o == bt1) {
			 	  		   int fee = JOptionPane.showConfirmDialog(null, "是否要保存当前设置 ？", "提示", JOptionPane.YES_NO_OPTION);
			 	  		   if(fee == JOptionPane.YES_OPTION) {
			 	  		   	   sunini.saveIni(ini);				//保存INI文件
			 	  		   	   JOptionPane.showMessageDialog(null, "您已更改了系统设置，必须重新登录系统 ...", "提示", JOptionPane.INFORMATION_MESSAGE);
			 	  		   	   System.exit(0);
			 	  		   }//Ene if(fee == JOptionPane.YES_OPTION)
			 	  		   else
			 	  		       bt1.setEnabled(false);
			 	  	   }//End if(o == bt1)
			 	  	   else if(o == bt2)
			 	  	   			this.setVisible(false);			//返回
			 	  	   		else if(o == bt3 || o==bt4)
			 	  	   				 ceShi();					//测试连接
	}
	
	/**=======================================================================**
	 *			MouseListener 监听
	 **=======================================================================**
	 */
	public void mouseClicked (MouseEvent me) {
	}

	public void mousePressed (MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseEntered (MouseEvent me) {		//鼠标移进提示
		Object o = me.getSource ();
		if(o == bt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"将当前设置保存到INI配置文件中　　　　　　　　　　　　　  ");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"返回主操作界面　　　　　　　　　　　　　　　　　　　　   ");
		}else if(o == bt3) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"测试当前连接方式是否能正常连接指定的数据库　　　　　　   ");
		}else if(o == bt4) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"测试当前连接方式是否能正常连接指定的数据库　　　　　　   ");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + 
		"请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
	
	/**=======================================================================**
	 *			KeyListener 监听
	 **=======================================================================**
	 */
	public void keyPressed (KeyEvent ke) {
		//键盘按下监听
	}
	
	public void keyReleased (KeyEvent ke) {
		//键盘释放监听
		bt1.setEnabled (false);
	}
	
	public void keyTyped (KeyEvent ke) {
		//按键型监听
	}
	
	
	/**=======================================================================**
	 *			ItemListener 监听
	 **=======================================================================**
	 */
	public void itemStateChanged (ItemEvent ie) {
		if(ie.getSource() == cb1) {
			setupInit(cb1.getSelectedIndex());		//刷新控件的值
			bt1.setEnabled(false);
		}//Endif
	}
	
}