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
import com.sunshine.sunsdk.system.*;
import com.sunshine.mainframe.HotelFrame;

public class Record 
extends JDialog 
implements ActionListener,MouseListener {
	
	private JButton bt1,bt2;
	private JTextField tf1,tf2,tf;
	private JTable tb;
	private DefaultTableModel dtm;
	private JScrollPane sp;
	private JPanel panelMain,pc,pn;
	
	public Record(JFrame frame) {
		super (frame, "系统日志", true);
		panelMain = new JPanel(new BorderLayout());
		buildPC();
		buildPN();
		
		panelMain.add("North",pn);
		panelMain.add(pc);
		
		this.setContentPane(panelMain);
		this.setPreferredSize (new Dimension (800,450));
		this.setMinimumSize (new Dimension (800,450));
		this.setResizable(false);		//不允许改变窗口大小
		pack();
		sunswing.setWindowCenter(this);	//窗口屏幕居中
		addListener();
	}
	
	private void addListener() {
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		tf1.addActionListener(this);
		bt1.addMouseListener(this);
		bt2.addMouseListener(this);
	}
	
	//制作表格面板
	private void buildPC() {
		pc = new JPanel(new BorderLayout(0,0));
		dtm = new DefaultTableModel();
		tb  = new JTable(dtm);
		sp  = new JScrollPane(tb);
		
		tf = new JTextField("日志信息");
		tf.setHorizontalAlignment (JTextField.CENTER);
		tf.setBackground(new Color(199,183,143));
		tf.setBorder(new LineBorder(new Color(87,87,47)));
		tf.setEditable(false);
		
		pc.add("North",tf);
		pc.add(sp);
		pc.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	public void initDTM() {
		String sqlCode;
		sqlCode = "select pk,time 操作时间,operator 操作员,brief 内容摘要,content 内容 from record where delmark = 0 ";
		sunsql.initDTM(dtm,sqlCode);
		tb.removeColumn(tb.getColumn("pk"));
	}
	
	//制作起始终止时间及按钮面板
	private void buildPN() {
		pn = new JPanel();
		JLabel lb1,lb4,lb7,lb;
		lb1 = new JLabel("起始时间：");
		lb4 = new JLabel("　      终止时间：");
		lb7 = new JLabel("                   ");
		lb  = new JLabel("       ");
		
		tf1 = new TJTextField (12);
		tf2 = new TJTextField (12);
		
		bt1 = new TJButton ("pic/find.gif", "查询", "查询日志信息");
		bt2 = new TJButton ("pic/del.gif", "删除", "删除日志信息");
		
		pn.add(lb1);
		pn.add(tf1);
		pn.add(lb4);
		pn.add(tf2);
		pn.add(lb7);
		pn.add(bt1);
		pn.add(lb);
		pn.add(bt2);
	}
	

	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o==bt1) {//============================================查询
			String start,end;
			start = tf1.getText();
			end = tf2.getText();
			if(!suntools.isDate(end)||!suntools.isDate(start)) {
				//若日期不合法
				JOptionPane.showMessageDialog(null,"时间输入有误,请正确输入(yyyy-mm-dd)");
				tf2.setText("");
				tf1.setText("");
				tf1.requestFocus();
			}else {//若日期合法
				start = tf1.getText()+" 00:00:00";
				end = tf2.getText()+" 23:59:59";
				String sqlCode = "select pk,time 操作时间,operator 操作员,brief 内容摘要,content 内容 from record where delmark = 0 and time between '"+start+"' and '"+end+"' ";
				sunsql.initDTM(dtm,sqlCode);
				tb.removeColumn(tb.getColumn("pk"));
			}
			tf1.setText("");
			tf2.setText("");
		}
		else if(o==bt2) {//========================================删除
			int rRow[] = tb.getSelectedRows();
			int rowCount = rRow.length;
			if(rowCount > 0) {
				int isDel = JOptionPane.showConfirmDialog (null, "确定要删除日志记录吗?", "提示", JOptionPane.YES_NO_OPTION);
				if(isDel == JOptionPane.YES_OPTION) {
					String sqlCode[] = new String[rowCount];
					//生成SQL语句
					for (int i = 0; i < rowCount; i++) {
						String pk = dtm.getValueAt(rRow[i], 0)+"";
						sqlCode[i] = "update record set delmark = 1 where pk= "+pk;
				    }//Endfor
				    //以事务模式执行SQL语句组, 确保操作正确, 返回值为成功执行SQL语句的条数
				    isDel = sunsql.runTransaction(sqlCode);		
				    if(isDel != rowCount) {			//如果成功执行的条数 = 数组长度，则表示更新成功
				    	String mm = "在执行第 [ " + (isDel + 1) + " ] 条记录的删除操作时出错，数据有可能被其它终端修改\n或者是网络不通畅 ...";
				    	JOptionPane.showMessageDialog(null, mm, "错误",JOptionPane.ERROR_MESSAGE);
				    }//Endif
				    else {
				    	initDTM();
				    }
				}//Endif
				
			}
			tf1.setText("");
			tf2.setText("");
		}
		else if(o==tf1) {
			if(!suntools.isDate(tf1.getText())) {
				//若日期不合法
				JOptionPane.showMessageDialog(null,"起始时间输入有误,请正确输入(yyyy-mm-dd)");
				tf1.setText("");
				tf1.requestFocus();
			}else {//若日期合法
				tf2.requestFocus();
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
			"查询系统日志信息      　　　　　　　　　　　　　　　　　 　");
		}else if(o == bt2) {
			HotelFrame.lbA.setText (HotelFrame.clue + 
			"删除系统日志信息      　　　　　　　　　　　　　　　　　 　");
		}
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + "请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
}