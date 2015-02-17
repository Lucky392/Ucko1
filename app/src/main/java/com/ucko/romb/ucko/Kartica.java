package com.ucko.romb.ucko;

public class Kartica extends Okvir {
	
	private String naslov;
	private String slika;
	private String zvuk;
	private int id;
	
	public int getId() {
		return id;
	}

	public Kartica(String naslov, String slika, String zvuk) {
		this.naslov = naslov;
		this.slika = slika;
		this.zvuk = zvuk;
	}
	
	public Kartica(int id, String naslov, String slika, String zvuk){
		this.id = id;
		this.naslov = naslov;
		this.slika = slika;
		this.zvuk = zvuk;
	}

	public String getNaslov() {
		return naslov;
	}

	public String getSlika() {
		return slika;
	}

	public String getZvuk() {
		return zvuk;
	}

	@Override
	public String vratiNaslov() {
		return naslov;
	}
}
