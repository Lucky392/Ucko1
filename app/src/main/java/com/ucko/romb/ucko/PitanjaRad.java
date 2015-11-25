package com.ucko.romb.ucko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import baza.DatabaseHandler;
import okviri.Lekcija;
import okviri.Odgovor;
import okviri.Pitanje;
import sesija.Kontroler;


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

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        btnZvukPitanja = (ImageButton) findViewById(R.id.imgBtnZvukPitanja);
        naslovPitanja = (TextView) findViewById(R.id.tvNaslovPitanja);
        trFragment = (TableRow) findViewById(R.id.trFragment);

        String [] podesavanja = Pocetna.db.vratiPodesavanja();
        if (!podesavanja[2].equals("")){
            String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[2])));
            findViewById(R.id.pitanja_rad).setBackgroundColor(Color.parseColor(hexColor));
            btnZvukPitanja.setBackgroundColor(Color.parseColor(hexColor));
        }
        if (!podesavanja[3].equals("")){
            String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[3])));
            naslovPitanja.setTextColor(Color.parseColor(hexColor));
        }
        try {
            lekcija = (Lekcija)Kontroler.getInstance().vratiOkvir(
                    Kontroler.getInstance().getOkviri().get(getIntent().getIntExtra("pozicija", 0)));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            return;
        }

        brojac = lekcija.getPitanja().size();

        changeFragment(brojac%lekcija.getPitanja().size());

        naslovPitanja.setText(lekcija.getPitanja().get(brojac % lekcija.getPitanja().size()).toString());

        brojac++;
    }

    @Override
    public void onTacanClicked() {
        changeFragment(brojac % lekcija.getPitanja().size());
        naslovPitanja.setText(lekcija.getPitanja().get(brojac % lekcija.getPitanja().size()).toString());
        brojac++;
    }

    private void changeFragment(int trenutno) {
        Bundle b = new Bundle();
        b.putInt("tren", trenutno);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
