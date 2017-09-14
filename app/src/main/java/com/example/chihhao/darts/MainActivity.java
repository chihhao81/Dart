package com.example.chihhao.darts;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.chihhao.darts.fragment.Game01Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;

    private TextView btnGame01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        try{getWindow().setFlags(16777216, 16777216);} catch (Exception e){}
        fragmentManager = getSupportFragmentManager();

        btnGame01 = (TextView)findViewById(R.id.btnGame01);

        btnGame01.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() == 0)
            super.onBackPressed();
        else
            fragmentManager.popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGame01:
                fragmentManager.beginTransaction().replace(R.id.flContains, new Game01Fragment()).addToBackStack("").commitAllowingStateLoss();
                break;
        }
    }
}