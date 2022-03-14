package com.TFG.springbootApp.controller;





import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.springbootApp.service.Servicio;
import com.TFG.springbootApp.service.requestLibrAIry;
import com.TFG.springbootApp.service.respuestaJSONDeteccion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;






@RestController
public class ServiceController {	
	
	
    //Elimina aquello que no sean letras
    private String tratamientoText (String text){
        return text.replaceAll("[^A-Za-zÀ-ÿ\u00f1\u00d1]"," ");
    }	
	
	
@Autowired
	Servicio servicio;
	

	@GetMapping({"/explicacionDeteccion"})
    public String explicacionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {
		
		List<String[]> blockWords = new LinkedList<String[]>();
		
		List<String[]> posiblesExplicaciones = new LinkedList<String[]>();

        int id = 1;
        String name = "Regla - Explicaciones entre comas";
        String description = "Detectar el uso de explicaciones entre comas.";
        boolean pass = true;
        String reason = "Not Apply";


        String[] block = text.split(",");
        
        
        for (String words : block){
            String[] word = words.split(" ");
            blockWords.add(word);
        }
        
        for (int i = 1; i < block.length; i++) {
        	boolean posibleExplicacion = true;
        	String[] palabras  =  blockWords.get(i);
        	for (String tokens : palabras){
        		if(tokens.indexOf(".") != -1)
        			posibleExplicacion = false;
        	}
        	if(posibleExplicacion)
        		posiblesExplicaciones.add(palabras);
        		
        }
        
        System.out.println("POSIBLES EXPLICACIONES");
        for (int i = 0; i < posiblesExplicaciones.size(); i++) {
        	for (String tokens : posiblesExplicaciones.get(i)){
        		System.out.print(tokens+ " ");
        	}
			System.out.println();
		}

        

        return respuestaJSONDeteccion.codificador(id,name,description, pass, reason);	
		
	}	
	
	@GetMapping({"/explicacionAdaptacion"})
    public String explicacionAdaptacion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {
		
		
		
			


	return text;
	}	
	
	@GetMapping({"/apopsicionDeteccion"})
    public String apopsicionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {


	return text;
	}
	
	@GetMapping({"/aposicionAdaptacion"})
    public String aposicionAdaptacion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {


	return text;
	}	
	

}
