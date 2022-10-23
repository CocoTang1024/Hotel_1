/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 带监听的密码文本框
 *	[ 文件名      ]  : TJPasswordField.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 带监听的密码文本框
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/19      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## public TJPasswordField(String text) {} ]:
 *		功能: 构造函数	
 *
 *	[## public TJPasswordField(String text, int column) {} ]:
 *		功能: 构造函数  
 *	
 *	[## public void setLineWidth(int l) {} ]:
 *		功能: 设置外框线宽
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;


public class TJPasswordField 
extends JPasswordField 
implements MouseListener, FocusListener {
	
	private int thickness = 1;
	
	public TJPasswordField() {
		setStyle();
	}
	
	public TJPasswordField(int col) {
		super(col);
		setStyle();
	}
	
	public TJPasswordField(String text) {
		super(text);
		setStyle();
	}
	
	public TJPasswordField(String text, int column) {
		super(text, column);
		setStyle();
	}
	
	public void setLineWidth(int l) {
		thickness = l;
		this.setBorder(new LineBorder(new Color(159, 145, 118), thickness));
	}
	
	private void setStyle() {
		this.setForeground(new Color( 87,  87,  47));
		this.setBackground(new Color(248, 242, 230));
		this.setBorder(new LineBorder(new Color(159, 145, 118), thickness));
		this.addMouseListener(this);
		this.addFocusListener(this);
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

	public void mouseEntered (MouseEvent me) {
		this.setBorder(new LineBorder(new Color(241, 171, 84), thickness));
	}

	public void mouseExited (MouseEvent me) {
		this.setBorder(new LineBorder(new Color(159, 145, 118), thickness));
	}
	
	/**=======================================================================**
	 *			FocusListener 监听
	 **=======================================================================**
	 */
	public void focusGained (FocusEvent fe) {
		this.setBorder(new LineBorder(new Color(241, 171, 84), thickness));
	}
	
	public void focusLost (FocusEvent fe) {
		this.setBorder(new LineBorder(new Color(159, 145, 118), thickness));
	}
}