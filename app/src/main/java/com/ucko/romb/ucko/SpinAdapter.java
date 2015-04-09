package com.ucko.romb.ucko;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> values;

    public SpinAdapter(Context context, int textViewResourceId,
                       ArrayList<String> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public String getItem(int position){
        return values.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position));
        label.setTextSize(20);
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position));
        label.setTextSize(20);
        return label;
    }
}