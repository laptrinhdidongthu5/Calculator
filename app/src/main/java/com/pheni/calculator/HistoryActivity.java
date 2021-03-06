package com.pheni.calculator;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView lvHistory;
    public static ArrayList<String> arrayList = new ArrayList<>();
    List<History> listHistory;
    Button btnClearHistory;


    //Lưu trữ lịch sử cần các biến sau:
    public static String filename = "internalStorageHistory.txt";
    //Thư mục do mình đặt
    public static String filepath = "historyCalculator";
    public static File myInternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        float width = dm.widthPixels;
        float height = dm.heightPixels;
        getWindow().setLayout((int) (width * 1), (int) (height * 1));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = -50;
        params.y = 180;
        params.width = (int) width;
        params.height = (int) height;
        getWindow().setAttributes(params);


        btnClearHistory = (Button) findViewById(R.id.btn_clear_history);
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
                finish();
            }
        });

        //Trước khi in ra thì đảo ngược lại mảng,
        Collections.reverse(HistoryActivity.arrayList);

        lvHistory = (ListView) findViewById(R.id.listViewHistory);
        ArrayAdapter arrayAdapter = new ArrayAdapter(HistoryActivity.this,
                android.R.layout.simple_list_item_1, HistoryActivity.arrayList);
        lvHistory.setAdapter(arrayAdapter);

    }

    @Override
    public void onDestroy() {
        //Trước khi thoát ra thì đảo ngược lại mảng,
        Collections.reverse(HistoryActivity.arrayList);
        super.onDestroy();
    }

    public static void clearHistory() {
        HistoryActivity.arrayList.clear();
        try {
            //Mở file
            FileOutputStream fos = new FileOutputStream(HistoryActivity.myInternalFile);
            //Ghi dữ liệu vào file
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IT1006", "Không xóa được dữ liệu!");
        }
    }

}