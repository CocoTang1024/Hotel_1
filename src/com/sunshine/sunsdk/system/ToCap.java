/*
 * @(#)ToCap.java	1.24 03/12/19
 *
 * Copyright 2006 CazoSoft, Inc. All rights reserved.
 */

package com.sunshine.sunsdk.system;

/**
 * <code> ToCap </code>
 * <p>
 * 本类用于转化中文大写人民币
 * 
 * @author 鲜强
 * @version 22.14, 04/05/06
 * @since CAZOSOFT 1.0
 */
public class ToCap {

    /**
     * 用户设置大写钱数(100,000,000 (一亿)以内).
     * 
     * @param money :
     *            String 接收用户的正实数.
     * @return : String 包括 圆整 或 角分的中文大写人民币,如果不合法,将返回空(null)
     */
    public String setMoney(String money) {

        String re = null;
        double num;
        
        //测试数据是否合法
        try {
            num = Double.parseDouble(money.trim());
            if (num < 100000000 && num >= 0) {
                //合法, 使用字符串开始转换
                re = this.mySetMoney(money);
            }//end if
        } catch (Exception ex) {
        }//end try
        //返回值
        return re;
    }
    
    /**
     * 大写转换函数. 功能: 转换数字为中文大写
     * 
     * @param num :
     *            int 接受整数
     * @return : String 大写数字 零 到 玖.
     */
    private String setDaXie(int num) {

        String re = null;
        switch (num) {
        case 0:
            re = "零";
            break;
        case 1:
            re = "壹";
            break;
        case 2:
            re = "贰";
            break;
        case 3:
            re = "叁";
            break;
        case 4:
            re = "肆";
            break;
        case 5:
            re = "伍";
            break;
        case 6:
            re = "陆";
            break;
        case 7:
            re = "柒";
            break;
        case 8:
            re = "捌";
            break;
        case 9:
            re = "玖";
            break;
        default:
            break;
        }
        return re;
    }

    /**
     * 处理大写人民币
     * 
     * @param num :
     *            String 接收处理过的字符串(实为数字).
     * @return : 返回处理过的大写人民币.
     */
    private String mySetMoney(String num) throws Exception {

        //要返回的字符串
        String re = "";

        //转换过程
        String setNum = null;
        int index = num.indexOf(".");

        //处理整数
        if (index == -1) {
            setNum = num;
        } else {
            setNum = num.substring(0, index);
        }
        int weiShu = setNum.length();
        if (weiShu > 0) {
            int leng = setNum.length();
            int numYuan = Integer.parseInt("" + setNum.charAt(weiShu - 1));
            String zhuanYuan = setDaXie(numYuan);
            re = zhuanYuan;
            //个位
            if (weiShu > 1) {
                int numShi = Integer.parseInt("" + setNum.charAt(weiShu - 2));
                String zhuanShi = setDaXie(numShi);
                //十位
                re = zhuanShi + "拾" + re;
                if (weiShu > 2) {
                    int numBai = Integer.parseInt(""
                            + setNum.charAt(weiShu - 3));
                    String zhuanBai = setDaXie(numBai);
                    //百位
                    re = zhuanBai + "佰" + re;
                    if (weiShu > 3) {
                        int numQian = Integer.parseInt(""
                                + setNum.charAt(weiShu - 4));
                        String zhuanQian = setDaXie(numQian);
                        //千位
                        re = zhuanQian + "仟" + re;
                        if (weiShu > 4) {
                            int numWan = Integer.parseInt(""
                                    + setNum.charAt(weiShu - 5));
                            String zhuanWan = setDaXie(numWan);
                            //万位
                            re = zhuanWan + "万" + re;
                            if (weiShu > 5) {
                                int numShiWan = Integer.parseInt(""
                                        + setNum.charAt(weiShu - 6));
                                String zhuanShiWan = setDaXie(numShiWan);
                                //十万位
                                re = zhuanShiWan + "拾" + re;
                                if (weiShu > 6) {
                                    int numBaiWan = Integer.parseInt(""
                                            + setNum.charAt(weiShu - 7));
                                    String zhuanBaiWan = setDaXie(numBaiWan);
                                    //百万位
                                    re = zhuanBaiWan + "佰" + re;
                                    if (weiShu > 7) {
                                        int numQianWan = Integer.parseInt(""
                                                + setNum.charAt(weiShu - 8));
                                        String zhuanQianWan = setDaXie(numQianWan);
                                        //千万位
                                        re = zhuanQianWan + "仟" + re;
                                    }//end qianwan
                                }//end baiwan
                            }//end shiwan
                        }//end wan
                    }//end qian
                }//end bai
            }//end shi
        }//end yuan
      
        //处理小数
        if (index != -1) {
            String xiaoShu = num.substring(index + 1, num.length());
            int leng = xiaoShu.length();
            int numJiao = Integer.parseInt("" + xiaoShu.charAt(0));
            String zhuanJiao = setDaXie(numJiao);
            int numFen;
            if (leng == 1) {
                numFen = 0;
            }//end if
            numFen = Integer.parseInt("" + xiaoShu.charAt(1));
            String zhuanFen = setDaXie(numFen);
            //十分位+百分位
            if (numJiao == 0 && numFen != 0 || numJiao != 0 && numFen != 0) {
                re = re + "圆" + zhuanJiao + "角" + zhuanFen + "分";
            } else {
                re = re + "圆整";
            }//end if
        } else {
            re = re + "圆整";
        }//end index

        return re;
    }
}