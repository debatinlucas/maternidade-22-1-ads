package br.com.dlweb.maternidade_ads.bebe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.dlweb.maternidade_ads.R;
import br.com.dlweb.maternidade_ads.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    EditText etNome;
    EditText etDataNascimento;
    EditText etPeso;
    EditText etAltura;
    Spinner spMae;
    Spinner spMedico;
    ArrayList<Integer> listMaeId;
    ArrayList<String> listMaeName;
    ArrayList<Integer> listMedicoId;
    ArrayList<String> listMedicoName;
    DatabaseHelper databaseHelper;
    Bebe b;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bebe_fragment_editar, container, false);
        Bundle bundle = getArguments();
        int id_bebe = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        b = databaseHelper.getByIdBebe(id_bebe);

        spMae = v.findViewById(R.id.spinnerMaeBebe);
        spMedico = v.findViewById(R.id.spinnerMedicoBebe);
        etNome = v.findViewById(R.id.editTextNomeBebe);
        etDataNascimento = v.findViewById(R.id.editTextDataNascimentoBebe);
        etPeso = v.findViewById(R.id.editTextPesoBebe);
        etAltura = v.findViewById(R.id.editTextAlturaBebe);

        listMaeId = new ArrayList<>();
        listMaeName = new ArrayList<>();
        databaseHelper.getAllNameMae(listMaeId, listMaeName);

        listMedicoId = new ArrayList<>();
        listMedicoName = new ArrayList<>();
        databaseHelper.getAllNameMedico(listMedicoId, listMedicoName);

        ArrayAdapter<String> spMaeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMaeName);
        spMae.setAdapter(spMaeArrayAdapter);

        ArrayAdapter<String> spMedicoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listMedicoName);
        spMedico.setAdapter(spMedicoArrayAdapter);

        etNome.setText(b.getNome());
        etDataNascimento.setText(b.getData_nascimento());
        etPeso.setText(String.valueOf(b.getPeso()));
        etAltura.setText(String.valueOf(b.getAltura()));
        spMae.setSelection(listMaeId.indexOf(b.getId_mae()));
        spMedico.setSelection(listMedicoId.indexOf(b.getId_medico()));

        Button btnEditar = v.findViewById(R.id.buttonEditarBebe);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_bebe);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirBebe);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_bebe);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_bebe);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDataNascimento.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data nascimento!", Toast.LENGTH_LONG).show();
        } else if (etPeso.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o peso!", Toast.LENGTH_LONG).show();
        } else if (etAltura.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o altura!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            Bebe b = new Bebe();
            b.setId(id);
            String nomeMae = spMae.getSelectedItem().toString();
            b.setId_mae(listMaeId.get(listMaeName.indexOf(nomeMae)));
            String nomeMedico = spMedico.getSelectedItem().toString();
            b.setId_medico(listMedicoId.get(listMedicoName.indexOf(nomeMedico)));
            b.setNome(etNome.getText().toString());
            b.setData_nascimento(etDataNascimento.getText().toString());
            b.setPeso(Float.parseFloat(etPeso.getText().toString()));
            b.setAltura(Integer.parseInt(etAltura.getText().toString()));
            databaseHelper.updateBebe(b);
            Toast.makeText(getActivity(), "Bebê editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameBebe, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        b = new Bebe();
        b.setId(id);
        databaseHelper.deleteBebe(b);
        Toast.makeText(getActivity(), "Bebê excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameBebe, new ListarFragment()).commit();
    }
}