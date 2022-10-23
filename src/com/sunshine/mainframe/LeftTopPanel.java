/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 主窗口左边上部面板
 *	[ 文件名      ]  : ModiRoomType.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 显示时钟，指定房间状态，和便签
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/18      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :	
 *	
 *	[## public LeftTopPanel() {} ]:
 *		功能: 组件主窗口左边上部面板
 *
 *	[## private void buildHabitus () {} ]: 
 *		功能: 制作状态页
 *
 *	[## public void initRoomstate() {} ]: 
 *		功能: 初始化房间总状态
 *
 *	[## public void run() {} ]: 
 *		功能: 时钟线程
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.mainframe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;			//公共类
import com.sunshine.sunsdk.swing.*;



public class LeftTopPanel 
extends JPanel 
implements ActionListener, Runnable {
	//房间信息
	public static JLabel title0, title1, title2, line;
	public static JLabel lt[] = new JLabel[13];
	
	//时钟与时钟按键
	private JTextField lt_Clock;
	private JButton clock_Button;
	
	//便签与滚动面板
	private TJTextArea lt_ta;
	private JScrollPane lt_sp;
	
	//标签面板与面板
	private JTabbedPane lt_tp;
	private JPanel habitus, notepaper;			//状态、便签
	
	
	
	/**=======================================================================**
	 *		[## public LeftTopPanel() {} ]: 				构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组件主窗口左边上部面板
	 **=======================================================================**
	 */
	public LeftTopPanel () {
		super (new BorderLayout());
		
		lt_Clock = new JTextField();						//定义时钟
		lt_tp	 = new JTabbedPane (JTabbedPane.BOTTOM);	//标签栏

		lt_Clock.setEditable(false);						//设置时钟
		lt_Clock.setForeground(new Color (99, 130, 191));
		lt_Clock.setFont (new Font ("宋体", Font.BOLD, 13));
		lt_Clock.setHorizontalAlignment (JTextField.CENTER);
		
		//制作状态页
		buildHabitus ();
		
		//制作便签页
		buildNotepaper ();
		
		//制作标签栏
		lt_tp.addTab("状态", new ImageIcon("pic/b1.gif"), habitus);
		lt_tp.addTab("便签", new ImageIcon("pic/b2.gif"), notepaper);
		
		//初始化房间总状态
		initRoomstate();
		
		this.add("North", lt_Clock);
		this.add("Center", lt_tp);
		(new Thread(this)).start();				//启动时间线程
	}
	
	/**=======================================================================**
	 *		[## private void buildHabitus () {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作状态页
	 **=======================================================================**
	 */
	private void buildHabitus (){
		String lbText[] = { "  宾客姓名：", "  预设单价：", "  房间电话：", "  所在区域：", 
							"  进店时间：", "  已用时间：", "  已交押金：", "  应收金额：", 
							"  房间总数：", "  当前占用：", "  当前可供：", "  当前预订：", 
							"  当前停用：" };
							
		JLabel lb[] = new JLabel[13];
		JPanel jp1, jp2, jp3 ,jp4, jp5, jp6, jp7, jp8;
		
		//设置标题
		title0 = new JLabel("标准单人间: ");
		title1 = new JLabel("");
		title2 = new JLabel("房间总状态");
		line   = new JLabel(new ImageIcon("pic/line2.gif"));
		title0.setForeground(Color.red);
		title1.setForeground(Color.red);
		title2.setForeground(Color.red);
		title0.setFont (new Font ("宋体", Font.PLAIN, 14));
		title1.setFont (new Font ("宋体", Font.PLAIN, 14));
		title2.setFont (new Font ("宋体", Font.PLAIN, 14));
		
		//定义面板
		habitus = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp1 = new JPanel(new FlowLayout());
		jp2 = new JPanel(new GridLayout(8, 1));
		jp3 = new JPanel(new GridLayout(8, 1));
		jp4 = new JPanel(new GridLayout(5, 1));
		jp5 = new JPanel(new GridLayout(5, 1));
		jp6 = new JPanel(new BorderLayout());
		jp7 = new JPanel(new BorderLayout());
		jp8 = new JPanel(new GridLayout(2, 1));
		
		//初始化标签内容////////////////////////////////////程序数据显示接口////
		for (int i = 0; i < 8; i++) {			//加入第一个表
			lb[i] = new JLabel(lbText[i]);
			lt[i] = new JLabel("");
			lt[i].setForeground(Color.BLUE);
			jp2.add(lb[i]);
			jp3.add(lt[i]);
	    }
	    for (int i = 8; i < 13; i++) {			//加入第二个表
			lb[i] = new JLabel(lbText[i]);
			lt[i] = new JLabel("");
			lt[i].setForeground(Color.BLUE);
			jp4.add(lb[i]);
			jp5.add(lt[i]);
	    }
	    
		//加入组件到面板
		jp1.add(title0);
		jp1.add(title1);
		jp8.add(line);				
		jp8.add(title2);
		
		habitus.add(jp1);			//加标题
		habitus.add(jp2);			//加房间信息
		habitus.add(jp3);
		habitus.add(jp8);			//加分隔线和下面房间状态的标题
		habitus.add(jp4);			//加房间状态信息
		habitus.add(jp5);
	}
	
	/**=======================================================================**
	 *		[## private void buildNotepaper () {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作便签页
	 **=======================================================================**
	 */
	private void buildNotepaper () {
		clock_Button = new TJButton("pic/clock.gif", "便签标记时间", "加入当前时间");
		lt_ta = new TJTextArea(25, 4);
		lt_sp = new JScrollPane(lt_ta);
		
		notepaper = new JPanel(new BorderLayout());
		//设置滚动面板没有横向滚动条
		lt_sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//加件监听
		clock_Button.addActionListener(this);
		
		notepaper.add("North", clock_Button);
		notepaper.add("Center", lt_sp);
	}
	
	/**=======================================================================**
	 *		[## public void initRoomstate() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化房间总状态
	 **=======================================================================**
	 */
	public void initRoomstate() {
		try {
			ResultSet rs = sunsql.executeQuery("select count(*) from roominfo where delmark=0");
			rs.next();
			lt[8].setText(rs.getString(1));		//房间总数
			rs = sunsql.executeQuery("select count(*) from roominfo where delmark=0 and state='占用'");
			rs.next();
			lt[9].setText(rs.getString(1));
			rs = sunsql.executeQuery("select count(*) from roominfo where delmark=0 and state='可供'");
			rs.next();
			lt[10].setText(rs.getString(1));
			rs = sunsql.executeQuery("select count(*) from roominfo where delmark=0 and state='预订'");
			rs.next();
			lt[11].setText(rs.getString(1));
			rs = sunsql.executeQuery("select count(*) from roominfo where delmark=0 and state='停用'");
			rs.next();
			lt[12].setText(rs.getString(1));
	    }
	    catch (Exception ex) {
	    	System.out.println ("LeftTopPanel.initRoomstate(): false");
	    }//End try
	}
	
	/**=======================================================================**
	 *		[## public void run() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：时钟线程
	 **=======================================================================**
	 */
	public void run() {
		while( true ) {
			GregorianCalendar gc = new GregorianCalendar();
			lt_Clock.setText(gc.getTime().toLocaleString());
			try {
				Thread.sleep(500);
		    }
		    catch (Exception ex) {
		    }//End try
		}//End while
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed (ActionEvent ae) {
		if(ae.getSource() == clock_Button) {
			lt_ta.append("\n---" + lt_Clock.getText() + "---\n");
			lt_ta.requestFocus(true);
		}//Enif
	}
	
}