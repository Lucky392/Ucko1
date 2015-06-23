package okviri;

import android.content.ContentValues;

import baza.DatabaseHandler;
import sesija.Kontroler;

public class Odgovor extends Okvir {

	String text;
	String zvuk;
	String slika;
	
	public Odgovor(String text, String slika, String zvuk){
		this.text = text;
		this.slika = slika;
		this.zvuk = zvuk;
	}

	public Odgovor() {
	}

	public Odgovor(int id) {
		this.id = id;
	}

	public Odgovor(int id, String text, String slika, String zvuk){
		this.id = id;
		this.text = text;
		this.slika = slika;
		this.zvuk = zvuk;
	}

	@Override
	public String getIme() {
		return DatabaseHandler.ODGOVORI;
	}

	@Override
	public String[] vratiNizAtributa() {
		return new String[] { DatabaseHandler.ID, DatabaseHandler.NAZIV, DatabaseHandler.SLIKA, DatabaseHandler.ZVUK };
	}

	@Override
	public ContentValues getValues() {
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.NAZIV, text);
		values.put(DatabaseHandler.SLIKA, slika);
		values.put(DatabaseHandler.ZVUK, zvuk);
		return values;
	}

	public String getText() {
		return text;
	}

	public String getZvuk() {
		return zvuk;
	}

	public String getSlika() {
		return slika;
	}

	@Override
	public String toString() {
		return text;
	}
}
