package br.com.elsonsofts.studiodassobrancelhas;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CriarAgendamentoHttp {
    static InputStream is = null;
    private boolean criado = false;

    public CriarAgendamentoHttp() {

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
            String id, message;
            id = String.valueOf(reader.getString("success"));
            if (id.equals("1")) {
                criado = true;
            } else {
                criado = false;
                message = String.valueOf(reader.getString("message"));
                String erro = String.format("http://elsongabriel.com/webservices/agendamentos/send_log.php?message=" + message, "utf-8");
                httpGet = new HttpGet(erro);
                httpClient.execute(httpGet);
                //httpEntity = httpResponse.getEntity();
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
