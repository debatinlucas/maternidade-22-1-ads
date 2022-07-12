package br.com.dlweb.maternidade_ads.mae;

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

import java.util.concurrent.ExecutionException;

import br.com.dlweb.maternidade_ads.R;
import br.com.dlweb.maternidade_ads.database.DatabaseHelper;
import br.com.dlweb.maternidade_ads.webservice.DadosEndereco;
import br.com.dlweb.maternidade_ads.webservice.RetornarEnderecoPeloCep;

public class EditarFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private Mae m;
    private EditText etNome;
    private EditText etCep;
    private EditText etLogradouro;
    private EditText etNumero;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etFixo;
    private EditText etCelular;
    private EditText etComercial;
    private EditText etDataNascimento;

    public EditarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.mae_fragment_editar, container, false);

        // Recupera o valor do ID enviado por parâmetro ao clicar no ListarFragment
        Bundle b = getArguments();
        int id_mae = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());

        // Busca os dados da mãe do valor do ID
        m = databaseHelper.getByIdMae(id_mae);

        // localiza os elementos da interface pelo ID (definidos no arquivo XML)
        etNome = v.findViewById(R.id.editTextNomeMae);
        etCep = v.findViewById(R.id.editTextCepMae);
        etLogradouro = v.findViewById(R.id.editTextLogradouroMae);
        etNumero = v.findViewById(R.id.editTextNumeroMae);
        etBairro = v.findViewById(R.id.editTextBairroMae);
        etCidade = v.findViewById(R.id.editTextCidadeMae);
        etFixo = v.findViewById(R.id.editTextFixoMae);
        etCelular = v.findViewById(R.id.editTextCelularMae);
        etComercial = v.findViewById(R.id.editTextComercialMae);
        etDataNascimento = v.findViewById(R.id.editTextDataNascimentoMae);

        // Seta nos campos da interface os valores do banco de dados do ID da mãe, recuperado via parâmetro
        etNome.setText(m.getNome());
        etCep.setText(m.getCep());
        etLogradouro.setText(m.getLogradouro());
        etNumero.setText(String.valueOf(m.getNumero()));
        etBairro.setText(m.getBairro());
        etCidade.setText(m.getCidade());
        etFixo.setText(m.getFixo());
        etCelular.setText(m.getCelular());
        etComercial.setText(m.getComercial());
        etDataNascimento.setText(m.getData_nascimento());

        // Quando o foco sair do campo do CEP, é executado o WS para recuperar os dados do CEP informado
        etCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (m.getCep() != etCep.getText().toString()) {
                        try {
                            DadosEndereco dadosEndereco = new RetornarEnderecoPeloCep(etCep.getText().toString()).execute().get();
                            etLogradouro.setText(dadosEndereco.getLogradouro());
                            etBairro.setText(dadosEndereco.getBairro());
                            etCidade.setText(dadosEndereco.getLocalidade());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Button btnEditar = v.findViewById(R.id.buttonEditarMae);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_mae);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirMae);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            // Cria um alerta para o usuário informar Sim ou Não para a exclusão
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_excluir_mae);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        excluir(id_mae);
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

    private void editar (int id) {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etCep.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o CEP!", Toast.LENGTH_LONG).show();
        } else if (etLogradouro.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o logradouro!", Toast.LENGTH_LONG).show();
        } else if (etNumero.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número do logradouro!", Toast.LENGTH_LONG).show();
        } else if (etBairro.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o bairro!", Toast.LENGTH_LONG).show();
        } else if (etCidade.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        } else if (etCelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número do celular!", Toast.LENGTH_LONG).show();
        } else if (etDataNascimento.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a data de nascimento!", Toast.LENGTH_LONG).show();
        } else {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
            // Setar os valores para a classe Mãe
            Mae m = new Mae();
            m.setId(id);
            m.setNome(etNome.getText().toString());
            m.setLogradouro(etLogradouro.getText().toString());
            m.setCep(etCep.getText().toString());
            m.setNumero(Integer.parseInt(etNumero.getText().toString()));
            m.setBairro(etBairro.getText().toString());
            m.setCidade(etCidade.getText().toString());
            m.setFixo(etFixo.getText().toString());
            m.setCelular(etCelular.getText().toString());
            m.setComercial(etComercial.getText().toString());
            m.setData_nascimento(etDataNascimento.getText().toString());
            databaseHelper.updateMae(m);
            Toast.makeText(getActivity(), "Mãe atualizada!", Toast.LENGTH_LONG).show();
            // Substitui o valor atual do fragmento FrameMae (EditarFragment) para o novo valor (ListarFragment)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
        }
    }

    private void excluir(int id) {
        m = new Mae();
        m.setId(id);
        databaseHelper.deleteMae(m);
        Toast.makeText(getActivity(), "Mãe excluída!", Toast.LENGTH_LONG).show();
        // Substitui o valor atual do fragmento FrameMae (EditarFragment) para o novo valor (ListarFragment)
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
    }
}