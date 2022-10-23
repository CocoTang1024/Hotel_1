/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 批量添加新房间信息窗口
 *	[ 文件名      ]  : AddRoomInfos.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 实现对新房间信息的批量添加
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.1
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
 *	[## public AddRoomInfoa(JDialog dialog) {} ]:
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
 *	[## private void saveRoomInfos() {} ]: 
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
import com.sunshine.sunsdk.sql.*;			//公共类
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.HotelFrame;	//主窗口


public class AddRoomInfos
extends JDialog 
implements ActionListener {
	
	public static JComboBox cb;
	private JTextField tf1, tf2, tf3, tf4;
	private JButton bt1, bt2;
	
	private JPanel panelMain, pc, ps;
	
	/**=======================================================================**
	 *		[## public AddRoomInfos(JDialog dialog) {} ]: 	构造函数
	 *			参数   ：JDialog为父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：批量添加新的房间信息
	 **=======================================================================**
	 */
	public AddRoomInfos(JDialog dialog) {
		super(dialog, "批量添加房间", true);
		
		JLabel lb = new JLabel();			//假空格
		panelMain = new JPanel(new BorderLayout());
		pc 		  = new JPanel();			//信息面板
		ps 		  = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 6));
		
		//制作信息面板
		buildPC();
		//制作按键面板
		buildPS();
		
		//加入组件
		panelMain.add("North", lb);
		panelMain.add("Center",pc);
		panelMain.add("South", ps);
		
		//加入事件监听
		addListener();
		
		this.setContentPane(panelMain);
		this.setPreferredSize(new Dimension(300, 298));
		this.setMinimumSize(new Dimension(300, 298));
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
		tf4.addActionListener(this);
		cb.addActionListener(this);
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
		JPanel pc1, pc2, pc3, pc4, pc5, pc6;
		JLabel lb0, lb1, lb2, lb3, lb4, lb5, lb6;
		
		lb0 = new JLabel("　  ");		//对位置用的假空格
		lb1 = new JLabel("房间类型：");
		lb2 = new JLabel("起始房号：");
		lb3 = new JLabel("终止房号：");
		lb4 = new JLabel("标记字符：");
		lb5 = new JLabel("所在区域：");
		lb6 = new JLabel("   注意：批量添加房间，房间号码位即为房间电话。");
		lb6.setForeground(new Color(255, 138, 0));

		tf1 = new TJTextField(10);		//起始房间号
		tf2 = new TJTextField(10);		//终止房间号
		tf3 = new TJTextField(10);		//标记字符
		tf4 = new TJTextField(10);		//所在区域
				
		cb = new JComboBox();
		cb.setMaximumRowCount(5);			//设置JComboBox的下拉框显示的行数
		String sql = "select r_type from roomtype where delmark = 0";
		//初始化房间类型
		sunsql.initJComboBox(cb,sql);
		
		pc1 = new JPanel();
		pc2 = new JPanel();
		pc3 = new JPanel();
		pc4 = new JPanel();
		pc5 = new JPanel();
		pc6 = new JPanel(new GridLayout(5,1));
		
		//加入组件
		pc1.add(lb1);		//房间类型
		pc1.add(cb);
		pc1.add(lb0);
		pc2.add(lb2);		//起始房号
		pc2.add(tf1);
		pc3.add(lb3);		//终止房号
		pc3.add(tf2);
		pc4.add(lb4);		//标记字符
		pc4.add(tf3);
		pc5.add(lb5);		//所在区域
		pc5.add(tf4);
		
		pc6.add(pc2);
		pc6.add(pc3);
		pc6.add(pc4);
		pc6.add(pc5);
		pc6.add(lb6);
		
		//加入主面板
		pc.add(pc1);
		pc.add(pc6);
		
		pc.setBorder (BorderFactory.createTitledBorder("批量房间参数"));
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
		if(!suntools.isNum(tf1.getText(), 4, 1000, 9999)) {
			JOptionPane.showMessageDialog(null, "[ 起始房号 ] 只能是数字，最长为4位，" +
			"范围 1000-9999 之间", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf1.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf2.getText(), 4, 1000, 9999)) {
			JOptionPane.showMessageDialog(null, "[ 终止房号 ] 只能是数字，最长为4位，" +
			"范围 1000-9999 之间", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf2.requestFocus(true);
			return false;
		}else if(tf3.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "房号的 [ 标记字符 ] 不能为空", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf3.requestFocus(true);
			return false;
		}else if(tf4.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "房间 [ 所在区域 ] 不能为空", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf4.requestFocus(true);
			return false;
		}else if(Integer.parseInt(tf1.getText()) >= Integer.parseInt(tf2.getText())) {
			JOptionPane.showMessageDialog(null, "注意 [ 起始房号 ] 必须小于 [ 终止房号 ]", "提示", 
			JOptionPane.INFORMATION_MESSAGE);
			tf1.requestFocus(true);
			return false;
		}//Endif
		return true;
	}
	
	/**=======================================================================**
	 *		[## private void saveRoomInfos() {} ]: 	
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：保存房间信息
	 **=======================================================================**
	 */
	private void saveRoomInfos() {
		if(isValidity()) {				//检测输入的数据是否合法
			//检查新房号内是否有已存在的房间
			int rooms = Integer.parseInt(tf1.getText());
			int roomn = Integer.parseInt(tf2.getText());
			long pk   = 0;								//主键
			String rnum[] = new String[roomn-rooms + 1];		//定义房号数组
			try {
				ResultSet rs = sunsql.executeQuery("select id from roomtype " +
				"where delmark=0 and r_type='" + cb.getSelectedItem() + "'");	//获得房间类型编号
				rs.next();
				String rType = rs.getString(1);				//把房间类型编号存入rType变量
				pk = sunsql.getPrimaryKey();				//主键
				for (int i = 0; i < roomn - rooms + 1; i++) {
					rnum[i] = tf3.getText() + (rooms + i);	//获得带标记的房间号
					rs = sunsql.executeQuery("select * from roominfo where " +
					"delmark=0 and id='" + rnum[i] + "'");	//去数据库检测是否合法
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "房间号码 [ " + rnum[i] + " ] 已存在，" +
						"不能完成添加，请核对房间信息", "提示", JOptionPane.INFORMATION_MESSAGE);
						tf1.requestFocus(true);
						return;
					}//Endif
					//连接SQL语句
					rnum[i] = "insert into roominfo(pk,id,r_type_id,state,location,r_tel) values(" +
					pk + ",'" + rnum[i] + "','" + rType + "','可供','" + tf4.getText() + "','" + (rooms + i) + "')";
					pk++;					//主键++，循环运算太快，容易出现重复主键，所以用++
		  		}//Endfor
		  		int temp = sunsql.runTransaction(rnum);
		  		if(temp < rnum.length) {
					JOptionPane.showMessageDialog(null, "保存新的房间基本信息失败，" +
					"请检查网络连接或联系管理员", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}//Endif
				String journal = "执行了 [ " + cb.getSelectedItem() + " ] 的批量添加--起始房号 [ " + tf3.getText() + 
				rooms + " ] 房间数量：" + (roomn - rooms + 1);
				Journal.writeJournalInfo(HotelFrame.userid, journal, Journal.TYPE_RI);//记录操作日志
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				cb.setSelectedIndex(0);
				this.setVisible(false);
		    }
		    catch (Exception ex) {
		    ex.printStackTrace();
		    //	System.out.println ("AddRoomInfos.saveRoomInfos false");
		    }//Endtry
		}//Endif
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == bt1) {
			saveRoomInfos();			//保存
		}else if(o == bt2) {
			this.setVisible(false);		//取消
		}else if(o == tf1) {
			tf2.requestFocus(true);		//起始房间号
		}else if(o == tf2) {
			tf3.requestFocus(true);		//终止房间号
		}else if(o == tf3) {
			tf4.requestFocus(true);		//标记字符
		}else if(o == tf4) {			//所在区域
			saveRoomInfos();			//保存
		}//Endif
	}
}