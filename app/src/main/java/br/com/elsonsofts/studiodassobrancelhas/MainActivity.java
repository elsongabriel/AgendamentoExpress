package br.com.elsonsofts.studiodassobrancelhas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.elsonsofts.studiodassobrancelhas.utils.Mensagem;
import br.com.elsonsofts.studiodassobrancelhas.utils.Utils;

public class MainActivity extends ActionBarActivity {

    private EditText txtPesquisa;
    private Button btnProcurar;
    private ConexaoHttp conexaoHttp = new ConexaoHttp();
    private ProgressDialog pDialog;
    private static Agendamento agendamento;
    private static Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPesquisa = (EditText) findViewById(R.id.txtPesquisarId);
        btnProcurar = (Button) findViewById(R.id.btnProcurarId);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Utils.validateCampo(txtPesquisa, getResources().getString(R.string.txt_pesquisa), 1))) {
                    ConnectivityManager conectivtyManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (conectivtyManager.getActiveNetworkInfo() != null
                            && conectivtyManager.getActiveNetworkInfo().isAvailable()
                            && conectivtyManager.getActiveNetworkInfo().isConnected()) {
                        new Validar().execute();
                    } else {
                        Mensagem.exibir(MainActivity.this, getResources().getString(R.string.msg_sem_conexao));
                    }
                }
            }
        });
    }

    class Validar extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Validando..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            try {
                usuario = conexaoHttp.validarPesquisaUsuarioId(txtPesquisa);
                agendamento = conexaoHttp.validarPesquisaAgendaId(txtPesquisa);
            } catch (Exception e) {
                throw e;
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            try {
                pDialog.dismiss();
                if (usuario != null) {
                    // TODO
                    // criar nova tela de visualização do agendamento para adm
                } else if (agendamento != null) {
                    // TODO
                    // criar nova tela de visualização do agendamento para cliente
                } else {
                    Mensagem.exibir(MainActivity.this, getResources().getString(R.string.msg_not_found));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novoAgendamento:
                startActivity(new Intent(this, CriarAgendamentoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
