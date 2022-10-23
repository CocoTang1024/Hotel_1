/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 主窗口右边下部面板
 *	[ 文件名      ]  : RightBottPanel.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 与客房有关的信息列表
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
 *	[## public RightBottPanel() {} ]:
 *		功能: 右下面板构造函数
 *
 *	[## public static void listRightBottDTM(String sqlCode, boolean flag) {} ]: 
 *		功能: 为DTM加入数据，无数据时显示空行
 *
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.mainframe;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import com.sunshine.sunsdk.sql.*;				//公共类


public class RightBottPanel extends JPanel {
	
	public static DefaultTableModel rbDTM;
	private JTable tb;
	private JScrollPane sp;
	

	/**=======================================================================**
	 *		[## public RightBottPanel() {} ]: 				构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：右下面板构造函数
	 **=======================================================================**
	 */
	public RightBottPanel() {
		super (new BorderLayout());
		//声名表格
		rbDTM = new DefaultTableModel ();
		tb	  = new JTable (rbDTM);
		sp	  = new JScrollPane (tb);
		
		//初始化消费表==假象
		listRightBottDTM("", false);
		
		tb.setRowHeight (18);			//表格的行高度
		tb.setEnabled(false);
		tb.setMinimumSize(new Dimension(600, 90));
		sp.setBorder(new LineBorder(new Color(199, 183, 143)));
		//将表格加入面板
		this.add("Center", sp);
	}
	
	/**=======================================================================**
	 *		[## public static void listRightBottDTM(String sqlCode, boolean flag) {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：为DTM加入数据，无数据时显示空行
	 **=======================================================================**
	 */
	public static void listRightBottDTM(String sqlCode, boolean flag) {
		String code[] = new String[6];
		code[0] = "delete from expense_temp";
		
		if(flag) {
			code[1] = "insert into expense_temp(in_no,r_no,price,c_name," +
		"discount,money,in_time,userid) (" + sqlCode + ")";
		}else {
			code[1] = "insert into expense_temp(pk) values('')";
		}//Endif
		
		for (int i = 2; i < 6; i++) {
			code[i] = "insert into expense_temp(pk) values('')";
	    }//Endif
	    
	    sunsql.runTransaction(code);		//执行事务
	    
	    sunsql.initDTM(rbDTM, "select in_no 入住单号,r_no 主房间号,price 标准单价," +
		"c_name 宾客类型,discount 享受折扣,money 消费金额,in_time 消费时间," + 
		"userid 记账人 from expense_temp");
	}
}
















