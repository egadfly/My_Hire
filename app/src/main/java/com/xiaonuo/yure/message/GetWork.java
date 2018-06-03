package com.xiaonuo.yure.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.xiaonuo.yure.R;
import com.xiaonuo.yure.utils.Utils;


/**
 * Created by Administrator on 2018/2/10.
 */

public class GetWork extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getwork);

        //返回按钮
        findViewById(R.id.getMessageActivity_button_exit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Utils.prePage(GetWork.this);
                    }
                });

        //提交按钮
        final AVLoadingIndicatorView loadingView = findViewById(R.id.getMessageActivity_loadingView);
        findViewById(R.id.getMessageActivity_button_submit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingView.smoothToShow();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                GetWork.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        loadingView.smoothToHide();

                                        Utils.show("服务请求失败");
                                    }
                                });
                            }
                        }).start();

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.prePage(GetWork.this);
    }
}
