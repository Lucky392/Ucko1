package com.ucko.romb.ucko;

import java.util.LinkedList;

public class SkupLekcija extends Okvir {

	private int id;
	private String naziv;
	private LinkedList<Integer> lekcije;

	public SkupLekcija(int id, String naziv , String stringLekcija){
		this.id = id;
		this.naziv = naziv;
		lekcije = new LinkedList<Integer>();
		for (String i : stringLekcija.split("##")) {
			lekcije.add(Integer.parseInt(i));
		}
	}
	
	public int getId() {
		return id;
	}

	public String getNaziv() {
		return naziv;
	}

	public LinkedList<Integer> getLekcije() {
		return lekcije;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < lekcije.size() - 1; i++) {
			s += lekcije.get(i) + "##";
		}
		s += lekcije.getLast();
		return s;
	}

	@Override
	public String vratiNaslov() {
		return naziv;
	}
	
}
