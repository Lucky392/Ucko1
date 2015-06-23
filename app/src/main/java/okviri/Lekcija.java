package okviri;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import baza.DatabaseHandler;
import sesija.Kontroler;


public class Lekcija extends Okvir {

	String naslov;
	List<Okvir> pitanja;

	public Lekcija(int id, String naslov, List<Okvir> pitanja) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.pitanja = new ArrayList<>();
		for (Okvir o : pitanja){
			this.pitanja.add((Pitanje)o);
		}
	}
	
	public Lekcija(String naslov, List<Okvir> pitanja) {
		super();
		this.naslov = naslov;
		this.pitanja = pitanja;
	}

	public Lekcija() {
	}

	public Lekcija(int id) {
		this.id = id;
	}

	@Override
	public String getIme() {
		return DatabaseHandler.LEKCIJE;
	}

	@Override
	public String[] vratiNizAtributa() {
		return new String[] { DatabaseHandler.ID, DatabaseHandler.NAZIV, DatabaseHandler.PITANJA };
	}

	@Override
	public ContentValues getValues() {
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.NAZIV, naslov);
		values.put(DatabaseHandler.PITANJA, getPitanjaString());
		return values;
	}

	public String getNaslov() {
		return naslov;
	}

	public List<Okvir> getPitanja() {
		return pitanja;
	}
	
	public String getPitanjaString(){
		String s = pitanja.get(0).getId() + "";
		for (int i = 1; i < pitanja.size(); i++) {
			s += "##" + pitanja.get(i).getId();
		}
		return s;
	}

	@Override
	public String toString() {
		return naslov;
	}
}
