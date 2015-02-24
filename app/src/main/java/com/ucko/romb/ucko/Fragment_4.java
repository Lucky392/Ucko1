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
public class Fragment_4 extends Fragment {

    TableRow tr41, tr42, tr43, tr44;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tr41 = (TableRow)getView().findViewById(R.id.odg41);
        tr42 = (TableRow)getView().findViewById(R.id.odg42);
        tr43 = (TableRow)getView().findViewById(R.id.odg43);
        tr44 = (TableRow)getView().findViewById(R.id.odg44);

        tr41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tr44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
