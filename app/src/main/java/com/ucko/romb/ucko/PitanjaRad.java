package com.ucko.romb.ucko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;


public class PitanjaRad extends Activity {

    public static ImageButton btnZvukPitanja;
    private TextView naslovPitanja;
    private TableRow trFragment;
    private OkvirLekcija lekcija;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanja_rad);

        btnZvukPitanja = (ImageButton) findViewById(R.id.imgBtnZvukPitanja);
        naslovPitanja = (TextView) findViewById(R.id.tvNaslovPitanja);
        trFragment = (TableRow) findViewById(R.id.trFragment);


        for (int i = 0; i < lekcija.getPitanja().size(); i++) {
            int brOdgovora = ((OkvirPitanje) Pocetna.db.vratiProsireniOkvir(lekcija.getPitanja().get(i), DatabaseHandler.PITANJA)).getNetacniOdgovori().size() + 1;

            switch (brOdgovora) {
                case 2 :
                    Fragment_2 fr2 = new Fragment_2();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.add(R.id.fragment_2, fr2);
                    transaction2.commit();
                    break;
                case 3 :
                    Fragment_3 fr3 = new Fragment_3();
                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                    transaction3.add(R.id.fragment_3, fr3);
                    transaction3.commit();
                    break;
                case 4 :
                    Fragment_4 fr4 = new Fragment_4();
                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                    transaction4.add(R.id.fragment_4, fr4);
                    transaction4.commit();
                    break;
                case 5 :
                    Fragment_5 fr5 = new Fragment_5();
                    FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                    transaction5.add(R.id.fragment_5, fr5);
                    transaction5.commit();
                    break;
                case 6 :
                    Fragment_6 fr6 = new Fragment_6();
                    FragmentTransaction transaction6 = getFragmentManager().beginTransaction();
                    transaction6.add(R.id.fragment_6, fr6);
                    transaction6.commit();
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pitanja_rad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
