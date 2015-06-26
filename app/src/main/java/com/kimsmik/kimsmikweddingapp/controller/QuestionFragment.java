package com.kimsmik.kimsmikweddingapp.controller;


import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.kimsmik.kimsmikweddingapp.IMenuFragment;
import com.kimsmik.kimsmikweddingapp.R;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment implements IMenuFragment {
    public class Question{
        String quest = "";
        List<String> option = new ArrayList<>();
        String answer = "";
    }

    private List<Question> questions = new ArrayList<>();
    private RadioGroup optionGroup;
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

        optionGroup = (RadioGroup)(view.findViewById(R.id.optionGroup));
        questText = (TextView)(view.findViewById(R.id.questText));
        progressText = (TextView)(view.findViewById(R.id.progressText));

        Button confirmBtn = (Button)(view.findViewById(R.id.comfirmBtn));
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAns()) {
                    SetNextQuestion();
                }
            }
        });

        SetNextQuestion();
        return view;
    }

    private boolean CheckAns(){
        RadioButton checkedBtn = (RadioButton)getView().findViewById(optionGroup.getCheckedRadioButtonId());
        if(checkedBtn == null)
            return false;
        Question quest = questions.get(nowQuestIndex);
        if(checkedBtn.getText().equals(quest.answer))
            correctNum++;
        return true;
    }
    private void SetNextQuestion(){
        if(nowQuestIndex >= questions.size()){
            if(finishListener != null)
                finishListener.OnFinish(correctNum,questions.size());
        }else{
            optionGroup.clearCheck();
            Question quest = questions.get(nowQuestIndex);
            questText.setText(quest.quest);
            for(int i=0; i<quest.option.size(); i++){
                RadioButton radioBtn =  (RadioButton)optionGroup.getChildAt(i);
                radioBtn.setText(quest.option.get(i));
            }
            nowQuestIndex++;
            progressText.setText(nowQuestIndex + "/" + questions.size());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public String GetTitle() {
        return "有獎問答";
    }

    public interface QuizFinishListener{
        void OnFinish(int correctNum, int totalNum);
    }
}
