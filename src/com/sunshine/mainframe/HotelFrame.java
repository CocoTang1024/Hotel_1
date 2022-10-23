package com.sunshine.mainframe;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import com.sunshine.sunsdk.swing.*;		//加入公共类库
import com.sunshine.sunsdk.system.*;
import com.sunshine.sunsdk.sql.*;
import com.sunshine.mainframe.*;		//加入模块类库
import com.sunshine.individual.*;		//散客开单
import com.sunshine.team.*;				//团体开单
import com.sunshine.checkout.*;			//宾客结帐
import com.sunshine.engage.*;			//预订房间
import com.sunshine.query.*;            //营业查询
import com.sunshine.customer.*;			//客户管理
import com.sunshine.netsetup.*;			//网络设置
import com.sunshine.setup.*;			//系统设置
import com.sunshine.about.*;			//关于我们
import com.sunshine.menu.*;				//下拉菜单中的功能库



public class HotelFrame 
extends JFrame 
implements ActionListener, MouseListener, Runnable {
	
	//用户名，权限
	public static String userid, puil;
	public static JLabel lbA, lbB;
	public static String clue = "    提 示 :  ";
	public static String face = "    当前操作界面 :  ";
	
	//声名与菜单相关的类
	private JMenuBar mb;
	private JMenu m1, m2, m3, m4;
	private JMenuItem mi11, mi12, mi13, mi14, mi15, mi16, mi17, mi18, mi19,
					  mi21, mi22, mi23, mi24, mi25,
			  		  mi31, mi32, mi33, mi34, mi35, mi36;
	//工具栏
	private JToolBar tb;
	private JButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btA;
	//分割面板
	private JSplitPane spaneMain, spaneLeft, spaneRight;
	//模块接口
	private JPanel panelMain, bott, jp2, jp3, jp4;
	private LeftTopPanel jp1;						//主窗口左边面板
	//功能提示
	private String toolTip[] = {
		"零散宾客入住登记   　　　　　　　　　　　　　　　　　　　",
		"团体入住登记   　　　　　　　　　　　　　　　　　　　　　",
		"关于本软件的支持信息   　　　　　　　　　　　　　　　　　",
		"宾客退房结算   　　　　　　　　　　　　　　　　　　　　　",
		"为宾客预订房间   　　　　　　　　　　　　　　　　　　　　",
		"查询营业情况   　　　　　　　　　　　　　　　　　　　　　",
		"为酒店固定客户设置参数   　　　　　　　　　　　　　　　　",
		"设置系统的网络连接方式   　　　　　　　　　　　　　　　　",
		"设置系统参数   　　　　　　　　　　　　　　　　　　　　　",
		"返回Windows  　　　　　　　　　　　　　　　　　　　　　"
	};
	
	//功能模块
	/*#######################################################################*/
		Individual idv = new Individual(this);	//散客开单
		Team		tm = new Team(this);		//团体开单
		CheckOut	co = new CheckOut(this);	//宾客结帐
		Engage		eg = new Engage(this);		//客房预定
		Query		qr = new Query(this);		//营业查询
		Customer	ct = new Customer(this);	//客户管理
		NetSetup	ns = new NetSetup(this);	//网络设置
		Setup 		st = new Setup(this);		//系统设置
		About		ab = new About(this);		//关于我们 
		GoOn		go = new GoOn(this);		//宾客续住
		Change		cg = new Change(this);		//更换房间
	//	Remind		rm = new Remind(this);		//电子提醒
		UniteBill	ub = new UniteBill(this);   //合并帐单
		ApartBill	ap = new ApartBill(this);   //拆分帐单
		Record		rc = new Record(this);		//系统日志
	/*#######################################################################*/
	
	
	
	//构造函数
	public HotelFrame (String us, String pu) {
		super ("酒店管理系统 - 【一路向北】修订版");
		
		userid = us;		//获得操作员名称
		puil   = pu;		//获得操作员权限
		
		panelMain = new JPanel (new BorderLayout());		//主面板
		
		//制作菜单
		buildMenuBar ();
		//制作工具栏
		buildToolBar ();
		//制作分割面板
		buildSpaneMain ();
		//制作窗口底端信息框
		buildBott ();
		
		//加入组件到主面板
		panelMain.add ("North", tb);			//加入工具栏
		panelMain.add ("South", bott);			//加入窗口底端信息框
		panelMain.add ("Center", spaneMain);	//加入分割面板
		
		//加入菜单栏
		this.setJMenuBar (mb);
		
		//加事件监听
		addListener ();
		
		this.addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent we) {
				quit ();
			}//End windowClosing
		});
		
		this.setContentPane (panelMain);
		this.setBounds (2, 2, 1020, 740);
		this.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize (new Dimension (1020, 740));	//设置窗口最小尺寸
		this.setVisible (true);
		(new Thread(this)).start();				//启动房间状态检查线程
	}
	
	//制作菜单
	private void buildMenuBar () {
		//实例菜单栏
		mb = new JMenuBar ();
		
		//实例菜单
		m1 = new JMenu ("来宾登记 (B)");
		m2 = new JMenu ("收银结算 (S)");
		m3 = new JMenu ("系统维护 (W)");
		
		//实例菜单项
		mi11 = new JMenuItem ("散客开单　　(G)");
		mi12 = new JMenuItem ("团体开单　　(M)");
		mi13 = new JMenuItem ("宾客续住　　(Z)");
		mi14 = new JMenuItem ("更换房间　　(A)");
		mi15 = new JMenuItem ("修改登记　　(J)");
		mi16 = new JMenuItem ("房间状态　　(Z)");
		mi17 = new JMenuItem ("预订管理　　(T)");
		mi18 = new JMenuItem ("电子提醒　　(L)");
		mi19 = new JMenuItem ("退出系统　　(X)");
		mi21 = new JMenuItem ("宾客结帐　　(J)");
		mi22 = new JMenuItem ("合并帐单　　(E)");
		mi23 = new JMenuItem ("折分帐单　　(F)");
		mi24 = new JMenuItem ("宾客消费明细查询");
		mi25 = new JMenuItem ("宾客退单明细查询");
		mi31 = new JMenuItem ("网络设置　　(N)");
		mi32 = new JMenuItem ("系统设置　　(X)");
		mi33 = new JMenuItem ("系统日志　　(Z)");
		mi34 = new JMenuItem ("数据备份　　(R)");
		mi35 = new JMenuItem ("软件帮助　　(H)");
		mi36 = new JMenuItem ("关于我们　　(A)");
		///////////////////////////////////////////
		mi16.setEnabled(false);//未开发完毕的功能
		mi18.setEnabled(false);
		mi34.setEnabled(false);
		////////////////////////////////////////////
		//组织菜单
		m1.add (mi11);			//来宾登记
		m1.add (mi12);
		m1.add (mi13);
		m1.add (mi14);
		m1.add (mi15);
		m1.add (mi16);
		m1.addSeparator();
		m1.add (mi17);
		m1.add (mi18);
		m1.addSeparator();
		m1.add (mi19);

		m2.add (mi21);			//收银结算
		m2.add (mi22);
		m2.add (mi23);
		//m2.addSeparator();
		//m2.add (mi24);			//宾客消费明细
		//m2.add (mi25);
		m3.add (mi31);			//系统维护
		m3.add (mi32);
		m3.add (mi33);
		m3.addSeparator();
		m3.add (mi34);
		m3.addSeparator();
		m3.add (mi35);
		m3.add (mi36);
		
		mb.add (m1);			//加入菜单栏
		mb.add (m2);
		mb.add (m3);
	}
	
	//制作工具栏
	private void buildToolBar () {
		tb = new JToolBar();
		//制作按键
		bt1 = new TJButton ("pic/ToolBar/m01.gif", "  散客开单  ", "零散宾客入住登记", true);
		bt2 = new TJButton ("pic/ToolBar/m02.gif", "  团体开单  ", "团体入住登记", true);
		bt3 = new TJButton ("pic/ToolBar/m03.gif", "  关于我们  ", "软件信息", true);
		bt4 = new TJButton ("pic/ToolBar/m04.gif", "  宾客结帐  ", "宾客退房结算", true);
		bt5 = new TJButton ("pic/ToolBar/m05.gif", "  客房预订  ", "为宾客预定房间", true);
		bt6 = new TJButton ("pic/ToolBar/m06.gif", "  营业查询  ", "查询营业情况", true);
		bt7 = new TJButton ("pic/ToolBar/m07.gif", "  客户管理  ", "为酒店固定客户设置", true);
		bt8 = new TJButton ("pic/ToolBar/m08.gif", "  网络设置  ", "设置连接方式", true);
		bt9 = new TJButton ("pic/ToolBar/m09.gif", "  系统设置  ", "设置系统参数", true);
		btA = new TJButton ("pic/ToolBar/m10.gif", "  退出系统  ", "返回Windows", true);
		
		//把按键加入工具栏
		tb.addSeparator ();
		tb.add (bt1);
		tb.add (bt2);
		tb.addSeparator ();
		tb.add (bt4);
		tb.add (bt5);
		tb.add (bt6);
		tb.addSeparator ();
		tb.add (bt7);
		tb.add (bt8);
		tb.add (bt9);
		tb.addSeparator ();
		tb.add (bt3);
		tb.addSeparator ();
		tb.add (btA);
		
		//设置工具栏不可浮动
		tb.setFloatable(false);
	}
	
	//制作主面板
	private void buildSpaneMain () {
		
		jp1 = new LeftTopPanel ();		//这四个面板为功能接口//////////////
		jp2 = new LeftBottPanel();		//左下面板		快速通道
		jp3 = new RightTopPanel();		///////////////////////////////
		jp4 = new RightBottPanel();		//右下面板		消费信息表
		
		//声名分割面板
		spaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jp1, jp2);
		spaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jp3, jp4);
		spaneMain  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, spaneLeft, spaneRight);
		
		//设置面板最小尺寸
		jp1.setMinimumSize(new Dimension (157, 450));
		jp2.setMinimumSize(new Dimension (157, 94));
		jp3.setMinimumSize(new Dimension (875, 300));
		jp4.setMinimumSize(new Dimension (875, 94));
		spaneRight.setMinimumSize(new Dimension (875, 565));
		
		//设置分割条是否有伸缩键
		spaneMain.setOneTouchExpandable (true);
		spaneRight.setOneTouchExpandable(true);
		
		//设置各面板的初起尺寸
		spaneMain.setDividerLocation (160);
		spaneLeft.setDividerLocation (450);
		spaneRight.setDividerLocation(450);
		
		//设置分隔条宽度
		spaneMain.setDividerSize (10);
		spaneLeft.setDividerSize (23);
		spaneRight.setDividerSize(23);
	}
	
	//制作bott栏
	private void buildBott () {
		JLabel lb1, lb2;
		
		lb1 = new JLabel("     酒 店 宾 馆 管 理 系 统    ");
		lb2 = new JLabel("    当前操作员 :  " + userid + "                  ");
		lbA = new JLabel(clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
		lbB	= new JLabel(face + " 酒 店 宾 馆 管 理 系 统 -    【一路向北】修订版         ");
		
		//加外框线
		lbA.setBorder(new LineBorder(new Color(87, 87, 47)));
		lbB.setBorder(new LineBorder(new Color(87, 87, 47)));
		lb1.setBorder(new LineBorder(new Color(87, 87, 47)));
		lb2.setBorder(new LineBorder(new Color(87, 87, 47)));
		
		bott = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		bott.add (lb1);
		bott.add (lbA);
		bott.add (lbB);
		bott.add (lb2);
	}
	
	//加事件监听
	private void addListener () {
		mi11.addActionListener (this);		//来宾登记
		mi12.addActionListener (this);
		mi13.addActionListener (this);
		mi14.addActionListener (this);
		mi15.addActionListener (this);
		mi16.addActionListener (this);
		mi17.addActionListener (this);
		mi18.addActionListener (this);
		mi19.addActionListener (this);
		mi21.addActionListener (this);		//收银结算
		mi22.addActionListener (this);
		mi23.addActionListener (this);
		mi24.addActionListener (this);
		mi25.addActionListener (this);
		mi31.addActionListener (this);		//系统维护
		mi32.addActionListener (this);
		mi33.addActionListener (this);
		mi34.addActionListener (this);
		mi35.addActionListener (this);
		mi36.addActionListener (this);
		bt1.addActionListener (this);		//按键加动作监听
		bt2.addActionListener (this);
		bt3.addActionListener (this);
		bt4.addActionListener (this);
		bt5.addActionListener (this);
		bt6.addActionListener (this);
		bt7.addActionListener (this);
		bt8.addActionListener (this);
		bt9.addActionListener (this);
		btA.addActionListener (this);
		bt1.addMouseListener (this);		//按键加鼠标监听
		bt2.addMouseListener (this);
		bt3.addMouseListener (this);
		bt4.addMouseListener (this);
		bt5.addMouseListener (this);
		bt6.addMouseListener (this);
		bt7.addMouseListener (this);
		bt8.addMouseListener (this);
		bt9.addMouseListener (this);
		btA.addMouseListener (this);
	}
	
	/**=======================================================================**
	 *		[## private void quit () {} ]: 				系统退出
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：关闭系统函数，仅类内使用
	 **=======================================================================**
	 */
	private void quit () {
		int flag = 0;
		String msg = "您 现 在 要 关 闭 系 统 吗 ？";
		flag = JOptionPane.showConfirmDialog (null, msg, "提示", JOptionPane.YES_NO_OPTION);
		if(flag == JOptionPane.YES_OPTION) {
			Journal.writeJournalInfo(userid, "退出本系统", Journal.TYPE_LG);//记录操作日志
			this.setVisible (false);
			System.exit (0);
		}//End if(flag == JOptionPane.YES_OPTION)
		return;
	}
	
	//刷新左房间信息栏数据
	private void initLeftData() {
		jp1.title1.setText("");		//刷房间信息
		for (int i = 0; i < 8; i++) {
			jp1.lt[i].setText("");
	    }//Endfor
		jp1.initRoomstate();					//刷新房间总状态
	}
	
	//传数据给散客开单窗口
	private boolean initIDV() {
		try {
			//从房间信息表里获得当前房间的状态和房间类型编号
			ResultSet rs = sunsql.executeQuery("select state,r_type_id from roominfo " +
			"where delmark=0 and id='" + LeftTopPanel.title1.getText() + "'");
			
			if(!rs.next()) {		//如果无结果集，提示用户刷新房间数据
				if(LeftTopPanel.title1.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,"请选定房间后，再为宾客开设房间", 
					"提示", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "[ " + LeftTopPanel.title1.getText() + 
					" ] 房间信息已更改，请刷新房间信息，再为宾客开设房间", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				return false;
			}else {
				if(!rs.getString(1).equals("可供")) {		//只有状态是可供房间，才能为宾客开设
					JOptionPane.showMessageDialog(null, "请选择空房间，再为宾客开设房间", 
					"提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}//Endif
				
				//传房间号到开单窗口
				Individual.lbA.setText(LeftTopPanel.title1.getText());
				//传房间类型到开单窗口
				Individual.lbB.setText(LeftTopPanel.title0.getText().substring(0, 
									   LeftTopPanel.title0.getText().length()-2));
				//传房间单价到开单窗口
				Individual.lbC.setText(LeftTopPanel.lt[1].getText());
				
				//房间类型编号
				String clRoom = rs.getString(2);
				//获得此类型房间是否可以开设钟点房
				rs = sunsql.executeQuery("select cl_room from roomtype where " +
				"delmark=0 and id='" + clRoom + "'");
				rs.next();
				if(rs.getString(1).equals("N")) {	//不能开设，则开单窗口的钟点选项不可用
					Individual.chk1.setSelected(false);		//取消选中状态
					Individual.chk1.setEnabled(false);		//设置不可用
				}else {
					Individual.chk1.setEnabled(true);		//可用
				}//Endif
				
				//传宾客类型数据给开单窗口
				rs = sunsql.executeQuery("select distinct c_type from customertype where " +
				"delmark = 0 and pk!=0");
				int ct = sunsql.recCount(rs);
				String cType[] = new String[ct];
				for (int i = 0; i < ct; i++) {
					rs.next();
					cType[i] = rs.getString(1);
			    }//Endfor
			    Individual.cb2.removeAllItems();
				for (int i = 0; i < ct; i++) {
					Individual.cb2.addItem(cType[i]);
			    }//Endfor
			    Individual.cb2.setSelectedItem("普通宾客");
				
				//初始化开单房间表---------临时表
				sunsql.executeUpdate("delete from roomnum");		//清空临时表
				sunsql.executeUpdate("insert into roomnum(roomid) values('" + 
				LeftTopPanel.title1.getText() + "')");				//加入当前房间信息
				//初始化开单窗口的开单房间表
				sunsql.initDTM(Individual.dtm2,"select roomid 房间编号 from roomnum");
				
				//初始化追加房间表---------当前类型的除当前房间的所有可供房间
				sunsql.executeUpdate("update roominfo set indimark=0");	//刷新所有房间的开单状态
				sunsql.executeUpdate("update roominfo set indimark=1 where id='" + 
				LeftTopPanel.title1.getText() + "'");				//设置当前房间为开单状态
				//初始化开单窗口的可供房间表
				sunsql.initDTM(Individual.dtm1,"select a.id 房间编号1 from roominfo " +
				"a,(select id from roomtype where r_type='" + Individual.lbB.getText() + 
				"') b where a.delmark=0 and a.indimark=0 and a.state='可供' and a.r_type_id=b.id");
				
			}//Endif
	    }
	    catch (Exception ex) {
	    	System.out.println ("HotelFrame.initIDV(): false");
	    }//End try
	    return true;
	}
	
	//传数据给团体开单窗口
	private boolean initTeam() {
		try {
			//初始化开单房间表---------临时表
			sunsql.executeUpdate("delete from roomnums");		//清空临时表
			//初始化开单窗口的开单房间表
			Team.initDTM2();
			
			//初始化追加房间表---------当前类型的除当前房间的所有可供房间
			sunsql.executeUpdate("update roominfo set indimark=0");	//刷新所有房间的开单状态
			
			//传房间类型数据给团体开单窗口
			ResultSet rs = sunsql.executeQuery("select r_type from roomtype where delmark=0");
			int ct = sunsql.recCount(rs);
			String type[] = new String[ct];
			
			//传宾客房型数据给团体开单窗口
			for (int i = 0; i < ct; i++) {
				rs.next();
				type[i] = rs.getString(1);
			}//Endfor
			Team.cb.removeAllItems();
			for (int i = 0; i < ct; i++) {
				Team.cb.addItem(type[i]);
			}//Endfor
			
			//传宾客类型数据给团体开单窗口
			rs = sunsql.executeQuery("select distinct c_type from customertype where " +
			"delmark = 0 and pk!=0");
			ct = sunsql.recCount(rs);
			for (int i = 0; i < ct; i++) {
				rs.next();
				type[i] = rs.getString(1);
			}//Endfor
			Team.cb2.removeAllItems();
			for (int i = 0; i < ct; i++) {
				Team.cb2.addItem(type[i]);
			}//Endfor
			Team.cb2.setSelectedItem("普通宾客");
	    }
	    catch (Exception ex) {
	    	System.out.println ("HotelFrame.initTeam(): false");
	    }//Endtry
	    return true;
	}
	
	//传数据给结算窗口
	private boolean initCKO() {
		try {
			ResultSet rs = sunsql.executeQuery("select state,r_type_id from roominfo " +
			"where delmark=0 and id='" + LeftTopPanel.title1.getText() + "'");
			
			if(!rs.next()) {		//如果无结果集，提示用户刷新房间数据
				if(LeftTopPanel.title1.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,"请选定宾客入住的房间后，再为宾客结算费用", 
					"提示", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "[ " + LeftTopPanel.title1.getText() + 
					" ] 房间信息已更改，请刷新房间信息，再为宾客开设房间", "提示", JOptionPane.INFORMATION_MESSAGE);
				}//Endif
				return false;
			}else {
				//只有状态是占用或钟点房间，才能为宾客结算
				if(!rs.getString(1).equals("占用") && !rs.getString(1).equals("钟点")) {
					JOptionPane.showMessageDialog(null, "请选择宾客正在消费的房间进行结算费用操作", 
					"提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}//Endif
				
				//传结算单号给结算窗口
				co.lbA.setText(suntools.getNumber(suntools.Number_JS));
				//传结算房间给结算窗口
				co.lbB.setText(jp1.title1.getText());
				//传结算宾客名称给结算窗口
				co.lbC.setText(jp1.lt[0].getText());
				//传押金给结算窗口
				co.lbF.setText(jp1.lt[6].getText());
				
				//清空结算中间表
				sunsql.executeUpdate("delete from checkout_temp");
				
				//获得主房间号
				rs = sunsql.executeQuery("select main_room,in_no from livein where " +
				"delmark=0 and r_no='" + co.lbB.getText() + "' and statemark='正在消费'");
				
				if(!rs.next()) {
					JOptionPane.showMessageDialog(null, "操作失败，请检查网络情况", 
					"提示", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}//Endif
				
				String mainRoom = rs.getString(1);		//取出主房间号
				co.inNo = rs.getString(2);				//传入住单号为结算窗口
				
				//获得主房间状态，看是普通住宿还是钟点房
				rs = sunsql.executeQuery("select state from roominfo where delmark=0 and id='" + mainRoom + "'"); 
				rs.next();
				int mrState = 0;    //主房间状态 0:普通入住  1:钟点房
				if(rs.getString(1).equals("钟点")) {
					mrState = 1;
				}//Endif
				
				DefaultTableModel ckoDTM = new DefaultTableModel();		//主房间下的所有房间信息
				sunsql.initDTM(ckoDTM, "select pk,r_no,r_type_id,c_type_id,in_time,foregift from " +
				"livein where delmark=0 and main_room='" + mainRoom + "'");
				
				double total = 0;		//消费总金额
				double shsh  = 0;		//实收金额
				double youh  = 0;		//优惠金额
				for (int i = 0; i < ckoDTM.getRowCount(); i++) {
					//取出当前房间的标准单价
					rs = sunsql.executeQuery("select price,cl_price from roomtype where delmark=0 and id='" + 
					ckoDTM.getValueAt(i, 2) + "'");
					rs.next();
					double rprice = 0;
					double days	  = 0;
					if(mrState == 0) {
						rprice = rs.getDouble(1);		//普通入住单价
						//获得入住天数
						days   = suntools.getConsumeFactor(ckoDTM.getValueAt(i, 4) + "", Journal.getNowDTime());
					}else {
						rprice = rs.getDouble(2);		//钟点房单价
						days   = suntools.getClockFactor(ckoDTM.getValueAt(i, 4) + "", Journal.getNowDTime());
					}//Endif
					
					double rd 	  = rprice * days;		//当前房间的消费总金额
					total 		  = total + rd;			//累加总消费
					rs = sunsql.executeQuery("select discount from customertype where delmark=0 and " +
					"id='" + ckoDTM.getValueAt(i, 3) + "' and dis_attr='" + ckoDTM.getValueAt(i, 2) + "'");
					rs.next();
					//取出宾客享受的折扣
					int dst 	  = rs.getInt(1);
					double ddr    = rd * dst/10;			//当前房间的打折后价格
					shsh		  = shsh + ddr;				//应收金额累加
					youh		  = youh + rd - ddr;		//优惠金额累加
					
					//向结算中间表加入数据
					sunsql.executeUpdate("insert into checkout_temp(pk,r_type_id,r_no,price," +
					"discount,dis_price,account,money,in_time) values(" + ckoDTM.getValueAt(i, 0) +
					",'" + ckoDTM.getValueAt(i, 2) + "','" + ckoDTM.getValueAt(i, 1) + "'," + 
					rprice + "," + dst + "," + rprice * dst / 10 + "," + days + "," + ddr + ",'" + 
					ckoDTM.getValueAt(i, 4) + "')");
			    }
				
				//传消费金额给结算窗口
				co.lbD.setText(total + "");
				//传应收金额给结算窗口
				co.lbE.setText(shsh + "");
				//传实收金额
				co.tf1.setText(shsh + "");
				//传优惠金额给结算窗口
				co.lbG.setText(youh + "");
				//传找零金额给结算窗口
				co.lbH.setText(Double.parseDouble(co.lbF.getText()) - shsh + "");
				
				//刷新结算中间表数据
				co.initDTM();
				
				co.tf2.requestFocus(true);		//给宾客支付焦点
				
			}//Endif
	    }
	    catch (Exception ex) {
	    	JOptionPane.showMessageDialog(null, "操作失败，请检查网络情况", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
	    	ex.printStackTrace();
	    	System.out.println ("HotelFrame.initCKO(): false");
	    	return false;
	    }//End try
		return true;
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed (ActionEvent ae) {
		Object o = ae.getSource ();
		if(o == bt1 || o ==mi11) {//============================================
			lbB.setText(face + "散 客 开 单                   　　　　　　　-");
			if(initIDV()) {								//传数据给散客开单窗口
				idv.show();								//散客开单
				initLeftData();							//刷新左房间信息栏数据
			}//Endif
		}else if(o == bt2 || o == mi12) {//=====================================
			lbB.setText(face + "团 体 开 单                   　　　　　　　-");
			if(initTeam()) {							//传宾客类型数据给团体开单窗口
				tm.show();								//团体开单
				initLeftData();							//刷新左房间信息栏数据
			}//Endif
		}else if(o == bt3 || o == mi36) {//=====================================
			lbB.setText(face + "关 于 我 们                   　　　　　　　-");
			ab.show();									//关于我们
		}else if(o == bt4 || o == mi21) {//=====================================
			lbB.setText(face + "宾 客 结 帐                   　　　　　　　-");
			if(initCKO()) {								//传数据给结算窗口
				co.show();								//宾客结帐
				initLeftData();							//刷新左房间信息栏数据
			}//Endif
		}else if(o == bt5 || o == mi17) {//=====================================
			lbB.setText(face + "客 房 预 订                   　　　　　　　-");
			eg.show();									//客房预订
			initLeftData();								//刷新左房间信息栏数据
		}else if(o == bt6) {//==================================================
			lbB.setText(face + "营 业 查 询                   　　　　　　　-");
			qr.show();									//营业查询
		}else if(o == bt7) {//==================================================
			if(puil.equals("普通操作员")) {				//客户管理
				String msg = "对不起，您的权限不能进入 [ 客户管理 ] 页面，请用管理员ID登录 ...";
				JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			lbB.setText(face + "客 户 管 理                   　　　　　　　-");
			ct.show();
		}else if(o == bt8 || o == mi31) {//=====================================
			if(puil.equals("普通操作员")) {				//网络设置
				String msg = "对不起，您的权限不能进入 [ 网络设置 ] 页面，请用管理员ID登录 ...";
				JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			lbB.setText(face + "网 络 设 置                   　　　　　　　-");
			ns.show();
		}else if(o == bt9 || o == mi32) {//=====================================
			if(puil.equals("普通操作员")) {				//系统设置
				String msg = "对不起，您的权限不能进入 [ 网络设置 ] 页面，请用管理员ID登录 ...";
				JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			lbB.setText(face + "系 统 设 置                   　　　　　　　-");
			st.buildDTM12("");							//刷新房间信息表的房间状态
			st.show();
			initLeftData();								//刷新左房间信息栏数据
		}else if(o == btA || o == mi19) {//=====================================
			quit ();									//退出系统
		}else if(o == mi13) {//=================================================
			lbB.setText(face + "宾 客 续 住                   　　　　　　　-");
			go.show();									//宾客续住
		}else if(o == mi14) {//=================================================
			lbB.setText(face + "更 换 房 间                   　　　　　　　-");
			cg.show();									//更换房间
		}else if(o == mi15) {//=================================================
			lbB.setText(face + "修 改 登 记                   　　　　　　　-");
			idv.show();						//修改登记-------还要修改
		}else if(o == mi16) {//=================================================
			//房间状态
		}else if(o == mi18) {//=================================================
			//电子提醒
			
		}else if(o == mi22) {//=================================================
			lbB.setText(face + "合 并 帐 单                   　　　　　　　-");
			ub.show();									//合并帐单
		}else if(o == mi23) {//=================================================
			lbB.setText(face + "折 分 帐 单                   　　　　　　　-");
			ap.show();									//折分帐单
		}else if(o == mi33) {//=================================================
			lbB.setText(face + "系 统 日 志                   　　　　　　　-");
			rc.initDTM();								//刷新日志列表
			rc.show();									//系统日志
		}else if(o == mi34) {//=================================================
			//数据备份
		}else if(o == mi35) {//=================================================
			//软件帮助
		}
		lbB.setText(face + "阳 光 酒 店 管 理 系 统 -    ★★ 版         ");
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
			lbA.setText (clue + toolTip[0]);
		}else if(o == bt2) {
			lbA.setText (clue + toolTip[1]);
		}else if(o == bt3) {
			lbA.setText (clue + toolTip[2]);
		}else if(o == bt4) {
			lbA.setText (clue + toolTip[3]);
		}else if(o == bt5) {
			lbA.setText (clue + toolTip[4]);
		}else if(o == bt6) {
			lbA.setText (clue + toolTip[5]);
		}else if(o == bt7) {
			lbA.setText (clue + toolTip[6]);
		}else if(o == bt8) {
			lbA.setText (clue + toolTip[7]);
		}else if(o == bt9) {
			lbA.setText (clue + toolTip[8]);
		}else if(o == btA) {
			lbA.setText (clue + toolTip[9]);
		}
	}

	public void mouseExited (MouseEvent me) {
		lbA.setText (clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
	
	
	/**=======================================================================**
	 *		[## public void run() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：结算后检查房间状态线程
	 **=======================================================================**
	 */
	public void run() {
		try {
			Statement ste1 = null;
			Connection conn1 = null;
			if(sunini.getIniKey ("Default_Link").equals ("1")) {		//JDBC连接方式
				String user1 = sunini.getIniKey ("UserID");
				String pwd1  = sunini.getIniKey ("Password");
				String ip1   = sunini.getIniKey ("IP");
				String acc1  = sunini.getIniKey ("Access");
				String dbf1  = sunini.getIniKey ("DBFname");
				//String url1  = "jdbc:microsoft:sqlserver://" + ip1 + ":" + acc1 + ";" + "databasename=" + dbf1;
				String url1  = "jdbc:mysql://" + ip1 + ":" + acc1 + "/" + dbf1 +"?useUnicode=true&characterEncoding=utf-8";
				//注册驱动
				//DriverManager.registerDriver (new com.microsoft.jdbc.sqlserver.SQLServerDriver());
				//获得一个连接
				conn1 = DriverManager.getConnection (url1, user1, pwd1);
			}
			else {
				//注册驱动										//JDBCODBC连接方式
				//DriverManager.registerDriver (new sun.jdbc.odbc.JdbcOdbcDriver());
				//获得一个连接
				conn1 = DriverManager.getConnection ("jdbc:odbc:" + sunini.getIniKey("LinkName"));
			}
			
			//建立高级载体
			ste1 = conn1.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			while( true ) {
				ste1.executeUpdate("update roominfo set statetime=statetime-1 where statetime>0");
				ste1.executeUpdate("update roominfo set state='可供' where statetime=0 and state='脏房'");
				Thread.sleep(30000);
			}//End while
	    }
	    catch (Exception ex) {
	    	JOptionPane.showMessageDialog (null, "数据库连接失败...", "错误", JOptionPane.ERROR_MESSAGE);
	    	System.exit(0);
	    	//ex.printStackTrace();
	    }//End try
	}
/*
	public static void main (String sd[]) {
		sunswing.setWindowStyle (sunini.getIniKey ("Sys_style").charAt (0));
		new HotelFrame ("gujun", "管理员");
	}*/
}