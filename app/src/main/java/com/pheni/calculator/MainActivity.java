package com.pheni.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView textCalculator;

    public void onKeyboeardAdvanced(View view){

        TableLayout keyboardBasic = findViewById(R.id.keyboardBasic);
        Button btnArrowLeft = findViewById(R.id.btn_arrow_left);
        Button btnArrowRight = findViewById(R.id.btn_arrow_right);
        TableLayout keyboardAdvanced1 = findViewById(R.id.keyboardAdvanced1);
        TableLayout keyboardAdvanced2 = findViewById(R.id.keyboardAdvanced2);

        keyboardBasic.animate().xBy(-1090);
        btnArrowLeft.animate().xBy(-1090);
        btnArrowRight.animate().xBy(-1090);
        keyboardAdvanced1.animate().xBy(-1090);
        keyboardAdvanced2.animate().xBy(-1090);

    }

    public void onKeyboeardBasic(View view){

        TableLayout keyboardBasic = findViewById(R.id.keyboardBasic);
        Button btnArrowLeft = findViewById(R.id.btn_arrow_left);
        Button btnArrowRight = findViewById(R.id.btn_arrow_right);
        TableLayout keyboardAdvanced1 = findViewById(R.id.keyboardAdvanced1);
        TableLayout keyboardAdvanced2 = findViewById(R.id.keyboardAdvanced2);

        keyboardBasic.animate().xBy(1090);
        btnArrowLeft.animate().xBy(1090);
        btnArrowRight.animate().xBy(1090);
        keyboardAdvanced1.animate().xBy(1090);
        keyboardAdvanced2.animate().xBy(1090);

    }

    public void onClickEqual (View view){
//        textCalculator.setText(textCalculator.getText().toString()+"=");
    }

    public void onClickDot(View view){
        textCalculator.setText(textCalculator.getText().toString()+".");
    }

    public void onClickAdd(View view){
        textCalculator.setText(textCalculator.getText().toString()+"+");
    }

    public void onClickSub(View view){
        textCalculator.setText(textCalculator.getText().toString()+"-");
    }

    public void onClickMul(View view){
        textCalculator.setText(textCalculator.getText().toString()+"x");
    }

    public void onClickDiv(View view){
        textCalculator.setText(textCalculator.getText().toString()+":");
    }

    public void onClickZero(View view){
        textCalculator.setText(textCalculator.getText().toString()+"0");
    }

    public void onClickOne(View view){
        textCalculator.setText(textCalculator.getText().toString()+"1");
    }

    public void onClickTwo(View view){
        textCalculator.setText(textCalculator.getText().toString()+"2");
    }

    public void onClickThree(View view){
        textCalculator.setText(textCalculator.getText().toString()+"3");
    }

    public void onClickFour(View view){
        textCalculator.setText(textCalculator.getText().toString()+"4");
    }

    public void onClickFive(View view){
        textCalculator.setText(textCalculator.getText().toString()+"5");
    }

    public void onClickSix(View view){
        textCalculator.setText(textCalculator.getText().toString()+"6");
    }

    public void onClickSeven(View view){
        textCalculator.setText(textCalculator.getText().toString()+"7");
    }

    public void onClickEight(View view){
        textCalculator.setText(textCalculator.getText().toString()+"8");
    }

    public void onClickNine(View view){
        textCalculator.setText(textCalculator.getText().toString()+"9");
    }

    public void onClickRemove(View view){
        String text = textCalculator.getText().toString();
        text = text.substring(0, text.length()-1);
        textCalculator.setText(text);
    }

    public void onClickDelete(View view){
        textCalculator.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCalculator = findViewById(R.id.textCalculator);

    }
}
