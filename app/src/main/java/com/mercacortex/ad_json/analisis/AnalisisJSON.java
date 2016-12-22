package com.mercacortex.ad_json.analisis;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mercacortex.ad_json.model.Contacto;
import com.mercacortex.ad_json.model.Noticia;
import com.mercacortex.ad_json.model.Telefono;
import com.mercacortex.ad_json.model.Titular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class AnalisisJSON {

    public static String analizarPrimitiva(String texto) throws JSONException {
        JSONObject jsonObjeto, item;
        JSONArray jsonContenido;
        String tipo, datos;
        StringBuilder cadena = new StringBuilder();
        jsonObjeto = new JSONObject(texto);
        tipo = jsonObjeto.getString("info");
        //datos = jsonObjeto.getString("sorteo");
        //jsonContenido = new JSONArray(datos);
        jsonContenido = jsonObjeto.getJSONArray("sorteo");
        cadena.append("Sorteos de la Primitiva:" + "\n");
        for (int i = 0; i < jsonContenido.length(); i++) {
            item = jsonContenido.getJSONObject(i);
            cadena.append(tipo+":"+item.getString("fecha"));
            cadena.append(item.getInt("numero1")+", "+item.getInt("numero2")+", "+item.getInt("numero3")+
                    ", "+item.getInt("numero4")+", "+item.getInt("numero5")+", "+item.getInt("numero6")+ "\n");
        }
        return cadena.toString();
    }


    public static String analizarPrimitiva(JSONObject jsonObjeto) throws JSONException {
        JSONObject item;
        JSONArray jsonContenido;
        String tipo, datos;
        StringBuilder cadena = new StringBuilder();
        tipo = jsonObjeto.getString("info");
        //datos = jsonObjeto.getString("sorteo");
        //jsonContenido = new JSONArray(datos);
        jsonContenido = jsonObjeto.getJSONArray("sorteo");
        cadena.append("Sorteos de la Primitiva:" + "\n");
        for (int i = 0; i < jsonContenido.length(); i++) {
            item = jsonContenido.getJSONObject(i);
            cadena.append(tipo+":"+item.getString("fecha"));
            cadena.append(item.getInt("numero1")+", "+item.getInt("numero2")+", "+item.getInt("numero3")+
                    ", "+item.getInt("numero4")+", "+item.getInt("numero5")+", "+item.getInt("numero6")+ "\n");
        }
        return cadena.toString();
    }


    public static ArrayList<Contacto> analizarContactos(JSONObject respuesta) throws JSONException {
        JSONArray jAcontactos;
        JSONObject jOcontacto, jOtelefono;
        Contacto contacto;
        Telefono telefono;
        ArrayList<Contacto> personas;

        personas = new ArrayList<>();
        jAcontactos = respuesta.getJSONArray("contactos");
        for (int i = 0; i < jAcontactos.length(); i++) {
            jOcontacto = jAcontactos.getJSONObject(i);
            contacto = new Contacto();
            contacto.setNombre(jOcontacto.getString("nombre"));
            contacto.setDireccion(jOcontacto.getString("direccion"));
            contacto.setEmail(jOcontacto.getString("email"));

            jOtelefono = jOcontacto.getJSONObject("telefono");
            telefono = new Telefono();
            telefono.setCasa(jOtelefono.getString("casa"));
            telefono.setMovil(jOtelefono.getString("movil"));
            telefono.setTrabajo(jOtelefono.getString("trabajo"));

            contacto.setTelefono(telefono);
            personas.add(contacto);

        }

        return personas;
    }

    public static void escribirJSON(ArrayList<Noticia> noticias, String fichero) throws IOException, JSONException
    {
        OutputStreamWriter out;
        File miFichero;
        JSONObject objeto, rss, item;
        JSONArray lista;
        miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fichero);
        out = new FileWriter(miFichero);

        objeto = new JSONObject();
        objeto.put("web", "http://www.alejandrosuarez.es/");
        objeto.put("link", "http://www.alejandrosuarez.es/feed/");
        lista = new JSONArray();


        for (int i = 0; i < noticias.size(); i++) {
            item = new JSONObject();
            item.put("titular", noticias.get(i).getTitle());
            item.put("descripcion", noticias.get(i).getDescription());
            item.put("enlace", noticias.get(i).getLink());
            item.put("fecha", noticias.get(i).getPubDate());
            lista.put(item);
        }
        objeto.put("titulares", lista);

        rss = new JSONObject();
        rss.put("rss", objeto);


        out.write(rss.toString(4)); //tabulación de 4 caracteres
        out.flush();
        out.close();
        Log.i("info", objeto.toString());
    }

    public static void escribirGSON(ArrayList<Noticia> noticias, String fichero) throws IOException {
        OutputStreamWriter out;
        File miFichero;
        Titular titulares;
        String texto;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy");
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        titulares = new Titular();
        titulares.setWeb("http://www.alejandrosuarez.es/");
        titulares.setFeed("http://www.alejandrosuarez.es/feed/");
        titulares.setTitulares(noticias);
        texto = gson.toJson(titulares);

        miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fichero);
        out = new FileWriter(miFichero);
        out.write(texto);
        out.close();
    }

}
