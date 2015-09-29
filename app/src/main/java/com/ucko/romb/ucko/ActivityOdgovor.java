package com.ucko.romb.ucko;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
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
import android.util.Base64;
import android.util.Log;
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

import baza.DatabaseHandler;
import okviri.Odgovor;
import okviri.Okvir;
import sesija.Kontroler;

public class ActivityOdgovor extends Activity {

    public static ActivityPitanje aPitanje;

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
    static Context context;

    Okvir okvir;

    // ZA SLIKE
    public static String putanjaZaBazu = "";

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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            // User had pick an image.
            Cursor cursor = getContentResolver()
                    .query(uri,
                            filePathColumn, null, null, null);
            cursor.moveToFirst();

            // Link to the image
            putanjaZaBazu = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            Bitmap bm = Kontroler.getInstance().shrinkBitmap(putanjaZaBazu, 720, 1280);
            cursor.close();
            iv.setImageBitmap(bm);
            putanjaZaBazu = "";
            saveToInternalSorage(bm);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            galleryAddPic();
            Bitmap bm = Kontroler.getInstance().shrinkBitmap(putanjaZaBazu, 720, 1280);
            iv.setImageBitmap(bm);
            saveToInternalSorage(bm);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odgovor);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        naslov = (EditText) findViewById(R.id.etNaslovKartice);
        odaberiSliku = (Button) findViewById(R.id.btnOdaberiSliku);
        snimi = (Button) findViewById(R.id.btnSnimiNaslov);
        pusti = (Button) findViewById(R.id.btnPustiNaslov);
        zapamti = (Button) findViewById(R.id.btnZapamti);
        tv = (TextView) findViewById(R.id.textView);
        iv = (ImageView) findViewById(R.id.imageView);
        context = getApplicationContext();


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
            //k = Pocetna.db.vratiOdgovor(getIntent().getIntExtra("pozicija", 1));
            try {
                okvir = Kontroler.getInstance().vratiOkvir(new Odgovor(getIntent().getIntExtra("id", 0)));
            } catch (Exception e) {
                Toast.makeText(ActivityOdgovor.this, "Greska prilikom pronalaska odgovora", Toast.LENGTH_SHORT).show();
                return;
            }
            naslov.setText(((Odgovor)okvir).getText());
            putanjaZaBazu = ((Odgovor)okvir).getSlika();
            iv.setImageBitmap(Kontroler.getInstance().loadImageFromStorage(putanjaZaBazu));
            mFileName = ((Odgovor)okvir).getZvuk();
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
                            ActivityOdgovor.this, Uri.parse(mFileName));
                    int duration = mp.getDuration();
                    startPlaying();
                    SystemClock.sleep(duration);
                    mPlayer.release();
                    mPlayer = null;
                } else {
                    Toast.makeText(ActivityOdgovor.this,
                            "Niste snimili zvuk", Toast.LENGTH_LONG).show();
                }
            }
        });

        zapamti.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ekstra.equals("ne")) {
                    //AKO JE TEKST PROMENJEN, PROMENJENA JE SLIKA ILI ZVUK, AZURIRA SE ODGOVOR U BAZI
                    if (!naslov.getText().toString().equals(((Odgovor)okvir).getText()) || flagSlika || flagZvuk) {

                        okvir = new Odgovor(okvir.getId(), naslov.getText().toString().toUpperCase(), putanjaZaBazu, mFileName);

                        Kontroler.getInstance().azurirajOkvir(okvir);

                        Toast.makeText(ActivityOdgovor.this, "Uspešno ste ažurirali odgovor", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < Kontroler.getInstance().getOdgovori().size(); i++){
                            if (Kontroler.getInstance().getOdgovori().get(i).getId()==okvir.getId()){
                                Kontroler.getInstance().getOdgovori().remove(i);
                                break;
                            }
                        }
                        Kontroler.getInstance().getOdgovori().add(okvir);
                        ActivityPitanje.getLo().refreshGridView(Kontroler.getInstance().getOdgovori());

                        aPitanje.refreshSpinner();
                    }
                    finish();
                } else {
                    if (!naslov.getText().toString().trim().equals("") && !putanjaZaBazu.equals("") && flag) {

                        //PRAVLJENJE ODGOVORA
                        okvir = new Odgovor(naslov.getText()
                                .toString().toUpperCase(), putanjaZaBazu, mFileName);

                        //DODAVANJE U BAZU
                        okvir.setId(Kontroler.getInstance().dodajOkvir(okvir));

                        //DODAVANJE U LISTU ODGOVORA, KAKO BI SE PRIKAZIVALA LISTA DODATIH
                        Kontroler.getInstance().getOdgovori().add(okvir);

                        Toast.makeText(ActivityOdgovor.this, "Uspešno ste dodali odgovor", Toast.LENGTH_SHORT).show();

                        ActivityPitanje.getLo().refreshGridView(Kontroler.getInstance().getOdgovori());

                        aPitanje.refreshSpinner();

                        finish();
                    } else {
                        Toast.makeText(ActivityOdgovor.this,
                                "Niste uneli sve podatke", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }

    public void NapraviNoviOdgovor() {
        if (mFileName != null && !mFileName.equals(""))
            (new File(mFileName)).delete();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        mFileName = getFilesDir().getAbsolutePath() + File.separator + "NASLOV_" + timeStamp + ".3gp";
    }

    private void pokreniKameru(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Nema memorijske kartice",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 2);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        putanjaZaBazu = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {

        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("file:" + putanjaZaBazu);
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
        NapraviNoviOdgovor();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(null,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
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

    private void dialogSlika() {
        new AlertDialog.Builder(ActivityOdgovor.this)
                .setTitle("Odabir slike")
                .setMessage("Kako zelite da dodate sliku?")
                .setCancelable(true)
                .setPositiveButton("Galerija",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, 1);
                            }
                        })
                .setNeutralButton("Fotoaparat",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                pokreniKameru();
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
                                skidanjeSlika = new SkidanjeSlika(ActivityOdgovor.this, iv);
                                skidanjeSlika.execute();
                            }
                        }).show();
    }

    public static void saveToInternalSorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("slike", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new File(putanjaZaBazu).delete();
        putanjaZaBazu = mypath.getAbsolutePath();
    }

}
