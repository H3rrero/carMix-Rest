package com.carmix.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.carmix.business.api.UserService;
import com.carmix.business.dto.UserTokenDto;
import com.carmix.business.dto.ViajeDto;
import com.carmix.model.Usuario;
import com.carmix.rest.api.UserRestService;

@Component
public class UserRestServiceImpl implements UserRestService{

	@Autowired
	UserService userService;
	
	@Override
	public ResponseEntity<UserTokenDto> logIn(@RequestBody Usuario usuario) {
		System.out.println(usuario.getUser());
		System.out.println(usuario.getPassword());
		
		Usuario usuarioBD = userService.logIn(usuario);
		System.out.println(usuarioBD);
		UserTokenDto u = new UserTokenDto();
		u.setToken("tokenGeneradoPorCarMix");
		u.setId(usuarioBD.getId());
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
		
		Usuario u = this.userService.getUser(id);
		if(u == null){
			return new ResponseEntity<>(u, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ViajeDto>> getViajesUsuario(@PathVariable Long id) {
		List<ViajeDto> viajes = this.userService.findViajesUsuario(id);
		if(viajes == null){
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(viajes, HttpStatus.OK);
	}

}
