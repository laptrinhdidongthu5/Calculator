package com.pheni.calculator;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Context;
import android.content.ContextWrapper;
=======
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
<<<<<<< HEAD
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
=======
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8

import com.pheni.calculator.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
    public EditText textEdit;                                   //Thành giải thích??
    public TextView txt1;
    public Button btnComma;

    public static final int MY_REQUEST_CODE = 100;
    private static final int REQUEST_CODE_KEYBOARD = 0x9345;    //const activity?


    /**
     * event add number and calculator in edittext
     *
     * @param view
     */
=======
    public EditText textEdit;

    public static final int MY_REQUEST_CODE = 100;
    private static final int REQUEST_CODE_KEYBOARD = 0x9345;

>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
    public void onClickNumber(View view) {
        String text = view.getTag().toString();

        int pos = textEdit.getSelectionStart();
        String a = textEdit.getText().toString();

        textEdit.setText(a.substring(0, pos) + text + a.substring(pos, a.length()));

        Editable etext = textEdit.getText();
        Selection.setSelection(etext, pos + 1);

    }

<<<<<<< HEAD
    /**
     * event remove 1 char in edittext
     *
     * @param view
     */
=======
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
    public void onClickRemove(View view) {
        String textCacul = textEdit.getText().toString();
        if (textCacul.length() != 0) {
            int position = textCacul.length() - 1;
<<<<<<< HEAD
            if (textCacul.charAt(position) == 'w' || textCacul.charAt(position) == 'r' ||
                    textCacul.charAt(position) == 's' || textCacul.charAt(position) == 'v' ||
                    textCacul.charAt(position) == 'n') {
=======
            if(textCacul.charAt(position) == 'w' || textCacul.charAt(position) == 'r' ||
                    textCacul.charAt(position) == 's' || textCacul.charAt(position) == 'v' ||
                    textCacul.charAt(position) == 'n'){
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
                String text = textEdit.getText().toString();
                text = text.substring(0, text.length() - 3);
                textEdit.setText(text);
                Editable etext = textEdit.getText();
                Selection.setSelection(etext, textEdit.length());
<<<<<<< HEAD
            } else {
=======
            }
            else {
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
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
<<<<<<< HEAD

        txt1 = (TextView) findViewById(R.id.text_expression);
        double kq;
        String giatri = textEdit.getText().toString();

        // Tính riêng Sin
        if (giatri.charAt(1) == 's' && giatri.length() == 7) {
            String giatritam = "";
            giatritam += giatri.charAt(4);
            giatritam += giatri.charAt(5);
            Operation1 tinhToan = new Operation1(Double.parseDouble(giatritam));
            kq = tinhToan.TinhSin();
        } else {
            //Tính riêng Cos
            if (giatri.charAt(1) == 'c' && giatri.length() == 7) {
                String giatritam = "";
                giatritam += giatri.charAt(4);
                giatritam += giatri.charAt(5);
                Operation1 tinhToan = new Operation1(Double.parseDouble(giatritam));
                kq = tinhToan.TinhCos();
            } else {
                //Tinh riêng Tan
                if (giatri.charAt(1) == 't' && giatri.length() == 7) {
                    String giatritam = "";
                    giatritam += giatri.charAt(4);
                    giatritam += giatri.charAt(5);
                    Operation1 tinhToan = new Operation1(Double.parseDouble(giatritam));
                    kq = tinhToan.TinhTan();
                } else {
                    OtherFuntion ketqua = new OtherFuntion();
                    kq = ketqua.KyPhapBaLanNguoc(textEdit.getText().toString());
                }
            }
        }
        txt1.setText(String.valueOf(kq));
        Log.i("a", txt1.toString());
=======
        Expression exp = new Expression(textEdit.getText().toString());

        String a = exp.getPrioritize();

        Log.i("a",a);

>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
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

        btnComma = findViewById(R.id.btn_dot);
        btnComma.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = textEdit.getSelectionStart();
                String a = textEdit.getText().toString();

                textEdit.setText(a.substring(0, pos) + " , " + a.substring(pos, a.length()));

                Editable etext = textEdit.getText();
                Selection.setSelection(etext, pos + " , ".length());
                return true;
            }
        });

        textEdit = findViewById(R.id.text_result);
        textEdit.setShowSoftInputOnFocus(false);

<<<<<<< HEAD
        View view_openHistory = findViewById(R.id.btn_onHistory);
        view_openHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
=======
        View view_open = findViewById(R.id.btn_onKeyBoard);
        view_open.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,KeyBoard.class);
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
                startActivityForResult(intent, REQUEST_CODE_KEYBOARD);// Activity is started with requestCode 2
            }
        });

        //Gọi load file
        try {
            loadHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        //Clear list hiện tại
        HistoryActivity.arrayList.clear();
        super.onDestroy();
    }
<<<<<<< HEAD

    /**
     * chính là load arrayList
     *
     * @throws IOException
     */
    public void loadHistory() throws IOException {
        //Chỉ load 1 lần khi mở ứng dụng

        //Lưu trữ lịch sử cho lần khởi động tiếp theo
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        //Tạo (Hoặc là mở file nếu nó đã tồn tại) Trong bộ nhớ trong có thư mục là ThuMucCuaToi.
        File directory = contextWrapper.getDir(HistoryActivity.filepath, Context.MODE_PRIVATE);
        HistoryActivity.myInternalFile = new File(directory, HistoryActivity.filename);
        //Trước khi lưu phải đọc lên trước
        FileInputStream fis = new FileInputStream(HistoryActivity.myInternalFile);
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(in));
        String strLine;
        //Đọc từng dòng
        while ((strLine = br.readLine()) != null) {
            HistoryActivity.arrayList.add(strLine);
        }
        in.close();

    }

    public void saveHisory() {
        //Lưu trữ lịch sử cho lần khởi động tiếp theo
        //Ghi đè mảng vào file
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        //Tạo (Hoặc là mở file nếu nó đã tồn tại) Trong bộ nhớ trong có thư mục là ThuMucCuaToi.
        File directory = contextWrapper.getDir(HistoryActivity.filepath, Context.MODE_APPEND);
        //Save array list
        HistoryActivity.myInternalFile = new File(directory, HistoryActivity.filename);

        try {
            //Mở file
            FileOutputStream fos = new FileOutputStream(HistoryActivity.myInternalFile);
            //Ghi dữ liệu vào file
            fos.write("".getBytes());
            for (int i = 0; i < HistoryActivity.arrayList.size(); i++) {
                fos.write(HistoryActivity.arrayList.get(i).getBytes());
                //   fos.write("\n".getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
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

=======
>>>>>>> f7852af34f763e5d1c9ee3aa73b067ba4f4b54e8
}
