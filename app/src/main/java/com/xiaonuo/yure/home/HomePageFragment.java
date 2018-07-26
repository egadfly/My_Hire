package com.xiaonuo.yure.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.xiaonuo.yure.R;
import com.xiaonuo.yure.ui.vPage.BezierRoundView;
import com.xiaonuo.yure.ui.vPage.BezierViewPager;
import com.xiaonuo.yure.ui.vPage.CardPagerAdapter;
import com.xiaonuo.yure.ui.widget.WaveSwipeRefreshLayout;
import com.xiaonuo.yure.utils.Utils;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/2/3.
 */

public class HomePageFragment extends Fragment {

    private Context context;
    private View layout;
    private static int LOCATION_REQUESTCODE = 123;
    private List<Object> imgList;
    private Activity activity;
    private Timer timer;
    private ViewPager postViewPager;
    private SmartTabLayout postViewPagerTab;
    private ArrayList<String> datas;
    private PopupWindow popupWindow;
    private ListView postSelectListView;
    private Button button_addPage;
    private ArrayList<String> mPostList;
    private MyBaseAdapter mMyPostBaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        activity = getActivity();
        layout = View.inflate(getActivity().getApplicationContext(), R.layout.fragment_homepage, null);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //定位Button的监听
        initFindLocationButton();

        //轮播图的加载
        initViewPagerOfAdvertisement();

        //初始化招聘信息ViewPager
        initRecruitmentViewPager();

        //初始化招聘信息ViewPager旁边的添加按钮
        initButtonOfAddPage();

        //水滴刷新
        initRefresh();

    }

    private void initRefresh() {
        final WaveSwipeRefreshLayout waveSwipeRefreshLayout = layout.findViewById(R.id.homePage_swipe);
        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
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
                                Utils.show("OK");
                                waveSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

    }

    private void initButtonOfAddPage() {

        datas = new ArrayList<>();
        // 创建一些数据
        datas.add("车票代购");
        datas.add("保洁");
        datas.add("挂号排队");
        datas.add("餐饮工");
        datas.add("安保");
        datas.add("充场");
        datas.add("问卷调查");
        datas.add("保姆");
        datas.add("体验员");
        datas.add("宠物照看");
        datas.add("临时物品看管");

        button_addPage = layout.findViewById(R.id.homePage_button_addPage);
        button_addPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScrollView scrollView = layout.findViewById(R.id.homePageFragment_scrollView);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        initListView();

        // 显示下拉选择框
        popupWindow = new PopupWindow(postSelectListView, 200, 400);

        // 设置点击外部区域, 自动隐藏
        popupWindow.setOutsideTouchable(true); // 外部可触摸
        popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件

        popupWindow.setFocusable(true); //设置可获取焦点

        // 显示在指定控件下
        popupWindow.showAsDropDown(button_addPage, 0, 15);
    }


    // 初始化要显示的内容
    private void initListView() {
        postSelectListView = new ListView(context);
        postSelectListView.setDividerHeight(0);
        postSelectListView.setBackgroundResource(R.drawable.listview_background);

        mMyPostBaseAdapter = new MyBaseAdapter();
        postSelectListView.setAdapter(mMyPostBaseAdapter);

        postSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        String post = datas.remove(position);
                        mMyPostBaseAdapter.notifyDataSetChanged();
                        //将数据添加到标签页
                        mPostList.add(post);
                        FragmentPagerItems.Creator creator = FragmentPagerItems.with(context);
                        for (int x = 0; x < mPostList.size(); x++) {
                            creator.add(mPostList.get(x), NoDataFragment.class);
                        }

                        FragmentPagerItems items = creator.create();

                        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                                getFragmentManager(), items);


                        postViewPager.setAdapter(adapter);
                        postViewPagerTab.setViewPager(postViewPager);
                        postViewPager.setCurrentItem(mPostList.size()-1);
                        popupWindow.dismiss(); // 消失了
                    }
                });
            }
        });
    }


    class MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(parent.getContext(), R.layout.item_homepage_recruitment_listview, null);
            } else {
                view = convertView;
            }

            TextView tv_number = (TextView) view.findViewById(R.id.listview_item);
            tv_number.setText(" · "+datas.get(position));
            return view;
        }
    }


    private void initFindLocationButton() {
        layout.findViewById(R.id.homePageFragment_button_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, CityPickerActivity.class),
                        LOCATION_REQUESTCODE);
            }
        });
    }

    private void initViewPagerOfAdvertisement() {
        initImgList();
        int mWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        float heightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例

        CardPagerAdapter cardAdapter = new CardPagerAdapter(context);
        cardAdapter.addImgUrlList(imgList);


        //设置阴影大小，即vPage  左右两个图片相距边框  maxFactor + 0.3*CornerRadius   *2
        //设置阴影大小，即vPage 上下图片相距边框  maxFactor*1.5f + 0.3*CornerRadius
        int maxFactor = mWidth / 25;
        cardAdapter.setMaxElevationFactor(maxFactor);

        int mWidthPading = mWidth / 8;

        //因为我们adapter里的cardView CornerRadius已经写死为10dp，所以0.3*CornerRadius=3
        //设置Elevation之后，控件宽度要减去 (maxFactor + dp2px(3)) * heightRatio
        //heightMore 设置Elevation之后，控件高度 比  控件宽度* heightRatio  多出的部分
        float heightMore = (1.5f * maxFactor + dp2px(3)) - (maxFactor + dp2px(3)) * heightRatio;
        int mHeightPading = (int) (mWidthPading * heightRatio - heightMore);

        final BezierViewPager viewPager = layout.findViewById(R.id.homePageFragment_viewPage_picturePlay);
        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) (mWidth * heightRatio)));
        viewPager.setPadding(mWidthPading, mHeightPading, mWidthPading, mHeightPading);
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(cardAdapter);
        viewPager.showTransformer(0.2f);


        BezierRoundView bezRound = layout.findViewById(R.id.homePageFragment_roundView_bezRound);
        bezRound.attach2ViewPage(viewPager);

        viewPager.setCurrentItem(0);

        //执行无限循环任务
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        if (currentItem == imgList.size() - 1) {
                            viewPager.setCurrentItem(0);
                        } else {
                            viewPager.setCurrentItem(currentItem + 1);
                        }
                    }
                });

            }
        };
        timer.schedule(task, 3000, 3000);
    }

    private void initRecruitmentViewPager() {
        mPostList = new ArrayList<String>();
        mPostList.add("全部职位");
        mPostList.add("传单派发");


        FragmentPagerItems.Creator creator = FragmentPagerItems.with(context);
        for (int x = 0; x < mPostList.size(); x++) {
            if(x==0){
                creator.add(mPostList.get(x), AllPostFragment.class);
            }else {
                creator.add(mPostList.get(x), NoDataFragment.class);
            }
        }

        FragmentPagerItems items = creator.create();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), items);

        postViewPager = (ViewPager) layout.findViewById(R.id.homePage_RecruitmentViewPager);
        postViewPager.setAdapter(adapter);

        postViewPagerTab = (SmartTabLayout) layout.findViewById(R.id.homePage_RecruitmentViewPagerTab);
        postViewPagerTab.setViewPager(postViewPager);


//        int position = FragmentPagerItem.getPosition(getArguments());
        postViewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ScrollView scrollView = layout.findViewById(R.id.homePageFragment_scrollView);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initImgList() {
        imgList = new ArrayList<>();
        imgList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2410028758,1593417090&fm=27&gp=0.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&s" +
                "ec=1532586451980&di=221e5bd267e5f9617590fba8c1d32570&imgtype=0&s" +
                "rc=http%3A%2F%2Fpic1.zhimg.com%2Fv2-ebd5359c735c4d3416b590f33a15fe73_1200x500.jpg");
        imgList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u" +
                "=1456709294,3742664892&fm=27&gp=0.jpg");
        imgList.add("http://img1.gtimg.com/comic/pics/hv1/130/173/2101/136661770.jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //定位返回
        if (requestCode == LOCATION_REQUESTCODE && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
//                Toast.makeText(context, "当前选择：" + city, Toast.LENGTH_LONG).show();
                FancyButton button_location = layout.findViewById(R.id.homePageFragment_button_location);
                button_location.setText(city);
            }
        }
    }

    @Override
    public void onDestroy() {
        //销毁Activity时候取消Viewpager的自动循环任务
        timer.cancel();
        super.onDestroy();
    }
}
