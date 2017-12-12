package com.example.nicolas.mybeer.fr.if26.loic.nicolas.controler;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicolas.mybeer.MainActivity;
import com.example.nicolas.mybeer.R;
import com.example.nicolas.mybeer.fr.if26.loic.nicolas.model.DataBaseDepense;


public class AddBeer extends AppCompatActivity {
    //On instancie la BDD
    DataBaseDepense myDb;
    //on relie les champs du formulaire à notre classe
    EditText nom, degre, note;
    Button btnAddData;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
        myDb = new DataBaseDepense(this);
        mp = MediaPlayer.create(this, R.raw.beersound);

        //on relie les champs du formulaire à notre classe
        nom = (EditText) findViewById(R.id.nom);
        degre = (EditText) findViewById(R.id.degre);
        note = (EditText) findViewById(R.id.note);
        btnAddData = (Button) findViewById(R.id.addButton);
        AddData();
    }

    //lorsque l'utilisateur valide, on vérifie les données et on les entre dans la BDD
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(nom.getText().toString().trim().length() == 0 || degre.getText().toString().trim().length() == 0 ||
                                note.getText().toString().trim().length() == 0){
                            Toast.makeText(AddBeer.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                        return;
                        }
                        int noteEntier = Integer.parseInt(note.getText().toString());
                        int degreNombre = Integer.parseInt(degre.getText().toString());
                        //si la note est entre 0 et 20 on continue
                        if (noteEntier >= 0 && noteEntier <= 20) {
                            //si le degré est entre 0 et 70 on continue
                            if (degreNombre >= 0 && degreNombre < 70) {
                                //on regarde si les données sont entrées en base via un booléen
                                boolean isInserted = myDb.insertData(nom.getText().toString(),
                                        degre.getText().toString(),
                                        note.getText().toString());
                                //si elles sont entrées on affiche un Toast et on retourne sur la page principale
                                if (isInserted == true) {
                                    Toast.makeText(AddBeer.this, "Bière ajoutée", Toast.LENGTH_LONG).show();
                                    mp.start();
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(intent);
                                    //si elles ne sont pas entrées on informe qu'il y a une erreur
                                } else {
                                    Toast.makeText(AddBeer.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(AddBeer.this, "Le degré n'est pas valide", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddBeer.this, "La note doit être entre 0 et 20", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

}