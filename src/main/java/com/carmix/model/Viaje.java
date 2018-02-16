package com.carmix.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viaje")
public class Viaje {

	@Id
	@GeneratedValue
	private Long id;
	
	private String origen, destino, descripcion;
	
	private int plazas;
	
	private BigDecimal precio;
	
//	@OneToMany(mappedBy="id.viaje")
//	private List<Users> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

//	public List<UserViaje> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<UserViaje> users) {
//		this.users = users;
//	}

	
}
