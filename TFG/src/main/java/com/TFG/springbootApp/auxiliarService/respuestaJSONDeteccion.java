package com.TFG.springbootApp.auxiliarService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

// REGLAS DE DETECCION
public class respuestaJSONDeteccion {


    public static class result{
        int id; //id de la regla
        String name; //nombre de la regla
        String description; //descripcion de la regla
        //pass y reason publicos para poder acceder desde las clases de prueba
        public boolean pass; //el texto ha pasado la regla
        public String reason; //pass = true ->Not Apply ; pass = false -> Por que no ha pasado la regla

        //Constructor
        public result(int id, String name, String description, boolean pass, String reason){
            this.id = id;
            this.name = name;
            this.description = description;
            this.pass = pass;
            this.reason = reason;
        }


    }
    //Obtener el JSON
    public static String codificador(int id, String name, String description, boolean pass, String reason){
        result res = new result(id, name, description, pass, reason);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(res);
    }
    //Dado el JSON, acceder a los atributos del mismo
    public result decodificador(String json){
        Gson gson=new Gson();
        Type tipoObjeto = new TypeToken<result>(){}.getType();
        return gson.fromJson(json, tipoObjeto);
    }

}
