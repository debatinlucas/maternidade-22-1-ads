package br.com.dlweb.maternidade_ads.medico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.dlweb.maternidade_ads.R;

public class AdicionarFragment extends Fragment {

    public AdicionarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.medico_fragment_adicionar, container, false);

        Button btnAdicionar = v.findViewById(R.id.button_adicionar_medico);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_medico, new ListarFragment()).commit();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}