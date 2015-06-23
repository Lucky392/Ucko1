package com.ucko.romb.ucko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

        try {
            lekcija = (Lekcija)Kontroler.getInstance().vratiOkvir(
                    Kontroler.getInstance().getOkviri().get(getIntent().getIntExtra("pozicija", 0)));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            return;
        }

        naslovPitanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(brojac % lekcija.getPitanja().size());
            }
        });

        brojac = lekcija.getPitanja().size();

        changeFragment(brojac%lekcija.getPitanja().size());

        naslovPitanja.setText(lekcija.getPitanja().get(brojac % lekcija.getPitanja().size()).toString());

        brojac++;
    }

    @Override
    public void onTacanClicked() {
        changeFragment(brojac%lekcija.getPitanja().size());
        naslovPitanja.setText(lekcija.getPitanja().get(brojac%lekcija.getPitanja().size()).toString());
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

    private void playSound(int i){
        String file = ((Pitanje)lekcija.getPitanja().get(i)).getZvuk();
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(file);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Toast.makeText(this, "Greska!", Toast.LENGTH_SHORT).show();
        }
        long duration = mp.getDuration();
        SystemClock.sleep(duration);
        mp.release();
    }
}
