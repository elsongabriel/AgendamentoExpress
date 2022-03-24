package br.com.elsonsofts.studiodassobrancelhas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.elsonsofts.studiodassobrancelhas.fragments.HomeAdmFragment;

public class HomeAdmActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_adm);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new HomeAdmFragment()).commit();
    }
}
