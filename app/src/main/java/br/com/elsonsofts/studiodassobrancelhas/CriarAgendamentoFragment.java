package br.com.elsonsofts.studiodassobrancelhas;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import br.com.elsonsofts.studiodassobrancelhas.utils.Mask;
import br.com.elsonsofts.studiodassobrancelhas.utils.Mensagem;
import br.com.elsonsofts.studiodassobrancelhas.utils.Utils;

public class CriarAgendamentoFragment extends Fragment {

    private EditText txtNome, txtTelefone1, txtTelefone2, txtEndereco, txtPontoRef, txtEmail, txtDataAgendada, txtHoraAgendada;
    private Button btnSalvar;
    private CriarAgendamentoHttp criarAgendamentoHttp = new CriarAgendamentoHttp();
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_criar_agendamento,
                container, false);

        txtNome = (EditText) layout.findViewById(R.id.txtNomeCriar);
        txtTelefone1 = (EditText) layout.findViewById(R.id.txtTelefone1Criar);
        txtTelefone1.addTextChangedListener(Mask.insertEdText("(##)#####-####",
                txtTelefone1));
        txtTelefone2 = (EditText) layout.findViewById(R.id.txtTelefone2Criar);
        txtTelefone2.addTextChangedListener(Mask.insertEdText("(##)#####-####",
                txtTelefone2));
        txtEndereco = (EditText) layout.findViewById(R.id.txtEnderecoCriar);
        txtPontoRef = (EditText) layout.findViewById(R.id.txtPontoCriar);
        txtEmail = (EditText) layout.findViewById(R.id.txtEmailCriar);
        txtDataAgendada = (EditText) layout.findViewById(R.id.txtDataAgendaCriar);
        txtDataAgendada.addTextChangedListener(Mask.insertEdText("##/##/####",
                txtDataAgendada));
        txtHoraAgendada = (EditText) layout.findViewById(R.id.txtHoraAgendaCriar);
        txtHoraAgendada.addTextChangedListener(Mask.insertEdText("##:##",
                txtHoraAgendada));
        btnSalvar = (Button) layout.findViewById(R.id.btnCriarSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCamposNulos()) {
                    ConnectivityManager conectivtyManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (conectivtyManager.getActiveNetworkInfo() != null
                            && conectivtyManager.getActiveNetworkInfo().isAvailable()
                            && conectivtyManager.getActiveNetworkInfo().isConnected()) {
                        new CreateNewAgendamento().execute();
                    } else {
                        Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_sem_conexao));
                    }
                }
            }
        });
        return layout;
    }

    public boolean validarCamposNulos() {
        boolean ok = false;
        if ((Utils.validateNotNull(txtNome,
                "Por Favor, Verificar o Campo de Nome!"))
                && (Utils.validateCampo(txtTelefone1,
                "Por Favor, Verificar o Campo de Telefone!", 14))
                && (Utils.validateData(txtDataAgendada,
                "Por Favor, Verificar a Data de Agendamento!", 10))
                && (Utils.validateHora(txtHoraAgendada,
                "Por Favor, Verificar a Hora de Agendamento!", 5))) {
            ok = true;
            if (txtEmail.length() > 0) {
                if (Utils.validateEmail(txtEmail, "Por Favor, Verificar o Campo de Email!")) {
                    ok = true;
                } else {
                    ok = false;
                }
            }
            if (txtTelefone2.length() > 0) {
                if (Utils.validateCampo(txtTelefone2, "Por Favor, Verificar o Campo de Telefone!", 14)) {
                    ok = true;
                } else {
                    ok = false;
                }
            }
        }
        return ok;
    }

    class CreateNewAgendamento extends AsyncTask<String, String, String> {

        boolean criou = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Salvando Agendamento..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            try {
                criou = criarAgendamentoHttp.criarAgendamentoRequest(receberValores());
            } catch (Exception e) {
                //Mensagem.exibir(getActivity(), "Verifique se os Campos estao preenchidos corretamente!");
                throw e;
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            try {
                pDialog.dismiss();
                if (criou == true) {
                    String id = " " + CriarAgendamentoHttp.idAgendamento;
                    Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_usuario_cadastrado) + id);
                    CriarAgendamentoHttp.idAgendamento = "-1";
                    getActivity().finish();
                } else {
                    Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_trabalhando));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<NameValuePair> receberValores() {
        String data = Mask.unmask(txtDataAgendada.getText().toString());
        String hora = Mask.unmask(txtHoraAgendada.getText().toString());
        String dataEnvio = data.substring(4, 8) //ano
                + data.substring(2, 4) //mes
                + data.substring(0, 2) //dia
                + hora.substring(0, 2) //hora
                + hora.substring(2, 4) // minuto
                + "00"; //segs

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("nomeCliente", txtNome.getText().toString()));
        params.add(new BasicNameValuePair("telCliente1", Mask.unmask(txtTelefone1.getText().toString())));
        params.add(new BasicNameValuePair("dataAgendada", dataEnvio));

        if (Mask.unmask(txtTelefone2.getText().toString()).length() > 0) {
            params.add(new BasicNameValuePair("telCliente2", Mask.unmask(txtTelefone2.getText().toString())));
        }
        if (txtEndereco.getText().toString().length() > 0) {
            params.add(new BasicNameValuePair("endereco", txtEndereco.getText().toString()));
        }
        if (txtPontoRef.getText().toString().length() > 0) {
            params.add(new BasicNameValuePair("pontoRef", txtPontoRef.getText().toString()));
        }
        if (txtEmail.getText().toString().length() > 0) {
            params.add(new BasicNameValuePair("email", txtEmail.getText().toString()));
        }
        return params;
    }
}
