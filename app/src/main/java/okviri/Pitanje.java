package okviri;
import java.util.ArrayList;


public class Pitanje {

	int id;
	String naslov, zvuk;
	Odgovor tacan;
	ArrayList<Odgovor> netacni;
	
	public Pitanje(int id, String naslov, String zvuk, Odgovor tacan,
			ArrayList<Odgovor> netacni) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.zvuk = zvuk;
		this.tacan = tacan;
		this.netacni = netacni;
	}

	public Pitanje(String naslov, String zvuk, Odgovor tacan,
			ArrayList<Odgovor> netacni) {
		super();
		this.naslov = naslov;
		this.zvuk = zvuk;
		this.tacan = tacan;
		this.netacni = netacni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
}
