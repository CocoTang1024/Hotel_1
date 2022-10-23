/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 沙漠色主题类
 *	[ 文件名      ]  : DesertMetalTheme.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 配置窗口为沙漠色主题
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.0
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/30      1.0             顾俊            创建
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## public String getName() {} ]:
 *		功能: 返回主题名
 *
 *  [ 遗留问题    ]  : 	
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.swing.Theme;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.border.*;
import java.awt.*;


public class DesertMetalTheme extends DefaultMetalTheme {

    public String getName() { return "Desert"; }

    private final ColorUIResource primary1 = new ColorUIResource(147, 118, 30);
    private final ColorUIResource primary2 = new ColorUIResource(219, 177, 50);
    private final ColorUIResource primary3 = new ColorUIResource(237, 195, 67);

    private final ColorUIResource secondary1 = new ColorUIResource(170, 121,  53);
    private final ColorUIResource secondary2 = new ColorUIResource(200, 167,  68);
    private final ColorUIResource secondary3 = new ColorUIResource(243, 208, 100);

    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }

    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
    
}