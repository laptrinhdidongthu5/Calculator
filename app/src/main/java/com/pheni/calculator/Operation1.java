package com.pheni.calculator;

import java.lang.Math;

public class Operation1 {
    protected double toanHang;

    public Operation1() {
    }

    public Operation1(double toanHang) {
        this.toanHang = toanHang;
    }

    public double Tinh1Phanx() {
        return 1 / toanHang;
    }

    public double TinhCanBacHai() {
        return Math.sqrt(toanHang);
    }

    public double TinhCos() {
        return Math.cos(toanHang);
    }

    public double TinhMuHai() {
        return Math.pow(toanHang, 2);
    }

    public double TinhSin() {
        return Math.sin(toanHang);
    }

    public double TinhTan() {
        return Math.tan(toanHang);
    }

    public double TinhTriTuyetDoi() {
        return Math.abs(toanHang);
    }
}
