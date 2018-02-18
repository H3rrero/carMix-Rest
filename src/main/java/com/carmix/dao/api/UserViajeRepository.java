package com.carmix.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carmix.model.UserViaje;
import com.carmix.model.UserViajeId;

@Repository
public interface UserViajeRepository extends CrudRepository<UserViaje, UserViajeId>{

	@Query("delete from UserViaje uv where uv.id.viaje.id = ?1")
	void deleteViaje(Long id);
	
	@Query("select uv from UserViaje uv where uv.id.viaje.id = ?1")
	List<UserViaje> getViaje(Long id);
	
}