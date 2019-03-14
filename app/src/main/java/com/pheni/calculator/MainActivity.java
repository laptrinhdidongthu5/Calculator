package com.pheni.calculator;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

import com.pheni.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public EditText textEdit;

    public void onClickNumber(View view) {
        String text = view.getTag().toString();

        int pos = textEdit.getSelectionStart();
        String a = textEdit.getText().toString();

        textEdit.setText(a.substring(0, pos) + text + a.substring(pos, a.length()));

        Editable etext = textEdit.getText();
        Selection.setSelection(etext, pos + 1);
//        Log.i("location", Integer.toString(a) );

    }

    public void onClickRemove(View view) {
        if (textEdit.getText().toString().length() != 0) {
            String text = textEdit.getText().toString();
            text = text.substring(0, text.length() - 1);
            textEdit.setText(text);
        }
    }

    public void onClickDelete(View view) {
        textEdit.setText("");
    }

    public void onClickResult(View view) {
        textEdit.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        textCalculator = findViewById(R.id.text_calculator);

        TableLayout keyBoarch = (TableLayout) findViewById(R.id.basic_keyBoard);

        textEdit = findViewById(R.id.text_result);
        textEdit.setShowSoftInputOnFocus(false);

    }
}
