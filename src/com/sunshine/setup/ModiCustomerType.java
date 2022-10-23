/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 修改客户类型
 *	[ 文件名      ]  : ModiRoomInfo.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 实现对新客户类型的添加
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/19      1.0             董丰            创建
 *	2006/04/21      1.1             顾俊            实现功能
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :	
 *
 *	[## public ModiCustomerType(JDialog dialog) {} ]:
 *		功能: 添加新的客户类型
 *
 *	[## private void addListener() {} ]: 
 *		功能: 加事件监听
 *
 *	[## private boolean isValidity() {} ]:
 *		功能: 测试用户输入的数据是否合法
 *
 *	[## private void saveAddCustomerType() {} ]:
 *		功能: 保存客户类型
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.setup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;			//公共类库
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.HotelFrame;	//主窗口



public class ModiCustomerType 
extends JDialog 
implements ActionListener {
	
	public static JTextField tf1, tf2, tf3;
	public static String pk;
	private JButton bt1, bt2;
	
	/**=======================================================================**
	 *		[## public ModiCustomerType(JDialog dialog) {} ]: 	构造函数
	 *			参数   ：JDialog为父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：修改客户类型
	 **=======================================================================**
	 */
	public ModiCustomerType(JDialog dialog) {
		super(dialog, "客户类型", true);
		
		JLabel lb, lb1, lb2, lb4;
		JPanel panelMain, panelInfo, p1, p2, p3, p4, p5;		//定义各组件面板
		
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		p5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		p4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 6));
		panelInfo = new JPanel(new GridLayout(4, 1, 0, 0));
		panelMain = new JPanel(new BorderLayout());
		
		lb1 = new JLabel("客户类型：");
		lb2 = new JLabel("打折比例：");
		lb4 = new JLabel("类型编号：");
		lb  = new JLabel("<html>注：此打折比例仅适用于商品项目！<br>　　8为八折，10为不打折<html>");
		lb.setForeground(new Color(255, 138, 0));
		
		tf1 = new TJTextField(7);
		tf2 = new TJTextField(7);
		tf3 = new TJTextField("10", 7);
		tf1.setEnabled(false);
		
		bt1 = new TJButton ("pic/save.gif", "确定", "确定添加客户类型"); 
		bt2 = new TJButton ("pic/cancel.gif", "取消", "取消操作"); 
		
		//加入组件
		p1.add(lb1);
		p1.add(tf2);
		p2.add(lb2);
		p2.add(tf3);
		p3.add(lb);
		p4.add(bt1);
		p4.add(bt2);
		p5.add(lb4);
		p5.add(tf1);
		
		panelInfo.add(p5);
		panelInfo.add(p1);
		panelInfo.add(p2);
		panelInfo.add(p3);
		panelMain.add("Center", panelInfo);
		panelMain.add("South", p4);
		
		//加事件监听
		addListener();
		
		panelInfo.setBorder (BorderFactory.createTitledBorder("客户类型信息"));
		
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (250,255));
		this.setMinimumSize (new Dimension (250,255));
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
		bt2.addActionListener(this);
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
	}
	
	/**=======================================================================**
	 *		[## private boolean isValidity() {} ]: 	测试用户输入的数据是否合法
	 *			参数   ：无
	 *			返回值 ：boolean
	 *			修饰符 ：private
	 *			功能   ：测试用户输入的数据是否合法
	 **=======================================================================**
	 */
	private boolean isValidity() {
		if(tf1.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "客户 [ 类型编号 ] 不能为空", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf1.requestFocus(true);
			return false;
		}else if(tf2.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "客户 [ 类型名称 ] 不能为空", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf2.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf3.getText(), 2, 1, 10)) {
			JOptionPane.showMessageDialog(null, "[ 折扣比例 ] 只能是数字，且范围在 1-10 之间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf3.requestFocus(true);
			return false;
		}//Endif
		return true;
	}
	
	/**=======================================================================**
	 *		[## private void saveAddCustomerType() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：保存客户类型
	 **=======================================================================**
	 */
	private void saveAddCustomerType() {
		if(isValidity()) {					//栓测用户输入的数据是否合法
			try {
				ResultSet rs = sunsql.executeQuery("select id from customertype " +
				"where delmark=0 and c_type='" + tf2.getText() + "' and pk!=" +pk);
				if(rs.next()) {				//检测新的类型名称是否存在
					JOptionPane.showMessageDialog(null, "新的客户类型名称 [ " + tf2.getText() +
					" ] 已存在，不能执行添加操作，请重新核对 ...", "提示", JOptionPane.INFORMATION_MESSAGE);
					tf2.requestFocus(true);
					return;
				}//Endif
				String sqlCode = "update customertype set c_type='" + tf2.getText() + 
				"',discount=" + tf3.getText() + " where delmark=0 and pk=" + pk;;
				
				int rec = sunsql.executeUpdate(sqlCode);			//将数据保存到数据库
				if(rec == 0) {
					JOptionPane.showMessageDialog(null, "保存新的房间类型信息失败，" +
					"请检查网络连接或联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
				}else {
					String journal = "修改了客户类型-- [ " + tf2.getText() + " ]";
					Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_US);//记录操作日志
					this.setVisible(false);
				}//Endif
		    }
		    catch (Exception ex) {
		    	System.out.println ("ModiCustomerType false");
		    }//End try
		}//Endif
	}//End saveAddCustomerType()
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == bt1) {					//确定
			saveAddCustomerType();		//保存数据
		}else if(o == bt2) {			//取消
			this.setVisible(false);
		}else if(o == tf1) {			//客户类型
			tf2.requestFocus(true);
		}else if(o == tf2) {			//客户类型
			tf3.requestFocus(true);
		}else if(o == tf3) {			//折扣
			saveAddCustomerType();		//保存数据
		}//Endif
	}
}