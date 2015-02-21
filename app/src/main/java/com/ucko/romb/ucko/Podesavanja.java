package com.ucko.romb.ucko;

import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Podesavanja extends ActionBarActivity implements ColorPickerDialog.OnColorChangedListener {

    private Paint mPaint;
    private String [] podesavanja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podesavanja);

        Button zvukRadovanja = (Button) findViewById(R.id.btnZvukRadovanja);
        Button promeniSlova = (Button) findViewById(R.id.btnSlova);
        Button bojaPozadine = (Button) findViewById(R.id.btnBojaPozadine);
        Button bojaDugmeta = (Button) findViewById(R.id.btnBojaDugmeta);
        Button bojaTeksta = (Button) findViewById(R.id.btnBojaTeksta);



        podesavanja = Pocetna.db.vratiPodesavanja();

        zvukRadovanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TREBA SMISLITI KAKO DA SE DODAJE ZVUK ZA RADOVANJE!!!
            }
        });

        promeniSlova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPaint = new Paint();
        bojaDugmeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor()).show();
            }
        });

        bojaPozadine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Podesavanja.this, mPaint.getColor() + "", Toast.LENGTH_SHORT).show();
            }
        });

        bojaTeksta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_podesavanja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void colorChanged(int color) {
        mPaint.setColor(color);
    }
}
