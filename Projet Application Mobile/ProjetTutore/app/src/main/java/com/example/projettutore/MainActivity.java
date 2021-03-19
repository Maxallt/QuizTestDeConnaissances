package com.example.projettutore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ONE = 0;
    private TabHost tableHote;

    private static final String TAG = "Test";
    private TextView nomFich;
    private String pathFich;

    private org.json.simple.JSONObject jsonO;

    private Uri uri;

    private boolean importation = false;

    /*Partie onglet Lecture */
    TextView nomFichierFiche;

    Button btnAfficRep;
    TextView libelQuest;
    TextView numQuest;
    TextView repVraie;
    TextView repFausse1;
    TextView repFausse2;
    TextView repFausse3;
    TextView repFausse4;
    TextView difficulty;

    private boolean suivant = false, precedent = false;

    private ListIterator<org.json.simple.JSONObject> itr;
    private int countQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nomFich = (TextView) findViewById(R.id.nomFichier);
        tableHote = (TabHost) findViewById(R.id.tableOnglet);

        /* Widget pour la fiche de lecture */
        nomFichierFiche = (TextView) findViewById(R.id.nomFichierFiche);

        btnAfficRep = (Button) findViewById(R.id.boutonRepCorrecte);

        libelQuest = (TextView) findViewById(R.id.libelleQuestion);
        numQuest = (TextView) findViewById(R.id.numQuest);
        difficulty = (TextView) findViewById(R.id.difficulte);
        repVraie = (TextView) findViewById(R.id.repVraie);
        repFausse1 = (TextView) findViewById(R.id.repFausse1);
        repFausse2 = (TextView) findViewById(R.id.repFausse2);
        repFausse3 = (TextView) findViewById(R.id.repFausse3);
        repFausse4 = (TextView) findViewById(R.id.repFausse4);


        /* Partie mise en place des onglets */
        tableHote.setup();

        TabHost.TabSpec specification = tableHote.newTabSpec("onglet_1");
        specification.setIndicator(getResources().getString(R.string.labelOnglet1));
        specification.setContent(R.id.onglet1);
        tableHote.addTab(specification);

        tableHote.addTab(tableHote.newTabSpec("onglet_2")
                                  .setIndicator(getResources().getString(R.string.labelOnglet2))
                                  .setContent(R.id.onglet2));

        tableHote.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId){
                if(!importation) {
                    tableHote.setCurrentTab(0);
                    Toast.makeText(MainActivity.this, "Veuillez importer un fichier avant !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tableHote.setCurrentTab(0);

        verifyStoragePermissions(this);
    }


    private  final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ONE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public void lancementChoix(View view) {
        showFileChooser();
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    uri = data.getData();

                    // A suppr, utile pour tester
                    Log.d(TAG, "File Uri: " + uri.toString());

                    // Get the file instance
                    File file = new File(uri.toString());

                    pathFich = uri.getPath();

                    // A suppr, utile pour tester
                    Log.d(TAG,"uri get path : " + uri.getPath());
                    nomFich.setText(pathFich);
                }
                break;
        }

    }


    public void validation(View view) {
        if (!nomFich.getText().toString().equals(getString(R.string.messageNomFich))) {

            String regex = "[A-Za-z\\d-_%:/. ]+.json";
            if(!pathFich.matches(regex)) {
                Toast.makeText(this, "Problème avec le format du " +
                                "fichier, ce n'est pas un json !",
                        Toast.LENGTH_SHORT).show();
            } else {
                importation = true;
                tableHote.setCurrentTab(1);


                JSONParser jsonP = new JSONParser();


                String[] tmp = uri.getPath().split("/");
                String path= "/" + tmp[tmp.length-1];

                try {
                    File jsonFile = null;
                    File listFiles = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


                    Log.d(TAG,"listFiles path : " + listFiles.getPath());
                    Log.d(TAG," path : " + path);

                    jsonFile = new java.io.File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            + path);

                    FileInputStream in = new FileInputStream(jsonFile.getPath()); //path

                    jsonO = (org.json.simple.JSONObject) jsonP.parse(new FileReader(jsonFile)); //new File(path)
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!nomFichierFiche.getText().toString().equals(tmp[tmp.length-1])){
                    miseEnPlaceFiche();
                }

                nomFichierFiche.setText(tmp[tmp.length-1]);



            }


        } else {
            Toast.makeText(this, "Veuillez sélectionner un fichier !",
                    Toast.LENGTH_SHORT).show();
        }
    }

    org.json.simple.JSONArray questions;
    boolean mauvaiseReponse;


    private void miseEnPlaceFiche(){
        String booleanBadAnswer = (String) jsonO.get("booleanBadAnswer");

        if(booleanBadAnswer.equals("0")){
            btnAfficRep.setVisibility(View.INVISIBLE);
            repFausse1.setVisibility(View.INVISIBLE);
            repFausse2.setVisibility(View.INVISIBLE);
            repFausse3.setVisibility(View.INVISIBLE);
            repFausse4.setVisibility(View.INVISIBLE);
            mauvaiseReponse = false;
        } else {
            btnAfficRep.setVisibility(View.VISIBLE);
            repFausse1.setVisibility(View.VISIBLE);
            repFausse2.setVisibility(View.VISIBLE);
            repFausse3.setVisibility(View.VISIBLE);
            repFausse4.setVisibility(View.VISIBLE);
            mauvaiseReponse = true;
        }
        questions = (org.json.simple.JSONArray) jsonO.get("questions");

        itr = questions.listIterator();
        countQuest=0;
        affichFich();


    }

    boolean suivantEnCours;

    org.json.simple.JSONArray mauvaisesReponses;

    ListIterator<org.json.simple.JSONObject> itr2;

    ArrayList<String> listeRep = new ArrayList<>();

    private void affichFich(){

        org.json.simple.JSONObject test1 = null;

        boolean affichOk = false;

        if (suivant) {
            if(countQuest < questions.size()) {
                if(listeRep != null ) {
                    listeRep.clear();
                }
                suivant = false;
                test1 = itr.next();
                if (!suivantEnCours) {
                    test1 = itr.next();
                    suivantEnCours = true;
                }
                countQuest++;
                affichOk = true;
            } else {
                Toast.makeText(this, "Ceci est la dernière question de votre fiche !",
                        Toast.LENGTH_SHORT).show();
                suivant = false;
            }
        } else if (precedent) {
            if(countQuest > 1) {
                if(listeRep != null ) {
                    listeRep.clear();
                }
                precedent = false;
                test1 = itr.previous();
                if (suivantEnCours) {
                    test1 = itr.previous();
                    suivantEnCours = false;
                }
                countQuest--;
                affichOk = true;
            } else {
                Toast.makeText(this, "Ceci est la première question de votre fiche !",
                        Toast.LENGTH_SHORT).show();
                precedent = false;
            }
        } else if (!suivant && !precedent) {
            test1 = itr.next();
            suivantEnCours = true;
            countQuest++;
            affichOk = true;
        }

        if(affichOk) {
            libelQuest.setText((String) test1.get("libelleQuestion"));

            difficulty.setText((String) test1.get("difficulte"));
            numQuest.setText("" + countQuest);
            if(mauvaiseReponse){
                listeRep.add((String) test1.get("reponseVraie"));


                mauvaisesReponses = (org.json.simple.JSONArray) test1.get("reponseFausse");

                itr2 = mauvaisesReponses.listIterator();

                listeRep.add(""+itr2.next());
                listeRep.add(""+itr2.next());
                listeRep.add(""+itr2.next());
                listeRep.add(""+itr2.next());

                repVraie.setText("");
                repFausse1.setText("");
                repFausse2.setText("");
                repFausse3.setText("");
                repFausse4.setText("");

                repVraie.setTextColor(getResources().getColor(R.color.black));
                repFausse1.setTextColor(getResources().getColor(R.color.black));
                repFausse2.setTextColor(getResources().getColor(R.color.black));
                repFausse3.setTextColor(getResources().getColor(R.color.black));
                repFausse4.setTextColor(getResources().getColor(R.color.black));



                miseEnPlaceRandomReponses(listeRep);
            } else {
                repVraie.setText((String) test1.get("reponseVraie"));
            }
        }
    }

    private void miseEnPlaceRandomReponses(ArrayList<String> listeRep){
        /*Recherche du nombre de réponses*/
        ArrayList<String> repsNonVides = new ArrayList<>();
        //int nbRep=0;
        for(int i = 0; i < listeRep.size() && !listeRep.get(i).equals(""); i++){
            repsNonVides.add(listeRep.get(i));
        }

        Log.d(TAG,"repsNonVides : "+ repsNonVides.toString());
        int alea;


        alea = (int) (Math.random() * repsNonVides.size());
        repVraie.setText(repsNonVides.get(alea));
        repsNonVides.remove(alea);


        alea = (int) (Math.random() * repsNonVides.size());
        repFausse1.setText(repsNonVides.get(alea));
        repsNonVides.remove(alea);

        if (repsNonVides.size()>0) {
            alea = (int) (Math.random() * repsNonVides.size());
            repFausse2.setText(repsNonVides.get(alea));
            repsNonVides.remove(alea);
        } else {
            repFausse2.setText("");
        }

        if (repsNonVides.size()>0 ) {
            alea = (int) (Math.random() * repsNonVides.size());
            repFausse3.setText(repsNonVides.get(alea));
            repsNonVides.remove(alea);
        } else {
            repFausse3.setText("");
        }

        if (repsNonVides.size()>0 ) {
            alea = (int) (Math.random() * repsNonVides.size());
            repFausse4.setText(repsNonVides.get(alea));
            repsNonVides.remove(alea);
        } else {
            repFausse4.setText("");
        }

    }

    public void setSuivant(View view) {
        suivant = true;
        affichFich();
    }

    public void setPrecedent(View view) {
        precedent = true;
        affichFich();
    }

    public void affichBonneRep(View view) {
        if(repVraie.getText().toString().equals(listeRep.get(0))){
            repVraie.setTextColor(getResources().getColor(R.color.bonneReponse));
        }
        if(repFausse1.getText().toString().equals(listeRep.get(0))){
            repFausse1.setTextColor(getResources().getColor(R.color.bonneReponse));
        }
        if(repFausse2.getText().toString().equals(listeRep.get(0))){
            repFausse2.setTextColor(getResources().getColor(R.color.bonneReponse));
        }
        if(repFausse3.getText().toString().equals(listeRep.get(0))){
            repFausse3.setTextColor(getResources().getColor(R.color.bonneReponse));
        }
        if(repFausse4.getText().toString().equals(listeRep.get(0))){
            repFausse4.setTextColor(getResources().getColor(R.color.bonneReponse));
        }
    }
}