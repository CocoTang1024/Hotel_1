/*
 * @(#)CsHashtable.java	1.24 03/12/19
 * 
 * public class
 *
 * Copyright 2006 CazoSoft, Inc. All rights reserved.
 */

package com.sunshine.sunsdk.system;

import java.util.*;

/**
 * <code> CsHashtable </code>
 * <p>
 * 该类的作用是封装哈希表
 * <p>
 * <strong>注意: </strong> 本类使用的哈希表方法 put, 可能存在不安全因素
 * <p>
 * 相关类：Hashtable
 * 
 * @author 鲜强
 * @version 2006.3.28 22:18
 * @see Hashtable
 * @since CAZOSOFT 1.0
 */
public class CsHashtable {

    /**
     * 静态变量 功能: 构造一个 hashtable 供其他函数使用
     */
    private static Hashtable hTable = new Hashtable();

    /**
     * 构造函数 功能: 构造函数为静态的不能创建对象
     */
    private CsHashtable() {
    }

    /**
     * 放入对象函数 功能: 把对象放入哈希表,对象为Object类型
     * 
     * @param key :
     *            键值, Object 对象
     * @param value :
     *            对象, Object 对象
     */
    public static void put(Object key, Object value) {
        hTable.put(key, value);
    }

    /**
     * 返回对象函数 功能: 从哈希表中取出对象,返回为Object类型
     * 
     * @param key :
     *            键值
     * @return : 一个 Object 对象
     */
    public static Object get(Object key) {
        return hTable.get(key);
    }

    /**
     * 返回大小函数 功能: 返回哈希表的大小
     * 
     * @return : 一个 int 哈希表长度值
     */
    public static int size() {
        return hTable.size();
    }
    
    /**
     * 移除一个对象
     * 
     * @param key : 接受一个要移除的键值
     */
    public static Object remove(Object key) {
        return hTable.remove(key);
    }
}