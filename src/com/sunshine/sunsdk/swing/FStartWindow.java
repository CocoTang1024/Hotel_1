/**
 *##############################################################################
 *
 *	[ 模块名       ]  : 系统启动界面
 *	[ 文件名       ]  : FStartWindow.java
 *	[ 文件实现功能 ]  : 完成初始化系统运行环境
 *	[ 版本         ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注         ]  : 用于初始化运行环境，连接数据库
 *	----------------------------------------------------------------------------
 *	[ 修改记录     ]  : 
 *
 *	[ 日  期 ]           [修改人]         [修改内容] 
 *	2006/03/22             顾俊             创建
 *
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明     ]  :
 *	
 *	[## public FStartWindow(String picName, Frame f, int waitTime) {} ] :  
 *			参数   : String 对象,表示界面背景图片;
 *					 Frame  对象,表示父窗口;
 *					 int    变量,表示启动界面等待的时间
 *			返回值 : 无 
 *  		修饰符 : public  
 *			功能   : 本类的构造函数
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing;

import javax.swing.*;
import java.awt.*;


public class FStartWindow extends JWindow {
	ImageIcon image;
	JLabel pic;
	
	//构造函数
	public FStartWindow(String picName, Frame f, int waitTime) {
		super(f);
		ImageIcon image = new ImageIcon(picName);
		JLabel pic      = new JLabel(image);
		
		this.getContentPane().add(pic, BorderLayout.CENTER);
		//调整图片大小到屏幕中央
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize  = pic.getPreferredSize();
		
		setLocation(screenSize.width/2 - (labelSize.width/2), 
					screenSize.height/2 - (labelSize.height/2));

		final int PAUSE = waitTime;
		final Runnable closerRunner = new Runnable() {
			public void run() {
				setVisible(false);
				dispose();
				//System.exit(0);
			}
		};		//End closerRunner
		
		Runnable waitRunner = new Runnable() {
			public void run() {
				try {
					Thread.sleep(PAUSE);
					SwingUtilities.invokeAndWait(closerRunner);
				}
				catch(Exception e) {
					e.printStackTrace();
					// 能够捕获InvocationTargetException
					// 能够捕获InterruptedException
				}
			}
		};		//End waitRunner
		
		setVisible(true);
		Thread waitThread = new Thread(waitRunner, "SplashThread");
		waitThread.start();
	}
	
}