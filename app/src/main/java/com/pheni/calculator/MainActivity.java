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
        Calculator.sTextInput = view.getTag().toString();//Lấy các thuộc tính để gán vào biến tạm
        Calculator.iPos = textEdit.getSelectionStart();
        Calculator.sResult = textEdit.getText().toString();

        //Validate để chuỗi nhập vào luôn đúng đắn
        this.validateEditText();

        textEdit.setText(Calculator.sResult);
        Editable etext = textEdit.getText(); //cái này Thành sẽ giải thích
        Selection.setSelection(etext, Calculator.iPos);
        textEdit.requestFocus();
    }

    /**
     * @return
     */
    public void validateEditText() {
        //Cập nhật chuỗi hiện tại
        //TrH con trỏ có thể đứng:
        //Đầu chuỗi          //Cuối chuối         //Giữa chuỗi:
        //Giữa 2 số          //Giữa 1 số, 1 dấu         //Giữa 1 dấu, 1 số
        //Thuật toán là nếu trước đó là số và sau nó là số thì nhập thoải mái
        //Nếu trước nó không phải số thì chỉ được nhập số
        //Nếu sau nó không phải số thì chỉ được nhập số
        String temp = Calculator.sResult;
        cacTruongHopDauCuoiChuoi();
        if (Calculator.sResult != temp) {
            return;
        } else {
            cacTruongHopDacBiet();
            if (Calculator.sResult != temp) {
                return;
            } else
                dauNgoac();
            if (Calculator.sResult != temp) {
                return;
            }
        }
    }

    /**
     * Nhập tiếp ký tự vừa nhấn bên giao diện
     */
    public void nhapTiep() {
        Calculator.sResult = Calculator.sResult.substring(0, Calculator.iPos) + Calculator.sTextInput +
                Calculator.sResult.substring(Calculator.iPos, Calculator.sResult.length());
        if (Calculator.sTextInput.length() == 1) {//Trường hợp nhập 1 số hoặc 1 ký tự tiếp theo
            Calculator.iPos += 1;
        } else {//Trường hợp nhập min,max
            Calculator.iPos += Calculator.sTextInput.length() - 1;
        }
    }


    public void cacTruongHopDauCuoiChuoi() {
        String reg = "[0-9]";
        //Xử lý validate cho đầu và cuối
        if (Calculator.iPos != 0) {//chỉ lấy khi nó không phải đầu
            Calculator.sStart = Calculator.sResult.substring(Calculator.iPos - 1, Calculator.iPos);
        } else {//Tức là đầu chuỗi phải nhập số rồi
            if (Calculator.sTextInput.matches(reg) || Calculator.sTextInput.matches("[(][)]")
                    || Calculator.sTextInput.matches("\\w{3,4}[(][)]")) {
                nhapTiep();
                return;
            }
        }
        if (Calculator.iPos != Calculator.sResult.length()) {//lấy khi nó không phải cuối
            Calculator.sEnd = Calculator.sResult.substring(Calculator.iPos, Calculator.iPos + 1);
            //Log.d("IT1006", "validateEditText: " + Calculator.sStart + "  " + Calculator.sResult);
        } else {//Nó cuối thì coi trước nó là gì là xong
            if ((Calculator.sStart.matches(reg))
                    && (Calculator.sTextInput.matches("[+\\-*\\/]") || Calculator.sTextInput.matches(reg))) {
                //Nếu trước nó là số thì nhập thoải mái nếu không thì chỉ cho nhập số
                nhapTiep();
                return;
            } else if (Calculator.sStart.matches("[+\\-*\\/]") &&
                    !Calculator.sTextInput.matches("[+\\-*\\/]")) {//+-*/ thì cho nhập thoải mái
                nhapTiep();
                return;
            } else if (Calculator.sStart.matches("[)]") && Calculator.sTextInput.matches("[+\\-*\\/]")) {
                nhapTiep();
                return;
            }
            return;
        }
    }

    public void cacTruongHopDacBiet() {
        String reg = "[0-9]";
        boolean matchStart = Calculator.sStart.matches(reg);
        boolean matchEnd = Calculator.sEnd.matches(reg);
        boolean matchTextInput = Calculator.sTextInput.matches(reg);
        //Các trường hợp đặc biệt
        if (!matchStart || (!matchEnd && !matchStart)) {//Trước con trỏ k phải số và sau trỏ không phải số
            if (matchTextInput) {//chỉ nhập số
                nhapTiep();
                return;
            }
        }
        if (((matchEnd && matchStart) || (matchStart && !matchEnd)
                || (matchStart && Calculator.sEnd.matches("[)]")))
                && (Calculator.sTextInput.matches("[(][)]") ||
                !Calculator.sTextInput.matches("\\w{3,4}[(][)]"))) {
            nhapTiep();
            return;
        }
        return;
    }

    public void dauNgoac() {
        //TH:
        //Mở dấu ngoặc đầu tiên
        //Sau dấu + - * /
        String rex = "\\w{3,4}[(]";
        String rexDau = "[+\\-*\\/]";
        //Nếu là dấu ngoặc thì cho con trỏ vào giữa, xong!
        if ((Calculator.sTextInput == "()" || Calculator.sTextInput.matches(rex)) && (!Calculator.sStart.matches("[0-9]") ||
                (Calculator.sStart.matches(rexDau)))) {//Hoặc trước là dấu + - * /
            nhapTiep();
            return;
        }
        return;
    }


    public void onClickResult(View view) {

        txtViewExpression = (TextView) findViewById(R.id.text_expression);
        double kq = 0;
        String giatri = textEdit.getText().toString();

        //trước khi tính toán cho lưu nhẹ để làm lịch sử cái nhé:
        String chuoiTinh = String.valueOf(textEdit.getText());

        kq = sinCosTanPrivate();

        //Xử lý kq.string đã mới in ra;
        xuLyChuoiInRa(Double.toString(kq));

        Calculator.sExpression = chuoiTinh + "= " + Calculator.sResult;
        txtViewExpression.setText(Calculator.sExpression);
        Log.i("a", txtViewExpression.toString());

        textEdit.setText(Calculator.sResult);
        Editable etext = textEdit.getText(); //cái này Thành sẽ giải thích
        textEdit.requestFocus();
        Calculator.sResult = textEdit.getText().toString();
        Selection.setSelection(etext, Calculator.sResult.length());

        //Lưu kết quả và phép tính để hiển thị qua bên History ở đây ở đây ở đây nè
        HistoryActivity.arrayList.add((HistoryActivity.arrayList.size() + 1) + ": "
                + chuoiTinh + " = " + String.valueOf(kq));

        saveHisory();
    }


    public void xuLyChuoiInRa(String kq) {
        if (kq.indexOf(".") != -1) {
            //Nếu chuỗi cuối là .0 thì xóa nó đi thôi
            if (kq.endsWith(".0")) {
                kq = kq.substring(0, kq.length() - 2);
            }
        }
        Calculator.sResult = kq;
        Calculator.iPos = Calculator.sResult.length();
    }

    public boolean kiemTraTonTaiSinCos(String giaTri) {
        int sum = giaTri.indexOf("s")
                + giaTri.indexOf("r")
                + giaTri.indexOf("m")
                + giaTri.indexOf("l")
                + giaTri.indexOf("i")
                + giaTri.indexOf("a")
                + giaTri.indexOf("c")
                + giaTri.indexOf("t");
        if (sum != -8)
            return true;
        else
            return false;
    }

    public Double sinCosTanPrivate() {
        String giaTri = Calculator.sResult;
        Double kq = 0.0;
        //Dùng thuật toán của thanh niên Công tính hết giá trị trong dấu ngoặc của sin.
        //Kiểm tra nó tồn tại bằng kiểm tra phần tử sin() cos(), tan(), min(), max(), log()
        //bằng indexOf(string)
        //Nếu thấy thì tính rồi trả về chuỗi mới rồi tiếp tục kiểm tra lại chuỗi xem còn tồn tại hông
        //Thuật toán tính là khi phát hiển phần tử, thì lấy được vị trí -->pos
        //Sau đó tiến hành tách chuỗi bên trong dấu ngoặc(cũng dùng indesOF để thấy dấu ) đầu tiên trong chuỗi
        //Tính sau đó lại ghép lại vào chuỗi(thay thế sqr(...) thành một giá trị và 2 dấu bao lại như(value)
        //Tiếp tục như thế cho đến khi không thấy
        //thì thoát ra và trả chuỗi này thành result và gọi hàm tính thêm một lần nữa
//
//        while (kiemTraTonTaiSinCos(giaTri)) {
//
//        }

        if (giaTri.charAt(1) == 's' && giaTri.length() == 7) {
            String giatritam = "";
            giatritam += giaTri.charAt(4);
            giatritam += giaTri.charAt(5);
            Operation1 tinhToan = new Operation1(Double.parseDouble(giatritam));
            kq = tinhToan.TinhSin();
        } else {
            //Tính riêng Cos
            if (giaTri.charAt(1) == 'c' && giaTri.length() == 7) {
                String giatritam = "";
                giatritam += giaTri.charAt(4);
                giatritam += giaTri.charAt(5);
                Operation1 tinhToan = new Operation1(Double.parseDouble(giatritam));
                kq = tinhToan.TinhCos();
            } else {
                //Tinh riêng Tan
                if (giaTri.charAt(1) == 't' && giaTri.length() == 7) {
                    String giatritam = "";
                    giatritam += giaTri.charAt(4);
                    giatritam += giaTri.charAt(5);
                    Operation1 tinhToan = new Operation1(Double.parseDouble(giatritam));
                    kq = tinhToan.TinhTan();
                } else {
                    OtherFuntion ketqua = new OtherFuntion();
                    kq = ketqua.KyPhapBaLanNguoc(textEdit.getText().toString());
                }
            }
        }
        return kq;
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

                int leng = a.length();
                textEdit.setText(a.substring(0, pos) + "," + a.substring(pos, leng));

                Editable etext = textEdit.getText();
                Selection.setSelection(etext, pos + ",".length());
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
        //Cập nhật lại các thông số:
        Calculator.iPos = textEdit.getSelectionStart();
        Calculator.sResult = textEdit.getText().toString();

        if (Calculator.sResult.length() != 0) {
            //Xóa 1 chuỗi ký tự là các hàm min, max,....
            String reg = "\\w{3,4}[(]";
            if (Calculator.iPos > 4 &&
                    Calculator.sResult.substring(Calculator.iPos - 4, Calculator.iPos).matches(reg)) {
                //Trường hợp trước con trỏ là một hàm thì xóa 4 ký tự
                xoaChuoiKyTu(4);

                Calculator.iPos -= 4;
            }
            if (Calculator.iPos > 2 &&
                    Calculator.sResult.substring(Calculator.iPos - 2, Calculator.iPos) == "()") {
                //Xóa Dấu ()
                xoaChuoiKyTu(2);
                Calculator.iPos -= 2;
            } else {
                //Xóa 1 ký tự ở phía trước con trỏ
                //Xóa ở giữa phải cập nhật lại chuỗi, dùng cắt chuỗi nhanh hơn
                xoaChuoiKyTu(1);
                Calculator.iPos -= 1;
            }
            Editable etext = textEdit.getText(); //cái này Thành sẽ giải thích
            textEdit.requestFocus();
            Calculator.sResult = textEdit.getText().toString();
            Selection.setSelection(etext, Calculator.iPos);
        }
//        if (textCacul.length() != 0) {
//            int position = textCacul.length() - 1;
//            if (textCacul.charAt(position) == 'w' || textCacul.charAt(position) == 'r' ||
//                    textCacul.charAt(position) == 's' || textCacul.charAt(position) == 'v' ||
//                    textCacul.charAt(position) == 'n') {
//                String text = textEdit.getText().toString();
//                text = text.substring(0, text.length() - 3);
//                textEdit.setText(text);
//                Editable etext = textEdit.getText();
//                Selection.setSelection(etext, textEdit.length());
//            } else {
//                String text = textEdit.getText().toString();
//                text = text.substring(0, text.length() - 1);
//                textEdit.setText(text);
//                Editable etext = textEdit.getText();
//                Selection.setSelection(etext, textEdit.length());
//            }
//        }
    }

    public void xoaChuoiKyTu(int baoNhiuKyTu) {
        String sDau = Calculator.sResult.substring(0, Calculator.iPos - baoNhiuKyTu);
        int length = Calculator.sResult.length();
        String sCuoi = Calculator.sResult.substring(Calculator.iPos, length);
        Calculator.sResult = sDau + sCuoi;
        textEdit.setText(Calculator.sResult);
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
                fos.write("\n".getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
