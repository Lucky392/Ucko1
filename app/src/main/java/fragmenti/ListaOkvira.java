package fragmenti;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ucko.romb.ucko.ActivityOdgovor;
import com.ucko.romb.ucko.ActivityPitanje;
import com.ucko.romb.ucko.ActvityLekcija;
import com.ucko.romb.ucko.MojAdapter;
import com.ucko.romb.ucko.Pitanja;
import com.ucko.romb.ucko.PitanjaRad;
import com.ucko.romb.ucko.R;
import com.ucko.romb.ucko.RadLekcije;

import java.util.ArrayList;
import java.util.List;

import baza.DatabaseHandler;
import okviri.Odgovor;
import okviri.Okvir;
import okviri.Pitanje;
import sesija.Kontroler;

/**
 * Created by Lucky on 20-Jun-15.
 */
public class ListaOkvira extends Fragment {

    public MojAdapter adapter;
    GridView gv;
    Okvir okvir;
    private String switchValue;
    private List<Okvir> okviri;

    public List<Okvir> getOkviri() {
        return okviri;
    }

    public void setSwitchValue(Okvir o) {
        switchValue = o.getIme();
    }

    public void setOkviri(List<Okvir> okviri) {
        this.okviri = okviri;
    }

    public GridView getGv() {
        return gv;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gv = (GridView) getView().findViewById(R.id.gridViewOkviri);
        if (okviri == null)
            okviri = new ArrayList<>();
        refreshGridView(okviri);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = ListaOkvira.this.getArguments();
                switch (switchValue) {
                    case DatabaseHandler.LEKCIJE:
                        if (b.getString("svrha").equals("rad")) {
                            Intent i = new Intent(getActivity(), PitanjaRad.class);
                            i.putExtra("pozicija", position);
                            startActivity(i);
                        } else {
                            popUp(position, okviri.get(position));
                        }
                        break;
                    case DatabaseHandler.PITANJA:
                        if (b != null && b.getString("svrha").equals("odabir")){
                            try {
                                Kontroler.getInstance().getPitanja().add(okviri.get(position));
                                ActvityLekcija.getLo().refreshGridView(Kontroler.getInstance().getPitanja());
                                getActivity().finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            popUp(position, okviri.get(position));
                        }
                        break;
                    case DatabaseHandler.ODGOVORI:
                        if (b != null && b.getString("svrha").equals("odabir")){
                            try {
                                Kontroler.getInstance().getOdgovori().add(okviri.get(position));
                                ActivityPitanje.getLo().refreshGridView(Kontroler.getInstance().getOdgovori());
                                ActivityOdgovor.aPitanje.refreshSpinner();
                                getActivity().finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            popUp(position, okviri.get(position));
                        }
                        break;
                }
            }
        });
    }

    public void refreshGridView(List<Okvir> lista) {
        ArrayList<String> l = new ArrayList<>();
        for (Okvir o : lista) {
            l.add(o.toString());
        }
        adapter = new MojAdapter(getActivity(), android.R.layout.simple_list_item_1, l);
        gv.setAdapter(adapter);
    }

    private void popUp(final int pozicija, final Okvir o) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Šta želite da uradite?")
                .setCancelable(true)
                .setPositiveButton("Izmeni", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = null;
                        switch (switchValue) {
                            case DatabaseHandler.LEKCIJE:
                                i = new Intent(getActivity(), ActvityLekcija.class);
                                break;
                            case DatabaseHandler.PITANJA:
                                i = new Intent(getActivity(), ActivityPitanje.class);
                                break;
                            case DatabaseHandler.ODGOVORI:
                                i = new Intent(getActivity(), ActivityOdgovor.class);
                                break;
                        }
                        i.putExtra("nov", "ne");
                        i.putExtra("svrha", "azuriranje");
                        i.putExtra("id", o.getId());
                        startActivity(i);
                    }
                })
                .setNeutralButton("Obriši", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (switchValue) {
                            case DatabaseHandler.LEKCIJE:
                                provera(pozicija);
                                break;
                            case DatabaseHandler.PITANJA:
                                Kontroler.getInstance().getPitanja().remove(pozicija);
                                refreshGridView(okviri);
                                break;
                            case DatabaseHandler.ODGOVORI:
                                Kontroler.getInstance().getOdgovori().remove(pozicija);
                                refreshGridView(okviri);
                                break;
                        }
                    }
                })
                .show();
    }

    public void provera(final int pozicija) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Da li ste sigurni?")
                .setCancelable(true)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Kontroler.getInstance().obrisiOkvir(Kontroler.getInstance().getOkviri().get(pozicija)) == 1) {
                            okviri.remove(pozicija);
                            refreshGridView(okviri);
                        } else {
                            Toast.makeText(getActivity(), "Neuspešno brisanje!", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNeutralButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
