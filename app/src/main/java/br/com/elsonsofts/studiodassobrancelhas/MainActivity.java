package br.com.elsonsofts.studiodassobrancelhas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;
import br.com.elsonsofts.studiodassobrancelhas.basicas.Usuario;
import br.com.elsonsofts.studiodassobrancelhas.utils.ConexaoHttp;
import br.com.elsonsofts.studiodassobrancelhas.utils.Mensagem;
import br.com.elsonsofts.studiodassobrancelhas.utils.Utils;

public class MainActivity extends ActionBarActivity {

    private EditText txtPesquisa;
    private Button btnProcurar, btnCriar;
    private ConexaoHttp conexaoHttp = new ConexaoHttp();
    private ProgressDialog pDialog;
    private Agendamento agendamento;
    private Usuario usuario;
    private CheckBox checkSalvar;
    private String idSaved;
    private boolean mCkBoxSalvar, isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("SalvarID", 0);
        mCkBoxSalvar = pref.getBoolean("checked", false);
        idSaved = pref.getString("id", "");

        txtPesquisa = (EditText) findViewById(R.id.txtPesquisarId);
        txtPesquisa.setText(idSaved);
        checkSalvar = (CheckBox) findViewById(R.id.checkSalvar);
        checkSalvar.setChecked(mCkBoxSalvar);
        btnProcurar = (Button) findViewById(R.id.btnProcurarId);
        btnCriar = (Button) findViewById(R.id.btnCriar);
        btnCriar.setVisibility(View.INVISIBLE);

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = null;
                if ((Utils.validateCampo(txtPesquisa, getResources().getString(R.string.txt_pesquisa), 0))) {
                    ConnectivityManager conectivtyManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (conectivtyManager.getActiveNetworkInfo() != null
                            && conectivtyManager.getActiveNetworkInfo().isAvailable()
                            && conectivtyManager.getActiveNetworkInfo().isConnected()) {
                        new ValidarLogin().execute();
                        SharedPreferences pref = getSharedPreferences("SalvarID",
                                MODE_PRIVATE);
                        editor = pref.edit();
                        isChecked = checkSalvar.isChecked();
                    } else {
                        Mensagem.exibir(MainActivity.this, getResources().getString(R.string.msg_sem_conexao));
                    }
                }

                if (isChecked) {
                    editor.putString("id", txtPesquisa.getText().toString());
                } else {
                    editor.putString("id", null);
                }
                editor.putBoolean("checked", isChecked);
                editor.commit();
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CriarAgendamentoActivity.class));
            }
        });

        if (savedInstanceState != null) {
            idSaved = savedInstanceState.getString("id_pesq");
            if (idSaved != null) {
                txtPesquisa.setText(idSaved);
                checkSalvar.setChecked(mCkBoxSalvar);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (idSaved != null) {
            outState.putString("id_pesq", idSaved);
        }
    }

    class ValidarLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getResources().getString(R.string.msg_validando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            try {
                conexaoHttp.validarPesquisaId(txtPesquisa);
                usuario = ConexaoHttp.usuario;
                if (usuario == null)
                    agendamento = ConexaoHttp.agendamento;
            } catch (Exception e) {
                throw e;
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            try {
                pDialog.dismiss();
                if (usuario != null) {
                    startActivity(new Intent(MainActivity.this, HomeAdmActivity.class));
                } else if (agendamento != null) {
                    Intent it = new Intent(MainActivity.this, DetalheAgendamentoActivity.class);
                    it.putExtra("agendamento", agendamento);
                    it.putExtra("tipoUser", "cliente");
                    startActivity(it);
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
