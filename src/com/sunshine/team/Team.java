/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 散客开单窗口
 *	[ 文件名      ]  : Team.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 团体宾客开设房间窗口
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.1
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/22      1.0             董丰            创建
 *	2006/04/27      1.1             顾俊            完成功能
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :	
 *	
 *	[## public Team(JFrame frame) {} ]:
 *		功能: 散客开单窗口
 *
 *	[## private void addListener() {} ]: 
 *		功能: 加事件监听
 *
 *	[## private void buildPanel() {} ]: 
 *		功能: 制作主面板
 *
 *	[## private void initDTM1(String cType) {} ]:
 *		功能: 初始化可供房间列表
 *
 *	[## private void initDTM2() {} ]: 
 *		功能: 初始化开单房间列表
 *
 *	[## private void addRoom() {} ]: 
 *		功能: 加到开单区
 *
 *	[## private void subRoom() {} ]: 
 *		功能: 从开单区移除
 *
 *	[## private boolean isValidity() {} ]:
 *		功能: 测试用户输入的数据是否合法
 *
 *	[## private void saveLiveIn() {} ]: 
 *		功能: 保存所有宾客的入住信息
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.team;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;				//公共类
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.*;				//主框架窗口


public class Team 
extends JDialog 
implements ActionListener, MouseListener {
	
	public static JComboBox cb, cb2;			//房间类型，宾客类型
	public static DefaultTableModel dtm1, dtm2;	//可供房间表，开单房间表
	public static JTable tb2;					//开单列表
	
	private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tfA, tfB;
                   //证件码,主客,人数,押金,主房,天数,地址,备注	表格标题
    private JButton bt1, bt2, bt3, bt4;
                  //确定,取消,添加,移除
	private JComboBox cb1, cb3;
               //证件类型,性别
	private JCheckBox chk;
                //到预住天数提醒
	private JTable tb1;				//表格
	private JScrollPane sp1, sp2;	//滚动面板
	
	private JPanel panelMain;		//主面板
	
	private String rt;				//房间类型
    
    
    /**=======================================================================**
	 *		[## public Team(JFrame frame) {} ]: 			构造函数
	 *			参数   ：JFrame frame表示本对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：团体开单窗口
	 **=======================================================================**
	 */
	public Team(JFrame frame) {
		super (frame, "团体开单", true);
		panelMain = new JPanel(new GridLayout(2, 1, 0, 10));	//主面板为表格布局
		buildPanel();
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (540,500));
		this.setMinimumSize (new Dimension (540, 500));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
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
		tf1.addActionListener(this);	//文本框加监听
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
		tf6.addActionListener(this);
		tf7.addActionListener(this);
		bt1.addActionListener(this);	//按键的动作监听
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		cb.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
		bt3.addMouseListener(this);
		bt4.addMouseListener(this);
	}
	
	/**=======================================================================**
	 *		[## private void buildPanel() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作信息面板
	 **=======================================================================**
	 */
	private void buildPanel() {
		
		JLabel lb0, lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9, lb10, lb11;
		lb0 = new JLabel("房间类型：");
		lb1 = new JLabel("证件类型：");
		lb2 = new JLabel("  宾客类型：");
		lb3 = new JLabel("  主客性别：");
		lb4 = new JLabel("证件编码：");
		lb5 = new JLabel("  主客姓名：");
		lb6 = new JLabel("  宾客人数：");
		lb7 = new JLabel("实收押金：");
		lb8 = new JLabel("  主客房间：");
		lb9 = new JLabel("  预住天数：");
		lb10 = new JLabel("地址信息：");
		lb11 = new JLabel("备注信息：");
		
		//下拉列表
		cb = new JComboBox();//宾客类型
		cb1 = new JComboBox();
		cb1.addItem("身份证");
		cb1.addItem("驾驶证");
		cb1.addItem("学生证");
		cb1.addItem("军官证");
		cb1.addItem("护照");
		cb1.addItem("其他");
		cb2 = new JComboBox();//宾客类型
		cb3 = new JComboBox();
		cb3.addItem("男         ");
		cb3.addItem("女         ");
		
		//文本框
		tf1 = new TJTextField(8);
		tf2 = new TJTextField(10);
		tf3 = new TJTextField("1");
		tf4 = new TJMoneyField();
		tf5 = new JTextField("", 10);
		tf6 = new TJTextField(8);
		tf7 = new TJTextField(40);
		tf8 = new TJTextField(40);
		tfA = new JTextField("房间信息");
		tfB = new JTextField("开单房间");
		tf3.setHorizontalAlignment (JTextField.RIGHT);	//设置宾客人数文本框右对齐
		tf6.setHorizontalAlignment (JTextField.RIGHT);	//设置宾客预住天数右对齐
		tf5.setEditable(false);							//设置主客房不可编辑
		tfA.setHorizontalAlignment (JTextField.CENTER);	//以下是设置标题
		tfA.setBackground(new Color(199, 183, 143));
		tfA.setBorder(new LineBorder(new Color(87, 87, 47)));
		tfA.setEditable(false);
		tfB.setHorizontalAlignment (JTextField.CENTER);
		tfB.setBackground(new Color(199, 183, 143));
		tfB.setBorder(new LineBorder(new Color(87, 87, 47)));
		tfB.setEditable(false);
		
		//多选钮
		chk = new JCheckBox("   到预住天数时自动提醒");
				
		//左边表格
		dtm1 = new DefaultTableModel();
		tb1  = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		tb1.setShowHorizontalLines(false);
		tb1.setForeground(new Color( 87,  87,  47));
		tb1.setBackground(new Color(248, 242, 230));
		sp1.setBorder(BorderFactory.createTitledBorder(""));
		String rtype = cb.getSelectedItem()+"";
		
		/////////////////////////////////////////做表格
		
		//右边表格
		dtm2 = new DefaultTableModel();
		tb2  = new JTable(dtm2);
		sp2  = new JScrollPane(tb2);
		tb2.setShowHorizontalLines(false);
		tb2.setForeground(new Color( 87,  87,  47));
		tb2.setBackground(new Color(248, 242, 230));
		sp2.setBorder(BorderFactory.createTitledBorder(""));
		/////////////////////////////////////////做表格
		
		//按钮
		bt1 = new TJButton ("pic/modi3.gif", " 确 定 ", "开单");
		bt2 = new TJButton ("pic/cancel.gif", " 取 消 ", "取消开单");
		bt3 = new TJButton("pic/new.gif","添加到开单区","添加到开单区");
		bt4 = new TJButton("pic/del.gif","从开单区删除","从开单区删除");
		
		//面板
		JPanel pb,pt,ptc,pt1,pt1c,pt1cn,pt2,
			   pc2,pc21,pc22,pcd1,pc23,pc24,pcd2,pc25,pc26,pcd3,
			   pc3,pc31,pc32,
			   pc4,pc5;
		JLabel line0 = new JLabel("                                                            ");
		JLabel line1 = new JLabel(new ImageIcon("pic/line4.gif"));//分隔线
		JLabel line2 = new JLabel(new ImageIcon("pic/line4.gif"));//分隔线
		
		pt = new JPanel(new BorderLayout(0,5));    //放置上半面板
		ptc = new JPanel(new GridLayout(1,2,10,5));//上半面板的表格
		pt1 = new JPanel(new BorderLayout());	   //放置左边表格
		pt1c = new JPanel(new BorderLayout());	   //
		pt1cn = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
		pt2 = new JPanel(new BorderLayout());      //放置右边表格	
		pb = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));   //下半面板
		pc2  = new JPanel(new FlowLayout(FlowLayout.LEFT,10,3));  //放置"证件类型...,宾客类型....实收押金"三行
		pc21 = new JPanel(new GridLayout(3,1,5,16));//放置证件类型,宾客类型,实收押金
		pc22 = new JPanel(new GridLayout(3,1,5,6));//放置证件类型,宾客类型下拉框,实收押金文本框
		pcd1  = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置pcc21,pcc22
		pc23 = new JPanel(new GridLayout(3,1,5,15));//放置证件编码,主客姓名,主客房间
		pc24 = new JPanel(new GridLayout(3,1,5,6));//放置证件编码,主客姓名,主客房间文本框
		pcd2  = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置pcc23,pcc24
		pc25 = new JPanel(new GridLayout(3,1,5,15));//放置主客性别,宾客人数,预住天数
		pc26 = new JPanel(new GridLayout(3,1,5,6));//放置主客性别下拉框,宾客人数,预住天数文本框
		pcd3  = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置pcc25,pcc26
		pc3  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));   //放置"地址信息,备注信息"两行
		pc31 = new JPanel(new GridLayout(2,1,0,6));//放置地址信息,备注信息
		pc32 = new JPanel(new GridLayout(2,1,0,6));//放置地址信息,备注信息文本框
		pc4  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));   //放置chk
		pc5  = new JPanel(new FlowLayout(FlowLayout.CENTER,70,6));//放置确定和取消按钮
		
		//构造pc1
		JLabel temp = new JLabel ("");
		pt1cn.add(lb0);
		pt1cn.add(cb);
		pt1cn.setBorder(BorderFactory.createTitledBorder(""));
		pt1c.add("North",pt1cn);
		pt1c.add(sp1);
		pt1.add("North",tfA);
		pt1.add(pt1c);
		pt1.add("South",bt3);
		pt2.add("North",tfB);
		pt2.add(sp2);
		pt2.add("South",bt4);
		ptc.add(pt1);
		ptc.add(pt2);
		pt.add("North",temp);
		pt.add(ptc);
		
		//构造证件类型,宾客类型,实收押金三行
		pc21.add(lb1);		
		pc21.add(lb4);
		pc21.add(lb7);
		pc22.add(cb1);		
		pc22.add(tf1);	
		pc22.add(tf4);	
		pc23.add(lb2);	
		pc23.add(lb5);	
		pc23.add(lb8);	
		pc24.add(cb2);
		pc24.add(tf2);
		pc24.add(tf5);
		pc25.add(lb3);
		pc25.add(lb6);
		pc25.add(lb9);
		pc26.add(cb3);
		pc26.add(tf3);
		pc26.add(tf6);
		pcd1.add(pc21);
		pcd1.add(pc22);
		pcd2.add(pc23);
		pcd2.add(pc24);
		pcd3.add(pc25);
		pcd3.add(pc26);
		pc2.add(pcd1);
		pc2.add(pcd2);
		pc2.add(pcd3);
		
		//构造地址信息,备注信息两行
		pc31.add(lb10);
		pc31.add(lb11);
		pc32.add(tf7);
		pc32.add(tf8);
		pc3.add(pc31);
		pc3.add(pc32);
		
		//构造chk	
		JLabel temp10 = new JLabel("                                                                                                              ");
		pc4.add(chk);
		pc4.add(temp10);
		
		//构造确定取消按钮面板
		pc5.add(bt1);
		pc5.add(bt2);
		
		//将开单信息加入面板
		pb.add(line1);
		pb.add(pc2);
		pb.add(pc3);
		pb.add(pc4);
		pb.add(line2);		
		pb.add(pc5);		
		
		
		panelMain.add(pt);
		panelMain.add(pb);
	}
	
	/**=======================================================================**
	 *		[## private void initDTM1(String rtype) {} ]: 
	 *			参数   ：String rtype表示房间类型
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化可供房间列表
	 **=======================================================================**
	 */
	private void initDTM1(String rtype) {
		String sqlCode = "select a.id 客房编号,b.price 标准单价,b.id from roominfo as a,roomtype " +
		"as b where a.indimark=0 and b.r_type = '"+rtype+"' and a.r_type_id = b.id and " +
		"a.state = '可供' and a.delmark = 0";
		sunsql.initDTM(dtm1,sqlCode);
		tb1.removeColumn(tb1.getColumn("id"));
	}
	
	/**=======================================================================**
	 *		[## private void initDTM2() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化开单房间列表
	 **=======================================================================**
	 */
	public static void initDTM2() {
		sunsql.initDTM(Team.dtm2,"select roomid 客房编号,price 标准单价,rr_type from roomnums");
		tb2.removeColumn(tb2.getColumn("rr_type"));
	}
	
	/**=======================================================================**
	 *		[## private void addRoom() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：加到开单区
	 **=======================================================================**
	 */
	private void addRoom() {
		//获得选择的行号
		int arows[] = tb1.getSelectedRows();
		
		int ar = 0;
		String sqlCode[]  = new String[arows.length * 2];
		if(arows.length > 0) {
			
			if(tf5.getText().length() == 0) {		//默认第一个加入的房间为主房间
				tf5.setText(dtm1.getValueAt(arows[0], 0) + "");
			}//Endif
			
			for (int i = 0; i < arows.length; i++) {
				//加入开单列表
				sqlCode[ar] = "insert into roomnums(roomid,price,rr_type) values('" +
				dtm1.getValueAt(arows[i], 0) + "'," + dtm1.getValueAt(arows[i], 1) + ",'" + 
				dtm1.getValueAt(arows[i], 2) + "')";
				ar++;
				//清除可供列表
				sqlCode[ar] = "update roominfo set indimark=1 where " +
				"delmark=0 and id='" + dtm1.getValueAt(arows[i], 0) + "'";
				ar++;
			}//Endfor
			int flag = sunsql.runTransaction(sqlCode);
			
			if(flag < arows.length) {
				JOptionPane.showMessageDialog(null, "添加失败，请检查网络情况", 
				"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}//Endif
			initDTM1(rt);		//刷新可供列表
			initDTM2();			//刷新开单列表
		}else {
			JOptionPane.showMessageDialog(null, "请在可供房间列表中选中指定房间，" +
			"再追加", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}//endif
	}
	
	/**=======================================================================**
	 *		[## private void subRoom() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：从开单区移除
	 **=======================================================================**
	 */
	private void subRoom() {
		//获得选择的行号
		int arows[] = tb2.getSelectedRows();
		
		int ar = 0;
		String sqlCode[]  = new String[arows.length * 2];
		if(arows.length > 0) {
			for (int i = 0; i < arows.length; i++) {
				sqlCode[ar] = dtm2.getValueAt(arows[i], 0) + "";
				if(!sqlCode[ar].equals(tf5.getText())) {		//判断主房间不能删除
					sqlCode[ar] = "delete from roomnums where roomid='" +
					dtm2.getValueAt(arows[i], 0) + "'";//移除开单列表
					ar++;
					//清除可供列表
					sqlCode[ar] = "update roominfo set indimark=0 where " +
					"delmark=0 and id='" + dtm2.getValueAt(arows[i], 0) + "'";
					ar++;
				}else {
					JOptionPane.showMessageDialog(null, "[ " + dtm2.getValueAt(arows[i], 0) + 
					" ] 房间是主房间，不能移除 ...", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}//Endif
			}//Endfor
			int flag = sunsql.runTransaction(sqlCode);
			
			if(flag < arows.length) {
				JOptionPane.showMessageDialog(null, "移除失败，请检查网络情况", 
				"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}//Endif
			initDTM1(rt);		//刷新可供列表
			initDTM2();			//刷新开单列表
		}else {
			JOptionPane.showMessageDialog(null, "请在开单列表中选中指定房间，" +
			"再移除", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}//endif
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
			JOptionPane.showMessageDialog(null, "[ 证件编码 ] 不能为空", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf1.requestFocus(true);
			return false;
		}else if(tf2.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "[ 宾客名称 ] 不能为空", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf2.requestFocus(true);
			return false;
		}else if(tf7.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "[ 地址信息 ] 不能为空", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf7.requestFocus(true);
			return false;
		}else if(tf5.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "[ 主客房间 ] 不能为空", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else if(!suntools.isNum(tf3.getText(), 3, 1, 600)) {
			JOptionPane.showMessageDialog(null, "[ 宾客人数 ] 只能由数字组成，" +
			"且至少是1人，最多600人", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf3.setText("1");
			tf3.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf6.getText(), 2, 1, 99)) {
			JOptionPane.showMessageDialog(null, "散客开单的 [ 预住天数 ] 至少是1天，" +
			"最多99天，超过三个月的请到VIP部办理包房", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf6.setText("1");
			tf6.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf4.getText(), 8, 1000, 20000)) {
			JOptionPane.showMessageDialog(null, "团体开单的 [ 实收押金 ] 最少为1000元，" +
			"最多20000元，超过50000元的团体请到VIP部开设房间", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf4.requestFocus(true);
			return false;
		}//Endif
		return true;
	}
	
	/**=======================================================================**
	 *		[## private void saveLiveIn() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：保存宾客入住信息
	 **=======================================================================**
	 */
	private void saveLiveIn() {
		long pkMain = sunsql.getPrimaryKey();			//主PK
		String inNumber = suntools.getNumber(suntools.Number_RZ);	//入住单号
		String roomMain = tf5.getText();				//主房间号
		String cName	= tf2.getText();				//客户名称
		String sex		= cb3.getSelectedItem().toString().trim();	//性别
		String zjType	= cb1.getSelectedItem() + "";	//证件类型
		String zjNo		= tf1.getText();				//证件编号
		String address	= tf7.getText();				//地址
		String renShu	= tf3.getText();				//人数
		String inTime	= Journal.getNowDTime();		//入住时间
		String days		= tf6.getText();				//预注天数
		String account	= tb2.getRowCount() +"";		//消费数量
		String foregift = tf4.getText();				//押金
		String reMark	= tf8.getText();				//备注
		String userid	= HotelFrame.userid;			//经手人
		int cluemark 	= 0;							//提醒标志
		if(chk.isSelected())
			cluemark	= 1;
		String sqlCode[]= new String[tb2.getRowCount() * 2];	//要存数据，还要改状态，所以是两倍大小的SQL数组
		try {
			ResultSet rs = sunsql.executeQuery("select distinct id from customertype where " +
			"delmark = 0 and pk!=0 and c_type='" + cb2.getSelectedItem() + "'");
			rs.next();
			String cType = rs.getString(1);		//获得客户类型编号
			
			int rcss = 0;						//表格记录指针
			for (int i = 0; i < tb2.getRowCount() * 2; i++) {
				sqlCode[i] = "insert into livein(pk,in_no,r_no,r_type_id,main_room," +
				"main_pk,c_type_id,c_name,sex,zj_type,zj_no,address,renshu,in_time,days," +
				"foregift,remark,userid,cluemark) values(" + (pkMain + rcss) + ",'" + inNumber + 
				"','" + dtm2.getValueAt(rcss, 0) + "','" + dtm2.getValueAt(rcss, 2) + "','" + roomMain + "'," + 
				pkMain + ",'" + cType + "','" + cName + "','" + sex + "','" + zjType + "','" + 
				zjNo + "','" + address + "','" + renShu + "','" + inTime + "','" + days + "','" +
				foregift + "','" + reMark + "','" + userid + "'," + cluemark + ")";//写入了一条入住信息
				
				i++;
				sqlCode[i] = "update roominfo set state='占用' where delmark=0 " +
				"and id='" + dtm2.getValueAt(rcss, 0) + "'";	//普通入住状态设置
				//更换房间状态图片
				RightTopPanel.setViewListButtonImage(dtm2.getValueAt(rcss, 2) + "", dtm2.getValueAt(rcss, 0) + "", "占用");
				
				rcss++;		//DTM指针 +1
		    }//Endfor
		    
		    //以事务的方式提交给数据库
		    int livins = sunsql.runTransaction(sqlCode);
		    if(livins > tb2.getRowCount() * 2) {
		    	JOptionPane.showMessageDialog(null, "开设房间操作失败，请检查网络" +
		    	"连接或联系管理员", "提示", JOptionPane.INFORMATION_MESSAGE);
		    	//如果事务失败的话，则恢复状态图片
		    	for (int i = 0; i < tb2.getRowCount(); i++) {
		    		RightTopPanel.setViewListButtonImage(dtm2.getValueAt(rcss, 0) + "", 
		    		dtm2.getValueAt(rcss, 0) + "", "可供");
			    }
				return;					//用户继续输入
		    }//Endif

			tf1.setText("");			//如果事务执行正常，则窗口控件清零
			tf2.setText("");
			tf3.setText("1");
			tf4.setText("0.00");
			tf5.setText("");
			tf6.setText("1");
			tf7.setText("");
			tf8.setText("");
			cb1.setSelectedIndex(0);
			cb3.setSelectedIndex(0);
			suntools.savNumber(inNumber, suntools.Number_RZ);//保存入住单号
			this.setVisible(false);		//返回主窗口
	    }
	    catch (Exception ex) {
	    	System.out.println ("Individual.saveLiveIn(): false");
	    }//End try
	}
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		try {
			ResultSet rs = null;
			if(o == tf1) {		//证件编码框获得焦点时回车
				//查找宾客以前入住记录
				rs = sunsql.executeQuery("select c_name,address from " +
				"livein where delmark=0 and zj_no='" + tf1.getText() + "'");
				
				if(rs.next()) {
					tf2.setText(rs.getString(1));	//宾客姓名
					tf7.setText(rs.getString(2));	//宾客地址
				}else {					//如果是第一次来的宾客则自测省份
					String gt = tf1.getText();
					if(gt.length() > 5 && gt.equals("370203")) {
						tf7.setText("原户籍：山东省青岛市市北区");
					}else if(gt.length() > 5 && gt.equals("370502")) {
						tf7.setText("原户籍：山东省东营市东营区");
					}else if(gt.length() > 5 && gt.equals("370603")) {
						tf7.setText("原户籍：山东省烟台市");
					}//Endif
				}//Endif
				tf2.requestFocus(true);		//将焦点给宾客名称		    
			}else if(o == tf2) {
				tf3.requestFocus(true);		//将焦点给宾客人数	
			}else if(o == tf3) {
				tf4.requestFocus(true);		//将焦点给预交押金
			}else if(o == tf4) {
				tf6.requestFocus(true);		//将焦点给预住天数
			}else if(o == tf6) {
				tf7.requestFocus(true);		//将焦点给宾客地址	
			}else if(o == tf7) {
				tf8.requestFocus(true);		//将焦点给备注
			}else if(o == cb) {
				rt = cb.getSelectedItem()+"";
				initDTM1(rt);				//以指定的房间类型可供房间类型
			}else if(o == bt1) {			//确定
				if(isValidity()) {
					int isAdd = JOptionPane.showConfirmDialog(null, "您确定以当前团体信息开设房间的吗？\n" +
					"主房间 [ " + tf5.getText() + " ] 　　主宾客 [ " + tf2.getText() + " ]", "提示", JOptionPane.YES_NO_OPTION);
					
					if(isAdd == JOptionPane.YES_OPTION) {
						saveLiveIn();					//保存入住信息
					}//Endif
				}//Endif
			}else if(o == bt2) {			//取消	清零所有控件
				tf1.setText("");
				tf2.setText("");
				tf3.setText("1");
				tf4.setText("0.00");
				tf5.setText("");
				tf6.setText("1");
				tf7.setText("");
				tf8.setText("");
				cb1.setSelectedIndex(0);
				cb3.setSelectedIndex(0);
				this.setVisible(false);
			}else if(o == bt3) {			//加入到开单房间
				addRoom();
			}else if(o == bt4) {			//移除开单房间
				subRoom();
			}//Endif
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	    	System.out.println ("Team.actionPerformed(): false");
	    }//End try
		
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
			"确认当前团体的入住信息   　　　　　　　　　　　　　　　　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"取消添加团体入住信息       　　　　　　　　　　　　　　　　");
		}else if(o == bt3) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"添加房间到团体开单区       　　　　　　　　　　　　　　　　");
		}else if(o == bt4) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"从开单区移除房间信息       　　　　　　　　　　　　　　　　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
	
}