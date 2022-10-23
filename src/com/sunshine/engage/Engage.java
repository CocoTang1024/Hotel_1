package com.sunshine.engage;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import com.sunshine.sunsdk.sql.*;
import com.sunshine.sunsdk.swing.*;
import com.sunshine.mainframe.*;




public class Engage 
extends JDialog 
implements ActionListener,MouseListener {
	
	public static DefaultTableModel dtm = new DefaultTableModel();
	public static long pk;
	public static String r_type,r_no;
	
	private JTable tb = new JTable(dtm);
	private JScrollPane sp = new JScrollPane(tb);
	private JButton bt1,bt2,bt3,bt4,bt5,bt6,bt7;
	private JTextField tf1,tf2;
	private JPopupMenu pm;
	private JMenuItem mi1,mi2;
	
	EngageInfo ei  = new EngageInfo(this);
	ModiEngage em  = new ModiEngage(this);
	Eindividual ev = new Eindividual(this);
	
	public Engage(JFrame frame) {
		super (frame, "客户预订", true);
		
		JPanel panelMain,panelCent,panelNort;
		panelMain = new JPanel(new BorderLayout());
		panelNort = buildNorth();
		panelCent = buildDTM();
		
		panelMain.add("North",panelNort);
		panelMain.add(panelCent);
		addListener();
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (880,508));
		this.setMinimumSize (new Dimension (880,508));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
		
	}
	
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt6.addActionListener(this);
		bt7.addActionListener(this);
		mi1.addActionListener(this);
		mi2.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
		bt3.addMouseListener(this);
		bt4.addMouseListener(this);
		bt5.addMouseListener(this);
		bt6.addMouseListener(this);
		bt7.addMouseListener(this);
	}
	
	//////////////////////
	private JPanel buildNorth() {
		JPanel panelNort1 = new JPanel();
		JLabel lb = new JLabel("     房间号/姓名/电话:");
		tf1 = new JTextField (10);
		bt1 = new TJButton ("pic/new.gif", "增加", "增加预定信息");
		bt2 = new TJButton ("pic/modi0.gif", "修改", "修改预定信息");
		bt3 = new TJButton ("pic/del.gif", "删除", "删除预定信息");
		bt4 = new TJButton ("pic/find.gif", "查询", "查询预定信息");
		bt5 = new TJButton ("pic/recall.gif", "过滤", "过滤预定信息");
		bt6 = new TJButton ("pic/b1.gif", "刷新", "刷新预定信息");
		bt7 = new TJButton ("pic/modi3.gif", "开设房间", "为预定房间开单");
		
		pm = new JPopupMenu();
		mi1 = new JMenuItem("今天预计抵达的宾客");
		mi2 = new JMenuItem("明天预计抵达的宾客");
		pm.addSeparator();
		pm.add(mi1);
		pm.add(mi2);
		pm.addSeparator();
		
		panelNort1.add(bt1);
		panelNort1.add(bt2);
		panelNort1.add(bt3);
		panelNort1.add(lb);
		panelNort1.add(tf1);
		panelNort1.add(bt4);
		panelNort1.add(bt5);
		panelNort1.add(bt6);
		panelNort1.add(bt7);
		
		return panelNort1;
	}
	
	
	private JPanel buildDTM () {
		JPanel panelCent1 = new JPanel(new BorderLayout());
		initDTM();
		tf2 = new JTextField("宾客预定信息");
		tf2.setHorizontalAlignment (JTextField.CENTER);
		tf2.setBackground(new Color(199,183,143));
		tf2.setBorder(new LineBorder(new Color(87,87,47)));
		tf2.setEditable(false);
		
		panelCent1.add("North",tf2);
		panelCent1.add(sp);
		panelCent1.setBorder(BorderFactory.createTitledBorder(""));
		
		return panelCent1;
	}
	
	private void initDTM() {
		String sqlCode;
		sqlCode = "select a.pk,a.c_name 宾客姓名,a.c_tel 联系电话,b.r_type 预定房间类型,a.r_no 预定房间编号,a.pa_time 预抵时间,a.keep_time 保留时间,a.eng_time 预定时间,a.remark 备注,b.id,"+
				  "b.price from engage as a,roomtype as b where a.r_type_id = b.id and a.delmark = 0 and engagemark = 2 and b.delmark = 0";
		sunsql.initDTM(dtm,sqlCode);
		tb.removeColumn(tb.getColumn("pk"));
		tb.removeColumn(tb.getColumn("id"));
		tb.removeColumn(tb.getColumn("price"));
	}
	
	private boolean initMrt() {
		int row = tb.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "请在宾客预定信息表中指定记录！","提示", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		pk = Long.parseLong(dtm.getValueAt(row,0)+"");
		r_type = dtm.getValueAt(row,3)+"";
		r_no = dtm.getValueAt(row,4)+"";
		ModiEngage.tf1.setText(dtm.getValueAt(row,1) + "");		
		ModiEngage.tf2.setText(dtm.getValueAt(row,2) + "");	
		ModiEngage.tf3.setText(dtm.getValueAt(row,5) + "");		
		ModiEngage.tf4.setText(dtm.getValueAt(row,6) + "");
		ModiEngage.ta.setText(dtm.getValueAt(row,8) + "");
		ModiEngage.cb1.setSelectedItem(dtm.getValueAt(row,3)+"");		
		ModiEngage.cb2.addItem(dtm.getValueAt(row,4));	
		ModiEngage.cb2.setSelectedItem(dtm.getValueAt(row,4)+"");
		try {
			String sql = "select cluemark from engage where pk = '"+pk+"'";
			ResultSet rs = sunsql.executeQuery(sql);
			if(rs.next()) {
				int cluemark = Integer.parseInt(rs.getString(1));
				if(cluemark==0) 
					ModiEngage.chk.setSelected(false);
				else
					ModiEngage.chk.setSelected(true);
			}
	    }
	    catch (Exception ex) {
	    }
		
		return true;
	}
	
	private boolean delInfo (int dr[]) {
		int rowCount = dr.length*2;
		int r =0;							//DTM行指针
					
		if(rowCount > 0) {					//判断选择记录数
			int isDel = JOptionPane.showConfirmDialog (null, "确定要删除预订记录吗?", "提示", JOptionPane.YES_NO_OPTION);
			if(isDel == JOptionPane.YES_OPTION) {
				String sqlCode[] = new String[rowCount];
				//生成SQL语句
				for (int i = 0; i < rowCount; i++) {
					String pk = dtm.getValueAt(dr[r], 0)+"";
					sqlCode[i] = "update engage set delmark = 1, engagemark = 0 where pk= "+pk;
					i++;
					String r_no = dtm.getValueAt(dr[r],4)+"";
					String r_type_id = dtm.getValueAt(dr[r],9)+"";
					sqlCode[i] = "update roominfo set state = '可供' where id = '"+r_no+"' and delmark = 0";
					RightTopPanel.setViewListButtonImage(r_type_id,r_no,"可供");
					r++;		//DTM行指针加1
			    }//Endfor
			    //以事务模式执行SQL语句组, 确保操作正确, 返回值为成功执行SQL语句的条数
			    isDel = sunsql.runTransaction(sqlCode);		
			    if(isDel != rowCount) {			//如果成功执行的条数 = 数组长度，则表示更新成功
			    	String mm = "在执行第 [ " + (isDel + 1) + " ] 条记录的删除操作时出错，数据有可能被其它终端修改\n或者是网络不通畅 ...";
			    	JOptionPane.showMessageDialog(null, mm, "错误",JOptionPane.ERROR_MESSAGE);
			    	//更新失败，返回false
			    	for(int i = 0; i<dr.length; i++) {
			    		RightTopPanel.setViewListButtonImage(dtm.getValueAt(dr[i],4)+"",dtm.getValueAt(dr[i],9)+"","预定");
			    	}
			    	
			    	return false;
			    }//Endif
			    return true;		//更新成功，返回true
			}//Endif
		}
		else {						//如果没有选中记录，则提示一下
			String msg1 = "请先选定记录再按删除键!";
			JOptionPane.showMessageDialog(null, msg1, "提示",JOptionPane.INFORMATION_MESSAGE);
		}
		return false;
	}
	
	private boolean initIDV(int row) {
		try {
			//从房间信息表里获得当前房间的状态和房间类型编号
			ResultSet rs = null;			
			
							
				//传房间号到开单窗口
				ev.lbA.setText(dtm.getValueAt(row, 4) + "");
				//传房间类型到开单窗口
				ev.lbB.setText(dtm.getValueAt(row, 3) + "");
				//传房间单价到开单窗口
				ev.lbC.setText(dtm.getValueAt(row, 10) + "");
				//传姓名给开单窗口
				ev.tf2.setText(dtm.getValueAt(row, 1) + "");
				
				//房间类型编号
				String clRoom = dtm.getValueAt(row, 9) + "";
				//获得此类型房间是否可以开设钟点房
				rs = sunsql.executeQuery("select cl_room from roomtype where " +
				"delmark=0 and id='" + clRoom + "'");
				
				rs.next();
				if(rs.getString(1).equals("N")) {	//不能开设，则开单窗口的钟点选项不可用
					ev.chk1.setSelected(false);		//取消选中状态
					ev.chk1.setEnabled(false);		//设置不可用
				}else {
					ev.chk1.setEnabled(true);		//可用
				}//Endif
				
				//传宾客类型数据给开单窗口
				rs = sunsql.executeQuery("select distinct c_type from customertype where " +
				"delmark = 0 and pk!=0");
				int ct = sunsql.recCount(rs);
				String cType[] = new String[ct];
				for (int i = 0; i < ct; i++) {
					rs.next();
					cType[i] = rs.getString(1);
			    }//Endfor
			    ev.cb2.removeAllItems();
				for (int i = 0; i < ct; i++) {
					ev.cb2.addItem(cType[i]);
			    }//Endfor
			    ev.cb2.setSelectedItem("普通宾客");
				
				//初始化开单房间表---------临时表
				sunsql.executeUpdate("delete from roomnum");		//清空临时表
				sunsql.executeUpdate("insert into roomnum(roomid) values('" + 
				dtm.getValueAt(row, 4) + "')");				//加入当前房间信息
				//初始化开单窗口的开单房间表
				sunsql.initDTM(ev.dtm2,"select roomid 房间编号 from roomnum");
				
				//初始化追加房间表---------当前类型的除当前房间的所有可供房间
				sunsql.executeUpdate("update roominfo set indimark=0");	//刷新所有房间的开单状态
				sunsql.executeUpdate("update roominfo set indimark=1 where id='" + 
				dtm.getValueAt(row, 4) + "'");				//设置当前房间为开单状态
				//初始化开单窗口的可供房间表
				sunsql.initDTM(ev.dtm1,"select a.id 房间编号1 from roominfo " +
				"a,(select id from roomtype where r_type='" + ev.lbB.getText() + 
				"') b where a.delmark=0 and a.indimark=0 and a.state='可供' and a.r_type_id=b.id");
				
			
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	    	System.out.println ("Engage.initIDV(): false");
	    }//End try
	    return true;
	}
	
	
	
	
	
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt1) {
			//增加预定信息
			sunsql.executeUpdate("delete from engage1");
			String sqlCode = "select a.r_type 预定规格,b.r_no 房间 from roomtype as a,engage1 as b where a.id = b.r_type_id";
			sunsql.initDTM(ei.dtm1,sqlCode);
			ei.chk.setSelected(false);
			ei.show(true);
			initDTM();
		//	sunsql.initJComboBox(ei.cb1,"select r_type from roomtype where delmark = 0");
		}
		else if(o==bt2) {
			//修改预定信息
			if(initMrt()) {					//传数据给窗口
				em.show(true);				//修改预定信息
				initDTM();			    	//刷新表数据
			}//Endif
		}
		else if(o==bt3) {
			//删除预定信息
			int rRow[] = tb.getSelectedRows();				//删除预订信息
			if(delInfo (rRow)) {	//执行删除操作
				initDTM();			//刷新房间表数据
			}//Endif
			
		}
		else if(o==bt4) {
			//查询
			String s = tf1.getText();
			String sqlCode = "select a.c_name 宾客姓名,a.c_tel 联系电话,b.r_type 预定房间类型,a.r_no 预定房间编号,a.pa_time 预抵时间,a.keep_time 保留时间,a.eng_time 预定时间,a.remark 备注 "+
				      "from engage as a,roomtype as b where a.r_type_id = b.id and a.delmark = 0 and b.delmark = 0 and a.engagemark = 2 and (a.c_name like '%"+s+"%' or a.r_no like '%"+s+"%' or a.c_tel like '%"+s+"%')";
			sunsql.initDTM(dtm,sqlCode);
		}
		else if(o==bt6) {
			//刷新
			String sqlCode = "select a.c_name 宾客姓名,a.c_tel 联系电话,b.r_type 预定房间类型,a.r_no 预定房间编号,a.pa_time 预抵时间,a.keep_time 保留时间,a.eng_time 预定时间,a.remark 备注 "+
					  "from engage as a,roomtype as b where a.r_type_id = b.id and a.delmark = 0 and b.delmark = 0 and engagemark = 2";
			sunsql.initDTM(dtm,sqlCode);
		}
		else if(o==bt7) {	//开设房间
			int row = tb.getSelectedRow();
			if(row < 0) {
				JOptionPane.showMessageDialog(null, "请在预订列表中选中指定的预订房间，开设房间", 
				"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}//Endif
			if(initIDV(row)) {
				ev.show(true);
				initDTM();
			}//Endif
		}
		else if(o==mi1) {
			//过滤今日预抵宾客
			String date = getDate();
			String start = date + " 00:00:00";
			String end = date + " 23:59:59";
			String sqlCode = "select a.c_name 宾客姓名,a.c_tel 联系电话,b.r_type 预定房间类型,a.r_no 预定房间编号,a.pa_time 预抵时间,a.keep_time 保留时间,a.eng_time 预定时间,a.remark 备注 "+
					 		 "from engage as a,roomtype as b where a.r_type_id = b.id and a.delmark = 0 and b.delmark = 0 and engagemark = 2 and a.pa_time between '"+start+"' and '"+end+"'";
			sunsql.initDTM(dtm,sqlCode);
		}
		else if(o==mi2) {
			//过滤明日预抵宾客
			String date = tomorrow();
			String start = date + " 00:00:00";
			String end = date + " 23:59:59";
			String sqlCode = "select a.c_name 宾客姓名,a.c_tel 联系电话,b.r_type 预定房间类型,a.r_no 预定房间编号,a.pa_time 预抵时间,a.keep_time 保留时间,a.eng_time 预定时间,a.remark 备注 "+
					 		 "from engage as a,roomtype as b where a.r_type_id = b.id and a.delmark = 0 and b.delmark = 0 and engagemark = 2 and a.pa_time between '"+start+"' and '"+end+"'";
			sunsql.initDTM(dtm,sqlCode);
		}
	}
	
	//取得当前的日期
	private String getDate() {
		GregorianCalendar gc = new GregorianCalendar();
		String year = gc.get(GregorianCalendar.YEAR)+"";
		//为月份补'0'
		String month = gc.get (GregorianCalendar.MONTH) + 1 + "";
		if( month.length() == 1)
			month = "0" + month;
		//为天补'0'
		String day = gc.get (GregorianCalendar.DAY_OF_MONTH) + "";
		if( day.length () == 1)
			day = "0" + day;
			
		String date = year+"-"+month+"-"+day;
		return date;
	}
	
	//取得下一天的日期
	private String tomorrow() {
		GregorianCalendar gc = new GregorianCalendar();
		int ty = gc.get(GregorianCalendar.YEAR);
		int tM = gc.get(GregorianCalendar.MONTH);
		int td = gc.get(GregorianCalendar.DAY_OF_MONTH) + 1;
		gc.set(ty, tM, td, 0, 0, 0);
		ty = gc.get(GregorianCalendar.YEAR);
		tM = gc.get(GregorianCalendar.MONTH)+1;
		td = gc.get(GregorianCalendar.DAY_OF_MONTH);
		String tomorrow = "";
		if(tM < 10) 
			tomorrow = ty + "-0" + tM;
		else 
			tomorrow = ty + "-" + tM;
		if(td < 10)
			tomorrow = tomorrow + "-0" + td;
		else
			tomorrow = tomorrow + "-" + td;
		return tomorrow;
	}
	
	/**=======================================================================**
	 *			MouseListener 监听
	 **=======================================================================**
	 */
	public void mouseClicked (MouseEvent me) {
		Object o = me.getSource();
		//弹出过滤菜单
		if(o==bt5) {
			int x = me.getX();
			int y = me.getY();
			pm.show(bt5,x,y);
		}
	}

	public void mousePressed (MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseEntered (MouseEvent me) {		//鼠标移进提示
		Object o = me.getSource ();
		if(o == bt1) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"增加房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"修改房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt3) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt4) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"查询房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt5) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"过滤房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt6) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"刷新房间预定信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt7) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"为预订房间开单　      　　　　　　　　　　　　　　　　　 　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
}