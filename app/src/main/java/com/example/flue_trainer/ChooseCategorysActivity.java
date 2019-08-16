package com.example.flue_trainer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;



public class ChooseCategorysActivity extends AppCompatActivity implements View.OnClickListener{
    private QuestionaryGenrator questionaryGenrator;

    private Button btnOk;
    private Button btnGrundbogen;
    private Button btnBronze;
    private Button btnSilber;
    private Button btnGold;


    private CheckBox checkBoxAtemschutz;
    private CheckBox checkBoxBelastung;
    private CheckBox checkBoxBrennen;
    private CheckBox checkBoxBSD;
    private CheckBox checkBoxErsteHilfe;
    private CheckBox checkBoxFahrzeugkunde;
    private CheckBox checkBoxGefahren;
    private CheckBox checkBoxGefahrstoffe;
    private CheckBox checkBoxGeraeteTh;
    private CheckBox checkBoxHygiene;
    private CheckBox checkBoxKatastrophenschutz;
    private CheckBox checkBoxLoescheinsatz;
    private CheckBox checkBoxLoeschen;
    private CheckBox checkBoxPsa;
    private CheckBox checkBoxRechtsgrundlagen;
    private CheckBox checkBoxRettung;
    private CheckBox checkBoxRettungsgeraete;
    private CheckBox checkBoxSonstigeGeraete;
    private CheckBox checkBoxSprechfunk;
    private CheckBox checkBoxTh;
    private CheckBox checkBoxUvv;
    private CheckBox checkBoxWasserfoerderung;
    private CheckBox checkBoxZivilschutz;
    private CheckBox checkBoxBronze;
    private CheckBox checkBoxSilber;
    private CheckBox checkBoxGold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categorys);
        questionaryGenrator = new QuestionaryGenrator();

        ReadSettings();

        btnOk = findViewById(R.id.categroysChoosenButton);
        btnOk.setOnClickListener(this);

        btnGrundbogen = findViewById(R.id.btnGrundbogen);
        btnGrundbogen.setOnClickListener(this);

        btnBronze = findViewById(R.id.btnBronze);
        btnBronze.setOnClickListener(this);

        btnSilber = findViewById(R.id.btnSilber);
        btnSilber.setOnClickListener(this);

        btnGold = findViewById(R.id.btnGold);
        btnGold.setOnClickListener(this);


        checkBoxAtemschutz = findViewById(R.id.categoryCheckBoxAtemschutz);
        checkBoxBelastung = findViewById(R.id.categoryCheckBoxBelastung);
        checkBoxBrennen = findViewById(R.id.categoryCheckBoxBrennen);
        checkBoxBSD = findViewById(R.id.categoryCheckBoxBSD);
        checkBoxErsteHilfe = findViewById(R.id.categoryCheckBoxersteHilfe);
        checkBoxFahrzeugkunde = findViewById(R.id.categoryCheckBoxFahrzeugkunde);
        checkBoxGefahren = findViewById(R.id.categoryCheckBoxGefahren);
        checkBoxGefahrstoffe = findViewById(R.id.categoryCheckBoxGefahrstoffe);
        checkBoxGeraeteTh = findViewById(R.id.categoryCheckBoxGeraeteTH);
        checkBoxHygiene = findViewById(R.id.categoryCheckBoxHygiene);
        checkBoxKatastrophenschutz = findViewById(R.id.categoryCheckBoxKatastrophenschutz);
        checkBoxLoescheinsatz = findViewById(R.id.categoryCheckBoxLoescheinsatz);
        checkBoxLoeschen = findViewById(R.id.categoryCheckBoxLoeschen);
        checkBoxPsa = findViewById(R.id.categoryCheckBoxPsa);
        checkBoxRechtsgrundlagen = findViewById(R.id.categoryCheckBoxRechtsgrundlagen);
        checkBoxRettung = findViewById(R.id.categoryCheckBoxRettung);
        checkBoxRettungsgeraete = findViewById(R.id.categoryCheckBoxRettungsgeraete);
        checkBoxSonstigeGeraete = findViewById(R.id.categoryCheckBoxSonstigeGeraete);
        checkBoxSprechfunk = findViewById(R.id.categoryCheckBoxSprechfunk);
        checkBoxTh = findViewById(R.id.categoryCheckBoxTh);
        checkBoxUvv = findViewById(R.id.categoryCheckBoxUvv);
        checkBoxWasserfoerderung = findViewById(R.id.categoryCheckBoxWasserfoerderung);
        checkBoxZivilschutz = findViewById(R.id.categoryCheckBoxZivilschutz);
        checkBoxBronze = findViewById(R.id.categoryCheckBoxBronze);
        checkBoxSilber = findViewById(R.id.categoryCheckBoxSilber);
        checkBoxGold = findViewById(R.id.categoryCheckBoxGold);


        LoadChoosenCategorys();
    }


    private void LoadChoosenCategorys(){
        LoadChoosenCategory(QuestionCategory.Atemschutz,checkBoxAtemschutz);
        LoadChoosenCategory(QuestionCategory.Belastung,checkBoxBelastung);
        LoadChoosenCategory(QuestionCategory.Brennen,checkBoxBrennen);
        LoadChoosenCategory(QuestionCategory.Brandsicherheitsdienst,checkBoxBSD);
        LoadChoosenCategory(QuestionCategory.ErsteHilfe,checkBoxErsteHilfe);
        LoadChoosenCategory(QuestionCategory.Fahrzeugkunde,checkBoxFahrzeugkunde);
        LoadChoosenCategory(QuestionCategory.Verhalten_bei_Gefahr,checkBoxGefahren);
        LoadChoosenCategory(QuestionCategory.Gefahrstoffe,checkBoxGefahrstoffe);
        LoadChoosenCategory(QuestionCategory.Geraete_TH,checkBoxGeraeteTh);
        LoadChoosenCategory(QuestionCategory.Hygiene,checkBoxHygiene);
        LoadChoosenCategory(QuestionCategory.Katastrophenschutz,checkBoxKatastrophenschutz);
        LoadChoosenCategory(QuestionCategory.Loescheinsatz,checkBoxLoescheinsatz);
        LoadChoosenCategory(QuestionCategory.Loeschen,checkBoxLoeschen);
        LoadChoosenCategory(QuestionCategory.PSA,checkBoxPsa);
        LoadChoosenCategory(QuestionCategory.Rechtsgrundlagen,checkBoxRechtsgrundlagen);
        LoadChoosenCategory(QuestionCategory.Rettung,checkBoxRettung);
        LoadChoosenCategory(QuestionCategory.Rettungsgeraete,checkBoxRettungsgeraete);
        LoadChoosenCategory(QuestionCategory.sonstige_Geraete,checkBoxSonstigeGeraete);
        LoadChoosenCategory(QuestionCategory.Sprechfunk,checkBoxSprechfunk);
        LoadChoosenCategory(QuestionCategory.Technische_Hilfeleistung,checkBoxTh);
        LoadChoosenCategory(QuestionCategory.UVV,checkBoxUvv);
        LoadChoosenCategory(QuestionCategory.Wasserfoerderung,checkBoxWasserfoerderung);
        LoadChoosenCategory(QuestionCategory.Zivilschutz,checkBoxZivilschutz);
        LoadChoosenCategory(QuestionCategory.Bronze,checkBoxBronze);
        LoadChoosenCategory(QuestionCategory.Silber,checkBoxSilber);
        LoadChoosenCategory(QuestionCategory.Gold,checkBoxGold);

    }

    void LoadChoosenCategory(QuestionCategory category, CheckBox checkBox){
        if(questionaryGenrator.GetChoosenCategorys().contains(category)){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnOk){
            SaveChoosenCategorys();
            WriteSettings();
            finish();
        }

        if (v == btnGrundbogen){
            CheckBoxesForGrundbogen();
        }
        if (v == btnBronze){
            CheckBoxesForBronze();
        }

        if (v == btnSilber){
            CheckBoxesForSilber();
        }
        if (v == btnGold){
            CheckBoxesForGold();
        }


    }

    private void CheckBoxesForGrundbogen(){
        checkBoxAtemschutz.setChecked(true);
        checkBoxBelastung.setChecked(true);
        checkBoxBrennen.setChecked(true);
        checkBoxBSD.setChecked(true);
        checkBoxErsteHilfe.setChecked(true);
        checkBoxFahrzeugkunde.setChecked(true);
        checkBoxGefahren.setChecked(true);
        checkBoxGefahrstoffe.setChecked(true);
        checkBoxGeraeteTh.setChecked(true);
        checkBoxHygiene.setChecked(true);
        checkBoxKatastrophenschutz.setChecked(true);
        checkBoxLoescheinsatz.setChecked(true);
        checkBoxLoeschen.setChecked(true);
        checkBoxPsa.setChecked(true);
        checkBoxRechtsgrundlagen.setChecked(true);
        checkBoxRettung.setChecked(true);
        checkBoxRettungsgeraete.setChecked(true);
        checkBoxSonstigeGeraete.setChecked(true);
        checkBoxSprechfunk.setChecked(true);
        checkBoxTh.setChecked(true);
        checkBoxUvv.setChecked(true);
        checkBoxWasserfoerderung.setChecked(true);
        checkBoxZivilschutz.setChecked(true);
        checkBoxBronze.setChecked(false);
        checkBoxSilber.setChecked(false);
        checkBoxGold.setChecked(false);
    }

    void CheckBoxesForBronze(){
        checkBoxAtemschutz.setChecked(false);
        checkBoxBelastung.setChecked(false);
        checkBoxBrennen.setChecked(false);
        checkBoxBSD.setChecked(false);
        checkBoxErsteHilfe.setChecked(false);
        checkBoxFahrzeugkunde.setChecked(false);
        checkBoxGefahren.setChecked(false);
        checkBoxGefahrstoffe.setChecked(false);
        checkBoxGeraeteTh.setChecked(false);
        checkBoxHygiene.setChecked(false);
        checkBoxKatastrophenschutz.setChecked(false);
        checkBoxLoescheinsatz.setChecked(false);
        checkBoxLoeschen.setChecked(false);
        checkBoxPsa.setChecked(false);
        checkBoxRechtsgrundlagen.setChecked(false);
        checkBoxRettung.setChecked(false);
        checkBoxRettungsgeraete.setChecked(false);
        checkBoxSonstigeGeraete.setChecked(false);
        checkBoxSprechfunk.setChecked(false);
        checkBoxTh.setChecked(false);
        checkBoxUvv.setChecked(false);
        checkBoxWasserfoerderung.setChecked(false);
        checkBoxZivilschutz.setChecked(false);
        checkBoxBronze.setChecked(true);
        checkBoxSilber.setChecked(false);
        checkBoxGold.setChecked(false);
    }

    void CheckBoxesForSilber(){
        checkBoxAtemschutz.setChecked(false);
        checkBoxBelastung.setChecked(false);
        checkBoxBrennen.setChecked(false);
        checkBoxBSD.setChecked(false);
        checkBoxErsteHilfe.setChecked(false);
        checkBoxFahrzeugkunde.setChecked(false);
        checkBoxGefahren.setChecked(false);
        checkBoxGefahrstoffe.setChecked(false);
        checkBoxGeraeteTh.setChecked(false);
        checkBoxHygiene.setChecked(false);
        checkBoxKatastrophenschutz.setChecked(false);
        checkBoxLoescheinsatz.setChecked(false);
        checkBoxLoeschen.setChecked(false);
        checkBoxPsa.setChecked(false);
        checkBoxRechtsgrundlagen.setChecked(false);
        checkBoxRettung.setChecked(false);
        checkBoxRettungsgeraete.setChecked(false);
        checkBoxSonstigeGeraete.setChecked(false);
        checkBoxSprechfunk.setChecked(false);
        checkBoxTh.setChecked(false);
        checkBoxUvv.setChecked(false);
        checkBoxWasserfoerderung.setChecked(false);
        checkBoxZivilschutz.setChecked(false);
        checkBoxBronze.setChecked(false);
        checkBoxSilber.setChecked(true);
        checkBoxGold.setChecked(false);
    }

    void CheckBoxesForGold(){
        checkBoxAtemschutz.setChecked(false);
        checkBoxBelastung.setChecked(false);
        checkBoxBrennen.setChecked(false);
        checkBoxBSD.setChecked(false);
        checkBoxErsteHilfe.setChecked(false);
        checkBoxFahrzeugkunde.setChecked(false);
        checkBoxGefahren.setChecked(false);
        checkBoxGefahrstoffe.setChecked(false);
        checkBoxGeraeteTh.setChecked(false);
        checkBoxHygiene.setChecked(false);
        checkBoxKatastrophenschutz.setChecked(false);
        checkBoxLoescheinsatz.setChecked(false);
        checkBoxLoeschen.setChecked(false);
        checkBoxPsa.setChecked(false);
        checkBoxRechtsgrundlagen.setChecked(false);
        checkBoxRettung.setChecked(false);
        checkBoxRettungsgeraete.setChecked(false);
        checkBoxSonstigeGeraete.setChecked(false);
        checkBoxSprechfunk.setChecked(false);
        checkBoxTh.setChecked(false);
        checkBoxUvv.setChecked(false);
        checkBoxWasserfoerderung.setChecked(false);
        checkBoxZivilschutz.setChecked(false);
        checkBoxBronze.setChecked(false);
        checkBoxSilber.setChecked(false);
        checkBoxGold.setChecked(true);
    }

    void SaveChoosenCategorys(){
        SaveChoosenCategory(QuestionCategory.Atemschutz,checkBoxAtemschutz);
        SaveChoosenCategory(QuestionCategory.Belastung,checkBoxBelastung);
        SaveChoosenCategory(QuestionCategory.Brennen,checkBoxBrennen);
        SaveChoosenCategory(QuestionCategory.Brandsicherheitsdienst,checkBoxBSD);
        SaveChoosenCategory(QuestionCategory.ErsteHilfe,checkBoxErsteHilfe);
        SaveChoosenCategory(QuestionCategory.Fahrzeugkunde,checkBoxFahrzeugkunde);
        SaveChoosenCategory(QuestionCategory.Verhalten_bei_Gefahr,checkBoxGefahren);
        SaveChoosenCategory(QuestionCategory.Gefahrstoffe,checkBoxGefahrstoffe);
        SaveChoosenCategory(QuestionCategory.Geraete_TH,checkBoxGeraeteTh);
        SaveChoosenCategory(QuestionCategory.Hygiene,checkBoxHygiene);
        SaveChoosenCategory(QuestionCategory.Katastrophenschutz,checkBoxKatastrophenschutz);
        SaveChoosenCategory(QuestionCategory.Loescheinsatz,checkBoxLoescheinsatz);
        SaveChoosenCategory(QuestionCategory.Loeschen,checkBoxLoeschen);
        SaveChoosenCategory(QuestionCategory.PSA,checkBoxPsa);
        SaveChoosenCategory(QuestionCategory.Rechtsgrundlagen,checkBoxRechtsgrundlagen);
        SaveChoosenCategory(QuestionCategory.Rettung,checkBoxRettung);
        SaveChoosenCategory(QuestionCategory.Rettungsgeraete,checkBoxRettungsgeraete);
        SaveChoosenCategory(QuestionCategory.sonstige_Geraete,checkBoxSonstigeGeraete);
        SaveChoosenCategory(QuestionCategory.Sprechfunk,checkBoxSprechfunk);
        SaveChoosenCategory(QuestionCategory.Technische_Hilfeleistung,checkBoxTh);
        SaveChoosenCategory(QuestionCategory.UVV,checkBoxUvv);
        SaveChoosenCategory(QuestionCategory.Wasserfoerderung,checkBoxWasserfoerderung);
        SaveChoosenCategory(QuestionCategory.Zivilschutz,checkBoxZivilschutz);
        SaveChoosenCategory(QuestionCategory.Bronze,checkBoxBronze);
        SaveChoosenCategory(QuestionCategory.Silber,checkBoxSilber);
        SaveChoosenCategory(QuestionCategory.Gold,checkBoxGold);
    }

    void SaveChoosenCategory(QuestionCategory category, CheckBox checkBox){
        if(checkBox.isChecked()){
            questionaryGenrator.AddCategory(category);
        }else{
            questionaryGenrator.RemoveCategory(category);
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
}
