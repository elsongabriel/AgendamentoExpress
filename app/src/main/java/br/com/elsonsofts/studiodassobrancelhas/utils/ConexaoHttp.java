package br.com.elsonsofts.studiodassobrancelhas.utils;

import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.com.elsonsofts.studiodassobrancelhas.basicas.Agendamento;
import br.com.elsonsofts.studiodassobrancelhas.basicas.Usuario;

public class ConexaoHttp {
    static InputStream is = null;
    public static String idAgendamento = "-1";

    public static Agendamento agendamento;
    public static Usuario usuario;

    public void validarPesquisaId(EditText txtPesquisa) {
        try {
            String URL = "http://egcservice.com/webservices/agendamentos/login.php";
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
                agendamento = null;
                usuario = new Usuario();
                usuario.setId(Integer.valueOf(reader.getString("ID")));
                usuario.setLogin(String.valueOf(reader.getString("LOGIN")));
                usuario.setSenha(String.valueOf(reader.getString("SENHA")));
                usuario.setNome(String.valueOf(reader.getString("NOME")));
                usuario.setPermissao(Integer.valueOf(reader.getString("PERMISSAO")));
                usuario.setEmail(String.valueOf(reader.getString("EMAIL")));
                usuario.setTelefone(String.valueOf(reader.getString("TELEFONE")));
                usuario.setDataCadastro(String.valueOf(reader.getString("DATA CADASTRO")));
                usuario.setAtivo((Integer.parseInt(reader.getString("ATIVO")) == 1) ? true : false);
            } else if (config.equals("agenda")) {
                usuario = null;
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
                agendamento.setConfirmado((Integer.parseInt(reader.getString("CONFIRMADO")) == 1) ? true : false);
                agendamento.setAtivo((Integer.parseInt(reader.getString("ATIVO")) == 1) ? true : false);
            } else {
                usuario = null;
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
    }

    public boolean criarAgendamentoRequest(List<NameValuePair> params) {
        boolean criado = false;
        try {
            String URL = "http://egcservice.com/webservices/agendamentos/salvar_agendamento.php";
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
                message = String.valueOf(reader.getString("message"));
                String erro = String.format(message, "utf-8");
                httpGet = new HttpGet("http://egcservice.com/webservices/agendamentos/send_log.php?erro=" + erro.replace(" ", "%20"));
                httpClient.execute(httpGet);
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
        return criado;
    }

    public List<Agendamento> listarAgendamentos(int confirmado) {
        List<Agendamento> mAgendamentos = new ArrayList<>();
        try {
            String URL = "http://egcservice.com/webservices/agendamentos/listar_agendamentos.php";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("confirmado", String.valueOf(confirmado)));
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String paramString = URLEncodedUtils.format(params, "utf-8");
            URL += "?" + paramString;
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            JSONObject reader = new JSONObject(bytesToString(is));
            JSONArray jsonAgendamentos = reader.getJSONArray("agendamentos");
            for (int i = 0; i < jsonAgendamentos.length(); i++) {
                JSONObject jsonAgendamento = jsonAgendamentos.getJSONObject(i);

                agendamento = new Agendamento();
                agendamento.setId(Integer.valueOf(jsonAgendamento.getString("ID")));
                agendamento.setNome(String.valueOf(jsonAgendamento.getString("NOME")));
                agendamento.setTelefone1(String.valueOf(jsonAgendamento.getString("TELEFONE1")));
                agendamento.setTelefone2(String.valueOf(jsonAgendamento.getString("TELEFONE2")));
                agendamento.setEndereco(String.valueOf(jsonAgendamento.getString("ENDERECO")));
                agendamento.setPontoReferencia(String.valueOf(jsonAgendamento.getString("PONTO REF")));
                agendamento.setEmail(String.valueOf(jsonAgendamento.getString("EMAIL")));
                agendamento.setDataCadastro(String.valueOf(jsonAgendamento.getString("DATA CADASTRO")));
                agendamento.setDataAgendamento(String.valueOf(jsonAgendamento.getString("DATA AGENDAMENTO")));
                agendamento.setConfirmado((Integer.parseInt(jsonAgendamento.getString("CONFIRMADO")) == 1) ? true : false);
                agendamento.setAtivo((Integer.parseInt(jsonAgendamento.getString("ATIVO")) == 1) ? true : false);
                mAgendamentos.add(agendamento);
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
        return mAgendamentos;
    }

    public void atualizarAgendamento(int id, int conf, int atv) {
        try {
            String URL = "http://egcservice.com/webservices/agendamentos/atualizar_agendamento.php";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", String.valueOf(id)));
            params.add(new BasicNameValuePair("conf", String.valueOf(conf)));
            params.add(new BasicNameValuePair("ativo", String.valueOf(atv)));
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
            if (!success.equals("1")) {
                message = String.valueOf(reader.getString("message"));
                String erro = String.format(message, "utf-8");
                httpGet = new HttpGet("http://egcservice.com/webservices/agendamentos/send_log.php?erro=" + erro.replace(" ", "%20"));
                httpClient.execute(httpGet);
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
