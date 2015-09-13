package com.ucko.romb.ucko;

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
    boolean mStartRecording = true;
    private MediaPlayer mPlayer = null;
    boolean flagZvuk;
    boolean flag = false;
    private static final String LOG_TAG = "AudioRecordTest";
    private String mFileNameTacan;
    private String mFileNameNetacan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podesavanja);

        final Button snimiZvukTacnog = (Button) findViewById(R.id.btnSnimiTacan);
        final Button slusajZvukTacnog = (Button) findViewById(R.id.btnSlusajTacan);
        Button vratiZvukTacnogNaPodrazumevano = (Button) findViewById(R.id.btnVratiNaPodrazumevaniTacni);

        Button snimiZvukNetacnog = (Button) findViewById(R.id.btnSnimiNetacni);
        Button slusajZvukNetacnog = (Button) findViewById(R.id.btnSlusajNetacni);
        Button vratiZvukNetacnogNaPodrazumevano = (Button) findViewById(R.id.btnVratiNaPodrazumevanNetacni);



        Button promeniSlova = (Button) findViewById(R.id.btnOdaberiSlova);
        Button bojaPozadine = (Button) findViewById(R.id.btnOdaberiBojuPozadine);

        Button bojaTeksta = (Button) findViewById(R.id.btnOdaberiBojuTeksta);

        podesavanja = Pocetna.db.vratiPodesavanja();
        mPaint = new Paint();

        snimiZvukTacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFileNameTacan != null && !mFileNameTacan.equals(""))
                    (new File(mFileNameTacan)).delete();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
                mFileNameTacan = getFilesDir().getAbsolutePath() + File.separator + "TACAN_" + timeStamp + ".3gp";
                onRecordTacan(mStartRecording);
                flag = true;
                if (mStartRecording) {
                    snimiZvukTacnog.setText("Prekini");
                    slusajZvukTacnog.setClickable(false);
                } else {
                    snimiZvukTacnog.setText("Snimaj");
                    slusajZvukTacnog.setClickable(true);
                }
                mStartRecording = !mStartRecording;
                podesavanja[0] = mFileNameTacan;
                Pocetna.db.azurirajPodesavanja(podesavanja);
            }
        });

        slusajZvukTacnog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flag) {
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
            public void onClick(View view) {
                if (mFileNameNetacan != null && !mFileNameNetacan.equals(""))
                    (new File(mFileNameNetacan)).delete();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
                mFileNameNetacan = getFilesDir().getAbsolutePath() + File.separator + "NETACAN" + timeStamp + ".3gp";
                onRecordNetacan(mStartRecording);
                flag = true;
                if (mStartRecording) {
                    snimiZvukTacnog.setText("Prekini");
                    slusajZvukTacnog.setClickable(false);
                } else {
                    snimiZvukTacnog.setText("Snimaj");
                    slusajZvukTacnog.setClickable(true);
                }
                mStartRecording = !mStartRecording;
                podesavanja[1] = mFileNameNetacan;
                Pocetna.db.azurirajPodesavanja(podesavanja);
            }
        });

        slusajZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
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

            }
        });

        vratiZvukTacnogNaPodrazumevano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vratiZvukNetacnogNaPodrazumevano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        snimiZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        slusajZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bojaPozadine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog c = new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor());
                c.setCancelable(true);
                c.show();
            }
        });

        bojaTeksta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog c = new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor());
                c.setCancelable(true);
                c.show();
            }
        });

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

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void onRecordTacan(boolean start) {
        if (start) {
            startRecordingTacan();
        } else {
            stopRecording();
        }
    }
    private void onRecordNetacan(boolean start) {
        if (start) {
            startRecordingNetacan();
        } else {
            stopRecording();
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
    }
}
