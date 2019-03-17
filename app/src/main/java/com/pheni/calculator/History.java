package com.pheni.calculator;

public class History {
    protected String sExpression;
    protected String sResult;

    public History() {
    }

    public History(String sExpression, String sResult) {
        this.sExpression = sExpression;
        this.sResult = sResult;
    }

    public String getsExpression() {
        return sExpression;
    }

    public void setsExpression(String sExpression) {
        this.sExpression = sExpression;
    }

    public String getsResult() {
        return sResult;
    }

    public void setsResult(String sResult) {
        this.sResult = sResult;
    }
}
