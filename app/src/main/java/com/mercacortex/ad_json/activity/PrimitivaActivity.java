package com.mercacortex.ad_json.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mercacortex.ad_json.analisis.AnalisisJSON;
import com.mercacortex.ad_json.utils.Memoria;
import com.mercacortex.ad_json.R;
import com.mercacortex.ad_json.utils.Resultado;

import org.json.JSONException;

public class PrimitivaActivity extends AppCompatActivity {

    TextView informacion;
    Memoria memoria;
    Resultado resultado;
    public static final String FICHERO_PRIMITIVA = "primitiva.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_primitiva );
        informacion = (TextView) findViewById(R.id.txvPrimitiva);
        memoria = new Memoria(this);
        resultado = memoria.leerAsset( FICHERO_PRIMITIVA );
        if (resultado.getCodigo())
            try {
                informacion.setText(AnalisisJSON.analizarPrimitiva(resultado.getContenido()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        else
            informacion.setText("Error al leer el fichero " + FICHERO_PRIMITIVA);
    }
}
