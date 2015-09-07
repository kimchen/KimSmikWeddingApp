package com.kimsmik.kimsmikweddingapp.controller;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.kimsmik.kimsmikweddingapp.IMenuFragment;
import com.kimsmik.kimsmikweddingapp.R;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment{
    public class Question{
        String quest = "";
        List<String> option = new ArrayList<>();
        String answer = "";
    }

    private List<Question> questions = new ArrayList<>();
    private LinearLayout optionGroup;
    private TextView questText;
    private TextView progressText;
    private int nowQuestIndex = 0;
    private int correctNum = 0;
    private QuizFinishListener finishListener = null;

    public QuestionFragment() {

    }

    public void SetOnQuizFinishListener(QuizFinishListener l){
        finishListener = l;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XmlResourceParser parser = getResources().getXml(R.xml.questions);
        try {
            Question quest = null;
            while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {

                if(parser.getEventType() == XmlResourceParser.START_TAG){
                    if(parser.getName().equals("Quest")){
                        quest = new Question();
                        quest.quest = parser.getAttributeValue(null,"text");
                    }else if(parser.getName().equals("Option")){
                        String text = parser.getAttributeValue(null, "text");
                        quest.option.add(text);
                        boolean isAnswer = parser.getAttributeBooleanValue(null,"answer",false);
                        if(isAnswer)
                            quest.answer = text;
                    }

                }else if(parser.getEventType() == XmlResourceParser.END_TAG){
                    if(parser.getName().equals("Quest")){
                        questions.add(quest);
                    }
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        nowQuestIndex = 0;
        correctNum = 0;
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        optionGroup = (LinearLayout)(view.findViewById(R.id.optionGroup));
        questText = (TextView)(view.findViewById(R.id.questText));
        progressText = (TextView)(view.findViewById(R.id.progressText));

        for(int i=0; i<optionGroup.getChildCount(); i++){
            TextView opt = (TextView)optionGroup.getChildAt(i);
            opt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckAns((TextView)v);
                }
            });
        }

//        Button confirmBtn = (Button)(view.findViewById(R.id.comfirmBtn));
//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(CheckAns()) {
//                    SetNextQuestion();
//                }
//            }
//        });

        SetNextQuestion();
        return view;
    }

    private void CheckAns(TextView tv){
//        RadioButton checkedBtn = (RadioButton)getView().findViewById(optionGroup.getCheckedRadioButtonId());
//        if(checkedBtn == null)
//            return false;
        Question quest = questions.get(nowQuestIndex);
        if(tv.getText().equals(quest.answer))
            correctNum++;
        nowQuestIndex++;
        SetNextQuestion();
        //return true;
    }
    private void SetNextQuestion(){
        if(nowQuestIndex >= questions.size()){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(finishListener != null) {
                        if (correctNum == questions.size())
                            finishListener.OnFinish(true);
                        else
                            finishListener.OnFinish(false);
                    }
                }
            });
            dialogBuilder.setMessage(GetResultString());
            dialogBuilder.setTitle("答題結果");
            dialogBuilder.show();
        }else{
            Question quest = questions.get(nowQuestIndex);
            questText.setText(quest.quest);
            for(int i=0; i<optionGroup.getChildCount(); i++) {
                TextView opt = (TextView) optionGroup.getChildAt(i);
                if(i >= quest.option.size()) {
                    opt.setVisibility(View.INVISIBLE);
                }else{
                    opt.setText(quest.option.get(i));
                    opt.setVisibility(View.VISIBLE);
                }
            }

//            for(int i=0; i<quest.option.size(); i++){
//                RadioButton radioBtn =  (RadioButton)optionGroup.getChildAt(i);
//                radioBtn.setText(quest.option.get(i));
//            }
            progressText.setText((nowQuestIndex + 1) + "/" + questions.size());
        }
    }

    private String GetResultString(){
        String msg = "答對題數 : " + correctNum + "/" + questions.size() + "\n\n";
        float persent = ((float)correctNum)/((float)questions.size());
        if(persent >= 1){
            msg += "恭喜你答對所有題目\n你真是太了解新人了\n婚禮當天請至現場\n出示App內的通關證明\n換取神祕小禮";
        }else if(persent >= 0.8){
            msg += "真是太可惜了\n只差一點點就全部正解了\n請再接再厲";
        }else if(persent >= 0.5){
            msg += "看來你對新人還是一知半解的喔\n但沒關係\n請再多試幾次\n也可更了解新人喔";
        }else{
            msg += "哎呀\n你對新人不是很了解的樣子呢\n這樣子不太行唷\n請多試幾次\n也許你會慢慢了解新人喔";
        }

        return msg;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public interface QuizFinishListener{
        void OnFinish(boolean clear);
    }
}
