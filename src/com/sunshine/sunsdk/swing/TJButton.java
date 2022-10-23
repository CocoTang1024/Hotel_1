/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 制作带ICO和气泡提示的按键
 *	[ 文件名      ]  : TJButton.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 制作带ICO和气泡提示的按键
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.1
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/03/31      1.0             顾俊            创建
 *	2006/04/06      1.1             顾俊            增加构造函数
 *	2006/04/20      1.1             顾俊            增加监听效果
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007, SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## public TJButton(String picName, String text, String toolTip) {} ]:
 *		功能: 构造函数	制作带ICO和气泡提示的按键
 *
 *	[## public TJButton(String picName, String text, String toolTip,boolean flag) {} ]:
 *		功能: 制作带ICO和气泡提示的工具栏按键
 *
 *	[## private buildButton(String picName, String text, String toolTip,boolean flag) {} ]:
 *		功能: 制作带ICO和气泡提示的工具栏按键,仅类内使用
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TJButton 
extends JButton 
implements MouseListener {
	
	/**=======================================================================**
	 *		[## public TJButton(String picName, String text, String toolTip) {} ]
	 *			构造函数
	 *			参数   ：String picName 表示按键上的ICO文件名
	 *					 String text 表示按键的名称
	 *					 String toolTip 表示按键的ToolTip标示
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：制作带ICO和气泡提示的按键
	 **=======================================================================**
	 */
	public TJButton(String picName, String text, String toolTip) {
		buildButton(picName, text, toolTip, false);
	}
	
	/**=======================================================================**
	 *		[## public TJButton(String picName, String text, String toolTip,boolean flag) {} ]
	 *			构造函数
	 *			参数   ：String picName 表示按键上的ICO文件名
	 *					 String text 表示按键的名称
	 *					 String toolTip 表示按键的ToolTip标示
	 *					 boolean 表示按键为大图标工具栏模式
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：制作带ICO和气泡提示的工具栏按键
	 **=======================================================================**
	 */
	public TJButton(String picName, String text, String toolTip,boolean flag) {
		buildButton(picName, text, toolTip, flag);
	}
	
	/**=======================================================================**
	 *		[## private buildButton(String picName, String text, String toolTip,boolean flag) {} ]
	 *			制作按键函数
	 *			参数   ：String picName 表示按键上的ICO文件名
	 *					 String text 表示按键的名称
	 *					 String toolTip 表示按键的ToolTip标示
	 *					 boolean 表示按键为大图标工具栏模式
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作带ICO和气泡提示的工具栏按键,仅类内使用
	 **=======================================================================**
	 */
	private void buildButton(String picName, String text, String toolTip,boolean flag) {
		this.setIcon (new ImageIcon (picName));
		this.setText (text);
		this.setToolTipText (toolTip);
		if(flag) {
			//this.setBorderPainted (false);
			this.setContentAreaFilled(false);		//设置背景色透明
			this.setHorizontalTextPosition (SwingConstants.CENTER);
			this.setVerticalTextPosition (SwingConstants.BOTTOM);
		}//End if(flag)
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
		this.setForeground(new Color(156, 126,  66));
		this.setBackground(new Color(234, 223, 203));
	}

	public void mouseExited (MouseEvent me) {
		this.setForeground(new Color( 87,  87,  47));
		this.setBackground(new Color(231, 215, 183));
	}
}