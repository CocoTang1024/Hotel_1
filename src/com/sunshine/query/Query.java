/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 营业查询模块
 *	[ 文件名      ]  : Setup.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 组织营业查询窗口及功能
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/23      1.0             董丰            创建
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
package com.sunshine.query;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import com.sunshine.sunsdk.sql.*;			//公共类库
import com.sunshine.sunsdk.system.*;
import com.sunshine.sunsdk.swing.*;
import com.sunshine.mainframe.HotelFrame;	//加载主窗口


public class Query 
extends JDialog 
implements ActionListener, MouseListener {
	
	private JLabel top;
	private JTabbedPane tp;
	private JPanel panelMain;
	
	//=========结帐单查询
	private JTextField tf11,tf12,tf13,tf1;
	private JButton bt11,bt12;
	private JCheckBox chk11,chk12;
	private JTable tb1;
	private DefaultTableModel dtm1;
	private JScrollPane sp1;
	
	//=========全部宾客信息查询
	private JTextField tf21,tf2;
	private JButton bt21,bt22,bt23;
	private JTable tb2;
	private DefaultTableModel dtm2;
	private JScrollPane sp2;
	
	//=========在店宾客消费查询
	private JTextField tf31,tf32,tf33,tf3;
	private JButton bt31,bt32;
	private JRadioButton rb31,rb32;
	private JTable tb3;
	private DefaultTableModel dtm3;
	private JScrollPane sp3;
	
	//=========离店宾客消费查询
	private JTextField tf41,tf42,tf43,tf4;
	private JButton bt41,bt42;
	private JCheckBox chk41,chk42;
	private JComboBox cb41;
	private JTable tb4;
	private DefaultTableModel dtm4;
	private JScrollPane sp4;
	
	/**=======================================================================**
	 *		[## public Query(JFrame frame) {} ]: 		构造函数
	 *			参数   ：JDialog对象表示本对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组建营业查询模块
	 **=======================================================================**
	 */
	public Query(JFrame frame) {
		super(frame,"营业查询",true);
		
		top = new JLabel();		//假空格
		panelMain = new JPanel(new BorderLayout(0,5));
		tab();					//制作系统设置项目标签面板
		addListener();			//加入事件监听
		panelMain.add("North",top);
		panelMain.add("Center",tp);
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (800,500));
		this.setMinimumSize (new Dimension (800,500));
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
		bt21.addActionListener(this);
		bt22.addActionListener(this);
		bt23.addActionListener(this);
		bt31.addActionListener(this);
		bt32.addActionListener(this);
		bt41.addActionListener(this);
		bt42.addActionListener(this);
		bt11.addMouseListener(this);		//加鼠标监听
		bt12.addMouseListener(this);
		bt21.addMouseListener(this);
		bt22.addMouseListener(this);
		bt23.addMouseListener(this);
		bt31.addMouseListener(this);
		bt32.addMouseListener(this);
		bt41.addMouseListener(this);
		bt42.addMouseListener(this);
	}
	
	/**=======================================================================**
	 *		[## private void tab() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作系统设置项目标签面板
	 **=======================================================================**
	 */
	private void tab() {
		JPanel jp1,jp2,jp3,jp4;
		///////////////////////////////////////////////-----------模块接口
		jp1 = pay();		    //结账单查询
		jp2 = allCustomer();	//全部宾客信息查询
		jp3 = stay();			//在店宾客消费查询
		jp4 = leave();			//离店宾客消费查询
		//////////////////////////////////////////////////////////////////
		tp = new JTabbedPane();
		tp.addTab("结帐单查询", new ImageIcon("pic/u04.gif"), jp1);
		tp.addTab("全部宾客信息查询", new ImageIcon("pic/u02.gif"), jp2);
		tp.addTab("在店宾客消费查询", new ImageIcon("pic/u03.gif"), jp3);
		tp.addTab("离店宾客消费查询", new ImageIcon("pic/v04.gif"), jp4);
	}
	
	/**=======================================================================**
	 *		[## private JPanel pay() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：结帐单查询
	 **=======================================================================**
	 */
	private JPanel pay() {
		
		tf11 = new TJTextField (13);
		tf12 = new TJTextField (13);
		tf13 = new TJTextField (13);
		tf1  = new JTextField ("结帐状态信息");
		tf1.setHorizontalAlignment (JTextField.CENTER);
		tf1.setBackground(new Color(199,183,143));
		tf1.setBorder(new LineBorder(new Color(87,87,47)));
		tf1.setEditable(false);
		
		bt11 = new TJButton ("pic/find.gif", "查　询", "查询结帐单信息");
		bt12 = new TJButton ("pic/b1.gif", "刷　新", "刷新结帐单信息");
		
		chk11 = new JCheckBox(" 结帐时间：");
		chk12 = new JCheckBox();
		
		dtm1 = new DefaultTableModel();
		tb1  = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		////////////////////////填写表格
		String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,b.c_name 宾客姓名,b.foregift 已收押金,a.money 实收金额,a.chk_time 结算时间,a.remark 备注 "+
						 "from checkout as a,livein as b where a.delmark = 0 and a.in_no = b.in_no";
		sunsql.initDTM(dtm1,sqlCode);
		
		JLabel lb1,lb4,lb5,lb8,lb9,lb10,lb11;
		lb1 = new JLabel("起始时间　");
		lb4 = new JLabel("　　 终止时间　");
		lb5 = new JLabel("　");
		lb8 = new JLabel("姓名/房间号/帐单号：    ");
		lb9 = new JLabel("　　");
		lb10 = new JLabel("　  ");
		lb11 = new JLabel("　");
		
		JPanel panelPay,pn,pn1,pn2,pc; 
		panelPay = new JPanel(new BorderLayout());
		pn		 = new JPanel(new GridLayout(2,1,0,0));
		pn1	     = new JPanel(new FlowLayout());
		pn2	     = new JPanel(new FlowLayout());
		pc		 = new JPanel(new BorderLayout());
		
		pn1.add(chk11);
		pn1.add(lb1);
		pn1.add(tf11);
		pn1.add(lb4);
		pn1.add(tf12);
		pn1.add(lb5);
		pn2.add(chk12);
		pn2.add(lb8);
		pn2.add(tf13);
		pn2.add(lb9);
		pn2.add(bt11);
		pn2.add(lb10);
		pn2.add(bt12);
		pn2.add(lb11);
		
		pn.add(pn1);
		pn.add(pn2);
		pn.setBorder(BorderFactory.createTitledBorder(""));
		
		pc.add("North",tf1);
		pc.add(sp1);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		panelPay.add("North",pn);
		panelPay.add(pc);
		
		return panelPay;
	}
	
	/**=======================================================================**
	 *		[## private JPanel allCustomer() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：全部宾客信息查询
	 **=======================================================================**
	 */
	private JPanel allCustomer() {
		
		tf21 = new TJTextField (10);
		tf2  = new JTextField ("所有来宾信息");
		tf2.setHorizontalAlignment (JTextField.CENTER);
		tf2.setBackground(new Color(199,183,143));
		tf2.setBorder(new LineBorder(new Color(87,87,47)));
		tf2.setEditable(false);
		
		bt21 = new TJButton ("pic/find.gif", "查询", "查询宾客信息");
		bt22 = new TJButton ("pic/b1.gif", "刷新", "刷新宾客信息");
		bt23 = new TJButton ("pic/recall.gif", "今日来宾", "今日来宾信息");
		
		dtm2 = new DefaultTableModel();
		tb2  = new JTable(dtm2);
		sp2  = new JScrollPane(tb2);
		////////////////////////填写表格
		String sqlCode = "select m_id 会员编号,r_no 房间号,c_name 宾客姓名,sex 性别,zj_type 证件类型,zj_no 证件编号,renshu 人数,foregift 押金,"+
						 "days 预住天数,statemark 当前状态,in_time 入住时间,chk_time 结帐时间,chk_no 结算单号 from livein where delmark = 0";
		sunsql.initDTM(dtm2,sqlCode);
		
		JLabel lb1,lb2,lb3,lb4;
		lb1 = new JLabel("宾客姓名/证件编号/房间号：");
		lb2 = new JLabel("      ");
		lb3 = new JLabel("   ");
		lb4 = new JLabel("   ");
		
		JPanel panelAC,pn,pc; 
		panelAC = new JPanel(new BorderLayout());
		pn	    = new JPanel();
		pc		= new JPanel(new BorderLayout());
		
		pn.add(lb1);
		pn.add(tf21);
		pn.add(lb2);
		pn.add(bt21);
		pn.add(lb3);
		pn.add(bt22);
		pn.add(lb4);
		pn.add(bt23);
		pn.setBorder(BorderFactory.createTitledBorder(""));
		
		pc.add("North",tf2);
		pc.add(sp2);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		panelAC.add("North",pn);
		panelAC.add(pc);
		
		return panelAC;
	}
	
	/**=======================================================================**
	 *		[## private JPanel stay() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：在店宾客消费查询
	 **=======================================================================**
	 */
	private JPanel stay() {
		
		tf31 = new TJTextField (13);
		tf32 = new TJTextField (13);
		tf33 = new TJTextField (13);
		tf3  = new JTextField ("在店宾客消费");
		tf3.setHorizontalAlignment (JTextField.CENTER);
		tf3.setBackground(new Color(199,183,143));
		tf3.setBorder(new LineBorder(new Color(87,87,47)));
		tf3.setEditable(false);
		
		bt31 = new TJButton ("pic/find.gif", "查　询", "查询在店宾客消费");
		bt32 = new TJButton ("pic/b1.gif", "刷　新", "刷新在店宾客消费");
		
		rb31 = new JRadioButton("入住时间：",true);
		rb32 = new JRadioButton("房  间  号：");
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb31);
		bg.add(rb32);
		
		dtm3 = new DefaultTableModel();
		tb3  = new JTable(dtm3);
		sp3  = new JScrollPane(tb3);
		////////////////////////填写表格
		String sqlCode = "select a.r_no 房间号,b.r_type 房间类型,b.price 单价,c.discount 折扣比例,c.dis_price 折后单价,(c.price - c.dis_price) 优惠金额,a.in_time 入住时间 "+
						 "from livein as a,roomtype as b,customertype as c where a.statemark = '正在消费' and a.delmark = 0 and a.r_type_id = b.id and a.c_type_id = c.id and a.r_type_id = c.dis_attr";
		sunsql.initDTM(dtm3,sqlCode);
		
		JLabel lb1,lb4,lb8,lb9;
		lb1 = new JLabel("起始时间　");
		lb4 = new JLabel("　     　终止时间　");
		lb8 = new JLabel(" 　  　  　  　   ");
		lb9 = new JLabel("　  　   ");
		
		JPanel panelStay,pn,pn1,pn2,pc; 
		panelStay = new JPanel(new BorderLayout());
		pn		  = new JPanel(new GridLayout(2,1,0,0));
		pn1	      = new JPanel();
		pn2	      = new JPanel();
		pc		  = new JPanel(new BorderLayout());
		
		pn1.add(rb31);
		pn1.add(lb1);
		pn1.add(tf31);
		pn1.add(lb4);
		pn1.add(tf32);
		pn2.add(rb32);
		pn2.add(tf33);
		pn2.add(lb8);
		pn2.add(bt31);
		pn2.add(lb9);
		pn2.add(bt32);
		
		pn.add(pn1);
		pn.add(pn2);
		pn.setBorder(BorderFactory.createTitledBorder(""));
		
		pc.add("North",tf3);
		pc.add(sp3);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		panelStay.add("North",pn);
		panelStay.add(pc);
		
		return panelStay;
	}
	
	/**=======================================================================**
	 *		[## private JPanel leave() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：离店宾客消费查询
	 **=======================================================================**
	 */
	private JPanel leave() {
		
		tf41 = new TJTextField (13);
		tf42 = new TJTextField (13);
		tf43 = new TJTextField (7);
		tf4  = new JTextField ("离店宾客消费");
		tf4.setHorizontalAlignment (JTextField.CENTER);
		tf4.setBackground(new Color(199,183,143));
		tf4.setBorder(new LineBorder(new Color(87,87,47)));
		tf3.setEditable(false);
		
		bt41 = new TJButton ("pic/find.gif", "查　询", "查询离店宾客消费");
		bt42 = new TJButton ("pic/b1.gif", "刷　新", "刷新离店宾客消费");
		
		chk41 = new JCheckBox("结帐时间：");
		chk42 = new JCheckBox("查询条件：");
		cb41  = new JComboBox();
		cb41.addItem("按结帐单号");
		cb41.addItem("按房间号");
		
		dtm4 = new DefaultTableModel();
		tb4  = new JTable(dtm4);
		sp4  = new JScrollPane(tb4);
		////////////////////////填写表格
		String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
						 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr";
		sunsql.initDTM(dtm4,sqlCode);
		
		
		JLabel lb1,lb2,lb4,lb5,lb6,lb7,lb8,lb9,lb10;
		lb1 = new JLabel("起始时间　");
		lb4 = new JLabel(" 　　  终止时间　");
		lb5 = new JLabel("　");
		lb8 = new JLabel("    关键字：");
		lb9 = new JLabel("      ");
		lb10 = new JLabel(" ");
		lb2  = new JLabel("   ");
		
		JPanel panelLeave,pn,pn1,pn2,pc; 
		panelLeave = new JPanel(new BorderLayout());
		pn		   = new JPanel(new GridLayout(2,1,0,0));
		pn1	       = new JPanel();
		pn2	       = new JPanel();
		pc		   = new JPanel(new BorderLayout());
		
		pn1.add(chk41);
		pn1.add(lb1);
		pn1.add(tf41);
		pn1.add(lb4);
		pn1.add(tf42);
		pn1.add(lb5);
		pn2.add(chk42);
		pn2.add(cb41);
		pn2.add(lb8);
		pn2.add(tf43);
		pn2.add(lb9);
		pn2.add(bt41);
		pn2.add(lb10);
		pn2.add(bt42);
		pn2.add(lb2);
		
		pn.add(pn1);
		pn.add(pn2);
		pn.setBorder(BorderFactory.createTitledBorder(""));
		
		pc.add("North",tf4);
		pc.add(sp4);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		panelLeave.add("North",pn);
		panelLeave.add(pc);
		
		return panelLeave;
	}
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt11) {
			//========================================================结帐单查询
			if(chk11.isSelected()) {
				if(!chk12.isSelected()) {//**************只选择结帐时间查询
					String start,end;
					start = tf11.getText();
					end = tf12.getText();
					if(!suntools.isDate(start)||!suntools.isDate(end)) {
						//若日期不合法
						JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
						tf11.setText("");
						tf12.setText("");
						tf13.setText("");
						tf11.requestFocus();
					}else {//若日期合法
						start = tf11.getText()+" 00:00:00";
						end = tf12.getText()+" 23:59:59";
						String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,b.c_name 宾客姓名,b.foregift 已收押金,a.money 实收金额,a.chk_time 结算时间,a.remark 备注 "+
										 "from checkout as a,livein as b where a.delmark = 0 and a.in_no = b.in_no and a.chk_time between '"+start+"' and '"+end+"'";
						sunsql.initDTM(dtm1,sqlCode);
						tf13.setText("");
					}
				}else {                 //***************两项联合查询
					String start = tf11.getText();
					String end = tf12.getText();
					if(!suntools.isDate(start)||!suntools.isDate(end)) {
						//若日期不合法
						JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
						tf11.setText("");
						tf12.setText("");
						tf11.requestFocus();
					}else {//若日期合法
						String nrc = tf13.getText();
						String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,b.c_name 宾客姓名,b.foregift 已收押金,a.money 实收金额,a.chk_time 结算时间,a.remark 备注 "+
										 "from checkout as a,livein as b where a.delmark = 0 and a.in_no = b.in_no and  (a.chk_no like '%"+nrc+"%' or b.r_no like '%"+nrc+"%' or b.c_name like '%"+nrc+"%') and a.chk_time between '"+start+"' and '"+end+"'";
						sunsql.initDTM(dtm1,sqlCode);
					}
					
				}
			}else {
				if(!chk12.isSelected()) {//****************两项全不选择
					JOptionPane.showMessageDialog(null,"请选择查询方式!");
					tf11.setText("");
					tf12.setText("");
					tf13.setText("");
					tf11.requestFocus();
				}else {                 //*****************只选择姓名/房号/帐单号查询
					String nrc = tf13.getText();
					String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,b.c_name 宾客姓名,b.foregift 已收押金,a.money 实收金额,a.chk_time 结算时间,a.remark 备注 "+
									 "from checkout as a,livein as b where a.delmark = 0 and a.in_no = b.in_no and  (a.chk_no like '%"+nrc+"%' or b.r_no like '%"+nrc+"%' or b.c_name like '%"+nrc+"%')";
					sunsql.initDTM(dtm1,sqlCode);
					tf11.setText("");	
					tf12.setText("");	
				}
			}
		}else if(o==bt12) {
			//===========================================================结帐单刷新
			String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,b.c_name 宾客姓名,b.foregift 已收押金,a.money 实收金额,a.chk_time 结算时间,a.remark 备注 "+
							 "from checkout as a,livein as b where a.delmark = 0 and a.in_no = b.in_no";
			sunsql.initDTM(dtm1,sqlCode);
			tf11.setText("");
			tf12.setText("");
			tf13.setText("");
			chk11.setSelected(false);
			chk12.setSelected(false);
		}else if(o==bt21) {
			//===========================================================全部宾客查询
			String nzr = tf21.getText();
			String sqlCode = "select m_id 会员编号,r_no 房间号,c_name 宾客姓名,sex 性别,zj_type 证件类型,zj_no 证件编号,renshu 人数,foregift 押金,"+
							 "days 预住天数,statemark 当前状态,in_time 入住时间,chk_time 结帐时间,chk_no 结算单号 from livein where delmark = 0 and (c_name like '%"+nzr+"%' or zj_no like '%"+nzr+"%' or r_no like '%"+nzr+"%')";
			sunsql.initDTM(dtm2,sqlCode);
		}else if(o==bt22) {
			//===========================================================全部宾客刷新
			String sqlCode = "select m_id 会员编号,r_no 房间号,c_name 宾客姓名,sex 性别,zj_type 证件类型,zj_no 证件编号,renshu 人数,foregift 押金,"+
							 "days 预住天数,statemark 当前状态,in_time 入住时间,chk_time 结帐时间,chk_no 结算单号 from livein where delmark = 0";
			sunsql.initDTM(dtm2,sqlCode);
			tf21.setText("");
		}else if(o==bt23) {
			//===========================================================今日来宾
			GregorianCalendar gc = new GregorianCalendar();
			String year = gc.get (GregorianCalendar.YEAR) + "";
			//为月份补'0'
			String month = gc.get (GregorianCalendar.MONTH) + 1 + "";
			if( month.length() == 1)
				month = "0" + month;
			//为天补'0'
			String day = gc.get (GregorianCalendar.DAY_OF_MONTH) + "";
			if( day.length () == 1)
				day = "0" + day;
			String in_time = year+"-"+month+"-"+day;
			
			String nzr = tf21.getText();
			String sqlCode = "select m_id 会员编号,r_no 房间号,c_name 宾客姓名,sex 性别,zj_type 证件类型,zj_no 证件编号,renshu 人数,foregift 押金,"+
							 "days 预住天数,statemark 当前状态,in_time 入住时间,chk_time 结帐时间,chk_no 结算单号 from livein where delmark = 0 and (c_name like '%"+nzr+"%' or zj_no like '%"+nzr+"%' or r_no like '%"+nzr+"%') and in_time = '"+in_time+"'";
			sunsql.initDTM(dtm2,sqlCode);
		}else if(o==bt31) {
			//===========================================================在店宾客查询
			if(rb31.isSelected()) {//*********************按入住时间查询
				String start = tf31.getText();
				String end = tf32.getText();
				if(!suntools.isDate(start)||!suntools.isDate(end)) {
					//若日期不合法
					JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
					tf31.setText("");
					tf32.setText("");
					tf33.setText("");
					tf31.requestFocus();
				}else {//若日期合法
					start = tf31.getText()+" 00:00:00";
					end = tf32.getText()+" 23:59:59";	
					String sqlCode = "select a.r_no 房间号,b.r_type 房间类型,b.price 单价,c.discount 折扣比例,c.dis_price 折后单价,(c.price - c.dis_price) 优惠金额,a.in_time 入住时间 "+
									 "from livein as a,roomtype as b,customertype as c where a.statemark = '正在消费' and a.delmark = 0 and a.r_type_id = b.id and a.c_type_id = c.id and a.r_type_id = c.dis_attr and a.in_time between '"+start+"' and '"+end+"'";
					sunsql.initDTM(dtm3,sqlCode);
					tf33.setText("");
					}
			}else if(rb32.isSelected()) {//****************按房间号查询
				String r_no = tf33.getText();
				String sqlCode = "select a.r_no 房间号,b.r_type 房间类型,b.price 单价,c.discount 折扣比例,c.dis_price 折后单价,(c.price - c.dis_price) 优惠金额,a.in_time 入住时间 "+
								 "from livein as a,roomtype as b,customertype as c where a.statemark = '正在消费' and a.delmark = 0 and a.r_type_id = b.id and a.c_type_id = c.id and a.r_type_id = c.dis_attr and a.r_no like '%"+r_no+"%'";
				sunsql.initDTM(dtm3,sqlCode);
				tf31.setText("");
				tf32.setText("");
			}
		}else if(o==bt32) {
			//===========================================================在店宾客刷新
			String sqlCode = "select a.r_no 房间号,b.r_type 房间类型,b.price 单价,c.discount 折扣比例,c.dis_price 折后单价,(c.price - c.dis_price) 优惠金额,a.in_time 入住时间 "+
							 "from livein as a,roomtype as b,customertype as c where a.statemark = '正在消费' and a.delmark = 0 and a.r_type_id = b.id and a.c_type_id = c.id and a.r_type_id = c.dis_attr";
			sunsql.initDTM(dtm3,sqlCode);
			tf31.setText("");
			tf32.setText("");
			tf33.setText("");
			rb31.setSelected(true);
		}else if(o==bt41) {
			//===========================================================离店宾客查询
			if(chk41.isSelected()) {
				if(!chk42.isSelected()) {//**************只选择结帐时间查询
					String start = tf41.getText();
					String end = tf42.getText();
					if(!suntools.isDate(start)||!suntools.isDate(end)) {
						//若日期不合法
						JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
						tf41.setText("");
						tf42.setText("");
						tf43.setText("");
						tf41.requestFocus();
						cb41.setSelectedIndex(0);
					}else {//若日期合法
						start = tf41.getText()+" 00:00:00";
						end = tf42.getText()+" 23:59:59";
						String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
										 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr and a.chk_time between '"+start+"' and '"+end+"'";
						sunsql.initDTM(dtm4,sqlCode);
						tf43.setText("");
						chk41.setSelected(false);
						chk42.setSelected(false);
						cb41.setSelectedIndex(0);
					}
				}else {                 //***************两项联合查询
					String start = tf41.getText();
					String end = tf42.getText();
					if(!suntools.isDate(start)||!suntools.isDate(end)) {
						//若日期不合法
						JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
						tf41.setText("");
						tf42.setText("");
						tf41.requestFocus();
					}else {//若日期合法
						String nrc = tf43.getText();
						if(cb41.getSelectedIndex()==0) {//按结帐单号
							String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
											 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr and a.chk_no like '%"+nrc+"%' and a.chk_time between '"+start+"' and '"+end+"'";
							sunsql.initDTM(dtm4,sqlCode);
						}else if(cb41.getSelectedIndex()==1) {//按房间号
							String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
											 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr and b.r_no like '%"+nrc+"%' and a.chk_time between '"+start+"' and '"+end+"'";
							sunsql.initDTM(dtm4,sqlCode);
						}
					}
					
				}
			}else {
				if(!chk42.isSelected()) {//****************两项全不选择
					JOptionPane.showMessageDialog(null,"请选择查询方式!");
					tf41.setText("");
					tf42.setText("");
					tf43.setText("");
					cb41.setSelectedIndex(0);
				}else {                 //*****************只选择房号/帐单号查询
					String nrc = tf43.getText();
					if(cb41.getSelectedIndex()==0) {//按结帐单号
						String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
										 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr and a.chk_no like '%"+nrc+"%'";
						sunsql.initDTM(dtm4,sqlCode);
					}else if(cb41.getSelectedIndex()==1) {//按房间号
						String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
										 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr and b.r_no like '%"+nrc+"%'";
						sunsql.initDTM(dtm4,sqlCode);
					}
					tf41.setText("");
					tf42.setText("");
				}
			}
		}else if(o==bt42) {
			//离店宾客刷新
			String sqlCode = "select a.chk_no 帐单号,b.r_no 房间号,c.r_type 房间类型,c.price 单价,d.discount 折扣比例,d.dis_price 实收金额,(d.price - d.dis_price) 优惠金额,a.chk_time 入帐时间 "+
						 	 "from checkout as a,livein as b,roomtype as c,customertype as d where a.delmark = 0 and b.statemark = '已结算' and a.in_no = b.in_no and b.r_type_id = c.id and b.c_type_id = d.id and b.r_type_id = d.dis_attr";
			sunsql.initDTM(dtm4,sqlCode);
			tf41.setText("");
			tf42.setText("");
			tf43.setText("");
			chk41.setSelected(false);
			chk42.setSelected(false);
			cb41.setSelectedIndex(0);
		}
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
		Object o = me.getSource();
		if(o==bt11) {
			//结帐单查询
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询结帐单信息　      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt12) {
			//结帐单刷新
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新结帐单信息　      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt21) {
			//全部宾客查询
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询全部宾客信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt22) {
			//全部宾客刷新
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新全部宾客信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt23) {
			//全部宾客过滤
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询今日来宾信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt31) {
			//在店宾客查询
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询在店宾客信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt32) {
			//在店宾客刷新
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新在店宾客信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt41) {
			//离店宾客查询
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询离店宾客信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o==bt42) {
			//离店宾客刷新
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新离店宾客信息      　　　　　　　　　　　　　　　　　 　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
}