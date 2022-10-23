package com.sunshine.customer;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;
import com.sunshine.sunsdk.swing.*;
import com.sunshine.mainframe.HotelFrame;



public class Customer 
extends JDialog 
implements ActionListener,MouseListener {
	
	public static long pk;
	private JLabel top;
	private JTabbedPane tp;
	private JPanel panelMain;
	
	//会员基本信息维护
	private JButton bt11,bt12,bt13,bt14,bt15;
	private JTextField tf11,tf1;
	private JTable tb1;
	public static DefaultTableModel dtm1;
	private JScrollPane sp1;
	
	//来宾信息一览表
	private JButton bt21,bt22;
	private JTextField tf21,tf2;
	private JTable tb2;
	private DefaultTableModel dtm2;
	private JScrollPane sp2;
	
	AddHuiYuan ahy = new AddHuiYuan(this);
	ModiHuiYuan mhy = new ModiHuiYuan(this);
	
	public Customer(JFrame frame) {
		super(frame,"客户管理",true);
		
		top = new JLabel();		//假空格
		panelMain = new JPanel(new BorderLayout(0,5));
		tab();					//制作系统设置项目标签面板
		addListener();			//加入事件监听
		panelMain.add("North",top);
		panelMain.add("Center",tp);
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (750,500));
		this.setMinimumSize (new Dimension (750,500));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	private void addListener() {
		bt11.addActionListener(this);		//加动作监听
		bt12.addActionListener(this);
		bt13.addActionListener(this);
		bt14.addActionListener(this);
		bt15.addActionListener(this);
		bt21.addActionListener(this);
		bt22.addActionListener(this);
		bt11.addMouseListener(this);		//加鼠标监听
		bt12.addMouseListener(this);
		bt13.addMouseListener(this);
		bt14.addMouseListener(this);
		bt15.addMouseListener(this);
		bt21.addMouseListener(this);
		bt22.addMouseListener(this);
	}
	
	//制作系统设置项目标签面板
	private void tab() {
		JPanel jp1,jp2;
		///////////////////////////////////////////////-----------模块接口
		jp1 = huiYuan();		    //会员基本信息维护
		jp2 = laiBin();	            //来宾信息一览表
		//////////////////////////////////////////////////////////////////
		tp = new JTabbedPane();
		tp.addTab("会员基本信息维护", new ImageIcon("pic/u02.gif"), jp1);
		tp.addTab("来宾信息一览表", new ImageIcon("pic/u03.gif"), jp2);
	}
	
	//会员基本信息维护
	private JPanel huiYuan() {
		
		bt11 = new TJButton ("pic/new.gif", "增加", "增加会员信息");
		bt12 = new TJButton ("pic/modi0.gif", "修改", "修改会员信息");
		bt13 = new TJButton ("pic/del.gif", "删除", "删除会员信息");
		bt14 = new TJButton ("pic/find.gif", "查询", "查询会员信息");
		bt15 = new TJButton ("pic/b1.gif", "刷新", "刷新会员信息");
		
		tf11 = new TJTextField (10);
		tf1 = new JTextField("会员信息");
		tf1.setHorizontalAlignment (JTextField.CENTER);
		tf1.setBackground(new Color(199,183,143));
		tf1.setBorder(new LineBorder(new Color(87,87,47)));
		tf1.setEditable(false);
		
		dtm1 = new DefaultTableModel();
		tb1  = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		//////////////////////////////填写表格
		initDTM1();
		tf11.setText("");
		
		JPanel ph,pn,pc;
		ph = new JPanel(new BorderLayout());
		pn = new JPanel();
		pc = new JPanel(new BorderLayout());
		JLabel lb,lb1;
		lb = new JLabel("     会员编号/姓名：");
		lb1 = new JLabel("   ");

		pn.add(bt11);
		pn.add(bt12);
		pn.add(bt13);
		pn.add(lb);
		pn.add(tf11);
		pn.add(lb1);
		pn.add(bt14);
		pn.add(bt15);
		
		pc.add("North",tf1);
		pc.add(sp1);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		ph.add("North",pn);
		ph.add(pc);
		
		return ph;
	}
	
	//DTM1初始化
	public void initDTM1() {
		String sqlCode = "select pk,m_id 会员编号,m_name 会员姓名,sex 性别,zj_no 身份证号,m_tel 联系电话,address 详细地址 from member where delmark = 0";
		sunsql.initDTM(dtm1,sqlCode);
		tb1.removeColumn(tb1.getColumn("pk"));
		tf11.setText("");
	}
	
	//来宾信息一览表
	private JPanel laiBin() {
		
		tf21 = new TJTextField (10);
		tf2  = new TJTextField ("来宾信息");
		tf2.setHorizontalAlignment (JTextField.CENTER);
		tf2.setBackground(new Color(199,183,143));
		tf2.setBorder(new LineBorder(new Color(87,87,47)));
		tf2.setEditable(false);
		
		bt21 = new TJButton ("pic/find.gif", "查询", "查询来宾信息");
		bt22 = new TJButton ("pic/b1.gif", "刷新", "刷新来宾信息");
		
		dtm2 = new DefaultTableModel();
		tb2  = new JTable(dtm2);
		sp2  = new JScrollPane(tb2);
		////////////////////////填写表格
		initDTM2();
		
		JLabel lb1,lb2,lb3,lb4;
		lb1 = new JLabel("来宾姓名/证件编号：");
		lb2 = new JLabel("      ");
		lb3 = new JLabel("   ");
		lb4 = new JLabel("   ");
		
		JPanel pl,pn,pc; 
		pl = new JPanel(new BorderLayout());
		pn = new JPanel();
		pc = new JPanel(new BorderLayout());
		
		pn.add(lb1);
		pn.add(tf21);
		pn.add(lb2);
		pn.add(bt21);
		pn.add(lb3);
		pn.add(bt22);
		pn.add(lb4);
		
		pc.add("North",tf2);
		pc.add(sp2);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		pl.add("North",pn);
		pl.add(pc);
		
		return pl;
	}
	
	//DTM2初始化
	private void initDTM2() {
		String sqlCode = "select c_name 宾客姓名,sex 性别,zj_type 证件类型,zj_no 证件编号,address 详细地址 from livein where delmark = 0";
		sunsql.initDTM(dtm2,sqlCode);
		tf21.setText("");
	}
	
	private boolean initMmb() {
		int row = tb1.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "请在宾客预定信息表中指定记录！","提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		pk = Long.parseLong(dtm1.getValueAt(row,0)+"");
		ModiHuiYuan.tf1.setText(dtm1.getValueAt(row,1) + "");		
		ModiHuiYuan.tf2.setText(dtm1.getValueAt(row,2) + "");	
		ModiHuiYuan.tf3.setText(dtm1.getValueAt(row,4) + "");		
		ModiHuiYuan.tf4.setText(dtm1.getValueAt(row,5) + "");
		ModiHuiYuan.tf5.setText(dtm1.getValueAt(row,6) + "");
		ModiHuiYuan.cb1.setSelectedItem(dtm1.getValueAt(row,3)+"");		
		return true;
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt11) {
			//增加会员信息
			ahy.tf1.setText("");
			ahy.tf2.setText("");
			ahy.tf3.setText("");
			ahy.tf4.setText("");
			ahy.tf5.setText("");
			ahy.cb1.setSelectedItem("男");
			ahy.tf1.requestFocus();
			ahy.show(true);
			initDTM1();
		}
		else if(o==bt12) {
			//修改会员信息
			if(initMmb()) {
				mhy.show(true);
				initDTM1();
			}
		}
		else if(o==bt13) {
			//删除会员信息
			int rRow[] = tb1.getSelectedRows();
			int rowCount = rRow.length;
			if(rowCount > 0) {
				int isDel = JOptionPane.showConfirmDialog (null, "确定要删除会员信息吗?", "提示", JOptionPane.YES_NO_OPTION);
				if(isDel == JOptionPane.YES_OPTION) {
					String sqlCode[] = new String[rowCount];
					//生成SQL语句
					for (int i = 0; i < rowCount; i++) {
						String pk = dtm1.getValueAt(rRow[i], 0)+"";
						sqlCode[i] = "update member set delmark = 1 where pk= "+pk;
				    }//Endfor
				    //以事务模式执行SQL语句组, 确保操作正确, 返回值为成功执行SQL语句的条数
				    isDel = sunsql.runTransaction(sqlCode);		
				    if(isDel != rowCount) {			//如果成功执行的条数 = 数组长度，则表示更新成功
				    	String mm = "在执行第 [ " + (isDel + 1) + " ] 条记录的删除操作时出错，数据有可能被其它终端修改\n或者是网络不通畅 ...";
				    	JOptionPane.showMessageDialog(null, mm, "错误",JOptionPane.ERROR_MESSAGE);
				    }//Endif
				    else {
				    	initDTM1();
				    }
				}//Endif
				
			}
		}
		else if(o==bt14) {
			//查询会员信息
			String bx = tf11.getText();
			String sqlCode = "select m_id 会员编号,m_name 会员姓名,sex 性别,zj_no 身份证号,m_tel 联系电话,address 详细地址 "+
							 "from member where delmark = 0 and (m_id like '%"+bx+"%' or m_name like '%"+bx+"%')";
			sunsql.initDTM(dtm1,sqlCode);
		}
		else if(o==bt15) {
			//刷新会员信息
			initDTM1();
			tf11.setText("");
		}
		else if(o==bt21) {
			//查询来宾信息
			String cz = tf21.getText();
			String sqlCode = "select c_name 宾客姓名,sex 性别,zj_type 证件类型,zj_no 证件编号,address 详细地址 "+
							 "from livein where delmark = 0 and (c_name like '%"+cz+"%' or zj_no like '%"+cz+"%')";
			sunsql.initDTM(dtm2,sqlCode);
		}
		else if(o==bt22) {
			//刷新来宾信息
			initDTM2();
		}
		
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
		if(o == bt11) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"增加会员基本信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt12) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"修改会员基本信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt13) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除会员基本信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt14) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询会员基本信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt15) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新会员基本信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt21) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询来宾信息　　      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt22) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新来宾信息　　      　　　　　　　　　　　　　　　　　 　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
}