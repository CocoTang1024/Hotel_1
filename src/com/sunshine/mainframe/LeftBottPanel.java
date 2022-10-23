/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 主窗口左边下部面板
 *	[ 文件名      ]  : LeftBottPanel.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 快速通道，可以实现输入房间号立即显示信息
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/18      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :	
 *	
 *	[## public LeftBottPanel() {} ]:
 *		功能: 组件主窗口左边下部面板
 *
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.mainframe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;			//公共类
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;


public class LeftBottPanel
extends JPanel
implements ActionListener, MouseListener, FocusListener {
	
	private TJTextField tf;
	private JLabel lb;
	
	/**=======================================================================**
	 *		[## public LeftBottPanel() {} ]: 				构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组件主窗口左边下部面板
	 **=======================================================================**
	 */
	public LeftBottPanel() {
		//设置主面板为边界部局
		super (new BorderLayout());
		
		lb = new JLabel (new ImageIcon ("pic/sunshine.gif"));
		tf = new TJTextField ("     快 速 通 道", 10);
		lb.setBorder (new LineBorder (new Color (199, 183, 143)));
		tf.setLineWidth(1);
		//设置气泡提示信息
		tf.setToolTipText("输入房间号码，可直接获得房间的所有信息");
		//设置字体
		tf.setFont (new Font ("宋体", Font.PLAIN, 15));
		
		this.add ("North", tf);
		this.add ("Center", lb);
		
		tf.addActionListener(this);
		tf.addMouseListener (this);
		tf.addFocusListener (this);
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed (ActionEvent ae) {
		try {
			ResultSet rs = sunsql.executeQuery("select a.r_type from roomtype " +
			"a,(select r_type_id from roominfo where delmark=0 and id='" + tf.getText() + 
			"') b where a.delmark=0 and a.id=b.r_type_id");			//获得当前房间的类型名称
			
			if(rs.next()) {
				
				String chooseRoomNum = tf.getText();
				LeftTopPanel.title0.setText(rs.getString(1));
				LeftTopPanel.title1.setText(chooseRoomNum);
				
				//宾客名称，入住时间，已交押金，已用时间
				rs = sunsql.executeQuery("select c_name,in_time,foregift from livein " +
				"where delmark=0 and statemark='正在消费' and r_no='" + chooseRoomNum + "'");
				
				//入住时间
				String inTime = "";
				
				if(rs.next()) {
					LeftTopPanel.lt[0].setText(rs.getString(1));
					inTime = rs.getString(2);
					LeftTopPanel.lt[4].setText(inTime.substring(0, 10));
					LeftTopPanel.lt[5].setText(suntools.getConsumeHour(inTime, Journal.getNowDTime()));
					LeftTopPanel.lt[6].setText(rs.getString(3));
				}else {
					LeftTopPanel.lt[0].setText("");
					LeftTopPanel.lt[4].setText("");
					LeftTopPanel.lt[5].setText("");
					LeftTopPanel.lt[6].setText("");
				}//Endif
				
				//房间所在区域，房间电话		顺便取出房间状态为计算是住宿还是钟点房
				rs = sunsql.executeQuery("select r_tel,location,state from roominfo where id='" + 
				chooseRoomNum + "' and delmark=0");
				if(rs.next()) {
					LeftTopPanel.lt[2].setText(rs.getString(1));
					LeftTopPanel.lt[3].setText(rs.getString(2));
				}else {
					LeftTopPanel.lt[2].setText("");
					LeftTopPanel.lt[3].setText("");
				}//Endif
				
				//获得房间状态
				String rState = rs.getString(3);
				
				//预设房价,钟点房价		下面SQL语句是通过房间号获得房间类型标准单价, 钟点房价为下面应收金额计算用
				rs = sunsql.executeQuery("select a.price,a.cl_price from roomtype a, (select " +
				"r_type_id from roominfo where delmark=0 and id='" + chooseRoomNum + "') b where " +
				"a.delmark=0 and a.id=b.r_type_id");
				if(rs.next())
					LeftTopPanel.lt[1].setText("￥" + rs.getString(1));
				else 
					LeftTopPanel.lt[1].setText("");
				
				//获得房间状态以选择计费方式----单价
				double money = 0;
				if(rState.equals("占用"))			//普通入住计费单价
					money = Double.parseDouble(LeftTopPanel.lt[1].getText().substring(1));
				else
					money = rs.getDouble(2);		//钟点房计费单价
				
				//应收金额		下面SQL语句是通过房间号获得宾客折扣比例
				rs = sunsql.executeQuery("select a.discount from customertype a," +
				"(select c_type_id,r_type_id from livein  where r_no='" + chooseRoomNum + 
				"' and statemark='正在消费' and delmark=0) b where a.delmark=0 and " +
				"a.id=b.c_type_id and a.dis_attr=b.r_type_id");
				
				if(rs.next()) {
					if(rState.equals("占用"))		//计算普通入住应收费用
						money = money * suntools.getConsumeFactor(inTime, Journal.getNowDTime());
					else							//计算钟点房应收费用
						money = money * suntools.getClockFactor(inTime, Journal.getNowDTime());
					
					//以宾客折扣比例，打折当前应收金额
					money = money * rs.getDouble(1)/10;
					LeftTopPanel.lt[7].setText("￥" + money);
				}else {
					LeftTopPanel.lt[7].setText("");
				}//Endif
				
				//刷新右下面板表数据
				if(rState.equals("可供")) {
					RightBottPanel.listRightBottDTM("", false);		//执行刷新
				}else {
					String rbCode = "select a.in_no 入住单号,a.main_room 主房间号,b.price " +
					"标准单价,b.c_type 宾客类型,b.discount 享受折扣,b.dis_price 消费金额,in_time " +
					"消费时间,userid 记帐人 from livein a, customertype b where a.r_no='" + 
					chooseRoomNum + "' and " + "statemark='正在消费' and a.c_type_id=b.id " +
					"and b.dis_attr=a.r_type_id and a.delmark=0";
				
					RightBottPanel.listRightBottDTM(rbCode, true);		//执行刷新
				}//Endif
				
			}else {
				JOptionPane.showMessageDialog(null, "系统中没有 [ " + tf.getText() +
				" ] 的房间信息", "提示", JOptionPane.INFORMATION_MESSAGE);
			}//Endif
	    }
	    catch (Exception ex) {
	    	System.out.println ("LeftBottPanel.actionPerformed() false");
	    }//Endtry
	    tf.requestFocus(false);
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
		HotelFrame.lbA.setText (HotelFrame.clue + 
		"输入房间号码，可直接获得房间的所有信息   　　　　　　　　");
	}

	public void mouseExited (MouseEvent me) {
		HotelFrame.lbA.setText (HotelFrame.clue + 
		"请选择功能项 ...   　　　　　　　　　　　　　　　　　　　　");
	}
	
	/**=======================================================================**
	 *			FocusListener 监听
	 **=======================================================================**
	 */
	public void focusGained (FocusEvent fe) {
		//焦点监听
		tf.setText("");
	}
	
	public void focusLost (FocusEvent fe) {
		//失去焦点监听
		tf.setText("     快 速 通 道");
	}
}