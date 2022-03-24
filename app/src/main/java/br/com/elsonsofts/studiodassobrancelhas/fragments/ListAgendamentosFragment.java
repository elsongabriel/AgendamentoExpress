package br.com.elsonsofts.studiodassobrancelhas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.com.elsonsofts.studiodassobrancelhas.AgendamentoAdapter;
import br.com.elsonsofts.studiodassobrancelhas.AgendamentoClicadoListener;
import br.com.elsonsofts.studiodassobrancelhas.R;
import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;
import br.com.elsonsofts.studiodassobrancelhas.utils.ConexaoHttp;
import br.com.elsonsofts.studiodassobrancelhas.utils.Mensagem;

public class ListAgendamentosFragment extends ListFragment {

    private ConexaoHttp conexaoHttp = new ConexaoHttp();
    private ProgressDialog pDialog;
    private List<Agendamento> agendamentosRequests;
    private List<Agendamento> agendamentosConfirmados;
    private AgendamentoAdapter adapter;
    private String acao = "";

    public static ListAgendamentosFragment novaInstancia(String acao) {
        Bundle args = new Bundle();
        args.putSerializable("acao", acao);

        ListAgendamentosFragment f = new ListAgendamentosFragment();
        f.setArguments(args);
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_agn,
                container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        acao = (String) getArguments().getSerializable("acao");

        if (acao.equals("reqs")) {
            if (agendamentosRequests != null) {
                refreshList(agendamentosRequests);
            } else {
                realizarDownload(0);
            }
        } else if (acao.equals("conf")) {
            if (agendamentosConfirmados != null) {
                refreshList(agendamentosConfirmados);
            } else {
                realizarDownload(1);
            }
        }
    }

    public void realizarDownload(int confirmado) {
        ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnected()) {
            new Carregar().execute(confirmado);
        } else {
            Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_sem_conexao));
        }
    }

    class Carregar extends AsyncTask<Integer, Void, List<Agendamento>> {

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
        protected List<Agendamento> doInBackground(Integer... params) {
            try {
                return conexaoHttp.listarAgendamentos(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Agendamento> result) {
            super.onPostExecute(result);
            try {
                if (result != null) {
                    if (acao.equals("reqs")) {
                        agendamentosRequests = result;
                        refreshList(agendamentosRequests);
                    } else if (acao.equals("conf")) {
                        agendamentosConfirmados = result;
                        refreshList(agendamentosConfirmados);
                    }
                    pDialog.dismiss();
                } else {
                    Mensagem.exibir(getActivity(), getResources().getString(R.string.msg_not_agenda));
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (getActivity() instanceof AgendamentoClicadoListener) {
            if (acao.equals("reqs")) {
                ((AgendamentoClicadoListener) getActivity()).agendamentoClicado(agendamentosRequests
                        .get(position));
            } else if (acao.equals("conf")) {
                ((AgendamentoClicadoListener) getActivity()).agendamentoClicado(agendamentosConfirmados
                        .get(position));
            }
        }
    }

    private void refreshList(List<Agendamento> a) {
        adapter = new AgendamentoAdapter(getActivity(), a);
        setListAdapter(adapter);
    }
}