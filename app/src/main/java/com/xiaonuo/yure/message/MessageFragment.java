package com.xiaonuo.yure.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taobao.library.BaseBannerAdapter;
import com.taobao.library.VerticalBannerView;
import com.wang.avi.AVLoadingIndicatorView;
import com.xiaonuo.yure.R;
import com.xiaonuo.yure.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MessageFragment extends Fragment {

    private View layout;
    private Context context;
    private Activity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getActivity().getApplicationContext(), R.layout.fragment_message, null);
        context = getActivity().getApplicationContext();
        activity = getActivity();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //滚动广告
        initVerticalAdvertisement();

        //发布
        initPostMessageButton();

        //消息
        initOpenMessageButton();
    }

    private void initOpenMessageButton() {
        layout.findViewById(R.id.messageFragment_button_openMessage)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.show("No Message");
                    }
                });
    }

    private void initPostMessageButton() {
        layout.findViewById(R.id.messageFragment_button_postMessage)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(context,SelectPostType.class));
                        Utils.nextPage(activity);
                    }
                });
    }


    private void initVerticalAdvertisement() {
        List<Model> datas03 = new ArrayList<>();
        datas03.add(new Model("欢迎您的到来","New"));
        datas03.add(new Model("苹果手机APP试玩在家随时可做","Hot"));
        final SampleAdapter03 adapter03 = new SampleAdapter03(datas03);
        final VerticalBannerView banner03 =  layout.findViewById(R.id.banner_03);
        banner03.setAdapter(adapter03);
        banner03.start();
    }

    private class SampleAdapter03 extends BaseBannerAdapter<Model> {
        private List<Model> mDatas;

        public SampleAdapter03(List<Model> datas) {
            super(datas);
        }

        @Override
        public View getView(VerticalBannerView parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verticalaadvertisement,null);
        }

        @Override
        public void setItem(final View view, final Model data) {
            TextView tv = (TextView) view.findViewById(R.id.title);
            tv.setText(data.title);

            TextView tag = (TextView) view.findViewById(R.id.tag);
            tag.setText(data.url);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018/2/10

                    final AVLoadingIndicatorView loadingView = activity.findViewById(R.id.messageFragment_loadingView);
                    loadingView.smoothToShow();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            activity.runOnUiThread(new Runnable() {
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
    }

    private class Model {
        public String title;
        public String url;

        public Model(String title, String url) {
            this.title = title;
            this.url = url;
        }
    }
}
