package com.example.chihhao.darts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chihhao.darts.App;
import com.example.chihhao.darts.tool.Game01Item;
import com.example.chihhao.darts.tool.ToolBox;

import java.util.List;

/**
 * Created by chihhao on 2017/7/13.
 */

public class Game01Adapter extends RecyclerView.Adapter<Game01Adapter.ViewHolder> {

    private Context context;

    private List<Game01Item> list;

    public Game01Adapter(Context context){
        this.context = context;
    }

    public void setList(List<Game01Item> list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        int round = list.size() % 3 == 0 ? 0 : 1;
        return (list.size()/3+round) + list.size() + (list.size()/3);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new TextView(context));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position % 5 == 0) {
            holder.textView.setText(""+(position/5+1));
        } else if(position % 5 == 4) {
            position = (position/5 + 1) * 3;
            int score = 0;
            for(int i = 1; i < 4; i++)
                score += list.get(position-i).score;
            holder.textView.setText(""+score);
        } else {
            position -= (position/5 * 2 + 1);
            holder.textView.setText(list.get(position).scoreText);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            int padding = ToolBox.dp2px(5);
            textView = (TextView)itemView;
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            textView.setPadding(padding, padding, padding, padding);
            textView.setLayoutParams(new LinearLayout.LayoutParams(App.screenWidth/5, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
}
