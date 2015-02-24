package com.ucko.romb.ucko;

import java.util.LinkedList;

public class OkvirPitanje extends Okvir {

	private String zvuk;
	private int tacanOdgovor;
	private LinkedList<Integer> netacniOdgovori;

	public OkvirPitanje(int id, String pitanje, String zvuk, int tacanOdgovor,
                        String netacniOdgovori) {
		this.id = id;
		this.naziv = pitanje;
		this.tacanOdgovor = tacanOdgovor;
		this.zvuk = zvuk;
		this.netacniOdgovori = new LinkedList<Integer>();
		for (String in : netacniOdgovori.split("##")) {
			this.getNetacniOdgovori().add(Integer.parseInt(in));
		}
	}

	public OkvirPitanje(String pitanje, String zvuk, int tacanOdgovor,
                        String netacniOdgovori) {
		this.naziv = pitanje;
		this.tacanOdgovor = tacanOdgovor;
		this.zvuk = zvuk;
		this.netacniOdgovori = new LinkedList<Integer>();
        for (String in : netacniOdgovori.split("##")) {
            this.getNetacniOdgovori().add(Integer.parseInt(in));
        }
	}

	public String getZvuk() {
		return zvuk;
	}

	public int getTacanOdgovor() {
		return tacanOdgovor;
	}

	public LinkedList<Integer> getNetacniOdgovori() {
		return netacniOdgovori;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < getNetacniOdgovori().size() - 1; i++) {
			s += getNetacniOdgovori().get(i) + "##";
		}
		s += getNetacniOdgovori().getLast();
		return s;
	}
}
