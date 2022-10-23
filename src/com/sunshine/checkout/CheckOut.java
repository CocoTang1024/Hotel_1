package com.sunshine.checkout;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;			//公共类
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.*;			//主框架窗口


public class CheckOut 
extends JDialog 
implements ActionListener, MouseListener {
	
	public JLabel lbA, lbB, lbC, lbD, lbE, lbF, lbG, lbH;
	           //账单号,房间,姓名,金额,应收,押金,优惠,找零
	public DefaultTableModel dtm;
	public JTable tb;
	
	public JTextField tf1, tf2, tf3, tf;
	                 //实收,支付,备注
	private JButton bt1, bt2;
				 //结账,取消
	private JScrollPane sp;
	private JPanel panelMain,p1,p2;
	
	public String inNo = "";		//入住单号
	
	
	//构造函数
	public CheckOut(JFrame frame) {
		super (frame, "收银结账", true);
		
		panelMain = new JPanel(new GridLayout(2, 1, 0, 0));		//主面板为(2,1)表格布局
		p1        = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));//上半部分为(4,1)表格布局
		p2        = new JPanel(new BorderLayout(15, 0));		//下半部分为边界布局
		
		buildP1();//构造上半面板
		buildP2();//构造下半面板
		
		panelMain.add(p1);
		panelMain.add(p2);
		
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension(640, 450));
		this.setMinimumSize (new Dimension(640, 450));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	//加事件监听
	private void addListener() {
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
	}
	
	//构造上半面板
	private void buildP1() {
		JPanel p11,p111,p112,p113,
			   p12,p121,p122,p123,p124,
			   p13,p131,p132,p133,
			   p14,p141,p142,p15;
		JLabel lb1,lb2,lb3,lb4,lb5,lb6,lb7,lb8,lb9,lb10,lb11;
		p11 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,3));	//放置结账单号...一行
		p111 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置结账单号
		p112 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置结账房间
		p113 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置宾客姓名
		p12  = new JPanel(new FlowLayout(FlowLayout.LEFT,10,3));//放置应收金额...一行
		p121 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置应收金额
		p122 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置已收押金
		p123 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置优惠金额
		p124 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置消费金额
		p13  = new JPanel(new FlowLayout(FlowLayout.LEFT,10,3));//放置实收金额...一行
		p131 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置实收金额
		p132 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置宾客支付
		p133 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置找零
		p14  = new JPanel(new FlowLayout(FlowLayout.LEFT,10,3));//放置结账备注...一行
		p141 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,3)); //放置结账备注
		p142 = new JPanel(new FlowLayout(FlowLayout.LEFT,33,3)); //放置按钮
		p15  = new JPanel(new GridLayout(3,1,0,5));
		lb1 = new JLabel("结账单号：");
		lb2 = new JLabel("       结账房间：");
		lb3 = new JLabel("       宾客姓名：");
		lb4 = new JLabel("消费金额：");
		lb5 = new JLabel(" 应收金额：");
		lb6 = new JLabel(" 已收押金：");
		lb7 = new JLabel(" 优惠金额：");
		lb8 = new JLabel("实收金额：");
		lb9 = new JLabel("      宾客支付：");
		lb10 = new JLabel("   找     零：");
		lb11 = new JLabel("结账备注：");
		lb1.setFont(new Font("宋体",Font.BOLD,15));
		lb2.setFont(new Font("宋体",Font.BOLD,15));
		lb3.setFont(new Font("宋体",Font.BOLD,15));
		lb4.setFont(new Font("宋体",Font.BOLD,15));
		lb5.setFont(new Font("宋体",Font.BOLD,15));
		lb6.setFont(new Font("宋体",Font.BOLD,15));
		lb7.setFont(new Font("宋体",Font.BOLD,15));
		lb10.setFont(new Font("宋体",Font.BOLD,15));
		lb4.setForeground(Color.blue);
		lb5.setForeground(Color.blue);
		lb6.setForeground(Color.blue);
		lb7.setForeground(Color.blue);
		lb10.setForeground(Color.blue);
		////////////////////////////////////////////////////////////////////////
		lbA = new JLabel("ZD200604210001");
		lbB = new JLabel("BD001");
		lbC = new JLabel("你好吗");
		lbD = new JLabel("500.00");
		lbE = new JLabel("450.00");
		lbF = new JLabel("300.00");
		lbG = new JLabel("50.00");
		lbH = new JLabel("￥0.00");
		////////////////////////////////////////////////////////////////////////
		lbA.setFont(new Font("宋体",Font.BOLD,15));
		lbB.setFont(new Font("宋体",Font.BOLD,15));
		lbC.setFont(new Font("宋体",Font.BOLD,15));
		lbD.setFont(new Font("宋体",Font.BOLD,15));
		lbE.setFont(new Font("宋体",Font.BOLD,15));
		lbF.setFont(new Font("宋体",Font.BOLD,15));
		lbG.setFont(new Font("宋体",Font.BOLD,15));
		lbH.setFont(new Font("宋体",Font.BOLD,15));
		lbA.setForeground(Color.blue);
		lbB.setForeground(Color.blue);
		lbC.setForeground(Color.blue);
		lbD.setForeground(Color.blue);
		lbE.setForeground(Color.red);
		lbF.setForeground(Color.red);
		lbG.setForeground(Color.red);
		lbH.setForeground(Color.red);
		tf1 = new TJMoneyField("0.00",10);
		tf2 = new TJMoneyField("0.00",10);
		tf3 = new TJTextField(20);
		
		bt1 = new TJButton("pic/u04.gif", " 结   账 ", "结算房费");
		bt2 = new TJButton("pic/cancel.gif", " 取   消 ", "取消结账");
		
		//第一行
		p111.add(lb1);
		p111.add(lbA);
		p112.add(lb2);
		p112.add(lbB);
		p113.add(lb3);
		p113.add(lbC);
		p11.add(p111);
		p11.add(p112);
		p11.add(p113);
		p11.setBorder(BorderFactory.createTitledBorder(""));
		
		//第二行
		p121.add(lb4);
		p121.add(lbD);
		p122.add(lb5);
		p122.add(lbE);
		p123.add(lb6);
		p123.add(lbF);
		p124.add(lb7);
		p124.add(lbG);
		p12.add(p121);
		p12.add(p122);
		p12.add(p123);
		p12.add(p124);
		
		//第三行
		p131.add(lb8);
		p131.add(tf1);
		p132.add(lb9);
		p132.add(tf2);
		p133.add(lb10);
		p133.add(lbH);
		p13.add(p131);
		p13.add(p132);
		p13.add(p133);
		
		//第四行
		p141.add(lb11);
		p141.add(tf3);
		p142.add(bt1);
		p142.add(bt2);
		p14.add(p141);
		p14.add(p142);
		
		p15.add(p12);
		p15.add(p13);
		p15.add(p14);
		p15.setBorder(BorderFactory.createTitledBorder(""));
		p1.add(p11);
		p1.add(p15);
	}
	
	//构造下半面板
	private void buildP2() {
		JPanel p2c;
		p2c = new JPanel(new GridLayout(1,1));
		tf = new JTextField("结账区内房间消费清单");
		tf.setEditable(false);
		tf.setBorder(new LineBorder(new Color(87,87,47)));
		tf.setBackground(new Color(199,183,143));
		tf.setHorizontalAlignment(JTextField.CENTER);
		dtm = new DefaultTableModel();
		tb	= new JTable(dtm);
		sp	= new JScrollPane(tb);
		///////////////////////填表格
		p2c.add(sp);
		p2.add("North",tf);
		p2.add(p2c);
		p2.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	//初始化DTM
	public void initDTM() {
		sunsql.initDTM(dtm, "select pk,r_type_id,r_no 房间号,price 单价,discount 折扣," +
		"dis_price 折扣价,account 消费天数,money 消费金额,in_time 消费时间 from checkout_temp");
		tb.removeColumn(tb.getColumn("pk"));
		tb.removeColumn(tb.getColumn("r_type_id"));
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
		double t1 = Double.parseDouble(tf1.getText());//实收金额
		double t2 = Double.parseDouble(lbE.getText());//应收金额
		double t3 = Double.parseDouble(lbH.getText());//找零
		if(t1 < t2) {
			JOptionPane.showMessageDialog(null, "[ 实收金额 ] 不能小于 [ 应收金额 ]", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf1.requestFocus(true);
			return false;
		}else if(t3 < 0) {
			JOptionPane.showMessageDialog(null, "请宾客支付足够的消费费用", 
			"提示", JOptionPane.INFORMATION_MESSAGE);
			tf2.requestFocus(true);
			return false;
		}//Endif
		return true;
	}
	
	//保存结算数据
	private void saveCKO() {
		long ckPK = sunsql.getPrimaryKey();			//获得结算记录首PK
		String chNO = suntools.getNumber(suntools.Number_JS);	//获得结算单号
		String chkTime = Journal.getNowDTime();		//结算时间
		String reMark  = tf3.getText();				//备注
		int count = tb.getRowCount();				//得到有几点结算记录
		String sqlCode[] = new String[count * 3];	//创建SQL语句数组
		String riState = "可供";					//房间状态
		String stateTime = "0";						//房间的状态计时
		int flag = Integer.parseInt(sunini.getIniKey("Ck_Habitus"));//结算后的房间状态  0:可供 1:清理	
		if(flag == 1) {
			riState = "脏房";
			stateTime = sunini.getIniKey("Ck_Minute");
		}//Endif
		int sc=0;
		for (int i = 0; i < count * 3; i++) {
			//向结算表加数据
			sqlCode[i] = "insert into checkout(pk,chk_no,in_no,days,money,chk_time,remark) values(" +
			(ckPK + sc) + ",'" + chNO + "','" + inNo + "'," + dtm.getValueAt(sc, 6) + "," + 
			dtm.getValueAt(sc, 7) + ",'" + chkTime + "','" + reMark + "')";
			i++;
			//更改入住信息表里的记录状态为已结算
			sqlCode[i] = "update livein set statemark='已结算' where pk='" + dtm.getValueAt(sc, 0) + "'";
			i++;
			//更改房间状态
			sqlCode[i] = "update roominfo set state='" + riState + "',statetime=" + stateTime + " where delmark=0 and id='" + 
			dtm.getValueAt(sc, 2) + "'";

			sc++;				//DTM指针+1
	    }//Endfor
	    
	    flag = sunsql.runTransaction(sqlCode);		//执行事务操作
	    
	    if(flag < sqlCode.length) {
		    JOptionPane.showMessageDialog(null, "开设房间操作失败，请检查网络" +
		    "连接或联系管理员", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;					//用户继续输入
		}else if(flag == sqlCode.length) {
			//如果事务成功，则更改状态图片
			for (int i = 0; i < tb.getRowCount(); i++) {
		    	//更改状态图片
				RightTopPanel.setViewListButtonImage(dtm.getValueAt(i, 1) + "", 
				dtm.getValueAt(i, 2) + "", riState);
			}//Endfor
			tf1.setText("0.00");		//清空控件
			tf2.setText("0.00");
			tf3.setText("");
			this.setVisible(false);		//关闭窗口
		}//Endif
	}
	
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == bt1) {
			if(isValidity()) {
				saveCKO();				//保存结算数据
			}//Endif
		}else if(o == bt2) {			//取消操作
			tf1.setText("0.00");		//所有文本框清零
			tf2.setText("0.00");
			tf3.setText("");
			this.setVisible(false);		//关闭窗口
		}//Endif
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
			"结算当前房间的消费项目   　　　　　　　　　　　　　　　　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"取消结算操作，返回主窗口  　　　　　　　　　　　　　　　 ");
		}//Endif
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
	
}