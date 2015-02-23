package com.ucko.romb.ucko;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ucko.romb.ucko.DatabaseHandler;
import com.ucko.romb.ucko.Okvir;
import com.ucko.romb.ucko.OkvirOdgovor;
import com.ucko.romb.ucko.Pocetna;
import com.ucko.romb.ucko.R;
import com.ucko.romb.ucko.SkidanjeSlika;

public class Odgovor extends Activity {

    EditText naslov;
    Button odaberiSliku;
    Button snimi;
    Button pusti;
    Button zapamti;
    TextView tv;
    ImageView iv;

    boolean flagSlika;
    boolean flagZvuk;
    String ekstra;

    OkvirOdgovor k;

    // ZA SLIKE
    String trenutnaPutanja = "";
    public static String putanjaZaBazu = "";

    private static final int PICK_IMAGE = 1;
    private SkidanjeSlika skidanjeSlika;

    // ZA ZVUK
    private static final String LOG_TAG = "AudioRecordTest";
    private String mFileName;
    boolean flag = false;
    private MediaRecorder mRecorder = null;
    boolean mStartRecording = true;
    private MediaPlayer mPlayer = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            Uri uri = data.getData();

            // User had pick an image.
            Cursor cursor = getContentResolver()
                    .query(uri,
                            new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
                            null, null, null);
            cursor.moveToFirst();

            // Link to the image
            putanjaZaBazu = cursor.getString(0);

            iv.setImageBitmap(BitmapFactory.decodeFile(putanjaZaBazu));
            //Izmeniti da se povuce slika i da se konvertuje u bitmap, pa u string i tako da se cuva u bazi...
            cursor.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odgovor);

        naslov = (EditText) findViewById(R.id.etNaslovKartice);
        odaberiSliku = (Button) findViewById(R.id.btnOdaberiSliku);
        snimi = (Button) findViewById(R.id.btnSnimiNaslov);
        pusti = (Button) findViewById(R.id.btnPustiNaslov);
        zapamti = (Button) findViewById(R.id.btnZapamti);
        tv = (TextView) findViewById(R.id.textView);
        iv = (ImageView) findViewById(R.id.imageView);

        naslov.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv.setText(naslov.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ekstra = getIntent().getStringExtra("nov");

        //AKO NIJE NOV ODGOVOR, MENJA SE
        if (ekstra.equals("ne")) {
            k = (OkvirOdgovor) Pocetna.db.vratiProsireniOkvir(getIntent().getIntExtra("pozicija", 1), DatabaseHandler.KARTICE);
            naslov.setText(k.getNaziv());
        }

        odaberiSliku.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ekstra.equals("ne")) {
                    flagSlika = true;
                }
                dialogSlika();
            }
        });

        snimi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ekstra.equals("ne")) {
                    flagZvuk = true;
                }
                onRecord(mStartRecording);
                flag = true;
                if (mStartRecording) {
                    snimi.setText("Prekini");
                    pusti.setClickable(false);
                } else {
                    snimi.setText("Snimaj");
                    pusti.setClickable(true);
                }
                mStartRecording = !mStartRecording;
            }
        });

        pusti.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (flag) {
                    MediaPlayer mp = MediaPlayer.create(
                            Odgovor.this, Uri.parse(mFileName));
                    int duration = mp.getDuration();
                    startPlaying();
                    SystemClock.sleep(duration);
                    mPlayer.release();
                    mPlayer = null;
                } else {
                    Toast.makeText(Odgovor.this,
                            "Niste snimili zvuk", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        zapamti.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ekstra.equals("ne")) {
                    if (!naslov.getText().toString().equals(k.getNaziv()) || flagSlika || flagZvuk) {
                        k = new OkvirOdgovor(k.getId(), naslov.getText().toString(), putanjaZaBazu, mFileName);

                        Pocetna.db.azurirajOdgovor(k);

                        Toast.makeText(Odgovor.this, "Uspešno ste ažurirali odgovor", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    if (!naslov.getText().toString().trim().equals("")
                            && !putanjaZaBazu.equals("") && flag) {

                        OkvirOdgovor kartica = new OkvirOdgovor(naslov.getText()
                                .toString(), putanjaZaBazu, mFileName);

                        Pocetna.db.dodajOdgovor(kartica);

                        Pocetna.odgovori.add(kartica);

                        Toast.makeText(Odgovor.this, "Uspešno ste dodali odgovor", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(Odgovor.this,
                                "Niste uneli sve podatke", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }

    public void NapraviNoviOdgovor() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "App" + File.separator + "Zvuci";
        if (!(new File(mFileName).exists())) {
            new File(mFileName).mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mFileName += File.separator + "NASLOV_" + timeStamp + ".3gp";
    }

    private void slikanje() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (i.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = putanja();
            } catch (IOException ex) {
                Toast.makeText(this, "Nema memorijske kartice",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (photoFile != null) {
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivity(i);
            }
        }
    }

    private File putanja() throws IOException {

        String uri = Environment.getExternalStorageDirectory() + File.separator
                + "App" + File.separator + "Slike";

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        if (!(new File(uri).exists())) {
            new File(uri).mkdirs();
        }
        File storageDir = new File(uri);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        trenutnaPutanja = "file:" + image.getAbsolutePath();
        putanjaZaBazu = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(trenutnaPutanja);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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

    private void dialogSlika(){
        new AlertDialog.Builder(Odgovor.this)
                .setTitle("Odabir slike")
                .setMessage("Kako zelite da dodate sliku?")
                .setCancelable(true)
                .setPositiveButton("Galerija",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent
                                                .createChooser(intent,
                                                        "Select Picture"),
                                        PICK_IMAGE);
                            }
                        })
                .setNeutralButton("Fotoaparat",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                slikanje();

                                galleryAddPic();
                            }
                        })
                .setNegativeButton("Internet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://www.google.rs/imghp?hl=en&tab=wi&ei=CAniU-OeDK-GyAPnjYGIBg&ved=0CAQQqi4oAg"));
                                startActivity(i);
                                skidanjeSlika = new SkidanjeSlika(
                                        Odgovor.this);
                                skidanjeSlika.execute();
                            }
                        }).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
