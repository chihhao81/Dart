package com.example.chihhao.darts.pop;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chihhao.darts.App;
import com.example.chihhao.darts.R;
import com.example.chihhao.darts.tool.ToolBox;

/**
 * Created by chihhao on 2017/7/13.
 */

public class Setting01Dialog extends Dialog{

    private final int WIDTH = App.screenWidth*8/10;
    private final int[] SCORES = new int[]{301, 501, 701, 901, 1101, 1501};

    private Context context;

    private View layout;
    private CheckBox checkBox;
    private GridLayout gridLayout;

    private int score = 0;

    public Setting01Dialog(@NonNull Context context) {
        super(context, R.style.dialog);

        this.context = context;

        layout = LayoutInflater.from(context).inflate(R.layout.dialog_setting_01, null);

        checkBox = (CheckBox)layout.findViewById(R.id.checkbox);
        gridLayout = (GridLayout)layout.findViewById(R.id.gridLayout);

        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));

        addButtons();

        setContentView(layout);
    }

    private void addButtons(){
        int padding = ToolBox.dp2px(5);
        int margins = -1;
        for(int i = 0; i < SCORES.length; i++){
            String s = ""+SCORES[i];
            TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_stay_time_textview, null);
            textView.setId(i);
            textView.setText(s);
            textView.setSelected(i == 0); // 預設第一個
            textView.setTextSize(15);
            textView.setPadding(padding*2, padding, padding*2, padding);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if(id != score) {
                        gridLayout.getChildAt(score).setSelected(false);
                        v.setSelected(true);
                        score = id;
                    }
                }
            });

            if(margins == -1) {
                textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int width = textView.getMeasuredWidth();
                margins = (WIDTH - width * 3) / 6;
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(margins, padding, margins, padding);
            textView.setLayoutParams(params);
            gridLayout.addView(textView);
        }
    }

    public boolean isSoft(){
        return checkBox.isChecked();
    }

    public int getScore(){
        return SCORES[score];
    }
}
