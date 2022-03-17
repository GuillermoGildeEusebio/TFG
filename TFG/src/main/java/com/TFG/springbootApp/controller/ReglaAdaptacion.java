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

import com.TFG.springbootApp.service.requestLibrAIryTokens;
import com.TFG.springbootApp.auxiliarService.respuestaJSONCorreccion;
import com.TFG.springbootApp.auxiliarService.respuestaJSONDeteccion;
import com.TFG.springbootApp.service.requestLibrAIryAnnotationsGroups;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;






@RestController
public class ReglaAdaptacion {	




	@GetMapping({"/apopsicionDeteccion"})
	public String apopsicionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {


		return text;
	}

	@GetMapping({"/aposicionAdaptacion"})
	public String aposicionAdaptacion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {


		return text;
	}	


}
