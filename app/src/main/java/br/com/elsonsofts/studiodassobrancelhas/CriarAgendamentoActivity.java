package br.com.elsonsofts.studiodassobrancelhas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class CriarAgendamentoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_agendamento);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new CriarAgendamentoFragment()).commit();
    }
}
