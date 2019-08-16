package com.example.flue_trainer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.*;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FlueTrainer extends AppCompatActivity implements OnClickListener, SeekBar.OnSeekBarChangeListener{
    private QuestionaryGenrator questionaryGenrator;

    private Button btnCategorys;
    private Button btnStart;

    private ToggleButton toggleButtonErros;

    private TextView textViewNumberOfQuestions;
    private SeekBar seekBarNumberOfQuestions;



    private void initializeQuestionarySpinner(){
        btnCategorys = findViewById(R.id.chooseCategoryButton);
        btnCategorys.setOnClickListener(this);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        toggleButtonErros = findViewById(R.id.toggleButtonErrors);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flue_trainer);

        initializeQuestionarySpinner();

        questionaryGenrator = QuestionaryGenrator.getInstance();

        textViewNumberOfQuestions = findViewById(R.id.textViewNumberOfQuestions);
        seekBarNumberOfQuestions = findViewById(R.id.seekBarNumberOfQuestions);
        seekBarNumberOfQuestions.setOnSeekBarChangeListener(this);
        seekBarNumberOfQuestions.setMax(30);
        seekBarNumberOfQuestions.setProgress(15);
        questionaryGenrator.SetNumberOfQuestions(15);

        ReadSettings();
        ReadQuestions();

        try {
            Questionary questionary = ReadQuestionary();

            if (questionary != null && questionary.GetNumberOfRemainingQuestions() > 0) {
                WriteQuestionary(questionary);
                Intent intent = new Intent(this, QuestionaryActivity.class);
                startActivity(intent);
            }
        }catch (Exception e){

        }


    }

    private void ReadQuestions(){
        InputStream inputStream = getResources().openRawResource(R.raw.questions);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;

        try {
            while((line = bufferedReader.readLine()) != null){
                Question newQuestion = new Question(line);
                questionaryGenrator.AddQuestion(newQuestion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void ReadSettings(){
        String FILENAME = "settings.txt";

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
            questionaryGenrator.UpdateSettings(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteSettings(){
        String FILENAME = "settings.txt";
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            try {
                fos.write(questionaryGenrator.GetSettingsString().getBytes());
                fos.close();
            } finally {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v){

        //Unterscheidung, Auslöser des Ereignis.
        if(v == btnCategorys){
            ReadSettings();
            WriteSettings();

            Intent intent = new Intent(this, ChooseCategorysActivity.class);
            startActivity(intent);
        }

        if (v == btnStart){
            ReadSettings();
            int numberOfQuestions = seekBarNumberOfQuestions.getProgress() + 1;

            if(numberOfQuestions > 30){
                numberOfQuestions = Integer.MAX_VALUE;
            }
            questionaryGenrator.SetNumberOfQuestions(numberOfQuestions);
            WriteSettings();
            Questionary questionary = questionaryGenrator.GetNewQuestionary();
            questionary.setShowErrorsDirectly(toggleButtonErros.isChecked());
            WriteQuestionary(questionary);
            Intent intent = new Intent(this, QuestionaryActivity.class);
            startActivity(intent);
        }

    }
    private Questionary ReadQuestionary(){
        String FILENAME = "actQuestionary.txt";
        Questionary questionary = null;

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
            return null;
        }

        return questionary;
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int numberOfQuestions = progress + 1;

        if(numberOfQuestions > 30){
            numberOfQuestions = Integer.MAX_VALUE;
        }

        if (questionaryGenrator != null){
            questionaryGenrator.SetNumberOfQuestions(numberOfQuestions);
        }


        String numberOfQuestionsString =  "Anzahl der Fragen: ";
        if (numberOfQuestions == Integer.MAX_VALUE){
            numberOfQuestionsString = numberOfQuestionsString + "Alle";
        }else{
            numberOfQuestionsString = numberOfQuestionsString + numberOfQuestions;
        }
        textViewNumberOfQuestions.setText(numberOfQuestionsString);


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}