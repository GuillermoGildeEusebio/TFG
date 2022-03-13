package com.TFG.springbootApp.controller;





import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TFG.springbootApp.service.Servicio;






@RestController
public class ServiceController {	
	
	
	
	
	
@Autowired
	Servicio servicio;
	

	@GetMapping({"/explicacionDeteccion"})
    public String explicacionDeteccion(@RequestParam(value = "text", defaultValue = "") String text) throws IOException {


	return text;
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
