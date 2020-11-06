package com.example.flyup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Timer;
import java.util.TimerTask;

public class PlayFragment extends Fragment
{
    private static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE" ;
    private FlyingBirdView mFlyingBirdView;
    private int mScore = 0;
    private Handler mHandler;
    private Timer timer;
    public static final long Time = 30;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlyingBirdView = new FlyingBirdView(getActivity());
        timer = new Timer();
        mHandler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         //return super.onCreateView(inflater, container, savedInstanceState);
         timer.schedule(new TimerTask() {
             @Override
             public void run() {
                 mFlyingBirdView.post(new Runnable() {
                     @Override
                     public void run() {
                         //Force to draw the view
                         mFlyingBirdView.invalidate();
                         Log.i("life_count_play_frag", "Life count is :"+ mFlyingBirdView.getLife_count());
                         if (mFlyingBirdView.getLife_count() == 0)
                         {
                             EndGame endgame = new EndGame( mFlyingBirdView.getScore());
                             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                             fragmentTransaction.replace(R.id.container_fragment,endgame);
                             fragmentTransaction.addToBackStack(null);
                             fragmentTransaction.commit();
                         }
                     }
                 });
             }
         },0,Time);
        //return view;
        return mFlyingBirdView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


    @Override
    public void onStart() {
        super.onStart();

    }
}
