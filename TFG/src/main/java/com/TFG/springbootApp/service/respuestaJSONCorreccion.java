package com.TFG.springbootApp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//REGLAS DE CORRECCION
public class respuestaJSONCorreccion {


    public static class result{
        int id; //id de la regla
        String name; //nombre de la regla
        String description; //descripcion de la regla
        //En caso de que haya que hacer transformaciones en el texto, se obtiene el texto con las transformaciones
        //Si no hay que realizar cambios, textCorrected = Not Apply
        String textCorrected;

        //Constructor
        public result(int id, String name, String description, String textCorrected){
            this.id = id;
            this.name = name;
            this.description = description;
            this.textCorrected = textCorrected;
        }


    }
    //Obtener el JSON
    public static String codificador(int id, String name, String description, String textCorrected){
        result res = new result(id,name,description,textCorrected);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(res);
    }

}
