package com.carmix.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_viaje")
public class UserViaje {

	@EmbeddedId
	private UserViajeId id;
	
	private String role;

	public UserViajeId getId() {
		return id;
	}

	public void setId(UserViajeId id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
