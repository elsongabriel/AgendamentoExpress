package br.com.elsonsofts.studiodassobrancelhas.utils;

import android.app.Activity;
import android.widget.Toast;

public class Mensagem {

    public static void exibir(Activity tela, String message) {
        Toast.makeText(tela.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void exibirLong(Activity tela, String message) {
        Toast.makeText(tela.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
