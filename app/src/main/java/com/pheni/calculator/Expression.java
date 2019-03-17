package com.pheni.calculator;

import android.util.Log;

public class Expression {

    public String textCaculator;

    public Expression (String textCaculator){
        this.textCaculator = textCaculator;
    }

    public static String addSpace(String textCaculator){

        String textHanding = textCaculator;

        for (int i = 0 ; i< textHanding.length(); i++){
            if(textHanding.charAt(i) == '+' || textHanding.charAt(i) == '-' ||
                    textHanding.charAt(i) == 'x' || textHanding.charAt(i) == '/' ||
                    textHanding.charAt(i) == '%' || textHanding.charAt(i) == ',' ||
                    textHanding.charAt(i) == '(' || textHanding.charAt(i) == ')'){
                textHanding = addChar(textHanding, ' ', i);
                textHanding = addChar(textHanding, ' ', i+2);
                i+=2;

            }

            else if(textHanding.charAt(i) == 's' || textHanding.charAt(i) == 'm' ||
                    textHanding.charAt(i) == 'i' ||textHanding.charAt(i) == 'a' ||
                    textHanding.charAt(i) == 'c' ||textHanding.charAt(i) == 't' ){
                textHanding = addChar(textHanding, ' ', i);
                i+=3;
            }
        }

        return textHanding;
    }

    private static String replaceResult (String textCaculator, String number1, String number2, String method){
        String textHanding = textCaculator;
        String text = method + " ( " + number1 + " , " + number2 + " )";
        String textReplace = "";
        String result = "";

        Operation2 pt = new Operation2(Double.parseDouble(number1), Double.parseDouble(number2));

        switch (method){
            case "pow":
                textReplace = Double.toString(pt.TinhMu());
                result = textHanding.replace(text, textReplace);
                break;
            case "sqr":
                textReplace = Double.toString(pt.TinhCan());
                result = textHanding.replace(text, textReplace);
                break;
            case "max":
                textReplace = Double.toString(pt.TimMax());
                result = textHanding.replace(text, textReplace);
                break;
            case "min":
                textReplace = Double.toString(pt.TimMin());
                result = textHanding.replace(text, textReplace);
                break;
            case "mod":
                textReplace = Double.toString(pt.TinhMode());
                result = textHanding.replace(text, textReplace);
                break;
            default:
                break;
        }

        return result;
    }

    private static String replaceResult (String textCaculator, String number, String method){
        String textHanding = textCaculator;
        String text = method + " ( " + number + " )";
        String textReplace = "";
        String result = "";

//        Operation1 pt = new Operation1(Double.parseDouble(number1));

        switch (method){
            case "sin":
                textReplace = Double.toString(Math.sin(Double.parseDouble(number)));

                result = textHanding.replace(text, textReplace);
                break;
            case "cos":
                textReplace = Double.toString(Math.cos(Double.parseDouble(number)));
                result = textHanding.replace(text, textReplace);
                break;
            case "tan":
                textReplace = Double.toString(Math.tan(Double.parseDouble(number)));
                result = textHanding.replace(text, textReplace);
                break;
            case "inv":
                textReplace = Double.toString(1/Double.parseDouble(number));
                result = textHanding.replace(text, textReplace);
                break;
            case "abs":
                textReplace = Double.toString(Math.abs(Double.parseDouble(number)));
                result = textHanding.replace(text, textReplace);
                break;
            default:
                break;
        }

        return result;
    }

    public String prioritize(){
        String textHanding = addSpace(textCaculator);
        String result = "";

        String[] arStr = textHanding.split(" ");
        int index = 0;
        for (String item : arStr) {
            index++;
            switch (item){
                case "pow":
                case "max":
                case "min":
                case "sqr":
                case "mod":
                    result = replaceResult(textHanding,arStr[index + 1],arStr[index + 3],item );
                    break;
                case "sin":
                case "cos":
                case "tan":
                case "inv":
                case "abs":
                    result = replaceResult(textHanding,arStr[index + 1],item );
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    private static String addChar(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }

    public String getTextCaculator() {
        return textCaculator;
    }

    public void setTextCaculator(String textCaculator) {
        this.textCaculator = textCaculator;
    }


}
