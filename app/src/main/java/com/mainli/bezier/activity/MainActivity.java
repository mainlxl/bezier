package com.mainli.bezier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mainli.bezier.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1(View view) {
        startActivity(new Intent(this, Bezier2Activity.class));
    }

    public void btn2(View view) {
        startActivity(new Intent(this, Bezier3Activity.class));
    }

    public void btn3(View view) {
        startActivity(new Intent(this, LineActivity.class));
    }

    public void btn4(View view) {
        startActivity(new Intent(this, AnimatorActivity.class));
    }

    public void btn5(View view) {
        startActivity(new Intent(this, WaveActivity.class));
    }

    public void btn6(View view) {
        startActivity(new Intent(this, CircleActivity.class));
    }
    public void btn7(View view) {
        startActivity(new Intent(this, Circle2HeartViewActivity.class));
    }
}
