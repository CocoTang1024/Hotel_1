/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 滑动菜单
 *	[ 文件名      ]  : SlitherMenu.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 防QQ一样的滑动菜单
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
 *	[## public SlitherMenu() {} ]:
 *		功能: 滑动菜单的构造函数 (无部局面板)
 *
 *	[## public void addPanel(String name,String ico, int index, int quantity) {} ]:
 *		功能: 制作按键模板，并加入主模板
 *
 *	[## public void addButton(String ico, String name, String tooltip, int index) {} ]:
 *		功能: 制作功能按键，并加入相应的模板中
 *
 *	[## public void initButtonPanelDimension() {} ]:
 *		功能: 初始化每个按键模板的最大尺寸(在主窗口的setVisible()方法之前使用)
 *
 *	[## public void setInitMenu() {} ]: 
 *		功能: 设置初始化后展开第一项菜单(在主窗口的setVisible()方法之后使用)
 *
 *	[## public void setMenuDimension(int w,int h) {} ]: 
 *		功能: 设置菜单宽度和高度(在初始化菜单后加入组件之前使用)
 *
 *	[## public void setMenuLocation(int x, int y) {} ]: 
 *		功能: 设置菜单的坐标(在初始化菜单后加入组件之前使用)
 *
 *	[## public void setTitleHeight(int h) {} ]: 
 *		功能: 设置模板标题按键高度(当标题按键有背景图标时才使用本方法)
 *
 *	[## public void setButtonPanelBackground(Color bg) {} ]: 
 *		功能: 设置按键模板背景颜色(在初始化菜单后加入组件之后使用)
 *
 *	[## public String getSelectButtonName() {} ]: 
 *		功能: 获得选中的按键名
 *
 *	[## private void slither(int index) {} ]:
 *		功能: 处理菜单滑动效果
 *
 *  [ 遗留问题    ]  : 	1、在改变窗口大小时，不能重画菜单
 *						2、不能给各按键模板设置背景图案
 *						3、不能改变按键的前景颜色
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing;
 
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;



public class SlitherMenu 
extends JPanel 
implements ActionListener {
	
	//按键模板数组
	private ArrayList template = new ArrayList();
	//放置按键面板的数组
	private ArrayList buttonPanels = new ArrayList();
	//触发ActionListener事件的按键名
	private String selectButtonName = "";
	//当前展开模板的序号
	private int selectPanelNumber	= 0;
	//将要展开模板的序号
	private int selectPanelNumberNew = 0;
	
	//模板总数
	private int panelconut = 0;
	//定义滑动菜单的坐标
	private int sm_X = 0;
	private int sm_Y = 0;
	//定义滑动菜单的宽度和高度
	private int slitherMenuBar_Width  = 60;
	private int slitherMenuBar_Height = 300;
	//按键组模板缩起后的高度
	private int titleHeight = 28;
	
	
	
	/**=======================================================================**
	 *		[## public SlitherMenu() {} ]: 	构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：滑动菜单的构造函数 (无部局面板)
	 **=======================================================================**
	 */
	public SlitherMenu() {
		super(null);		//设置主面板为无部局
	}
	
	/**=======================================================================**
	 *		[## public void addPanel(String name,String ico, int index, int quantity) {} ]:
	 *			参数   ：String name 表示按键模板的标题按键名
	 *					 String ico	 表示标题栏的背景图标
	 *					 int index	 表示按键模板的索引序号
	 *					 int quantity表示模板内将要放入按键的数量
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：制作按键模板，并加入主模板
	 **=======================================================================**
	 */
	public void addPanel(String name,String ico, int index, int quantity) {
		JButton titleButton;
		JScrollPane spPanel;
		JPanel panelMain, buttonPanel;
		
		titleButton = new JButton(name, new ImageIcon(ico));	//制作标题按键
		panelMain	= new JPanel(new BorderLayout(0, 0));		//框架面板为边界部局
		buttonPanel = new JPanel(new GridLayout(quantity, 1));	//按键组面板为表格部局
		spPanel		= new JScrollPane(buttonPanel);				//制作滚动面板
		spPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//设置标题按键属性, 如果图标为空，则默认Java原有属性
		if(ico.length() > 0) {
			titleButton.setFocusPainted(false);					//设置焦点外框为假
			titleButton.setBorderPainted(false);				//设置按键无边框
			titleButton.setContentAreaFilled(false);			//设置按键背景色透明
			titleButton.setHorizontalTextPosition(SwingConstants.CENTER); //设置Ico与文字居中
			//panelMain.setBorder(new LineBorder(new Color(184, 207, 229)));//加外框线
		}//Endif
		
		//对标题按键加事件监听
		titleButton.addActionListener(this);
		
		titleButton.setName(index+ "");				//设置框架面板标志
		panelMain.add("North", titleButton);		//加入标题按键
		panelMain.add("Center",spPanel);			//加入按键组面板
		this.add(panelMain);						//放入菜单面板
		panelconut++;								//面板记数器+1
		
		template.add(index, panelMain);				//将按键模板存入数组
		buttonPanels.add(index, buttonPanel);		//将放置按键的面板存入数组
	}
	
	/**=======================================================================**
	 *		[## public void addButton(String ico, String name, String tooltip, int index) {} ]:
	 *			参数   ：String name	表示功能按键的名字
	 *					 String ico		表示功能按键的背景图标
	 *					 String tooltip 表示按键的气泡提示文本
	 *					 int index		表示按键要放入哪个模板
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：制作功能按键，并加入相应的模板中
	 **=======================================================================**
	 */
	public void addButton(String ico, String name, String tooltip, int index) {
		JButton button = new JButton(name, new ImageIcon(ico));
		button.setToolTipText(tooltip);				//设置按键提示
		button.setBorderPainted(false);				//设置按键无边框
		button.setContentAreaFilled(false);			//设置按键背景色透明
		button.setHorizontalTextPosition(SwingConstants.CENTER);//设置Ico与文字居中
		button.setVerticalTextPosition(SwingConstants.BOTTOM);//设置Ico相对文字的位置
		button.addActionListener(this);						  //加事件监听
		((JPanel)buttonPanels.get(index)).add(button);		  //将按键加入按键面板
	}
	
	/**=======================================================================**
	 *		[## public void initButtonPanelDimension() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：初始化每个按键模板的最大尺寸(在主窗口的setVisible()方法之前使用)
	 **=======================================================================**
	 */
	public void initButtonPanelDimension() {
		for (int i = 0; i < panelconut; i++) {
			((JPanel)template.get(i)).setBounds(sm_X, sm_Y, slitherMenuBar_Width, 
			slitherMenuBar_Height - titleHeight * (panelconut - 1));
	    }//Endfor
	}
	
	/**=======================================================================**
	 *		[## public void setInitMenu() {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：设置初始化后展开第一项菜单(在主窗口的setVisible()方法之后使用)
	 **=======================================================================**
	 */
	public void setInitMenu() {
		if(panelconut > 0) {
			JPanel mainMenu  = (JPanel)template.get(0);			//展开第一项菜单
			int titlesHeight = (panelconut - 1) * titleHeight;	//获得其它模板标题的高度总和
			int height = slitherMenuBar_Height - titlesHeight;
			mainMenu.setBounds(sm_X, sm_Y, slitherMenuBar_Width, height);
			//处理其它菜单标题
			for (int i = 1; i < panelconut; i++) {
				((JPanel)template.get(i)).setBounds(sm_X, sm_Y + height + (i - 1) *  titleHeight, 
													slitherMenuBar_Width, titleHeight);
		    }//Endfor
		}else {
			String msg = "没有按键模板可组织。";
			JOptionPane.showMessageDialog(null, msg, "错误",JOptionPane.ERROR_MESSAGE);
		}//Endif
	}
	
	/**=======================================================================**
	 *		[## public void setMenuDimension(int w,int h) {} ]: 
	 *			参数   ：int w 表示菜单宽度，int h 表示菜单高度
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：设置菜单宽度和高度(在初始化菜单后加入组件之前使用)
	 **=======================================================================**
	 */
	public void setMenuDimension(int w,int h) {
		if(panelconut == 0) {
			slitherMenuBar_Width  = w;
			slitherMenuBar_Height = h;
		}else {
			String msg = "setMenuDimension()方法请在加入组件之前使用。";
			JOptionPane.showMessageDialog(null, msg, "警告",JOptionPane.WARNING_MESSAGE);
		}//Endif
	}
	
	/**=======================================================================**
	 *		[## public void setMenuLocation(int x, int y) {} ]: 
	 *			参数   ：int x 表示菜单的横向坐标，int y 表示菜单的纵向坐标
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：设置菜单的坐标(在初始化菜单后加入组件之前使用)
	 **=======================================================================**
	 */
	public void setMenuLocation(int x, int y) {
		if(panelconut == 0) {
			sm_X = x;
			sm_Y = y;
		}else {
			String msg = "setMenuLocation()方法请在加入组件之前使用。";
			JOptionPane.showMessageDialog(null, msg, "警告",JOptionPane.WARNING_MESSAGE);
		}//Endif
	}
	
	/**=======================================================================**
	 *		[## public void setTitleHeight(int h) {} ]: 
	 *			参数   ：int h 表示按键模板的标题键的高度
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：设置模板标题按键高度(当标题按键有背景图标时才使用本方法)
	 **=======================================================================**
	 */
	public void setTitleHeight(int h) {
		titleHeight = h;
	}
	
	/**=======================================================================**
	 *		[## public void setButtonPanelBackground(Color bg) {} ]: 
	 *			参数   ：Color 对象表示按键模板的背景颜色
	 *			返回值 ：无
	 *			修饰符 ：public
	 *			功能   ：设置按键模板背景颜色(在初始化菜单后加入组件之后使用)
	 **=======================================================================**
	 */
	public void setButtonPanelBackground(Color bg) {
		if(panelconut > 0) {
			for (int i = 0; i < panelconut; i++) {
				((JPanel)buttonPanels.get(i)).setBackground(bg);
	   		}//Endfor
		}else {
			String msg = "setButtonPanelBackground()方法请在加入组件之后使用。";
			JOptionPane.showMessageDialog(null, msg, "警告",JOptionPane.WARNING_MESSAGE);
		}//Endif
		
	}
	
	/**=======================================================================**
	 *		[## public String getSelectButtonName() {} ]: 
	 *			参数   ：无
	 *			返回值 ：String 对象表示触发事件的功能按键的名字
	 *			修饰符 ：public
	 *			功能   ：获得选中的按键名
	 **=======================================================================**
	 */
	public String getSelectButtonName() {
		return selectButtonName;
	}
	
	/**=======================================================================**
	 *		[## private void slither(int index) {} ]: 
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：处理菜单滑动效果
	 **=======================================================================**
	 */
	private void slither(int index) {
		//获得其它标题的高度总和
		int sp_h = slitherMenuBar_Height - titleHeight * (panelconut - 1);
		
		if(index == selectPanelNumber) {			//如果是当前面板，则不处理
			return;					
		}else if(index > selectPanelNumber) {		//菜单上移动作
			int sp_y = titleHeight * (selectPanelNumber + 1);
			for (int i = sp_h; i >= titleHeight; i --) {
				//当前展开面板缩起
				((JPanel)template.get(selectPanelNumber)).setSize(slitherMenuBar_Width, i);	
				//处理当前展开面板与将要展开面板之间的标题
				for (int j = selectPanelNumber + 1; j < index; j++) {
					int other_Y = ((JPanel)template.get(j)).getY() - 1;		
					((JPanel)template.get(j)).setLocation(sm_X, other_Y);
			    }//Endfor
			    //新面板展开
			    int index_Y = ((JPanel)template.get(index)).getY() - 1;
			    int index_H = ((JPanel)template.get(index)).getHeight() + 1;
			    ((JPanel)template.get(index)).setBounds(sm_X, index_Y, slitherMenuBar_Width, index_H);
		    }//Endfor
		}else if(index < selectPanelNumber) {		//下移动作
			int sp_y = titleHeight * (selectPanelNumber - 2);
			for (int i = sp_h; i >= titleHeight; i --) {
				//当前展开面板缩起
				int spy = ((JPanel)template.get(selectPanelNumber)).getY() + 1;
			    ((JPanel)template.get(selectPanelNumber)).setBounds(sm_X, spy, slitherMenuBar_Width, i);
				//处理当前展开面板与将要展开面板之间的标题
				for (int j = selectPanelNumber - 1; j > index; j--) {
					int other_Y = ((JPanel)template.get(j)).getY() + 1;		
					((JPanel)template.get(j)).setLocation(sm_X, other_Y);
			    }//Endfor
			    //新面板展开
			    int index_H  = ((JPanel)template.get(index)).getHeight() + 1;
			    ((JPanel)template.get(index)).setSize(slitherMenuBar_Width, index_H);
		    }//Endfor
		}//Endif
		this.validate();		//确定当前菜单的变化
	}
	
	/**=======================================================================**
	 *			ActionListener 监听
	 **=======================================================================**
	 */
	public void actionPerformed ( ActionEvent ae ) {
		//获得触发事件的按键Name
		selectButtonName = ((JButton)ae.getSource()).getName();
		
		if(selectButtonName != null) {		//不为空则选择的就是标题按键
			//获得被选中模板的序号
			selectPanelNumberNew = Integer.parseInt(selectButtonName);
			//处理菜单滑动效果
			slither(selectPanelNumberNew);
			selectPanelNumber = selectPanelNumberNew;
		}
		else {
			//获得功能按键的名字
			selectButtonName = ((JButton)ae.getSource()).getText();
		}//Endif
	}
	
}