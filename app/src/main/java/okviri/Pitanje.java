package okviri;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import baza.DatabaseHandler;
import sesija.Kontroler;


public class Pitanje extends Okvir {

	String naslov, zvuk;
	Odgovor tacan;
	ArrayList<Odgovor> netacni;
	
	public Pitanje(int id, String naslov, String zvuk, Odgovor tacan,
			ArrayList<Okvir> okviri) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.zvuk = zvuk;
		this.tacan = tacan;
		netacni = new ArrayList<>();
		for (Okvir o : okviri){
			netacni.add((Odgovor)o);
		}
	}

	public Pitanje(String naslov, String zvuk, Odgovor tacan,
			List<Okvir> okviri) {
		super();
		this.naslov = naslov;
		this.zvuk = zvuk;
		this.tacan = tacan;
		netacni = new ArrayList<>();
		for (Okvir o : okviri){
			netacni.add((Odgovor)o);
		}
	}

	public Pitanje() {
	}

	public Pitanje(int id) {
		this.id = id;
	}

	@Override
	public String getIme() {
		return DatabaseHandler.PITANJA;
	}

	@Override
	public String[] vratiNizAtributa() {
		return new String[] { DatabaseHandler.ID, DatabaseHandler.NAZIV,
				DatabaseHandler.ZVUK, DatabaseHandler.TACAN_ODGOVOR, DatabaseHandler.NETACNI_ODGOVORI };
	}

	@Override
	public ContentValues getValues() {
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.NAZIV, naslov);
		values.put(DatabaseHandler.ZVUK, zvuk);
		values.put(DatabaseHandler.TACAN_ODGOVOR, tacan.getId());
		values.put(DatabaseHandler.NETACNI_ODGOVORI, getNetacniString());
		return values;
	}

	public String getNaslov() {
		return naslov;
	}

	public String getZvuk() {
		return zvuk;
	}

	public Odgovor getTacan() {
		return tacan;
	}

	public ArrayList<Odgovor> getNetacni() {
		return netacni;
	}
	
	public String getNetacniString() {
		String s = netacni.get(0).getId() + "";
		for (int i = 1; i < netacni.size(); i++) {
			s += "##" + netacni.get(i).getId();
		}
		return s;
	}

	@Override
	public String toString() {
		return naslov;
	}
}
