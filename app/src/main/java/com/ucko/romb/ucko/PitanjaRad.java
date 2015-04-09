package com.ucko.romb.ucko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import baza.DatabaseHandler;
import okviri.Lekcija;
import okviri.Pitanje;


public class PitanjaRad extends Activity implements RadLekcije.OdgovorInterface {

    public static ImageButton btnZvukPitanja;
    private TextView naslovPitanja;
    private TableRow trFragment;
    public static Lekcija lekcija;
    private RadLekcije radLekcije;
    int brojac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanja_rad);

        btnZvukPitanja = (ImageButton) findViewById(R.id.imgBtnZvukPitanja);
        naslovPitanja = (TextView) findViewById(R.id.tvNaslovPitanja);
        trFragment = (TableRow) findViewById(R.id.trFragment);

        try {
            lekcija = Pocetna.db.vratiLekciju(1);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            return;
        }

        brojac = lekcija.getPitanja().size();

        changeFragment(brojac%lekcija.getPitanja().size());

        brojac++;
    }

    @Override
    public void onTacanClicked() {
        changeFragment(brojac%lekcija.getPitanja().size());
        brojac++;
    }

    private void changeFragment(int trenutno) {
        Bundle b = new Bundle();
        b.putInt("tren", trenutno);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (radLekcije != null)
            fragmentTransaction.detach(radLekcije);
        radLekcije = new RadLekcije();
        radLekcije.setArguments(b);
        fragmentTransaction.replace(R.id.fragment_container, radLekcije);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
