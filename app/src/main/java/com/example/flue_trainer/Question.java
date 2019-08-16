package com.example.flue_trainer;

/**
 * Created by timo on 26.12.17.
 */

public class Question {

    private static int EMPTY_QUESTION_IMAGE_ID = 0x7F070055;

    private String questionText;
    private String answerText[];
    private QuestionAnswer correctAnswer;
    private QuestionCategory questionCategory;
    private int numberOfCorrectAnswers;
    private int numberOfWrongAnswers;
    private int imageResId;

    public Question(String serializedString){
        String parts[] = serializedString.split(";");

        this.questionText = parts[0];
        this.answerText = new String[3];
        this.answerText[0] = parts[1];
        this.answerText[1] = parts[2];
        this.answerText[2] = parts[3];

        this.correctAnswer = QuestionAnswer.valueOf(parts[4]);

        this.questionCategory = QuestionCategory.valueOf(parts[5]);

        this.numberOfCorrectAnswers = Integer.parseInt(parts[6]);
        this.numberOfWrongAnswers = Integer.parseInt(parts[7]);

        if(parts.length > 8){
            this.imageResId = Integer.parseInt(parts[8]);
        }else{
            this.imageResId = EMPTY_QUESTION_IMAGE_ID;
        }

    }

    public Question(String questionText, String answerText[], QuestionAnswer correctAnswer) {
        this(questionText, answerText, correctAnswer, QuestionCategory.none);
    }

    public Question(String questionText, String answerText[], QuestionAnswer correctAnswer, QuestionCategory questionCategory){
        this(questionText,answerText,correctAnswer, questionCategory, EMPTY_QUESTION_IMAGE_ID);
    }

    public Question(String questionText, String answerText[], QuestionAnswer correctAnswer, QuestionCategory questionCategory, int imageResId){
        this.questionText = questionText;
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
        this.numberOfCorrectAnswers = 0;
        this.numberOfWrongAnswers = 0;
        this.questionCategory = questionCategory;
        this.imageResId = imageResId;
    }

    public String GetQuestionText(){
        return questionText;
    }

    public String GetAnswerText(QuestionAnswer answer){
        return answerText[answer.ordinal()];
    }

    public boolean CheckAnswer(QuestionAnswer answer){
        boolean isAnswerCorrect = (answer == correctAnswer);
        if(isAnswerCorrect){
            numberOfCorrectAnswers++;
        }else{
            numberOfWrongAnswers++;
        }
        return isAnswerCorrect;
    }

    public QuestionCategory GetQuestionCategory(){
        return questionCategory;
    }

    public void SetQuestionCategory(QuestionCategory questionCategory){
        this.questionCategory = questionCategory;
    }

    public int GetNumberOfCorrectAnswers(){
        return numberOfCorrectAnswers;
    }

    public int GetNumberOfWrongAnswers(){
        return numberOfWrongAnswers;
    }

    public String GetSerializedQuestion(){
        String serialazionString;
        serialazionString = GetQuestionText() + ";";

        serialazionString = serialazionString + GetAnswerText(QuestionAnswer.A) + ";";
        serialazionString = serialazionString + GetAnswerText(QuestionAnswer.B) + ";";
        serialazionString = serialazionString + GetAnswerText(QuestionAnswer.C) + ";";

        serialazionString = serialazionString +  correctAnswer.name() + ";";

        serialazionString = serialazionString +  questionCategory.name() + ";";

        serialazionString = serialazionString + numberOfCorrectAnswers + ";";

        serialazionString = serialazionString + numberOfWrongAnswers + ";";

        serialazionString = serialazionString + imageResId + ";";

        return serialazionString;
    }


    public int GetRelevance(){
        int relevance = numberOfWrongAnswers - numberOfCorrectAnswers;
        relevance = relevance + (int)(Math.random() * relevance);
        return relevance;

    }

    public int GetImageResId(){
        return imageResId;
    }
}
