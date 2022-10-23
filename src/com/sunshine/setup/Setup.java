/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 系统设置模块
 *	[ 文件名      ]  : Setup.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 组织系统设置窗口
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.1
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/19      1.0             董丰            创建
 *	2006/04/22      1.1             顾俊            实现数据保存
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :			看类内各函数开头
 *	
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.setup;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;			//公共类库
import com.sunshine.sunsdk.system.*;
import com.sunshine.sunsdk.swing.*;
import com.sunshine.mainframe.HotelFrame;	//加载主窗口


public class Setup 
extends JDialog 
implements ActionListener, MouseListener {
	
	private JLabel top,bott;
	private JTabbedPane tp;
	private JPanel panelMain;
	//=========房间项目设置
	private JTable tb11, tb12;
	private DefaultTableModel dtm11, dtm12;		//房间类型列表//房间信息列表
	 
	private JScrollPane sp11,sp12;
	private JComboBox cb11,cb12;
			    //房间类型,可供/清理状态
	private JButton bt11, bt12, bt13, bt14, bt15, bt16, bt17, bt18, bt19, bt20;
			      //添加, 修改,删除LX,折扣,单个,批量添加,删除,修改FJ,保存,筛选
	private JTextField tf11;
	//=========客户类型设置
	private JTable tb21,tb22;
	private DefaultTableModel dtm21,dtm22;
			         //客户类型列表,房间费打折列表
	private JScrollPane sp21,sp22;
	private JButton bt21, bt22, bt23, bt24;
			      //添加, 修改,删除LX,房费打折
	//=========操作员设置
	private JTable tb31;
	private DefaultTableModel dtm31;
						//操作员列表
	private JScrollPane sp31;
	private JComboBox cb31;//用户名
	private JPasswordField tf31, tf32, tf33;
			 	   		//原密码,新密码,确认密码
	private JRadioButton rb31,   rb32,   rb33,   rb34,   rb35;
				//新用户登记, 修改密码,删除用户,普通用户,管理员
	private JButton bt31, bt32, bt33;
	 		     // 登记, 修改, 删除
	//=========计费设置
	private JTextField tf41, tf42, tf43, tf44, tf45, tf46, tf47; 
	private JCheckBox ck;
	private JButton bt41, bt42;
	
	//提示信息
	String msg0 = "您确定要删除在表格中选中的资料条目吗？";
	String msg1 = "请在相应的表格中选定条目，再点删除键 ...";
	//日志信息
	String journal;
	//INI文件中的键名
	String ini[] = { "[SOFTINFO]", "UserName", "CompName", "[CONFIG]", "Soft_First",
					 "Default_Link" , "Default_Page", "Sys_style", "[NUMBER]",
					 "LodgName", "LodgNumber", "EngaName", "EngaNumber", "ChouName", 
					 "ChouNumber", "[HABITUS]", "Ck_Habitus", "Ck_Minute", "[PARTTIME]", 
					 "In_Room", "Out_Room1", "Out_Room2", "InsuDay", "ClockRoom1", 
					 "ClockRoom2", "InsuHour1", "InsuHour2", "[JDBC]", "DBFname", 
					 "UserID", "Password", "IP", "Access", "[ODBC]", "LinkName" };
	
	//实例化功能模块
	//========================================================================//
		AddRoomType			art	 = new AddRoomType (this);		//添加房间类型
		ModiRoomType		mrt  = new ModiRoomType(this);		//添加房间类型
		AddCustomerType		act  = new AddCustomerType(this); 	//添加客户类型
		ModiCustomerType	mct  = new ModiCustomerType(this);	//添加客户类型
		Discount			dis  = new Discount(this);			//折扣设置
		AddRoomInfo 		ari  = new AddRoomInfo(this);		//单个添加房间
		AddRoomInfos		aris = new AddRoomInfos(this);		//批量添加房间
		ModiRoomInfo		mri  = new ModiRoomInfo(this);		//修改房间信息
	//========================================================================//
	
	/**=======================================================================**
	 *		[## public Setup(JFrame frame) {} ]: 		构造函数
	 *			参数   ：JDialog对象表示本对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组建系统设置模块
	 **=======================================================================**
	 */
	public Setup(JFrame frame) {
		super (frame, "系统设置", true);
		top = new JLabel();		//假空格
		panelMain = new JPanel(new BorderLayout(0,10));
		tab();					//制作系统设置项目标签面板
		addListener();			//加入事件监听
		panelMain.add("North",top);
		panelMain.add("Center",tp);
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (718,508));
		this.setMinimumSize (new Dimension (718,508));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	/**=======================================================================**
	 *		[## private void addListener() {} ]: 		加事件监听
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：加事件监听
	 **=======================================================================**
	 */
	private void addListener() {
		bt11.addActionListener(this);		//加动作监听
		bt12.addActionListener(this);
		bt13.addActionListener(this);
		bt14.addActionListener(this);
		bt15.addActionListener(this);
		bt16.addActionListener(this);
		bt17.addActionListener(this);
		bt18.addActionListener(this);
		bt19.addActionListener(this);
		bt20.addActionListener(this);
		bt21.addActionListener(this);
		bt22.addActionListener(this);
		bt23.addActionListener(this);
		bt24.addActionListener(this);
		bt31.addActionListener(this);
		bt32.addActionListener(this);
		bt33.addActionListener(this);
		bt41.addActionListener(this);
		bt42.addActionListener(this);
		rb31.addActionListener(this);		//操作员作操范围监听
		rb32.addActionListener(this);
		rb33.addActionListener(this);
		tf41.addActionListener(this);		//计费设置文本框加监听
		tf42.addActionListener(this);
		tf43.addActionListener(this);
		tf44.addActionListener(this);
		tf45.addActionListener(this);
		tf46.addActionListener(this);
		bt11.addMouseListener(this);		//加鼠标监听
		bt12.addMouseListener(this);
		bt13.addMouseListener(this);
		bt14.addMouseListener(this);
		bt15.addMouseListener(this);
		bt16.addMouseListener(this);
		bt17.addMouseListener(this);
		bt18.addMouseListener(this);
		bt19.addMouseListener(this);
		bt20.addMouseListener(this);
		bt21.addMouseListener(this);
		bt22.addMouseListener(this);
		bt23.addMouseListener(this);
		bt24.addMouseListener(this);
		bt31.addMouseListener(this);
		bt32.addMouseListener(this);
		bt33.addMouseListener(this);
		bt41.addMouseListener(this);
		bt42.addMouseListener(this);
	}
	
	/**=======================================================================**
	 *		[## private void tab() {} ]: 		制作系统设置项目标签面板
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作系统设置项目标签面板
	 **=======================================================================**
	 */
	private void tab() {
		JPanel jp1,jp2,jp3,jp4;
		///////////////////////////////////////////////-------模块面板接口
		jp1 = fangjian();		//房间项目设置
		jp2 = kehu();			//客户类型设置
		jp3 = caozuo();			//操作员设置
		jp4 = jiFei();			//计费设置
		//////////////////////////////////////////////////////////////////
		tp = new JTabbedPane();
		tp.addTab("房间项目设置", new ImageIcon("pic/u01.gif"), jp1);
		tp.addTab("客户类型设置", new ImageIcon("pic/u02.gif"), jp2);
		tp.addTab("操作员设置", new ImageIcon("pic/u03.gif"), jp3);
		tp.addTab("计费设置", new ImageIcon("pic/u04.gif"), jp4);
	}
	
	/**=======================================================================**
	 *		[## private JPanel fangjian() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：房间项目设置
	 **=======================================================================**
	 */
	private JPanel fangjian() {
		
		dtm11 = new DefaultTableModel();
		tb11  = new JTable(dtm11);
		sp11  = new JScrollPane(tb11);
		dtm12 = new DefaultTableModel();
		tb12  = new JTable(dtm12);
		sp12  = new JScrollPane(tb12);
		
		JPanel pfangjian,pTop,pBott,pTn,pTc,pBn,pBc,pTcc,pTcs,pBcc,pBcs;
		pfangjian = new JPanel(new GridLayout(2,1,0,5));
		pTop	  = new JPanel(new BorderLayout());
		pBott	  = new JPanel(new BorderLayout());
		pTn		  = new JPanel();					//放置保存按钮等...
		pTc		  = new JPanel(new BorderLayout());	//放置房间类型列表及四个按钮
		pBn		  = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));//放置下拉列表
		pBc		  = new JPanel(new BorderLayout());	//放置房间信息列表及四个按钮
		pTcc	  = new JPanel(new GridLayout(1,1));//放置房间类型列表
		pTcs	  = new JPanel(new FlowLayout(FlowLayout.CENTER,20,5));//放置四个按钮
		pBcc	  = new JPanel(new GridLayout(1,1));//放置房间信息列表
		pBcs	  = new JPanel(new FlowLayout(FlowLayout.CENTER,20,5));//放置四个按钮
		
		//保存按钮等 ...
		JLabel lb1,lb2,lb3;
		lb1 = new JLabel("结帐后房间状态变为:    ");
		lb2 = new JLabel("          结帐后");
		lb3 = new JLabel("分钟后变为可供状态        ");
		tf11 = new TJTextField(sunini.getIniKey(ini[17]),5);		//根据INI文件给初值
		tf11.setHorizontalAlignment(JTextField.RIGHT);
		cb12 = new JComboBox();
		cb12.addItem("  可供状态     ");
		cb12.addItem("  清理状态     ");							//根据INI文件给初值
		cb12.setSelectedIndex(Integer.parseInt(sunini.getIniKey(ini[16])));
		bt19 = new TJButton ("pic/save.gif", "   保  存   ", "保存设置");
		pTn.add(lb1);
		pTn.add(cb12);
		pTn.add(lb2);
		pTn.add(tf11);
		pTn.add(lb3);
		pTn.add(bt19);
		pTn.setBorder(BorderFactory.createTitledBorder(""));
		
		//房间类型列表及四个按钮
		bt11 = new TJButton ("pic/new.gif", "添加类型", "添加房间类型");
		bt12 = new TJButton ("pic/modi0.gif", "修改类型", "修改房间类型");
		bt13 = new TJButton ("pic/del.gif", "删除类型", "删除房间类型");
		bt14 = new TJButton ("pic/modi3.gif", "房费打折", "设置房间费折扣");
		pTcc.add(sp11);
		pTcs.add(bt11);
		pTcs.add(bt12);
		pTcs.add(bt13);
		pTcs.add(bt14);
		pTc.add(pTcc);
		pTc.add("South",pTcs);
		pTc.setBorder(BorderFactory.createTitledBorder("房间类型"));
		
		//完成上半部分
		pTop.add("North",pTn);
		pTop.add(pTc);
		
		
		//下拉列表
		JLabel lb0 = new JLabel("按包厢类型过滤:  ");
		cb11 = new JComboBox();
		bt20 = new TJButton ("pic/choose1.gif", "筛  选", "筛选房间信息");
		bt20.setBorderPainted(false);
		bt20.setFocusPainted(false);
		pBn.add(lb0);
		pBn.add(cb11);
		pBn.add(bt20);
		
		buildDTM11();				//初始化房间类型列表和下拉列表的值
		buildDTM12("");				//初始化房间号列表
		
		
		//房间信息列表及四个按钮
		bt15 = new TJButton ("pic/new.gif", "单个添加", "添加单个房间信息");
		bt16 = new TJButton ("pic/book.gif", "批量添加", "批量添加房间信息");
		bt17 = new TJButton ("pic/del.gif", "删除房间", "删除某个房间信息");
		bt18 = new TJButton ("pic/modi0.gif", "修改房间", "修改某个房间信息");
		pBcc.add(sp12);
		pBcs.add(bt15);
		pBcs.add(bt16);
		pBcs.add(bt17);
		pBcs.add(bt18);
		pBc.add(pBcc);
		pBc.add("South",pBcs);
		pBc.setBorder ( BorderFactory.createTitledBorder ("房间信息") );
		
		//完成下半部分
		pBott.add("North",pBn);
		pBott.add(pBc);
		
		//组合
		pfangjian.add(pTop);
		pfangjian.add(pBott);
		
		return pfangjian;
	}
	
	//
	/**=======================================================================**
	 *		[## private void buildDTM11() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：房间类型列表和ComboBox
	 **=======================================================================**
	 */
	private void buildDTM11() {
		String sqlCode2 = "select pk,sysmark,id,foregift,r_type 房间类型," +
		"price 预设单价,cl_price " + "'钟点价格/小时'" + ",bed 床位数量,cl_room "+
		"'能否按钟点计费(Y/N)' from roomtype where delmark = 0";
		sunsql.initDTM(dtm11,sqlCode2);
		tb11.removeColumn(tb11.getColumn("pk"));
		tb11.removeColumn(tb11.getColumn("sysmark"));
		tb11.removeColumn(tb11.getColumn("foregift"));
		tb11.removeColumn(tb11.getColumn("id"));
		
		String sqlCode1 = "select r_type from roomtype where delmark = 0";
		sunsql.initJComboBox(cb11,sqlCode1);
		cb11.addItem("显示全部房间信息");
		cb11.setSelectedIndex(cb11.getItemCount() - 1);		//设置显示全部
	}
	
	/**=======================================================================**
	 *		[## private void buildDTM12(String rType) {} ]: 
	 *			参数   ：String rType为刷新参数
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：房间信息表
	 **=======================================================================**
	 */
	public void buildDTM12(String rType) {
		String sqlCode = "select a.pk,a.r_type_id,a.id 房间号,b.r_type 房间类型," +
		"a.state 房间状态,a.location 所在区域,a.r_tel 房间电话 from roominfo as a," +
		"roomtype as b where a.r_type_id = b.id and a.delmark = 0 " + rType;
		sunsql.initDTM(dtm12,sqlCode);
		tb12.removeColumn(tb12.getColumn("pk"));
		tb12.removeColumn(tb12.getColumn("r_type_id"));
	}
	
	/**=======================================================================**
	 *		[## private JPanel kehu() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：客户类型设置
	 **=======================================================================**
	 */
	private JPanel kehu() {
		
		dtm21 = new DefaultTableModel();
		tb21  = new JTable(dtm21);
		sp21  = new JScrollPane(tb21);
		dtm22 = new DefaultTableModel();
		tb22  = new JTable(dtm22);
		sp22  = new JScrollPane(tb22);
		
		JPanel pkehu,p1,p2,p1b,p2b;
		p1 = new JPanel(new BorderLayout());//客户类型面板
		p2 = new JPanel(new BorderLayout());//房间费打折面板
		p1b = new JPanel(new FlowLayout(FlowLayout.CENTER,30,5));//客户类型按钮面板
		p2b = new JPanel();	//房间费打折按钮面板
		
		buildDTM21();		//初始化客户类型表
		bt21 = new TJButton ("pic/new.gif", "添加类型", "添加客户类型");
		bt22 = new TJButton ("pic/modi0.gif", "修改类型", "修改客户类型");
		bt23 = new TJButton ("pic/del.gif", "删除类型", "删除客户类型");
		p1b.add(bt21);
		p1b.add(bt22);
		p1b.add(bt23);
		p1.add(sp21);
		p1.add("South",p1b);
		p1.setBorder ( BorderFactory.createTitledBorder ("客户类型") );
		
		buildDTM22();		//初始化房间打折表
		bt24 = new TJButton ("pic/modi3.gif", "   房间费打折  ", "设置房间费折扣");
		p2b.add(bt24);
		p2.add(sp22);
		p2.add("South",p2b);
		p2.setBorder ( BorderFactory.createTitledBorder ("房间费打折") );
		
		pkehu = new JPanel(new GridLayout(2,1,0,10));
		pkehu.add(p1);
		pkehu.add(p2);
		
		return pkehu;
	}
	
	/**=======================================================================**
	 *		[## private JPanel caozuo() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：操作员设置
	 **=======================================================================**
	 */
	private JPanel caozuo() {
		JPanel panelMain,panelMain1,panelTop,panelBott1,panelBott2;
		
		dtm31 = new DefaultTableModel();
		tb31  = new JTable(dtm31);
		sp31  = new JScrollPane(tb31);
		
		panelMain = new JPanel(new GridLayout(2,1,0,5));
		panelMain1 = new JPanel (new BorderLayout (0,3));		//下半面板
		panelTop   = new JPanel(new GridLayout(1,1));			//操作员列表面板
		panelBott1 = new JPanel(new GridLayout (1, 2));//详细信息,操作范围,操作权限面板
		panelBott2 = new JPanel(new FlowLayout (FlowLayout.CENTER,20,5));//按钮面板
		
		bt31 = new TJButton ("pic/new.gif", " 登  记 ", "保存当前用户信息", false);
		bt32 = new TJButton ("pic/key.gif", " 修  改 ", "修改密码", false);
		bt33 = new TJButton ("pic/del.gif", " 删  除 ", "删除当前用户", false);
		
		bt32.setEnabled(false);
		bt33.setEnabled(false);
		
		panelBott2.add (bt31);
		panelBott2.add (bt32);
		panelBott2.add (bt33);
		
		//制作并加入Top_Left面板
		panelBott1.add (bottLeft());
		
		//制作并加入Top_Right面板
		panelBott1.add (bottRight());
		
		panelMain1.add ("Center", panelBott1);
		panelMain1.add ("South", panelBott2);
		
		buildDTM31();						//初始化操作员信息表
		panelTop.add(sp31);
		panelTop.setBorder(BorderFactory.createTitledBorder("操作员列表"));
		panelMain.add(panelTop);
		panelMain.add(panelMain1);
		
		return panelMain;
	}
	
	/**=======================================================================**
	 *		[## private JPanel topLeft () {} ]: 		制作Top_Left面板
	 *			参数   ：无
	 *			返回值 ：JPanel表示组织好的面板
	 *			修饰符 ：private
	 *			功能   ：组建对话框的用户名和密码面板
	 **=======================================================================**
	 */
	private JPanel bottLeft () {
		JLabel lb1, lb2, lb3, lb4;
		JPanel tl, jp1, jp2;
		
		lb1 = new JLabel ("用  户  名:    ");
		lb2 = new JLabel ("原  密  码:    ");
		lb3 = new JLabel ("新  密  码:    ");
		lb4 = new JLabel ("确认密码:    ");
		
		tf31 = new TJPasswordField (17);
		tf32 = new TJPasswordField (17);
		tf33 = new TJPasswordField (17);
		
		
		tl	= new JPanel ();
		jp1 = new JPanel (new GridLayout (4, 1, 0, 18));
		jp2 = new JPanel (new GridLayout (4, 1, 0, 9));
		
		//初始化用户名下拉框
		cb31 = new JComboBox ();
		cb31.setEditable (true);
		
		tf31.setEditable (false);
		
		//加入组件
		jp1.add (lb1);
		jp1.add (lb2);
		jp1.add (lb3);
		jp1.add (lb4);
		jp2.add (cb31);
		jp2.add (tf31);
		jp2.add (tf32);
		jp2.add (tf33);
		
		tl.add (jp1);
		tl.add (jp2);
		tl.setBorder (BorderFactory.createTitledBorder (" 详细信息 "));
		return tl;
	}
	
	/**=======================================================================**
	 *		[## private JPanel topRight () {} ]: 		制作Top_Right面板
	 *			参数   ：无
	 *			返回值 ：JPanel表示组织好的面板
	 *			修饰符 ：private
	 *			功能   ：组建对话框操作面板
	 **=======================================================================**
	 */
	private JPanel bottRight () {
		JPanel tr, jp1, jp2;
		ButtonGroup bg1,bg2;
		
		rb31 = new JRadioButton ("新用户登记", true);
		rb32 = new JRadioButton ("修改密码");
		rb33 = new JRadioButton ("删除用户");
		rb34 = new JRadioButton ("普通操作员", true);
		rb35 = new JRadioButton ("管 理 员");
		
		bg1 = new ButtonGroup ();
		bg2 = new ButtonGroup ();
		
		tr = new JPanel (new GridLayout (2, 1));
		jp1 = new JPanel ();
		jp2 = new JPanel ();
		
		//加单选组	操作范围
		bg1.add (rb31);
		bg1.add (rb32);
		bg1.add (rb33);
		
		//加单选组	操作权限
		bg2.add (rb34);
		bg2.add (rb35);
		
		jp1.add (rb31);
		jp1.add (rb32);
		jp1.add (rb33);
		
		jp2.add (rb34);
		jp2.add (rb35);
		
		jp1.setBorder (BorderFactory.createTitledBorder (" 操作范围 "));
		jp2.setBorder (BorderFactory.createTitledBorder (" 操作权限 "));
		
		tr.add (jp1);
		tr.add (jp2);
		
		return tr;
	}
	
	/**=======================================================================**
	 *		[## private void buildDTM21() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化客户类型列表
	 **=======================================================================**
	 */
	private void buildDTM21() {
		String sqlCode = "select pk,id 客户类型编号,id,c_type 客户类型,discount " +
		"打折比率 from customertype where delmark = 0 and dis_attr = '购物折扣' and id!='SYSMARK'";
		sunsql.initDTM(dtm21,sqlCode);
		tb21.removeColumn(tb21.getColumn("pk"));
		tb21.removeColumn(tb21.getColumn("id"));
	}
	
	/**=======================================================================**
	 *		[## private void buildDTM22() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化房间打折费列表
	 **=======================================================================**
	 */
	private void buildDTM22() {
		String sqlCode = "select pk,sysmark,id,foregift,r_type 房间类型,price 预设单价 from roomtype where delmark = 0";
		sunsql.initDTM(dtm22,sqlCode);
		tb22.removeColumn(tb22.getColumn("pk"));
		tb22.removeColumn(tb22.getColumn("id"));
		tb22.removeColumn(tb22.getColumn("sysmark"));
		tb22.removeColumn(tb22.getColumn("foregift"));
	}
	

	/**=======================================================================**
	 *		[## private void buildDTM31() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化操作员列表
	 **=======================================================================**
	 */
	private void buildDTM31() {
		String sqlCode = "select pk,userid 用户登录ID,puis 用户权限 from pwd where delmark = 0";
		sunsql.initDTM(dtm31,sqlCode);
		tb31.removeColumn(tb31.getColumn("pk"));
		
		sunsql.initJComboBox (cb31, "select userid from pwd where delmark=0");
	}
	
	/**=======================================================================**
	 *		[## private JPanel jiFei() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：计费设置
	 **=======================================================================**
	 */
	private JPanel jiFei() {
		JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9, lb10, 
			   lb11, lb12, lb13, lb14, lb15, lb16;
		//定义各方位面板
		JPanel panelJF, jfTop, jfLeft, jfRight, jfBott;
		JPanel jp1, jp2, jp3, jp4, jp5, jp6, jp7, jp8, jp9;
		//定义标签
		lb1 = new JLabel("　　客人开房时间在");
		lb2 = new JLabel("点之后按新的一天开始计费");
		lb3 = new JLabel("　　客人退房时间在");
		lb4 = new JLabel("点之后计价天数自动追加半天");
		lb5 = new JLabel("　　客人退房时间在");
		lb6 = new JLabel("点之后计价天数自动追加一天");
		lb7 = new JLabel("　　开房后");
		lb8 = new JLabel("分钟开始计费");
		lb9 = new JLabel("　　最少按");
		lb10 = new JLabel("小时计费，小于这个时间的按此时间计费");
		lb11 = new JLabel("　　若不足一小时但超过");
		lb12 = new JLabel("分钟的部分按1小时计费");
		lb13 = new JLabel("　　不足上面分钟数但超过");
		lb14 = new JLabel("分钟的部分按半小时计费");
		lb15 = new JLabel("注：此设置仅限于标准计费的钟点房！　　　　");
		lb16 = new JLabel("　　");
		lb15.setForeground(new Color(255, 138, 0));
		//初始化计时计费设置
		tf41 = new TJTextField(sunini.getIniKey("In_Room"),    5);
		tf42 = new TJTextField(sunini.getIniKey("Out_Room1"),  5);
		tf43 = new TJTextField(sunini.getIniKey("Out_Room2"),  5);
		tf44 = new TJTextField(sunini.getIniKey("ClockRoom1"), 5);
		tf45 = new TJTextField(sunini.getIniKey("ClockRoom2"), 5);
		tf46 = new TJTextField(sunini.getIniKey("InsuHour1"),  5);
		tf47 = new TJTextField(sunini.getIniKey("InsuHour2"),  5);
		//设置文本框右对齐
		tf41.setHorizontalAlignment(JTextField.RIGHT);
		tf42.setHorizontalAlignment(JTextField.RIGHT);
		tf43.setHorizontalAlignment(JTextField.RIGHT);
		tf44.setHorizontalAlignment(JTextField.RIGHT);
		tf45.setHorizontalAlignment(JTextField.RIGHT);
		tf46.setHorizontalAlignment(JTextField.RIGHT);
		tf47.setHorizontalAlignment(JTextField.RIGHT);
		//不足一天是否按一天计价
		ck	 = new JCheckBox("入住时间不足一天的按一天计费");
		if(sunini.getIniKey("InsuDay").equals("1")) {
			ck.setSelected(true);
		}
		bt41 = new TJButton ("pic/save.gif", " 保  存 ", "保存当前设置");
		bt42 = new TJButton ("pic/exit.gif", " 返  回 ", "返回主窗口");
		
		panelJF = new JPanel(new BorderLayout());		//计费主面板
		jfTop	= new JPanel(new GridLayout(2, 1));		//放左右面板
		jfLeft	= new JPanel(new GridLayout(4, 1));		//计费左面板
		jfRight	= new JPanel(new GridLayout(5, 1));		//计费右面板
		jfBott	= new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 4));//按键面板
		
		jp1		= new JPanel(new FlowLayout(FlowLayout.LEFT));	//左边的面板用到的
		jp2		= new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp3		= new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp4		= new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp5		= new JPanel(new FlowLayout(FlowLayout.LEFT));	//右边的面板用到的
		jp6		= new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp7		= new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp8		= new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp9		= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		//制作左边面板
		jp1.add(lb1);
		jp1.add(tf41);
		jp1.add(lb2);
		jp2.add(lb3);
		jp2.add(tf42);
		jp2.add(lb4);
		jp3.add(lb5);
		jp3.add(tf43);
		jp3.add(lb6);
		jp4.add(lb16);				//假空格
		jp4.add(ck);
		
		jfLeft.add(jp1);
		jfLeft.add(jp2);
		jfLeft.add(jp3);
		jfLeft.add(jp4);
		
		//制作右边面板
		jp5.add(lb7);
		jp5.add(tf44);
		jp5.add(lb8);
		jp6.add(lb9);
		jp6.add(tf45);
		jp6.add(lb10);
		jp7.add(lb11);
		jp7.add(tf46);
		jp7.add(lb12);
		jp8.add(lb13);
		jp8.add(tf47);
		jp8.add(lb14);
		jp9.add(lb15);
		
		jfRight.add(jp5);
		jfRight.add(jp6);
		jfRight.add(jp7);
		jfRight.add(jp8);
		jfRight.add(jp9);
		
		//组织两个表到一起
		jfTop.add(jfLeft);
		jfTop.add(jfRight);
		
		//组织按键面板
		jfBott.add(bt41);
		jfBott.add(bt42);
		
		//加入主面板
		panelJF.add("Center", jfTop);
		panelJF.add("South", jfBott);
		
		jfLeft.setBorder(BorderFactory.createTitledBorder("普通房间标准计费"));
		jfRight.setBorder ( BorderFactory.createTitledBorder ("钟点房标准计费") );
		return panelJF;
	}
	
	/**=======================================================================**
	 *		[## private boolean initMrt() {} ]: 
	 *			参数   ：无
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：传数据给修改房间类型窗口
	 **=======================================================================**
	 */
	private boolean initMrt() {
		int row = tb11.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "请在上面类型表中指定房间类型，" +
			"才能执行修改操作", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		ModiRoomType.tf0.setText(dtm11.getValueAt(row,2) + "");		//类型编号
		ModiRoomType.tf1.setText(dtm11.getValueAt(row,4) + "");		//类型名称
		ModiRoomType.tf2.setText(dtm11.getValueAt(row,7) + "");		//床位数量
		ModiRoomType.tf3.setText(dtm11.getValueAt(row,5) + "");		//预设单价
		ModiRoomType.tf4.setText(dtm11.getValueAt(row,3) + "");		//预设押金
		ModiRoomType.tf5.setText(dtm11.getValueAt(row,6) + "");		//钟点计费
		String cl_room = dtm11.getValueAt(row, 8) + "";
		if(cl_room.equals("Y")) {
			ModiRoomType.chk.setSelected(true);			//允许提供钟点服务
		}
		else {
			ModiRoomType.chk.setSelected(false);		//不允许提供钟点服务
			ModiRoomType.tf5.setEnabled(false);			//设置钟点计费不可用
		}
		return true;
	}
	
	/**=======================================================================**
	 *		[## private boolean initMri() {} ]: 
	 *			参数   ：无
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：传数据给修改房间信息窗口
	 **=======================================================================**
	 */
	private boolean initMri() {
		int row = tb12.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "请在上面房间信息表中指定房间号，" +
			"才能执行修改操作", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		ModiRoomInfo.tf1.setText(dtm12.getValueAt(row, 2) + "");	//房间编号
		ModiRoomInfo.tf2.setText(dtm12.getValueAt(row, 5) + "");	//所在区域
		ModiRoomInfo.tf3.setText(dtm12.getValueAt(row, 6) + "");	//房间电话
		ModiRoomInfo.pk = dtm12.getValueAt(row, 0) + "";			//获得当前行的PK
		try {
			ResultSet rs = sunsql.executeQuery("select r_type from roomtype " +
			"where delmark=0 and id='" + dtm12.getValueAt(row,1) + "'");
			rs.next();
			ModiRoomInfo.cb1.setSelectedItem(rs.getString(1));		//房间类型
	    }
	    catch (Exception ex) {
	    	System.out.println ("ModiRoomInfo.initMri(): Modi false");
	    }//End try
	    return true;
	}
	
	/**=======================================================================**
	 *		[## private boolean initMct() {} ]: 
	 *			参数   ：无
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：传数据给修改客户类型窗口
	 **=======================================================================**
	 */
	private boolean initMct() {
		int row = tb21.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "请在上面类型表中指定客户类型，" +
			"才能执行修改操作", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		ModiCustomerType.tf1.setText(dtm21.getValueAt(row,1) + "");		//客户类型编号
		ModiCustomerType.tf2.setText(dtm21.getValueAt(row,3) + "");		//客户类型名称
		ModiCustomerType.tf3.setText(dtm21.getValueAt(row,4) + "");		//客户类型折扣
		ModiCustomerType.pk = dtm21.getValueAt(row, 0) + "";			//获得当前行的PK
		return true;
	}
	
	/**=======================================================================**
	 *		[## private boolean initDis(JTable dtb, DefaultTableModel ddtm) {} ]: 
	 *			参数   ：JTable 与 DefaultTableModel 为项目与折扣中的表
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：传数据给打折设置窗口
	 **=======================================================================**
	 */
	private boolean initDis(JTable dtb, DefaultTableModel ddtm) {
		int row = dtb.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "请在相应列表中指定房间类型，" +
			"才能进行打折设置操作", "提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		try {
			Discount.rt = ddtm.getValueAt(row, 2) + "";			//获得房间编号
			ResultSet rs = sunsql.executeQuery("select discount from customertype " +
			"where id='SYSMARK' and dis_attr='" + ddtm.getValueAt(row, 2) + "'");
			rs.next();
			int temp = rs.getInt(1);
			if(temp == 10){
				Discount.cb1.setSelectedIndex(1);	//普通宾客不打折选项
				Discount.tf1.setEnabled(false);
			}else {
				Discount.cb1.setSelectedIndex(0);	//普通宾客打折选项
				Discount.tf1.setEnabled(true);
			}//Endif
			Discount.tf1.setText(temp + "");		//普通宾客的折扣
	    }
	    catch (Exception ex) {
	    }
		Discount.lb1.setText(ddtm.getValueAt(row, 4) + "");	//房间类型名称
		Discount.lb2.setText(ddtm.getValueAt(row, 5) + "");	//房间价格
		//初始化表格的DTM
		sunsql.initDTM(Discount.dtm, "select c_type 客户等级,discount 享受折扣, " +
		"dis_price 折扣价格 from customertype where delmark=0 and dis_attr='" + 
		ddtm.getValueAt(row, 2) + "' and id!='SYSMARK'");
		//初始化会员等级ComboBox
		sunsql.initJComboBox(Discount.cb2, "select c_type from customertype where " + 
		"delmark=0 and id!='SYSMARK' and dis_attr='" + ddtm.getValueAt(row, 2) + "'");
		
		return true;
	}
	
	/**=======================================================================**
	 *		[## private boolean delInfo (String tName, DefaultTableModel delDtm, int dr[], String m) {} ]: 
	 *			参数   ：Sring tName	表示要执行删除的表名
	 *					 DTM delDtm		表示相关联的DTM
	 *					 int dr[]		要被执行删除的行数
	 *					 String m		提示信息
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：房间类型、房间信息和客户类型的删操作
	 **=======================================================================**
	 */
	private boolean delInfo (String tName, DefaultTableModel delDtm, int dr[], String m) {
		int rowCount = dr.length;
		int r =0;							//DTM行指针
		
		if(tName.equals ("roomtype")) {		//如果删除房间类型，则删除与房间类型相关的房间信息与客户折扣设置
			rowCount = rowCount * 3;
		}//Endif
			
		if(rowCount > 0) {					//判断选择记录数
			int isDel = JOptionPane.showConfirmDialog (null, m, "提示", JOptionPane.YES_NO_OPTION);
			if(isDel == JOptionPane.YES_OPTION) {
				String sqlCode[] = new String[rowCount];
				//生成SQL语句
				for (int i = 0; i < rowCount; i++) {
					sqlCode[i] = "update " + tName +" set delmark=1 where pk=" + delDtm.getValueAt(dr[r], 0) + " and id='" + delDtm.getValueAt(dr[r],2) + "'";
					if(tName.equals ("roomtype")) {		//如果删除房间类型，则同时删除相关房间信息
						i++;
						sqlCode[i] = "update roominfo set delmark=1 where id='sunhotel' or r_type_id='" + delDtm.getValueAt(dr[r],2) + "'";
						i++;
						sqlCode[i] = "update customertype set delmark=1 where id='sunhotel' or dis_attr='" + delDtm.getValueAt(dr[r],2) + "'";
					}
					r++;		//DTM行指针加1
			    }//Endfor
			    //以事务模式执行SQL语句组, 确保操作正确, 返回值为成功执行SQL语句的条数
			    isDel = sunsql.runTransaction(sqlCode);		
			    if(isDel != rowCount) {			//如果成功执行的条数 = 数组长度，则表示更新成功
			    	String mm = "在执行第 [ " + (isDel + 1) + " ] 条记录的删除操作时出错，数据有可能被其它终端修改\n或者是网络不通畅 ...";
			    	JOptionPane.showMessageDialog(null, mm, "错误",JOptionPane.ERROR_MESSAGE);
			    	//更新失败，返回false
			    	return false;
			    }//Endif
			    return true;		//更新成功，返回true
			}//Endif
		}
		else 						//如果没有选中记录，则提示一下
			JOptionPane.showMessageDialog(null, msg1, "提示",JOptionPane.INFORMATION_MESSAGE);
		return false;
	}
	
	/**=======================================================================**
	 *		[## private void umAdd () {} ]: 		添加操作
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：添加新的用户ID
	 **=======================================================================**
	 */
	private void umAdd () {
		String pwd1 = String.valueOf(tf32.getPassword());
		String pwd2 = String.valueOf(tf33.getPassword());
		String pu	= "普通操作员";			//用户权限
		if(!pwd1.equals (pwd2)) {			//两次密码不相等
			JOptionPane.showMessageDialog (null, "输入错误，[ 新密码 ] 与 [ 确认密码 ] " +
			"不正确，请重新输入 ...", "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}//Endif
		String umName = cb31.getEditor ().getItem () + "";//获得用户名
		try {
			ResultSet rs = sunsql.executeQuery ("select userid from pwd where delmark=0 " +
			"and userid='" + umName + "'");
			
			int isID = sunsql.recCount(rs);
			if(isID > 0){					//当前要加入的用户名是否存在
				JOptionPane.showMessageDialog (null, "管理员ID [ " + umName + " ] 已存在，" +
				"请重新输入 ...", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}//Endif
			if(rb35.isSelected())			//获得是否是管理员权限
				pu = "管理员";
			long pk = sunsql.getPrimaryKey();//从服务获得主键
			//记入数据库
			isID = sunsql.executeUpdate("insert into pwd(pk,userid,pwd,puis) values(" + pk + 
			",'" + umName + "','" + pwd1 + "','" + pu + "')");
			
			if(isID == 0) {
				JOptionPane.showMessageDialog (null, "添加操作失败，请检查网络连接是否正常 " +
				"...", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tf32.setText("");				//清空密码
			tf33.setText("");
	    }
	    catch (Exception ex) {
	    	System.out.println ("UserID.umAdd(): Add new ID error.");
	    }//End try
	}
	
	/**=======================================================================**
	 *		[## private void umUpdate (int type) {} ]: 		更新删除操作
	 *			参数   ：int 变量表示操作类型  0:表示修改密码  1:表示删除记录
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：修改密码和删除用户ID
	 **=======================================================================**
	 */
	private void umUpdate (int type) {
		String umName = cb31.getSelectedItem() + "";		//获得用户名
		String pwd0;
		String pwd1 = String.valueOf (tf32.getPassword ());
		String pwd2 = String.valueOf (tf33.getPassword ());
		
		int isID = 0;						//用户是否存在
		if(!pwd1.equals (pwd2)) {			//两次密码不相等
			JOptionPane.showMessageDialog (null, "输入错误，[ 新密码 ] 与 [ 确认密码 ] " +
			"不正确，请重新输入 ...", "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}//Endif
		if(type == 0) 
			pwd0 = String.valueOf (tf31.getPassword ());//取修改操作的密码
		else
			pwd0 = String.valueOf (tf32.getPassword ());//取删除操作的密码
		try {
			ResultSet rs = sunsql.executeQuery ("select pwd from pwd where delmark=0 and " +
			"userid='" + umName + "'");
			
			rs.next();
			if(!pwd0.equals(rs.getString(1))){					//判断原密码是否正确
				JOptionPane.showMessageDialog (null, "管理员ID [ " + umName + " ] 的 [ 原" +
				"密码 ] 不正确，请重新输入 ...", "错误",JOptionPane.ERROR_MESSAGE);
				
				return;
			}//Endif
			if(type == 0) {	//执行修改密码操作
				isID = sunsql.executeUpdate("update pwd set pwd='" + pwd1 + "' where " +
				"delmark=0 and userid='" + umName + "'");
			}else {			//执行删除操作
				isID = sunsql.executeUpdate("update pwd set delmark=1 where userid='" + umName + "'");
			}//Endif
	    }
	    catch (Exception ex) {
	    }//End try
		if(isID == 0) {				//判断操作是否成功
			JOptionPane.showMessageDialog (null, "执行操作失败，请检查网络连接是否正常 ...", "错误",JOptionPane.ERROR_MESSAGE);
		}
		tf31.setText("");				//清空密码
		tf32.setText("");
		tf33.setText("");
		return;
	}
	
	//检查计费设置是否合法
	private boolean isValidity() {
		if(!suntools.isNum(tf41.getText(), 1, 6, 9)) {
			JOptionPane.showMessageDialog(null, "[ 计费参数 1 ] 只能是数字，且范围在 6-9 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf41.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf42.getText(), 2, 11, 13)) {
			JOptionPane.showMessageDialog(null, "[ 计费参数 2 ] 只能是数字，且范围在 11-13 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf42.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf43.getText(), 2, 17, 19)) {
			JOptionPane.showMessageDialog(null, "[ 计费参数 3 ] 只能是数字，且范围在 17-19 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf43.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf44.getText(), 1, 0, 5)) {
			JOptionPane.showMessageDialog(null, "[ 钟点计费参数 1 ] 只能是数字，且范围在 0-5 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf44.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf45.getText(), 1, 1, 5)) {
			JOptionPane.showMessageDialog(null, "[ 钟点计费参数 2 ] 只能是数字，且范围在 1-5 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf45.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf46.getText(), 2, 20, 40)) {
			JOptionPane.showMessageDialog(null, "[ 钟点计费参数 3 ] 只能是数字，且范围在 20-40 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf46.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf47.getText(), 2, 5, 15)) {
			JOptionPane.showMessageDialog(null, "[ 钟点计费参数 4 ] 只能是数字，且范围在 5-15 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf47.requestFocus(true);
			return false;
		}//endif
		return true;
	}
	
	/**=======================================================================**
	 *		[## private boolean isState(int aRow[]) {} ]: 
	 *			参数   ：int aRom[] 表示要执行删除操作的行号
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：检查房间的状态是否可以删除
	 **=======================================================================**
	 */
	private boolean isState(int aRow[]) {
		int ar = aRow.length;
		ResultSet rs = null;
		String aState = "";
		try {
			for (int i = 0; i < ar; i++) {
				rs = sunsql.executeQuery("select state from roominfo where " +
				"delmark=0 and pk='" + dtm12.getValueAt(aRow[i], 0) + "'");
				rs.next();
				aState = rs.getString(1);
				if(aState.equals("占用")) {
					JOptionPane.showMessageDialog(null, "[ " + dtm12.getValueAt(aRow[i], 2) + 
					" ] 房间正处于占用状态，无法执行删除操作，系统取消了所有删除操作", "提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}else if(aState.equals("钟点")) {
					JOptionPane.showMessageDialog(null, "[ " + dtm12.getValueAt(aRow[i], 2) + 
					" ] 房间正处于钟点房状态，无法执行删除操作，系统取消了所有删除操作", "提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}else if(aState.equals("预订")) {
					JOptionPane.showMessageDialog(null, "[ " + dtm12.getValueAt(aRow[i], 2) + 
					" ] 房间正处于预订状态，无法执行删除操作，系统取消了所有删除操作", "提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}//Endif
	    	}//Endif
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	    	System.out.println ("Setup.isState(): false");
	    }//End try
	    return true;
	}
	
	
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == bt11) {//=======================================================
			art.show(true);					//添加房间类型
			buildDTM11();					//刷新表数据
			buildDTM12("");					//刷新表数据
			buildDTM22();					//刷新表数据
			
		}else if(o == bt12) {//=================================================
			if(initMrt()) {					//传数据给窗口
				mrt.show(true);				//修改房间类型
				buildDTM11();				//刷新表数据
				buildDTM12("");				//刷新表数据
				buildDTM22();				//刷新表数据
			}//Endif
			
		}else if(o == bt13) {//=================================================
				int rRow[] = tb11.getSelectedRows();			//删除房间类型
				int sysmark;
				for (int i = 0; i < rRow.length; i++) {
					//获得得记录的属性标志，并转成整型
					sysmark = Integer.parseInt(dtm11.getValueAt(rRow[i], 1) + "");
					if(sysmark == 1) {
						JOptionPane.showMessageDialog(null, "[ " + dtm11.getValueAt(rRow[i], 4) + 
						" ] 类型为系统级设置，不允许被删除，系统终止了删除操作 ...", "提示",
						JOptionPane.INFORMATION_MESSAGE);
						return;				//不执行删除返回窗口
					}//Endif
			    }//Endfor
				String msg = "注意，删除 [ 房间类型 ] 操作会将与 [ 房间类型 ] 相关" +
				"的所有 [ 房间信息 ] 一并删除。\n您确定要删除在表格中选中的类型条目吗？";
				
				if(delInfo ("roomtype", dtm11, rRow, msg)) {	//执行删除操作
					buildDTM11();			//刷新类型表数据
					buildDTM12("");			//刷新房间表数据
					buildDTM22();			//刷新类型表数据
					journal = "执行了删除房间类型的操作--删除数量：" + rRow.length;
					Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_RT);//记录操作日志
				}//Endif
				
		}else if(o == bt14) {//=================================================
			if(initDis(tb11, dtm11)) {				//房间费打折
				dis.show(true);
			}//Endif
		}else if(o == bt15) {//=================================================
			sunsql.initJComboBox(AddRoomInfo.cb1, "select r_type from roomtype where delmark = 0");
			ari.show(true);					//单个添加房间信息
			buildDTM12("");					//刷新表数据
			
		}else if(o == bt16) {//=================================================
			sunsql.initJComboBox(AddRoomInfos.cb, "select r_type from roomtype where delmark = 0");
			aris.show(true);				//批量添加房间信息
			buildDTM12("");					//刷新表数据
			
		}else if(o == bt18) {//=================================================
			sunsql.initJComboBox(ModiRoomInfo.cb1, "select r_type from roomtype where delmark = 0");
			if(initMri()) {					//传数据给窗口
				mri.show(true);				//修改房间信息
				buildDTM12("");				//刷新表数据
			}//Endif
			
		}else if(o == bt17) {//=================================================
			int rRow[] = tb12.getSelectedRows();			//删除房间信息
			if(isState(rRow)) {				//判断房间的状态是否可以删除
				if(delInfo ("roominfo", dtm12, rRow, msg0)) {	//执行删除操作
					buildDTM12("");			//刷新房间表数据
					journal = "执行了删除房间信息的操作--删除数量：" + rRow.length;
					Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_RI);//记录操作日志
				}//Endif
			}
		}else if(o == bt19) {//=================================================
			if(!suntools.isNum(tf11.getText(),2 ,5 ,30))	{	//保存退房后房间状态
				JOptionPane.showMessageDialog(null, "结算后更改房间状态的时间框只能为数字，且范围在 5 - 30 之间",
				"提示", JOptionPane.INFORMATION_MESSAGE);
				tf11.requestFocus(true);
				return;
			}
			sunini.setIniKey(ini[16], cb12.getSelectedIndex() + "");	//将设置保存至缓冲区
			sunini.setIniKey(ini[17], tf11.getText());			
			sunini.saveIni(ini);								//将缓冲区的设置保存至INI文件
			
		}else if(o == bt20) {//=================================================
				int cbIndex = cb11.getSelectedIndex();		//筛选信息
				if(cbIndex == cb11.getItemCount() - 1) {	//显示全部房间
					buildDTM12("");							//刷新房间表数据
				}else {
					String rt = "and a.r_type_id = '" + dtm11.getValueAt(cbIndex, 2) + "'";
					buildDTM12(rt);							//根据指定房间类型刷新表数据
				}//Endif
				
		}else if(o == bt21) {//=================================================
			act.show(true);					//添加客户类型
			buildDTM21();
			
		}else if(o == bt22) {//=================================================
			if(initMct()) {					//传数据给窗口
				mct.show(true);				//修改客户类型
				buildDTM21();				//刷新表数据
			}//Endif
			
		}else if(o == bt23) {//=================================================
			int rRow[] = tb21.getSelectedRows();				//删除客户类型
			if(delInfo ("customertype", dtm21, rRow, msg0)) {	//执行删除操作
				buildDTM21();			//刷新房间表数据
				journal = "执行了删除客户类型的操作--删除数量：" + rRow.length;
				Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_US);//记录操作日志
			}//Endif
			
		}else if(o == bt24) {//=================================================
			if(initDis(tb22, dtm22)) {		//房间费打折
				dis.show(true);
			}//Endif
		}else if(o == bt31) {//===================================//登记操作员信息
			if(String.valueOf(tf32.getPassword()).length() == 0) {
			 	JOptionPane.showMessageDialog(null, " [ 新密码 ] 不能为空", "提示", 
			 	JOptionPane.INFORMATION_MESSAGE);
			 	return;
			}else if(String.valueOf(tf33.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(null, " [ 确认密码 ] 不能为空", "提示", 
				JOptionPane.INFORMATION_MESSAGE);
				return;
			}//Endif
			umAdd ();					//执行添加操作
			buildDTM31();				//刷新表数据
			journal = "添加了新的操作员信息-- [ " + cb31.getEditor ().getItem () + " ]";
			Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_CZ);//记录操作日志
			
		}else if(o == bt32) {//==================================//修改操作员信息
			if(String.valueOf(tf31.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(null, " [ 新密码 ] 不能为空", "提示", 
				JOptionPane.INFORMATION_MESSAGE);
				return;
			}else if(String.valueOf(tf32.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(null, " [ 新密码 ] 不能为空", "提示", 
				JOptionPane.INFORMATION_MESSAGE);
				return;
			}else if(String.valueOf(tf33.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(null, " [ 确认密码 ] 不能为空", "提示", 
				JOptionPane.INFORMATION_MESSAGE);
				return;
			}//Endif
			umUpdate (0);				//执行修改密码操作
			journal = "修改了操作员 [ " + cb31.getEditor ().getItem () + " ] 的设置";
			Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_CZ);//记录操作日志
			
		}else if(o == bt33) {//=================================================//删除操作员信息
			if(String.valueOf(tf32.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(null, " [ 新密码 ] 不能为空", "提示", 
				JOptionPane.INFORMATION_MESSAGE);
				return;
			}else if(String.valueOf(tf33.getPassword()).length() == 0) {
				JOptionPane.showMessageDialog(null, " [ 确认密码 ] 不能为空", "提示", 
				JOptionPane.INFORMATION_MESSAGE);
				return;
			}//Endif
			int wi = JOptionPane.showConfirmDialog(null,"您确认要删除当前的用户ID吗？", 
			"提示", JOptionPane.YES_NO_OPTION);
			
			if(wi == JOptionPane.YES_OPTION) {
				umUpdate (1);			//执行删除用户ID操作
				buildDTM31();			//刷新表数据
				journal = "删除了操作员 [ " + cb31.getEditor ().getItem () + " ] 的设置";
				Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_CZ);//记录操作日志
			}//Endif
			
		}else if(o == bt41) {//=================================================
			if(isValidity()) {
				int saveJf = JOptionPane.showConfirmDialog(null, "您 确 实 要 保 存 " +
				"当 前 的 计 费 设 置 吗 ？","保存设置",JOptionPane.YES_NO_OPTION);
				if(saveJf == JOptionPane.YES_OPTION) {		//保存计费设置
					sunini.setIniKey("In_Room", tf41.getText());	//将设置保存至缓冲区
					sunini.setIniKey("Out_Room1", tf42.getText());
					sunini.setIniKey("Out_Room2", tf43.getText());
					sunini.setIniKey("ClockRoom1", tf44.getText());
					sunini.setIniKey("ClockRoom2", tf45.getText());
					sunini.setIniKey("InsuHour1", tf46.getText());
					sunini.setIniKey("InsuHour2", tf47.getText());
					if(ck.isSelected()) {					//不足一天按一天收费
						sunini.setIniKey("InsuDay","1");
					}else {
						sunini.setIniKey("InsuDay","0");
					}//Endif
					sunini.saveIni(ini);					//将缓冲区的设置保存至INI文件
					journal = "修改了系统的计费设置";
					Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_JF);//记录操作日志
				}//Endif
			}//Endif
		}else if(o == bt42) {//=================================================
			this.setVisible(false);			//返回主窗口
		}else if(o == tf41) {
			tf42.requestFocus(true);
		}else if(o == tf42) {
			tf43.requestFocus(true);
		}else if(o == tf43) {
			tf44.requestFocus(true);
		}else if(o == tf44) {
			tf45.requestFocus(true);
		}else if(o == tf45) {
			tf46.requestFocus(true);
		}else if(o == tf46) {
			tf47.requestFocus(true);
			
		}else if(o == rb31) {//=================================================
			bt31.setEnabled (true);			//操作范围--添加新操作员
			bt32.setEnabled (false);
			bt33.setEnabled (false);
			rb34.setEnabled (true);
			rb35.setEnabled (true);
			tf31.setEditable(false);
			tf32.setEditable(true);
			tf33.setEditable(true);
			cb31.setEditable (true);
			
		}else if(o == rb32) {				//操作范围--操作员修改密码
			bt31.setEnabled (false);
			bt32.setEnabled (true);
			bt33.setEnabled (false);
			rb34.setEnabled (false);
			rb35.setEnabled (false);
			tf31.setEditable(true);
			tf32.setEditable(true);
			tf33.setEditable(true);
			cb31.setEditable (false);
			
		}else if(o == rb33) {				//操作范围--删除操作员
			bt31.setEnabled (false);
			bt32.setEnabled (false);
			bt33.setEnabled (true);
			rb34.setEnabled (false);
			rb35.setEnabled (false);
			tf31.setEditable(false);
			tf32.setEditable(true);
			tf33.setEditable(true);
			cb31.setEditable (false);
		}//Endif
	}//End actionPerformed
	
	
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
		if(o == bt11) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"添加新的房间类型   　　　　　　　　　　　　　　　　　　　");
		}else if(o == bt12) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"修改指定的房间类型   　　　　　　　　　　　　　　　　　　");
		}else if(o == bt13) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除指定的房间类型   　　　　　　　　　　　　　　　　　　");
		}else if(o == bt14) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"设置指定房间类型的消费折扣   　　　　　　　　　　　　　　");
		}else if(o == bt15) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"为指定的房间类型添加新的客房   　　　　　　　　　　　　　");
		}else if(o == bt16) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"为指定的房间类型一次添加多个新客房   　　　　　　　　　　");
		}else if(o == bt17) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"修改指定客房信息设置   　　　　　　　　　　　　　　　　　");
		}else if(o == bt18) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除指定的客房   　　　　　　　　　　　　　　　　　　　　");
		}else if(o == bt19) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"保存房间退房后的状态   　　　　　　　　　　　　　　　　　");
		}else if(o == bt20) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"以左边指定的类型显示房间信息   　　　　　　　　　　　　　");
		} if(o == bt21) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"添加新的客户类型   　　　　　　　　　　　　　　　　　　　");
		}else if(o == bt22) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"修改指定的客户资料   　　　　　　　　　　　　　　　　　　");
		}else if(o == bt23) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除指定的客户资料   　　　　　　　　　　　　　　　　　　");
		}else if(o == bt24) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"设置指定房间类型的消费折扣   　　　　　　　　　　　　　　");
		}else if(o == bt31) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"添加新的操作员   　　　　　　　　　　　　　　　　　　　　");
		}else if(o == bt32) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"修改指定的操作员密码   　　　　　　　　　　　　　　　　　");
		}else if(o == bt33) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除指定的操作员   　　　　　　　　　　　　　　　　　　　");
		}else if(o == bt41) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"保存当前的计费设置至INI配置文件  　　　　　　　　　　　　");
		}else if(o == bt42) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"返回主窗口   　　　　　　　　　　　　　　　　　　　　　　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + 
		"请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
	
}