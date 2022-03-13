package com.TFG.springbootApp.service;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


import org.springframework.stereotype.Service;

import com.TFG.springbootApp.entity.Administrador;
import com.TFG.springbootApp.entity.Taxi;
import com.TFG.springbootApp.entity.User;
import com.TFG.springbootApp.entity.Viaje;




@Service
public class ServicioImpl implements Servicio {
	public Connection conn;
	

	public ServicioImpl() {

	}
	
	public void connect() {
		
		try{
	         conn = DriverManager.getConnection("jdbc:sqlite:sqlite-tools-win32-x86-3360000\\mibase.db" );
	         if ( conn != null ){
	            
	         }
	      }
	      catch ( Exception ex ) {
	         
	      }	
		
	}
	
	
	public boolean login(String email, String contraseña) throws SQLException {
        if(conn==null)
            connect();

        String query = "SELECT COUNT() FROM usuario WHERE correo = '" + email + "' AND contraseña = '" + contraseña +"'" + " AND verificado = true ;" ;

        PreparedStatement  pst= null;
        ResultSet rs = null;

        pst = conn.prepareStatement(query);
        rs=pst.executeQuery();

        if(rs.getInt(1) > 0) {
    		pst.close();
    		rs.close();
    		return true;
        }
            

        query = "SELECT COUNT() FROM administrador WHERE usuario = '" + email + "'AND contraseña = '" + contraseña +"';" ;

		pst.close();
		rs.close();

        pst = conn.prepareStatement(query);
        rs=pst.executeQuery();

        if(rs.getInt(1) > 0) {
    		pst.close();
    		rs.close();
    		return true;
        }
            

        return false;
    }
	
	
	public boolean register(User usuario) throws IOException, SQLException{      
		
		if(conn==null)
			connect();
		
		String query = "SELECT COUNT(*) FROM usuario WHERE correo = '" + usuario.getCorreo() + "';" ;
		
		PreparedStatement  pst= null;
		ResultSet rs = null;
		
		pst = conn.prepareStatement(query);
		rs=pst.executeQuery();
		
		if(rs.getInt(1) > 0)
			return false;
		
		pst.close();
		rs.close();
		
		query = "INSERT INTO usuario(usuario,correo,contraseña,telefono,medio_de_pago) values('"+usuario.getNombre() +"','"+usuario.getCorreo() +"','"+usuario.getPassword() 
				+"',"+usuario.getTelefono() +",'"+usuario.getMedio() +"');";
		
		pst = conn.prepareStatement(query);
		pst.addBatch();
		pst.executeBatch();
		
		pst.close();
		rs.close();
		

		

	    

	    	
		
		return true;
	}
	
	public ArrayList<Taxi> getList(int linea) throws SQLException{

        if(conn==null)
            connect();

        String query = "SELECT * FROM taxi WHERE linea = '" + linea + "';" ;

        PreparedStatement  pst= null;
        ResultSet rs = null;

        pst = conn.prepareStatement(query);
        rs=pst.executeQuery();

        ArrayList<Taxi> taxis = new ArrayList<Taxi>();

        while(rs.next()) {
            Taxi taxi = new Taxi();
            taxi.setId(rs.getLong("id"));
            taxi.setLine(rs.getInt("linea"));
            taxi.setPos(rs.getString("ubicacion"));
            taxi.setAvailable(rs.getBoolean("disponibilidad"));
            taxi.setDest(rs.getString("destino"));
            taxi.setCorreo(rs.getString("correo"));
            taxis.add(taxi);
        }

        pst.close();
        rs.close();

        return taxis;
    }
	
	public int getLinea(Administrador admin) throws SQLException{
        if(conn==null)
            connect();

        String query = "SELECT * FROM administrador WHERE usuario = '" + admin.getNombre() + "' AND contraseña = '" + admin.getPassword() + "';";

        PreparedStatement  pst= null;
        ResultSet rs = null;

        pst = conn.prepareStatement(query);
        rs=pst.executeQuery();


        int linea = -1;
        while(rs.next()) {
            linea = rs.getInt("linea");         
        }

        pst.close();
        rs.close();

		
		return linea;
	}
	
	public ArrayList<Viaje> getViajes(Administrador admin) throws SQLException {
		int linea = getLinea(admin);
		ArrayList<Taxi> taxis = getList(linea);
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		if(taxis.size() == 0) {
			return viajes;
		}

	        String query = "SELECT * FROM viaje " ;
	        for (int i = 0; i < taxis.size(); i++) {
				
	        	if (i==0) {
	        		query = query +  "WHERE idtaxi = " + taxis.get(i).getId();                      
				}
	        	else {
	        		query = query +  " OR idtaxi = " + taxis.get(i).getId(); 
	        	}
	        	
	        	
			}

	        PreparedStatement  pst= null;
	        ResultSet rs = null;

	        pst = conn.prepareStatement(query);
	        rs=pst.executeQuery();

	        

	        while(rs.next()) {
	        	Viaje viaje = new Viaje();
	        	viaje.setId(rs.getLong("id"));
	            viaje.setDate(rs.getString("fecha"));
	            viaje.setDestiny(rs.getString("destino"));
	            viaje.setIdtaxi(rs.getInt("idtaxi"));
	            viaje.setIduser(rs.getInt("idusuario"));
	            viaje.setInitial(rs.getString("origen"));
	            viaje.setTime(rs.getString("hora"));
	            viaje.setVerificado(rs.getInt("verificado"));
	            viajes.add(viaje);
	        }

	        pst.close();
	        rs.close();
		
		return viajes;
	}
	
	
	
	
	public boolean acceptViaje(int idviaje) throws SQLException {
		
		if(conn==null)
			connect();
		
		
		String query = "UPDATE viaje SET verificado = 1 WHERE id = '" + idviaje + "';" ;
		
		PreparedStatement  pst= null;
		ResultSet rs = null;

		
		pst = conn.prepareStatement(query);
		pst.addBatch();
		pst.executeBatch();
		

		pst.close();
		
		
		
		query = "SELECT * FROM viaje WHERE id='"+ idviaje+"';" ;
		
        pst = conn.prepareStatement(query);
        rs=pst.executeQuery();
		
		Viaje viaje = new Viaje();
		if(rs.next()) {
			viaje.setId(rs.getLong("id"));
            viaje.setDate(rs.getString("fecha"));
            viaje.setDestiny(rs.getString("destino"));
            viaje.setIdtaxi(rs.getInt("idtaxi"));
            viaje.setIduser(rs.getInt("idusuario"));
            viaje.setInitial(rs.getString("origen"));
            viaje.setTime(rs.getString("hora"));
            viaje.setVerificado(rs.getInt("verificado"));
            
		}

		String correoUsario = null;
		String nombreUsuario = null;
		String correoTaxi = null;
		
		String queryU = "SELECT correo FROM usuario WHERE id = '" + viaje.getIduser() + "';" ;
		String queryUN = "SELECT usuario FROM usuario WHERE id = '" + viaje.getIduser() + "';" ;
		String queryT = "SELECT correo FROM taxi WHERE id = '" + viaje.getIdtaxi() + "';" ;
		
		pst = conn.prepareStatement(queryU);
		rs= pst.executeQuery();
		
		if(rs.next()) {
			correoUsario= rs.getString(1);
		}
		
		pst.close();
		rs.close();

		
		pst = conn.prepareStatement(queryUN);
		rs= pst.executeQuery();
		
		if(rs.next()) {
			nombreUsuario= rs.getString(1);
		}
		
		pst.close();
		rs.close();
		
		pst = conn.prepareStatement(queryT);
		rs= pst.executeQuery();
		
		if(rs.next()) {
			correoTaxi= rs.getString(1);
		}
		
		
		pst.close();
		rs.close();



		
		return true;
	}
	
	
	
	
	public boolean cancelViaje(int idviaje) throws SQLException {
		
		if(conn==null)
			connect();
		
		String query = "UPDATE viaje SET verificado = -1 WHERE id = '" + idviaje + "';" ;
		
		PreparedStatement  pst= null;

		
		pst = conn.prepareStatement(query);
		pst.addBatch();
		pst.executeBatch();
		

		pst.close();
		
		
		 
		
		return true;
	}
	

	
	public boolean validateUser(User usuario) throws IOException, SQLException {
		
		if(conn==null)
			connect();
		
		String query = "UPDATE usuario SET verificado = true WHERE correo = '" + usuario.getCorreo() + "';" ;
		
		PreparedStatement  pst= null;

		
		pst = conn.prepareStatement(query);
		pst.addBatch();
		pst.executeBatch();
		

		pst.close();
		
		return true;
	}
	
	public boolean order(Viaje viaje) throws SQLException{
        if(conn==null)
            connect();

		String query = "INSERT INTO viaje(origen,destino ,fecha ,hora ,verificado,idusuario,idtaxi) values('"+viaje.getInitial() +"','"+viaje.getDestiny()
		+"','"+viaje.getDate() +"','"+viaje.getTime() +"',"+viaje.getVerificado() +","+viaje.getIduser() +","+viaje.getIdtaxi()+");";

        PreparedStatement  pst= null;


        pst = conn.prepareStatement(query);
		pst.addBatch();
		pst.executeBatch();


        pst.close();
        
        return true;

		
	}
	
	public int getIdusuario(String correo,String contraseña) throws SQLException{
        if(conn==null)
            connect();

		String query = "SELECT id FROM usuario WHERE correo = '" + correo + "' AND contraseña = '" + contraseña + "';" ;

        PreparedStatement  pst= null;
        ResultSet rs = null;

        pst = conn.prepareStatement(query);
		rs= pst.executeQuery();
		
		int idUsuario = 0;
		
		if(rs.next()) {
			idUsuario= rs.getInt(1);
		}
		rs.close();
		pst.close();
		return idUsuario;
		
	}
	
	
	public boolean logViajes(Administrador admin) throws SQLException, IOException {
		int linea = getLinea(admin);
		ArrayList<Taxi> taxis = getList(linea);
		
		Writer writer = new FileWriter("src\\main\\resources\\log.txt");
		
		if(taxis.size() == 0) {
			writer.close();
			return true;
		}

	        String query = "SELECT * FROM viaje " ;
	        for (int i = 0; i < taxis.size(); i++) {
				
	        	if (i==0) {
	        		query = query +  "WHERE idtaxi = " + taxis.get(i).getId()  ;                      
				}
	        	else {
	        		query = query +  " OR idtaxi = " + taxis.get(i).getId()  ; 
	        	}
	        	
	        	
			}

	        PreparedStatement  pst= null;
	        ResultSet rs = null;

	        pst = conn.prepareStatement(query);
	        rs=pst.executeQuery();

	        

	        while(rs.next()) {
	        	Viaje viaje = new Viaje();
	        	viaje.setId(rs.getLong("id"));
	            viaje.setDate(rs.getString("fecha"));
	            viaje.setDestiny(rs.getString("destino"));
	            viaje.setIdtaxi(rs.getInt("idtaxi"));
	            viaje.setIduser(rs.getInt("idusuario"));
	            viaje.setInitial(rs.getString("origen"));
	            viaje.setTime(rs.getString("hora"));
	            viaje.setVerificado(rs.getInt("verificado"));
	            
	            String res = viaje.getId().toString() + " " + viaje.getInitial() + " " + viaje.getDestiny() + " " + viaje.getDate() + " " + viaje.getTime() + " " + viaje.getVerificado()+ " " + viaje.getIduser() + " " + viaje.getIdtaxi()  + "\r\n";
	            
	            writer.write(res);
	            
	            
	        }
	        
	        writer.close();
	        pst.close();
	        rs.close();
		
		return true;
	}
	
	
	
	
	
	
	

}
