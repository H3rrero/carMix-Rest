package com.carmix.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;

	private String user,password;
	
	@Column(name="generomusical")
	private String generoMusical;
	
//	@OneToMany(mappedBy="id.usuario")
//	private List<UserViaje> viajes;

	 
	public String getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGeneroMusical() {
		return generoMusical;
	}

	public void setGeneroMusical(String generoMusical) {
		this.generoMusical = generoMusical;
	}

//	public List<UserViaje> getViajes() {
//		return viajes;
//	}
//
//	public void setViajes(List<UserViaje> viajes) {
//		this.viajes = viajes;
//	}
	
}