package com.xiaonuo.yure.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaonuo.yure.R;
import com.xiaonuo.yure.utils.MyApplication;
import com.xiaonuo.yure.utils.Utils;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2018/2/3.
 */

public class MineFragment extends Fragment {

    private View layout;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getActivity().getApplicationContext(), R.layout.fragment_mine, null);
        activity = getActivity();
        return layout;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FancyButton button_collection = layout.findViewById(R.id.mineFragment_button_collection);
        button_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyApplication.getContext(),Mine_Collection.class));
                Utils.nextPage(activity);
            }
        });
    }
}
