/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 主窗口右边房间信息面板
 *	[ 文件名      ]  : RightTopPanel.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 以ViewList控件思想，实现房间图标化
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/23      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :		详细说明请看类内各方法开头
 *
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.mainframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;				//公共类
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;


public class RightTopPanel 
extends JPanel 
implements ActionListener, MouseListener {
	
	private JButton rtbt1, rtbt2, rtbt3;
	private JTabbedPane rtp_tb;
	
	private JPanel rjp_bott;
	//定义过滤菜单
	private JPopupMenu pm;
	private JMenuItem mi1, mi2, mi3, mi4;
	
	//保存房间类型ViewList控件面板的哈希表
	private static Hashtable ht;
	//当前标签栏页
	private String tb_Name;
	//筛选房间信息的条件	过滤显示
	private String sqlProviso = "";
	
	
	/**=======================================================================**
	 *		[## public RightTopPanel() {} ]: 				构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组建主窗口右边房间信息面板
	 **=======================================================================**
	 */
	public RightTopPanel() {
		super(new BorderLayout());
		
		//制作过滤菜单
		pm = new JPopupMenu();
		mi1 = new JMenuItem("显示可供");
		mi2 = new JMenuItem("显示停用");
		mi3 = new JMenuItem("显示占用");
		mi4 = new JMenuItem("显示预订");
		
		pm.addSeparator();	//加入菜单项
		pm.add(mi1);
		pm.add(mi2);
		pm.add(mi3);
		pm.add(mi4);
		pm.addSeparator();
		
		//存放房间类型
		ht = new Hashtable();
		//房间类型标签栏
		rtp_tb = new JTabbedPane();
		
		//制作标签栏
		buildJTabbedPane();
		
		//设置起始页
		rtp_tb.setSelectedIndex(Integer.parseInt(sunini.getIniKey("Default_Page")));
		
		//制作刷新等按键面板
		rjp_bott = buildrjp_bott();		
		
		//加事件监听
		addListener();
		
		//加入组件
		this.add("South", rjp_bott);
		this.add("Center", rtp_tb);
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
		//过滤菜单
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		mi3.addActionListener(this);
		mi4.addActionListener(this);
		//动作监听
		rtbt2.addActionListener(this);
		rtbt3.addActionListener(this);
		//鼠标监听
		rtbt1.addMouseListener (this);
		rtbt2.addMouseListener (this);
		rtbt3.addMouseListener (this);
		rtbt1.addMouseListener (this);
		rtp_tb.addMouseListener(this);
	}
	
	/**=======================================================================**
	 *		[## public void buildJTabbedPane() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作标签栏
	 **=======================================================================**
	 */
	public void buildJTabbedPane() {
		try {
			//获得所有房间类型的名称
			ResultSet rs = sunsql.executeQuery("select r_type,id from roomtype where delmark=0");
			rs.next();
			//获得所有房间类型的数量
			int roomtypeCount = sunsql.recCount(rs);
			String roomtypeName[] = new String[roomtypeCount];	//保存房间类型名数组
			String roomtypeId[]   = new String[roomtypeCount];	//保存房间类型编号数组
			
			//将结果集的信息保存到数组中
			for (int i = 0; i < roomtypeCount; i++) {
				rs.next();
				roomtypeName[i] = rs.getString(1);
				roomtypeId[i]	= rs.getString(2);
			}//Endfor
			
			String sqlCode = "";		//获得指定房间号与房间状态的SQL语句
			//初始化各房间类型面板
			for (int i = 0; i < roomtypeCount; i++) {
				
				sqlCode = "select id,state from roominfo where delmark=0 " +
				"and r_type_id='" + roomtypeId[i] + "' " + sqlProviso;
				
			    //建立一个房间类型的标签栏单页
			    JPanel jp = new JPanel(new GridLayout(1, 1));
			    //制作ViewList面板并加入jp
			    JPanel vl = buildViewList(sqlCode);
			    jp.add(vl);
			    rtp_tb.addTab(roomtypeName[i], jp);
			    
			    //将当前房间类型(ViewList控件)存入哈希表
			    ht.put(roomtypeId[i], vl);
		    }//Endfor
		    tb_Name = rtp_tb.getTitleAt(0);		//获得当前标签栏标题
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	    }//Endtry
	}
	
	/**=======================================================================**
	 *		[## public JPanel buildViewList(String sqlCode) {} ]: 
	 *			参数   ：String sqlCode对象表示从数据库中获得指定房间信息
	 *			返回值 ：JPanel
	 *			修饰符 ：public
	 *			功能   ：制作指定房间类型的ViewList控件
	 **=======================================================================**
	 */
	public JPanel buildViewList(String sqlCode) {
		ViewList vl = null;		//定义一个ViewList对象
		String picName = "";	//房间状态图片名称
		try {
			//获得指定房间类型的所有房间号码
			ResultSet vrs = sunsql.executeQuery(sqlCode);
			
			//获得指定房间类型的所有房间数量
			int flag = sunsql.recCount(vrs);
			
			//以房间的数量实例化ViewList控件
			vl = new ViewList(flag);
			
			//将所有房间加入到ViewList里面
			for (int j = 0; j < flag; j++) {
				vrs.next();
				//在ViewList中建立按键
				vl.addButton(vrs.getString(1)).addActionListener(this);
				vl.setButtonImage(vrs.getString(1), vrs.getString(2));
			}//Endfor
			
			//如果按键没有达到要求的个数，VL自动补空
			vl.remeButtons();
		    }
	    catch (Exception ex) {
	    	System.out.println ("RightTopPanel.buildViewList(): false");
	    }//Endtry
	    return vl;
	}
	
	/**=======================================================================**
	 *		[## public static JButton getViewListButton(String roomtypeID, String roominfoID) {} ]: 
	 *			参数   ：String roomtypeID对象表示按键对应房间号码的房间类型
	 *					 String roominfoID对象表示与按键对应的房间号码
	 *			返回值 ：JButton
	 *			修饰符 ：public
	 *			功能   ：获得与房间对应的按键
	 **=======================================================================**
	 */
	public static JButton getViewListButton(String roomtypeID, String roominfoID) {
		return ((ViewList)ht.get(roomtypeID)).getButton(roominfoID);
	}
	
	/**=======================================================================**
	 *		[## public static void setViewListButtonImage(String roomtypeID, String roomNum, String state) {} ]: 
	 *			参数   ：String roomtypeID对象表示按键对应房间号码的房间类型
	 *					 String roominfoID对象表示与按键对应的房间号码
	 *					 String state 对象表示房间状态
	 *			返回值 ：JButton
	 *			修饰符 ：public
	 *			功能   ：设置指定房间的状态图片
	 **=======================================================================**
	 */
	public static void setViewListButtonImage(String roomtypeID, String roomID, String state) {
		((ViewList)ht.get(roomtypeID)).setButtonImage(roomID, state);
	}
	
	
	/**=======================================================================**
	 *		[## private void sxRoominfos() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：根据 [ sqlProviso ] 指定的条件刷房间信息显示
	 **=======================================================================**
	 */
	private void sxRoominfos() {
		//得到标签栏当前页码
		int tbSelectIndex = rtp_tb.getSelectedIndex();
		//清空哈希表
		ht.clear();
		//清空标签栏
		rtp_tb.removeAll();
		//清理内存
		System.gc();
		//重建标签栏
		buildJTabbedPane();
		//设置标签栏为建立前的显示页
		rtp_tb.setSelectedIndex(tbSelectIndex);
	}
	
	/**=======================================================================**
	 *		[## private JPanel buildrjp_bott() {} ]: 
	 *			参数   ：无
	 *			返回值 ：JPanel
	 *			修饰符 ：private
	 *			功能   ：制作按键面板
	 **=======================================================================**
	 */
	private JPanel buildrjp_bott() {
		JPanel bott = new JPanel(new FlowLayout(FlowLayout.RIGHT,45,6));
		rtbt1 = new TJButton ("pic/choose.gif", "过滤状态", "显示指定状态的房间");
		rtbt2 = new TJButton ("pic/browse.gif", "显示全部", "显示所有房间");
		rtbt3 = new TJButton ("pic/refurbish.gif", "  刷   新  ", "刷新房间信息");
		
		bott.add(rtbt2);
		bott.add(rtbt1);
		bott.add(rtbt3);
		
		return bott;
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed (ActionEvent ae) {
		Object o = ae.getSource();
		if(o == rtbt2) {							//显示全部
			sqlProviso = "";
			sxRoominfos();	//重建标签栏
			return;
		}else if(o == rtbt3) {						//刷新
			sxRoominfos();	//重建标签栏
			return;
		}else if(o == mi1) {						//显示可供
			sqlProviso = "and state='可供'";
			sxRoominfos();	//重建标签栏
			return;
		}else if(o == mi2) {						//显示停用
			sqlProviso = "and state='停用'";
			sxRoominfos();	//重建标签栏
			return;
		}else if(o == mi3) {						//显示占用
			sqlProviso = "and state='占用'";
			sxRoominfos();	//重建标签栏
			return;
		}else if(o == mi4) {						//显示预订
			sqlProviso = "and state='预订'";
			sxRoominfos();	//重建标签栏
			return;
		}//Endif
		
		//刷新主窗口左边的信息
		String chooseRoomNum = ((JButton)o).getText();
		LeftTopPanel.title0.setText(tb_Name + ": ");
		LeftTopPanel.title1.setText(chooseRoomNum);
		try {
			//宾客名称，入住时间，已交押金，已用时间
			ResultSet rs = sunsql.executeQuery("select c_name,in_time,foregift " +
			"from livein where delmark=0 and statemark='正在消费' and r_no='" + chooseRoomNum + "'");
			//入住时间
			String inTime = "";
			
			if(rs.next()) {
				LeftTopPanel.lt[0].setText(rs.getString(1));
				inTime = rs.getString(2);
				LeftTopPanel.lt[4].setText(inTime.substring(0, 10));
				LeftTopPanel.lt[5].setText(suntools.getConsumeHour(inTime, Journal.getNowDTime()));
				LeftTopPanel.lt[6].setText(rs.getString(3));
			}else {
				LeftTopPanel.lt[0].setText("");
				LeftTopPanel.lt[4].setText("");
				LeftTopPanel.lt[5].setText("");
				LeftTopPanel.lt[6].setText("");
			}//Endif
			
			//房间所在区域，房间电话		顺便取出房间状态为计算是住宿还是钟点房
			rs = sunsql.executeQuery("select r_tel,location,state from roominfo where id='" + 
			chooseRoomNum + "' and delmark=0");
			if(rs.next()) {
				LeftTopPanel.lt[2].setText(rs.getString(1));
				LeftTopPanel.lt[3].setText(rs.getString(2));
			}else {
				LeftTopPanel.lt[2].setText("");
				LeftTopPanel.lt[3].setText("");
			}//Endif
			//获得房间状态
			String rState = rs.getString(3);
			
			//预设房价,钟点房价		下面SQL语句是通过房间号获得房间类型标准单价, 钟点房价为下面应收金额计算用
			rs = sunsql.executeQuery("select a.price,a.cl_price from roomtype a, (select " +
			"r_type_id from roominfo where delmark=0 and id='" + chooseRoomNum + 
			"') b where a.delmark=0 and a.id=b.r_type_id");
			if(rs.next())
				LeftTopPanel.lt[1].setText("￥" + rs.getString(1));
			else 
				LeftTopPanel.lt[1].setText("");
			
			//获得房间状态以选择计费方式----单价
			double money = 0;
			if(rState.equals("占用"))			//普通入住计费单价
				money = Double.parseDouble(LeftTopPanel.lt[1].getText().substring(1));
			else
				money = rs.getDouble(2);		//钟点房计费单价
			
			//应收金额		下面SQL语句是通过房间号获得宾客折扣比例
			rs = sunsql.executeQuery("select a.discount from customertype a," +
			"(select c_type_id,r_type_id from livein  where r_no='" + chooseRoomNum + 
			"' and statemark='正在消费' and delmark=0) b where a.delmark=0 and " +
			"a.id=b.c_type_id and a.dis_attr=b.r_type_id");
			if(rs.next()) {
				if(rState.equals("占用"))		//计算普通入住应收费用
					money = money * suntools.getConsumeFactor(inTime, Journal.getNowDTime());
				else							//计算钟点房应收费用
					money = money * suntools.getClockFactor(inTime, Journal.getNowDTime());
				//以宾客折扣比例，打折当前应收金额
				money = money * rs.getDouble(1)/10;
				LeftTopPanel.lt[7].setText("￥" + money);
			}else {
				LeftTopPanel.lt[7].setText("");
			}//Endif
			
			//刷新右下面板表数据
				if(rState.equals("可供")) {
					RightBottPanel.listRightBottDTM("", false);		//执行刷新
				}else {
					String rbCode = "select a.in_no 入住单号,a.main_room 主房间号,b.price " +
					"标准单价,b.c_type 宾客类型,b.discount 享受折扣,b.dis_price 消费金额,in_time " +
					"消费时间,userid 记帐人 from livein a, customertype b where a.r_no='" + 
					chooseRoomNum + "' and " + "statemark='正在消费' and a.c_type_id=b.id " +
					"and b.dis_attr=a.r_type_id and a.delmark=0";
				
					RightBottPanel.listRightBottDTM(rbCode, true);		//执行刷新
				}//Endif
				
	    }
	    catch (Exception ex) {
	    	//ex.printStackTrace();
	    	System.out.println ("RightTopPanel.actionPerformed(): false");
	    }//Endtry
	}
	
	/**=======================================================================**
	 *			MouseListener 监听
	 **=======================================================================**
	 */
	public void mouseClicked (MouseEvent me) {
		Object o = me.getSource();
		if(o == rtp_tb) {
			tb_Name = rtp_tb.getTitleAt(rtp_tb.getSelectedIndex());		//获得当前标签栏标题
			LeftTopPanel.title0.setText(tb_Name + ": ");
			LeftTopPanel.title1.setText("");
			LeftTopPanel.lt[0].setText("");
			LeftTopPanel.lt[1].setText("");
			LeftTopPanel.lt[2].setText("");
			LeftTopPanel.lt[3].setText("");
			LeftTopPanel.lt[4].setText("");
			LeftTopPanel.lt[5].setText("");
			LeftTopPanel.lt[6].setText("");
			LeftTopPanel.lt[7].setText("");
		}else if(o == rtbt1) {							//过滤状态
			int x = me.getX();
			int y = me.getY();
			pm.show(rtbt1, x, y);
		}//Endif
	}

	public void mousePressed (MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseEntered (MouseEvent me) {		//鼠标移进提示
		Object o = me.getSource ();
		if(o == rtbt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"按照指定的状态显示房间信息   　　　　　　　　　　　　　　");
		}else if(o == rtbt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"显示所有的房间信息   　　　　　　　　　　　　　　　　　　");
		}else if(o == rtbt3) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新当前房间信息显示   　　　　　　　　　　　　　　　　　");
		}//Endif
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + 
		"请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
}