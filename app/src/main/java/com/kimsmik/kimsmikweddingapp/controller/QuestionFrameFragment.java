package com.kimsmik.kimsmikweddingapp.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimsmik.kimsmikweddingapp.IMenuFragment;
import com.kimsmik.kimsmikweddingapp.R;

public class QuestionFrameFragment extends Fragment{
    private FragmentActivity act;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        act = (FragmentActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_question_frame, container, false);
        if(act != null) {
            QuestStartFragment startFragment = (QuestStartFragment) act.getSupportFragmentManager().findFragmentById(R.id.startFrag);
//            if(startFragment != null){
//                startFragment.SetOnQuizStartListener(new QuestStartFragment.QuizStartListener() {
//                @Override
//                public void OnStart() {
//                    if (act != null) {
//                        act.getSupportFragmentManager().beginTransaction().replace(R.id.startFrag, new QuestionFragment()).commit();
//                    }
//                }
//                });
//            }
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
