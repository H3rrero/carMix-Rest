package com.carmix.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserViajeId implements Serializable {

	@ManyToOne
	@JoinColumn(name="FK_user_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="FK_viaje_id")
	private Viaje viaje;

	public UserViajeId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserViajeId(Usuario usuario, Viaje viaje) {
		super();
		this.usuario = usuario;
		this.viaje = viaje;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	
	
	
}
