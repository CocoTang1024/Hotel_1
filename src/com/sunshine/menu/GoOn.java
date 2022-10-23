package com.sunshine.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import com.sunshine.sunsdk.swing.*;

public class GoOn 
extends JDialog 
implements ActionListener {
	
	private JLabel lb1,lb2;
	private JTextField tf1,tf2;
	private JButton bt1,bt2;
	private JPanel panelMain,ps,pc;
	
	public GoOn(JFrame frame) {
		super(frame, "宾客续住", true);
		
		JLabel lb = new JLabel();
		panelMain = new JPanel(new BorderLayout(0,7));	//主面板为边界布局
		ps		  = new JPanel(new FlowLayout(FlowLayout.CENTER,20,7));
		pc		  = new JPanel(new FlowLayout(FlowLayout.CENTER,10,7));
		
		buildPC();
		buildPS();
		
		panelMain.add("North",lb);
		panelMain.add(pc);
		panelMain.add("South",ps);
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (270,195));
		this.setMinimumSize (new Dimension (270,195));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	//事件监听
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
	}
	
	//构造中间面板
	private void buildPC() {
		JPanel pc1,pc2;
		JLabel lbA,lbB,lbC,lbD;
		lbA = new JLabel("房  间  号：");
		lbB = new JLabel("宾客姓名：");
		lbC = new JLabel("续住天数：");
		lbD = new JLabel("续缴押金：");
		pc1 = new JPanel(new GridLayout(4,1,0,7));
		pc2 = new JPanel(new GridLayout(4,1,0,5));
		
		lb1 = new JLabel("BD001");/////////////////////////
		lb2 = new JLabel("张三");//////////////////////////
		
		tf1 = new TJTextField(7);
		tf2 = new TJMoneyField("500.00",7);
		
		pc1.add(lbA);
		pc1.add(lbB);
		pc1.add(lbC);
		pc1.add(lbD);
		
		pc2.add(lb1);
		pc2.add(lb2);
		pc2.add(tf1);
		pc2.add(tf2);
		
		pc.add(pc1);
		pc.add(pc2);
	}
	
	//构造South面板
	private void buildPS() {
		bt1 = new TJButton ("pic/save.gif", "确定", "确定信息"); 
		bt2 = new TJButton ("pic/cancel.gif", "取消", "取消操作"); 
		
		ps.add(bt1);
		ps.add(bt2);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt1) {
			this.setVisible(false);//确定
		}
		else if(o==bt2) {
			this.setVisible(false);//取消
		}
	}
}