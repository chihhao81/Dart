package com.example.chihhao.darts.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by chihhao on 2017/7/13.
 */

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity activity;

    protected View layout;

    abstract protected void buildView();

    abstract public boolean onBackPressed();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity)context;
    }

    protected View findViewById(int id){
        return layout.findViewById(id);
    }
}
