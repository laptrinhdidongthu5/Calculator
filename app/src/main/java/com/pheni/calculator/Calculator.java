package com.pheni.calculator;

public class Calculator {
    public static double dNumFirst = 0;
    public static double dNumSecond = 0;
    public static String sResult = "0";
    public static String sChuoiTinh = "0";

    public Calculator() {
    }

    public static double getdNumFirst() {
        return dNumFirst;
    }

    public static void setdNumFirst(double dNumFirst) {
        Calculator.dNumFirst = dNumFirst;
    }

    public static double getdNumSecond() {
        return dNumSecond;
    }

    public static void setdNumSecond(double dNumSecond) {
        Calculator.dNumSecond = dNumSecond;
    }

    public static String getsResult() {
        return sResult;
    }

    public static void setdResult(String dResult) {
        Calculator.sResult = dResult;
    }

    public static String getsChuoiTinh() {
        return sChuoiTinh;
    }

    public static void setsChuoiTinh(String sChuoiTinh) {
        Calculator.sChuoiTinh = sChuoiTinh;
    }
}
