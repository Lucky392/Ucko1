package okviri;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by Lucky on 20-Jun-15.
 */
public abstract class Okvir {

    protected int id;

    public abstract String getIme();
    public abstract String [] vratiNizAtributa();
    public abstract ContentValues getValues();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
