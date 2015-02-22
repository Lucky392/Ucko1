package com.ucko.romb.ucko;

import java.io.File;
import java.util.Date;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.widget.Toast;
public class SkidanjeSlika extends AsyncTask<Void, Integer, Void>{

    String listOfFileNames[];
    File folder;
    String najnoviji = "";
    Context cont;
    long check;

    public SkidanjeSlika(Context c){
        cont = c;
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
            if ((s.endsWith(".jpg") || s.endsWith(".jpeg"))) {
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

        if (new File(folder + File.separator + najnoviji).lastModified() >= check || najnoviji == "") {
            Odgovor.putanjaZaBazu = folder + File.separator + najnoviji;
        } else {
            Toast.makeText(cont, "Format slike nije podrzan, izadjite iz pretrazivaca i pokusajte ponovo", Toast.LENGTH_LONG).show();
        }

        super.onPostExecute(result);
    }

}
