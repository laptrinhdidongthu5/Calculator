package com.pheni.calculator;

import com.pheni.calculator.databinding.ActivityMainBinding;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    public static final int MY_REQUEST_CODE = 100;
    private static final int REQUEST_CODE_KEYBOARD = 0x9345;    //const activity?

    public EditText textEdit;                                   //Thành giải thích??
    public TextView txtViewExpression;
    public Button btnComma; //Xử lý sự kiện longClickLisenter của dấu . và , chung 1 nút btn

    /**
     * event add number and calculator in edittext
     *
     * @param view
     */
    public void onClickNumber(View view) {
        String text = view.getTag().toString();//Lấy các thuộc tính để gán vào biến tạm

        int pos = textEdit.getSelectionStart();
        String a = textEdit.getText().toString();

        //Validate để chuỗi nhập vào luôn đúng đắn
        String validate = validateEditText(a, pos, text);
        textEdit.setText(validate);
        Editable etext = textEdit.getText(); //cái này Thành sẽ giải thích


        String rex = "\\w{3,4}[(]";
        textEdit.findFocus();
        if (validate != a) {//Trường hợp validate gặp sai phạm
            Selection.setSelection(etext, pos + 1);
        } else if (text == "()" || text.matches(rex)) {
            Selection.setSelection(etext, pos + text.length() - 1);
        } else {
            Selection.setSelection(etext, pos);
        }

        textEdit.requestFocus();
    }

    /**
     * @param textEdit  :Expression
     * @param pos       :vị trí con trỏ
     * @param textInput :ký tự nhập tiếp theo
     * @return
     */
    public String validateEditText(String textEdit, int pos, String textInput) {
        //Cập nhật chuỗi hiện tại
        String result = textEdit;
        String reg = "[0-9]";
        //TrH con trỏ có thể đứng:
        //Đầu chuỗi          //Cuối chuối         //Giữa chuỗi:
        //Giữa 2 số          //Giữa 1 số, 1 dấu         //Giữa 1 dấu, 1 số
        //Thuật toán là nếu trước đó là số và sau nó là số thì nhập thoải mái
        //Nếu trước nó không phải số thì chỉ được nhập số
        //Nếu sau nó không phải số thì chỉ được nhập số
        String start = "";
        String end = "";
        //Xử lý validate cho đầu và cuối
        if (pos != 0) {//chỉ lấy khi nó không phải đầu
            start = textEdit.substring(pos - 1, pos);
        } else {//Tức là đầu chuỗi phải nhập số rồi
            if (textInput.matches(reg) || textInput.matches("[()]") || textInput.matches("\\w{3,4}[(]")) {
                result = textEdit.substring(0, pos) + textInput + textEdit.substring(pos, textEdit.length());
                return result;
            }
        }
        if (pos != textEdit.length()) {//lấy khi nó không phải cuối
            end = textEdit.substring(pos, pos + 1);
            Log.d("IT1006", "validateEditText: " + start + "  " + textEdit);
        } else {//Nó cuối thì coi trước nó là gì là xong
            if (start.matches(reg) && !textInput.matches("[()]}")) {//Nếu trước nó là số thì nhập thoải mái nếu không thì chỉ cho nhập số
                result = textEdit.substring(0, pos) + textInput + textEdit.substring(pos, textEdit.length());
                return result;
            } else {
                if (textInput.matches(reg)) {
                    result = textEdit.substring(0, pos) + textInput + textEdit.substring(pos, textEdit.length());
                    return result;
                }
            }
        }

        boolean matchStart = start.matches(reg);
        boolean matchEnd = end.matches(reg);
        boolean matchTextInput = textInput.matches(reg);
        //Các trường hợp đặc biệt
        if (!matchStart || !matchEnd || (!matchEnd && !matchStart)) {//Trước con trỏ k phải số và sau trỏ không phải số
            if (matchTextInput) {//chỉ nhập số
                result = textEdit.substring(0, pos) + textInput + textEdit.substring(pos, textEdit.length());
                return result;
            }

        } else if ((matchEnd && matchStart) || (matchStart && !matchEnd)
                || (matchStart && end.matches("[)]"))) {
            result = textEdit.substring(0, pos) + textInput + textEdit.substring(pos, textEdit.length());
            return result;
        }


        dauNgoac(textEdit, pos, textInput, start, end);

        return result;
    }

    public String dauNgoac(String textEdit, int pos, String textInput, String strStart, String strEnd) {
        //TH:
        //Mở dấu ngoặc đầu tiên
        //Sau dấu + - * /

        String result = textEdit;
        String rex = "\\w{3,4}[(]";
        String rexDau = "[+\\-*\\/]";
        //Nếu là dấu ngoặc thì cho con trỏ vào giữa, xong!
        if (((textInput == "()" || textInput.matches(rex)) && !strStart.matches("[0-9]")) ||
                (strStart.matches(rexDau))) {//Hoặc trước là dấu + - * /
            //(), sqrt(), matchStart = false
            result = textEdit.substring(0, pos) + textInput + textEdit.substring(pos, textEdit.length());
            return result;
        }
        return result;
    }


    public void onClickResult(View view) {

        txtViewExpression = (TextView) findViewById(R.id.text_expression);
        double kq = 0;
        String giatri = textEdit.getText().toString();

        //trước khi tính toán cho lưu nhẹ để làm lịch sử cái nhé:
        String chuoiTinh = String.valueOf(textEdit.getText());

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
        txtViewExpression.setText(chuoiTinh + "= " + Double.toString(kq));
        Log.i("a", txtViewExpression.toString());

        textEdit.setText(Double.toString(kq));

        //Lưu kết quả và phép tính để hiển thị qua bên History ở đây ở đây ở đây nè
        HistoryActivity.arrayList.add((HistoryActivity.arrayList.size() + 1) + ": "
                + chuoiTinh + " = " + String.valueOf(kq) + "\n");

        saveHisory();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);//biding source

        btnComma = findViewById(R.id.btn_dot);//ánh xạ cho phần xử lý riêng nút . và , chung 1 btn
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
        //textEdit.setShowSoftInputOnFocus(false); //không sử dụng được ở API 19: fix lại ở disableinputRetaincursor() method:
        //hideKeyboard(this);//this ở đây chính là một MainActivity but it is not working
        disableinputRetaincursor(textEdit); //it working success!

        textEdit.requestFocus();

        View view_openHistory = findViewById(R.id.btn_onHistory);
        view_openHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(intent, MY_REQUEST_CODE);// Activity is started with requestCode 2
            }
        });

        //Gọi load file
        try {
            loadHistory();
        } catch (IOException e) {
            Log.d("IT1006", "onCreate:");
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        //Clear list hiện tại
        HistoryActivity.arrayList.clear();
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_KEYBOARD) {
            if (resultCode == Activity.RESULT_OK) {
                String feedback = data.getStringExtra("feedback");

                int pos = textEdit.getSelectionStart();
                String a = textEdit.getText().toString();

                textEdit.setText(a.substring(0, pos) + feedback + a.substring(pos, a.length()));

                Editable etext = textEdit.getText();
                Selection.setSelection(etext, pos + feedback.length());
            }
        }
    }

    public void onClickDelete(View view) {
        textEdit.setText("");
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
            if (textCacul.charAt(position) == 'w' || textCacul.charAt(position) == 'r' ||
                    textCacul.charAt(position) == 's' || textCacul.charAt(position) == 'v' ||
                    textCacul.charAt(position) == 'n') {
                String text = textEdit.getText().toString();
                text = text.substring(0, text.length() - 3);
                textEdit.setText(text);
                Editable etext = textEdit.getText();
                Selection.setSelection(etext, textEdit.length());
            } else {
                String text = textEdit.getText().toString();
                text = text.substring(0, text.length() - 1);
                textEdit.setText(text);
                Editable etext = textEdit.getText();
                Selection.setSelection(etext, textEdit.length());
            }
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

}
