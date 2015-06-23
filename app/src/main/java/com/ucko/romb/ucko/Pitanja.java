package com.ucko.romb.ucko;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fragmenti.ListaOkvira;
import okviri.Lekcija;
import okviri.Okvir;
import okviri.Pitanje;
import sesija.Kontroler;


public class Pitanja extends Activity {

    ListaOkvira lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanja);

        lo = new ListaOkvira();
        Bundle b = new Bundle();
        b.putString("svrha", "odabir");
        lo.setArguments(b);
        Pitanje p = new Pitanje();
        lo.setSwitchValue(p);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, lo).commit();
        try {
            for (Okvir o : Kontroler.getInstance().vratiOkvire(p, false)){
                Kontroler.getInstance().getOkviri().add(o);
            }
            lo.setOkviri(Kontroler.getInstance().getOkviri());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        Kontroler.getInstance().getOkviri().clear();
        super.onDestroy();
    }

}
