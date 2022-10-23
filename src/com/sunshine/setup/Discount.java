/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 修改单项折扣
 *	[ 文件名      ]  : Discount.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 实现对客户折扣的单项修改
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/19      1.0             董丰            创建
 *	2006/04/22      1.1             顾俊            实现功能
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :	
 *
 *	[## public Discount(JDialog dialog) {} ]:
 *		功能: 修改单项折扣
 *
 *	[## private void addListener() {} ]: 
 *		功能: 加事件监听
 *
 *	[## private void buildPN() {} ]: 
 *		功能: 制作北边面板
 *
 *	[## private void buildPC() {} ]: 
 *		功能: 制作中间面板
 *
 *	[## private void buildPS() {} ]: 
 *		功能: 制作南边面板
 *
 *	[## private void initDtm() {} ]:
 *		功能: 初始化DTM
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.setup;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import com.sunshine.sunsdk.sql.*;			//公共类库
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;

public class Discount 
extends JDialog 
implements ActionListener, FocusListener {
	
	public static JLabel lb1, lb2;			//定义接参变量
	public static JTextField tf1;
	public static JComboBox cb1, cb2;
	public static DefaultTableModel dtm;
	public static String rt;				//房间类型编号
	
	private JButton bt1;
	private JComboBox cb3;
	private JTable tb;
	private JScrollPane sp;
	private JPanel panelMain, pn, pc, ps;
	
	
	/**=======================================================================**
	 *		[## public Discount(JDialog dialog) {} ]: 		构造函数
	 *			参数   ：JDialog对象表示本对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：修改单项折扣
	 **=======================================================================**
	 */
	public Discount(JDialog dialog) {
		super(dialog, "单项打折设置", true);
		
		pn = new JPanel(new GridLayout(2, 2, 0, 0));
		pc = new JPanel(new BorderLayout());
		ps = new JPanel(new GridLayout(2, 1, 0, 0));
		panelMain = new JPanel(new BorderLayout(0, 0));
		
		buildPN();		//制作窗口北边面板
		buildPC();		//制作窗口中间面板
		buildPS();		//制作窗口南边面板
		
		//加入组件
		panelMain.add("North", pn);
		panelMain.add("Center",pc);
		panelMain.add("South", ps);
		
		//加事件监听
		addListener();
		
		this.setContentPane(panelMain);
		this.setPreferredSize(new Dimension(450, 360));
		this.setMinimumSize(new Dimension(450, 360));
		this.setResizable(false);			//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);		//窗口屏幕居中
	}
	
	/**=======================================================================**
	 *		[## private void addListener() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：加事件监听
	 **=======================================================================**
	 */
	private void addListener() {
		bt1.addActionListener(this);
		cb1.addActionListener(this);
		cb3.addActionListener(this);
		tf1.addFocusListener(this);
	}
	
	/**=======================================================================**
	 *		[## private void buildPN() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作北边面板
	 **=======================================================================**
	 */
	private void buildPN() {
		JPanel pn1, pn2;
		JLabel lbA, lbB, lbC, lbD;
		
		pn1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		pn2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		
		lb1 = new JLabel("豪华双人间");			//项目名称
		lb2 = new JLabel("￥500.00");			//房间单价
		lbA = new JLabel("项目名称：");
		lbB = new JLabel("　　　　　单价：");
		lbC = new JLabel("　");			//假空格
		lbD = new JLabel("　");			//假空格
		
		lbA.setFont(new Font("宋体", Font.BOLD, 15));	//设置字体大小
		lbB.setFont(new Font("宋体", Font.BOLD, 15));
		lb1.setFont(new Font("宋体", Font.PLAIN, 15));
		lb2.setFont(new Font("宋体", Font.PLAIN, 15));
		lbA.setForeground(new Color(87, 87, 47));		//设置标签前景色
		lbB.setForeground(new Color(87, 87, 47));
		lb1.setForeground(Color.RED);
		lb2.setForeground(Color.RED);
		
		//加入组件
		pn1.add(lbA);
		pn1.add(lb1);
		pn2.add(lbB);
		pn2.add(lb2);
		pn.add(lbC);
		pn.add(lbD);
		pn.add(pn1);
		pn.add(pn2);
	}
	
	/**=======================================================================**
	 *		[## private void buildPC() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作中间面板
	 **=======================================================================**
	 */
	private void buildPC() {
		JPanel pcn,pcc;
		JLabel lbA,lbB;
		
		lbA = new JLabel("普通宾客打折类型：");
		lbB = new JLabel("　　普通宾客打折比例：");
		
		cb1 = new JComboBox();
		cb1.addItem("打折");
		cb1.addItem("不打折");
		tf1 = new TJTextField(6);
		
		dtm = new DefaultTableModel();
		tb  = new JTable(dtm);
		sp  = new JScrollPane(tb);
		sp.setBorder(BorderFactory.createTitledBorder(""));
		tb.setEnabled(false);
		/////////////////////////////////公共类添加表格
		pcn = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
		pcc = new JPanel(new GridLayout(1,1));
		
		//加入组件
		pcn.add(lbA);
		pcn.add(cb1);
		pcn.add(lbB);
		pcn.add(tf1);
		pcc.add(sp);
		
		pc.add("North",pcn);
		pc.add(pcc);
		pc.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	/**=======================================================================**
	 *		[## private void buildPS() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作南边面板
	 **=======================================================================**
	 */
	private void buildPS() {
		JLabel lb1, lb2, lb3, lb4;
		JPanel bps, bp1;
		String cbItem[] = { "免费", "一折", "二折", "三折", "四折", "五折", "六折", 
							"七折", "八折", "九折", "无折扣　　　　　"};
		
		lb1 = new JLabel("       客户等级：");
		lb2 = new JLabel("       享受折扣：");
		lb3 = new JLabel("　注：打折比例　　8为八折，10为不打折，0为免费！   ");
		lb4 = new JLabel("　");
		cb2 = new JComboBox();
		cb3 = new JComboBox();
		bt1 = new TJButton ("pic/cancel.gif", "清除打折", "清除已有折扣"); 
		
		lb3.setForeground(new Color(255, 138, 0));		//设置lb3的前景色
		
		bps = new JPanel(new GridLayout(1, 5, 0, 0));
		bp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,8,6));
		
		//初如化会员等级
		sunsql.initJComboBox(cb2, "select distinct c_type from customertype where delmark = 0 and pk!=0");
		cb2.setMaximumRowCount(5);			//设置JComboBox的下拉框显示的行数
		//初始化享受折扣
		for (int i = 0; i < 11; i++) {
			cb3.addItem(cbItem[i]);
	    }
	    cb3.setMaximumRowCount(5);			//设置JComboBox的下拉框显示的行数
	    cb3.setSelectedIndex(10);			//设置为无折扣选项
		
		//加入组件
		bps.add(lb1);
		bps.add(cb2);
		bps.add(lb2);
		bps.add(cb3);
		bps.add(lb4);
		bp1.add(lb3);
		bp1.add(bt1);
		
		ps.add(bps);
		ps.add(bp1);
		
		bps.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	/**=======================================================================**
	 *		[## private void initDtm() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化DTM
	 **=======================================================================**
	 */
	private void initDtm() {
		sunsql.initDTM(Discount.dtm, "select c_type 客户等级,discount 享受折扣, " +
		"dis_price 折扣价格 from customertype where delmark=0 and dis_attr='" + 
		rt + "' and id!='SYSMARK'");
	}
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == bt1) {						//清除折扣
			int cl = JOptionPane.showConfirmDialog(null, "您 确 实 要 将所有会员的 [ " + 
			lb1.getText() + " ] 的折扣清除吗 ？","保存设置",JOptionPane.YES_NO_OPTION);
			
			if(cl == JOptionPane.YES_OPTION) {
				sunsql.executeUpdate("update customertype set discount=10 where delmark=0 " +
				"and dis_attr='" + rt + "'");
				initDtm();					//刷新表格数据
			}//Endif
		}else if(o == cb1) {
			if(cb1.getSelectedIndex() == 0) {
				tf1.setEnabled(true);
				tf1.requestFocus(true);
			}else {
				tf1.setText("10");
				tf1.setEnabled(false);
				sunsql.executeUpdate("update customertype set discount=10 where dis_attr='" + rt + "' and id='SYSMARK'");
			}
		}else if(o == cb3) {
			int dis = cb3.getSelectedIndex();
			sunsql.executeUpdate("update customertype set discount=" + dis + " where delmark=0 " +
			"and c_type='" + cb2.getSelectedItem() + "' and dis_attr='" + rt + "'");
			
				initDtm();					//刷新表格数据
		}//Endif
	}
	
	/**=======================================================================**
	 *			FocusListener 监听
	 **=======================================================================**
	 */
	public void focusGained (FocusEvent fe) {
	}
	
	public void focusLost (FocusEvent fe) {
		sunsql.executeUpdate("update customertype set discount=" + tf1.getText() + 
		" where dis_attr='" + rt + "' and id='SYSMARK'");
	}
}