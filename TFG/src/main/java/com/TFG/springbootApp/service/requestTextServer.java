package com.TFG.springbootApp.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class requestTextServer {
    // service base URL
    private String url = "http://frodo.lsi.upc.edu:8080/TextWS/textservlet/ws/processQuery/";

    // Idioma
    private String lang = "es";
    // Formato de salida (xml,json,conll,naf)
    private String out = "json";
    // Usuario y contrasenia para la cuenta de TextServer
    // Este cuenta tiene llamadas ilimitadas al servicio Desambiguacion PoS hasta el 31 de agosto de 2020
    private String user = "TeresaGCG";
    private String pwd = "miContras3nia.";

    public String request(String text, String service) throws IOException {

        // Se crea la peticion rellenando los parametros necesarios
        HttpPost request = new HttpPost(url+service);
        request.setEntity(MultipartEntityBuilder.create()
                .addTextBody("username", user)
                .addTextBody("password", pwd)
                .addTextBody("text_input", text)
                .addTextBody("language", lang)
                .addTextBody("output", out)
                .addTextBody("interactive", "1")
                .build()
        );

        // Crear cliente, mandar peticion, recibir respuesta
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        String content = EntityUtils.toString(response.getEntity());

        // Manejar errores en la peticion
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            System.out.println(response.getStatusLine() + " - " + content);
            System.out.println("\nFALLO LLAMADA TEXTSERVER");
            content = "ERROR";
        }

        return content;
    }
}
