package sesija;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Spinner;

import com.ucko.romb.ucko.SpinAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import baza.DatabaseHandler;
import okviri.Okvir;

/**
 * Created by Lucky on 20-Jun-15.
 */
public class Kontroler {

    private static Kontroler instance;
    private List<Okvir> pitanja;
    private List<Okvir> odgovori;
    private List<Okvir> okviri;
    DatabaseHandler db;
    Spinner tacanOdgovor;
    SpinAdapter adapter;

    public void initSpinner(Spinner to, SpinAdapter a) {
        tacanOdgovor = to;
        adapter = a;
    }

    public List<Okvir> getPitanja() {
        return pitanja;
    }

    public List<Okvir> getOdgovori() {
        return odgovori;
    }

    public List<Okvir> getOkviri() {
        return okviri;
    }

    public void initDB(Context cont) {
        db = new DatabaseHandler(cont);
    }

    private Kontroler() {
        pitanja = new ArrayList<>();
        odgovori = new ArrayList<>();
        okviri = new ArrayList<>();
    }

    public static Kontroler getInstance() {
        if (instance == null)
            instance = new Kontroler();
        return instance;
    }

    public List<Okvir> vratiOkvire(Okvir o, boolean all) throws Exception {
        return db.vratiOkvire(o, all);
    }

    public ArrayList<String> vratiTekstoveOkvira(List<Okvir> ok) {
        ArrayList<String> s = new ArrayList<>();
        for (Okvir o : ok)
            s.add(o.toString());
        return s;
    }

    public int dodajOkvir(Okvir o) {
        return db.dodajOkvir(o);
    }

    public int azurirajOkvir(Okvir o) {
        return db.azurirajOkvir(o);
    }

    public Okvir vratiOkvir(Okvir o) throws Exception {
        return db.vratiOkvir(o);
    }

    public int obrisiOkvir(Okvir o){
        return db.obrisiOkvir(o);
    }

    public Bitmap shrinkBitmap(String file, int width, int height) {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }

    public Bitmap loadImageFromStorage(String path) {
        try {
            File f = new File(path);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
