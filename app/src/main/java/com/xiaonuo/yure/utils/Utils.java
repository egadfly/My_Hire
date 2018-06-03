package com.xiaonuo.yure.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.xiaonuo.yure.R;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Utils {

    private static SharedPreferences sp;


    /**
     * @param value the value will be show by Toast
     */
    public static void show(String value) {
        Toast.makeText(MyApplication.getContext(), value, Toast.LENGTH_SHORT).show();
    }

    public static boolean cleanSp() {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.edit().clear().commit();
    }


    public static void nextPage(Activity activity){
        activity.overridePendingTransition(R.anim.next_in,R.anim.next_out);
    }


    public static void prePage(Activity activity){
        activity.overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }


    /**
     * @param key   键
     * @param value 值
     * @return true or false
     */
    public static boolean putString(String key, String value) {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.edit().putString(key, value).commit();
    }


    /**
     * @param key          键
     * @param defaultValue 默认值
     * @return 取出值
     */
    public static String getString(String key, String defaultValue) {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.getString(key, defaultValue);
    }

    /**
     * @param key   键
     * @param bool 值
     * @return true or false
     */
    public static boolean putBoolean(String key, boolean bool) {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.edit().putBoolean(key, bool).commit();
    }


    /**
     * @param key          键
     * @param defaultBool 默认值
     * @return 取出值
     */
    public static boolean getBoolean(String key, boolean defaultBool) {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.getBoolean(key, defaultBool);
    }

}
