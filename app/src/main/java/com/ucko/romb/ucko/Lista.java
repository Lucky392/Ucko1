package com.ucko.romb.ucko;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Lista extends Fragment {

    public static ArrayList<String> lista = new ArrayList<String>();
    public static boolean readFromDatabase;
    public static MojAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!readFromDatabase){
            for (Okvir o : Pocetna.okviri){
                lista.add(o.getNaziv());
            }
        } else {
            DatabaseHandler db = new DatabaseHandler(getActivity());
            List<Tuple> tl = db.vratiOkvire(getActivity().getIntent().getStringExtra("tabela"));
            for (Tuple t : tl){
                lista.add(t.getS());
            }
        }

        adapter = new MojAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);
        if (lista.size() == 0) {
            if (readFromDatabase){
                readFromDatabase = false;
                Toast.makeText(getActivity(), "Lista je prazna", Toast.LENGTH_SHORT).show();
                return;
            }
            readFromDatabase = false;
        }
        GridView gv = (GridView) getView().findViewById(R.id.gridViewOkviri);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Item " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

