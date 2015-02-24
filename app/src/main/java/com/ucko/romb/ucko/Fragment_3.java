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
public class Fragment_3 extends Fragment {

    TableRow tr31, tr32, tr33;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tr31 = (TableRow)getView().findViewById(R.id.odg31);
        tr32 = (TableRow)getView().findViewById(R.id.odg32);
        tr33 = (TableRow)getView().findViewById(R.id.odg33);

        tr31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tr32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tr33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
