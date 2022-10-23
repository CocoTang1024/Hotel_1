/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 金额文本框
 *	[ 文件名      ]  : TJMoneyField.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 金额文本框
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/16      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## public TJMoneyField () {} ]:
 *		功能: 构造函数	金额输入本文框，默认长度6
 *
 *	[## public TJMoneyField (String text) {} ]:
 *		功能: 构造函数  金额输入本文框，默认长度10
 *
 *	[## public TJMoneyField (String text, int length) {} ]:
 *		功能: 构造函数  建立一个指定金额和长度的金额输入本文框
 *
 *	[## private void setLayout (int l) {} ]:
 *		功能: 设置金额输入本文框的对齐方式和和列数并加焦点监听
 *
 *	[## private void buildMoneyField (String text, boolean build) {} ]:
 *		功能: 制作金额输入本文框
 *
 *	[## public String getText () {} ]:
 *		功能: 获得文本框金额		重写了JTextField的getText()方法
 *
 *	[## public void setText (String text) {} ]:
 *		功能: 设置文本框金额		重写了JTextField的setText()方法
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.sunshine.sunsdk.system.*;
import com.sunshine.sunsdk.swing.*;


public class TJMoneyField 
extends TJTextField 
implements FocusListener {
	/**=======================================================================**
	 *		[## public TJMoneyField () {} ]: 				构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：金额输入本文框，默认长度6
	 **=======================================================================**
	 */
	public TJMoneyField () {
		buildMoneyField ("0.00",true);
		setLayout (6);					//设置部局
	}
	
	/**=======================================================================**
	 *		[## public TJMoneyField (String text) {} ]: 	构造函数
	 *			参数   ：String 对象表示文本框初始化的金额
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：金额输入本文框，默认长度10
	 **=======================================================================**
	 */
	public TJMoneyField (String text) {
		buildMoneyField (text, true);
		setLayout (10);					//设置部局
	}
	
	/**=======================================================================**
	 *		[## public TJMoneyField (String text, int length) {} ]: 构造函数
	 *			参数   ：String 对象表示文本框初始化的金额
	 *					 int 变量表示文本框的长度
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：建立一个指定金额和长度的金额输入本文框
	 **=======================================================================**
	 */
	public TJMoneyField (String text, int length) {
		buildMoneyField (text, true);
		setLayout (length);					//设置部局
	}
	
	/**=======================================================================**
	 *		[## private void setLayout (int l) {} ]: 构造函数
	 *			参数   ：int 变量表示文本框的长度
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：设置金额输入本文框的对齐方式和和列数并加焦点监听
	 **=======================================================================**
	 */
	private void setLayout (int l) {
		this.setColumns(l);								//设置文本框列数
		this.setHorizontalAlignment (JTextField.RIGHT);	//设置文本右对齐
		this.addFocusListener(this);					//加焦点监听
	}
	
	/**=======================================================================**
	 *		[## private void buildMoneyField (String text, boolean build) {} ]: 
	 *			参数   ：String 对象表示金额				制作金额本文框
	 *					 boolean 变量表示是新建文本框还是焦点事件调用
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：制作金额输入本文框
	 **=======================================================================**
	 */
	private void buildMoneyField (String text, boolean build) {
		//判断金额是否合法
		if(suntools.isNum (text, 11, 0, 9999999.99)) {
			//去掉金额开头的'0'
			text = Double.parseDouble(text) + "";
			//判断如果只有一位小数则补'0'
			if(text.length() - text.indexOf('.') == 2) {
				text = Double.parseDouble(text) + "0";
			}//Endif
			super.setText("￥" + text);		//设置文本
		}//Endif
		else {
			JOptionPane.showMessageDialog(null, "金额格式错误，或者是金额超出范围:[ 0-9999999.99 ] ...", "错误", JOptionPane.ERROR_MESSAGE);
			if(build)				//如果是new本类时发生错误，则系统自动退出
				System.exit(0);
			else {
				super.setText ("0");
				this.requestFocus(true);//用户使用时发生错误，则重新输入
			}//Endif
		}//Endif
	}
	
	/**=======================================================================**
	 *		[## public String getText () {} ]: 			获得文本框金额
	 *			参数   ：无
	 *			返回值 ：String 对象表示文本框的金额值
	 *			修饰符 ：public
	 *			功能   ：获得文本框金额
	 **=======================================================================**
	 */
	public String getText () {
		return super.getText().substring(1);
	}
	
	/**=======================================================================**
	 *		[## public void setText (String text) {} ]: 	设置文本框金额
	 *			参数   ：String 对象表示要设置文本框的金额值
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：设置文本框金额
	 **=======================================================================**
	 */
	public void setText (String text) {
		buildMoneyField (text, false);
	}
	
	
	
	/**=======================================================================**
	 *			FocusListener 监听
	 **=======================================================================**
	 */
	public void focusGained (FocusEvent fe) {
		//焦点监听
		super.setText ("0");
	}
	
	public void focusLost (FocusEvent fe) {
		//失去焦点监听
		String t = super.getText();
		if(t.length() > 1)
			t = t.substring(1);
		buildMoneyField (t, false);
	}
	
}