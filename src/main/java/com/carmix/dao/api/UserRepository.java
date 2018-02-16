package com.carmix.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carmix.model.Usuario;
import com.carmix.model.Viaje;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long>{
	
	@Query("select u from Usuario u where u.user = ?1 and u.password = ?2")
	public Usuario logIn(String login, String password);

	@Query("select uv.id.viaje from UserViaje uv where uv.id.usuario.id = ?1 and uv.role = 'creador'")
	public List<Viaje> findViajesPorusuario(Long id);

	

}
