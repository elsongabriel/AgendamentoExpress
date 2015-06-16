package br.com.elsonsofts.studiodassobrancelhas.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by eliasafe on 09/05/15.
 */
public class Mensagem {

    public static void exibir(Activity tela, String message){
        Toast.makeText(tela.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
