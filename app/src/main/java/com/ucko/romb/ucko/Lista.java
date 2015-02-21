package com.ucko.romb.ucko;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Lista extends ListFragment implements OnItemClickListener {

    List<String> lista = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List<Tuple> tl = db.vratiOkvire(getActivity().getIntent().getStringExtra("tabela"));
        for (Tuple t : tl){
            lista.add(t.getS());
        }

        MojAdapter adapter = new MojAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(getActivity(), "Item " + position, Toast.LENGTH_SHORT).show();
    }
}

