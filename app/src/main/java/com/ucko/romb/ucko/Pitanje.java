package com.ucko.romb.ucko;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class Pitanje extends ActionBarActivity {

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

    SpinAdapter adapter;

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

        ArrayList<Tuple> t = new ArrayList<Tuple>();
        for (Okvir o : Pocetna.odgovori){
            t.add(new Tuple(o.getId(),o.getNaziv()));
        }
        adapter = new SpinAdapter(Pitanje.this, android.R.layout.simple_spinner_item, t);
        tacanOdgovor.setAdapter(adapter);

        tacanOdgovor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Tuple t = adapter.getItem(position);
                // Here you can do the action you want to...
                Toast.makeText(Pitanje.this, t.getI() + ", " + t.getS(),
                        Toast.LENGTH_SHORT).show();
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
                            Pitanje.this, Uri.parse(mFileName));
                    int duration = mp.getDuration();
                    startPlaying();
                    SystemClock.sleep(duration);
                    mPlayer.release();
                    mPlayer = null;
                } else {
                    Toast.makeText(Pitanje.this,
                            "Niste snimili zvuk", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        zapamti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dodajOdgovor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pitanje.this, Odgovor.class);
                i.putExtra("nov", "da");
                startActivity(i);
            }
        });
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
        ArrayList<Tuple> t = new ArrayList<Tuple>();
        for (Okvir o : Pocetna.odgovori){
            t.add(new Tuple(o.getId(),o.getNaziv()));
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
}
