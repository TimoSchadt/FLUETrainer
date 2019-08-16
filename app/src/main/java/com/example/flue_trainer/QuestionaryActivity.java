package com.example.flue_trainer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class QuestionaryActivity extends AppCompatActivity implements View.OnClickListener{

    private Questionary questionary;
    private QuestionaryGenrator questionaryGenrator;


    private TextView txtViewQuestionText;
    private TextView textViewProgress;
    private TextView textViewErrors;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private ProgressBar questionaryProgressBar;
    private ImageView questionImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);

        questionaryGenrator = QuestionaryGenrator.getInstance();


        questionImage = findViewById(R.id.imageView);

        txtViewQuestionText = findViewById(R.id.questionText);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewErrors = findViewById(R.id.textViewErrors);

        btnAnswer1 = findViewById(R.id.btnAnswer1);
        btnAnswer1.setOnClickListener(this);

        btnAnswer2 = findViewById(R.id.btnAnswer2);
        btnAnswer2.setOnClickListener(this);

        btnAnswer3 = findViewById(R.id.btnAnswer3);
        btnAnswer3.setOnClickListener(this);

        questionaryProgressBar = findViewById(R.id.questionaryProgressBar);

        ReadQuestionary();

        questionaryProgressBar.setMax(questionary.GetNumberOfQuestions());

        ShowNextQuestion();
    }

    private void ReadQuestionary(){
        String FILENAME = "actQuestionary.txt";

        FileInputStream fis;
        try {
            fis = openFileInput(FILENAME);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];

            int n = fis.read(buffer);
            while (n !=-1)
            {
                fileContent.append(new String(buffer, 0, n));
                n = fis.read(buffer);
            }
            questionary = new Questionary(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteQuestionary(Questionary questionary){
        String FILENAME = "actQuestionary.txt";
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            try {
                fos.write(questionary.GetSerializedQuestionary().getBytes());
                fos.close();
            } finally {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowNextQuestion(){
        WriteQuestionary(questionary);

        Question question = questionary.GetNextQuestion();

        txtViewQuestionText.setText(question.GetQuestionText());
        btnAnswer1.setText(question.GetAnswerText(QuestionAnswer.A));
        btnAnswer2.setText(question.GetAnswerText(QuestionAnswer.B));
        btnAnswer3.setText(question.GetAnswerText(QuestionAnswer.C));
        int actQuestionIndex = questionary.GetNumberOfQuestions() - questionary.GetNumberOfRemainingQuestions();
        questionaryProgressBar.setProgress(actQuestionIndex - 1);
        String progressString = "Frage " + actQuestionIndex + "/" + questionary.GetNumberOfQuestions();
        textViewProgress.setText(progressString);
        String errorString = "Fehler: " + questionary.GetNumberOfWrongQuestions() + "/" + questionary.GetNumberOfQuestions();
        textViewErrors.setText(errorString);

        questionImage.setImageResource(question.GetImageResId());
    }

    @Override
    public void onClick(View v) {
        if (v == btnAnswer1){
            CheckAnswer(QuestionAnswer.A);
        }

        if (v == btnAnswer2){
            CheckAnswer(QuestionAnswer.B);
        }

        if (v == btnAnswer3){
            CheckAnswer(QuestionAnswer.C);
        }

    }

    public void CheckAnswer (QuestionAnswer answer){
        boolean answerCorrect = questionary.CheckActQuestion(answer);
        String errorString = "Fehler: " + questionary.GetNumberOfWrongQuestions() + "/" + questionary.GetNumberOfQuestions();
        textViewErrors.setText(errorString);

        if (questionary.isShowErrorsDirectly()){
            if(!answerCorrect){
                Toast.makeText(this, "Leider falsch!",Toast.LENGTH_SHORT).show();
            }else{
                MoveToNextQuestion();
            }
        }else{
            MoveToNextQuestion();
        }
    }

    void MoveToNextQuestion(){
        questionaryGenrator.UpdateQuestioninCataloge(questionary.GetActQuestion());
        if(questionary.GetNumberOfRemainingQuestions() > 0){
            ShowNextQuestion();
        }else{
            finishQuestionary();
        }
    }

    public void finishQuestionary(){
        WriteQuestionary(questionary);
        finish();
    }
}
