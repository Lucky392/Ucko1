package com.ucko.romb.ucko;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.*;
import android.view.View;


public class Pocetna extends ActionBarActivity {

    Button radiLekcije;
    Button upravljanjeSadrzajem;
    Button opcije;
    Button izlaz;
    public static DatabaseHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);

        db = new DatabaseHandler(this);

        radiLekcije = (Button) findViewById(R.id.btnRadiLekcije);
        upravljanjeSadrzajem = (Button) findViewById(R.id.btnUpravljanjeSadrzajem);
        opcije = (Button) findViewById(R.id.btnOpcije);
        izlaz = (Button) findViewById(R.id.btnIzlaz);


        DatabaseHandler db = new DatabaseHandler(this);
        String [] s = db.vratiPodesavanja();
        if(s!= null && s[4].equals("c")){
            setTitle(R.string.c_app_name);
            radiLekcije.setText(R.string.c_radi_lekcije);
            upravljanjeSadrzajem.setText(R.string.c_upravljanje_sadrzajem);
            opcije.setText(R.string.c_opcije);
            izlaz.setText(R.string.c_izlaz);
        }

        radiLekcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, null);
                i.putExtra("koji", 1);
                i.putExtra("radjenje", true);
                startActivity(i);
            }
        });

        upravljanjeSadrzajem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Pocetna.this)
                        .setMessage("Šta želite da uradite sa sadržajem?")
                        .setCancelable(true)
                        .setPositiveButton("Uredjivanje",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent i = new Intent(Pocetna.this, null);
                                        startActivity(i);
                                    }
                                })
                        .setNeutralButton("Napravi novo",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        Intent i = new Intent(Pocetna.this, null);
                                        startActivity(i);
                                    }
                                })
                        .show();

            }
        });

        opcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pocetna.this, Podesavanja.class));
            }
        });

        izlaz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
