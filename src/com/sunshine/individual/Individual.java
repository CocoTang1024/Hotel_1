/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 散客开单窗口
 *	[ 文件名      ]  : Individual.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 普通宾客开设房间窗口
 *	[ 作者        ]  : 董丰
 *	[ 版本        ]  : 1.1
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/22      1.0             董丰            创建
 *	2006/04/26      1.1             顾俊            完成功能
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :	
 *	
 *	[## public Individual(JFrame frame) {} ]:
 *		功能: 散客开单窗口
 *
 *	[## private void addListener() {} ]: 
 *		功能: 加事件监听
 *
 *	[## private void buildPanel() {} ]: 
 *		功能: 制作主面板
 *
 *	[## private void initDTM1() {} ]:
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
 *		功能: 保存宾客入住信息
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.individual;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;			//公共类库
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.*;	//主框架窗口


public class Individual 
extends JDialog 
implements ActionListener, MouseListener {
	
	public static JLabel lbA, lbB, lbC;			//主客房间,房间类型,预设单价
	public static JComboBox cb2;				//宾客类型
	public static JCheckBox chk1;				//钟点房否
	public static DefaultTableModel dtm1, dtm2;	//可供房间号列表的DTM
	
	private JPanel panelMain;
	private JTable tb1, tb2;
	private JScrollPane sp1, sp2;
	private JComboBox cb1, cb3;
               //证件类型, 性别
    private JCheckBox chk2;
                //到预住天数提醒
    private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf;
                   //证件码,主客,人数,地址,备注,折扣,实价,天数,押金,标题
    private JButton bt1, bt2, bt3, bt4;
                  //确定,取消,添加,移除
    private JTabbedPane tp;
    
    String cType;							//宾客类型编号
    String roomType = "";					//房间类型
    
    private double disPrice = 0;			//折扣价
    private int zRooms = 0;					//追加客房计数器
    
    
    /**=======================================================================**
	 *		[## public Individual(JFrame frame) {} ]: 			构造函数
	 *			参数   ：JFrame frame表示本对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：散客开单窗口
	 **=======================================================================**
	 */
	public Individual(JFrame frame) {
		super (frame, "散客开单", true);
		panelMain = new JPanel(new BorderLayout());	//主面板为边界布局,确定取消按钮放South
		buildPanel();
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (530,510));
		this.setMinimumSize (new Dimension (530, 510));
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
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
		tf5.addActionListener(this);
		tf8.addActionListener(this);
		cb2.addActionListener(this);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
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
	 *			功能   ：制作面板
	 **=======================================================================**
	 */
	private void buildPanel() {
		
		JLabel lb0, lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9, lb10, lb11, 
		lb12, lb13, lb14, lb15;
		
		lb0 = new JLabel("主客房间：");
		lb1 = new JLabel("房间类型：");
		lb2 = new JLabel("　　　  预设单价：");
		lb3 = new JLabel("证件类型：");
		lb4 = new JLabel("        证件编码：");
		lb5 = new JLabel("       主客性别：");
		lb6 = new JLabel("宾客类型：");
		lb7 = new JLabel("        主客姓名：");
		lb8 = new JLabel("       宾客人数：");
		lb9 = new JLabel("地址信息：");
		lb10 = new JLabel("备注信息：");
		lb11 = new JLabel("折扣比例：");
		lb12 = new JLabel("    实际单价：");
		lb13 = new JLabel("     预住天数：");
		lb14 = new JLabel("    实收押金：");
		lb15 = new JLabel("　　　　注：只能追加同类房间，最多4间！若要追加不同类型的房间请选择“团体开单”");
		lbA = new JLabel("BD1001");///////////////////////////////////////////
		lbB = new JLabel("标准单人间　");///////////////////////////////////////////
		lbC = new JLabel("150.00");///////////////////////////////////////////
		
		//下拉列表
		cb1 = new JComboBox();
		cb1.addItem("身份证");
		cb1.addItem("驾驶证");
		cb1.addItem("学生证");
		cb1.addItem("军官证");
		cb1.addItem("护照");
		cb1.addItem("其他");
		//宾客类型
		cb2 = new JComboBox();
		cb3 = new JComboBox();
		cb3.addItem("男         ");
		cb3.addItem("女         ");
		
		//文本框
		tf1 = new TJTextField(10);
		tf2 = new TJTextField(10);
		tf3 = new TJTextField("1", 6);
		tf4 = new TJTextField(40);
		tf5 = new TJTextField(40);
		tf6 = new JTextField(3);
		tf7 = new JTextField("￥0.00", 6);
		tf8 = new TJTextField("1", 4);
		tf9 = new TJMoneyField();
		tf  = new JTextField("开单信息");
		tf6.setEditable(false);
		tf7.setEditable(false);
		tf3.setHorizontalAlignment (JTextField.RIGHT);
		tf6.setHorizontalAlignment (JTextField.RIGHT);
		tf7.setHorizontalAlignment (JTextField.RIGHT);
		tf8.setHorizontalAlignment (JTextField.RIGHT);
		tf.setHorizontalAlignment (JTextField.CENTER);
		tf.setBackground(new Color(199, 183, 143));
		tf.setBorder(new LineBorder(new Color(87, 87, 47)));
		tf.setEditable(false);
		
		//多选钮
		chk1 = new JCheckBox("　钟点房");
		chk2 = new JCheckBox("   到预住天数时自动提醒");
		
		//按钮
		bt1 = new TJButton("pic/modi3.gif", " 确 定 ", "开单");
		bt2 = new TJButton("pic/cancel.gif"," 取 消 ", "取消开单");
		bt3 = new TJButton("pic/right.gif", "", "添加到开单区");
		bt4 = new TJButton("pic/left.gif",  "", "从开单区删除");
		bt3.setBorderPainted(false);	//无外框
		bt4.setBorderPainted(false);
		bt3.setFocusPainted(false);		//无焦点框
		bt4.setFocusPainted(false);
		bt3.setContentAreaFilled(false);//设置透明色
		bt4.setContentAreaFilled(false);
		
		//面板
		JPanel ps, pc, pcc, pcc1, pcc11, pcc12, pcc13,
			   pcc2, pcc21, pcc22, pcd1, pcc23, pcc24, pcd2, pcc25, pcc26, pcd3, 
			   pcc3, pcc31, pcc32,
			   pcc4, pcc41, pcc42, pcc43, pcc44,
			   pcc5, pcc6,  pcc7;
			   
		JLabel line0 = new JLabel(new ImageIcon("pic/line5.gif"));//分隔线
		JLabel line1 = new JLabel(new ImageIcon("pic/line4.gif"));//分隔线
		JLabel line2 = new JLabel(new ImageIcon("pic/line4.gif"));//分隔线
		
		ps = new JPanel(new FlowLayout(FlowLayout.CENTER,50,5));  	//放置确定和取消按钮	
		pc = new JPanel(new BorderLayout(0,5));					  	//放置按钮以上的部分
		pcc = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0)); 	//开单信息文本框为不可编辑,放在North,其他放在流布局的面板中
		pcc1 = new JPanel(new GridLayout(1, 3));					//放置"主客房间,房间类型,预设单价"...一行
		pcc11 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));	//放置主客房间
		pcc12 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));	//放置房间类型
		pcc13 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));	//放置预设单价
		pcc2  = new JPanel(new FlowLayout(FlowLayout.LEFT,10,3));  //放置"证件类型...,宾客类型..."两行
		pcc21 = new JPanel(new GridLayout(2,1,5,6));//放置证件类型,宾客类型
		pcc22 = new JPanel(new GridLayout(2,1,5,6));//放置证件类型,宾客类型下拉框
		pcd1  = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置pcc21,pcc22
		pcc23 = new JPanel(new GridLayout(2,1,5,6));//放置证件编码,主客姓名
		pcc24 = new JPanel(new GridLayout(2,1,5,6));//放置证件编码,主客姓名文本框
		pcd2  = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置pcc23,pcc24
		pcc25 = new JPanel(new GridLayout(2,1,5,6));//放置主客性别,宾客人数
		pcc26 = new JPanel(new GridLayout(2,1,5,6));//放置主客性别下拉框,宾客人数文本框
		pcd3  = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置pcc25,pcc26
		pcc3  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));  //放置"地址信息,备注信息"两行
		pcc31 = new JPanel(new GridLayout(2,1,0,6));//放置地址信息,备注信息
		pcc32 = new JPanel(new GridLayout(2,1,0,6));//放置地址信息,备注信息文本框
		pcc4  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));//放置"打折比例,实际单价,预住天数,实收押金"一行
		pcc41 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置打折比例
		pcc42 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置实际单价
		pcc43 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置预住天数
		pcc44 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));//放置实收押金
		pcc5  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));   //放置两个chk
		pcc6  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));   //放置tp
		pcc7  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3));   //放置"注:......."
		
		
		
		pcc11.add(lb0);
		pcc11.add(lbA);
		pcc12.add(lb1);
		pcc12.add(lbB);
		pcc13.add(lb2);
		pcc13.add(lbC);
		pcc1.add(pcc11);
		pcc1.add(pcc12);
		pcc1.add(pcc13);
		lbA.setFont(new Font("宋体",Font.BOLD,15));
		lbA.setForeground(Color.BLUE);
		lbB.setFont(new Font("宋体",Font.BOLD,15));
		lbB.setForeground(Color.BLUE);
		lbC.setFont(new Font("宋体",Font.BOLD,15));
		lbC.setForeground(Color.RED);
		
		//构造证件类型,宾客类型两行
		pcc21.add(lb3);		
		pcc21.add(lb6);
		pcc22.add(cb1);		
		pcc22.add(cb2);	
		pcc23.add(lb4);	
		pcc23.add(lb7);	
		pcc24.add(tf1);
		pcc24.add(tf2);
		pcc25.add(lb5);
		pcc25.add(lb8);
		pcc26.add(cb3);
		pcc26.add(tf3);
		pcd1.add(pcc21);
		pcd1.add(pcc22);
		pcd2.add(pcc23);
		pcd2.add(pcc24);
		pcd3.add(pcc25);
		pcd3.add(pcc26);
		pcc2.add(pcd1);
		pcc2.add(pcd2);
		pcc2.add(pcd3);
		
		//构造地址信息,备注信息两行
		pcc31.add(lb9);
		pcc31.add(lb10);
		pcc32.add(tf4);
		pcc32.add(tf5);
		pcc3.add(pcc31);
		pcc3.add(pcc32);
		
		//构造打折比例,实际单价,预注天数,实收押金两行
		pcc41.add(lb11);
		pcc41.add(tf6);
		pcc42.add(lb12);
		pcc42.add(tf7);
		pcc43.add(lb13);
		pcc43.add(tf8);
		pcc44.add(lb14);
		pcc44.add(tf9);
		pcc4.add(pcc41);
		pcc4.add(pcc42);
		pcc4.add(pcc43);
		pcc4.add(pcc44);
		
		//构造钟点房一行
		JLabel temp4,temp5;
		temp4 = new JLabel("             ");
		temp5 = new JLabel("                                                                          ");
		pcc5.add(chk1);
		pcc5.add(temp4);
		pcc5.add(chk2);
		pcc5.add(temp5);
		
		//构造JTabbedPane
		tp = new JTabbedPane();
		//======================================================================
		JLabel zlb1, zlb2, zlb3;				//假空格
		JTextField ztf0 ,ztf1;					//标题文本，也假的
		JPanel zjMain, zj0, zj1, zj2;
		
		zlb1 = new JLabel("　");
		zlb2 = new JLabel("　");
		zlb3 = new JLabel("　");
		ztf0 = new JTextField("可供房间", 18);
		ztf1 = new JTextField("开单房间", 18);
		
		zjMain = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		zj0    = new JPanel(new BorderLayout());	//可供房间
		zj1    = new JPanel(new BorderLayout());	//开单房间
		zj2    = new JPanel(new GridLayout(5, 1));	//添加按键
		
		//左边表格
		dtm1 = new DefaultTableModel();
		tb1  = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		tb1.setTableHeader(null);
		tb1.setShowHorizontalLines(false);
		tb1.setForeground(new Color( 87,  87,  47));
		tb1.setBackground(new Color(248, 242, 230));
		//设置表格显示的尺寸
		tb1.setPreferredScrollableViewportSize(new Dimension(80,100));
		/////////////////////////////////////////做表格
		
		//右边表格
		dtm2 = new DefaultTableModel();
		tb2  = new JTable(dtm2);
		sp2  = new JScrollPane(tb2);
		tb2.setTableHeader(null);
		tb2.setShowHorizontalLines(false);
		tb2.setForeground(new Color( 87,  87,  47));
		tb2.setBackground(new Color(248, 242, 230));
		//设置表格显示的尺寸
		tb2.setPreferredScrollableViewportSize(new Dimension(80,100));
		/////////////////////////////////////////做表格
		sp1	   = new JScrollPane(tb1);
		sp2	   = new JScrollPane(tb2);
		
		//设置标题文字居中
		ztf0.setHorizontalAlignment (JTextField.CENTER);
		ztf1.setHorizontalAlignment (JTextField.CENTER);
		//设置标题文本框背景色
		ztf0.setBackground(new Color(199,183,143));
		ztf1.setBackground(new Color(199,183,143));
		//设置标题文本外框
		ztf0.setBorder(new LineBorder(new Color(87,87,47)));
		ztf1.setBorder(new LineBorder(new Color(87,87,47)));
		//设置文本不可编辑
		ztf0.setEditable(false);
		ztf1.setEditable(false);
		
		//加入组件
		zj2.add(zlb1);			//加入添加按键
		zj2.add(bt3);
		zj2.add(zlb2);
		zj2.add(bt4);
		zj2.add(zlb3);
		
		zj0.add("North", ztf0);
		zj0.add("Center", sp1);
		zj1.add("North", ztf1);
		zj1.add("Center", sp2);
		
		//加入追加房间面板
		zjMain.add(zj0);
		zjMain.add(zj2);
		zjMain.add(zj1);
		
		tp.addTab("追 加 房 间", zjMain);
		//======================================================================
		pcc6.add(tp);
			
		//构造"注:.................."	
		JLabel temp10 = new JLabel("                                 ");
		lb15.setForeground(Color.red);
		pcc7.add(lb15);
		pcc7.add(temp10);
		
		//将开单信息加入面板
		pcc.add(pcc1);
		pcc.add(line1);
		pcc.add(pcc2);
		pcc.add(pcc3);
		pcc.add(pcc4);
		pcc.add(pcc5);
		pcc.add(line2);
		pcc.add(pcc6);
		pcc.add(pcc7);
		
		
		pc.add("North",tf);
		pc.add(pcc);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		ps.add(bt1);
		ps.add(bt2);
		
		panelMain.add("South",ps);
		panelMain.add(pc);
	}
	
	/**=======================================================================**
	 *		[## private void initDTM1() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化可供房间列表
	 **=======================================================================**
	 */
	private void initDTM1() {
		sunsql.initDTM(Individual.dtm1,"select a.id 房间编号1 from roominfo " +
		"a,(select id from roomtype where r_type='" + lbB.getText() + 
		"') b where a.delmark=0 and a.indimark=0 and a.state='可供' and a.r_type_id=b.id");
	}
	
	/**=======================================================================**
	 *		[## private void initDTM2() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：初始化开单房间列表
	 **=======================================================================**
	 */
	private void initDTM2() {
		sunsql.initDTM(Individual.dtm2,"select roomid 房间编号 from roomnum");
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
		if(arows.length + tb2.getRowCount() > 5) {
			JOptionPane.showMessageDialog(null, "最多只能追加四间客房，" +
			"入住五间以上客房请使用团体开单", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int ar = 0;
		int zr = zRooms;							//记录房间数量，配合事务工作
		String sqlCode[]  = new String[arows.length * 2];
		if(arows.length > 0) {
			for (int i = 0; i < arows.length; i++) {
				sqlCode[ar] = "insert into roomnum(roomid) values('" +
				dtm1.getValueAt(arows[i], 0) + "')";//加入开单列表
				ar++;
				//清除可供列表
				sqlCode[ar] = "update roominfo set indimark=1 where " +
				"delmark=0 and id='" + dtm1.getValueAt(arows[i], 0) + "'";
				ar++;
				zRooms++;						//记数器 +1
			}//Endfor
			int flag = sunsql.runTransaction(sqlCode);
			
			if(flag < arows.length) {
				JOptionPane.showMessageDialog(null, "添加失败，请检查网络情况", 
				"提示", JOptionPane.INFORMATION_MESSAGE);
				zRooms = zr;
				return;
			}//Endif
			initDTM1();		//刷新可供列表
			initDTM2();		//刷新开单列表
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
		if(tb2.getRowCount() - arows.length < 1) {
			JOptionPane.showMessageDialog(null, "[ 散客开单 ] 至少要开设一个房间", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int ar = 0;
		int zr = zRooms;							//记录房间数量，配合事务工作
		String sqlCode[]  = new String[arows.length * 2];
		if(arows.length > 0) {
			for (int i = 0; i < arows.length; i++) {
				sqlCode[ar] = dtm2.getValueAt(arows[i], 0) + "";
				if(!sqlCode[ar].equals(lbA.getText())) {		//判断主房间不能删除
					sqlCode[ar] = "delete from roomnum where roomid='" +
					dtm2.getValueAt(arows[i], 0) + "'";//移除开单列表
					ar++;
					//清除可供列表
					sqlCode[ar] = "update roominfo set indimark=0 where " +
					"delmark=0 and id='" + dtm2.getValueAt(arows[i], 0) + "'";
					ar++;
					zRooms--;				//记数器 -1	
				}else {
					JOptionPane.showMessageDialog(null, "[ " + dtm2.getValueAt(arows[i], 0) + 
					" ] 房间是主房间，不能移除 ...", "提示", JOptionPane.INFORMATION_MESSAGE);
					zRooms = ar;
					return;
				}//Endif
			}//Endfor
			int flag = sunsql.runTransaction(sqlCode);
			
			if(flag < arows.length) {
				JOptionPane.showMessageDialog(null, "移除失败，请检查网络情况", 
				"提示", JOptionPane.INFORMATION_MESSAGE);
				zRooms = zr;
				return;
			}//Endif
			initDTM1();		//刷新可供列表
			initDTM2();		//刷新开单列表
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
		}else if(tf4.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "[ 地址信息 ] 不能为空", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf4.requestFocus(true);
			return false;
		}
		else if(!suntools.isNum(tf3.getText(), 2, 1, 15)) {
			JOptionPane.showMessageDialog(null, "散客开单中 [ 宾客人数 ] 至少是1人，" +
			"最多15人", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf3.setText("1");
			tf3.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf8.getText(), 2, 1, 99)) {
			JOptionPane.showMessageDialog(null, "散客开单的 [ 预住天数 ] 至少是1天，" +
			"最多99天，超过三个月的请到VIP部办理包房", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf8.setText("1");
			tf8.requestFocus(true);
			return false;
		}else if(!suntools.isNum(tf9.getText(), 7, 100, 8000)) {
			JOptionPane.showMessageDialog(null, "散客开单的 [ 实收押金 ] 最少为100元，" +
			"最多8000元，超过8000元宾客的请到VIP部办理金卡", "提示", JOptionPane.INFORMATION_MESSAGE);
			tf8.requestFocus(true);
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
		String roomMain = lbA.getText();				//主房间号
		String cName	= tf2.getText();				//客户名称
		String sex		= cb3.getSelectedItem().toString().trim();	//性别
		String zjType	= cb1.getSelectedItem() + "";	//证件类型
		String zjNo		= tf1.getText();				//证件编号
		String address	= tf4.getText();				//地址
		String renShu	= tf3.getText();				//人数
		String inTime	= Journal.getNowDTime();		//入住时间
		String days		= tf8.getText();				//预注天数
		String account	= tb2.getRowCount() +"";		//消费数量
		String foregift = tf9.getText();				//押金
		String reMark	= tf5.getText();				//备注
		String userid	= HotelFrame.userid;			//经手人
		int cluemark 	= 0;							//提醒标志
		if(chk2.isSelected())
			cluemark	= 1;
		String sqlCode[]= new String[tb2.getRowCount() * 2];	//要存数数，还要改状态，所以是两倍大小的SQL数组
		try {
			int rcss = 0;					//表格记录指针
			for (int i = 0; i < tb2.getRowCount() * 2; i++) {
				sqlCode[i] = "insert into livein(pk,in_no,r_no,r_type_id,main_room," +
				"main_pk,c_type_id,c_name,sex,zj_type,zj_no,address,renshu,in_time,days," +
				"foregift,remark,userid,statemark,cluemark) values(" + (pkMain + rcss) + ",'" + inNumber + 
				"','" + dtm2.getValueAt(rcss, 0) + "','" + roomType + "','" + roomMain + "'," + 
				pkMain + ",'" + cType + "','" + cName + "','" + sex + "','" + zjType + "','" + 
				zjNo + "','" + address + "','" + renShu + "','" + inTime + "','" + days + "','" +
				foregift + "','" + reMark + "','" + userid + "','正在消费'," + cluemark + ")";//写入了一条入住信息
				
				i++;
				if(chk1.isSelected()) {	
					sqlCode[i] = "update roominfo set state='钟点' where delmark=0 " +
					"and id='" + dtm2.getValueAt(rcss, 0) + "'";	//钟点房状态设置
					//更换房间状态图片
					RightTopPanel.setViewListButtonImage(roomType, dtm2.getValueAt(rcss, 0) + "", "钟点");
				}else {
					sqlCode[i] = "update roominfo set state='占用' where delmark=0 " +
					"and id='" + dtm2.getValueAt(rcss, 0) + "'";	//普通入住状态设置
					//更换房间状态图片
					RightTopPanel.setViewListButtonImage(roomType, dtm2.getValueAt(rcss, 0) + "", "占用");
				}//Endif
				
				rcss++;		//DTM指针 +1
		    }//Endfor
		    
		    //以事务的方式提交给数据库
		    int livins = sunsql.runTransaction(sqlCode);
		    if(livins < tb2.getRowCount() * 2) {
		    	JOptionPane.showMessageDialog(null, "开设房间操作失败，请检查网络" +
		    	"连接或联系管理员", "提示", JOptionPane.INFORMATION_MESSAGE);
		    	//如果事务失败的话，则恢复状态图片
		    	for (int i = 0; i < tb2.getRowCount(); i++) {
		    		RightTopPanel.setViewListButtonImage(roomType, dtm2.getValueAt(rcss, 0) + "", "可供");
			    }
				return;					//用户继续输入
		    }//Endif
		    tf1.setText("");			//如果事务执行正常，则窗口控件清零
			tf2.setText("");
			tf3.setText("1");
			tf4.setText("");
			tf5.setText("");
			tf8.setText("1");
			tf9.setText("0.00");
			cb1.setSelectedIndex(0);
			cb3.setSelectedIndex(0);
			suntools.savNumber(inNumber, suntools.Number_RZ);
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
			if(o == tf1) {					//证件编码框获得焦点时回车
				//查找宾客以前入住记录
				rs = sunsql.executeQuery("select c_name,address from " +
				"livein where delmark=0 and zj_no='" + tf1.getText() + "'");
				
				if(rs.next()) {
					tf2.setText(rs.getString(1));	//宾客姓名
					tf4.setText(rs.getString(2));	//宾客地址
				}else {					//如果是第一次来的宾客则自测省份
					String gt = tf1.getText();
					if(gt.length() > 5 && gt.equals("370203")) {
						tf4.setText("原户籍：山东省青岛市市北区");
					}else if(gt.length() > 5 && gt.equals("370502")) {
						tf4.setText("原户籍：山东省东营市东营区");
					}else if(gt.length() > 5 && gt.equals("370603")) {
						tf4.setText("原户籍：山东省烟台市");
					}//Endif
				}//Endif
				tf2.requestFocus(true);		//将焦点给宾客名称		    
			}else if(o == tf2) {
				tf3.requestFocus(true);		//将焦点给宾客人数
			}else if(o == tf3) {
				tf4.requestFocus(true);		//将焦点给宾客地址
			}else if(o == tf4) {
				tf5.requestFocus(true);		//将焦点给备注
			}else if(o == tf5) {
				tf8.requestFocus(true);		//将焦点给预住天数
			}else if(o == tf8) {
				//获得房间的预设押金
				rs = sunsql.executeQuery("select foregift from roomtype where " +
				"delmark=0 and r_type='" + lbB.getText() + "'");
				if(rs.next()) {
					tf9.setText(rs.getDouble(1) + "");	//预交押金
				}//Endif
			}else if(o == cb2) {
				//自动折扣计算
				rs = sunsql.executeQuery("select a.discount,a.dis_price,a.id,b.id from " +
				"customertype a,(select id from roomtype where delmark=0 and " +
				"r_type='" + lbB.getText() + "') b where a.delmark=0 and " +
				"a.c_type='" + cb2.getSelectedItem() + "' and a.dis_attr=b.id");
				if(rs.next()) {
					tf6.setText(rs.getString(1));	//折扣
					disPrice = rs.getDouble(2);		//折扣价
					tf7.setText("￥" + disPrice);
					cType = rs.getString(3);
					roomType  = rs.getString(4);
				}//Endif
				tf1.requestFocus(true);				//确定
			}else if(o == bt1) {
				if(isValidity()) {
					int isAdd = JOptionPane.showConfirmDialog(null, "您确定以当前宾客信息，以 [ " + 
					lbA.getText() + " ] 为主房间，开设房间吗？", "提示", JOptionPane.YES_NO_OPTION);
					
					if(isAdd == JOptionPane.YES_OPTION) {
						saveLiveIn();					//保存入住信息
					}//Endif
				}//Endif
			}else if(o == bt2) {					//取消
				tf1.setText("");
				tf2.setText("");
				tf3.setText("1");
				tf4.setText("");
				tf5.setText("");
				tf8.setText("1");
				tf9.setText("0.00");
				cb1.setSelectedIndex(0);
				cb3.setSelectedIndex(0);
				this.setVisible(false);
			}else if(o == bt3) {					//加入到开单房间
				if(zRooms < 4) {					//检查房间数量
					addRoom();
				}else {
					JOptionPane.showMessageDialog(null, "最多只能追加四间客房，" +
					"入住五间以上客房请使用团体开单", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}//Endif
			}else if(o == bt4) {
				if(zRooms >= 0) {					//检查房间数量
					subRoom();
				}else {
					JOptionPane.showMessageDialog(null, "[ 散客开单 ] 至少要开设一个房间", 
					"提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}//Endif
			}//Endif
		}
		catch (Exception ex) {
			//ex.printStackTrace();
			System.out.println ("Individual.actionPerformed: false");
		}//End try
	}
	
	
	/**=======================================================================**
	 *			MouseListener 监听
	 **=======================================================================**
	 */
	public void mouseClicked (MouseEvent me) {
	}

	public void mousePressed (MouseEvent me) {
		Object o = me.getSource();
		if(o == bt3) {
			bt3.setIcon(new ImageIcon("pic/right2.gif"));
		}if(o == bt4) {
			bt4.setIcon(new ImageIcon("pic/left2.gif"));
		}//Endif
	}

	public void mouseReleased(MouseEvent me) {
		Object o = me.getSource();
		if(o == bt3) {
			bt3.setIcon(new ImageIcon("pic/right.gif"));
		}if(o == bt4) {
			bt4.setIcon(new ImageIcon("pic/left.gif"));
		}//Endif
	}

	public void mouseEntered (MouseEvent me) {		//鼠标移进提示
		Object o = me.getSource ();
		if(o == bt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"确认当前宾客的入住信息   　　　　　　　　　　　　　　　　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"取消添加入住信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt3) {
			bt3.setIcon(new ImageIcon("pic/right1.gif"));
		}else if(o == bt4) {
			bt4.setIcon(new ImageIcon("pic/left1.gif"));
		}//Endif
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
		bt3.setIcon(new ImageIcon("pic/right.gif"));
		bt4.setIcon(new ImageIcon("pic/left.gif"));
	}
	
}