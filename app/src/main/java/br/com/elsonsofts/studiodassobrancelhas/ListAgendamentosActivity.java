package br.com.elsonsofts.studiodassobrancelhas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;
import br.com.elsonsofts.studiodassobrancelhas.fragments.ListAgendamentosFragment;

public class ListAgendamentosActivity extends ActionBarActivity implements AgendamentoClicadoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agn);

        String acao = (String) getIntent().getSerializableExtra("acao");
        ListAgendamentosFragment l = ListAgendamentosFragment
                .novaInstancia(acao);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, l).commit();
    }

    public void agendamentoClicado(Agendamento agendamento) {
        Intent it = new Intent(this, DetalheAgendamentoActivity.class);
        it.putExtra("agendamento", agendamento);
        startActivity(it);
    }
}
