package br.com.elsonsofts.studiodassobrancelhas;

import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ConexaoHttp {
    static InputStream is = null;
    private boolean criado = false;
    static String idAgendamento = "-1";

    private Agendamento agendamento;
    private Usuario usuario;

    public ConexaoHttp() {

    }

    public Usuario validarPesquisaUsuarioId(EditText txtPesquisa) {
        try {
            String URL = "http://elsongabriel.com/webservices/agendamentos/login.php";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", txtPesquisa.getText().toString()));
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String paramString = URLEncodedUtils.format(params, "utf-8");
            URL += "?" + paramString;
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            JSONObject reader = new JSONObject(bytesToString(is));
            String config = String.valueOf(reader.getString("CONFIG"));
            if (config.equals("usuario")) {
                usuario = new Usuario();
                usuario.setId(Integer.valueOf(reader.getString("ID")));
                usuario.setLogin(String.valueOf(reader.getString("LOGIN")));
                usuario.setSenha(String.valueOf(reader.getString("SENHA")));
                usuario.setNome(String.valueOf(reader.getString("NOME")));
                usuario.setPermissao(Integer.valueOf(reader.getString("PERMISSAO")));
                usuario.setEmail(String.valueOf(reader.getString("EMAIL")));
                usuario.setTelefone(String.valueOf(reader.getString("TELEFONE")));
                usuario.setDataCadastro(String.valueOf(reader.getString("DATA CADASTRO")));
            } else {
                usuario = null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Agendamento validarPesquisaAgendaId(EditText txtPesquisa) {
        try {
            String URL = "http://elsongabriel.com/webservices/agendamentos/login.php";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", txtPesquisa.getText().toString()));
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String paramString = URLEncodedUtils.format(params, "utf-8");
            URL += "?" + paramString;
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            JSONObject reader = new JSONObject(bytesToString(is));
            String config = String.valueOf(reader.getString("CONFIG"));
            if (config.equals("agenda")) {
                agendamento = new Agendamento();
                agendamento.setId(Integer.valueOf(reader.getString("ID")));
                agendamento.setNome(String.valueOf(reader.getString("NOME")));
                agendamento.setTelefone1(String.valueOf(reader.getString("TELEFONE1")));
                agendamento.setTelefone2(String.valueOf(reader.getString("TELEFONE2")));
                agendamento.setEndereco(String.valueOf(reader.getString("ENDERECO")));
                agendamento.setPontoReferencia(String.valueOf(reader.getString("PONTO REF")));
                agendamento.setEmail(String.valueOf(reader.getString("EMAIL")));
                agendamento.setDataCadastro(String.valueOf(reader.getString("DATA CADASTRO")));
                agendamento.setDataAgendamento(String.valueOf(reader.getString("DATA AGENDAMENTO")));
                agendamento.setConfirmado(Boolean.valueOf(reader.getString("CONFIRMADO")));
            } else {
                agendamento = null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return agendamento;
    }

    public boolean criarAgendamentoRequest(List<NameValuePair> params) {
        try {
            String URL = "http://elsongabriel.com/webservices/agendamentos/salvar_agendamento.php";
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String paramString = URLEncodedUtils.format(params, "utf-8");
            URL += "?" + paramString;
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            JSONObject reader = new JSONObject(bytesToString(is));
            String success, message;
            success = String.valueOf(reader.getString("success"));
            if (success.equals("1")) {
                criado = true;
                idAgendamento = String.valueOf(reader.getString("id"));
            } else {
                criado = false;
                message = String.valueOf(reader.getString("message"));
                String erro = String.format(message, "utf-8");
                httpGet = new HttpGet("http://elsongabriel.com/webservices/agendamentos/send_log.php?erro=" + erro.replace(" ", "%20"));
                httpClient.execute(httpGet);
            }
        } catch (UnsupportedEncodingException e) {
            criado = false;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            criado = false;
            e.printStackTrace();
        } catch (IOException e) {
            criado = false;
            e.printStackTrace();
        } catch (JSONException e) {
            criado = false;
            e.printStackTrace();
        }
        return criado;
    }

    private static String bytesToString(InputStream is) throws IOException {
        byte[] buf = new byte[1024];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bytesLidos;
        while ((bytesLidos = is.read(buf)) != -1) {
            buffer.write(buf, 0, bytesLidos);
        }
        return new String(buffer.toByteArray());
    }
}
