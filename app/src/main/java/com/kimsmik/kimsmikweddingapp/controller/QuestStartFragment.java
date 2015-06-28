package com.kimsmik.kimsmikweddingapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kimsmik.kimsmikweddingapp.IMenuFragment;
import com.kimsmik.kimsmikweddingapp.R;


public class QuestStartFragment extends Fragment implements IMenuFragment {
    private static String PREF_STAGE_CLEAR = "quiz_stage_clear";
    private QuizStartListener startListener = null;
    private ViewGroup uiContainer = null;
    private ViewGroup fragContainer = null;
    private ImageView clearView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quest_start, container, false);

        clearView = (ImageView)root.findViewById(R.id.clearView);
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(pref.getBoolean(PREF_STAGE_CLEAR,false)){
            clearView.setVisibility(View.VISIBLE);
        }else{
            clearView.setVisibility(View.INVISIBLE);
        }

        uiContainer = (ViewGroup)root.findViewById(R.id.uiContainer);
        fragContainer = (ViewGroup)root.findViewById(R.id.fragContainer);

        ImageView beginBtn = (ImageView)root.findViewById(R.id.beginBtn);
        beginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiContainer.setVisibility(View.INVISIBLE);
                final QuestionFragment quesFrag = new QuestionFragment();
                quesFrag.SetOnQuizFinishListener(new QuestionFragment.QuizFinishListener() {
                    @Override
                    public void OnFinish(boolean clear) {
                        uiContainer.setVisibility(View.VISIBLE);
                        getActivity().getSupportFragmentManager().beginTransaction().detach(quesFrag).commit();
                        if(clear){
                            SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            pref.edit().putBoolean(PREF_STAGE_CLEAR,true).commit();
                            clearView.setVisibility(View.VISIBLE);
                        }
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, quesFrag).commit();

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

    @Override
    public String GetTitle() {
        return "有獎問答";
    }
}
