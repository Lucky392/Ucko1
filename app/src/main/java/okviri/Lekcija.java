package okviri;
import java.util.ArrayList;


public class Lekcija {

	int id;
	String naslov;
	ArrayList<Pitanje> pitanja;
	
	public Lekcija(int id, String naslov, ArrayList<Pitanje> pitanja) {
		super();
		this.id = id;
		this.naslov = naslov;
		this.pitanja = pitanja;
	}
	
	public Lekcija(String naslov, ArrayList<Pitanje> pitanja) {
		super();
		this.naslov = naslov;
		this.pitanja = pitanja;
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

	public ArrayList<Pitanje> getPitanja() {
		return pitanja;
	}
	
	public String getPitanjaString(){
		String s = pitanja.get(0).getId() + "";
		for (int i = 1; i < pitanja.size(); i++) {
			s += "##" + pitanja.get(i).getId();
		}
		return s;
	}
	
}
