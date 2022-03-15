package com.TFG.springbootApp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class requestLibrAIryTokens {

    /* Las consultas a cualquier servicio de LibrAIry se realizan con el siguiente JSON
    *       String request = "{" +
            " \"filter\": [ \"ADVERB\" ], \n" +
            "  \"lang\": \"es\",\n" +
            "  \"multigrams\": false,\n" +
            "  \"references\": false,\n" +
            "  \"synset\": false,\n" +
            "  \"text\": \"aqui pongo el texto\"\n" +
            "}";
    * */
    public static class JSONlibrAIry{
        String[] filter;
        String form;
        String lang;
        boolean multigrams;
        String text;

        //Constructor
        public JSONlibrAIry(String[] filter, String text){
            if (filter.length == 0) {
                this.filter = null;
            }
            else {
                this.filter = filter;
            }
            form = "RAW";
            lang = "es";
            multigrams = true;

            this.text = text;
        }


    }

    // Convierte un objeto JSONlibrAIry a un String con el formato JSON
    public static String codificador(JSONlibrAIry res){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(res);
    }

    // filter: ADVERB, VERB, NOUN ....
    // text: el texto que se quiere analizar con LibrAIry
    // url: tokens; es la url especifica del servicio
    public static String request(String[] filter, String text, String url) throws IOException {
        String urlComplete = "http://librairy.linkeddata.es/nlp/" + url;
        URLConnection urlObject = new URL(urlComplete).openConnection();
        HttpURLConnection httpcon = (HttpURLConnection) urlObject;

        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestMethod("POST");
        httpcon.connect();

        java.io.OutputStream os = httpcon.getOutputStream();
        os.write(codificador(new JSONlibrAIry(filter, text)).getBytes());

        String response = "";

        int responseCode = httpcon.getResponseCode();

        //El codigo 200 indica que la peticion ha sido correcta
        if (responseCode != 200) {
            return "ERROR: " + responseCode + httpcon.getErrorStream();
        }else {
            InputStream inputStream = httpcon.getInputStream();
            response = IOUtils.toString(inputStream, "UTF-8");
        }

        os.close();
        return response;
    }
}
