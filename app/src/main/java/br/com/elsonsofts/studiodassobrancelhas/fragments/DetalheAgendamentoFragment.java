package br.com.elsonsofts.studiodassobrancelhas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.elsonsofts.studiodassobrancelhas.R;
import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;
import br.com.elsonsofts.studiodassobrancelhas.utils.ConexaoHttp;
import br.com.elsonsofts.studiodassobrancelhas.utils.Mensagem;

public class DetalheAgendamentoFragment extends Fragment {

    private Agendamento mAgendamento;
    private TextView lblId, lblNome, lblTelefone1, lblTelefone2, lblEndereco, lblPonto, lblEmail, lblCad, lblAgn, lblConf, lblAtivo;
    private ImageButton imgBtnAceitar, imgBtnRecusar, imgBtnCancel;
    private ConexaoHttp conexaoHttp = new ConexaoHttp();
    private ProgressDialog pDialog;

    public static DetalheAgendamentoFragment novaInstancia(Agendamento agendamento) {
        Bundle args = new Bundle();
        args.putSerializable("agendamento", agendamento);

        DetalheAgendamentoFragment f = new DetalheAgendamentoFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_detalhe_agendamento, null);

        lblId = (TextView) layout.findViewById(R.id.lblIdDetail);
        lblNome = (TextView) layout.findViewById(R.id.lblNomeDetail);
        lblTelefone1 = (TextView) layout.findViewById(R.id.lblTel1Detail);
        lblTelefone2 = (TextView) layout.findViewById(R.id.lblTel2Detail);
        lblEndereco = (TextView) layout.findViewById(R.id.lblEndDetail);
        lblPonto = (TextView) layout.findViewById(R.id.lblPontoDetail);
        lblEmail = (TextView) layout.findViewById(R.id.lblEmailDetail);
        lblCad = (TextView) layout.findViewById(R.id.lblDataCadDetail);
        lblAgn = (TextView) layout.findViewById(R.id.lblDataAgnDetail);
        lblConf = (TextView) layout.findViewById(R.id.lblConfDetail);
        lblAtivo = (TextView) layout.findViewById(R.id.lblAtivoDetail);
        imgBtnAceitar = (ImageButton) layout
                .findViewById(R.id.imgBtnAccept);
        imgBtnRecusar = (ImageButton) layout
                .findViewById(R.id.imgBtnReject);
        imgBtnCancel = (ImageButton) layout
                .findViewById(R.id.imgBtnCancel);

        atualizar();

        imgBtnAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coloca confirmado = 1 e ativo = 1 ACEITO
                ConnectivityManager cm = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                if (cm.getActiveNetworkInfo() != null
                        && cm.getActiveNetworkInfo().isConnected()) {
                    new Processar().execute(mAgendamento.id, 1, 1);
                } else {
                    Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_sem_conexao));
                }
            }
        });

        imgBtnRecusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coloca confirmado = 0 e ativo = 0 RECUSADO
                ConnectivityManager cm = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                if (cm.getActiveNetworkInfo() != null
                        && cm.getActiveNetworkInfo().isConnected()) {
                    new Processar().execute(mAgendamento.id, 0, 0);
                } else {
                    Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_sem_conexao));
                }
            }
        });

        imgBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return layout;
    }

    public String retornarString(boolean value) {
        String retorno;

        if (value) {
            retorno = getResources().getString(R.string.msg_sim);
        } else {
            retorno = getResources().getString(R.string.msg_nao);
        }
        return retorno;
    }

    class Processar extends AsyncTask<Integer, Void, Agendamento> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getString(R.string.msg_carregando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Agendamento doInBackground(Integer... params) {
            try {
                conexaoHttp.atualizarAgendamento(params[0], params[1], params[2]);
                mAgendamento.confirmado = (Integer.parseInt(String.valueOf(params[1])) == 1) ? true : false;
                mAgendamento.ativo = (Integer.parseInt(String.valueOf(params[2])) == 1) ? true : false;
                return mAgendamento;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Agendamento result) {
            super.onPostExecute(result);
            try {
                if (result != null) {
                    atualizar();
                    pDialog.dismiss();
                    Mensagem.exibir(getActivity(), "Agendamento atualizado com sucesso!");
                } else {
                    Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_not_agenda));
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public void atualizar() {
        mAgendamento = (Agendamento) getArguments().getSerializable("agendamento");

        lblId.setText("Id: " + String.valueOf(mAgendamento.id));
        lblNome.setText("Nome: " + mAgendamento.nome);
        lblTelefone1.setText("Telefone: " + mAgendamento.telefone1);
        lblTelefone2.setText("Telefone: " + mAgendamento.telefone2);
        lblEndereco.setText("End.: " + mAgendamento.endereco);
        lblPonto.setText("Ponto Ref: " + mAgendamento.pontoReferencia);
        lblEmail.setText("Email: " + mAgendamento.email);
        lblCad.setText("Cadastro em " + mAgendamento.dataCadastro);
        lblAgn.setText("Agendado p/" + mAgendamento.dataAgendamento);
        lblConf.setText("Confirmado? " + retornarString(mAgendamento.confirmado));
        lblAtivo.setText("Ativo? " + retornarString(mAgendamento.ativo));
    }
}