package com.pheni.calculator;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.pheni.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public EditText textEdit;

    public static final int MY_REQUEST_CODE = 100;
    private static final int REQUEST_CODE_KEYBOARD = 0x9345;

    public void onClickNumber(View view) {
        String text = view.getTag().toString();

        int pos = textEdit.getSelectionStart();
        String a = textEdit.getText().toString();

        textEdit.setText(a.substring(0, pos) + text + a.substring(pos, a.length()));

        Editable etext = textEdit.getText();
        Selection.setSelection(etext, pos + 1);

    }

    public void onClickRemove(View view) {
        String textCacul = textEdit.getText().toString();
        if (textCacul.length() != 0) {
            int position = textCacul.length() - 1;
            if(textCacul.charAt(position) == 'w' || textCacul.charAt(position) == 'r' ||
                    textCacul.charAt(position) == 's' || textCacul.charAt(position) == 'v' ||
                    textCacul.charAt(position) == 'n'){
                String text = textEdit.getText().toString();
                text = text.substring(0, text.length() - 3);
                textEdit.setText(text);
                Editable etext = textEdit.getText();
                Selection.setSelection(etext, textEdit.length());
            }
            else {
                String text = textEdit.getText().toString();
                text = text.substring(0, text.length() - 1);
                textEdit.setText(text);
                Editable etext = textEdit.getText();
                Selection.setSelection(etext, textEdit.length());
            }
        }
    }

    public void onClickDelete(View view) {
        textEdit.setText("");
    }

    public void onClickResult(View view) {
        Expression exp = new Expression(textEdit.getText().toString());

        String a = exp.getPrioritize();

        Log.i("a",a);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_KEYBOARD) {
            if(resultCode == Activity.RESULT_OK) {
                String feedback = data.getStringExtra("feedback");

                int pos = textEdit.getSelectionStart();
                String a = textEdit.getText().toString();

                textEdit.setText(a.substring(0, pos)  + feedback + a.substring(pos, a.length()));

                Editable etext = textEdit.getText();
                Selection.setSelection(etext, pos + feedback.length());
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        textEdit = findViewById(R.id.text_result);
        textEdit.setShowSoftInputOnFocus(false);

        View view_open = findViewById(R.id.btn_onKeyBoard);
        view_open.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,KeyBoard.class);
                startActivityForResult(intent, REQUEST_CODE_KEYBOARD);// Activity is started with requestCode 2

            }
        });

    }
}
