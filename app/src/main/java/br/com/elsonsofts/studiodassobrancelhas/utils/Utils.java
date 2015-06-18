package br.com.elsonsofts.studiodassobrancelhas.utils;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("SimpleDateFormat")
public class Utils {

    private static final String LOGIN = "Login";

    public static boolean validateNotNull(View pView, String pMessage) {
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null && text.length() >= 2) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }
            // em qualquer outra condição é gerado um erro
            edText.setError(pMessage);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }

    public static boolean validateEmail(View pView, String pMessage) {
        boolean emailValido = false;
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression,
                        Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(strText);
                if (matcher.matches()) {
                    emailValido = true;
                } else {
                    emailValido = false;
                    edText.setError(pMessage);
                    edText.setFocusable(true);
                    edText.requestFocus();
                }
            }
        }
        return emailValido;
    }

    public static boolean validateCampo(View pView, String pMessage, int qtd) {
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null && text.length() == qtd) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }
            // em qualquer outra condição é gerado um erro
            edText.setError(pMessage);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }

    public static boolean validateData(View pView, String pMessage, int qtd) {
        boolean dataValida = false;
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null && text.length() == qtd) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yyyy");
                    sdfEntrada.setLenient(false);
                    try {
                        Date data = sdfEntrada.parse(strText);
                        dataValida = true;
                    } catch (ParseException e) {
                        dataValida = false;
                        edText.setError(pMessage);
                        edText.setFocusable(true);
                        edText.requestFocus();
                    }
                }
            }
        }
        return dataValida;
    }

    public static boolean validateHora(View pView, String pMessage, int qtd) {
        boolean hora = false;
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null && text.length() == qtd) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    SimpleDateFormat sdfEntrada = new SimpleDateFormat("HH:mm");
                    sdfEntrada.setLenient(false);
                    try {
                        Date data = sdfEntrada.parse(strText);
                        hora = true;
                    } catch (ParseException e) {
                        hora = false;
                        edText.setError(pMessage);
                        edText.setFocusable(true);
                        edText.requestFocus();
                    }
                }
            }
        }
        return hora;
    }

    public static String CryptographyPassword(String input) {
        String output = null;
        MessageDigest md;
        byte[] inputBytes;
        StringBuilder sb;

        try {
            md = MessageDigest.getInstance("MD5");
            inputBytes = md.digest(input.getBytes("UTF-8"));
            sb = new StringBuilder();

            for (byte b : inputBytes) {
                sb.append(String.format("%02X", 0xFF & b));
            }
            output = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return output;
    }
}
