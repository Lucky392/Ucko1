package com.ucko.romb.ucko;

import android.content.Intent;

import java.util.ArrayList;
import java.util.LinkedList;

public class OkvirLekcija extends Okvir {

	private ArrayList<Integer> pitanja;

	public OkvirLekcija(int id, String naziv, String stringLekcija){
		this.id = id;
		this.naziv = naziv;
		pitanja = new ArrayList<Integer>();
		for (String i : stringLekcija.split("##")) {
			pitanja.add(Integer.parseInt(i));
		}
	}

    public OkvirLekcija(String naziv, ArrayList<Okvir> pitanja){
        this.naziv = naziv;
        this.pitanja = new ArrayList<Integer>();
        for (int i = 0; i < pitanja.size(); i++) {
            this.pitanja.add(pitanja.get(i).getId());
        }
    }

	@Override
	public String toString() {
		String s = "";
        s += pitanja.get(0);
		for (int i = 1; i < pitanja.size(); i++) {
			s +=  "##" + pitanja.get(i);
		}
		return s;
	}
	
}
