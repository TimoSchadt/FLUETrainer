package com.example.flue_trainer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class QuestionaryTest {
    @Test
    public void AddQuestionTest() throws Exception {

        Questionary questionary = new Questionary();

        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.A);
        Question question2  = new Question(questionText, answerText, QuestionAnswer.B,QuestionCategory.Rettung);

        questionary.AddQuestion(question);
        assertEquals(1,questionary.GetNumberOfRemainingQuestions());

        questionary.AddQuestion(question2);
        assertEquals(2,questionary.GetNumberOfRemainingQuestions());

        assertEquals(question, questionary.GetNextQuestion());
        assertEquals(question2, questionary.GetNextQuestion());
    }

    @Test
    public void CheckQuestionTest() throws Exception {

        Questionary questionary = new Questionary();

        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.A);
        Question question2  = new Question(questionText, answerText, QuestionAnswer.B,QuestionCategory.Rettung);

        questionary.AddQuestion(question);
        questionary.AddQuestion(question2);

        assertEquals(question, questionary.GetNextQuestion());
        assertEquals(true, questionary.CheckActQuestion(QuestionAnswer.A));
        assertEquals(0, questionary.GetNumberOfWrongQuestions());

        assertEquals(question2, questionary.GetNextQuestion());
        assertEquals(false, questionary.CheckActQuestion(QuestionAnswer.A));
        assertEquals(1, questionary.GetNumberOfWrongQuestions());
    }

    @Test
    public void QuestionarySerializationTest() throws Exception {
        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.A,QuestionCategory.Rettung);
        Question question2  = new Question(questionText, answerText, QuestionAnswer.B,QuestionCategory.Rettung);

        Questionary questionary = new Questionary();
        questionary.AddQuestion(question);
        questionary.AddQuestion(question2);

        String serializationString =    "<Questions>\n" +
                                        "Question;Ans1;Ans2;Ans3;A;Rettung;0;0;\n" +
                                        "Question;Ans1;Ans2;Ans3;B;Rettung;0;0;\n" +
                                        "<\\Questions>\n" +
                                        "<WrongQuestions>\n" +
                                        "<\\WrongQuestions>\n";
        assertEquals(serializationString,  questionary.GetSerializedQuestionary());
    }

    @Test
    public void QuestionaryDeserializationTest() throws Exception {
        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.A,QuestionCategory.Rettung);
        Question question2  = new Question(questionText + "2", answerText, QuestionAnswer.B,QuestionCategory.Rettung);


        String serializationString =    "<Questions>\n" +
                "Question;Ans1;Ans2;Ans3;A;Rettung;0;0;\n" +
                "Question2;Ans1;Ans2;Ans3;B;Rettung;0;0;\n" +
                "<\\Questions>\n" +
                "<WrongQuestions>\n" +
                "<\\WrongQuestions>\n";

        Questionary questionary = new Questionary(serializationString);

        assertEquals(question.GetQuestionText(), questionary.GetNextQuestion().GetQuestionText());
        assertEquals(question2.GetQuestionText(), questionary.GetNextQuestion().GetQuestionText());
    }

}