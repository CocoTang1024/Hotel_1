/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 添加新房间信息窗口
 *	[ 文件名      ]  : AddRoomInfo.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 实现对新房间信息的添加
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
 *	[## public AddRoomInfo(JDialog dialog) {} ]:
 *		功能: 添加新的房间信息
 *
 *	[## private void addListener() {} ]: 
 *		功能: 加事件监听
 *
 *	[## private void buildPC() {} ]: 
 *		功能: 制作信息面板
 *
 *	[## private void buildPS() {} ]:
 *		功能: 制作按键面板
 *
 *	[## private boolean isValidity() {} ]:
 *		功能: 测试用户输入的数据是否合法
 *
 *	[## private void saveRoomInfo() {} ]: 
 *		功能: 保存房间信息
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


public class AddRoomInfo
extends JDialog 
implements ActionListener {
	
	public static JComboBox cb1;
	private JTextField tf1,tf2,tf3;
	private JButton bt1,bt2;
	private JPanel panelMain,pc,ps;
	
	/**=======================================================================**
	 *		[## public AddRoomInfo(JDialog dialog) {} ]: 	构造函数
	 *			参数   ：JDialog为父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：添加新的房间信息
	 **=======================================================================**
	 */
	public AddRoomInfo(JDialog dialog) {
		super(dialog,"房间信息",true);
		
		JLabel lb = new JLabel();
		panelMain = new JPanel(new BorderLayout(0,5));
		pc 		  = new JPanel(new FlowLayout(FlowLayout.CENTER,10,5));
		ps 		  = new JPanel(new FlowLayout(FlowLayout.CENTER,30,10));
		
		buildPC();			//制作房间信息面板
		buildPS();			//制作按键面板
		
		//加入组件
		panelMain.add("North", lb);
		panelMain.add("Center",pc);
		panelMain.add("South", ps);
		
		//加事件监听
		addListener();
		
		this.setContentPane(panelMain);
		this.setPreferredSize(new Dimension(300, 235));
		this.setMinimumSize(new Dimension(300, 235));
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
		cb1.addActionListener(this);
	}
	
	/**=======================================================================**
	 *		[## private void buildPC() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作信息面板
	 **=======================================================================**
	 */
	private void buildPC() {
		JPanel pc1,pc2;
		JLabel lb1,lb2,lb3,lb4;
		
		pc1 = new JPanel(new GridLayout(4, 1, 0, 16));
		pc2 = new JPanel(new GridLayout(4, 1, 0,  8));
		
		lb1 = new JLabel("房间类型：");
		lb2 = new JLabel("房间编号：");
		lb3 = new JLabel("所在区域：");
		lb4 = new JLabel("房间电话：");
		
		cb1 = new JComboBox();				//客房类型
		cb1.setMaximumRowCount(5);			//设置JComboBox的下拉框显示的行数
		String sql = "select r_type from roomtype where delmark = 0";
		sunsql.initJComboBox(cb1, sql);
		tf1 = new TJTextField(10);			//房间编号
		tf2 = new TJTextField(10);			//所在区域
		tf3 = new TJTextField(10);			//房间电话
		
		pc1.add(lb1);
		pc1.add(lb2);
		pc1.add(lb3);
		pc1.add(lb4);
		
		pc2.add(cb1);
		pc2.add(tf1);
		pc2.add(tf2);
		pc2.add(tf3);
		
		pc.add(pc1);
		pc.add(pc2);
	}
	
	/**=======================================================================**
	 *		[## private void buildPS() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作按键面板
	 **=======================================================================**
	 */
	private void buildPS() {
		bt1 = new TJButton ("pic/save.gif", " 保  存 ", "保存房间"); 
		bt2 = new TJButton ("pic/cancel.gif", " 取  消 ", "取消操作"); 
		ps.add(bt1);
		ps.add(bt2);
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
			JOptionPane.showMessageDialog(null, " [ 房间编号 ] 不能为空", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf1.requestFocus(true);
			return false;
		}else if(tf2.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "房间 [ 所在区域 ] 不能为空", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf2.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf3.getText(), 4, 1000, 9999)) {
			JOptionPane.showMessageDialog(null, "[ 房间电话 ] 只能是数字，最长为4位，" +
			"范围 1000-9999 之间", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf3.requestFocus(true);
			return false;
		}//Endif
		return true;
	}
	
	/**=======================================================================**
	 *		[## private void saveRoomInfo() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：保存房间信息
	 **=======================================================================**
	 */
	private void saveRoomInfo() {
		if(isValidity()) {
			try {
				ResultSet rs = sunsql.executeQuery("select r_type_id from roominfo " +
				"where delmark=0 and id='" + tf1.getText() + "'");
				if(rs.next()) {			//检测新的房间编号是否存在
					JOptionPane.showMessageDialog(null, "新指定的房间编号 [ " + tf1.getText() +
					" ] 已存在，不能执行添加操作，请重新核对 ...", "提示", JOptionPane.INFORMATION_MESSAGE);
					tf1.requestFocus(true);
					return;
				}//Endif
				rs = sunsql.executeQuery("select id from roominfo " +
				"where delmark=0 and r_tel='" + tf3.getText() + "'");
				if(rs.next()) {			//检测新的房间电话号是否重复
					JOptionPane.showMessageDialog(null, "分配给新房间的电话号 [ " + tf3.getText() +
					" ] 已存在，不能执行添加操作，请重新核对 ...", "提示", JOptionPane.INFORMATION_MESSAGE);
					tf3.requestFocus(true);
					return;
				}//Endif
				rs = sunsql.executeQuery("select id from roomtype " +
				"where delmark=0 and r_type='" + cb1.getSelectedItem() + "'");
				rs.next();
				String r_ty_id = rs.getString(1);
				long pk = sunsql.getPrimaryKey();
				String sqlCode = "insert into roominfo(pk,id,r_type_id,state,location," +
								 "r_tel) values(" + pk + ",'" + tf1.getText() + "','" +
								 r_ty_id + "','可供','" + tf2.getText() + "','" + tf3.getText() + "')";
				
				int rec = sunsql.executeUpdate(sqlCode);			//将数据保存到数据库
				if(rec == 0) {
					JOptionPane.showMessageDialog(null, "保存新的房间基本信息失败，" +
					"请检查网络连接或联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
				}else {
					String journal = "添加了新的" + cb1.getSelectedItem() + "-- 房间号：[ " + tf1.getText() + " ]";
					Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_RI);//记录操作日志
					cb1.setSelectedIndex(0);		//保存成功，则将所有控件清零
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf1.requestFocus(true);
				}//Endif
		    }
		    catch (Exception ex) {
		    	ex.printStackTrace();
		    	System.out.println ("AddRoomInfo false");
		    }//End try
		}//Endif
			
	}
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == bt1) {
			saveRoomInfo();							//保存
		}else if(o == bt2) {
			this.setVisible(false);					//取消
		}else if(o == cb1) {
			tf1.requestFocus(true);					//房间类型
		}else if(o == tf1) {
			tf2.requestFocus(true);					//房间号码
		}else if(o == tf2) {
			tf3.requestFocus(true);					//所在区域
		}else if(o == tf3) {						//房间电话
			saveRoomInfo();
		}//Endif
	}
}