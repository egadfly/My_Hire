package com.xiaonuo.yure.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.a520wcf.yllistview.YLListView;
import com.xiaonuo.yure.R;
import com.xiaonuo.yure.home.DetailedPositionMessageActivity;
import com.xiaonuo.yure.utils.Constant;
import com.xiaonuo.yure.utils.MyApplication;
import com.xiaonuo.yure.utils.Utils;

/**
 * Created by Administrator on 2018/2/10.
 */

public class Mine_Collection extends AppCompatActivity {

    private MyBaseAdapter myBaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection);

        initListView();

        initExitButton();

    }

    private void initExitButton() {
        findViewById(R.id.mine_CollectionActivity_button_exit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Utils.prePage(Mine_Collection.this);
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.prePage(Mine_Collection.this);
    }

    private void initListView() {
        final YLListView listView =  findViewById(R.id.listView);
        // 不添加也有默认的头和底
        View topView=View.inflate(this,R.layout.item_listview_top,null);
        listView.addHeaderView(topView);

        View bottomView=new View(getApplicationContext());
        listView.addFooterView(bottomView);

        // 顶部和底部也可以固定最终的高度 不固定就使用布局本身的高度
        listView.setFinalBottomHeight(100);
        listView.setFinalTopHeight(100);

        myBaseAdapter = new MyBaseAdapter();
        listView.setAdapter(myBaseAdapter);

        //YLListView默认有头和底  处理点击事件位置注意减去
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                position=position-listView.getHeaderViewsCount();



            }
        });
    }

    private class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            boolean isCollection = Utils.getBoolean(Constant.ISCOLLECTION, false);
            if(isCollection){
                ImageView iv= Mine_Collection.this.findViewById(R.id.mineactivity_imageview);
                iv.setVisibility(View.GONE);
                return 1;
            }else {
                ImageView iv= Mine_Collection.this.findViewById(R.id.mineactivity_imageview);
                iv.setVisibility(View.VISIBLE);
                return 0;
            }
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
            View inflate = View.inflate(MyApplication.getContext(), R.layout.item_allpost_listview, null);
            inflate.setBackgroundResource(R.drawable.listview_background);
            LinearLayout button_go = inflate.findViewById(R.id.allPostFragment_button_go);
            button_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyApplication.getContext(),DetailedPositionMessageActivity.class));
                    Utils.nextPage(Mine_Collection.this);
                }
            });
            return inflate;
        }
    }

    @Override
    protected void onResume() {
        if(myBaseAdapter!=null){
            myBaseAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }
}
