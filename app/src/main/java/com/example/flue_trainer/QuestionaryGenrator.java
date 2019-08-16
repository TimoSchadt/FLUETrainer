package com.example.flue_trainer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by timo on 26.12.17.
 */

public class QuestionaryGenrator {
    private LinkedList<Question> allQuestions;
    private LinkedList<QuestionCategory> choosenCategorys;
    int numberOfQuestions;

    private static QuestionaryGenrator instance;


    public QuestionaryGenrator() {
        allQuestions = new LinkedList<Question>();
        choosenCategorys  = new LinkedList<QuestionCategory>();
        numberOfQuestions = 0;
    }

    public QuestionaryGenrator(String settingsString) {
        this();
        UpdateSettings(settingsString);
    }


    public void UpdateSettings(String settingsString){
        String parts[] = settingsString.split(";");
        numberOfQuestions = Integer.parseInt(parts[0]);

        RemoveAllCategorys();

        for (int i = 1; i < parts.length; i++) {
            AddCategory(QuestionCategory.valueOf(parts[i]));
        }
    }

    public void AddQuestion(Question question){
        allQuestions.add(question);
    }

    public LinkedList<QuestionCategory> GetChoosenCategorys(){
        return choosenCategorys;
    }

    public void AddCategory(QuestionCategory category){
        if (! choosenCategorys.contains(category)) {
            choosenCategorys.add(category);
        }
    }

    public void RemoveCategory(QuestionCategory category){
        if(choosenCategorys.contains(category)) {
            choosenCategorys.remove(category);
        }
    }

    public void RemoveAllCategorys(){
        while(choosenCategorys.size()>0){
            choosenCategorys.removeFirst();
        }
    }

    public int GetNumberofChoosenCategorys(){
        return choosenCategorys.size();
    }

    public int GetNumberOfQuestions(){
        return numberOfQuestions;
    }

    public void SetNumberOfQuestions(int numberOfQuestions){
        if (allQuestions.size() >= numberOfQuestions){
            this.numberOfQuestions = numberOfQuestions;
        }else{
            this.numberOfQuestions = allQuestions.size();
        }
    }

    private Question GetLeastRelevantQuestion(LinkedList<Question> questions){
        Question leastRelevantQuestion = questions.get(0);
        for (int i=1; i<questions.size(); i++){
            if (questions.get(i).GetRelevance() < leastRelevantQuestion.GetRelevance()){
                leastRelevantQuestion = questions.get(i);
            }
        }

        return leastRelevantQuestion;
    }

    private void AddQuestionToQuestionary(LinkedList<Question> questions, Question question){
        questions.add(question);
        if(questions.size() > numberOfQuestions){
            questions.remove(GetLeastRelevantQuestion(questions));
        }
    }

    public Questionary GetNewQuestionary(){
        LinkedList<Question> choosenQuestions = new LinkedList<Question>();

        if (choosenCategorys.size() == 0){
            choosenCategorys.add(QuestionCategory.Atemschutz);
        }
        for(int i=0; i<allQuestions.size(); i++){
            Question actQuestion = allQuestions.get(i);
            if(choosenCategorys.contains(actQuestion.GetQuestionCategory())){
                AddQuestionToQuestionary(choosenQuestions, actQuestion);
            }
        }

        Collections.shuffle(choosenQuestions);

        return new Questionary(choosenQuestions);
    }

    public String GetCategoryListString(){
        String categoryListString;
        if(choosenCategorys.size()>0){
            categoryListString = choosenCategorys.get(0).name();
            for (int i=1; i<choosenCategorys.size(); i++){
                categoryListString = categoryListString + ";" + choosenCategorys.get(i).name() ;
            }
        }else{
            categoryListString = "";
        }
        return categoryListString;
    }

    public String GetSettingsString(){
        String settingsString = "";
        settingsString = settingsString + numberOfQuestions + ";";
        settingsString = settingsString + GetCategoryListString();

        return settingsString;
    }

    public void UpdateQuestioninCataloge(Question question){
        for (int i=0; i<allQuestions.size();i++){
            Question actQuestion = allQuestions.get(i);
            if(question.GetQuestionText().equals(actQuestion.GetQuestionText())){
                //actQuestion.
            }
        }
    }

    public static synchronized QuestionaryGenrator getInstance(){
        if(instance==null){
            instance=new QuestionaryGenrator();
        }
        return instance;
    }


}
