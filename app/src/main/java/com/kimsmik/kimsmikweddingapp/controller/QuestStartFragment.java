package com.kimsmik.kimsmikweddingapp.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kimsmik.kimsmikweddingapp.R;


public class QuestStartFragment extends Fragment {
    private static String PREF_STAGE_CLEAR = "quiz_stage_clear";
    private QuizStartListener startListener = null;
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
        View root = inflater.inflate(R.layout.fragment_quest_start, container, false);

        ImageView clearView = (ImageView)root.findViewById(R.id.clearView);
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(pref.getBoolean(PREF_STAGE_CLEAR,false)){
            clearView.setVisibility(View.VISIBLE);
        }else{
            clearView.setVisibility(View.INVISIBLE);
        }

        ImageView beginBtn = (ImageView)root.findViewById(R.id.beginBtn);
        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act != null) {
                        act.getSupportFragmentManager().beginTransaction().replace(R.id.container, new QuestionFragment()).commit();
                    }
//                if(startListener != null) {
//                    startListener.OnStart();
//                }
            }
        });

        return root;
    }
    public void SetOnQuizStartListener(QuizStartListener l){
        startListener = l;
    }
    public interface QuizStartListener{
        void OnStart();
    }
}
