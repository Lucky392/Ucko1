package com.ucko.romb.ucko;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fragmenti.ListaOdgovora;
import fragmenti.ListaPitanja;
import okviri.Odgovor;
import okviri.Pitanje;


public class ActivityPitanje extends ActionBarActivity {

    Button snimi;
    Button slusaj;
    Button zapamti;
    Button dodajOdgovor;
    Spinner tacanOdgovor;
    EditText pitanje;
    // ZA ZVUK
    private static final String LOG_TAG = "AudioRecordTest";
    private String mFileName;
    boolean flag = false;
    private MediaRecorder mRecorder = null;
    boolean mStartRecording = true;
    private MediaPlayer mPlayer = null;
    boolean flagZvuk;
    String ekstra;
    ListaOdgovora l;
    public static ArrayList<Odgovor> odgovori = new ArrayList<Odgovor>();

    SpinAdapter adapter;
    MojAdapter mojAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanje);

        ekstra = getIntent().getStringExtra("nov");

        snimi = (Button) findViewById(R.id.btnSnimiPitanje);
        slusaj = (Button) findViewById(R.id.btnSlusajPitanje);
        zapamti = (Button) findViewById(R.id.btnSacuvajPitanje);
        dodajOdgovor = (Button) findViewById(R.id.btnDodajOdgovor);
        tacanOdgovor = (Spinner) findViewById(R.id.tacan_odgovor);
        pitanje = (EditText) findViewById(R.id.etPitanje);

        //adapter = new SpinAdapter(ActivityPitanje.this, android.R.layout.simple_spinner_item, tuple);
        //tacanOdgovor.setAdapter(adapter);

        NapraviNovoPitanje();

        tacanOdgovor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                String t = adapter.getItem(position);
                Toast.makeText(ActivityPitanje.this, t, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        snimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ekstra.equals("ne")) {
                    flagZvuk = true;
                }
                onRecord(mStartRecording);
                flag = true;
                if (mStartRecording) {
                    snimi.setText("Prekini");
                    slusaj.setClickable(false);
                } else {
                    snimi.setText("Snimaj");
                    slusaj.setClickable(true);
                }
                mStartRecording = !mStartRecording;
            }
        });

        slusaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    MediaPlayer mp = MediaPlayer.create(
                            ActivityPitanje.this, Uri.parse(mFileName));
                    int duration = mp.getDuration();
                    startPlaying();
                    SystemClock.sleep(duration);
                    mPlayer.release();
                    mPlayer = null;
                } else {
                    Toast.makeText(ActivityPitanje.this,
                            "Niste snimili zvuk", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        zapamti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ekstra.equals("ne")) {
                    if (true) {

                    }
                    finish();
                } else {
                    if (!mFileName.equals("") && !pitanje.getText().toString().trim().equals("") && odgovori.size() >= 2/* && tacanOdgovor.isSelected()*/) {

                        //NULA PROMENITI NA ODABRANO SA SPINERA
                        Odgovor tacan = odgovori.get(0);

                        odgovori.remove(0);

                        Pitanje p = new Pitanje(pitanje.getText().toString(), mFileName, tacan, odgovori);

                        Pocetna.db.dodajPitanje(p);

                        p.setId(Pocetna.db.vratiIdPitanja());

                        ActvityLekcija.pitanja.add(p);

                        Toast.makeText(ActivityPitanje.this, "Uspešno ste dodali pitanje", Toast.LENGTH_SHORT).show();

                        odgovori.clear();

                        finish();
                    } else {
                        Toast.makeText(ActivityPitanje.this,
                                "Niste uneli sve podatke", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

        dodajOdgovor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (odgovori.size() < 6) {
                    Intent i = new Intent(ActivityPitanje.this, ActivityOdgovor.class);
                    i.putExtra("nov", "da");
                    startActivity(i);
                } else {
                    Toast.makeText(ActivityPitanje.this, "Uneli ste maksimalan broj pitanja", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void NapraviNovoPitanje() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "App" + File.separator + "Zvuci";
        if (!(new File(mFileName).exists())) {
            new File(mFileName).mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mFileName += File.separator + "PITANJE_" + timeStamp + ".3gp";
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        l = new ListaOdgovora();
        fragmentTransaction.replace(R.id.fragment_container, l);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
