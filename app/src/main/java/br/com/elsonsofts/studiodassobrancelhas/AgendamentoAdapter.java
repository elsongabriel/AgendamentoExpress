package br.com.elsonsofts.studiodassobrancelhas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;

public class AgendamentoAdapter extends ArrayAdapter<Agendamento> {

    private Agendamento mAgendamento;

    private TextView mLblNome, mLblTelefone, mLblData, mLblId;

    public AgendamentoAdapter(Context ctx, List<Agendamento> agendamentos) {
        super(ctx, 0, agendamentos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mAgendamento = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_agnd, null);
        }

        mLblNome = (TextView) convertView.findViewById(R.id.lblNomeReq);
        mLblTelefone = (TextView) convertView.findViewById(R.id.lblTelReq);
        mLblData = (TextView) convertView.findViewById(R.id.lblDataReq);
        mLblId = (TextView) convertView.findViewById(R.id.lblIdReq);

        mLblNome.setText(mAgendamento.nome);
        mLblTelefone.setText(mAgendamento.telefone1);
        mLblData.setText(mAgendamento.dataAgendamento);
        mLblId.setText(String.valueOf(mAgendamento.id));

        return convertView;
    }
}
