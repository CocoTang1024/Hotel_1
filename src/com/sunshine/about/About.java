/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 阳光酒店管理系统
 *  [ 公司名      ]  : 清华IT
 *	[ 模块名      ]  : 关于窗口
 *	[ 文件名      ]  : About.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 关于窗口
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/11      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## public About (JFrame frame) {} ]:
 *		功能: 构造函数  组建关于窗口
 *
 *  [ 遗留问题    ]  : 
 *
 *##############################################################################
 */
package com.sunshine.about;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import com.sunshine.sunsdk.swing.*;
import com.sunshine.sunsdk.system.*;


public class About
extends JDialog
implements ActionListener {
	
	JLabel top, li, fee, tit, ver, user, co, boss, temp;
	JPanel pm, info, i1, i2, bott, bp;
	JButton bt1;
	
	
	/**=======================================================================**
	 *		[## public About (JFrame frame) {} ]: 	构造函数
	 *			参数   ：JFrame对象是对话框的父窗口
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：组建关于窗口
	 **=======================================================================**
	 */
	public About (JFrame frame) {
		super (frame, "关于我们", true);
		
		top = new JLabel (new ImageIcon("pic/about.gif"));
		li	= new JLabel (new ImageIcon("pic/line1.gif"));
		fee = new JLabel ("<html>技术支持: http://www.baidu.com/<br>版权所有  (C) 2022   【一路向北】 Corporation.</html>");
		tit = new JLabel ("        欢 迎 使 用 酒 店 管 理 系 统");
		ver = new JLabel ("版本:  2022 Bate        ");
		user= new JLabel ("软件用户:  " + sunini.getIniKey("UserName"));
		co	= new JLabel ("                         所属团队:【一路向北】");
		boss= new JLabel ("       【一路向北】于2022年10月18日修订     ");
		temp= new JLabel ("       ");
		bt1 = new TJButton ("pic/choose.gif", "确  定", "");
		
		pm	= new JPanel (new BorderLayout ());
		info = new JPanel (new GridLayout (4, 1, 0, 0));
		bott = new JPanel (new BorderLayout ());
		i1	 = new JPanel ();
		i2	 = new JPanel (new FlowLayout (FlowLayout.RIGHT));
		bp	 = new JPanel (new FlowLayout (FlowLayout.RIGHT, 10 ,8));
		
		//制作信息面板
		i1.add (user);
		i1.add (co);
		i2.add (ver);
		info.add (tit);
		info.add (i2);
		info.add (i1);
		info.add (boss);
		
		//制作下边组件
		bp.add (bt1);
		bott.add ("North", li);
		bott.add ("West", temp);
		bott.add ("East", bp);
		bott.add ("Center", fee);
		
		//制作主面板
		pm.add ("North", top);
		pm.add ("South", bott);
		pm.add ("Center", info);
		
		bt1.addActionListener (this);
		
		this.setContentPane (pm);
		this.setResizable (false);
		pack();
		sunswing.setWindowCenter(this);
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed (ActionEvent ae) {
		this.setVisible (false);
	}
	
}
