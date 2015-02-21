package com.ucko.romb.ucko;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.*;
import android.view.View;


public class Pocetna extends ActionBarActivity {

    Button radiLekcije;
    Button novaLekcija;
    Button urediPostojece;
    Button opcije;
    Button izlaz;
    public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);

        radiLekcije = (Button) findViewById(R.id.btnRadiLekcije);
        novaLekcija = (Button) findViewById(R.id.btnNapraviNovuLekciju);
        urediPostojece = (Button) findViewById(R.id.btnUrediPostojece);
        opcije = (Button) findViewById(R.id.btnOpcije);
        izlaz = (Button) findViewById(R.id.btnIzlaz);
        db = new DatabaseHandler(this);

        radiLekcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, PostojeceLekcije.class);
                i.putExtra("radjenje", true);
                i.putExtra("tabela", DatabaseHandler.LEKCIJE);
                startActivity(i);
            }
        });

        novaLekcija.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, Lekcija.class);
                i.putExtra("nova", true);
                startActivity(i);
            }
        });

        urediPostojece.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, Lekcija.class);
                i.putExtra("nova", false);
                i.putExtra("tabela", DatabaseHandler.LEKCIJE);
                startActivity(i);
            }
        });

        opcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, Podesavanja.class);
                startActivity(i);
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
