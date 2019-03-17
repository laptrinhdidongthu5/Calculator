package com.pheni.calculator;

import java.util.Arrays;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class OtherFuntion {
    public static int KiemTraDoUuTien(char c)
    {
        if(c=='('||c==')')
            return 0;
        if(c=='+'||c=='-')
            return 1;
        return 2;
    }

    public static boolean KiemTraToanTu(char c)
    {
        char operator[]= {'+','-','*','/'};
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, c) > -1)
            return true;
        else return false;
    }

    public double KyPhapBaLanNguoc(String toanHang)
    {
        Stack<String> NganXep=new Stack<String>();
        String tam2="";
        Queue<String> tam = new LinkedList<String>();
        int dem=0;
        int dem1=0;
        int dem2=0;
        for(int i=0;i<toanHang.length();i++)
        {
            dem++;
            if(toanHang.charAt(i)=='(')
            {
                String tam1="";
                tam1+=toanHang.charAt(i);
                NganXep.push(tam1);
            }
            else
            {
                if(toanHang.charAt(i)==')')
                {
                    if(tam2!="")
                    {
                        tam.add(tam2);
                        tam2="";
                    }
                    while(NganXep.peek().charAt(0)!='(')
                    {
                        tam.add(NganXep.pop());
                    }
                    NganXep.pop();
                }
                else
                {
                    if(KiemTraToanTu(toanHang.charAt(i))!=true)
                    {
                        if(i-1>=0)
                        {
                            if(toanHang.charAt(i-1)=='('|| toanHang.charAt(i-1)==')'||KiemTraToanTu(toanHang.charAt(i-1))==true)
                            {
                                if(dem2>dem1 && tam2!="")
                                {
                                    tam.add(tam2);
                                    tam2="";
                                }
                            }
                        }
                        tam2+=toanHang.charAt(i);
                    }
                    else
                    {
                        dem1=dem2;
                        dem2++;
                        String tam1="";
                        if(NganXep.isEmpty()!=true)
                        {
                            if(KiemTraDoUuTien(NganXep.peek().charAt(0))>=KiemTraDoUuTien(toanHang.charAt(i)))
                            {
                                if(tam2!="")
                                {
                                    tam.add(tam2);
                                    tam2="";
                                }
                                tam.add(NganXep.pop());
                                tam1+=toanHang.charAt(i);
                                NganXep.push(tam1);
                            }
                            else
                            {
                                if(tam2!="")
                                {
                                    tam.add(tam2);
                                    tam2="";
                                }
                                tam1+=toanHang.charAt(i);
                                NganXep.push(tam1);
                            }
                        }
                        else
                        {
                            tam1+=toanHang.charAt(i);
                            NganXep.push(tam1);
                        }
                    }
                }
            }

            if(dem==toanHang.length())
            {
                if(tam2!="")
                {
                    tam.add(tam2);
                }
                while(NganXep.isEmpty()!=true)
                {
                    tam.add(NganXep.pop());
                }
            }
        }
        double a;
        double b;
        Stack<String> NganXep1=new Stack<String>();
        while(tam.isEmpty()!=true)
        {
            if(tam.peek().length()==1)
            {
                char tam1=tam.peek().charAt(0);
                if(KiemTraToanTu(tam1)==true)
                {
                    a=Double.parseDouble(String.valueOf(NganXep1.pop()));
                    b=Double.parseDouble(String.valueOf(NganXep1.pop()));
                    if(tam1=='+')
                        NganXep1.push(String.valueOf(a+b));
                    else
                    if(tam1=='-')
                        NganXep1.push(String.valueOf(b-a));
                    else
                    if(tam1=='*')
                        NganXep1.push(String.valueOf(a*b));
                    else
                    if(tam1=='/')
                        NganXep1.push(String.valueOf(b/a));
                    tam.poll();
                }
                else
                {
                    NganXep1.push(tam.poll());
                }
            }
            else
            {
                NganXep1.push(tam.poll());
            }
        }
        return Double.parseDouble(NganXep1.pop());
    }
}
