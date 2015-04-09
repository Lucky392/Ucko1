package okviri;

public class Odgovor {

	int id;
	String text;
	String zvuk;
	String slika;
	
	public Odgovor(String text, String slika, String zvuk){
		this.text = text;
		this.slika = slika;
		this.zvuk = zvuk;
	}
	
	public Odgovor(int id, String text, String slika, String zvuk){
		this.id = id;
		this.text = text;
		this.slika = slika;
		this.zvuk = zvuk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
	
}
