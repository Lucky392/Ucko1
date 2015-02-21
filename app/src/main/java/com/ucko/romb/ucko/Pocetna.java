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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);

        radiLekcije = (Button) findViewById(R.id.btnRadiLekcije);
        novaLekcija = (Button) findViewById(R.id.btnNapraviNovuLekciju);
        urediPostojece = (Button) findViewById(R.id.btnUrediPostojece);
        opcije = (Button) findViewById(R.id.btnOpcije);
        izlaz = (Button) findViewById(R.id.btnIzlaz);


        DatabaseHandler db = new DatabaseHandler(this);
        String[] s = db.vratiPodesavanja();
        if (s != null && s[4].equals("c")) {
            setTitle(R.string.c_app_name);
            radiLekcije.setText(R.string.c_radi_lekcije);
            novaLekcija.setText(R.string.c_upravljanje_sadrzajem);
            urediPostojece.setText(R.string.c_uredi_postojece);
            opcije.setText(R.string.c_opcije);
            izlaz.setText(R.string.c_izlaz);
        }

        radiLekcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, Lekcija.class);
                i.putExtra("radjenje", true);
                i.putExtra("tabela", DatabaseHandler.LEKCIJE);
                startActivity(i);
            }
        });
    }
}
