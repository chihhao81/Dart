package com.example.chihhao.darts.tool;

import com.example.chihhao.darts.App;

/**
 * Created by chihhao on 2017/7/13.
 */

public class ToolBox {

    public static int dp2px(int dp){
        return (int) (dp * App.context.getResources().getDisplayMetrics().density);
    }
}
