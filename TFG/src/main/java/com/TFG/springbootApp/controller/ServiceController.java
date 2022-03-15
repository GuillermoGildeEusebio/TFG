package com.TFG.springbootApp.controller;





import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.springbootApp.service.Servicio;
import com.TFG.springbootApp.service.requestLibrAIryTokens;
import com.TFG.springbootApp.service.requestLibrAIryAnnotationsGroups;
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

	private List<String[]> posiblesExplicaciones_Aposiciones(String text){

		return null;
	}



	@Autowired
	Servicio servicio;


	@GetMapping({"/explicacionDeteccion"})
	public String explicacionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {

		List<String[]> blockWords = new LinkedList<String[]>();

		List<String[]> posiblesExplicaciones = new LinkedList<String[]>();

		List<String[]> posiblesExplicacionesVerbo = new LinkedList<String[]>();
		
		List<String[]> explicacionesDetectadas = new LinkedList<String[]>();

		int id = 1;
		String name = "Regla - Explicaciones entre comas";
		String description = "Detectar el uso de explicaciones entre comas.";
		boolean pass = true;
		


		/*
		 *  Cogemos el texto y lo dividimos por las comas 
		 *  para identificar mejor las explicaciones o aposiciones
		 */
		String[] block = text.split(",");
		for (String words : block){
			String[] word = words.split(" ");
			blockWords.add(word);
		}

		/*
		 * Vemos si tiene . en los bloques para la identificacion de 
		 * falsas aposiciones o explicaciones
		 */

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


		/*
		 * Para que entren en la posibilidad de ser posibles explicaciones deben tener un verbo en ella
		 */

		for (int i = 0; i < posiblesExplicaciones.size(); i++) {
			String frase = "";
			for (String tokens : posiblesExplicaciones.get(i)){
				frase  = frase +  " " + tokens;
			}
			
			//llamada a Libraly

			String[] filter = {"VERB"};
			String url = "tokens"; //Servicio a utilizar de LibrAIry
			
			String response = requestLibrAIryTokens.request(filter, frase, url);
			if (!response.contains("ERROR")) {

				JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

				String tokens = responseJSON.get("tokens").toString();
				if(tokens.length() != 2)   //tiene verbo entre comas
					posiblesExplicacionesVerbo.add(posiblesExplicaciones.get(i));

			}

		}
		
		
		/*
		 * Para la identificacion de explicaciones lo que hay antes de la coma debe ser un pronombre, un nombre o un nombre propio
		 */
		

		for (int i = 0; i < posiblesExplicacionesVerbo.size(); i++) {
		
			int j = blockWords.indexOf(posiblesExplicacionesVerbo.get(i));
			
			String [] blockBefore = blockWords.get(j-1);
			
			String posibleSujeto = blockBefore[blockBefore.length-1];
			
			
			
			String[] filter = new String[3];
			filter[0] = "PROPER_NOUN";
			filter[1] = "PRONOUN";
			filter[2] = "NOUN";
			
			String url = "tokens"; //Servicio a utilizar de LibrAIry
			String response = requestLibrAIryTokens.request(filter, posibleSujeto, url);
			System.out.println(response);
			if (!response.contains("ERROR")) {

				JsonObject responseJSON = new Gson().fromJson(response, JsonObject.class);

				String tokens = responseJSON.get("tokens").toString();
				if(tokens.length() != 2)   //Tiene un pronombre, un nombre o un nombre propioantes de las comas
					explicacionesDetectadas.add(posiblesExplicacionesVerbo.get(i));

			}
		}

		
		String reason = "El texto contiene las siguientes explicaciones [";
		for (int i = 0; i < explicacionesDetectadas.size(); i++) {
			String explicacion = "";
			for (String tokens : explicacionesDetectadas.get(i)){
				explicacion  = explicacion +  " " + tokens;
			}
			reason = reason + explicacion;
			if(i!=explicacionesDetectadas.size()-1)
				reason = reason + " , ";
			
		}
		
		reason = reason + "]";

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
