package com.xiaonuo.yure.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xiaonuo.yure.R;
import com.xiaonuo.yure.utils.MyApplication;
import com.xiaonuo.yure.utils.Utils;

/**
 * Created by Administrator on 2018/2/10.
 */

public class SelectPostType extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectposttype);


        //返回按钮的初始
        findViewById(R.id.selectPostTypeActivity_button_exit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Utils.prePage(SelectPostType.this);
                    }
                });

        //我要发布职位
        findViewById(R.id.selectPostTypeActivity_button_postWork)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MyApplication.getContext(),PostWork.class));
                        finish();
                        Utils.nextPage(SelectPostType.this);
                    }
                });

        //我要找职位
        findViewById(R.id.selectPostTypeActivity_button_getWork)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MyApplication.getContext(),GetWork.class));
                        finish();
                        Utils.nextPage(SelectPostType.this);
                    }
                });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.prePage(SelectPostType.this);
    }
}
