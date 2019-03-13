package com.pheni.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView textCalculator;

    public void onClickNumber(View view){
        String text = view.getTag().toString();

        textCalculator.setText(textCalculator.getText().toString()+ text );

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

        textCalculator = findViewById(R.id.text_calculator);
    }
}
