package br.com.dlweb.maternidade_ads.medico;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.dlweb.maternidade_ads.R;
import br.com.dlweb.maternidade_ads.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    private EditText etNome;
    private EditText etEspecialidade;
    private EditText etCelular;
    private DatabaseHelper databaseHelper;
    private Medico m;

    public EditarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.medico_fragment_editar, container, false);

        etNome = v.findViewById(R.id.editText_nome_medico);
        etEspecialidade = v.findViewById(R.id.editText_especialidade_medico);
        etCelular = v.findViewById(R.id.editText_celular_medico);

        Bundle b = getArguments();
        int id_medico = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        m = databaseHelper.getByIdMedico(id_medico);

        etNome.setText(m.getNome());
        etEspecialidade.setText(m.getEspecialidade());
        etCelular.setText(m.getCelular());

        Button btnEditar = v.findViewById(R.id.button_editar_medico);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(id_medico);
            }
        });
        Button btnExcluir = v.findViewById(R.id.button_excluir_medico);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.excluir_medico_mensagem);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_medico);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Não faz nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }

    private void editar(int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome do médico", Toast.LENGTH_LONG).show();
        } else if (etEspecialidade.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a especialidade do médico", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número do celular do médico", Toast.LENGTH_LONG).show();
        } else {
            m = new Medico();
            m.setId(id);
            m.setNome(etNome.getText().toString());
            m.setEspecialidade(etEspecialidade.getText().toString());
            m.setCelular(etCelular.getText().toString());
            databaseHelper.updateMedico(m);
            Toast.makeText(getActivity(), "Médico atualizado", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_medico, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        m = new Medico();
        m.setId(id);
        databaseHelper.deleteMedico(m);
        Toast.makeText(getActivity(), "Médico excluído", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_medico, new ListarFragment()).commit();
    }
}