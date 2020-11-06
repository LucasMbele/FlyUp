package com.example.flyup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

public class EndGame extends Fragment {
    private Button restart_btn , exit_btn;
    private TextView result;
    private int mScore;

    public int getResult() {
        return mScore;
    }

    public void setResult(int result) {
        this.mScore = result;
    }

    public EndGame(int result) {
        setResult(result);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.end_game,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restart_btn = view.findViewById(R.id.restart_btn);
        exit_btn = view.findViewById(R.id.exit_play);
        result = view.findViewById(R.id.result_score);
        //We call again PlayFragment
        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayFragment playFragment = new PlayFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.container_fragment,playFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
            }
        });

        //Otherwise End definitively
        exit_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               //Welcome Back to our Main Activity
               getActivity().finish();
               Intent intent = new Intent(getActivity(),MainActivity.class);
               intent.putExtra("Score", getResult());
               getActivity().setResult(RESULT_OK,intent);
               startActivity(intent);
           }
        });
        result.setText(String.valueOf(getResult()));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
