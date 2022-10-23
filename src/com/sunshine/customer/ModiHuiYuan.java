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
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.HotelFrame;

public class ModiHuiYuan 
extends JDialog 
implements ActionListener,MouseListener {
	
	public static JTextField tf1,tf2,tf3,tf4,tf5;
	public static JComboBox cb1;
	private JTextField tf;
	private JButton bt1,bt2;
	private JPanel panelMain;
	
	public ModiHuiYuan (JDialog dialog) {
		super(dialog, "修改会员信息",true);
		
		panelMain = new JPanel(new BorderLayout(0,0));
		buildPanel();
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (300,300));
		this.setMinimumSize (new Dimension (300,300));
	//	this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
	}
	
	//加事件监听
	private void addListener(){
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
	}
	
	//构造面板
	private void buildPanel() {
		tf1 = new TJTextField(12);
		tf2 = new TJTextField(12);
		tf3 = new TJTextField(10);
		tf4 = new TJTextField(10);
		tf5 = new TJTextField(10);
		tf  = new JTextField("会员信息");
		tf.setHorizontalAlignment (JTextField.CENTER);
		tf.setBackground(new Color(199,183,143));
		tf.setBorder(new LineBorder(new Color(87,87,47)));
		tf.setEditable(false);
		tf1.setEditable(false);
		
		cb1 = new JComboBox();
		cb1.addItem("男");
		cb1.addItem("女");
				
		bt1 = new TJButton ("pic/save.gif", "保存", "保存会员信息");
		bt2 = new TJButton ("pic/cancel.gif", "取消", "取消添加");
		
		JLabel lb1,lb2,lb3,lb5,lb6,lb7,lb8;
		lb1 = new JLabel("会员编号：");
		lb2 = new JLabel("会员姓名：");
		lb3 = new JLabel("会员性别：");
		lb5 = new JLabel("身份证号：");
		lb6 = new JLabel("联系电话：");
		lb7 = new JLabel("详细地址：");
		lb8 = new JLabel();
		
		JPanel ps,ps1,pc,pcc,pcc1,pcc2;
		ps = new JPanel();
		ps1 = new JPanel(new FlowLayout(FlowLayout.CENTER,35,0));
		pc  = new JPanel(new BorderLayout(0,5));
		pcc = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
		pcc1 = new JPanel(new GridLayout(6,1,0,14));
		pcc2 = new JPanel(new GridLayout(6,1,0,5));
		
		pcc1.add(lb1);
		pcc1.add(lb2);
		pcc1.add(lb3);
		pcc1.add(lb5);
		pcc1.add(lb6);
		pcc1.add(lb7);
		
		pcc2.add(tf1);
		pcc2.add(tf2);
		pcc2.add(cb1);
		pcc2.add(tf3);
		pcc2.add(tf4);
		pcc2.add(tf5);
		
		pcc.add(pcc1);
		pcc.add(pcc2);
		
		pc.add("North",tf);
		pc.add(pcc);
		pc.setBorder(BorderFactory.createTitledBorder(""));
		
		ps1.add(bt1);
		ps1.add(bt2);
		ps.add(ps1);
		
		panelMain.add("North",lb8);
		panelMain.add(pc);
		panelMain.add("South",ps);
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt1) {//=====================================保存
			long pk = Customer.pk;//得到主键
			String m_id,m_name,sex,zj_no,m_tel,address;
			m_id = tf1.getText();
			m_name = tf2.getText();
			sex = cb1.getSelectedItem()+"";
			zj_no = tf3.getText();
			m_tel = tf4.getText();
			address = tf5.getText();
			
			if(m_name.equals("")||zj_no.equals("")||m_tel.equals("")||address.equals("")) {
				//若添加项有空值
				JOptionPane.showMessageDialog(null,"会员信息有空值，请重新输入！");
				return;
			}else {
				if(!suntools.isNum(tf4.getText())) {//判断电话是否由数字组成
					JOptionPane.showMessageDialog(null,"联系电话必须由数字组成,请重新输入!");
					tf4.setText("");
				}else {//将添加的信息插入会员表
					String sqlCode = "update member set m_id='"+m_id+"',m_name='"+m_name+"',sex='"+sex+"',zj_no='"+zj_no+"',m_tel='"+m_tel+"',address='"+address+"' where pk = "+pk;
					sunsql.executeUpdate(sqlCode);
				}
				this.setVisible(false);
			}
		}else if(o==bt2) {//===============================取消
			this.setVisible(false);
		}else if(o==tf3) {
			tf4.requestFocus();
		}else if(o==tf4) {
			//判断必须全部为数字
			if(!suntools.isNum(tf4.getText())) {//判断电话是否由数字组成
				JOptionPane.showMessageDialog(null,"联系电话必须由数字组成,请重新输入!");
				tf4.setText("");
			}else {
				tf5.requestFocus();
			}
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
		if(o == bt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"保存会员信息　　      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"取消会员信息修改      　　　　　　　　　　　　　　　　　 　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
}		