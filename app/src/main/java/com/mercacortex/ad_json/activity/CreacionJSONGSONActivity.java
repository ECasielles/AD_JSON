package com.mercacortex.ad_json.activity;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.mercacortex.ad_json.analisis.Analisis;
import com.mercacortex.ad_json.analisis.AnalisisJSON;
import com.mercacortex.ad_json.model.Noticia;
import com.mercacortex.ad_json.R;
import com.mercacortex.ad_json.utils.RestClient;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreacionJSONGSONActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String WEB = "http://www.alejandrosuarez.es/feed/";
    //public static final String WEB = "http://192.168.2.110/acceso/alejandro.rss";
    public static final String RESULTADO_JSON = "resultado.json";
    public static final String RESULTADO_GSON = "resultado_gson.json";
    public static final String TEMPORAL = "alejandro.xml";
    ArrayList<Noticia> noticias;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_json);
        boton = (Button) findViewById(R.id.btnCrearJSON);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == boton)
            descarga(WEB, TEMPORAL);
    }

    //obtener el rss y escribir los ficheros
    private void descarga(String web, String temporal) {
        final ProgressDialog progreso = new ProgressDialog(this);
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), temporal);
        RestClient.get(web, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fallo: " + statusCode + "\n" + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fichero descargado OK\n" + file.getPath(), Toast.LENGTH_LONG).show();
                try {
                    noticias = Analisis.analizarNoticias(file);
                    AnalisisJSON.escribirJSON(noticias, RESULTADO_JSON);
                    Toast.makeText(getApplicationContext(), "Fichero: " + RESULTADO_JSON + ", escrito OK", Toast.LENGTH_SHORT).show();
                    AnalisisJSON.escribirGSON(noticias, RESULTADO_GSON);
                    Toast.makeText(getApplicationContext(), "Fichero: " + RESULTADO_GSON + ", escrito OK", Toast.LENGTH_SHORT).show();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Excepcion XML: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Excepcion I/O: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Excepcion JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }
        });
    }
}