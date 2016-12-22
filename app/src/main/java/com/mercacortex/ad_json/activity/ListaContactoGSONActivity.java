package com.mercacortex.ad_json.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mercacortex.ad_json.R;
import com.mercacortex.ad_json.model.Contact;
import com.mercacortex.ad_json.model.Person;
import com.mercacortex.ad_json.utils.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaContactoGSONActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener{
    //public static final String WEB = "http://10.0.2.2/acceso/contacts.json";
    public static final String WEB = "https://portadaalta.club/acceso/contacts.json";
    Button boton;
    ListView lista;
    ArrayList<Contact> contacts;
    ArrayAdapter<Contact> adapter;
    Person person;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contacto);

        boton = (Button) findViewById(R.id.btnDescargaContactos);
        boton.setOnClickListener(this);
        lista = (ListView) findViewById(R.id.listViewContactos);
        lista.setOnItemClickListener(this);
        contacts = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        if (v == boton)
            descarga(WEB);
    }

    //usar JsonHttpResponseHandler()
    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
         RestClient.get(web, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog. STYLE_SPINNER );
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(true);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    gson = new Gson();
                    person = gson.fromJson(String.valueOf(response), Person.class);
                    mostrar();
                    progreso.dismiss();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JsonSyntaxException: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Error: " + responseString, Toast.LENGTH_LONG).show();
            }
        } );
    }

    private void mostrar() {
        if (person != null) {
            contacts.clear();
            contacts.addAll(person.getContactos());

            if (adapter == null) {
                adapter = new ArrayAdapter<Contact>(this, android.R.layout. simple_list_item_1 , contacts);
                lista.setAdapter(adapter);
            }
        } else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast. LENGTH_SHORT ).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Móvil: " + contacts.get(position).getTelefono().getMovil(), Toast. LENGTH_SHORT ).show();
    }
}