/*
 * @(#)CsUIManager.java	2006-4-13
 * 
 * public class
 *
 * Copyright 2006 CazoSoft, Inc. All rights reserved.
 */
package com.sunshine.sunsdk.swing;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.metal.*;

/**
 * <code> CsUIManager </code>
 * <p>
 * 说明：本类为外观管理器
 * <p>
 * <strong>警告：</strong>
 * 使用金属（BOLD）外观时，输入中文会有异常，但不影响系统运行
 * <p>
 * 相关库文件：o.jar
 * 
 * @author 鲜强
 * @version 2006-4-13 23:05:39
 * @since CAZOSOFT 1.0
 */
public class CsUIManager {

    //Windows 外观
    public static final int WINDOWS = 0;

    //Java 默认外观
    public static final int METAL = 1;

    //Linux 外观
    public static final int MOTIF = 2;

    //WindowsClassic 外观
    public static final int WINDOWSCLASSIC = 3;

    //Alloy 外观
    public static final int ALLOY = 4;

    //Alloy 玻璃外观
    public static final int GLASSTHEMEALLOY = 5;

    //Alloy 迷幻外观
    public static final int ACIDTHEMEALLOY = 6;

    //Alloy 贝多因外观
    public static final int BEDOUIDTHEMEALLOY = 7;

    //Alloy 默认外观
    public static final int DEAFULTTHEMEALLOY = 8;
    
    //Bold 外观
    public static final int BOLD = 9;
    
    /**
	 * 界面风格选择器
	 * 
	 * @param cp
	 *            接受要改变的控件
	 * @param style
	 *            接受界面下标
	 * @return 返回成功设置与否
	 */
	public static boolean setUI(Component cp, int style) {

		try {
			switch (style) {
			case 0:
				UIManager.setLookAndFeel("com.sun.java.swing.plaf."
						+ "windows.WindowsLookAndFeel");
				break;
			case 1:
				UIManager.setLookAndFeel("javax.swing.plaf."
						+ "metal.MetalLookAndFeel");
				break;
			case 2:
				UIManager.setLookAndFeel("com.sun.java.swing.plaf."
						+ "motif.MotifLookAndFeel");
				break;
			case 3:
				UIManager.setLookAndFeel("com.sun.java.swing.plaf."
						+ "windows.WindowsClassicLookAndFeel");
				break;
			case 4:
				UIManager.setLookAndFeel("com.incors.plaf."
						+ "alloy.AlloyLookAndFeel");
				break;
			case 5:
				UIManager.setLookAndFeel("soft.wes.feels."
						+ "GlassThemeAlloyLookAndFeel");
				break;
			case 6:
				UIManager.setLookAndFeel("soft.wes.feels."
						+ "AcidThemeAlloyLookAndFeel");
				break;
			case 7:
				UIManager.setLookAndFeel("soft.wes.feels."
						+ "BedouinThemeAlloyLookAndFeel");
				break;
			case 8:
				UIManager.setLookAndFeel("soft.wes.feels."
						+ "DefaultThemeAlloyLookAndFeel");
				break;
			case 9:
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				//可设置对话框外观
				JDialog.setDefaultLookAndFeelDecorated(true);
				//可设置设置窗口外观
				JFrame.setDefaultLookAndFeelDecorated(true);
				Toolkit.getDefaultToolkit().setDynamicLayout(true);
				System.setProperty("sun.awt.noerasebackground", "true");
				UIManager.setLookAndFeel(new MetalLookAndFeel());
				break;
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "更换风格失败,以原风格显示");
			return false;
		}
		SwingUtilities.updateComponentTreeUI(cp);
		cp.repaint();
		
		return true;
	}
}
