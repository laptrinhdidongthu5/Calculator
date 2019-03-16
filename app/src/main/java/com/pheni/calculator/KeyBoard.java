package com.pheni.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class KeyBoard extends Activity {

    public void onClickNumber(View view){

        String text = view.getTag().toString();

        Intent data = new Intent();
        data.putExtra("feedback", text);

        this.setResult(Activity.RESULT_OK, data);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .9), (int)(height * .6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = 0;
        params.y = 50;

        getWindow().setAttributes(params);

    }
}
