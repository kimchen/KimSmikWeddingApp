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

public class QuestionFrameFragment extends Fragment implements IMenuFragment {
    private static QuestStartFragment startFrg = null;
    private static QuestionFragment questFrg = null;

    private FragmentActivity act;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(startFrg == null){
//            startFrg = new QuestStartFragment();
//            startFrg.SetOnQuizStartListener(new QuestStartFragment.QuizStartListener() {
//                @Override
//                public void OnStart() {
//                    if (act != null) {
//                        act.getSupportFragmentManager().beginTransaction().replace(R.id.container, questFrg).commit();
//                    }
//                }
//            });
//        }
//        if(questFrg == null){
//            questFrg = new QuestionFragment();
//        }
//        if(act != null){
//            act.getSupportFragmentManager().beginTransaction().replace(R.id.container, startFrg).commit();
//        }
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

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public String GetTitle() {
        return "有獎問答";
    }
}
