package com.example.flue_trainer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class QuestionaryGeneratorTest {

    @Test
    public void SimpleConstructorTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();

        String questionText = "Question";
        String answerText[] = {"Ans1", "Ans2", "Ans3"};
        Question question  = new Question(questionText, answerText, QuestionAnswer.A,QuestionCategory.Rettung);
        Question question2  = new Question(questionText + "2", answerText, QuestionAnswer.B,QuestionCategory.Atemschutz);

        questionaryGenrator.AddQuestion(question);
        questionaryGenrator.AddQuestion(question2);

        assertEquals(0,  questionaryGenrator.GetNumberOfQuestions());

        questionaryGenrator.SetNumberOfQuestions(1);
        assertEquals(1,  questionaryGenrator.GetNumberOfQuestions());

        questionaryGenrator.SetNumberOfQuestions(10);
        assertEquals(2,  questionaryGenrator.GetNumberOfQuestions());


    }

    @Test
    public void AddCategoryTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();
        assertEquals(0,questionaryGenrator.GetNumberofChoosenCategorys());

        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        assertEquals(1,questionaryGenrator.GetNumberofChoosenCategorys());

        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        assertEquals(1,questionaryGenrator.GetNumberofChoosenCategorys());
    }

    @Test
    public void RemoveCategoryTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();

        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        assertEquals(1,questionaryGenrator.GetNumberofChoosenCategorys());

        questionaryGenrator.RemoveCategory(QuestionCategory.ErsteHilfe);
        assertEquals(1,questionaryGenrator.GetNumberofChoosenCategorys());

        questionaryGenrator.RemoveCategory(QuestionCategory.Brennen);
        assertEquals(0,questionaryGenrator.GetNumberofChoosenCategorys());
    }

    @Test
    public void RemoveAllCategorysTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();

        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        questionaryGenrator.AddCategory(QuestionCategory.ErsteHilfe);
        questionaryGenrator.AddCategory(QuestionCategory.Fahrzeugkunde);
        assertEquals(3,questionaryGenrator.GetNumberofChoosenCategorys());

        questionaryGenrator.RemoveAllCategorys();
        assertEquals(0,questionaryGenrator.GetNumberofChoosenCategorys());
    }

    @Test
    public void CategoryTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();

        assertFalse(questionaryGenrator.GetChoosenCategorys().contains(QuestionCategory.Brennen));
        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        assertTrue(questionaryGenrator.GetChoosenCategorys().contains(QuestionCategory.Brennen));
        questionaryGenrator.RemoveCategory(QuestionCategory.Brennen);
        assertFalse(questionaryGenrator.GetChoosenCategorys().contains(QuestionCategory.Brennen));

    }

    @Test
    public void CategoryListStringTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();

        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        questionaryGenrator.AddCategory(QuestionCategory.ErsteHilfe);
        assertEquals("Brennen;ErsteHilfe",questionaryGenrator.GetCategoryListString());
    }

    @Test
    public void SettingsStringTest() throws Exception {
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator();

        questionaryGenrator.AddCategory(QuestionCategory.Brennen);
        questionaryGenrator.AddCategory(QuestionCategory.ErsteHilfe);
        assertEquals("0;Brennen;ErsteHilfe",questionaryGenrator.GetSettingsString());

    }

    @Test
    public void LoadSettingTest() throws Exception {
        String settingsString = "15;Brennen;ErsteHilfe";
        QuestionaryGenrator questionaryGenrator = new QuestionaryGenrator(settingsString);

        assertEquals(15,questionaryGenrator.GetNumberOfQuestions());

        assertTrue(questionaryGenrator.GetChoosenCategorys().contains(QuestionCategory.Brennen));
        assertTrue(questionaryGenrator.GetChoosenCategorys().contains(QuestionCategory.ErsteHilfe));


    }



}