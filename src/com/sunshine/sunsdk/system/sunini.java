/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 对于INI文件的相关操作
 *	[ 文件名      ]  : sunini.java
 *	[ 相关文件    ]  : config.ini
 *	[ 文件实现功能]  : 读取和保存INI文件
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.1
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/03/31      1.0             顾俊            创建
 *	2006/04/06      1.1             顾俊            修改读取函数
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *
 *	[## private sunini() {} ]:
 *		功能: 构造函数
 *
 *	[## public static String getIniKey (String k) {} ]:
 *		功能: 获得INI文件中的指定键的键值
 *
 *	[## public static void setIniKey (String k, String v) {} ]:
 *		功能: 设置k键的键值为v对象
 *
 *	[## public static void saveIni (String k[]) {} ]:
 *		功能: 将k字符数组中所有键所对应的键值保存到INI文件中
 *	
 *  [ 遗留问题    ]  : setIniKey ()方法不能处理键值中的转义字符
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.system;

import java.util.*;
import java.io.*;


public class sunini {
	
	private static Properties ini = null;
	
	static {
		try	{
			ini = new Properties ();
			ini.load (new FileInputStream ("config.ini"));
		}catch (Exception ex) {
			System.out.println ("Load CONFIG.INI is false!!");
		}//End try
	}
	
	/**=======================================================================**
	 *		[## private sunini() {} ]:		构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：防止实例化sunini对象
	 **=======================================================================**
	 */
	private sunini() {
	}
	
	/**=======================================================================**
	 *		[## public static String getIniKey (String k) {} ]:	
	 *			参数   ：String对象表示键
	 *			返回值 ：String对象表示k键所对应的键值，如果失败则返回空串
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：获得INI文件中的指定键的键值
	 **=======================================================================**
	 */
	public static String getIniKey (String k) {
		if(!ini.containsKey (k)) {		//是否有 k 这个键
			System.out.println ("The [ " + k + " ] Key is not exist!!");
			return "";
		}//End if(!ini.containsKey (k))
		return ini.get (k).toString ();
	}
	
	/**=======================================================================**
	 *		[## public static void setIniKey (String k, String v) {} ]:	
	 *			参数   ：String k对象表示键,String v对象表示键值
	 *			返回值 ：无
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：设置k键的键值为v对象
	 **=======================================================================**
	 */
	public static void setIniKey (String k, String v) {
		if(!ini.containsKey (k)) {		//是否有 k 这个键
			System.out.println ("The [ " + k + " ] Key is not exist!!");
			return;
		}//End if(!ini.containsKey (k))
		ini.put (k, v);
	}
	
	/**=======================================================================**
	 *		[## public static void saveIni (String k[]) {} ]:	
	 *			参数   ：String k[]字符串数组表示要保存的所有键
	 *			返回值 ：无
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：将k字符数组中所有键所对应的键值保存到INI文件中
	 **=======================================================================**
	 */
	public static void saveIni (String k[]) {
		try	{
			FileWriter fw = new FileWriter ("config.ini");
			BufferedWriter bw = new BufferedWriter ( fw );
			//循环变量i是k字符串数组的下标
			for (int i = 0; i < k.length; i++) {
				bw.write (k[i] + "=" + getIniKey (k[i]));
				bw.newLine ();
			}//End for
			bw.close ();
			fw.close ();
		}catch (Exception ex) {
			System.out.println ("Save CONFIG.INI is false.");
		}//End try
	}
	
}