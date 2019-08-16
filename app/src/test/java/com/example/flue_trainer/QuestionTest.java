package com.example.flue_trainer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class QuestionTest {

    @Test
    public void SimpleConstructorTest() throws Exception {

        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.A);

        assertEquals(questionText,  question.GetQuestionText());
        assertEquals(answerText[0],  question.GetAnswerText(QuestionAnswer.A));
        assertEquals(answerText[1],  question.GetAnswerText(QuestionAnswer.B));
        assertEquals(answerText[2],  question.GetAnswerText(QuestionAnswer.C));

        assertEquals(true,  question.CheckAnswer(QuestionAnswer.A));
        assertEquals(false,  question.CheckAnswer(QuestionAnswer.B));
        assertEquals(false,  question.CheckAnswer(QuestionAnswer.C));

    }

    @Test
    public void AnswerTest() throws Exception {
        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.B);

        assertEquals(0,  question.GetNumberOfCorrectAnswers());
        assertEquals(0,  question.GetNumberOfWrongAnswers());

        assertEquals(false,  question.CheckAnswer(QuestionAnswer.A));
        assertEquals(0,  question.GetNumberOfCorrectAnswers());
        assertEquals(1,  question.GetNumberOfWrongAnswers());

        assertEquals(true,  question.CheckAnswer(QuestionAnswer.B));
        assertEquals(1,  question.GetNumberOfCorrectAnswers());
        assertEquals(1,  question.GetNumberOfWrongAnswers());

        assertEquals(false,  question.CheckAnswer(QuestionAnswer.C));
        assertEquals(1,  question.GetNumberOfCorrectAnswers());
        assertEquals(2,  question.GetNumberOfWrongAnswers());
    }

    @Test
    public void QuestionCategoryTest() throws Exception {
        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.B);

        assertEquals(QuestionCategory.none,  question.GetQuestionCategory());
        question.SetQuestionCategory(QuestionCategory.Atemschutz);
        assertEquals(QuestionCategory.Atemschutz,  question.GetQuestionCategory());


        Question question2  = new Question(questionText, answerText, QuestionAnswer.B,QuestionCategory.Rettung);

        assertEquals(QuestionCategory.Rettung,  question2.GetQuestionCategory());
        question.SetQuestionCategory(QuestionCategory.Brandsicherheitsdienst);
        assertEquals(QuestionCategory.Brandsicherheitsdienst,  question.GetQuestionCategory());
    }

    @Test
    public void QuestionSerializationTest() throws Exception {
        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.B,QuestionCategory.Rettung);

        String serializationString = "Question;Ans1;Ans2;Ans3;B;Rettung;0;0;";
        assertEquals(serializationString,  question.GetSerializedQuestion());
    }

    @Test
    public void QuestionDeserializationTest() throws Exception {
        String serializationString = "Question;Ans1;Ans2;Ans3;B;Rettung;1;2;";

        Question question  = new Question(serializationString);

        assertEquals("Question",  question.GetQuestionText());
        assertEquals("Ans1",  question.GetAnswerText(QuestionAnswer.A));
        assertEquals("Ans2",  question.GetAnswerText(QuestionAnswer.B));
        assertEquals("Ans3",  question.GetAnswerText(QuestionAnswer.C));

        assertEquals(1,question.GetNumberOfCorrectAnswers());
        assertEquals(2,question.GetNumberOfWrongAnswers());

        question.CheckAnswer(QuestionAnswer.B);

        assertEquals(2,question.GetNumberOfCorrectAnswers());
        assertEquals(2,question.GetNumberOfWrongAnswers());

        assertEquals(QuestionCategory.Rettung,question.GetQuestionCategory());

    }



}