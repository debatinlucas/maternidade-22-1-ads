package br.com.dlweb.maternidade_ads.mae;

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

public class AdicionarFragment extends Fragment {

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

    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.mae_fragment_adicionar, container, false);

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

        // Quando o foco sair do campo do CEP, é executado o WS para recuperar os dados do CEP informado
        etCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
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
        });

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarMae);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
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
            databaseHelper.createMae(m);
            Toast.makeText(getActivity(), "Mãe salva!", Toast.LENGTH_LONG).show();
            // Substitui o valor atual do fragmento FrameMae (AdicionarFragment) para o novo valor (ListarFragment)
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
        }
    }
}