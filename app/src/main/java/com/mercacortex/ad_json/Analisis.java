package com.mercacortex.ad_json;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Analisis {
    public static ArrayList<Noticia> analizarNoticias(File file) throws XmlPullParserException, IOException {
        int eventType;
        ArrayList<Noticia> noticias = null;
        Noticia actual = null;
        boolean dentroItem = false;

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();

        while (eventType!=XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    noticias = new ArrayList<>();
                    break;

                case XmlPullParser.START_TAG:
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = true;
                        actual = new Noticia();
                    }
                    if(dentroItem)
                        if (xpp.getName().equalsIgnoreCase("title"))
                            actual.setTitle(xpp.nextText());
                        else if (xpp.getName().equalsIgnoreCase("link"))
                            actual.setLink(xpp.nextText());
                        else if (xpp.getName().equalsIgnoreCase("description"))
                            actual.setDescription(xpp.nextText());
                        else if (xpp.getName().equalsIgnoreCase("pubdate"))
                            actual.setPubDate(xpp.nextText());
                    break;

                case XmlPullParser.END_TAG:
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = false;
                        noticias.add(actual);
                    }
                    break;
            }
            eventType = xpp.next();
        }
        //devolver el array de noticias
        return noticias;
    }
}
