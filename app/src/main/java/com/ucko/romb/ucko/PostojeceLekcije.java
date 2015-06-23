package com.ucko.romb.ucko;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import fragmenti.ListaOkvira;
import okviri.Lekcija;
import okviri.Okvir;
import sesija.Kontroler;

public class PostojeceLekcije extends Activity {

    private static ListaOkvira lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postojece_lekcije);

        Bundle b = new Bundle();
        if (getIntent().getStringExtra("odabir").equals("rad")) {
            b.putString("svrha", "rad");
        } else {
            b.putString("svrha", "azuriranje");
        }
        lo = new ListaOkvira();
        lo.setArguments(b);
        Lekcija l = new Lekcija();
        lo.setSwitchValue(l);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, lo).commit();
        try {
            for (Okvir o : Kontroler.getInstance().vratiOkvire(l, false)){
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
