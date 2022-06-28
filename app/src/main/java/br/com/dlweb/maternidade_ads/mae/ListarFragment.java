package br.com.dlweb.maternidade_ads.mae;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.dlweb.maternidade_ads.R;
import br.com.dlweb.maternidade_ads.database.DatabaseHelper;

public class ListarFragment extends Fragment {

    public ListarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.mae_fragment_listar, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ListView lv = v.findViewById(R.id.listViewMaes);
        databaseHelper.getAllMae(getActivity(), lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvId = view.findViewById(R.id.textViewIdListMae);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));

                EditarFragment editarFragment = new EditarFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameMae, editarFragment).commit();
            }
        });

        return v;
    }
}