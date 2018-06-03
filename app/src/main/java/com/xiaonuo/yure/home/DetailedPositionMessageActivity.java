package com.xiaonuo.yure.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.wx.goodview.GoodView;
import com.xiaonuo.yure.R;
import com.xiaonuo.yure.utils.Constant;
import com.xiaonuo.yure.utils.Utils;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2018/2/8.
 */

public class DetailedPositionMessageActivity extends AppCompatActivity {

    private GoodView goodView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedpositionmessage);

        goodView = new GoodView(this);

        //收藏按钮逻辑
        initCollectionButton();

        //返回按钮逻辑
        initExitButton();

        //立即报名按钮的逻辑
        initApplyButton();
    }

    private void initApplyButton() {

        final AVLoadingIndicatorView loadingView = findViewById(R.id.detailedPositionActivity_loadingView);

        FancyButton exit_Button = findViewById(R.id.detailedPositionActivity_button_apply);
        exit_Button.setOnClickListener(new View.OnClickListener() {
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

                        DetailedPositionMessageActivity.this.runOnUiThread(new Runnable() {
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

    private void initExitButton() {
        FancyButton exit_Button = findViewById(R.id.detailedPositionActivity_button_exit);
        exit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.prePage(DetailedPositionMessageActivity.this);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.prePage(DetailedPositionMessageActivity.this);
    }

    private void initCollectionButton() {
        final FancyButton collection_Button = findViewById(R.id.detailedPositionActivity_button_collection);
        if(!Utils.getBoolean(Constant.ISCOLLECTION,false)){
            //未收藏的逻辑
            //白心五角星
            collection_Button.setIconResource("\uf005");
            collection_Button.setIconColor(Color.parseColor("#ffffff"));
        }else {
            //已收藏的逻辑
            //黑心五角星
            collection_Button.setIconResource("\uf005");
            collection_Button.setIconColor(Color.parseColor("#000000"));
        }

        collection_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.getBoolean(Constant.ISCOLLECTION,false)){
                    //未收藏的逻辑
                    Utils.putBoolean(Constant.ISCOLLECTION,true);
                    goodView.setText("收藏成功");
                    goodView.show(v);
                    //黑心五角星
                    collection_Button.setIconResource("\uf005");
                    collection_Button.setIconColor(Color.parseColor("#000000"));
                }else {
                    //已收藏的逻辑
                    Utils.putBoolean(Constant.ISCOLLECTION,false);
                    goodView.setText("取消收藏");
                    goodView.show(v);
                    //白心五角星
                    collection_Button.setIconResource("\uf005");
                    collection_Button.setIconColor(Color.parseColor("#ffffff"));

                }


            }
        });

    }
}
