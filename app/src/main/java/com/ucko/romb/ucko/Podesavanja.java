package com.ucko.romb.ucko;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Podesavanja extends ActionBarActivity implements ColorPickerDialog.OnColorChangedListener {

    private Paint mPaint;
    private String [] podesavanja;
    private MediaRecorder mRecorder = null;
    boolean mStartRecordingTacan = true;
    boolean mStartRecordingNetacan = true;
    private MediaPlayer mPlayer = null;
    boolean flagZvuk;
    boolean flagTacan = false;
    boolean flagNetacan = false;
    private static final String LOG_TAG = "AudioRecordTest";
    private String mFileNameTacan;
    private String mFileNameNetacan;
    boolean pozadina = false;
    boolean slova = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podesavanja);

        final Button snimiZvukTacnog = (Button) findViewById(R.id.btnSnimiTacan);
        final Button slusajZvukTacnog = (Button) findViewById(R.id.btnSlusajTacan);
        Button vratiZvukTacnogNaPodrazumevano = (Button) findViewById(R.id.btnVratiNaPodrazumevaniTacni);

        final Button snimiZvukNetacnog = (Button) findViewById(R.id.btnSnimiNetacni);
        final Button slusajZvukNetacnog = (Button) findViewById(R.id.btnSlusajNetacni);
        Button vratiZvukNetacnogNaPodrazumevano = (Button) findViewById(R.id.btnVratiNaPodrazumevanNetacni);



        final Button promeniSlova = (Button) findViewById(R.id.btnOdaberiSlova);
        Button bojaPozadine = (Button) findViewById(R.id.btnOdaberiBojuPozadine);

        Button bojaTeksta = (Button) findViewById(R.id.btnOdaberiBojuTeksta);

//        snimiZvukNetacnog.setBackgroundColor(Color.RED);

        podesavanja = Pocetna.db.vratiPodesavanja();
        if (podesavanja[0] != null && !podesavanja[0].equals("")){
            flagTacan = true;
        }
        if (podesavanja[1] != null && !podesavanja[1].equals("")){
            flagNetacan = true;
        }
        if (podesavanja[4]!=null && podesavanja[4].equals("")){
            promeniSlova.setText("LATINICA");
        } else {
            promeniSlova.setText("ĆIRILICA");
        }
        mPaint = new Paint();

        snimiZvukTacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecordTacan(mStartRecordingTacan);
                flagTacan = true;
                if (mStartRecordingTacan) {
                    snimiZvukTacnog.setText("Prekini");
                    slusajZvukTacnog.setClickable(false);
                } else {
                    snimiZvukTacnog.setText("Snimaj");
                    slusajZvukTacnog.setClickable(true);
                }
                mStartRecordingTacan = !mStartRecordingTacan;
            }
        });

        slusajZvukTacnog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flagTacan) {
                    MediaPlayer mp = MediaPlayer.create(
                            Podesavanja.this, Uri.parse(mFileNameTacan));
                    int duration = mp.getDuration();
                    startPlayingTacan();
                    SystemClock.sleep(duration);
                    mPlayer.release();
                    mPlayer = null;
                } else {
                    Toast.makeText(Podesavanja.this,
                            "Niste snimili zvuk", Toast.LENGTH_LONG).show();
                }
            }
        });

        snimiZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecordNetacan(mStartRecordingNetacan);
                flagNetacan = true;
                if (mStartRecordingNetacan) {
                    snimiZvukNetacnog.setText("Prekini");
                    slusajZvukNetacnog.setClickable(false);
                } else {
                    snimiZvukNetacnog.setText("Snimaj");
                    slusajZvukNetacnog.setClickable(true);
                }
                mStartRecordingNetacan = !mStartRecordingNetacan;
            }
        });

        slusajZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagTacan) {
                    MediaPlayer mp = MediaPlayer.create(
                            Podesavanja.this, Uri.parse(mFileNameNetacan));
                    int duration = mp.getDuration();
                    startPlayingNetacan();
                    SystemClock.sleep(duration);
                    mPlayer.release();
                    mPlayer = null;
                } else {
                    Toast.makeText(Podesavanja.this,
                            "Niste snimili zvuk", Toast.LENGTH_LONG).show();
                }
            }
        });

        promeniSlova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(podesavanja[4].equals("c")) {
                    podesavanja[4] = "";
                    Pocetna.db.azurirajPodesavanja(podesavanja);
                    promeniSlova.setText("LATINICA");
                    Toast.makeText(getApplicationContext(), "Postavili ste pismo na latinicu.", Toast.LENGTH_LONG).show();
                } else {
                    podesavanja[4] = "c";
                    Pocetna.db.azurirajPodesavanja(podesavanja);
                    promeniSlova.setText("ĆIRILICA");
                    Toast.makeText(getApplicationContext(), "Postavili ste pismo na ćirilicu.", Toast.LENGTH_LONG).show();
                }

            }
        });

        vratiZvukTacnogNaPodrazumevano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                podesavanja[0] = "d";
                Pocetna.db.azurirajPodesavanja(podesavanja);
                Toast.makeText(getApplicationContext(), "Postavili ste zvuk tacnog odgovora na podrazumevanu vrednost.", Toast.LENGTH_LONG).show();
            }
        });

        vratiZvukNetacnogNaPodrazumevano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                podesavanja[1] = "d";
                Pocetna.db.azurirajPodesavanja(podesavanja);
                Toast.makeText(getApplicationContext(), "Postavili ste zvuk netacnog odgovora na podrazumevanu vrednost.", Toast.LENGTH_LONG).show();
            }
        });



        bojaPozadine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int boja = 0;
                ColorPickerDialog c = new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor());
                c.setCancelable(true);
                c.show();
                if (slova == true){
                    slova = false;
                }
                pozadina = true;
            }
        });

        bojaTeksta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog c = new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor());
                c.setCancelable(true);
                c.show();
                if (pozadina == true){
                    pozadina = false;
                }
                slova = true;
            }
        });

    }

    public void napraviZvukTacan() {
        if (mFileNameTacan != null && !mFileNameTacan.equals(""))
            (new File(mFileNameTacan)).delete();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mFileNameTacan = getFilesDir().getAbsolutePath() + File.separator + "TACAN_" + timeStamp + ".3gp";
    }

    public void napraviZvukNetacan() {
        if (mFileNameNetacan != null && !mFileNameNetacan.equals(""))
            (new File(mFileNameNetacan)).delete();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mFileNameNetacan = getFilesDir().getAbsolutePath() + File.separator + "NETACAN_" + timeStamp + ".3gp";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_podesavanja, menu);
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

    private void startRecordingTacan() {
        napraviZvukTacan();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileNameTacan);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }
    private void startRecordingNetacan() {
        napraviZvukNetacan();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileNameNetacan);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording(int tn) {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        if (tn == 0) {
            podesavanja[tn] = mFileNameTacan;
        } else {
            podesavanja[tn] = mFileNameNetacan;
        }
        Pocetna.db.azurirajPodesavanja(podesavanja);
    }

    private void onRecordTacan(boolean start) {
        if (start) {
            startRecordingTacan();
        } else {
            stopRecording(0);
        }
    }
    private void onRecordNetacan(boolean start) {
        if (start) {
            startRecordingNetacan();
        } else {
            stopRecording(1);
        }
    }

    private void startPlayingTacan() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileNameTacan);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    private void startPlayingNetacan() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileNameNetacan);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    public void colorChanged(int color) {
        mPaint.setColor(color);
        if (pozadina){
            podesavanja[2] = mPaint.getColor() + "";
            Pocetna.db.azurirajPodesavanja(podesavanja);
            Toast.makeText(getApplicationContext(), "Promenili ste boju pozadine u lekcijama.", Toast.LENGTH_LONG).show();
            pozadina = false;
        }
        if (slova){
            podesavanja[3] = mPaint.getColor() + "";
            Pocetna.db.azurirajPodesavanja(podesavanja);
            Toast.makeText(getApplicationContext(), "Promenili ste boju teksta u lekcijama.", Toast.LENGTH_LONG).show();
            slova = false;
        }
    }
}
