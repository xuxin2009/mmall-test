package com.mmall.utils;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/12/20.
 */
public class BigdecimalUtil {
    private BigdecimalUtil(){

    }

    public static BigDecimal add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v1));
        return b1.add(b2);
    }
    public static BigDecimal sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v1));
        return b1.subtract(b2);
    }
    public static BigDecimal mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v1));
        return b1.multiply(b2);
    }
    public static BigDecimal div(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v1));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);//保留2位小数，四舍五入
    }

}
