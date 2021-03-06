package br.com.elsonsofts.studiodassobrancelhas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;
import br.com.elsonsofts.studiodassobrancelhas.fragments.DetalheAgendamentoFragment;

public class DetalheAgendamentoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Agendamento agendamento = (Agendamento) getIntent().getSerializableExtra("agendamento");
        String tipoUser = (String) getIntent().getSerializableExtra("tipoUser");
        DetalheAgendamentoFragment a = DetalheAgendamentoFragment
                .novaInstancia(agendamento, tipoUser);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, a).commit();
    }
}
