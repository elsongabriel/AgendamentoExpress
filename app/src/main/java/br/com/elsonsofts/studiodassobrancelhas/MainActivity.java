package br.com.elsonsofts.studiodassobrancelhas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.elsonsofts.studiodassobrancelhas.utils.Mask;
import br.com.elsonsofts.studiodassobrancelhas.utils.Mensagem;
import br.com.elsonsofts.studiodassobrancelhas.utils.Utils;

public class MainActivity extends ActionBarActivity {

    private EditText txtPesquisa;
    private Button btnProcurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPesquisa = (EditText) findViewById(R.id.txtPesquisarId);
        btnProcurar = (Button) findViewById(R.id.btnProcurarId);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Utils.validateNotNull(txtPesquisa,
                        "Por Favor, Verificar o Campo de Pesquisa!"))) {
                    Mensagem.exibir(MainActivity.this, "teste");
                }
            }
        });
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
