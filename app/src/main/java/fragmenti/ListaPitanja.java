package fragmenti;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.ucko.romb.ucko.ActvityLekcija;
import com.ucko.romb.ucko.MojAdapter;
import com.ucko.romb.ucko.Pocetna;
import com.ucko.romb.ucko.R;
import java.util.ArrayList;
import okviri.Pitanje;

public class ListaPitanja extends Fragment {

    public ArrayList<String> lista = new ArrayList<String>();
    public boolean readFromDatabase;
    public MojAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!readFromDatabase){
            for (Pitanje op : ActvityLekcija.pitanja){
                lista.add(op.getNaslov());
            }
        } else {
            ArrayList<Pitanje> p = new ArrayList<Pitanje>();
            try {
                p = Pocetna.db.vratiSvaPitanja();
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            for (Pitanje o : p){
                lista.add(o.getNaslov());
            }
        }

        adapter = new MojAdapter(getActivity(), android.R.layout.simple_list_item_1, lista);
        if (lista.size() == 0) {
            if (readFromDatabase){
                readFromDatabase = false;
                Toast.makeText(getActivity(), "Lista je prazna", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        GridView gv = (GridView) getView().findViewById(R.id.gridViewOkviri);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Item " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
