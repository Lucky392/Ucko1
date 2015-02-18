package com.ucko.romb.ucko;

public class OkvirOdgovor extends Okvir {

	private String slika;
	private String zvuk;
	
	public int getId() {
		return id;
	}

	public OkvirOdgovor(String naslov, String slika, String zvuk) {
		this.naziv = naslov;
		this.slika = slika;
		this.zvuk = zvuk;
	}
	
	public OkvirOdgovor(int id, String naslov, String slika, String zvuk){
		this.id = id;
		this.naziv = naslov;
		this.slika = slika;
		this.zvuk = zvuk;
	}

	public String getSlika() {
		return slika;
	}

	public String getZvuk() {
		return zvuk;
	}
}
