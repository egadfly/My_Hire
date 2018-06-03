package com.xiaonuo.yure.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xiaonuo.yure.R;
import com.xiaonuo.yure.utils.Utils;

/**
 * Created by Administrator on 2018/2/3.
 */

public class AllPostFragment extends Fragment {

    private View layout;
    private Context context;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = View.inflate(getActivity().getApplicationContext(), R.layout.fragment_homepage_havedata_allpost, null);
        context = getActivity().getApplicationContext();
        activity = getActivity();
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView_postMessage = layout.findViewById(R.id.allPostFragment_listView_postMessage);

        listView_postMessage.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return position+"";
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = View.inflate(context, R.layout.item_allpost_listview, null);
                inflate.setBackgroundResource(R.drawable.listview_background);
                LinearLayout button_go = inflate.findViewById(R.id.allPostFragment_button_go);
                button_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(context,DetailedPositionMessageActivity.class));
                        Utils.nextPage(activity);
                    }
                });
                return inflate;
            }
        });


    }
}
