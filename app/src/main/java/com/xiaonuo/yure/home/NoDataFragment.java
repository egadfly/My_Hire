package com.xiaonuo.yure.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wang.avi.AVLoadingIndicatorView;
import com.xiaonuo.yure.R;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2018/2/3.
 */

public class NoDataFragment extends Fragment {

    private View layout;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getActivity().getApplicationContext(), R.layout.fragment_homepage_nodata, null);
        activity = getActivity();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FancyButton button = layout.findViewById(R.id.noDataFragment_refreshButton);
        //刷新
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView imageView = layout.findViewById(R.id.noDataFragment_imageView);
                final AVLoadingIndicatorView loadingView = layout.findViewById(R.id.noDataFragment_loadingView);

                imageView.setVisibility(View.GONE);
                loadingView.smoothToShow();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingView.hide();
                                imageView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();

            }
        });
    }
}
