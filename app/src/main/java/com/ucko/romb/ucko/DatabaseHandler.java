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
	private static final String KARTICE = "kartice";
	private static final String PITANJA = "pitanja";
	private static final String LEKCIJE = "lekcije";

	private static final String KEY_ID = "id";
	private static final String NASLOV = "naslov";
	private static final String SLIKA = "slika";
	private static final String ZVUK = "zvuk";
	private static final String PITANJE = "pitanje";
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

	private void napraviTabeluKartica(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + KARTICE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NASLOV
				+ " TEXT NOT NULL," + SLIKA + " TEXT NOT NULL," + ZVUK
				+ " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	private void napraviTabeluPitanja(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + PITANJA + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + PITANJE
				+ " TEXT NOT NULL," + ZVUK + " TEXT NOT NULL," + TACAN_ODGOVOR
				+ " INTEGER NOT NULL," + NETACNI_ODGOVORI + " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	private void napraviTabeluLekcija(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + LEKCIJE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV_LEKCIJE
				+ " TEXT NOT NULL," + LEKCIJA + " TEXT NOT NULL)";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		napraviTabeluLekcija(db);
		napraviTabeluPitanja(db);
		napraviTabeluKartica(db);
        napraviTabeluPodesavanja(db);
	}

    private void azurirajTabeluPodesavanja(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + KARTICE);
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
		onCreate(db);
	}

    public String[] vratiPodesavanja() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PODESAVANJA, new String[] { KEY_ID, ZVUK_RADOVANJA, BOJA_POZADINE,
                        BOJA_DUGMETA, BOJA_SLOVA, PISMO }, KEY_ID + "=?", new String[] { String.valueOf(1) },
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

	public void dodajKarticu(OkvirOdgovor k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NASLOV, k.getNaziv());
		values.put(SLIKA, k.getSlika());
		values.put(ZVUK, k.getZvuk());

		db.insert(KARTICE, null, values);
		db.close();
	}

	public OkvirOdgovor vratiKarticu(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(KARTICE, new String[] { KEY_ID, NASLOV, SLIKA,
				ZVUK }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		OkvirOdgovor k = new OkvirOdgovor(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getString(3));
        db.close();
        cursor.close();
		return k;
	}

	public List<OkvirOdgovor> vratiSveKartice() {
		List<OkvirOdgovor> listaOkvirOdgovor = new ArrayList<OkvirOdgovor>();

		String selectQuery = "SELECT  * FROM " + KARTICE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				OkvirOdgovor k = new OkvirOdgovor(cursor.getInt(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3));
				listaOkvirOdgovor.add(k);
			} while (cursor.moveToNext());
		}
        db.close();
        cursor.close();
		return listaOkvirOdgovor;
	}

	public int azurirajKarticu(OkvirOdgovor k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NASLOV, k.getNaziv());
		values.put(SLIKA, k.getSlika());
		values.put(ZVUK, k.getZvuk());
        int a = db.update(KARTICE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(k.getId()) });
        db.close();
		return a;
	}

	public void obrisiKarticu(OkvirOdgovor k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(KARTICE, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

	public void dodajLekciju(OkvirPitanje k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PITANJE, k.getNaziv());
		values.put(ZVUK, k.getZvuk());
		values.put(TACAN_ODGOVOR, k.getTacanOdgovor());
		values.put(NETACNI_ODGOVORI, k.toString());

		db.insert(PITANJA, null, values);
		db.close();
	}

	public OkvirPitanje vratiLekciju(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(PITANJA, new String[] { KEY_ID, PITANJE, ZVUK,
				TACAN_ODGOVOR, NETACNI_ODGOVORI }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		OkvirPitanje k = new OkvirPitanje(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
        cursor.close();
        db.close();
		return k;
	}

	public List<OkvirPitanje> vratiSveLekcije() {
		List<OkvirPitanje> listaOkvirPitanje = new ArrayList<OkvirPitanje>();

		String selectQuery = "SELECT  * FROM " + PITANJA;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				OkvirPitanje k = new OkvirPitanje(cursor.getInt(0), cursor.getString(1),
						cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				listaOkvirPitanje.add(k);
			} while (cursor.moveToNext());
		}
        db.close();
        cursor.close();
		return listaOkvirPitanje;
	}

	public int azurirajLekciju(OkvirPitanje k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PITANJE, k.getNaziv());
		values.put(ZVUK, k.getZvuk());
		values.put(TACAN_ODGOVOR, k.getTacanOdgovor());
		values.put(NETACNI_ODGOVORI, k.toString());
        int a = db.update(PITANJA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(k.getId()) });
        db.close();
		return a;
	}

	public void obrisiLekciju(OkvirPitanje k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(PITANJA, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

	public void dodajSkupLekcija(OkvirLekcija sk) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NAZIV_LEKCIJE, sk.getNaziv());
		values.put(LEKCIJA, sk.toString());

		db.insert(LEKCIJE, null, values);
		db.close();
	}

	public OkvirLekcija vratiSkupLekcija(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(LEKCIJE, new String[] { KEY_ID, NAZIV_LEKCIJE,
				LEKCIJA }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		OkvirLekcija sk = new OkvirLekcija(cursor.getInt(0), cursor.getString(1),
				cursor.getString(2));
        db.close();
        cursor.close();
		return sk;
	}

	public List<OkvirLekcija> vratiSveSkupoveLekcija() {
		List<OkvirLekcija> okvirLekcija = new ArrayList<OkvirLekcija>();

		String selectQuery = "SELECT  * FROM " + LEKCIJE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				OkvirLekcija k = new OkvirLekcija(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2));
				okvirLekcija.add(k);
			} while (cursor.moveToNext());
		}
        db.close();
        cursor.close();
		return okvirLekcija;
	}

	public int azurirajSkupLekcija(OkvirLekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(NAZIV_LEKCIJE, k.getNaziv());
		values.put(LEKCIJA, k.toString());
        int a = db.update(LEKCIJE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(k.getId()) });
        db.close();
		return a;
	}

	public void obrisiSkupLekcija(OkvirLekcija k) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(LEKCIJE, KEY_ID + " = ?",
				new String[] { String.valueOf(k.getId()) });
		db.close();
	}

}
