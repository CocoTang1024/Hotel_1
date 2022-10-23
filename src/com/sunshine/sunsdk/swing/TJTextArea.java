/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 带监听的文本区域
 *	[ 文件名      ]  : TJTextField.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 带监听的文本区域
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
 *	[## public TJTextArea(int r, int c) {} ]:
 *		功能: 构造函数	
 *
 *	[ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TJTextArea 
extends JTextArea 
implements MouseListener {
	
	public TJTextArea(int r, int c) {
		super(r, c);
		this.setForeground(new Color(161, 149, 121));
		this.setBackground(new Color(233, 227, 214));
		this.addMouseListener(this);
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
		this.setForeground(new Color( 87,  87,  47));
		this.setBackground(new Color(248, 242, 230));
	}

	public void mouseExited (MouseEvent me) {
		this.setForeground(new Color(161, 149, 121));
		this.setBackground(new Color(233, 227, 214));
	}
}