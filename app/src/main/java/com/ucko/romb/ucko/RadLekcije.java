package com.ucko.romb.ucko;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import okviri.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okviri.Pitanje;
import sesija.Kontroler;

public class RadLekcije extends Fragment {

    OdgovorInterface odgovor;
    int brojOdgovora;
    Pitanje pitanje;
    TableRow tr21, tr22;
    TableRow tr31, tr32, tr33;
    TableRow tr41, tr42, tr43, tr44;
    TableRow tr51, tr52, tr53, tr54, tr55;
    TableRow tr61, tr62, tr63, tr64, tr65, tr66;
    List<Odgovor> odgovori;
    Odgovor tacan;
    int a = 0;
    String [] podesavanja;

    public interface OdgovorInterface {
        void onTacanClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int a = this.getArguments().getInt("tren");
        pitanje = (Pitanje)PitanjaRad.lekcija.getPitanja().get(a);

        tacan = pitanje.getTacan();
        try {
            odgovori = new ArrayList<>();
            for (Odgovor o : pitanje.getNetacni()){
                odgovori.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        odgovori.add(tacan);
        brojOdgovora = odgovori.size();
        Collections.shuffle(odgovori);

        View v;
        switch (brojOdgovora) {
            case 2 :
                v = inflater.inflate(R.layout.fragment_2, container, false);
                break;
            case 3 :
                v = inflater.inflate(R.layout.fragment_3, container, false);
                break;
            case 4 :
                v = inflater.inflate(R.layout.fragment_4, container, false);
                break;
            case 5 :
                v = inflater.inflate(R.layout.fragment_5, container, false);
                break;
            case 6 :
                v = inflater.inflate(R.layout.fragment_6, container, false);
                break;
            default:
                v = inflater.inflate(R.layout.fragment_6, container, false);
        }
        podesavanja = Pocetna.db.vratiPodesavanja();
        if (!podesavanja[2].equals("")){
            String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[2])));
            v.getRootView().setBackgroundColor(Color.parseColor(hexColor));
        }
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            odgovor = (OdgovorInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ActivityOdgovor");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton pitanjeSound = (ImageButton) getActivity().findViewById(R.id.imgBtnZvukPitanja);
        TextView pitanjeTextSound = (TextView) getActivity().findViewById(R.id.tvNaslovPitanja);
        pitanjeSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPitanje();
            }
        });
        pitanjeTextSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPitanje();
            }
        });
        if (!podesavanja[4].equals("")){
            pitanjeTextSound.setText(Pocetna.izLatiniceUCirilicu(pitanjeTextSound.getText().toString(), true));
        }
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        switch (brojOdgovora) {
            case 2 :
                tr21 = (TableRow) getView().findViewById(R.id.odg21);
                tr22 = (TableRow) getView().findViewById(R.id.odg22);
                tv1 = (TextView) getView().findViewById(R.id.textView21);
                tv2 = (TextView) getView().findViewById(R.id.textView22);
                if (!podesavanja[3].equals("")){
                    String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[3])));
                    tv1.setTextColor(Color.parseColor(hexColor));
                    tv2.setTextColor(Color.parseColor(hexColor));
                }
                setOdgovor((ImageView)getView().findViewById(R.id.imageview21), (TextView)getView().findViewById(R.id.textView21), odgovori.get(0));
                setOdgovor((ImageView)getView().findViewById(R.id.imageview22), (TextView)getView().findViewById(R.id.textView22), odgovori.get(1));
                if (!podesavanja[4].equals("")){
                    tv1.setText(Pocetna.izLatiniceUCirilicu(tv1.getText().toString(), false));
                    tv2.setText(Pocetna.izLatiniceUCirilicu(tv2.getText().toString(), false));
                }
                tr21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 1:
                                playSound(0);
                                if (odgovori.get(0).getId() == pitanje.getTacan().getId()){
                                    //zvuk radovanja
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    //zvuk tuge
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(0);
                                a = 1;
                        }
                    }
                });

                tr22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 2:
                                playSound(1);
                                if (odgovori.get(1).getId() == pitanje.getTacan().getId()){
                                    //zvuk radovanja
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    //zvuk tuge
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(1);
                                a = 2;
                        }
                    }
                });
                break;
            case 3 :
                tr31 = (TableRow)getView().findViewById(R.id.odg31);
                tr32 = (TableRow)getView().findViewById(R.id.odg32);
                tr33 = (TableRow)getView().findViewById(R.id.odg33);
                tv1 = (TextView) getView().findViewById(R.id.textView31);
                tv2 = (TextView) getView().findViewById(R.id.textView32);
                tv3 = (TextView) getView().findViewById(R.id.textView33);
                if (!podesavanja[3].equals("")){
                    String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[3])));
                    tv1.setTextColor(Color.parseColor(hexColor));
                    tv2.setTextColor(Color.parseColor(hexColor));
                    tv3.setTextColor(Color.parseColor(hexColor));
                }
                setOdgovor((ImageView)getView().findViewById(R.id.imageview31), (TextView)getView().findViewById(R.id.textView31), odgovori.get(0));
                setOdgovor((ImageView)getView().findViewById(R.id.imageview32), (TextView)getView().findViewById(R.id.textView32), odgovori.get(1));
                setOdgovor((ImageView)getView().findViewById(R.id.imageview33), (TextView)getView().findViewById(R.id.textView33), odgovori.get(2));
                if (!podesavanja[4].equals("")){
                    tv1.setText(Pocetna.izLatiniceUCirilicu(tv1.getText().toString(), false));
                    tv2.setText(Pocetna.izLatiniceUCirilicu(tv2.getText().toString(), false));
                    tv3.setText(Pocetna.izLatiniceUCirilicu(tv3.getText().toString(), false));
                }
                tr31.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 1:
                                playSound(0);
                                if (odgovori.get(0).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(0);
                                a = 1;
                        }
                    }
                });

                tr32.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 2:
                                playSound(1);
                                if (odgovori.get(1).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(1);
                                a = 2;
                        }
                    }
                });

                tr33.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 3:
                                playSound(2);
                                if (odgovori.get(2).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(2);
                                a = 3;
                        }
                    }
                });
                break;
            case 4 :
                tr41 = (TableRow)getView().findViewById(R.id.odg41);
                tr42 = (TableRow)getView().findViewById(R.id.odg42);
                tr43 = (TableRow)getView().findViewById(R.id.odg43);
                tr44 = (TableRow)getView().findViewById(R.id.odg44);
                tv1 = (TextView) getView().findViewById(R.id.textView41);
                tv2 = (TextView) getView().findViewById(R.id.textView42);
                tv3 = (TextView) getView().findViewById(R.id.textView43);
                tv4 = (TextView) getView().findViewById(R.id.textView44);
                if (!podesavanja[3].equals("")){
                    String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[3])));
                    tv1.setTextColor(Color.parseColor(hexColor));
                    tv2.setTextColor(Color.parseColor(hexColor));
                    tv3.setTextColor(Color.parseColor(hexColor));
                    tv4.setTextColor(Color.parseColor(hexColor));
                }
                setOdgovor((ImageView)getView().findViewById(R.id.imageview41), (TextView)getView().findViewById(R.id.textView41), odgovori.get(0));
                setOdgovor((ImageView)getView().findViewById(R.id.imageview42), (TextView)getView().findViewById(R.id.textView42), odgovori.get(1));
                setOdgovor((ImageView)getView().findViewById(R.id.imageview43), (TextView)getView().findViewById(R.id.textView43), odgovori.get(2));
                setOdgovor((ImageView) getView().findViewById(R.id.imageview44), (TextView) getView().findViewById(R.id.textView44), odgovori.get(3));
                if (!podesavanja[4].equals("")){
                    tv1.setText(Pocetna.izLatiniceUCirilicu(tv1.getText().toString(), false));
                    tv2.setText(Pocetna.izLatiniceUCirilicu(tv2.getText().toString(), false));
                    tv3.setText(Pocetna.izLatiniceUCirilicu(tv3.getText().toString(), false));
                    tv4.setText(Pocetna.izLatiniceUCirilicu(tv4.getText().toString(), false));
                }
                tr41.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 1:
                                playSound(0);
                                if (odgovori.get(0).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(0);
                                a = 1;
                        }
                    }
                });
                tr42.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 2:
                                playSound(1);
                                if (odgovori.get(1).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(1);
                                a = 2;
                        }
                    }
                });
                tr43.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 3:
                                playSound(2);
                                if (odgovori.get(2).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(2);
                                a = 3;
                        }
                    }
                });
                tr44.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 4:
                                playSound(3);
                                if (odgovori.get(3).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(3);
                                a = 4;
                        }
                    }
                });
                break;
            case 5 :
                tr51 = (TableRow)getView().findViewById(R.id.odg51);
                tr52 = (TableRow)getView().findViewById(R.id.odg52);
                tr53 = (TableRow)getView().findViewById(R.id.odg53);
                tr54 = (TableRow)getView().findViewById(R.id.odg54);
                tr55 = (TableRow)getView().findViewById(R.id.odg55);
                tv1 = (TextView) getView().findViewById(R.id.textView51);
                tv2 = (TextView) getView().findViewById(R.id.textView52);
                tv3 = (TextView) getView().findViewById(R.id.textView53);
                tv4 = (TextView) getView().findViewById(R.id.textView54);
                tv5 = (TextView) getView().findViewById(R.id.textView55);
                if (!podesavanja[3].equals("")){
                    String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[3])));
                    tv1.setTextColor(Color.parseColor(hexColor));
                    tv2.setTextColor(Color.parseColor(hexColor));
                    tv3.setTextColor(Color.parseColor(hexColor));
                    tv4.setTextColor(Color.parseColor(hexColor));
                    tv5.setTextColor(Color.parseColor(hexColor));
                }
                setOdgovor((ImageView)getView().findViewById(R.id.imageView51), (TextView)getView().findViewById(R.id.textView51), odgovori.get(0));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView52), (TextView)getView().findViewById(R.id.textView52), odgovori.get(1));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView53), (TextView)getView().findViewById(R.id.textView53), odgovori.get(2));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView54), (TextView)getView().findViewById(R.id.textView54), odgovori.get(3));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView55), (TextView)getView().findViewById(R.id.textView55), odgovori.get(4));
                if (!podesavanja[4].equals("")){
                    tv1.setText(Pocetna.izLatiniceUCirilicu(tv1.getText().toString(), false));
                    tv2.setText(Pocetna.izLatiniceUCirilicu(tv2.getText().toString(), false));
                    tv3.setText(Pocetna.izLatiniceUCirilicu(tv3.getText().toString(), false));
                    tv4.setText(Pocetna.izLatiniceUCirilicu(tv4.getText().toString(), false));
                    tv5.setText(Pocetna.izLatiniceUCirilicu(tv5.getText().toString(), false));
                }
                tr51.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 1:
                                playSound(0);
                                if (odgovori.get(0).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(0);
                                a = 1;
                        }
                    }
                });
                tr52.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 2:
                                playSound(1);
                                if (odgovori.get(1).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(1);
                                a = 2;
                        }
                    }
                });
                tr53.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 3:
                                playSound(2);
                                if (odgovori.get(2).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(2);
                                a = 3;
                        }
                    }
                });
                tr54.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 4:
                                playSound(3);
                                if (odgovori.get(3).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(3);
                                a = 4;
                        }
                    }
                });
                tr55.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 5:
                                playSound(4);
                                if (odgovori.get(4).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(4);
                                a = 5;
                        }
                    }
                });
                break;
            case 6 :
                tr61 = (TableRow)getView().findViewById(R.id.odg61);
                tr62 = (TableRow)getView().findViewById(R.id.odg62);
                tr63 = (TableRow)getView().findViewById(R.id.odg63);
                tr64 = (TableRow)getView().findViewById(R.id.odg64);
                tr65 = (TableRow)getView().findViewById(R.id.odg65);
                tr66 = (TableRow)getView().findViewById(R.id.odg66);
                tv1 = (TextView) getView().findViewById(R.id.textView61);
                tv2 = (TextView) getView().findViewById(R.id.textView62);
                tv3 = (TextView) getView().findViewById(R.id.textView63);
                tv4 = (TextView) getView().findViewById(R.id.textView64);
                tv5 = (TextView) getView().findViewById(R.id.textView65);
                tv6 = (TextView) getView().findViewById(R.id.textView66);
                if (!podesavanja[3].equals("")){
                    String hexColor = String.format("#%06X", (0xFFFFFF & Integer.parseInt(podesavanja[3])));
                    tv1.setTextColor(Color.parseColor(hexColor));
                    tv2.setTextColor(Color.parseColor(hexColor));
                    tv3.setTextColor(Color.parseColor(hexColor));
                    tv4.setTextColor(Color.parseColor(hexColor));
                    tv5.setTextColor(Color.parseColor(hexColor));
                    tv6.setTextColor(Color.parseColor(hexColor));
                }
                setOdgovor((ImageView)getView().findViewById(R.id.imageView61), (TextView)getView().findViewById(R.id.textView61), odgovori.get(0));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView62), (TextView)getView().findViewById(R.id.textView62), odgovori.get(1));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView63), (TextView)getView().findViewById(R.id.textView63), odgovori.get(2));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView64), (TextView)getView().findViewById(R.id.textView64), odgovori.get(3));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView65), (TextView)getView().findViewById(R.id.textView65), odgovori.get(4));
                setOdgovor((ImageView)getView().findViewById(R.id.imageView66), (TextView)getView().findViewById(R.id.textView66), odgovori.get(5));
                if (!podesavanja[4].equals("")){
                    tv1.setText(Pocetna.izLatiniceUCirilicu(tv1.getText().toString(), false));
                    tv2.setText(Pocetna.izLatiniceUCirilicu(tv2.getText().toString(), false));
                    tv3.setText(Pocetna.izLatiniceUCirilicu(tv3.getText().toString(), false));
                    tv4.setText(Pocetna.izLatiniceUCirilicu(tv4.getText().toString(), false));
                    tv5.setText(Pocetna.izLatiniceUCirilicu(tv5.getText().toString(), false));
                    tv6.setText(Pocetna.izLatiniceUCirilicu(tv6.getText().toString(), false));
                }
                tr61.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 1:
                                playSound(0);
                                if (odgovori.get(0).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(0);
                                a = 1;
                        }
                    }
                });
                tr62.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 2:
                                playSound(1);
                                if (odgovori.get(1).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(1);
                                a = 2;
                        }
                    }
                });
                tr63.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 3:
                                playSound(2);
                                if (odgovori.get(2).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(2);
                                a = 3;
                        }
                    }
                });
                tr64.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 4:
                                playSound(3);
                                if (odgovori.get(3).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(3);
                                a = 4;
                        }
                    }
                });
                tr65.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 5:
                                playSound(4);
                                if (odgovori.get(4).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(4);
                                a = 5;
                        }
                    }
                });
                tr66.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (a){
                            case 6:
                                playSound(5);
                                if (odgovori.get(5).getId() == pitanje.getTacan().getId()){
                                    playTacanNetacan(true);
                                    odgovor.onTacanClicked();
                                } else {
                                    playTacanNetacan(false);
                                }
                                break;
                            default:
                                playSound(5);
                                a = 6;
                        }
                    }
                });
                break;
        }
    }

    private void setOdgovor(ImageView iv, TextView tv, Odgovor odg){
        iv.setImageBitmap(Kontroler.getInstance().loadImageFromStorage(odg.getSlika()));
        tv.setText(odg.getText());
    }

    private void playPitanje(){
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(pitanje.getZvuk());
            mp.prepare();
            mp.start();
            long duration = mp.getDuration();
            SystemClock.sleep(duration);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Morate ponovo da dodate zvuk za pitanje!", Toast.LENGTH_SHORT).show();
        } finally {
            mp.release();
            mp = null;
        }
    }

    private void playSound(int i){
        String file = odgovori.get(i).getZvuk();
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(file);
            mp.prepare();
            mp.start();
            long duration = mp.getDuration();
            SystemClock.sleep(duration);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Greska!", Toast.LENGTH_SHORT).show();
        } finally {
            mp.release();
            mp = null;
        }
    }

    private void playTacanNetacan(boolean tn){
        MediaPlayer mp = null;
        try {
            if (tn){
                if (podesavanja[0] == null || podesavanja[0].equals("d")){
                    mp = MediaPlayer.create(getActivity(), R.raw.cheer);
                } else {
                    mp = new MediaPlayer();
                    mp.setDataSource(podesavanja[0]);
                    mp.prepare();
                }
            } else {
                if (podesavanja[1] == null || podesavanja[1].equals("d")){
                    mp = MediaPlayer.create(getActivity(), R.raw.pain);
                } else {
                    mp = new MediaPlayer();
                    mp.setDataSource(podesavanja[1]);
                    mp.prepare();
                }
            }

            mp.start();
            long duration = mp.getDuration();
            SystemClock.sleep(duration);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Morate ponovo da dodate zvuk za odgovore!", Toast.LENGTH_SHORT).show();
        } finally {
            mp.release();
            mp = null;
        }
    }


}
