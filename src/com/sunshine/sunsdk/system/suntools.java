/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 工具函数
 *	[ 文件名      ]  : suntools.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 测试数据是否合法及流水号自动分配
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.2
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/04/07      1.0             顾俊            建立isDate()函数
 *	2006/04/08      1.1             顾俊            添加函数 #A, #B, #C, #D
 *	2006/04/08      1.2             顾俊            添加函数 #E, #F, #G
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *	
 *	[## private suntools () {} ] :  
 *		功能: 防止实例化suntools对象
 *
 *  [## public static boolean isDate (String date) {} ]:
 *		功能: 判断日期字符串是否合法函数
 *
 *	[## public static boolean isNum (String in) {} ]: 	#A
 *		功能: 测试字符串是否由数字(0-9)组成
 *
 *	[## public static boolean isNum (String in, int length, double min, double max) {} ]: #B
 *		功能: 测试字符串表示的数值及范围，且字符串只能是数字组成
 *
 *	[## public static String getNumber (int type) {} ]:	#C
 *		功能: 自动分配单据编号, 自动递增
 *	
 *	[## public static void savNumber (String num, int type) {} ]: #D
 *		功能: 将已用编号保存到INI文件
 *
 *	[## public static double getConsumeFactor(String sDate, String eDate) {} ]: #E
 *		功能: 计算酒店计费天数  按INI文件中设置的
 *
 *	[## public static double getClockFactor(String sDate, String eDate) {} ]: #F
 *		功能: 计算酒店钟点房计费系数  按INI文件中设置的
 *
 *	[## public static String getConsumeHour(String sDate, String eDate) {} ]: #G
 *		功能: 计算两个时间之间有几小时几分
 *
 *
 *  [ 遗留问题    ]  :
 *
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.system;

import java.util.*;
import java.text.*;
import java.sql.*;
import com.sunshine.sunsdk.sql.*;


public class suntools {
	
	public static final int Number_RZ = 0;			//表示销售单号
	public static final int Number_YD	= 1;		//表示进货单号
	public static final int Number_JS	= 1;		//表示进货单号
	
	/**=======================================================================**
	 *		[## private suntools () {} ]:		构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：防止实例化suntools对象
	 **=======================================================================**
	 */
	private suntools () {
	}
	
	/**=======================================================================**
	 *		[## public static boolean isDate (String date) {} ]:		
	 *			参数   ：String对象表示日期的字符串
	 *			返回值 ：boolean 合法返回true
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：判断日期字符串是否合法函数
	 **=======================================================================**
	 */
	public static boolean isDate (String date) {
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		String isdate = date;
		if(date.length() == 10)				//如果只有日期，函数自动加上00:00:00
			isdate = date + " 00:00:00";
		try {
			sdf.parse (isdate);
			return true;
	    }
	    catch (Exception ex) {
	    	System.out.println ("feetools.isDate(): The DATE format is error .");
	    	return false;
	    }
	}
	
	/**=======================================================================**
	 *		[## public static boolean isNum (String in) {} ]:		
	 *			参数   ：String对象表示被测字符串
	 *			返回值 ：boolean 合法返回true
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：测试字符串是否由数字(0-9)组成
	 **=======================================================================**
	 */
	public static boolean isNum (String in) {
		return isNum (in, 0, 0, 0);
	}
	
	/**=======================================================================**
	 *		[## public static boolean isNum (String in, int length, double min, double max) {} ]:		
	 *			参数   ：String对象表示被测字符串
	 *					 length变量表示字符串最大长度，取值0，表示没有长度限定
	 *					 min,max变量限定了String对象表示的数值范围，当(length > 0)时
	 *			返回值 ：boolean 合法返回true
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：测试字符串表示的数值及范围，且字符串只能是数字组成
	 **=======================================================================**
	 */
	public static boolean isNum (String in, int length, double min, double max) {
		String num = in;
		int point  = 0;						//'.'的个数
		int len = num.length (); 
		if(length > 0) {
			if(len > length || len == 0) {	//判断字符串长度,不合法空返回false
				System.out.println ("suntools.isNum(): Length error.");
				return false;
			}//Endif
		}//End if(length > 0)
		else
			if(len == 0) {					//判断字符串是否为空,空返回false
				System.out.println ("suntools.isNum(): String is NULL");
				return false;
			}//End if(len == 0)
		for (int i = len - 1; i >=0; i--) {		//判断字符串只能是数字
			char ac = num.charAt (i);
			if(ac == '.' && point == 0 &&  i!= 0) {	//如果是'.'字符，且是第一次出现，且不是只有一个点
				if(i > len - 4) {			//判断小数位只能是两位
					point++;
					continue;
				}//Endif
			}//Endif
			if(ac < '0' || ac > '9' ) {
				System.out.println ("suntools.isNum(): Character isn't ( '0' - '9' )");
				return false;
			}//Endif
	    }//Endfor
	    if(length !=0) {
	    	double s = Double.parseDouble (num);		//现在len为字符串表示的数值
	    	if(s < min  || s >max) {					//限制范围min-max之间
	    		System.out.println ("suntools.isNum(): Amount limit error. ");
	    		return false;
	    	}//Endif
	    }//End if(length != 0)
	    return true;
	}
	
	/**=======================================================================**
	 *		[## public static String getNumber (int type) {} ]:		
	 *			参数   ：int变量表示要获得什么类型的单号(见类头常量)
	 *			返回值 ：String对象: 单号;
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：自动分配单据编号, 自动递增
	 **=======================================================================**
	 */
	public static String getNumber (int type) {
		
		GregorianCalendar gc = new GregorianCalendar();
		String tp, number, month, day;		//单号标头, 单号记数位, 为月份, 为天
		int numLen = 0;						//单号的默认位数
		
		if(type == Number_RZ) {				//要获取销售单号
			tp = sunini.getIniKey ("LodgName");
			number = sunini.getIniKey ("LodgNumber");
		}else if(type == Number_YD){					//要获取进货单号
			tp = sunini.getIniKey ("EngaName");
			number = sunini.getIniKey ("EngaNumber");
		}else {
			tp = sunini.getIniKey ("ChouName");
			number = sunini.getIniKey ("ChouNumber");
		}
		
		numLen = number.length ();						//得到单号的默认位数
		number = Integer.parseInt (number) + 1 + "";	//将单号增1,再转成字符串

		//判断记数号是否超位(超出要求长度)
		if(number.equals ((int)Math.pow (10, numLen - 1) + "") && number.length() > 1)
			number = number.substring(1);				//单号记数位清零

		//for循环,用'0'为number补位 (i = 当前号码位数, i < numLen)
		for (int i = number.length (); i < numLen; i++) {
			number = "0" + number;
	    }//Endfor
		
		//为月份补'0'
		month = gc.get (GregorianCalendar.MONTH) + 1 + "";
		if( month.length() == 1)
			month = "0" + month;
		
		//为天补'0'
		day = gc.get (GregorianCalendar.DAY_OF_MONTH) + "";
		if( day.length () == 1)
			day = "0" + day;
			
		//连接单号标头,日期,记数位;组成单据号码
	    tp = tp + gc.get (GregorianCalendar.YEAR) + month + day + number;
		
		return tp;			//返回单号
	}
	
	/**=======================================================================**
	 *		[## public static void savNumber (String num, int type) {} ]:		
	 *			参数   ：String对象表示要保存的单号
	 *					 int变量表示要保存什么类型的单号(见类头常量)
	 *			返回值 ：无
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：将已用编号保存到INI文件
	 **=======================================================================**
	 */
	public static void savNumber (String num, int type) {
		//INI文件中的键名
		String ini[] = { "[SOFTINFO]", "UserName", "CompName", "[CONFIG]", "Soft_First",
					 "Default_Link" , "Default_Page", "Sys_style", "[NUMBER]",
					 "LodgName", "LodgNumber", "EngaName", "EngaNumber", "ChouName", 
					 "ChouNumber", "[HABITUS]", "Ck_Habitus", "Ck_Minute", "[PARTTIME]", 
					 "In_Room", "Out_Room1", "Out_Room2", "InsuDay", "ClockRoom1", 
					 "ClockRoom2", "InsuHour1", "InsuHour2", "[JDBC]", "DBFname", 
					 "UserID", "Password", "IP", "Access", "[ODBC]", "LinkName" };
		String bt;
		if(type == Number_RZ) {
			bt = sunini.getIniKey ("LodgName");
			sunini.setIniKey ("LodgNumber", num.substring (bt.length () + 8));
		}
		else if(type == Number_YD){
			bt = sunini.getIniKey ("EngaName");
			sunini.setIniKey ("EngaNumber", num.substring (bt.length () + 8));
		} else{
			bt = sunini.getIniKey ("ChouName");
			sunini.setIniKey ("ChouNumber", num.substring (bt.length () + 8));
		}
		//保存到INI文件
		sunini.saveIni (ini);
	}
	
	/**=======================================================================**
	 *		[## public static double getConsumeFactor(String sDate, String eDate) {} ]:		
	 *			参数   ：String sDate对象表示开始时间
	 *					 String eDate变量表示结束时间
	 *			返回值 ：double
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：计算酒店计费天数  按INI文件中设置的
	 **=======================================================================**
	 */
	 //获得消费系数
	public static double getConsumeFactor(String sDate, String eDate) {
		
		//获得开始日期时间的--年--月--日--时--分--秒
		String syh [] = sDate.trim ().split(" ");
		String symd[] = syh[0].trim().split("-");
		String shms[] = syh[1].trim().split(":");
		int sy = Integer.parseInt(symd[0]);
		int sM = Integer.parseInt(symd[1]);
		int sd = Integer.parseInt(symd[2]);
		int sh = Integer.parseInt(shms[0]);
		int sm = Integer.parseInt(shms[1]);
		int ss = Integer.parseInt(shms[2]);
		
		//获得结束日期时间的--年--月--日--时--分--秒
		String eyh [] = eDate.trim ().split(" ");
		String eymd[] = eyh[0].trim().split("-");
		String ehms[] = eyh[1].trim().split(":");
		int ey = Integer.parseInt(eymd[0]);
		int eM = Integer.parseInt(eymd[1]);
		int ed = Integer.parseInt(eymd[2]);
		int eh = Integer.parseInt(ehms[0]);
		int em = Integer.parseInt(ehms[1]);
		int es = Integer.parseInt(ehms[2]);
		
		//获得sDate的long值
		long sdt = new Timestamp(sy, sM, sd, sh, sm, ss, 0).getTime();
		//获得eDate的long值
		long edt = new Timestamp(ey, eM, ed, eh, em, es, 0).getTime();
		
		double db = 0;
		
		if(sdt > edt) {			//不合法	开始日期一定要小于结束日期
			db = -1;
			return db;
		}//Endif
		
		if(sdt == edt) {		//无时间差
			db = 0;
			return db;
		}//Endif
		
		
		int insuDay = (int)(edt - sdt)/3600000;
		if(insuDay < 24) {				//入住不到一天
			if(Integer.parseInt(sunini.getIniKey("InsuDay")) == 1)
				db = 1;					//按全天计费
			else {
				if(insuDay > 1 && insuDay < 12)
					db = 0.5;			//如果不按全天计费，则大于1小时按半天计费
				else
					db = 1;				//如果不按全天计费，则大于12小时按全天计费
			}//Endif
			return db;	
		}//Endif
		
		//几点之后按新的一天计费
		int inRoom = Integer.parseInt(sunini.getIniKey("In_Room"));
		if(sh < inRoom) {
			db = 0.5;
		}//Endif
		sh = inRoom;		//多的时间已经加了系数，去掉多余的
		//几点之后按半天计费
		int outRoom1 = Integer.parseInt(sunini.getIniKey("Out_Room1"));
		int outRoom2 = Integer.parseInt(sunini.getIniKey("Out_Room2"));
		if(eh > outRoom1 && eh < outRoom2) {
			db = db + 0.5;
			eh = outRoom1;	//多的时间已经加了系数，去掉多余的
		}else if(eh >= outRoom2) {
			db = db + 1;
			eh = outRoom2;
		}//Endif
		
		//计算入住时间
		sdt = new Timestamp(sy, sM, sd, sh, sm, ss, 0).getTime();
		edt = new Timestamp(ey, eM, ed, eh, em, es, 0).getTime();
		
		db = db + (edt - sdt)/86400000;
		return db;
	}
	
	/**=======================================================================**
	 *		[## public static double getClockFactor(String sDate, String eDate) {} ]:		
	 *			参数   ：String sDate对象表示开始时间
	 *					 String eDate变量表示结束时间
	 *			返回值 ：double
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：计算酒店钟点房计费系数  按INI文件中设置的
	 **=======================================================================**
	 */
	public static double getClockFactor(String sDate, String eDate) {
		
		//获得开始日期时间的--年--月--日--时--分--秒
		String syh [] = sDate.trim ().split(" ");
		String symd[] = syh[0].trim().split("-");
		String shms[] = syh[1].trim().split(":");
		int sy = Integer.parseInt(symd[0]);
		int sM = Integer.parseInt(symd[1]);
		int sd = Integer.parseInt(symd[2]);
		int sh = Integer.parseInt(shms[0]);
		int sm = Integer.parseInt(shms[1]);
		int ss = Integer.parseInt(shms[2]);
		
		//获得结束日期时间的--年--月--日--时--分--秒
		String eyh [] = eDate.trim ().split(" ");
		String eymd[] = eyh[0].trim().split("-");
		String ehms[] = eyh[1].trim().split(":");
		int ey = Integer.parseInt(eymd[0]);
		int eM = Integer.parseInt(eymd[1]);
		int ed = Integer.parseInt(eymd[2]);
		int eh = Integer.parseInt(ehms[0]);
		int em = Integer.parseInt(ehms[1]);
		int es = Integer.parseInt(ehms[2]);
		
		//获得sDate的long值
		long sdt = new Timestamp(sy, sM, sd, sh, sm, ss, 0).getTime();
		//获得eDate的long值
		long edt = new Timestamp(ey, eM, ed, eh, em, es, 0).getTime();
		//获得时间差
		
		double db = 0;
		
		if(sdt > edt) {			//不合法	开始日期一定要小于结束日期
			db = -1;
			return db;
		}//Endif
		
		//获得两个时间之间有多少秒
		edt = (edt - sdt) / 1000;
		//--------------------------------------------------------------------//
		if(edt <= 60 * Integer.parseInt(sunini.getIniKey("ClockRoom1"))) {
			db = 0;				//开房后几分钟开始计费，读INI文件设置
			return db;
		}//Endif
		//--------------------------------------------------------------------//
		if(edt / 60 < 60 * Integer.parseInt(sunini.getIniKey("ClockRoom2"))) {
			db = 1;				//不足几小时按一个单位计费
			return db;
		}//Endif
		//--------------------------------------------------------------------//
		
		//除以上可能的正常计费公式如下
		db = edt / 3600;				//获得过去的小时数
		
		edt = edt % 3600 / 60;			//获得多余的分钟数
		
		if(edt > Integer.parseInt(sunini.getIniKey("InsuHour2")) && edt <= Integer.parseInt(sunini.getIniKey("InsuHour1"))) {
			db = db + 0.5;				//超过多少分，但小于多少分部分，收半价
		}else if(edt > Integer.parseInt(sunini.getIniKey("InsuHour2"))) {
			db = db + 1;				//超过多少分的，收全价
		}//Endif
		//--------------------------------------------------------------------//
		
		return db;
	}
	
	
	/**=======================================================================**
	 *		[## public static String getConsumeHour(String sDate, String eDate) {} ]:		
	 *			参数   ：String sDate对象表示开始时间
	 *					 String eDate变量表示结束时间
	 *			返回值 ：String
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：计算两个时间之间有几小时几分
	 **=======================================================================**
	 */
	public static String getConsumeHour(String sDate, String eDate) {
		
		//获得开始日期时间的--年--月--日--时--分--秒
		String syh [] = sDate.trim ().split(" ");
		String symd[] = syh[0].trim().split("-");
		String shms[] = syh[1].trim().split(":");
		int sy = Integer.parseInt(symd[0]);
		int sM = Integer.parseInt(symd[1]);
		int sd = Integer.parseInt(symd[2]);
		int sh = Integer.parseInt(shms[0]);
		int sm = Integer.parseInt(shms[1]);
		int ss = Integer.parseInt(shms[2]);
		
		//获得结束日期时间的--年--月--日--时--分--秒
		String eyh [] = eDate.trim ().split(" ");
		String eymd[] = eyh[0].trim().split("-");
		String ehms[] = eyh[1].trim().split(":");
		int ey = Integer.parseInt(eymd[0]);
		int eM = Integer.parseInt(eymd[1]);
		int ed = Integer.parseInt(eymd[2]);
		int eh = Integer.parseInt(ehms[0]);
		int em = Integer.parseInt(ehms[1]);
		int es = Integer.parseInt(ehms[2]);
		
		//获得sDate的long值
		long sdt = new Timestamp(sy, sM, sd, sh, sm, ss, 0).getTime();
		//获得eDate的long值
		long edt = new Timestamp(ey, eM, ed, eh, em, es, 0).getTime();
		//获得时间差
		sdt = edt - sdt;
		
		//生成过去多少时间的字符串
		String t = sdt / 3600000 + "小时" + sdt % 3600000 / 60000 + "分";
		
		return t;
	}
}