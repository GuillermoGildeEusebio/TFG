package com.TFG.springbootApp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import com.TFG.springbootApp.entity.Administrador;
import com.TFG.springbootApp.entity.Taxi;
import com.TFG.springbootApp.entity.User;
import com.TFG.springbootApp.entity.Viaje;

public interface Servicio {
	
	public void connect();
	
	public boolean register(User usuario) throws IOException, SQLException;
	
	public ArrayList<Taxi> getList(int linea) throws SQLException;
	
	public int getLinea(Administrador admin) throws SQLException;
	
	public ArrayList<Viaje> getViajes(Administrador admin) throws SQLException ;
	
	public boolean acceptViaje(int viaje) throws SQLException;
	
	public boolean cancelViaje(int viaje) throws SQLException;
	
	public boolean validateUser(User usuario) throws IOException, SQLException;
	
	public boolean login(String email, String contraseña) throws SQLException;
	
	public boolean order(Viaje viaje) throws SQLException;
	
	public int getIdusuario(String correo,String contraseña) throws SQLException;
	
	public boolean logViajes(Administrador admin) throws SQLException, IOException;

}
