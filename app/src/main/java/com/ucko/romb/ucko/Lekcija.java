package com.ucko.romb.ucko;

import java.util.LinkedList;

public class Lekcija extends Okvir {

	private int id;
	private String pitanje;
	private String zvuk;
	private int tacanOdgovor;
	private LinkedList<Integer> netacniOdgovori;

	public Lekcija(int id, String pitanje, String zvuk, int tacanOdgovor,
			int ... netacniOdgovori) {
		this.id = id;
		this.pitanje = pitanje;
		this.tacanOdgovor = tacanOdgovor;
		this.zvuk = zvuk;
		this.netacniOdgovori = new LinkedList<Integer>();
		for (int in : netacniOdgovori) {
			this.netacniOdgovori.add(in);
		}
	}

	public Lekcija(String pitanje, String zvuk, int tacanOdgovor,
			int ... netacniOdgovori) {
		this.pitanje = pitanje;
		this.tacanOdgovor = tacanOdgovor;
		this.zvuk = zvuk;
		this.netacniOdgovori = new LinkedList<Integer>();
		for (int in : netacniOdgovori) {
			this.netacniOdgovori.add(in);
		}
	}

	public int getId() {
		return id;
	}

	public String getPitanje() {
		return pitanje;
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
		for (int i = 0; i < netacniOdgovori.size() - 1; i++) {
			s += netacniOdgovori.get(i) + "##";
		}
		s += netacniOdgovori.getLast();
		return s;
	}

	@Override
	public String vratiNaslov() {
		return pitanje;
	}
}
