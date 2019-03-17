package com.pheni.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pheni.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public EditText textEdit;                                   //Thành giải thích
    public TextView txt1;

    public static final int MY_REQUEST_CODE = 100;                //
    private static final int REQUEST_CODE_KEYBOARD = 0x9345;    //const activity

    /**
     * event add number and calculator in edittext
     *
     * @param view
     */
    public void onClickNumber(View view) {
        String text = view.getTag().toString();

        int pos = textEdit.getSelectionStart();
        String a = textEdit.getText().toString();

        textEdit.setText(a.substring(0, pos) + text + a.substring(pos, a.length()));

        Editable etext = textEdit.getText(); //cái này Thành sẽ giải thích
        Selection.setSelection(etext, pos + 1);

    }


    /**
     * event remove 1 char in edittext
     *
     * @param view
     */
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

        String text_expression = exp.prioritize();
        
        txt1= (TextView) findViewById(R.id.text_result);
        double kq;
        OtherFuntion ketqua=new OtherFuntion();
        kq=ketqua.KyPhapBaLanNguoc(text_expression);

        txt1.setText(String.valueOf(kq));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_KEYBOARD) {
            if (resultCode == Activity.RESULT_OK) {
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

        textEdit = findViewById(R.id.text_expression);
        //textEdit.setShowSoftInputOnFocus(false); //không sử dụng được ở API 19: fix lại ở disableinputRetaincursor() method:
        //hideKeyboard(this);//this ở đây chính là một MainActivity but it is not working
        disableinputRetaincursor(textEdit); //it working success!

        View view_open = findViewById(R.id.btn_onKeyBoard);
        view_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KeyBoard.class);
                startActivityForResult(intent, REQUEST_CODE_KEYBOARD);// Activity is started with requestCode 2

            }
        });

    }

    /**
     * Author: IT1006
     * Ẩn input của activity được truyền vào
     * Không phải ẩn một mà là tất cả các loại input
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();

        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Ẩn input của một đối tượng cụ thể
     *
     * @param editText
     */
    public static void disableinputRetaincursor(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }
}
