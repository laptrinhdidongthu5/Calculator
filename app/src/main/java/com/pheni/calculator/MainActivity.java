package com.pheni.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
