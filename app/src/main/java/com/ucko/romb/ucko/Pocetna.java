package com.ucko.romb.ucko;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.*;
import android.view.View;

import java.util.ArrayList;

import baza.DatabaseHandler;
import okviri.Lekcija;


public class Pocetna extends ActionBarActivity {

    Button radiLekcije;
    Button novaLekcija;
    Button urediPostojece;
    Button opcije;
    Button izlaz;
    public static DatabaseHandler db;
    public static ArrayList<Lekcija> lekcije = new ArrayList<Lekcija>();

    private static char[] removeElements(char[] input, int deleteMe) {
        ArrayList<Character> result = new ArrayList<Character>();
        for (int i = 0; i < input.length; i++) {
            if (i != deleteMe) {
                result.add(input[i]);
            }
        }
        char[] k = new char[result.size()];
        for (int i = 0; i < result.size(); i++) {
            k[i] = result.get(i);
        }
        return k;
    }

    public static String izLatiniceUCirilicu(String s, boolean isPitanje) {
        s = s.toUpperCase();
        char[] n = s.toCharArray();
        for (int i = 0; i < n.length; i++) {
            switch (n[i]) {
                case 'A':
                    n[i] = 'A';
                    break;
                case 'B':
                    n[i] = 'Б';
                    break;
                case 'V':
                    n[i] = 'В';
                    break;
                case 'G':
                    n[i] = 'Г';
                    break;
                case 'D':
                    try {
                        if (n[i + 1] == 'J') {
                            n[i] = 'Ђ';
                            break;
                        } else if (n[i + 1] == 'Ž') {
                            n[i] = 'Џ';
                            break;
                        } else {
                            n[i] = 'Д';
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        n[i] = 'Д';
                        break;
                    }

                case 'Đ':
                    n[i] = 'Ђ';
                    break;
                case 'E':
                    n[i] = 'Е';
                    break;
                case 'Ž':
                    try {
                        if (n[i - 1] == 'Џ'){
                            n = removeElements(n, i);
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException ef){
                    }
                    n[i] = 'Ж';
                    break;
                case 'Z':
                    n[i] = 'З';
                    break;
                case 'I':
                    n[i] = 'И';
                    break;
                case 'J':
                    try {
                        if (n[i - 1] == 'Ђ' || n[i - 1] == 'Њ' || n[i - 1] == 'Љ'){
                            n = removeElements(n, i);
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException ef){
                    }
                        n[i] = 'Ј';
                        break;
                case 'K':
                    n[i] = 'К';
                    break;
                case 'L':
                    try {
                        if (n[i + 1] == 'J') {
                            n[i] = 'Љ';
                            break;
                        } else {
                            n[i] = 'Л';
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        n[i] = 'Л';
                        break;
                    }
                case 'M':
                    n[i] = 'М';
                    break;
                case 'N':
                    try {
                        if (n[i + 1] == 'J') {
                            n[i] = 'Њ';
                            break;
                        } else {
                            n[i] = 'Н';
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        n[i] = 'Н';
                        break;
                    }
                case 'O':
                    n[i] = 'О';
                    break;
                case 'P':
                    n[i] = 'П';
                    break;
                case 'R':
                    n[i] = 'Р';
                    break;
                case 'S':
                    n[i] = 'С';
                    break;
                case 'T':
                    n[i] = 'Т';
                    break;
                case 'Ć':
                    n[i] = 'Ћ';
                    break;
                case 'U':
                    n[i] = 'У';
                    break;
                case 'F':
                    n[i] = 'Ф';
                    break;
                case 'H':
                    n[i] = 'Х';
                    break;
                case 'C':
                    n[i] = 'Ц';
                    break;
                case 'Č':
                    n[i] = 'Ч';
                    break;
                case 'Š':
                    n[i] = 'Ш';
                    break;
            }
        }
        if (isPitanje == true) {
            String a = new String(n);
            a = a.toLowerCase();
            a = Character.toUpperCase(a.charAt(0)) + a.substring(1);
            return a;
        } else {
            String a = new String(n);
            return a;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);

        radiLekcije = (Button) findViewById(R.id.btnRadiLekcije);
        novaLekcija = (Button) findViewById(R.id.btnNapraviNovuLekciju);
        urediPostojece = (Button) findViewById(R.id.btnUrediPostojece);
        opcije = (Button) findViewById(R.id.btnOpcije);
        izlaz = (Button) findViewById(R.id.btnIzlaz);
        db = new DatabaseHandler(this);

        radiLekcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, PitanjaRad.class);
                /*i.putExtra("radjenje", true);
                i.putExtra("tabela", DatabaseHandler.LEKCIJE);*/
                startActivity(i);
            }
        });

        novaLekcija.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, ActvityLekcija.class);
                i.putExtra("nova", true);
                startActivity(i);
            }
        });

        urediPostojece.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, ActvityLekcija.class);
                i.putExtra("nova", false);
                i.putExtra("tabela", DatabaseHandler.LEKCIJE);
                startActivity(i);
            }
        });

        opcije.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Pocetna.this, Podesavanja.class);
                startActivity(i);
            }
        });

        izlaz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
