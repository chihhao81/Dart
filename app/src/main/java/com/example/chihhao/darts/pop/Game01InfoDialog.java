package com.example.chihhao.darts.pop;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chihhao.darts.App;
import com.example.chihhao.darts.R;

/**
 * Created by chihhao on 2017/7/14.
 */

public class Game01InfoDialog extends Dialog{

    private View layout;
    private TextView textView;

    public Game01InfoDialog(@NonNull Context context) {
        super(context, R.style.dialog);

        layout = LayoutInflater.from(context).inflate(R.layout.dialog_info_01, null);
        textView = (TextView)layout.findViewById(R.id.textView);

        ScrollView scrollView = (ScrollView)layout.findViewById(R.id.scrollView);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(App.screenWidth*8/10, App.screenHeight*8/10));

        setContentView(layout);
    }

    public Game01InfoDialog setText(String text){
        textView.setText(text);
        return this;
    }
}
