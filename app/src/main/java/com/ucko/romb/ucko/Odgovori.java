package com.ucko.romb.ucko;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fragmenti.ListaOkvira;
import okviri.Odgovor;
import okviri.Okvir;
import okviri.Pitanje;
import sesija.Kontroler;


public class Odgovori extends Activity {

    ListaOkvira lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odgovori);

        lo = new ListaOkvira();
        Bundle b = new Bundle();
        b.putString("svrha", "odabir");
        lo.setArguments(b);
        Odgovor o = new Odgovor();
        lo.setSwitchValue(o);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, lo).commit();
        try {
            for (Okvir ok : Kontroler.getInstance().vratiOkvire(o, false)){
                Kontroler.getInstance().getOkviri().add(ok);
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
