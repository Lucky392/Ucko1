package com.ucko.romb.ucko;

import java.util.ArrayList;

public class OkvirLekcija extends Okvir {

	private ArrayList<Integer> pitanja;

	public OkvirLekcija(int id, String naziv, String stringLekcija){
		this.id = id;
		this.naziv = naziv;
		pitanja = new ArrayList<Integer>();
		for (String i : stringLekcija.split("##")) {
			getPitanja().add(Integer.parseInt(i));
		}
	}

    public OkvirLekcija(String naziv, ArrayList<Okvir> pitanja){
        this.naziv = naziv;
        this.pitanja = new ArrayList<Integer>();
        for (int i = 0; i < pitanja.size(); i++) {
            this.getPitanja().add(pitanja.get(i).getId());
        }
    }

	@Override
	public String toString() {
		String s = "";
        s += getPitanja().get(0);
		for (int i = 1; i < getPitanja().size(); i++) {
			s +=  "##" + getPitanja().get(i);
		}
		return s;
	}

    public ArrayList<Integer> getPitanja() {
        return pitanja;
    }
}
