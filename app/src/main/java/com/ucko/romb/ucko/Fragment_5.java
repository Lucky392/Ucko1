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
public class Fragment_5 extends Fragment {

    TableRow tr51, tr52, tr53, tr54, tr55;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tr51 = (TableRow)getView().findViewById(R.id.odg51);
        tr52 = (TableRow)getView().findViewById(R.id.odg52);
        tr53 = (TableRow)getView().findViewById(R.id.odg53);
        tr54 = (TableRow)getView().findViewById(R.id.odg54);
        tr55 = (TableRow)getView().findViewById(R.id.odg55);

        tr51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return inflater.inflate(R.layout.fragment_5, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
