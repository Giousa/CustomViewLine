package com.giou.customprogressview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    protected static final int WHAT_INCREASE = 1;
    private NumberProgressView np_numberProgressBar;
    private int progress;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            progress++;
            np_numberProgressBar.setProgress(progress);
            handler.sendEmptyMessageDelayed(WHAT_INCREASE, getRadomNumber(50, 200));
            if (progress >= 100) {
                handler.removeMessages(WHAT_INCREASE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        np_numberProgressBar = (NumberProgressView) findViewById(R.id.np_numberProgressBar);
        Button btn_numberProgressBar = (Button) findViewById(R.id.btn_numberProgressBar);
        btn_numberProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });
    }

    private void increase() {
        progress = 0;
        np_numberProgressBar.setProgress(0);
        handler.removeMessages(WHAT_INCREASE);
        handler.sendEmptyMessage(WHAT_INCREASE);
    }

    /**
     * 得到两个整数之间的一个随机数
     *
     * @param start 较小的数
     * @param end   较大的数
     * @return
     */
    public int getRadomNumber(int start, int end) {
        return (int) (start + Math.random() * (end - start));
    }
}
