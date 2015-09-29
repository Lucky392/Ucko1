package baza;

import java.util.ArrayList;
import java.util.List;

import okviri.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ucko.romb.ucko.Pocetna;
import com.ucko.romb.ucko.Podesavanja;

public class DatabaseHandler extends SQLiteOpenHelper {
    // VERZIJA BAZE
    private static final int DATABASE_VERSION = 1;
    // IME BAZE
    private static final String DATABASE_NAME = "Ucko";

    // NAZIVI TABELA
    private static final String PODESAVANJA = "podesavanja";
    public static final String ODGOVORI = "ODGOVORI";
    public static final String PITANJA = "PITANJA";
    public static final String LEKCIJE = "LEKCIJE";

    // NAZIVI KOLONA
    public static final String ID = "id";
    public static final String NAZIV = "naziv";
    public static final String SLIKA = "slika";
    public static final String ZVUK = "zvuk";
    public static final String TACAN_ODGOVOR = "tacan_odgovor";
    public static final String NETACNI_ODGOVORI = "netacni_odgovori";
    private static final String ZVUK_RADOVANJA = "zvuk_radovanja";
    private static final String ZVUK_TUGOVANJA = "zvuk_tugovanja";
    private static final String BOJA_POZADINE = "boja_pozadine";
    private static final String BOJA_SLOVA = "boja_slova";
    private static final String PISMO = "pismo";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void napraviTabeluPodesavanja(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + PODESAVANJA + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + ZVUK_RADOVANJA
                + " TEXT," + ZVUK_TUGOVANJA + " TEXT," + BOJA_POZADINE
                + " TEXT," + BOJA_SLOVA + " TEXT,"
                + PISMO + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    private void napraviTabeluOdgovora(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + ODGOVORI + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV
                + " TEXT NOT NULL," + SLIKA + " TEXT NOT NULL," + ZVUK
                + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    private void napraviTabeluPitanja(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + PITANJA + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV
                + " TEXT NOT NULL," + ZVUK + " TEXT NOT NULL," + TACAN_ODGOVOR
                + " INTEGER NOT NULL," + NETACNI_ODGOVORI + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    private void napraviTabeluLekcija(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + LEKCIJE + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV
                + " TEXT NOT NULL," + PITANJA + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        napraviTabeluLekcija(db);
        napraviTabeluPitanja(db);
        napraviTabeluOdgovora(db);
        napraviTabeluPodesavanja(db);
    }

    private void azurirajTabeluPodesavanja(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + PODESAVANJA);
    }

    private void azurirajTabeluOdgovori(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + ODGOVORI);
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
        azurirajTabeluOdgovori(db);
        azurirajTabeluPodesavanja(db);
        onCreate(db);
    }
    // ////////////////////////////////////////

    // METODE ZA PODESAVANJA

    // ////////////////////////////////////////


    public String[] vratiPodesavanja() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PODESAVANJA, new String[]{ID, ZVUK_RADOVANJA, ZVUK_TUGOVANJA, BOJA_POZADINE,
                         BOJA_SLOVA, PISMO}, ID + "=?", new String[]{String.valueOf(1)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        String[] s;
        try {
            s = new String[]{cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)};
        } catch (Exception e) {
            dodajPodesavanje();
            s = new String[]{"d", "d", "-16777216", "-1", ""};
        }
        cursor.close();
        return s;
    }

    public int dodajPodesavanje() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ZVUK_RADOVANJA, "");
        values.put(ZVUK_TUGOVANJA, "");
        values.put(BOJA_POZADINE, "");
        values.put(BOJA_SLOVA, "");
        values.put(PISMO, "");
        return (int) db.insert(PODESAVANJA, null, values);
    }

    public void azurirajPodesavanja(String[] niz) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] s = vratiPodesavanja();
        ContentValues values = new ContentValues();
        values.put(ZVUK_RADOVANJA, niz[0]);
        values.put(ZVUK_TUGOVANJA, niz[1]);
        values.put(BOJA_POZADINE, niz[2]);
        values.put(BOJA_SLOVA, niz[3]);
        values.put(PISMO, niz[4]);
        if (s == null) {
            db.insert(PODESAVANJA, null, values);
        } else {
            db.update(PODESAVANJA, values, ID + " = ?",
                    new String[]{String.valueOf(1)});
        }
    }

    // ////////////////////////////////////////

    // GENERICKE METODE

    // ////////////////////////////////////////

    public int dodajOkvir(Okvir o) {
        SQLiteDatabase db = this.getWritableDatabase();
        return (int) db.insert(o.getIme(), null, o.getValues());
    }

    public int azurirajOkvir(Okvir o) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(o.getIme(), o.getValues(), ID + " = ?",
                new String[]{String.valueOf(o.getId())});
    }

    public int obrisiOkvir(Okvir o) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(o.getIme(), ID + " = ?",
                new String[]{String.valueOf(o.getId())});
    }

    public Okvir vratiOkvir(Okvir o) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(o.getIme(), o.vratiNizAtributa(), ID + "=?", new String[]{String.valueOf(o.getId())}, null,
                null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Okvir ok = vratiOkvir(o.getIme(), cursor, true);
        cursor.close();
        return ok;
    }

    public ArrayList<Okvir> vratiOkvire(Okvir o, boolean all) throws Exception {
        ArrayList<Okvir> listaOkvira = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + o.getIme();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            listaOkvira.add(vratiOkvir(o.getIme(), cursor, all));
        }
        if (listaOkvira.size() == 0) {
            throw new RuntimeException("Lista je prazna");
        }
        cursor.close();
        return listaOkvira;
    }

    private Okvir vratiOkvir(String ime, Cursor cursor, boolean all) throws Exception {
        switch (ime) {
            case PITANJA:
                ArrayList<Okvir> odgovori = new ArrayList<>();
                if (all) {
                    for (String s : cursor.getString(4).split("##")) {
                        odgovori.add(vratiOkvir(new Odgovor(Integer.parseInt(s))));
                    }
                }
                return new Pitanje(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), (Odgovor) vratiOkvir(new Odgovor(cursor.getInt(3))), odgovori);
            case ODGOVORI:
                return new Odgovor(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
            case LEKCIJE:
                ArrayList<Okvir> okviri = new ArrayList<>();
                if (all) {
                    for (String s : cursor.getString(2).split("##")) {
                        okviri.add(vratiOkvir(new Pitanje(Integer.parseInt(s))));
                    }
                }
                return new Lekcija(cursor.getInt(0), cursor.getString(1), okviri);
        }
        return null;
    }

    // ////////////////////////////////////////

    // METODE ZA ODGOVORE

    // ////////////////////////////////////////
//	public void dodajOdgovor(Odgovor o) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(NAZIV, o.getText());
//		values.put(SLIKA, o.getSlika());
//		values.put(ZVUK, o.getZvuk());
//		db.insert(ODGOVORI, null, values);
//	}
//
//	public Odgovor vratiOdgovor(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(ODGOVORI, new String[] { ID, NAZIV, SLIKA,
//				ZVUK }, ID + "=?", new String[] { String.valueOf(id) }, null,
//				null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		Odgovor o = new Odgovor(cursor.getInt(0), cursor.getString(1),
//				cursor.getString(2), cursor.getString(3));
//		cursor.close();
//		return o;
//	}
//
//	public ArrayList<Odgovor> vratiSveOdgovore() throws Exception {
//		ArrayList<Odgovor> listaOdgovor = new ArrayList<Odgovor>();
//
//		String selectQuery = "SELECT * FROM " + ODGOVORI;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		while (cursor.moveToNext()) {
//			Odgovor o = new Odgovor(cursor.getInt(0), cursor.getString(1),
//					cursor.getString(2), cursor.getString(3));
//			listaOdgovor.add(o);
//		}
//		if (listaOdgovor.size() == 0) {
//			throw new RuntimeException("Lista odgovora je prazna");
//		}
//		cursor.close();
//		return listaOdgovor;
//	}
//
//	public int azurirajOdgovor(Odgovor o) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(NAZIV, o.getText());
//		values.put(SLIKA, o.getSlika());
//		values.put(ZVUK, o.getZvuk());
//		return db.update(ODGOVORI, values, ID + " = ?",
//				new String[] { String.valueOf(o.getId()) });
//	}
//
//	public void obrisiOdgovor(Odgovor o) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(ODGOVORI, ID + " = ?",
//				new String[] { String.valueOf(o.getId()) });
//	}
//
//	// /////////////////////////////////////////////
//
//	// METODE ZA PITANJA
//
//	// /////////////////////////////////////////////
//	public void dodajPitanje(Pitanje p) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(NAZIV, p.getNaslov());
//		values.put(ZVUK, p.getZvuk());
//		values.put(TACAN_ODGOVOR, p.getTacan().getId());
//		values.put(NETACNI_ODGOVORI, p.getNetacniString());
//
//		db.insert(PITANJA, null, values);
//	}
//
//	public Pitanje vratiPitanje(int id) throws Exception {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(PITANJA, new String[] { ID, NAZIV, ZVUK,
//				TACAN_ODGOVOR, NETACNI_ODGOVORI }, ID + "=?",
//				new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//		else
//			throw new RuntimeException("Lista je prazna");
//		ArrayList<Odgovor> odgovori = new ArrayList<Odgovor>();
//		for (String s : cursor.getString(4).split("##")) {
//			odgovori.add(vratiOdgovor(Integer.parseInt(s)));
//		}
//		Pitanje p = new Pitanje(cursor.getInt(0), cursor.getString(1),
//				cursor.getString(2), vratiOdgovor(cursor.getInt(3)), odgovori);
//        cursor.close();
//		return p;
//	}
//
//    public int vratiIdPitanja() {
//        String selectQuery = "SELECT * FROM " + PITANJA;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToLast();
//        int a = cursor.getInt(0);
//        cursor.close();
//        return a;
//    }
//
//	public ArrayList<Pitanje> vratiSvaPitanja() throws Exception {
//		ArrayList<Pitanje> listaPitanja = new ArrayList<Pitanje>();
//
//		String selectQuery = "SELECT * FROM " + PITANJA;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		while (cursor.moveToNext()) {
//			ArrayList<Odgovor> odgovori = new ArrayList<Odgovor>();
//			for (String s : cursor.getString(4).split("##")) {
//				odgovori.add(vratiOdgovor(Integer.parseInt(s)));
//			}
//			listaPitanja.add(new Pitanje(cursor.getInt(0), cursor.getString(1),
//					cursor.getString(2), vratiOdgovor(cursor.getInt(3)),
//					odgovori));
//		}
//		if (listaPitanja.size() == 0) {
//			throw new RuntimeException("Lista pitanja je prazna");
//		}
//		return listaPitanja;
//	}
//
//	public int azurirajPitanje(Pitanje p) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(NAZIV, p.getNaslov());
//		values.put(ZVUK, p.getZvuk());
//		values.put(TACAN_ODGOVOR, p.getTacan().getId());
//		values.put(NETACNI_ODGOVORI, p.getNetacniString());
//		return db.update(PITANJA, values, ID + " = ?",
//				new String[] { String.valueOf(p.getId()) });
//	}
//
//	public void obrisiPitanje(Pitanje p) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(PITANJA, ID + " = ?",
//				new String[] { String.valueOf(p.getId()) });
//	}
//
//	// /////////////////////////////////////////////
//
//	// METODE ZA LEKCIJE
//
//	// /////////////////////////////////////////////
//	public void dodajLekciju(Lekcija l) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(NAZIV, l.getNaslov());
//		values.put(PITANJA, l.getPitanjaString());
//
//		db.insert(LEKCIJE, null, values);
//	}
//
//	public Lekcija vratiLekciju(int id) throws Exception {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(LEKCIJE, new String[] { ID, NAZIV, PITANJA },
//				ID + "=?", new String[] { String.valueOf(id) }, null, null,
//				null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//		else
//			throw new RuntimeException("Lista lekcija je prazna");
//		ArrayList<Pitanje> pitanja = new ArrayList<Pitanje>();
//		for (String s : cursor.getString(2).split("##")) {
//			pitanja.add(vratiPitanje(Integer.parseInt(s)));
//		}
//		Lekcija l = new Lekcija(cursor.getInt(0), cursor.getString(1), pitanja);
//		return l;
//	}
//
//    public int vratiIdLekcije() {
//        String selectQuery = "SELECT * FROM " + LEKCIJE;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToLast();
//        int a = cursor.getInt(0);
//        cursor.close();
//        return a;
//    }
//
//	public ArrayList<Lekcija> vratiSveLekcije() throws Exception {
//		ArrayList<Lekcija> listaLekcija = new ArrayList<Lekcija>();
//
//		String selectQuery = "SELECT * FROM " + LEKCIJE;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		while (cursor.moveToNext()) {
//			ArrayList<Pitanje> pitanja = new ArrayList<Pitanje>();
//			for (String s : cursor.getString(2).split("##")) {
//				pitanja.add(vratiPitanje(Integer.parseInt(s)));
//			}
//			listaLekcija.add(new Lekcija(cursor.getInt(0), cursor.getString(1),
//					pitanja));
//		}
//		if (listaLekcija.size() == 0) {
//			throw new RuntimeException("Lista pitanja je prazna");
//		}
//		return listaLekcija;
//	}
//
//	public int azurirajLekciju(Lekcija l) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(NAZIV, l.getNaslov());
//		values.put(PITANJA, l.getPitanjaString());
//		return db.update(LEKCIJE, values, ID + " = ?",
//				new String[] { String.valueOf(l.getId()) });
//	}
//
//	public void obrisiLekciju(Lekcija l) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(LEKCIJE, ID + " = ?",
//				new String[] { String.valueOf(l.getId()) });
//	}

}