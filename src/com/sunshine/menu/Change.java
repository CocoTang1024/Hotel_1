package com.sunshine.menu;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import com.sunshine.sunsdk.swing.*;

public class Change 
extends JDialog 
implements ActionListener {
	
	private JLabel lbA;
	private JTextField tf1,tf2;
	private JCheckBox chk;
	private JButton bt1,bt2;
	private JPanel panelMain;
	
	public Change(JFrame frame) {
		super(frame,"更换房间",true);
		
		buildPanel();
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (270,200));
		this.setMinimumSize (new Dimension (270,200));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	//事件监听
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
	}
	
	//
	private void buildPanel() {
		JPanel pcc,p1,p2,p3,p4,p5;
		JLabel lb1,lb2,lb3,lb4,lb5,lb6; 
		
		panelMain = new JPanel(new BorderLayout(0,5));
		pcc		  = new JPanel(new GridLayout(5,1));
		p1		  = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
		p2 	      = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
		p3 		  = new JPanel(new FlowLayout(FlowLayout.LEFT,25,0));
		p4 		  = new JPanel(new FlowLayout(FlowLayout.LEFT,25,0));
		p5 		  = new JPanel(new FlowLayout(FlowLayout.CENTER,35,0));
		
		lb1 	  = new JLabel("         原房间：");
		lb2 	  = new JLabel("         调整为：");
		lb3 	  = new JLabel("     房价：");
		lb4 	  = new JLabel(" 注：调换同类房间时房价无效！");
		lb5 	  = new JLabel();
		lb6 	  = new JLabel();
		lbA		  = new JLabel("BD001");
		
		chk       = new JCheckBox("    保留原房间的房间费");
		bt1 	  = new TJButton ("pic/save.gif", "确定", "确定信息"); 
		bt2 	  = new TJButton ("pic/u01.gif", "取消", "取消操作"); 
		
		tf1       = new TJTextField(5);
		tf2       = new TJMoneyField("0.00",5);
		
		p1.add(lb1);
		p1.add(lbA);
		p2.add(lb2);
		p2.add(tf1);
		p2.add(lb3);
		p2.add(tf2);
		p3.add(chk);
		p4.add(lb4);
		p5.add(bt1);
		p5.add(bt2);
		
		pcc.add(p1);
		pcc.add(p2);
		pcc.add(p3);
		pcc.add(p4);
		pcc.add(p5);
		
		
		panelMain.add("North",lb5);
		panelMain.add(pcc);
		panelMain.add("South",lb6);
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