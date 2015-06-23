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

import fragmenti.ListaOkvira;
import okviri.Lekcija;
import okviri.Okvir;
import okviri.Pitanje;
import sesija.Kontroler;

public class ActvityLekcija extends ActionBarActivity {

    Button dodajPitanje, sacuvaj;
    EditText nazivLekcije;
    static ListaOkvira lo;
    String ekstra;
    Lekcija l;

    public static ListaOkvira getLo() {
        return lo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekcija);

        dodajPitanje = (Button) findViewById(R.id.btnDodajPitanje);
        sacuvaj = (Button) findViewById(R.id.btnSacuvaj);
        nazivLekcije = (EditText) findViewById(R.id.editText1);

        lo = new ListaOkvira();
        lo.setSwitchValue(new Pitanje());
        getFragmentManager().beginTransaction().add(R.id.fragment_container, lo).commit();
        ekstra = getIntent().getStringExtra("nov");
        if (ekstra.equals("ne")) {
            try {
                l = (Lekcija) Kontroler.getInstance().vratiOkvir(Kontroler.getInstance().getOkviri().get(getIntent().getIntExtra("id", 0)));
            } catch (Exception e) {
                Toast.makeText(ActvityLekcija.this, "Nije moguće pronaći lekciju!", Toast.LENGTH_SHORT).show();
                return;
            }
            nazivLekcije.setText(l.toString());
            for (Okvir o : l.getPitanja()) {
                Kontroler.getInstance().getPitanja().add(o);
            }
            lo.setOkviri(Kontroler.getInstance().getPitanja());
        }

        dodajPitanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ActvityLekcija.this)
                        .setMessage("Dodaj pitanje")
                        .setCancelable(true)
                        .setPositiveButton("Novo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ActvityLekcija.this, ActivityPitanje.class);
                                i.putExtra("nov", "da");
                                startActivity(i);
                            }
                        })
                        .setNeutralButton("Postojeće", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ActvityLekcija.this, Pitanja.class);
                                i.putExtra("svrha", "odabir");
                                startActivity(i);
                            }
                        })
                        .show();
            }
        });

        sacuvaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ekstra.equals("ne")) {
                    Kontroler.getInstance().azurirajOkvir(new Lekcija(l.getId(), nazivLekcije.getText().toString(), Kontroler.getInstance().getPitanja()));
                    Toast.makeText(ActvityLekcija.this, "Uspešno ste izmenili lekciju!", Toast.LENGTH_SHORT).show();
                } else {
                    Kontroler.getInstance().dodajOkvir(new Lekcija(nazivLekcije.getText().toString(), Kontroler.getInstance().getPitanja()));
                    Toast.makeText(ActvityLekcija.this, "Uspešno ste dodali lekciju!", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Kontroler.getInstance().getPitanja().clear();
    }
}
