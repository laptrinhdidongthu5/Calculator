package com.pheni.calculator;

public class Expression {

    public String textCaculator;

    public Expression (String textCaculator){
        this.textCaculator = textCaculator;
    }

    public String getPrioritize(){

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

    private String addChar(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }

    public String getTextCaculator() {
        return textCaculator;
    }

    public void setTextCaculator(String textCaculator) {
        this.textCaculator = textCaculator;
    }


}
