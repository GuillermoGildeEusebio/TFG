package com.TFG.springbootApp.entity;

public class Viaje {
	
	private Long id;
	
	private String initial;
	
	private String destiny;
	
	private String date;
	
	private String time;
	
	private int iduser;
	
	private int idtaxi;
	
	private int verificado;
	
	public Viaje() {
		
	}
	
	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public int getIdtaxi() {
		return idtaxi;
	}

	public void setIdtaxi(int idtaxi) {
		this.idtaxi = idtaxi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	public int getVerificado() {
		return verificado;
	}

	public void setVerificado(int verificado) {
		this.verificado = verificado;
	}
}
