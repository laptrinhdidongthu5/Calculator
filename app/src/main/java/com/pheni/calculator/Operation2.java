package com.pheni.calculator;

import java.lang.Math;

public class Operation2 {
	double toanHang1;
	double toanHang2;

	public Operation2(double toanHang1,double toanHang2)
	{
		this.toanHang1=toanHang1;
		this.toanHang2=toanHang2;
	}

	public double TimMin()
	{
		return Math.min(toanHang1, toanHang2);
	}

	public double TimMax()
	{
		return Math.max(toanHang1, toanHang2);
	}

	public double TinhMode()
	{
		return toanHang1%toanHang2;
	}

	public double TinhCan()
	{
		if(toanHang2 == 0){
			toanHang2 = 1;
		}
		return Math.pow(toanHang1, 1/toanHang2);
	}

	public double TinhMu()
	{
		return Math.pow(toanHang1, toanHang2);
	}
}
