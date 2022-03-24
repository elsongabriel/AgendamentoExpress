package br.com.elsonsofts.studiodassobrancelhas.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.elsonsofts.studiodassobrancelhas.ListAgendamentosActivity;
import br.com.elsonsofts.studiodassobrancelhas.R;

public class HomeAdmFragment extends Fragment {

    private TextView lblReq, lblAgnds;
    private ImageButton imgBtnReq, imgBtnAgnds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_adm, container, false);

        lblReq = (TextView) layout.findViewById(R.id.lblReqHome);
        lblAgnds = (TextView) layout.findViewById(R.id.lblAgdsHome);
        imgBtnReq = (ImageButton) layout.findViewById(R.id.imgBtnReq);
        imgBtnAgnds = (ImageButton) layout.findViewById(R.id.imgBtnAgnds);

        lblReq.setText(getResources().getString(R.string.lbl_agdns_req));
        lblAgnds.setText(getResources().getString(R.string.lbl_agdns_con));

        imgBtnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ListAgendamentosActivity.class);
                it.putExtra("acao", "reqs");
                startActivity(it);
            }
        });

        imgBtnAgnds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ListAgendamentosActivity.class);
                it.putExtra("acao", "conf");
                startActivity(it);
            }
        });

        return layout;
    }
}
