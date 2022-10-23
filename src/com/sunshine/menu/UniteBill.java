package com.sunshine.menu;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;
import com.sunshine.sunsdk.swing.*;

public class UniteBill 
extends JDialog 
implements ActionListener {
	
	private JTable tb1,tb2;
	private DefaultTableModel dtm1,dtm2;
	private JScrollPane sp1,sp2;
	private JButton bt1,bt2,bt3,bt4;
	private JTextField tf1,tf2,tfA,tfB;
	private JPanel panelMain;
	
	public UniteBill(JFrame frame) {
		super(frame,"合并账单",true);
		
		panelMain = new JPanel(new BorderLayout(0,5));//主面板为边界布局
		buildPanel();
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (540,410));
		this.setMinimumSize (new Dimension (540, 410));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		tf1.addActionListener(this);
	}
	
	private void buildPanel() {
		
		
		bt1 = new TJButton("pic/modi3.gif","确定","确定合并");
		bt2 = new TJButton("pic/recall.gif","取消","取消合并");
		bt3 = new TJButton("pic/new.gif","添加到合并区","添加到合并区");
		bt4 = new TJButton("pic/del.gif","从合并区删除","从合并区删除");
		
		tf1 = new TJTextField(5);
		tf2 = new TJTextField(5);
		tfA = new JTextField("在店宾客");
		tfB = new JTextField("合并区");
		tfA.setHorizontalAlignment (JTextField.CENTER);
		tfA.setBackground(new Color(199,183,143));
		tfA.setBorder(new LineBorder(new Color(87,87,47)));
		tfA.setEditable(false);
		tfB.setHorizontalAlignment (JTextField.CENTER);
		tfB.setBackground(new Color(199,183,143));
		tfB.setBorder(new LineBorder(new Color(87,87,47)));
		tfB.setEditable(false);
		tf2.setEditable(false);
		
		dtm1 = new DefaultTableModel();
		tb1  = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		sp1.setBorder(BorderFactory.createTitledBorder(""));
		String sqlCode = "select r_no 房间号,in_time 入住时间 from livein where statemark = '正在消费' and delmark = 0";
		sunsql.initDTM(dtm1,sqlCode);
		
		dtm2 = new DefaultTableModel();
		tb2  = new JTable(dtm2);
		sp2  = new JScrollPane(tb2);
		sp2.setBorder(BorderFactory.createTitledBorder(""));
		/////////////////////////表格
		
		JPanel pc,ps,pc1,pc1c,pc1cn,pc2,pc2c,pc2cn;
		JLabel lb1,lb2,temp;
		lb1 = new JLabel("指定房间号：");
		lb2 = new JLabel("合并后主房间：");
		temp = new JLabel("");
		pc  = new JPanel(new GridLayout(1,2,10,0));
		ps  = new JPanel(new FlowLayout(FlowLayout.CENTER,70,10));
		pc1 = new JPanel(new BorderLayout());//放置左边表格
		pc1c = new JPanel(new BorderLayout());
		pc1cn = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
		pc2   = new JPanel(new BorderLayout());//放置右边表格
		pc2c  = new JPanel(new BorderLayout());
		pc2cn = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
		
		pc1cn.add(lb1);
		pc1cn.add(tf1);
		pc1cn.setBorder(BorderFactory.createTitledBorder(""));
		pc1c.add("North",pc1cn);
		pc1c.add(sp1);
		pc1.add("North",tfA);
		pc1.add(pc1c);
		pc1.add("South",bt3);
		
		pc2cn.add(lb2);
		pc2cn.add(tf2);
		pc2cn.setBorder(BorderFactory.createTitledBorder(""));
		pc2c.add("North",pc2cn);
		pc2c.add(sp2);
		pc2.add("North",tfB);
		pc2.add(pc2c);
		pc2.add("South",bt4);
		
		pc.add(pc1);
		pc.add(pc2);
		ps.add(bt1);
		ps.add(bt2);
		panelMain.add("North",temp);
		panelMain.add(pc);
		panelMain.add("South",ps);
	}
		
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt1) {
			this.setVisible(false);//确定
		}
		else if(o==bt2) {
			this.setVisible(false);//取消
		}
		else if(o==bt3) {
			//添加
		}
		else if(o==bt4) {
			//移除
		}
		else if(o==tf1) {
			
		}
	}
}
