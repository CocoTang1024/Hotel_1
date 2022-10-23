package com.sunshine.engage;

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
import com.sunshine.mainframe.*;

public class EngageInfo 
extends JDialog 
implements ActionListener,MouseListener {
	
	public static DefaultTableModel dtm1;	
	public static JComboBox cb1,cb2;
	public static JCheckBox chk;
	private JTable tb1;
	private JScrollPane sp1,sp2;
	private JTextField tf,tf1,tf2,tf3,tf4;
	private JTextArea ta;
	private JButton bt1,bt2,bt3;
	private JPanel panelMain,pc,ps,pcc,pcs,pccc,pccs,pccw,pccwc,pccwc1,pccwc2,pccws;
	
	public EngageInfo (JDialog dialog) {
		super(dialog, "客房预订",true);
		
		panelMain = new JPanel(new BorderLayout ());
		ps = new JPanel(new FlowLayout(FlowLayout.CENTER,200,10));//放按钮
		pc = new JPanel(new BorderLayout());//除去按钮其他部分放在一个面板里
		buildPane();
		panelMain.add(pc);
		panelMain.add("South",ps);
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (595,460));
		this.setMinimumSize (new Dimension (595,460));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
		
	}
	
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		cb1.addActionListener(this);
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
		bt3.addMouseListener(this);
		
	}
	
	private void buildPane() {
		
		pcs = new JPanel(new FlowLayout(FlowLayout.LEFT,20,5));//放置JCheckBox
		pcc = new JPanel(new BorderLayout());//放置备注以上,tf以下的控件
		pccs = new JPanel(new FlowLayout(FlowLayout.LEFT,20,5));//放置备注
		pccc = new JPanel(new GridLayout(1,1));//放置两个列表
		pccw = new JPanel(new BorderLayout());//放置列表左边的控件
		pccws = new JPanel(new FlowLayout(FlowLayout.LEFT,20,5));//放添加按钮
		pccwc = new JPanel(new FlowLayout(FlowLayout.LEFT,5,10));//放置填写项目
		pccwc1 = new JPanel(new GridLayout(6,1,0,14));//放置标签
		pccwc2 = new JPanel(new GridLayout(6,1,0,5));//放置文本框和下拉列表框

		JLabel lb1,lb2,lb3,lb4,lb5,lb6,lb7,lb8;
		lb1 = new JLabel("宾客姓名：");
		lb2 = new JLabel("联系电话：");
		lb3 = new JLabel("预定规格：");
		lb4 = new JLabel("房间编号：");
		lb5 = new JLabel("预抵时间：");
		lb6 = new JLabel("保留时间：");

		pccwc1.add(lb1);
		pccwc1.add(lb2);
		pccwc1.add(lb3);
		pccwc1.add(lb4);
		pccwc1.add(lb5);
		pccwc1.add(lb6);
		
		tf1 = new TJTextField(12);
		tf2 = new TJTextField(12);
		tf3 = new TJTextField(12);
		tf4 = new TJTextField(12);
		cb1 = new JComboBox();//////////////公共类刷新
		sunsql.initJComboBox(cb1,"select r_type from roomtype where delmark = 0");
		cb2 = new JComboBox();
		String s = cb1.getSelectedItem()+"";
		sunsql.initJComboBox(cb2,"select a.id from roominfo as a,roomtype as b where b.r_type = '"+s+"' and a.r_type_id = b.id and a.state = '可供' and a.delmark = 0 and b.delmark = 0");
		
		pccwc2.add(tf1);
		pccwc2.add(tf2);
		pccwc2.add(cb1);
		pccwc2.add(cb2);
		pccwc2.add(tf3);
		pccwc2.add(tf4);
		
		pccwc.add(pccwc1);
		pccwc.add(pccwc2);
		
		bt3 = new TJButton ("pic/add.gif", "", "添加到预定列表");
		bt3.setBorderPainted(false);	//无外框
		bt3.setFocusPainted(false);		//无焦点框
		bt3.setContentAreaFilled(false);//设置透明色
		
		lb7 = new JLabel("点击右边按钮添加预定");
		pccws.add(lb7);
		pccws.add(bt3);
		
		pccw.add(pccwc);
		pccw.add("South",pccws);
		
		dtm1 = new DefaultTableModel();
		tb1	 = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		String sqlCode1 = "select a.r_type 预定规格,b.r_no 房间 from roomtype as a,engage1 as b where a.id = b.r_type_id";
		sunsql.initDTM(dtm1,sqlCode1);

		pccc.setBorder(BorderFactory.createTitledBorder("本次预订的房间"));
		pccc.add(sp1);
		
		lb8 = new JLabel("备注：");
		ta  = new TJTextArea(3,45);
		sp2 = new JScrollPane(ta);
		
		pccs.add(lb8);
		pccs.add(sp2);
		
		pcc.add("West",pccw);
		pcc.add("South",pccs);
		pcc.add(pccc);
		
		chk = new JCheckBox("到达保留时间是否自动取消预定");
		chk.setSelected(false);
		
		pcs.add(chk);

		bt1 = new TJButton ("pic/save.gif", "保  存", "保存预定信息"); 
		bt2 = new TJButton ("pic/cancel.gif", "取  消", "取消操作"); 
		
		ps.add(bt1);
		ps.add(bt2);
		
		tf = new JTextField("基本预定信息");
		tf.setHorizontalAlignment (JTextField.CENTER);
		tf.setBackground(new Color(199,183,143));
		tf.setBorder(new LineBorder(new Color(87,87,47)));
		tf.setEditable(false);
		
		pc.add("North",tf);
		pc.add(pcc);
		pc.add("South",pcs);
		pc.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		String c_name,c_tel,roomtype,r_no,pa_time,keep_time,remark;
		c_name = tf1.getText();					//宾客姓名
		c_tel = tf2.getText();					//联系电话
		roomtype = cb1.getSelectedItem()+"";	//房间类型
		r_no = cb2.getSelectedItem()+"";		//房间编号
		pa_time = tf3.getText();				//预抵时间
		keep_time = tf4.getText();				//保留时间
		remark = ta.getText();					//备注
		
		if(o==bt1) {//==============================================================保存
			if(dtm1.getRowCount()==0) {  //若右边表格内没有数据
				JOptionPane.showMessageDialog(null,"请添加预定信息后保存，如要取消请点击取消按钮！");
				return;
			}else {  //右边表格内有数据，用事务对预订表和房间信息表进行更新修改
				int count = tb1.getRowCount();
				String sqlCode[] = new String[count + 1];
				for(int i = 0;i < count; i++) {
	   				sqlCode[i] = "update roominfo set state='预订' where id = '"+dtm1.getValueAt(i,2)+"' and delmark = 0";
					RightTopPanel.setViewListButtonImage(dtm1.getValueAt(i,0)+"",dtm1.getValueAt(i,2)+"","预订");
				}
				sqlCode[count] = "insert into engage (pk,c_name,c_tel,r_type_id,r_no,pa_time,keep_time,eng_time,cluemark,remark) "+
								 "(select pk,c_name,c_tel,r_type_id,r_no,pa_time,keep_time,eng_time,cluemark,remark from engage1)";
				count = sunsql.runTransaction(sqlCode);
				if(sqlCode.length != count) {
					JOptionPane.showMessageDialog(null, "提交失败，请检查网络连接 ...", "错误", JOptionPane.ERROR_MESSAGE);
					for (int i = 0; i < count; i++) {
						RightTopPanel.setViewListButtonImage(dtm1.getValueAt(i,0)+"",dtm1.getValueAt(i,2)+"","可供");
				    }//Endfor
					return;
				}
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
				ta.setText("");
				cb1.setSelectedItem("标准单人间");
				cb2.setSelectedItem("");
			}
			
			this.setVisible(false);
			
			
		}
		else if(o==bt2) {
			//取消
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			ta.setText("");
			cb1.setSelectedItem("标准单人间");
			cb2.setSelectedItem("");
			this.setVisible(false);
		}
		else if(o==bt3) {
			//添加预定
			if(c_name.equals("")||c_tel.equals("")||r_no.equals("")||pa_time.equals("")||keep_time.equals("")) {
				JOptionPane.showMessageDialog(null,"预定信息有空值，请完整填写！");
				return;
			}else {//
				if(!suntools.isNum(tf2.getText())) {//判断电话是否由数字组成
					JOptionPane.showMessageDialog(null,"联系电话必须由数字组成,请重新输入!");
					tf2.setText("");
					tf2.requestFocus();
				}else if(!suntools.isDate(pa_time)) {  //判断预抵日期是否合法
					JOptionPane.showMessageDialog(null,"预抵时间输入有误,请正确输入(yyyy-mm-dd)");
					tf3.setText("");
					tf3.requestFocus();
				}else if(!suntools.isDate(tf4.getText())) {  //判断保留时间是否合法
					JOptionPane.showMessageDialog(null,"保留时间输入有误,请正确输入(yyyy-mm-dd)");
					tf4.setText("");
					tf4.requestFocus();
				}else {
					try {
						ResultSet rs = sunsql.executeQuery("select r_no from engage1 where r_no = '"+r_no+"'");
						if(rs.next()) {//判断要预订的房间是否已经被预订
							JOptionPane.showMessageDialog(null,"该房间已经被预订,请重新选择房间!");
						}else {//要添加的房间为可供状态
							String eng_time = Journal.getNowDTime();
							String r_type_id = "";
							try {
								ResultSet rs1 = sunsql.executeQuery("select id from roomtype where r_type = '"+roomtype+"'");
								rs1.next();
								r_type_id = rs1.getString(1);//取到所选规格对应的房间类型编号
						    }
						    catch (Exception ex) {
						    	ex.printStackTrace();
						    }
						    //将预定信息插入临时表
						    long pk = sunsql.getPrimaryKey();
						    String sqlCode = "";
						    if(chk.isSelected()) {
						    	sqlCode = "insert into engage1 (pk,c_name,c_tel,r_type_id,r_no,pa_time,keep_time,eng_time,cluemark,remark) "+
						    				 "values ("+pk+",'"+c_name+"','"+c_tel+"','"+r_type_id+"','"+r_no+"','"+pa_time+"','"+keep_time+"','"+eng_time+"',1,'"+remark+"')";
						    }else {
						    	sqlCode = "insert into engage1 (pk,c_name,c_tel,r_type_id,r_no,pa_time,keep_time,eng_time,remark) "+
						    				 "values ("+pk+",'"+c_name+"','"+c_tel+"','"+r_type_id+"','"+r_no+"','"+pa_time+"','"+keep_time+"','"+eng_time+"','"+remark+"')";
						    }		    
						   	sunsql.executeUpdate(sqlCode);
						   	//将插入的预定信息显示出来
						   	String sqlCode1 = "select b.r_type_id,a.r_type 预定规格,b.r_no 房间 from roomtype as a,engage1 as b where a.id = b.r_type_id and a.delmark = 0";
							sunsql.initDTM(dtm1,sqlCode1);
							tb1.removeColumn(tb1.getColumn("r_type_id"));
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							ta.setText("");
							cb1.setSelectedItem("标准单人间");
							String type = cb1.getSelectedItem()+"";
							String sql = "select a.id from roominfo as a,roomtype as b where a.delmark = 0 and b.delmark = 0 and a.r_type_id = b.id and b.r_type = '"+type+"' and a.state = '可供' and a.delmark = 0";
							sunsql.initJComboBox(cb2,sql);
						}
				    }
				    catch (Exception ex) {
				    	ex.printStackTrace();
				    }
				}
			}
			
		
		}
		else if(o==cb1) {
			String type = cb1.getSelectedItem()+"";
			String sql = "select a.id from roominfo as a,roomtype as b where a.delmark = 0 and b.delmark = 0 and a.r_type_id = b.id and b.r_type = '"+type+"' and a.state = '可供' and a.delmark = 0";
			sunsql.initJComboBox(cb2,sql);
		}
		else if(o==tf1) {
			tf2.requestFocus();
		}
		else if(o==tf2) {
			if(!suntools.isNum(c_tel)||c_tel.equals("")) {//判断电话是否由数字组成
				JOptionPane.showMessageDialog(null,"联系电话必须由数字组成或不能为空,请重新输入!");
				tf2.setText("");
				tf2.requestFocus();
			}
		}
		else if(o==tf3) {
			//判断时间是否合法
			if(!suntools.isDate(pa_time)) {
				//若日期不合法
				JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
				tf3.setText("");
				tf3.requestFocus();
			}else {//若日期合法
				tf4.requestFocus();
			}
		}
		else if(o==tf4) {
			//判断时间是否合法
			if(!suntools.isDate(tf4.getText())) {
				//若日期不合法
				JOptionPane.showMessageDialog(null,"日期输入有误,请正确输入(yyyy-mm-dd)");
				tf4.setText("");
				tf4.requestFocus();
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
		Object o = me.getSource();
		if(o == bt3) 
			bt3.setIcon(new ImageIcon("pic/add2.gif"));
	}

	public void mouseReleased(MouseEvent me) {
		Object o = me.getSource();
		if(o == bt3) 
			bt3.setIcon(new ImageIcon("pic/add.gif"));
	}

	public void mouseEntered (MouseEvent me) {		//鼠标移进提示
		Object o = me.getSource ();
		if(o == bt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"保存房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"取消预定信息添加      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt3) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"添加房间到预定表      　　　　　　　　　　　　　　　　　 　");
			bt3.setIcon(new ImageIcon("pic/add1.gif"));
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}

	
}