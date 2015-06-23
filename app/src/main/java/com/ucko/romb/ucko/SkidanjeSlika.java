package com.ucko.romb.ucko;

import java.io.File;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.Toast;

import sesija.Kontroler;

public class SkidanjeSlika extends AsyncTask<Void, Integer, Void>{

    String listOfFileNames[];
    File folder;
    String najnoviji = "";
    Context cont;
    long check;
    ImageView iv;

    public SkidanjeSlika(Context c, ImageView iv){
        cont = c;
        this.iv = iv;
    }
    @Override
    protected void onPreExecute() {

        folder = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        listOfFileNames = folder.list();

        check = new Date().getTime();

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        while (folder.list().length != listOfFileNames.length + 1) {
            SystemClock.sleep(1000);
        }

        listOfFileNames = folder.list();

        najnoviji = "";

        for (String s : listOfFileNames) {
            if (s.endsWith(".jpg") || s.endsWith(".jpeg")) {
                najnoviji = s;
                break;
            }
        }

        for (String s : listOfFileNames) {
            if (s.endsWith(".jpg") || s.endsWith(".jpeg") || s.endsWith(".png")) {
                if (new Date(new File(folder.getAbsolutePath() + File.separator
                        + s).lastModified()).getTime() > new Date(new File(folder
                        .getAbsolutePath() + File.separator + najnoviji)
                        .lastModified()).getTime()) {
                    najnoviji = s;
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        if (new File(folder + File.separator + najnoviji).lastModified() >= check || najnoviji.equals("")) {
                ActivityOdgovor.putanjaZaBazu = folder + File.separator + najnoviji;
            Bitmap bm = Kontroler.getInstance().shrinkBitmap(ActivityOdgovor.putanjaZaBazu, 720, 1280);
            iv.setImageBitmap(bm);
            ActivityOdgovor.saveToInternalSorage(bm);
            Toast.makeText(cont, "Slika je dodata", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(cont, "Format slike nije podrzan, izadjite iz pretrazivaca i pokusajte ponovo", Toast.LENGTH_LONG).show();
        }

        super.onPostExecute(result);
    }

}
