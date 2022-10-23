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

public class ModiEngage 
extends JDialog 
implements ActionListener,MouseListener {
	
	public static DefaultTableModel dtm1;	
	public static JComboBox cb1,cb2;
	public static JTextField tf,tf1,tf2,tf3,tf4;
	public static JTextArea ta;
	public static JCheckBox chk;
	private JTable tb1;
	private JScrollPane sp1,sp2;
	private JButton bt1,bt2;
	private JPanel panelMain,pc,ps,pcc,pcs,pccs,pccw,pccwc,pccwc1,pccwc2;
	
	public ModiEngage (JDialog dialog) {
		super(dialog, "客房预订修改",true);
		
		panelMain = new JPanel(new BorderLayout ());
		ps = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));//放按钮
		pc = new JPanel(new BorderLayout());//除去按钮其他部分放在一个面板里
		buildPane();
		panelMain.add(pc);
		panelMain.add("South",ps);
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (280,415));
		this.setMinimumSize (new Dimension (280,415));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
		
	}
	
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		cb1.addActionListener(this);
		tf3.addActionListener(this);
		tf4.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
	}
	
	private void buildPane() {
		
		pcs = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));//放置JCheckBox
		pcc = new JPanel(new BorderLayout());//放置备注以上,tf以下的控件
		pccs = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));//放置备注
		pccw = new JPanel(new BorderLayout());//放置列表左边的控件
		pccwc = new JPanel(new FlowLayout(FlowLayout.CENTER,5,10));//放置填写项目
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
		
		tf1 = new JTextField(12);
		tf2 = new JTextField(12);
		tf3 = new TJTextField(12);
		tf4 = new TJTextField(12);
		tf1.setEditable(false);
		tf2.setEditable(false);
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
				
		pccw.add(pccwc);
		
		dtm1 = new DefaultTableModel();
		tb1	 = new JTable(dtm1);
		sp1  = new JScrollPane(tb1);
		String sqlCode1 = "select a.r_type 预定规格,b.r_no 房间 from roomtype as a,engage1 as b where a.id = b.r_type_id";
		sunsql.initDTM(dtm1,sqlCode1);
		
		lb8 = new JLabel("备　　注：");
		ta  = new TJTextArea(3,12);
		sp2 = new JScrollPane(ta);
		
		pccs.add(lb8);
		pccs.add(sp2);
		
		pcc.add("South",pccs);
		pcc.add(pccw);
		
		chk = new JCheckBox("　到达保留时间是否自动取消预定");
		
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
		String r_type,r_no,pa_time,keep_time,remark;
		r_type = cb1.getSelectedItem()+"";
		r_no = cb2.getSelectedItem()+"";
		pa_time = tf3.getText();
		keep_time = tf4.getText();
		remark = ta.getText();
		
		if(o==bt1) {
			//===============================================保存,用事务完成
			if(r_no.equals("")||pa_time.equals("")||keep_time.equals("")) {
				JOptionPane.showMessageDialog(null,"预定信息有空值，请完整填写！");
				return;
			}else {//
				if(!suntools.isDate(pa_time)) {  //判断预抵日期是否合法
					JOptionPane.showMessageDialog(null,"预抵时间输入有误,请正确输入(yyyy-mm-dd)");
					tf3.setText("");
					tf3.requestFocus();
				}else if(!suntools.isDate(tf4.getText())) {  //判断保留时间是否合法
					JOptionPane.showMessageDialog(null,"保留时间输入有误,请正确输入(yyyy-mm-dd)");
					tf4.setText("");
					tf4.requestFocus();
				}else {
					String sqlCode[] = new String[3];
					
				    //更新房间信息
				    String r_type_id0 = "";
				    String r_type0 = Engage.r_type;
				   	String r_no0 = Engage.r_no;
					try {
						ResultSet rs = sunsql.executeQuery("select id from roomtype where r_type = '"+r_type0+"'");
						rs.next();
						r_type_id0 = rs.getString(1);//取到所选规格对应的房间类型编号
				    }
				    catch (Exception ex) {
				    	ex.printStackTrace();
				    }
				   	sqlCode[0] = "update roominfo set state = '可供' where id = '"+r_no0+"' and delmark = 0";
					RightTopPanel.setViewListButtonImage(r_type_id0,r_no0,"可供");
					//更新预定信息
					String eng_time = Journal.getNowDTime();			
					String r_type_id = "";
					try {
						ResultSet rs = sunsql.executeQuery("select id from roomtype where r_type = '"+r_type+"'");
						rs.next();
						r_type_id = rs.getString(1);//取到所选规格对应的房间类型编号
				    }
				    catch (Exception ex) {
				    	ex.printStackTrace();
				    }
				    long pk = Engage.pk;
				    if(chk.isSelected()) {
				    	sqlCode[1] = "update engage set r_type_id = '"+r_type_id+"',r_no = '"+r_no+"',pa_time = '"+pa_time+"',keep_time = '"+keep_time+"',eng_time = '"+eng_time+"',cluemark = 1,remark = '"+remark+"' where pk = "+pk;
				    }else {
				    	sqlCode[1] = "update engage set r_type_id = '"+r_type_id+"',r_no = '"+r_no+"',pa_time = '"+pa_time+"',keep_time = '"+keep_time+"',eng_time = '"+eng_time+"',cluemark = 0,remark = '"+remark+"' where pk = "+pk;
				    }
				    sqlCode[2] = "update roominfo set state = '预订' where id = '"+r_no+"' and delmark = 0";
				    RightTopPanel.setViewListButtonImage(r_type_id,r_no,"预订");
				    
				    int count = sunsql.runTransaction(sqlCode);
					if(count!=3) {
						JOptionPane.showMessageDialog(null, "提交失败，请检查网络连接 ...", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
				    		
					this.setVisible(false);
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf4.setText("");
					ta.setText("");
				}
			}	
			
		}
		else if(o==bt2) {
			//取消
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			ta.setText("");
			this.setVisible(false);
		}
		else if(o==cb1) {
			String type = cb1.getSelectedItem()+"";
			String sql = "select a.id from roominfo as a,roomtype as b where a.delmark = 0 and b.delmark = 0 and a.r_type_id = b.id and b.r_type = '"+type+"' and a.state = '可供'";
			sunsql.initJComboBox(cb2,sql);
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

	}

	public void mouseReleased(MouseEvent me) {
	
	}

	public void mouseEntered (MouseEvent me) {		//鼠标移进提示
		Object o = me.getSource ();
		if(o == bt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"保存房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"取消预定信息添加      　　　　　　　　　　　　　　　　　 　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}

}