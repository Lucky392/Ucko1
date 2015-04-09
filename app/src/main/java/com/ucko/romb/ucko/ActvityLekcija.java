package com.ucko.romb.ucko;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fragmenti.ListaLekcija;
import fragmenti.ListaPitanja;
import okviri.Lekcija;
import okviri.Pitanje;

public class ActvityLekcija extends ActionBarActivity {

    Button dodajPitanje, sacuvaj;
    EditText nazivLekcije;
    public static ArrayList<Pitanje> pitanja = new ArrayList<Pitanje>();
    ListaPitanja l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekcija);

        dodajPitanje = (Button) findViewById(R.id.btnDodajPitanje);
        sacuvaj = (Button) findViewById(R.id.btnSacuvaj);
        nazivLekcije = (EditText) findViewById(R.id.editText1);

        dodajPitanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new AlertDialog.Builder(ActvityLekcija.this)
                        .setMessage("Dodaj pitanje")
                        .setCancelable(true)
                        .setPositiveButton("Novo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {*/
                Intent i = new Intent(ActvityLekcija.this, ActivityPitanje.class);
                i.putExtra("nov", "da");
                startActivity(i);
                     /*       }
                        })
                        .setNeutralButton("Postojeće", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ActvityLekcija.this, Pitanja.class);
                                i.putExtra("tabela", DatabaseHandler.LEKCIJE);
                                Lista.readFromDatabase = true;
                                startActivity(i);
                            }
    })
            .show();*/
            }
        });

        sacuvaj.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (!pitanja.isEmpty()) {
            Pocetna.db.dodajLekciju(new Lekcija(nazivLekcije.getText().toString(), pitanja));
            pitanja.clear();
            finish();
        }
        else
            Toast.makeText(null, "Niste uneli ni jedno pitanje!", Toast.LENGTH_SHORT).show();
        }
        });
        }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        l = new ListaPitanja();
        fragmentTransaction.replace(R.id.fragment_container, l);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
