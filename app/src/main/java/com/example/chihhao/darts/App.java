package com.example.chihhao.darts;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by chihhao on 2017/7/11.
 */

public class App  extends Application {

    public static Context context;
    public static int screenWidth, screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }
}