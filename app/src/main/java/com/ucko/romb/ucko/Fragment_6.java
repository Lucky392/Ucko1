package com.ucko.romb.ucko;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

/**
 * Created by Ognjen on 2/23/2015.
 */
public class Fragment_6 extends Fragment {

    TableRow tr61, tr62, tr63, tr64, tr65, tr66;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tr61 = (TableRow)getView().findViewById(R.id.odg61);
        tr62 = (TableRow)getView().findViewById(R.id.odg62);
        tr63 = (TableRow)getView().findViewById(R.id.odg63);
        tr64 = (TableRow)getView().findViewById(R.id.odg64);
        tr65 = (TableRow)getView().findViewById(R.id.odg65);
        tr66 = (TableRow)getView().findViewById(R.id.odg66);

        tr61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.fragment_6, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
