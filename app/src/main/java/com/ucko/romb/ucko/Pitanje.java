package com.ucko.romb.ucko;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class Pitanje extends ActionBarActivity {

    Button snimi;
    Button slusaj;
    Button zapamti;
    Button dodajOdgovor;
    Spinner tacanOdgovor;
    EditText pitanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanje);

        snimi = (Button) findViewById(R.id.btnSacuvajPitanje);
        slusaj = (Button) findViewById(R.id.btnSlusajPitanje);
        zapamti = (Button) findViewById(R.id.btnSnimiPitanje);
        dodajOdgovor = (Button) findViewById(R.id.btnDodajOdgovor);
        tacanOdgovor = (Spinner) findViewById(R.id.tacan_odgovor);
        pitanje = (EditText) findViewById(R.id.etPitanje);

        snimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        slusaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        zapamti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dodajOdgovor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pitanje.this, Odgovor.class);
                startActivity(i);
            }
        });


    }
}
