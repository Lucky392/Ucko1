package com.ucko.romb.ucko;

import java.util.LinkedList;

public class OkvirLekcija extends Okvir {

	private LinkedList<Integer> pitanja;

	public OkvirLekcija(int id, String naziv, String stringLekcija){
		this.id = id;
		this.naziv = naziv;
		pitanja = new LinkedList<Integer>();
		for (String i : stringLekcija.split("##")) {
			pitanja.add(Integer.parseInt(i));
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < pitanja.size() - 1; i++) {
			s += pitanja.get(i) + "##";
		}
		s += pitanja.getLast();
		return s;
	}
	
}
