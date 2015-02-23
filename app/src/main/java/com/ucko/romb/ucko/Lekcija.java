package com.ucko.romb.ucko;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lekcija extends ActionBarActivity {

    Button dodajPitanje, sacuvaj;
    EditText nazivLekcije;

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
                new AlertDialog.Builder(Lekcija.this)
                        .setMessage("Dodaj lekciju")
                        .setCancelable(true)
                        .setPositiveButton("Nova", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Lekcija.this, Pitanje.class);
                                startActivity(i);
                            }
                        })
                        .setNeutralButton("Postojeća", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Lekcija.this, Pitanja.class);
                                startActivity(i);
                            }
    })
            .show();
}
});

        sacuvaj.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (Pocetna.pitanja.size() > 0)
        Pocetna.db.dodajLekciju(new OkvirLekcija(nazivLekcije.getText().toString(), Pocetna.pitanja));
        else
        Toast.makeText(null, "Niste uneli ni jedno pitanje!", Toast.LENGTH_SHORT).show();
        }
        });
        }

@Override
protected void onResume() {
        super.onResume();
        if (Lista.adapter != null)
        Lista.adapter.notifyDataSetChanged();
    }
}
