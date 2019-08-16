package com.example.flue_trainer;

import java.util.LinkedList;


/**
 * Created by Timo Schadt on 26.12.17.
 */

public class Questionary {
    private LinkedList<Question> questions;
    private LinkedList<Question> wrongQuestions;
    private Question actQuestion;
    private int numberOfQuestions;

    public boolean isShowErrorsDirectly() {
        return showErrorsDirectly;
    }

    public void setShowErrorsDirectly(boolean showErrorsDirectly) {
        this.showErrorsDirectly = showErrorsDirectly;
    }

    boolean showErrorsDirectly;

    public Questionary(){
        questions = new LinkedList<Question>();
        wrongQuestions = new LinkedList<Question>();
        numberOfQuestions = 0;
        showErrorsDirectly = true;
    }

    public Questionary(LinkedList<Question> questions){
        this.questions = questions;
        wrongQuestions = new LinkedList<Question>();
        numberOfQuestions = questions.size();
        showErrorsDirectly = true;
    }

    public Questionary(String serializationString){
        this();
        String lines[] = serializationString.split("\n");
        int lineIndex = 0;
        showErrorsDirectly = true;


        while (lineIndex < lines.length){
            if (lines[lineIndex].contains("<Questions>")){
                lineIndex ++ ;
                while (lineIndex < lines.length && !lines[lineIndex].contains("<\\Questions>")){
                    questions.add(new Question(lines[lineIndex]));
                    lineIndex++;
                }
            }

            if (lines[lineIndex].contains("<WrongQuestions>")){
                lineIndex ++ ;
                while (lineIndex < lines.length && !lines[lineIndex].contains("<\\WrongQuestions>")){
                    wrongQuestions.add(new Question(lines[lineIndex]));
                    lineIndex++;
                }
            }

            if (lines[lineIndex].contains("<NumberOfQuestions>")){
                lineIndex++;
                numberOfQuestions = Integer.parseInt(lines[lineIndex]);
            }

            if (lines[lineIndex].contains("<ShowErrorsDirectly>")){
                lineIndex++;
                showErrorsDirectly = lines[lineIndex].contains("True");
            }
            lineIndex++;
        }
    }

    public int GetNumberOfQuestions(){ return  numberOfQuestions;}

    public int GetNumberOfRemainingQuestions(){
        return questions.size();
    }

    public int GetNumberOfWrongQuestions(){
        return wrongQuestions.size();
    }

    public void AddQuestion(Question question){
        if (!questions.contains(question)){
            questions.add(question);
            numberOfQuestions++;
        }
    }

    public void AddwrongQuestion(Question question){
        if (!wrongQuestions.contains(question)){
            wrongQuestions.add(question);
        }
    }

    public Question GetActQuestion(){
        return actQuestion;
    }

    public Question GetNextQuestion(){
        actQuestion = questions.pop();
        return actQuestion;
    }

    public boolean CheckActQuestion(QuestionAnswer answer){
        boolean isAnswerCorrect = actQuestion.CheckAnswer(answer);
        if(!isAnswerCorrect){
            AddwrongQuestion(actQuestion);
        }
        return isAnswerCorrect;
    }

    public String GetSerializedQuestionary(){
        String serialazionString = "<Questions>\n";
        for(int i = 0; i<questions.size(); i++){
            serialazionString = serialazionString + questions.get(i).GetSerializedQuestion() + "\n";
        }
        serialazionString = serialazionString + "<\\Questions>\n";


        serialazionString = serialazionString + "<WrongQuestions>\n";
        for(int i = 0; i<wrongQuestions.size(); i++){
            serialazionString = serialazionString + wrongQuestions.get(i).GetSerializedQuestion() + "\n";
        }
        serialazionString = serialazionString + "<\\WrongQuestions>\n";

        serialazionString = serialazionString + "<NumberOfQuestions>\n" + numberOfQuestions + "\n<\\NumberOfQuestions>\n";

        String showErrorsDirectlyString = "True";
        if(!showErrorsDirectly){
            showErrorsDirectlyString="False";
        }
        serialazionString = serialazionString + "<ShowErrorsDirectly>\n" + showErrorsDirectlyString + "\n<\\ShowErrorsDirectly>\n";

        return serialazionString;

    }




}
