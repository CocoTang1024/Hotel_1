/**
 *##############################################################################
 *
 *	[ 项目名      ]  : 
 *  [ 公司名      ]  : SunshineSOFT
 *	[ 模块名      ]  : 数据库连接
 *	[ 文件名      ]  : sunsql.java
 *	[ 相关文件    ]  : 
 *	[ 文件实现功能]  : 连接及操作数据库
 *	[ 作者        ]  : 顾俊
 *	[ 版本        ]  : 1.3
 *	----------------------------------------------------------------------------
 *	[ 备注        ]  : 
 *	----------------------------------------------------------------------------
 *	[ 修改记录    ]  : 
 *
 *	[ 日  期 ]     [版本]         [修改人]         [修改内容] 
 *	2006/03/31      1.0             顾俊            重新组织
 *	2006/04/05      1.1             顾俊            添加ODBC连接方式
 *	2006/04/08      1.2             顾俊            添加函数 #A, #B, #C, #D
 *	2006/04/25      1.2             顾俊            添加函数 #E
 *	##--------------------------------------------------------------------------
 *  			 版权所有(c) 2006-2007,  SunshineSOFT Corporation
 *	--------------------------------------------------------------------------##
 *	
 *	[ 函数说明    ]  :
 *	
 *	[## private sunsql() {} ] :  
 *		功能: 防止实例化sunsql对象
 *
 *  [## public static int executeUpdate(String sql) {} ] :
 *		功能: 执行对数据库更改的sql命令，并返回更改所影响的行数
 *
 *  [## public static ResultSet executeQuery(String sql) {} ] :
 *		功能: 执行对数据库的select查询功能，并返回查询所得到的结果
 *
 *	[## public static int recCount(ResultSet rs)) {} ] :
 *		功能: 获得指定结果集的记录数量
 *
 *	[## public static long getPrimaryKey() {} ] : #A
 *		功能: 通过服务器当前的时间获得一个主键
 *
 *	[## public static void initJComboBox (JComboBox cb, String sqlCode) {} ] : #B
 *		功能: 按SQL语句从数据库选出Items加入JComboBox
 *
 *	[## public static void initJList (JList jt, String sqlCode) {} ] : #E
 *		功能: 按SQL语句从数据库选出数据加入JList
 *
 *	[## public static void initDTM (DefaultTableModel fdtm, String sqlCode) {} ] : #C
 *		功能: 按SQL语句从数据库中获得数据(结果集)，添加到fdtm中(也可以说JTable中)
 *
 *	[## public static int runTransaction (String updateCode[]) {} ] : #D
 *		功能: 利用事务的模式以updateCode中的sql语句对数据库进行更新
 *
 *
 *  [ 遗留问题    ]  :
 *
 *	[ 数据库连接关闭问题 ] : 现在: 只有应用程序完全退出时数据库连接才关闭
 *                           目的: 每次数据库操作完成后都要关闭
 *	
 *
 *##############################################################################
 */
package com.sunshine.sunsdk.sql;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import com.sunshine.sunsdk.system.*;


public class sunsql {
	
	private static Statement ste = null;
	private static Connection conn = null;
	
	static {
		try {
			if(sunini.getIniKey ("Default_Link").equals ("1")) {		//JDBC连接方式
				String user = sunini.getIniKey ("UserID");
				String pwd  = sunini.getIniKey ("Password");
				String ip   = sunini.getIniKey ("IP");
				String acc  = sunini.getIniKey ("Access");
				String dbf  = sunini.getIniKey ("DBFname");
				String url  = "jdbc:mysql://" + ip + ":" + acc + "/" + dbf +"?useUnicode=true&characterEncoding=utf-8";
				//注册驱动
				//DriverManager.registerDriver (new com.microsoft.jdbc.sqlserver.SQLServerDriver());
				//获得一个连接
				conn = DriverManager.getConnection (url, user, pwd);
			}
			else {
				//注册驱动										//JDBCODBC连接方式
				//DriverManager.registerDriver (new sun.jdbc.odbc.JdbcOdbcDriver());
				//获得一个连接
				conn = DriverManager.getConnection ("jdbc:odbc:" + sunini.getIniKey("LinkName"));
			}
			//设置自动提交为false
			conn.setAutoCommit (false);
			//建立高级载体
			ste = conn.createStatement (ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	    }
	    catch (Exception ex) {
	    	JOptionPane.showMessageDialog (null, "数据库连接失败...", "错误", JOptionPane.ERROR_MESSAGE);
	    	System.exit(0);
	    	//ex.printStackTrace();
	    }//End try
	}
	
	/**=======================================================================**
	 *		[## private sunsql() {} ]:		构造函数
	 *			参数   ：无
	 *			返回值 ：无
	 *			修饰符 ：private
	 *			功能   ：防止实例化sunsql对象
	 **=======================================================================**
	 */
	private sunsql(){
	}
	
	/**=======================================================================**
	 *		[## public static int executeUpdate(String sql) {} ] :	
	 *			参数   ：String 对象, 表示需要执行的sql语句
	 *			返回值 ：int, 表示此sql语句对数据库影响了几行
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：执行对数据库更改的sql命令，并返回更改所影响的行数
	 **=======================================================================**
	 */
	public static int executeUpdate(String sql) {
//		System.out.println ("Update SQL : " + sql);
		int i = 0 ;
		try {
			i = ste.executeUpdate(sql) ;
			conn.commit();
		}catch(Exception e) {
			e.printStackTrace() ;
		}//End try
		return i ;
	}
	
	/**=======================================================================**
	 *		[## public static int runTransaction (String updateCode[]) {} ] :	
	 *			参数   ：String[]是字符串数组, 表示需要执行的所有sql语句
	 *			返回值 ：int, 表示sql语句执行的情况, (i==数组长度)为更新成功
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：利用事务的模式以updateCode中的sql语句对数据库进行更新
	 **=======================================================================**
	 */
	public static int runTransaction (String updateCode[]) {
		int ok = 0, i = 0;
		int row = updateCode.length;		//更新语句的数量
		try {
			for (i = 0; i < row; i++) {
				ok = ste.executeUpdate (updateCode[i]);		//执行SQL语句
				if(ok == 0) {				//如果不成功，则跳出循环
					System.out.println ("sunsql.runTransaction(): updateCode[" + i + "] 失败" + ok);
					break;
				}
				System.out.println ("sunsql.runTransaction(): updateCode[" + i + "] 成功 " + ok);
			}
			//根据变量 ok 判断上面循环是否正常运行完毕
			if(ok == 0) {
				conn.rollback ();		//(ok == 0)表示更新过程中出错，回滚数据
				System.out.println ("sunsql.runTransaction(): Update data false, rollback");
			}
			else {
				conn.commit ();			//(ok != 0)基本上是所有SQL语句运行成功, 则提交给数据库
				System.out.println ("sunsql.runTransaction(): Update finish");
			}
		}
	    catch (Exception ex) {
	    	System.out.println ("sunsql.runTransaction(): Update false ...");
	    	ex.printStackTrace();
	    }
		return i;
	}
	
	/**=======================================================================**
	 *		[## public static ResultSet executeQuery(String sql) {} ] :
	 *			参数   ：String 对象, 表示需要执行的sql语句
	 *			返回值 ：ResultSet对象, 表示此查询语句返回的结果集
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：执行对数据库的select查询功能，并返回查询所得到的结果
	 **=======================================================================**
	 */
	public static ResultSet executeQuery(String sql) {
//		System.out.println ("Query SQL : " + sql);
		ResultSet rs = null ;
		try {
			rs = ste.executeQuery(sql) ;
		}catch(Exception e) {
			e.printStackTrace() ;
		}//End try
		return rs ;
	}
	
	/**=======================================================================**
	 *		[## public static int recCount(ResultSet rs)) {} ] :
	 *			参数   ：ResultSet 对象, 表示目标结果集
	 *			返回值 ：int, 表示结果集中的记录个数
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：获得指定结果集的记录数量
	 **=======================================================================**
	 */
	public static int recCount(ResultSet rrs) {
		int i = 0;
		try {
			if(rrs.getRow() != 0)
				rrs.beforeFirst();
			//while用于计算rs的记录条数
			while(rrs.next())
				i++;
			rrs.beforeFirst();	
	    }catch(Exception ex) {
	    	ex.printStackTrace();
	    }//End try
		return i;
	}
	
	/**=======================================================================**
	 *		[## public static long getPrimaryKey() {} ] :
	 *			参数   ：无
	 *			返回值 ：long, 表示从服务器获得的主键
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：通过服务器当前的时间获得一个主键
	 **=======================================================================**
	 */
	public static long getPrimaryKey() {
		long pk = 0;
		
		try {
			//获得服务器时间
			ResultSet rs = executeQuery("select now()");
			rs.next();
			pk = rs.getTimestamp(1).getTime();
	    }
	    catch (Exception ex) {
	    	System.out.println ("sunsql.getPrimaryKey (): false");
	    }
	    return pk;
	}
	
	/**=======================================================================**
	 *		[## public static void initJComboBox (JComboBox cb, String sqlCode) {} ] :
	 *			参数   ：JComboBox表示要加数据的下拉框, String对象表示一条SQL语句
	 *			返回值 ：JComboBox, 表示返回一个加好Item的JComboBox对象
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：按SQL语句从数据库选出Items加入JComboBox
	 **=======================================================================**
	 */
	public static void initJComboBox (JComboBox cb, String sqlCode) {
		cb.removeAllItems();
		try {
			ResultSet rs = executeQuery (sqlCode);
			int row = recCount (rs);
			rs.beforeFirst ();
			//从结果集中取出Item加入JComboBox中
			for (int i = 0; i < row; i++) {
				rs.next();
				cb.addItem (rs.getString (1));
		    }
	    }
	    catch (Exception ex) {
	    	System.out.println ("sunsql.initJComboBox (): false");
	    }
	}
	
	/**=======================================================================**
	 *		[## public static void initJList (JList jt, String sqlCode) {} ] :
	 *			参数   ：JList表示要加数据的列表框, String对象表示一条SQL语句
	 *			返回值 ：无
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：按SQL语句从数据库选出数据加入JList
	 **=======================================================================**
	 */
	public static void initJList (JList jt, String sqlCode) {
		try {
			ResultSet rs = executeQuery (sqlCode);
			int row = recCount (rs);
			String list[] = new String[row];
			//从结果集中取出数据存入数组中
			for (int i = 0; i < row; i++) {
				rs.next();
				list[i] = rs.getString(1);
		    }//Endfor
		    jt.setListData(list);	//初始化List
	    }
	    catch (Exception ex) {
	    	System.out.println ("sunsql.initJList(): false");
	    }//Endtry
	}
	
	/**=======================================================================**
	 *		[## public static void initDTM (DefaultTableModel fdtm, String sqlCode) {} ] :
	 *			参数   ：DefaultTableModel对象表示要添充数据的表模式
	 *					 String对象表示一条SQL语句
	 *			返回值 ：无
	 *			修饰符 ：public static 可以不实例化对象而直接调用方法
	 *			功能   ：按SQL语句从数据库中获得数据，添加到fdtm中(也可以说JTable中)
	 **=======================================================================**
	 */
	public static void initDTM (DefaultTableModel fdtm, String sqlCode) {
		try {
			ResultSet rs = executeQuery( sqlCode );	//获得结果集
			int row = recCount( rs );				//获得结果集中有几行数据
			ResultSetMetaData rsm =rs.getMetaData();	//获得列集
			int col = rsm.getColumnCount();		//获得列的个数
			String colName[] = new String[col];
			//取结果集中的表头名称, 放在colName数组中
			for (int i = 0; i < col; i++) {
				colName[i] = rsm.getColumnName( i + 1 );
			}//End for
			rs.beforeFirst();
			String data[][] = new String[row][col];
			//取结果集中的数据, 放在data数组中
			for (int i = 0; i < row; i++) {
				rs.next();
				for (int j = 0; j < col; j++) {
					data[i][j] = rs.getString (j + 1);
					//System.out.println (data[i][j]);
			    }
			}//End for
			fdtm.setDataVector (data, colName);
	    }
	    catch (Exception ex) {
	    	System.out.println ("sunsql.initDTM (): false");
	    }//End try
	}

}