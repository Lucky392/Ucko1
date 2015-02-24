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

        Button snimiZvukTacnog = (Button) findViewById(R.id.btnSnimiTacan);
        Button slusajZvukTacnog = (Button) findViewById(R.id.btnSlusajTacan);
        Button vratiZvukTacnogNaPodrazumevano = (Button) findViewById(R.id.btnVratiNaPodrazumevaniTacni);

        Button snimiZvukNetacnog = (Button) findViewById(R.id.btnSnimiNetacni);
        Button slusajZvukNetacnog = (Button) findViewById(R.id.btnSlusajNetacni);
        Button vratiZvukNetacnogNaPodrazumevano = (Button) findViewById(R.id.btnVratiNaPodrazumevanNetacni);



        Button promeniSlova = (Button) findViewById(R.id.btnOdaberiSlova);
        Button bojaPozadine = (Button) findViewById(R.id.btnOdaberiBojuPozadine);

        Button bojaTeksta = (Button) findViewById(R.id.btnOdaberiBojuTeksta);

        podesavanja = Pocetna.db.vratiPodesavanja();
        mPaint = new Paint();

        snimiZvukTacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        promeniSlova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        slusajZvukTacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vratiZvukTacnogNaPodrazumevano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        vratiZvukNetacnogNaPodrazumevano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        snimiZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        slusajZvukNetacnog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bojaPozadine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog c = new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor());
                c.setCancelable(true);
                c.show();
            }
        });

        bojaTeksta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog c = new ColorPickerDialog(Podesavanja.this, Podesavanja.this, mPaint.getColor());
                c.setCancelable(true);
                c.show();
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
