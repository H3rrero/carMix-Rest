package com.carmix.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carmix.model.Usuario;
import com.carmix.model.Viaje;

@Repository
public interface ViajesRepository extends CrudRepository<Viaje, Long>{
	
	@Query("select uv.id.usuario from UserViaje uv where uv.id.viaje.id = ?1 and UPPER(uv.role) = 'CREADOR'")
	public Usuario getCreadorViaje(Long id);
	
	@Query("select uv.id.usuario from UserViaje uv where uv.id.viaje.id = ?1 and UPPER(uv.role) = 'INVITADO'")
	public List<Usuario> getUsuariosViaje(Long id);
	
	

}
