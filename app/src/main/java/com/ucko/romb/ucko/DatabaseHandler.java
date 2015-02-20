package com.ucko.romb.ucko;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "Ucko";

    private static final String PODESAVANJA = "podesavanja";
	public static final String KARTICE = "odgovori";
    private static final String OKVIRI = "okviri";
	public static final String PITANJA = "pitanja";
	public static final String LEKCIJE = "lekcije";

	private static final String KEY_ID = "id";
	private static final String NAZIV = "naziv";
	private static final String SLIKA = "slika";
	private static final String ZVUK = "zvuk";
	private static final String TACAN_ODGOVOR = "tacan_odgovor";
	private static final String NETACNI_ODGOVORI = "netacni_odgovori";
	private static final String LEKCIJA = "lekcija";

    private static final String ZVUK_RADOVANJA = "zvuk_radovanja";
    private static final String BOJA_POZADINE = "boja_pozadine";
    private static final String BOJA_DUGMETA = "boja_dugmeta";
    private static final String BOJA_SLOVA = "boja_slova";
    private static final String PISMO = "pismo";

	private static final String NAZIV_LEKCIJE = "naziv_lekcije";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

    private void napraviTabeluPodesavanja(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + PODESAVANJA + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ZVUK_RADOVANJA
                + " TEXT," + BOJA_POZADINE + " TEXT," + BOJA_DUGMETA
                + " TEXT," + BOJA_SLOVA + " TEXT," + PISMO + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    private void napraviTabeluOkvira(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + OKVIRI + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

	private void napraviTabeluKartica(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + KARTICE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + SLIKA + " TEXT NOT NULL," + ZVUK
				+ " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	private void napraviTabeluPitanja(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + PITANJA + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + ZVUK + " TEXT NOT NULL," + TACAN_ODGOVOR
				+ " INTEGER NOT NULL," + NETACNI_ODGOVORI + " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	private void napraviTabeluLekcija(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + LEKCIJE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + LEKCIJA + " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		napraviTabeluLekcija(db);
		napraviTabeluPitanja(db);
		napraviTabeluKartica(db);
        napraviTabeluOkvira(db);
        napraviTabeluPodesavanja(db);
	}

    private void azurirajTabeluPodesavanja(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + KARTICE);
    }

    private void azurirajTabeluOkviri(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + OKVIRI);
    }

    private void azurirajTabeluKartica(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + KARTICE);
    }

    private void azurirajTabeluPitanja(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + PITANJA);
    }

    private void azurirajTabeluLekcija(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + LEKCIJE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		azurirajTabeluLekcija(db);
		azurirajTabeluPitanja(db);
		azurirajTabeluKartica(db);
        azurirajTabeluPodesavanja(db);
        azurirajTabeluOkviri(db);
		onCreate(db);
	}

    public String[] vratiPodesavanja() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PODESAVANJA, new String[]{KEY_ID, ZVUK_RADOVANJA, BOJA_POZADINE,
                        BOJA_DUGMETA, BOJA_SLOVA, PISMO}, KEY_ID + "=?", new String[]{String.valueOf(1)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        String[] s = null;
        try {
            s = new String[]{cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)};
        } catch(Exception e){
            s = new String[]{"","","","",""};
        }
        db.close();
        cursor.close();
        return s;
    }

    public void azurirajPodesavanja(String s1, String s2, String s3, String s4, String s5) {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] s = vratiPodesavanja();
        ContentValues values = new ContentValues();
        values.put(ZVUK_RADOVANJA, s1);
        values.put(BOJA_POZADINE, s2);
        values.put(BOJA_DUGMETA, s3);
        values.put(BOJA_SLOVA, s4);
        values.put(PISMO, s5);
        if (s == null){
            db.insert(PODESAVANJA, null, values);
        } else {
            db.update(PODESAVANJA, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(1) });
        }
        db.close();
    }

    public int vratiIdOkvira() {
        String selectQuery = "SELECT " + KEY_ID +" FROM " + OKVIRI;
        int a = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToLast()) {
            a = cursor.getInt(0);
        }
        db.close();
        cursor.close();
        return a;
    }

    private Okvir vratiProsireniOkvir(Cursor cursor, String tabela){
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        switch (tabela){
            case KARTICE:
                return new OkvirOdgovor(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3));
            case PITANJA:
                return new OkvirPitanje(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));
            case LEKCIJE:
                return new OkvirLekcija(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        return null;
    }

	public Okvir vratiProsireniOkvir(int id, String tabela) {
		SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + OKVIRI + " AS o INNER JOIN "+ tabela +" AS t ON o.id=t.id WHERE o." + KEY_ID + "=" + id;
		Cursor cursor = db.rawQuery(selectQuery, null);
		Okvir o = vratiProsireniOkvir(cursor, tabela);
        db.close();
        cursor.close();
		return o;
	}

    private List<Okvir> vratiProsireneOkvire(Cursor cursor, String tabela)
    {
        List<Okvir> o = new ArrayList<Okvir>();
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                switch (tabela){
                    case KARTICE:
                        o.add(new OkvirOdgovor(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3)));
                    case PITANJA:
                        o.add(new OkvirPitanje(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
                    case LEKCIJE:
                        o.add(new OkvirLekcija(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                }
            }
            return o;
        }
        return null;
    }

	public List<Okvir> vratiProsireneOkvire(String tabela) {
		String selectQuery = "SELECT  * FROM " + OKVIRI + " AS o INNER JOIN "+ tabela +" AS t ON o.id=t.id";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        List<Okvir> listaOkvirOdgovor = vratiProsireneOkvire(cursor, tabela);
        db.close();
        cursor.close();
		return listaOkvirOdgovor;
	}

    public List<Tuple> vratiOkvire(String tabela) {
        String selectQuery = "SELECT  * FROM " + OKVIRI + " as o inner join " + tabela + " as t on o.id=t.id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Tuple> listaOkvira = new ArrayList<Tuple>();
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                listaOkvira.add(new Tuple(cursor.getInt(0),cursor.getString(1)));
            }
        }
        db.close();
        cursor.close();
        return listaOkvira;
    }

    public void unesiOkvir(String naziv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAZIV, naziv);
        db.insert(OKVIRI, null, values);
        db.close();
    }

    public void dodajOdgovor(OkvirOdgovor o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        unesiOkvir(o.getNaziv());
        values.put(KEY_ID, vratiIdOkvira());
        values.put(SLIKA, o.getSlika());
        values.put(ZVUK, o.getZvuk());
        db.insert(KARTICE, null, values);
        db.close();
    }

    public void dodajPitanje(OkvirPitanje p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        unesiOkvir(p.getNaziv());
        values.put(KEY_ID, vratiIdOkvira());
        values.put(ZVUK, p.getZvuk());
        values.put(TACAN_ODGOVOR, p.getTacanOdgovor());
        values.put(NETACNI_ODGOVORI, p.toString());
        db.insert(PITANJA, null, values);
        db.close();
    }

    public void dodajLekciju(OkvirLekcija l) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        unesiOkvir(l.getNaziv());
        values.put(KEY_ID, vratiIdOkvira());
        values.put(LEKCIJA, l.toString());
        db.insert(LEKCIJE, null, values);
        db.close();
    }

    private int azurirajOkvir(int id, String naziv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAZIV, naziv);
        int a = db.update(OKVIRI, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
        return a;
    }

	public int azurirajOdgovor(OkvirOdgovor o) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
        azurirajOkvir(o.getId(), o.getNaziv());
		values.put(SLIKA, o.getSlika());
		values.put(ZVUK, o.getZvuk());
        int a = db.update(KARTICE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(o.getId()) });
        db.close();
		return a;
	}

    public int azurirajPitanje(OkvirPitanje o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        azurirajOkvir(o.getId(), o.getNaziv());
        values.put(ZVUK, o.getZvuk());
        values.put(TACAN_ODGOVOR, o.getTacanOdgovor());
        values.put(NETACNI_ODGOVORI, o.toString());
        int a = db.update(PITANJA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(o.getId()) });
        db.close();
        return a;
    }

    public int azurirajLekciju(OkvirLekcija o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        azurirajOkvir(o.getId(), o.getNaziv());
        values.put(LEKCIJA, o.toString());
        int a = db.update(LEKCIJE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(o.getId()) });
        db.close();
        return a;
    }

	public void obrisiKarticu(OkvirOdgovor o) throws Exception{
		SQLiteDatabase db = this.getWritableDatabase();
        for (Okvir ok : vratiProsireneOkvire(PITANJA)) {
            for (Integer i : ((OkvirPitanje)ok).getNetacniOdgovori()){
                if (i.intValue() == o.getId())
                    throw new RuntimeException("Odgovor se nalazi u nekom od pitanja");
            }
        }
        db.delete(KARTICE, KEY_ID + " = ?",
				new String[] { String.valueOf(o.getId()) });
		db.close();
	}

	public void obrisiLekciju(OkvirPitanje o) {
		SQLiteDatabase db = this.getWritableDatabase();
        for (Okvir ok : vratiProsireneOkvire(LEKCIJE)) {
            for (Integer i : ((OkvirPitanje)ok).getNetacniOdgovori()){
                if (i.intValue() == o.getId())
                    throw new RuntimeException("Pitanje se nalazi u nekoj od lekcija");
            }
        }
		db.delete(PITANJA, KEY_ID + " = ?",
				new String[] { String.valueOf(o.getId()) });
		db.close();
	}

	public void obrisiSkupLekcija(OkvirLekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(LEKCIJE, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

}
