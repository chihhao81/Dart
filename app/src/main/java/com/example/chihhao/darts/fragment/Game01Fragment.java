package com.example.chihhao.darts.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chihhao.darts.DartsView;
import com.example.chihhao.darts.R;
import com.example.chihhao.darts.adapter.Game01Adapter;
import com.example.chihhao.darts.pop.Game01InfoDialog;
import com.example.chihhao.darts.pop.Setting01Dialog;
import com.example.chihhao.darts.tool.Game01Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chihhao on 2017/7/13.
 */

public class Game01Fragment extends BaseFragment{

    private TextView tvGoal, tvRound, tvScore;
    private DartsView dartsView;
    private RecyclerView recyclerView;

    private Setting01Dialog setting01Dialog;
    private Game01Adapter adapter;
    private List<Game01Item> list = new ArrayList<>();

    private int round = 20;
    private int goal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(layout == null) {
            layout = LayoutInflater.from(activity).inflate(R.layout.fragment_game_01, container, false);
            buildView();
        }
        return layout;
    }

    @Override
    protected void buildView() {
        tvGoal = (TextView)findViewById(R.id.tvGoal);
        tvRound = (TextView)findViewById(R.id.tvRound);
        tvScore = (TextView)findViewById(R.id.tvScore);
        dartsView = (DartsView)findViewById(R.id.dartsView);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        adapter = new Game01Adapter(activity);
        adapter.setList(list);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 5));
        recyclerView.setAdapter(adapter);

        setting01Dialog = new Setting01Dialog(activity);
        setting01Dialog.show();
        setting01Dialog.setOnDismissListener(onDismissListener);

        dartsView.setOnScoreListener(onScoreListener);
    }

    private void analysis(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(Game01Item item : list) {
            String key = item.scoreText;
            if(hashMap.containsKey(key))
                hashMap.put(key, hashMap.get(key)+1);
            else
                hashMap.put(key, 1);
        }

        List<Game01Item> list = new ArrayList<>();
        for(String key : hashMap.keySet())
            list.add(new Game01Item(hashMap.get(key), key));

        Collections.sort(list, new Comparator<Game01Item>() {
            @Override
            public int compare(Game01Item o1, Game01Item o2) {
                return o2.score - o1.score;
            }
        });

        String text = "";
        for(Game01Item item : list)
            text += item.scoreText + "  " + (String.format("%.1f", item.score*100f/this.list.size())) + "%\n";

        new Game01InfoDialog(activity).setText(text).show();
    }

    /** dialog消失 */
    private Dialog.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            goal = setting01Dialog.getScore();
            tvGoal.setText(""+goal);
            dartsView.setBullAllDouble(setting01Dialog.isSoft());
            if(goal == 301)
                round = 10;
            else if(goal == 501)
                round = 15;
            tvRound.setText("1/"+round);
        }
    };

    /** 分數點擊 */
    private DartsView.OnScoreListener onScoreListener = new DartsView.OnScoreListener() {
        @Override
        public void onScore(Game01Item item) {
            if(goal < item.score) {
                for(int i = 0; i < list.size()%3; i++)
                    goal += list.get(list.size()-i-1).score;
                list.add(item);
                for(int i = 0; i < list.size()%3; i++)
                    list.add(new Game01Item(0, "-"));
            } else {
                goal -= item.score;
                list.add(item);
                if(goal == 0)
                    analysis();
            }

            tvGoal.setText("" + goal);
            tvRound.setText((list.size() / 3 + 1) + "/" + round);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(adapter.getItemCount()-1);
        }

        @Override
        public void onAim(String scoreText) {
            tvScore.setText(scoreText);
        }
    };

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
