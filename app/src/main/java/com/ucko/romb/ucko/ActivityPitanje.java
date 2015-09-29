package com.ucko.romb.ucko;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fragmenti.ListaOkvira;
import okviri.Odgovor;
import okviri.Okvir;
import okviri.Pitanje;
import sesija.Kontroler;


public class ActivityPitanje extends ActionBarActivity {

    Button snimi;
    Button slusaj;
    Button zapamti;
    Button dodajOdgovor;
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
    private static ListaOkvira lo;
    Spinner tacanOdgovor;
    SpinAdapter adapter;
    Pitanje p;

    public static ListaOkvira getLo() {
        return lo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanje);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        snimi = (Button) findViewById(R.id.btnSnimiPitanje);
        slusaj = (Button) findViewById(R.id.btnSlusajPitanje);
        zapamti = (Button) findViewById(R.id.btnSacuvajPitanje);
        dodajOdgovor = (Button) findViewById(R.id.btnDodajOdgovor);
        tacanOdgovor = (Spinner) findViewById(R.id.tacan_odgovor);
        pitanje = (EditText) findViewById(R.id.etPitanje);

        ekstra = getIntent().getStringExtra("nov");
        lo = new ListaOkvira();
        lo.setSwitchValue(new Odgovor());
        ActivityOdgovor.aPitanje = ActivityPitanje.this;
        getFragmentManager().beginTransaction().add(R.id.fragment_container, lo).commit();
        if (ekstra.equals("ne")) {
            try {
                p = (Pitanje) Kontroler.getInstance().vratiOkvir(new Pitanje(getIntent().getIntExtra("id", 0)));
            } catch (Exception e) {
                Toast.makeText(ActivityPitanje.this, "Greska prilikom pronalaska pitanja", Toast.LENGTH_SHORT).show();
                return;
            }
            pitanje.setText(p.toString());
            mFileName = p.getZvuk();
            int a = 0;
            for (int i = 0; i < tacanOdgovor.getCount(); i++) {
                if (tacanOdgovor.getSelectedItem().toString().equals(p.getTacan().toString())) {
                    a = i;
                    break;
                }
            }
            Kontroler.getInstance().getOdgovori().add(p.getTacan());
            for (Okvir o : p.getNetacni()) {
                Kontroler.getInstance().getOdgovori().add(o);
            }
            refreshSpinner();
            tacanOdgovor.setSelection(a);
            lo.setOkviri(Kontroler.getInstance().getOdgovori());
        }

        snimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }
            }
        });

        zapamti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ekstra.equals("ne")) {
                    if (!mFileName.equals("") && !pitanje.getText().toString().trim().equals("") && Kontroler.getInstance().getOdgovori().size() >= 2) {
                        Okvir tacan = Kontroler.getInstance().getOdgovori().get(tacanOdgovor.getSelectedItemPosition());
                        Kontroler.getInstance().getOdgovori().remove(tacanOdgovor.getSelectedItemPosition());
                        String s = pitanje.getText().toString().toLowerCase();
                        Okvir o = new Pitanje(Character.toUpperCase(s.charAt(0)) + s.substring(1), mFileName, (Odgovor) tacan, Kontroler.getInstance().getOdgovori());
                        int a = getIntent().getIntExtra("id", 0);
                        o.setId(a);
                        for (int i = 0; i < Kontroler.getInstance().getPitanja().size(); i++){
                            if (Kontroler.getInstance().getPitanja().get(i).getId()==a){
                                Kontroler.getInstance().getPitanja().remove(i);
                            }
                        }
                        Kontroler.getInstance().getPitanja().add(o);
                        Kontroler.getInstance().azurirajOkvir(o);
                        ActvityLekcija.lo.refreshGridView(Kontroler.getInstance().getPitanja());
                    }
                    finish();
                } else {
                    if (!mFileName.equals("") && !pitanje.getText().toString().trim().equals("") && Kontroler.getInstance().getOdgovori().size() >= 2) {
                        Okvir tacan = Kontroler.getInstance().getOdgovori().get(tacanOdgovor.getSelectedItemPosition());
                        Kontroler.getInstance().getOdgovori().remove(tacanOdgovor.getSelectedItemPosition());
                        String s = pitanje.getText().toString().toLowerCase();
                        Okvir o = new Pitanje(Character.toUpperCase(s.charAt(0)) + s.substring(1), mFileName, (Odgovor) tacan, Kontroler.getInstance().getOdgovori());
                        o.setId(Kontroler.getInstance().dodajOkvir(o));
                        Kontroler.getInstance().getPitanja().add(o);
                        Toast.makeText(ActivityPitanje.this, "Uspešno ste dodali pitanje", Toast.LENGTH_SHORT).show();
                        ActvityLekcija.lo.refreshGridView(Kontroler.getInstance().getPitanja());
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
                if (Kontroler.getInstance().getOdgovori().size() < 6) {
                    new AlertDialog.Builder(ActivityPitanje.this)
                            .setMessage("Dodaj odgovor")
                            .setCancelable(true)
                            .setPositiveButton("Nov", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(ActivityPitanje.this, ActivityOdgovor.class);
                                    i.putExtra("nov", "da");
                                    startActivity(i);
                                }
                            })
                            .setNeutralButton("Postojeći", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(ActivityPitanje.this, Odgovori.class);
                                    i.putExtra("svrha", "odabir");
                                    startActivity(i);
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(ActivityPitanje.this, "Uneli ste maksimalan broj Odgovora", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void NapraviNovoPitanje() {
        if (mFileName != null && !mFileName.equals(""))
            (new File(mFileName)).delete();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mFileName = getFilesDir().getAbsolutePath() + File.separator + "PITANJE_" + timeStamp + ".3gp";
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
        NapraviNovoPitanje();
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

    public void refreshSpinner() {
        adapter = new SpinAdapter(ActivityPitanje.this, android.R.layout.simple_spinner_item,
                Kontroler.getInstance().vratiTekstoveOkvira(Kontroler.getInstance().getOdgovori()));
        tacanOdgovor.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        Kontroler.getInstance().getOdgovori().clear();
        super.onDestroy();
    }
}
